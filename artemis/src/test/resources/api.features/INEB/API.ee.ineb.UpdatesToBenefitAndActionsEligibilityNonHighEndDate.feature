Feature: IN-EB Updates to Benefit Status and Consumer Actions when eligibility has a non-high end date


  @API-CP-32018 @API-CP-32018-01 @API-EE-IN @IN-EB-API-Regression @elshan
  Scenario Outline: Decide Benefit Status value is "ELIGIBLE" for HHW or HCC Program/Population with non-high date
  1.0 Decide Benefit Status value is "ELIGIBLE" for HHW or HCC Program/Population
  4.0 “Enroll” Consumer Action for HHW and HCC
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
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
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
      | fileSource                   | RECIPIENT               |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | <name>.consumerId        |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    # 1.0 Decide Benefit Status value is "ELIGIBLE" for HHW or HCC Program/Population
    Then I verify latest benefit status information with data
      | benefitStatus | Eligible |
    # 4.0 “Enroll” Consumer Action for HHW and HCC
    Then I Verify Consumer Actions as following data
      | [0].action               | Required             |
      | [0].consumerAction       | Enroll               |
      | [0].planSelectionAllowed | true                 |
      | [0].changeAllowedFrom    | <changeAllowedFrom>  |
      | [0].changeAllowedUntil   | <changeAllowedUntil> |
    Then I send API call with name "OU" for update Enrollment information
      | [0].consumerId         | <name>.consumerId      |
      | [0].planId             | 26                     |
      | [0].planCode           | <planCode>             |
      | [0].startDate          | <enrollmentStartDate1> |
      | [0].endDate            | <enrollmentEndDate1>   |
      | [0].subProgramTypeCode | <subProgramTypeCode>   |
      | [0].serviceRegionCode  | Statewide              |
      | [0].selectionReason    | 02                     |
      | [0].channel            | SYSTEM_INTEGRATION     |
      | [0].anniversaryDate    | anniversaryDate::      |
    And I send API call with name "OU" for process Outbound Update
      | [0].planId             | delete::               |
      | [0].planCode           | <planCode>             |
      | [0].consumerId         | <name>.consumerId      |
      | [0].enrollmentId       | c.data[0].enrollmentId |
      | [0].startDate          | <enrollmentStartDate1> |
      | [0].endDate            | <enrollmentEndDate1>   |
      | [0].subProgramTypeCode | <subProgramTypeCode>   |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | isEnrollmentProviderRequired | no                    |
      | recordId                     | 16                    |
      | enrollmentRecordId           | 16                    |
      | enrollmentStartDate          | <enrollmentStartDate> |
      | enrollmentEndDate            | <enrollmentEndDate>   |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted              |
      | programCode                  | <programCode>         |
      | subProgramTypeCode           | <subProgramTypeCode>  |
      | planCode                     | <planCode>            |
      | planId                       | 26                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
    And User provide other enrollment segments details
      | consumerId        | <name>.consumerId      |
      | startDate         | <enrollmentStartDate>  |
      | endDate           | <enrollmentEndDate>    |
      | genericFieldText1 | c.data[0].enrollmentId |
      | segmentTypeCode   | OTHER_TXN              |
    And User provide other enrollment segments details
      | genericFieldText1 | 010          |
      | segmentTypeCode   | ERROR_REASON |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
  # 1.0 Decide Benefit Status value is "ELIGIBLE" for HHW or HCC Program/Population
    Then I verify latest benefit status information with data
      | benefitStatus | Eligible |
  #4.0 “Enroll” Consumer Action for HHW and HCC
    Then I Verify Consumer Actions as following data
      | [0].action               | Required             |
      | [0].consumerAction       | Enroll               |
      | [0].planSelectionAllowed | true                 |
      | [0].changeAllowedFrom    | <changeAllowedFrom>  |
      | [0].changeAllowedUntil   | <changeAllowedUntil> |
    Examples:
      | name     | programCode | subProgramTypeCode | eligibilityStatusCode | planCode  | eligibilityStartDate | eligibilityEndDate | changeAllowedFrom    | changeAllowedUntil        | enrollmentStartDate | enrollmentEndDate       | enrollmentStartDate1 | enrollmentEndDate1        |
      | 32018-01 | R           | HoosierHealthwise  | M                     | 700410350 | 1stDayofPresentMonth | future             | 1stDayofPresentMonth | 14DayFromFirstDayOfMonth  | 1stDayofNextMonth   | lastDayOf3MonthsFromNow | fdnxtmth::           | lastdayof3monthsfromnow:: |
      | 32018-02 | R           | HoosierHealthwise  | X                     | 700410350 | 1stDayofPresentMonth | future             | 1stDayofPresentMonth | 14DayFromFirstDayOfMonth  | 1stDayofNextMonth   | lastDayOf3MonthsFromNow | fdnxtmth::           | lastdayof3monthsfromnow:: |
      | 32018-03 | A           | HoosierCareConnect | M                     | 499254630 | 1stDayofPresentMonth | future             | 1stDayofPresentMonth | 60DaysFromFirstDayOfMonth | 1stDayofNextMonth   | lastDayOf3MonthsFromNow | fdnxtmth::           | lastdayof3monthsfromnow:: |
      | 32018-04 | A           | HoosierCareConnect | V                     | 499254630 | 1stDayofPresentMonth | future             | 1stDayofPresentMonth | 60DaysFromFirstDayOfMonth | 1stDayofNextMonth   | lastDayOf3MonthsFromNow | fdnxtmth::           | lastdayof3monthsfromnow:: |
      | 32018-05 | A           | HoosierCareConnect | X                     | 499254630 | 1stDayofPresentMonth | future             | 1stDayofPresentMonth | 60DaysFromFirstDayOfMonth | 1stDayofNextMonth   | lastDayOf3MonthsFromNow | fdnxtmth::           | lastdayof3monthsfromnow:: |
      # Might be a defect, check again, record id 16 reject response cannot be achieved successfully causing consumer action to be unavailable
#      | 32018-06 | A           | HoosierCareConnect | M                     | 499254630 | 1stDayofPresentMonth | lastDayofPresentMonth | 1stDayofPresentMonth | lastDayofPresentMonth     | 1stDayofPresentMonth | lastDayofPresentMonth    | 1stdayofpresentmonth:: | lastdayofpresentmonth::   |


  @API-CP-32018 @API-CP-32018-02 @API-CP-32018-04 @API-EE-IN @IN-EB-API-Regression @elshan
  Scenario Outline: Decide Benefit Status value is "ENROLLED" for HHW or HCC Program/Population with non-high date
  2.0 Decide Benefit Status value is "ENROLLED" for HHW or HCC Program/Population
  4.0 “Enroll” Consumer Action for HHW and HCC
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | future                  |
      | enrollmentStartDate          | 1stDayofNextMonth       |
      | enrollmentEndDate            | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | planCode                     | <planCode>              |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
      | genericFieldText1            | Eligible                |
      | fileSource                   | RECIPIENT               |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | benefitStatus | Enrolled |
    #4.0 Else validation for “Enroll” Consumer Action for HHW and HCC
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable |
      | [0].consumerAction       | null        |
      | [0].planSelectionAllowed | false       |
    Examples:
      | name     | programCode | subProgramTypeCode | eligibilityStatusCode | planCode  |
      | 32018-08 | R           | HoosierHealthwise  | M                     | 700410350 |
      | 32018-09 | R           | HoosierHealthwise  | X                     | 700410350 |
      | 32018-10 | A           | HoosierCareConnect | M                     | 499254630 |
      | 32018-11 | A           | HoosierCareConnect | V                     | 499254630 |
      | 32018-12 | A           | HoosierCareConnect | X                     | 499254630 |


  @API-CP-32018 @API-CP-32018-03 @API-EE-IN @IN-EB-API-Regression @elshan
  Scenario Outline: Decide Benefit Status value is "ENROLLED" for HIP Program/Population with non-high date
  3.0 Decide Benefit Status value is "ENROLLED" for HIP Program/Population
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | future                  |
      | enrollmentStartDate          | 1stDayofNextMonth       |
      | enrollmentEndDate            | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | planCode                     | <planCode>              |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
      | genericFieldText1            | Eligible                |
      | fileSource                   | RECIPIENT               |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | benefitStatus | Enrolled |
    Examples:
      | name     | programCode | subProgramTypeCode | eligibilityStatusCode | planCode  |
      # HIP status becomes -- -- and I see check core indicator, this might be an issue
      | 32018-13 | H           | HealthyIndianaPlan | M                     | 555763410 |
      | 32018-14 | H           | HealthyIndianaPlan | X                     | 555763410 |
      | 32018-15 | H           | HealthyIndianaPlan | V                     | 555763410 |


  @API-CP-32018 @API-CP-32018-05 @API-EE-IN @IN-EB-API-Regression @elshan
  Scenario Outline: “Plan Change Pre-lockin” Consumer Action for HHW and HCC with non-high date
  5.0 “Plan Change Pre-lockin” Consumer Action for HHW and HCC
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | <eligibilityEndDate>    |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | enrollmentEndDate            | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | planCode                     | <planCode>              |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
      | genericFieldText1            | Eligible                |
      | fileSource                   | RECIPIENT               |
    And User provide other enrollment segments details
      | recordId          | 21                            |
      | consumerId        | <name>.consumerId             |
      | startDate         | <OpenEnrollmentEffectiveDate> |
      | endDate           | <OpenEnrollmentEndDate>       |
      | genericFieldText1 | O                             |
      | genericFieldDate1 | 1stDayofPresentMonth          |
      | segmentTypeCode   | OPEN_ENROLLMENT               |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | benefitStatus | Enrolled |
    #4.0 Else validation for “Enroll” Consumer Action for HHW and HCC
    Then I Verify Consumer Actions as following data
      | [0].action               | <Action>               |
      | [0].consumerAction       | <ConsumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil>   |
    Examples:
      | name     | programCode | subProgramTypeCode | eligibilityStatusCode | planCode  | OpenEnrollmentEffectiveDate | eligibilityEndDate       | OpenEnrollmentEndDate    | changeAllowedFrom | changeAllowedUntil       | Action      | ConsumerAction         | planSelectionAllowed |
      | 32018-16 | R           | HoosierHealthwise  | M                     | 700410350 | 1stDayofLastMonth           | future                   | 90DayFromFirstDayOfMonth | 1stDayofLastMonth | 90DayFromFirstDayOfMonth | Available   | Plan Change Pre-lockin | true                 |
      | 32018-17 | R           | HoosierHealthwise  | M                     | 700410350 | 1stDayofLastMonth           | 90DayFromFirstDayOfMonth | future                   | 1stDayofLastMonth | 90DayFromFirstDayOfMonth | Available   | Plan Change Pre-lockin | true                 |
      | 32018-18 | R           | HoosierHealthwise  | M                     | 700410350 | 3rdDayofNextMonth           | lastDayofPresentMonth    | future                   | null              | null                     | Unavailable | null                   | false                |
      | 32018-19 | A           | HoosierCareConnect | M                     | 499254630 | 1stDayofLastMonth           | future                   | 90DayFromFirstDayOfMonth | 1stDayofLastMonth | 90DayFromFirstDayOfMonth | Available   | Plan Change Pre-lockin | true                 |
      | 32018-20 | A           | HoosierCareConnect | M                     | 499254630 | 1stDayofLastMonth           | 90DayFromFirstDayOfMonth | future                   | 1stDayofLastMonth | 90DayFromFirstDayOfMonth | Available   | Plan Change Pre-lockin | true                 |
      | 32018-21 | A           | HoosierCareConnect | V                     | 499254630 | 1stDayofLastMonth           | future                   | 90DayFromFirstDayOfMonth | 1stDayofLastMonth | 90DayFromFirstDayOfMonth | Available   | Plan Change Pre-lockin | true                 |
      | 32018-22 | A           | HoosierCareConnect | V                     | 499254630 | 1stDayofLastMonth           | 90DayFromFirstDayOfMonth | future                   | 1stDayofLastMonth | 90DayFromFirstDayOfMonth | Available   | Plan Change Pre-lockin | true                 |
      | 32018-23 | A           | HoosierCareConnect | M                     | 499254630 | 3rdDayofNextMonth           | lastDayofPresentMonth    | future                   | null              | null                     | Unavailable | null                   | false                |
      | 32018-24 | A           | HoosierCareConnect | V                     | 499254630 | 3rdDayofNextMonth           | lastDayofPresentMonth    | future                   | null              | null                     | Unavailable | null                   | false                |


  @API-CP-32018 @API-CP-32018-06 @API-EE-IN @IN-EB-API-Regression @elshan
  Scenario Outline: “Plan Change Anniversary” Consumer Action for HHW and HCC
  6.0 “Plan Change Anniversary” Consumer Action for HHW and HCC
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | <eligibilityEndDate>    |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | enrollmentEndDate            | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | planCode                     | <planCode>              |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
      | genericFieldText1            | Eligible                |
      | fileSource                   | RECIPIENT               |
    And User provide other enrollment segments details
      | recordId          | 21                            |
      | consumerId        | <name>.consumerId             |
      | startDate         | <OpenEnrollmentEffectiveDate> |
      | endDate           | <OpenEnrollmentEndDate>       |
      | genericFieldText1 | C                             |
      | genericFieldDate1 | 1stDayofPresentMonth          |
      | segmentTypeCode   | OPEN_ENROLLMENT               |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | benefitStatus | Enrolled |
    #4.0 Else validation for “Enroll” Consumer Action for HHW and HCC
    Then I Verify Consumer Actions as following data
      | [0].action               | <Action>               |
      | [0].consumerAction       | <ConsumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil>   |
    Examples:
      | name     | programCode | subProgramTypeCode | eligibilityStatusCode | planCode  | OpenEnrollmentEffectiveDate | eligibilityEndDate      | OpenEnrollmentEndDate     | changeAllowedFrom      | changeAllowedUntil        | Action      | ConsumerAction          | planSelectionAllowed |
      | 32018-25 | R           | HoosierHealthwise  | M                     | 700410350 | 1stDayofLastMonth           | future                  | 90DaysFrom3rdMonthFromNow | 1stDayOf2MonthsFromNow | 90DaysFrom3rdMonthFromNow | Available   | Plan Change Anniversary | true                 |
      | 32018-26 | R           | HoosierHealthwise  | M                     | 700410350 | 1stDayofLastMonth           | lastDayOf2MonthsFromNow | 90DaysFrom3rdMonthFromNow | 1stDayOf2MonthsFromNow | lastDayOf2MonthsFromNow   | Available   | Plan Change Anniversary | true                 |
      | 32018-27 | R           | HoosierHealthwise  | M                     | 700410350 | 1stDayofLastMonth           | lastDayofPresentMonth   | 90DaysFrom3rdMonthFromNow | null                   | null                      | Unavailable | null                    | false                |
      | 32018-28 | A           | HoosierCareConnect | M                     | 499254630 | 1stDayofLastMonth           | future                  | 90DaysFrom3rdMonthFromNow | 1stDayOf2MonthsFromNow | 90DaysFrom3rdMonthFromNow | Available   | Plan Change Anniversary | true                 |
      | 32018-29 | A           | HoosierCareConnect | M                     | 499254630 | 1stDayofLastMonth           | lastDayOf2MonthsFromNow | 90DaysFrom3rdMonthFromNow | 1stDayOf2MonthsFromNow | lastDayOf2MonthsFromNow   | Available   | Plan Change Anniversary | true                 |
      | 32018-30 | A           | HoosierCareConnect | V                     | 499254630 | 1stDayofLastMonth           | future                  | 90DaysFrom3rdMonthFromNow | 1stDayOf2MonthsFromNow | 90DaysFrom3rdMonthFromNow | Available   | Plan Change Anniversary | true                 |
      | 32018-31 | A           | HoosierCareConnect | V                     | 499254630 | 1stDayofLastMonth           | lastDayOf2MonthsFromNow | 90DaysFrom3rdMonthFromNow | 1stDayOf2MonthsFromNow | lastDayOf2MonthsFromNow   | Available   | Plan Change Anniversary | true                 |
      | 32018-32 | A           | HoosierCareConnect | M                     | 499254630 | 1stDayofLastMonth           | lastDayofPresentMonth   | 90DaysFrom3rdMonthFromNow | null                   | null                      | Unavailable | null                    | false                |
      | 32018-33 | A           | HoosierCareConnect | V                     | 499254630 | 1stDayofLastMonth           | lastDayofPresentMonth   | 90DaysFrom3rdMonthFromNow | null                   | null                      | Unavailable | null                    | false                |

#rmving Regression tag plan transer issue
  @API-CP-32018  @API-EE-IN-removed @IN-EB-API-Regression @elshan
  Scenario Outline: “Free Change” Consumer Actions for HHW and HCC
  7.0 “Free Change” Consumer Actions for HHW and HCC
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | <eligibilityEndDate>    |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | enrollmentEndDate            | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramCode>        |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | planCode                     | <planCode1>             |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
      | genericFieldText1            | Eligible                |
      | fileSource                   | RECIPIENT               |
    And User provide other enrollment segments details
      | recordId          | 21                            |
      | consumerId        | <name>.consumerId             |
      | startDate         | <OpenEnrollmentEffectiveDate> |
      | endDate           | <OpenEnrollmentEndDate>       |
      | genericFieldText1 | O                             |
      | genericFieldDate1 | 1stDayofPresentMonth          |
      | segmentTypeCode   | OPEN_ENROLLMENT               |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | <name>.consumerId  |
      | [0].planId             | 26                 |
      | [0].planCode           | <planCode2>        |
      | [0].startDate          | fdnxtmth::         |
      | [0].endDate            | highDate::         |
      | [0].subProgramTypeCode | <subProgramCode>   |
      | [0].serviceRegionCode  | Statewide          |
      | [0].selectionReason    | 02                 |
      | [0].anniversaryDate    | anniversaryDate::  |
      | [0].channel            | SYSTEM_INTEGRATION |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "<name>.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "<name>a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | <name>.consumerId                |
      | [0].planId             | delete::                         |
      | [0].planCode           | <planCode1>                      |
      | [0].enrollmentId       | <name>a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED              |
      | [0].txnStatus          | DISENROLL_SUBMITTED              |
      | [0].startDate          | fdofmnth::                       |
      | [0].endDate            | lstdaymnth::                     |
      | [0].enrollmentType     | delete::                         |
      | [0].subProgramTypeCode | <subProgramCode>                 |
      | [0].serviceRegionCode  | Statewide                        |
      | [1].planId             | delete::                         |
      | [1].planCode           | <planCode2>                      |
      | [1].consumerId         | <name>.consumerId                |
      | [1].enrollmentId       | <name>a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                       |
      | [1].endDate            | highDate::                       |
      | [1].subProgramTypeCode | <subProgramCode>                 |
      | [1].serviceRegionCode  | Statewide                        |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 17                   |
      | enrollmentRecordId           | 17                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | enrollmentEndDate            | <eligibilityEndDate> |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | <subProgramCode>     |
      | planCode                     | <planCode2>          |
      | planId                       | 26                   |
      | serviceRegionCode            | Statewide            |
      | channel                      | SYSTEM_INTEGRATION   |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth            |
      | endDate           | highDate                     |
      | genericFieldText1 | <name>a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                    |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify benefit status information with data
      | benefitStatus | Enrolled |
    Then I Verify Consumer Actions as following data
      | [0].action               | <Action>               |
      | [0].consumerAction       | <ConsumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil>   |
    Examples:
      | name     | programCode | subProgramCode     | eligibilityStatusCode | planCode1 | planCode2 | OpenEnrollmentEffectiveDate | eligibilityEndDate | OpenEnrollmentEndDate     | changeAllowedFrom    | changeAllowedUntil        | Action    | ConsumerAction          | planSelectionAllowed |
      | 32018-34 | R           | HoosierHealthwise  | M                     | 700410350 | 400752220 | 1stDayofPresentMonth        | future             | 90DaysFrom3rdMonthFromNow | 1stDayofPresentMonth | 90DaysFrom3rdMonthFromNow | Available | Plan Change Free Change | true                 |
      | 32018-35 | A           | HoosierCareConnect | M                     | 499254630 | 400752220 | 1stDayofPresentMonth        | future             | 90DaysFrom3rdMonthFromNow | 1stDayofPresentMonth | 90DaysFrom3rdMonthFromNow | Available | Plan Change Free Change | true                 |
      | 32018-36 | A           | HoosierCareConnect | V                     | 499254630 | 400752220 | 1stDayofPresentMonth        | future             | 90DaysFrom3rdMonthFromNow | 1stDayofPresentMonth | 90DaysFrom3rdMonthFromNow | Available | Plan Change Free Change | true                 |
