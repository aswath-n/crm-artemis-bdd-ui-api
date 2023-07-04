Feature: IN-EB ENROLLMENT (A or C) Decide Scenario: MMIS Sends Reconciliation

  @API-CP-24692 @API-CP-24692-01 @API-CP-24693-01.01 @API-CP-24693 @API-CP-24694 @API-CP-24694-01 @API-CP-32231 @API-CP-32272 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: INEB- Verify Recon scenario for Enrollment No Match , No Enrollment and high end date -Accepted, Reconciliation Action= Enrollment Created
  CP-32231 IN-EB Assignment Update Decide Scenario - at least one "Add" Transaction or one "Change" transaction
  CP-32231 AC 1.0 Decide Scenario is MMIS Sends Reconciliation for Enrollment
  CP-32272 AC 2.0 No Accepted Enrollment in System, Create New Enrollment Segment
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo     | <name> |
      | caseIdentificationNo | new    |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event>        |
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | <providerRequired>   |
      | providerNpi                  | 1921987974           |
      | providerFirstName            | Steven               |
      | providerLastName             | Gerrard              |
      | providerMiddleName           | M                    |
      | enrollmentStartDate          | <StartDate>          |
      | enrollmentEndDate            | <EndDate>            |
      | eligibilityStartDate         | <StartDate>          |
      | eligibilityEndDate           | <EndDate>            |
      | txnStatus                    | Accepted             |
      | programCode                  | <ProgramCode>        |
      | subProgramTypeCode           | <SubProgramTypeCode> |
      | eligibilityStatusCode        | <StatusCode>         |
      | categoryCode                 | <CategoryCode>       |
      | coverageCode                 | cc01                 |
      | planCode                     | 455701400            |
      | selectionReason              | selection reason     |
      | planEndDateReason            | end reason           |
    And User provide other enrollment segments details
      | recordId          | 21        |
      | genericFieldText3 | MEDICAL   |
      | genericFieldText1 | O         |
      | genericFieldText2 | A         |
      | segmentTypeCode   | OTHER_TXN |
    And User provide one more other enrollment segments details
      | recordId          | 21                |
      | consumerId        | <name>.consumerId |
      | startDate         | <startDate2>      |
      | endDate           | <endDate2>        |
      | genericFieldText1 | I                 |
      | genericFieldDate1 | openEnrollmentDay |
      | genericFieldDate2 |[blank]|
      | segmentTypeCode   | LILO              |
      | genericFieldText2 | 300029354         |
      | genericFieldText5 | BO  ASANTE      M |
      | genericFieldText3 | RCP-Physician     |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    # CP-32231 AC 1.0 Decide Scenario is MMIS Sends Reconciliation for Enrollment
    Then I verify scenario output in the benefit status records are displayed as "NO MATCH","No Enrollment"
    # CP-32231 AC 2.0 No Accepted Enrollment in System, Create New Enrollment Segment
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate | <StartDate>        |
      | medicalPlanEndDate   | <EndDate>          |
      | selectionReason      | selection reason   |
      | planEndDateReason    | end reason         |
      | planCode             | 455701400          |
      | enrollmentType       | MEDICAL            |
      | channel              | SYSTEM_INTEGRATION |
      | txnStatus            | <status>           |
      | status               | <status>           |
      | createdOn            | current            |
      | createdBy            | 597                |
    And Wait for 3 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                       |
      | consumerId    | <name>.consumerId                           |
      | correlationId | <name><event>.enrollments.[0].correlationId |
      | status        | SUCCESS                                     |
      | consumerFound | true                                        |
      | DPBI          |[blank]|
    #API-CP-24694-01
    Then I will verify business events are generated with data
      | eventName            | RECONCILIATION                                                |
      | externalConsumerId   | <name>.externalConsumerId                                     |
      | correlationId        | <name><event>.eligibilities.[0].coreEligibility.correlationId |
      | channel              | SYSTEM_INTEGRATION                                            |
      | createdBy            | 597                                                           |
      | processDate          | current                                                       |
      | externalCaseId       | <name>.caseIdentificationNumber                               |
      | consumerId           | <name>.consumerId                                             |
      | consumerName         | <name>                                                        |
      | reconciliationAction | Enrollment Created                                            |
      | module               | ENROLLMENT                                                    |
      # Business Event Optional Fields
      | enrollmentStartDate  | <StartDate>                                                   |
      | enrollmentEndDate    | <EndDate>                                                     |
      | selectionReason      | selection reason                                              |
      #Need to verify if planEndDateReason =planChangeReason
#      | planEndDateReason    | end reason                      |
      | rejectionReason      | null                                                          |
      | planCode             | 455701400                                                     |
      | selectionStatus      | <status>                                                      |
      | pcpNpi               | <pcpNpi>                                                      |
      | pcpName              | <pcpName>                                                     |

    Examples:
      | name       | StartDate            | EndDate  | ProgramCode | SubProgramTypeCode | CategoryCode | StatusCode | providerRequired | pcpNpi     | pcpName        | status      | event           |
      | 24692-01-1 | 1stDayofPresentMonth | highDate | R           | HoosierHealthwise  | 10           | V          | no               | null       | null           | ACCEPTED    | eligilibility-1 |
      | 24692-01-2 | 1stDayofPresentMonth | highDate | A           | HoosierCareConnect | 10           | M          | no               | null       | null           | ACCEPTED    | eligilibility-2 |
      | 24692-01-3 | 1stDayofPresentMonth | highDate | H           | HealthyIndianaPlan | 4            | V          | no               | null       | null           | ACCEPTED    | eligilibility-3 |
      | 24692-01-4 | 1stDayofPresentMonth | future   | R           | HoosierHealthwise  | 10           | V          | yes              | 1921987974 | Steven Gerrard | DISENROLLED | eligilibility-4 |
      | 24692-01-5 | 1stDayofPresentMonth | future   | A           | HoosierCareConnect | 10           | M          | yes              | 1921987974 | Steven Gerrard | DISENROLLED | eligilibility-5 |
      | 24692-01-6 | 1stDayofPresentMonth | future   | H           | HealthyIndianaPlan | 4            | V          | yes              | 1921987974 | Steven Gerrard | DISENROLLED | eligilibility-6 |


  @API-CP-24692 @API-CP-24692-01.02 @API-CP-24693 @API-CP-24693-01.02 @API-CP-24694 @API-CP-24694-02 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: Verify Enrollment Recon- INEB HHW NO Match ,No Enrollment with provider & non high enddate-disenrolled-Reconciliation Action= Enrollment Create
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event>           |
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | yes                     |
      | providerNpi                  | 1921987974              |
      | providerFirstName            | Steven                  |
      | providerLastName             | Gerrard                 |
      | providerMiddleName           | M                       |
      | enrollmentStartDate          | <eligibilityStartDate>  |
      | enrollmentEndDate            | <eligibilityEndDate>    |
      | eligibilityStartDate         | <eligibilityStartDate>  |
      | eligibilityEndDate           | <eligibilityEndDate>    |
      | selectionReason              | test                    |
      | planEndDateReason            | test                    |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | <categoryCode>          |
      | coverageCode                 | cc01                    |
      | planCode                     | 455701400               |
    And User provide other enrollment segments details
      | recordId          | 21        |
      | genericFieldText3 | MEDICAL   |
      | genericFieldText1 | O         |
      | genericFieldText2 | A         |
      | segmentTypeCode   | OTHER_TXN |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | <name>.consumerId     |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | 1stDayofPresentMonth  |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "NO MATCH","No Enrollment"
    Then I verify benefit status information with data
      | programPopulation | <programPopulation> |
      | benefitStatus     | <benefitStatus>     |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>         |
      | [0].consumerAction       | <consumerAction> |
      | [0].planSelectionAllowed | false            |
    And I verify enrollment by consumer id "<name>.consumerId" with data
       # will updtae
      | txnStatus            | DISENROLLED            |
      | status               | DISENROLLED            |
      | medicalPlanEndDate   | <eligibilityEndDate>   |
      # remains same
      | medicalPlanBeginDate | <eligibilityStartDate> |
      | enrollmentType       | MEDICAL                |
      | channel              | SYSTEM_INTEGRATION     |
      | createdBy            | 597                    |
      | createdOn            | current                |
      | planCode             | 455701400              |
      | selectionReason      | test                   |
      | planEndDateReason    | test                   |
    When Wait for 3 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                       |
      | consumerId    | <name>.consumerId                           |
      | correlationId | <name><event>.enrollments.[0].correlationId |
      | status        | SUCCESS                                     |
      | consumerFound | true                                        |
      | DPBI          |[blank]|
      | module        | ENROLLMENT                                  |
    Then I will verify business events are generated with data
      | eventName            | RECONCILIATION                                                |
      | correlationId        | <name><event>.eligibilities.[0].coreEligibility.correlationId |
      | module               | ENROLLMENT                                                    |
      | externalConsumerId   | <name>.externalConsumerId                                     |
      | channel              | SYSTEM_INTEGRATION                                            |
      | createdBy            | 597                                                           |
      | processDate          | current                                                       |
      | externalCaseId       | <name>.caseIdentificationNumber                               |
      | consumerId           | <name>.consumerId                                             |
      | consumerName         | <name>                                                        |
      | reconciliationAction | Enrollment Created                                            |
      # Business Event Optional Fields
      | enrollmentStartDate  | <eligibilityStartDate>                                        |
      | enrollmentEndDate    | <eligibilityEndDate>                                          |
      | selectionReason      | test                                                          |
      #Need to verify if planEndDateReason =planChangeReason
#      | planEndDateReason    | end reason                      |
      | rejectionReason      | null                                                          |
      | planCode             | 455701400                                                     |
      | selectionStatus      | DISENROLLED                                                   |
      | pcpNpi               | 1921987974                                                    |
      | pcpName              | Steven Gerrard                                                |

    Examples:
      | name       | eligibilityStartDate | eligibilityEndDate      | programCode | subProgramTypeCode | categoryCode | eligibilityStatusCode | programPopulation | action      | consumerAction | benefitStatus | event             |
      | 24692-02-1 | 1stDayofPresentMonth | lastDayOfThePresentYear | R           | HoosierHealthwise  | 10           | V                     | HHW-Voluntary     | Unavailable | null           | Eligible      | eventcorrelation1 |
      | 24692-02-2 | 1stDayofPresentMonth | lastDayOfThePresentYear | A           | HoosierCareConnect | 10           | V                     | HCC-Voluntary     | Unavailable | null           | Eligible      | eventcorrelation2 |
   #chnaged as part of CP-31579
      | 24692-02-3 | 1stDayofPresentMonth | lastDayOfThePresentYear | H           | HealthyIndianaPlan | 10           | V                     | HIP-Voluntary     | Unavailable | null           | Enrolled      | eventcorrelation3 |


  @API-CP-24692 @API-CP-24692-02 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: INEB-Enrollment Recon-No Eligibility-Enrollment event is failed- negative
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event>                |
      | consumerId                   | <name>.consumerId            |
      | isEligibilityRequired        | no                           |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                          |
      | recordId                     | 21                           |
      | eligibilityRecordId          | 21                           |
      | enrollmentRecordId           | 21                           |
      | isEnrollmentProviderRequired | no                           |
      | enrollmentStartDate          | <reconEligibilityStartDate>  |
      | enrollmentEndDate            | <reconEligibilityEndDate>    |
      | eligibilityStartDate         | <reconEligibilityStartDate>  |
      | eligibilityEndDate           | <reconEligibilityEndDate>    |
      | txnStatus                    | Accepted                     |
      | programCode                  | <reconProgramCode>           |
      | subProgramTypeCode           | <reconSubProgramTypeCode>    |
      | eligibilityStatusCode        | <reconEligibilityStatusCode> |
      | categoryCode                 | <reconCategoryCode>          |
      | coverageCode                 | cc01                         |
      | segmentTypeCode              | OTHER                        |
    And User provide other enrollment segments details
      | recordId          | 21        |
      | genericFieldText3 | MEDICAL   |
      | genericFieldText1 | O         |
      | genericFieldText2 | A         |
      | segmentTypeCode   | OTHER_TXN |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | <name>.consumerId     |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | 1stDayofPresentMonth  |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                       |
      | correlationId | <name><event>.enrollments.[0].correlationId |
      | status        | FAIL                                        |
      | errorStack    | No Valid Eligibility                        |
    Examples:
      | name       | reconEligibilityStartDate | reconEligibilityEndDate | reconProgramCode | reconSubProgramTypeCode | reconCategoryCode | reconEligibilityStatusCode | population    | action   | event     |
      | 24692-03-1 | 1stDayofPresentMonth      | highDate                | R                | HoosierHealthwise       | 10                | V                          | HHW-Voluntary | Required | eligcorr1 |
      | 24692-03-3 | 1stDayofPresentMonth      | highDate                | A                | HoosierCareConnect      | 10                | M                          | HHW-Voluntary | Required | eligcorr2 |
      | 24692-03-4 | 1stDayofPresentMonth      | highDate                | H                | HealthyIndianaPlan      | 4                 | V                          | HHW-Voluntary | Required | eligcorr3 |

  @API-CP-24692 @API-CP-24692-03 @API-CP-24693 @API-CP-24693-01 @API-CP-32231 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario: Inflight enrollment -plan change event is failed and enrollment is not updated
  CP-23753 IN-EB Create or Update Data based on Plan Change Reject Response
  CP-23758 IN-EB Create Business Event - Plan Change Reject Response
  CP-26294 IN-EB Updated - IN-EB Decide Plan Change Responses HIP (Record Id 18)
  CP-32231 AC 2.0 Identify In-Flight Enrollment Selections for the Consumer
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
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
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
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | rejectionReason              | test1                   |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | <name>.consumerId    |
      | [0].planId             | 199                  |
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
    And I initiated get enrollment by consumer id "<name>.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "<name>b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | <name>.consumerId                |
      | [0].planId             | delete::                         |
      | [0].planCode           | 455701400                        |
      | [0].enrollmentId       | <name>b.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED              |
      | [0].txnStatus          | DISENROLL_SUBMITTED              |
      | [0].startDate          | fdofmnth::                       |
      | [0].endDate            | lastDayOfThePresentYear::        |
      | [0].enrollmentType     | delete::                         |
      | [0].subProgramTypeCode | HealthyIndianaPlan               |
      | [0].serviceRegionCode  | Statewide                        |
      | [1].planId             | delete::                         |
      | [1].planCode           | 355787430                        |
      | [1].consumerId         | <name>.consumerId                |
      | [1].enrollmentId       | <name>b.selectedEnrollmentId     |
      | [1].startDate          | firstDayOfNextYear::             |
      | [1].endDate            | highDate::                       |
      | [1].subProgramTypeCode | HealthyIndianaPlan               |
      | [1].serviceRegionCode  | Statewide                        |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | enrollmentRecordId           | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
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
      | recordId          | 21        |
      | genericFieldText3 | MEDICAL   |
      | genericFieldText1 | O         |
      | genericFieldText2 | A         |
      | segmentTypeCode   | OTHER_TXN |
    And I run create Eligibility and Enrollment API
    Then I will verify business events are generated with data
      | eventName  | ENROLLMENT_SAVE_EVENT |
      | status     | FAIL                  |
      | errorStack | In-flight Enrollment  |

  @API-CP-24692 @API-CP-24692-03.1 @API-CP-24693 @API-CP-24693-1.1 @API-CP-32231 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: Inflight enrollment -new enrollment event is failed and enrollment is not updated
  CP-32231 AC 2.0 Identify In-Flight Enrollment Selections for the Consumer
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
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | enrollmentRecordId           | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityStatusCode        | M                    |
      | genericFieldText1            | Eligible             |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    And User provide other enrollment segments details
      | recordId          | 21        |
      | genericFieldText3 | MEDICAL   |
      | genericFieldText1 | O         |
      | genericFieldText2 | A         |
      | segmentTypeCode   | OTHER_TXN |
    And I run create Eligibility and Enrollment API
    Then I will verify business events are generated with data
      | eventName  | ENROLLMENT_SAVE_EVENT |
      | status     | FAIL                  |
      | errorStack | In-flight Enrollment  |
    Examples:
      | name    | programCode | subProgramTypeCode | planCode  | programPopulation | planName | planId |
      | 24692-1 | R           | HoosierHealthwise  | 400752220 | HHW-Mandatory     | ANTHEM   | 103    |
      | 24692-2 | A           | HoosierCareConnect | 400752220 | HHW-Mandatory     | ANTHEM   | 103    |

  @API-CP-24692 @API-CP-24692-04.02 @API-CP-24693-03.02 @API-CP-24693 @API-CP-24694 @API-CP-24694-03 @API-CP-34263 @API-CP-32231 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: Enrollment Recon-Plan Match- high date set to accepted -Recon Action - Enrollment Updated
  CP-32231 AC 1.0 Decide Scenario is MMIS Sends Reconciliation for Enrollment
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event1>          |
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | enrollmentRecordId           | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | providerNpi                  | 1921987974              |
      | providerFirstName            | Steven                  |
      | providerLastName             | Gerrard                 |
      | providerMiddleName           | M                       |
      | eligibilityStartDate         | <eligibilityStartDate>  |
      | eligibilityEndDate           | <eligibilityEndDate>    |
      | enrollmentStartDate          | <enrollmentStartDate>   |
      | enrollmentEndDate            | <enrollmentEndDate>     |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | planCode                     | 455701400               |
      | planId                       | 33                      |
      | genericFieldText1            | Eligible                |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
    And I run create Eligibility and Enrollment API
    When I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event2>             |
      | consumerId                   | <name>.consumerId          |
      | isEligibilityRequired        | yes                        |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                        |
      | recordId                     | 21                         |
      | eligibilityRecordId          | 21                         |
      | enrollmentRecordId           | 21                         |
      | isEnrollmentProviderRequired | yes                        |
      | providerNpi                  | 1921987974                 |
      | providerFirstName            | Steven                     |
      | providerLastName             | Gerrard                    |
      | providerMiddleName           | M                          |
      | enrollmentStartDate          | <reconEnrollmentStartDate> |
      | eligibilityStartDate         | <eligibilityStartDate>     |
      | eligibilityEndDate           | <eligibilityEndDate>       |
      | enrollmentEndDate            | <reconEnrollmentEndDate>   |
      | txnStatus                    | Accepted                   |
      | programCode                  | <programCode>              |
      | subProgramTypeCode           | <subProgramTypeCode>       |
      | eligibilityStatusCode        | <eligibilityStatusCode>    |
      | planCode                     | 455701400                  |
      | planId                       | 33                         |
      | genericFieldText1            | Eligible                   |
      | categoryCode                 | 10                         |
      | coverageCode                 | cc01                       |
    And User provide other enrollment segments details
      | recordId          | 21        |
      | genericFieldText3 | MEDICAL   |
      | genericFieldText1 | O         |
      | genericFieldText2 | C         |
      | segmentTypeCode   | OTHER_TXN |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    # CP-32231 AC 1.0 Decide Scenario is MMIS Sends Reconciliation for Enrollment
    Then I verify scenario output in the benefit status records are displayed as "EXACT MATCH","Plan Match"
    And I verify enrollment by consumer id "<name>.consumerId" with data
       # will updtae
      | txnStatus            | <reconStatus>              |
      | status               | <reconStatus>              |
      # remains same
      | medicalPlanBeginDate | <reconEnrollmentStartDate> |
      | medicalPlanEndDate   | <reconEnrollmentEndDate>   |
      | enrollmentType       | MEDICAL                    |
      | channel              | SYSTEM_INTEGRATION         |
      | createdBy            | 597                        |
      | createdOn            | current                    |
      | updatedBy            | 597                        |
      | updatedOn            | current                    |
      | planCode             | 455701400                  |
    And Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT                      |
      | correlationId | <name><event1>.enrollments.[0].correlationId |
      | consumerId    | <name>.consumerId                            |
      | consumerFound | true                                         |
      | status        | SUCCESS                                      |
      | DPBI          |[blank]|
      | module        | ENROLLMENT                                   |
    Then I will verify business events are generated with data
      | eventName                    | RECONCILIATION                                                 |
      | externalConsumerId           | <name>.externalConsumerId                                      |
      | channel                      | SYSTEM_INTEGRATION                                             |
      | createdBy                    | 597                                                            |
      | processDate                  | current                                                        |
      | externalCaseId               | <name>.caseIdentificationNumber                                |
      | consumerId                   | <name>.consumerId                                              |
      | consumerName                 | <name>                                                         |
      | reconciliationAction         | Enrollment Updated                                             |
      | correlationId                | <name><event2>.eligibilities.[0].coreEligibility.correlationId |
      | module                       | ENROLLMENT                                                     |
      # Business Event Optional Fields
      | oldValue.enrollmentStartDate | 1stDayofPresentMonth                                           |
      | oldValue.enrollmentEndDate   | <enrollmentEndDate>                                            |
      | oldValue.selectionReason     | null                                                           |
      | oldValue.planEndDateReason   | null                                                           |
      | oldValue.rejectionReason     | null                                                           |
      | oldValue.planCode            | 455701400                                                      |
      | oldValue.selectionStatus     | <status>                                                       |
      | oldValue.pcpNpi              | null                                                           |
      | oldValue.pcpName             | null                                                           |
      | newValue.enrollmentStartDate | 1stDayofPresentMonth                                           |
      | newValue.enrollmentEndDate   | <reconEnrollmentEndDate>                                       |
      | newValue.selectionReason     | null                                                           |
      | newValue.planEndDateReason   | null                                                           |
      | newValue.rejectionReason     | null                                                           |
      | newValue.planCode            | 455701400                                                      |
      | newValue.selectionStatus     | <reconStatus>                                                  |
      | newValue.pcpNpi              | 1921987974                                                     |
      | newValue.pcpName             | Steven Gerrard                                                 |

    Examples:
      | name       | eligibilityStartDate | eligibilityEndDate | benefitStatus | programCode | subProgramTypeCode | eligibilityStatusCode | enrollmentStartDate  | reconEnrollmentStartDate | reconEnrollmentEndDate  | enrollmentEndDate       | reconStatus | status      | event1       | event2       |
      | 24692-04-1 | 1stDayofPresentMonth | highDate           | EXACT MATCH   | R           | HoosierHealthwise  | M                     | 1stDayofPresentMonth | 1stDayofPresentMonth     | highDate                | lastDayOfThePresentYear | ACCEPTED    | DISENROLLED | Eligibility1 | Eligibility2 |
      | 24692-04-2 | 1stDayofPresentMonth | highDate           | EXACT MATCH   | A           | HoosierCareConnect | V                     | 1stDayofPresentMonth | 1stDayofPresentMonth     | highDate                | lastDayOfThePresentYear | ACCEPTED    | DISENROLLED | Eligibility1 | Eligibility2 |
      | 24692-04-3 | 1stDayofPresentMonth | highDate           | EXACT MATCH   | H           | HealthyIndianaPlan | M                     | 1stDayofPresentMonth | 1stDayofPresentMonth     | highDate                | lastDayOfThePresentYear | ACCEPTED    | DISENROLLED | Eligibility1 | Eligibility2 |
      | 24692-04-4 | 1stDayofPresentMonth | highDate           | EXACT MATCH   | R           | HoosierHealthwise  | M                     | 1stDayofPresentMonth | 1stDayofPresentMonth     | lastDayOfThePresentYear | highDate                | DISENROLLED | ACCEPTED    | Eligibility1 | Eligibility2 |
      | 24692-04-5 | 1stDayofPresentMonth | highDate           | EXACT MATCH   | A           | HoosierCareConnect | V                     | 1stDayofPresentMonth | 1stDayofPresentMonth     | lastDayOfThePresentYear | highDate                | DISENROLLED | ACCEPTED    | Eligibility1 | Eligibility2 |
      | 24692-04-6 | 1stDayofPresentMonth | highDate           | EXACT MATCH   | H           | HealthyIndianaPlan | M                     | 1stDayofPresentMonth | 1stDayofPresentMonth     | lastDayOfThePresentYear | highDate                | DISENROLLED | ACCEPTED    | Eligibility1 | Eligibility2 |


  @API-CP-24692 @API-CP-24692-04.03 @API-CP-24693-03.03 @API-CP-24693 @API-CP-32272 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: INEB- Enrollment Recon-Plan Match - change in PCP , selection reason and end reason
  CP-32272 AC 3.0 Inbound Records with “MMIS Requested” Plan and Plan Match to Enrollment in System
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | enrollmentRecordId           | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | enrollmentEndDate            | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | genericFieldText1            | Eligible                |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
      | planCode                     | 455701400               |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | selectionReason              | test2                   |
    And User provide other enrollment segments details
      | recordId          | 21        |
      | genericFieldText3 | MEDICAL   |
      | genericFieldText1 | O         |
      | genericFieldText2 | A         |
      | segmentTypeCode   | OTHER_TXN |
    And I run create Eligibility and Enrollment API
    When I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | enrollmentRecordId           | 21                       |
      | isEnrollmentProviderRequired | no                       |
      | enrollmentStartDate          | 1stDayofPresentMonth     |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | highDate                 |
      | enrollmentEndDate            | highDate                 |
      | txnStatus                    | Accepted                 |
      | programCode                  | <programCode>            |
      | planCode                     | 455701400                |
      | planId                       | 33                       |
      | subProgramTypeCode           | <subProgramTypeCode>     |
      | eligibilityStatusCode        | <eligibilityStatusCode>  |
      | genericFieldText1            | Eligible                 |
      | categoryCode                 | 10                       |
      | coverageCode                 | cc01                     |
      | serviceRegionCode            | Statewide                |
      | anniversaryDate              | anniversaryDate          |
      | channel                      | SYSTEM_INTEGRATION       |
      | selectionReason              | updated selection reason |
      | planEndDateReason            | updated end reason       |
    And User provide other enrollment segments details
      | recordId          | 21        |
      | genericFieldText3 | MEDICAL   |
      | genericFieldText1 | O         |
      | genericFieldText2 | C         |
      | segmentTypeCode   | OTHER_TXN |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | <name>.consumerId     |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | 1stDayofPresentMonth  |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "EXACT MATCH","Plan Match"
    And I verify enrollment by consumer id "<name>.consumerId" with data
       # will updtae
      | txnStatus            | ACCEPTED             |
      | status               | ACCEPTED             |
      | medicalPlanEndDate   | highDate             |
      | enrollmentType       | MEDICAL              |
      | channel              | SYSTEM_INTEGRATION   |
      | createdBy            | 597                  |
      | updatedBy            | 597                  |
      | updatedOn            | current              |
      | createdOn            | current              |
      | planCode             | 455701400            |
    # CP-24693 AC 3.0 expects selection reason to be updated with plan match but this is changed with CP-32272 AC 3.0 which states that updating selection reason only happens if inbound record has earlier start date
      | selectionReason      | test2                |
#      | selectionReason      | updated selection reason |
      | planEndDateReason    | updated end reason   |
    # remain same
      | medicalPlanBeginDate | 1stDayofPresentMonth |
    Then I will verify business events are generated with data
      | eventName | ENROLLMENT_UPDATE_EVENT |
      | status    | SUCCESS                 |
      | DPBI      |[blank]|
    Examples:
      | name       | reconEligibilityStartDate | reconEligibilityEndDate | benefitStatus | programCode | subProgramTypeCode | eligibilityStatusCode |
      | 24692-04-7 | 1stDayofPresentMonth      | lastDayOfThePresentYear | EXACT MATCH   | R           | HoosierHealthwise  | M                     |
      | 24692-04-8 | 1stDayofPresentMonth      | lastDayOfThePresentYear | EXACT MATCH   | A           | HoosierCareConnect | V                     |
      | 24692-04-9 | 1stDayofPresentMonth      | lastDayOfThePresentYear | EXACT MATCH   | H           | HealthyIndianaPlan | M                     |

  @API-CP-24693-04.01 @API-CP-24693 @API-CP-32231 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: INEB- Enrollment Recon-No Plan Match - end date previous enrollment and add new enrollment high end date
  CP-32231 AC 1.0 Decide Scenario is MMIS Sends Reconciliation for Enrollment
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    When I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | enrollmentRecordId           | 21                      |
      | isEnrollmentProviderRequired | yes                     |
      | providerNpi                  | <provider>              |
      | providerFirstName            | Steven                  |
      | providerLastName             | Gerrard                 |
      | providerMiddleName           | M                       |
      | providerStartDate            | 1stDayofPresentMonth    |
      | providerEndDate              | lastDayOfThePresentYear |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | enrollmentEndDate            | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | planCode                     | <planCode>              |
      | planId                       | 33                      |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | genericFieldText1            | Eligible                |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
      | selectionReason              | selection reason        |
      | planEndDateReason            | plan end reason 1       |
    And User provide other enrollment segments details
      | recordId          | 21        |
      | genericFieldText3 | MEDICAL   |
      | genericFieldText1 | O         |
      | genericFieldText2 | A         |
      | segmentTypeCode   | OTHER_TXN |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    When I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | enrollmentRecordId           | 21                       |
      | isEnrollmentProviderRequired | yes                      |
      | providerNpi                  | <reconProvider>          |
      | providerStartDate            | 1stDayofNextMonth        |
      | providerEndDate              | highDate                 |
      | enrollmentStartDate          | 1stDayofNextMonth        |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | highDate                 |
      | enrollmentEndDate            | highDate                 |
      | planEndDateReason            | plan end reason 2        |
      | txnStatus                    | Accepted                 |
      | programCode                  | <programCode>            |
      | planCode                     | <reconplanCode>          |
      | planId                       | 33                       |
      | subProgramTypeCode           | <subProgramTypeCode>     |
      | eligibilityStatusCode        | <eligibilityStatusCode>  |
      | genericFieldText1            | Eligible                 |
      | categoryCode                 | 10                       |
      | coverageCode                 | cc01                     |
      | selectionReason              | updated selection reason |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | <name>.consumerId     |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofNextMonth    |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | 1stDayofPresentMonth  |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    Then I send API call with name "<name>b" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    # CP-32231 AC 1.0 Decide Scenario is MMIS Sends Reconciliation for Enrollment
    Then I verify scenario output in the benefit status records are displayed as "EXACT MATCH","No plan Match"
    Then I verify benefit status information with data
      | programPopulation | <programPopulation> |
      | benefitStatus     | <benefitStatus>     |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>         |
      | [0].consumerAction       | <consumerAction> |
      | [0].planSelectionAllowed | true             |
    And I verify enrollment by consumer id "<name>.consumerId" with data for multiple records
       # will create new segment
      | [0].txnStatus                      | ACCEPTED                 |
      | [0].status                         | ACCEPTED                 |
      | [0].medicalPlanEndDate             | highDate                 |
      | [0].enrollmentType                 | MEDICAL                  |
      | [0].channel                        | SYSTEM_INTEGRATION       |
      | [0].createdBy                      | 597                      |
      | [0].updatedBy                      | 597                      |
      | [0].updatedOn                      | current                  |
      | [0].createdOn                      | current                  |
      | [0].enrollmentProvider.providerNpi | <reconProvider>          |
      | [0].planCode                       | <reconplanCode>          |
      | [0].selectionReason                | updated selection reason |
      | [0].planEndDateReason              | plan end reason 2        |
      | [0].medicalPlanBeginDate           | 1stDayofNextMonth        |
#      | [0].enrollmentProvider.providerStartDate | 1stDayofNextMonth        |
#      | [0].enrollmentProvider.providerEndDate   | highDate                 |
      #will update old segment end date to 1 day less than the new enrollment start date
      | [1].txnStatus                      | DISENROLLED              |
      | [1].status                         | DISENROLLED              |
      | [1].medicalPlanEndDate             | lastDayofPresentMonth    |
      | [1].enrollmentType                 | MEDICAL                  |
      | [1].channel                        | SYSTEM_INTEGRATION       |
      | [1].createdBy                      | 597                      |
      | [1].updatedBy                      | 597                      |
      | [1].updatedOn                      | current                  |
      | [1].createdOn                      | current                  |
      | [1].enrollmentProvider.providerNpi | <provider>               |
      | [1].planCode                       | <planCode>               |
      | [1].selectionReason                | selection reason         |
      | [1].planEndDateReason              | plan end reason 2        |
      | [1].medicalPlanBeginDate           | 1stDayofPresentMonth     |
#      | [1].enrollmentProvider.providerStartDate | 1stDayofNextMonth        |
#      | [1].enrollmentProvider.providerEndDate   | highDate                 |
    And Wait for 3 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT               |
      | consumerId    | <name>.consumerId                     |
      | status        | SUCCESS                               |
      | correlationId | <name>b.enrollments.[0].correlationId |
      | consumerFound | true                                  |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                 |
      | consumerId    | <name>a.consumerId                    |
      | status        | SUCCESS                               |
      | correlationId | <name>a.enrollments.[0].correlationId |
      | consumerFound | true                                  |
      | DPBI          |[blank]|
    Examples:
      | name       | reconEligibilityStartDate | reconEligibilityEndDate | benefitStatus | programCode | subProgramTypeCode | eligibilityStatusCode | planCode  | reconplanCode | reconProvider | provider   | programPopulation | action    | consumerAction              |
      | 24693-04-1 | 1stDayofPresentMonth      | lastDayOfThePresentYear | Enrolled      | R           | HoosierHealthwise  | M                     | 500307680 | 300119960     | 7736793744    | 2831291940 | HHW-Mandatory     | Available | Plan Change Pre-lockin      |
      | 24693-04-2 | 1stDayofPresentMonth      | lastDayOfThePresentYear | Enrolled      | A           | HoosierCareConnect | V                     | 399243310 | 699842000     | 5369321579    | 5894807202 | HCC-Voluntary     | Available | Plan Change Pre-lockin      |
      | 24693-04-3 | 1stDayofPresentMonth      | lastDayOfThePresentYear | Enrolled      | H           | HealthyIndianaPlan | M                     | 755726440 | 455701400     | 4129105074    | 1550701659 | HIP-Mandatory     | Available | Plan Change Open Enrollment |

  @API-CP-24693-04.02 @API-CP-24693 @API-CP-32272 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: INEB- Enrollment Recon-No Plan Match - end date previous enrollment and add new enrollment non high end date-Plan Change Business Event
  CP-32272 AC 4.0 Inbound Records with “MMIS Requested” Plan and No Plan Match to Enrollment in System
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    When I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | enrollmentRecordId           | 21                      |
      | isEnrollmentProviderRequired | yes                     |
      | providerNpi                  | <provider>              |
      | providerFirstName            | Steven                  |
      | providerLastName             | Gerrard                 |
      | providerStartDate            | 1stDayofPresentMonth    |
      | providerEndDate              | lastDayOfThePresentYear |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | enrollmentEndDate            | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | planCode                     | <planCode>              |
      | planId                       | 33                      |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | genericFieldText1            | Eligible                |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
      | selectionReason              | selection reason        |
      | planEndDateReason            | plan end reason 1       |
    And User provide other enrollment segments details
      | recordId          | 21        |
      | genericFieldText3 | MEDICAL   |
      | genericFieldText1 | O         |
      | genericFieldText2 | A         |
      | segmentTypeCode   | OTHER_TXN |
    And I run create Eligibility and Enrollment API
    When I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | enrollmentRecordId           | 21                       |
      | isEnrollmentProviderRequired | yes                      |
      | providerNpi                  | <reconProvider>          |
      | providerFirstName            | HOUSTON                  |
      | providerLastName             | PAUL                     |
      | providerStartDate            | 1stDayofNextMonth        |
      | providerEndDate              | highDate                 |
      | enrollmentStartDate          | 1stDayofNextMonth        |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | highDate                 |
      | enrollmentEndDate            | lastDayOfThePresentYear  |
      | planEndDateReason            | plan end reason 2        |
      | txnStatus                    | Accepted                 |
      | programCode                  | <programCode>            |
      | planCode                     | <reconplanCode>          |
      | planId                       | 33                       |
      | subProgramTypeCode           | <subProgramTypeCode>     |
      | eligibilityStatusCode        | <eligibilityStatusCode>  |
      | genericFieldText1            | Eligible                 |
      | categoryCode                 | 10                       |
      | coverageCode                 | cc01                     |
      | selectionReason              | updated selection reason |

    And User provide other enrollment segments details
      | recordId          | 21        |
      | genericFieldText3 | MEDICAL   |
      | genericFieldText1 | O         |
      | genericFieldText2 | C         |
      | segmentTypeCode   | OTHER_TXN |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "EXACT MATCH","No plan Match"
    Then I verify benefit status information with data
      | programPopulation | <programPopulation> |
      | benefitStatus     | <benefitStatus>     |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action> |
      | [0].consumerAction       | null     |
      | [0].planSelectionAllowed | false    |
    And I verify enrollment by consumer id "<name>.consumerId" with data for multiple records
       # will create new segment
      | [0].txnStatus                            | <status>                 |
      | [0].status                               | <status>                 |
      | [0].medicalPlanEndDate                   | lastDayOfThePresentYear  |
      | [0].enrollmentType                       | MEDICAL                  |
      | [0].channel                              | SYSTEM_INTEGRATION       |
      | [0].createdBy                            | 597                      |
      | [0].updatedBy                            | 597                      |
      | [0].updatedOn                            | current                  |
      | [0].createdOn                            | current                  |
      | [0].enrollmentProvider.providerNpi       | <reconProvider>          |
      | [0].planCode                             | <reconplanCode>          |
      | [0].selectionReason                      | updated selection reason |
      | [0].planEndDateReason                    | plan end reason 2        |
      | [0].medicalPlanBeginDate                 | 1stDayofNextMonth        |
      | [0].enrollmentProvider.providerStartDate | 1stDayofNextMonth        |
      | [0].enrollmentProvider.providerEndDate   | highDate                 |
      #will update old segment end date to 1 day less than the new enrollment start date
      | [1].txnStatus                            | <reconStatus>            |
      | [1].status                               | <reconStatus>            |
      | [1].medicalPlanEndDate                   | lastDayofPresentMonth    |
      | [1].enrollmentType                       | MEDICAL                  |
      | [1].channel                              | SYSTEM_INTEGRATION       |
      | [1].createdBy                            | 597                      |
      | [1].updatedBy                            | 597                      |
      | [1].updatedOn                            | current                  |
      | [1].createdOn                            | current                  |
      | [1].enrollmentProvider.providerNpi       | <provider>               |
      | [1].planCode                             | <planCode>               |
      | [1].selectionReason                      | selection reason         |
      | [1].planEndDateReason                    | plan end reason 2        |
      | [1].medicalPlanBeginDate                 | 1stDayofPresentMonth     |
      | [1].enrollmentProvider.providerStartDate | 1stDayofPresentMonth     |
      | [1].enrollmentProvider.providerEndDate   | lastDayOfThePresentYear  |
    And Wait for 3 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | <name>.consumerId       |
      | status        | SUCCESS                 |
      | correlationId | null                    |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | <name>.consumerId     |
      | status        | SUCCESS               |
      | correlationId | null                  |
      | consumerFound | true                  |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName                              | RECONCILIATION                  |
      | externalConsumerId                     | <name>.externalConsumerId       |
      | channel                                | SYSTEM_INTEGRATION              |
      | createdBy                              | 597                             |
      | processDate                            | current                         |
      | externalCaseId                         | <name>.caseIdentificationNumber |
      | consumerId                             | <name>.consumerId               |
      | consumerName                           | <name>                          |
      | reconciliationAction                   | Plan Change                     |
      # Business Event Optional Fields
      | previousEnrollment.enrollmentStartDate | <eligibilityStartDate>          |
      | previousEnrollment.enrollmentEndDate   | lastDayofPresentMonth           |
      | previousEnrollment.selectionReason     | selection reason                |
      | previousEnrollment.planEndDateReason   | plan end reason 1               |
      | previousEnrollment.rejectionReason     | null                            |
      | previousEnrollment.planCode            | <planCode>                      |
      | previousEnrollment.selectionStatus     | <status>                        |
      | previousEnrollment.pcpNpi              | <provider>                      |
      | previousEnrollment.pcpName             | Steven Gerrard                  |
      | requestedSelection.enrollmentStartDate | <reconEnrollmentStartDate>      |
      | requestedSelection.enrollmentEndDate   | <eligibilityEndDate>            |
      | requestedSelection.selectionReason     | updated selection reason        |
      | requestedSelection.planEndDateReason   | plan end reason 2               |
      | requestedSelection.rejectionReason     | null                            |
      | requestedSelection.planCode            | <reconplanCode>                 |
      | requestedSelection.selectionStatus     | <reconStatus>                   |
      | requestedSelection.pcpNpi              | <reconProvider>                 |
      | requestedSelection.pcpName             | HOUSTON PAUL                    |


    Examples:
      | name       | eligibilityStartDate | eligibilityEndDate      | benefitStatus | programCode | subProgramTypeCode | eligibilityStatusCode | planCode  | reconplanCode | reconProvider | provider   | action      | programPopulation | status      | reconStatus | reconEnrollmentStartDate |
      | 24693-05-1 | 1stDayofPresentMonth | lastDayOfThePresentYear | Eligible      | R           | HoosierHealthwise  | M                     | 500307680 | 300119960     | 7736793744    | 2831291940 | Unavailable | HHW-Mandatory     | DISENROLLED | DISENROLLED | 1stDayofNextMonth        |
      | 24693-05-2 | 1stDayofPresentMonth | lastDayOfThePresentYear | Eligible      | A           | HoosierCareConnect | V                     | 399243310 | 699842000     | 5369321579    | 5894807202 | Unavailable | HCC-Voluntary     | DISENROLLED | DISENROLLED | 1stDayofNextMonth        |
      | 24693-05-3 | 1stDayofPresentMonth | lastDayOfThePresentYear | Enrolled      | H           | HealthyIndianaPlan | M                     | 755726440 | 455701400     | 4129105074    | 1550701659 | Unavailable | HIP-Mandatory     | DISENROLLED | DISENROLLED | 1stDayofNextMonth        |


  @API-CP-24694 @API-CP-24694-04-01 @API-CP-32280 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: Verify Enrollment Recon Business Event- No Enrollment Update
  CP-32280 7.0 Business Event Optional Enrollment Fields: No Update
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event1>          |
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | yes                     |
      | providerNpi                  | 1921987974              |
      | providerFirstName            | Steven                  |
      | providerLastName             | Gerrard                 |
      | providerMiddleName           | M                       |
      | enrollmentStartDate          | <eligibilityStartDate>  |
      | enrollmentEndDate            | <eligibilityEndDate>    |
      | eligibilityStartDate         | <eligibilityStartDate>  |
      | eligibilityEndDate           | <eligibilityEndDate>    |
      | selectionReason              | test                    |
      | planEndDateReason            | test                    |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | <categoryCode>          |
      | coverageCode                 | cc01                    |
      | planCode                     | 455701400               |
    And User provide other enrollment segments details
      | recordId          | 21        |
      | genericFieldText3 | MEDICAL   |
      | genericFieldText1 | O         |
      | genericFieldText2 | A         |
      | segmentTypeCode   | OTHER_TXN |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | <name>.consumerId     |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | 1stDayofPresentMonth  |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event2>          |
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | yes                     |
      | providerNpi                  | 1921987974              |
      | providerFirstName            | Steven                  |
      | providerLastName             | Gerrard                 |
      | providerMiddleName           | M                       |
      | enrollmentStartDate          | <eligibilityStartDate>  |
      | enrollmentEndDate            | <eligibilityEndDate>    |
      | eligibilityStartDate         | <eligibilityStartDate>  |
      | eligibilityEndDate           | <eligibilityEndDate>    |
      | selectionReason              | test                    |
      | planEndDateReason            | test                    |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | <categoryCode>          |
      | coverageCode                 | cc01                    |
      | planCode                     | 455701400               |
    And User provide other enrollment segments details
      | recordId          | 21        |
      | genericFieldText3 | MEDICAL   |
      | genericFieldText1 | O         |
      | genericFieldText2 | A         |
      | segmentTypeCode   | OTHER_TXN |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | <name>.consumerId     |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | 1stDayofPresentMonth  |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I will verify business events are generated with data
      | eventName            | RECONCILIATION                                                 |
      | correlationId        | <name><event2>.eligibilities.[0].coreEligibility.correlationId |
      | externalConsumerId   | <name>.externalConsumerId                                      |
      | channel              | SYSTEM_INTEGRATION                                             |
      | createdBy            | 597                                                            |
      | processDate          | current                                                        |
      | externalCaseId       | <name>.caseIdentificationNumber                                |
      | consumerId           | <name>.consumerId                                              |
      | consumerName         | <name>                                                         |
      | reconciliationAction | No Enrollment Update                                           |
      | module               | ENROLLMENT                                                     |
      # Business Event Optional Fields
      | enrollmentStartDate  | <eligibilityStartDate>                                         |
      | enrollmentEndDate    | <eligibilityEndDate>                                           |
      | selectionReason      | test                                                           |
      #Need to verify if planEndDateReason =planChangeReason
#      | planEndDateReason    | end reason                      |
      | rejectionReason      | null                                                           |
      | planCode             | 455701400                                                      |
      | selectionStatus      | DISENROLLED                                                    |
      | pcpNpi               | 1921987974                                                     |
      | pcpName              | Steven Gerrard                                                 |

    Examples:
      | name       | eligibilityStartDate | eligibilityEndDate      | programCode | subProgramTypeCode | categoryCode | eligibilityStatusCode | programPopulation | action      | consumerAction | benefitStatus        | event1       | event2       |
      | 24694-04-1 | 1stDayofPresentMonth | lastDayOfThePresentYear | R           | HoosierHealthwise  | 10           | V                     | HHW-Voluntary     | Unavailable | null           | benefit status error | eligibility1 | eligibility2 |
      | 24694-04-2 | 1stDayofPresentMonth | lastDayOfThePresentYear | A           | HoosierCareConnect | 10           | V                     | HCC-Voluntary     | Unavailable | null           | benefit status error | eligibility1 | eligibility2 |
      | 24694-04-3 | 1stDayofPresentMonth | lastDayOfThePresentYear | H           | HealthyIndianaPlan | 10           | V                     | HIP-Voluntary     | Unavailable | null           | benefit status error | eligibility1 | eligibility2 |

  @API-CP-24694 @API-CP-24694-05-01 @API-CP-32280 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: Verify Enrollment Recon Business Event- Plan Change
  CP-32280 10.0 Business Event Optional Enrollment Fields: Plan Change
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | yes                     |
      | providerNpi                  | 1921987974              |
      | providerFirstName            | Steven                  |
      | providerLastName             | Gerrard                 |
      | providerMiddleName           | M                       |
      | enrollmentStartDate          | <eligibilityStartDate>  |
      | enrollmentEndDate            | <eligibilityEndDate>    |
      | eligibilityStartDate         | <eligibilityStartDate>  |
      | eligibilityEndDate           | <eligibilityEndDate>    |
      | selectionReason              | test                    |
      | planEndDateReason            | test                    |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | <categoryCode>          |
      | coverageCode                 | cc01                    |
      | planCode                     | <planCode>              |
    And User provide other enrollment segments details
      | recordId          | 21        |
      | genericFieldText3 | MEDICAL   |
      | genericFieldText1 | O         |
      | genericFieldText2 | A         |
      | segmentTypeCode   | OTHER_TXN |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | <name>.consumerId     |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | 1stDayofPresentMonth  |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId          |
      | isEligibilityRequired        | yes                        |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                        |
      | recordId                     | 21                         |
      | eligibilityRecordId          | 21                         |
      | isEnrollmentProviderRequired | yes                        |
      | providerNpi                  | <reconProviderNpi>         |
      | providerFirstName            | <reconProviderFirstName>   |
      | providerLastName             | <reconProviderLastName>    |
      | providerMiddleName           | N                          |
      | enrollmentStartDate          | <reconEnrollmentStartDate> |
      | enrollmentEndDate            | <eligibilityEndDate>       |
      | eligibilityStartDate         | <eligibilityStartDate>     |
      | eligibilityEndDate           | <eligibilityEndDate>       |
      | selectionReason              | test1                      |
      | planEndDateReason            | test1                      |
      | txnStatus                    | Accepted                   |
      | programCode                  | <programCode>              |
      | subProgramTypeCode           | <subProgramTypeCode>       |
      | eligibilityStatusCode        | <eligibilityStatusCode>    |
      | categoryCode                 | <categoryCode>             |
      | coverageCode                 | cc01                       |
      | planCode                     | <reconplanCode>            |
    And User provide other enrollment segments details
      | recordId          | 21        |
      | genericFieldText3 | MEDICAL   |
      | genericFieldText1 | O         |
      | genericFieldText2 | A         |
      | segmentTypeCode   | OTHER_TXN |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | <name>.consumerId     |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | 1stDayofPresentMonth  |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I will verify business events are generated with data
      | eventName                              | RECONCILIATION                  |
      | externalConsumerId                     | <name>.externalConsumerId       |
      | channel                                | SYSTEM_INTEGRATION              |
      | createdBy                              | 597                             |
      | processDate                            | current                         |
      | externalCaseId                         | <name>.caseIdentificationNumber |
      | consumerId                             | <name>.consumerId               |
      | consumerName                           | <name>                          |
      | reconciliationAction                   | Plan Change                     |
      # Business Event Optional Fields
      | previousEnrollment.enrollmentStartDate | <eligibilityStartDate>          |
      | previousEnrollment.enrollmentEndDate   | lastDayofPresentMonth           |
      | previousEnrollment.selectionReason     | test                            |
      | previousEnrollment.planEndDateReason   | test                            |
      | previousEnrollment.rejectionReason     | null                            |
      | previousEnrollment.planCode            | <planCode>                      |
      | previousEnrollment.selectionStatus     | <status>                        |
      | previousEnrollment.pcpNpi              | 1921987974                      |
      | previousEnrollment.pcpName             | Steven Gerrard                  |
      | requestedSelection.enrollmentStartDate | <reconEnrollmentStartDate>      |
      | requestedSelection.enrollmentEndDate   | highDate                        |
      | requestedSelection.selectionReason     | test1                           |
      | requestedSelection.planEndDateReason   | test1                           |
      | requestedSelection.rejectionReason     | null                            |
      | requestedSelection.planCode            | <reconplanCode>                 |
      | requestedSelection.selectionStatus     | <reconStatus>                   |
      | requestedSelection.pcpNpi              | <reconProviderNpi>              |
      | requestedSelection.pcpName             | HOUSTON PAUL                    |

    Examples:
      | name       | eligibilityStartDate | eligibilityEndDate | reconEnrollmentStartDate | programCode | subProgramTypeCode | eligibilityStatusCode | planCode  | reconplanCode | status      | reconStatus | reconProviderFirstName | reconProviderLastName | reconProviderNpi |
      | 24694-05-1 | 1stDayofPresentMonth | highDate           | 1stDayofNextMonth        | R           | HoosierHealthwise  | M                     | 500307680 | 300119960     | DISENROLLED | ACCEPTED    | HOUSTON                | PAUL                  | 4350373378       |
      | 24693-05-2 | 1stDayofPresentMonth | highDate           | 1stDayofNextMonth        | A           | HoosierCareConnect | V                     | 399243310 | 699842000     | DISENROLLED | ACCEPTED    | HOUSTON                | PAUL                  | 4350373378       |
      | 24693-05-3 | 1stDayofPresentMonth | highDate           | 1stDayofNextMonth        | H           | HealthyIndianaPlan | M                     | 755726440 | 455701400     | DISENROLLED | ACCEPTED    | HOUSTON                | PAUL                  | 4350373378       |


  @API-CP-32272 @API-CP-32272-02 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: IN-EB Assignment Update Create or Update - at least one "Add" Transaction or one "Change" transaction - New Enrollment Past Date
  CP-32272 AC 2.0 No Accepted Enrollment in System, Create New Enrollment Segment
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event>         |
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof3MonthsBefore |
      | enrollmentEndDate            | lastDayofLastMonth    |
      | eligibilityStartDate         | 1stDayof3MonthsBefore |
      | eligibilityEndDate           | lastDayofLastMonth    |
      | txnStatus                    | Accepted              |
      | programCode                  | <ProgramCode>         |
      | subProgramTypeCode           | <SubProgramTypeCode>  |
      | eligibilityStatusCode        | <StatusCode>          |
      | categoryCode                 | <CategoryCode>        |
      | coverageCode                 | cc01                  |
      | planCode                     | 455701400             |
      | selectionReason              | selection reason      |
      | planEndDateReason            | end reason            |
    And User provide other enrollment segments details
      | recordId          | 21        |
      | genericFieldText3 | MEDICAL   |
      | genericFieldText1 | O         |
      | genericFieldText2 | A         |
      | segmentTypeCode   | OTHER_TXN |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    # 2.0 No Accepted Enrollment in System, Create New Enrollment Segment
    # Then I will NOT update create an enrollment segment for the consumer in the system AND
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | enrollmentCreated | false |
    And Wait for 3 seconds
    # 2.0 No Accepted Enrollment in System, Create New Enrollment Segment
    # Then I will NOT publish any events for DP4BI to consume for reporting)
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                       |
      | correlationId | <name><event>.enrollments.[0].correlationId |
      | consumerId    | <name>.consumerId                           |
      | status        | FAIL                                        |
      | consumerFound | true                                        |
      | errorStack    | No Valid Eligibility                        |

    Examples:
      | name       | ProgramCode | SubProgramTypeCode | CategoryCode | StatusCode | event        |
      | 32272-01-1 | R           | HoosierHealthwise  | 10           | M          | eligibility1 |
      | 32272-01-2 | A           | HoosierCareConnect | 10           | M          | eligibility1 |
      | 32272-01-3 | H           | HealthyIndianaPlan | 4            | M          | eligibility1 |

  @API-CP-32272 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: IN-EB: Assignment Update Create or Update - Inbound Records with Plan Match to Enrollment in System - No Change
  CP-32272 AC 3.0 Inbound Records with “MMIS Requested” Plan and Plan Match to Enrollment in System
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | enrollmentRecordId           | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | enrollmentEndDate            | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | genericFieldText1            | Eligible                |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
      | planCode                     | 455701400               |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | selectionReason              | selectionReason1        |
      | planEndDateReason            | planEndDateReason1      |
    And User provide other enrollment segments details
      | recordId          | 21        |
      | genericFieldText3 | MEDICAL   |
      | genericFieldText1 | O         |
      | genericFieldText2 | A         |
      | segmentTypeCode   | OTHER_TXN |
    And I run create Eligibility and Enrollment API
    When I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | enrollmentRecordId           | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | enrollmentEndDate            | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | planCode                     | 455701400               |
      | planId                       | 33                      |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | genericFieldText1            | Eligible                |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
      | selectionReason              | selectionReason1        |
      | planEndDateReason            | planEndDateReason1      |
    And User provide other enrollment segments details
      | recordId          | 21        |
      | genericFieldText3 | MEDICAL   |
      | genericFieldText1 | O         |
      | genericFieldText2 | C         |
      | segmentTypeCode   | OTHER_TXN |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | <name>.consumerId     |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | 1stDayofPresentMonth  |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate | 1stDayofPresentMonth |
      | medicalPlanEndDate   | highDate             |
      | enrollmentType       | MEDICAL              |
      | channel              | SYSTEM_INTEGRATION   |
      | txnStatus            | ACCEPTED             |
      | status               | ACCEPTED             |
      | createdBy            | 597                  |
      | updatedBy            | 597                  |
      | updatedOn            | current              |
      | createdOn            | current              |
      | planCode             | 455701400            |
      | selectionReason      | selectionReason1     |
      | planEndDateReason    | planEndDateReason1   |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | <name>.consumerId       |
      | consumerFound | false                   |
      | status        | FAIL                    |
    Examples:
      | name       | programCode | subProgramTypeCode | eligibilityStatusCode |
      | 32272-03-1 | R           | HoosierHealthwise  | M                     |
      | 32272-03-2 | A           | HoosierCareConnect | M                     |
      | 32272-03-3 | H           | HealthyIndianaPlan | M                     |


  @API-CP-32272 @API-CP-32272-04 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: IN-EB Assignment Update Create or Update - at least one "Add" Transaction or one "Change" transaction - Inbound Enrollment Past Date
  CP-32272 AC 4.0 Inbound Records with “MMIS Requested” Plan and No Plan Match to Enrollment in System
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | lastDayofNextMonth   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | lastDayofNextMonth   |
      | txnStatus                    | Accepted             |
      | programCode                  | <ProgramCode>        |
      | subProgramTypeCode           | <SubProgramTypeCode> |
      | eligibilityStatusCode        | <StatusCode>         |
      | categoryCode                 | <CategoryCode>       |
      | coverageCode                 | cc01                 |
      | planCode                     | <planCode1>          |
      | selectionReason              | selection reason     |
      | planEndDateReason            | end reason           |
    And User provide other enrollment segments details
      | recordId          | 21        |
      | genericFieldText3 | MEDICAL   |
      | genericFieldText1 | O         |
      | genericFieldText2 | A         |
      | segmentTypeCode   | OTHER_TXN |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof3MonthsBefore |
      | enrollmentEndDate            | 1stDayofLastMonth     |
      | eligibilityStartDate         | 1stDayof3MonthsBefore |
      | eligibilityEndDate           | 1stDayofLastMonth     |
      | txnStatus                    | Accepted              |
      | programCode                  | <ProgramCode>         |
      | subProgramTypeCode           | <SubProgramTypeCode>  |
      | eligibilityStatusCode        | <StatusCode>          |
      | categoryCode                 | <CategoryCode>        |
      | coverageCode                 | cc01                  |
      | planCode                     | <planCode2>           |
      | selectionReason              | selection reason      |
      | planEndDateReason            | end reason            |
    And User provide other enrollment segments details
      | recordId          | 21        |
      | genericFieldText3 | MEDICAL   |
      | genericFieldText1 | O         |
      | genericFieldText2 | A         |
      | segmentTypeCode   | OTHER_TXN |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | <name>.consumerId       |
      | status        | FAIL                    |
      | consumerFound | false                   |

    Examples:
      | name       | ProgramCode | SubProgramTypeCode | CategoryCode | StatusCode | planCode1 | planCode2 |
      | 32272-04-1 | R           | HoosierHealthwise  | 10           | M          | 400752220 | 700410350 |
      | 32272-04-2 | A           | HoosierCareConnect | 10           | M          | 399243310 | 499254630 |
      | 32272-04-3 | H           | HealthyIndianaPlan | 4            | M          | 455701400 | 755726440 |
