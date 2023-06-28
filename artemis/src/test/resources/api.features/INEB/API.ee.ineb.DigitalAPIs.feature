Feature: API: EE INEB digital API's

  @API-CP-38998 @API-CP-38998-02 @API-EE-IN @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: CP-38998 INEB  Digital planTransfer API to include selected providers multiple -2
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | RA1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA1.consumerId       |
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
      | consumerId        | RA1.consumerId           |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA1.consumerId          |
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
      | planId                       | 104                     |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | rejectionReason              | test1                   |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | RA2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA2.consumerId       |
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
      | consumerId        | RA2.consumerId           |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA2.consumerId          |
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
      | planId                       | 104                     |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | rejectionReason              | test1                   |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | RA3 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA3.consumerId       |
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
      | consumerId        | RA3.consumerId           |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA3.consumerId          |
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
      | planId                       | 104                     |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | rejectionReason              | test1                   |
    And I run create Eligibility and Enrollment API
    When I run Digital Plan Transfer API with or without "with" random contactRecordId and channel "SYSTEM_INTEGRATION" with data
#    Map<String, List<String>> please use this sequence:
#   | Map key consumerID | disenrollmentReason | planCode | planProviderID | consumerAction |
      | RA1.consumerId | Reason1 | 355787430 | 137327,137351 | Plan Change Prelockin |
      | RA2.consumerId | Reason1 | 355787430 | NA            | Plan Change Prelockin |
      | RA3.consumerId | Reason1 | 355787430 | null          | Plan Change Prelockin |
    Then I verify digital enrollment plans details with data
      | [0].consumerId    | RA1.consumerId    |
      | [0].effectiveDate | 1stDayofNextMonth |
      | [0].message       | SELECTION_MADE    |
      | [0].successInd    | Y                 |
      | [1].consumerId    | RA2.consumerId    |
      | [1].effectiveDate | 1stDayofNextMonth |
      | [1].message       | SELECTION_MADE    |
      | [1].successInd    | Y                 |
      | [2].consumerId    | RA3.consumerId    |
      | [2].effectiveDate | 1stDayofNextMonth |
      | [2].message       | SELECTION_MADE    |
      | [2].successInd    | Y                 |