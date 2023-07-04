Feature: Plan Change Accept and Reject Scenarios for DCEB


  @API-CP-39003 @API-CP-39801 @API-CP-47287 @API-DC-EE @DC-EB-API-Regression @kursat
  Scenario Outline: DC EB  Decide Scenario Plan Change Accept Response  DCHF
  CP-39801 DC EB: Decide Scenario for Plan Change Accept Response (Alliance & Immigrant Children)
  CP-47287 DC EB: Create/Update for Plan Change Accept Response (Alliance & Immigrant Children)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | coverageCode                 | <coverageCode>        |
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | isEnrollmentProviderRequired | no                    |
      | recordId                     | 25                    |
      | eligibilityStartDate         | 1stDayofLastMonth     |
      | eligibilityEndDate           | highDate-DC           |
      | enrollmentStartDate          | 1stDayofPresentMonth  |
      | enrollmentEndDate            | highDate-DC           |
      | txnStatus                    | Accepted              |
      | programCode                  | MEDICAID              |
      | subProgramTypeCode           | <subProgramTypeCode>  |
      | categoryCode                 | 10                    |
      | planCode                     | <planCode>            |
      | planId                       | 104                   |
      | serviceRegionCode            | Northeast             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | selectionReason              | selectionReason1      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Default Eligibility","Default Enrollment"
    Then I verify benefit status information with data
      | programPopulation | <population> |
      | benefitStatus     | Enrolled     |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available               |
      | [0].consumerAction       | Plan Change Anniversary |
      | [0].planSelectionAllowed | true                    |
      | [1].action               | Available               |
      | [1].consumerAction       | Plan Change Pre-lockin  |
      | [1].planSelectionAllowed | true                    |
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | <name>.consumerId    |
      | [0].planId             | 26                   |
      | [0].planCode           | <planCode2>          |
      | [0].startDate          | fdnxtmth::           |
      | [0].endDate            | highdatedc::         |
      | [0].subProgramTypeCode | <subProgramTypeCode> |
      | [0].serviceRegionCode  | Northeast            |
      | [0].selectionReason    | 02                   |
      | [0].anniversaryDate    | anniversaryDate::    |
      | [0].channel            | SYSTEM_INTEGRATION   |
    And Wait for 3 seconds
    And I initiated get enrollment by consumer id "<name>.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "<name>b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | <name>.consumerId                |
      | [0].planId             | delete::                         |
      | [0].planCode           | <planCode>                       |
      | [0].enrollmentId       | <name>b.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED              |
      | [0].txnStatus          | DISENROLL_SUBMITTED              |
      | [0].startDate          | fdofmnth::                       |
      | [0].endDate            | lstdaymnth::                     |
      | [0].enrollmentType     | delete::                         |
      | [0].subProgramTypeCode | <subProgramTypeCode>             |
      | [0].serviceRegionCode  | Northeast                        |
      | [1].planId             | delete::                         |
      | [1].planCode           | <planCode2>                      |
      | [1].consumerId         | <name>.consumerId                |
      | [1].enrollmentId       | <name>b.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                       |
      | [1].endDate            | highdatedc::                     |
      | [1].subProgramTypeCode | <subProgramTypeCode>             |
      | [1].serviceRegionCode  | Northeast                        |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event>        |
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 17                   |
      | enrollmentRecordId           | 17                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | enrollmentEndDate            | highDate-DC          |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | planCode                     | <planCode2>          |
      | planId                       | 26                   |
      | serviceRegionCode            | Northeast            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth            |
      | endDate           | highDate-DC                  |
      | genericFieldText1 | <name>b.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                    |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    # CP-39801 1.0 Decide Scenario is MMIS Sends Plan Change Accept Response
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Default Eligibility","Plan Change Enrollment Accept"
    Then I verify benefit status information with data
      | programPopulation | <population> |
      | benefitStatus     | Enrolled     |
    # CP-47287 3.0 Re-determining Consumer Actions and Change Window
    Then I Verify Consumer Actions as following data
      | [0].action               | Available                              |
      | [0].consumerAction       | Plan Change Anniversary                |
      | [0].planSelectionAllowed | true                                   |
      | [0].enrollmentStartDate  | <name><event>.enrollments[0].startDate |
      | [0].changeAllowedFrom    | anniversaryWindow                      |
      | [0].changeAllowedUntil   | anniversaryWindow                      |
    And Wait for 3 seconds
    # CP-47287 1.0 Updating the consumers enrollment Selection Status as “Accepted” for the Requested plan
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      | txnStatus            | ACCEPTED           |
      | updatedOn            | current            |
      | updatedBy            | SYSTEM_INTEGRATION |
      | channel              | SYSTEM_INTEGRATION |
      | createdOn            | current            |
      | createdBy            | null               |
      | planCode             | <planCode2>        |
      | medicalPlanBeginDate | 1stDayofNextMonth  |
      | medicalPlanEndDate   | highDate-DC        |
      | planEndDateReason    | null               |
      | enrollmentType       | MEDICAL            |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT                      |
      | correlationId | <name><event>.enrollments.[0].correlationId  |
      | txnStatus     | ACCEPTED                                     |
      | consumerId    | <name>.consumerId                            |
      | consumerFound | true                                         |
      | DPBI          |[blank]|
    # CP-47287 2.0 Updating the consumers enrollment Selection Status as “Disenrolled” for the Prior plan
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | txnStatus            | DISENROLLED           |
      | updatedOn            | current               |
      | updatedBy            | SYSTEM_INTEGRATION    |
      | planCode             | <planCode>            |
      | medicalPlanBeginDate | 1stDayofPresentMonth  |
      | medicalPlanEndDate   | lastDayofPresentMonth |
      | selectionReason      | selectionReason1      |
      | enrollmentType       | MEDICAL               |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT                      |
      | correlationId | <name><event>.enrollments.[0].correlationId  |
      | txnStatus     | DISENROLLED                                  |
      | consumerId    | <name>.consumerId                            |
      | consumerFound | true                                         |
      | DPBI          |[blank]|
    Examples:
      | name     | event       | coverageCode | subProgramTypeCode | planCode  | planCode2 | population         |
      | 39003-01 | Plan Change | 130          | DCHF               | 081080400 | 055558200 | DCHF-Adult         |
      | 39003-02 | Plan Change | 420          | ImmigrantChildren  | 081080400 | 055558200 | Immigrant Children |
      | 39003-03 | Plan Change | 460          | Alliance           | 087358900 | 077573200 | Alliance-Child     |


  @API-CP-42984 @API-CP-42984-01 @API-CP-42983 @API-CP-42983-01 @API-CP-42983-01 @API-CP-43254 @API-CP-48200 @shruti @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: Plan Change Reject Scenario -DCHF-Record 18
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event>        |
      | coverageCode                 | 750Y                 |
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 25                   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate-DC          |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate-DC          |
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | DCHF                 |
      | categoryCode                 | 10                   |
      | genericFieldText1            | null                 |
      | planCode                     | 081080400            |
      | planId                       | 104                  |
      | serviceRegionCode            | Northeast            |
      | channel                      | SYSTEM_INTEGRATION   |
      | selectionReason              | selectionReason1     |
    And I run create Eligibility and Enrollment API
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | <name>.consumerId |
      | [0].planId             | 26                |
      | [0].planCode           | 055558200         |
      | [0].startDate          | fdnxtmth::        |
      | [0].endDate            | highdatedc::      |
      | [0].subProgramTypeCode | DCHF              |
      | [0].serviceRegionCode  | Northeast         |
      | [0].selectionReason    | 02                |
      | [0].anniversaryDate    | anniversaryDate:: |
      | [0].channel            | PHONE             |
    And Wait for 10 seconds
    And I initiated get enrollment by consumer id "<name>.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "<name>b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | <name>.consumerId                |
      | [0].planId             | delete::                         |
      | [0].planCode           | 081080400                        |
      | [0].enrollmentId       | <name>b.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED              |
      | [0].txnStatus          | DISENROLL_SUBMITTED              |
      | [0].startDate          | fdofmnth::                       |
      | [0].endDate            | lstdaymnth::                     |
      | [0].enrollmentType     | delete::                         |
      | [0].subProgramTypeCode | DCHF                             |
      | [0].serviceRegionCode  | Northeast                        |
      | [1].planId             | delete::                         |
      | [1].planCode           | 055558200                        |
      | [1].consumerId         | <name>.consumerId                |
      | [1].enrollmentId       | <name>b.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                       |
      | [1].endDate            | highdatedc::                     |
      | [1].subProgramTypeCode | DCHF                             |
      | [1].serviceRegionCode  | Northeast                        |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId  |
      | saveEligibilityEventName     | <name><event2>     |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | isEnrollmentProviderRequired | no                 |
      | recordId                     | 18                 |
      | enrollmentRecordId           | 18                 |
      | enrollmentStartDate          | 1stDayofNextMonth  |
      | enrollmentEndDate            | highDate-DC        |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted           |
      | programCode                  | MEDICAID           |
      | subProgramTypeCode           | DCHF               |
      | planCode                     | 055558200          |
      | planId                       | 26                 |
      | serviceRegionCode            | Northeast          |
      | anniversaryDate              | anniversaryDate    |
      | channel                      | SYSTEM_INTEGRATION |
      | requestedBy                  | 1                  |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth            |
      | endDate           | highDate-DC                  |
      | genericFieldText1 | <name>b.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                    |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Default Eligibility","Plan Change Reject"
    Then I verify benefit status information with data
      | programPopulation | DCHF-Disabled |
      | benefitStatus     | Enrolled      |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available               |
      | [0].consumerAction       | Plan Change Anniversary |
      | [0].planSelectionAllowed | true                    |
      | [1].action               | Available               |
      | [1].consumerAction       | Plan Change Pre-lockin  |
      | [1].planSelectionAllowed | true                    |
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      | txnStatus            | REJECTED          |
      | updatedOn            | current           |
      | updatedBy            | 1                 |
      | channel              | PHONE             |
      | createdOn            | current           |
      | createdBy            | null              |
      | planCode             | 055558200         |
      | medicalPlanBeginDate | 1stDayofNextMonth |
      | medicalPlanEndDate   | highDate-DC       |
      | planEndDateReason    | null              |
      | enrollmentType       | MEDICAL           |
    And Wait for 3 seconds
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | txnStatus            | ACCEPTED             |
      | updatedOn            | current              |
      | updatedBy            | 1                    |
      | planCode             | 081080400            |
      | medicalPlanBeginDate | 1stDayofPresentMonth |
      | medicalPlanEndDate   | highDate-DC          |
      | selectionReason      | selectionReason1     |
      | enrollmentType       | MEDICAL              |
      | anniversaryDate      | anniversaryDateDC    |
      # plan end date reason will be decided later as stated in the story CP-42984
      | planEndDateReason    | null                 |
    # Below DPBI validation is for ACCEPTED status. Once CP-43371 is fixed there should be another DPBI validation of ENROLLMENT_UPDATE_EVENT for REJECTED status
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT                      |
      | correlationId | <name><event2>.enrollments.[0].correlationId |
      | consumerId    | <name>.consumerId                            |
      | consumerFound | true                                         |
      | DPBI          |[blank]|
    And Wait for 6 seconds
    Then I will verify business events are generated with data
      | eventName                              | PLAN_CHANGE_REJECT_RESPONSE |
      | channel                                | PHONE                       |
      | createdBy                              | 1                           |
      | processDate                            | current                     |
      | consumerId                             | <name>.consumerId           |
      | consumerName                           | <name>                      |
      | requestedSelection.enrollmentStartDate | 1stDayofNextMonth           |
      | requestedSelection.enrollmentEndDate   | highDate-DC                 |
      | requestedSelection.planSelectionReason | null                        |
      | requestedSelection.rejectionReason     | null                        |
      | requestedSelection.planChangeReason    | null                        |
      | requestedSelection.planCode            | 055558200                   |
      | requestedSelection.enrollmentType      | MEDICAL                     |
      | requestedSelection.selectionStatus     | REJECTED                    |
      | requestedSelection.pcpNpi              | null                        |
      | requestedSelection.pcpName             | null                        |
      | requestedSelection.pdpNpi              | null                        |
      | requestedSelection.pdpName             | null                        |

    Examples:
      | name     | event      | event2   |
      | 42983-01 | Enrollment | Response |


  @API-CP-39574 @API-CP-39574-01 @shruti @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: VErify Rejected Seletion Task i created for all sub program types 18
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event>        |
      | coverageCode                 | <coverageCode>       |
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 25                   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate-DC          |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate-DC          |
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | genericFieldText1            | null                 |
      | planCode                     | 081080400            |
      | planId                       | 104                  |
      | serviceRegionCode            | Northeast            |
      | channel                      | SYSTEM_INTEGRATION   |
      | selectionReason              | selectionReason1     |
    And I run create Eligibility and Enrollment API
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | <name>.consumerId |
      | [0].planId             | 26                |
      | [0].planCode           | 055558200         |
      | [0].startDate          | fdnxtmth::        |
      | [0].endDate            | highdatedc::      |
      | [0].subProgramTypeCode | DCHF              |
      | [0].serviceRegionCode  | Northeast         |
      | [0].selectionReason    | 02                |
      | [0].anniversaryDate    | anniversaryDate:: |
      | [0].channel            | PHONE             |
    And Wait for 10 seconds
    And I initiated get enrollment by consumer id "<name>.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "<name>b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | <name>.consumerId                |
      | [0].planId             | delete::                         |
      | [0].planCode           | 081080400                        |
      | [0].enrollmentId       | <name>b.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED              |
      | [0].txnStatus          | DISENROLL_SUBMITTED              |
      | [0].startDate          | fdofmnth::                       |
      | [0].endDate            | lstdaymnth::                     |
      | [0].enrollmentType     | delete::                         |
      | [0].subProgramTypeCode | DCHF                             |
      | [0].serviceRegionCode  | Northeast                        |
      | [1].planId             | delete::                         |
      | [1].planCode           | 055558200                        |
      | [1].consumerId         | <name>.consumerId                |
      | [1].enrollmentId       | <name>b.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                       |
      | [1].endDate            | highdatedc::                     |
      | [1].subProgramTypeCode | <subProgramTypeCode>             |
      | [1].serviceRegionCode  | Northeast                        |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | saveEligibilityEventName     | <name><event2>       |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 18                   |
      | enrollmentRecordId           | 18                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | enrollmentEndDate            | highDate-DC          |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | rejectionReason              | <rejectionReason>    |
      | planCode                     | 055558200            |
      | planId                       | 26                   |
      | serviceRegionCode            | Northeast            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | requestedBy                  | 1                    |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth            |
      | endDate           | highDate-DC                  |
      | genericFieldText1 | <name>b.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                    |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Default Eligibility","Plan Change Reject"
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      | txnStatus            | REJECTED          |
      | updatedOn            | current           |
      | updatedBy            | 1                 |
      | channel              | PHONE             |
      | createdOn            | current           |
      | createdBy            | null              |
      | planCode             | 055558200         |
      | medicalPlanBeginDate | 1stDayofNextMonth |
      | medicalPlanEndDate   | highDate-DC       |
      | planEndDateReason    | null              |
      | enrollmentType       | MEDICAL           |
    And Wait for 3 seconds
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | txnStatus            | ACCEPTED             |
      | updatedOn            | current              |
      | updatedBy            | 1                    |
      | planCode             | 081080400            |
      | medicalPlanBeginDate | 1stDayofPresentMonth |
      | medicalPlanEndDate   | highDate-DC          |
      | selectionReason      | selectionReason1     |
      | enrollmentType       | MEDICAL              |
      | anniversaryDate      | anniversaryDateDC    |
    Then I verify latest task information with name "" for consumer id "<name>.consumerId" with data
      | saveResponseWithName | <name>-taskInfo                                            |
      | staffAssignedTo      | null                                                       |
      | createdBy            | 597                                                        |
      | createdOn            | current                                                    |
#      | defaultDueDate  | in2BusinessDays                                                                                                       |
#      | dueInDays       | 7                                                                                                                     |
#      | defaultPriority | 2                                                                                                                     |
      | taskStatus           | Created                                                    |
      | statusDate           | current                                                    |
    # taskInfo has 8 info areas: PlanID,PlanName,PlanStartDate,PlanEndDate,Program,Disposition,ExternalConsumerID,ReasonText
      | taskInfo             | Reject Enrollment  Medicaid ID ^ <name>.externalConsumerId |
    # CP-24256 optional
      | channel              | SYSTEM_INTEGRATION                                         |
      | caseId               | SYSTEM_INTEGRATION                                         |
      | externalCaseId       | <name>.caseIdentificationNumber                            |
      | disposition          | null                                                       |
      # planId is dinamic -> not possible to automate need JDBC
      | planId               | 055558200                                                  |
      | planName             | CAREFIRST COMMUNITY HEALTH PLAN DC                         |
      | planStartDate        | 1stDayofNextMonth                                          |
      | endDate              | highDate-DC                                                |
      | program              | <subProgramTypeCode>                                       |
      | reason               | <reason>                                                   |
    Then I will verify link events are generated with data
      # CP-23758 1.0 Business Event Required Fields
      | eventName     | LINK_EVENT                                     |
      | correlationId | <name>-taskInfo.tasks.[0].taskVO.correlationId |
      | consumerId    | <name>.consumerId                              |
      | taskId        | <name>-taskInfo.tasks.[0].taskVO.taskId        |
    Examples:
      | name     | event      | event2   | coverageCode | subProgramTypeCode | rejectionReason | reason      |
      | 39574-01 | Enrollment | Response | 750Y         | DCHF               | 800             | 800-Unknown |
#      | 39574-02 | Enrollment | Response | 460          | Alliance           | 310                | 310 - Invalid MCO enrollment begin date                       |
#      | 39574-03 | Enrollment | Response | 420          | ImmigrantChildren  | REJECTION_REASON01 | REJECTION_REASON01 - Plan is not in consumer's service region |
#      | 39574-04 | Enrollment | Response | 750Y         | DCHF               | 434                | 434 - Non-alliance provider with Alliance program code        |
#      | 39574-05 | Enrollment | Response | 460          | Alliance           | 220                | 220 - Member is already enrolled                              |
#      | 39574-06 | Enrollment | Response | 420          | ImmigrantChildren  | REJECTION_REASON02 | REJECTION_REASON02 - Plan is not for consumer's eligibility   |


  @API-CP-43255 @API-DC-EE @DC-EB-API-Regression @kursat
  Scenario Outline: DC EB: Create Business Event - Plan Change Reject Response
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | coverageCode                 | <coverageCode>       |
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |                      |
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 25                   |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | highDate-DC          |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate-DC          |
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | categoryCode                 | 10                   |
      | planCode                     | <planCode>           |
      | planId                       | 104                  |
      | serviceRegionCode            | Northeast            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | selectionReason              | selectionReason1     |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Default Eligibility","Default Enrollment"
    Then I verify benefit status information with data
      | programPopulation | <population> |
      | benefitStatus     | Enrolled     |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available               |
      | [0].consumerAction       | Plan Change Anniversary |
      | [0].planSelectionAllowed | true                    |
      | [1].action               | Available               |
      | [1].consumerAction       | Plan Change Pre-lockin  |
      | [1].planSelectionAllowed | true                    |
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | <name>.consumerId    |
      | [0].planId             | 26                   |
      | [0].planCode           | <planCode2>          |
      | [0].startDate          | fdnxtmth::           |
      | [0].endDate            | highdatedc::         |
      | [0].subProgramTypeCode | <subProgramTypeCode> |
      | [0].serviceRegionCode  | Northeast            |
      | [0].selectionReason    | 02                   |
      | [0].anniversaryDate    | anniversaryDate::    |
      | [0].channel            | SYSTEM_INTEGRATION   |
    And Wait for 3 seconds
    And I initiated get enrollment by consumer id "<name>.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "<name>b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | <name>.consumerId                |
      | [0].planId             | delete::                         |
      | [0].planCode           | <planCode>                       |
      | [0].enrollmentId       | <name>b.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED              |
      | [0].txnStatus          | DISENROLL_SUBMITTED              |
      | [0].startDate          | fdofmnth::                       |
      | [0].endDate            | lstdaymnth::                     |
      | [0].enrollmentType     | delete::                         |
      | [0].subProgramTypeCode | <subProgramTypeCode>             |
      | [0].serviceRegionCode  | Northeast                        |
      | [1].planId             | delete::                         |
      | [1].planCode           | <planCode2>                      |
      | [1].consumerId         | <name>.consumerId                |
      | [1].enrollmentId       | <name>b.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                       |
      | [1].endDate            | highdatedc::                     |
      | [1].subProgramTypeCode | <subProgramTypeCode>             |
      | [1].serviceRegionCode  | Northeast                        |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event>        |
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | no                   |
      | otherSegments                |                      |
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 18                   |
      | enrollmentRecordId           | 18                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | enrollmentEndDate            | highDate-DC          |
      | eligibilityStartDate         |                      |
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | planCode                     | <planCode2>          |
      | planId                       | 26                   |
      | serviceRegionCode            | Northeast            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth            |
      | endDate           | highDate-DC                  |
      | genericFieldText1 | <name>b.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                    |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Default Eligibility","Plan Change Reject"
    Then I verify benefit status information with data
      | programPopulation | <population> |
      | benefitStatus     | Enrolled     |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available               |
      | [0].consumerAction       | Plan Change Anniversary |
      | [0].planSelectionAllowed | true                    |
      | [1].action               | Available               |
      | [1].consumerAction       | Plan Change Pre-lockin  |
      | [1].planSelectionAllowed | true                    |
    And Wait for 3 seconds
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      | txnStatus            | REJECTED           |
      | updatedOn            | current            |
      | updatedBy            | 597                |
      | channel              | SYSTEM_INTEGRATION |
      | createdOn            | current            |
      | createdBy            | null               |
      | planCode             | <planCode2>        |
      | medicalPlanBeginDate | 1stDayofNextMonth  |
      | medicalPlanEndDate   | highDate-DC        |
      | planEndDateReason    | null               |
      | enrollmentType       | MEDICAL            |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT                     |
      | correlationId | <name><event>.enrollments.[0].correlationId |
      | txnStatus     | REJECTED                                    |
      | consumerId    | <name>.consumerId                           |
      | consumerFound | true                                        |
      | DPBI          |                                             |
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | txnStatus            | ACCEPTED              |
      | updatedOn            | current               |
      | updatedBy            | 597                   |
      | planCode             | <planCode>            |
      | medicalPlanBeginDate | 1stDayofPresentMonth  |
      | medicalPlanEndDate   | highDate-DC           |
      | selectionReason      | selectionReason1      |
      | enrollmentType       | MEDICAL               |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT                     |
      | correlationId | <name><event>.enrollments.[0].correlationId |
      | txnStatus     | ACCEPTED                                    |
      | consumerId    | <name>.consumerId                           |
      | consumerFound | true                                        |
      | DPBI          |                                             |
    Then I will verify business events are generated with data
      | eventName                              | PLAN_CHANGE_REJECT_RESPONSE |
      | channel                                | SYSTEM_INTEGRATION          |
      | createdBy                              | 597                         |
      | processDate                            | current                     |
      | consumerId                             | <name>.consumerId           |
      | consumerName                           | <name>                      |
      | previousEnrollment.enrollmentStartDate | 1stDayofPresentMonth        |
      | previousEnrollment.enrollmentEndDate   | highDate-DC                 |
      | previousEnrollment.planSelectionReason | selectionReason1            |
      | previousEnrollment.planChangeReason    | null                        |
      | previousEnrollment.rejectionReason     | null                        |
      | previousEnrollment.planCode            | <planCode>                  |
      | previousEnrollment.enrollmentType      | MEDICAL                     |
      | previousEnrollment.selectionStatus     | ACCEPTED                    |
      | previousEnrollment.anniversaryDate     | anniversaryDateDC           |
      | previousEnrollment.pcpNpi              | null                        |
      | previousEnrollment.pcpName             | null                        |
      | previousEnrollment.pdpNpi              | null                        |
      | previousEnrollment.pdpName             | null                        |
      | requestedSelection.enrollmentStartDate | 1stDayofNextMonth           |
      | requestedSelection.enrollmentEndDate   | highDate-DC                 |
      | requestedSelection.planSelectionReason | null                        |
      | requestedSelection.planChangeReason    | null                        |
      | requestedSelection.rejectionReason     | null                        |
      | requestedSelection.planCode            | <planCode2>                 |
      | requestedSelection.enrollmentType      | MEDICAL                     |
      | requestedSelection.selectionStatus     | REJECTED                    |
      | requestedSelection.anniversaryDate     | anniversaryDateDC           |
      | requestedSelection.pcpNpi              | null                        |
      | requestedSelection.pcpName             | null                        |
      | requestedSelection.pdpNpi              | null                        |
      | requestedSelection.pdpName             | null                        |
    Examples:
      | name     | event       | coverageCode | subProgramTypeCode | planCode  | planCode2 | population         |
      | 43255-01 | Plan Change | 130          | DCHF               | 081080400 | 055558200 | DCHF-Adult         |
      | 43255-02 | Plan Change | 420          | ImmigrantChildren  | 081080400 | 055558200 | Immigrant Children |
      | 43255-03 | Plan Change | 460          | Alliance           | 087358900 | 077573200 | Alliance-Child     |