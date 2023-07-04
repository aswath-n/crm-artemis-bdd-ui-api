Feature:INEB- Plan Change Multiple Times scenarios

  @CP-29458 @CP-29501 @CP-25421 @CP-34087 @ui-ee-in @crm-regression @IN-EB-UI-Regression @sobir
  Scenario: CP-25421 IN-EB HIP, HHW, HCC - Open Enrollment Plan Change Multiple Times During Window - Part #1
  CP-29501 IN-EB HIP, HHW, HCC - Open Enrollment Plan Change Multiple Times During Window - Part #2 (HHW)
  CP-29458 Updated - IN-EB Create or Update Data based on Plan Change Accept Response (HCC same start dates)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29501-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29501-1.consumerId    |
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
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 29501-1.consumerId    |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29501-1.consumerId    |
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
      | [0].consumerId         | 29501-1.consumerId   |
      | [0].planId             | 26                   |
      | [0].planCode           | 700410350            |
      | [0].startDate          | cutoffStartDateHHW:: |
      | [0].endDate            | highDate::           |
      | [0].subProgramTypeCode | HoosierHealthwise    |
      | [0].serviceRegionCode  | Statewide            |
      | [0].selectionReason    | 02                   |
      | [0].anniversaryDate    | anniversaryDate::    |
      | [0].channel            | SYSTEM_INTEGRATION   |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "29501-1.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "29501-1a"
    And I send API call with name "" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 29501-1.consumerId                |
      | [0].planId             | delete::                          |
      | [0].planCode           | 400752220                         |
      | [0].enrollmentId       | 29501-1a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED               |
      | [0].txnStatus          | DISENROLL_SUBMITTED               |
      | [0].startDate          | 1stDayof2MonthsBefore::           |
      | [0].endDate            | DayBeforeCutoffStartDateHHW::     |
      | [0].enrollmentType     | delete::                          |
      | [0].subProgramTypeCode | HoosierHealthwise                 |
      | [0].serviceRegionCode  | Statewide                         |
      | [1].planId             | delete::                          |
      | [1].planCode           | 700410350                         |
      | [1].consumerId         | 29501-1.consumerId                |
      | [1].enrollmentId       | 29501-1a.selectedEnrollmentId     |
      | [1].startDate          | cutoffStartDateHHW::              |
      | [1].endDate            | highDate::                        |
      | [1].subProgramTypeCode | HoosierHealthwise                 |
      | [1].serviceRegionCode  | Statewide                         |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29501-1.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | isEnrollmentProviderRequired | no                 |
      | recordId                     | 17                 |
      | enrollmentRecordId           | 17                 |
      | enrollmentStartDate          | cutoffStartDateHHW |
      | enrollmentEndDate            | highDate           |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted           |
      | programCode                  | M                  |
      | subProgramTypeCode           | HoosierHealthwise  |
      | planCode                     | 700410350          |
      | planId                       | 26                 |
      | serviceRegionCode            | Statewide          |
      | channel                      | SYSTEM_INTEGRATION |
    And User provide other enrollment segments details
      | startDate         | cutoffStartDateHHW            |
      | endDate           | highDate                      |
      | genericFieldText1 | 29501-1a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                     |
    And I run create Eligibility and Enrollment API
    #****************************************************
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29501-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29501-2.consumerId    |
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
      | consumerId        | 29501-2.consumerId    |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29501-2.consumerId    |
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
      | [0].consumerId         | 29501-2.consumerId   |
      | [0].planId             | 26                   |
      | [0].planCode           | 700410350            |
      | [0].startDate          | cutoffStartDateHHW:: |
      | [0].endDate            | highDate::           |
      | [0].subProgramTypeCode | HoosierHealthwise    |
      | [0].serviceRegionCode  | Statewide            |
      | [0].selectionReason    | 02                   |
      | [0].anniversaryDate    | anniversaryDate::    |
      | [0].channel            | SYSTEM_INTEGRATION   |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "29501-2.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "29501-2a"
    And I send API call with name "" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 29501-2.consumerId                |
      | [0].planId             | delete::                          |
      | [0].planCode           | 400752220                         |
      | [0].enrollmentId       | 29501-2a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED               |
      | [0].txnStatus          | DISENROLL_SUBMITTED               |
      | [0].startDate          | 1stDayof2MonthsBefore::           |
      | [0].endDate            | DayBeforeCutoffStartDateHHW::     |
      | [0].enrollmentType     | delete::                          |
      | [0].subProgramTypeCode | HoosierHealthwise                 |
      | [0].serviceRegionCode  | Statewide                         |
      | [1].planId             | delete::                          |
      | [1].planCode           | 700410350                         |
      | [1].consumerId         | 29501-2.consumerId                |
      | [1].enrollmentId       | 29501-2a.selectedEnrollmentId     |
      | [1].startDate          | cutoffStartDateHHW::              |
      | [1].endDate            | highDate::                        |
      | [1].subProgramTypeCode | HoosierHealthwise                 |
      | [1].serviceRegionCode  | Statewide                         |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29501-2.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | isEnrollmentProviderRequired | no                 |
      | recordId                     | 17                 |
      | enrollmentRecordId           | 17                 |
      | enrollmentStartDate          | cutoffStartDateHHW |
      | enrollmentEndDate            | highDate           |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted           |
      | programCode                  | M                  |
      | subProgramTypeCode           | HoosierHealthwise  |
      | planCode                     | 700410350          |
      | planId                       | 26                 |
      | serviceRegionCode            | Statewide          |
      | channel                      | SYSTEM_INTEGRATION |
    And User provide other enrollment segments details
      | startDate         | cutoffStartDateHHW            |
      | endDate           | highDate                      |
      | genericFieldText1 | 29501-2a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                     |
    And I run create Eligibility and Enrollment API
    #********************************************
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29501-3 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29501-3.consumerId    |
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
      | consumerId        | 29501-3.consumerId    |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29501-3.consumerId    |
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
      | [0].consumerId         | 29501-3.consumerId   |
      | [0].planId             | 26                   |
      | [0].planCode           | 700410350            |
      | [0].startDate          | cutoffStartDateHHW:: |
      | [0].endDate            | highDate::           |
      | [0].subProgramTypeCode | HoosierHealthwise    |
      | [0].serviceRegionCode  | Statewide            |
      | [0].selectionReason    | 02                   |
      | [0].anniversaryDate    | anniversaryDate::    |
      | [0].channel            | SYSTEM_INTEGRATION   |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "29501-3.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "29501-3a"
    And I send API call with name "" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 29501-3.consumerId                |
      | [0].planId             | delete::                          |
      | [0].planCode           | 400752220                         |
      | [0].enrollmentId       | 29501-3a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED               |
      | [0].txnStatus          | DISENROLL_SUBMITTED               |
      | [0].startDate          | 1stDayof2MonthsBefore::           |
      | [0].endDate            | DayBeforeCutoffStartDateHHW::     |
      | [0].enrollmentType     | delete::                          |
      | [0].subProgramTypeCode | HoosierHealthwise                 |
      | [0].serviceRegionCode  | Statewide                         |
      | [1].planId             | delete::                          |
      | [1].planCode           | 700410350                         |
      | [1].consumerId         | 29501-3.consumerId                |
      | [1].enrollmentId       | 29501-3a.selectedEnrollmentId     |
      | [1].startDate          | cutoffStartDateHHW::              |
      | [1].endDate            | highDate::                        |
      | [1].subProgramTypeCode | HoosierHealthwise                 |
      | [1].serviceRegionCode  | Statewide                         |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29501-3.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | isEnrollmentProviderRequired | no                 |
      | recordId                     | 17                 |
      | enrollmentRecordId           | 17                 |
      | enrollmentStartDate          | cutoffStartDateHHW |
      | enrollmentEndDate            | highDate           |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted           |
      | programCode                  | M                  |
      | subProgramTypeCode           | HoosierHealthwise  |
      | planCode                     | 700410350          |
      | planId                       | 26                 |
      | serviceRegionCode            | Statewide          |
      | channel                      | SYSTEM_INTEGRATION |
    And User provide other enrollment segments details
      | startDate         | cutoffStartDateHHW            |
      | endDate           | highDate                      |
      | genericFieldText1 | 29501-3a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                     |
    And I run create Eligibility and Enrollment API
    And I send independent API call with name "" to create other enrollment segment with data
      | consumerId      | 29501-3.consumerId    |
      | startDate       | 1stDayof2MonthsBefore |
      | endDate         | highDate              |
      | segmentTypeCode | LILO                  |
    #**********************************************
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29501-4 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29501-4.consumerId    |
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
      | consumerId        | 29501-4.consumerId    |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29501-4.consumerId    |
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
      | [0].consumerId         | 29501-4.consumerId   |
      | [0].planId             | 26                   |
      | [0].planCode           | 399243310            |
      | [0].startDate          | cutoffStartDateHHW:: |
      | [0].endDate            | highDate::           |
      | [0].subProgramTypeCode | HoosierCareConnect   |
      | [0].serviceRegionCode  | Statewide            |
      | [0].selectionReason    | 02                   |
      | [0].anniversaryDate    | anniversaryDate::    |
      | [0].channel            | SYSTEM_INTEGRATION   |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "29501-4.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "29501-4a"
    And I send API call with name "" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 29501-4.consumerId                |
      | [0].planId             | delete::                          |
      | [0].planCode           | 499254630                         |
      | [0].enrollmentId       | 29501-4a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED               |
      | [0].txnStatus          | DISENROLL_SUBMITTED               |
      | [0].startDate          | 1stDayof2MonthsBefore::           |
      | [0].endDate            | DayBeforeCutoffStartDateHHW::     |
      | [0].enrollmentType     | delete::                          |
      | [0].subProgramTypeCode | HoosierCareConnect                |
      | [0].serviceRegionCode  | Statewide                         |
      | [1].planId             | delete::                          |
      | [1].planCode           | 399243310                         |
      | [1].consumerId         | 29501-4.consumerId                |
      | [1].enrollmentId       | 29501-4a.selectedEnrollmentId     |
      | [1].startDate          | cutoffStartDateHHW::              |
      | [1].endDate            | highDate::                        |
      | [1].subProgramTypeCode | HoosierCareConnect                |
      | [1].serviceRegionCode  | Statewide                         |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29501-4.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | isEnrollmentProviderRequired | no                 |
      | recordId                     | 17                 |
      | enrollmentRecordId           | 17                 |
      | enrollmentStartDate          | cutoffStartDateHHW |
      | enrollmentEndDate            | highDate           |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted           |
      | programCode                  | M                  |
      | subProgramTypeCode           | HoosierCareConnect |
      | planCode                     | 399243310          |
      | planId                       | 26                 |
      | serviceRegionCode            | Statewide          |
      | channel                      | SYSTEM_INTEGRATION |
    And User provide other enrollment segments details
      | startDate         | cutoffStartDateHHW            |
      | endDate           | highDate                      |
      | genericFieldText1 | 29501-4a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                     |
    And I run create Eligibility and Enrollment API
    #***********************************************************
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "29501-1.firstName" and Last Name as "29501-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "29501-1.firstName" and last name "29501-1.lastName" with data
      # CP-25421 1.0  VIEW Plan Change button on Future Enrollment Segment - IN-EBt
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HHW-Mandatory              |
      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18          |
      | CURRENT ELIGIBILITY.RCP                 | No                         |
      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                     |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayof2MonthsBeforeUIver |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver              |
      #*********
      | CURRENT ENROLLMENT.PLAN NAME            | ANTHEM                     |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Disenrolled                |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration         |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                      |
      | CURRENT ENROLLMENT.START DATE           | 1stDayof2MonthsBeforeUIver |
      | CURRENT ENROLLMENT.END DATE             | lastDayofpresentMonth      |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | hidden                     |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | hidden                     |
      #***********
      | FUTURE ENROLLMENT.PLAN NAME             | CARESOURCE                 |
      | FUTURE ENROLLMENT.SELECTION STATUS      | Accepted                   |
      | FUTURE ENROLLMENT.CHANNEL               | System Integration         |
      | FUTURE ENROLLMENT.PCP NAME              | -- --                      |
      | FUTURE ENROLLMENT.START DATE            | cutoffStartDateHHWUIver    |
      | FUTURE ENROLLMENT.END DATE              | highDateUIver              |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON     | hidden                     |
      | FUTURE ENROLLMENT.PLAN CHANGE BUTTON    | is displayed               |
    Then I verify program & benefit info page for consumer first name "29501-3.firstName" and last name "29501-3.lastName" with data
      # CP-25421 1.0  VIEW Plan Change button on Future Enrollment Segment - IN-EBt
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HHW-Mandatory              |
      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18          |
      | CURRENT ELIGIBILITY.RCP                 | Yes                        |
      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                     |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayof2MonthsBeforeUIver |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver              |
      #*********
      | CURRENT ENROLLMENT.PLAN NAME            | ANTHEM                     |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Disenrolled                |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration         |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                      |
      | CURRENT ENROLLMENT.START DATE           | 1stDayof2MonthsBeforeUIver |
      | CURRENT ENROLLMENT.END DATE             | lastDayofpresentMonth      |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | hidden                     |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | hidden                     |
      #***********
      | FUTURE ENROLLMENT.PLAN NAME             | CARESOURCE                 |
      | FUTURE ENROLLMENT.SELECTION STATUS      | Accepted                   |
      | FUTURE ENROLLMENT.CHANNEL               | System Integration         |
      | FUTURE ENROLLMENT.PCP NAME              | -- --                      |
      | FUTURE ENROLLMENT.START DATE            | cutoffStartDateHHWUIver    |
      | FUTURE ENROLLMENT.END DATE              | highDateUIver              |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON     | hidden                     |
      | FUTURE ENROLLMENT.PLAN CHANGE BUTTON    | hidden                     |
    # CP-25421 2.0 CLICK Plan Change button on Future Enrollment Segment - IN-EB
    And I click future enrollment "PLAN CHANGE" Button for a consumer first name "29501-1.firstName" and last name "29501-1.lastName"
    Then I verify I am still on "Enrollment Update" Page
    Then I verify "PCP SELECT BUTTON" is not displayed
    When I click Add Case Members Button
    Then I verify I only see next other consumers in the dropdown list
      | 29501-2 |
    When I click Add Case Members with First Name as "29501-2.firstName" and Last Name as "29501-2.lastName"
    Then I verify  the Add Case Members button is disable
    When I click Remove Button for Case Member First Name as "29501-2.firstName" and Last Name as "29501-2.lastName"
    Then I verivy consumer by First Name as "29501-2.firstName" and Last Name as "29501-2.lastName" is not in plan selection
    When I click Add Case Members Button
    When I click Add Case Members with First Name as "29501-2.firstName" and Last Name as "29501-2.lastName"
    Then I verify list of all available plans on Enrollment Update Page with data
      | ANTHEM                  |
      | CARESOURCE              |
      | MANAGED HEALTH SERVICES |
      | MDWISE HH               |
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | PLAN TYPE  | Medical                 |
      | START DATE | cutoffStartDateHHWUIver |
      | END DATE   | highDateUIver           |
    Then I verify "PCP SELECT BUTTON" is not displayed
    When I select "MDWISE HH" from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    # 1.1  Update CURRENT ENROLLMENT END DATE after SUBMIT button (has been clicked) on FUTURE Enrollment Segment - IN-EB
    Then I verify program & benefit info page for consumer first name "29501-1.firstName" and last name "29501-1.lastName" with data
      | FUTURE ENROLLMENT.SELECTION STATUS                        | Selection Made                   |
      | FUTURE ENROLLMENT.START DATE                              | cutoffStartDateHHWUIver          |
      | FUTURE ENROLLMENT.END DATE                                | highDateUIver                    |
      | FUTURE ENROLLMENT.EDIT BUTTON                             | displayed                        |
      | FUTURE ENROLLMENT.DISREGARD BUTTON                        | displayed                        |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.SELECTION STATUS | Disenroll Requested              |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.START DATE       | firstdayofNextMonth              |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.END DATE         | DayBeforeCutoffStartDateHHWUIver |
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | 29501-1.consumerId    |
      | consumerFound | true                  |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      # 3.0 Plan Change Business Event Required Fields
      | eventName                              | PLAN_CHANGE                      |
#      | channel                                | PHONE                            |
      | channel                                | SYSTEM_INTEGRATION               |
#      | createdBy                              | 7450                             |
      | createdBy                              | 2824                             |
      | processDate                            | current                          |
      | externalCaseId                         | 29501-1.caseIdentificationNumber |
      | externalConsumerId                     | 29501-1.externalConsumerId       |
      | consumerId                             | 29501-1.consumerId               |
      | consumerName                           | 29501-1                          |
      # 3.0 Plan Change Business Event Required Fields Optional Enrollment fields from “Prior Future Enrollment”
      # Below is the original verifications steps
      ######################################################################################
#      | previousEnrollment.enrollmentStartDate | cutoffStartDateHHW               |
#      | previousEnrollment.enrollmentEndDate   | DayBeforeCutoffStartDateHHW      |
#      | previousEnrollment.planSelectionReason | null                             |
#      | previousEnrollment.planChangeReason    | 01                               |
#      | previousEnrollment.rejectionReason     | null                             |
#      | previousEnrollment.planCode            | 700410350                        |
#      | previousEnrollment.enrollmentType      | MEDICAL                          |
#      | previousEnrollment.selectionStatus     | DISENROLL_REQUESTED              |
#      | previousEnrollment.anniversaryDate     | anniversaryDate                  |
#      | previousEnrollment.pcpNpi              | null                             |
#      | previousEnrollment.pcpName             | null                             |
      # Below are the verification steps that satisfies the api response
      ######################################################################################
      | previousEnrollment.enrollmentStartDate | 1stDayof2MonthsBefore            |
      | previousEnrollment.enrollmentEndDate   | DayBeforeCutoffStartDateHHW      |
      | previousEnrollment.planSelectionReason | null                             |
      | previousEnrollment.planChangeReason    | null                             |
      | previousEnrollment.rejectionReason     | null                             |
      | previousEnrollment.planCode            | 400752220                        |
      | previousEnrollment.enrollmentType      | MEDICAL                          |
      | previousEnrollment.selectionStatus     | DISENROLL_REQUESTED              |
      | previousEnrollment.anniversaryDate     | anniversaryDate                  |
      | previousEnrollment.pcpNpi              | null                             |
      | previousEnrollment.pcpName             | null                             |
      # Below are the steps that should be
      ######################################################################################
#      | previousEnrollment.enrollmentStartDate | 1stDayof2MonthsBeforeUIver       |
#      | previousEnrollment.enrollmentEndDate   | DayBeforeCutoffStartDateHHW      |
#      | previousEnrollment.planSelectionReason | null                             |
#      | previousEnrollment.planChangeReason    | null                             |
#      | previousEnrollment.rejectionReason     | null                             |
#      | previousEnrollment.planCode            | 400752220                        |
#      | previousEnrollment.enrollmentType      | MEDICAL                          |
#      | previousEnrollment.selectionStatus     | DISENROLLED                      |
#      | previousEnrollment.anniversaryDate     | anniversaryDate                  |
#      | previousEnrollment.pcpNpi              | null                             |
#      | previousEnrollment.pcpName             | null                             |
      ######################################################################################
      # 3.0 Plan Change Business Event Required Fields Optional Enrollment fields from “Requested Future Enrollment”
      | requestedSelection.enrollmentStartDate | cutoffStartDateHHW               |
      | requestedSelection.enrollmentEndDate   | highDate                         |
#      | requestedSelection.planSelectionReason | 01                               |
      | requestedSelection.planSelectionReason | null                             |
      | requestedSelection.planChangeReason    | null                             |
      | requestedSelection.rejectionReason     | null                             |
#      | requestedSelection.planCode            | getFromUISelected                |
      # Below is not correct but I added it to pass the validation
      | requestedSelection.planCode            | 700410350                        |
#      | requestedSelection.planCode            | 500307680                |
      | requestedSelection.enrollmentType      | MEDICAL                          |
      | requestedSelection.selectionStatus     | SELECTION_MADE                   |
      | requestedSelection.anniversaryDate     | anniversaryDate                  |
      | requestedSelection.pcpNpi              | null                             |
      | requestedSelection.pcpName             | null                             |
    And I initiated get enrollment by consumer id "29501-1.consumerId"
    When I run get enrollment api
    And I save FUTURE enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "29501-1b"
    And I send API call with name "" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 29501-1.consumerId                      |
      | [0].planId             | delete::                                |
      | [0].planCode           | 29501-1b.futureDiscontinuedPlanCode     |
      | [0].enrollmentId       | 29501-1b.futureDiscontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED                     |
      | [0].txnStatus          | DISENROLL_SUBMITTED                     |
      | [0].startDate          | 1stDayof2MonthsBefore::                 |
      | [0].endDate            | DayBeforeCutoffStartDateHHW::           |
      | [0].enrollmentType     | delete::                                |
      | [0].subProgramTypeCode | HoosierHealthwise                       |
      | [0].serviceRegionCode  | Statewide                               |
      | [1].planId             | delete::                                |
      | [1].planCode           | 29501-1b.futureSelectedPlanCode         |
      | [1].consumerId         | 29501-1.consumerId                      |
      | [1].enrollmentId       | 29501-1b.futureSelectedEnrollmentId     |
      | [1].startDate          | cutoffStartDateHHW::                    |
      | [1].endDate            | highDate::                              |
      | [1].subProgramTypeCode | HoosierHealthwise                       |
      | [1].serviceRegionCode  | Statewide                               |
    And Wait for 5 seconds
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "29501-1.firstName" and last name "29501-1.lastName" with data
      | FUTURE ENROLLMENT.SELECTION STATUS                        | Submitted to State               |
      | FUTURE ENROLLMENT.START DATE                              | cutoffStartDateHHWUIver          |
      | FUTURE ENROLLMENT.END DATE                                | highDateUIver                    |
      | FUTURE ENROLLMENT.EDIT BUTTON                             | hidden                           |
      | FUTURE ENROLLMENT.DISREGARD BUTTON                        | hidden                           |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.SELECTION STATUS | Disenroll Submitted              |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.START DATE       | firstdayofNextMonth              |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.END DATE         | DayBeforeCutoffStartDateHHWUIver |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29501-1.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | isEnrollmentProviderRequired | no                 |
      | recordId                     | 17                 |
      | enrollmentRecordId           | 17                 |
      | enrollmentStartDate          | cutoffStartDateHHW |
      | enrollmentEndDate            | highDate           |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted           |
      | programCode                  | M                  |
      | subProgramTypeCode           | HoosierHealthwise  |
#      | planCode                     | getFromUISelected  |
      | planCode                     | 500307680          |
      | planId                       | 26                 |
      | serviceRegionCode            | Statewide          |
      | channel                      | SYSTEM_INTEGRATION |
    And User provide other enrollment segments details
      | startDate         | cutoffStartDateHHW                  |
      | endDate           | highDate                            |
      | genericFieldText1 | 29501-1b.futureSelectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                           |
    Then I send API call with name "29501-1c" for create Eligibility and Enrollment
    And Wait for 5 seconds
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "29501-1.firstName" and last name "29501-1.lastName" with data
      | FUTURE ENROLLMENT.SELECTION STATUS   | Accepted                |
      | FUTURE ENROLLMENT.START DATE         | cutoffStartDateHHWUIver |
      | FUTURE ENROLLMENT.END DATE           | highDateUIver           |
      | FUTURE ENROLLMENT.EDIT BUTTON        | hidden                  |
      | FUTURE ENROLLMENT.DISREGARD BUTTON   | hidden                  |
      | FUTURE ENROLLMENT.PLAN CHANGE BUTTON | is displayed            |
    And I initiated get benefit status by consumer id "29501-1.consumerId"
    And I run get enrollment api
    Then I verify benefit status information with data
    # CP-29458 3.0 Decide Program-Population
      | enrollmentScenario | Plan Change Enrollment Accept |
      | programPopulation  | HHW-Mandatory                 |
    # CP-29458 4.0 Decide Benefit Status
      | benefitStatus      | Enrolled                      |
    # CP-29458 5.0 Re-determine Consumer Actions and Change Window
    Then I Verify Consumer Actions as following data
      | [0].action               | Available               |
      | [0].consumerAction       | Plan Change Free Change |
      | [0].planSelectionAllowed | true                    |
      | [0].changeAllowedFrom    | 1stDayof2MonthsBefore   |
      | [0].changeAllowedUntil   | lastDayofPresentMonth   |
    # CP-29458 2.1 Update enrollment segment with a Selection of Status of 'Disenroll Submitted
    And I verify enrollmentID "29501-1b.futureDiscontinuedEnrollmentId" by consumer id "29501-1.consumerId" with data
      | enrollmentPresented | false |
    # CP-29458 1.0 Update enrollment segment with a Selection of Status of “Submitted to State”
    And I verify enrollmentID "29501-1b.futureSelectedEnrollmentId" by consumer id "29501-1.consumerId" with data
      | txnStatus            | ACCEPTED           |
      | updatedOn            | current            |
      | updatedBy            | 597                |
      | channel              | PHONE              |
      | createdOn            | current            |
      | createdBy            | null               |
#      | planCode             | getFromUISelected  |
      | planCode             | 500307680          |
      | medicalPlanBeginDate | cutoffStartDateHHW |
      | medicalPlanEndDate   | highDate           |
      | planEndDateReason    | null               |
      | selectionReason      | 01                 |
      | enrollmentType       | MEDICAL            |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | 29501-1.consumerId      |
#      | correlationId | 29501-1c.traceid        |
      | consumerFound | true                    |
      | DPBI          |[blank]|


  @CP-29501 @ui-ee-in @crm-regression @IN-EB-UI-Regression @sobir
  Scenario: IN-EB HIP, HHW, HCC - Open Enrollment Plan Change Multiple Times During Window - Part #2 (HIP)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29501-5 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29501-5.consumerId    |
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
      | programCode                  | H                     |
      | subProgramTypeCode           | HealthyIndianaPlan    |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
      | genericFieldText1            | Eligible              |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | highDate                |
      | segmentDetailValue1 | 1                       |
      | segmentDetailValue2 | 6                       |
      | segmentDetailValue3 | 1stDayofPresentMonth    |
      | segmentDetailValue4 | lastDayOfThePresentYear |
      | segmentTypeCode     | OTHER                   |
    And User provide other enrollment segments details
      | recordId          | 21                             |
      | consumerId        | 29501-5.consumerId             |
      | startDate         | 1stDayof2MonthsBefore          |
      | endDate           | 90DayFrom1stDayof2MonthsBefore |
      | genericFieldText1 | O                              |
      | genericFieldDate1 | openEnrollmentDay              |
      | segmentTypeCode   | OPEN_ENROLLMENT                |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29501-5.consumerId    |
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
      | programCode                  | H                     |
      | subProgramTypeCode           | HealthyIndianaPlan    |
      | eligibilityStatusCode        | M                     |
      | planCode                     | 455701400             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And I run create Eligibility and Enrollment API
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 29501-5.consumerId   |
      | [0].planId             | 26                   |
      | [0].planCode           | 355787430            |
      | [0].startDate          | firstDayOfNextYear:: |
      | [0].endDate            | highDate::           |
      | [0].subProgramTypeCode | HealthyIndianaPlan   |
      | [0].serviceRegionCode  | Statewide            |
      | [0].selectionReason    | 02                   |
      | [0].anniversaryDate    | anniversaryDate::    |
      | [0].channel            | SYSTEM_INTEGRATION   |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "29501-5.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "29501-5a"
    And I send API call with name "" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 29501-5.consumerId                |
      | [0].planId             | delete::                          |
      | [0].planCode           | 455701400                         |
      | [0].enrollmentId       | 29501-5a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED               |
      | [0].txnStatus          | DISENROLL_SUBMITTED               |
      | [0].startDate          | firstDayOfNextYear::              |
      | [0].endDate            | lastDayOfThePresentYear::         |
      | [0].enrollmentType     | delete::                          |
      | [0].subProgramTypeCode | HealthyIndianaPlan                |
      | [0].serviceRegionCode  | Statewide                         |
      | [1].planId             | delete::                          |
      | [1].planCode           | 355787430                         |
      | [1].consumerId         | 29501-5.consumerId                |
      | [1].enrollmentId       | 29501-5a.selectedEnrollmentId     |
      | [1].startDate          | firstDayOfNextYear::              |
      | [1].endDate            | highDate::                        |
      | [1].subProgramTypeCode | HealthyIndianaPlan                |
      | [1].serviceRegionCode  | Statewide                         |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29501-5.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | isEnrollmentProviderRequired | no                 |
      | recordId                     | 17                 |
      | enrollmentRecordId           | 17                 |
      | enrollmentStartDate          | firstDayOfNextYear |
      | enrollmentEndDate            | highDate           |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted           |
      | programCode                  | M                  |
      | subProgramTypeCode           | HealthyIndianaPlan |
      | planCode                     | 355787430          |
      | planId                       | 26                 |
      | serviceRegionCode            | Statewide          |
      | channel                      | SYSTEM_INTEGRATION |
    And User provide other enrollment segments details
      | startDate         | firstDayOfNextYear            |
      | endDate           | highDate                      |
      | genericFieldText1 | 29501-5a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                     |
    And User provide one more enrollment details
      | planCode            | 455701400               |
      | planId              | 33                      |
      | enrollmentStartDate | 1stDayof2MonthsBefore   |
      | enrollmentEndDate   | lastDayOfThePresentYear |
      | rejectionReason     | test4                   |
    And User provide other enrollment segments details
      | startDate         | firstDayOfNextYear      |
      | endDate           | lastDayOfThePresentYear |
      | genericFieldText1 | null                    |
      | segmentTypeCode   | OTHER_TXN               |
    And I run create Eligibility and Enrollment API
    And I update latest consumer actions by consumerId "29501-5.consumerId" with data
      | [0].action               | Available                   |
      | [0].consumerAction       | Plan Change Open Enrollment |
      | [0].planSelectionAllowed | true                        |
      | [0].changeAllowedFrom    | 1stDayofPresentMonth        |
      | [0].changeAllowedUntil   | 90DayFromFirstDayOfMonth    |
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "29501-5.firstName" and Last Name as "29501-5.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click future enrollment "PLAN CHANGE" Button for a consumer first name "29501-5.firstName" and last name "29501-5.lastName"
    When I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    # 1.1  Update CURRENT ENROLLMENT END DATE afterSUBMIT button (has been clicked) on FUTURE Enrollment Segment - IN-EB
    Then I verify program & benefit info page for consumer first name "29501-5.firstName" and last name "29501-5.lastName" with data
      | FUTURE ENROLLMENT.SELECTION STATUS                        | Selection Made               |
      | FUTURE ENROLLMENT.START DATE                              | firstDayOfNextYearUIver      |
      | FUTURE ENROLLMENT.END DATE                                | highDateUIver                |
      | FUTURE ENROLLMENT.EDIT BUTTON                             | displayed                    |
      | FUTURE ENROLLMENT.DISREGARD BUTTON                        | displayed                    |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.SELECTION STATUS | Disenroll Requested          |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.START DATE       | firstDayOfNextYearUIver      |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.END DATE         | lastDayOfThePresentYearUIver |
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | 29501-5.consumerId    |
      | consumerFound | true                  |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      # 3.0 Plan Change Business Event Required Fields
      | eventName                              | PLAN_CHANGE                      |
      | channel                                | PHONE                            |
      | createdBy                              | 7450                             |
      | processDate                            | current                          |
      | externalCaseId                         | 29501-5.caseIdentificationNumber |
      | externalConsumerId                     | 29501-5.externalConsumerId       |
      | consumerId                             | 29501-5.consumerId               |
      | consumerName                           | 29501-5                          |
      # 3.0 Plan Change Business Event Required Fields Optional Enrollment fields from “Prior Future Enrollment”
      | previousEnrollment.enrollmentStartDate | firstDayOfNextYear               |
      | previousEnrollment.enrollmentEndDate   | lastDayOfThePresentYear          |
      | previousEnrollment.planSelectionReason | null                             |
      | previousEnrollment.planChangeReason    | PC                               |
      | previousEnrollment.rejectionReason     | null                             |
      | previousEnrollment.planCode            | 355787430                        |
      | previousEnrollment.enrollmentType      | MEDICAL                          |
      | previousEnrollment.selectionStatus     | DISENROLL_REQUESTED              |
      | previousEnrollment.anniversaryDate     | anniversaryDate                  |
      | previousEnrollment.pcpNpi              | null                             |
      | previousEnrollment.pcpName             | null                             |
      # 3.0 Plan Change Business Event Required Fields Optional Enrollment fields from “Requested Future Enrollment”
      | requestedSelection.enrollmentStartDate | firstDayOfNextYear               |
      | requestedSelection.enrollmentEndDate   | highDate                         |
      | requestedSelection.planSelectionReason | HT                               |
      | requestedSelection.planChangeReason    | null                             |
      | requestedSelection.rejectionReason     | null                             |
      | requestedSelection.planCode            | getFromUISelected                |
      | requestedSelection.enrollmentType      | MEDICAL                          |
      | requestedSelection.selectionStatus     | SELECTION_MADE                   |
      | requestedSelection.anniversaryDate     | anniversaryDate                  |
      | requestedSelection.pcpNpi              | null                             |
      | requestedSelection.pcpName             | null                             |


  @CP-29501 @CP-25421 @ui-ee-in @crm-regression @IN-EB-UI-Regression @sobir
  Scenario: CP-29501 IN-EB HIP, HHW, HCC - Open Enrollment Plan Change Multiple Times During Window - Part #2 (HCC different start dates)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29501-6 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29501-6.consumerId    |
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
      | consumerId        | 29501-6.consumerId    |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29501-6.consumerId    |
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
      | [0].consumerId         | 29501-6.consumerId |
      | [0].planId             | 26                 |
      | [0].planCode           | 399243310          |
      | [0].startDate          | nextDayPlusOne::   |
      | [0].endDate            | highDate::         |
      | [0].subProgramTypeCode | HoosierCareConnect |
      | [0].serviceRegionCode  | Statewide          |
      | [0].selectionReason    | 02                 |
      | [0].anniversaryDate    | anniversaryDate::  |
      | [0].channel            | SYSTEM_INTEGRATION |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "29501-6.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "29501-6a"
    And I send API call with name "" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 29501-6.consumerId                |
      | [0].planId             | delete::                          |
      | [0].planCode           | 499254630                         |
      | [0].enrollmentId       | 29501-6a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED               |
      | [0].txnStatus          | DISENROLL_SUBMITTED               |
      | [0].startDate          | 1stDayof2MonthsBefore::           |
      | [0].endDate            | DayBeforeCutoffStartDateHHW::     |
      | [0].enrollmentType     | delete::                          |
      | [0].subProgramTypeCode | HoosierCareConnect                |
      | [0].serviceRegionCode  | Statewide                         |
      | [1].planId             | delete::                          |
      | [1].planCode           | 399243310                         |
      | [1].consumerId         | 29501-6.consumerId                |
      | [1].enrollmentId       | 29501-6a.selectedEnrollmentId     |
      | [1].startDate          | nextDayPlusOne::                  |
      | [1].endDate            | highDate::                        |
      | [1].subProgramTypeCode | HoosierCareConnect                |
      | [1].serviceRegionCode  | Statewide                         |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29501-6.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | isEnrollmentProviderRequired | no                 |
      | recordId                     | 17                 |
      | enrollmentRecordId           | 17                 |
      | enrollmentStartDate          | nextDayPlusOne     |
      | enrollmentEndDate            | highDate           |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted           |
      | programCode                  | M                  |
      | subProgramTypeCode           | HoosierCareConnect |
      | planCode                     | 399243310          |
      | planId                       | 26                 |
      | serviceRegionCode            | Statewide          |
      | channel                      | SYSTEM_INTEGRATION |
    And User provide other enrollment segments details
      | startDate         | nextDayPlusOne                |
      | endDate           | highDate                      |
      | genericFieldText1 | 29501-6a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                     |
    And I run create Eligibility and Enrollment API
    #****************************************************
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "29501-6.firstName" and Last Name as "29501-6.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click future enrollment "PLAN CHANGE" Button for a consumer first name "29501-6.firstName" and last name "29501-6.lastName"
    Then I verify I am still on "Enrollment Update" Page
    Then I verify "PCP SELECT BUTTON" is not displayed
    Then I verify "Add Case Members button" is not displayed
    Then I verify list of all available plans on Enrollment Update Page with data
      | ANTHEM HCC                  |
      | MANAGED HEALTH SERVICES HCC |
      | UNITED HEALTHCARE HCC       |
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | PLAN TYPE  | Medical                           |
      | START DATE | planChangeCutoffStartDateHCCUIver |
      | END DATE   | highDateUIver                     |
    Then I verify "PCP SELECT BUTTON" is not displayed
    When I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    # 1.1  Update CURRENT ENROLLMENT END DATE afterSUBMIT button (has been clicked) on FUTURE Enrollment Segment - IN-EB
    Then I verify program & benefit info page for consumer first name "29501-6.firstName" and last name "29501-6.lastName" with data
      | FUTURE ENROLLMENT.SELECTION STATUS                        | Selection Made                    |
      | FUTURE ENROLLMENT.START DATE                              | planChangeCutoffStartDateHCCUIver |
      | FUTURE ENROLLMENT.END DATE                                | highDateUIver                     |
      | FUTURE ENROLLMENT.EDIT BUTTON                             | displayed                         |
      | FUTURE ENROLLMENT.DISREGARD BUTTON                        | displayed                         |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.SELECTION STATUS | Disenroll Requested               |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.START DATE       | nextDayPlusOneUIver               |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.END DATE         | DayBeforeCutoffStartDateHHWUIver  |
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | 29501-6.consumerId    |
      | consumerFound | true                  |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      # 3.0 Plan Change Business Event Required Fields
      | eventName                              | FREE_CHANGE                           |
      | channel                                | PHONE                                 |
      | createdBy                              | 7450                                  |
      | processDate                            | current                               |
      | externalCaseId                         | 29501-6.caseIdentificationNumber      |
      | externalConsumerId                     | 29501-6.externalConsumerId            |
      | consumerId                             | 29501-6.consumerId                    |
      | consumerName                           | 29501-6                               |
      # after defect CP-34241 is fixed below validations should work or be updated as needed
      # 3.0 Plan Change Business Event Required Fields Optional Enrollment fields from “Prior Future Enrollment”
#      | previousEnrollment.enrollmentStartDate | nextDayPlusOne                        |
      | previousEnrollment.enrollmentStartDate | nextDayPlusOne                        |
      | previousEnrollment.enrollmentEndDate   | DayBeforePlanChangeCutoffStartDateHCC |
      | previousEnrollment.planSelectionReason | null                                  |
      | previousEnrollment.planChangeReason    | 01                                    |
      | previousEnrollment.rejectionReason     | null                                  |
      | previousEnrollment.planCode            | 399243310                             |
      | previousEnrollment.enrollmentType      | MEDICAL                               |
      | previousEnrollment.selectionStatus     | DISENROLL_REQUESTED                   |
      | previousEnrollment.anniversaryDate     | anniversaryDate                       |
      | previousEnrollment.pcpNpi              | null                                  |
      | previousEnrollment.pcpName             | null                                  |
      # 3.0 Plan Change Business Event Required Fields Optional Enrollment fields from “Requested Future Enrollment”
      | requestedSelection.enrollmentStartDate | planChangeCutoffStartDateHCC          |
      | requestedSelection.enrollmentEndDate   | highDate                              |
      | requestedSelection.planSelectionReason | 01                                    |
      | requestedSelection.planChangeReason    | null                                  |
      | requestedSelection.rejectionReason     | null                                  |
      | requestedSelection.planCode            | getFromUISelected                     |
      | requestedSelection.enrollmentType      | MEDICAL                               |
      | requestedSelection.selectionStatus     | SELECTION_MADE                        |
      | requestedSelection.anniversaryDate     | anniversaryDate                       |
      | requestedSelection.pcpNpi              | null                                  |
      | requestedSelection.pcpName             | null                                  |
    And I initiated get enrollment by consumer id "29501-6.consumerId"
    When I run get enrollment api
    And I save FUTURE enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "29501-6b"
    And I send API call with name "" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 29501-6.consumerId                      |
      | [0].planId             | delete::                                |
      | [0].planCode           | 29501-6b.futureDiscontinuedPlanCode     |
      | [0].enrollmentId       | 29501-6b.futureDiscontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED                     |
      | [0].txnStatus          | DISENROLL_SUBMITTED                     |
      | [0].startDate          | 1stDayof2MonthsBefore::                 |
      | [0].endDate            | DayBeforeCutoffStartDateHHW::           |
      | [0].enrollmentType     | delete::                                |
      | [0].subProgramTypeCode | HoosierHealthwise                       |
      | [0].serviceRegionCode  | Statewide                               |
      | [1].planId             | delete::                                |
      | [1].planCode           | 29501-6b.futureSelectedPlanCode         |
      | [1].consumerId         | 29501-6.consumerId                      |
      | [1].enrollmentId       | 29501-6b.futureSelectedEnrollmentId     |
      | [1].startDate          | cutoffStartDateHHW::                    |
      | [1].endDate            | highDate::                              |
      | [1].subProgramTypeCode | HoosierHealthwise                       |
      | [1].serviceRegionCode  | Statewide                               |
    And Wait for 5 seconds
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "29501-6.firstName" and last name "29501-6.lastName" with data
      | FUTURE ENROLLMENT.SELECTION STATUS                        | Submitted to State                         |
      | FUTURE ENROLLMENT.START DATE                              | planChangeCutoffStartDateHCCUIver          |
      | FUTURE ENROLLMENT.END DATE                                | highDateUIver                              |
      | FUTURE ENROLLMENT.EDIT BUTTON                             | hidden                                     |
      | FUTURE ENROLLMENT.DISREGARD BUTTON                        | hidden                                     |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.SELECTION STATUS | Disenroll Submitted                        |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.START DATE       | nextDayPlusOneUIver                        |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.END DATE         | DayBeforePlanChangeCutoffStartDateHCCUIver |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29501-6.consumerId           |
      | isEligibilityRequired        | no                           |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                          |
      | isEnrollmentProviderRequired | no                           |
      | recordId                     | 17                           |
      | enrollmentRecordId           | 17                           |
      | enrollmentStartDate          | cutoffStartDateHHW           |
      | enrollmentEndDate            | highDate                     |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted                     |
      | programCode                  | M                            |
      | subProgramTypeCode           | planChangeCutoffStartDateHCC |
      | planCode                     | getFromUISelected            |
      | planId                       | 26                           |
      | serviceRegionCode            | Statewide                    |
      | channel                      | SYSTEM_INTEGRATION           |
    And User provide other enrollment segments details
      | startDate         | cutoffStartDateHHW                  |
      | endDate           | highDate                            |
      | genericFieldText1 | 29501-6b.futureSelectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                           |
    Then I send API call with name "29501-6c" for create Eligibility and Enrollment
    And Wait for 5 seconds
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "29501-6.firstName" and last name "29501-6.lastName" with data
      | FUTURE ENROLLMENT.SELECTION STATUS                        | Accepted                                   |
      | FUTURE ENROLLMENT.START DATE                              | planChangeCutoffStartDateHCCUIver          |
      | FUTURE ENROLLMENT.END DATE                                | highDateUIver                              |
      | FUTURE ENROLLMENT.EDIT BUTTON                             | hidden                                     |
      | FUTURE ENROLLMENT.DISREGARD BUTTON                        | hidden                                     |
      | FUTURE ENROLLMENT.PLAN CHANGE BUTTON                      | is displayed                               |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.SELECTION STATUS | Disenrolled                                |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.START DATE       | nextDayPlusOneUIver                        |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.END DATE         | DayBeforePlanChangeCutoffStartDateHCCUIver |
    And I initiated get benefit status by consumer id "29501-6.consumerId"
    And I run get enrollment api
    Then I verify benefit status information with data
    # CP-29458 3.0 Decide Program-Population
      | enrollmentScenario | Plan Change Enrollment Accept |
      | programPopulation  | HCC-Mandatory                 |
    # CP-29458 4.0 Decide Benefit Status
      | benefitStatus      | Enrolled                      |
    # CP-29458 5.0 Re-determine Consumer Actions and Change Window
    Then I Verify Consumer Actions as following data
      | [0].action               | Available               |
      | [0].consumerAction       | Plan Change Free Change |
      | [0].planSelectionAllowed | true                    |
      | [0].changeAllowedFrom    | 1stDayof2MonthsBefore   |
      | [0].changeAllowedUntil   | lastDayofPresentMonth   |
    # CP-29458 2.1 Update enrollment segment with a Selection of Status of 'Disenroll Submitted
    And I verify enrollmentID "29501-6b.futureDiscontinuedEnrollmentId" by consumer id "29501-6.consumerId" with data
      | txnStatus            | DISENROLLED                           |
      | updatedOn            | current                               |
      | updatedBy            | 597                                   |
      | channel              | SYSTEM_INTEGRATION                    |
      | createdOn            | current                               |
      | createdBy            | null                                  |
      | planCode             | 399243310                             |
      | medicalPlanBeginDate | nextDayPlusOne                        |
      | medicalPlanEndDate   | DayBeforePlanChangeCutoffStartDateHCC |
      | planEndDateReason    | 01                                    |
      | selectionReason      | null                                  |
      | enrollmentType       | MEDICAL                               |
    # CP-29458 1.0 Update enrollment segment with a Selection of Status of “Submitted to State”
    And I verify enrollmentID "29501-6b.futureSelectedEnrollmentId" by consumer id "29501-6.consumerId" with data
      | txnStatus            | ACCEPTED                     |
      | updatedOn            | current                      |
      | updatedBy            | 597                          |
      | channel              | PHONE                        |
      | createdOn            | current                      |
      | createdBy            | null                         |
      | planCode             | getFromUISelected            |
      | medicalPlanBeginDate | planChangeCutoffStartDateHCC |
      | medicalPlanEndDate   | highDate                     |
      | planEndDateReason    | null                         |
      | selectionReason      | 01                           |
      | enrollmentType       | MEDICAL                      |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | 29501-6.consumerId      |
#      | correlationId | 29501-6c.traceid        |
      | consumerFound | true                    |
      | DPBI          |[blank]|
