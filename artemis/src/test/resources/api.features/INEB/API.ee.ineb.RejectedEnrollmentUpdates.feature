Feature: IN-EB Rejected Enrollment updates

  @API-CP-29356 @API-CP-24258 @API-CP-24258-03 @API-CP-28092 @API-CP-28092-02 @API-CP-23748 @API-CP-23747 @API-EE-IN @kursat
  Scenario Outline: IN-EB Updated - IN-EB Decide New Enrollment Responses (HHW positive Record Id 16) 1
  CP-24258 IN-EB Create Rejected Selection Task - New Enrollment
  CP-23748 IN-EB Create or Update Data based on New Enrollment Reject Response
  CP-23747 IN-EB: Create Business Event - New Enrollment Reject Response
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | eligibilityRecordId          | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofLastMonth    |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityStatusCode        | M                    |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Then I send API call with name "OU" for update Enrollment information
      | [0].consumerId         | <name>.consumerId    |
      | [0].planId             | 26                   |
      | [0].planCode           | <planCode>           |
      | [0].startDate          | fdnxtmth::           |
      | [0].endDate            | highDate::           |
      | [0].subProgramTypeCode | <subProgramTypeCode> |
      | [0].serviceRegionCode  | Statewide            |
      | [0].selectionReason    | 02                   |
      | [0].channel            | SYSTEM_INTEGRATION   |
      | [0].anniversaryDate    | anniversaryDate::    |
    And Wait for 5 seconds
    And I send API call with name "OU" for process Outbound Update
      | [0].planId             | delete::               |
      | [0].planCode           | <planCode>             |
      | [0].consumerId         | <name>.consumerId      |
      | [0].enrollmentId       | c.data[0].enrollmentId |
      | [0].startDate          | fdnxtmth::             |
      | [0].endDate            | highDate::             |
      | [0].subProgramTypeCode | <subProgramTypeCode>   |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 16                   |
      | enrollmentRecordId           | 16                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | enrollmentEndDate            | highDate             |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | planCode                     | <planCode>           |
      | planId                       | 26                   |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | correlationId                |[blank]|
    And User provide other enrollment segments details
      | consumerId        | <name>.consumerId      |
      | startDate         | 1stDayofNextMonth      |
      | endDate           | highDate               |
      | genericFieldText1 | c.data[0].enrollmentId |
      | segmentTypeCode   | OTHER_TXN              |
    And User provide other enrollment segments details
      | genericFieldText1 | 010          |
      | segmentTypeCode   | ERROR_REASON |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","New Enrollment Reject"
    Then I verify benefit status information with data
    # CP-23748 2.0 Decide Program-Population
      | programPopulation | <programPopulation> |
    # CP-23748 3.0 Decide Benefit Status
      | benefitStatus     | Eligible            |
    # CP-23748 4.0 Re-determine Consumer Actions and Change Window
    Then I Verify Consumer Actions as following data
      | [0].action               | Required |
      | [0].consumerAction       | Enroll   |
      | [0].planSelectionAllowed | true     |
    # CP-23748 1.0 Update the consumer’s enrollment selection
    And I verify enrollment by consumer id "<name>.consumerId" with data
       # will updtae
      | txnStatus            | REJECTED           |
      | status               | REJECTED           |
      | rejectionReason      | null               |
      | updatedOn            | current            |
      | updatedBy            | 597                |
      # remains same
      | medicalPlanBeginDate | 1stDayofNextMonth  |
      | medicalPlanEndDate   | highDate           |
      | enrollmentType       | MEDICAL            |
      | channel              | SYSTEM_INTEGRATION |
      | createdBy            | 2893               |
      | createdOn            | current            |
      | planCode             | <planCode>         |
      | planEndDateReason    | null               |
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
#      | correlationId | <name>a.traceid         |
      | consumerId    | <name>.consumerId       |
      | consumerFound | true                    |
      | recordId      | 16                      |
      | DPBI          |[blank]|
      # if recordId = 16 -> ENROLLMENT_UPDATE scenario recognized -> "New Enrollment Reject"
    Then I verify latest task information with name "" for consumer id "<name>.consumerId" with data
      | staffAssignedTo | null                                                   |
      | createdBy       | 597                                                    |
      | createdOn       | current                                                |
      | defaultDueDate  | in2BusinessDays                                        |
      | dueInDays       | 2                                                      |
      | defaultPriority | 1                                                      |
      | taskStatus      | Created                                                |
      | statusDate      | current                                                |
    # taskInfo has 3 info areas: reason contains/externalCaseId/what is Internal Scenario Code
    # empty fiels or null ==  taskInfo = null
    # not null == validates that taskInfo value is not null
      | taskInfo        | Reject Reason Medicaid ID/<name>.externalConsumerId/16 |
    # CP-24258 optional
      | channel         | SYSTEM_INTEGRATION                                     |
      | caseId          | <name>.caseId                                          |
      | externalCaseId  | <name>.caseIdentificationNumber                        |
      | disposition     | null                                                   |
      # planId is dinimic -> not possible to automate need JDBC
#      | planId          | <planId>                                               |
      | planName        | <planName>                                             |
      | planStartDate   | 1stDayofNextMonth                                      |
      | endDate         | highDate                                               |
      | program         | <subProgramTypeCode>                                   |
      | linkToCase      | <name>.caseId                                          |
      | linkToConsumer  | <name>.consumerId                                      |
      | applicationType | New Enrollment                                         |
#      | reason          | null                                                   |
      | reason          | 010 - Aid category / dates not effective/valid         |
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      #  CP-23747 1.0 Business Event Required Fields
      | eventName           | NEW_ENROLLMENT_REJECT_RESPONSE  |
      | module              | ENROLLMENT                      |
#      | correlationId       | <name>a.enrollments.[0].correlationId |
      | channel             | SYSTEM_INTEGRATION              |
      | createdBy           | 597                             |
      | processDate         | current                         |
      | externalCaseId      | <name>.caseIdentificationNumber |
      | externalConsumerId  | <name>.externalConsumerId       |
      | consumerId          | <name>.consumerId               |
      | consumerName        | <name>                          |
      #  CP-23747 2.0 Business Event Optional Fields
      | taskId              | c.tasks[0].taskVO.taskId        |
      | enrollmentStartDate | 1stDayofNextMonth               |
      | enrollmentEndDate   | highDate                        |
      | planSelectionReason | 02                              |
      | planChangeReason    | null                            |
      | rejectionReason     | null                            |
      | planCode            | <planCode>                      |
      | enrollmentType      | MEDICAL                         |
      | selectionStatus     | REJECTED                        |
      | anniversaryDate     | anniversaryDate                 |
    Examples:
      | name    | programCode | subProgramTypeCode | planCode  | programPopulation | planName | planId |
      | 23748-1 | R           | HoosierHealthwise  | 400752220 | HHW-Mandatory     | ANTHEM   | 103    |


  @API-CP-29356 @API-CP-24258 @API-CP-24258-03 @API-CP-28092 @API-CP-28092-02 @API-CP-23748 @API-CP-23747 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: IN-EB Updated - IN-EB Decide New Enrollment Responses (HCC positive Record Id 16) 2
  CP-24258 IN-EB Create Rejected Selection Task - New Enrollment
  CP-23748 IN-EB Create or Update Data based on New Enrollment Reject Response
  CP-23747 IN-EB: Create Business Event - New Enrollment Reject Response
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | eligibilityRecordId          | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofLastMonth    |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityStatusCode        | M                    |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Then I send API call with name "OU" for update Enrollment information
      | [0].consumerId         | <name>.consumerId    |
      | [0].planId             | 26                   |
      | [0].planCode           | <planCode>           |
      | [0].startDate          | fdnxtmth::           |
      | [0].endDate            | highDate::           |
      | [0].subProgramTypeCode | <subProgramTypeCode> |
      | [0].serviceRegionCode  | Statewide            |
      | [0].selectionReason    | 02                   |
      | [0].channel            | SYSTEM_INTEGRATION   |
      | [0].anniversaryDate    | anniversaryDate::    |
    And Wait for 5 seconds
    And I send API call with name "OU" for process Outbound Update
      | [0].planId             | delete::               |
      | [0].planCode           | <planCode>             |
      | [0].consumerId         | <name>.consumerId      |
      | [0].enrollmentId       | c.data[0].enrollmentId |
      | [0].startDate          | fdnxtmth::             |
      | [0].endDate            | highDate::             |
      | [0].subProgramTypeCode | <subProgramTypeCode>   |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 16                   |
      | enrollmentRecordId           | 16                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | enrollmentEndDate            | highDate             |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | planCode                     | <planCode>           |
      | planId                       | 26                   |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
    And User provide other enrollment segments details
      | consumerId        | <name>.consumerId      |
      | startDate         | 1stDayofNextMonth      |
      | endDate           | highDate               |
      | genericFieldText1 | c.data[0].enrollmentId |
      | segmentTypeCode   | OTHER_TXN              |
    And User provide other enrollment segments details
      | genericFieldText1 | 010          |
      | segmentTypeCode   | ERROR_REASON |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","New Enrollment Reject"
    Then I verify benefit status information with data
    # CP-23748 2.0 Decide Program-Population
      | programPopulation | <programPopulation> |
    # CP-23748 3.0 Decide Benefit Status
      | benefitStatus     | Eligible            |
    # CP-23748 4.0 Re-determine Consumer Actions and Change Window
    Then I Verify Consumer Actions as following data
      | [0].action               | Required |
      | [0].consumerAction       | Enroll   |
      | [0].planSelectionAllowed | true     |
    # CP-23748 1.0 Update the consumer’s enrollment selection
    And I verify enrollment by consumer id "<name>.consumerId" with data
       # will updtae
      | txnStatus            | REJECTED           |
      | status               | REJECTED           |
      | rejectionReason      | null               |
      | updatedOn            | current            |
      | updatedBy            | 597                |
      # remains same
      | medicalPlanBeginDate | 1stDayofNextMonth  |
      | medicalPlanEndDate   | highDate           |
      | enrollmentType       | MEDICAL            |
      | channel              | SYSTEM_INTEGRATION |
      | createdBy            | 2893               |
      | createdOn            | current            |
      | planCode             | <planCode>         |
      | planEndDateReason    | null               |
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
#      | correlationId | <name>a.traceid         |
      | consumerId    | <name>.consumerId       |
      | consumerFound | true                    |
      | recordId      | 16                      |
      | DPBI          |[blank]|
      # if recordId = 16 -> ENROLLMENT_UPDATE scenario recognized -> "New Enrollment Reject"
    Then I verify latest task information with name "" for consumer id "<name>.consumerId" with data
      | staffAssignedTo | null                                                   |
      | createdBy       | 597                                                    |
      | createdOn       | current                                                |
      | defaultDueDate  | in2BusinessDays                                        |
      | dueInDays       | 2                                                      |
      | defaultPriority | 1                                                      |
      | taskStatus      | Created                                                |
      | statusDate      | current                                                |
    # taskInfo has 3 info areas: reason contains/externalCaseId/what is Internal Scenario Code
    # empty fiels or null ==  taskInfo = null
    # not null == validates that taskInfo value is not null
      | taskInfo        | Reject Reason Medicaid ID/<name>.externalConsumerId/16 |
    # CP-24258 optional
      | channel         | SYSTEM_INTEGRATION                                     |
      | caseId          | <name>.caseId                                          |
      | externalCaseId  | <name>.caseIdentificationNumber                        |
      | disposition     | null                                                   |
      # planId is dinamic -> not possible to automate need JDBC
#      | planId          | <planId>                                               |
      | planName        | <planName>                                             |
      | planStartDate   | 1stDayofNextMonth                                      |
      | endDate         | highDate                                               |
      | program         | <subProgramTypeCode>                                   |
      | linkToCase      | <name>.caseId                                          |
      | linkToConsumer  | <name>.consumerId                                      |
      | applicationType | New Enrollment                                         |
#      | reason          | null                                                   |
      # CP-24258 AC 3.0 Include the following Data Elements and Values in the Task Creation Request - Error Response Type Jira
      | reason          | 010 - Aid category / dates not effective/valid         |
    And Wait for 10 seconds
    Then I will verify business events are generated with data
      #  CP-23747 1.0 Business Event Required Fields
      | eventName           | NEW_ENROLLMENT_REJECT_RESPONSE  |
#      | correlationId       | <name>a.traceid                 |
      | correlationId       | null                 |
      | channel             | SYSTEM_INTEGRATION              |
      | createdBy           | 597                             |
      | processDate         | current                         |
      | externalCaseId      | <name>.caseIdentificationNumber |
      | externalConsumerId  | <name>.externalConsumerId       |
      | consumerId          | <name>.consumerId               |
      | consumerName        | <name>                          |
      #  CP-23747 2.0 Business Event Optional Fields
      | taskId              | c.tasks[0].taskVO.taskId        |
      | enrollmentStartDate | 1stDayofNextMonth               |
      | enrollmentEndDate   | highDate                        |
      | planSelectionReason | 02                              |
      | planChangeReason    | null                            |
      | rejectionReason     | null                            |
      | planCode            | <planCode>                      |
      | enrollmentType      | MEDICAL                         |
      | selectionStatus     | REJECTED                        |
      | anniversaryDate     | anniversaryDate                 |
    Examples:
      | name    | programCode | subProgramTypeCode | planCode  | programPopulation | planName   | planId |
      | 23748-2 | A           | HoosierCareConnect | 499254630 | HCC-Mandatory     | ANTHEM HCC | 83     |


  @API-CP-24258 @API-CP-24258-04 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: IN-EB Create Rejected Selection Task - New Enrollment (HHW task negative Record Id 16)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | eligibilityRecordId          | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofLastMonth    |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityStatusCode        | M                    |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Then I send API call with name "OU" for update Enrollment information
      | [0].consumerId         | <name>.consumerId    |
      | [0].planId             | 26                   |
      | [0].planCode           | <planCode>           |
      | [0].startDate          | fdnxtmth::           |
      | [0].endDate            | highDate::           |
      | [0].subProgramTypeCode | <subProgramTypeCode> |
      | [0].serviceRegionCode  | Statewide            |
      | [0].selectionReason    | 02                   |
      | [0].channel            | SYSTEM_INTEGRATION   |
    And Wait for 5 seconds
    And I send API call with name "OU" for process Outbound Update
      | [0].planId             | delete::               |
      | [0].planCode           | <planCode>             |
      | [0].consumerId         | <name>.consumerId      |
      | [0].enrollmentId       | c.data[0].enrollmentId |
      | [0].startDate          | fdnxtmth::             |
      | [0].endDate            | highDate::             |
      | [0].subProgramTypeCode | <subProgramTypeCode>   |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 16                   |
      | enrollmentRecordId           | 16                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | enrollmentEndDate            | highDate             |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | planCode                     | <planCode>           |
      | planId                       | 26                   |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
    And User provide other enrollment segments details
      | consumerId        | <name>.consumerId      |
      | startDate         | 1stDayofNextMonth      |
      | endDate           | highDate               |
      | genericFieldText1 | c.data[0].enrollmentId |
      | segmentTypeCode   | OTHER_TXN              |
    And User provide other enrollment segments details
      | genericFieldText1 | 300          |
      | segmentTypeCode   | ERROR_REASON |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","New Enrollment Reject"
    Then I verify latest task information with name "" for consumer id "<name>.consumerId" with data
      | empty |[blank]|
    Examples:
      | name    | programCode | subProgramTypeCode | planCode  |
      | 24258-1 | R           | HoosierHealthwise  | 400752220 |


  @API-CP-24258 @API-CP-24258-04 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: IN-EB Create Rejected Selection Task - New Enrollment (HCC task negative Record Id 16)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | eligibilityRecordId          | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofLastMonth    |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityStatusCode        | M                    |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Then I send API call with name "OU" for update Enrollment information
      | [0].consumerId         | <name>.consumerId    |
      | [0].planId             | 26                   |
      | [0].planCode           | <planCode>           |
      | [0].startDate          | fdnxtmth::           |
      | [0].endDate            | highDate::           |
      | [0].subProgramTypeCode | <subProgramTypeCode> |
      | [0].serviceRegionCode  | Statewide            |
      | [0].selectionReason    | 02                   |
      | [0].channel            | SYSTEM_INTEGRATION   |
    And Wait for 5 seconds
    And I send API call with name "OU" for process Outbound Update
      | [0].planId             | delete::               |
      | [0].planCode           | <planCode>             |
      | [0].consumerId         | <name>.consumerId      |
      | [0].enrollmentId       | c.data[0].enrollmentId |
      | [0].startDate          | fdnxtmth::             |
      | [0].endDate            | highDate::             |
      | [0].subProgramTypeCode | <subProgramTypeCode>   |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 16                   |
      | enrollmentRecordId           | 16                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | enrollmentEndDate            | highDate             |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | planCode                     | <planCode>           |
      | planId                       | 26                   |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
    And User provide other enrollment segments details
      | consumerId        | <name>.consumerId      |
      | startDate         | 1stDayofNextMonth      |
      | endDate           | highDate               |
      | genericFieldText1 | c.data[0].enrollmentId |
      | segmentTypeCode   | OTHER_TXN              |
    And User provide other enrollment segments details
      | genericFieldText1 | 300          |
      | segmentTypeCode   | ERROR_REASON |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","New Enrollment Reject"
    Then I verify latest task information with name "" for consumer id "<name>.consumerId" with data
      | empty |[blank]|
    Examples:
      | name    | programCode | subProgramTypeCode | planCode  |
      | 24258-2 | A           | HoosierCareConnect | 499254630 |

  @API-CP-24258 @API-CP-28092 @API-CP-28092-02 @API-CP-23748 @API-CP-23749 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: IN-EB Updated - IN-EB Decide New Enrollment Responses (HHW/HCC negative Record Id 16)
  CP-23748 IN-EB Create or Update Data based on New Enrollment Reject Response
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | eligibilityRecordId          | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | highDate          |
      | txnStatus                    | Accepted          |
      | programCode                  | R                 |
      | subProgramTypeCode           | HoosierHealthwise |
      | eligibilityStatusCode        | M                 |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Then I send API call with name "OU" for update Enrollment information
      | [0].consumerId         | <name>.consumerId  |
      | [0].planId             | 26                 |
      | [0].planCode           | 400752220          |
      | [0].startDate          | fdnxtmth::         |
      | [0].endDate            | highDate::         |
      | [0].subProgramTypeCode | HoosierHealthwise  |
      | [0].serviceRegionCode  | Statewide          |
      | [0].selectionReason    | 02                 |
      | [0].channel            | SYSTEM_INTEGRATION |
    And Wait for 5 seconds
    And I send API call with name "OU" for process Outbound Update
      | [0].planId             | delete::               |
      | [0].planCode           | 400752220              |
      | [0].consumerId         | <name>.consumerId      |
      | [0].enrollmentId       | c.data[0].enrollmentId |
      | [0].startDate          | fdnxtmth::             |
      | [0].endDate            | highDate::             |
      | [0].subProgramTypeCode | <subProgramTypeCode>   |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | isEnrollmentProviderRequired | no                    |
      | recordId                     | 16                    |
      | enrollmentRecordId           | 16                    |
      | enrollmentStartDate          | <enrollmentStartDate> |
      | enrollmentEndDate            | highDate              |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | planCode                     | <planCode>            |
      | planId                       | 26                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
    And User provide other enrollment segments details
      | consumerId        | <name>.consumerId |
      | startDate         | 1stDayofNextMonth |
      | endDate           | highDate          |
      | genericFieldText1 | <txnID>           |
      | segmentTypeCode   | OTHER_TXN         |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Not Found","Not Found"
    Examples:
      | name    | scenario             | planCode  | enrollmentStartDate | txnID                  |
      | 23748-3 | wrong plan code      | 499254630 | 1stDayofNextMonth   | c.data[0].enrollmentId |
      | 23748-4 | wrong start date     | 400752220 | 1stDayofLastMonth   | c.data[0].enrollmentId |
      | 23748-5 | wrong transaction id | 400752220 | 1stDayofNextMonth   | 9999                   |


  @API-CP-29356 @API-CP-24256 @API-CP-26294 @API-CP-26294-02 @API-CP-23754 @API-CP-23754-01 @API-CP-23753 @API-CP-23758 @API-CP-33456 @API-CP-33456-03 @API-EE-IN @IN-EB-API-Regression @sobir @kursat
  Scenario: IN-EB Decide Plan Change Reject Response - HIP (positive)
  CP-24256 IN-EB Create Rejected Selection Task - Plan Change (task positive scenario)
  CP-23753 IN-EB Create or Update Data based on Plan Change Reject Response
  CP-23758 IN-EB Create Business Event - Plan Change Reject Response
  CP-26294 IN-EB Updated - IN-EB Decide Plan Change Responses HIP (Record Id 18)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23754-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23754-1.consumerId   |
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
      | fileSource                   | HRECIP               |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth   |
      | endDate             | highDate               |
      | segmentDetailValue1 | 1                      |
      | segmentDetailValue2 | 6                      |
      | segmentDetailValue3 | 1stDayofPresentMonth:: |
      | segmentDetailValue4 | highDate::             |
      | segmentTypeCode     | OTHER                  |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23754-1.consumerId      |
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
    And Wait for 5 seconds
    And I update latest consumer actions by consumerId "23754-1.consumerId" with data
      | [0].action               | Available                   |
      | [0].consumerAction       | Plan Change Open Enrollment |
      | [0].planSelectionAllowed | true                        |
      | [0].changeAllowedFrom    | 1stDayofPresentMonth        |
      | [0].changeAllowedUntil   | lastDayofPresentMonth       |
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 23754-1.consumerId   |
      | [0].planId             | 101                  |
      | [0].planCode           | 355787430            |
      | [0].startDate          | firstDayOfNextYear:: |
      | [0].endDate            | highDate::           |
      | [0].planEndDateReason  | PC                   |
      | [0].programTypeCode    | MEDICAID             |
      | [0].subProgramTypeCode | HealthyIndianaPlan   |
      | [0].serviceRegionCode  | Statewide            |
      | [0].channel            | SYSTEM_INTEGRATION   |
      | [0].rejectionReason    | test2                |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "23754-1.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "23754-1b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 23754-1.consumerId                |
      | [0].planId             | delete::                          |
      | [0].planCode           | 455701400                         |
      | [0].enrollmentId       | 23754-1b.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED               |
      | [0].txnStatus          | DISENROLL_SUBMITTED               |
      | [0].startDate          | fdofmnth::                        |
      | [0].endDate            | lastDayOfThePresentYear::         |
      | [0].enrollmentType     | delete::                          |
      | [0].subProgramTypeCode | HealthyIndianaPlan                |
      | [0].serviceRegionCode  | Statewide                         |
      | [1].planId             | delete::                          |
      | [1].planCode           | 355787430                         |
      | [1].consumerId         | 23754-1.consumerId                |
      | [1].enrollmentId       | 23754-1b.selectedEnrollmentId     |
      | [1].startDate          | firstDayOfNextYear::              |
      | [1].endDate            | highDate::                        |
      | [1].subProgramTypeCode | HealthyIndianaPlan                |
      | [1].serviceRegionCode  | Statewide                         |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23754-1.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | isEnrollmentProviderRequired | no                 |
      | recordId                     | 18                 |
      | enrollmentRecordId           | 18                 |
      | enrollmentStartDate          | firstDayOfNextYear |
      | enrollmentEndDate            | highDate           |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted           |
      | programCode                  | H                  |
      | subProgramTypeCode           | HealthyIndianaPlan |
      | planCode                     | 355787430          |
      | planId                       | 101                |
      | serviceRegionCode            | Statewide          |
      | anniversaryDate              | anniversaryDate    |
      | channel                      | SYSTEM_INTEGRATION |
      | rejectionReason              | test3              |
    And User provide other enrollment segments details
      | startDate         | firstDayOfNextYear            |
      | endDate           | highDate                      |
      | genericFieldText1 | 23754-1b.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                     |
    And User provide other enrollment segments details
      | genericFieldText1 | 010          |
      | segmentTypeCode   | ERROR_REASON |
    And User provide other enrollment segments details
      | genericFieldText1 | 831          |
      | segmentTypeCode   | ERROR_REASON |
    And User provide one more enrollment details
      | planCode            | 455701400               |
      | planId              | 104                     |
      #Updated start date for CP-33456
      | enrollmentStartDate | 1stDayof2MonthsBefore   |
      | enrollmentEndDate   | lastDayOfThePresentYear |
      | rejectionReason     | test4                   |
    And User provide other enrollment segments details
      | startDate         | firstDayOfNextYear |
      | endDate           | highDate           |
      | genericFieldText1 | null               |
      | segmentTypeCode   | OTHER_TXN          |
    Then I send API call with name "23754-1a" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "23754-1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "NO MATCH","Plan Change Reject"
    Then I verify benefit status information with data
    # CP-23753 3.0 Decide Program-Population
      | programPopulation | HIP-Mandatory |
    # CP-23753 4.0 Decide Benefit Status
      | benefitStatus     | Enrolled      |
    # CP-23753 5.0 Re-determine Consumer Actions and Change Window
    Then I Verify Consumer Actions as following data
      | [0].action               | Available                   |
      | [0].consumerAction       | Plan Change Open Enrollment |
      | [0].planSelectionAllowed | true                        |
    # CP-23753 1.0 Update the consumer’s requested plan change selection
    And I verify latest enrollment by consumer id "23754-1.consumerId" with data
      # will update
      | txnStatus            | REJECTED           |
      | updatedOn            | current            |
      | updatedBy            | 597                |
      # remains same
      | channel              | SYSTEM_INTEGRATION |
      | createdOn            | current            |
      | createdBy            | null               |
      | planCode             | 355787430          |
      | medicalPlanBeginDate | firstDayOfNextYear |
      | medicalPlanEndDate   | highDate           |
      | selectionReason      | HT                 |
      | planEndDateReason    | null               |
      | enrollmentType       | MEDICAL            |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
#      | correlationId | 23754-1a.traceid        |
      | consumerId    | 23754-1.consumerId      |
      | consumerFound | true                    |
      | recordId      | 18                      |
      | DPBI          |[blank]|
  # CP-23753 2.0 Update the consumer’s prior plan change selection
    And I verify enrollment by consumer id "23754-1.consumerId" with data
      # will updtae
      | txnStatus            | ACCEPTED                |
      | medicalPlanEndDate   | lastDayOfThePresentYear |
      | updatedOn            | current                 |
      | updatedBy            | 597                     |
      # remains same
      | medicalPlanBeginDate | 1stDayofPresentMonth    |
      | enrollmentType       | MEDICAL                 |
      | channel              | SYSTEM_INTEGRATION      |
      | anniversaryDate      | anniversaryDate         |
      | planEndDateReason    | PC                      |
      | planCode             | 455701400               |
    # CP-24256 2.0 Include the following Data Elements and Values in the Task Creation Request
    Then I verify latest task information with name "" for consumer id "23754-1.consumerId" with data
      | staffAssignedTo | null                                                                                                                  |
      | createdBy       | 597                                                                                                                   |
      | createdOn       | current                                                                                                               |
      | defaultDueDate  | in2BusinessDays                                                                                                       |
      | dueInDays       | 2                                                                                                                     |
      | defaultPriority | 1                                                                                                                     |
      | taskStatus      | Created                                                                                                               |
      | statusDate      | current                                                                                                               |
    # taskInfo has 3 info areas: reason contains/externalCaseId/what is Internal Scenario Code
    # empty fiels or null ==  taskInfo = null
    # not null == validates that taskInfo value is not null
      | taskInfo        | Reject Reason Medicaid ID/23754-1.externalConsumerId/18                                                               |
    # CP-24256 optional
      | channel         | SYSTEM_INTEGRATION                                                                                                    |
      | caseId          | SYSTEM_INTEGRATION                                                                                                    |
      | externalCaseId  | 23754-1.caseIdentificationNumber                                                                                      |
      | disposition     | null                                                                                                                  |
      # planId is dinamic -> not possible to automate need JDBC
#      | planId          | <planId>                                               |
      | planName        | MANAGED HEALTH SERVICES HIP                                                                                           |
      | planStartDate   | firstDayOfNextYear                                                                                                    |
      | endDate         | highDate                                                                                                              |
      | program         | HealthyIndianaPlan                                                                                                    |
      | linkToCase      | 23754-1.caseId                                                                                                        |
      | linkToConsumer  | 23754-1.consumerId                                                                                                    |
      | applicationType | Plan Change                                                                                                           |
#      | reason          | null                                                    |
      | reason          | 010 - Aid category / dates not effective/valid,831 - Recipient is not eligible for Disease Management/Case Management |
    # CP-23758 Create Business Event - Plan Change Reject Response
    Then I will verify business events are generated with data
      # CP-23758 1.0 Business Event Required Fields
      | eventName                           | PLAN_CHANGE_REJECT_RESPONSE      |
#      | correlationId                       | 23754-1a.traceid                 |
      | channel                             | SYSTEM_INTEGRATION               |
      | createdBy                           | 597                              |
      | processDate                         | current                          |
      | externalCaseId                      | 23754-1.caseIdentificationNumber |
      | externalConsumerId                  | 23754-1.externalConsumerId       |
      | consumerId                          | 23754-1.consumerId               |
      | consumerName                        | 23754-1                          |
      # CP-23758 2.0 Business Event Optional Fields
      | taskId                              | c.tasks[0].taskVO.taskId         |
      # CP-23758 3.0 Business Event Plan Change Enrollment Segments
      | PreviousEnrollmentStartDate         | 1stDayofPresentMonth             |
      | PreviousEnrollmentEndDate           | lastDayOfThePresentYear          |
      | PreviousEnrollmentSelectionReason   | null                             |
      | PreviousEnrollmentPlanChangeReason  | PC                               |
      | PreviousEnrollmentRejectionReason   | test1                            |
      | PreviousEnrollmentPlanCode          | 455701400                        |
      | PreviousEnrollmentType              | MEDICAL                          |
      | PreviousEnrollmentSelectionStatus   | ACCEPTED                         |
      | RequestedEnrollmentStartDate        | firstDayOfNextYear               |
      | RequestedEnrollmentEndDate          | highDate                         |
      | RequestedEnrollmentSelectionReason  | HT                               |
      | RequestedEnrollmentPlanChangeReason | null                             |
      | RequestedEnrollmentRejectionReason  | test3                            |
      | RequestedEnrollmentPlanCode         | 355787430                        |
      | RequestedEnrollmentType             | MEDICAL                          |
      | RequestedEnrollmentSelectionStatus  | REJECTED                         |

  @API-CP-24256 @API-CP-24256-02 @API-EE-IN @IN-EB-API-Regression @sobir @kursat
  Scenario: IN-EB Create Rejected Selection Task - Plan Change (task negative scenario)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 24256-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 24256-1.consumerId   |
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
      | consumerId        | 24256-1.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 24256-1.consumerId      |
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
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 24256-1.consumerId   |
      | [0].planId             | 101                  |
      | [0].planCode           | 355787430            |
      | [0].startDate          | firstDayOfNextYear:: |
      | [0].endDate            | highDate::           |
      | [0].planEndDateReason  | PC                   |
      | [0].programTypeCode    | MEDICAID             |
      | [0].subProgramTypeCode | HealthyIndianaPlan   |
      | [0].serviceRegionCode  | Statewide            |
      | [0].channel            | SYSTEM_INTEGRATION   |
      | [0].rejectionReason    | test2                |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "24256-1.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "24256-1b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 24256-1.consumerId                |
      | [0].planId             | delete::                          |
      | [0].planCode           | 455701400                         |
      | [0].enrollmentId       | 24256-1b.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED               |
      | [0].txnStatus          | DISENROLL_SUBMITTED               |
      | [0].startDate          | fdofmnth::                        |
      | [0].endDate            | lastDayOfThePresentYear::         |
      | [0].enrollmentType     | delete::                          |
      | [0].subProgramTypeCode | HealthyIndianaPlan                |
      | [0].serviceRegionCode  | Statewide                         |
      | [1].planId             | delete::                          |
      | [1].planCode           | 355787430                         |
      | [1].consumerId         | 24256-1.consumerId                |
      | [1].enrollmentId       | 24256-1b.selectedEnrollmentId     |
      | [1].startDate          | firstDayOfNextYear::              |
      | [1].endDate            | highDate::                        |
      | [1].subProgramTypeCode | HealthyIndianaPlan                |
      | [1].serviceRegionCode  | Statewide                         |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 24256-1.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | isEnrollmentProviderRequired | no                 |
      | recordId                     | 18                 |
      | enrollmentRecordId           | 18                 |
      | enrollmentStartDate          | firstDayOfNextYear |
      | enrollmentEndDate            | highDate           |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted           |
      | programCode                  | H                  |
      | subProgramTypeCode           | HealthyIndianaPlan |
      | planCode                     | 355787430          |
      | planId                       | 101                |
      | serviceRegionCode            | Statewide          |
      | anniversaryDate              | anniversaryDate    |
      | channel                      | SYSTEM_INTEGRATION |
      | rejectionReason              | test3              |
    And User provide other enrollment segments details
      | startDate         | firstDayOfNextYear            |
      | endDate           | highDate                      |
      | genericFieldText1 | 24256-1b.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                     |
    And User provide other enrollment segments details
      | genericFieldText1 | 300          |
      | segmentTypeCode   | ERROR_REASON |
    And User provide other enrollment segments details
      | genericFieldText1 | 831          |
      | segmentTypeCode   | ERROR_REASON |
    And User provide one more enrollment details
      | planCode            | 455701400               |
      | planId              | 33                      |
      | enrollmentStartDate | 1stDayofPresentMonth    |
      | enrollmentEndDate   | lastDayOfThePresentYear |
      | rejectionReason     | test4                   |
    And User provide other enrollment segments details
      | startDate         | firstDayOfNextYear |
      | endDate           | highDate           |
      | genericFieldText1 | null               |
      | segmentTypeCode   | OTHER_TXN          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "24256-1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "NO MATCH","Plan Change Reject"
    # CP-24256 2.0 Include the following Data Elements and Values in the Task Creation Request
    Then I verify latest task information with name "" for consumer id "24256-1.consumerId" with data
      | empty |[blank]|




