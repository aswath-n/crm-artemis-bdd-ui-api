Feature: DCEB BenefitStatus DMN


  @API-CP-35994 @API-CP-35994-01-01 @API-CP-35996 @API-CP-35997 @shruti @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: Verify consumer population DCEB
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>       |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 25                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | past                 |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | <eligibilityEndDate> |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | DCHF                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>        |
      | population          | <population>        |
      | benefitStatus       | <benefitStatus>     |
      | eligibilityScenario | Default Eligibility |
      | timeframe           | Active              |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil>   |

    Examples:
      | name       | eligibilityEndDate | coverageCode | population          | benefitStatus        | consumerAction | planSelectionAllowed | changeAllowedFrom | changeAllowedUntil | action      |
      | 35994-01-1 | highDate-DC        | 231          | DCHF-Adult          | Eligible             | Enroll         | true                 | current           | 30DaysFromToday    | Required    |
      | 35994-01-2 | highDate-DC        | 740Y         | DCHF-Blind          | Eligible             | Enroll         | true                 | current           | 30DaysFromToday    | Required    |
      | 35994-01-3 | highDate-DC        | 120          | DCHF-Child          | Eligible             | Enroll         | true                 | current           | 30DaysFromToday    | Required    |
      | 35994-01-4 | highDate-DC        | 751Y         | DCHF-Disabled       | Eligible             | Enroll         | true                 | current           | 30DaysFromToday    | Required    |
      | 35994-01-5 | highDate-DC        | 241          | DCHF-Pregnant Women | Eligible             | Enroll         | true                 | current           | 30DaysFromToday    | Required    |
      | 35994-01-6 | nextMonth          | 241          | DCHF-Pregnant Women | Eligible             | Enroll         | true                 | current           | 30DaysFromToday    | Required    |

  @API-CP-37405 @API-CP-37405-01-01 @shruti @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: Verify consumer population DCEB-Update
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "<age>" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>       |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 25                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | past                 |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | <eligibilityEndDate> |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | DCHF                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>        |
      | population          | <population>        |
      | benefitStatus       | <benefitStatus>     |
      | eligibilityScenario | Default Eligibility |
      | timeframe           | Active              |
#    Then I Verify Consumer Actions as following data
#      | [0].action               | <action>               |
#      | [0].consumerAction       | <consumerAction>       |
#      | [0].planSelectionAllowed | <planSelectionAllowed> |
#      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
#      | [0].changeAllowedUntil   | <changeAllowedUntil>   |

    Examples:
      | name       | age                    | eligibilityEndDate | coverageCode | population | benefitStatus | consumerAction | planSelectionAllowed | changeAllowedFrom | changeAllowedUntil | action   |
      | 37405-01-1 | DC-EB-CONSUMER-above60 | highDate-DC        | 110Y         | DCHF-SSI   | Eligible      | Enroll         | true                 | current           | 30DaysFromToday    | Required |
      | 37405-01-2 | DC-EB-CONSUMER-below60 | highDate-DC        | 271          | DCHF-Adult | Eligible      | Enroll         | true                 | current           | 30DaysFromToday    | Required |
      | 37405-01-3 | DC-EB-CONSUMER-below60 | highDate-DC        | 774          | DCHF-Adult | Eligible      | Enroll         | true                 | current           | 30DaysFromToday    | Required |
      | 37405-01-4 | DC-EB-CONSUMER-above60 | highDate-DC        | 774D         | NONE       | null          | Enroll         | true                 | current           | 30DaysFromToday    | Required |

  @API-CP-36988 @API-CP-36988-01 @priyal @API-CP-36993 @API-CP-36993-01 @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: Verify MMIS Sends Default Eligibility for DCEB
  CP-36993 DC EB/ BLCRM Capture Business Event for New Eligibility for MMIS sends Default Eligibility
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-above60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>       |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 25                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | past                 |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | <eligibilityEndDate> |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | DCHF                 |
      | categoryCode                 | <categoryCode>       |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>        |
      | population          | <population>        |
      | benefitStatus       | <benefitStatus>     |
      | eligibilityScenario | Default Eligibility |
      | timeframe           | Active              |
    Then I will verify business events are generated with data
    # CP-36993 1.0 Business Event Required Fields
      | eventName                | DEFAULT_ELIGIBILITY  |
      | channel                  | SYSTEM_INTEGRATION   |
      | createdBy                | 597                  |
      | processDate              | current              |
      | consumerId               | <name>.consumerId    |
      | consumerName             | <name>               |
      | consumerFound            | true                 |
    # CP-36993 2.0 Business Event Optional Fields
      | outCome                  | <outCome>            |
      | eligibilityStartDate     | 1stDayofPresentMonth |
      | eligibilityEndDate       | <eligibilityEndDate> |
      | eligibilityEndReason     | null                 |
      | eligibilityCategoryCode  | <categoryCode>       |
      | eligibilityProgramCode   | MEDICAID             |
      | eligibilityCoverageCode  | <coverageCode>       |
      | eligibilityExemptionCode | qwer                 |
      | consumerPopulation       | <population>         |
      | benefitStatus            | <benefitStatus>      |

    Examples:
      | name       | eligibilityEndDate | coverageCode | population    | benefitStatus | outCome         | categoryCode |
      | 36988-01-1 | highDate-DC        | 750Y         | DCHF-Disabled | Eligible      | New Eligibility | 10           |

  @API-CP-39436 @API-CP-39437 @API-CP-39434 @API-CP-39435 @API-CP-39435-02 @API-CP-43458 @CP-43209 @shruti @burak @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: Verify consumer population DCEB - Alliance and Verify MMIS Sends Default Eligibility for Immigrant Children & Alliance
  CP-43209- 6.0 All Actions Unavailable- Deceasead / FFS
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>         |
      | isEnrollemntRequired         | no                     |
      | recordId                     | 25                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | past                   |
      | eligibilityStartDate         | 1stDayofPresentMonth   |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | eligibilityEndReason         | <eligibilityEndReason> |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>        |
      | population          | <population>        |
      | eligibilityScenario | Default Eligibility |
      | benefitStatus       | <benefitStatus>     |
    Examples:
      | name     | eligibilityEndDate | coverageCode | population         | subProgramTypeCode | benefitStatus | eligibilityEndReason |
      # CP-39436 AC2.0 Decide the Consumer’s Program and Population Based on Coverage Code
      # CP-39437 AC2.0 Unable to identify Benefit Status
      # CP-39435 AC3.0 Decide Program Population
      # CP-39435 AC4.0 Decide Benefit Status
      | 39436-01 | highDate-DC        | 100          | None               | Alliance           | null          | null                 |
      # CP-39436 AC2.1 Decide Consumer’s Program and Population for Immigrant Children
      # CP-39437 AC1.0 Decide Benefit Status is ELIGIBLE
      # CP-39435 AC3.0 Decide Program Population
      # CP-39435 AC4.0 Decide Benefit Status
      # CP-39434 AC1.0 MMIS Sends Default Eligibility for Immigrant Children
      | 39436-02 | highDate-DC        | 420          | Immigrant Children | ImmigrantChildren  | Eligible      | null                 |
      | 39436-03 | highDate-DC        | 420F         | Immigrant Children | ImmigrantChildren  | Eligible      | null                 |
      # CP-39436 AC2.2 Decide Consumer’s Program and Population for Alliance
      # CP-39437 AC1.0 Decide Benefit Status is ELIGIBLE
      # CP-39435 AC3.0 Decide Program Population
      # CP-39435 AC4.0 Decide Benefit Status
      # CP-39434 AC1.0 MMIS Sends Default Eligibility for Alliance
      | 39436-04 | highDate-DC        | 460          | Alliance-Child     | Alliance           | Eligible      | null                 |
      | 39436-05 | highDate-DC        | 470          | Alliance-Adult     | Alliance           | Eligible      | null                 |
      | 39436-06 | highDate-DC        | 470Q         | Alliance-Adult     | Alliance           | Eligible      | null                 |
      | 39436-07 | highDate-DC        | 470Z         | Alliance-Adult     | Alliance           | Eligible      | null                 |
      # CP-39437 AC2.0 Unable to identify Benefit Status
            # CP-39435 AC4.0 Decide Benefit Status
      | 39436-08 | lastDayofNextMonth | 470Z         | Alliance-Adult     | Alliance           | Eligible      | null                 |

  @API-CP-42972 @API-CP-42972-01 @burak @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: Verify System Decides MMIS Sends Default Enrollment DC for Alliance-Child ,Immigrant Children and DCHF-Disabled
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | coverageCode                 | <coverageCode>       |
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 25                   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate-DC          |
      | enrollmentStartDate          | 1stDayofPresentMonth |
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
    And Wait for 10 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>        |
      | population          | <population>        |
      | eligibilityScenario | Default Eligibility |
      | benefitStatus       | <benefitStatus>     |
    Examples:
      | name     | coverageCode | population         | subProgramTypeCode | benefitStatus | planCode  |
      | 42972-01 | 460          | Alliance-Child     | Alliance           | Enrolled      | 087358900 |
      | 42972-02 | 420          | Immigrant Children | ImmigrantChildren  | Enrolled      | 081080400 |
      | 42972-03 | 750Y         | DCHF-Disabled      | DCHF               | Enrolled      | 081080400 |

  @API-CP-42972 @API-CP-42972-02 @burak @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: Verify System Decides MMIS Sends Default Enrollment DC for Alliance-Child ,Immigrant Children and DCHF-Disabled for Different enrollmentStartDate
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | coverageCode                 | <coverageCode>       |
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 25                   |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | highDate-DC          |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate-DC          |
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | categoryCode                 | 10                   |
      | genericFieldText1            | null                 |
      | planCode                     | 081080400            |
      | planId                       | 104                  |
      | serviceRegionCode            | Northeast            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | selectionReason              | selectionReason1     |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
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
      | enrollmentEndDate            | highDate-DC           |
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
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | population          | <population>           |
      | benefitStatus       | <initialBenefitStatus> |
      | eligibilityScenario | Default Eligibility    |
      | enrollmentScenario  | <enrollmentScenario>   |
      | timeframe           | Active                 |
    Examples:
      | name     | coverageCode | population         | subProgramTypeCode | initialBenefitStatus | enrollmentStartDate  | enrollmentScenario | planCode  |
      | 42972-04 | 460          | Alliance-Child     | Alliance           | Enrolled             | 1stDayofPresentMonth | Default Enrollment | 087358900 |
      | 42972-05 | 420          | Immigrant Children | ImmigrantChildren  | Enrolled             | 1stDayofLastMonth    | Default Enrollment | 081080400 |
      | 42972-06 | 750Y         | DCHF-Disabled      | DCHF               | Eligible             | past                 |[blank]| 081080400 |

  @API-CP-42972 @API-CP-42972-07 @burak @API-DC-EE @DC-EB-API-Regression
  Scenario: Verify System Decides MMIS Sends Default Enrollment DC for Exact Match Scenario
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 42972-07 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | coverageCode                 | 750Y                 |
      | consumerId                   | 42972-07.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 25                   |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | highDate-DC          |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate-DC          |
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | DCHF                 |
      | categoryCode                 | 10                   |
      | genericFieldText1            | null                 |
      | planCode                     | 081080400            |
      | planId                       | 104                  |
      | serviceRegionCode            | Northeast            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | selectionReason              | selectionReason1     |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | coverageCode                 | 750Y                 |
      | consumerId                   | 42972-07.consumerId  |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 25                   |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | highDate-DC          |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate-DC          |
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | DCHF                 |
      | categoryCode                 | 10                   |
      | genericFieldText1            | null                 |
      | planCode                     | 081080400            |
      | planId                       | 104                  |
      | serviceRegionCode            | Northeast            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | selectionReason              | selectionReason1     |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | coverageCode                 | 750Y                 |
      | consumerId                   | 42972-07.consumerId  |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 25                   |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | highDate-DC          |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate-DC          |
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | DCHF                 |
      | categoryCode                 | 10                   |
      | genericFieldText1            | null                 |
      | planCode                     | 081080400            |
      | planId                       | 104                  |
      | serviceRegionCode            | Northeast            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | selectionReason              | selectionReason1     |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "42972-07.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | population          | DCHF-Disabled       |
      | benefitStatus       | Enrolled            |
      | eligibilityScenario | Default Eligibility |
      | enrollmentScenario  | Default Enrollment  |
      | timeframe           | Active              |

  @API-CP-43208 @API-CP-43208-01 @API-CP-43209 @API-CP-43209 @shruti @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: Verify Benefit Status =Not Eligible
  CP-43209- 6.0 All Actions Unavailable- Not Eligible scenario
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | coverageCode                 | <coverageCode>         |
      | consumerId                   | <name>.consumerId      |
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]                 |
      | isEnrollemntRequired         | <isEnrollemntRequired> |
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
      | genericFieldText1            | null                   |
      | planCode                     | 081080400              |
      | planId                       | 104                    |
      | serviceRegionCode            | Northeast              |
      | anniversaryDate              | anniversaryDate        |
      | channel                      | SYSTEM_INTEGRATION     |
      | selectionReason              | selectionReason1       |
      | eligibilityEndReason         | <eligibilityEndReason> |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>        |
      | population          | <population>        |
      | eligibilityScenario | Default Eligibility |
      | benefitStatus       | <benefitStatus>     |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumeraction>       |
      | [0].planSelectionAllowed | <planselectionallowed> |
    Examples:
      | name     | coverageCode | population                  | subProgramTypeCode | benefitStatus | eligibilityStartDate | eligibilityEndDate | isEnrollemntRequired | enrollmentStartDate | enrollmentEndDate | eligibilityEndReason |planselectionallowed | consumeraction | action      |
      | 43208-01 | 460          | Alliance-Child              | Alliance           | Not Eligible  | current              | current            | no                   | [blank]             | [blank]           | [blank]              |false                | null           | Unavailable |
      | 43208-02 | 420          | Immigrant Children-Deceased | ImmigrantChildren  | Not Eligible  | nextDay              | current            | no                   | [blank]             | [blank]           | 8X                   |false                | null           | Unavailable |
      | 43208-03 | 130          | DCHF-Adult                  | DCHF               | Not Eligible  | nextDayPlusOne       | nextDay            | no                   | [blank]             | [blank]           | [blank]              |false                | null           | Unavailable |
      | 43208-04 | 130          | DCHF-Adult                  | DCHF               | Eligible      | nextDay              | nextDayPlusOne     | no                   | [blank]             | [blank]           | [blank]              |true                 | Enroll         | Required    |
      | 43600-01 | 130          | DCHF-Adult                  | DCHF               | Not Eligible  | nextDay              | nextDay            | no                   | [blank]             | [blank]           | [blank]              |false                | null           | Unavailable |


  @API-CP-43458  @CP-43209 @API-CP-43598  @API-CP-43600 @shruti @burak @thilak @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: Verify consumer population DCEB - Deceassed and FFS
  CP-43209- 6.0 All Actions Unavailable- Deceasead / FFS
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>         |
      | isEnrollemntRequired         | no                     |
      | recordId                     | 25                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | past                   |
      | eligibilityStartDate         | 1stDayofPresentMonth   |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | eligibilityEndReason         | <eligibilityEndReason> |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>        |
      | population          | <population>        |
      | eligibilityScenario | Default Eligibility |
      | benefitStatus       | <benefitStatus>     |
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable |
      | [0].consumerAction       | null        |
      | [0].planSelectionAllowed | false       |
    Examples:
      | name     | eligibilityEndDate | coverageCode | population                         | subProgramTypeCode | benefitStatus | eligibilityEndReason |
   # CP-43458 AC2.0 Deceased
      | 43458-01 | highDate-DC        | 130          | DCHF-Deceased                      | DCHF               | Eligible      | 8X                   |
      | 43458-02 | lastDayofNextMonth | 470Z         | Alliance-Deceased                  | Alliance           | Eligible      | 8X                   |
      | 43458-03 | highDate-DC        | 420          | Immigrant Children-Deceased        | ImmigrantChildren  | Eligible      | 8X                   |
      | 43458-04 | highDate-DC        | 130          | DCHF-Deceased                      | DCHF               | Eligible      | DA                   |
      | 43458-05 | lastDayofNextMonth | 470Q         | Alliance-Deceased                  | Alliance           | Eligible      | DA                   |
      | 43458-06 | highDate-DC        | 420F         | Immigrant Children-Deceased        | ImmigrantChildren  | Eligible      | DA                   |
      | 43458-07 | highDate-DC        | 161Y         | DCHF-Deceased                      | DCHF               | Eligible      | DD                   |
      | 43458-08 | lastDayofNextMonth | 460          | Alliance-Deceased                  | Alliance           | Eligible      | DD                   |
      | 43458-09 | highDate-DC        | 420F         | Immigrant Children-Deceased        | ImmigrantChildren  | Eligible      | DD                   |
      # CP-43458 AC3.0 Fee for Service - End Reason Codes
      | 43458-10 | highDate-DC        | 130          | DCHF-Fee for Service               | DCHF               | Eligible      | 1F                   |
      | 43458-11 | lastDayofNextMonth | 470Z         | Alliance-Fee for Service           | Alliance           | Eligible      | 1F                   |
      | 43458-12 | highDate-DC        | 420          | Immigrant Children-Fee for Service | ImmigrantChildren  | Eligible      | 1F                   |
      | 43458-13 | highDate-DC        | 130          | DCHF-Fee for Service               | DCHF               | Eligible      | 2E                   |
      | 43458-14 | lastDayofNextMonth | 470Q         | Alliance-Fee for Service           | Alliance           | Eligible      | 2E                   |
      | 43458-15 | highDate-DC        | 420F         | Immigrant Children-Fee for Service | ImmigrantChildren  | Eligible      | 2E                   |
      | 43458-16 | highDate-DC        | 161Y         | DCHF-Fee for Service               | DCHF               | Eligible      | 2J                   |
      | 43458-17 | lastDayofNextMonth | 460          | Alliance-Fee for Service           | Alliance           | Eligible      | 2J                   |
      | 43458-18 | highDate-DC        | 420F         | Immigrant Children-Fee for Service | ImmigrantChildren  | Eligible      | 2J                   |
      | 43458-19 | highDate-DC        | 130          | DCHF-Fee for Service               | DCHF               | Eligible      | 3E                   |
      | 43458-20 | lastDayofNextMonth | 470Z         | Alliance-Fee for Service           | Alliance           | Eligible      | 3E                   |
      | 43458-21 | highDate-DC        | 420          | Immigrant Children-Fee for Service | ImmigrantChildren  | Eligible      | 3E                   |
      | 43458-22 | highDate-DC        | 130          | DCHF-Fee for Service               | DCHF               | Eligible      | LT                   |
      | 43458-23 | lastDayofNextMonth | 470Q         | Alliance-Fee for Service           | Alliance           | Eligible      | LT                   |
      | 43458-24 | highDate-DC        | 420F         | Immigrant Children-Fee for Service | ImmigrantChildren  | Eligible      | LT                   |
      | 43458-25 | highDate-DC        | 161Y         | DCHF-Fee for Service               | DCHF               | Eligible      | RH                   |
      | 43458-26 | lastDayofNextMonth | 460          | Alliance-Fee for Service           | Alliance           | Eligible      | RH                   |
      | 43458-27 | highDate-DC        | 420F         | Immigrant Children-Fee for Service | ImmigrantChildren  | Eligible      | RH                   |
      | 43458-28 | highDate-DC        | 130          | DCHF-Fee for Service               | DCHF               | Eligible      | EN                   |
      | 43458-29 | lastDayofNextMonth | 470Q         | Alliance-Fee for Service           | Alliance           | Eligible      | EN                   |
      | 43458-30 | highDate-DC        | 420F         | Immigrant Children-Fee for Service | ImmigrantChildren  | Eligible      | EN                   |
      | 43458-31 | highDate-DC        | 130          | DCHF-Fee for Service               | DCHF               | Eligible      | DR                   |
      | 43458-32 | lastDayofNextMonth | 470Q         | Alliance-Fee for Service           | Alliance           | Eligible      | DR                   |
      | 43458-33 | highDate-DC        | 420          | Immigrant Children-Fee for Service | ImmigrantChildren  | Eligible      | DR                   |
      | 43458-31 | highDate-DC        | 130          | DCHF-Fee for Service               | DCHF               | Eligible      | 9N                   |
      | 43458-32 | lastDayofNextMonth | 470Q         | Alliance-Fee for Service           | Alliance           | Eligible      | 9N                   |
      | 43458-33 | highDate-DC        | 420          | Immigrant Children-Fee for Service | ImmigrantChildren  | Eligible      | 9N                   |

  @API-CP-43598 @thilak @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: Verify consumer population DCEB - Deceassed and FFS
  CP-43598 - 6.0 All Actions Unavailable- Deceasead / FFS
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>         |
      | isEnrollemntRequired         | no                     |
      | recordId                     | 25                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | past                   |
      | eligibilityStartDate         | 1stDayofPresentMonth   |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | eligibilityEndReason         | <eligibilityEndReason> |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>        |
      | population          | <population>        |
      | eligibilityScenario | Default Eligibility |
      | benefitStatus       | <benefitStatus>     |
    Then I Verify Consumer Actions as following data
      | [0].action               | Required |
      | [0].consumerAction       | Enroll   |
      | [0].planSelectionAllowed | true     |
    Examples:
      | name    | eligibilityEndDate | coverageCode | population         | subProgramTypeCode | benefitStatus | eligibilityEndReason |
   # CP-43598
      | 43598-1 | highDate-DC        | 161Y         | DCHF-Adult         | DCHF               | Eligible      | CPFFS                |
      | 43598-2 | lastDayofNextMonth | 460          | Alliance-Child     | Alliance           | Eligible      | CPFFS                |
      | 43598-3 | highDate-DC        | 420F         | Immigrant Children | ImmigrantChildren  | Eligible      | CPFFS                |

  @API-CP-42972 @API-CP-42972-07 @burak @API-DC-EE @DC-EB-API-Regression
  Scenario: Verify System Decides MMIS Sends Default Enrollment DC for Exact Match Scenario
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 42972-07 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | coverageCode                 | 750Y                 |
      | consumerId                   | 42972-07.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |      [blank]                |
      | isEnrollemntRequired         | no                   |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 25                   |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | highDate-DC          |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate-DC          |
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | DCHF                 |
      | categoryCode                 | 10                   |
      | genericFieldText1            | null                 |
      | planCode                     | 081080400            |
      | planId                       | 104                  |
      | serviceRegionCode            | Northeast            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | selectionReason              | selectionReason1     |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | coverageCode                 | 750Y                 |
      | consumerId                   | 42972-07.consumerId  |
      | isEligibilityRequired        | no                   |
      | otherSegments                |   [blank]                   |
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 25                   |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | highDate-DC          |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate-DC          |
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | DCHF                 |
      | categoryCode                 | 10                   |
      | genericFieldText1            | null                 |
      | planCode                     | 081080400            |
      | planId                       | 104                  |
      | serviceRegionCode            | Northeast            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | selectionReason              | selectionReason1     |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | coverageCode                 | 750Y                 |
      | consumerId                   | 42972-07.consumerId  |
      | isEligibilityRequired        | no                   |
      | otherSegments                |     [blank]                 |
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 25                   |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | highDate-DC          |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate-DC          |
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | DCHF                 |
      | categoryCode                 | 10                   |
      | genericFieldText1            | null                 |
      | planCode                     | 081080400            |
      | planId                       | 104                  |
      | serviceRegionCode            | Northeast            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | selectionReason              | selectionReason1     |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "42972-07.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | population          | DCHF-Disabled       |
      | benefitStatus       | Enrolled            |
      | eligibilityScenario | Default Eligibility |
      | enrollmentScenario  | Default Enrollment  |
      | timeframe           | Active              |

  @API-CP-47324 @API-CP-47324-01 @thilak @API-DC-EE @DC-EB-API-Regression
  Scenario: Verify System Decides MMIS sends Enrollment Selection then Enrollment closes AASR
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 47324-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | coverageCode                 | 750Y                 |
      | consumerId                   | 47324-01.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |     [blank]                 |
      | isEnrollemntRequired         | no                   |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 25                   |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | highDate-DC          |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate-DC          |
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | DCHF                 |
      | categoryCode                 | 10                   |
      | genericFieldText1            | null                 |
      | planCode                     | 081080400            |
      | planId                       | 104                  |
      | serviceRegionCode            | Northeast            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | selectionReason              | selectionReason1     |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | coverageCode                 | 750Y                 |
      | consumerId                   | 47324-01.consumerId  |
      | isEligibilityRequired        | no                   |
      | otherSegments                |    [blank]                  |
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 25                   |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | highDate-DC          |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate-DC          |
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | DCHF                 |
      | categoryCode                 | 10                   |
      | genericFieldText1            | null                 |
      | planCode                     | 081080400            |
      | planId                       | 104                  |
      | serviceRegionCode            | Northeast            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | selectionReason              | selectionReason1     |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I verify latest task information with name "" for consumer id "47324-01.consumerId" with data
      | find_taskTypeId       | 16277              |
      | this_taskTypeId_count | 1                  |
      | taskStatus            | Closed             |

  @API-CP-42885 @API-CP-42885-01 @thilak @API-DC-EE @DC-EB-API-Regression
  Scenario: Verify System Decides DC EB MMIS sends Loss of Eligibility, Enrollment Open/Closed closes AA SR
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 42885-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | coverageCode                 | 130                 |
      | consumerId                   | 42885-01.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |     [blank]                 |
      | isEnrollemntRequired         | no                   |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 25                   |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | highDate-DC          |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate-DC          |
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | DCHF                 |
      | categoryCode                 | 10                   |
      | genericFieldText1            | null                 |
      | planCode                     | 081080400            |
      | planId                       | 104                  |
      | serviceRegionCode            | Northeast            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | selectionReason              | selectionReason1     |
    And I run create Eligibility and Enrollment API
    Then I verify latest task information with name "" for consumer id "42885-01.consumerId" with data
      | find_taskTypeId       | 16277 |
      | this_taskTypeId_count | 1     |
      | taskStatus            | Open  |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | coverageCode                 | 130                 |
      | consumerId                   | 42885-01.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |    [blank]                  |
      | isEnrollemntRequired         | no                   |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 25                   |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | lastDayofPresentMonth|
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            |lastDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | DCHF                 |
      | categoryCode                 | 10                   |
      | genericFieldText1            | null                 |
      | planCode                     | 081080400            |
      | planId                       | 104                  |
      | serviceRegionCode            | Northeast            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | selectionReason              | selectionReason1     |
    And I run create Eligibility and Enrollment API
    Then I verify latest task information with name "" for consumer id "42885-01.consumerId" with data
      | find_taskTypeId       | 16277               |
      | this_taskTypeId_count | 1                   |
      | taskStatus            | Closed              |
      | disposition           | LOSS_OF_ELIGIBILITY |

