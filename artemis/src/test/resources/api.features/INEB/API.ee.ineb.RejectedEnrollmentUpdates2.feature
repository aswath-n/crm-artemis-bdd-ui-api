Feature: IN-EB Rejected Enrollment updates 2

  @API-CP-26294 @API-CP-26294-02 @API-CP-23754 @API-CP-23754-01 @API-CP-23753 @API-CP-23758 @API-EE-IN @IN-EB-API-Regression @sobir @kursat
  Scenario Outline: IN-EB: Decide Plan Change Reject Response - HIP (negative)
  CP-23753 IN-EB Create or Update Data based on Plan Change Reject Response
  CP-23758 IN-EB Create Business Event - Plan Change Reject Response
  CP-26294 IN-EB Updated - IN-EB Decide Plan Change Responses HIP (Record Id 18)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
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
      | enrollmentEndDate            | lastDayOfThePresentYear |
      | txnStatus                    | Accepted                |
      | programCode                  | H                       |
      | subProgramTypeCode           | HealthyIndianaPlan      |
      | eligibilityStatusCode        | M                       |
      | planCode                     | 455701400               |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | rejectionReason              | test1                   |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | <name>.consumerId    |
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
    And I initiated get enrollment by consumer id "<name>.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "<name>b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | <name>.consumerId                |
      | [0].planId             | delete::                         |
      | [0].planCode           | 455701400                        |
      | [0].enrollmentId       | <name>b.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED              |
      | [0].txnStatus          | DISENROLL_SUBMITTED              |
      | [0].startDate          | fdofmnth::                       |
      | [0].endDate            | lastDayOfThePresentYear::        |
      | [0].enrollmentType     | delete::                         |
      | [0].subProgramTypeCode | HealthyIndianaPlan               |
      | [0].serviceRegionCode  | Statewide                        |
      | [1].planId             | delete::                         |
      | [1].planCode           | 355787430                        |
      | [1].consumerId         | <name>.consumerId                |
      | [1].enrollmentId       | <name>b.selectedEnrollmentId     |
      | [1].startDate          | firstDayOfNextYear::             |
      | [1].endDate            | highDate::                       |
      | [1].subProgramTypeCode | HealthyIndianaPlan               |
      | [1].serviceRegionCode  | Statewide                        |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId   |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | isEnrollmentProviderRequired | no                  |
      | recordId                     | 18                  |
      | enrollmentRecordId           | 18                  |
      | enrollmentStartDate          | <reqestedStartDate> |
      | enrollmentEndDate            | highDate            |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted            |
      | programCode                  | H                   |
      | subProgramTypeCode           | HealthyIndianaPlan  |
      | planCode                     | <requestedPlanCode> |
      | planId                       | 66                  |
      | serviceRegionCode            | Statewide           |
      | anniversaryDate              | anniversaryDate     |
      | channel                      | SYSTEM_INTEGRATION  |
      | rejectionReason              | test3               |
    And User provide other enrollment segments details
      | startDate         | firstDayOfNextYear           |
      | endDate           | highDate                     |
      | genericFieldText1 | <name>b.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                    |
    And User provide one more enrollment details
      | planCode            | <prevPlanCode>          |
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
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Not Found","Not Found"
    Then I verify latest benefit status information with data
    # CP-23753 3.0 Decide Program-Population
      | programPopulation | null |
    # CP-23753 4.0 Decide Benefit Status
      | benefitStatus     | null |
    # CP-23753 5.0 Re-determine Consumer Actions and Change Window
    Then I Verify Consumer Actions as following data
      | [0] | empty |
    # CP-23753 1.0 Update the consumer’s requested plan change selection
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      # will update
      | txnStatus            | SUBMITTED_TO_STATE |
      | updatedOn            | current            |
      | updatedBy            | null               |
      # remains same
      | channel              | SYSTEM_INTEGRATION |
      | createdOn            | current            |
      | createdBy            | null               |
      | planCode             | 355787430          |
      | medicalPlanBeginDate | firstDayOfNextYear |
      | medicalPlanEndDate   | highDate           |
      | selectionReason      | HT                 |
      | planEndDateReason    | null               |
      | enrollmentType       | MEDICAL            |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | correlationId | <name>a.traceid         |
      | consumerId    | <name>.consumerId       |
      | consumerFound | false                   |
  # CP-23753 2.0 Update the consumer’s prior plan change selection
    And I verify enrollment by consumer id "<name>.consumerId" with data
      # will updtae
      | txnStatus            | DISENROLL_SUBMITTED     |
      | medicalPlanEndDate   | lastDayOfThePresentYear |
      | updatedOn            | current                 |
      | updatedBy            | null                    |
      # remains same
      | medicalPlanBeginDate | 1stDayofPresentMonth    |
      | enrollmentType       | MEDICAL                 |
      | channel              | SYSTEM_INTEGRATION      |
      | anniversaryDate      | anniversaryDate         |
      | planEndDateReason    | PC                      |
      | planCode             | 455701400               |
    # CP-23758 Create Business Event - Plan Change Reject Response
    Then I will verify business events are generated with data
      | eventName     | PLAN_CHANGE_REJECT_RESPONSE |
      | correlationId | <name>a.traceid             |
      | consumerId    | <name>.consumerId           |
      | consumerFound | false                       |
    Examples:
      | name    | scenario                | prevStartDate        | prevPlanCode | reqestedStartDate  | requestedPlanCode |
     # Changed as per CP-33456 this scenario is invalid
#      | 23754-2 | wrong prevStartDate     | 1stDayofLastMonth    | 455701400    | firstDayOfNextYear | 355787430         |
      | 23754-3 | wrong prevPlanCode      | 1stDayofPresentMonth | 500307680    | firstDayOfNextYear | 355787430         |
      | 23754-4 | wrong reqestedStartDate | 1stDayofPresentMonth | 455701400    | 1stDayofLastMonth  | 355787430         |
      | 23754-5 | wrong requestedPlanCode | 1stDayofPresentMonth | 455701400    | firstDayOfNextYear | 500307680         |


  @API-CP-26294 @API-CP-26294-02 @API-CP-23754 @API-CP-23754-01 @API-CP-23753 @API-CP-23758 @API-EE-IN @IN-EB-API-Regression @sobir @kursat
  Scenario Outline: IN-EB: Decide Plan Change Reject Response - HIP (negative)-6667
  CP-23753 IN-EB Create or Update Data based on Plan Change Reject Response
  CP-23758 IN-EB Create Business Event - Plan Change Reject Response
  CP-26294 IN-EB Updated - IN-EB Decide Plan Change Responses HIP (Record Id 18)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
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
      | enrollmentEndDate            | lastDayOfThePresentYear |
      | txnStatus                    | Accepted                |
      | programCode                  | H                       |
      | subProgramTypeCode           | HealthyIndianaPlan      |
      | eligibilityStatusCode        | M                       |
      | planCode                     | 455701400               |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | rejectionReason              | test1                   |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | <name>.consumerId    |
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
    And I initiated get enrollment by consumer id "<name>.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "<name>b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | <name>.consumerId                |
      | [0].planId             | delete::                         |
      | [0].planCode           | 455701400                        |
      | [0].enrollmentId       | <name>b.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED              |
      | [0].txnStatus          | DISENROLL_SUBMITTED              |
      | [0].startDate          | fdofmnth::                       |
      | [0].endDate            | lastDayOfThePresentYear::        |
      | [0].enrollmentType     | delete::                         |
      | [0].subProgramTypeCode | HealthyIndianaPlan               |
      | [0].serviceRegionCode  | Statewide                        |
      | [1].planId             | delete::                         |
      | [1].planCode           | 355787430                        |
      | [1].consumerId         | <name>.consumerId                |
      | [1].enrollmentId       | <name>b.selectedEnrollmentId     |
      | [1].startDate          | firstDayOfNextYear::             |
      | [1].endDate            | highDate::                       |
      | [1].subProgramTypeCode | HealthyIndianaPlan               |
      | [1].serviceRegionCode  | Statewide                        |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId   |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | isEnrollmentProviderRequired | no                  |
      | recordId                     | 18                  |
      | enrollmentRecordId           | 18                  |
      | enrollmentStartDate          | <reqestedStartDate> |
      | enrollmentEndDate            | highDate            |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted            |
      | programCode                  | H                   |
      | subProgramTypeCode           | HealthyIndianaPlan  |
      | planCode                     | <requestedPlanCode> |
      | planId                       | 66                  |
      | serviceRegionCode            | Statewide           |
      | anniversaryDate              | anniversaryDate     |
      | channel                      | SYSTEM_INTEGRATION  |
      | rejectionReason              | test3               |
    And User provide other enrollment segments details
      | startDate         | firstDayOfNextYear |
      | endDate           | highDate           |
      | genericFieldText1 | 9999               |
      | segmentTypeCode   | OTHER_TXN          |
    And User provide one more enrollment details
      | planCode            | <prevPlanCode>          |
      | planId              | 33                      |
      | enrollmentStartDate | <prevStartDate>         |
      | enrollmentEndDate   | lastDayOfThePresentYear |
      | rejectionReason     | test4                   |
    And User provide other enrollment segments details
      | startDate         | firstDayOfNextYear |
      | endDate           | highDate           |
      | genericFieldText1 | null               |
      | segmentTypeCode   | OTHER_TXN          |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Not Found","Not Found"
    Then I verify latest benefit status information with data
    # CP-23753 3.0 Decide Program-Population
      | programPopulation | null |
    # CP-23753 4.0 Decide Benefit Status
      | benefitStatus     | null |
    # CP-23753 5.0 Re-determine Consumer Actions and Change Window
    Then I Verify Consumer Actions as following data
      | [0] | empty |
    # CP-23753 1.0 Update the consumer’s requested plan change selection
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      # will update
      | txnStatus            | SUBMITTED_TO_STATE |
      | updatedOn            | current            |
      | updatedBy            | null               |
      # remains same
      | channel              | SYSTEM_INTEGRATION |
      | createdOn            | current            |
      | createdBy            | null               |
      | planCode             | 355787430          |
      | medicalPlanBeginDate | firstDayOfNextYear |
      | medicalPlanEndDate   | highDate           |
      | selectionReason      | HT                 |
      | planEndDateReason    | null               |
      | enrollmentType       | MEDICAL            |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | correlationId | <name>a.traceid         |
      | consumerId    | <name>.consumerId       |
      | consumerFound | false                   |
  # CP-23753 2.0 Update the consumer’s prior plan change selection
    And I verify enrollment by consumer id "<name>.consumerId" with data
      # will updtae
      | txnStatus            | DISENROLL_SUBMITTED     |
      | medicalPlanEndDate   | lastDayOfThePresentYear |
      | updatedOn            | current                 |
      | updatedBy            | null                    |
      # remains same
      | medicalPlanBeginDate | 1stDayofPresentMonth    |
      | enrollmentType       | MEDICAL                 |
      | channel              | SYSTEM_INTEGRATION      |
      | anniversaryDate      | anniversaryDate         |
      | planEndDateReason    | PC                      |
      | planCode             | 455701400               |
    # CP-23758 Create Business Event - Plan Change Reject Response
    Then I will verify business events are generated with data
      | eventName     | PLAN_CHANGE_REJECT_RESPONSE |
      | correlationId | <name>a.traceid             |
      | consumerId    | <name>.consumerId           |
      | consumerFound | false                       |
    Examples:
      | name    | scenario           | prevStartDate        | prevPlanCode | reqestedStartDate  | requestedPlanCode |
      # wrong transactionid/enrollmentid
      | 23754-6 | wrong enrollmentid | 1stDayofPresentMonth | 455701400    | firstDayOfNextYear | 355787430         |


  @API-CP-29356 @API-CP-24256 @API-CP-26294 @API-CP-26294-04 @API-CP-23754 @API-CP-23754-01 @API-CP-23753 @API-CP-23758 @API-EE-IN @IN-EB-API-Regression @sobir @kursat
  Scenario Outline: IN-EB: Decide Plan Change Reject Response - HHW(positive) 1
  CP-24256 IN-EB Create Rejected Selection Task - Plan Change (task positive scenario)
  CP-23753 IN-EB Create or Update Data based on Plan Change Reject Response
  CP-23758 IN-EB Create Business Event - Plan Change Reject Response
  CP-26294 IN-EB Updated - IN-EB Decide Plan Change Responses HHW/HCC (Record Id 18)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
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
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityStatusCode        | M                    |
      | genericFieldText1            | Eligible             |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
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
      | consumerId                   | <name>.consumerId    |
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
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityStatusCode        | M                    |
      | planCode                     | <planCode1>          |
      | planId                       | <planId1>            |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | rejectionReason              | test1                |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | <name>.consumerId    |
      | [0].planId             | <planId2>            |
      | [0].planCode           | <planCode2>          |
      | [0].startDate          | 1stDayofNextMonth::  |
      | [0].endDate            | highDate::           |
      | [0].planEndDateReason  | PC                   |
      | [0].programTypeCode    | MEDICAID             |
      | [0].subProgramTypeCode | <subProgramTypeCode> |
      | [0].serviceRegionCode  | Statewide            |
      | [0].selectionReason    | 02                   |
      | [0].anniversaryDate    | anniversaryDate::    |
      | [0].channel            | SYSTEM_INTEGRATION   |
      | [0].rejectionReason    | test2                |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "<name>.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "<name>b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | <name>.consumerId                |
      | [0].planId             | delete::                         |
      | [0].planCode           | <planCode1>                      |
      | [0].enrollmentId       | <name>b.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED              |
      | [0].txnStatus          | DISENROLL_SUBMITTED              |
      | [0].startDate          | fdofmnth::                       |
      | [0].endDate            | lastDayofPresentMonth::          |
      | [0].enrollmentType     | delete::                         |
      | [0].subProgramTypeCode | <subProgramTypeCode>             |
      | [0].serviceRegionCode  | Statewide                        |
      | [1].planId             | delete::                         |
      | [1].planCode           | <planCode2>                      |
      | [1].consumerId         | <name>.consumerId                |
      | [1].enrollmentId       | <name>b.selectedEnrollmentId     |
      | [1].startDate          | 1stDayofNextMonth::              |
      | [1].endDate            | highDate::                       |
      | [1].subProgramTypeCode | <subProgramTypeCode>             |
      | [1].serviceRegionCode  | Statewide                        |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 18                   |
      | enrollmentRecordId           | 18                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | enrollmentEndDate            | highDate             |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | planCode                     | <planCode2>          |
      | planId                       | <planId2>            |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | rejectionReason              | test3                |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth            |
      | endDate           | highDate                     |
      | genericFieldText1 | <name>b.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                    |
    And User provide other enrollment segments details
      | genericFieldText1 | 010          |
      | segmentTypeCode   | ERROR_REASON |
    And User provide other enrollment segments details
      | genericFieldText1 | 831          |
      | segmentTypeCode   | ERROR_REASON |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "NO MATCH","Plan Change Reject"
    Then I verify benefit status information with data
    # CP-23753 3.0 Decide Program-Population
      | programPopulation | <programPopulation> |
    # CP-23753 4.0 Decide Benefit Status
      | benefitStatus     | Enrolled            |
    # CP-23753 5.0 Re-determine Consumer Actions and Change Window
    Then I Verify Consumer Actions as following data
      | [0].action               | Available              |
      | [0].consumerAction       | Plan Change Pre-lockin |
      | [0].planSelectionAllowed | true                   |
    # CP-23753 1.0 Update the consumer’s requested plan change selection
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      # will update
      | txnStatus            | REJECTED           |
      | updatedOn            | current            |
      | updatedBy            | 597                |
      # remains same
      | channel              | SYSTEM_INTEGRATION |
      | createdOn            | current            |
      | createdBy            | null               |
      | planCode             | <planCode2>        |
      | medicalPlanBeginDate | 1stDayofNextMonth  |
      | medicalPlanEndDate   | highDate           |
      | selectionReason      | HT                 |
      | planEndDateReason    | null               |
      | enrollmentType       | MEDICAL            |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
#| correlationId | <name>a.traceid         |
      | consumerId    | <name>.consumerId       |
      | consumerFound | true                    |
      | recordId      | 18                      |
      | DPBI          |[blank]|
  # CP-23753 2.0 Update the consumer’s prior plan change selection
    And I verify enrollment by consumer id "<name>.consumerId" with data
      # will updtae
      | txnStatus            | ACCEPTED             |
      | medicalPlanEndDate   | highDate             |
      | updatedOn            | current              |
      | updatedBy            | 597                  |
      # remains same
      | medicalPlanBeginDate | 1stDayofPresentMonth |
      | enrollmentType       | MEDICAL              |
      | channel              | SYSTEM_INTEGRATION   |
      | anniversaryDate      | anniversaryDate      |
      | planEndDateReason    | PC                   |
      | planCode             | <planCode1>          |
    # CP-24256 2.0 Include the following Data Elements and Values in the Task Creation Request
    Then I verify latest task information with name "" for consumer id "<name>.consumerId" with data
      | staffAssignedTo | null                                                                                                                  |
      | createdBy       | 597                                                                                                                   |
      | createdOn       | current                                                                                                               |
      | defaultDueDate  | in2BusinessDays                                                                                                       |
      | dueInDays       | 2                                                                                                                     |
      | defaultPriority | 1                                                                                                                     |
      | taskStatus      | Created                                                                                                               |
      | statusDate      | current                                                                                                               |
    # taskInfo has 3 info areas: reason contains/externalCaseId/what is Internal Scenario Code
    # empty fiels or null ==  taskInfo = null
    # not null == validates that taskInfo value is not null
      | taskInfo        | Reject Reason Medicaid ID/<name>.externalConsumerId/18                                                                |
    # CP-24256 optional
      | channel         | SYSTEM_INTEGRATION                                                                                                    |
      | caseId          | SYSTEM_INTEGRATION                                                                                                    |
      | externalCaseId  | <name>.caseIdentificationNumber                                                                                       |
      | disposition     | null                                                                                                                  |
      # planId is dinamic -> not possible to automate need JDBC
#      | planId          | <planId2>                                               |
      | planName        | <taskPlanName>                                                                                                        |
      | planStartDate   | 1stDayofNextMonth                                                                                                     |
      | endDate         | highDate                                                                                                              |
      | program         | <subProgramTypeCode>                                                                                                  |
      | linkToCase      | <name>.caseId                                                                                                         |
      | linkToConsumer  | <name>.consumerId                                                                                                     |
      | applicationType | Plan Change                                                                                                           |
#      | reason          | null                                                   |
      | reason          | 010 - Aid category / dates not effective/valid,831 - Recipient is not eligible for Disease Management/Case Management |
    # CP-23758 Create Business Event - Plan Change Reject Response
#    And I retrieve task information with name "" for consumer id "23754-1.consumerId"
    Then I will verify business events are generated with data
      # CP-23758 1.0 Business Event Required Fields
      | eventName                              | PLAN_CHANGE_REJECT_RESPONSE     |
#      | correlationId                          | <name>a.traceid                 |
      | channel                                | SYSTEM_INTEGRATION              |
      | createdBy                              | 597                             |
      | processDate                            | current                         |
      | externalCaseId                         | <name>.caseIdentificationNumber |
      | externalConsumerId                     | <name>.externalConsumerId       |
      | consumerId                             | <name>.consumerId               |
      | consumerName                           | <name>                          |
      # CP-23758 2.0 Business Event Optional Fields
      # task generation is not yet implenemted
#      | taskId                              | c.tasks[0].taskVO.taskId    |
      # CP-23758 3.0 Business Event Plan Change Enrollment Segments
      | previousEnrollment.enrollmentStartDate | 1stDayofPresentMonth            |
      | previousEnrollment.enrollmentEndDate   | highDate                        |
      | previousEnrollment.planSelectionReason | null                            |
      | previousEnrollment.planChangeReason    | PC                              |
      | previousEnrollment.rejectionReason     | test1                           |
      | previousEnrollment.planCode            | <planCode1>                     |
      | previousEnrollment.enrollmentType      | MEDICAL                         |
      | previousEnrollment.selectionStatus     | ACCEPTED                        |
      | requestedSelection.enrollmentStartDate | 1stDayofNextMonth               |
      | requestedSelection.enrollmentEndDate   | highDate                        |
      | requestedSelection.planSelectionReason | HT                              |
      | requestedSelection.planChangeReason    | null                            |
      | requestedSelection.rejectionReason     | test3                           |
      | requestedSelection.planCode            | <planCode2>                     |
      | requestedSelection.enrollmentType      | MEDICAL                         |
      | requestedSelection.selectionStatus     | REJECTED                        |
    Examples:
      | name    | programCode | subProgramTypeCode | programPopulation | planCode1 | planCode2 | planId1 | planId2 | taskPlanName |
      | 23754-7 | R           | HoosierHealthwise  | HHW-Mandatory     | 400752220 | 700410350 | 103     | 109     | CARESOURCE   |


  @API-CP-29356 @API-CP-24256 @API-CP-26294 @API-CP-26294-04 @API-CP-23754 @API-CP-23754-01 @API-CP-23753 @API-CP-23758 @API-EE-IN @IN-EB-API-Regression @sobir @kursat
  Scenario Outline: IN-EB: Decide Plan Change Reject Response - HCC (positive) 2
  CP-24256 IN-EB Create Rejected Selection Task - Plan Change (task positive scenario)
  CP-23753 IN-EB Create or Update Data based on Plan Change Reject Response
  CP-23758 IN-EB Create Business Event - Plan Change Reject Response
  CP-26294 IN-EB Updated - IN-EB Decide Plan Change Responses HHW/HCC (Record Id 18)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
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
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityStatusCode        | M                    |
      | genericFieldText1            | Eligible             |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
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
      | consumerId                   | <name>.consumerId    |
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
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityStatusCode        | M                    |
      | planCode                     | <planCode1>          |
      | planId                       | <planId1>            |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | rejectionReason              | test1                |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | <name>.consumerId    |
      | [0].planId             | <planId2>            |
      | [0].planCode           | <planCode2>          |
      | [0].startDate          | 1stDayofNextMonth::  |
      | [0].endDate            | highDate::           |
      | [0].planEndDateReason  | PC                   |
      | [0].programTypeCode    | MEDICAID             |
      | [0].subProgramTypeCode | <subProgramTypeCode> |
      | [0].serviceRegionCode  | Statewide            |
      | [0].selectionReason    | 02                   |
      | [0].anniversaryDate    | anniversaryDate::    |
      | [0].channel            | SYSTEM_INTEGRATION   |
      | [0].rejectionReason    | test2                |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "<name>.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "<name>b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | <name>.consumerId                |
      | [0].planId             | delete::                         |
      | [0].planCode           | <planCode1>                      |
      | [0].enrollmentId       | <name>b.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED              |
      | [0].txnStatus          | DISENROLL_SUBMITTED              |
      | [0].startDate          | fdofmnth::                       |
      | [0].endDate            | lastDayofPresentMonth::          |
      | [0].enrollmentType     | delete::                         |
      | [0].subProgramTypeCode | <subProgramTypeCode>             |
      | [0].serviceRegionCode  | Statewide                        |
      | [1].planId             | delete::                         |
      | [1].planCode           | <planCode2>                      |
      | [1].consumerId         | <name>.consumerId                |
      | [1].enrollmentId       | <name>b.selectedEnrollmentId     |
      | [1].startDate          | 1stDayofNextMonth::              |
      | [1].endDate            | highDate::                       |
      | [1].subProgramTypeCode | <subProgramTypeCode>             |
      | [1].serviceRegionCode  | Statewide                        |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 18                   |
      | enrollmentRecordId           | 18                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | enrollmentEndDate            | highDate             |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | planCode                     | <planCode2>          |
      | planId                       | <planId2>            |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | rejectionReason              | test3                |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth            |
      | endDate           | highDate                     |
      | genericFieldText1 | <name>b.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                    |
    And User provide other enrollment segments details
      | genericFieldText1 | 010          |
      | segmentTypeCode   | ERROR_REASON |
    And User provide other enrollment segments details
      | genericFieldText1 | 831          |
      | segmentTypeCode   | ERROR_REASON |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "NO MATCH","Plan Change Reject"
    Then I verify benefit status information with data
    # CP-23753 3.0 Decide Program-Population
      | programPopulation | <programPopulation> |
    # CP-23753 4.0 Decide Benefit Status
      | benefitStatus     | Enrolled            |
    # CP-23753 5.0 Re-determine Consumer Actions and Change Window
    Then I Verify Consumer Actions as following data
      | [0].action               | Available              |
      | [0].consumerAction       | Plan Change Pre-lockin |
      | [0].planSelectionAllowed | true                   |
    # CP-23753 1.0 Update the consumer’s requested plan change selection
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      # will update
      | txnStatus            | REJECTED           |
      | updatedOn            | current            |
      | updatedBy            | 597                |
      # remains same
      | channel              | SYSTEM_INTEGRATION |
      | createdOn            | current            |
      | createdBy            | null               |
      | planCode             | <planCode2>        |
      | medicalPlanBeginDate | 1stDayofNextMonth  |
      | medicalPlanEndDate   | highDate           |
      | selectionReason      | HT                 |
      | planEndDateReason    | null               |
      | enrollmentType       | MEDICAL            |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
#| correlationId | <name>a.traceid         |
      | consumerId    | <name>.consumerId       |
      | consumerFound | true                    |
      | recordId      | 18                      |
      | DPBI          |[blank]|
  # CP-23753 2.0 Update the consumer’s prior plan change selection
    And I verify enrollment by consumer id "<name>.consumerId" with data
      # will updtae
      | txnStatus            | ACCEPTED             |
      | medicalPlanEndDate   | highDate             |
      | updatedOn            | current              |
      | updatedBy            | 597                  |
      # remains same
      | medicalPlanBeginDate | 1stDayofPresentMonth |
      | enrollmentType       | MEDICAL              |
      | channel              | SYSTEM_INTEGRATION   |
      | anniversaryDate      | anniversaryDate      |
      | planEndDateReason    | PC                   |
      | planCode             | <planCode1>          |
    # CP-24256 2.0 Include the following Data Elements and Values in the Task Creation Request
    Then I verify latest task information with name "" for consumer id "<name>.consumerId" with data
      | staffAssignedTo | null                                                                                                                  |
      | createdBy       | 597                                                                                                                   |
      | createdOn       | current                                                                                                               |
      | defaultDueDate  | in2BusinessDays                                                                                                       |
      | dueInDays       | 2                                                                                                                     |
      | defaultPriority | 1                                                                                                                     |
      | taskStatus      | Created                                                                                                               |
      | statusDate      | current                                                                                                               |
    # taskInfo has 3 info areas: reason contains/externalCaseId/what is Internal Scenario Code
    # empty fiels or null ==  taskInfo = null
    # not null == validates that taskInfo value is not null
      | taskInfo        | Reject Reason Medicaid ID/<name>.externalConsumerId/18                                                                |
    # CP-24256 optional
      | channel         | SYSTEM_INTEGRATION                                                                                                    |
      | caseId          | SYSTEM_INTEGRATION                                                                                                    |
      | externalCaseId  | <name>.caseIdentificationNumber                                                                                       |
      | disposition     | null                                                                                                                  |
      # planId is dinamic -> not possible to automate need JDBC
#      | planId          | <planId2>                                               |
      | planName        | <taskPlanName>                                                                                                        |
      | planStartDate   | 1stDayofNextMonth                                                                                                     |
      | endDate         | highDate                                                                                                              |
      | program         | <subProgramTypeCode>                                                                                                  |
      | linkToCase      | <name>.caseId                                                                                                         |
      | linkToConsumer  | <name>.consumerId                                                                                                     |
      | applicationType | Plan Change                                                                                                           |
#      | reason          | null                                                   |
      | reason          | 010 - Aid category / dates not effective/valid,831 - Recipient is not eligible for Disease Management/Case Management |
    # CP-23758 Create Business Event - Plan Change Reject Response
    Then I will verify business events are generated with data
      # CP-23758 1.0 Business Event Required Fields
      | eventName                              | PLAN_CHANGE_REJECT_RESPONSE     |
#      | correlationId                          | <name>a.traceid                 |
      | channel                                | SYSTEM_INTEGRATION              |
      | createdBy                              | 597                             |
      | processDate                            | current                         |
      | externalCaseId                         | <name>.caseIdentificationNumber |
      | externalConsumerId                     | <name>.externalConsumerId       |
      | consumerId                             | <name>.consumerId               |
      | consumerName                           | <name>                          |
      # CP-23758 2.0 Business Event Optional Fields
      | taskId                                 | c.tasks[0].taskVO.taskId        |
      # CP-23758 3.0 Business Event Plan Change Enrollment Segments
      | previousEnrollment.enrollmentStartDate | 1stDayofPresentMonth            |
      | previousEnrollment.enrollmentEndDate   | highDate                        |
      | previousEnrollment.planSelectionReason | null                            |
      | previousEnrollment.planChangeReason    | PC                              |
      | previousEnrollment.rejectionReason     | test1                           |
      | previousEnrollment.planCode            | <planCode1>                     |
      | previousEnrollment.enrollmentType      | MEDICAL                         |
      | previousEnrollment.selectionStatus     | ACCEPTED                        |
      | requestedSelection.enrollmentStartDate | 1stDayofNextMonth               |
      | requestedSelection.enrollmentEndDate   | highDate                        |
      | requestedSelection.planSelectionReason | HT                              |
      | requestedSelection.planChangeReason    | null                            |
      | requestedSelection.rejectionReason     | test3                           |
      | requestedSelection.planCode            | <planCode2>                     |
      | requestedSelection.enrollmentType      | MEDICAL                         |
      | requestedSelection.selectionStatus     | REJECTED                        |
    Examples:
      | name    | programCode | subProgramTypeCode | programPopulation | planCode1 | planCode2 | planId1 | planId2 | taskPlanName                |
      | 23754-8 | A           | HoosierCareConnect | HCC-Mandatory     | 499254630 | 399243310 | 105     | 102     | MANAGED HEALTH SERVICES HCC |


  @API-CP-24256 @API-CP-24256-02 @API-EE-IN @IN-EB-API-Regression @sobir @kursat
  Scenario Outline: IN-EB Create Rejected Selection Task - Plan Change (task negative scenario)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
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
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityStatusCode        | M                    |
      | genericFieldText1            | Eligible             |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
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
      | consumerId                   | <name>.consumerId    |
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
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityStatusCode        | M                    |
      | planCode                     | <planCode1>          |
      | planId                       | <planId1>            |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | rejectionReason              | test1                |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | <name>.consumerId    |
      | [0].planId             | <planId2>            |
      | [0].planCode           | <planCode2>          |
      | [0].startDate          | 1stDayofNextMonth::  |
      | [0].endDate            | highDate::           |
      | [0].planEndDateReason  | PC                   |
      | [0].programTypeCode    | MEDICAID             |
      | [0].subProgramTypeCode | <subProgramTypeCode> |
      | [0].serviceRegionCode  | Statewide            |
      | [0].selectionReason    | 02                   |
      | [0].anniversaryDate    | anniversaryDate::    |
      | [0].channel            | SYSTEM_INTEGRATION   |
      | [0].rejectionReason    | test2                |
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
      | [0].endDate            | lastDayofPresentMonth::          |
      | [0].enrollmentType     | delete::                         |
      | [0].subProgramTypeCode | <subProgramTypeCode>             |
      | [0].serviceRegionCode  | Statewide                        |
      | [1].planId             | delete::                         |
      | [1].planCode           | <planCode2>                      |
      | [1].consumerId         | <name>.consumerId                |
      | [1].enrollmentId       | <name>a.selectedEnrollmentId     |
      | [1].startDate          | 1stDayofNextMonth::              |
      | [1].endDate            | highDate::                       |
      | [1].subProgramTypeCode | <subProgramTypeCode>             |
      | [1].serviceRegionCode  | Statewide                        |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 18                   |
      | enrollmentRecordId           | 18                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | enrollmentEndDate            | highDate             |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | planCode                     | <planCode2>          |
      | planId                       | <planId2>            |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | rejectionReason              | test3                |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth            |
      | endDate           | highDate                     |
      | genericFieldText1 | <name>a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                    |
    And User provide other enrollment segments details
      | genericFieldText1 | 300          |
      | segmentTypeCode   | ERROR_REASON |
    And User provide other enrollment segments details
      | genericFieldText1 | 831          |
      | segmentTypeCode   | ERROR_REASON |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "NO MATCH","Plan Change Reject"
    # CP-24256 2.0 Include the following Data Elements and Values in the Task Creation Request
    Then I verify latest task information with name "" for consumer id "<name>.consumerId" with data
      | empty |[blank]|
    Examples:
      | name    | programCode | subProgramTypeCode | planCode1 | planCode2 | planId1 | planId2 |
      | 24256-2 | R           | HoosierHealthwise  | 400752220 | 700410350 | 103     | 109     |
      | 24256-3 | A           | HoosierCareConnect | 499254630 | 399243310 | 105     | 102     |


  @API-CP-26294 @API-CP-26294-04 @API-CP-23754 @API-CP-23754-02 @API-CP-23753 @API-CP-23758 @API-EE-IN @IN-EB-API-Regression @sobir @kursat
  Scenario Outline: IN-EB: Decide Plan Change Reject Response - HHW/HCC (negative)
  CP-23753 IN-EB Create or Update Data based on Plan Change Reject Response
  CP-23758 IN-EB Create Business Event - Plan Change Reject Response
  CP-26294 IN-EB Updated - IN-EB Decide Plan Change Responses HHW/HCC (Record Id 18)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
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
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityStatusCode        | M                    |
      | genericFieldText1            | Eligible             |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
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
      | enrollmentEndDate            | lastDayOfThePresentYear |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | M                       |
      | planCode                     | <planCode1>             |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | rejectionReason              | test1                   |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | <name>.consumerId    |
      | [0].planId             | 199                  |
      | [0].planCode           | <planCode2>          |
      | [0].startDate          | firstDayOfNextYear:: |
      | [0].endDate            | highDate::           |
      | [0].planEndDateReason  | PC                   |
      | [0].programTypeCode    | MEDICAID             |
      | [0].subProgramTypeCode | <subProgramTypeCode> |
      | [0].serviceRegionCode  | Statewide            |
      | [0].channel            | SYSTEM_INTEGRATION   |
      | [0].rejectionReason    | test2                |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "<name>.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "<name>b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | <name>.consumerId                |
      | [0].planId             | delete::                         |
      | [0].planCode           | <planCode1>                      |
      | [0].enrollmentId       | <name>b.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED              |
      | [0].txnStatus          | DISENROLL_SUBMITTED              |
      | [0].startDate          | fdofmnth::                       |
      | [0].endDate            | lastDayOfThePresentYear::        |
      | [0].enrollmentType     | delete::                         |
      | [0].subProgramTypeCode | <subProgramTypeCode>             |
      | [0].serviceRegionCode  | Statewide                        |
      | [1].planId             | delete::                         |
      | [1].planCode           | <planCode2>                      |
      | [1].consumerId         | <name>.consumerId                |
      | [1].enrollmentId       | <name>b.selectedEnrollmentId     |
      | [1].startDate          | firstDayOfNextYear::             |
      | [1].endDate            | highDate::                       |
      | [1].subProgramTypeCode | <subProgramTypeCode>             |
      | [1].serviceRegionCode  | Statewide                        |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 18                   |
      | enrollmentRecordId           | 18                   |
      | enrollmentStartDate          | <reqestedStartDate>  |
      | enrollmentEndDate            | highDate             |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | planCode                     | <requestedPlanCode>  |
      | planId                       | 66                   |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | rejectionReason              | test3                |
    And User provide other enrollment segments details
      | startDate         | firstDayOfNextYear           |
      | endDate           | highDate                     |
      | genericFieldText1 | <name>b.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                    |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Not Found","Not Found"
    Then I verify latest benefit status information with data
    # CP-23753 3.0 Decide Program-Population
      | programPopulation | null |
    # CP-23753 4.0 Decide Benefit Status
      | benefitStatus     | null |
    # CP-23753 5.0 Re-determine Consumer Actions and Change Window
    Then I Verify Consumer Actions as following data
      | [0] | empty |
    # CP-23753 1.0 Update the consumer’s requested plan change selection
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      # will update
      | txnStatus            | SUBMITTED_TO_STATE |
      | updatedOn            | current            |
      | updatedBy            | null               |
      # remains same
      | channel              | SYSTEM_INTEGRATION |
      | createdOn            | current            |
      | createdBy            | null               |
      | planCode             | <planCode2>        |
      | medicalPlanBeginDate | firstDayOfNextYear |
      | medicalPlanEndDate   | highDate           |
      | selectionReason      | HT                 |
      | planEndDateReason    | null               |
      | enrollmentType       | MEDICAL            |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | correlationId | <name>a.traceid         |
      | consumerId    | <name>.consumerId       |
      | consumerFound | false                   |
  # CP-23753 2.0 Update the consumer’s prior plan change selection
    And I verify enrollment by consumer id "<name>.consumerId" with data
      # will updtae
      | txnStatus            | DISENROLL_SUBMITTED     |
      | medicalPlanEndDate   | lastDayOfThePresentYear |
      | updatedOn            | current                 |
      | updatedBy            | null                    |
      # remains same
      | medicalPlanBeginDate | 1stDayofPresentMonth    |
      | enrollmentType       | MEDICAL                 |
      | channel              | SYSTEM_INTEGRATION      |
      | anniversaryDate      | anniversaryDate         |
      | planEndDateReason    | PC                      |
      | planCode             | <planCode1>             |
    # CP-23758 Create Business Event - Plan Change Reject Response
    Then I will verify business events are generated with data
      | eventName     | PLAN_CHANGE_REJECT_RESPONSE |
      | correlationId | <name>a.traceid             |
      | consumerId    | <name>.consumerId           |
      | consumerFound | false                       |
    Examples:
      | name     | scenario                | programCode | subProgramTypeCode | planCode1 | planCode2 | reqestedStartDate  | requestedPlanCode |
      | 23754-9  | wrong reqestedStartDate | R           | HoosierHealthwise  | 400752220 | 700410350 | 1stDayofLastMonth  | 700410350         |
      | 23754-10 | wrong requestedPlanCode | A           | HoosierCareConnect | 499254630 | 399243310 | firstDayOfNextYear | 500307680         |

  @API-CP-26294 @API-CP-26294-04 @API-CP-23754 @API-CP-23754-02 @API-CP-23753 @API-CP-23758 @API-EE-IN @IN-EB-API-Regression @sobir @kursat
  Scenario Outline: IN-EB: Decide Plan Change Reject Response - HHW/HCC (negative) (wrong transactionid/enrollmentid)
  CP-23753 IN-EB Create or Update Data based on Plan Change Reject Response
  CP-23758 IN-EB Create Business Event - Plan Change Reject Response
  CP-26294 IN-EB Updated - IN-EB Decide Plan Change Responses HHW/HCC (Record Id 18)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
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
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityStatusCode        | M                    |
      | genericFieldText1            | Eligible             |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
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
      | enrollmentEndDate            | lastDayOfThePresentYear |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | M                       |
      | planCode                     | <planCode1>             |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | rejectionReason              | test1                   |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | <name>.consumerId    |
      | [0].planId             | 199                  |
      | [0].planCode           | <planCode2>          |
      | [0].startDate          | firstDayOfNextYear:: |
      | [0].endDate            | highDate::           |
      | [0].planEndDateReason  | PC                   |
      | [0].programTypeCode    | MEDICAID             |
      | [0].subProgramTypeCode | <subProgramTypeCode> |
      | [0].serviceRegionCode  | Statewide            |
      | [0].channel            | SYSTEM_INTEGRATION   |
      | [0].rejectionReason    | test2                |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "<name>.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "<name>b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | <name>.consumerId                |
      | [0].planId             | delete::                         |
      | [0].planCode           | <planCode1>                      |
      | [0].enrollmentId       | <name>b.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED              |
      | [0].txnStatus          | DISENROLL_SUBMITTED              |
      | [0].startDate          | fdofmnth::                       |
      | [0].endDate            | lastDayOfThePresentYear::        |
      | [0].enrollmentType     | delete::                         |
      | [0].subProgramTypeCode | <subProgramTypeCode>             |
      | [0].serviceRegionCode  | Statewide                        |
      | [1].planId             | delete::                         |
      | [1].planCode           | <planCode2>                      |
      | [1].consumerId         | <name>.consumerId                |
      | [1].enrollmentId       | <name>b.selectedEnrollmentId     |
      | [1].startDate          | firstDayOfNextYear::             |
      | [1].endDate            | highDate::                       |
      | [1].subProgramTypeCode | <subProgramTypeCode>             |
      | [1].serviceRegionCode  | Statewide                        |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 18                   |
      | enrollmentRecordId           | 18                   |
      | enrollmentStartDate          | <reqestedStartDate>  |
      | enrollmentEndDate            | highDate             |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | planCode                     | <requestedPlanCode>  |
      | planId                       | 66                   |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | rejectionReason              | test3                |
    And User provide other enrollment segments details
      | startDate         | firstDayOfNextYear           |
      | endDate           | highDate                     |
      | genericFieldText1 | <name>b.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                    |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Not Found","Not Found"
    Then I verify latest benefit status information with data
    # CP-23753 3.0 Decide Program-Population
      | programPopulation | null |
    # CP-23753 4.0 Decide Benefit Status
      | benefitStatus     | null |
    # CP-23753 5.0 Re-determine Consumer Actions and Change Window
    Then I Verify Consumer Actions as following data
      | [0] | empty |
    # CP-23753 1.0 Update the consumer’s requested plan change selection
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      # will update
      | txnStatus            | SUBMITTED_TO_STATE |
      | updatedOn            | current            |
      | updatedBy            | null               |
      # remains same
      | channel              | SYSTEM_INTEGRATION |
      | createdOn            | current            |
      | createdBy            | null               |
      | planCode             | <planCode2>        |
      | medicalPlanBeginDate | firstDayOfNextYear |
      | medicalPlanEndDate   | highDate           |
      | selectionReason      | HT                 |
      | planEndDateReason    | null               |
      | enrollmentType       | MEDICAL            |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | correlationId | <name>a.traceid         |
      | consumerId    | <name>.consumerId       |
      | consumerFound | false                   |
  # CP-23753 2.0 Update the consumer’s prior plan change selection
    And I verify enrollment by consumer id "<name>.consumerId" with data
      # will updtae
      | txnStatus            | DISENROLL_SUBMITTED     |
      | medicalPlanEndDate   | lastDayOfThePresentYear |
      | updatedOn            | current                 |
      | updatedBy            | null                    |
      # remains same
      | medicalPlanBeginDate | 1stDayofPresentMonth    |
      | enrollmentType       | MEDICAL                 |
      | channel              | SYSTEM_INTEGRATION      |
      | anniversaryDate      | anniversaryDate         |
      | planEndDateReason    | PC                      |
      | planCode             | <planCode1>             |
    # CP-23758 Create Business Event - Plan Change Reject Response
    Then I will verify business events are generated with data
      | eventName     | PLAN_CHANGE_REJECT_RESPONSE |
      | correlationId | <name>a.traceid             |
      | consumerId    | <name>.consumerId           |
      | consumerFound | false                       |
    Examples:
      | name     | scenario           | programCode | subProgramTypeCode | planCode1 | planCode2 | reqestedStartDate  | requestedPlanCode |
      # wrong transactionid/enrollmentid
      | 23754-11 | wrong enrollmentId | A           | HoosierCareConnect | 499254630 | 399243310 | firstDayOfNextYear | 500307680         |
