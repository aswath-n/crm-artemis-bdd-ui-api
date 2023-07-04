Feature: API: EE DCEB digital API's

  @API-CP-39979 @API-CP-39979-02 @shruti @API-DC-EE @eligibility-enrollment-ms-EE
  Scenario: DCEB-View Enrollment Plans Digital API Verify channel is displayed
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 39003-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39003-01.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | eligibilityRecordId          | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | highDate-DC         |
      | txnStatus                    | Accepted            |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
      | coverageCode                 | 130                 |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "39003-01a" for update Enrollment information
      | [0].consumerId         | 39003-01.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 081080400           |
      | [0].startDate          | fdofmnth::          |
      | [0].endDate            | highdatedc::        |
      | [0].programTypeCode    | MEDICAID            |
      | [0].subProgramTypeCode | DCHF                |
      | [0].serviceRegionCode  | Northeast           |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "39003-01a" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | fdofmnth::             |
      | [0].planId       | delete::               |
      | [0].planCode     | 081080400              |
      | [0].consumerId   | 39003-01.consumerId    |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39003-01.consumerId  |
      | isEligibilityRequired        | no                   |
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 15                   |
      | enrollmentRecordId           | 15                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate-DC          |
      | anniversaryDate              | anniversaryDate      |
      | eligibilityStartDate         |[blank]|
      | programCode                  | MEDICAID             |
      | planCode                     | 081080400            |
      | planId                       | 145                  |
      | subProgramTypeCode           | DCHF                 |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 39003-01.consumerId |
      | [0].planId             | 26                  |
      | [0].planCode           | 055558200           |
      | [0].startDate          | fdnxtmth::          |
      | [0].endDate            | highdatedc::        |
      | [0].subProgramTypeCode | DCHF                |
      | [0].serviceRegionCode  | Northeast           |
      | [0].selectionReason    | 02                  |
      | [0].anniversaryDate    | anniversaryDate::   |
      | [0].channel            | SYSTEM_INTEGRATION  |
    And Wait for 3 seconds
    And I initiated get enrollment by consumer id "39003-01.consumerId"
    When I run get enrollment api
    Given I initiated get digital enrollment plans by consumer ids ""
    When I run get digital enrollment plans API
    Then I verify digital enrollment plans details
      | consumerIds | timeframe |
      |[blank]|           |

  @API-CP-40686 @API-CP-40686-01 @shruti @API-DC-EE
  Scenario: DCEB- Plan Transfer - Enroll Action- HRA/CAMY Survey SR created
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | RA1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA1.consumerId    |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 25                |
      | eligibilityRecordId          | 25                |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | highDate-DC       |
      | txnStatus                    | Accepted          |
      | programCode                  | MEDICAID          |
      | subProgramTypeCode           | DCHF              |
      | coverageCode                 | 130               |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
#    Given I initiated digital plan transfer API
    When I run Digital Plan Transfer API with or without "with" random contactRecordId and channel "WEB" with data
      | RA1.consumerId | null | 044733300 | null | Enroll |
#    And I run digital plan transfer API with expected status "success"
#    When I initaite get enrollments for consumerID ""
#    And I run get enrollments for consumer with expected status "success"
    # Getting tasks after selection made for this consumer by calling mars/taskmanagement/tasks/{taksId} endpoint
    #Then I verify latest task information with name "" for consumer id "RA1.consumerId" with data
#    Then I verify latest task information with name "" for case id "internal" with data
#      | taskTypeId      | 16402   |
#      | staffAssignedTo | null    |
#      | createdBy       | WEB     |
#      | createdOn       | current |
#      | taskStatus      | Open    |
#      | statusDate      | current |
    Then I verify no task of type id "16402" is created

  @API-CP-40686 @API-CP-40686-02 @API-CP-38998 @API-CP-38998-03 @shruti @API-DC-EE
  Scenario: DCEB- Plan Transfer - Plan Change Action- HRA/CAMY Survey SR created
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | RA1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA1.consumerId    |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 25                |
      | eligibilityRecordId          | 25                |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | highDate-DC       |
      | txnStatus                    | Accepted          |
      | programCode                  | MEDICAID          |
      | subProgramTypeCode           | DCHF              |
      | coverageCode                 | 130               |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "RA1.consumerId" for update Enrollment information
      | [0].consumerId         | RA1.consumerId |
      | [0].planId             | 145            |
      | [0].planCode           | 081080400      |
      | [0].startDate          | fdofmnth::     |
      | [0].endDate            | highdatedc::   |
      | [0].programTypeCode    | MEDICAID       |
      | [0].subProgramTypeCode | DCHF           |
      | [0].serviceRegionCode  | Northeast      |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "RA1.consumerId" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | fdofmnth::             |
      | [0].planId       | delete::               |
      | [0].planCode     | 081080400              |
      | [0].consumerId   | RA1.consumerId         |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA1.consumerId       |
      | isEligibilityRequired        | no                   |
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 15                   |
      | enrollmentRecordId           | 15                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate-DC          |
      | anniversaryDate              | anniversaryDate      |
      | eligibilityStartDate         |[blank]|
      | programCode                  | MEDICAID             |
      | planCode                     | 081080400            |
      | planId                       | 145                  |
      | subProgramTypeCode           | DCHF                 |
    And I run create Eligibility and Enrollment API
#    Given I initiated digital plan transfer API
    When I run Digital Plan Transfer API with or without "with" random contactRecordId and channel "WEB" with data
      | RA1.consumerId | Reason1 | 044733300 | NA | Plan Change Pre-lockin |
#    Then I verify latest task information with name "" for case id "internal" with data
#      | taskTypeId      | 16402   |
#      | staffAssignedTo | null    |
#      | createdBy       | 2824    |
#      | createdOn       | current |
#      | taskStatus      | Open    |
#      | statusDate      | current |
    And I run service request details get Api
    Then I verify no task of type id "16402" is created

  @API-CP-42764 @API-DC-EE @DC-EB-API-Regression @shruti
  Scenario: Digital -Enrollment provider - is displayed for current and future
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 42764-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 42764-01.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | eligibilityRecordId          | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | highDate-DC         |
      | txnStatus                    | Accepted            |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
      | coverageCode                 | 130                 |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "42764-01a" for update Enrollment information
      | [0].consumerId         | 42764-01.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | random           |
      | [0].startDate          | fdofmnth::          |
      | [0].endDate            | highdatedc::        |
      | [0].programTypeCode    | MEDICAID            |
      | [0].subProgramTypeCode | DCHF                |
      | [0].serviceRegionCode  | Northeast           |
      | enrollmentProviders    | random              |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "42764-01a" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | fdofmnth::             |
      | [0].planId       | delete::               |
      | [0].planCode     | 081080400              |
      | [0].consumerId   | 42764-01.consumerId    |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 42764-01.consumerId  |
      | isEligibilityRequired        | no                   |
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 15                   |
      | enrollmentRecordId           | 15                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate-DC          |
      | anniversaryDate              | anniversaryDate      |
      | eligibilityStartDate         |[blank]|
      | programCode                  | MEDICAID             |
      | planCode                     | 081080400            |
      | planId                       | 145                  |
      | subProgramTypeCode           | DCHF                 |
    And I run create Eligibility and Enrollment API
    And I perform plan transfer via API to new plan with data
      | saveDetailsWithName                           | 42764-01-planTransferDetails                    |
      | [0].consumerId                                | 42764-01.consumerId                             |
      | [0].planId                                    | 26                                              |
      | existingPlanCode                              | 42764-01-enrollmentDetails.planCode             |
      | [0].planCode                                  | random                                          |
      | [0].startDate                                 | fdnxtmth::                                      |
      | [0].endDate                                   | highdatedc::                                    |
      | [0].subProgramTypeCode                        | DCHF                                            |
      | [0].serviceRegionCode                         | Northeast                                       |
      | [0].selectionReason                           | 02                                              |
      | [0].anniversaryDate                           | anniversaryDate::                               |
      | [0].channel                                   | SYSTEM_INTEGRATION                              |
      | enrollmentProviders                           | random                                          |
      | [0].enrollmentProviders[0].consumerId         | 42764-01.consumerId                             |
      | [0].enrollmentProviders[0].providerFirstName  | 42764-01-planTransferDetails.providerFirstName  |
      | [0].enrollmentProviders[0].providerMiddleName | 42764-01-planTransferDetails.providerMiddleName |
      | [0].enrollmentProviders[0].providerLastName   | 42764-01-planTransferDetails.providerLastName   |
      | [0].enrollmentProviders[0].providerNpi        | 42764-01-planTransferDetails.providerNpi        |
      | [0].enrollmentProviders[0].providerType       | 42764-01-planTransferDetails.providerType       |
    Given I initiated get digital enrollment provider by consumer ids ""
    When I run get digital enrollment provider API
    Then I verify digital enrollment provider details for "currentEnrollment"
    |providerId|42764-01a.[0].enrollmentProviders[0].providerId|
    Then I verify digital enrollment provider details for "futureEnrollment"
      |providerId|42764-01-planTransferDetails.providerId|
