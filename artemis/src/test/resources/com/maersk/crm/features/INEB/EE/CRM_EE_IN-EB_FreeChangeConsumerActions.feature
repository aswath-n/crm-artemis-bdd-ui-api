Feature: UI Free Change Consumer Action Overlapping ang Non Overlapping


  @CP-23958 @CP-23957 @CP-23956 @CP-23955 @CP-34087 @CP-23958 @CP-28380 @ui-ee-in @crm-regression @IN-EB-UI-Regression @sobir
  Scenario: CP-23957 IN-EB HHW, HCC - "Free Change" Click Plan Change Button, Add Case Members, and Review Plans Available
  CP-23958 IN-EB HHW, HCC - "Free Change" View Consumer on Program and Benefit Info Screen
  CP-23956 IN-EB HHW, HCC - "Free Change" View Selection, Submit Selection, Refresh on Program & Benefit Info screen
  CP-23955 IN-EB HHW, HCC - "Free Change" Capture Business Event for Plan Change Free Change
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23957-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23957-1.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | highDate                |
      | segmentDetailValue1 | 1                       |
      | segmentDetailValue2 | 6                       |
      | segmentDetailValue3 | 1stDayofPresentMonth    |
      | segmentDetailValue4 | lastDayOfThePresentYear |
      | segmentTypeCode     | OTHER                   |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 23957-1.consumerId    |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23957-1.consumerId    |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | enrollmentRecordId           | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | planCode                     | 400752220             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And I run create Eligibility and Enrollment API
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 23957-1.consumerId |
      | [0].planId             | 26                 |
      | [0].planCode           | 700410350          |
      | [0].startDate          | fdnxtmth::         |
      | [0].endDate            | highDate::         |
      | [0].subProgramTypeCode | HoosierHealthwise  |
      | [0].serviceRegionCode  | Statewide          |
      | [0].selectionReason    | 02                 |
      | [0].anniversaryDate    | anniversaryDate::  |
      | [0].channel            | SYSTEM_INTEGRATION |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "23957-1.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "23957-1a"
    And I send API call with name "" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 23957-1.consumerId                |
      | [0].planId             | delete::                          |
      | [0].planCode           | 400752220                         |
      | [0].enrollmentId       | 23957-1a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED               |
      | [0].txnStatus          | DISENROLL_SUBMITTED               |
      | [0].startDate          | 1stDayof2MonthsBefore::           |
      | [0].endDate            | lastDayofPresentMonth::           |
      | [0].enrollmentType     | delete::                          |
      | [0].subProgramTypeCode | HoosierHealthwise                 |
      | [0].serviceRegionCode  | Statewide                         |
      | [1].planId             | delete::                          |
      | [1].planCode           | 700410350                         |
      | [1].consumerId         | 23957-1.consumerId                |
      | [1].enrollmentId       | 23957-1a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                        |
      | [1].endDate            | highDate::                        |
      | [1].subProgramTypeCode | HoosierHealthwise                 |
      | [1].serviceRegionCode  | Statewide                         |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "23957-1.consumerId"
    When I run get enrollment api
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23957-1.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | isEnrollmentProviderRequired | no                 |
      | recordId                     | 17                 |
      | enrollmentRecordId           | 17                 |
      | enrollmentStartDate          | 1stDayofNextMonth  |
      | enrollmentEndDate            | highDate           |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted           |
      | programCode                  | MEDICAID           |
      | subProgramTypeCode           | HoosierHealthwise  |
      | planCode                     | 700410350          |
      | planId                       | 26                 |
      | serviceRegionCode            | Statewide          |
      | channel                      | SYSTEM_INTEGRATION |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth             |
      | endDate           | highDate                      |
      | genericFieldText1 | 23957-1a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                     |
    And I run create Eligibility and Enrollment API
    #***********************************************************
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23957-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23957-2.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | highDate                |
      | segmentDetailValue1 | 1                       |
      | segmentDetailValue2 | 6                       |
      | segmentDetailValue3 | 1stDayofPresentMonth    |
      | segmentDetailValue4 | lastDayOfThePresentYear |
      | segmentTypeCode     | OTHER                   |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 23957-2.consumerId    |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23957-2.consumerId    |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | enrollmentRecordId           | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | planCode                     | 400752220             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And I run create Eligibility and Enrollment API
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 23957-2.consumerId |
      | [0].planId             | 26                 |
      | [0].planCode           | 700410350          |
      | [0].startDate          | fdnxtmth::         |
      | [0].endDate            | highDate::         |
      | [0].subProgramTypeCode | HoosierHealthwise  |
      | [0].serviceRegionCode  | Statewide          |
      | [0].selectionReason    | 02                 |
      | [0].anniversaryDate    | anniversaryDate::  |
      | [0].channel            | SYSTEM_INTEGRATION |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "23957-2.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "23957-2a"
    And I send API call with name "" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 23957-2.consumerId                |
      | [0].planId             | delete::                          |
      | [0].planCode           | 400752220                         |
      | [0].enrollmentId       | 23957-2a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED               |
      | [0].txnStatus          | DISENROLL_SUBMITTED               |
      | [0].startDate          | 1stDayof2MonthsBefore::           |
      | [0].endDate            | lastDayofPresentMonth::           |
      | [0].enrollmentType     | delete::                          |
      | [0].subProgramTypeCode | HoosierHealthwise                 |
      | [0].serviceRegionCode  | Statewide                         |
      | [1].planId             | delete::                          |
      | [1].planCode           | 700410350                         |
      | [1].consumerId         | 23957-2.consumerId                |
      | [1].enrollmentId       | 23957-2a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                        |
      | [1].endDate            | highDate::                        |
      | [1].subProgramTypeCode | HoosierHealthwise                 |
      | [1].serviceRegionCode  | Statewide                         |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "23957-2.consumerId"
    When I run get enrollment api
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23957-2.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | isEnrollmentProviderRequired | no                 |
      | recordId                     | 17                 |
      | enrollmentRecordId           | 17                 |
      | enrollmentStartDate          | 1stDayofNextMonth  |
      | enrollmentEndDate            | highDate           |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted           |
      | programCode                  | MEDICAID           |
      | subProgramTypeCode           | HoosierHealthwise  |
      | planCode                     | 700410350          |
      | planId                       | 26                 |
      | serviceRegionCode            | Statewide          |
      | channel                      | SYSTEM_INTEGRATION |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth             |
      | endDate           | highDate                      |
      | genericFieldText1 | 23957-2a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                     |
    And I run create Eligibility and Enrollment API
    # ***********************************************************
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23957-3 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23957-3.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | highDate                |
      | segmentDetailValue1 | 1                       |
      | segmentDetailValue2 | 6                       |
      | segmentDetailValue3 | 1stDayofPresentMonth    |
      | segmentDetailValue4 | lastDayOfThePresentYear |
      | segmentTypeCode     | OTHER                   |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 23957-3.consumerId    |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23957-3.consumerId    |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | enrollmentRecordId           | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | planCode                     | 400752220             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And I run create Eligibility and Enrollment API
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 23957-3.consumerId |
      | [0].planId             | 26                 |
      | [0].planCode           | 700410350          |
      | [0].startDate          | fdnxtmth::         |
      | [0].endDate            | highDate::         |
      | [0].subProgramTypeCode | HoosierHealthwise  |
      | [0].serviceRegionCode  | Statewide          |
      | [0].selectionReason    | 02                 |
      | [0].anniversaryDate    | anniversaryDate::  |
      | [0].channel            | SYSTEM_INTEGRATION |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "23957-3.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "23957-3a"
    And I send API call with name "" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 23957-3.consumerId                |
      | [0].planId             | delete::                          |
      | [0].planCode           | 400752220                         |
      | [0].enrollmentId       | 23957-3a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED               |
      | [0].txnStatus          | DISENROLL_SUBMITTED               |
      | [0].startDate          | 1stDayof2MonthsBefore::           |
      | [0].endDate            | lastDayofPresentMonth::           |
      | [0].enrollmentType     | delete::                          |
      | [0].subProgramTypeCode | HoosierHealthwise                 |
      | [0].serviceRegionCode  | Statewide                         |
      | [1].planId             | delete::                          |
      | [1].planCode           | 700410350                         |
      | [1].consumerId         | 23957-3.consumerId                |
      | [1].enrollmentId       | 23957-3a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                        |
      | [1].endDate            | highDate::                        |
      | [1].subProgramTypeCode | HoosierHealthwise                 |
      | [1].serviceRegionCode  | Statewide                         |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "23957-3.consumerId"
    When I run get enrollment api
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23957-3.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | isEnrollmentProviderRequired | no                 |
      | recordId                     | 17                 |
      | enrollmentRecordId           | 17                 |
      | enrollmentStartDate          | 1stDayofNextMonth  |
      | enrollmentEndDate            | highDate           |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted           |
      | programCode                  | MEDICAID           |
      | subProgramTypeCode           | HoosierHealthwise  |
      | planCode                     | 700410350          |
      | planId                       | 26                 |
      | serviceRegionCode            | Statewide          |
      | channel                      | SYSTEM_INTEGRATION |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth             |
      | endDate           | highDate                      |
      | genericFieldText1 | 23957-3a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                     |
    And I run create Eligibility and Enrollment API
    And I send independent API call with name "" to create other enrollment segment with data
      | consumerId      | 23957-3.consumerId    |
      | startDate       | 1stDayof6MonthsBefore |
      | endDate         | highDate              |
      | segmentTypeCode | LILO                  |
    # ***********************************************************
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23957-4 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23957-4.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | highDate                |
      | segmentDetailValue1 | 1                       |
      | segmentDetailValue2 | 6                       |
      | segmentDetailValue3 | 1stDayofPresentMonth    |
      | segmentDetailValue4 | lastDayOfThePresentYear |
      | segmentTypeCode     | OTHER                   |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 23957-1.consumerId    |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23957-4.consumerId    |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | enrollmentRecordId           | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | planCode                     | 400752220             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And I run create Eligibility and Enrollment API
    # ***********************************************************
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23957-5 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23957-5.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | highDate                |
      | segmentDetailValue1 | 1                       |
      | segmentDetailValue2 | 6                       |
      | segmentDetailValue3 | 1stDayofPresentMonth    |
      | segmentDetailValue4 | lastDayOfThePresentYear |
      | segmentTypeCode     | OTHER                   |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 23957-5.consumerId    |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23957-5.consumerId    |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | enrollmentRecordId           | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | eligibilityStatusCode        | M                     |
      | planCode                     | 499254630             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And I run create Eligibility and Enrollment API
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 23957-5.consumerId |
      | [0].planId             | 26                 |
      | [0].planCode           | 399243310          |
      | [0].startDate          | fdnxtmth::         |
      | [0].endDate            | highDate::         |
      | [0].subProgramTypeCode | HoosierCareConnect |
      | [0].serviceRegionCode  | Statewide          |
      | [0].selectionReason    | 02                 |
      | [0].anniversaryDate    | anniversaryDate::  |
      | [0].channel            | SYSTEM_INTEGRATION |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "23957-5.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "23957-5a"
    And I send API call with name "" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 23957-5.consumerId                |
      | [0].planId             | delete::                          |
      | [0].planCode           | 499254630                         |
      | [0].enrollmentId       | 23957-5a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED               |
      | [0].txnStatus          | DISENROLL_SUBMITTED               |
      | [0].startDate          | 1stDayof2MonthsBefore::           |
      | [0].endDate            | lastDayofPresentMonth::           |
      | [0].enrollmentType     | delete::                          |
      | [0].subProgramTypeCode | HoosierCareConnect                |
      | [0].serviceRegionCode  | Statewide                         |
      | [1].planId             | delete::                          |
      | [1].planCode           | 399243310                         |
      | [1].consumerId         | 23957-5.consumerId                |
      | [1].enrollmentId       | 23957-5a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                        |
      | [1].endDate            | highDate::                        |
      | [1].subProgramTypeCode | HoosierHealthwise                 |
      | [1].serviceRegionCode  | Statewide                         |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "23957-5.consumerId"
    When I run get enrollment api
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23957-5.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | isEnrollmentProviderRequired | no                 |
      | recordId                     | 17                 |
      | enrollmentRecordId           | 17                 |
      | enrollmentStartDate          | 1stDayofNextMonth  |
      | enrollmentEndDate            | highDate           |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted           |
      | programCode                  | MEDICAID           |
      | subProgramTypeCode           | HoosierCareConnect |
      | planCode                     | 399243310          |
      | planId                       | 26                 |
      | serviceRegionCode            | Statewide          |
      | channel                      | SYSTEM_INTEGRATION |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth             |
      | endDate           | highDate                      |
      | genericFieldText1 | 23957-5a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                     |
    And I run create Eligibility and Enrollment API
    # ***********************************************************
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "23957-1.firstName" and Last Name as "23957-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "23957-1.firstName" and last name "23957-1.lastName" with data
      # @CP-23958 1.0 Viewing the consumer info row
      | CALENDAR ICON HOVER.ACTION TEXT         | FREE CHANGE - WINDOW                               |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | 1stDayof2MonthsBeforeUIver - lastDayofpresentMonth |
     #Removed comment out below line which failed due to defect CP-33274 before
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | lastDayofpresentMonth                              |
      | COUNTDOWN.ICON HOVER                    | Calendar days left to plan Change                  |
      # @CP-23958 2.0 Viewing the current eligibility segment
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HHW-Mandatory                                      |
      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18                                  |
      | CURRENT ELIGIBILITY.RCP                 | No                                                 |
      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                                             |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayof2MonthsBeforeUIver                         |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver                                      |
      # @CP-23958 3.0 Viewing the current enrollment segment
      | CURRENT ENROLLMENT.PLAN NAME            | ANTHEM                                             |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Disenrolled                                        |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration                                 |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                                              |
      | CURRENT ENROLLMENT.START DATE           | 1stDayof2MonthsBeforeUIver                         |
      | CURRENT ENROLLMENT.END DATE             | lastDayofpresentMonth                              |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | hidden                                             |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | hidden                                             |
      # @CP-23958 3.0 Viewing the current enrollment segment
      # Implementation changed
      | FUTURE ENROLLMENT.PLAN NAME             | CARESOURCE                                         |
      | FUTURE ENROLLMENT.SELECTION STATUS      | Accepted                                           |
      | FUTURE ENROLLMENT.CHANNEL               | System Integration                                 |
      | FUTURE ENROLLMENT.PCP NAME              | -- --                                              |
      | FUTURE ENROLLMENT.START DATE            | firstdayofNextMonth                                |
      | FUTURE ENROLLMENT.END DATE              | highDateUIver                                      |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON     | hidden                                             |
      | FUTURE ENROLLMENT.PLAN CHANGE BUTTON    | is displayed                                       |
    Then I verify program & benefit info page for consumer first name "23957-3.firstName" and last name "23957-3.lastName" with data
      # @CP-23958 1.0 Viewing the consumer info row
      | CALENDAR ICON HOVER.ACTION TEXT         | FREE CHANGE - WINDOW                               |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | 1stDayof2MonthsBeforeUIver - lastDayofpresentMonth |
     #Removed comment out below line which failed due to defect CP-33274 before
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | lastDayofpresentMonth                              |
      | COUNTDOWN.ICON HOVER                    | Calendar days left to plan Change                  |
      # @CP-23958 2.0 Viewing the current eligibility segment
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HHW-Mandatory                                      |
      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18                                  |
      | CURRENT ELIGIBILITY.RCP                 | Yes                                                |
      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                                             |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayof2MonthsBeforeUIver                         |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver                                      |
      # @CP-23958 3.0 Viewing the current enrollment segment
      | CURRENT ENROLLMENT.PLAN NAME            | ANTHEM                                             |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Disenrolled                                        |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration                                 |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                                              |
      | CURRENT ENROLLMENT.START DATE           | 1stDayof2MonthsBeforeUIver                         |
      | CURRENT ENROLLMENT.END DATE             | lastDayofpresentMonth                              |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | hidden                                             |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | hidden                                             |
      # @CP-23958 3.0 Viewing the current enrollment segment
      # Implementation changed
      | FUTURE ENROLLMENT.PLAN NAME             | CARESOURCE                                         |
      | FUTURE ENROLLMENT.SELECTION STATUS      | Accepted                                           |
      | FUTURE ENROLLMENT.CHANNEL               | System Integration                                 |
      | FUTURE ENROLLMENT.PCP NAME              | -- --                                              |
      | FUTURE ENROLLMENT.START DATE            | firstdayofNextMonth                                |
      | FUTURE ENROLLMENT.END DATE              | highDateUIver                                      |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON     | hidden                                             |
      | FUTURE ENROLLMENT.PLAN CHANGE BUTTON    | hidden                                             |
    # CP-23957 1.0 Navigating to Plan Change functionality
    And I click future enrollment "PLAN CHANGE" Button for a consumer first name "23957-1.firstName" and last name "23957-1.lastName"
    Then I verify I am still on "Enrollment Update" Page
    Then I verify "PCP SELECT BUTTON" is not displayed
    # CP-23957 2.0 Adding case member(s)
    When I click Add Case Members Button
    Then I verify I only see next other consumers in the dropdown list
      | 23957-2 |
      # consumer 23957-3 is in SAME sub-program but RCP indicator is YES
      # consumer 23957-4 is not in NOT in FreeChange window
    When I click Add Case Members with First Name as "23957-2.firstName" and Last Name as "23957-2.lastName"
    Then I verify  the Add Case Members button is disable
    # CP-23957 2.1 Removing a case member from selection dropdown (regression)
    When I click Remove Button for Case Member First Name as "23957-2.firstName" and Last Name as "23957-2.lastName"
    Then I verivy consumer by First Name as "23957-2.firstName" and Last Name as "23957-2.lastName" is not in plan selection
    When I click Add Case Members Button
    When I click Add Case Members with First Name as "23957-2.firstName" and Last Name as "23957-2.lastName"
    # CP-23957 3.0 Reviewing available plans
    # There is a defect CP-34087 in plans (ADVANTAGE HEALTH SOLUTIONS INC, MDWISE CS, OPT IN DECLINED should not be displayed). Commented out until defect is fixed
    Then I verify list of all available plans on Enrollment Update Page with data
      | ANTHEM                  |
      | CARESOURCE              |
      | MANAGED HEALTH SERVICES |
      | MDWISE HH               |
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | PLAN TYPE  | Medical                           |
      | START DATE | planChangeCutoffStartDateHCCUIver |
      | END DATE   | highDateUIver                     |
    Then I verify "PCP SELECT BUTTON" is not displayed
    # CP-23956 1.0 Viewing selected Free Change option
    # CP-23956 1.1 Select plan (Regression
    When I select A plan from Available Plans
    Then I verify below details on newly created current enrollment segment on Enrollment Update Page
      | SELECTED CONSUMER NAMES | 23957-1,23957-2                            |
      | AGE/GENDER              | 20/Female                                  |
      | POPULATION              | HHW-Mandatory                              |
      | RESIDENTIAL ADDRESS     | 22 main st , Preble Indiana 46782-4345     |
      | CURRENT PLAN            | CARESOURCE                                 |
      | CURRENT PROVIDER        | -- --                                      |
      | ELIGIBILITY DETAILS     | 1stDayof2MonthsBeforeUIver - highDateUIver |
      | PLAN NAME               | selectedPlanName                           |
      | PLAN TYPE               | Medical                                    |
      | SERVICE REGION          | hidden                                     |
      | START DATE              | planChangeCutoffStartDateHCCUIver          |
      | END DATE                | highDateUIver                              |
    Then I verify "PCP SELECT BUTTON" is not displayed
    # CP-23956 1.2 Remove plan (Regression)
    And I click on Remove Plan Option
    Then I verify I am still on "Enrollment Update" Page
    When I select A plan from Available Plans
    # CP-23956 1.3 Require Reason for Free Change
    Then I verify "SUBMIT" button is not clickable
    Then I verify I am still on "ENROLLMENT UPDATE" Page
    And I click "Reason" Button for a consumer
    And I verify bellow fields are displayed on "drop down reason" section:
      | 01 - Approved Change |
    And I click "Reason" Button for a consumer
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    # CP-23956 4.0 Display Program & Benefit Info screen with saved changes after clicking “Submit” button
    Then I verify program & benefit info page for consumer first name "23957-1.firstName" and last name "23957-1.lastName" with data
      | FUTURE ENROLLMENT.SELECTION STATUS                        | Selection Made                   |
      | FUTURE ENROLLMENT.START DATE                              | cutoffStartDateHHWUIver          |
      | FUTURE ENROLLMENT.END DATE                                | highDateUIver                    |
      | FUTURE ENROLLMENT.EDIT BUTTON                             | displayed                        |
      | FUTURE ENROLLMENT.DISREGARD BUTTON                        | displayed                        |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.SELECTION STATUS | Disenroll Requested              |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.START DATE       | cutoffStartDateHHWUIver          |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.END DATE         | DayBeforeCutoffStartDateHHWUIver |
    And I initiated get enrollment by consumer id "23957-1.consumerId"
    When I run get enrollment api
    And I save FUTURE enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "23957-1b"
      # CP-23956 2.0 Update each consumer’s prior enrollment segment
    And I verify enrollmentID "23957-1b.futureDiscontinuedEnrollmentId" by consumer id "23957-1.consumerId" with data
      | medicalPlanEndDate   | lastDayofPresentMonth |
      | planEndDateReason    | 01                    |
      | updatedBy            | 7450                  |
      | updatedOn            | current               |
      | txnStatus            | DISENROLL_REQUESTED   |
      | medicalPlanBeginDate | cutoffStartDateHHW    |
      | enrollmentType       | MEDICAL               |
      | selectionReason      | null                  |
      | channel              | SYSTEM_INTEGRATION    |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | 23957-1.consumerId      |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    # CP-23956 3.0 Create each consumer’s new (requested) enrollment segment
    And I verify enrollmentID "23957-1b.futureSelectedEnrollmentId" by consumer id "23957-1.consumerId" with data
      | medicalPlanBeginDate | cutoffStartDateHHW |
      | medicalPlanEndDate   | highDate           |
      | enrollmentType       | MEDICAL            |
      | channel              | PHONE              |
      | txnStatus            | SELECTION_MADE     |
      | selectionReason      | 01                 |
      | planEndDateReason    | null               |
      | createdOn            | current            |
      | createdBy            | 7450               |
      | updatedOn            | current            |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | 23957-1.consumerId    |
      | consumerFound | true                  |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      # CP-23955 1.0 Free Change Business Event Required Fields
      | eventName                              | FREE_CHANGE                      |
      | channel                                | PHONE                            |
      | createdBy                              | 7450                             |
      | processDate                            | current                          |
      | externalCaseId                         | 23957-1.caseIdentificationNumber |
      | externalConsumerId                     | 23957-1.externalConsumerId       |
      | consumerId                             | 23957-1.consumerId               |
      | consumerName                           | 23957-1                          |
      # 2.0 Free Change Business Event Optional Enrollment fields from “Prior Enrollment”
      | previousEnrollment.enrollmentStartDate | cutoffStartDateHHW               |
      | previousEnrollment.enrollmentEndDate   | DayBeforeCutoffStartDateHHW      |
      | previousEnrollment.planSelectionReason | null                             |
      | previousEnrollment.planChangeReason    | 01                               |
      | previousEnrollment.rejectionReason     | null                             |
      | previousEnrollment.planCode            | 700410350                        |
      | previousEnrollment.enrollmentType      | MEDICAL                          |
      | previousEnrollment.selectionStatus     | DISENROLL_REQUESTED              |
      | previousEnrollment.anniversaryDate     | anniversaryDate                  |
      | previousEnrollment.pcpNpi              | null                             |
      | previousEnrollment.pcpName             | null                             |
      # CP-23955 3.0 Free Change Business Event Optional Enrollment fields from “Requested Enrollment”
      | requestedSelection.enrollmentStartDate | cutoffStartDateHHW               |
      | requestedSelection.enrollmentEndDate   | highDate                         |
      | requestedSelection.planSelectionReason | 01                               |
      | requestedSelection.planChangeReason    | null                             |
      | requestedSelection.rejectionReason     | null                             |
      | requestedSelection.planCode            | getFromUISelected                |
      | requestedSelection.enrollmentType      | MEDICAL                          |
      | requestedSelection.selectionStatus     | SELECTION_MADE                   |
      | requestedSelection.anniversaryDate     | anniversaryDate                  |
      | requestedSelection.pcpNpi              | null                             |
      | requestedSelection.pcpName             | null                             |

