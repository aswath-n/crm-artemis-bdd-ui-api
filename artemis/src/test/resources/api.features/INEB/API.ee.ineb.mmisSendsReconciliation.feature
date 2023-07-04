@reconineb
Feature:INEB- MMIS send reconciliation Scenarios for Eligiblity & Enrollment Scenario ID 21


  @API-CP-27487 @API-CP-24288 @API-CP-24289 @API-CP-24289-01 @API-EE-IN @IN-EB-API-Regression
  Scenario Outline: Verify Recon scenario for Eligibility INEB HHW exact Match and Changed Start Date
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 3                       |
      | eligibilityRecordId          | 3                       |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId            |
      | isEligibilityRequired        | yes                          |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                           |
      | recordId                     | 21                           |
      | eligibilityRecordId          | 21                           |
      | isEnrollmentProviderRequired | no                           |
      | enrollmentStartDate          | 1stDayofPresentMonth         |
      | eligibilityStartDate         | <reconEligibilityStartDate>  |
      | eligibilityEndDate           | <reconEligibilityEndDate>    |
      | txnStatus                    | Accepted                     |
      | programCode                  | <reconProgramCode>           |
      | subProgramTypeCode           | <reconSubProgramTypeCode>    |
      | eligibilityStatusCode        | <reconEligibilityStatusCode> |
      | categoryCode                 | <reconCategoryCode>          |
      | coverageCode                 | cc01                         |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth      |
      | endDate             | lastDayOfThePresentYear   |
      | segmentDetailValue1 | 1                         |
      | segmentDetailValue2 | 6                         |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentTypeCode     | OTHER                     |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer "<name>.consumerId"
    Then I verify scenario output in the benefit status records are displayed as "<benefitStatus>","null"
    # 4.0 Update Matched Eligibility Segment, Two Inbound Eligibility Segments, Aid-Category Change or Add
    Then I verify benefit status information with data
      | programPopulation | <population> |
      | benefitStatus     | Eligible     |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action> |
      | [0].consumerAction       | Enroll   |
      | [0].planSelectionAllowed | true     |
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | categoryCode          | 10                           |
      | programCode           | <reconProgramCode>           |
      | subProgramTypeCode    | <reconSubProgramTypeCode>    |
      | coverageCode          | cc01                         |
      | eligibilityStatusCode | <reconEligibilityStatusCode> |
      | exemptionCode         | qwer                         |
      | eligibilityStartDate  | <reconEligibilityStartDate>  |
      | eligibilityEndDate    | highDate                     |
      | createdOn             | current                      |
      | createdBy             | 597                          |
      | updatedOn             | current                      |
      | updatedBy             | 597                          |
    # CP-27487 Other Eligibility Segment Get API
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "<name>.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console        |
      | [0].consumerId                | <name>.consumerId       |
      | [0].segmentTypeCode           | OTHER                   |
      | [0].startDate                 | 1stDayofPresentMonth    |
      | [0].endDate                   | lastDayOfThePresentYear |
      | [0].segmentDetailValue1       | 1                       |
      | [0].segmentDetailValue2       | 6                       |
      | [0].segmentDetailValue3       | 1stDayofPresentMonth    |
      | [0].segmentDetailValue4       | lastDayOfThePresentYear |
      | [0].segmentDetailValue5       | null                    |
      | [0].segmentDetailValue6       | null                    |
      | [0].segmentDetailValue7       | null                    |
      | [0].segmentDetailValue8       | null                    |
      | [0].segmentDetailValue9       | null                    |
      | [0].segmentDetailValue10      | null                    |
      | [0].createdOn                 | current                 |
      | [0].createdBy                 | 597                     |
      | [0].updatedOn                 | null                    |
      | [0].updatedBy                 | null                    |
      | [0].uiid                      | null                    |

    Examples:
      | name       | reconEligibilityStartDate | reconEligibilityEndDate | benefitStatus | programCode | reconProgramCode | subProgramTypeCode | reconSubProgramTypeCode | reconCategoryCode | eligibilityStatusCode | reconEligibilityStatusCode | population    | action   |
      | 24289-01-1 | 1stDayofPresentMonth      | highDate                | EXACT MATCH   | R           | R                | HoosierHealthwise  | HoosierHealthwise       | 10                | M                     | V                          | HHW-Voluntary | Required |
#      | 24289-01-3 | 1stDayofPresentMonth      | highDate                | Different Program / Sub-Program | R           | A                | HoosierHealthwise  | HoosierCareConnect      | 10                | M                     | M                          | HHW-Voluntary | Required |
#      | 24289-01-4 | 1stDayofPresentMonth      | highDate                | Different Aid Category          | A           | A                | HoosierCareConnect | HoosierCareConnect      | 4                 | M                     | V                          | HHW-Voluntary | Required |


  @API-CP-27487 @API-CP-24288 @API-CP-24289 @API-CP-24289-01 @API-EE-IN
  Scenario: Verify Recon scenario for Eligibility INEB HHW exact Match and Changed Start Date (Excluded)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 24289-01-5 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 24289-01-5.consumerId |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayofPresentMonth  |
      | eligibilityStartDate         | 1stDayofPresentMonth  |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
      | genericFieldText1            | Eligible              |
      | fileSource                   | RECIPIENT_HRECIP      |
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
      | consumerId                   | 24289-01-5.consumerId |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayofNextMonth     |
      | eligibilityStartDate         | 1stDayofNextMonth     |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | X                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
      | genericFieldText1            | Eligible              |
      | fileSource                   | RECIPIENT_HRECIP      |
    And User provide other eligibility segments details
      | startDate           | 1stDayofNextMonth      |
      | endDate             | highDate               |
      | segmentDetailValue1 | 10                     |
      | segmentDetailValue2 | 60                     |
      | segmentDetailValue3 | 1stDayofPresentMonth:: |
      | segmentDetailValue4 | highDate::             |
      | segmentTypeCode     | OTHER                  |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "24289-01-5.consumerId"
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer "24289-01-5.consumerId"
    Then I verify scenario output in the benefit status records are displayed as "CHANGED START DATE","null"
    # 4.0 Update Matched Eligibility Segment, Two Inbound Eligibility Segments, Aid-Category Change or Add
    Then I verify latest benefit status information with data
      | programPopulation | HHW-Mandatory |
      | benefitStatus     | Eligible      |
    Then I Verify Consumer Actions as following data
      | [0].action               | Required                     |
      | [0].consumerAction       | Enroll                       |
      | [0].planSelectionAllowed | true                         |
      | [0].changeAllowedFrom    | 1stDayofNextMonth            |
      | [0].changeAllowedUntil   | 14DayFromFirstDayOfNextMonth |
    When I initiated get eligibility by consumer id "24289-01-5.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | categoryCode          | 10                |
      | programCode           | R                 |
      | subProgramTypeCode    | HoosierHealthwise |
      | coverageCode          | cc01              |
      | eligibilityStatusCode | X                 |
      | exemptionCode         | qwer              |
      | eligibilityStartDate  | 1stDayofNextMonth |
      | eligibilityEndDate    | highDate          |
      | createdOn             | current           |
      | createdBy             | 597               |
      | updatedOn             | current           |
      | updatedBy             | 597               |
    # CP-27487 Other Eligibility Segment Get API
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "24289-01-5.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console      |
      | [0].consumerId                | 24289-01-5.consumerId |
      | [0].segmentTypeCode           | OTHER                 |
      | [0].startDate                 | 1stDayofNextMonth     |
      | [0].endDate                   | highDate              |
      | [0].segmentDetailValue1       | 10                    |
      | [0].segmentDetailValue2       | 60                    |
      | [0].segmentDetailValue3       | 1stDayofPresentMonth  |
      | [0].segmentDetailValue4       | highDate              |
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


  @API-CP-27461 @API-CP-27487 @API-CP-24289 @API-CP-24289-02 @API-CP-41142 @API-EE-IN
  Scenario Outline: Verify Recon scenario for Eligibility INEB HHW and HCC population 2 eligibility records (BASE AID CATEGORY updates)
    CP-41142 IN-EB ELIGIBILITY Same Record Sent Multiple Times: MMIS Sends Reconciliation (Part 2 Create or Update)
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
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId          |
      | isEligibilityRequired        | yes                        |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                         |
      | recordId                     | 21                         |
      | eligibilityRecordId          | 21                         |
      | isEnrollmentProviderRequired | no                         |
      | enrollmentStartDate          | 1stDayofPresentMonth       |
      | eligibilityStartDate         | 1stDayofPresentMonth       |
      # eligibilityEndDate change with CP-41142
      | eligibilityEndDate           | <reconEligibilityEndDate1> |
      | txnStatus                    | Accepted                   |
      | programCode                  | R                          |
      | subProgramTypeCode           | HoosierHealthwise          |
      | eligibilityStatusCode        | M                          |
      | categoryCode                 | 10                         |
      | coverageCode                 | cc01                       |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth      |
      | endDate             | highDate                  |
      | segmentDetailValue1 | 1                         |
      | segmentDetailValue2 | 6                         |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentTypeCode     | OTHER                     |
    And User provide one more eligibility details
      | isEligibilityRequired        | yes                          |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                           |
      | recordId                     | 21                           |
      | eligibilityRecordId          | 21                           |
      | isEnrollmentProviderRequired | no                           |
      | enrollmentStartDate          | 1stDayofPresentMonth         |
      | eligibilityStartDate         | <reconEligibilityStartDate2> |
      | eligibilityEndDate           | highDate                     |
      | txnStatus                    | Accepted                     |
      | programCode                  | R                            |
      | subProgramTypeCode           | HoosierHealthwise            |
      | eligibilityStatusCode        | M                            |
      | categoryCode                 | 4                            |
      | coverageCode                 | cc01                         |
    Then I send API call with name "<name>b" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "<eligibilityScenario>","null"
    # 5.0 Add New Eligibility Segment from Second Eligibility Segment, Program Change
    Then I verify benefit status information with data
      | programPopulation | HHW-Mandatory |
      | benefitStatus     | Eligible      |
    Then I Verify Consumer Actions as following data
      | [0].action               | Required |
      | [0].consumerAction       | Enroll   |
      | [0].planSelectionAllowed | true     |
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    # 6.0 Base Aid Category Change - End Aid-Category and Create new Aid-Category (Bindu)
    # 7.0 Aid Category Add - End Eligibility Segment and Create New Eligibility Segment
    Then I verify following fields are captured in the newly created Eligibility Record
      | categoryCode          | <categoryCodeRes>          |
      | programCode           | R                          |
      | subProgramTypeCode    | HoosierHealthwise          |
      | coverageCode          | cc01                       |
      | eligibilityStatusCode | M                          |
      | exemptionCode         | qwer                       |
      | eligibilityStartDate  | 1stDayofPresentMonth       |
      | eligibilityEndDate    | <reconEligibilityEndDate1> |
      | Created on            |[blank]|
      | createdBy             | 597                        |
      | Updated On            |[blank]|
      | updatedBy             | 597                        |
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT |
      | consumerId    | <name>.consumerId        |
      | correlationId | null                     |
      | consumerFound | true                     |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                                  |
      | consumerId    | <name>.consumerId                                       |
      | correlationId | <name>a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                    |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName     | OTHER_ELIGIBILITY_SAVE_EVENT                            |
      | consumerId    | <name>.consumerId                                       |
      | correlationId | <name>b.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                    |
     # CP-27487 Other Eligibility Segment Get API
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "<name>.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console        |
      | [0].consumerId                | <name>.consumerId       |
      | [0].segmentTypeCode           | OTHER                   |
      | [0].startDate                 | 1stDayofPresentMonth    |
      | [0].endDate                   | highDate                |
      | [0].segmentDetailValue1       | 1                       |
      | [0].segmentDetailValue2       | 6                       |
      | [0].segmentDetailValue3       | 1stDayofPresentMonth    |
      | [0].segmentDetailValue4       | lastDayOfThePresentYear |
      | [0].segmentDetailValue5       | null                    |
      | [0].segmentDetailValue6       | null                    |
      | [0].segmentDetailValue7       | null                    |
      | [0].segmentDetailValue8       | null                    |
      | [0].segmentDetailValue9       | null                    |
      | [0].segmentDetailValue10      | null                    |
      | [0].createdOn                 | current                 |
      | [0].createdBy                 | 597                     |
      | [0].updatedOn                 | null                    |
      | [0].updatedBy                 | null                    |
      | [0].uiid                      | null                    |
    Examples:
      | name       | eligibilityScenario | reconEligibilityEndDate1 | reconEligibilityStartDate2 | categoryCodeRes |
       # categoryCodeRes change with CP-41142
      | 24289-02-1 | CHANGED END DATE    | lastDayOfThePresentYear  | firstDayOfNextYear         | 10              |


  @API-CP-27487 @API-CP-24289 @API-CP-24289-03 @API-EE-IN
  Scenario Outline: Verify Recon scenario for Eligibility INEB HIP population
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
      | categoryCode                 | 10                   |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId            |
      | isEligibilityRequired        | yes                          |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                           |
      | recordId                     | 21                           |
      | eligibilityRecordId          | 21                           |
      | isEnrollmentProviderRequired | no                           |
      | enrollmentStartDate          | 1stDayofPresentMonth         |
      | eligibilityStartDate         | <reconEligibilityStartDate>  |
      | eligibilityEndDate           | <reconEligibilityEndDate>    |
      | txnStatus                    | Accepted                     |
      | programCode                  | <reconProgramCode>           |
      | subProgramTypeCode           | <reconSubProgramTypeCode>    |
      | eligibilityStatusCode        | <reconEligibilityStatusCode> |
      | categoryCode                 | <reconCategoryCode>          |
      | genericFieldText1            | <reconGenericField>          |
      | coverageCode                 | cc01                         |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth      |
      | endDate             | highDate                  |
      | segmentDetailValue1 | 1                         |
      | segmentDetailValue2 | 6                         |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentTypeCode     | OTHER                     |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "<benefitStatus>","null"
    # 5.0 Add New Eligibility Segment from Second Eligibility Segment, Program Change
    Then I verify benefit status information with data
      | programPopulation | <population>         |
      | benefitStatus     | Benefit Status Error |
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable |
      | [0].consumerAction       | null        |
      | [0].planSelectionAllowed | false       |
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    # 6.0 Base Aid Category Change - End Aid-Category and Create new Aid-Category (Bindu)
    # 7.0 Aid Category Add - End Eligibility Segment and Create New Eligibility Segment
    Then I verify following fields are captured in the newly created Eligibility Record
      | categoryCode          | 10                           |
      | programCode           | <reconProgramCode>           |
      | subProgramTypeCode    | <reconSubProgramTypeCode>    |
      | coverageCode          | cc01                         |
      | eligibilityStatusCode | <reconEligibilityStatusCode> |
      | exemptionCode         | qwer                         |
      | eligibilityStartDate  | <eligibilityStartDateRes>    |
      | eligibilityEndDate    | highDate                     |
      | Created on            | current                      |
      | createdBy             | 597                          |
      | updatedOn             | current                      |
      | updatedBy             | 597                          |
    # CP-27487 Other Eligibility Segment Get API
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "<name>.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console        |
      | [0].consumerId                | <name>.consumerId       |
      | [0].segmentTypeCode           | OTHER                   |
      | [0].startDate                 | 1stDayofPresentMonth    |
      | [0].endDate                   | highDate                |
      | [0].segmentDetailValue1       | 1                       |
      | [0].segmentDetailValue2       | 6                       |
      | [0].segmentDetailValue3       | 1stDayofPresentMonth    |
      | [0].segmentDetailValue4       | lastDayOfThePresentYear |
      | [0].segmentDetailValue5       | null                    |
      | [0].segmentDetailValue6       | null                    |
      | [0].segmentDetailValue7       | null                    |
      | [0].segmentDetailValue8       | null                    |
      | [0].segmentDetailValue9       | null                    |
      | [0].segmentDetailValue10      | null                    |
      | [0].createdOn                 | current                 |
      | [0].createdBy                 | 597                     |
      | [0].updatedOn                 | null                    |
      | [0].updatedBy                 | null                    |
      | [0].uiid                      | null                    |
    Examples:
      | name       | benefitStatus      | reconEligibilityEndDate | reconEligibilityStartDate | eligibilityStartDateRes | reconProgramCode | reconSubProgramTypeCode | reconEligibilityStatusCode | reconCategoryCode | reconGenericField | population    |
      | 24289-03-1 | EXACT MATCH        | highDate                | 1stDayofPresentMonth      | 1stDayofPresentMonth    | H                | HealthyIndianaPlan      | V                          | 10                | Denied            | HIP-Voluntary |
      | 24289-03-2 | CHANGED START DATE | highDate                | 1stDayofNextMonth         | 1stDayofNextMonth       | H                | HealthyIndianaPlan      | V                          | 10                | Conditional       | HIP-Voluntary |
#      | 24289-03-5 | Different Program / Sub-Program | highDate                | 1stDayofPresentMonth      | 1stDayofPresentMonth    | A                | HoosierCareConnect      | M                          | 10                | Denied            | HIP-Voluntary |
#      | 24289-03-6 | Different Aid Category          | highDate                | 1stDayofPresentMonth      | 1stDayofPresentMonth    | H                | HealthyIndianaPlan      | V                          | 4                 | Denied            | HIP-Voluntary |
#      | 24289-03-7 | Shrink Timeframe                | highDate                | lastDayOfThePresentYear   | 1stDayofNextMonth       | H                | HealthyIndianaPlan      | V                          | 10                | Denied            | HIP-Voluntary |


  @API-CP-27487 @API-CP-24289 @API-CP-24289-03 @API-EE-IN
  Scenario Outline: Verify Recon scenario for Eligibility INEB HIP population
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
      | categoryCode                 | 10                   |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId            |
      | isEligibilityRequired        | yes                          |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                           |
      | recordId                     | 21                           |
      | eligibilityRecordId          | 21                           |
      | isEnrollmentProviderRequired | no                           |
      | enrollmentStartDate          | 1stDayofPresentMonth         |
      | eligibilityStartDate         | <reconEligibilityStartDate>  |
      | eligibilityEndDate           | <reconEligibilityEndDate>    |
      | txnStatus                    | Accepted                     |
      | programCode                  | <reconProgramCode>           |
      | subProgramTypeCode           | <reconSubProgramTypeCode>    |
      | eligibilityStatusCode        | <reconEligibilityStatusCode> |
      | categoryCode                 | <reconCategoryCode>          |
      | genericFieldText1            | <reconGenericField>          |
      | coverageCode                 | cc01                         |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth      |
      | endDate             | highDate                  |
      | segmentDetailValue1 | 1                         |
      | segmentDetailValue2 | 6                         |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentTypeCode     | OTHER                     |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "<benefitStatus>","null"
    # 5.0 Add New Eligibility Segment from Second Eligibility Segment, Program Change
    Then I verify benefit status information with data
      | programPopulation | <population>         |
      | benefitStatus     | Benefit Status Error |
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable |
      | [0].consumerAction       | null        |
      | [0].planSelectionAllowed | false       |
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    # 6.0 Base Aid Category Change - End Aid-Category and Create new Aid-Category (Bindu)
    # 7.0 Aid Category Add - End Eligibility Segment and Create New Eligibility Segment
    Then I verify following fields are captured in the newly created Eligibility Record
      | categoryCode          | 10                           |
      | programCode           | <reconProgramCode>           |
      | subProgramTypeCode    | <reconSubProgramTypeCode>    |
      | coverageCode          | cc01                         |
      | eligibilityStatusCode | <reconEligibilityStatusCode> |
      | exemptionCode         | qwer                         |
      | eligibilityStartDate  | <eligibilityStartDateRes>    |
      | eligibilityEndDate    | <reconEligibilityEndDate>    |
      | Created on            | current                      |
      | createdBy             | 597                          |
      | updatedOn             | current                      |
      | updatedBy             | 597                          |
    # CP-27487 Other Eligibility Segment Get API
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "<name>.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console        |
      | [0].consumerId                | <name>.consumerId       |
      | [0].segmentTypeCode           | OTHER                   |
      | [0].startDate                 | 1stDayofPresentMonth    |
      | [0].endDate                   | highDate                |
      | [0].segmentDetailValue1       | 1                       |
      | [0].segmentDetailValue2       | 6                       |
      | [0].segmentDetailValue3       | 1stDayofPresentMonth    |
      | [0].segmentDetailValue4       | lastDayOfThePresentYear |
      | [0].segmentDetailValue5       | null                    |
      | [0].segmentDetailValue6       | null                    |
      | [0].segmentDetailValue7       | null                    |
      | [0].segmentDetailValue8       | null                    |
      | [0].segmentDetailValue9       | null                    |
      | [0].segmentDetailValue10      | null                    |
      | [0].createdOn                 | current                 |
      | [0].createdBy                 | 597                     |
      | [0].updatedOn                 | null                    |
      | [0].updatedBy                 | null                    |
      | [0].uiid                      | null                    |
    Examples:
      | name       | benefitStatus    | reconEligibilityEndDate | reconEligibilityStartDate | eligibilityStartDateRes | reconProgramCode | reconSubProgramTypeCode | reconEligibilityStatusCode | reconCategoryCode | reconGenericField | population    |
      | 24289-03-3 | CHANGED END DATE | lastDayOfThePresentYear | 1stDayofPresentMonth      | 1stDayofPresentMonth    | H                | HealthyIndianaPlan      | M                          | 10                | Denied            | HIP-Mandatory |


  @API-CP-27461 @API-CP-26403 @API-CP-27487 @API-CP-24288 @API-CP-24289 @API-CP-24289-05 @API-EE-IN
  Scenario Outline: Verify Recon scenario for Eligibility INEB HHW, HCC No  Match
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId            |
      | isEligibilityRequired        | yes                          |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                           |
      | recordId                     | 21                           |
      | eligibilityRecordId          | 21                           |
      | isEnrollmentProviderRequired | no                           |
      | enrollmentStartDate          | 1stDayofPresentMonth         |
      | eligibilityStartDate         | 1stDayofPresentMonth         |
      | eligibilityEndDate           | highDate                     |
      | txnStatus                    | Accepted                     |
      | programCode                  | <reconProgramCode>           |
      | subProgramTypeCode           | <reconSubProgramTypeCode>    |
      | eligibilityStatusCode        | <reconEligibilityStatusCode> |
      | categoryCode                 | 10                           |
      | coverageCode                 | cc01                         |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth      |
      | endDate             | lastDayOfThePresentYear   |
      | segmentDetailValue1 | 1                         |
      | segmentDetailValue2 | 6                         |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentTypeCode     | OTHER                     |
    # CP-26403
    And User provide other enrollment segments details
      | recordId          | 21                   |
      | consumerId        | <name>.consumerId    |
      | startDate         | 1stDayofPresentMonth |
      | endDate           | highDate             |
      | genericFieldText1 | C                    |
      | genericFieldDate1 | openEnrollmentDay    |
      | segmentTypeCode   | OPEN_ENROLLMENT      |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer "<name>.consumerId"
    Then I verify scenario output in the benefit status records are displayed as "<benefitStatus>","null"
    # 4.0 Update Matched Eligibility Segment, Two Inbound Eligibility Segments, Aid-Category Change or Add
    Then I verify benefit status information with data
      | programPopulation | <population> |
      | benefitStatus     | Eligible     |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action> |
      | [0].consumerAction       | Enroll   |
      | [0].planSelectionAllowed | true     |
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | categoryCode          | 10                           |
      | programCode           | <reconProgramCode>           |
      | subProgramTypeCode    | <reconSubProgramTypeCode>    |
      | coverageCode          | cc01                         |
      | eligibilityStatusCode | <reconEligibilityStatusCode> |
      | exemptionCode         | qwer                         |
      | eligibilityStartDate  | 1stDayofPresentMonth         |
      | eligibilityEndDate    | highDate                     |
      | createdOn             | current                      |
      | createdBy             | 597                          |
      | updatedOn             | current                      |
      | updatedBy             | 597                          |
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                                  |
      | consumerId    | <name>.consumerId                                       |
      | correlationId | <name>a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | <eligSaveEvent>                                         |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName     | OTHER_ELIGIBILITY_SAVE_EVENT                            |
      | consumerId    | <name>.consumerId                                       |
      | correlationId | <name>a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | <othereligSaveEvent>                                    |
    # CP-27487 Other Eligibility Segment Get API
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "<name>.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console        |
      | [0].consumerId                | <name>.consumerId       |
      | [0].segmentTypeCode           | OTHER                   |
      | [0].startDate                 | 1stDayofPresentMonth    |
      | [0].endDate                   | lastDayOfThePresentYear |
      | [0].segmentDetailValue1       | 1                       |
      | [0].segmentDetailValue2       | 6                       |
      | [0].segmentDetailValue3       | 1stDayofPresentMonth    |
      | [0].segmentDetailValue4       | lastDayOfThePresentYear |
      | [0].segmentDetailValue5       | null                    |
      | [0].segmentDetailValue6       | null                    |
      | [0].segmentDetailValue7       | null                    |
      | [0].segmentDetailValue8       | null                    |
      | [0].segmentDetailValue9       | null                    |
      | [0].segmentDetailValue10      | null                    |
      | [0].createdOn                 | current                 |
      | [0].createdBy                 | 597                     |
      | [0].updatedOn                 | null                    |
      | [0].updatedBy                 | null                    |
      | [0].uiid                      | null                    |
    # CP-26403
    Then I verify other enrollment segment for coma separated consumerIds "<name>.consumerId" with data
      | [0].consumerId        | <name>.consumerId    |
      | [0].segmentTypeCode   | OPEN_ENROLLMENT      |
      | [0].startDate         | 1stDayofPresentMonth |
      | [0].endDate           | highDate             |
      | [0].enrollmentId      | null                 |
      | [0].genericFieldText1 | C                    |
      | [0].genericFieldText2 | null                 |
      | [0].genericFieldText3 | null                 |
      | [0].genericFieldText4 | null                 |
      | [0].genericFieldText5 | null                 |
      | [0].genericFieldDate1 | openEnrollmentDay    |
      | [0].genericFieldDate2 | null                 |
      | [0].genericFieldDate3 | null                 |
      | [0].genericFieldDate4 | null                 |
      | [0].genericFieldDate5 | null                 |
      | [0].createdOn         | current              |
      | [0].createdBy         | 597                  |
      | [0].updatedOn         | current              |
      | [0].updatedBy         | 597                  |
      | [0].timeframe         | Active               |
    Examples:
      | name       | benefitStatus | reconProgramCode | reconSubProgramTypeCode | reconCategoryCode | reconEligibilityStatusCode | population    | action   | eligUpdateEvent | eligSaveEvent | othereligUpdateEvent | othereligSaveEvent |
      | 24289-05-1 | NO MATCH      | A                | HoosierCareConnect      | 10                | V                          | HCC-Voluntary | Required | false           | true          | false                | true               |
      | 24289-05-2 | NO MATCH      | R                | HoosierHealthwise       | 10                | M                          | HHW-Mandatory | Required | false           | true          | false                | true               |


  @API-CP-27461 @API-CP-27487 @API-CP-24289 @API-CP-24289-06 @API-EE-IN
  Scenario Outline: Verify Recon scenario for Eligibility HIP No Match
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId            |
      | isEligibilityRequired        | yes                          |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                           |
      | recordId                     | 21                           |
      | eligibilityRecordId          | 21                           |
      | isEnrollmentProviderRequired | no                           |
      | enrollmentStartDate          | 1stDayofPresentMonth         |
      | eligibilityStartDate         | 1stDayofPresentMonth         |
      | eligibilityEndDate           | highDate                     |
      | txnStatus                    | Accepted                     |
      | programCode                  | <reconProgramCode>           |
      | subProgramTypeCode           | <reconSubProgramTypeCode>    |
      | eligibilityStatusCode        | <reconEligibilityStatusCode> |
      | categoryCode                 | <reconCategoryCode>          |
      | coverageCode                 | cc01                         |
      | genericFieldText1            | Eligible                     |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth      |
      | endDate             | lastDayOfThePresentYear   |
      | segmentDetailValue1 | 1                         |
      | segmentDetailValue2 | 6                         |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentTypeCode     | OTHER                     |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer "<name>.consumerId"
    Then I verify scenario output in the benefit status records are displayed as "<benefitStatus>","null"
    Then I verify benefit status information with data
      | programPopulation | <population>         |
      | benefitStatus     | Benefit Status Error |
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable |
      | [0].consumerAction       | null        |
      | [0].planSelectionAllowed | false       |
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | categoryCode          | 10                           |
      | programCode           | <reconProgramCode>           |
      | subProgramTypeCode    | <reconSubProgramTypeCode>    |
      | coverageCode          | cc01                         |
      | eligibilityStatusCode | <reconEligibilityStatusCode> |
      | exemptionCode         | qwer                         |
      | eligibilityStartDate  | 1stDayofPresentMonth         |
      | eligibilityEndDate    | highDate                     |
      | createdOn             | current                      |
      | createdBy             | 597                          |
      | updatedOn             | current                      |
      | updatedBy             | 597                          |
      | genericFieldText1     | Eligible                     |
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                                  |
      | consumerId    | <name>.consumerId                                       |
      | correlationId | <name>a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | <eligSaveEvent>                                         |
    Then I will verify business events are generated with data
      | eventName     | OTHER_ELIGIBILITY_SAVE_EVENT                            |
      | consumerId    | <name>.consumerId                                       |
      | correlationId | <name>a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | <othereligSaveEvent>                                    |
    # CP-27487 Other Eligibility Segment Get API
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "<name>.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console        |
      | [0].consumerId                | <name>.consumerId       |
      | [0].segmentTypeCode           | OTHER                   |
      | [0].startDate                 | 1stDayofPresentMonth    |
      | [0].endDate                   | lastDayOfThePresentYear |
      | [0].segmentDetailValue1       | 1                       |
      | [0].segmentDetailValue2       | 6                       |
      | [0].segmentDetailValue3       | 1stDayofPresentMonth    |
      | [0].segmentDetailValue4       | lastDayOfThePresentYear |
      | [0].segmentDetailValue5       | null                    |
      | [0].segmentDetailValue6       | null                    |
      | [0].segmentDetailValue7       | null                    |
      | [0].segmentDetailValue8       | null                    |
      | [0].segmentDetailValue9       | null                    |
      | [0].segmentDetailValue10      | null                    |
      | [0].createdOn                 | current                 |
      | [0].createdBy                 | 597                     |
      | [0].updatedOn                 | null                    |
      | [0].updatedBy                 | null                    |
      | [0].uiid                      | null                    |
    Examples:
      | name       | benefitStatus | reconProgramCode | reconSubProgramTypeCode | reconCategoryCode | reconEligibilityStatusCode | population    | eligSaveEvent | othereligSaveEvent |
      | 24289-06-1 | NO MATCH      | H                | HealthyIndianaPlan      | 10                | V                          | HIP-Voluntary | true          | true               |
  #      | 24289-04-4 | Different Program with Overlapping Timeframe | lastDayOfThePresentYear  | 1stDayofPresentMonth       | R                 | A                 | HoosierHealthwise        | HoosierCareConnect       | 10                 | 10                 | M                           | V                           | HHW-Mandatory | Required | true            | true          | false                | true               |
#      | 24289-04-5 | Different Program / Sub-Program                                         | highDate                 | 1stDayofPresentMonth       | A                 | A                 | HoosierCareConnect       | HoosierCareConnect       | 10                 | 10                 | M                           | V                           | HHW-Voluntary | Required | true            | true          | false                | true               |
#      | 24289-04-6 | Different Aid Category                                                  | highDate                 | 1stDayofPresentMonth       | A                 | A                 | HoosierCareConnect       | HoosierCareConnect       | 4                  | 10                 | M                           | M                           | HCC-Mandatory | Required | true            | true          | false                | true               |


  @API-CP-28019 @API-CP-28019-03 @API-CP-29592 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario Outline: Update Open Enrollment Fields and Anniversary Date for HCC & HHW Eligibility recordId 21 Status C
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
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | <name>.consumerId        |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | C                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | enrollmentEndDate            | lastDayOfThePresentYear |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | planCode                     | <planCode>              |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I Verify Consumer Actions as following data
      | [0].action               | Available                |
      | [0].consumerAction       | Plan Change Anniversary  |
      | [0].planSelectionAllowed | true                     |
      | [0].changeAllowedFrom    | 1stDayofPresentMonth     |
      | [0].changeAllowedUntil   | 90DayFromFirstDayOfMonth |
    Examples:
      | name    | programCode | subProgramTypeCode | eligibilityStatusCode | planCode  |
      | 28019-7 | R           | HoosierHealthwise  | M                     | 400752220 |
      | 28019-8 | A           | HoosierCareConnect | M                     | 499254630 |
      | 28019-9 | A           | HoosierCareConnect | V                     | 499254630 |


  @API-CP-28019 @API-CP-28019-04 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario Outline: Update Open Enrollment Fields and Anniversary Date for HCC & HHW Eligibility recordId 21 Status O
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
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | <name>.consumerId        |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | enrollmentEndDate            | lastDayOfThePresentYear |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | planCode                     | <planCode>              |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I Verify Consumer Actions as following data
      | [0].action               | Available                |
      | [0].consumerAction       | Plan Change Pre-lockin   |
      | [0].planSelectionAllowed | true                     |
      | [0].changeAllowedFrom    | 1stDayofPresentMonth     |
      | [0].changeAllowedUntil   | 90DayFromFirstDayOfMonth |
    Examples:
      | name     | programCode | subProgramTypeCode | eligibilityStatusCode | planCode  |
      | 28019-10 | R           | HoosierHealthwise  | M                     | 400752220 |
      | 28019-11 | A           | HoosierCareConnect | M                     | 499254630 |
      | 28019-12 | A           | HoosierCareConnect | V                     | 499254630 |


  @API-CP-24287 @API-CP-24287-01 @API-CP-24287-02 @API-CP-24287-06 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario Outline: IN-EB ELIGIBILITY Create Business Event Reconciliation (1.0 2.0 6.0 Exact Match for HHW/HCC and HIP)
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
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
      | genericFieldText1            | <HIPstatus>          |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth      |
      | endDate             | highDate                  |
      | segmentDetailValue1 | 10                        |
      | segmentDetailValue2 | 60                        |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentTypeCode     | OTHER                     |
    And User provide other enrollment segments details
      | recordId          | 21                                    |
      | consumerId        | <name>.consumerId                     |
      | startDate         | 1stDayofPresentMonth                  |
      | endDate           | 60DaysFromFirstDayOfMonth             |
      | genericFieldText1 | C                                     |
      | genericFieldDate1 | nextDayAfter60DaysFromFirstDayOfMonth |
      | segmentTypeCode   | OPEN_ENROLLMENT                       |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
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
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
      | genericFieldText1            | <HIPstatus>          |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth      |
      | endDate             | highDate                  |
      | segmentDetailValue1 | 10                        |
      | segmentDetailValue2 | 60                        |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentTypeCode     | OTHER                     |
    And User provide other enrollment segments details
      | recordId          | 21                                    |
      | consumerId        | <name>.consumerId                     |
      | startDate         | 1stDayofPresentMonth                  |
      | endDate           | 60DaysFromFirstDayOfMonth             |
      | genericFieldText1 | C                                     |
      | genericFieldDate1 | nextDayAfter60DaysFromFirstDayOfMonth |
      | segmentTypeCode   | OPEN_ENROLLMENT                       |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "EXACT MATCH","null"
    And Wait for 10 seconds
    Then I will verify business events are generated with data
    # 1.0 Business Event Required Fields
      | eventName                                                               | RECONCILIATION                                          |
      | consumerId                                                              | <name>.consumerId                                       |
      | correlationId                                                           | <name>a.eligibilities.[0].coreEligibility.correlationId |
      | channel                                                                 | SYSTEM_INTEGRATION                                      |
      | createdBy                                                               | 597                                                     |
      | processDate                                                             | current                                                 |
      | externalCaseId                                                          | <name>.caseIdentificationNumber                         |
      | externalConsumerId                                                      | <name>.externalConsumerId                               |
      | consumerName                                                            | <name>                                                  |
      | consumerPopulation                                                      | <consumerPopulation>                                    |
    # 2.0 Business Event Optional Eligibility Fields: No Update
      | reconciliationAction                                                    | No Eligibility Update                                   |
      | eligibilityStartDate                                                    | 1stDayofPresentMonth                                    |
      | eligibilityEndDate                                                      | highDate                                                |
      | eligibilityEndReason                                                    | null                                                    |
      | eligibilityCategoryCode                                                 | 10                                                      |
      | eligibilityProgramCode                                                  | MEDICAID                                                |
      | eligibilitySubProgramCode                                               | <subProgramTypeCode>                                    |
      | eligibilityCoverageCode                                                 | cc01                                                    |
      | eligibilityExemptionCode                                                | qwer                                                    |
      | benefitStatus                                                           | <benefitStatus>                                         |
    # 6.0 Business Event Optional IN-EB Tenant-Specific Fields
      | additionalFileds.exactMatch.Managed Care Status                         | M                                                       |
      | additionalFileds.exactMatch.HIP Status                                  | <HIPstatus>                                             |

      | additionalFileds.OtherSegmentInfos.Open Enrollment Effective Date       | 1stDayofPresentMonth                                    |
      | additionalFileds.OtherSegmentInfos.Open Enrollment End Date             | 60DaysFromFirstDayOfMonth                               |
      | additionalFileds.OtherSegmentInfos.Open Enrollment Status               | C                                                       |
      | additionalFileds.OtherSegmentInfos.Open Enrollment Lockin Complete Date | nextDayAfter60DaysFromFirstDayOfMonth                   |
      | additionalFileds.OtherSegmentInfos.Redetermination Date                 | 1stDayofPresentMonth                                    |
      | additionalFileds.OtherSegmentInfos.Debt Indicator                       | 10                                                      |
      | additionalFileds.OtherSegmentInfos.Conditional Eligibility Date         | lastDayOfThePresentYear                                 |
      | additionalFileds.OtherSegmentInfos.Benefit Effective Date               | 1stDayofPresentMonth                                    |
      | additionalFileds.OtherSegmentInfos.Benefit End Date                     | highDate                                                |
      | additionalFileds.OtherSegmentInfos.Potential Plus Indicator             | 60                                                      |
    Examples: For HCC/HHW and HIP
      | name    | programCode | subProgramTypeCode | consumerPopulation | benefitStatus        | HIPstatus |
      | 24287-1 | R           | HoosierHealthwise  | HHW-Mandatory      | Eligible             | null      |
      | 24287-2 | H           | HealthyIndianaPlan | HIP-Mandatory      | Benefit Status Error | Eligible  |


  @API-CP-24287 @API-CP-24287-03 @API-CP-24287-02 @API-CP-24287-06 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario Outline: IN-EB ELIGIBILITY Create Business Event Reconciliation (1.0 3.0 6.0 Create Eligibility Segment HHW/HCC and HIP)
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
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
      | genericFieldText1            | <HIPstatus>          |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth      |
      | endDate             | highDate                  |
      | segmentDetailValue1 | 10                        |
      | segmentDetailValue2 | 60                        |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentTypeCode     | OTHER                     |
    And User provide other enrollment segments details
      | recordId          | 21                                    |
      | consumerId        | <name>.consumerId                     |
      | startDate         | 1stDayofPresentMonth                  |
      | endDate           | 60DaysFromFirstDayOfMonth             |
      | genericFieldText1 | C                                     |
      | genericFieldDate1 | nextDayAfter60DaysFromFirstDayOfMonth |
      | segmentTypeCode   | OPEN_ENROLLMENT                       |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "NO MATCH","null"
    And Wait for 10 seconds
    Then I will verify business events are generated with data
    # 1.0 Business Event Required Fields
      | eventName                                                               | RECONCILIATION                                          |
      | consumerId                                                              | <name>.consumerId                                       |
      | correlationId                                                           | <name>a.eligibilities.[0].coreEligibility.correlationId |
      | channel                                                                 | SYSTEM_INTEGRATION                                      |
      | createdBy                                                               | 597                                                     |
      | processDate                                                             | current                                                 |
      | externalCaseId                                                          | <name>.caseIdentificationNumber                         |
      | externalConsumerId                                                      | <name>.externalConsumerId                               |
      | consumerName                                                            | <name>                                                  |
      | consumerPopulation                                                      | <consumerPopulation>                                    |
    # 2.0 Business Event Optional Eligibility Fields: No Update
      | reconciliationAction                                                    | Eligibility Created                                     |
      | eligibilityStartDate                                                    | 1stDayofPresentMonth                                    |
      | eligibilityEndDate                                                      | highDate                                                |
      | eligibilityEndReason                                                    | null                                                    |
      | eligibilityCategoryCode                                                 | 10                                                      |
      | eligibilityProgramCode                                                  | MEDICAID                                                |
      | eligibilitySubProgramCode                                               | <subProgramTypeCode>                                    |
      | eligibilityCoverageCode                                                 | cc01                                                    |
      | eligibilityExemptionCode                                                | qwer                                                    |
      | benefitStatus                                                           | <benefitStatus>                                         |
    # 6.0 Business Event Optional IN-EB Tenant-Specific Fields
      | additionalFileds.noMatch.Managed Care Status                            | M                                                       |
      | additionalFileds.noMatch.HIP Status                                     | <HIPstatus>                                             |

      | additionalFileds.OtherSegmentInfos.Open Enrollment Effective Date       | 1stDayofPresentMonth                                    |
      | additionalFileds.OtherSegmentInfos.Open Enrollment End Date             | 60DaysFromFirstDayOfMonth                               |
      | additionalFileds.OtherSegmentInfos.Open Enrollment Status               | C                                                       |
      | additionalFileds.OtherSegmentInfos.Open Enrollment Lockin Complete Date | nextDayAfter60DaysFromFirstDayOfMonth                   |
      | additionalFileds.OtherSegmentInfos.Redetermination Date                 | 1stDayofPresentMonth                                    |
      | additionalFileds.OtherSegmentInfos.Debt Indicator                       | 10                                                      |
      | additionalFileds.OtherSegmentInfos.Conditional Eligibility Date         | lastDayOfThePresentYear                                 |
      | additionalFileds.OtherSegmentInfos.Benefit Effective Date               | 1stDayofPresentMonth                                    |
      | additionalFileds.OtherSegmentInfos.Benefit End Date                     | highDate                                                |
      | additionalFileds.OtherSegmentInfos.Potential Plus Indicator             | 60                                                      |
    Examples: For HCC, HHW and HIP
      | name    | programCode | subProgramTypeCode | consumerPopulation | benefitStatus        | HIPstatus |
      | 24287-3 | R           | HoosierHealthwise  | HHW-Mandatory      | Eligible             | null      |
      | 24287-4 | A           | HoosierCareConnect | HCC-Mandatory      | Eligible             | null      |
      | 24287-5 | H           | HealthyIndianaPlan | HIP-Mandatory      | Benefit Status Error | Eligible  |


  @API-CP-24287 @API-CP-24287-04 @API-CP-24287-02 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario: IN-EB ELIGIBILITY Create Business Event Reconciliation (4.0 Update Eligibility Segment CHANGED START DATE)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 24287-6 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 24287-6.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | highDate             |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    And User provide other eligibility segments details
      | startDate           | 1stDayofLastMonth         |
      | endDate             | highDate                  |
      | segmentDetailValue1 | 10                        |
      | segmentDetailValue2 | 60                        |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentTypeCode     | OTHER                     |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 24287-6.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth      |
      | endDate             | highDate                  |
      | segmentDetailValue1 | 10                        |
      | segmentDetailValue2 | 60                        |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentTypeCode     | OTHER                     |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 24287-6.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 21                 |
      | eligibilityRecordId          | 21                 |
      | isEnrollmentProviderRequired | no                 |
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | eligibilityEndDate           | highDate           |
      | enrollmentStartDate          | 1stDayofLastMonth  |
      | txnStatus                    | Accepted           |
      | programCode                  | R                  |
      | subProgramTypeCode           | HoosierHealthwise  |
      | eligibilityStatusCode        | M                  |
      | categoryCode                 | 10                 |
      | coverageCode                 | cc01               |
    And User provide other eligibility segments details
      | startDate           | 1stDayofLastMonth         |
      | endDate             | highDate                  |
      | segmentDetailValue1 | 10                        |
      | segmentDetailValue2 | 60                        |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentTypeCode     | OTHER                     |
    And User provide other enrollment segments details
      | recordId          | 21                                    |
      | consumerId        | 24287-6.consumerId                    |
      | startDate         | 1stDayofPresentMonth                  |
      | endDate           | 60DaysFromFirstDayOfMonth             |
      | genericFieldText1 | C                                     |
      | genericFieldDate1 | nextDayAfter60DaysFromFirstDayOfMonth |
      | segmentTypeCode   | OPEN_ENROLLMENT                       |
    Then I send API call with name "24287-6a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "24287-6.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "CHANGED START DATE","null"
    And Wait for 10 seconds
    Then I will verify business events are generated with data
    # 1.0 Business Event Required Fields
      | eventName                                                                        | RECONCILIATION                                           |
      | consumerId                                                                       | 24287-6.consumerId                                       |
      | correlationId                                                                    | 24287-6a.eligibilities.[0].coreEligibility.correlationId |
      | channel                                                                          | SYSTEM_INTEGRATION                                       |
      | createdBy                                                                        | 597                                                      |
      | processDate                                                                      | current                                                  |
      | externalCaseId                                                                   | 24287-6.caseIdentificationNumber                         |
      | externalConsumerId                                                               | 24287-6.externalConsumerId                               |
      | consumerName                                                                     | 24287-6                                                  |
    # 4.0 Business Event Optional Eligibility Fields: Update Eligibility Segment
      | reconciliationAction                                                             | Eligibility Updated                                      |
      | oldValue.eligibilityProgramCode                                                  | MEDICAID                                                 |
      | oldValue.eligibilitySubProgramCode                                               | HoosierHealthwise                                        |
      | oldValue.eligibilityStartDate                                                    | 1stDayofPresentMonth                                     |
      | oldValue.eligibilityEndDate                                                      | highDate                                                 |
      | oldValue.eligibilityEndReason                                                    | null                                                     |
      | oldValue.eligibilityCategoryCode                                                 | 10                                                       |
      | oldValue.eligibilityCoverageCode                                                 | cc01                                                     |
      | oldValue.eligibilityExemptionCode                                                | qwer                                                     |
      | oldValue.benefitStatus                                                           | Eligible                                                 |
      | newValue.eligibilityProgramCode                                                  | MEDICAID                                                 |
      | newValue.eligibilitySubProgramCode                                               | HoosierHealthwise                                        |
      | newValue.eligibilityStartDate                                                    | 1stDayofLastMonth                                        |
      | newValue.eligibilityEndDate                                                      | highDate                                                 |
      | newValue.eligibilityEndReason                                                    | null                                                     |
      | newValue.eligibilityCategoryCode                                                 | 10                                                       |
      | newValue.eligibilityCoverageCode                                                 | cc01                                                     |
      | newValue.eligibilityExemptionCode                                                | qwer                                                     |
      | newValue.benefitStatus                                                           | Eligible                                                 |
    # 6.0 Business Event Optional IN-EB Tenant-Specific Fields
      | newValue.additionalFileds.eligibilityUpdated.Managed Care Status                 | M                                                        |
      | newValue.additionalFileds.eligibilityUpdated.HIP Status                          | null                                                     |

      | newValue.additionalFileds.OtherSegmentInfos.Open Enrollment Effective Date       | 1stDayofPresentMonth                                     |
      | newValue.additionalFileds.OtherSegmentInfos.Open Enrollment End Date             | 60DaysFromFirstDayOfMonth                                |
      | newValue.additionalFileds.OtherSegmentInfos.Open Enrollment Status               | C                                                        |
      | newValue.additionalFileds.OtherSegmentInfos.Open Enrollment Lockin Complete Date | nextDayAfter60DaysFromFirstDayOfMonth                    |
      | newValue.additionalFileds.OtherSegmentInfos.Redetermination Date                 | 1stDayofPresentMonth                                     |
      | newValue.additionalFileds.OtherSegmentInfos.Debt Indicator                       | 10                                                       |
      | newValue.additionalFileds.OtherSegmentInfos.Conditional Eligibility Date         | lastDayOfThePresentYear                                  |
      | newValue.additionalFileds.OtherSegmentInfos.Benefit Effective Date               | 1stDayofLastMonth                                        |
      | newValue.additionalFileds.OtherSegmentInfos.Benefit End Date                     | highDate                                                 |
      | newValue.additionalFileds.OtherSegmentInfos.Potential Plus Indicator             | 60                                                       |


  @API-CP-24287 @API-CP-24287-04 @API-CP-24287-02 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario: IN-EB ELIGIBILITY Create Business Event Reconciliation (4.0 Update Eligibility Segment CHANGED END DATE)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 24287-7 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 24287-7.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth      |
      | endDate             | highDate                  |
      | segmentDetailValue1 | 10                        |
      | segmentDetailValue2 | 60                        |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentTypeCode     | OTHER                     |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 24287-7.consumerId      |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | lastDayOfThePresentYear |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | txnStatus                    | Accepted                |
      | programCode                  | R                       |
      | subProgramTypeCode           | HoosierHealthwise       |
      | eligibilityStatusCode        | M                       |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth      |
      | endDate             | highDate                  |
      | segmentDetailValue1 | 10                        |
      | segmentDetailValue2 | 60                        |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentTypeCode     | OTHER                     |
    And User provide other enrollment segments details
      | recordId          | 21                                    |
      | consumerId        | 24287-7.consumerId                    |
      | startDate         | 1stDayofPresentMonth                  |
      | endDate           | 60DaysFromFirstDayOfMonth             |
      | genericFieldText1 | C                                     |
      | genericFieldDate1 | nextDayAfter60DaysFromFirstDayOfMonth |
      | segmentTypeCode   | OPEN_ENROLLMENT                       |
    Then I send API call with name "24287-7a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "24287-7.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "CHANGED END DATE","null"
    And Wait for 10 seconds
    Then I will verify business events are generated with data
    # 1.0 Business Event Required Fields
      | eventName                                                                        | RECONCILIATION                                           |
      | consumerId                                                                       | 24287-7.consumerId                                       |
      | correlationId                                                                    | 24287-7a.eligibilities.[0].coreEligibility.correlationId |
      | channel                                                                          | SYSTEM_INTEGRATION                                       |
      | createdBy                                                                        | 597                                                      |
      | processDate                                                                      | current                                                  |
      | externalCaseId                                                                   | 24287-7.caseIdentificationNumber                         |
      | externalConsumerId                                                               | 24287-7.externalConsumerId                               |
      | consumerName                                                                     | 24287-7                                                  |
    # 4.0 Business Event Optional Eligibility Fields: Update Eligibility Segment
      | reconciliationAction                                                             | Eligibility Updated                                      |
      | newValue.eligibilityProgramCode                                                  | MEDICAID                                                 |
      | newValue.eligibilitySubProgramCode                                               | HoosierHealthwise                                        |
      | newValue.eligibilityStartDate                                                    | 1stDayofPresentMonth                                     |
      | newValue.eligibilityEndDate                                                      | highDate                                                 |
      | newValue.eligibilityEndReason                                                    | null                                                     |
      | newValue.eligibilityCategoryCode                                                 | 10                                                       |
      | newValue.eligibilityCoverageCode                                                 | cc01                                                     |
      | newValue.eligibilityExemptionCode                                                | qwer                                                     |
      # Since CP-32018 is implemented now it is Eligible
      | newValue.benefitStatus                                                           | Eligible                                                 |
    # 6.0 Business Event Optional IN-EB Tenant-Specific Fields
      | newValue.additionalFileds.eligibilityUpdated.Managed Care Status                 | M                                                        |
      | newValue.additionalFileds.eligibilityUpdated.HIP Status                          | null                                                     |

      | newValue.additionalFileds.OtherSegmentInfos.Open Enrollment Effective Date       | 1stDayofPresentMonth                                     |
      | newValue.additionalFileds.OtherSegmentInfos.Open Enrollment End Date             | 60DaysFromFirstDayOfMonth                                |
      | newValue.additionalFileds.OtherSegmentInfos.Open Enrollment Status               | C                                                        |
      | newValue.additionalFileds.OtherSegmentInfos.Open Enrollment Lockin Complete Date | nextDayAfter60DaysFromFirstDayOfMonth                    |
      | newValue.additionalFileds.OtherSegmentInfos.Redetermination Date                 | 1stDayofPresentMonth                                     |
      | newValue.additionalFileds.OtherSegmentInfos.Debt Indicator                       | 10                                                       |
      | newValue.additionalFileds.OtherSegmentInfos.Conditional Eligibility Date         | lastDayOfThePresentYear                                  |
      | newValue.additionalFileds.OtherSegmentInfos.Benefit Effective Date               | 1stDayofPresentMonth                                     |
      | newValue.additionalFileds.OtherSegmentInfos.Benefit End Date                     | highDate                                                 |
      | newValue.additionalFileds.OtherSegmentInfos.Potential Plus Indicator             | 60                                                       |


  @API-CP-24287 @API-CP-24287-05 @API-CP-24287-06 @API-CP-41142 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario Outline: IN-EB ELIGIBILITY Create Business Event Reconciliation (1.0 5.0 6.0 Program / Sub-Program Change)
  CP-41142 IN-EB ELIGIBILITY Same Record Sent Multiple Times: MMIS Sends Reconciliation (Part 2 Create or Update)
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
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | lastDayOfThePresentYear |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | txnStatus                    | Accepted                |
      | programCode                  | R                       |
      | subProgramTypeCode           | HoosierHealthwise       |
      | eligibilityStatusCode        | M                       |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth      |
      | endDate             | highDate                  |
      | segmentDetailValue1 | 10                        |
      | segmentDetailValue2 | 60                        |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentTypeCode     | OTHER                     |
    And User provide one more eligibility details
      | isEligibilityRequired        | yes                        |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                         |
      | recordId                     | 21                         |
      | eligibilityRecordId          | 21                         |
      | isEnrollmentProviderRequired | no                         |
      | eligibilityStartDate         | firstDayOfNextYear         |
      | eligibilityEndDate           | highDate                   |
      | enrollmentStartDate          | firstDayOfNextYear         |
      | txnStatus                    | Accepted                   |
      | programCode                  | <2reconProgramCode>        |
      | subProgramTypeCode           | <2reconSubProgramTypeCode> |
      | eligibilityStatusCode        | M                          |
      | categoryCode                 | 10                         |
      | coverageCode                 | cc01                       |
      | genericFieldText1            | <HIPstatus>                |
    And User provide other enrollment segments details
      | recordId          | 21                                    |
      | consumerId        | <name>.consumerId                     |
      | startDate         | 1stDayofPresentMonth                  |
      | endDate           | 60DaysFromFirstDayOfMonth             |
      | genericFieldText1 | C                                     |
      | genericFieldDate1 | nextDayAfter60DaysFromFirstDayOfMonth |
      | segmentTypeCode   | OPEN_ENROLLMENT                       |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "<eligibilityScenario>","null"
    And Wait for 10 seconds
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
    # 4.0 Business Event Optional Eligibility Fields: Update Eligibility Segment
      | reconciliationAction                                                             | Eligibility Updated                                     |
      | consumerPopulation                                                               | <2reconConsumerPopulation>                              |
      # priorSegment to oldValue and newSegment to newValue change with CP-41142
      | oldValue.eligibilityStartDate                                                    | 1stDayofPresentMonth                                    |
      | oldValue.eligibilityEndDate                                                      | highDate                                                |
      | oldValue.eligibilityEndReason                                                    | null                                                    |
      | oldValue.eligibilityCategoryCode                                                 | 10                                                      |
      | oldValue.eligibilityProgramCode                                                  | MEDICAID                                                |
      | oldValue.eligibilitySubProgramCode                                               | HoosierHealthwise                                       |
      | oldValue.eligibilityCoverageCode                                                 | cc01                                                    |
      | oldValue.eligibilityExemptionCode                                                | qwer                                                    |
      | oldValue.benefitStatus                                                           | Eligible                                                |
      | newValue.eligibilityProgramCode                                                  | MEDICAID                                                |
      | newValue.eligibilitySubProgramCode                                               | HoosierHealthwise                                       |
      | newValue.eligibilityStartDate                                                    | 1stDayofPresentMonth                                    |
      | newValue.eligibilityEndDate                                                      | lastDayOfThePresentYear                                 |
      | newValue.eligibilityEndReason                                                    | null                                                    |
      | newValue.eligibilityCategoryCode                                                 | 10                                                      |
      | newValue.eligibilityCoverageCode                                                 | cc01                                                    |
      | newValue.eligibilityExemptionCode                                                | qwer                                                    |
      | newValue.benefitStatus                                                           | <benefitStatus>                                         |
  # 6.0 Business Event Optional IN-EB Tenant-Specific Fields
      | newValue.additionalFileds.OtherSegmentInfos.Open Enrollment Effective Date       | 1stDayofPresentMonth                                    |
      | newValue.additionalFileds.OtherSegmentInfos.Open Enrollment End Date             | 60DaysFromFirstDayOfMonth                               |
      | newValue.additionalFileds.OtherSegmentInfos.Open Enrollment Status               | C                                                       |
      | newValue.additionalFileds.OtherSegmentInfos.Open Enrollment Lockin Complete Date | nextDayAfter60DaysFromFirstDayOfMonth                   |
      | newValue.additionalFileds.OtherSegmentInfos.Redetermination Date                 | 1stDayofPresentMonth                                    |
      | newValue.additionalFileds.OtherSegmentInfos.Debt Indicator                       | 10                                                      |
      | newValue.additionalFileds.OtherSegmentInfos.Conditional Eligibility Date         | lastDayOfThePresentYear                                 |
      | newValue.additionalFileds.OtherSegmentInfos.Benefit Effective Date               | 1stDayofPresentMonth                                    |
      | newValue.additionalFileds.OtherSegmentInfos.Benefit End Date                     | highDate                                                |
      | newValue.additionalFileds.OtherSegmentInfos.Potential Plus Indicator             | 60                                                      |
    Examples:
      | name    | eligibilityScenario | 2reconProgramCode | 2reconSubProgramTypeCode | 2reconConsumerPopulation | HIPstatus | benefitStatus |
      # 2reconProgramCode,2reconProgramCode and benefitStatus change with CP-41142
      | 24287-8 | CHANGED END DATE    | A                 | HoosierCareConnect        | HHW-Mandatory            | null      | Eligible      |
      | 24287-9 | CHANGED END DATE    | H                 | HealthyIndianaPlan        | HHW-Mandatory            | Eligible  | Eligible      |



  @API-CP-24287 @API-CP-24287-05 @API-CP-24287-06 @API-CP-41142 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario Outline: IN-EB ELIGIBILITY Create Business Event Reconciliation (1.0 5.0 6.0 Base Aid Category CHANGE)
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
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId          |
      | isEligibilityRequired        | yes                        |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                         |
      | recordId                     | 21                         |
      | eligibilityRecordId          | 21                         |
      | isEnrollmentProviderRequired | no                         |
      | eligibilityStartDate         | 1stDayofPresentMonth       |
      | eligibilityEndDate           | <1reconEligibilityEndDate> |
      | enrollmentStartDate          | 1stDayofPresentMonth       |
      | txnStatus                    | Accepted                   |
      | programCode                  | R                          |
      | subProgramTypeCode           | HoosierHealthwise          |
      | eligibilityStatusCode        | M                          |
      | categoryCode                 | 10                         |
      | coverageCode                 | cc01                       |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth      |
      | endDate             | highDate                  |
      | segmentDetailValue1 | 10                        |
      | segmentDetailValue2 | 60                        |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentTypeCode     | OTHER                     |
    And User provide one more eligibility details
      | isEligibilityRequired        | yes                          |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                           |
      | recordId                     | 21                           |
      | eligibilityRecordId          | 21                           |
      | isEnrollmentProviderRequired | no                           |
      | eligibilityStartDate         | <2reconEligibilityStartDate> |
      | eligibilityEndDate           | highDate                     |
      | enrollmentStartDate          | 1stDayofPresentMonth         |
      | txnStatus                    | Accepted                     |
      | programCode                  | R                            |
      | subProgramTypeCode           | HoosierHealthwise            |
      | eligibilityStatusCode        | M                            |
      | categoryCode                 | 4                            |
      | coverageCode                 | cc01                         |
      | genericFieldText1            | null                         |
    And User provide other enrollment segments details
      | recordId          | 21                                    |
      | consumerId        | <name>.consumerId                     |
      | startDate         | 1stDayofPresentMonth                  |
      | endDate           | 60DaysFromFirstDayOfMonth             |
      | genericFieldText1 | C                                     |
      | genericFieldDate1 | nextDayAfter60DaysFromFirstDayOfMonth |
      | segmentTypeCode   | OPEN_ENROLLMENT                       |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "<eligibilityScenario>","null"
    And Wait for 10 seconds
    Then I will verify business events are generated with data
    # 1.0 Business Event Required Fields
      | eventName                                                                        | RECONCILIATION                                          |
      | consumerId                                                                       | <name>.consumerId                                       |
      | correlationId                                                                    | <name>a.eligibilities.[0].coreEligibility.correlationId |
      | channel                                                                          | SYSTEM_INTEGRATION                                      |
      | createdBy                                                                        | 597                                                     |
      | processDate                                                                      | current                                                 |
      | externalCaseId                                                                   | <name>.caseIdentificationNumber                         |
      | externalConsumerId                                                               | <name>.externalConsumerId                               |
      | consumerName                                                                     | <name>                                                  |
      | consumerPopulation                                                               | HHW-Mandatory                                           |
    # 4.0 Business Event Optional Eligibility Fields: Update Eligibility Segment
      | reconciliationAction                                                             | Eligibility Updated                                     |
      | oldValue.eligibilityStartDate                                                    | 1stDayofPresentMonth                                    |
#      | oldValue.eligibilityEndDate        | lastDayOfThePresentYear                                 |
      | oldValue.eligibilityEndReason                                                    | null                                                    |
      | oldValue.eligibilityCategoryCode                                                 | 10                                                      |
      | oldValue.eligibilityProgramCode                                                  | MEDICAID                                                |
      | oldValue.eligibilitySubProgramCode                                               | HoosierHealthwise                                       |
      | oldValue.eligibilityCoverageCode                                                 | cc01                                                    |
      | oldValue.eligibilityExemptionCode                                                | qwer                                                    |
      | oldValue.benefitStatus                                                           | Eligible                                                |
      | newValue.eligibilityProgramCode                                                  | MEDICAID                                                |
      | newValue.eligibilitySubProgramCode                                               | HoosierHealthwise                                       |
      | newValue.eligibilityStartDate                                                    | <2reconEligibilityStartDate>                            |
      | newValue.eligibilityEndDate                                                      | highDate                                                |
      | newValue.eligibilityEndReason                                                    | null                                                    |
      | newValue.eligibilityCategoryCode                                                 | <eventCategoryCode>                                     |
      | newValue.eligibilityCoverageCode                                                 | cc01                                                    |
      | newValue.eligibilityExemptionCode                                                | qwer                                                    |
      | newValue.benefitStatus                                                           | Eligible                                                |
  # 6.0 Business Event Optional IN-EB Tenant-Specific Fields
      | newValue.additionalFileds.eligibilityUpdated.Managed Care Status                 | M                                                       |
      | newValue.additionalFileds.eligibilityUpdated.HIP Status                          | null                                                    |

      | newValue.additionalFileds.OtherSegmentInfos.Open Enrollment Effective Date       | 1stDayofPresentMonth                                    |
      | newValue.additionalFileds.OtherSegmentInfos.Open Enrollment End Date             | 60DaysFromFirstDayOfMonth                               |
      | newValue.additionalFileds.OtherSegmentInfos.Open Enrollment Status               | C                                                       |
      | newValue.additionalFileds.OtherSegmentInfos.Open Enrollment Lockin Complete Date | nextDayAfter60DaysFromFirstDayOfMonth                   |
      | newValue.additionalFileds.OtherSegmentInfos.Redetermination Date                 | 1stDayofPresentMonth                                    |
      | newValue.additionalFileds.OtherSegmentInfos.Debt Indicator                       | 10                                                      |
      | newValue.additionalFileds.OtherSegmentInfos.Conditional Eligibility Date         | lastDayOfThePresentYear                                 |
      | newValue.additionalFileds.OtherSegmentInfos.Benefit Effective Date               | 1stDayofPresentMonth                                    |
      | newValue.additionalFileds.OtherSegmentInfos.Benefit End Date                     | highDate                                                |
      | newValue.additionalFileds.OtherSegmentInfos.Potential Plus Indicator             | 60                                                      |
    Examples:
      | name     | eligibilityScenario                                                               | 1reconEligibilityEndDate | 2reconEligibilityStartDate | eventCategoryCode |
      # update due to CP-41142
#      | 24287-11 | First Eligibility: CHANGED END DATE, Second Eligibility: BASE AID CATEGORY CHANGE | lastDayOfThePresentYear  | firstDayOfNextYear         | 4                 |
      | 24287-11 | CHANGED END DATE | lastDayOfThePresentYear  | firstDayOfNextYear         | 10                 |


