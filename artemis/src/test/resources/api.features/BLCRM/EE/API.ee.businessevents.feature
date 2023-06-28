Feature: Verify MMIS triggered business events are generated and validate payload

  @API-CP-14126  @API-CP-14126-01 @API-CP-11478-01 @API-CP-36993 @API-CP-36993-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario:Verify New Enrollment Business Event is created
  CP-36993 DC EB/ BLCRM Capture Business Event for New Eligibility for MMIS sends Default Eligibility
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 14126 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14126.consumerId  |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 1                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         | 1stDayofNextMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planId                       | 1880              |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    Then I will verify business events are generated with data
    # CP-36993 1.0 Business Event Required Fields
      | eventName                | DEFAULT_ELIGIBILITY   |
      | channel                  | SYSTEM_INTEGRATION    |
      | createdBy                | 597                   |
      | processDate              | current               |
      | consumerId               | 14126.consumerId      |
      | consumerName             | 14126                 |
      | consumerFound            | true                  |
    # CP-36993 2.0 Business Event Optional Fields
#      | outCome                  | <outCome>             |
      | outCome                  | New Eligibility       |
      | eligibilityStartDate     | 1stDayofNextMonth     |
      | eligibilityEndDate       | null                  |
      | eligibilityEndReason     | null                  |
      | eligibilityCategoryCode  | null                  |
      | eligibilityProgramCode   | M                     |
      | eligibilityCoverageCode  | string                |
      | eligibilityExemptionCode | qwer                  |
      | consumerPopulation       | MEDICAID-GENERAL      |
      | benefitStatus            | Eligible              |
    And I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14126.consumerId  |
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 2                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         | 1stDayofNextMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planId                       | 1880              |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName          | NEW_ENROLLMENT                 |
      | channel            | SYSTEM_INTEGRATION             |
      | createdBy          | 597                            |
      | processDate        | current                        |
      | externalCaseId     | 14126.caseIdentificationNumber |
      | externalConsumerId | 14126.externalConsumerId       |
      | consumerId         | 14126.consumerId               |
      | consumerName       | 14126                          |

  @API-CP-14126 @API-CP-14126-02 @API-CP-11478-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario:Verify New Retro active Enrollment Business Events are created
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 14126-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14126-1.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14126-1.consumerId   |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
      | planId                       | 145                  |
      | serviceRegionCode            | East                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName          | NEW_RETROACTIVE_ENROLLMENT       |
      | channel            | SYSTEM_INTEGRATION               |
      | createdBy          | 597                              |
      | processDate        | current                          |
      | externalCaseId     | 14126-1.caseIdentificationNumber |
      | externalConsumerId | 14126-1.externalConsumerId       |
      | consumerId         | 14126-1.consumerId               |
      | consumerName       | 14126-1                          |

  @API-CP-14613 @API-CP-14613-01 @API-CP-14613-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: Enrollment Broker captures the MMIS New Enrollment Accept Response Business Event
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | BN1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | [0].consumerId               | BN1.consumerId       |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | MEDICAIDMCHB         |
      | anniversaryDate              | anniversaryDate      |
      | serviceRegionCode            | East                 |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Then I send API call with name "BN2" for update Enrollment information
      | [0].consumerId         | BN1.consumerId    |
      | [0].planId             | 145               |
      | [0].planCode           | 84                |
      | [0].startDate          | fdnxtmth::        |
      | [0].programTypeCode    | H                 |
      | [0].subProgramTypeCode | MEDICAIDMCHB      |
      | [0].anniversaryDate    | anniversaryDate:: |
    And I send API call with name "BN3" for process Outbound Update
      | [0].planId             | delete::               |
      | [0].planCode           | 84                     |
      | [0].consumerId         | BN1.consumerId         |
      | [0].enrollmentId       | c.data[0].enrollmentId |
      | [0].startDate          | fdnxtmth::             |
      | [0].programTypeCode    | H                      |
      | [0].subProgramTypeCode | MEDICAIDMCHB           |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | BN1.consumerId    |
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | isEnrollmentProviderRequired | no                |
      | recordId                     | 15                |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         |[blank]|
      | anniversaryDate              | anniversaryDate   |
      | txnStatus                    | Accepted          |
      | programCode                  | H                 |
      | subProgramTypeCode           | MEDICAIDMCHB      |
      | planCode                     | 84                |
      | planId                       | 145               |
      | serviceRegionCode            | East              |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "BN1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","New Enrollment Accept"
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName           | NEW_ENROLLMENT_ACCEPT_RESPONSE |
      | channel             | SYSTEM_INTEGRATION             |
      | createdBy           | 597                            |
      | processDate         | current                        |
      | externalCaseId      | BN1.caseIdentificationNumber   |
      | externalConsumerId  | BN1.externalConsumerId         |
      | consumerId          | BN1.consumerId                 |
      | consumerName        | BN1                            |
      #  @API-CP-14613-02
      | enrollmentStartDate | 1stDayofNextMonth              |
      | enrollmentEndDate   | null                           |
      | planChangeReason    | null                           |
      | planCode            | 84                             |
      | enrollmentType      | MEDICAL                        |
      | selectionStatus     | ACCEPTED                       |
      | consumerPopulation  | PREGNANT-WOMEN                 |
      | benefitStatus       | Enrolled                       |

  @API-CP-15060 @API-CP-15060-01 @API-CP-15060-02 @API-CP-15060-03 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: Verify PLAN_CHANGE_REJECT_RESPONSE event after MMIS sends Plan Change Reject Response
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | QW1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | [0].consumerId               | QW1.consumerId       |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | [0].consumerId               | QW1.consumerId       |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
      | planId                       | 145                  |
      | serviceRegionCode            | East                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | QW1.consumerId     |
      | [0].planId             | 145                |
      | [0].planCode           | 85                 |
      | [0].startDate          | fdnxtmth::         |
      | [0].subProgramTypeCode | MEDICAIDGF         |
      | [0].serviceRegionCode  | East               |
      | [0].selectionReason    | delete::           |
      | [0].anniversaryDate    | anniversaryDate::  |
      | [0].channel            | SYSTEM_INTEGRATION |
      | [0].planEndDateReason  | PLAN_TERMINATED    |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "QW1.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "QW1a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | QW1.consumerId                |
      | [0].planId             | delete::                      |
      | [0].planCode           | 84                            |
      | [0].enrollmentId       | QW1a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED           |
      | [0].txnStatus          | DISENROLL_SUBMITTED           |
      | [0].startDate          | fdofmnth::                    |
      | [0].endDate            | lstdaymnth::                  |
      | [0].enrollmentType     | delete::                      |
      | [0].subProgramTypeCode | MEDICAIDGF                    |
      | [0].serviceRegionCode  | East                          |
      | [1].planId             | delete::                      |
      | [1].planCode           | 85                            |
      | [1].consumerId         | QW1.consumerId                |
      | [1].enrollmentId       | QW1a.selectedEnrollmentId     |
      | [1].status             | SUBMITTED_TO_STATE            |
      | [1].txnStatus          | SUBMITTED_TO_STATE            |
      | [1].startDate          | fdnxtmth::                    |
      | [1].subProgramTypeCode | MEDICAIDGF                    |
      | [1].serviceRegionCode  | East                          |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | QW1.consumerId           |
      | isEligibilityRequired        | no                       |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | isEnrollmentProviderRequired | no                       |
      | recordId                     | 18                       |
      | enrollmentStartDate          | 1stDayofNextMonth        |
      | anniversaryDate              | anniversaryDate          |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted                 |
      | programCode                  | M                        |
      | planCode                     | 85                       |
      | planId                       | 145                      |
      | serviceRegionCode            | East                     |
      | rejectionReason              | Consumer is not eligible |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "QW1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","Plan Change Reject"
    And Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName                              | PLAN_CHANGE_REJECT_RESPONSE  |
      | channel                                | SYSTEM_INTEGRATION           |
      | createdBy                              | 597                          |
      | processDate                            | current                      |
      | externalCaseId                         | QW1.caseIdentificationNumber |
      | externalConsumerId                     | QW1.externalConsumerId       |
      | consumerId                             | QW1.consumerId               |
      | consumerName                           | QW1                          |
      #  @API-CP-15060-02
      | consumerPopulation                     | MEDICAID-GENERAL             |
      | benefitStatus                          | Enrolled                     |
      #  @API-CP-15060-03
      | previousEnrollment.enrollmentStartDate | 1stDayofPresentMonth         |
      | previousEnrollment.enrollmentEndDate   | null                         |
      | previousEnrollment.planChangeReason    | null                         |
      | previousEnrollment.rejectionReason     | null                         |
      | previousEnrollment.planCode            | 84                           |
      | previousEnrollment.enrollmentType      | MEDICAL                      |
      | previousEnrollment.selectionStatus     | ACCEPTED                     |
      | requestedSelection.enrollmentStartDate | 1stDayofNextMonth            |
      | requestedSelection.enrollmentEndDate   | null                         |
      | requestedSelection.planChangeReason    | null                         |
      | requestedSelection.rejectionReason     | Consumer is not eligible     |
      | requestedSelection.planCode            | 85                           |
      | requestedSelection.enrollmentType      | MEDICAL                      |
      | requestedSelection.selectionStatus     | REJECTED                     |

  @API-CP-15061 @API-CP-15061-01 @API-CP-15061-02 @API-CP-15061-03 @API-CP-15061-04 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: Create or Update Enrollment Data based on Plan Change Reject Response Scenario
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "WE1" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | WE1.consumerId       |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
      | planId                       | 145                  |
      | serviceRegionCode            | East                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | WE1.consumerId     |
      | [0].planId             | 145                |
      | [0].planCode           | 85                 |
      | [0].startDate          | fdnxtmth::         |
      | [0].subProgramTypeCode | MEDICAIDGF         |
      | [0].serviceRegionCode  | East               |
      | [0].selectionReason    | delete::           |
      | [0].anniversaryDate    | anniversaryDate::  |
      | [0].channel            | SYSTEM_INTEGRATION |
      | [0].planEndDateReason  | PLAN_TERMINATED    |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "WE1.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "WE1a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | WE1.consumerId                |
      | [0].planId             | delete::                      |
      | [0].planCode           | 84                            |
      | [0].enrollmentId       | WE1a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED           |
      | [0].txnStatus          | DISENROLL_SUBMITTED           |
      | [0].startDate          | fdofmnth::                    |
      | [0].endDate            | lstdaymnth::                  |
      | [0].enrollmentType     | delete::                      |
      | [0].subProgramTypeCode | MEDICAIDGF                    |
      | [0].serviceRegionCode  | East                          |
      | [1].planId             | delete::                      |
      | [1].planCode           | 85                            |
      | [1].consumerId         | WE1.consumerId                |
      | [1].enrollmentId       | WE1a.selectedEnrollmentId     |
      | [1].status             | SUBMITTED_TO_STATE            |
      | [1].txnStatus          | SUBMITTED_TO_STATE            |
      | [1].startDate          | fdnxtmth::                    |
      | [1].subProgramTypeCode | MEDICAIDGF                    |
      | [1].serviceRegionCode  | East                          |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | WE1.consumerId           |
      | isEligibilityRequired        | no                       |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | isEnrollmentProviderRequired | no                       |
      | recordId                     | 18                       |
      | enrollmentStartDate          | 1stDayofNextMonth        |
      | anniversaryDate              | anniversaryDate          |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted                 |
      | programCode                  | M                        |
      | planCode                     | 85                       |
      | planId                       | 145                      |
      | serviceRegionCode            | East                     |
      | rejectionReason              | Consumer is not eligible |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "WE1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","Plan Change Reject"
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName           | ENROLLMENT_UPDATE_EVENT |
      | consumerId          | WE1.consumerId          |
      | enrollmentStartDate | 1stDayofNextMonth       |
      | enrollmentEndDate   |[blank]|
      | enrollmentType      | MEDICAL                 |
      | channel             | SYSTEM_INTEGRATION      |
      | anniversaryDate     | anniversaryDate         |
      #  @API-CP-15061-02
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | WE1.consumerId     |
      | [0].planId             | 145                |
      | [0].planCode           | 84                 |
      | [0].startDate          | fdnxtmth::         |
      | [0].subProgramTypeCode | MEDICAIDGF         |
      | [0].serviceRegionCode  | East               |
      | [0].selectionReason    | delete::           |
      | [0].anniversaryDate    | anniversaryDate::  |
      | [0].channel            | SYSTEM_INTEGRATION |
      | [0].planEndDateReason  | PLAN_TERMINATED    |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "WE1.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "WE1a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | WE1.consumerId                |
      | [0].planId             | delete::                      |
      | [0].planCode           | 85                            |
      | [0].enrollmentId       | WE1a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED           |
      | [0].txnStatus          | DISENROLL_SUBMITTED           |
      | [0].startDate          | fdofmnth::                    |
      | [0].endDate            | lstdaymnth::                  |
      | [0].enrollmentType     | delete::                      |
      | [0].subProgramTypeCode | MEDICAIDGF                    |
      | [0].serviceRegionCode  | East                          |
      | [1].planId             | delete::                      |
      | [1].planCode           | 84                            |
      | [1].consumerId         | WE1.consumerId                |
      | [1].enrollmentId       | WE1a.selectedEnrollmentId     |
      | [1].status             | SUBMITTED_TO_STATE            |
      | [1].txnStatus          | SUBMITTED_TO_STATE            |
      | [1].startDate          | fdnxtmth::                    |
      | [1].subProgramTypeCode | MEDICAIDGF                    |
      | [1].serviceRegionCode  | East                          |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | WE1.consumerId    |
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | isEnrollmentProviderRequired | no                |
      | recordId                     | 15                |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         |[blank]|
      | anniversaryDate              | anniversaryDate   |
      | txnStatus                    | Accepted          |
      | programCode                  | H                 |
      | subProgramTypeCode           | MEDICAIDMCHB      |
      | planCode                     | 84                |
      | planId                       | 145               |
    And I run create Eligibility and Enrollment API
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName           | ENROLLMENT_UPDATE_EVENT |
      | consumerId          | WE1.consumerId          |
      | enrollmentStartDate | 1stDayofNextMonth       |
      | enrollmentType      | MEDICAL                 |
      | channel             | SYSTEM_INTEGRATION      |
      | anniversaryDate     | anniversaryDate         |
      #  @API-CP-15061-03
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "WE1.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status records are displayed with benefit status "Enrolled"
    #  @API-CP-15061-04
    Then I Verify Consumer Actions as following data
      | [0].action               | Available               |
      | [0].consumerAction       | Plan Change Anniversary |
      | [0].planSelectionAllowed | true                    |
      | [1].action               | Available               |
      | [1].consumerAction       | Plan Change Pre-lockin  |
      | [1].planSelectionAllowed | true                    |

  @API-CP-15062 @API-CP-15062-01 @API-CP-15062-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario Outline: Decide Plan Change Reject Response Scenario
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "<name>" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
      | planId                       | 145                  |
      | serviceRegionCode            | East                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | <name>.consumerId  |
      | [0].planId             | 145                |
      | [0].planCode           | 85                 |
      | [0].startDate          | fdnxtmth::         |
      | [0].subProgramTypeCode | MEDICAIDGF         |
      | [0].serviceRegionCode  | East               |
      | [0].selectionReason    | delete::           |
      | [0].anniversaryDate    | anniversaryDate::  |
      | [0].channel            | SYSTEM_INTEGRATION |
      | [0].planEndDateReason  | PLAN_TERMINATED    |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "<name>.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "<name>a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | <name>.consumerId                |
      | [0].planId             | delete::                         |
      | [0].planCode           | 84                               |
      | [0].enrollmentId       | <name>a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED              |
      | [0].txnStatus          | DISENROLL_SUBMITTED              |
      | [0].startDate          | fdofmnth::                       |
      | [0].endDate            | lstdaymnth::                     |
      | [0].enrollmentType     | delete::                         |
      | [0].subProgramTypeCode | MEDICAIDGF                       |
      | [0].serviceRegionCode  | East                             |
      | [1].planId             | delete::                         |
      | [1].planCode           | 85                               |
      | [1].consumerId         | <name>.consumerId                |
      | [1].enrollmentId       | <name>a.selectedEnrollmentId     |
      | [1].status             | SUBMITTED_TO_STATE               |
      | [1].txnStatus          | SUBMITTED_TO_STATE               |
      | [1].startDate          | fdnxtmth::                       |
      | [1].subProgramTypeCode | MEDICAIDGF                       |
      | [1].serviceRegionCode  | East                             |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | no                       |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | isEnrollmentProviderRequired | no                       |
      | recordId                     | 18                       |
      | enrollmentStartDate          | <enrollmentStartDate>    |
      | anniversaryDate              | anniversaryDate          |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted                 |
      | programCode                  | M                        |
      | planCode                     | <planCode>               |
      | planId                       | 145                      |
      | serviceRegionCode            | East                     |
      | rejectionReason              | Consumer is not eligible |
    And Wait for 5 seconds
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "<eligibilityScenario>","<enrollmentScenario>"
    Examples: plan change selection (“Submitted to State” status) conditions:
      | name    | planCode | enrollmentStartDate  | enrollmentScenario | eligibilityScenario         |
      | 15062-1 | 85       | 1stDayofNextMonth    | Plan Change Reject | New Retroactive Eligibility |
      | 15062-2 | 84       | 1stDayofNextMonth    | Not Found          | Not Found                   |
      | 15062-3 | 85       | 1stDayofPresentMonth | Not Found          | Not Found                   |