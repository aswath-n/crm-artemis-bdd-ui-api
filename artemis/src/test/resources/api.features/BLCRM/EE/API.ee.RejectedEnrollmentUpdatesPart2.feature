Feature: Rejected Enrollment updates Part 2

  @API-CP-16185 @API-CP-16185-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario Outline: Updates to Decide Plan Change Reject Response Scenario (enrollment=“Accepted”,  Selection=“Disregarded”)
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
    And Wait for 5 seconds
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "<eligibilityScenario>","<enrollmentScenatio>"
    Examples: plan change (enrollment=“Accepted”,  Selection=“Disregarded”) conditions:
      | name | planCode | enrollmentStartDate  | eligibilityScenario         | enrollmentScenatio |
      | EI   | 84       | 1stDayofPresentMonth | New Retroactive Eligibility | Plan Change Reject |
      | EO   | 85       | 1stDayofPresentMonth | Not Found                   | Not Found          |
      | EP   | 84       | 1stDayofNextMonth    | Not Found                   | Not Found          |

  @API-CP-16186 @API-CP-16188-01 @API-CP-16186-03 @API-CP-16186-05 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario Outline: Updates to Create or Update Enrollment Data based on Plan Change Reject Response Scenario
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | <name> |
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
    And Wait for 3 seconds
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "<name>1a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | <name>1.consumerId                |
      | [0].planId             | delete::                          |
      | [0].planCode           | 84                                |
      | [0].enrollmentId       | <name>1a.discontinuedEnrollmentId |
      | [0].status             | <disenrollStatus>                 |
      | [0].txnStatus          | DISENROLL_SUBMITTED               |
      | [0].startDate          | fdofmnth::                        |
      | [0].endDate            | lstdaymnth::                      |
      | [0].enrollmentType     | delete::                          |
      | [0].subProgramTypeCode | MEDICAIDGF                        |
      | [0].serviceRegionCode  | East                              |
      | [1].planId             | delete::                          |
      | [1].planCode           | 85                                |
      | [1].consumerId         | <name>1.consumerId                |
      | [1].enrollmentId       | <name>1a.selectedEnrollmentId     |
      | [1].status             | <selectionStatus>                 |
      | [1].txnStatus          | <selectionStatus>                 |
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
      | enrollmentStartDate          | 1stDayofNextMonth     |
      | anniversaryDate              | anniversaryDate          |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | txnStatus                    | Accepted                 |
      | programCode                  | M                        |
      | planCode                     | 85                       |
      | planId                       | 145                      |
      | serviceRegionCode            | East                     |
      | rejectionReason              | Consumer is not eligible |
      | channel                      | SYSTEM_INTEGRATION       |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","Plan Change Reject"
    # @API-CP-16186-03
    Then I verify benefit status records are displayed with benefit status "Enrolled"
    # @API-CP-16186-04
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>                |
      | [0].consumerAction       | Plan Change Anniversary |
      | [0].planSelectionAllowed | true                    |
      | [1].action               | <action>                |
      | [1].consumerAction       | Plan Change Pre-lockin  |
      | [1].planSelectionAllowed | true                    |
    And Wait for 5 seconds
    And I verify enrollment by consumer id "<name>1.consumerId" with data
      | medicalPlanBeginDate | 1stDayofPresentMonth |
      | medicalPlanEndDate   | <medicalPlanEndDate> |
      | enrollmentType       | MEDICAL              |
      | channel              | SYSTEM_INTEGRATION   |
      | anniversaryDate      | anniversaryDate      |
      | planCode             | 84                   |
    And Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | <name>1.consumerId      |
      | consumerFound | true                    |
      | recordId      | <eventRecordId>         |
    # recordId = 18 means ENROLLMENT_UPDATE_EVENT was published for Plan Change Reject
    # @API-CP-16186-05
#   Below verification is replaced with the following steps after that
#    Then I verify if tasks for consumer id "<name>1.consumerId" is created "<taskCreated>"
    Given I initiated get auto assignment transaction Api
    And I provide details to get auto assignment transaction api
      | consumerId | <name>1.consumerId |
    Then I run auto assignment transaction get Api
    Then I verify service request id is a non zero value
    # Getting tasks for this consumer by calling mars/taskmanagement/tasks/{taksId} endpoint
    And I run service request details get Api
    Then I verify tasks for newly created service request whose id is 1 greater than the original with data
      | recordCount   | <recordCount>         |
    Examples:  consumer’s enrollment segment is in a Selection Status of “Accepted“ OR “Disenrolled“
      | name | disenrollStatus     | selectionStatus    | action      | medicalPlanEndDate    | eventRecordId | taskCreated | recordCount |
      | EA   | DISENROLL_SUBMITTED | SUBMITTED_TO_STATE | Available   | null                  | 18            | true        | 1           |
      | ES   | DISENROLLED         | DISREGARDED        | Unavailable | lastDayofPresentMonth | null          | false       | 0           |
      # @API-CP-16186-02
      | ED   | ACCEPTED            | DISREGARDED        | Unavailable | lastDayofPresentMonth | null          | false       | 0           |

  @API-CP-16186 @API-CP-16188-01 @API-CP-16186-02 @API-CP-16186-03 @API-CP-16186-04 #@API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir @hasissues
  Scenario Outline: Updates to Create or Update Enrollment Data based on Plan Change Reject Response Scenario with loss of eligibility
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
      | [0].status             | <disenrollStatus>                 |
      | [0].txnStatus          | DISENROLL_SUBMITTED               |
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
    And I send API call with name "OU" for process Outbound Update Enrollments
      | [0].consumerId        | <name>1.consumerId  |
      | [0].channel           | SYSTEM_INTEGRATION  |
      | [0].planCode          | 84                  |
      | [0].planId            | 145                 |
      | [0].status            | <disenrollStatus>   |
      | [0].txnStatus         | DISENROLL_SUBMITTED |
      | [0].startDate         | fdofmnth::          |
      | [0].endDate           | null::              |
      | [0].serviceRegionCode | delete::            |
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
      | enrollmentStartDate          | 1stDayofPresentMonth  |
      | enrollmentEndDate            | lastDayofPresentMonth |
      | planEndDateReason            | Disenroll             |
      | txnStatus                    | Accepted              |
      | programCode                  | M                     |
      | planCode                     | 84                    |
      | planId                       | 145                   |
      | serviceRegionCode            | East                  |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>1.consumerId       |
      | isEligibilityRequired        | no                       |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | isEnrollmentProviderRequired | no                       |
      | recordId                     | 18                       |
      | enrollmentStartDate          | 1stDayofPresentMonth     |
      | anniversaryDate              | anniversaryDate          |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | txnStatus                    | Accepted                 |
      | programCode                  | M                        |
      | planCode                     | 84                       |
      | planId                       | 145                      |
      | serviceRegionCode            | East                     |
      | rejectionReason              | Consumer is not eligible |
      | channel                      | SYSTEM_INTEGRATION       |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Disenroll with Loss of Eligibility","Plan Change Reject"
    # @API-CP-15750-03
    Then I verify latest benefit status records are displayed with benefit status "Not Eligible"
    # @API-CP-15750-04
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable |
      | [0].consumerAction       | null        |
      | [0].planSelectionAllowed | false       |
    And I verify enrollment by consumer id "<name>1.consumerId" with data
      | medicalPlanBeginDate | 1stDayofPresentMonth  |
      | medicalPlanEndDate   | lastDayofPresentMonth |
      | enrollmentType       | MEDICAL               |
      | channel              | SYSTEM_INTEGRATION    |
      | anniversaryDate      | anniversaryDate       |
      | planCode             | 84                    |
    And Wait for 10 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | <name>1.consumerId      |
      | consumerFound | true                    |
      | recordId      | 14                      |
    # recordId = 14 not 18 means ENROLLMENT_UPDATE_EVENT was NOT published for Plan Change Reject
    Examples:  consumer’s enrollment segment is in a Selection Status of “Accepted“ OR “Disenrolled“
      | name | disenrollStatus     |
      # @API-CP-16186-02 DISENROLL_SUBMITTED scenarion + recordId 14 creates DISENROLLED enrollment status
      | EF   | DISENROLL_SUBMITTED |
      | EG   | ACCEPTED            |

  @API-CP-16188 @API-CP-16188-01 @API-CP-16186-02 @API-CP-16186-03 @API-CP-16186-04 @API-CP-16186-05 #@API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir @hasissues
  Scenario: Update the consumer’s enrollment selection based on Disenroll Request Reject Response Scenario
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
    Then I send API call with name "EH1" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | EH1.consumerId       |
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
      | [0].consumerId         | EH1.consumerId     |
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
    And I initiated get enrollment by consumer id "EH1.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "EH1a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | EH1.consumerId                |
      | [0].planId             | delete::                      |
      | [0].planCode           | 84                            |
      | [0].enrollmentId       | EH1a.discontinuedEnrollmentId |
      | [0].status             | ACCEPTED                      |
      | [0].txnStatus          | DISENROLL_SUBMITTED           |
      | [0].startDate          | fdofmnth::                    |
      | [0].endDate            | lstdaymnth::                  |
      | [0].enrollmentType     | delete::                      |
      | [0].subProgramTypeCode | MEDICAIDGF                    |
      | [0].serviceRegionCode  | East                          |
      | [1].planId             | delete::                      |
      | [1].planCode           | 85                            |
      | [1].consumerId         | EH1.consumerId                |
      | [1].enrollmentId       | EH1a.selectedEnrollmentId     |
      | [1].status             | SUBMITTED_TO_STATE            |
      | [1].txnStatus          | SUBMITTED_TO_STATE            |
      | [1].startDate          | fdnxtmth::                    |
      | [1].subProgramTypeCode | MEDICAIDGF                    |
      | [1].serviceRegionCode  | East                          |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | EH1.consumerId           |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | isEnrollmentProviderRequired | no                       |
      | recordId                     | 20                       |
      | anniversaryDate              | anniversaryDate          |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | lastDayofPresentMonth    |
      | eligibilityEndReason         | Disenroll                |
      | enrollmentStartDate          | 1stDayofPresentMonth     |
      | enrollmentEndDate            | lastDayofPresentMonth    |
      | txnStatus                    | Accepted                 |
      | programCode                  | M                        |
      | planCode                     | 84                       |
      | planId                       | 145                      |
      | serviceRegionCode            | East                     |
      | rejectionReason              | Consumer is not eligible |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "EH1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","Disenroll Reject"
    # @API-CP-16188-03
    Then I verify benefit status records are displayed with benefit status "Enrolled"
    # @API-CP-16188-04
    Then I Verify Consumer Actions as following data
      | [0].action               | Available               |
      | [0].consumerAction       | Plan Change Anniversary |
      | [0].planSelectionAllowed | true                    |
      | [1].action               | Available               |
      | [1].consumerAction       | Plan Change Pre-lockin  |
      | [1].planSelectionAllowed | true                    |
    And Wait for 5 seconds
    And I verify enrollment by consumer id "EH1.consumerId" with data
      | medicalPlanBeginDate | 1stDayofPresentMonth  |
      | medicalPlanEndDate   | lastDayofPresentMonth |
      | enrollmentType       | MEDICAL               |
      | channel              | SYSTEM_INTEGRATION    |
      | anniversaryDate      | anniversaryDate       |
      | planCode             | 84                    |
      | planEndDateReason    | PLAN_TERMINATED       |
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | EH1.consumerId          |
      | consumerFound | true                    |
      | recordId      | 20                      |
    # recordId = 20 means ENROLLMENT_UPDATE_EVENT was published for Disenroll Reject
    # @API-CP-16188-05
    Then I verify if tasks for consumer id "EH1.consumerId" is created "true"

  @API-CP-16188 @API-CP-16188-01 @API-CP-16186-02 @API-CP-16186-03 @API-CP-16186-04 @API-CP-16186-05 #@API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir @hasissues
  Scenario: Update the consumer’s enrollment selection based on Disenroll Request Reject Response Scenario with loss of eligibility
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
    Then I send API call with name "EJ1" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | EJ1.consumerId       |
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
      | [0].consumerId         | EJ1.consumerId     |
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
    And I initiated get enrollment by consumer id "EJ1.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "EJ1a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | EJ1.consumerId                |
      | [0].planId             | delete::                      |
      | [0].planCode           | 84                            |
      | [0].enrollmentId       | EJ1a.discontinuedEnrollmentId |
      | [0].status             | ACCEPTED                      |
      | [0].txnStatus          | DISENROLL_SUBMITTED           |
      | [0].startDate          | fdofmnth::                    |
      | [0].endDate            | lstdaymnth::                  |
      | [0].enrollmentType     | delete::                      |
      | [0].subProgramTypeCode | MEDICAIDGF                    |
      | [0].serviceRegionCode  | East                          |
      | [1].planId             | delete::                      |
      | [1].planCode           | 85                            |
      | [1].consumerId         | EJ1.consumerId                |
      | [1].enrollmentId       | EJ1a.selectedEnrollmentId     |
      | [1].status             | DISREGARDED                   |
      | [1].txnStatus          | DISREGARDED                   |
      | [1].startDate          | fdnxtmth::                    |
      | [1].subProgramTypeCode | MEDICAIDGF                    |
      | [1].serviceRegionCode  | East                          |
    And Wait for 5 seconds
    And I send API call with name "OU" for process Outbound Update Enrollments
      | [0].consumerId        | EJ1.consumerId      |
      | [0].channel           | SYSTEM_INTEGRATION  |
      | [0].planCode          | 84                  |
      | [0].planId            | 145                 |
      | [0].status            | ACCEPTED            |
      | [0].txnStatus         | DISENROLL_SUBMITTED |
      | [0].startDate         | fdofmnth::          |
      | [0].endDate           | null::              |
      | [0].serviceRegionCode | delete::            |
      | [0].planEndDateReason | null::              |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | EJ1.consumerId        |
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
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | EJ1.consumerId           |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | isEnrollmentProviderRequired | no                       |
      | recordId                     | 20                       |
      | anniversaryDate              | anniversaryDate          |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | lastDayofPresentMonth    |
      | eligibilityEndReason         | Disenroll                |
      | enrollmentStartDate          | 1stDayofPresentMonth     |
      | enrollmentEndDate            | lastDayofPresentMonth    |
      | txnStatus                    | Accepted                 |
      | programCode                  | M                        |
      | planCode                     | 84                       |
      | planId                       | 145                      |
      | serviceRegionCode            | East                     |
      | rejectionReason              | Consumer is not eligible |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "EJ1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Disenroll with Loss of Eligibility","Disenroll Reject"
    # @API-CP-16188-03
    Then I verify benefit status records are displayed with benefit status "Not Eligible"
    # @API-CP-16188-04
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable |
      | [0].consumerAction       | null        |
      | [0].planSelectionAllowed | false       |
    And Wait for 5 seconds
    And I verify enrollment by consumer id "EJ1.consumerId" with data
      | medicalPlanBeginDate | 1stDayofPresentMonth  |
      | medicalPlanEndDate   | lastDayofPresentMonth |
      | enrollmentType       | MEDICAL               |
      | channel              | SYSTEM_INTEGRATION    |
      | anniversaryDate      | anniversaryDate       |
      | planCode             | 84                    |
      | planEndDateReason    | Disenroll             |
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | EJ1.consumerId          |
      | consumerFound | true                    |
      | recordId      | 14                      |
    # recordId = 14 NOT 20 means ENROLLMENT_UPDATE_EVENT was NOT published for Disenroll Reject
    # @API-CP-16188-05
    Then I verify if tasks for consumer id "EJ1.consumerId" is created "false"

#    @API-CRM-Regression to recover after
  @API-CP-9636 @API-CP-9636-01 @API-CP-9636-02 @API-EE @eligibility-enrollment-ms-EE @sobir
  Scenario: Create Rejected Selection Task for New Enrollment Reject Response
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | RF1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RF1.consumerId       |
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
    Then I send API call with name "OU" for update Enrollment information
      | [0].consumerId         | RF1.consumerId |
      | [0].planId             | 145            |
      | [0].planCode           | 84             |
      | [0].startDate          | fdnxtmth::     |
      | [0].programTypeCode    | H              |
      | [0].subProgramTypeCode | MEDICAIDMCHB   |
      | [0].serviceRegionCode  | East           |
    And Wait for 5 seconds
    And I send API call with name "OU" for process Outbound Update
      | [0].consumerId     | RF1.consumerId         |
      | [0].planId         | delete::               |
      | [0].planCode       | 84                     |
      | [0].enrollmentId   | c.data[0].enrollmentId |
      | [0].status         | SUBMITTED_TO_STATE     |
      | [0].txnStatus      | SUBMITTED_TO_STATE     |
      | [0].startDate      | fdnxtmth::             |
    # Below enrollmentType is causing ClassCastException error
#      | [0].enrollmentType | delete::               |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RF1.consumerId           |
      | isEligibilityRequired        | no                       |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | isEnrollmentProviderRequired | no                       |
      | recordId                     | 16                       |
      | enrollmentStartDate          | 1stDayofNextMonth        |
      | anniversaryDate              | anniversaryDate          |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted                 |
      | programCode                  | H                        |
      | subProgramTypeCode           | MEDICAIDMCHB             |
      | planCode                     | 84                       |
      | planId                       | 145                      |
      | serviceRegionCode            | East                     |
      | rejectionReason              | Consumer is not eligible |
      | channel                      | SYSTEM_INTEGRATION       |
    Then I send API call with name "RF1a" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "RF1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","New Enrollment Reject"
    And Wait for 5 seconds
    # @API-CP-9636-02
    Then I verify latest task information with name "RF3" for consumer id "RF1.consumerId" with data
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
#      | taskInfo        | Consumer is not eligible/externalCaseId/16 |
      | taskInfo        | Consumer is not eligible/RF1.externalConsumerId/16 |
    # @API-CP-9636-02 optional
      | channel         | SYSTEM_INTEGRATION                         |
#      | externalCaseId  | RF1.caseIdentificationNumber               |
      | externalCaseId  | RF1.externalConsumerId                     |
      | disposition     |[blank]|
      # CP-9636 Optional Fields under AC 2.0 Include the following Data Elements and Values in the Task Creation Request
      # states that information type should be New Enrollment and not Disenroll Request
#      | informationType | Disenroll Request                          |
      | informationType | New Enrollment                             |
      | planId          | 145                                        |
      | planName        | AMERIGROUP COMMUNITY CARE                  |
      | planStartDate   | 1stDayofNextMonth                          |
      | program         | MEDICAID                                   |
      | linkToCase      |  RF1.caseId                                |
      | linkToConsumer  |[blank]|

#    @API-CRM-Regression to recover after
  @API-CP-16183 @API-EE @eligibility-enrollment-ms-EE @sobir
  Scenario Outline: Updates to Decide New Enrollment Reject Response Scenario
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
      | serviceRegionCode            | East                 |
    Then I send API call with name "<name>" for create Eligibility and Enrollment
    And Wait for 5 seconds
    Then I send API call with name "OU" for update Enrollment information
      | [0].consumerId         | <name>.consumerId |
      | [0].planId             | 145               |
      | [0].planCode           | 84                |
      | [0].startDate          | fdnxtmth::        |
      | [0].programTypeCode    | H                 |
      | [0].subProgramTypeCode | MEDICAIDMCHB      |
    And Wait for 5 seconds
    And I send API call with name "OU" for process Outbound Update
      | [0].consumerId     | <name>.consumerId      |
      | [0].planId         | delete::               |
      | [0].planCode       | 84                     |
      | [0].enrollmentId   | c.data[0].enrollmentId |
      | [0].status         | <selectionStatus>      |
      | [0].txnStatus      | <selectionStatus>      |
      | [0].startDate      | fdnxtmth::             |
    # Below enrollmentType is causing ClassCastException error
#      | [0].enrollmentType | delete::               |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | no                       |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | isEnrollmentProviderRequired | no                       |
      | recordId                     | 16                       |
      | enrollmentStartDate          | <enrollmentStartDate>    |
      | anniversaryDate              | anniversaryDate          |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted                 |
      | programCode                  | <programCode>            |
      | subProgramTypeCode           | MEDICAIDMCHB             |
      | planCode                     | 84                       |
      | planId                       | 145                      |
      | serviceRegionCode            | East                     |
      | rejectionReason              | Consumer is not eligible |
      | channel                      | SYSTEM_INTEGRATION       |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
#    Then I verify scenario output in the benefit status records are displayed as "eligibilityScenario","<enrollmentScenario>"
    Examples: segment with a Selection Status of “Submitted to State” OR “Disregarded” and correct Plan Code or Plan Start Date
      | name | selectionStatus    | programCode | enrollmentStartDate  | eligibilityScenario         | enrollmentScenario    |
      | RG1  | SUBMITTED_TO_STATE | H           | 1stDayofNextMonth    | New Retroactive Eligibility | New Enrollment Reject |
      | RG2  | DISREGARDED        | H           | 1stDayofNextMonth    | New Retroactive Eligibility | New Enrollment Reject |
      | RG3  | SUBMITTED_TO_STATE | M           | 1stDayofNextMonth    | Not Found                   | Not Found             |
      | RG4  | DISREGARDED        | H           | 1stDayofPresentMonth | Not Found                   | Not Found             |

#     to recover after
  @API-CP-16184 @API-CP-16184-01 @API-CP-16184-02 @API-CP-16184-03 @API-CP-16184-04 @API-EE @eligibility-enrollment-ms-EE @sobir
  Scenario Outline: Updates to Create or Update Enrollment Data based on New Enrollment Reject Response Scenario
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
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
      | serviceRegionCode            | East                 |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Then I send API call with name "OU" for update Enrollment information
      | [0].consumerId      | <name>.consumerId  |
      | [0].planId          | 145                |
      | [0].planCode        | 84                 |
      | [0].startDate       | fdnxtmth::         |
      | [0].programTypeCode | M                  |
      | [0].channel         | SYSTEM_INTEGRATION |
    And Wait for 5 seconds
    And I send API call with name "OU" for process Outbound Update
      | [0].consumerId     | <name>.consumerId      |
      | [0].planId         | delete::               |
      | [0].planCode       | 84                     |
      | [0].enrollmentId   | c.data[0].enrollmentId |
      | [0].status         | <selectionStatus>      |
      | [0].txnStatus      | <selectionStatus>      |
      | [0].startDate      | fdnxtmth::             |
    # Below enrollmentType is causing ClassCastException error
#      | [0].enrollmentType | delete::               |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | no                       |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | isEnrollmentProviderRequired | no                       |
      | recordId                     | 16                       |
      | enrollmentStartDate          | 1stDayofNextMonth        |
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
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
#    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","New Enrollment Reject"
    # @API-CP-16184-02
    Then I verify benefit status records are displayed with benefit status "Eligible"
    # @API-CP-16184-03
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>    |
      | [0].consumerAction       | Enroll      |
      | [0].planSelectionAllowed | true        |
    And Wait for 5 seconds
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | enrollmentCreated    | <enrollmentCreated> |
      | txnStatus            | REJECTED            |
      | updatedOn            | current             |
      | updatedBy            | 597                 |
      | medicalPlanBeginDate | 1stDayofNextMonth   |
      | medicalPlanEndDate   | null                |
      | enrollmentType       | MEDICAL             |
      | channel              | SYSTEM_INTEGRATION  |
      | anniversaryDate      | null                |
      | planCode             | 84                  |
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | <name>.consumerId       |
      | consumerFound | true                    |
      | recordId      | <recordId>              |
    # recordId = 16 means ENROLLMENT_UPDATE_EVENT was published for New Enrollment Reject
    # @API-CP-16184-04
    Then I verify if tasks for consumer id "<name>.consumerId" is created "<taskCreated>"
    Examples:  consumer’s enrollment segment is in a Selection Status of "Submitted to State" OR "Disregarded"
      | name | selectionStatus    | enrollmentCreated | recordId | taskCreated | action      |
      | RH1  | SUBMITTED_TO_STATE | true              | 16       | true        | Required    |
      | RH2  | DISREGARDED        | false             | null     | false       | Unavailable |

  @API-CP-16142 @API-CP-16142-01 @API-CP-16142-03 @API-CP-16142-04 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: Create or Update Data for MMIS Sends Disenroll with Loss of Eligibility AC 1.0 (enrollment=“DISENROLLED”,  Selection=“ACCEPTED”)
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
    Then I send API call with name "RV1" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RV1.consumerId       |
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
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | RV1.consumerId     |
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
    And I initiated get enrollment by consumer id "RV1.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "RV1a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | RV1.consumerId                |
      | [0].planId             | delete::                      |
      | [0].planCode           | 84                            |
      | [0].enrollmentId       | RV1a.discontinuedEnrollmentId |
      | [0].status             | ACCEPTED                      |
      | [0].txnStatus          | DISENROLLED                   |
      | [0].startDate          | fdofmnth::                    |
      | [0].endDate            | lstdaymnth::                  |
      | [0].enrollmentType     | delete::                      |
      | [0].subProgramTypeCode | MEDICAIDGF                    |
      | [0].serviceRegionCode  | East                          |
      | [1].planId             | delete::                      |
      | [1].planCode           | 85                            |
      | [1].consumerId         | RV1.consumerId                |
      | [1].enrollmentId       | RV1a.selectedEnrollmentId     |
      | [1].status             | SUBMITTED_TO_STATE            |
      | [1].txnStatus          | SUBMITTED_TO_STATE            |
      | [1].startDate          | fdnxtmth::                    |
      | [1].subProgramTypeCode | MEDICAIDGF                    |
      | [1].serviceRegionCode  | East                          |
    And Wait for 5 seconds
    And I send API call with name "OU" for process Outbound Update Enrollments
      | [0].planId            | 145            |
      | [0].planCode          | 84             |
      | [0].consumerId        | RV1.consumerId |
      | [0].txnStatus         | DISENROLLED    |
      | [0].startDate         | fdofmnth::     |
      | [0].endDate           | null::         |
      | [0].serviceRegionCode | delete::       |
      # Below channel is causing ClassCastException error
#      | [0].channel           | delete::       |
      | [0].planEndDateReason | null::         |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RV1.consumerId        |
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
    And I verify enrollment by consumer id "RV1.consumerId" with data
      | status             | DISENROLLED           |
      | medicalPlanEndDate | lastDayofPresentMonth |
      | planEndDateReason  | PLAN_HAS_TERMINATED   |
    And I initiated get benefit status by consumer id "RV1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Disenroll with Loss of Eligibility","Disenroll with Loss of Eligibility"
    # @API-CP-16142-03
    Then I verify benefit status records are displayed with benefit status "Not Eligible"
    # @API-CP-16142-04
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable |
      | [0].consumerAction       | null        |
      | [0].planSelectionAllowed | false       |
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT |
      | consumerId    | RV1.consumerId           |
      | consumerFound | true                     |
      | recordId      | 14                       |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | RV1.consumerId          |
      | consumerFound | true                    |
      | recordId      | 14                      |

  @API-CP-16142 @API-CP-16142-02 @API-CP-16142-03 @API-CP-16142-04 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: Create or Update Data for MMIS Sends Disenroll with Loss of Eligibility AC 2.0 (enrollment=“DISENROLL SUBMITTED”,  Selection=“ACCEPTED”)
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
    Then I send API call with name "RV2" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RV2.consumerId       |
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
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | RV2.consumerId     |
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
    And I initiated get enrollment by consumer id "RV2.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "RV2a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | RV2.consumerId                |
      | [0].planId             | delete::                      |
      | [0].planCode           | 84                            |
      | [0].enrollmentId       | RV2a.discontinuedEnrollmentId |
      | [0].status             | ACCEPTED                      |
      | [0].txnStatus          | DISENROLL_SUBMITTED           |
      | [0].startDate          | fdofmnth::                    |
      | [0].endDate            | lstdaymnth::                  |
      | [0].enrollmentType     | delete::                      |
      | [0].subProgramTypeCode | MEDICAIDGF                    |
      | [0].serviceRegionCode  | East                          |
      | [1].planId             | delete::                      |
      | [1].planCode           | 85                            |
      | [1].consumerId         | RV2.consumerId                |
      | [1].enrollmentId       | RV2a.selectedEnrollmentId     |
      | [1].status             | SUBMITTED_TO_STATE            |
      | [1].txnStatus          | SUBMITTED_TO_STATE            |
      | [1].startDate          | fdnxtmth::                    |
      | [1].subProgramTypeCode | MEDICAIDGF                    |
      | [1].serviceRegionCode  | East                          |
    And Wait for 5 seconds
    And I send API call with name "OU" for process Outbound Update Enrollments
      | [0].planId            | 145                 |
      | [0].planCode          | 84                  |
      | [0].consumerId        | RV2.consumerId      |
      | [0].txnStatus         | DISENROLL_SUBMITTED |
      | [0].startDate         | fdofmnth::          |
      | [0].endDate           | null::              |
      | [0].serviceRegionCode | delete::            |
      # Below channel is causing ClassCastException error
#      | [0].channel           | delete::            |
      | [0].planEndDateReason | null::              |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RV2.consumerId        |
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
    And I verify enrollment by consumer id "RV2.consumerId" with data
      | status             | DISENROLLED           |
      | medicalPlanEndDate | lastDayofPresentMonth |
      | planEndDateReason  | PLAN_HAS_TERMINATED   |
    And I initiated get benefit status by consumer id "RV2.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Disenroll with Loss of Eligibility","Disenroll with Loss of Eligibility"
    # @API-CP-16142-03
    Then I verify benefit status records are displayed with benefit status "Not Eligible"
    # @API-CP-16142-04
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable |
      | [0].consumerAction       | null        |
      | [0].planSelectionAllowed | false       |
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT |
      | consumerId    | RV2.consumerId           |
      | consumerFound | true                     |
      | recordId      | 14                       |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | RV2.consumerId          |
      | consumerFound | true                    |
      | recordId      | 14                      |

  @API-CP-16474 @API-CP-16474-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: Determine Enrollment Impacts and Create Disenroll Requests for Loss of Eligibility #2 AC 1.0 (enrollment=“DISENROLL_REQUESTED”,  Selection=“ACCEPTED”)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 16474 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 16474.consumerId     |
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
      | consumerId                   | 16474.consumerId     |
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
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 16474.consumerId      |
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
    Then I send API call with name "16474a" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "16474.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Disenroll with Loss of Eligibility","Disenroll with Loss of Eligibility"
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | 16474.consumerId        |
#      | correlationId | 16474a.traceid          |
      | consumerFound | true                    |
      | recordId      | 14                      |

  @API-CP-16474 @API-CP-16474-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: Determine Enrollment Impacts and Create Disenroll Requests for Loss of Eligibility #2 2.0 New Enrollments sent to MMIS ("Submitted to State")
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 16474-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 16474-2.consumerId   |
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
    Then I send API call with name "OU" for update Enrollment information
      | [0].consumerId         | 16474-2.consumerId |
      | [0].planId             | 145                |
      | [0].planCode           | 84                 |
      | [0].startDate          | nextdayplusone::   |
      | [0].programTypeCode    | H                  |
      | [0].subProgramTypeCode | MEDICAIDMCHB       |
      | [0].serviceRegionCode  | East               |
    And Wait for 5 seconds
    And I send API call with name "OU" for process Outbound Update
      | [0].consumerId         | 16474-2.consumerId     |
      | [0].planId             | delete::               |
      | [0].planCode           | 84                     |
      | [0].enrollmentId       | c.data[0].enrollmentId |
      | [0].status             | SUBMITTED_TO_STATE     |
      | [0].txnStatus          | SUBMITTED_TO_STATE     |
      | [0].startDate          | nextdayplusone::       |
      | [0].subProgramTypeCode | MEDICAIDMCHB           |
      | [0].endDate            | null::                 |
      | [0].serviceRegionCode  | delete::               |
      | [0].channel            | delete::               |
      | [0].enrollmentType     | delete::               |
      | [0].planEndDateReason  | null::                 |
    And Wait for 5 seconds

#    And Wait for 5 seconds
#    And I provide the enrollment and eligibility information to create enrollment
#      | consumerId                   | RV4.consumerId       |
#      | isEligibilityRequired        | no                   |
#      | otherSegments                |[blank]|
#      | isEnrollemntRequired         | yes                  |
#      | recordId                     | 4                    |
#      | isEnrollmentProviderRequired | no                   |
#      | enrollmentStartDate          | 1stDayofPresentMonth |
#      | eligibilityStartDate         | 1stDayofPresentMonth |
#      | txnStatus                    | Accepted             |
#      | programCode                  | H                    |
#      | subProgramTypeCode           | MEDICAIDMCHB         |
#      | planCode                     | 84                   |
#      | planId                       | 145                  |
#      | serviceRegionCode            | East                 |
#      | anniversaryDate              | anniversaryDate      |
#    And I run create Eligibility and Enrollment API
#    And Wait for 5 seconds
#    And I perform plan transfer via API to new plan with data
#      | [0].consumerId         | RV4.consumerId      |
#      | [0].planId             | 145                 |
#      | [0].planCode           | 85                  |
#      | [0].startDate          | fdnxtmth::          |
#      | [0].subProgramTypeCode | MEDICAIDMCHB        |
#      | [0].serviceRegionCode  | East                |
#      | [0].selectionReason    | delete::            |
#      | [0].anniversaryDate    | anniversaryDate::   |
#      | [0].channel            | SYSTEM_INTEGRATION  |
#      | [0].planEndDateReason  | PLAN_HAS_TERMINATED |
#    And Wait for 5 seconds
#    And I verify enrollment by consumer id "RV4.consumerId" with data
#      | createdOn          | current               |
#      | txnStatus          | DISENROLL_REQUESTED   |
#      | medicalPlanEndDate | lastDayofPresentMonth |
#      | planEndDateReason  | PLAN_HAS_TERMINATED   |
#    And I initiated get enrollment by consumer id "RV4.consumerId"
#    When I run get enrollment api
#    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "RV4a"
#    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
#      | [0].consumerId         | RV4.consumerId                |
#      | [0].planId             | delete::                      |
#      | [0].planCode           | 84                            |
#      | [0].enrollmentId       | RV4a.discontinuedEnrollmentId |
#      | [0].status             | ACCEPTED                      |
#      | [0].txnStatus          | DISENROLL_SUBMITTED           |
#      | [0].startDate          | fdofmnth::                    |
#      | [0].endDate            | lstdaymnth::                  |
#      | [0].enrollmentType     | delete::                      |
#      | [0].subProgramTypeCode | MEDICAIDMCHB                  |
#      | [0].serviceRegionCode  | East                          |
#      | [1].planId             | delete::                      |
#      | [1].planCode           | 85                            |
#      | [1].consumerId         | RV4.consumerId                |
#      | [1].enrollmentId       | RV4a.selectedEnrollmentId     |
#      | [1].status             | SUBMITTED_TO_STATE            |
#      | [1].txnStatus          | SUBMITTED_TO_STATE            |
#      | [1].startDate          | fdnxtmth::                    |
#      | [1].subProgramTypeCode | MEDICAIDMCHB                  |
#      | [1].serviceRegionCode  | East                          |
#    And Wait for 5 seconds
#    And I send API call with name "OU" for process Outbound Update Enrollments
#      | [0].planId            | 145                 |
#      | [0].planCode          | 84                  |
#      | [0].consumerId        | RV4.consumerId      |
#      | [0].txnStatus         | DISENROLL_SUBMITTED |
#      | [0].startDate         | fdofmnth::          |
#      | [0].endDate           | null::              |
#      | [0].serviceRegionCode | delete::            |
#      | [0].channel           | delete::            |
#      | [0].planEndDateReason | null::              |
#    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 16474-2.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | isEnrollmentProviderRequired | no                    |
      | recordId                     | 14                    |
      | anniversaryDate              | anniversaryDate       |
      | eligibilityStartDate         | 1stDayofPresentMonth  |
      | eligibilityEndDate           | lastDayofPresentMonth |
      | eligibilityEndReason         | Disenroll             |
      | enrollmentStartDate          | nextDayPlusOne        |
      | enrollmentEndDate            | lastDayofPresentMonth |
      | planEndDateReason            | PLAN_HAS_TERMINATED   |
      | txnStatus                    | Accepted              |
      | programCode                  | H                     |
      | subProgramTypeCode           | MEDICAIDMCHB          |
      | planCode                     | 84                    |
      | planId                       | 145                   |
      | serviceRegionCode            | East                  |
    Then I send API call with name "16474-2a" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "16474-2.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Disenroll with Loss of Eligibility","Disenroll with Loss of Eligibility"
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | DISREGARDED_NEW_ENROLLMENT |
      | consumerId    | 16474-2.consumerId         |
      | consumerFound | true                       |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | 16474-2.consumerId      |
      | correlationId | 16474-2a.traceid        |
      | consumerFound | true                    |
