Feature: Database connection scenarios

  @DB
  Scenario: CP-24294 IN-EB Decide "Free Change" Consumer Actions
#    Given I will get the Authentication token for "IN-EB" in "CRM"
#    Given I created a consumer with population type "IN-EB-CONSUMER" with data
#      | saveConsumerInfo | BDCons |
#    Given I initiated Eligibility and Enrollment Create API
#    And I provide the enrollment and eligibility information to create enrollment
#      | consumerId                   | BDCons.consumerId     |
#      | isEligibilityRequired        | yes                   |
#      | otherSegments                |[blank]|
#      | isEnrollemntRequired         | no                    |
#      | recordId                     | 21                    |
#      | eligibilityRecordId          | 21                    |
#      | isEnrollmentProviderRequired | no                    |
#      | enrollmentStartDate          | 1stDayof2MonthsBefore |
#      | eligibilityStartDate         | 1stDayof2MonthsBefore |
#      | eligibilityEndDate           | highDate              |
#      | txnStatus                    | Accepted              |
#      | programCode                  | R                     |
#      | subProgramTypeCode           | HoosierHealthwise     |
#      | eligibilityStatusCode        | M                     |
#      | categoryCode                 | 10                    |
#      | coverageCode                 | cc01                  |
#    And User provide other enrollment segments details
#      | recordId          | 21                    |
#      | consumerId        | BDCons.consumerId     |
#      | startDate         | 1stDayofPresentMonth  |
#      | endDate           | anniversaryDueDateHCC |
#      | genericFieldText1 | C                     |
#      | genericFieldDate1 | 1stDayofPresentMonth  |
#      | segmentTypeCode   | OPEN_ENROLLMENT       |
#    And I run create Eligibility and Enrollment API
#    And I provide the enrollment and eligibility information to create enrollment
#      | consumerId                   | BDCons.consumerId     |
#      | isEligibilityRequired        | no                    |
#      | otherSegments                |[blank]|
#      | isEnrollemntRequired         | yes                   |
#      | recordId                     | 4                     |
#      | enrollmentRecordId           | 4                     |
#      | isEnrollmentProviderRequired | no                    |
#      | eligibilityStartDate         | 1stDayof2MonthsBefore |
#      | eligibilityEndDate           | highDate              |
#      | enrollmentStartDate          | 1stDayof2MonthsBefore |
#      | enrollmentEndDate            | highDate              |
#      | txnStatus                    | Accepted              |
#      | programCode                  | R                     |
#      | subProgramTypeCode           | HoosierHealthwise     |
#      | eligibilityStatusCode        | M                     |
#      | planCode                     | 399243310             |
#      | planId                       | 33                    |
#      | serviceRegionCode            | Statewide             |
#      | anniversaryDate              | anniversaryDate       |
#      | channel                      | SYSTEM_INTEGRATION    |
#      | categoryCode                 | 10                    |
#      | coverageCode                 | cc01                  |
#    And I run create Eligibility and Enrollment API
    And I connect to Data Base
#    Then Verify DB consumer actions for consumer id "BDCons.consumerId" with data
    Then Verify DB consumer actions for consumer id "2675" with data
      | one | two |
