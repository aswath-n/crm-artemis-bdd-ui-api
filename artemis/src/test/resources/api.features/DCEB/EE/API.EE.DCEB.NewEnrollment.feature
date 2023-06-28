Feature: New Enrollment Accept Response for DCEB


  @API-CP-39445 @API-CP-39446 @API-CP-39447 @API-CP-39616 @API-DC-EE @DC-EB-API-Regression @kursat
  Scenario Outline: CP-39445 DC EB Decide Scenario 15 for New Enrollment Accept Response for Immigrant Children & Alliance
  CP-39446 DC EB Update Enrollment - Immigrant Children & Alliance
  CP-39447 DC EB: Decide Benefit Status = ENROLLED - Immigrant Children & Alliance
  CP-39616 DC EB: Create Business Event New Enrollment Accept Response - Immigrant Children & Alliance
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
      | newCaseCreation  | yes    |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId      |
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                     |
      | recordId                     | 25                     |
      | eligibilityRecordId          | 25                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | <eligibilityStartDate> |
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | highDate-DC            |
      | txnStatus                    | Accepted               |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | coverageCode                 | <coverageCode>         |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Given I initiated auto assignment plan batch process Api for "<subProgramTypeCode>"
    When I run auto assignment plan batch process Api
    And Wait for 100 seconds
    And I initiated get enrollment by consumer id "<name>.consumerId"
    And I save auto assigned plan code and plan name for "<name>.consumerId" with get enrollment api
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "<name>a" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION            |
      | [0].startDate    | <submitStartDate>             |
      | [0].planId       | delete::                      |
      | [0].planCode     | planCodeFromEnrollmentDetails |
      | [0].consumerId   | <name>.consumerId             |
      | [0].enrollmentId | c.data[0].enrollmentId        |
      | [0].status       | SUBMITTED_TO_STATE            |
      | [0].txnStatus    | SUBMITTED_TO_STATE            |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId             |
      | isEligibilityRequired        | no                            |
      | isEnrollemntRequired         | yes                           |
      | isEnrollmentProviderRequired | no                            |
      | recordId                     | 15                            |
      | enrollmentRecordId           | 15                            |
      | enrollmentStartDate          | <changeAllowedFrom1>          |
      | enrollmentEndDate            | highDate-DC                   |
      | eligibilityStartDate         |[blank]|
      | programCode                  | MEDICAID                      |
      | planCode                     | planCodeFromEnrollmentDetails |
      | planId                       | 145                           |
      | subProgramTypeCode           | <subProgramTypeCode>          |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    # CP-39445 1.0 Decide Scenario is MMIS Sends New Enrollment Accept Response (Auto Assignment)
    Then I verify scenario output in the benefit status records are displayed as "Default Eligibility","<enrollmentScenario>"
    # CP-39447 AC1.0 Decide Benefit Status is ENROLLED
    Then I verify benefit status information with data
      | programPopulation | <population> |
      | benefitStatus     | Enrolled     |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available               |
      | [0].consumerAction       | Plan Change Pre-lockin  |
      | [0].planSelectionAllowed | true                    |
      | [0].changeAllowedFrom    | <changeAllowedFrom1>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil1>   |
      | [1].action               | Available               |
      | [1].consumerAction       | Plan Change Anniversary |
      | [1].planSelectionAllowed | true                    |
      | [1].changeAllowedFrom    | <changeAllowedFrom2>    |
      | [1].changeAllowedUntil   | <changeAllowedUntil2>   |
    And Wait for 3 seconds
    # CP-39446 1.0 Update the consumer’s enrollment selection
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | txnStatus            | ACCEPTED                      |
      | updatedOn            | current                       |
      | updatedBy            | SYSTEM_INTEGRATION            |
      | consumerId           | <name>.consumerId             |
      | planCode             | planCodeFromEnrollmentDetails |
      | medicalPlanBeginDate | <changeAllowedFrom1>          |
      | medicalPlanEndDate   | highDate-DC                   |
      | selectionReason      | Round Robin                   |
      | enrollmentType       | MEDICAL                       |
    And Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | <name>.consumerId       |
      | correlationId | null                    |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName           | NEW_ENROLLMENT_ACCEPT_RESPONSE_AUTO_ASSIGNMENT |
    # CP-39616 1.0 Business Event Required Fields
      | consumerId          | <name>.consumerId                              |
      | consumerName        | <name>                                         |
      | consumerFound       | true                                           |
      | correlationId       | null                                           |
      | channel             | AUTO_ASSIGNMENT                                |
      | createdBy           | 597                                            |
      | processDate         | current                                        |
    # CP-39616 2.0 Business Event Optional Fields
      | enrollmentStartDate | <changeAllowedFrom1>                           |
      | enrollmentEndDate   | highDate-DC                                    |
      | planSelectionReason | Round Robin                                    |
      | planChangeReason    | null                                           |
      | rejectionReason     | null                                           |
      | planCode            | planCodeFromEnrollmentDetails                  |
      | enrollmentType      | MEDICAL                                        |
      | selectionStatus     | ACCEPTED                                       |
      | pcpNpi              | null                                           |
      | pcpName             | null                                           |
      | pdpNpi              | null                                           |
      | pdpName             | null                                           |
    Examples:
      | name     | coverageCode | population         | subProgramTypeCode | enrollmentScenario    | eligibilityStartDate  | submitStartDate                    | changeAllowedFrom1               | changeAllowedUntil1                         | changeAllowedFrom2                        | changeAllowedUntil2                                  |
      | 39445-01 | 420          | Immigrant Children | ImmigrantChildren  | New Enrollment Accept | 1stDayofLastMonth     | cutoffstartdateimmigrantchildren:: | cutoffStartDateImmigrantChildren | 89DaysAfterCutoffStartDateImmigrantChildren | 1YearFromCutoffStartDateImmigrantChildren | 89DaysAfter1YearFromCutoffStartDateImmigrantChildren |
#      | 39445-02 | 420F         | Immigrant Children | ImmigrantChildren  | New Enrollment Accept | 1stDayofLastMonth     | cutoffstartdateimmigrantchildren:: | cutoffStartDateImmigrantChildren | 90DaysAfterCutoffStartDateImmigrantChildren | 1YearFromCutoffStartDateImmigrantChildren | 90DaysAfter1YearFromCutoffStartDateImmigrantChildren |
      | 39445-03 | 460          | Alliance-Child     | Alliance           | New Enrollment Accept | 1stDayofLastMonth     | 1stdayoflastmonth::                | 1stDayofLastMonth                | 89DayFromFirstDayOfLastMonth                | 1YearFrom1stDayofLastMonth                | 89DaysAfter1YearFrom1stDayofLastMonth                |
      | 39445-04 | 470          | Alliance-Adult     | Alliance           | New Enrollment Accept | 1stDayof6MonthsBefore | 1stdayof2monthsbefore::            | 1stDayof2MonthsBefore            | 89DaysAfter1stDayof2MonthsBefore            | 1YearFrom1stDayof2MonthsBefore            | 89DaysAfter1YearFrom1stDayof2MonthsBefore            |

  @API-CP-42977 @API-CP-42977-01 @API-CP-39574 @API-CP-39574-01 @API-DC-EE @DC-EB-API-Regression @shruti
  Scenario Outline: DC EB  Decide New Enrollment Reject Response- Rejected Selection Task i generated and tasktemplate fields
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId |
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
      | coverageCode                 | <coverageCode>    |
    And Wait for 2 seconds
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "<name>a" for update Enrollment information
      | [0].consumerId         | <name>.consumerId |
      | [0].planId             | 145               |
      | [0].planCode           | 081080400         |
#      | [0].startDate          | fdnxtmth::          |
      | [0].startDate          | fdofmnth::        |
      | [0].endDate            | highdatedc::      |
      | [0].programTypeCode    | MEDICAID          |
      | [0].subProgramTypeCode | DCHF              |
      | [0].serviceRegionCode  | Northeast         |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "<name>a" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | fdofmnth::             |
#      | [0].startDate    | fdnxtmth::             |
      | [0].planId       | delete::               |
      | [0].planCode     | 081080400              |
      | [0].consumerId   | <name>.consumerId      |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "<name>a" for trigger Enrollment Start
      | consumerId                         | <name>.consumerId |
      | enrollments.[0].consumerId         | <name>.consumerId |
      | enrollments.[0].subProgramTypeCode | DCHF              |
      | enrollments.[0].planId             | 145               |
      | enrollments.[0].planCode           | 081080400         |
      | enrollments.[0].rejectionReason    | <rejectionReason> |
      | recordId                           | int::16           |
      | enrollments.[0].startDate          | fdofmnth::        |
    And Wait for 5 seconds
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | txnStatus       | REJECTED           |
      | updatedOn       | current            |
      | updatedBy       | SYSTEM_INTEGRATION |
      | consumerId      | <name>.consumerId  |
      | planCode        | 081080400          |
      | enrollmentType  | MEDICAL            |
      | status          | REJECTED           |
      | rejectionReason | <rejectionReason>  |
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
      | planId               | 081080400                                                  |
      | planName             | AMERIHEALTH CARITAS DC                                     |
      | planStartDate        | 1stDayofPresentMonth                                       |
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
      | name     | coverageCode | subProgramTypeCode | rejectionReason | reason                                  |
      | 42977-01 | 130          | DCHF               | 310             | 310 - Invalid MCO enrollment begin date |
      | 42977-02 | 750Y         | DCHF               | 800             | 800-Unknown                             |


  @API-CP-42977 @API-CP-42977-01 @API-DC-EE @DC-EB-API-Regression @priyal
  Scenario: DC EB  Decide New Enrollment Reject Response
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 42977-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 42977-01.consumerId |
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
    And Wait for 2 seconds
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "42977-01a" for update Enrollment information
      | [0].consumerId         | 42977-01.consumerId |
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
    And I send API call with name "42977-01a" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | fdofmnth::             |
#      | [0].startDate    | fdnxtmth::             |
      | [0].planId       | delete::               |
      | [0].planCode     | 081080400              |
      | [0].consumerId   | 42977-01.consumerId    |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "42977-01a" for trigger Enrollment Start
      | consumerId                         | 42977-01.consumerId      |
      | enrollments.[0].consumerId         | 42977-01.consumerId      |
      | enrollments.[0].subProgramTypeCode | DCHF                     |
      | enrollments.[0].planId             | 145                      |
      | enrollments.[0].planCode           | 081080400                |
      | enrollments.[0].rejectionReason    | Consumer is not eligible |
      | recordId                           | int::16                  |
      | enrollments.[0].startDate          | fdofmnth::               |
    And Wait for 5 seconds
    And I verify enrollment by consumer id "42977-01.consumerId" with data
      | txnStatus       | REJECTED                 |
      | updatedOn       | current                  |
      | updatedBy       | SYSTEM_INTEGRATION       |
      | consumerId      | 42977-01.consumerId      |
      | planCode        | 081080400                |
      | enrollmentType  | MEDICAL                  |
      | status          | REJECTED                 |
      | rejectionReason | Consumer is not eligible |


  @API-CP-42474 @API-CP-42474-01 @API-DC-EE @DC-EB-API-Regression @shruti
  Scenario: DC EB: Enrollment Transaction Status and Txn State Date API
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 42474-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 42474-01.consumerId |
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
      | requestedBy                  | 9056                |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "42474-01a" for update Enrollment information
      | [0].consumerId         | 42474-01.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 081080400           |
      | [0].startDate          | fdofmnth::          |
      | [0].endDate            | highdatedc::        |
      | [0].programTypeCode    | MEDICAID            |
      | [0].subProgramTypeCode | DCHF                |
      | [0].serviceRegionCode  | Northeast           |
    And Wait for 3 seconds
    And I verify enrollment by consumer id "42474-01.consumerId" with data
      | saveDetailsWithName | 42474-01b      |
      | txnStatus           | SELECTION_MADE |
      | updatedOn           | current        |
    When I run get enrollment transaction by status "42474-01b.status" and date "42474-01b.txnStatusDate"
    Then I verify enrollment transaction status and date
      | txnStatus     | 42474-01b.status        |
      | txnStatusDate | 42474-01b.txnStatusDate |


  @API-CP-42474 @API-CP-42474-02 @API-DC-EE @DC-EB-API-Regression @shruti
  Scenario: DC EB: Enrollment Transaction Status and Txn State Date API- with providers
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 42474-02 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 42474-02.consumerId |
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
    Then I send API call with name "42474-02a" for update Enrollment information
      | [0].consumerId         | 42474-02.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 081080400           |
      | [0].startDate          | fdofmnth::          |
      | [0].endDate            | highdatedc::        |
      | [0].programTypeCode    | MEDICAID            |
      | [0].subProgramTypeCode | DCHF                |
      | [0].serviceRegionCode  | Northeast           |
      | enrollmentProviders    | random              |
#      | [0].enrollmentProviders[0].consumerId         | 42474-02.consumerId                             |
#      | [0].enrollmentProviders[0].providerFirstName  | 42474-02-planTransferDetails.providerFirstName  |
#      | [0].enrollmentProviders[0].providerMiddleName | 42474-02-planTransferDetails.providerMiddleName |
#      | [0].enrollmentProviders[0].providerLastName   | 42474-02-planTransferDetails.providerLastName   |
#      | [0].enrollmentProviders[0].providerNpi        | 42474-02-planTransferDetails.providerNpi        |
#      | [0].enrollmentProviders[0].providerType       | 42474-02-planTransferDetails.providerType       |
    And Wait for 3 seconds
    And I verify enrollment by consumer id "42474-02.consumerId" with data
      | saveDetailsWithName | 42474-02b      |
      | txnStatus           | SELECTION_MADE |
      | updatedOn           | current        |
    When I run get enrollment transaction by status "42474-02b.status" and date "42474-02b.txnStatusDate"
    Then I verify enrollment transaction status and date
      | txnStatus     | 42474-02b.status        |
      | txnStatusDate | 42474-02b.txnStatusDate |
      | enrollmentProviders | 42474-02b.enrollmentProviders|

  @API-CP-42474 @API-CP-42474-03 @API-DC-EE @DC-EB-API-Regression @shruti
  Scenario: DC EB: Enrollment Transaction Status and Txn State Date API- Plan Change with providers
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 42474-03 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 42474-03.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
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
      | planCode                     | 081080400           |
      | planId                       | 104                 |
      | serviceRegionCode            | Northeast           |
      | anniversaryDate              | anniversaryDate     |
      | channel                      | SYSTEM_INTEGRATION  |
      | selectionReason              | selectionReason1    |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 42474-03.consumerId |
      | [0].planId             | 26                  |
      | [0].planCode           | 055558200           |
      | [0].startDate          | fdnxtmth::          |
      | [0].endDate            | highdatedc::        |
      | [0].subProgramTypeCode | DCHF                |
      | [0].serviceRegionCode  | Northeast           |
      | [0].selectionReason    | 02                  |
      | [0].anniversaryDate    | anniversaryDate::   |
      | [0].channel            | SYSTEM_INTEGRATION  |
      | enrollmentProviders    | random              |
    And Wait for 3 seconds
    And I verify enrollment by consumer id "42474-03.consumerId" with data
      | saveDetailsWithName | 42474-03b      |
      | txnStatus           | SELECTION_MADE |
      | updatedOn           | current        |
    When I run get enrollment transaction by status "42474-03b.status" and date "42474-03b.txnStatusDate"
    Then I verify enrollment transaction status and date
      | txnStatus     | 42474-03b.status        |
      | txnStatusDate | 42474-03b.txnStatusDate |

