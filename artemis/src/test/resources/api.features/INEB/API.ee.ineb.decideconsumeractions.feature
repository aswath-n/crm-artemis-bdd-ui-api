Feature: API: Decide consumer actions feature for INEB

  @API-CP-22297 @API-CP-22297-01 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario Outline: IN-EB Decide Consumer Actions 1.0 Enroll action for HHW and HCC (positive)
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
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | genericFieldText1            | Eligible                |
      | coverageCode                 | cc01                    |
      | fileSource                   | RECIPIENT               |
    Then I send API call with name "<name>" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    And I verify benefit status records are displayed with population "<programpopulation>"
    And I verify benefit status records are displayed with benefit status "Eligible"
    Then I Verify Consumer Actions as following data
      | [0].action               | Required             |
      | [0].consumerAction       | Enroll               |
      | [0].planSelectionAllowed | true                 |
      | [0].changeAllowedFrom    | 1stDayofPresentMonth |
      | [0].changeAllowedUntil   | <changeAllowedUntil> |
    Examples:
      | name    | programCode | subProgramTypeCode | eligibilityStatusCode | programpopulation | changeAllowedUntil        |
      | 22297-1 | R           | HoosierHealthwise  | M                     | HHW-Mandatory     | 14DayFromFirstDayOfMonth  |
      | 22297-2 | R           | HoosierHealthwise  | V                     | HHW-Voluntary     | 14DayFromFirstDayOfMonth  |
      | 22297-3 | A           | HoosierCareConnect | M                     | HCC-Mandatory     | 60DaysFromFirstDayOfMonth |
      | 22297-4 | A           | HoosierCareConnect | V                     | HCC-Voluntary     | 60DaysFromFirstDayOfMonth |
      # Implementation changed with CP-29755
      | 22297-5 | R           | HoosierHealthwise  | X                     | HHW-Mandatory     | 14DayFromFirstDayOfMonth  |
      | 22297-6 | A           | HoosierCareConnect | X                     | HCC-Mandatory     | 60DaysFromFirstDayOfMonth |


  @API-CP-22297 @API-CP-22297-02 @API-CP-22297-03 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario Outline: IN-EB Decide Consumer Actions (positive)
  2.0 Determine “Plan Change Pre-lockin” Consumer Action for HHW and HCC
  3.0 Determine “Plan Change Anniversary” Consumer Actions for HHW and HCC
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
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramCode>        |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | <name>.consumerId        |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | enrollmentEndDate            | lastDayOfThePresentYear |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramCode>        |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | planCode                     | <planCode>              |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    And I verify benefit status records are displayed with population "<programpopulation>"
    And I verify benefit status records are displayed with benefit status "Enrolled"
    Then I Verify Consumer Actions as following data
    # 2.0 Determine “Plan Change Pre-lockin” Consumer Action for HHW and HCC
      | [0].action               | Available                |
      | [0].consumerAction       | Plan Change Pre-lockin   |
      | [0].planSelectionAllowed | true                     |
      | [0].changeAllowedFrom    | 1stDayofPresentMonth     |
      | [0].changeAllowedUntil   | 90DayFromFirstDayOfMonth |
    Examples:
      | name     | programCode | subProgramCode     | eligibilityStatusCode | programpopulation | planCode  |
      | 22297-7  | R           | HoosierHealthwise  | M                     | HHW-Mandatory     | 400752220 |
      | 22297-8  | R           | HoosierHealthwise  | V                     | HHW-Voluntary     | 400752220 |
      | 22297-9  | A           | HoosierCareConnect | M                     | HCC-Mandatory     | 499254630 |
      | 22297-10 | A           | HoosierCareConnect | V                     | HCC-Voluntary     | 499254630 |
      # Implementation changed with CP-29755
      | 22297-11 | R           | HoosierHealthwise  | X                     | HHW-Mandatory     | 400752220 |
      | 22297-12 | A           | HoosierCareConnect | X                     | HCC-Mandatory     | 499254630 |


  @API-CP-24924 @API-CP-30072 @API-CP-22297 @API-CP-22297-04 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario Outline: CP-22297 IN-EB Decide  “Open Enrollment” Consumer Actions for HIP (positive)
  CP-24924 IN-EB Decide Consumer Actions - Update to logic for HIP (positive)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramCode>        |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
      | genericFieldText1            | Eligible                |
    Then I send API call with name "<name>" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | enrollmentEndDate            | lastDayOfThePresentYear |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramCode>        |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | planCode                     | 155723420               |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    And I verify benefit status records are displayed with population "<programpopulation>"
    And I verify benefit status records are displayed with benefit status "Enrolled"
    Then I Verify Consumer Actions as following data
      | [0].action               | Available                   |
      | [0].consumerAction       | Plan Change Open Enrollment |
      | [0].planSelectionAllowed | true                        |
      | [0].changeAllowedFrom    | Nov1stCurrentYear           |
      | [0].changeAllowedUntil   | Dec15thCurrentYear          |
    Examples:
      | name     | programCode | subProgramCode     | eligibilityStatusCode | programpopulation |
      | 22297-13 | H           | HealthyIndianaPlan | M                     | HIP-Mandatory     |
      | 22297-14 | H           | HealthyIndianaPlan | V                     | HIP-Voluntary     |
      # Implementation changed with CP-29755
      | 22297-15 | H           | HealthyIndianaPlan | X                     | HIP-Mandatory     |


