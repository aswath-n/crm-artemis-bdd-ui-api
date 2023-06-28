Feature: Decide New Enrollment and Plan Change Accept Response


  @API-CP-28092 @API-CP-28092-01 @API-CP-23752 @API-CP-23751 @API-CP-23751-01 @API-CP-23751-02 @API-CP-23751-03 @API-CP-23751-04 @API-EE-IN @sobir @kursat
  Scenario Outline: CP-23752 IN-EB Decide New Enrollment Accept Response (positive)
  CP-23751 IN-EB Create or Update Data based on New Enrollment Accept Response (positive)
  CP-28092 IN-EB Updated - IN-EB Decide New Enrollment Responses (Record 15)
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
    Then I send API call with name "OU" for update Enrollment information
      | [0].consumerId         | <name>.consumerId  |
      | [0].planId             | 26                 |
      | [0].planCode           | 500307680          |
      | [0].startDate          | fdnxtmth::         |
      | [0].endDate            | highDate::         |
      | [0].subProgramTypeCode | <subProgramCode>   |
      | [0].serviceRegionCode  | Statewide          |
      | [0].selectionReason    | 02                 |
      | [0].channel            | SYSTEM_INTEGRATION |
    And Wait for 5 seconds
    And I send API call with name "OU" for process Outbound Update
      | [0].planId             | delete::               |
      | [0].planCode           | 500307680              |
      | [0].consumerId         | <name>.consumerId      |
      | [0].enrollmentId       | c.data[0].enrollmentId |
      | [0].startDate          | fdnxtmth::             |
      | [0].endDate            | highDate::             |
      | [0].subProgramTypeCode | <subProgramCode>       |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId  |
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
      | programCode                  | <programCode>      |
      | subProgramTypeCode           | <subProgramCode>   |
      | planCode                     | 500307680          |
      | planId                       | 26                 |
      | serviceRegionCode            | Statewide          |
      | anniversaryDate              | anniversaryDate    |
      | channel                      | SYSTEM_INTEGRATION |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth      |
      | endDate           | highDate               |
      | genericFieldText1 | c.data[0].enrollmentId |
      | segmentTypeCode   | OTHER_TXN              |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "NO MATCH","New Enrollment Accept"
    Then I verify benefit status information with data
    # CP-23751 2.0 Decide Program-Population
      | programPopulation | <population> |
    # CP-23751 3.0 Decide Benefit Status
      | benefitStatus     | Enrolled     |
    # CP-23751 4.0 Re-determine Consumer Actions and Change Window
    Then I Verify Consumer Actions as following data
      | [0].action               | Available              |
      | [0].consumerAction       | Plan Change Pre-lockin |
      | [0].planSelectionAllowed | true                   |
#    And I verify enrollment by consumer id "<name>.consumerId" with data
#      | medicalPlanBeginDate | 1stDayofNextMonth  |
#      | medicalPlanEndDate   | highDate           |
##      | medicalPlanEndDate   | lastDayOfThePresentYear |
#      | createdBy            | 2893               |
#      | createdOn            | current            |
#      | planCode             | 500307680          |
#      | enrollmentType       | MEDICAL            |
#      | channel              | SYSTEM_INTEGRATION |
#    And Wait for 20 seconds
#    Then I will verify business events are generated with data
#      | eventName     | ENROLLMENT_UPDATE_EVENT |
#      | consumerId    | <name>.consumerId       |
#      | consumerFound | true                    |
#      | recordId      | 15                      |
      # if recordId = 15 -> ENROLLMENT_UPDATE scenario recognized -> "New Enrollment Accept"
    Examples:
      | name    | programCode | subProgramCode     | eligibilityStatusCode | population    |
      | 23752-1 | R           | HoosierHealthwise  | M                     | HHW-Mandatory |
      | 23752-2 | R           | HoosierHealthwise  | V                     | HHW-Voluntary |
      | 23752-3 | A           | HoosierCareConnect | M                     | HCC-Mandatory |
      | 23752-4 | A           | HoosierCareConnect | V                     | HCC-Voluntary |


  @API-CP-28092 @API-CP-28092-01 @API-CP-23752 @API-CP-23751 @API-CP-23751-01 @API-CP-23751-02 @API-CP-23751-03 @API-CP-23751-04 @API-EE-IN @IN-EB-API-Regression @sobir @kursat
  Scenario Outline: CP-23752 IN-EB Decide New Enrollment Accept Response (negative)
  CP-23751 IN-EB Create or Update Data based on New Enrollment Accept Response (negative)
  CP-28092 IN-EB Updated - IN-EB Decide New Enrollment Responses (Record 15)
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
    Then I send API call with name "OU" for update Enrollment information
      | [0].consumerId         | <name>.consumerId  |
      | [0].planId             | 26                 |
      | [0].planCode           | 500307680          |
      | [0].startDate          | fdnxtmth::         |
      | [0].endDate            | highDate::         |
      | [0].subProgramTypeCode | <subProgramCode>   |
      | [0].serviceRegionCode  | Statewide          |
      | [0].selectionReason    | 02                 |
      | [0].channel            | SYSTEM_INTEGRATION |
    And Wait for 5 seconds
    And I send API call with name "OU" for process Outbound Update
      | [0].planId             | delete::               |
      | [0].planCode           | 500307680              |
      | [0].consumerId         | <name>.consumerId      |
      | [0].enrollmentId       | c.data[0].enrollmentId |
      | [0].startDate          | fdnxtmth::             |
      | [0].endDate            | highDate::             |
      | [0].subProgramTypeCode | <subProgramCode>       |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | isEnrollmentProviderRequired | no                    |
      | recordId                     | 15                    |
      | enrollmentRecordId           | 15                    |
      | enrollmentStartDate          | <enrollmentStartDate> |
      | enrollmentEndDate            | highDate              |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted              |
      | programCode                  | MEDICAID              |
      | subProgramTypeCode           | <subProgramCode>      |
      | planId                       | 26                    |
      | planCode                     | <planCode>            |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth |
      | endDate           | highDate          |
      | genericFieldText1 | <enrollmentId>    |
      | segmentTypeCode   | OTHER_TXN         |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Not Found","Not Found"
    Then I verify benefit status information with data
    # CP-23751 2.0 Decide Program-Population
      | programPopulation | <population> |
    # CP-23751 3.0 Decide Benefit Status
      | benefitStatus     | Eligible     |
    # CP-23751 4.0 Re-determine Consumer Actions and Change Window
    Then I Verify Consumer Actions as following data
      | [0] | empty |
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate | 1stDayofNextMonth  |
      | medicalPlanEndDate   | highDate           |
      | createdBy            | 2893               |
      | createdOn            | current            |
      | planCode             | 500307680          |
      | enrollmentType       | MEDICAL            |
      | channel              | SYSTEM_INTEGRATION |
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | <name>.consumerId       |
      | consumerFound | true                    |
      | recordId      | null                    |
      # if recordId = null -> ENROLLMENT_UPDATE scenario NOT recognized -> "Not Found"
    Examples:
      | name     | programCode | subProgramCode     | eligibilityStatusCode | population    | planCode  | enrollmentStartDate  | enrollmentId           |
      # negative "Else" part of AC wrong planCode
      | 23752-5  | R           | HoosierHealthwise  | M                     | HHW-Mandatory | 300119960 | 1stDayofNextMonth    | c.data[0].enrollmentId |
      | 23752-6  | R           | HoosierHealthwise  | V                     | HHW-Voluntary | 300119960 | 1stDayofNextMonth    | c.data[0].enrollmentId |
      | 23752-7  | A           | HoosierCareConnect | M                     | HCC-Mandatory | 300119960 | 1stDayofNextMonth    | c.data[0].enrollmentId |
      | 23752-8  | A           | HoosierCareConnect | V                     | HCC-Voluntary | 300119960 | 1stDayofNextMonth    | c.data[0].enrollmentId |
      # negative "Else" part of AC wrong enrollmentStartDate
      | 23752-9  | R           | HoosierHealthwise  | M                     | HHW-Mandatory | 500307680 | 1stDayofPresentMonth | c.data[0].enrollmentId |
      | 23752-10 | R           | HoosierHealthwise  | V                     | HHW-Voluntary | 500307680 | 1stDayofPresentMonth | c.data[0].enrollmentId |
      | 23752-11 | A           | HoosierCareConnect | M                     | HCC-Mandatory | 500307680 | 1stDayofPresentMonth | c.data[0].enrollmentId |
      | 23752-12 | A           | HoosierCareConnect | V                     | HCC-Voluntary | 500307680 | 1stDayofPresentMonth | c.data[0].enrollmentId |
      # negative "Else" part of AC wrong enrollmentId  # added by @kursat below line
      | 23752-13 | R           | HoosierHealthwise  | M                     | HHW-Mandatory | 500307680 | 1stDayofNextMonth    | 9999                   |


  @API-CP-28092 @API-CP-28092-01 @API-CP-23750 @API-CP-23750-01 @API-CP-23750-02 @API-CP-23750-03 @API-EE-IN @IN-EB-API-Regression @sobir @kursat
  Scenario: IN-EB Create Business Event - New Enrollment Accept Response
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23750 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | eligibilityRecordId          | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
    Then I send API call with name "23750" for create Eligibility and Enrollment
    And Wait for 5 seconds
    Then I send API call with name "OU" for update Enrollment information
      | [0].consumerId         | 23750.consumerId   |
      | [0].planId             | 26                 |
      | [0].planCode           | 300119960          |
      | [0].startDate          | fdnxtmth::         |
      | [0].endDate            | highDate::         |
      | [0].subProgramTypeCode | <subProgramCode>   |
      | [0].serviceRegionCode  | Statewide          |
      | [0].selectionReason    | 02                 |
      | [0].channel            | SYSTEM_INTEGRATION |
    And Wait for 5 seconds
    And I send API call with name "OU" for process Outbound Update
      | [0].planId             | delete::               |
      | [0].planCode           | 300119960              |
      | [0].consumerId         | 23750.consumerId       |
      | [0].enrollmentId       | c.data[0].enrollmentId |
      | [0].startDate          | fdnxtmth::             |
      | [0].endDate            | highDate::             |
      | [0].subProgramTypeCode | HoosierHealthwise      |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23750.consumerId   |
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
      | programCode                  | MEDICAID           |
      | subProgramTypeCode           | HoosierHealthwise  |
      | planCode                     | 300119960          |
      | planId                       | 26                 |
      | serviceRegionCode            | Statewide          |
      | anniversaryDate              | anniversaryDate    |
      | channel                      | SYSTEM_INTEGRATION |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth      |
      | endDate           | highDate               |
      | genericFieldText1 | c.data[0].enrollmentId |
      | segmentTypeCode   | OTHER_TXN              |
    Then I send API call with name "23750a" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "23750.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","New Enrollment Accept"
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      #  @CP-23750-01
      | eventName           | NEW_ENROLLMENT_ACCEPT_RESPONSE |
#      | correlationId       | 23750a.traceid                 |
      | channel             | SYSTEM_INTEGRATION             |
      | createdBy           | 597                            |
      | processDate         | current                        |
      | externalCaseId      | 23750.caseIdentificationNumber |
      | externalConsumerId  | 23750.externalConsumerId       |
      | consumerId          | 23750.consumerId               |
      | consumerName        | 23750                          |
      #  @CP-23750-02
      | enrollmentStartDate | 1stDayofNextMonth              |
      | enrollmentEndDate   | highDate                       |
      | planChangeReason    | null                           |
      | planCode            | 300119960                      |
      | enrollmentType      | MEDICAL                        |
      | selectionStatus     | ACCEPTED                       |
      | anniversaryDate     | anniversaryDate                |
      | pcpNpi              | null                           |
      | pcpName             | null                           |


  @API-CP-26294 @API-CP-26294-03 @API-CP-23757 @API-CP-23755 @API-CP-23755-01 @API-CP-23755-02 @API-CP-23755-03 @API-CP-23755-04 @API-CP-23755-05 @API-EE-IN @IN-EB-API-Regression @sobir @kursat
  Scenario Outline: CP-23757 IN-EB Decide Plan Change Accept Response (positive)
  CP-23755 IN-EB Create or Update Data based on Plan Change Accept Response (positive)
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
      | planCode                     | <planCode1>             |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
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
      | consumerId                   | <name>.consumerId  |
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
      | subProgramTypeCode           | <subProgramCode>   |
      | planCode                     | <planCode2>        |
      | planId                       | 26                 |
      | serviceRegionCode            | Statewide          |
      | anniversaryDate              | anniversaryDate    |
      | channel                      | SYSTEM_INTEGRATION |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth            |
      | endDate           | highDate                     |
      | genericFieldText1 | <name>a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                    |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "NO MATCH","Plan Change Enrollment Accept"
    Then I verify benefit status information with data
    # CP-23755 3.0 Decide Program-Population
      | programPopulation | <population> |
    # CP-23755 4.0 Decide Benefit Status
      | benefitStatus     | Enrolled     |
    # CP-23755 5.0 Re-determine Consumer Actions and Change Window
    Then I Verify Consumer Actions as following data
      | [0].action               | Available               |
      | [0].consumerAction       | Plan Change Free Change |
      | [0].planSelectionAllowed | true                    |
    # CP-23755 1.0 Update enrollment segment with a Selection of Status of “Submitted to State”
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      | txnStatus            | ACCEPTED           |
      | updatedOn            | current            |
      | updatedBy            | 597                |
      | channel              | SYSTEM_INTEGRATION |
      | createdOn            | current            |
      | createdBy            | null               |
      | planCode             | <planCode2>        |
      | medicalPlanBeginDate | 1stDayofNextMonth  |
      | medicalPlanEndDate   | highDate           |
      | planEndDateReason    | null               |
      | enrollmentType       | MEDICAL            |
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | <name>.consumerId       |
      | consumerFound | true                    |
      | recordId      | 17                      |
      # if recordId = 17 -> ENROLLMENT_UPDATE scenario recognized -> "Plan Change Accept"
    # CP-23755 2.0 Update enrollment segment with a Selection of Status of 'Disenroll Submitted
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | txnStatus            | DISENROLLED           |
      | updatedOn            | current               |
      | updatedBy            | 597                   |
      | planCode             | <planCode1>           |
      | medicalPlanBeginDate | 1stDayofPresentMonth  |
      | medicalPlanEndDate   | lastDayofPresentMonth |
      | selectionReason      | null                  |
      | enrollmentType       | MEDICAL               |
    Examples:
      | name    | programCode | subProgramCode     | eligibilityStatusCode | population    | planCode1 | planCode2 |
      | 23757-1 | R           | HoosierHealthwise  | M                     | HHW-Mandatory | 400752220 | 500307680 |
      | 23757-2 | A           | HoosierCareConnect | M                     | HCC-Mandatory | 499254630 | 399243310 |
      | 23757-3 | A           | HoosierCareConnect | V                     | HCC-Voluntary | 499254630 | 399243310 |

  @API-CP-26294 @API-CP-26294-03 @API-CP-23757 @API-CP-23755 @API-CP-23755-01 @API-CP-23755-02 @API-CP-23755-03 @API-CP-23755-04 @API-CP-23755-05 @API-EE-IN @IN-EB-API-Regression @sobir @kursat
  Scenario Outline: CP-23757 IN-EB Decide Plan Change Accept Response HHW-V (positive)
  CP-23755 IN-EB Create or Update Data based on Plan Change Accept Response (positive)
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
      | consumerId                   | <name>.consumerId  |
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
      | subProgramTypeCode           | <subProgramCode>   |
      | planCode                     | 500307680          |
      | planId                       | 26                 |
      | serviceRegionCode            | Statewide          |
      | anniversaryDate              | anniversaryDate    |
      | channel                      | SYSTEM_INTEGRATION |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth            |
      | endDate           | highDate                     |
      | genericFieldText1 | <name>a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                    |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "NO MATCH","Plan Change Enrollment Accept"
    Then I verify benefit status information with data
    # CP-23755 3.0 Decide Program-Population
      | programPopulation | <population> |
    # CP-23755 4.0 Decide Benefit Status
      | benefitStatus     | Enrolled     |
    # CP-23755 5.0 Re-determine Consumer Actions and Change Window
    Then I Verify Consumer Actions as following data
      | [0].action               | Available              |
      | [0].consumerAction       | Plan Change Pre-lockin |
      | [0].planSelectionAllowed | true                   |
    # CP-23755 1.0 Update enrollment segment with a Selection of Status of “Submitted to State”
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      | txnStatus            | ACCEPTED           |
      | updatedOn            | current            |
      | updatedBy            | 597                |
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
      | recordId      | 17                      |
      # if recordId = 17 -> ENROLLMENT_UPDATE scenario recognized -> "Plan Change Accept"
    # CP-23755 2.0 Update enrollment segment with a Selection of Status of 'Disenroll Submitted
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | txnStatus            | DISENROLLED           |
      | updatedOn            | current               |
      | updatedBy            | 597                   |
      | planCode             | 400752220             |
      | medicalPlanBeginDate | 1stDayofPresentMonth  |
      | medicalPlanEndDate   | lastDayofPresentMonth |
      | selectionReason      | null                  |
      | enrollmentType       | MEDICAL               |
    Examples:
      | name    | programCode | subProgramCode    | eligibilityStatusCode | population    |
      | 23757-4 | R           | HoosierHealthwise | V                     | HHW-Voluntary |


  @API-CP-36780 @API-CP-36780-01 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: CP-36780 IN-EB Decide Plan Change Accept Response -  When Assignment response file is processed
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
      | enrollmentStartDate          | past                    |
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
      | recordId                     | 21                      |
      | enrollmentRecordId           | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | enrollmentStartDate          | past                    |
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
    And I run create Eligibility and Enrollment API
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
      | consumerId                   | <name>.consumerId  |
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
      | subProgramTypeCode           | <subProgramCode>   |
      | planCode                     | <planCode2>        |
      | planId                       | 26                 |
      | serviceRegionCode            | Statewide          |
      | anniversaryDate              | anniversaryDate    |
      | channel                      | SYSTEM_INTEGRATION |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth            |
      | endDate           | highDate                     |
      | genericFieldText1 | <name>a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                    |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "NO MATCH","Plan Change Enrollment Accept"
    Then I verify benefit status information with data
    # CP-23755 3.0 Decide Program-Population
      | programPopulation | <population> |
    # CP-23755 4.0 Decide Benefit Status
      | benefitStatus     | Enrolled     |
    # CP-23755 5.0 Re-determine Consumer Actions and Change Window
    Then I Verify Consumer Actions as following data
      | [0].action               | Available               |
      | [0].consumerAction       | Plan Change Free Change |
      | [0].planSelectionAllowed | true                    |
    # CP-23755 1.0 Update enrollment segment with a Selection of Status of “Submitted to State”
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      | txnStatus            | ACCEPTED           |
      | updatedOn            | current            |
      | updatedBy            | 597                |
      | channel              | SYSTEM_INTEGRATION |
      | createdOn            | current            |
      | createdBy            | null               |
      | planCode             | <planCode2>        |
      | medicalPlanBeginDate | 1stDayofNextMonth  |
      | medicalPlanEndDate   | highDate           |
      | planEndDateReason    | null               |
      | enrollmentType       | MEDICAL            |
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | <name>.consumerId       |
      | consumerFound | true                    |
      | recordId      | 17                      |
      # if recordId = 17 -> ENROLLMENT_UPDATE scenario recognized -> "Plan Change Accept"
    # CP-23755 2.0 Update enrollment segment with a Selection of Status of 'Disenroll Submitted
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | txnStatus            | DISENROLLED           |
      | updatedOn            | current               |
      | updatedBy            | 597                   |
      | planCode             | <planCode1>           |
      | medicalPlanBeginDate | past                  |
      | medicalPlanEndDate   | lastDayofPresentMonth |
      | selectionReason      | null                  |
      | enrollmentType       | MEDICAL               |
    Examples:
      | name    | programCode | subProgramCode     | eligibilityStatusCode | population    | planCode1 | planCode2 |
      | 36780-1 | R           | HoosierHealthwise  | M                     | HHW-Mandatory | 500307680 | 300119960 |
      | 36780-2 | A           | HoosierCareConnect | V                     | HCC-Voluntary | 499254630 | 399243310 |





