Feature: DCEB Business Events Feature

  @API-CP-39554 @shruti  @API-DC-EE @DC-EB-API-Regression
  Scenario Outline:A.C 1.0- CP-39554- Default Eligibility Business Event
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>       |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 25                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | past                 |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | <eligibilityEndDate> |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | categoryCode                 | <categoryCode>       |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation | <population>    |
      | population        | <population>    |
      | benefitStatus     | <benefitStatus> |
    And Wait for 5 seconds
    Then I will verify business events are generated with data
    # 1.0 Business Event Required Fields
      | eventName                 | DEFAULT_ELIGIBILITY  |
      | consumerId                | <name>.consumerId    |
#     | correlationId      | <name>a.eligibilities.[0].coreEligibility.correlationId |
      | channel                   | SYSTEM_INTEGRATION   |
      | createdBy                 | 597                  |
      | processDate               | current              |
      | consumerName              | <name>               |
      | consumerPopulation        | <population>         |
     # Business Event Optional Fields
      | eligibilityStartDate      | 1stDayofPresentMonth |
      | eligibilityEndDate        | <eligibilityEndDate> |
      | eligibilityEndReason      | null                 |
      | eligibilityCategoryCode   | <categoryCode>       |
      | eligibilityProgramCode    | MEDICAID             |
      | eligibilitySubProgramCode | <subProgramTypeCode> |
      | eligibilityCoverageCode   | <coverageCode>       |
      | eligibilityExemptionCode  | qwer                 |
      | benefitStatus             | <benefitStatus>      |
    Examples:
      | name     | eligibilityEndDate | coverageCode | population         | subProgramTypeCode | benefitStatus        | categoryCode |
      | 39554-02 | highDate-DC        | 420          | Immigrant Children | ImmigrantChildren  | Eligible             | 10           |
      | 39554-03 | highDate-DC        | 420F         | Immigrant Children | ImmigrantChildren  | Eligible             | 11           |
      | 39554-04 | highDate-DC        | 460          | Alliance-Child     | Alliance           | Eligible             | 12           |
      | 39554-05 | highDate-DC        | 470          | Alliance-Adult     | Alliance           | Eligible             | 13           |
      | 39554-06 | highDate-DC        | 470Q         | Alliance-Adult     | Alliance           | Eligible             | 14           |
      | 39554-07 | highDate-DC        | 470Z         | Alliance-Adult     | Alliance           | Eligible             | 15           |
      | 39554-08 | lastDayofNextMonth | 470Z         | Alliance-Adult     | Alliance           | Eligible             | 10           |

  @API-CP-39005 @API-DC-EE @DC-EB-API-Regression @priyal
  Scenario: Create Business Event Plan Change Accept Response - DCHF
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 39005-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39005-01.consumerId |
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
    Then I send API call with name "39005-01a" for update Enrollment information
      | [0].consumerId         | 39005-01.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 081080400           |
      | [0].startDate          | fdofmnth::          |
      | [0].endDate            | highdatedc::        |
      | [0].programTypeCode    | MEDICAID            |
      | [0].subProgramTypeCode | DCHF                |
      | [0].serviceRegionCode  | Northeast           |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "39005-01a" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | fdofmnth::             |
      | [0].planId       | delete::               |
      | [0].planCode     | 081080400              |
      | [0].consumerId   | 39005-01.consumerId    |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39005-01.consumerId  |
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
      | [0].consumerId         | 39005-01.consumerId |
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
    And I initiated get enrollment by consumer id "39005-01.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "39005-01b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 39005-01.consumerId                |
      | [0].planId             | delete::                           |
      | [0].planCode           | 081080400                          |
      | [0].enrollmentId       | 39005-01b.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED                |
      | [0].txnStatus          | DISENROLL_SUBMITTED                |
      | [0].startDate          | fdofmnth::                         |
      | [0].endDate            | lstdaymnth::                       |
      | [0].enrollmentType     | delete::                           |
      | [0].subProgramTypeCode | DCHF                               |
      | [0].serviceRegionCode  | Northeast                          |
      | [1].planId             | delete::                           |
      | [1].planCode           | 055558200                          |
      | [1].consumerId         | 39005-01.consumerId                |
      | [1].enrollmentId       | 39005-01b.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                         |
      | [1].endDate            | highdatedc::                       |
      | [1].subProgramTypeCode | DCHF                               |
      | [1].serviceRegionCode  | Northeast                          |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39005-01.consumerId |
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
      | genericFieldText1 | 39005-01b.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                      |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I will verify business events are generated with data
    # CP-39005 1.0 Plan Change Business Event Required Fields
      | eventName                | PLAN_CHANGE_ACCEPT_RESPONSE|
      | channel                  | SYSTEM_INTEGRATION|
      | createdBy                | 597|
      | processDate              | current|
      | consumerId               | 39005-01.consumerId|
      | consumerName             | 39005-01|
    # CP-39005 2.0 Plan Change Business Event Optional Enrollment fields from “Prior Enrollment”
      | previousEnrollment.enrollmentStartDate | 1stDayofPresentMonth             |
      | previousEnrollment.enrollmentEndDate   | lastDayofPresentMonth            |
      | previousEnrollment.planSelectionReason | null                             |
      | previousEnrollment.planChangeReason    | null                             |
      | previousEnrollment.rejectionReason     | null                             |
      | previousEnrollment.planCode            | 081080400                        |
      | previousEnrollment.enrollmentType      | MEDICAL                          |
      | previousEnrollment.selectionStatus     | DISENROLLED                      |
      | previousEnrollment.anniversaryDate     | anniversaryDateDC                |
      | previousEnrollment.pcpNpi              | null                             |
      | previousEnrollment.pcpName             | null                             |
      | previousEnrollment.pdpNpi              | null                             |
      | previousEnrollment.pdpName             | null                             |
      # CP-39005 3.0 Plan Change Business Event Optional Enrollment fields from “Requested Enrollment”
      | requestedSelection.enrollmentStartDate | 1stDayofNextMonth                |
      | requestedSelection.enrollmentEndDate   | highDate-DC                      |
      | requestedSelection.planSelectionReason | null                             |
      | requestedSelection.planChangeReason    | null                             |
      | requestedSelection.rejectionReason     | null                             |
      | requestedSelection.planCode            | 055558200                        |
      | requestedSelection.enrollmentType      | MEDICAL                          |
      | requestedSelection.selectionStatus     | ACCEPTED                         |
      | requestedSelection.anniversaryDate     | anniversaryDateDCPlanChange      |
      | requestedSelection.pcpNpi              | null                             |
      | requestedSelection.pcpName             | null                             |
      | requestedSelection.pdpNpi              | null                             |
      | requestedSelection.pdpName             | null                             |

  @API-CP-39435 @API-CP-39435-01 @priyal @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: Verify ELIGIBILITY_SAVE_EVENT event for DP4BI to consume
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo     | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>       |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 25                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | past                 |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | <eligibilityEndDate> |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | <subProgramTypeCode> |
    And I run create Eligibility and Enrollment API
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | programCode           | MEDICAID|
      | coverageCode          | <coverageCode>|
      | subProgramTypeCode    | <subProgramTypeCode>|
      | eligibilityStartDate  | 1stDayofPresentMonth|
      | eligibilityEndDate    | <eligibilityEndDate>|
      | Created on            | current|
      | createdBy             | 597|
      | Updated On            | current|
      | updatedBy             | 597|
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                                  |
      | consumerId    | <name>.consumerId                                       |
#      | correlationId | <name>a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                    |
      | DPBI          |[blank]|
    Examples:
      | name     | eligibilityEndDate | coverageCode | subProgramTypeCode |
      # CP-39435 AC1.0 Create or Update Data - MMIS Sends Default Eligibility Scenario
      # CP-39435 AC2.0 Publish an ELIGIBILITY_SAVE_EVENT event for DP4BI to consume
      | 39435-01 | highDate-DC        | 420         | ImmigrantChildren  |
      | 39436-03 | highDate-DC        | 420F        | ImmigrantChildren  |
      | 39436-04 | highDate-DC        | 460         | Alliance           |
      | 39436-05 | highDate-DC        | 470         | Alliance           |
      | 39436-06 | highDate-DC        | 470Q        | Alliance           |
      | 39436-07 | highDate-DC        | 470Z        | Alliance           |

  @API-CP-39009 @API-CP-39009-01 @API-DC-EE @DC-EB-API-Regression @priyal
  Scenario: Verify Capture Business Event - Plan Change (pre-lockin & anniversary)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-above60" with data
      | saveConsumerInfo | 39009-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39009-01.consumerId |
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
    Then I send API call with name "39009-01a" for update Enrollment information
      | [0].consumerId         | 39009-01.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 081080400           |
#      | [0].startDate          | fdnxtmth::          |
      | [0].startDate          | fdofmnth::          |
      | [0].endDate            | highdatedc::        |
      | [0].programTypeCode    | MEDICAID            |
      | [0].subProgramTypeCode | DCHF                |
      | [0].serviceRegionCode  | Northeast           |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "39009-01a" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | fdofmnth::             |
#      | [0].startDate    | fdnxtmth::             |
      | [0].planId       | delete::               |
      | [0].planCode     | 081080400              |
      | [0].consumerId   | 39009-01.consumerId    |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39009-01.consumerId |
      | isEligibilityRequired        | no                  |
      | isEnrollemntRequired         | yes                 |
      | isEnrollmentProviderRequired | no                  |
      | recordId                     | 15                  |
      | enrollmentRecordId           | 15                  |
#      | enrollmentStartDate          | 1stDayofNextMonth   |
      | enrollmentStartDate          | 1stDayofPresentMonth|
      | enrollmentEndDate            | highDate-DC         |
      | anniversaryDate              | anniversaryDate     |
      | eligibilityStartDate         |[blank]|
      | programCode                  | MEDICAID            |
      | planCode                     | 081080400           |
      | planId                       | 145                 |
      | subProgramTypeCode           | DCHF                |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    When I searched consumer created through api with First Name as "39009-01.firstName" and Last Name as "39009-01.lastName"
    And I click the first consumer id from the search results
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And Wait for 10 seconds
    And I click "Plan Change" Button for a consumer
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    Then I will verify business events are generated with data
    # CP-39009 1.0 Business Event Required Fields
      | eventName                | PLAN_CHANGE|
      | channel                  | PHONE|
      | createdBy                | 9056|
      | processDate              | current|
      | consumerId               | 39009-01.consumerId|
      | consumerName             | 39009-01|
    # CP-39009 2.0 Plan Change Business Event Optional Enrollment fields from “Prior Enrollment”
      | previousEnrollment.enrollmentStartDate | 1stDayofPresentMonth             |
      | previousEnrollment.enrollmentEndDate   | dayBeforeCutOffDateDCHF             |
      | previousEnrollment.planSelectionReason | null                             |
      | previousEnrollment.planChangeReason    | 90_DAY_TRANSFER                  |
      | previousEnrollment.rejectionReason     | null                             |
      | previousEnrollment.planCode            | 081080400                        |
      | previousEnrollment.enrollmentType      | MEDICAL                          |
      | previousEnrollment.selectionStatus     | DISENROLL_REQUESTED              |
      | previousEnrollment.anniversaryDate     | anniversaryDateDC                |
      | previousEnrollment.pcpNpi              | null                             |
      | previousEnrollment.pcpName             | null                             |
      | previousEnrollment.pdpNpi              | null                             |
      | previousEnrollment.pdpName             | null                             |
      # CP-39009 3.0 Plan Change Business Event Optional Enrollment fields from “Requested Enrollment”
      | requestedSelection.enrollmentStartDate | cutOffDateDCHF                          |
      | requestedSelection.enrollmentEndDate   | highDate-DC                      |
      | requestedSelection.planSelectionReason | null                             |
      | requestedSelection.planChangeReason    | null                             |
      | requestedSelection.rejectionReason     | null                             |
      | requestedSelection.planCode            | getFromUISelected                |
      | requestedSelection.enrollmentType      | MEDICAL                          |
      | requestedSelection.selectionStatus     | SELECTION_MADE                   |
      | requestedSelection.anniversaryDate     | anniversaryDateDC                |
      | requestedSelection.pcpNpi              | null                             |
      | requestedSelection.pcpName             | null                             |
      | requestedSelection.pdpNpi              | null                             |
      | requestedSelection.pdpName             | null                             |