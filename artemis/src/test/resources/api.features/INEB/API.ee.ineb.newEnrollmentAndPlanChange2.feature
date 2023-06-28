Feature: Decide New Enrollment and Plan Change Accept Response 2

  @API-CP-26294 @API-CP-26294-03 @API-CP-23757 @API-CP-23755 @API-CP-23755-01 @API-CP-23755-02 @API-CP-23755-03 @API-CP-23755-04 @API-CP-23755-05 @API-EE-IN @IN-EB-API-Regression @sobir @kursat
  Scenario Outline: CP-23757 IN-EB Decide Plan Change Accept Response (negative)
  CP-23755 IN-EB Create or Update Data based on Plan Change Accept Response (negative)
  CP-26294 IN-EB Updated - IN-EB Decide Plan Change Responses HCC/HHW (Record Id 17)
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
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | enrollmentEndDate            | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramCode>        |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | planCode                     | 400752220               |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | <name>.consumerId  |
      | [0].planId             | 26                 |
      | [0].planCode           | 500307680          |
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
      | [0].planCode           | 400752220                        |
      | [0].enrollmentId       | <name>a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED              |
      | [0].txnStatus          | DISENROLL_SUBMITTED              |
      | [0].startDate          | fdofmnth::                       |
      | [0].endDate            | lstdaymnth::                     |
      | [0].enrollmentType     | delete::                         |
      | [0].subProgramTypeCode | <subProgramCode>                 |
      | [0].serviceRegionCode  | Statewide                        |
      | [1].planId             | delete::                         |
      | [1].planCode           | 500307680                        |
      | [1].consumerId         | <name>.consumerId                |
      | [1].enrollmentId       | <name>a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                       |
      | [1].endDate            | highDate::                       |
      | [1].subProgramTypeCode | <subProgramCode>                 |
      | [1].serviceRegionCode  | Statewide                        |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | isEnrollmentProviderRequired | no                    |
      | recordId                     | 17                    |
      | enrollmentRecordId           | 17                    |
      | enrollmentStartDate          | <enrollmentStartDate> |
      | enrollmentEndDate            | highDate              |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted              |
      | programCode                  | MEDICAID              |
      | subProgramTypeCode           | <subProgramCode>      |
      | planCode                     | <planCode>            |
      | planId                       | 26                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth            |
      | endDate           | highDate                     |
      | genericFieldText1 | <name>a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                    |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Not Found","Not Found"
    Then I verify benefit status information with data
    # CP-23755 3.0 Decide Program-Population
      | programPopulation | <population> |
    # CP-23755 4.0 Decide Benefit Status
      | benefitStatus     | Enrolled     |
    # CP-23755 5.0 Re-determine Consumer Actions and Change Window
    Then I Verify Consumer Actions as following data
      | [0] | empty |
    # CP-23755 1.0 Update enrollment segment with a Selection of Status of “Submitted to State”
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      | txnStatus            | SUBMITTED_TO_STATE |
      | updatedOn            | current            |
      | updatedBy            | null               |
      | channel              | SYSTEM_INTEGRATION |
      | createdOn            | current            |
      | createdBy            | null               |
      | planCode             | 500307680          |
      | medicalPlanBeginDate | 1stDayofNextMonth  |
      | medicalPlanEndDate   | highDate           |
      | planEndDateReason    | null               |
      | enrollmentType       | MEDICAL            |
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | <name>.consumerId       |
      | consumerFound | true                    |
      | recordId      | null                    |
      # if recordId null -> ENROLLMENT_UPDATE scenario NOT recognized -> "Not Found"
    # CP-23755 2.0 Update enrollment segment with a Selection of Status of 'Disenroll Submitted
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | txnStatus            | DISENROLL_SUBMITTED   |
      | updatedOn            | current               |
      | updatedBy            | null                  |
      | planCode             | 400752220             |
      | medicalPlanBeginDate | 1stDayofPresentMonth  |
      | medicalPlanEndDate   | lastDayofPresentMonth |
      | selectionReason      | null                  |
      | enrollmentType       | MEDICAL               |
    Examples:
      | name    | programCode | subProgramCode     | eligibilityStatusCode | population    | planCode  | enrollmentStartDate  |
      # negative "Else" part of AC wrong planCode
      | 23755-1 | R           | HoosierHealthwise  | M                     | HHW-Mandatory | 300119960 | 1stDayofNextMonth    |
      | 23755-2 | R           | HoosierHealthwise  | V                     | HHW-Voluntary | 300119960 | 1stDayofNextMonth    |
      | 23755-3 | A           | HoosierCareConnect | M                     | HCC-Mandatory | 300119960 | 1stDayofNextMonth    |
      | 23755-4 | A           | HoosierCareConnect | V                     | HCC-Voluntary | 300119960 | 1stDayofNextMonth    |
      # negative "Else" part of AC wrong enrollmentStartDate
      | 23755-5 | R           | HoosierHealthwise  | M                     | HHW-Mandatory | 500307680 | 1stDayofPresentMonth |
      | 23755-6 | R           | HoosierHealthwise  | V                     | HHW-Voluntary | 500307680 | 1stDayofPresentMonth |
      | 23755-7 | A           | HoosierCareConnect | M                     | HCC-Mandatory | 500307680 | 1stDayofPresentMonth |
      | 23755-8 | A           | HoosierCareConnect | V                     | HCC-Voluntary | 500307680 | 1stDayofPresentMonth |

  @API-CP-26294 @API-CP-26294-03 @API-CP-23757 @API-CP-23755 @API-CP-23755-01 @API-CP-23755-02 @API-CP-23755-03 @API-CP-23755-04 @API-CP-23755-05 @API-EE-IN @IN-EB-API-Regression @sobir @kursat
  Scenario Outline: CP-23757 IN-EB Decide Plan Change Accept Response (negative) (wrong transactionid/enrollmentid)
  CP-23755 IN-EB Create or Update Data based on Plan Change Accept Response (negative)
  CP-26294 IN-EB Updated - IN-EB Decide Plan Change Responses HCC/HHW (Record Id 17)
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
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | enrollmentEndDate            | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramCode>        |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | planCode                     | 400752220               |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | <name>.consumerId  |
      | [0].planId             | 26                 |
      | [0].planCode           | 500307680          |
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
      | [0].planCode           | 400752220                        |
      | [0].enrollmentId       | <name>a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED              |
      | [0].txnStatus          | DISENROLL_SUBMITTED              |
      | [0].startDate          | fdofmnth::                       |
      | [0].endDate            | lstdaymnth::                     |
      | [0].enrollmentType     | delete::                         |
      | [0].subProgramTypeCode | <subProgramCode>                 |
      | [0].serviceRegionCode  | Statewide                        |
      | [1].planId             | delete::                         |
      | [1].planCode           | 500307680                        |
      | [1].consumerId         | <name>.consumerId                |
      | [1].enrollmentId       | <name>a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                       |
      | [1].endDate            | highDate::                       |
      | [1].subProgramTypeCode | <subProgramCode>                 |
      | [1].serviceRegionCode  | Statewide                        |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | isEnrollmentProviderRequired | no                    |
      | recordId                     | 17                    |
      | enrollmentRecordId           | 17                    |
      | enrollmentStartDate          | <enrollmentStartDate> |
      | enrollmentEndDate            | highDate              |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted              |
      | programCode                  | MEDICAID              |
      | subProgramTypeCode           | <subProgramCode>      |
      | planCode                     | <planCode>            |
      | planId                       | 26                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth |
      | endDate           | highDate          |
      | genericFieldText1 | 9999              |
      | segmentTypeCode   | OTHER_TXN         |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Not Found","Not Found"
    Then I verify benefit status information with data
    # CP-23755 3.0 Decide Program-Population
      | programPopulation | <population> |
    # CP-23755 4.0 Decide Benefit Status
      | benefitStatus     | Enrolled     |
    # CP-23755 5.0 Re-determine Consumer Actions and Change Window
    Then I Verify Consumer Actions as following data
      | [0] | empty |
    # CP-23755 1.0 Update enrollment segment with a Selection of Status of “Submitted to State”
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      | txnStatus            | SUBMITTED_TO_STATE |
      | updatedOn            | current            |
      | updatedBy            | null               |
      | channel              | SYSTEM_INTEGRATION |
      | createdOn            | current            |
      | createdBy            | null               |
      | planCode             | 500307680          |
      | medicalPlanBeginDate | 1stDayofNextMonth  |
      | medicalPlanEndDate   | highDate           |
      | planEndDateReason    | null               |
      | enrollmentType       | MEDICAL            |
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | <name>.consumerId       |
      | consumerFound | true                    |
      | recordId      | null                    |
      # if recordId null -> ENROLLMENT_UPDATE scenario NOT recognized -> "Not Found"
    # CP-23755 2.0 Update enrollment segment with a Selection of Status of 'Disenroll Submitted
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | txnStatus            | DISENROLL_SUBMITTED   |
      | updatedOn            | current               |
      | updatedBy            | null                  |
      | planCode             | 400752220             |
      | medicalPlanBeginDate | 1stDayofPresentMonth  |
      | medicalPlanEndDate   | lastDayofPresentMonth |
      | selectionReason      | null                  |
      | enrollmentType       | MEDICAL               |
    Examples:
      | name    | programCode | subProgramCode    | eligibilityStatusCode | population    | planCode  | enrollmentStartDate |
      # wrong transactionid/enrollmentid
      | 23755-9 | R           | HoosierHealthwise | M                     | HHW-Mandatory | 500307680 | 1stDayofNextMonth   |


  @API-CP-26294 @API-CP-26294-03 @API-CP-23756 @API-CP-23756-01 @API-CP-23756-02 @API-CP-23756-03 @API-EE-IN @IN-EB-API-Regression @sobir @kursat
  Scenario: IN-EB Create Business Event - Plan Change Accept Response
  CP-26294 IN-EB Updated - IN-EB Decide Plan Change Responses HCC/HHW (Record Id 17)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23756 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23756.consumerId     |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 23756.consumerId         |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23756.consumerId     |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | enrollmentRecordId           | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | planCode                     | 400752220            |
      | planId                       | 33                   |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 23756.consumerId   |
      | [0].planId             | 26                 |
      | [0].planCode           | 500307680          |
      | [0].startDate          | fdnxtmth::         |
      | [0].endDate            | highDate::         |
      | [0].subProgramTypeCode | HoosierHealthwise  |
      | [0].serviceRegionCode  | Statewide          |
      | [0].selectionReason    | 02                 |
      | [0].anniversaryDate    | anniversaryDate::  |
      | [0].channel            | SYSTEM_INTEGRATION |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "23756.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "23756a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 23756.consumerId                |
      | [0].planId             | delete::                        |
      | [0].planCode           | 400752220                       |
      | [0].enrollmentId       | 23756a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED             |
      | [0].txnStatus          | DISENROLL_SUBMITTED             |
      | [0].startDate          | fdofmnth::                      |
      | [0].endDate            | lstdaymnth::                    |
      | [0].enrollmentType     | delete::                        |
      | [0].subProgramTypeCode | HoosierHealthwise               |
      | [0].serviceRegionCode  | Statewide                       |
      | [1].planId             | delete::                        |
      | [1].planCode           | 500307680                       |
      | [1].consumerId         | 23756.consumerId                |
      | [1].enrollmentId       | 23756a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                      |
      | [1].endDate            | highDate::                      |
      | [1].subProgramTypeCode | HoosierHealthwise               |
      | [1].serviceRegionCode  | Statewide                       |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23756.consumerId   |
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
      | planCode                     | 500307680          |
      | planId                       | 26                 |
      | serviceRegionCode            | Statewide          |
      | anniversaryDate              | anniversaryDate    |
      | channel                      | SYSTEM_INTEGRATION |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth           |
      | endDate           | highDate                    |
      | genericFieldText1 | 23756a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                   |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "23756.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "NO MATCH","Plan Change Enrollment Accept"
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      # 1.0 Business Event Required Fields
      | eventName                              | PLAN_CHANGE_ACCEPT_RESPONSE    |
      | channel                                | SYSTEM_INTEGRATION             |
      | createdBy                              | 597                            |
      | processDate                            | current                        |
      | externalCaseId                         | 23756.caseIdentificationNumber |
      | externalConsumerId                     | 23756.externalConsumerId       |
      | consumerId                             | 23756.consumerId               |
      | consumerName                           | 23756                          |
      # 2.0 Business Event Optional Enrollment fields from “previous enrollment”
      | previousEnrollment.enrollmentStartDate | 1stDayofPresentMonth           |
      | previousEnrollment.enrollmentEndDate   | lastDayofPresentMonth          |
      | previousEnrollment.planChangeReason    | null                           |
      | previousEnrollment.rejectionReason     | null                           |
      | previousEnrollment.planCode            | 400752220                      |
      | previousEnrollment.enrollmentType      | MEDICAL                        |
      | previousEnrollment.selectionStatus     | DISENROLLED                    |
      | previousEnrollment.anniversaryDate     | anniversaryDate                |
      | previousEnrollment.pcpNpi              | null                           |
      | previousEnrollment.pcpName             | null                           |
      # 3.0 Business Event Optional Enrollment fields from “requested enrollment”
      | requestedSelection.enrollmentStartDate | 1stDayofNextMonth              |
      | requestedSelection.enrollmentEndDate   | highDate                       |
      | requestedSelection.planChangeReason    | null                           |
      | requestedSelection.rejectionReason     | null                           |
      | requestedSelection.planCode            | 500307680                      |
      | requestedSelection.enrollmentType      | MEDICAL                        |
      | requestedSelection.selectionStatus     | ACCEPTED                       |
      | requestedSelection.anniversaryDate     | anniversaryDate                |
      | requestedSelection.pcpNpi              | null                           |
      | requestedSelection.pcpName             | null                           |


  @API-CP-28092 @API-CP-28092-01 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario: IN-EB Updated - IN-EB Decide New Enrollment Responses (Record Id 15)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 28092-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 28092-1.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 21                 |
      | eligibilityRecordId          | 21                 |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          | 1stDayofLastMonth  |
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | eligibilityEndDate           | highDate           |
      | txnStatus                    | Accepted           |
      | programCode                  | R                  |
      | subProgramTypeCode           | HoosierHealthwise  |
      | eligibilityStatusCode        | M                  |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 28092-1.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Then I send API call with name "OU" for update Enrollment information
      | [0].consumerId         | 28092-1.consumerId |
      | [0].planId             | 26                 |
      | [0].planCode           | 400752220          |
      | [0].startDate          | fdnxtmth::         |
      | [0].endDate            | highDate::         |
      | [0].subProgramTypeCode | HooserHealthwise   |
      | [0].serviceRegionCode  | Statewide          |
      | [0].selectionReason    | 02                 |
      | [0].channel            | SYSTEM_INTEGRATION |
    And Wait for 5 seconds
    And I send API call with name "OU" for process Outbound Update
      | [0].planId             | delete::               |
      | [0].planCode           | 400752220              |
      | [0].consumerId         | 28092-1.consumerId     |
      | [0].enrollmentId       | c.data[0].enrollmentId |
      | [0].startDate          | fdnxtmth::             |
      | [0].endDate            | highDate::             |
      | [0].subProgramTypeCode | HoosierHealthwise      |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 28092-1.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | isEnrollmentProviderRequired | no                 |
      | recordId                     | 15                 |
      | enrollmentRecordId           | 15                 |
      | enrollmentStartDate          | 1stDayofNextMonth  |
      | enrollmentEndDate            | highDate           |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted           |
      | programCode                  | R                  |
      | subProgramTypeCode           | HoosierHealthwise  |
      | planCode                     | 400752220          |
      | planId                       | 26                 |
      | serviceRegionCode            | Statewide          |
      | anniversaryDate              | anniversaryDate    |
      | channel                      | SYSTEM_INTEGRATION |
    And User provide other enrollment segments details
      | consumerId        | 28092-1.consumerId     |
      | startDate         | 1stDayofNextMonth      |
      | endDate           | highDate               |
      | genericFieldText1 | c.data[0].enrollmentId |
      | segmentTypeCode   | OTHER_TXN              |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "28092-1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "NO MATCH","New Enrollment Accept"


  @API-CP-26294 @API-CP-26294-01 @API-CP-33456 @API-CP-33456-01 @API-EE-IN @IN-EB-API-Regression @kursat @shruti
  Scenario: IN-EB Updated - IN-EB Decide Plan Change Responses - HIP (positive) (Record Id 17)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 26294-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 26294-1.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | M                    |
      | genericFieldText1            | Eligible             |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 26294-1.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | C                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 26294-1.consumerId   |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | enrollmentRecordId           | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | M                    |
      | planCode                     | 455701400            |
      | planId                       | 33                   |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | rejectionReason              | test1                |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 26294-1.consumerId   |
      | [0].planId             | 199                  |
      | [0].planCode           | 355787430            |
      | [0].startDate          | firstDayOfNextYear:: |
      | [0].endDate            | highDate::           |
      | [0].planEndDateReason  | PC                   |
      | [0].programTypeCode    | MEDICAID             |
      | [0].subProgramTypeCode | HealthyIndianaPlan   |
      | [0].serviceRegionCode  | Statewide            |
      | [0].channel            | SYSTEM_INTEGRATION   |
      | [0].rejectionReason    | test2                |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "26294-1.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "26294-1a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 26294-1.consumerId                |
      | [0].planId             | delete::                          |
      | [0].planCode           | 455701400                         |
      | [0].enrollmentId       | 26294-1a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED               |
      | [0].txnStatus          | DISENROLL_SUBMITTED               |
      | [0].startDate          | fdofmnth::                        |
      | [0].endDate            | lastDayOfThePresentYear::         |
      | [0].enrollmentType     | delete::                          |
      | [0].subProgramTypeCode | HealthyIndianaPlan                |
      | [0].serviceRegionCode  | Statewide                         |
      | [1].planId             | delete::                          |
      | [1].planCode           | 355787430                         |
      | [1].consumerId         | 26294-1.consumerId                |
      | [1].enrollmentId       | 26294-1a.selectedEnrollmentId     |
      | [1].startDate          | firstDayOfNextYear::              |
      | [1].endDate            | highDate::                        |
      | [1].subProgramTypeCode | HealthyIndianaPlan                |
      | [1].serviceRegionCode  | Statewide                         |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 26294-1.consumerId |
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
      | programCode                  | H                  |
      | subProgramTypeCode           | HealthyIndianaPlan |
      | planCode                     | 355787430          |
      | planId                       | 66                 |
      | serviceRegionCode            | Statewide          |
      | anniversaryDate              | anniversaryDate    |
      | channel                      | SYSTEM_INTEGRATION |
      | rejectionReason              | test3              |
    And User provide other enrollment segments details
      | startDate         | firstDayOfNextYear            |
      | endDate           | highDate                      |
      | genericFieldText1 | 26294-1a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                     |
    And User provide one more enrollment details
      | planCode            | 455701400               |
      | planId              | 33                      |
      #Updated start date for CP-33456
      | enrollmentStartDate | 1stDayof2MonthsBefore   |
      | enrollmentEndDate   | lastDayOfThePresentYear |
      | rejectionReason     | test4                   |
    And User provide other enrollment segments details
      | startDate         | firstDayOfNextYear |
      | endDate           | highDate           |
      | genericFieldText1 | null               |
      | segmentTypeCode   | OTHER_TXN          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "26294-1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "NO MATCH","Plan Change Enrollment Accept"


  @API-CP-26294 @API-CP-26294-01 @API-CP-33456-02 @API-CP-33456 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario: IN-EB Updated - IN-EB Decide Plan Change Responses - HIP (negative) (Record Id 17)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 26294-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 26294-2.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | M                    |
      | genericFieldText1            | Eligible             |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 26294-2.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | C                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 26294-2.consumerId   |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | enrollmentRecordId           | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | M                    |
      | planCode                     | 455701400            |
      | planId                       | 33                   |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | rejectionReason              | test1                |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 26294-2.consumerId   |
      | [0].planId             | 199                  |
      | [0].planCode           | 355787430            |
      | [0].startDate          | firstDayOfNextYear:: |
      | [0].endDate            | highDate::           |
      | [0].planEndDateReason  | PC                   |
      | [0].programTypeCode    | MEDICAID             |
      | [0].subProgramTypeCode | HealthyIndianaPlan   |
      | [0].serviceRegionCode  | Statewide            |
      | [0].channel            | SYSTEM_INTEGRATION   |
      | [0].rejectionReason    | test2                |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "26294-2.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "26294-2a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 26294-2.consumerId                |
      | [0].planId             | delete::                          |
      | [0].planCode           | 455701400                         |
      | [0].enrollmentId       | 26294-2a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED               |
      | [0].txnStatus          | DISENROLL_SUBMITTED               |
      | [0].startDate          | fdofmnth::                        |
      | [0].endDate            | lastDayOfThePresentYear::         |
      | [0].enrollmentType     | delete::                          |
      | [0].subProgramTypeCode | HealthyIndianaPlan                |
      | [0].serviceRegionCode  | Statewide                         |
      | [1].planId             | delete::                          |
      | [1].planCode           | 355787430                         |
      | [1].consumerId         | 26294-2.consumerId                |
      | [1].enrollmentId       | 26294-2a.selectedEnrollmentId     |
      | [1].startDate          | firstDayOfNextYear::              |
      | [1].endDate            | highDate::                        |
      | [1].subProgramTypeCode | HealthyIndianaPlan                |
      | [1].serviceRegionCode  | Statewide                         |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 26294-2.consumerId |
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
      | programCode                  | H                  |
      | subProgramTypeCode           | HealthyIndianaPlan |
      | planCode                     | 355787430          |
      | planId                       | 66                 |
      | serviceRegionCode            | Statewide          |
      | anniversaryDate              | anniversaryDate    |
      | channel                      | SYSTEM_INTEGRATION |
      | rejectionReason              | test3              |
    And User provide other enrollment segments details
      | startDate         | firstDayOfNextYear |
      | endDate           | highDate           |
      | genericFieldText1 | 9999               |
      | segmentTypeCode   | OTHER_TXN          |
    And User provide one more enrollment details
      | planCode            | 455701400               |
      | planId              | 33                      |
      #Updated start date for CP-33456
      | enrollmentStartDate | 1stDayof2MonthsBefore   |
      | enrollmentEndDate   | lastDayOfThePresentYear |
      | rejectionReason     | test4                   |
    And User provide other enrollment segments details
      | startDate         | 1stDayofPresentMonth |
      | endDate           | highDate             |
      | genericFieldText1 | null                 |
      | segmentTypeCode   | OTHER_TXN            |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "26294-2.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Not Found","Not Found"