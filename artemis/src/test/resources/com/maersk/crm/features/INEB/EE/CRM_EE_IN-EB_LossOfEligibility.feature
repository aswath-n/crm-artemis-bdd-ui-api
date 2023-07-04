Feature:  Loss Of Eligibility

  @CP-26852 @CP-26852-01 @ui-ee-in  @IN-EB-UI-Regression @kursat
  Scenario: IN-EB Display "NOT ELIGIBLE" on last day of eligibility - HHW,HCC
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 26582-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 26582-1.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof3MonthsBefore |
      | eligibilityEndDate           | current               |
      | eligibilityStatusCode        | M                     |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 26582-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 26582-2.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayof3MonthsBefore |
      | eligibilityEndDate           | current               |
      | enrollmentStartDate          | 1stDayof3MonthsBefore |
      | enrollmentEndDate            | current               |
      | eligibilityStatusCode        | M                     |
      | txnStatus                    | Accepted              |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
      | planCode                     | 300119960             |
      | anniversaryDate              | anniversaryDate       |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 26582-2.consumerId    |
      | startDate         | 1stDayofPresentMonth  |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 26582-3 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 26582-3.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof3MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | eligibilityStatusCode        | M                     |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I update consumers eligibility by consumerId "26582-3.consumerId" with data
      | [0].eligibilityStatus | Active    |
      | [0].endDate           | yesterday |
    And I update consumers benefit by consumerId "26582-3.consumerId" with data
      | [0].timeframe   | Active    |
      | [0].eligEndDate | yesterday |
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "26582-1.firstName" and Last Name as "26582-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "26582-1.firstName" and last name "26582-1.lastName" with data
      | CURRENT ELIGIBILITY.ELIGIBILITY STATUS | NOT ELIGIBLE |
      | CALENDAR ICON HOVER.ACTION TEXT        | -- --        |
      | CALENDAR ICON HOVER.IMPORTANT DATES    | -- --        |
      | COUNTDOWN.NUMBER OF DAYS UNTIL         | -- --        |
      | COUNTDOWN.ICON HOVER                   | -- --        |
      | CURRENT ENROLLMENT.ENROLL BUTTON       | hidden       |
    Then I verify program & benefit info page for consumer first name "26582-2.firstName" and last name "26582-2.lastName" with data
      | CURRENT ELIGIBILITY.ELIGIBILITY STATUS | NOT ELIGIBLE |
      | CALENDAR ICON HOVER.ACTION TEXT        | -- --        |
      | CALENDAR ICON HOVER.IMPORTANT DATES    | -- --        |
      | COUNTDOWN.NUMBER OF DAYS UNTIL         | -- --        |
      | COUNTDOWN.ICON HOVER                   | -- --        |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON  | hidden       |
    Then I verify program & benefit info page for consumer first name "26582-3.firstName" and last name "26582-3.lastName" with data
      | CURRENT ELIGIBILITY.ELIGIBILITY STATUS | hidden |
      | CALENDAR ICON HOVER.ACTION TEXT        | -- --  |
      | CALENDAR ICON HOVER.IMPORTANT DATES    | -- --  |
      | COUNTDOWN.NUMBER OF DAYS UNTIL         | -- --  |
      | COUNTDOWN.ICON HOVER                   | -- --  |
      | CURRENT ENROLLMENT.ENROLL BUTTON       | hidden |