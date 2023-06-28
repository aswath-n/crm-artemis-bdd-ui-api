Feature: Rejected Enrollment updates Part 1

  @API-CP-15055 @API-CP-15055-01 @API-CP-15055-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: Capture Disenroll Request Reject Response Business Event
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 15055-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15055-01.consumerId  |
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
      | consumerId                   | 15055-01.consumerId  |
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
      | [0].consumerId         | 15055-01.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 85                  |
      | [0].startDate          | fdnxtmth::          |
      | [0].subProgramTypeCode | MEDICAIDGF          |
      | [0].serviceRegionCode  | East                |
      | [0].selectionReason    | delete::            |
      | [0].anniversaryDate    | anniversaryDate::   |
      | [0].channel            | SYSTEM_INTEGRATION  |
      | [0].planEndDateReason  | PLAN_TERMINATED     |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "15055-01.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "15055-01b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 15055-01.consumerId                |
      | [0].planId             | delete::                           |
      | [0].planCode           | 84                                 |
      | [0].enrollmentId       | 15055-01b.discontinuedEnrollmentId |
      | [0].status             | ACCEPTED                           |
      | [0].txnStatus          | DISENROLL_SUBMITTED                |
      | [0].startDate          | fdofmnth::                         |
      | [0].endDate            | lstdaymnth::                       |
      | [0].enrollmentType     | delete::                           |
      | [0].subProgramTypeCode | MEDICAIDGF                         |
      | [0].serviceRegionCode  | East                               |
      | [1].planId             | delete::                           |
      | [1].planCode           | 85                                 |
      | [1].consumerId         | 15055-01.consumerId                |
      | [1].enrollmentId       | 15055-01b.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                         |
      | [1].subProgramTypeCode | MEDICAIDGF                         |
      | [1].serviceRegionCode  | East                               |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15055-01.consumerId      |
      | isEligibilityRequired        | no                       |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | isEnrollmentProviderRequired | no                       |
      | recordId                     | 20                       |
      | enrollmentStartDate          | 1stDayofPresentMonth     |
      | anniversaryDate              | anniversaryDate          |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted                 |
      | programCode                  | M                        |
      | planCode                     | 84                       |
      | planId                       | 145                      |
      | serviceRegionCode            | East                     |
      | rejectionReason              | Consumer is not eligible |
    Then I send API call with name "15055-01a" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "15055-01.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","Disenroll Reject"
    And Wait for 5 seconds
    And I retrieve task information with name "TY4" for consumer id "15055-01.consumerId"
    And Wait for 10 seconds
    Then I will verify business events are generated with data
      | eventName                | DISENROLL_REQUEST_REJECT_RESPONSE |
      | taskId                   | c.tasks[0].taskVO.taskId          |
#      | correlationId            | 15055-01a.traceid                 |
      | correlationId            | null                |
      | channel                  | SYSTEM_INTEGRATION                |
      | createdBy                | 597                               |
      | processDate              | current                           |
      | externalCaseId           | 15055-01.caseIdentificationNumber |
      | externalConsumerId       | 15055-01.externalConsumerId       |
      | consumerId               | 15055-01.consumerId               |
      | consumerName             | 15055-01                          |
      #  @API-CP-15055-02
      | linkedObjects            |[blank]|
      | enrollmentStartDate      | 1stDayofPresentMonth              |
      | enrollmentEndDate        | lastDayofPresentMonth             |
      | planChangeReason         | null                              |
      | rejectionReason          | Consumer is not eligible          |
      | planCode                 | 84                                |
      | enrollmentType           | MEDICAL                           |
      | selectionStatus          | ACCEPTED                          |
      | consumerPopulation       | MEDICAID-GENERAL                  |
      | benefitStatus            | Enrolled                          |
      # new fields
      | disenrollEffectiveDate   | current                           |
      | disenrollReason          | PLAN_TERMINATED                   |
      | disenrollRequestedDate   | current                           |
      | disenrollSelectionStatus | DISENROLL_REJECTED                |

  @API-CP-15056 @API-CP-15056-01 @API-CP-15056-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: Create Rejected Selection Task for Disenroll Request Reject Response
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 15056-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15056-01.consumerId       |
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
    Then I send API call with name "15056-01" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15056-01.consumerId       |
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
      | [0].consumerId         | 15056-01.consumerId     |
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
    And I initiated get enrollment by consumer id "15056-01.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "15056-01b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 15056-01.consumerId                |
      | [0].planId             | delete::                      |
      | [0].planCode           | 84                            |
      | [0].enrollmentId       | 15056-01b.discontinuedEnrollmentId |
      | [0].status             | ACCEPTED                      |
      | [0].txnStatus          | DISENROLL_SUBMITTED           |
      | [0].startDate          | fdofmnth::                    |
      | [0].endDate            | lstdaymnth::                  |
      | [0].enrollmentType     | delete::                      |
      | [0].subProgramTypeCode | MEDICAIDGF                    |
      | [0].serviceRegionCode  | East                          |
      | [1].planId             | delete::                      |
      | [1].planCode           | 85                            |
      | [1].consumerId         | 15056-01.consumerId                |
      | [1].enrollmentId       | 15056-01b.selectedEnrollmentId     |
      | [1].status             | SUBMITTED_TO_STATE            |
      | [1].txnStatus          | SUBMITTED_TO_STATE            |
      | [1].startDate          | fdnxtmth::                    |
      | [1].subProgramTypeCode | MEDICAIDGF                    |
      | [1].serviceRegionCode  | East                          |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15056-01.consumerId           |
      | isEligibilityRequired        | no                       |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | isEnrollmentProviderRequired | no                       |
      | recordId                     | 20                       |
      | enrollmentStartDate          | 1stDayofPresentMonth     |
      | anniversaryDate              | anniversaryDate          |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted                 |
      | programCode                  | M                        |
      | planCode                     | 84                       |
      | planId                       | 145                      |
      | serviceRegionCode            | East                     |
      | rejectionReason              | Consumer is not eligible |
      | channel                      | SYSTEM_INTEGRATION       |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "15056-01.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","Disenroll Reject"
    And Wait for 5 seconds
    # @API-CP-15056-02
    Then I verify latest task information with name "" for consumer id "15056-01.consumerId" with data
      | staffAssignedTo |[blank]|
      | createdBy       | 597                                        |
      | createdOn       | current                                    |
      | defaultDueDate  | in3BusinessDays                            |
      | dueInDays       | 3                                          |
      | defaultPriority | 3                                          |
      | taskStatus      | Created                                    |
      | statusDate      | current                                    |
    # taskInfo has 3 info areas: reason contains/externalCaseId/what is Internal Scenario Code
    # empty fiels or null ==  taskInfo = null
    # not null == validates that taskInfo value is not null
#      | taskInfo        | Consumer is not eligible/externalCaseId/20 |
      | taskInfo        | Consumer is not eligible/15056-01.externalConsumerId/20 |
    # @API-CP-15056-02 optional
      | channel         | SYSTEM_INTEGRATION                         |
      | externalCaseId  |[blank]|
      | disposition     |[blank]|
      | informationType | Disenroll Request                          |
      | planId          | 145                                        |
      | planName        | AMERIGROUP COMMUNITY CARE                  |
      | planStartDate   | 1stDayofPresentMonth                       |
      | program         | MEDICAID                                   |
#      | linkToCase      |[blank]|
      | linkToCase      | 15056-01.caseId                              |
      | linkToConsumer  |[blank]|

  @API-CP-15058 @API-CP-15058-01 @API-CP-15058-02 @API-CP-15058-03 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: Create or Update Enrollment Data based on Disenroll Request Reject Response Scenario
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
    Then I send API call with name "PA1" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | PA1.consumerId       |
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
      | [0].consumerId         | PA1.consumerId     |
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
    And I initiated get enrollment by consumer id "PA1.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "PA1b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | PA1.consumerId                |
      | [0].planId             | delete::                      |
      | [0].planCode           | 84                            |
      | [0].enrollmentId       | PA1b.discontinuedEnrollmentId |
      | [0].status             | ACCEPTED                      |
      | [0].txnStatus          | DISENROLL_SUBMITTED           |
      | [0].startDate          | fdofmnth::                    |
      | [0].endDate            | lstdaymnth::                  |
      | [0].enrollmentType     | delete::                      |
      | [0].subProgramTypeCode | MEDICAIDGF                    |
      | [0].serviceRegionCode  | East                          |
      | [1].planId             | delete::                      |
      | [1].planCode           | 85                            |
      | [1].consumerId         | PA1.consumerId                |
      | [1].enrollmentId       | PA1b.selectedEnrollmentId     |
      | [1].status             | SUBMITTED_TO_STATE            |
      | [1].txnStatus          | SUBMITTED_TO_STATE            |
      | [1].startDate          | fdnxtmth::                    |
      | [1].subProgramTypeCode | MEDICAIDGF                    |
      | [1].serviceRegionCode  | East                          |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | PA1.consumerId           |
      | isEligibilityRequired        | no                       |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | isEnrollmentProviderRequired | no                       |
      | recordId                     | 20                       |
      | enrollmentStartDate          | 1stDayofPresentMonth     |
      | anniversaryDate              | anniversaryDate          |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted                 |
      | programCode                  | M                        |
      | planCode                     | 84                       |
      | planId                       | 145                      |
      | serviceRegionCode            | East                     |
      | rejectionReason              | Consumer is not eligible |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "PA1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","Disenroll Reject"
    And Wait for 5 seconds
    Then I send API call with name "PA4" for update Enrollment information
      | [0].consumerId        | PA1.consumerId                     |
      | [0].planId            | 145                                |
      | [0].planCode          | 84                                 |
      | [0].startDate         | fdnxtmth::                         |
      | [0].endDate           | null::                             |
      | [0].serviceRegionCode | East                               |
      | [0].channel           | SYSTEM_INTEGRATION                 |
      | [0].anniversaryDate   | PA1.enrollments[0].anniversaryDate |
    And Wait for 5 seconds
    # @API-CP-15058-02
    And I initiated get benefit status by consumer id "PA1.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status records are displayed with benefit status "Enrolled"
    # @API-CP-15058-03
    Then I Verify Consumer Actions as following data
      | [0].action               | Available               |
      | [0].consumerAction       | Plan Change Anniversary |
      | [0].planSelectionAllowed | true                    |
      | [1].action               | Available               |
      | [1].consumerAction       | Plan Change Pre-lockin  |
      | [1].planSelectionAllowed | true                    |

  @API-CP-15059 @API-CP-16187-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario Outline: Decide Disenroll Reject Response Scenario
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
    Then I send API call with name "<name>1" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>1.consumerId   |
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
      | [0].consumerId         | <name>1.consumerId |
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
    And I initiated get enrollment by consumer id "<name>1.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "<name>1b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | <name>1.consumerId                |
      | [0].planId             | delete::                          |
      | [0].planCode           | 84                                |
      | [0].enrollmentId       | <name>1b.discontinuedEnrollmentId |
      | [0].status             | ACCEPTED                          |
      | [0].txnStatus          | DISENROLL_SUBMITTED               |
      | [0].startDate          | fdofmnth::                        |
      | [0].endDate            | lstdaymnth::                      |
      | [0].enrollmentType     | delete::                          |
      | [0].subProgramTypeCode | MEDICAIDGF                        |
      | [0].serviceRegionCode  | East                              |
      | [1].planId             | delete::                          |
      | [1].planCode           | 85                                |
      | [1].consumerId         | <name>1.consumerId                |
      | [1].enrollmentId       | <name>1b.selectedEnrollmentId     |
      | [1].status             | SUBMITTED_TO_STATE                |
      | [1].txnStatus          | SUBMITTED_TO_STATE                |
      | [1].startDate          | fdnxtmth::                        |
      | [1].subProgramTypeCode | MEDICAIDGF                        |
      | [1].serviceRegionCode  | East                              |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>1.consumerId       |
      | isEligibilityRequired        | no                       |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | isEnrollmentProviderRequired | no                       |
      | recordId                     | 20                       |
      | enrollmentStartDate          | <date>                   |
      | anniversaryDate              | anniversaryDate          |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted                 |
      | programCode                  | M                        |
      | planCode                     | <planCode>               |
      | planId                       | 145                      |
      | serviceRegionCode            | East                     |
      | rejectionReason              | Consumer is not eligible |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "<eligibilityScenario>","<enrollmentScenarion>"
    Examples: enrollment scenario differences based on Plan Code and Plane Start Date is as enrollment selection
      | name | date                 | planCode | eligibilityScenario         | enrollmentScenarion |
      | AS   | 1stDayofPresentMonth | 84       | New Retroactive Eligibility | Disenroll Reject    |
      | SD   | 1stDayofNextMonth    | 84       | Not Found                   | Not Found           |
      | DF   | 1stDayofPresentMonth | 85       | Not Found                   | Not Found           |

  @API-CP-16187 @API-CP-16187-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario Outline: Updates to Decide Disenroll Reject Response Scenario (with a Selection Status of “Disregarded”)
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
    Then I send API call with name "<name>1" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>1.consumerId   |
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
    Then I send API call with name "<name>2" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | <name>1.consumerId |
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
    And I initiated get enrollment by consumer id "<name>1.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "<name>1b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | <name>1.consumerId                |
      | [0].planId             | delete::                          |
      | [0].planCode           | 84                                |
      | [0].enrollmentId       | <name>1b.discontinuedEnrollmentId |
      | [0].status             | DISENROLLED                       |
      | [0].txnStatus          | DISENROLLED                       |
      | [0].startDate          | fdofmnth::                        |
      | [0].endDate            | lstdaymnth::                      |
      | [0].enrollmentType     | delete::                          |
      | [0].subProgramTypeCode | MEDICAIDGF                        |
      | [0].serviceRegionCode  | East                              |
      | [1].planId             | delete::                          |
      | [1].planCode           | 85                                |
      | [1].consumerId         | <name>1.consumerId                |
      | [1].enrollmentId       | <name>1b.selectedEnrollmentId     |
      | [1].status             | DISREGARDED                       |
      | [1].txnStatus          | DISREGARDED                       |
      | [1].startDate          | fdnxtmth::                        |
      | [1].subProgramTypeCode | MEDICAIDGF                        |
      | [1].serviceRegionCode  | East                              |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>1.consumerId       |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | isEnrollmentProviderRequired | no                       |
      | recordId                     | 20                       |
      | anniversaryDate              | anniversaryDate          |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | lastDayofPresentMonth    |
      | eligibilityEndReason         | Disenroll                |
      | enrollmentStartDate          | <date>                   |
      | enrollmentEndDate            | lastDayofPresentMonth    |
      | txnStatus                    | Accepted                 |
      | programCode                  | M                        |
      | planCode                     | <planCode>               |
      | planId                       | 145                      |
      | serviceRegionCode            | East                     |
      | rejectionReason              | Consumer is not eligible |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "<eligibilityScenario>","<enrollmentScenarion>"
    Examples: enrollment scenario differences based on Plan Code and Plane Start Date is as enrollment selection
      | name | date                 | planCode | eligibilityScenario         | enrollmentScenarion |
      | QZ   | 1stDayofPresentMonth | 84       | New Retroactive Eligibility | Disenroll Reject    |
      | QX   | 1stDayofNextMonth    | 84       | Not Found                   | Not Found           |
      | QC   | 1stDayofPresentMonth | 85       | Not Found                   | Not Found           |

  @API-CP-10024 @API-CP-10024-01 @API-CP-10024-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: Create Rejected Selection Task for Plan Change Reject Response
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 10024-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 10024-1.consumerId   |
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
      | consumerId                   | 10024-1.consumerId   |
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
      | [0].consumerId         | 10024-1.consumerId |
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
    And I initiated get enrollment by consumer id "10024-1.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "10024-1b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 10024-1.consumerId                |
      | [0].planId             | delete::                          |
      | [0].planCode           | 84                                |
      | [0].enrollmentId       | 10024-1b.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED               |
      | [0].txnStatus          | DISENROLL_SUBMITTED               |
      | [0].startDate          | fdofmnth::                        |
      | [0].endDate            | lstdaymnth::                      |
      | [0].enrollmentType     | delete::                          |
      | [0].subProgramTypeCode | MEDICAIDGF                        |
      | [0].serviceRegionCode  | East                              |
      | [1].planId             | delete::                          |
      | [1].planCode           | 85                                |
      | [1].consumerId         | 10024-1.consumerId                |
      | [1].enrollmentId       | 10024-1b.selectedEnrollmentId     |
      | [1].status             | SUBMITTED_TO_STATE                |
      | [1].txnStatus          | SUBMITTED_TO_STATE                |
      | [1].startDate          | fdnxtmth::                        |
      | [1].subProgramTypeCode | MEDICAIDGF                        |
      | [1].serviceRegionCode  | East                              |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 10024-1.consumerId       |
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
      | channel                      | SYSTEM_INTEGRATION       |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "10024-1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","Plan Change Reject"
    And Wait for 5 seconds
    # @API-CP-15056-02
    Then I verify latest task information with name "IO2" for consumer id "10024-1.consumerId" with data
      | staffAssignedTo |[blank]|
      | createdBy       | 597                                        |
      | createdOn       | current                                    |
      | defaultDueDate  | in3BusinessDays                            |
      | dueInDays       | 3                                          |
      | defaultPriority | 3                                          |
      | taskStatus      | Created                                    |
      | statusDate      | current                                    |
    # taskInfo has 3 info areas: reason contains/externalCaseId/what is Internal Scenario Code
    # empty fiels or null ==  taskInfo = null
    # not null == validates that taskInfo value is not null
#      | taskInfo        | Consumer is not eligible/externalCaseId/18 |
      | taskInfo        | Consumer is not eligible/10024-1.externalConsumerId/18 |
    # @API-CP-15056-02 optional
      | channel         | SYSTEM_INTEGRATION                         |
#      | externalCaseId  | 10024-1.caseIdentificationNumber           |
      | externalCaseId  | 10024-1.externalConsumerId           |
      | disposition     |[blank]|
      | informationType | Plan Change                                |
      | planId          | 145                                        |
      | planName        | PEACH STATE                                |
      | planStartDate   | 1stDayofNextMonth                          |
      | program         | MEDICAID                                   |
      | linkToCase      | 10024-1.caseId                             |
      | linkToConsumer  |[blank]|

  @API-CP-23507 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: BLCRM Updates to Disenroll Business Events - Optional Fields
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | OP1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | OP1.consumerId       |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | OP1.consumerId       |
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
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | OP1.consumerId       |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 13                   |
      | eligibilityEndDate           | current              |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndReason         | PLAN_HAS_TERMINATED  |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    Then I send API call with name "OP1a" for create Eligibility and Enrollment
    And Wait for 10 seconds
    Then I will verify business events are generated with data
      | eventName                | DISENROLL_REQUESTED   |
      | consumerId               | OP1.consumerId        |
#      | correlationId            | OP1a.traceid          |
      | correlationId            | null          |
      | linkedObjects            |[blank]|
      | enrollmentStartDate      | 1stDayofPresentMonth  |
      | enrollmentEndDate        | null                  |
      | planSelectionReason      |[blank]|
      | planChangeReason         |[blank]|
      | rejectionReason          |[blank]|
      | planCode                 | 84                    |
      | enrollmentType           | MEDICAL               |
      | selectionStatus          | ACCEPTED              |
      | anniversaryDate          | anniversaryDate       |
      | pcpNpi                   |[blank]|
      | pcpName                  |[blank]|
      | disenrollRequestedDate   | current               |
      | disenrollEffectiveDate   | lastDayofPresentMonth |
      | disenrollReason          | PLAN_HAS_TERMINATED   |
      | disenrollSelectionStatus | DISENROLL_REQUESTED   |

  @API-CP-15748 @API-CP-15748-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario Outline: Decide Scenario MMIS Sends Disenroll with Loss of Eligibility (Txn Stattus = ACCEPTED)
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
    Then I send API call with name "<name>1" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>1.consumerId   |
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
    And I send API call with name "QK2" for process Outbound Update Enrollments
      | [0].planId            | 145                |
      | [0].planCode          | 84                 |
      | [0].consumerId        | <name>1.consumerId |
      | [0].txnStatus         | ACCEPTED           |
      | [0].startDate         | fdofmnth::         |
      | [0].endDate           | null::             |
      | [0].serviceRegionCode | delete::           |
      # Below channel is causing ClassCastException error and needs fix: class com.fasterxml.jackson.databind.node.ArrayNode cannot be cast to class java.util.List
#      | [0].channel           | delete::           |
      | [0].planEndDateReason | null::             |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>1.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | isEnrollmentProviderRequired | no                    |
      | recordId                     | 14                    |
      | anniversaryDate              | anniversaryDate       |
      | eligibilityStartDate         | 1stDayofPresentMonth  |
      | eligibilityEndDate           | lastDayofPresentMonth |
      | eligibilityEndReason         | Disenroll             |
      | enrollmentStartDate          | <date>                |
      | enrollmentEndDate            | lastDayofPresentMonth |
      | planEndDateReason            | PLAN_HAS_TERMINATED   |
      | txnStatus                    | Accepted              |
      | programCode                  | M                     |
      | planCode                     | <planCode>            |
      | planId                       | 145                   |
      | serviceRegionCode            | East                  |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "<scenario>","<scenario>"
    Examples: enrollment scenario differences based on Plan Code and Plane Start Date is as enrollment selection
      | name | date                 | planCode | scenario                           |
      | QA   | 1stDayofPresentMonth | 84       | Disenroll with Loss of Eligibility |
      | QS   | 1stDayofNextMonth    | 84       | Not Found                          |
      | QD   | 1stDayofPresentMonth | 85       | Not Found                          |

  @API-CP-15748 @API-CP-15748-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario Outline: Decide Scenario MMIS Sends Disenroll with Loss of Eligibility (Txn Stattus = DISENROLL_SUBMITTED)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN"
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
      | programCode                  | H                    |
      | subProgramTypeCode           | MEDICAIDMCHB         |
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "<name>1" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>1.consumerId   |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | MEDICAIDMCHB         |
      | planCode                     | 84                   |
      | planId                       | 145                  |
      | serviceRegionCode            | East                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I send API call with name "<name>2" for process Outbound Update Enrollments
      | [0].planId            | 145                 |
      | [0].planCode          | 84                  |
      | [0].consumerId        | <name>1.consumerId  |
      | [0].txnStatus         | DISENROLL_REQUESTED |
      | [0].startDate         | fdofmnth::          |
      | [0].serviceRegionCode | East                |
      | [0].channel           | SYSTEM_INTEGRATION  |
      | [0].planEndDateReason | PLAN_HAS_TERMINATED |
    And Wait for 5 seconds
    And I send API call with name "<name>3" for process Outbound Update
      | [0].consumerId     | <name>1.consumerId     |
      | [0].planId         | delete::               |
      | [0].planCode       | 84                     |
      | [0].enrollmentId   | c.data[0].enrollmentId |
      | [0].status         | ACCEPTED               |
      | [0].txnStatus      | DISENROLL_SUBMITTED    |
      | [0].startDate      | fdofmnth::             |
    # Below enrollmentType is causing ClassCastException error and needs fix: class com.fasterxml.jackson.databind.node.ArrayNode cannot be cast to class java.util.List
#      | [0].enrollmentType | delete::               |
    And Wait for 5 seconds
    And I send API call with name "QK2" for process Outbound Update Enrollments
      | [0].planId            | 145                 |
      | [0].planCode          | 84                  |
      | [0].consumerId        | <name>1.consumerId  |
      | [0].txnStatus         | DISENROLL_SUBMITTED |
      | [0].startDate         | fdofmnth::          |
      | [0].endDate           | null::              |
      | [0].serviceRegionCode | delete::            |
     # Below channel is causing ClassCastException error and needs fix: class com.fasterxml.jackson.databind.node.ArrayNode cannot be cast to class java.util.List
#      | [0].channel           | delete::            |
      | [0].planEndDateReason | null::              |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>1.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | isEnrollmentProviderRequired | no                    |
      | recordId                     | 14                    |
      | anniversaryDate              | anniversaryDate       |
      | eligibilityStartDate         | 1stDayofPresentMonth  |
      | eligibilityEndDate           | lastDayofPresentMonth |
      | eligibilityEndReason         | Disenroll             |
      | enrollmentStartDate          | <date>                |
      | enrollmentEndDate            | lastDayofPresentMonth |
      | planEndDateReason            | PLAN_HAS_TERMINATED   |
      | txnStatus                    | Accepted              |
      | programCode                  | H                     |
      | subProgramTypeCode           | MEDICAIDMCHB          |
      | planCode                     | <planCode>            |
      | planId                       | 145                   |
      | serviceRegionCode            | East                  |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "<scenario>","<scenario>"
    Examples: enrollment scenario differences based on Plan Code and Plane Start Date is as enrollment selection
      | name | date                 | planCode | scenario                           |
      | QF   | 1stDayofPresentMonth | 84       | Disenroll with Loss of Eligibility |
      | QG   | 1stDayofNextMonth    | 84       | Not Found                          |
      | QH   | 1stDayofPresentMonth | 85       | Not Found                          |

  @API-CP-15749 @API-CP-15749-01 @API-CP-15749-02 @API-CP-10431 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: Create Business Event for Disenroll with Loss of Eligibility
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 15749-02 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15749-02.consumerId  |
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
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15749-02.consumerId  |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | MEDICAIDMCHB         |
      | planCode                     | 84                   |
      | planId                       | 145                  |
      | serviceRegionCode            | East                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I send API call with name "QJ2" for process Outbound Update Enrollments
      | [0].planId            | 145                 |
      | [0].planCode          | 84                  |
      | [0].consumerId        | 15749-02.consumerId |
      | [0].txnStatus         | DISENROLL_REQUESTED |
      | [0].startDate         | fdofmnth::          |
      | [0].serviceRegionCode | East                |
      | [0].channel           | SYSTEM_INTEGRATION  |
      | [0].planEndDateReason | PLAN_HAS_TERMINATED |
    And Wait for 5 seconds
    And I send API call with name "QJ3" for process Outbound Update
      | [0].consumerId     | 15749-02.consumerId    |
      | [0].planId         | delete::               |
      | [0].planCode       | 84                     |
      | [0].enrollmentId   | c.data[0].enrollmentId |
      | [0].status         | ACCEPTED               |
      | [0].txnStatus      | DISENROLL_SUBMITTED    |
      | [0].startDate      | fdofmnth::             |
      | [0].endDate        | lstdaymnth::           |
    # Below enrollmentType is causing ClassCastException error and needs fix: class com.fasterxml.jackson.databind.node.ArrayNode cannot be cast to class java.util.List
#      | [0].enrollmentType | delete::               |
    And Wait for 5 seconds
    And I send API call with name "QK2" for process Outbound Update Enrollments
      | [0].planId            | 145                 |
      | [0].planCode          | 84                  |
      | [0].consumerId        | 15749-02.consumerId |
      | [0].txnStatus         | DISENROLL_SUBMITTED |
      | [0].startDate         | fdofmnth::          |
      | [0].endDate           | null::              |
      | [0].serviceRegionCode | delete::            |
    # Below channel is causing ClassCastException error and needs fix: class com.fasterxml.jackson.databind.node.ArrayNode cannot be cast to class java.util.List
#      | [0].channel           | delete::            |
      | [0].planEndDateReason | null::              |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15749-02.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | isEnrollmentProviderRequired | no                    |
      | recordId                     | 14                    |
      | anniversaryDate              | anniversaryDate       |
      | eligibilityStartDate         | 1stDayofPresentMonth  |
      | eligibilityEndDate           | lastDayofPresentMonth |
      | eligibilityEndReason         | Disenroll             |
      | enrollmentStartDate          | 1stDayofPresentMonth  |
      | enrollmentEndDate            | lastDayofPresentMonth |
      | planEndDateReason            | PLAN_HAS_TERMINATED   |
      | txnStatus                    | Accepted              |
      | programCode                  | H                     |
      | subProgramTypeCode           | MEDICAIDMCHB          |
      | planCode                     | 84                    |
      | planId                       | 145                   |
      | serviceRegionCode            | East                  |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "15749-02.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Disenroll with Loss of Eligibility","Disenroll with Loss of Eligibility"
    And Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName                             | DISENROLL_WITH_LOSS_OF_ELIGIBILITY |
      | channel                               | SYSTEM_INTEGRATION                 |
      | createdBy                             | 597                                |
      | processDate                           | current                            |
      | externalCaseId                        | 15749-02.caseIdentificationNumber  |
      | externalConsumerId                    | 15749-02.externalConsumerId        |
      | consumerId                            | 15749-02.consumerId                |
      | consumerName                          | 15749-02                           |
      #  @API-CP-15749-02
      # All eligibility.* fields verify capture the optional latest values as follows for the Eligibility Data
      | eligibility.eligibilityStartDate      | 1stDayofPresentMonth               |
      | eligibility.eligibilityEndDate        | lastDayofPresentMonth              |
      | eligibility.eligibilityEndReason      | Disenroll                          |
      | eligibility.eligibilityCategoryCode   | null                               |
      | eligibility.eligibilityProgramCode    | H                                  |
      | eligibility.eligibilitySubProgramCode | MEDICAIDMCHB                       |
      | eligibility.eligibilityCoverageCode   | string                             |
      | eligibility.eligibilityExemptionCode  | qwer                               |
#      | Residential County Code |[blank]| not found in event data
      | eligibility.consumerPopulation        | PREGNANT-WOMEN                     |
      | eligibility.consumerBenefitStatus     | Not Eligible                       |
      # All enrollment.* fields verify capture the optional latest values as follows for the Enrollment  Data
      | enrollment.enrollmentStartDate        | 1stDayofPresentMonth               |
      | enrollment.enrollmentEndDate          | lastDayofPresentMonth              |
      | enrollment.planChangeReason           | PLAN_HAS_TERMINATED                |
      | enrollment.rejectionReason            | null                               |
      | enrollment.planCode                   | 84                                 |
      | enrollment.enrollmentType             | MEDICAL                            |
      | enrollment.selectionStatus            | DISENROLLED                        |
      | enrollment.consumerPopulation         | PREGNANT-WOMEN                     |
      | enrollment.consumerBenefitStatus      | Not Eligible                       |

  @API-CP-15750 @API-CP-15750-01 @API-CP-15750-02 @API-CP-15750-03 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: End In-flight enrollment selections for Disenroll with Loss of Eligibility
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
    Then I send API call with name "QK1" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | QK1.consumerId       |
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
    And I send API call with name "QK2" for process Outbound Update Enrollments
      | [0].planId            | 145                 |
      | [0].planCode          | 84                  |
      | [0].consumerId        | QK1.consumerId      |
      | [0].txnStatus         | DISENROLL_REQUESTED |
      | [0].startDate         | fdofmnth::          |
      | [0].endDate           | lstdaymnth::        |
      | [0].serviceRegionCode | East                |
      | [0].channel           | SYSTEM_INTEGRATION  |
      | [0].planEndDateReason | PLAN_HAS_TERMINATED |
    And Wait for 5 seconds
    And I send API call with name "QK3" for process Outbound Update
      | [0].consumerId        | QK1.consumerId         |
      | [0].planId            | delete::               |
      | [0].planCode          | 84                     |
      | [0].enrollmentId      | c.data[0].enrollmentId |
      | [0].status            | ACCEPTED               |
      | [0].txnStatus         | DISENROLL_SUBMITTED    |
      | [0].startDate         | fdofmnth::             |
      | [0].endDate           | lstdaymnth::           |
      | [0].enrollmentType    | M                      |
      | [0].serviceRegionCode | East                   |
    And Wait for 5 seconds
    Then I send API call with name "QK4" for update Enrollment information
      | [0].consumerId | QK1.consumerId     |
      | [0].planId     | 145                |
      | [0].planCode   | 85                 |
      | [0].startDate  | fdnxtmth::         |
      | [0].channel    | SYSTEM_INTEGRATION |
    And Wait for 5 seconds
    And I send API call with name "QK5" for process Outbound Update
      | [0].channel        | SYSTEM_INTEGRATION     |
      | [0].startDate      | fdnxtmth::             |
      | [0].planId         | delete::               |
      | [0].planCode       | 85                     |
      | [0].consumerId     | QK1.consumerId         |
      | [0].enrollmentId   | c.data[0].enrollmentId |
      | [0].status         | SUBMITTED_TO_STATE     |
      | [0].txnStatus      | SUBMITTED_TO_STATE     |
   # Below enrollmentType is causing ClassCastException error and needs fix: class com.fasterxml.jackson.databind.node.ArrayNode cannot be cast to class java.util.List
#      | [0].enrollmentType | delete::               |
    And Wait for 5 seconds
    And I send API call with name "QK2" for process Outbound Update Enrollments
      | [0].planId            | 145                 |
      | [0].planCode          | 84                  |
      | [0].consumerId        | QK1.consumerId      |
      | [0].txnStatus         | DISENROLL_SUBMITTED |
      | [0].startDate         | fdofmnth::          |
      | [0].endDate           | null::              |
      | [0].serviceRegionCode | delete::            |
     # Below channel is causing ClassCastException error and needs fix: class com.fasterxml.jackson.databind.node.ArrayNode cannot be cast to class java.util.List
#      | [0].channel           | delete::            |
      | [0].planEndDateReason | null::              |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | QK1.consumerId        |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | isEnrollmentProviderRequired | no                    |
      | recordId                     | 14                    |
      | anniversaryDate              | anniversaryDate       |
      | eligibilityStartDate         | 1stDayofPresentMonth  |
      | eligibilityEndDate           | lastDayofPresentMonth |
      | eligibilityEndReason         | Disenroll             |
      | enrollmentStartDate          | 1stDayofPresentMonth  |
      | enrollmentEndDate            | lastDayofPresentMonth |
      | planEndDateReason            | Disenroll             |
      | txnStatus                    | Accepted              |
      | programCode                  | M                     |
      | planCode                     | 84                    |
      | planId                       | 145                   |
      | serviceRegionCode            | East                  |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "QK1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Disenroll with Loss of Eligibility","Disenroll with Loss of Eligibility"
    # @API-CP-15750-02
    Then I verify latest benefit status records are displayed with benefit status "Not Eligible"
    # @API-CP-15750-03
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable |
      | [0].consumerAction       | null        |
      | [0].planSelectionAllowed | false       |

  @API-CP-16185 @API-CP-16185-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario Outline: Updates to Decide Plan Change Reject Response Scenario (enrollment=“Disenrolled”,  Selection=“Disregarded”)
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
    Then I send API call with name "<name>1" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>1.consumerId   |
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
      | [0].consumerId         | <name>1.consumerId |
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
    And I initiated get enrollment by consumer id "<name>1.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "<name>1a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | <name>1.consumerId                |
      | [0].planId             | delete::                          |
      | [0].planCode           | 84                                |
      | [0].enrollmentId       | <name>1a.discontinuedEnrollmentId |
      | [0].status             | DISENROLLED                       |
      | [0].txnStatus          | DISENROLLED                       |
      | [0].startDate          | fdofmnth::                        |
      | [0].endDate            | lstdaymnth::                      |
      | [0].enrollmentType     | delete::                          |
      | [0].subProgramTypeCode | MEDICAIDGF                        |
      | [0].serviceRegionCode  | East                              |
      | [1].planId             | delete::                          |
      | [1].planCode           | 85                                |
      | [1].consumerId         | <name>1.consumerId                |
      | [1].enrollmentId       | <name>1a.selectedEnrollmentId     |
      | [1].status             | DISREGARDED                       |
      | [1].txnStatus          | DISREGARDED                       |
      | [1].startDate          | fdnxtmth::                        |
      | [1].subProgramTypeCode | MEDICAIDGF                        |
      | [1].serviceRegionCode  | East                              |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>1.consumerId       |
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
      | channel                      | SYSTEM_INTEGRATION       |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "<eligibilityScenario>","<enrollmentScenatio>"
    Examples: plan change (enrollment=“Disenrolled”,  Selection=“Disregarded”) conditions:
      | name | planCode | enrollmentStartDate  | eligibilityScenario         | enrollmentScenatio |
      | ET   | 85       | 1stDayofNextMonth    | New Retroactive Eligibility | Plan Change Reject |
      | EY   | 87       | 1stDayofNextMonth    | Not Found                   | Not Found          |
      | EU   | 85       | 1stDayofPresentMonth | Not Found                   | Not Found          |


