Feature:INEB- Eligibility HIP Updates for MMIS send reconciliation Scenarios


  @API-CP-29124 @API-CP-29124-1.1 @API-CP-29124-1.2 @API-CP-29737-1.1 @API-EE-IN @kursat  @shruti
  Scenario: Inbound Internal Scenario (21) for HIP Consumer (no Eligibility in system)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29124-03 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide dummy enrollment and eligibility information to create enrollment
      | consumerId            | 29124-03.consumerId |
      | isEligibilityRequired | yes                 |
      | isEnrollemntRequired  | no                  |
      | createdBy             | 597                 |
      | recordId              | 21                  |
      | programCode           | H                   |
      | subProgramTypeCode    | HealthyIndianaPlan  |
      | genericFieldText1     | Eligible            |
      | fileSource            | HRECIP              |
    Then I send API call with name "29124-03a" for create Eligibility and Enrollment
    Then I verify hrecip eligibility information by consumerId "29124-03.consumerId" with data
      | programCode           | H        |
      | hipStatus             | Eligible |
      | eligibilityStatusCode | null     |
      | createdOn             | current  |
      | updatedOn             | current  |
      | processInd            | false    |
    Then I will verify business events are generated with data
      | eventName                                | RECONCILIATION                    |
      | externalConsumerId                       | 29124-03.externalConsumerId       |
#      | correlationId                            | 29124-03a.eligibilities[0].coreEligibility.correlationId |
      | channel                                  | SYSTEM_INTEGRATION                |
      | createdBy                                | 597                               |
      | processDate                              | current                           |
      | externalCaseId                           | 29124-03.caseIdentificationNumber |
      | consumerId                               | 29124-03.consumerId               |
      | consumerName                             | 29124-03                          |
      | reconciliationAction                     | No Eligibility Segment            |
      # Business Event Optional Fields
      | eligibilityStartDate                     | null                              |
      | eligibilityEndDate                       | null                              |
      | eligibilityEndReason                     | null                              |
      | eligibilityCategoryCode                  | null                              |
      | eligibilityProgramCode                   | null                              |
      | eligibilitySubProgramCode                | null                              |
      | eligibilityCoverageCode                  | null                              |
      | eligibilityExemptionCode                 | null                              |
      | consumerPopulation                       | null                              |
      | benefitStatus                            | null                              |
      | additionalFileds.noEligSegment.hipStatus | Eligible                          |
    # 1.2 Inbound Internal Scenario (21) for HIP Consumer WITH Eligibility Segments (no Eligibility in system)
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29124-03.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | null                 |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    Then I send API call with name "29124-03b" for create Eligibility and Enrollment
    Then I verify hrecip eligibility information by consumerId "29124-03.consumerId" with data
      | programCode           | H        |
      | hipStatus             | Eligible |
      | eligibilityStatusCode | null     |
      | createdOn             | current  |
      | updatedOn             | current  |
      | processInd            | true     |
    Then I will verify business events are generated with data
      | eventName                                    | RECONCILIATION                    |
      | externalConsumerId                           | 29124-03.externalConsumerId       |
#      | correlationId                                | 29124-03b.eligibilities[0].coreEligibility.correlationId |
      | channel                                      | SYSTEM_INTEGRATION                |
      | createdBy                                    | 597                               |
      | processDate                                  | current                           |
      | externalCaseId                               | 29124-03.caseIdentificationNumber |
      | consumerId                                   | 29124-03.consumerId               |
      | consumerName                                 | 29124-03                          |
      | reconciliationAction                         | Eligibility Created               |
      # Business Event Optional Fields
      | eligibilityStartDate                         | 1stDayofPresentMonth              |
      | eligibilityEndDate                           | highDate                          |
      | eligibilityEndReason                         | null                              |
      | eligibilityCategoryCode                      | 10                                |
      | eligibilityProgramCode                       | MEDICAID                          |
      | eligibilitySubProgramCode                    | HealthyIndianaPlan                |
      | eligibilityCoverageCode                      | cc01                              |
      | eligibilityExemptionCode                     | qwer                              |
      | consumerPopulation                           | HIP-Mandatory                     |
      | benefitStatus                                | Benefit Status Error              |
      | additionalFileds.noMatch.HIP Status          | Eligible                          |
      | additionalFileds.noMatch.Managed Care Status | M                                 |


  @API-CP-29124 @API-CP-29124-1.3 @API-CP-29737-1.3 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario: Inbound Internal Scenario (21) for HIP Consumer WITHOUT Eligibility Segments (Eligibility in system)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29124-04 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29124-04.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Denied               |
      | coverageCode                 | cc01                 |
      | fileSource                   | HRECIP               |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth   |
      | endDate             | highDate               |
      | segmentDetailValue1 | 1                      |
      | segmentDetailValue2 | 6                      |
      | segmentDetailValue3 | 1stDayofPresentMonth:: |
      | segmentDetailValue4 | highDate::             |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | OTHER                  |
    Then I send API call with name "29124-04a" for create Eligibility and Enrollment
    Then I verify hrecip eligibility information by consumerId "29124-04.consumerId" with data
      | empty |[blank]|
    Then I will verify business events are generated with data
      | eventName                                    | RECONCILIATION                                           |
      | externalConsumerId                           | 29124-04.externalConsumerId                              |
      | correlationId                                | 29124-04a.eligibilities[0].coreEligibility.correlationId |
      | channel                                      | SYSTEM_INTEGRATION                                       |
      | createdBy                                    | 597                                                      |
      | processDate                                  | current                                                  |
      | externalCaseId                               | 29124-04.caseIdentificationNumber                        |
      | consumerId                                   | 29124-04.consumerId                                      |
      | consumerName                                 | 29124-04                                                 |
      | reconciliationAction                         | Eligibility Created                                      |
      # Business Event Optional Fields
      | eligibilityStartDate                         | 1stDayofPresentMonth                                     |
      | eligibilityEndDate                           | highDate                                                 |
      | eligibilityEndReason                         | null                                                     |
      | eligibilityCategoryCode                      | 10                                                       |
      | eligibilityProgramCode                       | MEDICAID                                                 |
      | eligibilitySubProgramCode                    | HealthyIndianaPlan                                       |
      | eligibilityCoverageCode                      | cc01                                                     |
      | eligibilityExemptionCode                     | qwer                                                     |
      | consumerPopulation                           | HIP-Mandatory                                            |
      | benefitStatus                                | Benefit Status Error                                     |
      | additionalFileds.noMatch.HIP Status          | Denied                                                   |
      | additionalFileds.noMatch.Managed Care Status | M                                                        |
    Given I initiated Eligibility and Enrollment Create API
    And I provide dummy enrollment and eligibility information to create enrollment
      | consumerId            | 29124-04.consumerId |
      | isEligibilityRequired | yes                 |
      | isEnrollemntRequired  | no                  |
      | createdBy             | 597                 |
      | recordId              | 21                  |
      | programCode           | H                   |
      | subProgramTypeCode    | HealthyIndianaPlan  |
      | genericFieldText1     | Eligible            |
      | fileSource            | HRECIP              |
    Then I send API call with name "29124-04b" for create Eligibility and Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29124-04.consumerId  |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | enrollmentRecordId           | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | M                    |
      | planCode                     | 455701400            |
      | planId                       | 104                  |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | rejectionReason              | test1                |
    And I run create Eligibility and Enrollment API
    Then I verify hrecip eligibility information by consumerId "29124-04.consumerId" with data
      | empty |[blank]|
    And I initiated get benefit status by consumer id "29124-04.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Eligibility Updated","New Retroactive Enrollment"
    Then I Verify Consumer Actions as following data
      | [0].action               | Available                   |
      | [0].consumerAction       | Plan Change Open Enrollment |
      | [0].planSelectionAllowed | true                        |
    Then I will verify business events are generated with data
      | eventName                                               | RECONCILIATION                                           |
      | externalConsumerId                                      | 29124-04.externalConsumerId                              |
      | correlationId                                           | 29124-04b.eligibilities[0].coreEligibility.correlationId |
      | channel                                                 | SYSTEM_INTEGRATION                                       |
      | createdBy                                               | 597                                                      |
      | processDate                                             | current                                                  |
      | externalCaseId                                          | 29124-04.caseIdentificationNumber                        |
      | consumerId                                              | 29124-04.consumerId                                      |
      | consumerName                                            | 29124-04                                                 |
      | reconciliationAction                                    | Eligibility Updated                                      |
      | consumerPopulation                                      | HIP-Mandatory                                            |
      # Business Event Optional Fields
      | oldValue                                                | null                                                     |
      | newValue.eligibilityStartDate                           | 1stDayofPresentMonth                                     |
      | newValue.eligibilityEndDate                             | highDate                                                 |
      | newValue.eligibilityEndReason                           | null                                                     |
      | newValue.eligibilityCategoryCode                        | 10                                                       |
      | newValue.eligibilityProgramCode                         | MEDICAID                                                 |
      | newValue.eligibilitySubProgramCode                      | HealthyIndianaPlan                                       |
      | newValue.eligibilityCoverageCode                        | cc01                                                     |
      | newValue.eligibilityExemptionCode                       | qwer                                                     |
      | newValue.benefitStatus                                  | Benefit Status Error                                     |
      | additionalFileds.eligibilityUpdated.HIP Status          | Eligible                                                 |
      | additionalFileds.eligibilityUpdated.Managed Care Status | M                                                        |


  @API-CP-29124 @API-CP-29124-3 @API-CP-29737-03 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: First Eligibility Reconciliation Data Check
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                       |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | isEnrollmentProviderRequired | no                       |
      | enrollmentStartDate          | 1stDayofPresentMonth     |
      | eligibilityStartDate         | <eligibilityStartDate1>  |
      | eligibilityEndDate           | <eligibilityEndDate1>    |
      | txnStatus                    | Accepted                 |
      | programCode                  | H                        |
      | subProgramTypeCode           | HealthyIndianaPlan       |
      | eligibilityStatusCode        | <eligibilityStatusCode1> |
      | genericFieldText1            | <hipStatus1>             |
      | categoryCode                 | 10                       |
      | coverageCode                 | cc01                     |
      | fileSource                   | RECIPIENT_HRECIP         |
    And I run create Eligibility and Enrollment API
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
      | planId                       | 104                     |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | rejectionReason              | test1                   |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                       |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | isEnrollmentProviderRequired | no                       |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | <eligibilityStartDate2>  |
      | eligibilityEndDate           | <eligibilityEndDate2>    |
      | programCode                  | H                        |
      | subProgramTypeCode           | HealthyIndianaPlan       |
      | eligibilityStatusCode        | <eligibilityStatusCode2> |
      | categoryCode                 | 10                       |
      | genericFieldText1            | <hipStatus2>             |
      | coverageCode                 | cc01                     |
      | fileSource                   | RECIPIENT_HRECIP         |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth      |
      | endDate             | highDate                  |
      | segmentDetailValue1 | 1                         |
      | segmentDetailValue2 | 6                         |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | OTHER                     |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    Then I verify hrecip eligibility information by consumerId "<name>.consumerId" with data
      | empty |[blank]|
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "<eligibilityScenario>","<enrollmentScenario>"
    #CP-29737 does not have reconciliation event for this scenario
#    Then I will verify business events are generated with data
#    # 1.0 Business Event Required Fields
#      | eventName                                       | RECONCILIATION                                         |
#      | consumerId                                      | <name>.consumerId                                      |
#      | correlationId                                   | <name>a.eligibilities[0].coreEligibility.correlationId |
#      | channel                                         | SYSTEM_INTEGRATION                                     |
#      | createdBy                                       | 597                                                    |
#      | processDate                                     | current                                                |
#      | externalCaseId                                  | <name>.caseIdentificationNumber                        |
#      | externalConsumerId                              | <name>.externalConsumerId                              |
#      | consumerName                                    | <name>                                                 |
#      | consumerPopulation                              | HIP-Mandatory                                          |
#    # 2.0 Business Event Optional Eligibility Fields: No Update
#      | reconciliationAction                            | <reconciliationAction>                                 |
#      | eligibilityStartDate                            | 1stDayofPresentMonth                                   |
#      | eligibilityEndDate                              | highDate                                               |
#      | eligibilityEndReason                            | null                                                   |
#      | eligibilityCategoryCode                         | 10                                                     |
#      | eligibilityProgramCode                          | MEDICAID                                               |
#      | eligibilitySubProgramCode                       | HealthyIndianaPlan                                     |
#      | eligibilityCoverageCode                         | cc01                                                   |
#      | eligibilityExemptionCode                        | qwer                                                   |
#      | benefitStatus                                   | Enrolled                                               |
#    # 6.0 Business Event Optional IN-EB Tenant-Specific Fields
#      | additionalFileds.exactMatch.Managed Care Status | M                                                      |
#      | additionalFileds.exactMatch.HIP Status          | <hipStatus2>                                           |
    Examples:
      | name     | hipStatus1 | hipStatus2 | eligibilityScenario | enrollmentScenario         | eligibilityStartDate1 | eligibilityStartDate2 | eligibilityEndDate1 | eligibilityEndDate2 | eligibilityStatusCode1 | eligibilityStatusCode2 | reconciliationAction  |
      | 29124-06 | Eligible   | Denied     | EXACT MATCH         | New Retroactive Enrollment | 1stDayofPresentMonth  | 1stDayofPresentMonth  | highDate            | highDate            | M                      | M                      | No Eligibility Update |
      | 29124-07 | Eligible   | Eligible   | CHANGED START DATE  | New Retroactive Enrollment | 1stDayofPresentMonth  | 1stDayofLastMonth     | highDate            | highDate            | M                      | X                      | Eligibility Updated   |


  @API-CP-29736 @API-CP-29736-01 @API-CP-29737-04 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario Outline: IN-EB ELIGIBILITY HIP Updates MMIS Sends Reconciliation (Part 2 - Create or Update) 1.0 2.0 8.0
  # 1.0 Create New Eligibility Segment from First Eligibility Segment
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | programCode                  | H                     |
      | subProgramTypeCode           | HealthyIndianaPlan    |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
      | genericFieldText1            | Denied                |
      | fileSource                   | HRECIP                |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore     |
      | endDate             | lastDayOfThePresentYear   |
      | segmentDetailValue1 | 1                         |
      | segmentDetailValue2 | 6                         |
      | segmentDetailValue3 | 1stDayof2MonthsBefore::   |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentTypeCode     | OTHER                     |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And Wait for 5 seconds
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | programCode           | H                     |
      | subProgramTypeCode    | HealthyIndianaPlan    |
      | coverageCode          | cc01                  |
      | eligibilityStatusCode | M                     |
      | exemptionCode         | qwer                  |
      | eligibilityStartDate  | 1stDayof2MonthsBefore |
      | eligibilityEndDate    | highDate              |
      | genericFieldText1     | Denied                |
      | eligibilityStatus     | Active                |
      | Created on            | current               |
      | createdBy             | 597                   |
      | Updated On            | current               |
      | updatedBy             | 597                   |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | benefitStatus       | Benefit Status Error |
      | population          | HIP-Mandatory        |
      | eligibilityScenario | NO MATCH             |
      | timeframe           | Active               |
      | programPopulation   | HIP-Mandatory        |
    # 2.0 Update Matched Eligibility Segment, Single Inbound Eligibility Segment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | V                    |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
      | genericFieldText1            | Eligible             |
      | fileSource                   | <fileSource>         |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth |
      | endDate             | highDate             |
      | segmentDetailValue1 | 10                   |
      | segmentDetailValue2 | 60                   |
      | segmentDetailValue3 | <redetermDate>::     |
      | segmentDetailValue4 | highDate::           |
      | segmentTypeCode     | OTHER                |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | programCode           | H                    |
      | subProgramTypeCode    | HealthyIndianaPlan   |
      | eligibilityStatusCode | V                    |
      | eligibilityStartDate  | 1stDayofPresentMonth |
      | eligibilityEndDate    | highDate             |
      | genericFieldText1     | <HIPstatus>          |
      | Updated On            | current              |
      | updatedBy             | 597                  |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | benefitStatus       | Benefit Status Error |
      | population          | HIP-Voluntary        |
      | eligibilityScenario | CHANGED START DATE   |
      | timeframe           | Active               |
      | programPopulation   | HIP-Voluntary        |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                               |
      | consumerId    | <name>.consumerId                                      |
      | correlationId | <name>a.eligibilities[0].coreEligibility.correlationId |
      | consumerFound | true                                                   |
      | DPBI          |[blank]|
    # 8.0 Update Other Eligibility Data Elements for the Consumer
    Then I verfify other eiigbility segment type "OTHER" details for coma separated consumerIds "<name>.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console      |
      | [0].consumerId                | <name>.consumerId     |
      | [0].segmentTypeCode           | OTHER                 |
      | [0].startDate                 | <startDate>           |
      | [0].endDate                   | <endDate>             |
      | [0].segmentDetailValue1       | <segmentDetailValue1> |
      | [0].segmentDetailValue2       | <segmentDetailValue2> |
      | [0].segmentDetailValue3       | <segmentDetailValue3> |
      | [0].segmentDetailValue4       | <segmentDetailValue4> |
      | [0].segmentDetailValue5       | null                  |
      | [0].segmentDetailValue6       | null                  |
      | [0].segmentDetailValue7       | null                  |
      | [0].segmentDetailValue8       | null                  |
      | [0].segmentDetailValue9       | null                  |
      | [0].segmentDetailValue10      | null                  |
      | [0].createdOn                 | current               |
      | [0].createdBy                 | 597                   |
      | [0].updatedOn                 | current               |
      | [0].updatedBy                 | 597                   |
      | [0].uiid                      | null                  |
    Examples:
      | name    | fileSource       | HIPstatus | redetermDate          | startDate             | endDate                 | segmentDetailValue1 | segmentDetailValue2 | segmentDetailValue3   | segmentDetailValue4     |
      | 29736-1 | HRECIP           | Eligible  | 1stDayofPresentMonth  | 1stDayofPresentMonth  | highDate                | 10                  | 60                  | 1stDayof2MonthsBefore | highDate                |
      | 29736-2 | RECIPIENT        | Denied    | 1stDayofPresentMonth  | 1stDayof2MonthsBefore | lastDayOfThePresentYear | 1                   | 6                   | 1stDayofPresentMonth  | lastDayOfThePresentYear |
      | 29736-3 | RECIPIENT_HRECIP | Eligible  | 1stDayofPresentMonth  | 1stDayof2MonthsBefore | lastDayOfThePresentYear | 1                   | 6                   | 1stDayofPresentMonth  | lastDayOfThePresentYear |
      | 29736-4 | RECIPIENT_HRECIP | Eligible  | 1stDayof2MonthsBefore | 1stDayofPresentMonth  | highDate                | 10                  | 60                  | 1stDayof2MonthsBefore | highDate                |


  #changed as part of CP-41141
  @API-CP-29736 @API-CP-29736-02 @API-CP-29737-05 @API-CP-41141 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario Outline: IN-EB ELIGIBILITY HIP Updates MMIS Sends Reconciliation (Part 2 - Create or Update) 1.0 3.0 8.0
  # 1.0 Create New Eligibility Segment from First Eligibility Segment
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | programCode                  | H                     |
      | subProgramTypeCode           | HealthyIndianaPlan    |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
      | genericFieldText1            | Denied                |
      | fileSource                   | HRECIP                |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore     |
      | endDate             | lastDayOfThePresentYear   |
      | segmentDetailValue1 | 10                        |
      | segmentDetailValue2 | 6                         |
      | segmentDetailValue3 | 1stDayof2MonthsBefore::   |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentTypeCode     | OTHER                     |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | programCode           | H                     |
      | subProgramTypeCode    | HealthyIndianaPlan    |
      | coverageCode          | cc01                  |
      | eligibilityStatusCode | M                     |
      | exemptionCode         | qwer                  |
      | eligibilityStartDate  | 1stDayof2MonthsBefore |
      | eligibilityEndDate    | highDate              |
      | genericFieldText1     | Denied                |
      | eligibilityStatus     | Active                |
      | Created on            | current               |
      | createdBy             | 597                   |
      | Updated On            | current               |
      | updatedBy             | 597                   |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | benefitStatus       | Benefit Status Error |
      | population          | HIP-Mandatory        |
      | eligibilityScenario | NO MATCH             |
      | timeframe           | Active               |
      | programPopulation   | HIP-Mandatory        |
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable |
      | [0].consumerAction       | null        |
      | [0].planSelectionAllowed | false       |
    # 3.0 Update Matched Eligibility Segment, Two Inbound Eligibility Segments, Program Change
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId  |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 21                 |
      | eligibilityRecordId          | 21                 |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth  |
      | eligibilityEndDate           | highDate           |
      | programCode                  | A                  |
      | subProgramTypeCode           | HoosierCareConnect |
      | eligibilityStatusCode        | V                  |
      | categoryCode                 | 4                  |
      | coverageCode                 | cc01               |
    And User provide other eligibility segments details
      | startDate           | 1stDayofNextMonth |
      | endDate             | highDate          |
      | segmentDetailValue1 | 10                |
      | segmentDetailValue2 | 6                 |
      | segmentDetailValue3 | <redetermDate>::  |
      | segmentDetailValue4 | highDate::        |
      | segmentTypeCode     | OTHER             |
    And User provide one more eligibility details
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | lastDayofPresentMonth |
      | programCode                  | H                     |
      | subProgramTypeCode           | HealthyIndianaPlan    |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
      | genericFieldText1            | Eligible              |
      | fileSource                   | <fileSource>          |
    And User provide other enrollment segments details
      | recordId          | 21                                    |
      | consumerId        | <name>.consumerId                     |
      | startDate         | 1stDayofPresentMonth                  |
      | endDate           | 60DaysFromFirstDayOfMonth             |
      | genericFieldText1 | O                                     |
      | genericFieldDate1 | nextDayAfter60DaysFromFirstDayOfMonth |
      | segmentTypeCode   | OPEN_ENROLLMENT                       |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And Wait for 5 seconds
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | [0].programCode           | H                     |
      | [0].subProgramTypeCode    | HealthyIndianaPlan    |
      | [0].eligibilityStatusCode | M                     |
      | [0].eligibilityStartDate  | 1stDayof2MonthsBefore |
      | [0].eligibilityEndDate    | lastDayofPresentMonth |
      | [0].genericFieldText1     | <HIPstatus>           |
      | [0].Updated On            | current               |
      | [0].updatedBy             | 597                   |
      | [1].programCode           | A                     |
      | [1].subProgramTypeCode    | HoosierCareConnect    |
      | [1].eligibilityStatusCode | V                     |
      | [1].eligibilityStartDate  | 1stDayofNextMonth     |
      | [1].eligibilityEndDate    | highDate              |
#      | [1].genericFieldText1     | <HIPstatus>        |
      | [1].Updated On            | current               |
      | [1].updatedBy             | 597                   |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | benefitStatus       | Eligible                                    |
      | population          | HCC-Voluntary                               |
      | eligibilityScenario | Program Sub-Program Change With Date Change |
      | timeframe           | FUTURE                                      |
      | programPopulation   | HCC-Voluntary                               |
#      | [1].benefitStatus       | Eligible                                    |
#      | [1].population          | HIP-Mandatary                               |
#      | [1].eligibilityScenario | Program Sub-Program Change With Date Change |
#      | [1].timeframe           | FUTURE                                      |
#      | [1].programPopulation   | HCC-Voluntary                               |
    Then I Verify Consumer Actions as following data
      | [0].action               | Required |
      | [0].consumerAction       | Enroll   |
      | [0].planSelectionAllowed | true     |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT |
      | consumerId    | <name>.consumerId        |
#      | correlationId | <name>a.eligibilities[0].coreEligibility.correlationId |
      | consumerFound | true                     |
      | DPBI          |[blank]|
    # 8.0 Update Other Eligibility Data Elements for the Consumer
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "<name>.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console        |
      | [0].consumerId                | <name>.consumerId       |
      | [0].segmentTypeCode           | OTHER                   |
      | [0].startDate                 | 1stDayof2MonthsBefore   |
      | [0].endDate                   | lastDayOfThePresentYear |
      | [0].segmentDetailValue1       | <segmentDetailValue1>   |
      | [0].segmentDetailValue2       | <segmentDetailValue2>   |
      | [0].segmentDetailValue3       | <segmentDetailValue3>   |
      | [0].segmentDetailValue4       | <segmentDetailValue4>   |
      | [0].segmentDetailValue5       | null                    |
      | [0].segmentDetailValue6       | null                    |
      | [0].segmentDetailValue7       | null                    |
      | [0].segmentDetailValue8       | null                    |
      | [0].segmentDetailValue9       | null                    |
      | [0].segmentDetailValue10      | null                    |
      | [0].createdOn                 | current                 |
      | [0].createdBy                 | 597                     |
#      | [0].updatedOn                 | current                 |
#      | [0].updatedBy                 | 597                     |
      | [0].uiid                      | null                    |
    Then I will verify business events are generated with data
     # 1.0 Business Event Required Fields
      | eventName                                                                          | RECONCILIATION                                          |
      | consumerId                                                                         | <name>.consumerId                                       |
      | correlationId                                                                      | <name>a.eligibilities.[0].coreEligibility.correlationId |
      | channel                                                                            | SYSTEM_INTEGRATION                                      |
      | createdBy                                                                          | 597                                                     |
      | processDate                                                                        | current                                                 |
      | externalCaseId                                                                     | <name>.caseIdentificationNumber                         |
      | externalConsumerId                                                                 | <name>.externalConsumerId                               |
      | consumerName                                                                       | <name>                                                  |
      | consumerPopulation                                                                 | HCC-Voluntary                                           |
    # 4.0 Business Event Optional Eligibility Fields: Update Eligibility Segment
      | reconciliationAction                                                               | Program Change                                          |
      | priorSegment.eligibilityProgramCode                                                | MEDICAID                                                |
      | priorSegment.eligibilitySubProgramCode                                             | HealthyIndianaPlan                                      |
      | priorSegment.eligibilityStartDate                                                  | 1stDayof2MonthsBefore                                   |
      | priorSegment.eligibilityEndDate                                                    | lastDayofPresentMonth                                   |
      | priorSegment.eligibilityEndReason                                                  | null                                                    |
      | priorSegment.eligibilityCategoryCode                                               | 10                                                      |
      | priorSegment.eligibilityCoverageCode                                               | cc01                                                    |
      | priorSegment.eligibilityExemptionCode                                              | qwer                                                    |
      | priorSegment.benefitStatus                                                         | Benefit Status Error                                    |
      | newSegment.eligibilityProgramCode                                                  | MEDICAID                                                |
      | newSegment.eligibilitySubProgramCode                                               | HoosierCareConnect                                      |
      | newSegment.eligibilityStartDate                                                    | 1stDayofNextMonth                                       |
      | newSegment.eligibilityEndDate                                                      | highDate                                                |
      | newSegment.eligibilityEndReason                                                    | null                                                    |
      | newSegment.eligibilityCategoryCode                                                 | 4                                                       |
      | newSegment.eligibilityCoverageCode                                                 | cc01                                                    |
      | newSegment.eligibilityExemptionCode                                                | qwer                                                    |
      | newSegment.benefitStatus                                                           | Eligible                                                |
    # 6.0 Business Event Optional IN-EB Tenant-Specific Fields
      | priorSegment.additionalFileds.programChange.Managed Care Status                    | M                                                       |
      | priorSegment.additionalFileds.programChange.HIP Status                             | Denied                                                  |
      | newSegment.additionalFileds.eligibilityUpdated.Managed Care Status                 | V                                                       |
      | newSegment.additionalFileds.eligibilityUpdated.HIP Status                          | Denied                                                  |

      | newSegment.additionalFileds.OtherSegmentInfos.Open Enrollment Effective Date       | 1stDayofPresentMonth                                    |
      | newSegment.additionalFileds.OtherSegmentInfos.Open Enrollment End Date             | 60DaysFromFirstDayOfMonth                               |
      | newSegment.additionalFileds.OtherSegmentInfos.Open Enrollment Status               | O                                                       |
      | newSegment.additionalFileds.OtherSegmentInfos.Open Enrollment Lockin Complete Date | nextDayAfter60DaysFromFirstDayOfMonth                   |
      | newSegment.additionalFileds.OtherSegmentInfos.Redetermination Date                 | <segmentDetailValue3>                                   |
      | newSegment.additionalFileds.OtherSegmentInfos.Debt Indicator                       | <segmentDetailValue1>                                   |
      | newSegment.additionalFileds.OtherSegmentInfos.Conditional Eligibility Date         | lastDayOfThePresentYear                                 |
      | newSegment.additionalFileds.OtherSegmentInfos.Benefit Effective Date               | 1stDayof2MonthsBefore                                   |
      | newSegment.additionalFileds.OtherSegmentInfos.Benefit End Date                     | lastDayOfThePresentYear                                 |
      | newSegment.additionalFileds.OtherSegmentInfos.Potential Plus Indicator             | <segmentDetailValue2>                                   |
    Examples:
      | name    | fileSource       | HIPstatus | redetermDate          | startDate             | endDate                 | segmentDetailValue1 | segmentDetailValue2 | segmentDetailValue3   | segmentDetailValue4     |
      | 29736-5 | HRECIP           | Eligible  | 1stDayofNextMonth     | 1stDayofNextMonth     | highDate                | 10                  | 6                   | 1stDayof2MonthsBefore | lastDayOfThePresentYear |
      | 29736-6 | RECIPIENT        | Denied    | 1stDayofNextMonth     | 1stDayof2MonthsBefore | lastDayOfThePresentYear | 10                  | 6                   | 1stDayof2MonthsBefore | lastDayOfThePresentYear |
      | 29736-7 | RECIPIENT_HRECIP | Eligible  | 1stDayofNextMonth     | 1stDayof2MonthsBefore | lastDayOfThePresentYear | 10                  | 6                   | 1stDayof2MonthsBefore | lastDayOfThePresentYear |
      | 29736-8 | RECIPIENT_HRECIP | Eligible  | 1stDayof2MonthsBefore | 1stDayofNextMonth     | highDate                | 10                  | 6                   | 1stDayof2MonthsBefore | lastDayOfThePresentYear |

  @API-CP-29751 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario Outline: IN-EB Decide Consumer Actions - 2nd Update to logic for HIP Open Enrollment
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | programCode                  | H                       |
      | subProgramTypeCode           | HealthyIndianaPlan      |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
      | genericFieldText1            | <HIPstatus>             |
      | fileSource                   | <fileSource>            |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth      |
      | endDate             | lastDayOfThePresentYear   |
      | segmentDetailValue1 | 1                         |
      | segmentDetailValue2 | 6                         |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentTypeCode     | OTHER                     |
    And I run create Eligibility and Enrollment API
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
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | planCode                     | 455701400               |
      | planId                       | 104                     |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | rejectionReason              | test1                   |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "NO MATCH","New Retroactive Enrollment"
    Then I Verify Consumer Actions as following data
      | [0].action               | Available                   |
      | [0].consumerAction       | Plan Change Open Enrollment |
      | [0].planSelectionAllowed | true                        |
    Examples:
      | name    | fileSource       | HIPstatus     | eligibilityStatusCode |
      | 29751-1 | HRECIP           | Eligible      | M                     |
      | 29751-2 | HRECIP           | PotentialPlus | V                     |
      | 29751-3 | RECIPIENT_HRECIP | Eligible      | M                     |
      | 29751-4 | RECIPIENT_HRECIP | PotentialPlus | V                     |
      | 29751-5 | RECIPIENT        | null          | M                     |

  @API-CP-29751 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario Outline: IN-EB Decide Consumer Actions - 2nd Update to logic for HIP Open Enrollment (negative)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | programCode                  | H                       |
      | subProgramTypeCode           | HealthyIndianaPlan      |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
      | genericFieldText1            | <HIPstatus>             |
      | fileSource                   | <fileSource>            |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth      |
      | endDate             | lastDayOfThePresentYear   |
      | segmentDetailValue1 | 1                         |
      | segmentDetailValue2 | 6                         |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentTypeCode     | OTHER                     |
    And I run create Eligibility and Enrollment API
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
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | planCode                     | 455701400               |
      | planId                       | 104                     |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | rejectionReason              | test1                   |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "NO MATCH","New Retroactive Enrollment"
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable |
      | [0].consumerAction       | null        |
      | [0].planSelectionAllowed | false       |
    Examples:
      | name    | fileSource       | HIPstatus | eligibilityStatusCode |
      | 29751-6 | HRECIP           | Denied    | M                     |
      | 29751-7 | RECIPIENT_HRECIP | Denied    | V                     |

  @API-CP-29737 @API-CP-29737-1.1 @API-CP-30695 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: Verify Recon Business Event is triggered when No Eligibility Segment is available
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><eligibilityName1> |
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                       |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | isEnrollmentProviderRequired | no                       |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | null                     |
      | eligibilityEndDate           | null                     |
      | programCode                  | H                        |
      | subProgramTypeCode           | HealthyIndianaPlan       |
      | eligibilityStatusCode        | M                        |
      | categoryCode                 | 10                       |
      | genericFieldText1            | <genericFieldText1-1>    |
      | coverageCode                 | cc01                     |
      | fileSource                   | <fileSource1>            |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth      |
      | endDate             | highDate                  |
      | segmentDetailValue1 | 1                         |
      | segmentDetailValue2 | 6                         |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | OTHER                     |
    And I run create Eligibility and Enrollment API
    Then I will verify business events are generated with data
    # 1.0 Business Event Required Fields
      | eventName                 | RECONCILIATION                                                           |
      | consumerId                | <name>.consumerId                                                        |
      | correlationId             | <name><eligibilityName1>.eligibilities.[0].coreEligibility.correlationId |
      | channel                   | SYSTEM_INTEGRATION                                                       |
      | createdBy                 | 597                                                                      |
      | processDate               | current                                                                  |
      | externalCaseId            | <name>.caseIdentificationNumber                                          |
      | externalConsumerId        | <name>.externalConsumerId                                                |
      | consumerName              | <name>                                                                   |
      | consumerPopulation        | null                                                                     |
    # 2.0 Business Event Optional Eligibility Fields: No Update
      | reconciliationAction      | No Eligibility Segment                                                   |
      | eligibilityStartDate      | null                                                                     |
      | eligibilityEndDate        | null                                                                     |
      | eligibilityEndReason      | null                                                                     |
      | eligibilityCategoryCode   | null                                                                     |
      | eligibilityProgramCode    | null                                                                     |
      | eligibilitySubProgramCode | null                                                                     |
      | eligibilityCoverageCode   | null                                                                     |
      | eligibilityExemptionCode  | null                                                                     |
      | benefitStatus             | null                                                                     |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><eligibilityName2> |
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                       |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | isEnrollmentProviderRequired | no                       |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | highDate                 |
      | programCode                  | H                        |
      | subProgramTypeCode           | HealthyIndianaPlan       |
      | eligibilityStatusCode        | M                        |
      | categoryCode                 | 10                       |
      | genericFieldText1            | <genericFieldText1-2>    |
      | coverageCode                 | cc01                     |
      | fileSource                   | <fileSource2>            |
    And User provide other eligibility segments details
      | startDate           | firstDayOfNextYear        |
      | endDate             | highDate                  |
      | segmentDetailValue1 | 1                         |
      | segmentDetailValue2 | 6                         |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | OTHER                     |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Then I will verify business events are generated with data
    # 1.0 Business Event Required Fields
      | eventName                                    | RECONCILIATION                                                           |
      | consumerId                                   | <name>.consumerId                                                        |
      | correlationId                                | <name><eligibilityName2>.eligibilities.[0].coreEligibility.correlationId |
      | channel                                      | SYSTEM_INTEGRATION                                                       |
      | createdBy                                    | 597                                                                      |
      | processDate                                  | current                                                                  |
      | externalCaseId                               | <name>.caseIdentificationNumber                                          |
      | externalConsumerId                           | <name>.externalConsumerId                                                |
      | consumerName                                 | <name>                                                                   |
      | consumerPopulation                           | HIP-Mandatory                                                            |
    # 2.0 Business Event Optional Eligibility Fields: No Update
      | reconciliationAction                         | Eligibility Created                                                      |
      | eligibilityStartDate                         | 1stDayofPresentMonth                                                     |
      | eligibilityEndDate                           | highDate                                                                 |
      | eligibilityEndReason                         | null                                                                     |
      | eligibilityCategoryCode                      | 10                                                                       |
      | eligibilityProgramCode                       | MEDICAID                                                                 |
      | eligibilitySubProgramCode                    | HealthyIndianaPlan                                                       |
      | eligibilityCoverageCode                      | cc01                                                                     |
      | eligibilityExemptionCode                     | qwer                                                                     |
      | benefitStatus                                | Benefit Status Error                                                     |
    # 6.0 Business Event Optional IN-EB Tenant-Specific Fields
      | additionalFileds.noMatch.Managed Care Status | M                                                                        |
      | additionalFileds.noMatch.HIP Status          | <hipStatus>                                                              |
    Examples:
      | name     | hipStatus | genericFieldText1-1 | fileSource1 | genericFieldText1-2 | fileSource2 | eligibilityName1 | eligibilityName2 |
      | 29737-01 | null      | null                | HRECIP      | null                | RECIPIENT   | eligilibility-1  | eligibility-2    |
      | 29737-02 | null      | null                | HRECIP      | Eligible            | RECIPIENT   | eligilibility-1  | eligibility-2    |
      | 29737-03 | Eligible  | Eligible            | HRECIP      | null                | RECIPIENT   | eligilibility-1  | eligibility-2    |


  @API-CP-29737 @API-CP-29737-1.3 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: Verify Recon Eligibility Updated when HIP Status is changed
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | <hipStatus>          |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    And User provide other eligibility segments details
      | startDate           | firstDayOfNextYear        |
      | endDate             | highDate                  |
      | segmentDetailValue1 | 1                         |
      | segmentDetailValue2 | 6                         |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | OTHER                     |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId  |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 21                 |
      | eligibilityRecordId          | 21                 |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | null               |
      | eligibilityEndDate           | null               |
      | programCode                  | H                  |
      | subProgramTypeCode           | HealthyIndianaPlan |
      | eligibilityStatusCode        | M                  |
      | categoryCode                 | 10                 |
      | genericFieldText1            | <hipStatus2>       |
      | coverageCode                 | cc01               |
      | fileSource                   | HRECIP             |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth      |
      | endDate             | highDate                  |
      | segmentDetailValue1 | 1                         |
      | segmentDetailValue2 | 6                         |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | OTHER                     |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    Then I will verify business events are generated with data
    # 1.0 Business Event Required Fields
      | eventName                                                        | RECONCILIATION                                          |
      | consumerId                                                       | <name>.consumerId                                       |
      | correlationId                                                    | <name>a.eligibilities.[0].coreEligibility.correlationId |
      | channel                                                          | SYSTEM_INTEGRATION                                      |
      | createdBy                                                        | 597                                                     |
      | processDate                                                      | current                                                 |
      | externalCaseId                                                   | <name>.caseIdentificationNumber                         |
      | externalConsumerId                                               | <name>.externalConsumerId                               |
      | consumerName                                                     | <name>                                                  |
      | consumerPopulation                                               | HIP-Mandatory                                           |
    # 2.0 Business Event Optional Eligibility Fields: No Update
      | oldValue                                                         | null                                                    |
      | newValue.reconciliationAction                                    | Eligibility Updated                                     |
      | newValue.eligibilityStartDate                                    | 1stDayofPresentMonth                                    |
      | newValue.eligibilityEndDate                                      | highDate                                                |
      | newValue.eligibilityEndReason                                    | null                                                    |
      | newValue.eligibilityCategoryCode                                 | 10                                                      |
      | newValue.eligibilityProgramCode                                  | MEDICAID                                                |
      | newValue.eligibilitySubProgramCode                               | HealthyIndianaPlan                                      |
      | newValue.eligibilityCoverageCode                                 | cc01                                                    |
      | newValue.eligibilityExemptionCode                                | qwer                                                    |
      | newValue.benefitStatus                                           | Benefit Status Error                                    |
    # 6.0 Business Event Optional IN-EB Tenant-Specific Fields
      | newValue.additionalFileds.eligibilityUpdated.Managed Care Status | M                                                       |
      | newValue.additionalFileds.eligibilityUpdated.HIP Status          | <hipStatus2>                                            |
    Examples:
      | name     | hipStatus2    | hipStatus |
      | 29737-04 | PotentialPlus | Eligible  |
      | 29737-05 | null          | Eligible  |



