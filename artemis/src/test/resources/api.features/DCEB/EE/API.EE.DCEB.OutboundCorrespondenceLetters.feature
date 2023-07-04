@API-CP-38286-01_api
Feature: Verify DCEB outbound correspondence letter are triggered

  @API-CP-38286 @API-CP-38286-01 @API-DC-EE @DC-EB-API-Regression @shruti
  Scenario: Verify Enrollment Confirmation Packet Letter 160 is triggered for DCHF Subprogram type without provider
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 38286-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 38286-01.consumerId |
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
    Then I send API call with name "38286-01" for update Enrollment information
      | [0].consumerId         | 38286-01.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 081080400           |
      | [0].startDate          | fdofmnth::          |
      | [0].endDate            | highdatedc::        |
      | [0].programTypeCode    | MEDICAID            |
      | [0].subProgramTypeCode | DCHF                |
      | [0].serviceRegionCode  | Northeast           |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "38286-01" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | fdofmnth::             |
      | [0].planId       | delete::               |
      | [0].planCode     | 081080400              |
      | [0].consumerId   | 38286-01.consumerId    |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 38286-01.consumerId  |
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
    Then I verify new correspondences generated for consumerId "38286-01.consumerId" with data
      | [0].enrollmentId              | null            |
      | [0].caseId                    | 38286-01.caseId |
      | [0].correspondenceTypeToMatch | 160             |
    And I verify correspondence details of correspondence id ""
      | planCode          | 081080400                |
      | healthPlan        | AMERIHEALTH CARITAS DC   |
      | startDate         | 1stDayofPresentMonth     |
      | planChangeDueDate | 89DayFromFirstDayOfMonth |
      | pcp               | UNKNOWN                  |
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 38286-02 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 38286-02.consumerId |
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
    Then I send API call with name "38286-02" for update Enrollment information
      | [0].consumerId         | 38286-02.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 055558200           |
      | [0].startDate          | fdofmnth::          |
      | [0].endDate            | highdatedc::        |
      | [0].programTypeCode    | MEDICAID            |
      | [0].subProgramTypeCode | DCHF                |
      | [0].serviceRegionCode  | Northeast           |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "38286-02" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | fdofmnth::             |
      | [0].planId       | delete::               |
      | [0].planCode     | 055558200              |
      | [0].consumerId   | 38286-02.consumerId    |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 38286-02.consumerId  |
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
      | planCode                     | 055558200            |
      | planId                       | 145                  |
      | subProgramTypeCode           | DCHF                 |
    And I run create Eligibility and Enrollment API
    Then I verify new correspondences generated for consumerId "38286-02.consumerId" with data
      | [0].enrollmentId              | null            |
      | [0].caseId                    | 38286-02.caseId |
      | [0].correspondenceTypeToMatch | 160             |
    And I verify correspondence details of correspondence id ""
      | planCode          | 055558200                          |
      | healthPlan        | CAREFIRST COMMUNITY HEALTH PLAN DC |
      | startDate         | 1stDayofPresentMonth               |
      | planChangeDueDate | 89DayFromFirstDayOfMonth           |
      | pcp               | UNKNOWN                            |

  @API-CP-38286 @API-CP-38286-02 @API-DC-EE @DC-EB-API-Regression @shruti
  Scenario: Verify Enrollment Confirmation Packet Letter 160 is not triggered for NON DCHF Subprogram type
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 38286-02 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 38286-02.consumerId |
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
      | subProgramTypeCode           | Alliance            |
      | coverageCode                 | 460                 |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "38286-02" for update Enrollment information
      | [0].consumerId         | 38286-02.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 077573200           |
      | [0].startDate          | fdofmnth::          |
      | [0].endDate            | highdatedc::        |
      | [0].programTypeCode    | MEDICAID            |
      | [0].subProgramTypeCode | Alliance            |
      | [0].serviceRegionCode  | Northeast           |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "38286-02" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | fdofmnth::             |
      | [0].planId       | delete::               |
      | [0].planCode     | 081080400              |
      | [0].consumerId   | 38286-01.consumerId    |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 38286-02.consumerId  |
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
      | planCode                     | 077573200            |
      | planId                       | 145                  |
      | subProgramTypeCode           | Alliance             |
    And I run create Eligibility and Enrollment API
    Then I verify letter type "160" not generated for non DCHF consumer with id "38286-02.consumerId"

  @API-CP-38286 @API-CP-38286-03 @API-DC-EE @DC-EB-API-Regression @shruti
  Scenario: Verify Enrollment Confirmation Packet Letter 160 is triggered for DCHF Subprogram type with provider
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 39888-03 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39888-03.consumerId |
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
    Then I send API call with name "39888-03a" for update Enrollment information
      | [0].consumerId                                | 39888-03.consumerId |
      | [0].planId                                    | 145                 |
      | [0].planCode                                  | 081080400           |
      | [0].startDate                                 | fdofmnth::          |
      | [0].endDate                                   | highdatedc::        |
      | [0].programTypeCode                           | MEDICAID            |
      | [0].subProgramTypeCode                        | DCHF                |
      | [0].serviceRegionCode                         | Northeast           |
      | enrollmentProviders                           | Yes                 |
      | [0].enrollmentProviders[0].consumerId         | 39888-03.consumerId |
      | [0].enrollmentProviders[0].providerFirstName  | Will                |
      | [0].enrollmentProviders[0].providerMiddleName | M                   |
      | [0].enrollmentProviders[0].providerLastName   | Tickle              |
      | [0].enrollmentProviders[0].providerNpi        | 1639321391          |
      | [0].enrollmentProviders[0].providerType       | P                   |
    And Wait for 3 seconds
#    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "39888-03a" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | fdofmnth::             |
      | [0].planId       | delete::               |
      | [0].planCode     | 081080400              |
      | [0].consumerId   | 39888-03.consumerId    |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39888-03.consumerId  |
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
    Then I verify new correspondences generated for consumerId "39888-03.consumerId" with data
      | [0].enrollmentId              | null            |
      | [0].caseId                    | 39888-03.caseId |
      | [0].correspondenceTypeToMatch | 160             |
    And I verify correspondence details of correspondence id ""
      | planCode          | 081080400                |
      | healthPlan        | AMERIHEALTH CARITAS DC   |
      | startDate         | 1stDayofPresentMonth     |
      | planChangeDueDate | 89DayFromFirstDayOfMonth |
      | pcp               | UNKNOWN                  |

  @API-CP-39888 @API-DC-EE @DC-EB-API-Regression @shruti
  Scenario: Trigger Confirmation Transfer Notice Letter (#162) - EB - DCHF
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 39888-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39888-01.consumerId |
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
    Then I send API call with name "39888-01a" for update Enrollment information
      | [0].consumerId                                | 39888-01.consumerId |
      | [0].planId                                    | 145                 |
      | [0].planCode                                  | 081080400           |
      | [0].startDate                                 | fdofmnth::          |
      | [0].endDate                                   | highdatedc::        |
      | [0].programTypeCode                           | MEDICAID            |
      | [0].subProgramTypeCode                        | DCHF                |
      | [0].serviceRegionCode                         | Northeast           |
      | enrollmentProviders                           | Yes                 |
      | [0].enrollmentProviders[0].consumerId         | 39888-01.consumerId |
      | [0].enrollmentProviders[0].providerFirstName  | James               |
      | [0].enrollmentProviders[0].providerMiddleName | E                   |
      | [0].enrollmentProviders[0].providerLastName   | Benson              |
      | [0].enrollmentProviders[0].providerNpi        | 1639321391          |
      | [0].enrollmentProviders[0].providerType       | P                   |
    And Wait for 3 seconds
#    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "39888-01a" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | fdofmnth::             |
      | [0].planId       | delete::               |
      | [0].planCode     | 081080400              |
      | [0].consumerId   | 39888-01.consumerId    |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39888-01.consumerId  |
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
      | [0].consumerId         | 39888-01.consumerId |
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
    And I initiated get enrollment by consumer id "39888-01.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "39888-01b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 39888-01.consumerId                |
      | [0].planId             | delete::                           |
      | [0].planCode           | 081080400                          |
      | [0].enrollmentId       | 39888-01b.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED                |
      | [0].txnStatus          | DISENROLL_SUBMITTED                |
      | [0].startDate          | fdofmnth::                         |
      | [0].endDate            | lstdaymnth::                       |
      | [0].enrollmentType     | delete::                           |
      | [0].subProgramTypeCode | DCHF                               |
      | [0].serviceRegionCode  | Northeast                          |
      | [1].planId             | delete::                           |
      | [1].planCode           | 055558200                          |
      | [1].consumerId         | 39888-01.consumerId                |
      | [1].enrollmentId       | 39888-01b.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                         |
      | [1].endDate            | highdatedc::                       |
      | [1].subProgramTypeCode | DCHF                               |
      | [1].serviceRegionCode  | Northeast                          |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39888-01.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | isEnrollmentProviderRequired | no                  |
      | recordId                     | 17                  |
      | enrollmentRecordId           | 17                  |
      | enrollmentStartDate          | 1stDayofNextMonth   |
      | enrollmentEndDate            | highDate-DC         |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted            |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
      | planCode                     | 055558200           |
      | planId                       | 26                  |
      | serviceRegionCode            | Northeast           |
      | anniversaryDate              | anniversaryDate     |
      | channel                      | SYSTEM_INTEGRATION  |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth              |
      | endDate           | highDate-DC                    |
      | genericFieldText1 | 39888-01b.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                      |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I verify new correspondences generated for consumerId "39888-01.consumerId" with data
      | [0].enrollmentId              | null            |
      | [0].caseId                    | 39888-01.caseId |
      | [0].correspondenceTypeToMatch | 162             |
    And I verify correspondence details of correspondence id ""
      | healthPlan     | AMERIHEALTH CARITAS DC,CAREFIRST COMMUNITY HEALTH PLAN DC |
      | startDate      | 1stDayofNextMonth                                         |
      | pcpPhoneNumber | null                                                      |
      | mcoPhoneNumber | 855-326-4831                                              |
      | pcp            | UNKNOWN                                                   |


  @API-CP-39888 @API-CP-39888-04 @API-DC-EE @DC-EB-API-Regression @shruti
  Scenario: Plan transfer Confirmation Packet Letter 162 is not triggered for NON DCHF Subprogram type
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 39888-04 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39888-04.consumerId |
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
      | subProgramTypeCode           | Alliance            |
      | coverageCode                 | 460                 |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "38286-02" for update Enrollment information
      | [0].consumerId         | 39888-04.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 077573200           |
      | [0].startDate          | fdofmnth::          |
      | [0].endDate            | highdatedc::        |
      | [0].programTypeCode    | MEDICAID            |
      | [0].subProgramTypeCode | Alliance            |
      | [0].serviceRegionCode  | Northeast           |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "38286-02" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | fdofmnth::             |
      | [0].planId       | delete::               |
      | [0].planCode     | 081080400              |
      | [0].consumerId   | 39888-04.consumerId    |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39888-04.consumerId  |
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
      | planCode                     | 077573200            |
      | planId                       | 145                  |
      | subProgramTypeCode           | Alliance             |
    And I run create Eligibility and Enrollment API
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 39888-01.consumerId |
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
    And I initiated get enrollment by consumer id "39888-04.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "39888-04b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 39888-04.consumerId                |
      | [0].planId             | delete::                           |
      | [0].planCode           | 081080400                          |
      | [0].enrollmentId       | 39888-01b.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED                |
      | [0].txnStatus          | DISENROLL_SUBMITTED                |
      | [0].startDate          | fdofmnth::                         |
      | [0].endDate            | lstdaymnth::                       |
      | [0].enrollmentType     | delete::                           |
      | [0].subProgramTypeCode | DCHF                               |
      | [0].serviceRegionCode  | Northeast                          |
      | [1].planId             | delete::                           |
      | [1].planCode           | 055558200                          |
      | [1].consumerId         | 39888-01.consumerId                |
      | [1].enrollmentId       | 39888-01b.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                         |
      | [1].endDate            | highdatedc::                       |
      | [1].subProgramTypeCode | DCHF                               |
      | [1].serviceRegionCode  | Northeast                          |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39888-04.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | isEnrollmentProviderRequired | no                  |
      | recordId                     | 17                  |
      | enrollmentRecordId           | 17                  |
      | enrollmentStartDate          | 1stDayofNextMonth   |
      | enrollmentEndDate            | highDate-DC         |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted            |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
      | planCode                     | 055558200           |
      | planId                       | 26                  |
      | serviceRegionCode            | Northeast           |
      | anniversaryDate              | anniversaryDate     |
      | channel                      | SYSTEM_INTEGRATION  |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth              |
      | endDate           | highDate-DC                    |
      | genericFieldText1 | 39888-04b.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                      |
    And I run create Eligibility and Enrollment API
    Then I verify letter type "162" not generated for non DCHF consumer with id "39888-04.consumerId"

  @API-CP-39553 @API-CP-39553-01 @API-DC-EE @DC-EB-API-Regression @shruti
  Scenario: DC EB- Auto Assign-Immigrant Children-Letter 211
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I initiated auto assignment plan batch process Api for Immigrant Children
    When I run auto assignment plan batch process Api
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | RA39553 |
      | newCaseCreation  | yes     |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA39553.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 25                 |
      | eligibilityRecordId          | 25                 |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          | 1stDayofLastMonth  |
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | eligibilityEndDate           | highDate-DC        |
      | txnStatus                    | Accepted           |
      | programCode                  | MEDICAID           |
      | subProgramTypeCode           | ImmigrantChildren  |
      | coverageCode                 | 420F               |
    And I run create Eligibility and Enrollment API
    Given I initiated auto assignment plan batch process Api for Immigrant Children
    When I run auto assignment plan batch process Api
    And Wait for 60 seconds
    And I verify enrollment by consumer id "RA39553.consumerId" with data
      | saveDetailsWithName | RA39553-enrollmentDetails |
      | planCode            | not null                  |
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "RA39553-02" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION                  |
      | [0].startDate    | RA39553-enrollmentDetails.startDate |
      | [0].planId       | delete::                            |
      | [0].planCode     | RA39553-enrollmentDetails.planCode  |
      | [0].consumerId   | RA39553.consumerId                  |
      | [0].enrollmentId | c.data[0].enrollmentId              |
      | [0].status       | SUBMITTED_TO_STATE                  |
      | [0].txnStatus    | SUBMITTED_TO_STATE                  |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA39553.consumerId                 |
      | isEligibilityRequired        | no                                 |
      | isEnrollemntRequired         | yes                                |
      | isEnrollmentProviderRequired | no                                 |
      | recordId                     | 15                                 |
      | enrollmentRecordId           | 15                                 |
      | enrollmentStartDate          | 1stDayofNextMonth                  |
      | enrollmentEndDate            | highDate-DC                        |
      | anniversaryDate              | anniversaryDate                    |
      | eligibilityStartDate         |[blank]|
      | programCode                  | MEDICAID                           |
      | planCode                     | RA39553-enrollmentDetails.planCode |
      | planId                       | 145                                |
      | subProgramTypeCode           | ImmigrantChildren                  |
    And I run create Eligibility and Enrollment API
    Then I verify new correspondences generated for consumerId "RA39553.consumerId" with data
      | [0].enrollmentId              | null           |
      | [0].caseId                    | RA39553.caseId |
      | [0].correspondenceTypeToMatch | 211            |
    And I verify correspondence details of correspondence id ""
      | healthPlan     | RA39553-enrollmentDetails.planName       |
      | startDate      | 1stDayofNextMonth                        |
      | mcoPhoneNumber | RA39553-enrollmentDetails.mcoPhoneNumber |


  @API-CP-39555 @API-DC-EE @DC-EB-API-Regression @shruti
  Scenario: Verify Enrollment Confirmation Packet Letter 180 is  triggered for Alliance Subprogram type
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | RA39555 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA39555.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 25                 |
      | eligibilityRecordId          | 25                 |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          | 1stDayofLastMonth  |
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | eligibilityEndDate           | highDate-DC        |
      | txnStatus                    | Accepted           |
      | programCode                  | MEDICAID           |
      | subProgramTypeCode           | Alliance           |
      | coverageCode                 | 460                |
    And I run create Eligibility and Enrollment API
    Given I initiated auto assignment plan batch process Api for Alliance
    When I run auto assignment plan batch process Api
    And Wait for 60 seconds
    And I verify enrollment by consumer id "RA39555.consumerId" with data
      | saveDetailsWithName | RA39555-enrollmentDetails |
      | planCode            | not null                  |
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "39555-02" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION                  |
      | [0].startDate    | RA39555-enrollmentDetails.startDate |
      | [0].planId       | delete::                            |
      | [0].planCode     | RA39555-enrollmentDetails.planCode  |
      | [0].consumerId   | RA39555.consumerId                  |
      | [0].enrollmentId | c.data[0].enrollmentId              |
      | [0].status       | SUBMITTED_TO_STATE                  |
      | [0].txnStatus    | SUBMITTED_TO_STATE                  |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA39555.consumerId                 |
      | isEligibilityRequired        | no                                 |
      | isEnrollemntRequired         | yes                                |
      | isEnrollmentProviderRequired | no                                 |
      | recordId                     | 15                                 |
      | enrollmentRecordId           | 15                                 |
      | enrollmentStartDate          | 1stDayofLastMonth                  |
      | enrollmentEndDate            | highDate-DC                        |
      | anniversaryDate              | anniversaryDate                    |
      | eligibilityStartDate         |[blank]|
      | programCode                  | MEDICAID                           |
      | planCode                     | RA39555-enrollmentDetails.planCode |
      | planId                       | 145                                |
      | subProgramTypeCode           | Alliance                           |
    And I run create Eligibility and Enrollment API
    Then I verify new correspondences generated for consumerId "RA39555.consumerId" with data
      | [0].enrollmentId              | null           |
      | [0].caseId                    | RA39555.caseId |
      | [0].correspondenceTypeToMatch | 180            |
    And I verify correspondence details of correspondence id ""
      | healthPlan     | RA39555-enrollmentDetails.planName       |
      | dateSignedUp   | 1stDayofLastMonth                        |
      | mcoPhoneNumber | RA39555-enrollmentDetails.mcoPhoneNumber |


  @API-CP-40389 @API-DC-EE @DC-EB-API-Regression @shruti
  Scenario: Plan Transfer notice-Letter 169 is  triggered for Alliance Subprogram type
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | RA40389 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA40389.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 25                 |
      | eligibilityRecordId          | 25                 |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          | 1stDayofLastMonth  |
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | eligibilityEndDate           | highDate-DC        |
      | txnStatus                    | Accepted           |
      | programCode                  | MEDICAID           |
      | subProgramTypeCode           | Alliance           |
      | coverageCode                 | 460                |
    And I run create Eligibility and Enrollment API
    Given I initiated auto assignment plan batch process Api for Alliance
    When I run auto assignment plan batch process Api
    And Wait for 60 seconds
    And I verify enrollment by consumer id "RA40389.consumerId" with data
      | saveDetailsWithName | RA40389-enrollmentDetails |
      | planCode            | not null                  |
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "40389-02" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION                  |
      | [0].startDate    | RA40389-enrollmentDetails.startDate |
      | [0].planId       | delete::                            |
      | [0].planCode     | RA40389-enrollmentDetails.planCode  |
      | [0].consumerId   | RA40389.consumerId                  |
      | [0].enrollmentId | c.data[0].enrollmentId              |
      | [0].status       | SUBMITTED_TO_STATE                  |
      | [0].txnStatus    | SUBMITTED_TO_STATE                  |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA40389.consumerId                 |
      | isEligibilityRequired        | no                                 |
      | isEnrollemntRequired         | yes                                |
      | isEnrollmentProviderRequired | no                                 |
      | recordId                     | 15                                 |
      | enrollmentRecordId           | 15                                 |
      | enrollmentStartDate          | 1stDayofLastMonth                  |
      | enrollmentEndDate            | highDate-DC                        |
      | anniversaryDate              | anniversaryDate                    |
      | eligibilityStartDate         |[blank]|
      | programCode                  | MEDICAID                           |
      | planCode                     | RA40389-enrollmentDetails.planCode |
      | planId                       | 145                                |
      | subProgramTypeCode           | Alliance                           |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    And I perform plan transfer via API to new plan with data
      | saveDetailsWithName    | RA40389-planTransferDetails        |
      | [0].consumerId         | RA40389.consumerId                 |
      | [0].planId             | 26                                 |
      | existingPlanCode       | RA40389-enrollmentDetails.planCode |
      | [0].planCode           | random                             |
      | [0].startDate          | fdnxtmth::                         |
      | [0].endDate            | highdatedc::                       |
      | [0].subProgramTypeCode | Alliance                           |
      | [0].serviceRegionCode  | Northeast                          |
      | [0].selectionReason    | 02                                 |
      | [0].anniversaryDate    | anniversaryDate::                  |
      | [0].channel            | SYSTEM_INTEGRATION                 |
    And Wait for 3 seconds
    And I initiated get enrollment by consumer id "RA40389.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "40389-01b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | RA40389.consumerId                   |
      | [0].planId             | delete::                             |
      | [0].planCode           | RA40389-enrollmentDetails.planCode   |
      | [0].enrollmentId       | 40389-01b.discontinuedEnrollmentId   |
      | [0].status             | DISENROLL_SUBMITTED                  |
      | [0].txnStatus          | DISENROLL_SUBMITTED                  |
      | [0].startDate          | fdofmnth::                           |
      | [0].endDate            | lstdaymnth::                         |
      | [0].enrollmentType     | delete::                             |
      | [0].subProgramTypeCode | DCHF                                 |
      | [0].serviceRegionCode  | Northeast                            |
      | [1].planId             | delete::                             |
      | [1].planCode           | RA40389-planTransferDetails.planCode |
      | [1].consumerId         | RA40389.consumerId                   |
      | [1].enrollmentId       | 40389-01b.selectedEnrollmentId       |
      | [1].startDate          | fdnxtmth::                           |
      | [1].endDate            | highdatedc::                         |
      | [1].subProgramTypeCode | DCHF                                 |
      | [1].serviceRegionCode  | Northeast                            |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA40389.consumerId                   |
      | isEligibilityRequired        | no                                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                                  |
      | isEnrollmentProviderRequired | no                                   |
      | recordId                     | 17                                   |
      | enrollmentRecordId           | 17                                   |
      | enrollmentStartDate          | 1stDayofNextMonth                    |
      | enrollmentEndDate            | highDate-DC                          |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted                             |
      | programCode                  | MEDICAID                             |
      | subProgramTypeCode           | Alliance                             |
      | planCode                     | RA40389-planTransferDetails.planCode |
      | planId                       | 26                                   |
      | serviceRegionCode            | Northeast                            |
      | anniversaryDate              | anniversaryDate                      |
      | channel                      | SYSTEM_INTEGRATION                   |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth              |
      | endDate           | highDate-DC                    |
      | genericFieldText1 | 39003-01b.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                      |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I verify new correspondences generated for consumerId "RA40389.consumerId" with data
      | [0].enrollmentId              | null           |
      | [0].caseId                    | RA40389.caseId |
      | [0].correspondenceTypeToMatch | 186            |
    And I verify correspondence details of correspondence id ""
      | healthPlan      | RA40389-planTransferDetails.planCode       |
      | priorHealthPlan | RA40389-enrollmentDetails.planCode         |
      | startDate       | 1stDayofNextMonth                          |
      | mcoPhoneNumber  | RA40389-planTransferDetails.mcoPhoneNumber |


  @API-CP-40389 @API-CP-40389-02 @API-DC-EE @DC-EB-API-Regression @shruti
  Scenario: Plan Transfer notice-Letter 169 is  triggered for Alliance Subprogram type with PCP-3
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | RA40389-02 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA40389-02.consumerId |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 25                    |
      | eligibilityRecordId          | 25                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayofLastMonth     |
      | eligibilityStartDate         | 1stDayofLastMonth     |
      | eligibilityEndDate           | highDate-DC           |
      | txnStatus                    | Accepted              |
      | programCode                  | MEDICAID              |
      | subProgramTypeCode           | Alliance              |
      | coverageCode                 | 460                   |
    And I run create Eligibility and Enrollment API
    Given I initiated auto assignment plan batch process Api for Alliance
    When I run auto assignment plan batch process Api
    And Wait for 60 seconds
    And I verify enrollment by consumer id "RA40389-02.consumerId" with data
      | saveDetailsWithName | RA40389-02-enrollmentDetails |
      | planCode            | not null                     |
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "40389-02" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION                     |
      | [0].startDate    | RA40389-02-enrollmentDetails.startDate |
      | [0].planId       | delete::                               |
      | [0].planCode     | RA40389-02-enrollmentDetails.planCode  |
      | [0].consumerId   | RA40389-02.consumerId                  |
      | [0].enrollmentId | c.data[0].enrollmentId                 |
      | [0].status       | SUBMITTED_TO_STATE                     |
      | [0].txnStatus    | SUBMITTED_TO_STATE                     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA40389-02.consumerId                 |
      | isEligibilityRequired        | no                                    |
      | isEnrollemntRequired         | yes                                   |
      | isEnrollmentProviderRequired | no                                    |
      | recordId                     | 15                                    |
      | enrollmentRecordId           | 15                                    |
      | enrollmentStartDate          | 1stDayofLastMonth                     |
      | enrollmentEndDate            | highDate-DC                           |
      | anniversaryDate              | anniversaryDate                       |
      | eligibilityStartDate         |[blank]|
      | programCode                  | MEDICAID                              |
      | planCode                     | RA40389-02-enrollmentDetails.planCode |
      | planId                       | 145                                   |
      | subProgramTypeCode           | Alliance                              |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
#    Given I initiated Plan Management for Search Provider
#    And User send Api call with payload "providerSearch" for search provider
#      | providerSearch.planName | AMERIGROUP COMMUNITY CARE |
    And I perform plan transfer via API to new plan with data
      | saveDetailsWithName                           | RA40389-02-planTransferDetails                    |
      | [0].consumerId                                | RA40389-02.consumerId                             |
      | [0].planId                                    | 26                                                |
      | existingPlanCode                              | RA40389-02-enrollmentDetails.planCode             |
      | [0].planCode                                  | random                                            |
      | [0].startDate                                 | fdnxtmth::                                        |
      | [0].endDate                                   | highdatedc::                                      |
      | [0].subProgramTypeCode                        | Alliance                                          |
      | [0].serviceRegionCode                         | Northeast                                         |
      | [0].selectionReason                           | 02                                                |
      | [0].anniversaryDate                           | anniversaryDate::                                 |
      | [0].channel                                   | SYSTEM_INTEGRATION                                |
      | enrollmentProviders                           | random                                            |
      | [0].enrollmentProviders[0].consumerId         | RA40389-02.consumerId                             |
      | [0].enrollmentProviders[0].providerFirstName  | RA40389-02-planTransferDetails.providerFirstName  |
      | [0].enrollmentProviders[0].providerMiddleName | RA40389-02-planTransferDetails.providerMiddleName |
      | [0].enrollmentProviders[0].providerLastName   | RA40389-02-planTransferDetails.providerLastName   |
      | [0].enrollmentProviders[0].providerNpi        | RA40389-02-planTransferDetails.providerNpi        |
      | [0].enrollmentProviders[0].providerType       | RA40389-02-planTransferDetails.providerType       |
    And Wait for 3 seconds
    And I initiated get enrollment by consumer id "RA40389-02.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "40389-01b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | RA40389-02.consumerId                   |
      | [0].planId             | delete::                                |
      | [0].planCode           | RA40389-02-enrollmentDetails.planCode   |
      | [0].enrollmentId       | 40389-01b.discontinuedEnrollmentId      |
      | [0].status             | DISENROLL_SUBMITTED                     |
      | [0].txnStatus          | DISENROLL_SUBMITTED                     |
      | [0].startDate          | fdofmnth::                              |
      | [0].endDate            | lstdaymnth::                            |
      | [0].enrollmentType     | delete::                                |
      | [0].subProgramTypeCode | DCHF                                    |
      | [0].serviceRegionCode  | Northeast                               |
      | [1].planId             | delete::                                |
      | [1].planCode           | RA40389-02-planTransferDetails.planCode |
      | [1].consumerId         | RA40389-02.consumerId                   |
      | [1].enrollmentId       | 40389-01b.selectedEnrollmentId          |
      | [1].startDate          | fdnxtmth::                              |
      | [1].endDate            | highdatedc::                            |
      | [1].subProgramTypeCode | DCHF                                    |
      | [1].serviceRegionCode  | Northeast                               |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA40389-02.consumerId                   |
      | isEligibilityRequired        | no                                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                                     |
      | isEnrollmentProviderRequired | no                                      |
      | recordId                     | 17                                      |
      | enrollmentRecordId           | 17                                      |
      | enrollmentStartDate          | 1stDayofNextMonth                       |
      | enrollmentEndDate            | highDate-DC                             |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted                                |
      | programCode                  | MEDICAID                                |
      | subProgramTypeCode           | Alliance                                |
      | planCode                     | RA40389-02-planTransferDetails.planCode |
      | planId                       | 26                                      |
      | serviceRegionCode            | Northeast                               |
      | anniversaryDate              | anniversaryDate                         |
      | channel                      | SYSTEM_INTEGRATION                      |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth              |
      | endDate           | highDate-DC                    |
      | genericFieldText1 | 40389-01b.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                      |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I verify new correspondences generated for consumerId "RA40389-02.consumerId" with data
      | [0].enrollmentId              | null              |
      | [0].caseId                    | RA40389-02.caseId |
      | [0].correspondenceTypeToMatch | 186               |
    And I verify correspondence details of correspondence id ""
      | healthPlan      | RA40389-02-planTransferDetails.planCode            |
      | priorHealthPlan | RA40389-02-enrollmentDetails.planCode              |
      | startDate       | 1stDayofNextMonth                                  |
      | mcoPhoneNumber  | RA40389-02-planTransferDetails.mcoPhoneNumber      |
      | pcp             | RA40389-02-planTransferDetails.pcp                 |
      | pcpPhoneNumber  | RA40389-02-planTransferDetails.providerPhoneNumber |

  @API-CP-40369 @API-CP-40369-01 @API-CP-43078 @API-CP-43078-01 @API-DC-EE @DC-EB-API-Regression @shruti
  Scenario: DCHF Annual Notification Letter 101 is triggered for DCHF Subprogram type with provider-2
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 40369-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 40369-01.consumerId |
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
    Then I send API call with name "40369-01a" for update Enrollment information
      | [0].consumerId                                | 40369-01.consumerId |
      | [0].planId                                    | 145                 |
      | [0].planCode                                  | 081080400           |
      | [0].startDate                                 | fdofmnth::          |
      | [0].endDate                                   | highdatedc::        |
      | [0].programTypeCode                           | MEDICAID            |
      | [0].subProgramTypeCode                        | DCHF                |
      | [0].serviceRegionCode                         | Northeast           |
      | enrollmentProviders                           | Yes                 |
      | [0].enrollmentProviders[0].consumerId         | 40369-01.consumerId |
      | [0].enrollmentProviders[0].providerFirstName  | Will                |
      | [0].enrollmentProviders[0].providerMiddleName | M                   |
      | [0].enrollmentProviders[0].providerLastName   | Tickle              |
      | [0].enrollmentProviders[0].providerNpi        | 1639321391          |
      | [0].enrollmentProviders[0].providerType       | P                   |
    And Wait for 3 seconds
#    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "40369-01b" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | fdofmnth::             |
      | [0].planId       | delete::               |
      | [0].planCode     | 081080400              |
      | [0].consumerId   | 40369-01.consumerId    |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 40369-01.consumerId  |
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
    And I initiated get benefit status by consumer id "40369-01.consumerId"
    And I run get enrollment api
    And I update consumers benefit by consumerId "40369-01.consumerId" with data
      | [0].timeframe          | Active                 |
      | [0].consumerAction     | Plan Change Pre-lockin |
      | [0].changeAllowedFrom  | current                |
      | [0].changeAllowedUntil | 90daysFromToday        |
    Given I initiated enrollment letter API
    When I run enrollment letter API
      | mmisCode | 101 |
    And Wait for 3 seconds
    Then I verify new correspondences generated for consumerId "40369-01.consumerId" with data
      | [0].enrollmentId              | null            |
      | [0].caseId                    | 40369-01.caseId |
      | [0].correspondenceTypeToMatch | 101             |
    And I verify correspondence details of correspondence id ""
      | healthPlan      | AMERIHEALTH CARITAS DC  |
      | anniversaryDate | 40369-01a.[0].startDate |
      | pcp             | UNKNOWN                 |
    Given I initiated enrollment letter transaction status API
    When I run enrollment letter transaction status API
      | transactionType | 101     |
      | moduleName      | Letter  |
      | createdOn       | current |
    Then I verify enrollment letter transaction status API response
      | transactionType   | 101       |
      | moduleName        | Letter    |
      | createdOn         | current   |
      | updatedOn         | current   |
      | transactionStatus | Completed |

  @API-CP-40388 @API-CP-40388-01 @API-CP-43078 @API-CP-43078-01 @API-DC-EE @DC-EB-API-Regression @shruti
  Scenario: Plan Transfer notice-Letter 181 is  triggered for Alliance Subprogram type-2
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | RA40388-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA40388-01.consumerId |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 25                    |
      | eligibilityRecordId          | 25                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayofLastMonth     |
      | eligibilityStartDate         | 1stDayofLastMonth     |
      | eligibilityEndDate           | highDate-DC           |
      | txnStatus                    | Accepted              |
      | programCode                  | MEDICAID              |
      | subProgramTypeCode           | Alliance              |
      | coverageCode                 | 460                   |
    And I run create Eligibility and Enrollment API
    Given I initiated auto assignment plan batch process Api for Alliance
    When I run auto assignment plan batch process Api
    And Wait for 60 seconds
    And I verify enrollment by consumer id "RA40388-01.consumerId" with data
      | saveDetailsWithName | RA40388-01-enrollmentDetails |
      | planCode            | not null                     |
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "40388-01" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION                     |
      | [0].startDate    | RA40388-01-enrollmentDetails.startDate |
      | [0].planId       | delete::                               |
      | [0].planCode     | RA40388-01-enrollmentDetails.planCode  |
      | [0].consumerId   | RA40388-01.consumerId                  |
      | [0].enrollmentId | c.data[0].enrollmentId                 |
      | [0].status       | SUBMITTED_TO_STATE                     |
      | [0].txnStatus    | SUBMITTED_TO_STATE                     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA40388-01.consumerId                  |
      | isEligibilityRequired        | no                                     |
      | isEnrollemntRequired         | yes                                    |
      | isEnrollmentProviderRequired | no                                     |
      | recordId                     | 15                                     |
      | enrollmentRecordId           | 15                                     |
      | enrollmentStartDate          | RA40388-01-enrollmentDetails.startDate |
      | enrollmentEndDate            | highDate-DC                            |
      | anniversaryDate              | anniversaryDate                        |
      | eligibilityStartDate         |[blank]|
      | programCode                  | MEDICAID                               |
      | planCode                     | RA40388-01-enrollmentDetails.planCode  |
      | planId                       | 145                                    |
      | subProgramTypeCode           | Alliance                               |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    And I update consumers benefit by consumerId "RA40388-01.consumerId" with data
      | [0].timeframe          | Active                 |
      | [0].consumerAction     | Plan Change Pre-lockin |
      | [0].changeAllowedFrom  | current                |
      | [0].changeAllowedUntil | 90daysFromToday        |
    Given I initiated enrollment letter API
    When I run enrollment letter API
      | mmisCode | 181 |
    And Wait for 3 seconds
    Then I verify new correspondences generated for consumerId "RA40388-01.consumerId" with data
      | [0].enrollmentId              | null              |
      | [0].caseId                    | RA40388-01.caseId |
      | [0].correspondenceTypeToMatch | 181               |
    And I verify correspondence details of correspondence id ""
      | healthPlan | RA40388-01-enrollmentDetails.planCode  |
      | date       | RA40388-01-enrollmentDetails.startDate |
      | pcp        | UNKNOWN                                |
    Given I initiated enrollment letter transaction status API
    When I run enrollment letter transaction status API
      | transactionType | 181     |
      | moduleName      | Letter  |
      | createdOn       | current |
    Then I verify enrollment letter transaction status API response
      | transactionType   | 181       |
      | moduleName        | Letter    |
      | createdOn         | current   |
      | updatedOn         | current   |
      | transactionStatus | Completed |

  @API-CP-40385 @API-CP-40386 @API-DC-EE @DC-EB-API-Regression @thilak
  Scenario Outline: DC EB:Alliance Packet to DCHF - 164 and Immigrant Children DCHF to Alliance Packet - 163
    # CP-40385-DC EB: (Immigrant Children) DCHF to Alliance Packet (#163) - EB
    # CP-40386-DC EB: Alliance to DCHF Packet (#164) - EB
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | yes                    |
      | otherSegments                | [blank]                |
      | isEnrollemntRequired         | no                     |
      | recordId                     | 25                     |
      | eligibilityRecordId          | 25                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | 1stDayofLastMonth      |
      | eligibilityStartDate         | 1stDayofLastMonth      |
      | eligibilityEndDate           | highDate-DC            |
      | txnStatus                    | Accepted               |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode_1> |
      | coverageCode                 | <coverageCode_1>                    |
    And I run create Eligibility and Enrollment API
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | yes                    |
      | otherSegments                | [blank]                |
      | isEnrollemntRequired         | no                     |
      | recordId                     | 25                     |
      | eligibilityRecordId          | 25                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | 1stDayofNextMonth      |
      | eligibilityStartDate         | 1stDayofNextMonth      |
      | eligibilityEndDate           | highDate-DC            |
      | txnStatus                    | Accepted               |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode_2> |
      | coverageCode                 | <coverageCode_2>       |
    And I run create Eligibility and Enrollment API
    Then I verify new correspondences generated for consumerId "<name>.consumerId " with data
      | [0].consumerId                | <name>.consumerId  |
      | [0].correspondenceTypeToMatch | <correspondenceId> |
    Examples:
      | name     | subProgramTypeCode_1 | subProgramTypeCode_2 | correspondenceId | coverageCode_1 | coverageCode_2 |
      | 40385-01 | ImmigrantChildren    | Alliance             | 163              | 420            | 470            |
      | 40386-01 | Alliance             | DCHF                 | 164              | 460            | 130            |