
Feature: MMIS Sends Default Enrollment (25) for system processing for all DCEB programs and plans.

  @API-CP-43393 @API-CP-43393-01 @shruti @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: DC EB: 1.0 Decide Scenario is MMIS Sends Default Enrollment-positive
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event1>         |
      | coverageCode                 | <coverageCode>         |
      | consumerId                   | <name>.consumerId      |
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                     |
      | isEnrollmentProviderRequired | no                     |
      | recordId                     | 25                     |
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | enrollmentStartDate          | <enrollmentStartDate>  |
      | enrollmentEndDate            | <enrollmentEndDate>    |
      | txnStatus                    | Accepted               |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | categoryCode                 | 10                     |
      | planCode                     | <planCode>             |
      | planId                       | 104                    |
      | serviceRegionCode            | Northeast              |
      | anniversaryDate              | anniversaryDate        |
      | channel                      | SYSTEM_INTEGRATION     |
      | selectionReason              | selectionReason1       |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event2>        |
      | coverageCode                 | <coverageCode>        |
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | isEnrollmentProviderRequired | no                    |
      | recordId                     | 25                    |
      | eligibilityStartDate         | 1stDayofLastMonth     |
      | eligibilityEndDate           | highDate-DC           |
      | enrollmentStartDate          | <enrollmentStartDate> |
      | enrollmentEndDate            | <enrollmentEndDate>   |
      | txnStatus                    | Accepted              |
      | programCode                  | MEDICAID              |
      | subProgramTypeCode           | <subProgramTypeCode>  |
      | categoryCode                 | 10                    |
      | genericFieldText1            | null                  |
      | planCode                     | <planCode>            |
      | planId                       | 104                   |
      | serviceRegionCode            | Northeast             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | selectionReason              | selectionReason1      |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | population          | <population>        |
      | benefitStatus       | <benefitStatus>     |
      | eligibilityScenario | Default Eligibility |
      | enrollmentScenario  | Default Enrollment  |
      | timeframe           | Active              |
#    Then I Verify Consumer Actions as following data
#      | [0].action               | Available               |
#      | [0].consumerAction       | Plan Change Anniversary |
#      | [0].planSelectionAllowed | true                    |
#      | [1].action               | Available               |
#      | [1].consumerAction       | Plan Change Pre-lockin  |
#      | [1].planSelectionAllowed | true                    |
#    And Wait for 3 seconds
#    And I verify enrollment by consumer id "<name>.consumerId" with data
#      | txnStatus            | ACCEPTED              |
#      | updatedOn            | current               |
#      # updatedBy for now displays createdBy in the enrollment data. It will be updated to display requestedBy (2023-03-08)
#      | updatedBy            | null                  |
#      | planCode             | <planCode>            |
#      | medicalPlanBeginDate | <enrollmentStartDate> |
#      | medicalPlanEndDate   | <enrollmentEndDate>   |
#      | selectionReason      | selectionReason1      |
#      | enrollmentType       | MEDICAL               |
    Examples:
      | name     | coverageCode | population         | subProgramTypeCode | planCode  | enrollmentStartDate  | enrollmentEndDate       | eligibilityEndDate      | benefitStatus | eligibilityStartDate |
      | 42989-01 | 460          | Alliance-Child     | Alliance           | 087358900 | 1stDayofPresentMonth | lastDayOfThePresentYear | highDate-DC             | Enrolled      | 1stDayofPresentMonth |
      | 42989-02 | 420          | Immigrant Children | ImmigrantChildren  | 081080400 | 1stDayofPresentMonth | lastDayOfThePresentYear | lastDayOfThePresentYear | Enrolled      | 1stDayofPresentMonth |
      | 42989-03 | 130          | DCHF-Adult         | DCHF               | 081080400 | current              | highDate-DC             | highDate-DC             | Enrolled      | 1stDayofLastMonth    |

  @API-CP-43393 @API-CP-43393-02 @shruti @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: DC EB: 1.1 Check Enrollment Data for Validity Against Eligibility Data in System
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event1>         |
      | coverageCode                 | <coverageCode>         |
      | consumerId                   | <name>.consumerId      |
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                     |
      | isEnrollmentProviderRequired | no                     |
      | recordId                     | 25                     |
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | enrollmentStartDate          | <enrollmentStartDate>  |
      | enrollmentEndDate            | <enrollmentEndDate>    |
      | txnStatus                    | Accepted               |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | categoryCode                 | 10                     |
      | planCode                     | <planCode>             |
      | planId                       | 104                    |
      | serviceRegionCode            | Northeast              |
      | anniversaryDate              | anniversaryDate        |
      | channel                      | SYSTEM_INTEGRATION     |
      | selectionReason              | selectionReason1       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event2>        |
      | coverageCode                 | <coverageCode>        |
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | isEnrollmentProviderRequired | no                    |
      | recordId                     | 25                    |
      | eligibilityStartDate         | 1stDayofLastMonth     |
      | eligibilityEndDate           | highDate-DC           |
      | enrollmentStartDate          | <enrollmentStartDate> |
      | enrollmentEndDate            | <enrollmentEndDate>   |
      | txnStatus                    | Accepted              |
      | programCode                  | MEDICAID              |
      | subProgramTypeCode           | <subProgramTypeCode>  |
      | categoryCode                 | 10                    |
      | genericFieldText1            | null                  |
      | planCode                     | <planCode>            |
      | planId                       | 104                   |
      | serviceRegionCode            | Northeast             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | selectionReason              | selectionReason1      |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | population          | <population>        |
      | benefitStatus       | Eligible            |
      | eligibilityScenario | Default Eligibility |
      | enrollmentScenario  | null                |
      | timeframe           | Active              |
#    Then I Verify Consumer Actions as following data
#      | [0].action               | Available               |
#      | [0].consumerAction       | Plan Change Anniversary |
#      | [0].planSelectionAllowed | true                    |
#      | [1].action               | Available               |
#      | [1].consumerAction       | Plan Change Pre-lockin  |
#      | [1].planSelectionAllowed | true                    |
#    And Wait for 3 seconds
#    And I verify enrollment by consumer id "<name>.consumerId" with data
#      | txnStatus            | ACCEPTED              |
#      | updatedOn            | current               |
#      # updatedBy for now displays createdBy in the enrollment data. It will be updated to display requestedBy (2023-03-08)
#      | updatedBy            | null                  |
#      | planCode             | <planCode>            |
#      | medicalPlanBeginDate | <enrollmentStartDate> |
#      | medicalPlanEndDate   | <enrollmentEndDate>   |
#      | selectionReason      | selectionReason1      |
#      | enrollmentType       | MEDICAL               |
    Examples:
      | name     | coverageCode | population     | subProgramTypeCode | planCode  | enrollmentStartDate  | enrollmentEndDate | eligibilityEndDate      | eligibilityStartDate |
   #Invalid Enrollment Start Date
      | 42989-05 | 130          | DCHF-Adult     | DCHF               | 081080400 | 1stDayofLastMonth    | highDate-DC       | highDate-DC             | 1stDayofPresentMonth |
#Invalid Enrollment End  Date
      | 42989-06 | 460          | Alliance-Child | Alliance           | 087358900 | 1stDayofPresentMonth | highDate-DC       | lastDayOfThePresentYear | 1stDayofPresentMonth |
#Invalid Enrollment Plan Code
      | 42989-07 | 460          | Alliance-Child | Alliance           | 081080400 | 1stDayofLastMonth    | highDate-DC       | highDate-DC             | 1stDayofLastMonth    |

  @API-CP-43393 @API-CP-43393-03 @API-CP-43209 @shruti @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: DC EB: 2.0 Compare & Match Inbound Enrollment Segment to Consumer Enrollment in ConnectionPoint-POSITIVE
  CP-43209 -5.0 Plan Change Anniversary Action, 4.0 Plan Change Pre-Lockin Action
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event1>        |
      | coverageCode                 | <coverageCode>        |
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | isEnrollmentProviderRequired | no                    |
      | recordId                     | 25                    |
      | eligibilityStartDate         | 1stDayofPresentMonth  |
      | eligibilityEndDate           | highDate-DC           |
      | enrollmentStartDate          | <enrollmentStartDate> |
      | enrollmentEndDate            | <enrollmentEndDate>   |
      | txnStatus                    | Accepted              |
      | programCode                  | MEDICAID              |
      | subProgramTypeCode           | <subProgramTypeCode>  |
      | categoryCode                 | 10                    |
      | planCode                     | <planCode>            |
      | planId                       | 104                   |
      | serviceRegionCode            | Northeast             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | selectionReason              | selectionReason1      |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event2>         |
      | coverageCode                 | <coverageCode>         |
      | consumerId                   | <name>.consumerId      |
      | isEligibilityRequired        | no                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                    |
      | isEnrollmentProviderRequired | no                     |
      | recordId                     | 25                     |
      | eligibilityStartDate         | 1stDayofLastMonth      |
      | eligibilityEndDate           | highDate-DC            |
      | enrollmentStartDate          | <enrollmentStartDate2> |
      | enrollmentEndDate            | <enrollmentEndDate2>   |
      | txnStatus                    | Accepted               |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | categoryCode                 | 10                     |
      | genericFieldText1            | null                   |
      | planCode                     | <planCode2>            |
      | planId                       | 104                    |
      | serviceRegionCode            | Northeast              |
      | anniversaryDate              | anniversaryDate        |
      | channel                      | SYSTEM_INTEGRATION     |
      | selectionReason              | selectionReason1       |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | population          | <population>         |
      | benefitStatus       | Enrolled             |
      | eligibilityScenario | Default Eligibility  |
      | enrollmentScenario  | <enrollmentScenario> |
      | timeframe           | Active               |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available                    |
      | [0].consumerAction       | Plan Change Anniversary      |
      | [0].planSelectionAllowed | true                         |
      | [0].changeAllowedFrom    | <changeAllowedFrom>          |
      | [1].action               | Available                    |
      | [1].consumerAction       | Plan Change Pre-lockin       |
      | [1].planSelectionAllowed | true                         |
      | [1].changeAllowedFrom    | <changeAllowedFromPrelockin> |

    Examples:
      | name     | coverageCode | population         | subProgramTypeCode | planCode  | enrollmentStartDate  | enrollmentEndDate | enrollmentEndDate2      | enrollmentStartDate2 | planCode2 | enrollmentScenario | changeAllowedFrom             | changeAllowedFromPrelockin |
   #Exatch Match
      | 42989-08 | 130          | DCHF-Adult         | DCHF               | 081080400 | 1stDayofPresentMonth | highDate-DC       | highDate-DC             | 1stDayofPresentMonth | 081080400 | Default Enrollment | 1YearFrom1stDayofPresentMonth | 1stDayofPresentMonth       |
   #Enrollment Update
      | 42989-09 | 460          | Alliance-Child     | Alliance           | 087358900 | 1stDayofPresentMonth | highDate-DC       | lastDayOfThePresentYear | 1stDayofPresentMonth | 087358900 | Default Enrollment | 1YearFrom1stDayofPresentMonth | 1stDayofPresentMonth       |
     #Plan Change with null plan code in the inbound enrollment
      | 42989-13 | 420          | Immigrant Children | ImmigrantChildren  | 044733300 | 1stDayofPresentMonth | highDate-DC       | highDate-DC             | 1stDayofNextMonth    |[blank]| Default Enrollment | 1YearFrom1stDayofPresentMonth | 1stDayofPresentMonth       |

  @API-CP-43393 @API-CP-43393-03 @API-CP-43209 @API-CP-43599 @shruti @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: DC EB: 2.0 Compare & Match Inbound Enrollment Segment to Consumer Enrollment in ConnectionPoint-POSITIVE
  CP-43209 -5.0 Plan Change Anniversary Action, 4.0 Plan Change Pre-Lockin Action
  CP-43599 - 4.0 Plan Change Pre-Lockin Action
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event1>        |
      | coverageCode                 | <coverageCode>        |
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | isEnrollmentProviderRequired | no                    |
      | recordId                     | 25                    |
      | eligibilityStartDate         | 1stDayofPresentMonth  |
      | eligibilityEndDate           | highDate-DC           |
      | enrollmentStartDate          | <enrollmentStartDate> |
      | enrollmentEndDate            | <enrollmentEndDate>   |
      | txnStatus                    | Accepted              |
      | programCode                  | MEDICAID              |
      | subProgramTypeCode           | <subProgramTypeCode>  |
      | categoryCode                 | 10                    |
      | planCode                     | <planCode>            |
      | planId                       | 104                   |
      | serviceRegionCode            | Northeast             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | selectionReason              | selectionReason1      |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event2>         |
      | coverageCode                 | <coverageCode>         |
      | consumerId                   | <name>.consumerId      |
      | isEligibilityRequired        | no                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                    |
      | isEnrollmentProviderRequired | no                     |
      | recordId                     | 25                     |
      | eligibilityStartDate         | 1stDayofLastMonth      |
      | eligibilityEndDate           | highDate-DC            |
      | enrollmentStartDate          | <enrollmentStartDate2> |
      | enrollmentEndDate            | <enrollmentEndDate2>   |
      | txnStatus                    | Accepted               |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | categoryCode                 | 10                     |
      | genericFieldText1            | null                   |
      | planCode                     | <planCode2>            |
      | planId                       | 104                    |
      | serviceRegionCode            | Northeast              |
      | anniversaryDate              | anniversaryDate        |
      | channel                      | SYSTEM_INTEGRATION     |
      | selectionReason              | selectionReason1       |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | population          | <population>         |
      | benefitStatus       | Enrolled             |
      | eligibilityScenario | Default Eligibility  |
      | enrollmentScenario  | <enrollmentScenario> |
      | timeframe           | Active               |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available               |
      | [0].consumerAction       | Plan Change Anniversary |
      | [0].planSelectionAllowed | true                    |
      | [0].changeAllowedFrom    | <changeAllowedFrom>     |

    Examples:
      | name     | coverageCode | population         | subProgramTypeCode | planCode  | enrollmentStartDate  | enrollmentEndDate | enrollmentEndDate2      | enrollmentStartDate2 | planCode2 | enrollmentScenario | changeAllowedFrom             |
     #Plan Change
      | 42989-10 | 460          | Alliance-Child     | Alliance           | 087358900 | 1stDayofPresentMonth | highDate-DC       | lastDayOfThePresentYear | 1stDayofPresentMonth | 077573200 | Plan Change        | 1YearFrom1stDayofPresentMonth |
      | 42989-12 | 420          | Immigrant Children | ImmigrantChildren  | 044733300 | 1stDayofPresentMonth | highDate-DC       | highDate-DC             | 1stDayofPresentMonth | 055558200 | Plan Change        | 1YearFrom1stDayofPresentMonth |
       #Plan Change with enddate < startdate in the inbound enrollment
      | 42989-11 | 460          | Alliance-Child     | Alliance           | 087358900 | 1stDayofPresentMonth | highDate-DC       | nextDay                 | nextDayPlusOne       | 077573200 | Plan Change        | 1YearFromNextDayPlusOne       |


  @API-CP-42989 @API-CP-42989-01 @API-CP-42989-02 @API-CP-42990 @API-CP-42990-03 @API-CP-43394 @API-CP-43394-03 @API-CP-43394-04 @kursat @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: DC EB: Enrollment Create or Update and Disenroll - No Match and Exact Match
  CP-42989 DC EB: Decide Scenario is Capture Disenroll (Process H and I Transactions)
  CP-42990 DC EB: Create or Update Data Disenroll (Process H and I Transactions)
  CP-43394 DC EB: 834 Default Enrollment Create or Update (AB+HI)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    # CP-42989 2.0 Compare & Match Inbound Enrollment Segment to Consumer Enrollment in ConnectionPoint
    # CP-42990 3.0 Create the consumer’s Enrollment segment - No Match
    # CP-43394 3.0 Create the consumer’s Enrollment segment - No Match
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event1>        |
      | coverageCode                 | <coverageCode>        |
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | isEnrollmentProviderRequired | no                    |
      | recordId                     | 25                    |
      | eligibilityStartDate         | 1stDayofLastMonth     |
      | eligibilityEndDate           | highDate-DC           |
      | enrollmentStartDate          | <enrollmentStartDate> |
      | enrollmentEndDate            | <enrollmentEndDate>   |
      | txnStatus                    | Accepted              |
      | programCode                  | MEDICAID              |
      | subProgramTypeCode           | <subProgramTypeCode>  |
      | categoryCode                 | 10                    |
      | planCode                     | <planCode>            |
      | planId                       | 104                   |
      | serviceRegionCode            | Northeast             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | selectionReason              | selectionReason1      |
      | createdBy                    | 1                     |
      | requestedBy                  | 2                     |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    # CP-42989 1.0 Decide Scenario is MMIS Sends Default Enrollment
    Then I verify latest benefit status information with data
      | population          | <population>        |
      | benefitStatus       | Enrolled            |
      | eligibilityScenario | Default Eligibility |
      | enrollmentScenario  | Default Enrollment  |
      | timeframe           | Active              |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available               |
      | [0].consumerAction       | Plan Change Anniversary |
      | [0].planSelectionAllowed | true                    |
      | [1].action               | Available               |
      | [1].consumerAction       | Plan Change Pre-lockin  |
      | [1].planSelectionAllowed | true                    |
    And Wait for 3 seconds
    And I verify enrollment by consumer id "<name>.consumerId" with data
    # CP-42990 3.0 Create the consumer’s Enrollment segment - No Match
      | medicalPlanBeginDate | <enrollmentStartDate> |
      | medicalPlanEndDate   | <enrollmentEndDate>   |
      | planCode             | <planCode>            |
      | updatedOn            | current               |
      | updatedBy            | 2                     |
      # Below added with CP-43394 3.0 Create the consumer’s Enrollment segment - No Match
      | txnStatus            | ACCEPTED              |
      | selectionReason      | selectionReason1      |
      | enrollmentType       | MEDICAL               |
      | createdOn            | current               |
      | createdBy            | 2                     |
    And Wait for 3 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                        |
      | correlationId | <name><event1>.enrollments.[0].correlationId |
      | consumerId    | <name>.consumerId                            |
      | consumerFound | true                                         |
      | DPBI          |[blank]|
    And I initiated Eligibility and Enrollment Create API
    # CP-43394 4.0 Do Not Create or Update Enrollment Data - Exact Match
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event2>        |
      | coverageCode                 | <coverageCode>        |
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | isEnrollmentProviderRequired | no                    |
      | recordId                     | 25                    |
      | eligibilityStartDate         | 1stDayofLastMonth     |
      | eligibilityEndDate           | highDate-DC           |
      | enrollmentStartDate          | <enrollmentStartDate> |
      | enrollmentEndDate            | <enrollmentEndDate>   |
      | txnStatus                    | Accepted              |
      | programCode                  | MEDICAID              |
      | subProgramTypeCode           | <subProgramTypeCode>  |
      | categoryCode                 | 10                    |
      | genericFieldText1            | null                  |
      | planCode                     | <planCode>            |
      | planId                       | 104                   |
      | serviceRegionCode            | Northeast             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | selectionReason              | selectionReason1      |
      | createdBy                    | 1                     |
      | requestedBy                  | 2                     |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    # CP-42989 1.0 Decide Scenario is MMIS Sends Default Enrollment
    Then I verify latest benefit status information with data
      | population          | <population>        |
      | benefitStatus       | Enrolled            |
      | eligibilityScenario | Default Eligibility |
      | enrollmentScenario  | Default Enrollment  |
      | timeframe           | Active              |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available               |
      | [0].consumerAction       | Plan Change Anniversary |
      | [0].planSelectionAllowed | true                    |
      | [1].action               | Available               |
      | [1].consumerAction       | Plan Change Pre-lockin  |
      | [1].planSelectionAllowed | true                    |
    And Wait for 3 seconds
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate | <enrollmentStartDate> |
      | medicalPlanEndDate   | <enrollmentEndDate>   |
      | planCode             | <planCode>            |
      | updatedOn            | current               |
      | updatedBy            | 597                     |
      | txnStatus            | ACCEPTED              |
      | selectionReason      | selectionReason1      |
      | enrollmentType       | MEDICAL               |
      | createdOn            | current               |
      | createdBy            | 597                     |
    Examples:
      | name     | coverageCode | population         | subProgramTypeCode | planCode  | enrollmentStartDate | enrollmentEndDate | event1      | event2      |
      | 42989-01 | 460          | Alliance-Child     | Alliance           | 087358900 | 1stDayofLastMonth   | highDate-DC       | Enrollment1 | Enrollment2 |
      | 42989-02 | 420          | Immigrant Children | ImmigrantChildren  | 081080400 | 1stDayofLastMonth   | highDate-DC       | Enrollment1 | Enrollment2 |
      | 42989-03 | 130          | DCHF-Adult         | DCHF               | 081080400 | 1stDayofLastMonth   | highDate-DC       | Enrollment1 | Enrollment2 |


  @API-CP-42989 @API-CP-42989-01 @API-CP-42989-02 @API-CP-42990 @API-CP-42990-01 @API-CP-43394 @API-CP-43394-01 @kursat @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: DC EB: Enrollment Create or Update and Disenroll - Changed Enrollment Start and End Date
  CP-42989 DC EB: Decide Scenario is Capture Disenroll (Process H and I Transactions)
  CP-42990 DC EB: Create or Update Data Disenroll (Process H and I Transactions)
  CP-43394 DC EB: 834 Default Enrollment Create or Update (AB+HI)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event1>        |
      | coverageCode                 | <coverageCode>        |
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | isEnrollmentProviderRequired | no                    |
      | recordId                     | 25                    |
      | eligibilityStartDate         | 1stDayofLastMonth     |
      | eligibilityEndDate           | highDate-DC           |
      | enrollmentStartDate          | <enrollmentStartDate> |
      | enrollmentEndDate            | <enrollmentEndDate>   |
      | txnStatus                    | Accepted              |
      | programCode                  | MEDICAID              |
      | subProgramTypeCode           | <subProgramTypeCode>  |
      | categoryCode                 | 10                    |
      | planCode                     | <planCode>            |
      | planId                       | 104                   |
      | serviceRegionCode            | Northeast             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | selectionReason              | selectionReason1      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated Eligibility and Enrollment Create API
    # CP-42990 1.0 Update the consumer’s Enrollment segment - Change of Enrollment Start/End Date
    # CP-43394 1.0 Update the Consumer’s Enrollment Segment - Enrollment Update
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event2>         |
      | coverageCode                 | <coverageCode>         |
      | consumerId                   | <name>.consumerId      |
      | isEligibilityRequired        | no                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                    |
      | isEnrollmentProviderRequired | no                     |
      | recordId                     | 25                     |
      | eligibilityStartDate         | 1stDayofLastMonth      |
      | eligibilityEndDate           | highDate-DC            |
      | enrollmentStartDate          | <enrollmentStartDate2> |
      | enrollmentEndDate            | <enrollmentEndDate2>   |
      | txnStatus                    | Accepted               |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | categoryCode                 | 10                     |
      | genericFieldText1            | null                   |
      | planCode                     | <planCode2>            |
      | planId                       | 104                    |
      | serviceRegionCode            | Northeast              |
      | anniversaryDate              | anniversaryDate        |
      | channel                      | SYSTEM_INTEGRATION     |
      | selectionReason              | selectionReason1       |
      | planEndDateReason            | planEndDateReason1     |
      | requestedBy                  | 1                      |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    # CP-42989 1.0 Decide Scenario is MMIS Sends Default Enrollment
    Then I verify latest benefit status information with data
      | population          | <population>        |
      | benefitStatus       | Enrolled            |
      | eligibilityScenario | Default Eligibility |
      | enrollmentScenario  | Default Enrollment  |
      | timeframe           | Active              |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available               |
      | [0].consumerAction       | Plan Change Anniversary |
      | [0].planSelectionAllowed | true                    |
      | [1].action               | Available               |
      | [1].consumerAction       | Plan Change Pre-lockin  |
      | [1].planSelectionAllowed | true                    |
    # CP-42990 1.0 Update the consumer’s Enrollment segment - Change of Enrollment Start/End Date
    # CP-43394 1.0 Update the Consumer’s Enrollment Segment - Enrollment Update
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate | <enrollmentStartDate2> |
      | medicalPlanEndDate   | <enrollmentEndDate2>   |
      | planEndDateReason    | planEndDateReason1     |
      | planCode             | <planCode>             |
      | updatedOn            | current                |
      | updatedBy            | 1                      |
      | txnStatus            | ACCEPTED               |
      | selectionReason      | selectionReason1       |
      | enrollmentType       | MEDICAL                |
    And Wait for 3 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT                      |
      | correlationId | <name><event1>.enrollments.[0].correlationId |
      | consumerId    | <name>.consumerId                            |
      | consumerFound | true                                         |
      | DPBI          |[blank]|
    Examples:
      | name     | coverageCode | population         | subProgramTypeCode | planCode  | planCode2 | enrollmentStartDate | enrollmentStartDate2 | enrollmentEndDate | enrollmentEndDate2 | event1      | event2      |
      | 42989-04 | 460          | Alliance-Child     | Alliance           | 087358900 | 087358900 | 1stDayofLastMonth   | 1stDayofNextMonth    | highDate-DC       | highDate-DC        | Enrollment1 | Enrollment2 |
      | 42989-05 | 420          | Immigrant Children | ImmigrantChildren  | 081080400 | 081080400 | 1stDayofLastMonth   | 1stDayofNextMonth    | highDate-DC       | highDate-DC        | Enrollment1 | Enrollment2 |
      | 42989-06 | 130          | DCHF-Adult         | DCHF               | 081080400 | 081080400 | 1stDayofLastMonth   | 1stDayofNextMonth    | highDate-DC       | highDate-DC        | Enrollment1 | Enrollment2 |
      | 42989-07 | 460          | Alliance-Child     | Alliance           | 087358900 | 087358900 | 1stDayofLastMonth   | 1stDayofLastMonth    | highDate-DC       | highDate           | Enrollment1 | Enrollment2 |
      | 42989-08 | 420          | Immigrant Children | ImmigrantChildren  | 081080400 | 081080400 | 1stDayofLastMonth   | 1stDayofLastMonth    | highDate-DC       | highDate           | Enrollment1 | Enrollment2 |
      | 42989-09 | 130          | DCHF-Adult         | DCHF               | 081080400 | 081080400 | 1stDayofLastMonth   | 1stDayofLastMonth    | highDate-DC       | highDate           | Enrollment1 | Enrollment2 |

  @API-CP-42989 @API-CP-42989-01 @API-CP-42989-02 @API-CP-42990 @API-CP-42990-02 @API-CP-43394 @API-CP-43394-02 @API-CP-39403 @kursat @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: DC EB: Enrollment Create or Update and Disenroll - Plan Change
  CP-42989 DC EB: Decide Scenario is Capture Disenroll (Process H and I Transactions)
  CP-42990 DC EB: Create or Update Data Disenroll (Process H and I Transactions)
  CP-43394 DC EB: 834 Default Enrollment Create or Update (AB+HI)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event1>        |
      | coverageCode                 | <coverageCode>        |
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | isEnrollmentProviderRequired | no                    |
      | recordId                     | 25                    |
      | eligibilityStartDate         | 1stDayofLastMonth     |
      | eligibilityEndDate           | highDate-DC           |
      | enrollmentStartDate          | <enrollmentStartDate> |
      | enrollmentEndDate            | <enrollmentEndDate>   |
      | txnStatus                    | Accepted              |
      | programCode                  | MEDICAID              |
      | subProgramTypeCode           | <subProgramTypeCode>  |
      | categoryCode                 | 10                    |
      | planCode                     | <planCode>            |
      | planId                       | 104                   |
      | serviceRegionCode            | Northeast             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | selectionReason              | selectionReason1      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                        |
      | correlationId | <name><event1>.enrollments.[0].correlationId |
      | consumerId    | <name>.consumerId                            |
      | consumerFound | true                                         |
      | DPBI          |[blank]|
    And Wait for 3 seconds
    # CP-39403 1.0 Business Event Required Fields
    Then I will verify business events are generated with data
      | eventName           | DEFAULT_ENROLLMENT                                             |
      | channel             | SYSTEM_INTEGRATION                                             |
      | createdBy           | 597                                                            |
      | processDate         | current                                                        |
      | correlationId       | <name><event1>.eligibilities.[0].coreEligibility.correlationId |
      | consumerId          | <name>.consumerId                                              |
      | consumerName        | <name>                                                         |
    # CP-39403 2.0 Business Event Optional Fields
      | enrollmentStartDate | <enrollmentStartDate>                                          |
      | enrollmentEndDate   | <enrollmentEndDate>                                            |
      | planSelectionReason | selectionReason1                                               |
      | planChangeReason    | null                                                           |
      | planCode            | <planCode>                                                     |
      | enrollmentType      | MEDICAL                                                        |
      | selectionStatus     | ACCEPTED                                                       |
      | anniversaryDate     | 1stDayof11MonthsFromNow                                        |
      | pcpNpi              | null                                                           |
      | pcpName             | null                                                           |
      | pdpNpi              | null                                                           |
      | pdpName             | null                                                           |
      | outCome             | Default Enrollment                                             |
    And Wait for 3 seconds
    And I initiated Eligibility and Enrollment Create API
    # CP-42990 2.0 Update the consumer’s Enrollment segment - Plan Change
    # CP-43394 2.0 Update the consumer’s Enrollment segment - Plan Change
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event2>         |
      | coverageCode                 | <coverageCode>         |
      | consumerId                   | <name>.consumerId      |
      | isEligibilityRequired        | no                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                    |
      | isEnrollmentProviderRequired | no                     |
      | recordId                     | 25                     |
      | eligibilityStartDate         | 1stDayofLastMonth      |
      | eligibilityEndDate           | highDate-DC            |
      | enrollmentStartDate          | <enrollmentStartDate2> |
      | enrollmentEndDate            | <enrollmentEndDate2>   |
      | txnStatus                    | Accepted               |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | categoryCode                 | 10                     |
      | genericFieldText1            | null                   |
      | planCode                     | <planCode2>            |
      | planId                       | 104                    |
      | serviceRegionCode            | Northeast              |
      | anniversaryDate              | anniversaryDate        |
      | channel                      | SYSTEM_INTEGRATION     |
      | selectionReason              | selectionReason1       |
      | planEndDateReason            | planEndDateReason1     |
      | requestedBy                  | 55                     |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | population          | <population>        |
      | benefitStatus       | Enrolled            |
      | eligibilityScenario | Default Eligibility |
      # currently enrollmentScenario is expecting Plan Change. Once Fixed comment out for below enrollmentSCenario validation will be removed
      | enrollmentScenario  | Plan Change         |
      | timeframe           | Active              |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available               |
      | [0].consumerAction       | Plan Change Anniversary |
      | [0].planSelectionAllowed | true                    |
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
    # CP-42990 2.0 Update the consumer’s Enrollment segment - Plan Change
      | medicalPlanBeginDate | <enrollmentStartDate2> |
      | medicalPlanEndDate   | <enrollmentEndDate2>   |
      | planCode             | <planCode2>            |
      | updatedOn            | current                |
      | updatedBy            | 55                     |
     # Additional fields added to be checked by CP-43394 2.0 Update the consumer’s Enrollment segment - Plan Change
      | enrollmentType       | MEDICAL                |
      | channel              | SYSTEM_INTEGRATION     |
      | txnStatus            | ACCEPTED               |
      | createdOn            | current                |
      | createdBy            | 55                     |
      | planEndDateReason    | planEndDateReason1     |
    And Wait for 3 seconds
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | txnStatus            | DISENROLLED            |
      | updatedOn            | current                |
      | updatedBy            | 55                     |
      | planCode             | <planCode>             |
      | medicalPlanBeginDate | <enrollmentStartDate2> |
      | medicalPlanEndDate   | lastDayOf2MonthsBefore |
      | selectionReason      | selectionReason1       |
      | planEndDateReason    | planEndDateReason1     |
      | enrollmentType       | MEDICAL                |
    And Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT                      |
      | correlationId | <name><event1>.enrollments.[0].correlationId |
      | consumerId    | <name>.consumerId                            |
      | consumerFound | true                                         |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                        |
      | correlationId | <name><event2>.enrollments.[0].correlationId |
      | consumerId    | <name>.consumerId                            |
      | consumerFound | true                                         |
      | DPBI          |[blank]                                       |
    And Wait for 5 seconds
    # CP-39403 3.0 Business Event Optional Fields - Plan Change
    Then I will verify business events are generated with data
      | eventName                     | DEFAULT_ENROLLMENT      |
      | consumerId                    | <name>.consumerId       |
      | priorPlan.enrollmentEndDate   | lastDayOf2MonthsBefore  |
      | priorPlan.planSelectionReason | selectionReason1        |
      | priorPlan.planChangeReason    | planEndDateReason1      |
      | priorPlan.planCode            | <planCode>              |
      | priorPlan.enrollmentType      | MEDICAL                 |
      | priorPlan.selectionStatus     | DISENROLLED             |
      | priorPlan.anniversaryDate     | 1stDayof11MonthsFromNow |
      | priorPlan.pcpNpi              | null                    |
      | priorPlan.pcpName             | null                    |
      | priorPlan.pdpNpi              | null                    |
      | priorPlan.pdpName             | null                    |
      | priorPlan.outcome             | Plan Change             |
      | newPlan.enrollmentStartDate   | <enrollmentStartDate2>  |
      | newPlan.enrollmentEndDate     | <enrollmentEndDate2>    |
      | newPlan.planSelectionReason   | selectionReason1        |
      | newPlan.planChangeReason      | planEndDateReason1      |
      | newPlan.rejectionReason       | null                    |
      | newPlan.planCode              | <planCode2>             |
      | newPlan.enrollmentType        | MEDICAL                 |
      | newPlan.selectionStatus       | ACCEPTED                |
      | newPlan.anniversaryDate       | 1stDayof11MonthsFromNow |
      | newPlan.pcpNpi                | null                    |
      | newPlan.pcpName               | null                    |
      | newPlan.pdpNpi                | null                    |
      | newPlan.pdpName               | null                    |
      | newPlan.outCome               | null                    |
      | DPBI          |[blank]|
    Examples:
      | name     | coverageCode | population         | subProgramTypeCode | planCode  | planCode2 | enrollmentStartDate | enrollmentStartDate2 | enrollmentEndDate | enrollmentEndDate2 | event1      | event2      |
      | 42989-10 | 460          | Alliance-Child     | Alliance           | 087358900 | 077573200 | 1stDayofLastMonth   | 1stDayofLastMonth    | highDate-DC       | highDate-DC        | Enrollment1 | Enrollment2 |
      | 42989-11 | 420          | Immigrant Children | ImmigrantChildren  | 081080400 | 044733300 | 1stDayofLastMonth   | 1stDayofLastMonth    | highDate-DC       | highDate-DC        | Enrollment1 | Enrollment2 |
      | 42989-12 | 130          | DCHF-Adult         | DCHF               | 081080400 | 055558200 | 1stDayofLastMonth   | 1stDayofLastMonth    | highDate-DC       | highDate-DC        | Enrollment1 | Enrollment2 |


  @API-CP-43394 @API-CP-43394-03 @kursat @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: DC EB: Enrollment Create or Update and Disenroll - Past Enrollment
  CP-43394 DC EB: 834 Default Enrollment Create or Update (AB+HI)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
      | newCaseCreation  | yes    |
    Given I initiated Eligibility and Enrollment Create API
    # CP-43394 3.0 Create the consumer’s Enrollment segment - Past Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | coverageCode                 | <coverageCode>       |
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 25                   |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | highDate-DC          |
      | enrollmentStartDate          | 1stDayofLastMonth    |
      | enrollmentEndDate            | lastDayofLastMonth   |
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | categoryCode                 | 10                   |
      | genericFieldText1            | null                 |
      | planCode                     | <planCode>           |
      | planId                       | 104                  |
      | serviceRegionCode            | Northeast            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | selectionReason              | selectionReason1     |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>        |
      | population          | <population>        |
      | eligibilityScenario | Default Eligibility |
      | enrollmentScenario  | Default Enrollment  |
      | benefitStatus       | Eligible            |
    Then I Verify Consumer Actions as following data
      | [0].action               | Required |
      | [0].consumerAction       | Enroll   |
      | [0].planSelectionAllowed | true     |
    And Wait for 3 seconds
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | empty |[blank]|
    Examples:
      | name     | coverageCode | population         | subProgramTypeCode | planCode  |
      | 43394-01 | 460          | Alliance-Child     | Alliance           | 087358900 |
      | 43394-02 | 420          | Immigrant Children | ImmigrantChildren  | 081080400 |
      | 43394-03 | 130          | DCHF-Adult         | DCHF               | 081080400 |


  @API-CP-43394 @API-CP-43394-03 @kursat @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: DC EB: Enrollment Create or Update and Disenroll - Update Enrollment with No Plan Code
  CP-43394 DC EB: 834 Default Enrollment Create or Update (AB+HI)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event1>       |
      | coverageCode                 | <coverageCode>       |
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 25                   |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | highDate-DC          |
      | enrollmentStartDate          | 1stDayofLastMonth    |
      | enrollmentEndDate            | highDate-DC          |
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | categoryCode                 | 10                   |
      | planCode                     | <planCode>           |
      | planId                       | 104                  |
      | serviceRegionCode            | Northeast            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | selectionReason              | selectionReason1     |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated Eligibility and Enrollment Create API
    # CP-43394 1.0 Update the Consumer’s Enrollment Segment - Enrollment Update
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event2>       |
      | coverageCode                 | <coverageCode>       |
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 25                   |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | highDate-DC          |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | enrollmentEndDate            | highDate-DC          |
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | categoryCode                 | 10                   |
      | genericFieldText1            | null                 |
      | planCode                     | <planCode2>          |
      | planId                       | 104                  |
      | serviceRegionCode            | Northeast            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | selectionReason              | selectionReason1     |
      | planEndDateReason            | planEndDateReason1   |
      | requestedBy                  | 1                    |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    # CP-42989 1.0 Decide Scenario is MMIS Sends Default Enrollment
    Then I verify latest benefit status information with data
      | population          | <population>        |
      | benefitStatus       | Enrolled            |
      | eligibilityScenario | Default Eligibility |
      | enrollmentScenario  | Default Enrollment  |
      | timeframe           | Active              |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available               |
      | [0].consumerAction       | Plan Change Anniversary |
      | [0].planSelectionAllowed | true                    |
      | [1].action               | Available               |
      | [1].consumerAction       | Plan Change Pre-lockin  |
      | [1].planSelectionAllowed | true                    |
    # CP-43394 1.0 Update the Consumer’s Enrollment Segment - Enrollment Update
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate | 1stDayofLastMonth |
      | medicalPlanEndDate   | highDate-DC       |
      | planEndDateReason    | null              |
      | planCode             | <planCode>        |
      | updatedOn            | current           |
      | updatedBy            | 597               |
      | txnStatus            | ACCEPTED          |
      | selectionReason      | selectionReason1  |
      | enrollmentType       | MEDICAL           |
    Examples:
      | name     | coverageCode | population         | subProgramTypeCode | planCode  | planCode2 | event1      | event2      |
      | 43394-04 | 460          | Alliance-Child     | Alliance           | 087358900 | null      | Enrollment1 | Enrollment2 |
      | 43394-05 | 420          | Immigrant Children | ImmigrantChildren  | 081080400 | null      | Enrollment1 | Enrollment2 |
      | 43394-06 | 130          | DCHF-Adult         | DCHF               | 081080400 | null      | Enrollment1 | Enrollment2 |