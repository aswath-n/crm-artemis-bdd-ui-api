Feature:IN-EB Enrollment Decide Scenario and Request Correspondence: MMIS Sends Potential for HCC & Create HCC SR despite Letters Request Status


  @API-CP-25304 @API-CP-24303 @API-CP-24303-01 @crm-regression @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario: CP-24303 IN-EB Enrollment Decide Scenario and Request Correspondence MMIS Sends Potential
  CP-24304 IN-EB Enrollment Create HCC Outbound Call Service Request MMIS Sends Potential
#    this scenario also works for non HCC population. According to project team it's not a defect
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 24303-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 24303-1.consumerId    |
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
      | startDate           | 1stDayofPresentMonth |
      | segmentDetailValue1 | Y                    |
      | segmentDetailValue2 | C                    |
      | segmentDetailValue3 | A                    |
      | segmentDetailValue4 | A                    |
      | segmentDetailValue5 | Z                    |
      | segmentTypeCode     | POTENTIAL            |
    And I run create Eligibility and Enrollment API
    And Wait for 20 seconds
    Then I verify new correspondences generated for consumerId "24303-1.consumerId" with data
      | [0].enrollmentId       | null                             |
      | [0].caseId             | 24303-1.caseId                   |
      | [0].externalConsumerId | 24303-1.externalConsumerId       |
      | [0].externalCaseId     | 24303-1.caseIdentificationNumber |
      | [0].status             | Requested                        |
      | [0].correspondenceType | IA                               |
      | [0].createdOn          | current                          |
      | [0].updatedOn          | null                             |
    # CP-24304 AC 1.0 Enrollment Create Outbound Call Service Request: MMIS Sends Potential (HCC)
    And Wait for 20 seconds
    Then I verify latest task information with name "" for consumer id "24303-1.consumerId" with data
      | find_taskTypeId       | 15414              |
      | this_taskTypeId_count | 1                  |
      | taskStatus            | Open               |
      | linkToConsumer        | 24303-1.consumerId |
      | linkToCase            | 24303-1.caseId     |


  @API-CP-24303 @API-CP-24303-02 @crm-regression @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario: IN-EB Enrollment Decide Scenario and Request Correspondence MMIS Sends Potential (negative no consumer in CP)
  CP-25304 IN-EB Enrollment Create HCC Outbound Call Service Request MMIS Sends Potential (negative no consumer in CP)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 999999999             |
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
      | startDate           | 1stDayofPresentMonth      |
      | endDate             | highDate                  |
      | segmentDetailValue1 | 10                        |
      | segmentDetailValue2 | 60                        |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentTypeCode     | OTHER                     |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth |
      | segmentDetailValue1 | Y                    |
      | segmentDetailValue2 | C                    |
      | segmentDetailValue3 | A                    |
      | segmentDetailValue4 | A                    |
      | segmentDetailValue5 | Z                    |
      | segmentTypeCode     | POTENTIAL            |
    And I run create Eligibility and Enrollment API
    And Wait for 20 seconds
    Then I verify new correspondences generated for consumerId "999999999" with data
      | [0] | empty |
    Then I verify latest task information with name "" for consumer id "999999999" with data
      | empty |[blank]|


  @API-CP-25304 @API-CP-24303 @API-CP-24303-01 @crm-regression @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario: CP-25304 IN-EB Enrollment Create HCC Outbound Call Service Request MMIS Sends Potential (negative SR already created)
#    this scenario also works for non HCC population. According to project team it's not a defect
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 25304-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 25304-1.consumerId    |
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
      | startDate           | 1stDayofPresentMonth |
      | segmentDetailValue1 | Y                    |
      | segmentDetailValue2 | C                    |
      | segmentDetailValue3 | A                    |
      | segmentDetailValue4 | A                    |
      | segmentDetailValue5 | Z                    |
      | segmentTypeCode     | POTENTIAL            |
    And I run create Eligibility and Enrollment API
    And Wait for 20 seconds
    Then I verify latest task information with name "" for consumer id "25304-1.consumerId" with data
      | find_taskTypeId       | 15414              |
      | this_taskTypeId_count | 1                  |
      | taskStatus            | Open               |
      | linkToConsumer        | 25304-1.consumerId |
      | linkToCase            | 25304-1.caseId     |
    Given I initiated Eligibility and Enrollment Create API
    And Wait for 120 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 25304-1.consumerId    |
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
      | startDate           | 1stDayofPresentMonth |
      | segmentDetailValue1 | Y                    |
      | segmentDetailValue2 | C                    |
      | segmentDetailValue3 | A                    |
      | segmentDetailValue4 | A                    |
      | segmentDetailValue5 | Z                    |
      | segmentTypeCode     | POTENTIAL            |
    And I run create Eligibility and Enrollment API
    And Wait for 20 seconds
    Then I verify latest task information with name "" for consumer id "25304-1.consumerId" with data
      | find_taskTypeId       | 15414              |
      | this_taskTypeId_count | 1                  |
      | taskStatus            | Open               |
      | linkToConsumer        | 25304-1.consumerId |
      | linkToCase            | 25304-1.caseId     |


  @API-CP-25304 @API-CP-24303 @API-CP-24303-01 @API-CP-35314 @crm-regression @API-EE-IN @IN-EB-API-Regression @shruti
    #updated original script as part of CP-35314
  Scenario: CP-25304 IN-EB Enrollment Create HCC Outbound Call Service Request MMIS Sends Potential- Verify SR is created- accepted enrollment
#    this scenario also works for non HCC population. According to project team it's not a defect
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 25304-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 25304-2.consumerId    |
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
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 25304-2.consumerId    |
      | startDate         | 1stDayofPresentMonth  |
      | endDate           | anniversaryDueDateHCC |
      | genericFieldText1 | C                     |
      | genericFieldDate1 | 1stDayofPresentMonth  |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 25304-2.consumerId    |
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
      | planCode                     | 399243310             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 25304-2.consumerId    |
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
      | startDate           | 1stDayofPresentMonth      |
      | endDate             | highDate                  |
      | segmentDetailValue1 | 10                        |
      | segmentDetailValue2 | 60                        |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentTypeCode     | OTHER                     |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth |
      | segmentDetailValue1 | Y                    |
      | segmentDetailValue2 | C                    |
      | segmentDetailValue3 | A                    |
      | segmentDetailValue4 | A                    |
      | segmentDetailValue5 | Z                    |
      | segmentTypeCode     | POTENTIAL            |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Then I verify latest task information with name "" for consumer id "25304-2.consumerId" with data
      | find_taskTypeId       | 15414              |
      | this_taskTypeId_count | 1                  |
      | taskStatus            | Open               |
      | linkToConsumer        | 25304-2.consumerId |
      | linkToCase            | 25304-2.caseId     |