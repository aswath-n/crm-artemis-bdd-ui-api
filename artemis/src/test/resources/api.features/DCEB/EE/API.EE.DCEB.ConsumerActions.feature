Feature: Decide consumer enrollment actions

  @API-CP-43458 @API-CP-43209 @API-CP-43599 @shruti @burak @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: Verify consumer population DCEB - Deceassed and FFS-Negative
  CP-43209- 6.0 All Actions Unavailable- Deceasead / FFS
  CP-43599- 6.0 All Actions Unavailable
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                    |
      | otherSegments                |  [blank]               |
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
      | name     | eligibilityEndDate | coverageCode | population | subProgramTypeCode | benefitStatus | eligibilityEndReason |
      # CP-43458 Negative Scenarios
      | 43458-34 | highDate-DC        | null         | NONE       | Alliance           | null          | Dy                   |
      | 43458-35 | highDate-DC        | null         | NONE       | ImmigrantChildren  | null          | null                 |
      | 43458-36 | highDate-DC        | null         | NONE       | DCHF               | null          | EF                   |

  @API-CP-35994 @API-CP-35994-01-01 @API-CP-35996 @API-CP-35997 @API-CP-43599 @API-CP-43599-03 @shruti @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: Verify consumer population DCEB
  CP-43599 -A.C 3.0Enroll Action for DCHF and Non DCHF
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                    |
      | otherSegments                |   [blank]                       |
      | coverageCode                 | <coverageCode>         |
      | isEnrollemntRequired         | no                     |
      | recordId                     | 25                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | past                   |
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode>   |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>        |
      | population          | <population>        |
      | benefitStatus       | <benefitStatus>     |
      | eligibilityScenario | Default Eligibility |
#      | timeframe           | Active              |
    Then I Verify Consumer Actions as following data
      | [0].action               | Required             |
      | [0].consumerAction       | Enroll               |
      | [0].planSelectionAllowed | true                 |
      | [0].changeAllowedFrom    | <changeAllowedFrom>  |
      | [0].changeAllowedUntil   | <changeAllowedUntil> |

    Examples:
      | name       | eligibilityEndDate | coverageCode | population          | benefitStatus | consumerAction | subProgramTypeCode | changeAllowedFrom      | changeAllowedUntil     | eligibilityStartDate  |
      | 35994-01-1 | highDate-DC        | 231          | DCHF-Adult          | Eligible      | Enroll         | DCHF               | current                | 30DaysFromToday        | 1stDayofPresentMonth  |
      | 35994-01-2 | highDate-DC        | 740Y         | DCHF-Blind          | Eligible      | Enroll         | DCHF               | current                | 30DaysFromToday        | 1stDayofPresentMonth  |
      | 35994-01-3 | highDate-DC        | 120          | DCHF-Child          | Eligible      | Enroll         | DCHF               | current                | 30DaysFromToday        | 1stDayofPresentMonth  |
      | 35994-01-4 | highDate-DC        | 751Y         | DCHF-Disabled       | Eligible      | Enroll         | DCHF               | current                | 30DaysFromToday        | 1stDayofPresentMonth  |
      | 35994-01-5 | highDate-DC        | 241          | DCHF-Pregnant Women | Eligible      | Enroll         | DCHF               | current                | 30DaysFromToday        | 1stDayofPresentMonth  |
      | 35994-01-6 | nextMonth          | 241          | DCHF-Pregnant Women | Eligible      | Enroll         | DCHF               | current                | 30DaysFromToday        | 1stDayofPresentMonth  |
      #New Logic to calculate Start date and due date For DCHF and Non DCHF A.C 3.0 Enroll Action - DCHF & Enroll Action - Alliance and Immigrant Children
      | 43599-03-1 | highDate-DC        | 241          | DCHF-Pregnant Women | Eligible      | Enroll         | DCHF               | current                | 30DaysFromToday        | 1stDayofPresentMonth  |
      | 43599-03-2 | highDate-DC        | 241          | DCHF-Pregnant Women | Eligible      | Enroll         | DCHF               | current                | 30DaysFromToday        | 1stDayof2MonthsBefore |
      | 43599-03-3 | highDate-DC        | 241          | DCHF-Pregnant Women | Eligible      | Enroll         | DCHF               | futureEnrollActionDCHF | futureEnrollActionDCHF | 1stDayof4MonthsAfter  |
      | 43599-03-4 | highDate-DC        | 460          | Alliance-Child      | Eligible      | Enroll         | Alliance           | enrollActionNonDCHF    | enrollActionNonDCHF    | 1stDayofPresentMonth  |
      | 43599-03-5 | highDate-DC        | 460          | Alliance-Child      | Eligible      | Enroll         | Alliance           | enrollActionNonDCHF    | enrollActionNonDCHF    | 1stDayof4MonthsAfter  |
      | 43599-03-6 | highDate-DC        | 460          | Alliance-Child      | Eligible      | Enroll         | Alliance           | enrollActionNonDCHF    | enrollActionNonDCHF    | 1stDayofLastMonth     |
      | 43599-03-7 | highDate-DC        | 420          | Immigrant Children  | Eligible      | Enroll         | ImmigrantChildren  | enrollActionNonDCHF    | enrollActionNonDCHF    | 1stDayof4MonthsAfter  |
      | 43599-03-8 | highDate-DC        | 420          | Immigrant Children  | Eligible      | Enroll         | ImmigrantChildren  | enrollActionNonDCHF    | enrollActionNonDCHF    | 1stDayofPresentMonth  |
      | 43599-03-9 | highDate-DC        | 420          | Immigrant Children  | Eligible      | Enroll         | ImmigrantChildren  | enrollActionNonDCHF    | enrollActionNonDCHF    | 1stDayofLastMonth     |

  @API-CP-43599 @API-CP-43599-04 @shruti @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: 4.0 Plan Change Pre-Lockin Action- Deceased and FFS - No Prelockin window
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | coverageCode                 | <coverageCode>         |
      | consumerId                   | <name>.consumerId      |
      | isEligibilityRequired        | yes                    |
      | otherSegments                |  [blank]               |
      | isEnrollemntRequired         | yes                    |
      | isEnrollmentProviderRequired | no                     |
      | recordId                     | 25                     |
      | eligibilityStartDate         | 1stDayofPresentMonth   |
      | eligibilityEndDate           | highDate-DC            |
      | enrollmentStartDate          | 1stDayofPresentMonth   |
      | enrollmentEndDate            | highDate-DC            |
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
      | eligibilityEndReason         | <eligibilityEndReason> |
    And I run create Eligibility and Enrollment API
    And Wait for 10 seconds
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
      | name        | coverageCode | population                         | subProgramTypeCode | benefitStatus | planCode  | eligibilityEndReason |
      | 43599-04-01 | 460          | Alliance-Fee for Service           | Alliance           | Enrolled      | 087358900 | 2E                   |
      | 42972-04-02 | 420          | Immigrant Children-Fee for Service | ImmigrantChildren  | Enrolled      | 081080400 | 1F                   |
      | 42972-04-03 | 750Y         | DCHF-Fee for Service               | DCHF               | Enrolled      | 081080400 | 2J                   |
      | 43599-04-04 | 460          | Alliance-Deceased                  | Alliance           | Enrolled      | 087358900 | 8X                   |
      | 42972-04-05 | 420          | Immigrant Children-Deceased        | ImmigrantChildren  | Enrolled      | 081080400 | DA                   |
      | 42972-04-06 | 750Y         | DCHF-Deceased                      | DCHF               | Enrolled      | 081080400 | DD                   |

  @API-CP-43599 @API-CP-43599-05 @shruti @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: 5.0 Plan Change Anniversary Action
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event>          |
      | coverageCode                 | <coverageCode>         |
      | consumerId                   | <name>.consumerId      |
      | isEligibilityRequired        | yes                    |
      | otherSegments                | [blank]                |
      | isEnrollemntRequired         | yes                    |
      | isEnrollmentProviderRequired | no                     |
      | recordId                     | 25                     |
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | highDate-DC            |
      | enrollmentStartDate          | <enrollmentStartDate>  |
      | enrollmentEndDate            | highDate-DC            |
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
    And Wait for 10 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation | <population>    |
      | population        | <population>    |
      | benefitStatus     | <benefitStatus> |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available                              |
      | [0].consumerAction       | Plan Change Pre-lockin                 |
      | [0].planSelectionAllowed | true                                   |
      | [1].action               | Available                              |
      | [1].consumerAction       | Plan Change Anniversary                |
      | [1].planSelectionAllowed | true                                   |
      | [1].enrollmentStartDate  | <name><event>.enrollments[0].startDate |
      | [1].changeAllowedFrom    | anniversaryWindow                      |
      | [1].changeAllowedUntil   | anniversaryWindow                      |
    Examples:
      | name        | coverageCode | population         | subProgramTypeCode | benefitStatus | planCode  | eligibilityStartDate          | enrollmentStartDate           |
      | 43599-05-01 | 460          | Alliance-Child     | Alliance           | Enrolled      | 087358900 | 1stDayof6Months2YearsBefore   | 1stDayof6MonthsBefore         |
      | 42972-05-02 | 420F         | Immigrant Children | ImmigrantChildren  | Enrolled      | 081080400 | 1YearFrom1stDayofPresentMonth | 1YearFrom1stDayofPresentMonth |
      | 42972-05-03 | 161Y         | DCHF-Adult         | DCHF               | Enrolled      | 081080400 | 1YearFrom1stDayofPresentMonth | 1YearFrom1stDayofPresentMonth |
      | 42972-05-04 | 161Y         | DCHF-Adult         | DCHF               | Enrolled      | 081080400 | 1stDayofPresentMonth          | 1stDayofPresentMonth          |
      | 42972-05-05 | 161Y         | DCHF-Adult         | DCHF               | Enrolled      | 081080400 | 1stDayof6MonthsBefore         | 1stDayofPresentMonth          |
