Feature: INEB - ENROLLMENT (A,C - C,C) DECIDE, CREATE AND UPDATE : MMIS SEND RECONCILIATION

  @API-CP-31578 @API-CP-31579 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: CP-31578 IN-EB ENROLLMENT (A-C or C-C) Decide Scenario: MMIS Sends Reconciliation (Plan Match non HIP) (Positive)
  CP-31579 IN-EB ENROLLMENT (A,C or C,C) Create or Update: MMIS Sends Reconciliation
  CP-31579 AC 3.0 Two Inbound Records (A-C or C-C) with Same Plan and with Plan Match to Enrollment in System, Update PCP Selection and End Date
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
      | isEnrollmentProviderRequired | yes                        |
      | providerNpi                  | 1                          |
      | providerStartDate            | 1stDayofPresentMonth       |
      | providerEndDate              | 90DayFromFirstDayOfMonth   |
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
      | planCode                     | <planCode>                 |
      | planId                       | 104                        |
      | serviceRegionCode            | Statewide                  |
      | anniversaryDate              | anniversaryDate            |
      | channel                      | SYSTEM_INTEGRATION         |
      | selectionReason              | selectionReason1           |
      | planEndDateReason            | planEndDateReason1         |
      | rejectionReason              | rejectionReason1           |
      | fileSource                   | RECIPIENT                  |
      | requestedBy                  | 1                          |
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
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate | 1stDayofPresentMonth       |
      | medicalPlanEndDate   | <initialEnrollmentEndDate> |
      | enrollmentType       | MEDICAL                    |
      | channel              | SYSTEM_INTEGRATION         |
      | anniversaryDate      | anniversaryDate            |
      | planCode             | <planCode>                 |
      | status               | <initialStatus>            |
      | selectionReason      | selectionReason1           |
      | planEndDateReason    | planEndDateReason1         |
      | rejectionReason      | rejectionReason1           |
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
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil>   |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                 |
#      | correlationId | <name>a.traceid       |
      | correlationId | <name>a.enrollments.[0].correlationId |
      | consumerId    | <name>.consumerId                     |
      | consumerFound | true                                  |
      | DPBI          |[blank]|

    # Now running our scenario with A + C or C + C records
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId            |
      | isEligibilityRequired        | no                           |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                          |
      | recordId                     | 21                           |
      | eligibilityRecordId          | 21                           |
      | enrollmentRecordId           | 21                           |
      | isEnrollmentProviderRequired | yes                          |
      | providerNpi                  | 2                            |
      | providerStartDate            | 3rdDayofNextMonth            |
      | providerEndDate              | 14DayFromFirstDayOfNextMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth         |
      | eligibilityEndDate           | highDate                     |
      | enrollmentStartDate          | 1stDayofPresentMonth         |
      | enrollmentEndDate            | highDate                     |
      | txnStatus                    | Accepted                     |
      | programCode                  | <programCode>                |
      | subProgramTypeCode           | <subProgramTypeCode>         |
      | eligibilityStatusCode        | M                            |
      | categoryCode                 | 10                           |
      | genericFieldText1            | null                         |
      | coverageCode                 | cc01                         |
      | planCode                     | <planCode>                   |
      | planId                       | 104                          |
      | serviceRegionCode            | Statewide                    |
      | anniversaryDate              | anniversaryDate              |
      | channel                      | SYSTEM_INTEGRATION           |
      | selectionReason              | selectionReason2             |
      | planEndDateReason            | planEndDateReason2           |
      | rejectionReason              | rejectionReason2             |
      | fileSource                   | RECIPIENT                    |
      | requestedBy                  | 2                            |
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
      | planCode            | <planCode>           |
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
    Then I send API call with name "<name>b" for create Eligibility and Enrollment
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
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate                 | 1stDayofPresentMonth         |
      | medicalPlanEndDate                   | highDate                     |
      | enrollmentType                       | MEDICAL                      |
      | channel                              | SYSTEM_INTEGRATION           |
      | anniversaryDate                      | anniversaryDate              |
      | planCode                             | <planCode>                   |
      | status                               | ACCEPTED                     |
      | selectionReason                      | selectionReason1             |
      | planEndDateReason                    | planEndDateReason2           |
      | rejectionReason                      | rejectionReason1             |
      | enrollmentProvider.providerNpi       | 2                            |
      | enrollmentProvider.providerStartDate | 3rdDayofNextMonth            |
      | enrollmentProvider.providerEndDate   | 14DayFromFirstDayOfNextMonth |
      | updatedOn                            | current                      |
      | updatedBy                            | 2                            |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population> |
      | population          | <population> |
      | benefitStatus       | Enrolled     |
      | eligibilityScenario | NO MATCH     |
      | enrollmentScenario  | Plan Match   |
      | timeframe           | Active       |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available                |
      | [0].consumerAction       | Plan Change Pre-lockin   |
      | [0].planSelectionAllowed | true                     |
      | [0].changeAllowedFrom    | 1stDayofPresentMonth     |
      | [0].changeAllowedUntil   | 90DayFromFirstDayOfMonth |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT               |
#      | correlationId | <name>b.traceid         |
      | correlationId | <name>a.enrollments.[0].correlationId |
      | consumerId    | <name>.consumerId                     |
      | consumerFound | true                                  |
      | DPBI          |[blank]|
    Examples: non HIP populations
      | name    | initialEnrollmentEndDate | initialStatus | initialBenefitStatus | programCode | subProgramTypeCode | population    | planCode  | enrollReconAction | action      | consumerAction         | planSelectionAllowed | changeAllowedFrom    | changeAllowedUntil       |
      | 31578-1 | highDate                 | ACCEPTED      | Enrolled             | R           | HoosierHealthwise  | HHW-Mandatory | 400752220 | A                 | Available   | Plan Change Pre-lockin | true                 | 1stDayofPresentMonth | 90DayFromFirstDayOfMonth |
      | 31578-2 | lastDayOfThePresentYear  | DISENROLLED   | Eligible             | R           | HoosierHealthwise  | HHW-Mandatory | 400752220 | A                 | Unavailable | null                   | false                | null                 | null                     |
      | 31578-3 | highDate                 | ACCEPTED      | Enrolled             | A           | HoosierCareConnect | HCC-Mandatory | 499254630 | C                 | Available   | Plan Change Pre-lockin | true                 | 1stDayofPresentMonth | 90DayFromFirstDayOfMonth |
      | 31578-4 | lastDayOfThePresentYear  | DISENROLLED   | Eligible             | A           | HoosierCareConnect | HCC-Mandatory | 499254630 | C                 | Unavailable | null                   | false                | null                 | null                     |


  @API-CP-31578 @API-CP-31579 @API-EE-IN @IN-EB-API-Regression @kursat @API-CP-34263
  Scenario Outline: CP-31578 IN-EB ENROLLMENT (A-C or C-C) Decide Scenario: MMIS Sends Reconciliation (Plan Match HIP) (Positive)
  CP-31579 IN-EB ENROLLMENT (A,C or C,C) Create or Update: MMIS Sends Reconciliation
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
      | isEnrollmentProviderRequired | yes                        |
      | providerNpi                  | 1                          |
      | providerStartDate            | 1stDayofPresentMonth       |
      | providerEndDate              | 90DayFromFirstDayOfMonth   |
      | eligibilityStartDate         | 1stDayofPresentMonth       |
      | eligibilityEndDate           | highDate                   |
      | enrollmentStartDate          | 1stDayofPresentMonth       |
      | enrollmentEndDate            | <initialEnrollmentEndDate> |
      | txnStatus                    | Accepted                   |
      | programCode                  | H                          |
      | subProgramTypeCode           | HealthyIndianaPlan         |
      | eligibilityStatusCode        | M                          |
      | categoryCode                 | 10                         |
      | genericFieldText1            | Eligible                   |
      | coverageCode                 | cc01                       |
      | planCode                     | 455701400                  |
      | planId                       | 104                        |
      | serviceRegionCode            | Statewide                  |
      | anniversaryDate              | anniversaryDate            |
      | channel                      | SYSTEM_INTEGRATION         |
      | selectionReason              | selectionReason1           |
      | planEndDateReason            | planEndDateReason1         |
      | rejectionReason              | rejectionReason1           |
      | fileSource                   | HRECIP                     |
      | requestedBy                  | 1                          |
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
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | 1stDayofPresentMonth |
      | eligibilityEndDate    | highDate             |
      | categoryCode          | 10                   |
      | programCode           | H                    |
      | subProgramTypeCode    | HealthyIndianaPlan   |
      | eligibilityStatusCode | M                    |
      | genericFieldText1     | Eligible             |
      | genericFieldText2     | 0                    |
      | createdOn             | current              |
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate | 1stDayofPresentMonth       |
      | medicalPlanEndDate   | <initialEnrollmentEndDate> |
      | enrollmentType       | MEDICAL                    |
      | channel              | SYSTEM_INTEGRATION         |
      | anniversaryDate      | anniversaryDate            |
      | planCode             | 455701400                  |
      | status               | <initialStatus>            |
      | selectionReason      | selectionReason1           |
      | planEndDateReason    | planEndDateReason1         |
      | rejectionReason      | rejectionReason1           |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | HIP-Mandatory          |
      | population          | HIP-Mandatory          |
      | benefitStatus       | <initialBenefitStatus> |
      | eligibilityScenario | NO MATCH               |
      | enrollmentScenario  | No Enrollment          |
      | timeframe           | Active                 |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil>   |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                 |
#      | correlationId | <name>a.traceid       |
      | correlationId | <name>a.enrollments.[0].correlationId |
      | consumerId    | <name>.consumerId                     |
      | consumerFound | true                                  |
      | DPBI          |[blank]|

    # Now running our scenario with A + C or C + C records
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId            |
      | isEligibilityRequired        | no                           |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                          |
      | recordId                     | 21                           |
      | eligibilityRecordId          | 21                           |
      | enrollmentRecordId           | 21                           |
      | isEnrollmentProviderRequired | yes                          |
      | providerNpi                  | 2                            |
      | providerStartDate            | 3rdDayofNextMonth            |
      | providerEndDate              | 14DayFromFirstDayOfNextMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth         |
      | eligibilityEndDate           | highDate                     |
      | enrollmentStartDate          | 1stDayofPresentMonth         |
      | enrollmentEndDate            | highDate                     |
      | txnStatus                    | Accepted                     |
      | programCode                  | H                            |
      | subProgramTypeCode           | HealthyIndianaPlan           |
      | eligibilityStatusCode        | M                            |
      | categoryCode                 | 10                           |
      | genericFieldText1            | null                         |
      | coverageCode                 | cc01                         |
      | planCode                     | 455701400                    |
      | planId                       | 104                          |
      | serviceRegionCode            | Statewide                    |
      | anniversaryDate              | anniversaryDate              |
      | channel                      | SYSTEM_INTEGRATION           |
      | selectionReason              | selectionReason2             |
      | planEndDateReason            | planEndDateReason2           |
      | rejectionReason              | rejectionReason2             |
      | fileSource                   | HRECIP                       |
      | requestedBy                  | 2                            |
    And User provide other enrollment segments details
      | uiid              | uiid::              |
      | segmentTypeCode   | OTHER_TXN           |
      | genericFieldText1 | null                |
      | genericFieldText2 | <enrollReconAction> |
      | genericFieldText3 | MEDICAL             |
    And User provide one more enrollment details
      | consumerId          | <name>.consumerId  |
      | recordId            | 21                 |
      | enrollmentStartDate | 1stDayofNextMonth  |
      | enrollmentEndDate   | highDate           |
      | txnStatus           | Accepted           |
      | programCode         | H                  |
      | subProgramTypeCode  | HealthyIndianaPlan |
      | planCode            | 455701400          |
      | planId              | 104                |
      | serviceRegionCode   | Statewide          |
      | anniversaryDate     | anniversaryDate    |
      | channel             | SYSTEM_INTEGRATION |
      | selectionReason     | selectionReason2   |
      | planEndDateReason   | planEndDateReason2 |
      | rejectionReason     | rejectionReason2   |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | C         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>b" for create Eligibility and Enrollment
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | 1stDayofPresentMonth |
      | eligibilityEndDate    | highDate             |
      | categoryCode          | 10                   |
      | programCode           | H                    |
      | subProgramTypeCode    | HealthyIndianaPlan   |
      | eligibilityStatusCode | M                    |
      | genericFieldText1     | Eligible             |
      | genericFieldText2     | 0                    |
      | createdOn             | current              |
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate                 | 1stDayofPresentMonth         |
      | medicalPlanEndDate                   | highDate                     |
      | enrollmentType                       | MEDICAL                      |
      | channel                              | SYSTEM_INTEGRATION           |
      | anniversaryDate                      | anniversaryDate              |
      | planCode                             | 455701400                    |
      | status                               | ACCEPTED                     |
      | selectionReason                      | selectionReason1             |
      | planEndDateReason                    | planEndDateReason2           |
      | rejectionReason                      | rejectionReason1             |
      | updatedOn                            | current                      |
      | updatedBy                            | 2                            |
      | enrollmentProvider.providerNpi       | 2                            |
      | enrollmentProvider.providerStartDate | 3rdDayofNextMonth            |
      | enrollmentProvider.providerEndDate   | 14DayFromFirstDayOfNextMonth |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | HIP-Mandatory |
      | population          | HIP-Mandatory |
      | benefitStatus       | Enrolled      |
      | eligibilityScenario | NO MATCH      |
      | enrollmentScenario  | Plan Match    |
      | timeframe           | Active        |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available                   |
      | [0].consumerAction       | Plan Change Open Enrollment |
      | [0].planSelectionAllowed | true                        |
      | [0].changeAllowedFrom    | openEnrollmentDay           |
      | [0].changeAllowedUntil   | Dec15thCurrentYear          |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
#      | correlationId | <name>b.traceid         |
#      | correlationId | <name>a.enrollments.[0].correlationId |
      | correlationId | null                    |
      | consumerId    | <name>.consumerId       |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    Examples: HIP populations
      | name    | initialEnrollmentEndDate | initialStatus | initialBenefitStatus | enrollReconAction | action      | consumerAction              | planSelectionAllowed | changeAllowedFrom | changeAllowedUntil |
      | 31578-5 | highDate                 | ACCEPTED      | Enrolled             | A                 | Available   | Plan Change Open Enrollment | true                 | openEnrollmentDay | Dec15thCurrentYear |
     #Changed due to CP-34263
      | 31578-6 | lastDayOfThePresentYear  | DISENROLLED   | Enrolled             | C                 | Unavailable | null                        | false                | null              | null               |

 # this scenario of CP-31579 AC 3.1 is no longer valid and updated with CP-32272 AC 4.0
  @API-CP-31578 @API-CP-31579 @API-EE-IN-Removed @IN-EB-API-Regression @kursat
  Scenario Outline: CP-31578 IN-EB ENROLLMENT (A-C or C-C) Decide Scenario: MMIS Sends Reconciliation (No Plan Match Error)
  CP-31579 IN-EB ENROLLMENT (A,C or C,C) Create or Update: MMIS Sends Reconciliation
  3.1 Two Inbound Records (A-C or C-C) with Same Plan and with No Plan Match Error Records
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
      | medicalPlanBeginDate | 1stDayofPresentMonth       |
      | medicalPlanEndDate   | <initialEnrollmentEndDate> |
      | enrollmentType       | MEDICAL                    |
      | channel              | SYSTEM_INTEGRATION         |
      | anniversaryDate      | anniversaryDate            |
      | planCode             | <planCode1>                |
      | status               | <initialStatus>            |
      | selectionReason      | selectionReason1           |
      | planEndDateReason    | planEndDateReason1         |
      | rejectionReason      | rejectionReason1           |
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
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil>   |

    # Now running our scenario with A + C records with Plan Match
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
      | planCode            | <planCode2>          |
      | planId              | 104                  |
      | serviceRegionCode   | Statewide            |
      | anniversaryDate     | anniversaryDate      |
      | channel             | SYSTEM_INTEGRATION   |
      | selectionReason     | selectionReason1     |
      | planEndDateReason   | planEndDateReason1   |
      | rejectionReason     | rejectionReason1     |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | C         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>b" for create Eligibility and Enrollment
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                                             |
#      | correlationId | <name>b.traceid         |
      | correlationId | <name>b.enrollments.[0].correlationId                             |
      | consumerId    | <name>.consumerId                                                 |
      | status        | FAIL                                                              |
      | errorStack    | Multiple Records with Same Plan do not Match Enrollment in System |
    Examples: non HIP populations
      | name    | initialEnrollmentEndDate | initialStatus | initialBenefitStatus | programCode | subProgramTypeCode | population    | planCode1 | planCode2 | enrollReconAction | action      | consumerAction         | planSelectionAllowed | changeAllowedFrom    | changeAllowedUntil       |
      | 31578-7 | highDate                 | ACCEPTED      | Enrolled             | R           | HoosierHealthwise  | HHW-Mandatory | 400752220 | 399243310 | A                 | Available   | Plan Change Pre-lockin | true                 | 1stDayofPresentMonth | 90DayFromFirstDayOfMonth |
      | 31578-8 | lastDayOfThePresentYear  | DISENROLLED   | Eligible             | A           | HoosierCareConnect | HCC-Mandatory | 400752220 | 399243310 | C                 | Unavailable | null                   | false                | null                 | null                     |


  @API-CP-31578 @API-CP-31579 @API-CP-32272 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: CP-31578 IN-EB ENROLLMENT (A-C or C-C) Decide Scenario: MMIS Sends Reconciliation (Different Plan and with Plan Match non HIP) (AC 4.0 Positive)
  CP-31579 IN-EB ENROLLMENT (A,C or C,C) Create or Update: MMIS Sends Reconciliation
  CP-31579 4.0 Two Inbound Records (A-C or C-C) with Different Plan and with Plan Match to Enrollment in System, Update Enrollment Segment
  CP-32272 3.0 Inbound Records with “MMIS Requested” Plan and Plan Match to Enrollment in System ( PCP, End Reason and Start Date Change )
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
      | isEnrollmentProviderRequired | yes                        |
      | providerNpi                  | 1                          |
      | providerStartDate            | 1stDayofPresentMonth       |
      | providerEndDate              | 90DayFromFirstDayOfMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth          |
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
      | requestedBy                  | 1                          |
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
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | 1stDayofLastMonth    |
      | eligibilityEndDate    | highDate             |
      | categoryCode          | 10                   |
      | programCode           | <programCode>        |
      | subProgramTypeCode    | <subProgramTypeCode> |
      | eligibilityStatusCode | M                    |
      | genericFieldText1     | null                 |
      | genericFieldText2     | 0                    |
      | createdOn             | current              |
      | updatedOn             | current              |
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate | 1stDayofPresentMonth       |
      | medicalPlanEndDate   | <initialEnrollmentEndDate> |
      | enrollmentType       | MEDICAL                    |
      | channel              | SYSTEM_INTEGRATION         |
      | anniversaryDate      | anniversaryDate            |
      | planCode             | <planCode1>                |
      | status               | <initialStatus>            |
      | selectionReason      | selectionReason1           |
      | planEndDateReason    | planEndDateReason1         |
      | rejectionReason      | rejectionReason1           |
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
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil>   |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
#      | correlationId | <name>a.traceid       |
#      | correlationId | <name>a.enrollments.[0].correlationId |
      | correlationId | null                  |
      | consumerId    | <name>.consumerId     |
      | consumerFound | true                  |
      | DPBI          |[blank]|

    # Now running our scenario with A + C or C + C records
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId            |
      | isEligibilityRequired        | no                           |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                          |
      | recordId                     | 21                           |
      | eligibilityRecordId          | 21                           |
      | enrollmentRecordId           | 21                           |
      | isEnrollmentProviderRequired | yes                          |
      | providerNpi                  | 2                            |
      | providerStartDate            | 3rdDayofNextMonth            |
      | providerEndDate              | 14DayFromFirstDayOfNextMonth |
      | eligibilityStartDate         | 1stDayofLastMonth            |
      | eligibilityEndDate           | highDate                     |
      | enrollmentStartDate          | 1stDayofLastMonth            |
      | enrollmentEndDate            | <requestedEndDate>           |
      | txnStatus                    | Accepted                     |
      | programCode                  | <programCode>                |
      | subProgramTypeCode           | <subProgramTypeCode>         |
      | eligibilityStatusCode        | M                            |
      | categoryCode                 | 10                           |
      | genericFieldText1            | null                         |
      | coverageCode                 | cc01                         |
      | planCode                     | <planCode2>                  |
      | planId                       | 104                          |
      | serviceRegionCode            | Statewide                    |
      | anniversaryDate              | anniversaryDate              |
      | channel                      | SYSTEM_INTEGRATION           |
      | selectionReason              | selectionReason2             |
      | planEndDateReason            | planEndDateReason2           |
      | rejectionReason              | rejectionReason2             |
      | fileSource                   | RECIPIENT                    |
      | requestedBy                  | 2                            |
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
      | selectionReason     | selectionReason1      |
      | planEndDateReason   | planEndDateReason1    |
      | rejectionReason     | rejectionReason1      |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | C         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>b" for create Eligibility and Enrollment
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | 1stDayofLastMonth    |
      | eligibilityEndDate    | highDate             |
      | categoryCode          | 10                   |
      | programCode           | <programCode>        |
      | subProgramTypeCode    | <subProgramTypeCode> |
      | eligibilityStatusCode | M                    |
      | genericFieldText1     | null                 |
      | genericFieldText2     | 0                    |
      | createdOn             | current              |
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate                 | 1stDayofLastMonth            |
      | medicalPlanEndDate                   | <requestedEndDate>           |
      | enrollmentType                       | MEDICAL                      |
      | channel                              | SYSTEM_INTEGRATION           |
      | anniversaryDate                      | anniversaryDate              |
      | planCode                             | <planCode1>                  |
      | status                               | <finalStatus>                |
      | selectionReason                      | selectionReason2             |
      | planEndDateReason                    | planEndDateReason2           |
      | rejectionReason                      | rejectionReason1             |
      | enrollmentTimeframe                  | Active                       |
      | updatedOn                            | current                      |
      | updatedBy                            | 2                            |
      | enrollmentProvider.providerNpi       | 2                            |
      | enrollmentProvider.providerStartDate | 3rdDayofNextMonth            |
      | enrollmentProvider.providerEndDate   | 14DayFromFirstDayOfNextMonth |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>         |
      | population          | <population>         |
      | benefitStatus       | <finalBenefitStatus> |
      | eligibilityScenario | NO MATCH             |
      | enrollmentScenario  | Plan Match           |
      | timeframe           | Active               |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action2>               |
      | [0].consumerAction       | <consumerAction2>       |
      | [0].planSelectionAllowed | <planSelectionAllowed2> |
      | [0].changeAllowedFrom    | <changeAllowedFrom2>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil2>   |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
#      | correlationId | <name>b.traceid         |
#      | correlationId | <name>b.enrollments.[0].correlationId |
      | correlationId | null                    |
      | consumerId    | <name>.consumerId       |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    Examples: non HIP populations
      | name     | initialEnrollmentEndDate | initialStatus | initialBenefitStatus | programCode | subProgramTypeCode | population    | planCode1 | planCode2 | planCode3 | enrollReconAction | action      | consumerAction         | planSelectionAllowed | changeAllowedFrom    | changeAllowedUntil       | requestedEndDate | finalStatus | finalBenefitStatus | action2     | consumerAction2        | planSelectionAllowed2 | changeAllowedFrom2   | changeAllowedUntil2      |
    # 4.0 Two Inbound Records (A-C or C-C) with Different Plan and with Plan Match to Enrollment in System, Update Enrollment Segment
      | 31578-9  | highDate                 | ACCEPTED      | Enrolled             | R           | HoosierHealthwise  | HHW-Mandatory | 400752220 | 400752220 | 300119960 | A                 | Available   | Plan Change Pre-lockin | true                 | 1stDayofPresentMonth | 90DayFromFirstDayOfMonth | highDate         | ACCEPTED    | Enrolled           | Available   | Plan Change Pre-lockin | true                  | 1stDayofPresentMonth | 90DayFromFirstDayOfMonth |
      | 31578-10 | lastDayOfThePresentYear  | DISENROLLED   | Eligible             | R           | HoosierHealthwise  | HHW-Mandatory | 400752220 | 400752220 | 300119960 | A                 | Unavailable | null                   | false                | null                 | null                     | future           | DISENROLLED | Eligible           | Unavailable | null                   | false                 | null                 | null                     |
      | 31578-11 | highDate                 | ACCEPTED      | Enrolled             | A           | HoosierCareConnect | HCC-Mandatory | 499254630 | 499254630 | 399243310 | C                 | Available   | Plan Change Pre-lockin | true                 | 1stDayofPresentMonth | 90DayFromFirstDayOfMonth | highDate         | ACCEPTED    | Enrolled           | Available   | Plan Change Pre-lockin | true                  | 1stDayofPresentMonth | 90DayFromFirstDayOfMonth |
      | 31578-12 | lastDayOfThePresentYear  | DISENROLLED   | Eligible             | A           | HoosierCareConnect | HCC-Mandatory | 499254630 | 499254630 | 399243310 | C                 | Unavailable | null                   | false                | null                 | null                     | future           | DISENROLLED | Eligible           | Unavailable | null                   | false                 | null                 | null                     |

# this scenario of CP-31579 AC 4.1 is no longer valid and updated with CP-32272 AC 3.0
  @API-CP-31578 @API-CP-31579 @API-EE-IN-Removed @IN-EB-API-Regression @kursat
  Scenario Outline: CP-31578 IN-EB ENROLLMENT (A-C or C-C) Decide Scenario: MMIS Sends Reconciliation (Different Plan and with Plan Match non HIP) (AC 4.0 Negative)
  CP-31579 IN-EB ENROLLMENT (A,C or C,C) Create or Update: MMIS Sends Reconciliation
  CP-31579 4.0 Two Inbound Records (A-C or C-C) with Different Plan and with Plan Match to Enrollment in System, Update Enrollment Segment
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
      | isEnrollmentProviderRequired | yes                        |
      | providerNpi                  | 1                          |
      | providerStartDate            | 1stDayofPresentMonth       |
      | providerEndDate              | 90DayFromFirstDayOfMonth   |
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
      | requestedBy                  | 1                          |
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

    # Now running our scenario with A + C or C + C records
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId            |
      | isEligibilityRequired        | no                           |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                          |
      | recordId                     | 21                           |
      | eligibilityRecordId          | 21                           |
      | enrollmentRecordId           | 21                           |
      | isEnrollmentProviderRequired | yes                          |
      | providerNpi                  | 2                            |
      | providerStartDate            | 3rdDayofNextMonth            |
      | providerEndDate              | 14DayFromFirstDayOfNextMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth         |
      | eligibilityEndDate           | highDate                     |
      | enrollmentStartDate          | <enrollmentStartDate>        |
      | enrollmentEndDate            | <requestedEndDate>           |
      | txnStatus                    | Accepted                     |
      | programCode                  | <programCode>                |
      | subProgramTypeCode           | <subProgramTypeCode>         |
      | eligibilityStatusCode        | M                            |
      | categoryCode                 | 10                           |
      | genericFieldText1            | null                         |
      | coverageCode                 | cc01                         |
      | planCode                     | <planCode2>                  |
      | planId                       | 104                          |
      | serviceRegionCode            | Statewide                    |
      | anniversaryDate              | anniversaryDate              |
      | channel                      | SYSTEM_INTEGRATION           |
      | selectionReason              | selectionReason2             |
      | planEndDateReason            | planEndDateReason2           |
      | rejectionReason              | rejectionReason2             |
      | fileSource                   | RECIPIENT                    |
      | requestedBy                  | 2                            |
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
      | selectionReason     | selectionReason1      |
      | planEndDateReason   | planEndDateReason1    |
      | rejectionReason     | rejectionReason1      |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | C         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>b" for create Eligibility and Enrollment
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                                                 |
#      | correlationId | <name>a.traceid         |
#      | correlationId | <name>b.enrollments.[0].correlationId                                 |
      | correlationId | null                                                                  |
      | consumerId    | <name>.consumerId                                                     |
      | status        | FAIL                                                                  |
      | errorStack    | Multiple Records with Different Plan Overlap or Have Gap in Timeframe |
    Examples: non HIP populations
      | name     | enrollmentStartDate    | initialEnrollmentEndDate | programCode | subProgramTypeCode | planCode1 | planCode2 | planCode3 | enrollReconAction | requestedEndDate |
    # 4.0 Two Inbound Records (A-C or C-C) with Different Plan and with Plan Match to Enrollment in System, Update Enrollment Segment
      | 31578-13 | 1stDayofPresentMonth   | highDate                 | R           | HoosierHealthwise  | 400752220 | 400752220 | 300119960 | A                 | highDate         |
      | 31578-14 | 1stDayOf2MonthsFromNow | lastDayOfThePresentYear  | R           | HoosierHealthwise  | 400752220 | 400752220 | 300119960 | A                 | future           |
      | 31578-15 | 1stDayofPresentMonth   | highDate                 | A           | HoosierCareConnect | 499254630 | 499254630 | 399243310 | C                 | highDate         |
      | 31578-16 | 1stDayOf2MonthsFromNow | lastDayOfThePresentYear  | A           | HoosierCareConnect | 499254630 | 499254630 | 399243310 | C                 | future           |


  @API-CP-31578 @API-CP-31579 @API-CP-32272 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: CP-31578 IN-EB ENROLLMENT (A-C or C-C) Decide Scenario: MMIS Sends Reconciliation (Different Plan and with no Plan Match non HIP) (AC 4.1 Positive)
  CP-31579 IN-EB ENROLLMENT (A,C or C,C) Create or Update: MMIS Sends Reconciliation
  CP-31579 4.1 Two Inbound Records (A-C or C-C) with Different Plan and with No Plan Match to Enrollment in System, Capture Plan Change
  CP-32272 4.0 Inbound Records with “MMIS Requested” Plan and No Plan Match to Enrollment in System ( end reason, pcp, start date update )
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
      | isEnrollmentProviderRequired | yes                        |
      | providerNpi                  | 1                          |
      | providerStartDate            | 1stDayofPresentMonth       |
      | providerEndDate              | 90DayFromFirstDayOfMonth   |
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
      | requestedBy                  | 1                          |
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
      | updatedOn             | current              |
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate | 1stDayofPresentMonth       |
      | medicalPlanEndDate   | <initialEnrollmentEndDate> |
      | enrollmentType       | MEDICAL                    |
      | channel              | SYSTEM_INTEGRATION         |
      | anniversaryDate      | anniversaryDate            |
      | planCode             | <planCode1>                |
      | status               | <initialStatus>            |
      | selectionReason      | selectionReason1           |
      | planEndDateReason    | planEndDateReason1         |
      | rejectionReason      | rejectionReason1           |
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
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil>   |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                 |
#      | correlationId | <name>a.traceid       |
      | correlationId | <name>a.enrollments.[0].correlationId |
      | consumerId    | <name>.consumerId                     |
      | consumerFound | true                                  |
      | DPBI          |[blank]|

    # Now running our scenario with A + C or C + C records
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId            |
      | isEligibilityRequired        | no                           |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                          |
      | recordId                     | 21                           |
      | eligibilityRecordId          | 21                           |
      | enrollmentRecordId           | 21                           |
      | isEnrollmentProviderRequired | yes                          |
      | providerNpi                  | 2                            |
      | providerStartDate            | 3rdDayofNextMonth            |
      | providerEndDate              | 14DayFromFirstDayOfNextMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth         |
      | eligibilityEndDate           | highDate                     |
      | enrollmentStartDate          | 1stDayofNextMonth            |
      | enrollmentEndDate            | <requestedEndDate>           |
      | txnStatus                    | Accepted                     |
      | programCode                  | <programCode>                |
      | subProgramTypeCode           | <subProgramTypeCode>         |
      | eligibilityStatusCode        | M                            |
      | categoryCode                 | 10                           |
      | genericFieldText1            | null                         |
      | coverageCode                 | cc01                         |
      | planCode                     | <planCode2>                  |
      | planId                       | 104                          |
      | serviceRegionCode            | Statewide                    |
      | anniversaryDate              | anniversaryDate              |
      | channel                      | SYSTEM_INTEGRATION           |
      | selectionReason              | selectionReason2             |
      | planEndDateReason            | planEndDateReason2           |
      | rejectionReason              | rejectionReason2             |
      | fileSource                   | RECIPIENT                    |
      | requestedBy                  | 2                            |
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
      | selectionReason     | selectionReason1      |
      | planEndDateReason   | planEndDateReason1    |
      | rejectionReason     | rejectionReason1      |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | C         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>b" for create Eligibility and Enrollment
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
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate                 | 1stDayofPresentMonth     |
      | medicalPlanEndDate                   | lastDayofPresentMonth    |
      | enrollmentType                       | MEDICAL                  |
      | channel                              | SYSTEM_INTEGRATION       |
      | anniversaryDate                      | anniversaryDate          |
      | planCode                             | <planCode1>              |
      | status                               | DISENROLLED              |
      | selectionReason                      | selectionReason1         |
      | planEndDateReason                    | planEndDateReason2       |
      | rejectionReason                      | rejectionReason1         |
      | enrollmentTimeframe                  | Active                   |
      | updatedOn                            | current                  |
      | updatedBy                            | 2                        |
      | enrollmentProvider.providerNpi       | 1                        |
      | enrollmentProvider.providerStartDate | 1stDayofPresentMonth     |
      | enrollmentProvider.providerEndDate   | 90DayFromFirstDayOfMonth |
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate                 | 1stDayofNextMonth            |
      | medicalPlanEndDate                   | <requestedEndDate>           |
      | enrollmentType                       | MEDICAL                      |
      | channel                              | SYSTEM_INTEGRATION           |
      | anniversaryDate                      | anniversaryDate              |
      | planCode                             | <planCode2>                  |
      | status                               | <finalStatus>                |
      | selectionReason                      | selectionReason2             |
      | planEndDateReason                    | planEndDateReason2           |
      | rejectionReason                      | rejectionReason2             |
      | enrollmentTimeframe                  | FUTURE                       |
      | createdOn                            | current                      |
      | createdBy                            | 2                            |
      | enrollmentProvider.providerNpi       | 2                            |
      | enrollmentProvider.providerStartDate | 3rdDayofNextMonth            |
      | enrollmentProvider.providerEndDate   | 14DayFromFirstDayOfNextMonth |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>         |
      | population          | <population>         |
      | benefitStatus       | <finalBenefitStatus> |
      | eligibilityScenario | NO MATCH             |
      | enrollmentScenario  | No Plan Match        |
      | timeframe           | Active               |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action2>               |
      | [0].consumerAction       | <consumerAction2>       |
      | [0].planSelectionAllowed | <planSelectionAllowed2> |
      | [0].changeAllowedFrom    | <changeAllowedFrom2>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil2>   |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
#      | correlationId | <name>b.traceid         |
#      | correlationId | <name>b.enrollments.[0].correlationId |
      | correlationId | null                    |
      | consumerId    | <name>.consumerId       |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
#      | correlationId | <name>b.traceid         |
#      | correlationId | <name>b.enrollments.[0].correlationId |
      | correlationId | null                  |
      | consumerId    | <name>.consumerId     |
      | consumerFound | true                  |
      | DPBI          |[blank]|
    Examples: non HIP populations
      | name     | initialEnrollmentEndDate | initialStatus | initialBenefitStatus | programCode | subProgramTypeCode | population    | planCode1 | planCode2 | planCode3 | enrollReconAction | action      | consumerAction         | planSelectionAllowed | changeAllowedFrom    | changeAllowedUntil       | requestedEndDate | finalStatus | finalBenefitStatus | action2     | consumerAction2        | planSelectionAllowed2 | changeAllowedFrom2   | changeAllowedUntil2      |
   # 4.1 Two Inbound Records (A-C or C-C) with Different Plan and with No Plan Match to Enrollment in System, Capture Plan Change
      | 31578-17 | highDate                 | ACCEPTED      | Enrolled             | R           | HoosierHealthwise  | HHW-Mandatory | 400752220 | 700410350 | 300119960 | A                 | Available   | Plan Change Pre-lockin | true                 | 1stDayofPresentMonth | 90DayFromFirstDayOfMonth | highDate         | ACCEPTED    | Enrolled           | Available   | Plan Change Pre-lockin | true                  | 1stDayofPresentMonth | 90DayFromFirstDayOfMonth |
      | 31578-18 | highDate                 | ACCEPTED      | Enrolled             | R           | HoosierHealthwise  | HHW-Mandatory | 400752220 | 700410350 | 300119960 | A                 | Available   | Plan Change Pre-lockin | true                 | 1stDayofPresentMonth | 90DayFromFirstDayOfMonth | future           | DISENROLLED | Eligible           | Unavailable | null                   | false                 | null                 | null                     |
      | 31578-19 | lastDayOfThePresentYear  | DISENROLLED   | Eligible             | A           | HoosierCareConnect | HCC-Mandatory | 499254630 | 699842000 | 399243310 | C                 | Unavailable | null                   | false                | null                 | null                     | highDate         | ACCEPTED    | Enrolled           | Available   | Plan Change Pre-lockin | true                  | 1stDayofPresentMonth | 90DayFromFirstDayOfMonth |
      | 31578-20 | lastDayOfThePresentYear  | DISENROLLED   | Eligible             | A           | HoosierCareConnect | HCC-Mandatory | 499254630 | 699842000 | 399243310 | C                 | Unavailable | null                   | false                | null                 | null                     | future           | DISENROLLED | Eligible           | Unavailable | null                   | false                 | null                 | null                     |

# this scenario of CP-31579 AC 4.1 is no longer valid and updated with CP-32272 AC 4.0
  @API-CP-31578 @API-CP-31579 @API-EE-IN-Removed @IN-EB-API-Regression @kursat
  Scenario Outline: CP-31578 IN-EB ENROLLMENT (A-C or C-C) Decide Scenario: MMIS Sends Reconciliation (Different Plan Overlap or Have Gap non HIP error)(AC 4.1 Negative)
  CP-31579 IN-EB ENROLLMENT (A,C or C,C) Create or Update: MMIS Sends Reconciliation
  CP-31579 4.1 Two Inbound Records (A-C or C-C) with Different Plan and with No Plan Match to Enrollment in System, Capture Plan Change
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
      | isEnrollmentProviderRequired | yes                        |
      | providerNpi                  | 1                          |
      | providerStartDate            | 1stDayofPresentMonth       |
      | providerEndDate              | 90DayFromFirstDayOfMonth   |
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
      | requestedBy                  | 1                          |
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

    # Now running our scenario with A + C or C + C records
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId            |
      | isEligibilityRequired        | no                           |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                          |
      | recordId                     | 21                           |
      | eligibilityRecordId          | 21                           |
      | enrollmentRecordId           | 21                           |
      | isEnrollmentProviderRequired | yes                          |
      | providerNpi                  | 2                            |
      | providerStartDate            | 3rdDayofNextMonth            |
      | providerEndDate              | 14DayFromFirstDayOfNextMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth         |
      | eligibilityEndDate           | highDate                     |
      | enrollmentStartDate          | <enrollmentStartDate>        |
      | enrollmentEndDate            | <requestedEndDate>           |
      | txnStatus                    | Accepted                     |
      | programCode                  | <programCode>                |
      | subProgramTypeCode           | <subProgramTypeCode>         |
      | eligibilityStatusCode        | M                            |
      | categoryCode                 | 10                           |
      | genericFieldText1            | null                         |
      | coverageCode                 | cc01                         |
      | planCode                     | <planCode2>                  |
      | planId                       | 104                          |
      | serviceRegionCode            | Statewide                    |
      | anniversaryDate              | anniversaryDate              |
      | channel                      | SYSTEM_INTEGRATION           |
      | selectionReason              | selectionReason2             |
      | planEndDateReason            | planEndDateReason2           |
      | rejectionReason              | rejectionReason2             |
      | fileSource                   | RECIPIENT                    |
      | requestedBy                  | 2                            |
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
      | selectionReason     | selectionReason1      |
      | planEndDateReason   | planEndDateReason1    |
      | rejectionReason     | rejectionReason1      |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | C         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>b" for create Eligibility and Enrollment
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                                                 |
#      | correlationId | <name>a.traceid         |
      | correlationId | <name>b.enrollments.[0].correlationId                                 |
      | consumerId    | <name>.consumerId                                                     |
      | status        | FAIL                                                                  |
      | errorStack    | Multiple Records with Different Plan Overlap or Have Gap in Timeframe |
    Examples: non HIP populations
      | name     | enrollmentStartDate    | initialEnrollmentEndDate | programCode | subProgramTypeCode | planCode1 | planCode2 | planCode3 | enrollReconAction | requestedEndDate |
   # 4.1 Two Inbound Records (A-C or C-C) with Different Plan and with No Plan Match to Enrollment in System, Capture Plan Change
      | 31578-21 | 1stDayofPresentMonth   | highDate                 | R           | HoosierHealthwise  | 400752220 | 700410350 | 300119960 | A                 | highDate         |
      | 31578-22 | 1stDayOf2MonthsFromNow | highDate                 | R           | HoosierHealthwise  | 400752220 | 700410350 | 300119960 | A                 | future           |
      | 31578-23 | 1stDayofPresentMonth   | lastDayOfThePresentYear  | A           | HoosierCareConnect | 499254630 | 699842000 | 399243310 | C                 | highDate         |
      | 31578-24 | 1stDayOf2MonthsFromNow | lastDayOfThePresentYear  | A           | HoosierCareConnect | 499254630 | 699842000 | 399243310 | C                 | future           |


  @API-CP-31578 @API-CP-31579 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: CP-31578 IN-EB ENROLLMENT (A-C or C-C) Decide Scenario: MMIS Sends Reconciliation (Different Plan and with Plan Match HIP) 4.0 positive
  CP-31579 IN-EB ENROLLMENT (A,C or C,C) Create or Update: MMIS Sends Reconciliation
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
      | isEnrollmentProviderRequired | yes                        |
      | providerNpi                  | 1                          |
      | providerStartDate            | 1stDayofPresentMonth       |
      | providerEndDate              | 90DayFromFirstDayOfMonth   |
      | eligibilityStartDate         | 1stDayofPresentMonth       |
      | eligibilityEndDate           | highDate                   |
      | enrollmentStartDate          | 1stDayofPresentMonth       |
      | enrollmentEndDate            | <initialEnrollmentEndDate> |
      | txnStatus                    | Accepted                   |
      | programCode                  | H                          |
      | subProgramTypeCode           | HealthyIndianaPlan         |
      | eligibilityStatusCode        | M                          |
      | categoryCode                 | 10                         |
      | genericFieldText1            | Eligible                   |
      | coverageCode                 | cc01                       |
      | planCode                     | <planCode1>                |
      | planId                       | 104                        |
      | serviceRegionCode            | Statewide                  |
      | anniversaryDate              | anniversaryDate            |
      | channel                      | SYSTEM_INTEGRATION         |
      | selectionReason              | selectionReason1           |
      | planEndDateReason            | planEndDateReason1         |
      | rejectionReason              | rejectionReason1           |
      | fileSource                   | HRECIP                     |
      | requestedBy                  | 1                          |
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
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | 1stDayofPresentMonth |
      | eligibilityEndDate    | highDate             |
      | categoryCode          | 10                   |
      | programCode           | H                    |
      | subProgramTypeCode    | HealthyIndianaPlan   |
      | eligibilityStatusCode | M                    |
      | genericFieldText1     | Eligible             |
      | genericFieldText2     | 0                    |
      | createdOn             | current              |
      | updatedOn             | current              |
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate | 1stDayofPresentMonth       |
      | medicalPlanEndDate   | <initialEnrollmentEndDate> |
      | enrollmentType       | MEDICAL                    |
      | channel              | SYSTEM_INTEGRATION         |
      | anniversaryDate      | anniversaryDate            |
      | planCode             | <planCode1>                |
      | status               | <initialStatus>            |
      | selectionReason      | selectionReason1           |
      | planEndDateReason    | planEndDateReason1         |
      | rejectionReason      | rejectionReason1           |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | HIP-Mandatory          |
      | population          | HIP-Mandatory          |
      | benefitStatus       | <initialBenefitStatus> |
      | eligibilityScenario | NO MATCH               |
      | enrollmentScenario  | No Enrollment          |
      | timeframe           | Active                 |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil>   |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                 |
#      | correlationId | <name>a.traceid       |
      | correlationId | <name>a.enrollments.[0].correlationId |
      | consumerId    | <name>.consumerId                     |
      | consumerFound | true                                  |
      | DPBI          |[blank]|

    # Now running our scenario with A + C or C + C records
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId            |
      | isEligibilityRequired        | no                           |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                          |
      | recordId                     | 21                           |
      | eligibilityRecordId          | 21                           |
      | enrollmentRecordId           | 21                           |
      | isEnrollmentProviderRequired | yes                          |
      | providerNpi                  | 2                            |
      | providerStartDate            | 3rdDayofNextMonth            |
      | providerEndDate              | 14DayFromFirstDayOfNextMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth         |
      | eligibilityEndDate           | highDate                     |
      | enrollmentStartDate          | 1stDayofNextMonth            |
      | enrollmentEndDate            | highDate                     |
      | txnStatus                    | Accepted                     |
      | programCode                  | H                            |
      | subProgramTypeCode           | HealthyIndianaPlan           |
      | eligibilityStatusCode        | M                            |
      | categoryCode                 | 10                           |
      | genericFieldText1            | null                         |
      | coverageCode                 | cc01                         |
      | planCode                     | <planCode2>                  |
      | planId                       | 104                          |
      | serviceRegionCode            | Statewide                    |
      | anniversaryDate              | anniversaryDate              |
      | channel                      | SYSTEM_INTEGRATION           |
      | selectionReason              | selectionReason2             |
      | planEndDateReason            | planEndDateReason2           |
      | rejectionReason              | rejectionReason2             |
      | fileSource                   | HRECIP                       |
      | requestedBy                  | 2                            |
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
      | programCode         | H                     |
      | subProgramTypeCode  | HealthyIndianaPlan    |
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
    Then I send API call with name "<name>b" for create Eligibility and Enrollment
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | 1stDayofPresentMonth |
      | eligibilityEndDate    | highDate             |
      | categoryCode          | 10                   |
      | programCode           | H                    |
      | subProgramTypeCode    | HealthyIndianaPlan   |
      | eligibilityStatusCode | M                    |
      | genericFieldText1     | Eligible             |
      | genericFieldText2     | 0                    |
      | createdOn             | current              |
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate                 | 1stDayofPresentMonth         |
      | medicalPlanEndDate                   | highDate                     |
      | enrollmentType                       | MEDICAL                      |
      | channel                              | SYSTEM_INTEGRATION           |
      | anniversaryDate                      | anniversaryDate              |
      | planCode                             | <planCode2>                  |
      | status                               | ACCEPTED                     |
      | selectionReason                      | selectionReason1             |
      | planEndDateReason                    | planEndDateReason2           |
      | rejectionReason                      | rejectionReason1             |
      | enrollmentTimeframe                  | Active                       |
      | updatedOn                            | current                      |
      | updatedBy                            | 2                            |
      | enrollmentProvider.providerNpi       | 2                            |
      | enrollmentProvider.providerStartDate | 3rdDayofNextMonth            |
      | enrollmentProvider.providerEndDate   | 14DayFromFirstDayOfNextMonth |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | HIP-Mandatory         |
      | population          | HIP-Mandatory         |
      | benefitStatus       | Enrolled              |
      | eligibilityScenario | <eligibilityScenario> |
      | enrollmentScenario  | Plan Match            |
      | timeframe           | Active                |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available                   |
      | [0].consumerAction       | Plan Change Open Enrollment |
      | [0].planSelectionAllowed | true                        |
      | [0].changeAllowedFrom    | openEnrollmentDay           |
      | [0].changeAllowedUntil   | Dec15thCurrentYear          |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
#      | correlationId | <name>b.traceid         |
#      | correlationId | <name>b.enrollments.[0].correlationId |
      | correlationId | null                    |
      | consumerId    | <name>.consumerId       |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    Examples: HIP populations
      | name     | eligibilityScenario | initialEnrollmentEndDate | initialStatus | initialBenefitStatus | planCode1 | planCode2 | planCode3 | enrollReconAction | action      | consumerAction              | planSelectionAllowed | changeAllowedFrom | changeAllowedUntil |
    # 4.0 Two Inbound Records (A-C or C-C) with Different Plan and with Plan Match to Enrollment in System, Update Enrollment Segment
      | 31578-25 | NO MATCH            | highDate                 | ACCEPTED      | Enrolled             | 455701400 | 455701400 | 355787430 | A                 | Available   | Plan Change Open Enrollment | true                 | openEnrollmentDay | Dec15thCurrentYear |
      | 31578-26 | NO MATCH            | lastDayOfThePresentYear  | DISENROLLED   | Enrolled             | 455701400 | 455701400 | 355787430 | C                 | Unavailable | null                        | false                | null              | null               |

# this scenario of CP-31579 AC 4.0 is no longer valid and updated with CP-32272 AC 3.0
  @API-CP-31578 @API-CP-31579 @API-EE-IN-Removed @IN-EB-API-Regression @kursat
  Scenario Outline: CP-31578 IN-EB ENROLLMENT (A-C or C-C) Decide Scenario: MMIS Sends Reconciliation (Different Plan and with Plan Match HIP) 4.0 negative
  CP-31579 IN-EB ENROLLMENT (A,C or C,C) Create or Update: MMIS Sends Reconciliation
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
      | isEnrollmentProviderRequired | yes                        |
      | providerNpi                  | 1                          |
      | providerStartDate            | 1stDayofPresentMonth       |
      | providerEndDate              | 90DayFromFirstDayOfMonth   |
      | eligibilityStartDate         | 1stDayofPresentMonth       |
      | eligibilityEndDate           | highDate                   |
      | enrollmentStartDate          | 1stDayofPresentMonth       |
      | enrollmentEndDate            | <initialEnrollmentEndDate> |
      | txnStatus                    | Accepted                   |
      | programCode                  | H                          |
      | subProgramTypeCode           | HealthyIndianaPlan         |
      | eligibilityStatusCode        | M                          |
      | categoryCode                 | 10                         |
      | genericFieldText1            | Eligible                   |
      | coverageCode                 | cc01                       |
      | planCode                     | <planCode1>                |
      | planId                       | 104                        |
      | serviceRegionCode            | Statewide                  |
      | anniversaryDate              | anniversaryDate            |
      | channel                      | SYSTEM_INTEGRATION         |
      | selectionReason              | selectionReason1           |
      | planEndDateReason            | planEndDateReason1         |
      | rejectionReason              | rejectionReason1           |
      | fileSource                   | HRECIP                     |
      | requestedBy                  | 1                          |
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

    # Now running our scenario with A + C or C + C records
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId            |
      | isEligibilityRequired        | no                           |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                          |
      | recordId                     | 21                           |
      | eligibilityRecordId          | 21                           |
      | enrollmentRecordId           | 21                           |
      | isEnrollmentProviderRequired | yes                          |
      | providerNpi                  | 2                            |
      | providerStartDate            | 3rdDayofNextMonth            |
      | providerEndDate              | 14DayFromFirstDayOfNextMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth         |
      | eligibilityEndDate           | highDate                     |
      | enrollmentStartDate          | <enrollmentStartDate>        |
      | enrollmentEndDate            | highDate                     |
      | txnStatus                    | Accepted                     |
      | programCode                  | H                            |
      | subProgramTypeCode           | HealthyIndianaPlan           |
      | eligibilityStatusCode        | M                            |
      | categoryCode                 | 10                           |
      | genericFieldText1            | null                         |
      | coverageCode                 | cc01                         |
      | planCode                     | <planCode2>                  |
      | planId                       | 104                          |
      | serviceRegionCode            | Statewide                    |
      | anniversaryDate              | anniversaryDate              |
      | channel                      | SYSTEM_INTEGRATION           |
      | selectionReason              | selectionReason2             |
      | planEndDateReason            | planEndDateReason2           |
      | rejectionReason              | rejectionReason2             |
      | fileSource                   | HRECIP                       |
      | requestedBy                  | 2                            |
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
      | programCode         | H                     |
      | subProgramTypeCode  | HealthyIndianaPlan    |
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
    Then I send API call with name "<name>b" for create Eligibility and Enrollment
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                                                 |
#      | correlationId | <name>a.traceid         |
      | correlationId | <name>b.enrollments.[0].correlationId                                 |
      | consumerId    | <name>.consumerId                                                     |
      | status        | FAIL                                                                  |
      | errorStack    | Multiple Records with Different Plan Overlap or Have Gap in Timeframe |
    Examples: HIP populations
      | name     | enrollmentStartDate    | initialEnrollmentEndDate | planCode1 | planCode2 | planCode3 | enrollReconAction |
    # 4.0 Two Inbound Records (A-C or C-C) with Different Plan and with Plan Match to Enrollment in System, Update Enrollment Segment
      | 31578-27 | 1stDayofPresentMonth   | highDate                 | 455701400 | 455701400 | 355787430 | A                 |
      | 31578-28 | 1stDayOf2MonthsFromNow | lastDayOfThePresentYear  | 455701400 | 455701400 | 355787430 | C                 |


  @API-CP-31578 @API-CP-31579 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: CP-31578 IN-EB ENROLLMENT (A-C or C-C) Decide Scenario: MMIS Sends Reconciliation (Different Plan and with No Plan Match HIP) 4.1 positive
  CP-31579 IN-EB ENROLLMENT (A,C or C,C) Create or Update: MMIS Sends Reconciliation
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
      | isEnrollmentProviderRequired | yes                        |
      | providerNpi                  | 1                          |
      | providerStartDate            | 1stDayofPresentMonth       |
      | providerEndDate              | 90DayFromFirstDayOfMonth   |
      | eligibilityStartDate         | 1stDayofPresentMonth       |
      | eligibilityEndDate           | highDate                   |
      | enrollmentStartDate          | 1stDayofPresentMonth       |
      | enrollmentEndDate            | <initialEnrollmentEndDate> |
      | txnStatus                    | Accepted                   |
      | programCode                  | H                          |
      | subProgramTypeCode           | HealthyIndianaPlan         |
      | eligibilityStatusCode        | M                          |
      | categoryCode                 | 10                         |
      | genericFieldText1            | Eligible                   |
      | coverageCode                 | cc01                       |
      | planCode                     | <planCode1>                |
      | planId                       | 104                        |
      | serviceRegionCode            | Statewide                  |
      | anniversaryDate              | anniversaryDate            |
      | channel                      | SYSTEM_INTEGRATION         |
      | selectionReason              | selectionReason1           |
      | planEndDateReason            | planEndDateReason1         |
      | rejectionReason              | rejectionReason1           |
      | fileSource                   | HRECIP                     |
      | requestedBy                  | 1                          |
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
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | 1stDayofPresentMonth |
      | eligibilityEndDate    | highDate             |
      | categoryCode          | 10                   |
      | programCode           | H                    |
      | subProgramTypeCode    | HealthyIndianaPlan   |
      | eligibilityStatusCode | M                    |
      | genericFieldText1     | Eligible             |
      | genericFieldText2     | 0                    |
      | createdOn             | current              |
      | updatedOn             | current              |
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate | 1stDayofPresentMonth       |
      | medicalPlanEndDate   | <initialEnrollmentEndDate> |
      | enrollmentType       | MEDICAL                    |
      | channel              | SYSTEM_INTEGRATION         |
      | anniversaryDate      | anniversaryDate            |
      | planCode             | <planCode1>                |
      | status               | <initialStatus>            |
      | selectionReason      | selectionReason1           |
      | planEndDateReason    | planEndDateReason1         |
      | rejectionReason      | rejectionReason1           |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | HIP-Mandatory          |
      | population          | HIP-Mandatory          |
      | benefitStatus       | <initialBenefitStatus> |
      | eligibilityScenario | NO MATCH               |
      | enrollmentScenario  | No Enrollment          |
      | timeframe           | Active                 |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil>   |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                 |
#      | correlationId | <name>a.traceid       |
      | correlationId | <name>a.enrollments.[0].correlationId |
      | consumerId    | <name>.consumerId                     |
      | consumerFound | true                                  |
      | DPBI          |[blank]|

    # Now running our scenario with A + C or C + C records
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId            |
      | isEligibilityRequired        | no                           |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                          |
      | recordId                     | 21                           |
      | eligibilityRecordId          | 21                           |
      | enrollmentRecordId           | 21                           |
      | isEnrollmentProviderRequired | yes                          |
      | providerNpi                  | 2                            |
      | providerStartDate            | 3rdDayofNextMonth            |
      | providerEndDate              | 14DayFromFirstDayOfNextMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth         |
      | eligibilityEndDate           | highDate                     |
      | enrollmentStartDate          | 1stDayofNextMonth            |
      | enrollmentEndDate            | <requestedEndDate>           |
      | txnStatus                    | Accepted                     |
      | programCode                  | H                            |
      | subProgramTypeCode           | HealthyIndianaPlan           |
      | eligibilityStatusCode        | M                            |
      | categoryCode                 | 10                           |
      | genericFieldText1            | null                         |
      | coverageCode                 | cc01                         |
      | planCode                     | <planCode2>                  |
      | planId                       | 104                          |
      | serviceRegionCode            | Statewide                    |
      | anniversaryDate              | anniversaryDate              |
      | channel                      | SYSTEM_INTEGRATION           |
      | selectionReason              | selectionReason2             |
      | planEndDateReason            | planEndDateReason2           |
      | rejectionReason              | rejectionReason2             |
      | fileSource                   | HRECIP                       |
      | requestedBy                  | 2                            |
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
      | programCode         | H                     |
      | subProgramTypeCode  | HealthyIndianaPlan    |
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
    Then I send API call with name "<name>b" for create Eligibility and Enrollment
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | 1stDayofPresentMonth |
      | eligibilityEndDate    | highDate             |
      | categoryCode          | 10                   |
      | programCode           | H                    |
      | subProgramTypeCode    | HealthyIndianaPlan   |
      | eligibilityStatusCode | M                    |
      | genericFieldText1     | Eligible             |
      | genericFieldText2     | 0                    |
      | createdOn             | current              |
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate                 | 1stDayofPresentMonth     |
      | medicalPlanEndDate                   | lastDayofPresentMonth    |
      | enrollmentType                       | MEDICAL                  |
      | channel                              | SYSTEM_INTEGRATION       |
      | anniversaryDate                      | anniversaryDate          |
      | planCode                             | <planCode1>              |
      | status                               | DISENROLLED              |
      | selectionReason                      | selectionReason1         |
      | planEndDateReason                    | planEndDateReason2       |
      | rejectionReason                      | rejectionReason1         |
      | enrollmentTimeframe                  | Active                   |
      | updatedOn                            | current                  |
      | updatedBy                            | 2                        |
      | enrollmentProvider.providerNpi       | 1                        |
      | enrollmentProvider.providerStartDate | 1stDayofPresentMonth     |
      | enrollmentProvider.providerEndDate   | 90DayFromFirstDayOfMonth |
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate                 | 1stDayofNextMonth            |
      | medicalPlanEndDate                   | <requestedEndDate>           |
      | enrollmentType                       | MEDICAL                      |
      | channel                              | SYSTEM_INTEGRATION           |
      | anniversaryDate                      | anniversaryDate              |
      | planCode                             | <planCode2>                  |
      | status                               | <finalStatus>                |
      | selectionReason                      | selectionReason2             |
      | planEndDateReason                    | planEndDateReason2           |
#      | rejectionReason      | rejectionReason1             |
      | enrollmentTimeframe                  | FUTURE                       |
      | createdOn                            | current                      |
      | createdBy                            | 2                            |
      | enrollmentProvider.providerNpi       | 2                            |
      | enrollmentProvider.providerStartDate | 3rdDayofNextMonth            |
      | enrollmentProvider.providerEndDate   | 14DayFromFirstDayOfNextMonth |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | HIP-Mandatory        |
      | population          | HIP-Mandatory        |
      | benefitStatus       | <finalBenefitStatus> |
      | eligibilityScenario | NO MATCH             |
      | enrollmentScenario  | No Plan Match        |
      | timeframe           | Active               |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action2>               |
      | [0].consumerAction       | <consumerAction2>       |
      | [0].planSelectionAllowed | <planSelectionAllowed2> |
      | [0].changeAllowedFrom    | <changeAllowedFrom2>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil2>   |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
#      | correlationId | <name>b.traceid         |
#      | correlationId | <name>b.enrollments.[0].correlationId |
      | correlationId | null                    |
      | consumerId    | <name>.consumerId       |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
#      | correlationId | <name>b.traceid         |
#      | correlationId | <name>b.enrollments.[0].correlationId |
      | correlationId | null                  |
      | consumerId    | <name>.consumerId     |
      | consumerFound | true                  |
      | DPBI          |[blank]|
    Examples: HIP populations
      | name     | initialEnrollmentEndDate | initialStatus | initialBenefitStatus | planCode1 | planCode2 | planCode3 | enrollReconAction | action      | consumerAction              | planSelectionAllowed | changeAllowedFrom | changeAllowedUntil | requestedEndDate | finalStatus | finalBenefitStatus | action2     | consumerAction2             | planSelectionAllowed2 | changeAllowedFrom2 | changeAllowedUntil2 |
   # 4.1 Two Inbound Records (A-C or C-C) with Different Plan and with No Plan Match to Enrollment in System, Capture Plan Change
      | 31578-29 | highDate                 | ACCEPTED      | Enrolled             | 455701400 | 755726440 | 355787430 | A                 | Available   | Plan Change Open Enrollment | true                 | openEnrollmentDay | Dec15thCurrentYear | highDate         | ACCEPTED    | Enrolled           | Available   | Plan Change Open Enrollment | true                  | openEnrollmentDay  | Dec15thCurrentYear  |
      | 31578-30 | lastDayOfThePresentYear  | DISENROLLED   | Enrolled             | 455701400 | 755726440 | 355787430 | A                 | Unavailable | null                        | false                | null              | null               | future           | DISENROLLED | Enrolled           | Unavailable | null                        | false                 | null               | null                |
      | 31578-31 | highDate                 | ACCEPTED      | Enrolled             | 455701400 | 755726440 | 355787430 | C                 | Available   | Plan Change Open Enrollment | true                 | openEnrollmentDay | Dec15thCurrentYear | highDate         | ACCEPTED    | Enrolled           | Available   | Plan Change Open Enrollment | true                  | openEnrollmentDay  | Dec15thCurrentYear  |
      | 31578-32 | lastDayOfThePresentYear  | DISENROLLED   | Enrolled             | 455701400 | 755726440 | 355787430 | C                 | Unavailable | null                        | false                | null              | null               | future           | DISENROLLED | Enrolled           | Unavailable | null                        | false                 | null               | null                |

# this scenario of CP-31579 AC 4.1 is no longer valid and updated with CP-32272 AC 4.0
  @API-CP-31578 @API-CP-31579 @API-EE-IN-Removed @IN-EB-API-Regression @kursat
  Scenario Outline: CP-31578 IN-EB ENROLLMENT (A-C or C-C) Decide Scenario: MMIS Sends Reconciliation (Different Plan and with No Plan Match HIP) 4.1 negative
  CP-31579 IN-EB ENROLLMENT (A,C or C,C) Create or Update: MMIS Sends Reconciliation
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
      | isEnrollmentProviderRequired | yes                        |
      | providerNpi                  | 1                          |
      | providerStartDate            | 1stDayofPresentMonth       |
      | providerEndDate              | 90DayFromFirstDayOfMonth   |
      | eligibilityStartDate         | 1stDayofPresentMonth       |
      | eligibilityEndDate           | highDate                   |
      | enrollmentStartDate          | 1stDayofPresentMonth       |
      | enrollmentEndDate            | <initialEnrollmentEndDate> |
      | txnStatus                    | Accepted                   |
      | programCode                  | H                          |
      | subProgramTypeCode           | HealthyIndianaPlan         |
      | eligibilityStatusCode        | M                          |
      | categoryCode                 | 10                         |
      | genericFieldText1            | Eligible                   |
      | coverageCode                 | cc01                       |
      | planCode                     | <planCode1>                |
      | planId                       | 104                        |
      | serviceRegionCode            | Statewide                  |
      | anniversaryDate              | anniversaryDate            |
      | channel                      | SYSTEM_INTEGRATION         |
      | selectionReason              | selectionReason1           |
      | planEndDateReason            | planEndDateReason1         |
      | rejectionReason              | rejectionReason1           |
      | fileSource                   | HRECIP                     |
      | requestedBy                  | 1                          |
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

    # Now running our scenario with A + C or C + C records
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId            |
      | isEligibilityRequired        | no                           |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                          |
      | recordId                     | 21                           |
      | eligibilityRecordId          | 21                           |
      | enrollmentRecordId           | 21                           |
      | isEnrollmentProviderRequired | yes                          |
      | providerNpi                  | 2                            |
      | providerStartDate            | 3rdDayofNextMonth            |
      | providerEndDate              | 14DayFromFirstDayOfNextMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth         |
      | eligibilityEndDate           | highDate                     |
      | enrollmentStartDate          | <enrollmentStartDate>        |
      | enrollmentEndDate            | <requestedEndDate>           |
      | txnStatus                    | Accepted                     |
      | programCode                  | H                            |
      | subProgramTypeCode           | HealthyIndianaPlan           |
      | eligibilityStatusCode        | M                            |
      | categoryCode                 | 10                           |
      | genericFieldText1            | null                         |
      | coverageCode                 | cc01                         |
      | planCode                     | <planCode2>                  |
      | planId                       | 104                          |
      | serviceRegionCode            | Statewide                    |
      | anniversaryDate              | anniversaryDate              |
      | channel                      | SYSTEM_INTEGRATION           |
      | selectionReason              | selectionReason2             |
      | planEndDateReason            | planEndDateReason2           |
      | rejectionReason              | rejectionReason2             |
      | fileSource                   | HRECIP                       |
      | requestedBy                  | 2                            |
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
      | programCode         | H                     |
      | subProgramTypeCode  | HealthyIndianaPlan    |
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
    Then I send API call with name "<name>b" for create Eligibility and Enrollment
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                                                 |
#      | correlationId | <name>a.traceid         |
#      | correlationId | <name>b.enrollments.[0].correlationId                                 |
      | correlationId | null                                                                  |
      | consumerId    | <name>.consumerId                                                     |
      | status        | FAIL                                                                  |
      | errorStack    | Multiple Records with Different Plan Overlap or Have Gap in Timeframe |
    Examples: HIP populations
      | name     | enrollmentStartDate    | initialEnrollmentEndDate | planCode1 | planCode2 | planCode3 | enrollReconAction | requestedEndDate |
   # 4.1 Two Inbound Records (A-C or C-C) with Different Plan and with No Plan Match to Enrollment in System, Capture Plan Change
      | 31578-33 | 1stDayofPresentMonth   | highDate                 | 455701400 | 755726440 | 355787430 | A                 | highDate         |
      | 31578-34 | 1stDayofPresentMonth   | lastDayOfThePresentYear  | 455701400 | 755726440 | 355787430 | A                 | future           |
      | 31578-35 | 1stDayOf2MonthsFromNow | highDate                 | 455701400 | 755726440 | 355787430 | C                 | highDate         |
      | 31578-36 | 1stDayOf2MonthsFromNow | lastDayOfThePresentYear  | 455701400 | 755726440 | 355787430 | C                 | future           |


  @API-CP-31578 @API-CP-31579 @API-CP-32231 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: CP-31578 IN-EB ENROLLMENT (A-C or C-C) Decide Scenario: MMIS Sends Reconciliation (In-Flight Enrollment Selection non HIP)
  CP-31579 IN-EB ENROLLMENT (A,C or C,C) Create or Update: MMIS Sends Reconciliation
  2.0 Identify In-Flight Enrollment Selections for the Consumer
  CP-32231 AC 2.0 Identify In-Flight Enrollment Selections for the Consumer
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
      | [0].action               | Unavailable              |
      | [0].consumerAction       | Plan Change Pre-lockin   |
      | [0].planSelectionAllowed | true                     |
      | [0].changeAllowedFrom    | 1stDayofPresentMonth     |
      | [0].changeAllowedUntil   | 90DayFromFirstDayOfMonth |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                 |
#      | correlationId | <name>a.traceid       |
      | correlationId | <name>a.enrollments.[0].correlationId |
      | consumerId    | <name>.consumerId                     |
      | consumerFound | true                                  |
      | DPBI          |[blank]|

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
      | 31578-37 | highDate                 | Enrolled             | R           | HoosierHealthwise  | HHW-Mandatory | 400752220 | 300119960 | 300119960 | A                 |


  @API-CP-31578 @API-CP-31579 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: CP-31578 IN-EB ENROLLMENT (A-C or C-C) Decide Scenario: MMIS Sends Reconciliation (No Valid Eligibility Error)
  CP-31579 IN-EB ENROLLMENT (A,C or C,C) Create or Update: MMIS Sends Reconciliation
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
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
      | planCode                     | <planCode>           |
      | planId                       | 104                  |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | selectionReason              | selectionReason1     |
      | planEndDateReason            | planEndDateReason1   |
      | rejectionReason              | rejectionReason1     |
      | fileSource                   | <fileSource>         |
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
      | planCode            | <planCode>           |
      | planId              | 104                  |
      | serviceRegionCode   | Statewide            |
      | anniversaryDate     | anniversaryDate      |
      | channel             | SYSTEM_INTEGRATION   |
      | selectionReason     | selectionReason1     |
      | planEndDateReason   | planEndDateReason1   |
      | rejectionReason     | rejectionReason1     |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | C         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                 |
#      | correlationId | <name>a.traceid         |
      | correlationId | <name>a.enrollments.[0].correlationId |
      | consumerId    | <name>.consumerId                     |
      | status        | FAIL                                  |
      | errorStack    | No Valid Eligibility                  |
    Examples: non HIP populations
      | name     | programCode | subProgramTypeCode | planCode  | enrollReconAction | fileSource |
      | 31578-38 | R           | HoosierHealthwise  | 400752220 | A                 | RECIPIENT  |
      | 31578-39 | A           | HoosierCareConnect | 499254630 | C                 | RECIPIENT  |
      | 31578-40 | H           | HealthyIndianaPlan | 455701400 | A                 | HRECIP     |


  @API-CP-31580 @API-CP-32280 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: CP-31580 IN-EB ENROLLMENT (A,C or C,C) Create Business Event: Reconciliation (No Update)
  CP-31580 7.0 Business Event Optional Enrollment Fields: No Update
  CP-32280 7.0 Business Event Optional Enrollment Fields: No Update
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    # Create pre-existing eligibility and enrollment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | enrollmentRecordId           | 21                       |
      | isEnrollmentProviderRequired | yes                      |
      | providerNpi                  | 1                        |
      | providerStartDate            | 1stDayofPresentMonth     |
      | providerEndDate              | 90DayFromFirstDayOfMonth |
      | providerFirstName            | James                    |
      | providerLastName             | Smith                    |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | highDate                 |
      | enrollmentStartDate          | 1stDayofPresentMonth     |
      | enrollmentEndDate            | <enrollmentEndDate>      |
      | txnStatus                    | Accepted                 |
      | programCode                  | <programCode>            |
      | subProgramTypeCode           | <subProgramTypeCode>     |
      | eligibilityStatusCode        | M                        |
      | categoryCode                 | 10                       |
      | genericFieldText1            | null                     |
      | coverageCode                 | cc01                     |
      | planCode                     | <planCode>               |
      | planId                       | 104                      |
      | serviceRegionCode            | Statewide                |
      | anniversaryDate              | anniversaryDate          |
      | channel                      | SYSTEM_INTEGRATION       |
      | selectionReason              | selectionReason1         |
      | planEndDateReason            | planEndDateReason1       |
      | rejectionReason              | rejectionReason1         |
      | fileSource                   | RECIPIENT                |
      | requestedBy                  | 1                        |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | A         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment

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
      | isEnrollmentProviderRequired | yes                      |
      | providerNpi                  | 1                        |
      | providerStartDate            | 1stDayofPresentMonth     |
      | providerEndDate              | 90DayFromFirstDayOfMonth |
      | providerFirstName            | James                    |
      | providerLastName             | Smith                    |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | highDate                 |
      | enrollmentStartDate          | 1stDayofPresentMonth     |
      | enrollmentEndDate            | <enrollmentEndDate>      |
      | txnStatus                    | Accepted                 |
      | programCode                  | <programCode>            |
      | subProgramTypeCode           | <subProgramTypeCode>     |
      | eligibilityStatusCode        | M                        |
      | categoryCode                 | 10                       |
      | genericFieldText1            | null                     |
      | coverageCode                 | cc01                     |
      | planCode                     | <planCode>               |
      | planId                       | 104                      |
      | serviceRegionCode            | Statewide                |
      | anniversaryDate              | anniversaryDate          |
      | channel                      | SYSTEM_INTEGRATION       |
      | selectionReason              | selectionReason1         |
      | planEndDateReason            | planEndDateReason1       |
      | rejectionReason              | rejectionReason1         |
      | fileSource                   | RECIPIENT                |
      | requestedBy                  | 1                        |
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
      | planCode            | <planCode>           |
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
    Then I send API call with name "<name>b" for create Eligibility and Enrollment
    Then I will verify business events are generated with data
      | eventName            | RECONCILIATION       |
      | reconciliationAction | No Enrollment Update |
      | enrollmentStartDate  | 1stDayofPresentMonth |
      | enrollmentEndDate    | <enrollmentEndDate>  |
      | selectionReason      | selectionReason1     |
      | planChangeReason     | planEndDateReason1   |
      | planCode             | <planCode>           |
      | enrollmentType       | MEDICAL              |
      | selectionStatus      | <selectionStatus>    |
      | pcpNpi               | 1                    |
      | pcpName              | James Smith          |

    Examples: non HIP populations
      | name    | enrollmentEndDate       | programCode | subProgramTypeCode | planCode  | enrollReconAction | selectionStatus |
      | 31580-1 | highDate                | R           | HoosierHealthwise  | 400752220 | A                 | ACCEPTED        |
      | 31580-2 | lastDayOfThePresentYear | R           | HoosierHealthwise  | 400752220 | A                 | DISENROLLED     |
      | 31580-3 | highDate                | A           | HoosierCareConnect | 499254630 | C                 | ACCEPTED        |
      | 31580-4 | lastDayOfThePresentYear | A           | HoosierCareConnect | 499254630 | C                 | DISENROLLED     |
      | 31580-5 | highDate                | H           | HealthyIndianaPlan | 499254630 | C                 | ACCEPTED        |
      | 31580-6 | lastDayOfThePresentYear | H           | HealthyIndianaPlan | 499254630 | A                 | DISENROLLED     |

  @API-CP-31580 @API-CP-32280 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: CP-31580 IN-EB ENROLLMENT (A,C or C,C) Create Business Event: Reconciliation (Create)
  CP-31580 8.0 Business Event Optional Enrollment Fields: Create Enrollment Segment
  CP-32280 8.0 Business Event Optional Enrollment Fields: Create Enrollment Segment
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    # Create pre-existing eligibility and enrollment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | enrollmentRecordId           | 21                       |
      | isEnrollmentProviderRequired | yes                      |
      | providerNpi                  | 1                        |
      | providerStartDate            | 1stDayofPresentMonth     |
      | providerEndDate              | 90DayFromFirstDayOfMonth |
      | providerFirstName            | James                    |
      | providerLastName             | Smith                    |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | highDate                 |
      | enrollmentStartDate          | 1stDayofPresentMonth     |
      | enrollmentEndDate            | <enrollmentEndDate>      |
      | txnStatus                    | Accepted                 |
      | programCode                  | <programCode>            |
      | subProgramTypeCode           | <subProgramTypeCode>     |
      | eligibilityStatusCode        | M                        |
      | categoryCode                 | 10                       |
      | genericFieldText1            | null                     |
      | coverageCode                 | cc01                     |
      | planCode                     | <planCode>               |
      | planId                       | 104                      |
      | serviceRegionCode            | Statewide                |
      | anniversaryDate              | anniversaryDate          |
      | channel                      | SYSTEM_INTEGRATION       |
      | selectionReason              | selectionReason1         |
      | planEndDateReason            | planEndDateReason1       |
      | rejectionReason              | rejectionReason1         |
      | fileSource                   | RECIPIENT                |
      | requestedBy                  | 1                        |
    And User provide other enrollment segments details
      | uiid              | uiid::              |
      | segmentTypeCode   | OTHER_TXN           |
      | genericFieldText1 | null                |
      | genericFieldText2 | <enrollReconAction> |
      | genericFieldText3 | MEDICAL             |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    Then I will verify business events are generated with data
      | eventName            | RECONCILIATION                                          |
      | correlationId        | <name>a.eligibilities.[0].coreEligibility.correlationId |
      | reconciliationAction | Enrollment Created                                      |
      | enrollmentStartDate  | 1stDayofPresentMonth                                    |
      | enrollmentEndDate    | <enrollmentEndDate>                                     |
      | selectionReason      | selectionReason1                                        |
      | planChangeReason     | planEndDateReason1                                      |
      | rejectionReason      | rejectionReason1                                        |
      | planCode             | <planCode>                                              |
      | enrollmentType       | MEDICAL                                                 |
      | selectionStatus      | <selectionStatus>                                       |
      | pcpNpi               | 1                                                       |
      | pcpName              | James Smith                                             |

    Examples: non HIP populations
      | name     | enrollmentEndDate       | programCode | subProgramTypeCode | planCode  | enrollReconAction | selectionStatus |
      | 31580-7  | highDate                | R           | HoosierHealthwise  | 400752220 | A                 | ACCEPTED        |
      | 31580-8  | lastDayOfThePresentYear | R           | HoosierHealthwise  | 400752220 | A                 | DISENROLLED     |
      | 31580-9  | highDate                | A           | HoosierCareConnect | 499254630 | C                 | ACCEPTED        |
      | 31580-10 | lastDayOfThePresentYear | A           | HoosierCareConnect | 499254630 | C                 | DISENROLLED     |
      | 31580-11 | highDate                | H           | HealthyIndianaPlan | 755726440 | A                 | ACCEPTED        |
      | 31580-12 | lastDayOfThePresentYear | H           | HealthyIndianaPlan | 755726440 | A                 | DISENROLLED     |

  @API-CP-31580 @API-CP-32280 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: CP-31580 IN-EB ENROLLMENT (A,C or C,C) Create Business Event: Reconciliation (Update)
  CP-31580 9.0 Business Event Optional Enrollment Fields: Update Enrollment Segment
  CP-32280 9.0 Business Event Optional Enrollment Fields: Update Enrollment Segment
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    # Create pre-existing eligibility and enrollment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | enrollmentRecordId           | 21                       |
      | isEnrollmentProviderRequired | yes                      |
      | providerNpi                  | 1                        |
      | providerStartDate            | 1stDayofPresentMonth     |
      | providerEndDate              | 90DayFromFirstDayOfMonth |
      | providerFirstName            | James                    |
      | providerLastName             | Smith                    |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | highDate                 |
      | enrollmentStartDate          | 1stDayofPresentMonth     |
      | enrollmentEndDate            | <enrollmentEndDate1>     |
      | txnStatus                    | Accepted                 |
      | programCode                  | <programCode>            |
      | subProgramTypeCode           | <subProgramTypeCode>     |
      | eligibilityStatusCode        | M                        |
      | categoryCode                 | 10                       |
      | genericFieldText1            | null                     |
      | coverageCode                 | cc01                     |
      | planCode                     | <planCode>               |
      | planId                       | 104                      |
      | serviceRegionCode            | Statewide                |
      | anniversaryDate              | anniversaryDate          |
      | channel                      | SYSTEM_INTEGRATION       |
      | selectionReason              | selectionReason1         |
      | planEndDateReason            | planEndDateReason1       |
      | rejectionReason              | rejectionReason1         |
      | fileSource                   | RECIPIENT                |
      | requestedBy                  | 1                        |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | A         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment

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
      | isEnrollmentProviderRequired | yes                      |
      | providerNpi                  | 2                        |
      | providerStartDate            | 1stDayofPresentMonth     |
      | providerEndDate              | 90DayFromFirstDayOfMonth |
      | providerFirstName            | Brian                    |
      | providerLastName             | Adams                    |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | highDate                 |
      | enrollmentStartDate          | 1stDayofNextMonth        |
      | enrollmentEndDate            | <enrollmentEndDate2>     |
      | txnStatus                    | Accepted                 |
      | programCode                  | <programCode>            |
      | subProgramTypeCode           | <subProgramTypeCode>     |
      | eligibilityStatusCode        | M                        |
      | categoryCode                 | 10                       |
      | genericFieldText1            | null                     |
      | coverageCode                 | cc01                     |
      | planCode                     | <planCode>               |
      | planId                       | 104                      |
      | serviceRegionCode            | Statewide                |
      | anniversaryDate              | anniversaryDate          |
      | channel                      | SYSTEM_INTEGRATION       |
      | selectionReason              | selectionReason2         |
      | planEndDateReason            | planEndDateReason2       |
      | rejectionReason              | rejectionReason2         |
      | fileSource                   | RECIPIENT                |
      | requestedBy                  | 1                        |
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
      | planCode            | <planCode>            |
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
    Then I send API call with name "<name>b" for create Eligibility and Enrollment
    And Wait for 3 seconds
    Then I will verify business events are generated with data
      | eventName                    | RECONCILIATION                        |
      | correlationId                | <name>a.enrollments.[0].correlationId |
      | reconciliationAction         | Enrollment Updated                    |
      | oldValue.enrollmentStartDate | 1stDayofPresentMonth                  |
      | oldValue.enrollmentEndDate   | <enrollmentEndDate1>                  |
      | oldValue.selectionReason     | selectionReason1                      |
      | oldValue.planChangeReason    | planEndDateReason1                    |
      | oldValue.rejectionReason     | rejectionReason1                      |
      | oldValue.planCode            | <planCode>                            |
      | oldValue.enrollmentType      | MEDICAL                               |
      | oldValue.selectionStatus     | <selectionStatus1>                    |
      | oldValue.pcpNpi              | 1                                     |
      | oldValue.pcpName             | James Smith                           |
      | newValue.enrollmentStartDate | 1stDayofPresentMonth                  |
      | newValue.enrollmentEndDate   | <enrollmentEndDate2>                  |
      | newValue.selectionReason     | selectionReason1                      |
      | newValue.planChangeReason    | planEndDateReason2                    |
      | newValue.rejectionReason     | rejectionReason1                      |
      | newValue.planCode            | <planCode>                            |
      | newValue.enrollmentType      | MEDICAL                               |
      | newValue.selectionStatus     | <selectionStatus2>                    |
      | newValue.pcpNpi              | 2                                     |
      | newValue.pcpName             | Brian Adams                           |

    Examples: non HIP populations
      | name     | enrollmentEndDate1      | programCode | subProgramTypeCode | planCode  | enrollReconAction | selectionStatus1 | enrollmentEndDate2      | selectionStatus2 |
      | 31580-13 | highDate                | R           | HoosierHealthwise  | 400752220 | A                 | ACCEPTED         | lastDayOfThePresentYear | DISENROLLED      |
      | 31580-14 | lastDayOfThePresentYear | R           | HoosierHealthwise  | 400752220 | A                 | DISENROLLED      | highDate                | ACCEPTED         |
      | 31580-15 | highDate                | A           | HoosierCareConnect | 499254630 | C                 | ACCEPTED         | lastDayOfThePresentYear | DISENROLLED      |
      | 31580-16 | lastDayOfThePresentYear | A           | HoosierCareConnect | 499254630 | C                 | DISENROLLED      | highDate                | ACCEPTED         |
      | 31580-17 | highDate                | H           | HealthyIndianaPlan | 755726440 | A                 | ACCEPTED         | lastDayOfThePresentYear | DISENROLLED      |
      | 31580-18 | lastDayOfThePresentYear | H           | HealthyIndianaPlan | 755726440 | C                 | DISENROLLED      | highDate                | ACCEPTED         |

  @API-CP-31580 @API-CP-32280 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: CP-31580 IN-EB ENROLLMENT (A,C or C,C) Create Business Event: Reconciliation (Plan Change)
  CP-31580 10.0 Business Event Optional Enrollment Fields: Plan Change
  CP-32280 10.0 Business Event Optional Enrollment Fields: Plan Change
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    # Create pre-existing eligibility and enrollment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | enrollmentRecordId           | 21                       |
      | isEnrollmentProviderRequired | yes                      |
      | providerNpi                  | 1                        |
      | providerStartDate            | 1stDayofPresentMonth     |
      | providerEndDate              | 90DayFromFirstDayOfMonth |
      | providerFirstName            | James                    |
      | providerLastName             | Smith                    |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | highDate                 |
      | enrollmentStartDate          | 1stDayofPresentMonth     |
      | enrollmentEndDate            | <enrollmentEndDate>      |
      | txnStatus                    | Accepted                 |
      | programCode                  | <programCode>            |
      | subProgramTypeCode           | <subProgramTypeCode>     |
      | eligibilityStatusCode        | M                        |
      | categoryCode                 | 10                       |
      | genericFieldText1            | null                     |
      | coverageCode                 | cc01                     |
      | planCode                     | <planCode1>              |
      | planId                       | 104                      |
      | serviceRegionCode            | Statewide                |
      | anniversaryDate              | anniversaryDate          |
      | channel                      | SYSTEM_INTEGRATION       |
      | selectionReason              | selectionReason1         |
      | planEndDateReason            | planEndDateReason1       |
      | rejectionReason              | rejectionReason1         |
      | fileSource                   | RECIPIENT                |
      | requestedBy                  | 1                        |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | A         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment

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
      | isEnrollmentProviderRequired | yes                      |
      | providerNpi                  | 2                        |
      | providerStartDate            | 1stDayofPresentMonth     |
      | providerEndDate              | 90DayFromFirstDayOfMonth |
      | providerFirstName            | Brian                    |
      | providerLastName             | Adams                    |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | highDate                 |
      | enrollmentStartDate          | 1stDayofNextMonth        |
      | enrollmentEndDate            | <enrollmentEndDate>      |
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
      | requestedBy                  | 2                        |
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
      | planCode            | <planCode1>           |
      | planId              | 104                   |
      | serviceRegionCode   | Statewide             |
      | anniversaryDate     | anniversaryDate       |
      | channel             | SYSTEM_INTEGRATION    |
      | selectionReason     | selectionReason1      |
      | planEndDateReason   | planEndDateReason1    |
      | rejectionReason     | rejectionReason1      |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | C         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>b" for create Eligibility and Enrollment
    Then I will verify business events are generated with data
      | eventName                              | RECONCILIATION        |
      | reconciliationAction                   | Plan Change           |
      | previousEnrollment.enrollmentStartDate | 1stDayofPresentMonth  |
      | previousEnrollment.enrollmentEndDate   | lastDayofPresentMonth |
      | previousEnrollment.selectionReason     | selectionReason1      |
      | previousEnrollment.planChangeReason    | planEndDateReason2    |
      | previousEnrollment.rejectionReason     | rejectionReason1      |
      | previousEnrollment.planCode            | <planCode1>           |
      | previousEnrollment.enrollmentType      | MEDICAL               |
      | previousEnrollment.selectionStatus     | DISENROLLED           |
      | previousEnrollment.pcpNpi              | 1                     |
      | previousEnrollment.pcpName             | James Smith           |
      | requestedSelection.enrollmentStartDate | 1stDayofNextMonth     |
      | requestedSelection.enrollmentEndDate   | <enrollmentEndDate>   |
      | requestedSelection.selectionReason     | selectionReason2      |
      | requestedSelection.planChangeReason    | planEndDateReason2    |
      | requestedSelection.rejectionReason     | rejectionReason2      |
      | requestedSelection.planCode            | <planCode2>           |
      | requestedSelection.enrollmentType      | MEDICAL               |
      | requestedSelection.selectionStatus     | <selectionStatus>     |
      | requestedSelection.pcpNpi              | 2                     |
      | requestedSelection.pcpName             | Brian Adams           |

    Examples: non HIP populations
      | name     | enrollmentEndDate       | programCode | subProgramTypeCode | planCode1 | planCode2 | enrollReconAction | selectionStatus |
      | 31580-19 | highDate                | R           | HoosierHealthwise  | 400752220 | 700410350 | A                 | ACCEPTED        |
      | 31580-20 | lastDayOfThePresentYear | R           | HoosierHealthwise  | 400752220 | 700410350 | A                 | DISENROLLED     |
      | 31580-21 | highDate                | A           | HoosierCareConnect | 499254630 | 399243310 | C                 | ACCEPTED        |
      | 31580-22 | lastDayOfThePresentYear | A           | HoosierCareConnect | 499254630 | 399243310 | C                 | DISENROLLED     |
      | 31580-23 | highDate                | A           | HoosierCareConnect | 499254630 | 399243310 | C                 | ACCEPTED        |
      | 31580-24 | lastDayOfThePresentYear | A           | HoosierCareConnect | 499254630 | 399243310 | C                 | DISENROLLED     |
