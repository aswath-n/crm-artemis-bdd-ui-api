Feature: Eligibility Reconciliation Same Record Sent Multiple Times

  @API-CP-41142 @API-CP-41142-01 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: Create New Eligibility Segment from Latest Inbound BASE Eligibility Segment
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
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | lastDayOfThePresentYear |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode1>          |
      | subProgramTypeCode           | <subProgramTypeCode1>   |
      | eligibilityStatusCode        | <managedCareStatus1>    |
      | categoryCode                 | <categoryCode1>         |
      | coverageCode                 | cc01                    |
      | genericFieldText1            | Eligible                |
    And User provide one more eligibility details
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayofLastMonth     |
      | eligibilityEndDate           | highDate              |
      | enrollmentStartDate          | 1stDayofLastMonth     |
      | txnStatus                    | Accepted              |
      | programCode                  | <programCode2>        |
      | subProgramTypeCode           | <subProgramTypeCode2> |
      | eligibilityStatusCode        | <managedCareStatus2>  |
      | categoryCode                 | <categoryCode2>       |
      | coverageCode                 | cc01                  |
      | genericFieldText1            | Eligible              |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "<eligibilityScenario>","null"
    Then I verify benefit status information with data
      | programPopulation | <population>    |
      | benefitStatus     | <benefitStatus> |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT |
      | consumerId    | <name>.consumerId      |
      | correlationId | null                   |
      | consumerFound | true                   |
      | DPBI          |[blank]|
    Examples:
      | name     | programCode1 | programCode2 | subProgramTypeCode1 | subProgramTypeCode2 | managedCareStatus1 | managedCareStatus2 | categoryCode1 | categoryCode2 | eligibilityScenario | benefitStatus        | population    | action      | consumerAction | planSelectionAllowed |
      | 41142-01 | R            | A            | HoosierHealthwise   | HoosierCareConnect  | M                  | V                  | 10            | 10            | NO MATCH            | Eligible             | HHW-MANDATORY | Required    | Enroll         | true                 |
      | 41142-02 | A            | R            | HoosierCareConnect  | HoosierHealthwise   | V                  | M                  | 10            | 10            | NO MATCH            | Eligible             | HCC-VOLUNTARY | Required    | Enroll         | true                 |
      | 41142-03 | H            | R            | HealthyIndianaPlan  | HoosierHealthwise   | X                  | M                  | 10            | 10            | NO MATCH            | Benefit Status Error | HIP-MANDATORY | Unavailable | null           | false                |


  @API-CP-41142 @API-CP-41142-02 @API-CP-41141 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: Update Matched Eligibility Segment - INEB
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
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId      |
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                     |
      | recordId                     | 21                     |
      | eligibilityRecordId          | 21                     |
      | isEnrollmentProviderRequired | no                     |
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | enrollmentStartDate          | 1stDayofPresentMonth   |
      | txnStatus                    | Accepted               |
      | programCode                  | <programCode>          |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | eligibilityStatusCode        | <managedCareStatus>    |
      | categoryCode                 | <categoryCode>         |
      | coverageCode                 | cc01                   |
      | genericFieldText1            | <HIPStatus>            |
    And User provide one more eligibility details
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 21                |
      | eligibilityRecordId          | 21                |
      | isEnrollmentProviderRequired | no                |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | highDate          |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | R                 |
      | subProgramTypeCode           | HoosierHealthwise |
      | eligibilityStatusCode        | M                 |
      | categoryCode                 | 10                |
      | coverageCode                 | cc01              |
      | genericFieldText1            | Eligible          |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>          |
      | benefitStatus       | <benefitStatus>       |
      | eligibilityScenario | <eligibilityScenario> |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | categoryCode          | 10                     |
      | programCode           | <programCode>          |
      | subProgramTypeCode    | <subProgramTypeCode>   |
      | coverageCode          | cc01                   |
      | eligibilityStatusCode | <managedCareStatus>    |
      | exemptionCode         | qwer                   |
      | eligibilityStartDate  | <eligibilityStartDate> |
      | eligibilityEndDate    | <eligibilityEndDate>   |
      | genericFieldText1     | Eligible               |
      | updatedOn             | current                |
      | updatedBy             | 597                    |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT |
      | consumerId    | <name>.consumerId        |
      | correlationId | null                     |
      | consumerFound | true                     |
      | DPBI          |[blank]|
    Examples:
      | name     | eligibilityStartDate | eligibilityEndDate      | programCode | subProgramTypeCode | managedCareStatus | categoryCode | HIPStatus | eligibilityScenario | benefitStatus | population    | action   | consumerAction | planSelectionAllowed |
      | 41142-04 | 1stDayofPresentMonth | highDate                | R           | HoosierHealthwise  | X                 | 10           | Eligible  | EXACT MATCH         | Eligible      | HHW-MANDATORY | Required | Enroll         | true                 |
      | 41142-05 | 1stDayofLastMonth    | highDate                | R           | HoosierHealthwise  | X                 | 10           | Eligible  | CHANGED START DATE  | Eligible      | HHW-MANDATORY | Required | Enroll         | true                 |
      | 41142-06 | 1stDayofPresentMonth | lastDayOfThePresentYear | R           | HoosierHealthwise  | X                 | 10           | Eligible  | CHANGED END DATE    | Eligible      | HHW-MANDATORY | Required | Enroll         | true                 |


  @API-CP-41142 @API-CP-41142-03 @API-CP-41142-04 @API-CP-41141 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: Update Prior Program Eligibility Segment, Program Change and Add New Eligibility Segment for Program Change - INEB
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
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId      |
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                     |
      | recordId                     | 21                     |
      | eligibilityRecordId          | 21                     |
      | isEnrollmentProviderRequired | no                     |
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | enrollmentStartDate          | 1stDayofPresentMonth   |
      | txnStatus                    | Accepted               |
      | programCode                  | <programCode>          |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | eligibilityStatusCode        | <managedCareStatus>    |
      | categoryCode                 | <categoryCode>         |
      | coverageCode                 | cc01                   |
      | genericFieldText1            | <HIPStatus>            |
    And User provide one more eligibility details
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 21                |
      | eligibilityRecordId          | 21                |
      | isEnrollmentProviderRequired | no                |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | highDate          |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | R                 |
      | subProgramTypeCode           | HoosierHealthwise |
      | eligibilityStatusCode        | M                 |
      | categoryCode                 | 10                |
      | coverageCode                 | cc01              |
      | genericFieldText1            | Eligible          |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>          |
      | benefitStatus       | <benefitStatus>       |
      | eligibilityScenario | <eligibilityScenario> |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in previously created Eligibility Record
      | eligibilityStartDate  | 1stDayofPresentMonth |
      | eligibilityEndDate    | current              |
      | categoryCode          | 10                   |
      | programCode           | R                    |
      | subProgramTypeCode    | HoosierHealthwise    |
      | eligibilityStatusCode | M                    |
      | genericFieldText1     | null                 |
      | updatedOn             | current              |
      | updatedBy             | 597                  |
    Then I verify following fields are captured in the newly created Eligibility Record
      | categoryCode          | 10                     |
      | programCode           | <programCode>          |
      | subProgramTypeCode    | <subProgramTypeCode>   |
      | coverageCode          | cc01                   |
      | eligibilityStatusCode | <managedCareStatus>    |
      | exemptionCode         | qwer                   |
      | eligibilityStartDate  | <eligibilityStartDate> |
      | eligibilityEndDate    | <eligibilityEndDate>   |
      | genericFieldText1     | Eligible               |
      | createdOn             | current                |
      | createdBy             | 597                    |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT |
      | consumerId    | <name>.consumerId      |
      | correlationId | null                   |
      | consumerFound | true                   |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT |
      | consumerId    | <name>.consumerId        |
      | correlationId | null                     |
      | consumerFound | true                     |
      | DPBI          |[blank]|
    Examples:
      | name     | eligibilityStartDate | eligibilityEndDate      | programCode | subProgramTypeCode | managedCareStatus | categoryCode | HIPStatus | eligibilityScenario                         | benefitStatus        | population    | action      | consumerAction | planSelectionAllowed |
      | 41142-07 | 1stDayofPresentMonth | highDate                | A           | HoosierCareConnect | V                 | 10           | Eligible  | Program Sub-Program Change                  | Eligible             | HCC-VOLUNTARY | Required    | Enroll         | true                 |
      | 41142-08 | 1stDayofPresentMonth | lastDayOfThePresentYear | H           | HealthyIndianaPlan | X                 | 10           | Eligible  | Program Sub-Program Change With Date Change | Benefit Status Error | HIP-MANDATORY | Unavailable | null           | false                |


  @API-CP-41142 @API-CP-41142-05 @API-CP-41141 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: Base Aid Category Change - INEB
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
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId      |
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                     |
      | recordId                     | 21                     |
      | eligibilityRecordId          | 21                     |
      | isEnrollmentProviderRequired | no                     |
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | enrollmentStartDate          | 1stDayofPresentMonth   |
      | txnStatus                    | Accepted               |
      | programCode                  | <programCode>          |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | eligibilityStatusCode        | <managedCareStatus>    |
      | categoryCode                 | <categoryCode>         |
      | coverageCode                 | cc01                   |
      | genericFieldText1            | <HIPStatus>            |
    And User provide one more eligibility details
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 21                |
      | eligibilityRecordId          | 21                |
      | isEnrollmentProviderRequired | no                |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | highDate          |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | R                 |
      | subProgramTypeCode           | HoosierHealthwise |
      | eligibilityStatusCode        | M                 |
      | categoryCode                 | 10                |
      | coverageCode                 | cc01              |
      | genericFieldText1            | Eligible          |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>          |
      | benefitStatus       | <benefitStatus>       |
      | eligibilityScenario | <eligibilityScenario> |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | categoryCode          | <categoryCode>         |
      | programCode           | <programCode>          |
      | subProgramTypeCode    | <subProgramTypeCode>   |
      | coverageCode          | cc01                   |
      | eligibilityStatusCode | <managedCareStatus>    |
      | exemptionCode         | qwer                   |
      | eligibilityStartDate  | <eligibilityStartDate> |
      | eligibilityEndDate    | <eligibilityEndDate>   |
      | genericFieldText1     | Eligible               |
      | updatedOn             | current                |
      | updatedBy             | 597                    |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT |
      | consumerId    | <name>.consumerId        |
      | correlationId | null                     |
      | consumerFound | true                     |
      | DPBI          |[blank]|
    Examples:
      | name     | eligibilityStartDate | eligibilityEndDate | programCode | subProgramTypeCode | managedCareStatus | categoryCode | HIPStatus | eligibilityScenario  | benefitStatus | population    | action   | consumerAction | planSelectionAllowed |
      | 41142-09 | 1stDayofPresentMonth | highDate           | R           | HoosierHealthwise  | M                 | 4            | Eligible  | Base Category Change | Eligible      | HHW-MANDATORY | Required | Enroll         | true                 |

  @API-CP-41141 @API-CP-41141-01 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: A.C 3.0- CP-41141 2 Eligibility Segments, Eligibility Segment Recon Check
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
      | eligibilityStartDate         | 1stDayof2MonthsBefore   |
      | eligibilityEndDate           | highDate                |
      | programCode                  | H                       |
      | subProgramTypeCode           | HealthyIndianaPlan      |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
      | genericFieldText1            | <HIPstatus>             |
      | fileSource                   | HRECIP                  |
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
    # 3.0 Eligibility Segment Reconciliation Data Check
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId            |
      | isEligibilityRequired        | yes                          |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                           |
      | recordId                     | 21                           |
      | eligibilityRecordId          | 21                           |
      | isEnrollmentProviderRequired | no                           |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth         |
      | eligibilityEndDate           | highDate                     |
      | programCode                  | H                            |
      | subProgramTypeCode           | HealthyIndianaPlan           |
      | eligibilityStatusCode        | <reconeligibilityStatusCode> |
      | categoryCode                 | 10                           |
      | coverageCode                 | cc01                         |
      | genericFieldText1            | <HipStatus1>                 |
      | fileSource                   | <fileSource>                 |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth |
      | endDate             | highDate             |
      | segmentDetailValue1 | 10                   |
      | segmentDetailValue2 | 60                   |
      | segmentDetailValue3 | <redetermDate>::     |
      | segmentDetailValue4 | highDate::           |
      | segmentTypeCode     | OTHER                |
    And User provide one more eligibility details
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | current                 |
      | eligibilityEndDate           | lastDayOf2MonthsFromNow |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | genericFieldText1            | <HipStatus1>            |
      | coverageCode                 | cc01                    |
      | fileSource                   | RECIPIENT               |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
#    And I run create Eligibility and Enrollment API
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | programCode           | H                            |
      | subProgramTypeCode    | HealthyIndianaPlan           |
      | eligibilityStatusCode | <reconeligibilityStatusCode> |
      | eligibilityStartDate  | 1stDayofPresentMonth         |
      | eligibilityEndDate    | highDate                     |
      | genericFieldText1     | <ExpectedHipStatus>          |
      | Updated On            | current                      |
      | updatedBy             | 597                          |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | benefitStatus       | Benefit Status Error |
      | population          | <population>         |
      | eligibilityScenario | CHANGED START DATE   |
      | timeframe           | Active               |
#      | programPopulation   | HIP-Voluntary        |
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
      | name    | fileSource       | HIPstatus | redetermDate          | startDate             | endDate                 | segmentDetailValue1 | segmentDetailValue2 | segmentDetailValue3   | segmentDetailValue4     | eligibilityStatusCode | programCode | subProgramTypeCode | reconeligibilityStatusCode | population    | HipStatus1    | ExpectedHipStatus |
      | 41141-1 | HRECIP           | Eligible  | 1stDayofPresentMonth  | 1stDayofPresentMonth  | highDate                | 10                  | 60                  | 1stDayof2MonthsBefore | highDate                | V                     | A           | HoosierCareConnect | V                          | HIP-Voluntary | Denied        | Denied            |
      | 41141-2 | RECIPIENT        | Denied    | 1stDayofPresentMonth  | 1stDayof2MonthsBefore | lastDayOfThePresentYear | 1                   | 6                   | 1stDayofPresentMonth  | lastDayOfThePresentYear | M                     | R           | HoosierHealthwise  | X                          | HIP-Mandatory | Eligible      | Denied            |
      | 41141-3 | RECIPIENT_HRECIP | Eligible  | 1stDayofPresentMonth  | 1stDayof2MonthsBefore | lastDayOfThePresentYear | 1                   | 6                   | 1stDayofPresentMonth  | lastDayOfThePresentYear | M                     | A           | HoosierHealthwise  | X                          | HIP-Mandatory | Conditional   | Conditional       |
      | 41141-4 | RECIPIENT_HRECIP | Eligible  | 1stDayof2MonthsBefore | 1stDayofPresentMonth  | highDate                | 10                  | 60                  | 1stDayof2MonthsBefore | highDate                | X                     | A           | HoosierCareConnect | V                          | HIP-Voluntary | PotentialPlus | PotentialPlus     |
      | 41141-5 | RECIPIENT_HRECIP | Eligible  | 1stDayof2MonthsBefore | 1stDayofPresentMonth  | highDate                | 10                  | 60                  | 1stDayof2MonthsBefore | highDate                | X                     | H           | HealthyIndianaPlan | M                          | HIP-Mandatory | Pending       | Pending           |

  @API-CP-41141 @API-CP-41141-02 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: A.C 3.0- CP-41141 2 Eligibility Segments, Run multiple times - No new segment created
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
      | genericFieldText1            | <HIPstatus>           |
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
    # 3.0 Eligibility Segment Reconciliation Data Check
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId            |
      | isEligibilityRequired        | yes                          |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                           |
      | recordId                     | 21                           |
      | eligibilityRecordId          | 21                           |
      | isEnrollmentProviderRequired | no                           |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth         |
      | eligibilityEndDate           | highDate                     |
      | programCode                  | H                            |
      | subProgramTypeCode           | HealthyIndianaPlan           |
      | eligibilityStatusCode        | <reconeligibilityStatusCode> |
      | categoryCode                 | 10                           |
      | coverageCode                 | cc01                         |
      | genericFieldText1            | <HipStatus1>                 |
      | fileSource                   | <fileSource>                 |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth |
      | endDate             | highDate             |
      | segmentDetailValue1 | 10                   |
      | segmentDetailValue2 | 60                   |
      | segmentDetailValue3 | <redetermDate>::     |
      | segmentDetailValue4 | highDate::           |
      | segmentTypeCode     | OTHER                |
    And User provide one more eligibility details
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | current                 |
      | eligibilityEndDate           | lastDayOf2MonthsFromNow |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | genericFieldText1            | <HipStatus1>            |
      | coverageCode                 | cc01                    |
      | fileSource                   | RECIPIENT               |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | programCode           | H                            |
      | subProgramTypeCode    | HealthyIndianaPlan           |
      | eligibilityStatusCode | <reconeligibilityStatusCode> |
      | eligibilityStartDate  | 1stDayofPresentMonth         |
      | eligibilityEndDate    | highDate                     |
      | genericFieldText1     | <HipStatus1>                 |
      | Updated On            | current                      |
      | updatedBy             | 597                          |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | benefitStatus       | Benefit Status Error |
      | population          | <population>         |
      | eligibilityScenario | CHANGED START DATE   |
      | timeframe           | Active               |
#      | programPopulation   | HIP-Voluntary        |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId            |
      | isEligibilityRequired        | yes                          |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                           |
      | recordId                     | 21                           |
      | eligibilityRecordId          | 21                           |
      | isEnrollmentProviderRequired | no                           |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth         |
      | eligibilityEndDate           | highDate                     |
      | programCode                  | H                            |
      | subProgramTypeCode           | HealthyIndianaPlan           |
      | eligibilityStatusCode        | <reconeligibilityStatusCode> |
      | categoryCode                 | 10                           |
      | coverageCode                 | cc01                         |
      | genericFieldText1            | <HipStatus1>                 |
      | fileSource                   | <fileSource>                 |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth |
      | endDate             | highDate             |
      | segmentDetailValue1 | 10                   |
      | segmentDetailValue2 | 60                   |
      | segmentDetailValue3 | <redetermDate>::     |
      | segmentDetailValue4 | highDate::           |
      | segmentTypeCode     | OTHER                |
    And User provide one more eligibility details
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | current                 |
      | eligibilityEndDate           | lastDayOf2MonthsFromNow |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | genericFieldText1            | <HipStatus1>            |
      | coverageCode                 | cc01                    |
      | fileSource                   | RECIPIENT               |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId            |
      | isEligibilityRequired        | yes                          |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                           |
      | recordId                     | 21                           |
      | eligibilityRecordId          | 21                           |
      | isEnrollmentProviderRequired | no                           |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth         |
      | eligibilityEndDate           | highDate                     |
      | programCode                  | H                            |
      | subProgramTypeCode           | HealthyIndianaPlan           |
      | eligibilityStatusCode        | <reconeligibilityStatusCode> |
      | categoryCode                 | 10                           |
      | coverageCode                 | cc01                         |
      | genericFieldText1            | <HipStatus1>                 |
      | fileSource                   | <fileSource>                 |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth |
      | endDate             | highDate             |
      | segmentDetailValue1 | 10                   |
      | segmentDetailValue2 | 60                   |
      | segmentDetailValue3 | <redetermDate>::     |
      | segmentDetailValue4 | highDate::           |
      | segmentTypeCode     | OTHER                |
    And User provide one more eligibility details
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | current                 |
      | eligibilityEndDate           | lastDayOf2MonthsFromNow |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | genericFieldText1            | Pending                 |
      | coverageCode                 | cc01                    |
      | fileSource                   | RECIPIENT               |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
#    And I run create Eligibility and Enrollment API
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | programCode           | H                            |
      | subProgramTypeCode    | HealthyIndianaPlan           |
      | eligibilityStatusCode | <reconeligibilityStatusCode> |
      | eligibilityStartDate  | 1stDayofPresentMonth         |
      | eligibilityEndDate    | highDate                     |
      | genericFieldText1     | <HipStatus1>                 |
      | Updated On            | current                      |
      | updatedBy             | 597                          |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | benefitStatus       | Benefit Status Error |
      | population          | <population>         |
      | eligibilityScenario | EXACT MATCH          |
      | timeframe           | Active               |
#      | programPopulation   | HIP-Voluntary        |
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
      | name      | fileSource | HIPstatus | redetermDate         | startDate            | endDate  | segmentDetailValue1 | segmentDetailValue2 | segmentDetailValue3   | segmentDetailValue4 | eligibilityStatusCode | programCode | subProgramTypeCode | reconeligibilityStatusCode | population    | HipStatus1 |
      | 41141-2-1 | HRECIP     | Eligible  | 1stDayofPresentMonth | 1stDayofPresentMonth | highDate | 10                  | 60                  | 1stDayof2MonthsBefore | highDate            | V                     | A           | HoosierCareConnect | V                          | HIP-Voluntary | Denied     |


  @API-CP-41141 @API-CP-41141-03 @API-EE-IN @IN-EB-API-Regression
  Scenario Outline: Verify Recon scenario for Eligibility INEB HHW exact Match and Changed Start Date -multiple records
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
    And User provide one more eligibility details
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | current                 |
      | eligibilityEndDate           | lastDayOf2MonthsFromNow |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | genericFieldText1            | Pending                 |
      | coverageCode                 | cc01                    |
      | fileSource                   | RECIPIENT               |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | eligibilityScenario | <benefitStatus> |
#      | timeframe           | FUTURE                                      |
#      | programPopulation   | HCC-Voluntary                               |
#    Then I verify benefit status records are displayed for the consumer "<name>.consumerId"
#    Then I verify scenario output in the benefit status records are displayed as "<benefitStatus>","null"
    # 4.0 Update Matched Eligibility Segment, Two Inbound Eligibility Segments, Aid-Category Change or Add
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
      | name       | reconEligibilityStartDate | reconEligibilityEndDate | benefitStatus              | programCode | reconProgramCode | subProgramTypeCode | reconSubProgramTypeCode | reconCategoryCode | eligibilityStatusCode | reconEligibilityStatusCode | population    | action   |
      | 41141-03-1 | 1stDayofPresentMonth      | highDate                | EXACT MATCH                | R           | R                | HoosierHealthwise  | HoosierHealthwise       | 10                | M                     | V                          | HHW-Voluntary | Required |
      | 41141-03-2 | 1stDayofPresentMonth      | highDate                | program sub-program change | R           | A                | HoosierHealthwise  | HoosierCareConnect      | 10                | M                     | M                          | HHW-Voluntary | Required |


  @API-CP-41143 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: IN-EB ELIGIBILITY Same Record Sent Multiple Times MMIS Sends Reconciliation (Part 3 Create or Update)
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
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | <eligibilityStartDate1>  |
      | eligibilityEndDate           | highDate                 |
      | programCode                  | <programCode1>           |
      | subProgramTypeCode           | <subProgramTypeCode1>    |
      | eligibilityStatusCode        | <eligibilityStatusCode1> |
      | categoryCode                 | 10                       |
      | coverageCode                 | cc01                     |
      | genericFieldText1            | <HipStatus1>             |
      | fileSource                   | <fileSource1>            |
    And User provide other eligibility segments details
      | startDate           | <aidStartDate1> |
      | endDate             | highDate        |
      | segmentDetailValue1 | 5               |
      | segmentDetailValue2 | null            |
      | segmentDetailValue3 | null            |
      | segmentDetailValue4 | null            |
      | segmentTypeCode     | AID_CATEGORY    |
    And User provide other eligibility segments details
      | startDate           | <aidStartDate1> |
      | endDate             | highDate        |
      | segmentDetailValue1 | 7               |
      | segmentDetailValue2 | null            |
      | segmentDetailValue3 | null            |
      | segmentDetailValue4 | null            |
      | segmentTypeCode     | AID_CATEGORY    |
    And User provide one more eligibility details
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                       |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | isEnrollmentProviderRequired | no                       |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | current                  |
      | eligibilityEndDate           | lastDayOf2MonthsFromNow  |
      | programCode                  | <programCode1>           |
      | subProgramTypeCode           | <subProgramTypeCode1>    |
      | eligibilityStatusCode        | <eligibilityStatusCode1> |
      | categoryCode                 | 10                       |
      | genericFieldText1            | <HipStatus1>             |
      | coverageCode                 | cc01                     |
      | fileSource                   | <fileSource1>            |
    And User provide other eligibility segments details
      | startDate           | <aidStartDate1> |
      | endDate             | highDate        |
      | segmentDetailValue1 | 12              |
      | segmentDetailValue2 | null            |
      | segmentDetailValue3 | null            |
      | segmentDetailValue4 | null            |
      | segmentTypeCode     | AID_CATEGORY    |
    Then I send API call with name "<name>" for create Eligibility and Enrollment
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | <eligibilityStartDate1>  |
      | eligibilityEndDate    | highDate                 |
      | categoryCode          | 10                       |
      | programCode           | <programCode1>           |
      | subProgramTypeCode    | <subProgramTypeCode1>    |
      | eligibilityStatusCode | <eligibilityStatusCode1> |
      | genericFieldText1     | Eligible                 |
      | genericFieldText2     | <checkCore1>             |
      | createdOn             | current                  |
      | createdBy             | 597                      |
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "<name>.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console  |
      | [0].consumerId                | <name>.consumerId |
      | [0].segmentTypeCode           | AID_CATEGORY      |
      | [0].startDate                 | <aidStartDate1>   |
      | [0].endDate                   | highDate          |
      | [0].segmentDetailValue1       | 5                 |
      | [0].segmentDetailValue2       | null              |
      | [0].segmentDetailValue3       | null              |
      | [0].segmentDetailValue4       | null              |
      | [0].segmentDetailValue5       | null              |
      | [0].segmentDetailValue6       | null              |
      | [0].segmentDetailValue7       | null              |
      | [0].segmentDetailValue8       | null              |
      | [0].segmentDetailValue9       | null              |
      | [0].segmentDetailValue10      | null              |
      | [0].createdOn                 | current           |
      | [0].createdBy                 | null              |
      | [0].updatedOn                 | current           |
      | [0].updatedBy                 | null              |
      | [0].correlationId             | null              |
      | [0].uiid                      | null              |
      # * * * * * * *
      | [1].otherEligbilitySegmentsId | print in console  |
      | [1].consumerId                | <name>.consumerId |
      | [1].segmentTypeCode           | AID_CATEGORY      |
      | [1].startDate                 | <aidStartDate1>   |
      | [1].endDate                   | highDate          |
      | [1].segmentDetailValue1       | 7                 |
      | [1].segmentDetailValue2       | null              |
      | [1].segmentDetailValue3       | null              |
      | [1].segmentDetailValue4       | null              |
      | [1].segmentDetailValue5       | null              |
      | [1].segmentDetailValue6       | null              |
      | [1].segmentDetailValue7       | null              |
      | [1].segmentDetailValue8       | null              |
      | [1].segmentDetailValue9       | null              |
      | [1].segmentDetailValue10      | null              |
      | [1].createdOn                 | current           |
      | [1].createdBy                 | null              |
      | [1].updatedOn                 | current           |
      | [1].updatedBy                 | null              |
      | [1].correlationId             | null              |
      | [1].uiid                      | null              |
    And Wait for 3 seconds
    Then I will verify business events are generated with data
      | eventName     | OTHER_ELIGIBILITY_SAVE_EVENT |
      | consumerId    | <name>.consumerId            |
      | correlationId | null                         |
      | consumerFound | true                         |
      | DPBI          |[blank]|
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                       |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | isEnrollmentProviderRequired | no                       |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | <eligibilityStartDate1>  |
      | eligibilityEndDate           | highDate                 |
      | programCode                  | <programCode1>           |
      | subProgramTypeCode           | <subProgramTypeCode1>    |
      | eligibilityStatusCode        | <eligibilityStatusCode1> |
      | categoryCode                 | 10                       |
      | coverageCode                 | cc01                     |
      | genericFieldText1            | <HipStatus1>             |
      | fileSource                   | <fileSource1>            |
    And User provide other eligibility segments details
      | startDate           | <aidStartDate2> |
      | endDate             | highDate        |
      | segmentDetailValue1 | 5               |
      | segmentDetailValue2 | null            |
      | segmentDetailValue3 | null            |
      | segmentDetailValue4 | null            |
      | segmentTypeCode     | AID_CATEGORY    |
    And User provide other eligibility segments details
      | startDate           | <aidStartDate1> |
      | endDate             | highDate        |
      | segmentDetailValue1 | J               |
      | segmentDetailValue2 | null            |
      | segmentDetailValue3 | null            |
      | segmentDetailValue4 | null            |
      | segmentTypeCode     | AID_CATEGORY    |
    And User provide one more eligibility details
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                       |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | isEnrollmentProviderRequired | no                       |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | current                  |
      | eligibilityEndDate           | lastDayOf2MonthsFromNow  |
      | programCode                  | <programCode1>           |
      | subProgramTypeCode           | <subProgramTypeCode1>    |
      | eligibilityStatusCode        | <eligibilityStatusCode1> |
      | categoryCode                 | 10                       |
      | genericFieldText1            | <HipStatus1>             |
      | coverageCode                 | cc01                     |
      | fileSource                   | <fileSource1>            |
    And User provide other eligibility segments details
      | startDate           | <aidStartDate1> |
      | endDate             | highDate        |
      | segmentDetailValue1 | 12              |
      | segmentDetailValue2 | null            |
      | segmentDetailValue3 | null            |
      | segmentDetailValue4 | null            |
      | segmentTypeCode     | AID_CATEGORY    |
    Then I send API call with name "<name>" for create Eligibility and Enrollment
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | <eligibilityStartDate1>  |
      | eligibilityEndDate    | highDate                 |
      | categoryCode          | 10                       |
      | programCode           | <programCode1>           |
      | subProgramTypeCode    | <subProgramTypeCode1>    |
      | eligibilityStatusCode | <eligibilityStatusCode1> |
      | genericFieldText1     | Eligible                 |
      | genericFieldText2     | <checkCore2>             |
      | createdOn             | current                  |
      | createdBy             | 597                      |
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "<name>.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console  |
      | [0].consumerId                | <name>.consumerId |
      | [0].segmentTypeCode           | AID_CATEGORY      |
      | [0].startDate                 | <aidStartDate1>   |
      | [0].endDate                   | highDate          |
      | [0].segmentDetailValue1       | 7                 |
      | [0].segmentDetailValue2       | null              |
      | [0].segmentDetailValue3       | null              |
      | [0].segmentDetailValue4       | null              |
      | [0].segmentDetailValue5       | null              |
      | [0].segmentDetailValue6       | null              |
      | [0].segmentDetailValue7       | null              |
      | [0].segmentDetailValue8       | null              |
      | [0].segmentDetailValue9       | null              |
      | [0].segmentDetailValue10      | null              |
      | [0].createdOn                 | current           |
      | [0].createdBy                 | null              |
      | [0].updatedOn                 | current           |
      | [0].updatedBy                 | null              |
      | [0].correlationId             | null              |
      | [0].uiid                      | null              |
      # * * * * * * *
      | [1].otherEligbilitySegmentsId | print in console  |
      | [1].consumerId                | <name>.consumerId |
      | [1].segmentTypeCode           | AID_CATEGORY      |
      | [1].startDate                 | <aidStartDate1>   |
      | [1].endDate                   | highDate          |
      | [1].segmentDetailValue1       | J                 |
      | [1].segmentDetailValue2       | null              |
      | [1].segmentDetailValue3       | null              |
      | [1].segmentDetailValue4       | null              |
      | [1].segmentDetailValue5       | null              |
      | [1].segmentDetailValue6       | null              |
      | [1].segmentDetailValue7       | null              |
      | [1].segmentDetailValue8       | null              |
      | [1].segmentDetailValue9       | null              |
      | [1].segmentDetailValue10      | null              |
      | [1].createdOn                 | current           |
      | [1].createdBy                 | null              |
      | [1].updatedOn                 | current           |
      | [1].updatedBy                 | null              |
      | [1].correlationId             | null              |
      | [1].uiid                      | null              |
      # * * * * * * *
      | [2].otherEligbilitySegmentsId | print in console  |
      | [2].consumerId                | <name>.consumerId |
      | [2].segmentTypeCode           | AID_CATEGORY      |
      | [2].startDate                 | <aidStartDate2>   |
      | [2].endDate                   | highDate          |
      | [2].segmentDetailValue1       | 5                 |
      | [2].segmentDetailValue2       | null              |
      | [2].segmentDetailValue3       | null              |
      | [2].segmentDetailValue4       | null              |
      | [2].segmentDetailValue5       | null              |
      | [2].segmentDetailValue6       | null              |
      | [2].segmentDetailValue7       | null              |
      | [2].segmentDetailValue8       | null              |
      | [2].segmentDetailValue9       | null              |
      | [2].segmentDetailValue10      | null              |
      | [2].createdOn                 | current           |
      | [2].createdBy                 | null              |
      | [2].updatedOn                 | current           |
      | [2].updatedBy                 | 597               |
      | [2].correlationId             | null              |
      | [2].uiid                      | null              |
    And Wait for 3 seconds
    Then I will verify business events are generated with data
      | eventName     | OTHER_ELIGIBILITY_UPDATE_EVENT |
      | consumerId    | <name>.consumerId              |
      | correlationId | null                           |
      | consumerFound | true                           |
      | DPBI          |[blank]|
    Examples:
      | name     | eligibilityStartDate1 | programCode1 | subProgramTypeCode1 | eligibilityStatusCode1 | HipStatus1 | fileSource1      | aidStartDate1        | aidStartDate2 | checkCore1 | checkCore2 |
      | 41143-01 | 1stDayofPresentMonth  | R            | HoosierHealthwise   | M                      | Eligible   | RECIPIENT_HRECIP | 1stDayofPresentMonth | current       | 0          | 2          |
      | 41143-02 | 1stDayofPresentMonth  | A            | HoosierCareConnect  | M                      | Eligible   | RECIPIENT_HRECIP | 1stDayofPresentMonth | current       | 0          | 2          |
      | 41143-03 | 1stDayofPresentMonth  | H            | HealthyIndianaPlan  | M                      | Eligible   | HRECIP           | 1stDayofPresentMonth | current       | 0          | 2          |