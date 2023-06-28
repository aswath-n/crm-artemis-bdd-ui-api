Feature: Create or Update Data for MMIS Sends Loss of Eligibility

  @API-CP-15760 @API-CP-15760-01 @API-CP-10430 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @alex
  Scenario: Update the consumer’s eligibility segment
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 15760-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15760-01.consumerId |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15760-01.consumerId |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 13                |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | current           |
      | eligibilityEndReason         | test              |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    And I initiated get benefit status by consumer id "15760-01.consumerId"
    And I run get enrollment api
#    Then I verify benefit status records are displayed for the consumer "<name>.consumerId"
    Then I verify scenario output in the benefit status records are displayed as "Loss of Eligibility","null"
    When I initiated get eligibility by consumer id ""
    And I run get eligibility api
    Then I verify that following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate | 1stDayofLastMonth |
      | eligibilityEndDate   | current           |
      | eligibilityEndReason | test              |
      | programCode          | M                 |
      | Created on           |[blank]|
      | createdBy            |[blank]|
      | Updated On           |[blank]|
      | updatedBy            |[blank]|
#    Then I will verify an "ELIGIBILITY_UPDATE_EVENT" for "DPBI" is created with fields in payload
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT |
      | consumerId    | 15760-01.consumerId        |
      | correlationId | null                     |
      | consumerFound | true                     |
      | DPBI          |[blank]|


  @API-CP-15760 @API-CP-15760-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @alex @api-smoke-devops
  Scenario: Decide Benefit Status (no action taken for loss of eligibility)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 15760-02 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15760-02.consumerId |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15760-02.consumerId |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 13                |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | current           |
      | eligibilityEndReason         | test              |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    And I initiated get benefit status by consumer id "15760-02.consumerId"
    And I run get enrollment api
#    Then I verify benefit status records are displayed for the consumer ""
    Then I verify scenario output in the benefit status records are displayed as "Loss of Eligibility","null"
    Then I verify benefit status records are displayed with benefit status "Not Eligible"

  @API-CP-15760 @API-CP-15760-03 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @alex
  Scenario: Re-determine Consumer Actions & Change Window
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 15760-03 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15760-03.consumerId |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15760-03.consumerId |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 13                |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | current           |
      | eligibilityEndReason         | test              |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    And I initiated get benefit status by consumer id "15760-03.consumerId"
    And I run get enrollment api
#    Then I verify benefit status records are displayed for the consumer " <name>.consumerId"
    Then I verify scenario output in the benefit status records are displayed as "Loss of Eligibility","null"
    Then I verify benefit status records are displayed with benefit status "Not Eligible"
    Then I Verify Consumer Actions are "Unavailable"

  @API-CP-15761 @API-CP-15761-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @alex
  Scenario: Decide Scenario is MMIS Sends Loss of Eligibility
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 15761-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15761-01.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15761-01.consumerId |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 13                |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth |
      | eligibilityEndDate           | future            |
      | eligibilityEndReason         | test              |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    And I initiated get benefit status by consumer id "15761-01.consumerId"
    And I run get enrollment api
#    Then I verify benefit status records are displayed for the consumer ""
    Then I verify benefit status records are displayed with benefit status "Eligible"
    When I initiated get eligibility by consumer id "15761-01.consumerId"
    And I run get eligibility api
    Then I verify that following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate | 1stDayofPresentMonth |
      | eligibilityEndReason | test                 |
      | programCode          | M                    |

  @API-CP-15759 @API-CP-15759-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @alex
  Scenario: Business Event Required and Optional Fields
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 15759-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15759-01.consumerId |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15759-01.consumerId |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 13                |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | current           |
      | eligibilityEndReason         | test              |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    And I initiated get benefit status by consumer id "15759-01.consumerId"
    And I run get enrollment api
#    Then I verify benefit status records are displayed for the consumer ""
    Then I verify scenario output in the benefit status records are displayed as "Loss of Eligibility","null"
    Then I will verify an "LOSS_OF_ELIGIBILITY" for "" is created with fields in payload
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT |
      | consumerId    | 15759-01.consumerId      |
      | consumerFound | true                     |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
    # 1.0 Business Event Required Fields
      | eventName                 | LOSS_OF_ELIGIBILITY               |
#      | correlationId        | null                              |
      | consumerId                | 15759-01.consumerId               |
      | channel                   | SYSTEM_INTEGRATION                |
      | createdBy                 | 597                               |
      | processDate               | current                           |
      | consumerName              | 15759-01                          |
      | externalCaseId            | 15759-01.caseIdentificationNumber |
      | externalConsumerId        | 15759-01.externalConsumerId       |
   # 2.0 Business Event Optional Fields
      | eligibilityStartDate      | 1stDayofLastMonth                 |
      | eligibilityEndDate        | current                           |
      | eligibilityEndReason      | test                              |
      | eligibilityCategoryCode   | null                              |
      | eligibilityProgramCode    | M                                 |
      | eligibilitySubProgramCode | MEDICAIDGF                        |
      | eligibilityCoverageCode   | string                            |
      | eligibilityExemptionCode  | qwer                              |
      | residentialCountyCode     |[blank]|
      | consumerPopulation        | MEDICAID-GENERAL                  |
      | benefitStatus             | Not Eligible                      |


 # Below scenario is merged with the above so removing regression tag
  @API-CP-15759 @API-CP-15759-02 @API-EE-ignore @API-CRM-Regression-ignore @eligibility-enrollment-ms-EE @alex
  Scenario: Business Event Optional Fields
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 13                |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | current           |
      | eligibilityEndReason         | test              |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
#    Then I verify benefit status records are displayed for the consumer ""
    Then I verify scenario output in the benefit status records are displayed as "Loss of Eligibility","null"
    Then I will verify an "LOSS_OF_ELIGIBILITY" for "" is created with fields in payload
    Then I will verify business events "LOSS_OF_ELIGIBILITY" are generated with
      | eligibilityStartDate      | 1stDayofLastMonth |
      | eligibilityEndReason      | test              |
      | eligibilityCoverageCode   | string            |
      | eligibilityProgramCode    | M                 |
      | eligibilitySubProgramCode | MEDICAIDGF        |
      | eligibilityExemptionCode  | qwer              |
      | residentialCountyCode     |[blank]|
      | consumerPopulation        | MEDICAID-GENERAL  |
      | benefitStatus             | Not Eligible      |

  @API-CP-15758 @API-CP-15758-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @alex
  Scenario: Inactive Enrollments (to include "Rejected" or "Disenrolled")
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 15758-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15758-01.consumerId |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | anniversaryDate              | future            |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15758-01.consumerId |
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 1739              |
      | anniversaryDate              | future            |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15758-01.consumerId |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 13                |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityEndDate           | current           |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndReason         | test              |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And Wait for 10 seconds
    And I initiated get benefit status by consumer id "15758-01.consumerId"
    And I run get enrollment api
#    Then I verify benefit status records are displayed for the consumer ""
    Then I verify scenario output in the benefit status records are displayed as "Loss of Eligibility","New Retroactive Enrollment"
    Then I will verify an "LOSS_OF_ELIGIBILITY" for "" is created with fields in payload
    Then I will verify business events "LOSS_OF_ELIGIBILITY" are generated with
      | eligibilityStartDate      | 1stDayofLastMonth |
      | eligibilityEndReason      | test              |
      | eligibilityCoverageCode   | string            |
      | eligibilityProgramCode    | M                 |
      | eligibilitySubProgramCode | MEDICAIDGF        |
      | eligibilityExemptionCode  | qwer              |
      | residentialCountyCode     |[blank]|
      | consumerPopulation        | MEDICAID-GENERAL  |
      | benefitStatus             | Not Eligible      |

  @API-CP-15758 @API-CP-15758-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @alex
  Scenario: Plan Changes sent to MMIS ("Submitted to State" & "Disenroll Submitted")
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 15758-02 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15758-02.consumerId  |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | anniversaryDate              | future            |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15758-02.consumerId  |
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 1739              |
      | anniversaryDate              | future            |
    And I run create Eligibility and Enrollment API
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 15758-02.consumerId   |
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
    And I initiated get enrollment by consumer id "15758-02.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "15758-02b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 15758-02.consumerId                |
      | [0].planId             | delete::                        |
      | [0].planCode           | 84                              |
      | [0].enrollmentId       | 15758-02b.discontinuedEnrollmentId |
      | [0].status             | ACCEPTED                        |
      | [0].txnStatus          | DISENROLL_SUBMITTED             |
      | [0].startDate          | fdofmnth::                      |
      | [0].endDate            | lstdaymnth::                    |
      | [0].enrollmentType     | delete::                        |
      | [0].subProgramTypeCode | MEDICAIDGF                      |
      | [0].serviceRegionCode  | East                            |
      | [1].planId             | delete::                        |
      | [1].planCode           | 85                              |
      | [1].consumerId         | 15758-02.consumerId                |
      | [1].enrollmentId       | 15758-02b.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                      |
      | [1].subProgramTypeCode | MEDICAIDGF                      |
      | [1].serviceRegionCode  | East                            |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15758-02.consumerId    |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 13                  |
      | eligibilityEndDate           | current             |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndReason         | PLAN_HAS_TERMINATED |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
    Then I send API call with name "15758-02a" for create Eligibility and Enrollment
    And Wait for 20 seconds
    And I initiated get benefit status by consumer id "15758-02.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Loss of Eligibility","New Retroactive Enrollment"
    Then I will verify business events are generated with data
      | eventName     | LOSS_OF_ELIGIBILITY |
#      | correlationId | 15758-02a.traceid      |
      | correlationId | null                |
      | consumerId    | 15758-02.consumerId |
      | consumerFound | true                |
    Then I will verify business events are generated with data
      | eventName     | DISENROLL_REQUESTED |
#      | correlationId | 15758-02a.traceid      |
      | correlationId | null                |
      | consumerId    | 15758-02.consumerId |
      | consumerFound | true                |
    Then I will verify business events are generated with data
      | eventName     | DISREGARDED_PLAN_CHANGE |
#      | correlationId | 15758-02a.traceid          |
      | correlationId | null                    |
      | consumerId    | 15758-02.consumerId     |
      | consumerFound | true                    |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
#      | correlationId | 15758-02a.traceid          |
      | correlationId | null                    |
      | consumerId    | 15758-02.consumerId     |
      | consumerFound | true                    |
      | DPBI          |[blank]|

  @API-CP-15758 @API-CP-15758-03 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @alex
  Scenario: Disenroll Requests sent to MMIS ("Disenroll Submitted")
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 15758-03 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15758-03.consumerId |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | anniversaryDate              | future            |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15758-03.consumerId |
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 1739              |
      | anniversaryDate              | future            |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15758-03.consumerId |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 13                |
      | eligibilityEndDate           | current           |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndReason         | test              |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    And I initiated get benefit status by consumer id "15758-03.consumerId"
    And I run get enrollment api
#    Then I verify benefit status records are displayed for the consumer ""
    Then I verify scenario output in the benefit status records are displayed as "Loss of Eligibility","New Retroactive Enrollment"
    And I will verify an "DISENROLL_REQUESTED" for "DPBI" is created with fields in payload

  @API-CP-15739 @API-CP-15739-01 @API-CP-15739-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario Outline: MMIS Sends Reinstatement of Eligibility benefit status records are displayed as "Reinstatement of Eligibility" or "Not Found"
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    Then I send API call with name "<name>" for create Eligibility and Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 13                |
      | eligibilityEndDate           | current           |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndReason         | test              |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Loss of Eligibility","null"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 21                |
      | eligibilityEndDate           | yesterday         |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndReason         |[blank]|
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 11                |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | <date>            |
      | eligibilityEndReason         |[blank]|
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "<enrollmentScenario>","<eligibilityScenario>"
    Examples: status records are displayed according to enrollment and eligibility information
      | name | date              | enrollmentScenario           | eligibilityScenario |
      | QN1  | current           | Reinstatement of Eligibility | null                |
      | QN2  | 1stDayofNextMonth | Not Found                    | Not Found           |

  @API-CP-10727 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: MMIS Sends Reinstatement of Eligibility and able to save the event of benefits status of "Eligible"
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    Then I send API call with name "QM1" for create Eligibility and Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | QM1.consumerId    |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 13                |
      | eligibilityEndDate           | current           |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndReason         | test              |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "QM1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Loss of Eligibility","null"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | QM1.consumerId    |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 21                |
      | eligibilityEndDate           | yesterday         |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndReason         |[blank]|
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | QM1.consumerId |
      | isEligibilityRequired        | yes            |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no             |
      | recordId                     | 11             |
      | isEnrollmentProviderRequired | no             |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | current        |
      | eligibilityEndReason         | null           |
      | eligibilityStatusCode        | xyz            |
      | txnStatus                    | Accepted       |
      | programCode                  | M              |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "QM1.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status records are displayed with benefit status "Eligible"

  @API-CP-15738 @API-CP-15738-01 @API-CP-15738-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario Outline: MMIS Sends Reinstatement of Eligibility and able to accurately reflect a consumer’s Business Event eligibility history.
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "<populationType>" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId           |
      | isEligibilityRequired        | yes                         |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                          |
      | recordId                     | 3                           |
      | isEnrollmentProviderRequired | no                          |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth           |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted                    |
      | programCode                  | <eligibilityProgramCode>    |
      | subProgramTypeCode           | <eligibilitySubProgramCode> |
      | anniversaryDate              | anniversaryDate             |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId           |
      | isEligibilityRequired        | yes                         |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                          |
      | recordId                     | 13                          |
      | eligibilityEndDate           | current                     |
      | isEnrollmentProviderRequired | no                          |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth           |
      | eligibilityEndReason         | test                        |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted                    |
      | programCode                  | <eligibilityProgramCode>    |
      | subProgramTypeCode           | <eligibilitySubProgramCode> |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Loss of Eligibility","null"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId           |
      | isEligibilityRequired        | yes                         |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                          |
      | recordId                     | 21                          |
      | eligibilityEndDate           | yesterday                   |
      | isEnrollmentProviderRequired | no                          |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth           |
      | eligibilityEndReason         |[blank]|
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted                    |
      | programCode                  | <eligibilityProgramCode>    |
      | subProgramTypeCode           | <eligibilitySubProgramCode> |
      | channel                      | SYSTEM_INTEGRATION          |
    And I run create Eligibility and Enrollment API
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId           |
      | isEligibilityRequired        | yes                         |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                          |
      | recordId                     | 11                          |
      | isEnrollmentProviderRequired | no                          |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | current                     |
      | eligibilityEndReason         | null                        |
      | eligibilityStatusCode        | xyz                         |
      | txnStatus                    | Accepted                    |
      | programCode                  | <eligibilityProgramCode>    |
      | subProgramTypeCode           | <eligibilitySubProgramCode> |
      | channel                      | SYSTEM_INTEGRATION          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName                 | REINSTATEMENT_OF_ELIGIBILITY    |
      | channel                   | <channel>                       |
      | createdBy                 | 597                             |
      | processDate               | current                         |
      | externalCaseId            | <name>.caseIdentificationNumber |
      | externalConsumerId        | <name>.externalConsumerId       |
      | consumerId                | <name>.consumerId               |
      | consumerName              | <name>                          |
      #  @API-CP-15738-02
      | eligibilityStartDate      | current                         |
      | eligibilityEndDate        | null                            |
      | eligibilityEndReason      | null                            |
      | eligibilityCategoryCode   | null                            |
      | eligibilityProgramCode    | <eligibilityProgramCode>        |
      | eligibilitySubProgramCode | <eligibilitySubProgramCode>     |
      | eligibilityCoverageCode   | string                          |
      | eligibilityExemptionCode  | qwer                            |
      | consumerPopulation        | <populationType>                |
      | benefitStatus             | Eligible                        |
    Examples: Population types:
      | name | projectName | channel            | populationType   | eligibilityProgramCode | eligibilitySubProgramCode |
      | ER1  |[blank]| Auto Assignment    | MEDICAID-GENERAL | M                      | MEDICAIDGF                |
      | ER2  |[blank]| SYSTEM_INTEGRATION | PREGNANT-WOMEN   | H                      | MEDICAIDMCHB              |
