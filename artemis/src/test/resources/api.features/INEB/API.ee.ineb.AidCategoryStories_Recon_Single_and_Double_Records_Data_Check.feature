Feature: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 2 Create or Update)

  @API-CP-30871 @API-CP-30871-01 @API-CP-30040 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario Outline: IN-EB ELIGIBILITY: BASE and ADDED Aid Category Codes: MMIS Sends Reconciliation (Part 2 Create or Update) 1.0 10.0 (Non HIP)
  1.0 One Inbound BASE Segment: Create New Eligibility Segment from BASE Eligibility Segment
  10.0 Set the “Check CORE Indicator” when Conditions are Found
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
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | genericFieldText1            | Eligible                |
      | coverageCode                 | cc01                    |
      | fileSource                   | RECIPIENT               |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | <name>.consumerId        |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | 1stDayofPresentMonth    |
      | eligibilityEndDate    | highDate                |
      | categoryCode          | 10                      |
      | programCode           | <programCode>           |
      | subProgramTypeCode    | <subProgramTypeCode>    |
      | eligibilityStatusCode | <eligibilityStatusCode> |
      | genericFieldText1     | Eligible                |
      | genericFieldText2     | <chechCore>             |
      | createdOn             | current                 |
      | createdBy             | 597                     |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population> |
      | population          | <population> |
      | benefitStatus       | Eligible     |
      | eligibilityScenario | NO MATCH     |
      | timeframe           | Active       |
    Then I Verify Consumer Actions as following data
      | [0].action               | Required             |
      | [0].consumerAction       | Enroll               |
      | [0].planSelectionAllowed | true                 |
      | [0].changeAllowedFrom    | 1stDayofPresentMonth |
      | [0].changeAllowedUntil   | <changeAllowedUntil> |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                                  |
      | consumerId    | <name>.consumerId                                       |
      | correlationId | <name>a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                    |
      | DPBI          |[blank]|
    Examples: Different populations
      | name    | programCode | subProgramTypeCode | eligibilityStatusCode | chechCore | population    | changeAllowedUntil        |
      | 30871-1 | A           | HoosierCareConnect | M                     | 0         | HCC-Mandatory | 60DaysFromFirstDayOfMonth |
      | 30871-2 | A           | HoosierCareConnect | V                     | 0         | HCC-Voluntary | 60DaysFromFirstDayOfMonth |
      | 30871-3 | A           | HoosierCareConnect | X                     | 1         | HCC-Mandatory | 60DaysFromFirstDayOfMonth |
      | 30871-4 | R           | HoosierHealthwise  | M                     | 0         | HHW-Mandatory | 14DayFromFirstDayOfMonth  |
      | 30871-5 | R           | HoosierHealthwise  | V                     | 0         | HHW-Voluntary | 14DayFromFirstDayOfMonth  |
      | 30871-6 | R           | HoosierHealthwise  | X                     | 1         | HHW-Mandatory | 14DayFromFirstDayOfMonth  |


  @API-CP-30871 @API-CP-30871-01 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario Outline: IN-EB ELIGIBILITY: BASE and ADDED Aid Category Codes: MMIS Sends Reconciliation (Part 2 Create or Update) 1.0 10.0 (HIP)
  1.0 One Inbound BASE Segment: Create New Eligibility Segment from BASE Eligibility Segment
  10.0 Set the “Check CORE Indicator” when Conditions are Found
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
      | genericFieldText1            | Eligible                |
      | coverageCode                 | cc01                    |
      | fileSource                   | HRECIP                  |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | <name>.consumerId        |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
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
      | enrollmentEndDate            | highDate                |
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
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | 1stDayofPresentMonth    |
      | eligibilityEndDate    | highDate                |
      | categoryCode          | 10                      |
      | programCode           | H                       |
      | subProgramTypeCode    | HealthyIndianaPlan      |
      | eligibilityStatusCode | <eligibilityStatusCode> |
      | genericFieldText1     | Eligible                |
      | genericFieldText2     | <chechCore>             |
      | createdOn             | current                 |
      | createdBy             | 597                     |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population> |
      | population          | <population> |
      | benefitStatus       | Enrolled     |
      | eligibilityScenario | NO MATCH     |
      | timeframe           | Active       |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available                   |
      | [0].consumerAction       | Plan Change Open Enrollment |
      | [0].planSelectionAllowed | true                        |
      | [0].changeAllowedFrom    | openEnrollmentDay           |
      | [0].changeAllowedUntil   | Dec15thCurrentYear          |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                                  |
      | consumerId    | <name>.consumerId                                       |
      | correlationId | <name>a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                    |
      | DPBI          |[blank]|
    Examples: Different populations
      | name     | eligibilityStatusCode | chechCore | population    |
      | 30871-7  | null                  | 1         | HIP-Mandatory |
      | 30871-8  | M                     | 0         | HIP-Mandatory |
      | 30871-9  | V                     | 0         | HIP-Voluntary |
      | 30871-10 | X                     | 1         | HIP-Mandatory |


  @API-CP-30871 @API-CP-30871-02 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario Outline: IN-EB ELIGIBILITY: BASE and ADDED Aid Category Codes: MMIS Sends Reconciliation (Part 2 Create or Update) 2.0 10.0 (Non HIP)
  2.0 One Inbound BASE Segment: Update Matched Eligibility Segment
  10.0 Set the “Check CORE Indicator” when Conditions are Found
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
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | <name>.consumerId        |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | <eligibilityStartDate>  |
      | eligibilityEndDate           | <eligibilityEndDate>    |
      | programCode                  | A                       |
      | subProgramTypeCode           | HoosierCareConnect      |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | genericFieldText1            | Eligible                |
      | coverageCode                 | cc01                    |
      | fileSource                   | RECIPIENT               |
    And I run create Eligibility and Enrollment API
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | <eligibilityStartDate>  |
      | eligibilityEndDate    | <eligibilityEndDate>    |
      | categoryCode          | 10                      |
      | programCode           | A                       |
      | subProgramTypeCode    | HoosierCareConnect      |
      | eligibilityStatusCode | <eligibilityStatusCode> |
      | genericFieldText1     | Eligible                |
      | genericFieldText2     | <chechCore>             |
      | createdOn             | current                 |
      | createdBy             | 597                     |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>    |
      | population          | <population>    |
      | benefitStatus       | <benefitStatus> |
      | eligibilityScenario | <scenario>      |
      | timeframe           | Active          |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil>   |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                                |
      | consumerId    | <name>.consumerId                                       |
      | correlationId | <name>a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                    |
      | DPBI          |[blank]|
    Examples: Different scenarios
      | name     | scenario           | eligibilityStartDate  | eligibilityEndDate | eligibilityStatusCode | chechCore | population    | benefitStatus | action   | consumerAction | planSelectionAllowed | changeAllowedFrom     | changeAllowedUntil              |
      | 30871-11 | EXACT MATCH        | 1stDayofPresentMonth  | highDate           | V                     | 0         | HCC-Voluntary | Eligible      | Required | Enroll         | true                 | 1stDayofPresentMonth  | 60DaysFromFirstDayOfMonth       |
      | 30871-12 | CHANGED START DATE | 1stDayof2MonthsBefore | highDate           | X                     | 1         | HCC-Mandatory | Eligible      | Required | Enroll         | true                 | 1stDayof2MonthsBefore | 60DaysFrom1stDayof2MonthsBefore |
#     After CP-32018 implemented benefit status updated to Eligible instead of Benefit Status Error with eligibility non-high date
      | 30871-13 | CHANGED END DATE   | 1stDayofPresentMonth  | futureDate         | M                     | 0         | HCC-Mandatory | Eligible      | Required | Enroll         | true                 | 1stDayofPresentMonth  | 60DaysFromFirstDayOfMonth       |


  @API-CP-30871 @API-CP-30871-02 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario Outline: IN-EB ELIGIBILITY: BASE and ADDED Aid Category Codes: MMIS Sends Reconciliation (Part 2 Create or Update) 2.0 10.0 (HIP)
  2.0 One Inbound BASE Segment: Update Matched Eligibility Segment
  10.0 Set the “Check CORE Indicator” when Conditions are Found
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
      | genericFieldText1            | null                 |
      | coverageCode                 | cc01                 |
      | fileSource                   | HRECIP               |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | <eligibilityStartDate>  |
      | eligibilityEndDate           | <eligibilityEndDate>    |
      | programCode                  | H                       |
      | subProgramTypeCode           | HealthyIndianaPlan      |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | genericFieldText1            | <HIPstatus>             |
      | coverageCode                 | cc01                    |
      | fileSource                   | <fileSource>            |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | <eligibilityStartDate>  |
      | eligibilityEndDate           | <eligibilityEndDate>    |
      | enrollmentStartDate          | <eligibilityStartDate>  |
      | enrollmentEndDate            | <eligibilityEndDate>    |
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
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | <eligibilityStartDate>  |
      | eligibilityEndDate    | <eligibilityEndDate>    |
      | categoryCode          | 10                      |
      | programCode           | H                       |
      | subProgramTypeCode    | HealthyIndianaPlan      |
      | eligibilityStatusCode | <eligibilityStatusCode> |
      | genericFieldText1     | <HIPstatus>             |
      | genericFieldText2     | <chechCore>             |
      | createdOn             | current                 |
      | createdBy             | 597                     |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>    |
      | population          | <population>    |
      | benefitStatus       | <benefitStatus> |
      | eligibilityScenario | <scenario>      |
      | timeframe           | Active          |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil>   |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                                |
      | consumerId    | <name>.consumerId                                       |
      | correlationId | <name>a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                    |
      | DPBI          |[blank]|
    Examples: Different populations
      | name     | scenario           | eligibilityStartDate  | eligibilityEndDate | eligibilityStatusCode | HIPstatus      | fileSource       | chechCore | population    | benefitStatus | action      | consumerAction              | planSelectionAllowed | changeAllowedFrom | changeAllowedUntil |
#     After CP-32018 implemented benefit status updated to Eligible instead of Benefit Status Error with eligibility non-high date
      | 30871-14 | CHANGED END DATE   | 1stDayofPresentMonth  | futureDate         | M                     | Eligible       | HRECIP           | 0         | HIP-Mandatory | Enrolled      | Available   | Plan Change Open Enrollment | true                 | openEnrollmentDay | Dec15thCurrentYear |
      | 30871-15 | EXACT MATCH        | 1stDayofPresentMonth  | highDate           | M                     | Eligible       | RECIPIENT_HRECIP | 0         | HIP-Mandatory | Enrolled      | Available   | Plan Change Open Enrollment | true                 | openEnrollmentDay | Dec15thCurrentYear |
      | 30871-16 | CHANGED START DATE | 1stDayof2MonthsBefore | highDate           | V                     | Potential Plus | RECIPIENT_HRECIP | 0         | HIP-Voluntary | Enrolled      | Unavailable | null                        | false                | null              | null               |
      | 30871-17 | CHANGED START DATE | 1stDayof2MonthsBefore | highDate           | X                     | Eligible       | HRECIP           | 1         | HIP-Mandatory | Enrolled      | Available   | Plan Change Open Enrollment | true                 | openEnrollmentDay | Dec15thCurrentYear |
      | 30871-18 | CHANGED START DATE | 1stDayof2MonthsBefore | highDate           | null                  | Eligible       | HRECIP           | 1         | HIP-Mandatory | Enrolled      | Available   | Plan Change Open Enrollment | true                 | openEnrollmentDay | Dec15thCurrentYear |


  @API-CP-30871 @API-CP-30871-03 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario: IN-EB ELIGIBILITY: BASE and ADDED Aid Category Codes: MMIS Sends Reconciliation (Part 2 Create or Update) 3.0 4.0 (Non HIP)
  3.0 One Inbound BASE Segment Update Matched Eligibility Segment, Program Change
  4.0 One Inbound BASE Segment Add New Eligibility Segment for Program Change
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30871-19 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30871-19.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | genericFieldText1            | Eligible              |
      | coverageCode                 | cc01                  |
      | fileSource                   | RECIPIENT             |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 30871-19.consumerId      |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    Then I send API call with name "30871-19a" for create Eligibility and Enrollment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30871-19.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | V                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    Then I send API call with name "30871-19b" for create Eligibility and Enrollment
    When I initiated get eligibility by consumer id "30871-19.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in previously created Eligibility Record
      | eligibilityStartDate  | 1stDayof2MonthsBefore |
      | eligibilityEndDate    | current               |
      | categoryCode          | 10                    |
      | programCode           | R                     |
      | subProgramTypeCode    | HoosierHealthwise     |
      | eligibilityStatusCode | M                     |
      | genericFieldText1     | Eligible              |
      | genericFieldText2     | 0                     |
      | createdOn             | current               |
      | createdBy             | 597                   |
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | 1stDayofPresentMonth |
      | eligibilityEndDate    | highDate             |
      | categoryCode          | 10                   |
      | programCode           | A                    |
      | subProgramTypeCode    | HoosierCareConnect   |
      | eligibilityStatusCode | V                    |
      | genericFieldText1     | Eligible             |
      | genericFieldText2     | 0                    |
      | createdOn             | current              |
      | createdBy             | 597                  |
    And I initiated get benefit status by consumer id "30871-19.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | HCC-Voluntary                               |
      | population          | HCC-Voluntary                               |
      | benefitStatus       | Eligible                                    |
      | eligibilityScenario | Program Sub-Program Change With Date Change |
      | timeframe           | Active                                      |
    Then I Verify Consumer Actions as following data
      | [0].action               | Required                  |
      | [0].consumerAction       | Enroll                    |
      | [0].planSelectionAllowed | true                      |
      | [0].changeAllowedFrom    | 1stDayofPresentMonth      |
      | [0].changeAllowedUntil   | 60DaysFromFirstDayOfMonth |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                                    |
      | consumerId    | 30871-19.consumerId                                       |
      | correlationId | 30871-19a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                      |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                                  |
      | consumerId    | 30871-19.consumerId                                       |
#      | correlationId | 30871-19b.eligibilities.[0].coreEligibility.correlationId |
      | correlationId | null |
      | consumerFound | true                                                      |
      | DPBI          |[blank]|


  @API-CP-30871 @API-CP-30871-03 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario: IN-EB ELIGIBILITY: BASE and ADDED Aid Category Codes: MMIS Sends Reconciliation (Part 2 Create or Update) 3.0 4.0 (HIP)
  3.0 One Inbound BASE Segment Update Matched Eligibility Segment, Program Change
  4.0 One Inbound BASE Segment Add New Eligibility Segment for Program Change
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30871-20 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30871-20.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | genericFieldText1            | null                  |
      | coverageCode                 | cc01                  |
      | fileSource                   | RECIPIENT             |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 30871-20.consumerId   |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofLastMonth    |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | 1stDayofPresentMonth  |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    Then I send API call with name "30871-20a" for create Eligibility and Enrollment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30871-20.consumerId  |
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
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
      | fileSource                   | HRECIP               |
    Then I send API call with name "30871-20b" for create Eligibility and Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30871-20.consumerId  |
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
      | eligibilityStatusCode        | V                    |
      | planCode                     | 455701400            |
      | planId                       | 104                  |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | rejectionReason              | test1                |
    And I run create Eligibility and Enrollment API
    When I initiated get eligibility by consumer id "30871-20.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in previously created Eligibility Record
      | eligibilityStartDate  | 1stDayof2MonthsBefore |
      | eligibilityEndDate    | current               |
      | categoryCode          | 10                    |
      | programCode           | R                     |
      | subProgramTypeCode    | HoosierHealthwise     |
      | eligibilityStatusCode | M                     |
      | genericFieldText1     | null                  |
      | genericFieldText2     | 0                     |
      | createdOn             | current               |
      | createdBy             | 597                   |
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | 1stDayofPresentMonth |
      | eligibilityEndDate    | highDate             |
      | categoryCode          | 10                   |
      | programCode           | H                    |
      | subProgramTypeCode    | HealthyIndianaPlan   |
      | eligibilityStatusCode | V                    |
      | genericFieldText1     | Eligible             |
      | genericFieldText2     | 0                    |
      | createdOn             | current              |
      | createdBy             | 597                  |
    And I initiated get benefit status by consumer id "30871-20.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | HIP-Voluntary                               |
      | population          | HIP-Voluntary                               |
      | benefitStatus       | Enrolled                                    |
      | eligibilityScenario | Program Sub-Program Change With Date Change |
      | timeframe           | Active                                      |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available                   |
      | [0].consumerAction       | Plan Change Open Enrollment |
      | [0].planSelectionAllowed | true                        |
      | [0].changeAllowedFrom    | openEnrollmentDay           |
      | [0].changeAllowedUntil   | Dec15thCurrentYear          |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                                    |
      | consumerId    | 30871-20.consumerId                                       |
      | correlationId | 30871-20a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                      |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                                  |
      | consumerId    | 30871-20.consumerId                                       |
#      | correlationId | 30871-20b.eligibilities.[0].coreEligibility.correlationId |
      | correlationId | null |
      | consumerFound | true                                                      |
      | DPBI          |[blank]|


  @API-CP-30871 @API-CP-30871-04 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario: IN-EB ELIGIBILITY: BASE and ADDED Aid Category Codes: MMIS Sends Reconciliation (Part 2 Create or Update) 5.0 (Non HIP)
  5.0 One Inbound BASE Segment: Base Aid Category Change
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30871-21 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30871-21.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 30871-21.consumerId      |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    Then I send API call with name "30871-21a" for create Eligibility and Enrollment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30871-21.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 4                    |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
      | fileSource                   | HRECIP               |
    Then I send API call with name "30871-21b" for create Eligibility and Enrollment
    When I initiated get eligibility by consumer id "30871-21.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | 1stDayofPresentMonth |
      | eligibilityEndDate    | highDate             |
      | categoryCode          | 4                    |
      | programCode           | R                    |
      | subProgramTypeCode    | HoosierHealthwise    |
      | eligibilityStatusCode | M                    |
      | genericFieldText1     | Eligible             |
      | genericFieldText2     | 0                    |
      | createdOn             | current              |
      | createdBy             | 597                  |
      | updatedOn             | current              |
      | updatedBy             | 597                  |
    And I initiated get benefit status by consumer id "30871-21.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | HHW-Mandatory        |
      | population          | HHW-Mandatory        |
      | benefitStatus       | Eligible             |
      | eligibilityScenario | Base Category Change |
      | timeframe           | Active               |
    Then I Verify Consumer Actions as following data
      | [0].action               | Required                 |
      | [0].consumerAction       | Enroll                   |
      | [0].planSelectionAllowed | true                     |
      | [0].changeAllowedFrom    | 1stDayofPresentMonth     |
      | [0].changeAllowedUntil   | 14DayFromFirstDayOfMonth |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                                    |
      | consumerId    | 30871-21.consumerId                                       |
      | correlationId | 30871-21a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                      |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                                  |
      | consumerId    | 30871-21.consumerId                                       |
      | correlationId | 30871-21a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                      |
      | DPBI          |[blank]|


  @API-CP-30871 @API-CP-30871-04 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario: IN-EB ELIGIBILITY: BASE and ADDED Aid Category Codes: MMIS Sends Reconciliation (Part 2 Create or Update) 5.0 (HIP)
  5.0 One Inbound BASE Segment: Base Aid Category Change
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30871-22 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30871-22.consumerId  |
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
    Then I send API call with name "30871-22a" for create Eligibility and Enrollment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30871-22.consumerId  |
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
      | categoryCode                 | 4                    |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
      | fileSource                   | HRECIP               |
    Then I send API call with name "30871-22b" for create Eligibility and Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30871-22.consumerId  |
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
    When I initiated get eligibility by consumer id "30871-22.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | 1stDayofPresentMonth |
      | eligibilityEndDate    | highDate             |
      | categoryCode          | 4                    |
      | programCode           | H                    |
      | subProgramTypeCode    | HealthyIndianaPlan   |
      | eligibilityStatusCode | M                    |
      | genericFieldText1     | Eligible             |
      | genericFieldText2     | 0                    |
      | createdOn             | current              |
      | createdBy             | 597                  |
    And I initiated get benefit status by consumer id "30871-22.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | HIP-Mandatory        |
      | population          | HIP-Mandatory        |
      | benefitStatus       | Enrolled             |
      | eligibilityScenario | Base Category Change |
      | timeframe           | Active               |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available                   |
      | [0].consumerAction       | Plan Change Open Enrollment |
      | [0].planSelectionAllowed | true                        |
      | [0].changeAllowedFrom    | openEnrollmentDay           |
      | [0].changeAllowedUntil   | Dec15thCurrentYear          |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                                    |
      | consumerId    | 30871-22.consumerId                                       |
      | correlationId | 30871-22a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                      |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                                  |
      | consumerId    | 30871-22.consumerId                                       |
      | correlationId | 30871-22a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                      |
      | DPBI          |[blank]|

 # with changes from CP-41141, CP-41142, CP-41143 this scenario is no longer valid so removing tags API-EE-IN and IN-EB-API-Regression
  @API-CP-30872 @API-CP-30872-01 @sobir
  Scenario Outline: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes: MMIS Sends Reconciliation (Part 3 Create or Update) 2.1 10.0 (Non HIP)
  2.1 Merged Inbound BASE Segment: Update Matched Eligibility Segment
  10.0 Set the “Check CORE Indicator” when Conditions are Found
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
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
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
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | <eligibilityStartDate>  |
      | eligibilityEndDate           | <eligibilityEndDate>    |
      | programCode                  | A                       |
      | subProgramTypeCode           | HoosierCareConnect      |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | genericFieldText1            | Eligible                |
      | coverageCode                 | cc01                    |
      | fileSource                   | RECIPIENT               |
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
      | programCode                  | A                       |
      | subProgramTypeCode           | HoosierCareConnect      |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | genericFieldText1            | Eligible                |
      | coverageCode                 | cc01                    |
      | fileSource                   | RECIPIENT               |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | <eligibilityStartDate>  |
      | eligibilityEndDate    | <eligibilityEndDate>    |
      | categoryCode          | 10                      |
      | programCode           | A                       |
      | subProgramTypeCode    | HoosierCareConnect      |
      | eligibilityStatusCode | <eligibilityStatusCode> |
      | genericFieldText1     | Eligible                |
      | genericFieldText2     | <chechCore>             |
      | createdOn             | current                 |
      | createdBy             | 597                     |
      | updatedOn             | current                 |
      | updatedBy             | 597                     |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>    |
      | population          | <population>    |
      | benefitStatus       | <benefitStatus> |
      | eligibilityScenario | <scenario>      |
      | timeframe           | Active          |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil>   |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                                |
      | consumerId    | <name>.consumerId                                       |
#      | correlationId | <name>a.eligibilities.[0].coreEligibility.correlationId |
      | correlationId | null|
      | consumerFound | true                                                    |
      | DPBI          |[blank]|
    Examples: Differens scenarios
      | name    | scenario                                                                  | eligibilityStartDate  | eligibilityEndDate | eligibilityStatusCode | chechCore | population    | benefitStatus | action   | consumerAction | planSelectionAllowed | changeAllowedFrom     | changeAllowedUntil              |
      | 30872-1 | First Eligibility: EXACT MATCH, Second Eligibility: Merge Segments        | 1stDayofPresentMonth  | highDate           | V                     | 0         | HCC-Voluntary | Eligible      | Required | Enroll         | true                 | 1stDayofPresentMonth  | 60DaysFromFirstDayOfMonth       |
      | 30872-2 | First Eligibility: CHANGED START DATE, Second Eligibility: Merge Segments | 1stDayof2MonthsBefore | highDate           | X                     | 1         | HCC-Mandatory | Eligible      | Required | Enroll         | true                 | 1stDayof2MonthsBefore | 60DaysFrom1stDayof2MonthsBefore |
#     After CP-32018 implemented benefit status updated to Eligible instead of Benefit Status Error with eligibility non-high date
      | 30872-3 | First Eligibility: CHANGED END DATE, Second Eligibility: Merge Segments   | 1stDayofPresentMonth  | futureDate         | M                     | 0         | HCC-Mandatory | Eligible      | Required | Enroll         | true                 | 1stDayofPresentMonth  | 60DaysFromFirstDayOfMonth       |

 # with changes from CP-41141, CP-41142, CP-41143 this scenario is no longer valid so removing tags API-EE-IN and IN-EB-API-Regression
  @API-CP-30872 @API-CP-30872-01 @sobir
  Scenario Outline: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes: MMIS Sends Reconciliation (Part 3 Create or Update) 2.1 10.0 (HIP)
  2.1 Merged Inbound BASE Segment: Update Matched Eligibility Segment
  10.0 Set the “Check CORE Indicator” when Conditions are Found
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
      | genericFieldText1            | null                 |
      | coverageCode                 | cc01                 |
      | fileSource                   | HRECIP               |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | <eligibilityStartDate>  |
      | eligibilityEndDate           | <eligibilityEndDate>    |
      | programCode                  | H                       |
      | subProgramTypeCode           | HealthyIndianaPlan      |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | genericFieldText1            | <HIPstatus>             |
      | coverageCode                 | cc01                    |
      | fileSource                   | <fileSource>            |
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
      | programCode                  | H                       |
      | subProgramTypeCode           | HealthyIndianaPlan      |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | genericFieldText1            | <HIPstatus>             |
      | coverageCode                 | cc01                    |
      | fileSource                   | <fileSource>            |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | <eligibilityStartDate>  |
      | eligibilityEndDate           | <eligibilityEndDate>    |
      | enrollmentStartDate          | <eligibilityStartDate>  |
      | enrollmentEndDate            | <eligibilityEndDate>    |
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
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | <eligibilityStartDate>  |
      | eligibilityEndDate    | <eligibilityEndDate>    |
      | categoryCode          | 10                      |
      | programCode           | H                       |
      | subProgramTypeCode    | HealthyIndianaPlan      |
      | eligibilityStatusCode | <eligibilityStatusCode> |
      | genericFieldText1     | <HIPstatus>             |
      | genericFieldText2     | <chechCore>             |
      | createdOn             | current                 |
      | createdBy             | 597                     |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>    |
      | population          | <population>    |
      | benefitStatus       | <benefitStatus> |
      | eligibilityScenario | <scenario>      |
      | timeframe           | Active          |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil>   |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                                |
      | consumerId    | <name>.consumerId                                       |
#      | correlationId | <name>a.eligibilities.[0].coreEligibility.correlationId |
      | correlationId | null |
      | consumerFound | true                                                    |
      | DPBI          |[blank]|
    Examples: Different populations
      | name    | scenario                                                                  | eligibilityStartDate  | eligibilityEndDate | eligibilityStatusCode | HIPstatus | fileSource       | chechCore | population    | benefitStatus | action    | consumerAction              | planSelectionAllowed | changeAllowedFrom | changeAllowedUntil |
      | 30872-4 | First Eligibility: EXACT MATCH, Second Eligibility: Merge Segments        | 1stDayofPresentMonth  | highDate           | null                  | Eligible  | HRECIP           | 1         | HIP-Mandatory | Enrolled      | Available | Plan Change Open Enrollment | true                 | openEnrollmentDay | Dec15thCurrentYear |
#     After CP-32018 implemented benefit status updated to Eligible instead of Benefit Status Error with eligibility non-high date
      | 30872-5 | First Eligibility: CHANGED END DATE, Second Eligibility: Merge Segments   | 1stDayofPresentMonth  | futureDate         | M                     | Eligible  | RECIPIENT_HRECIP | 0         | HIP-Mandatory | Enrolled      | Available | Plan Change Open Enrollment | true                 | openEnrollmentDay | Dec15thCurrentYear |
      | 30872-6 | First Eligibility: CHANGED START DATE, Second Eligibility: Merge Segments | 1stDayof2MonthsBefore | highDate           | V                     | Eligible  | RECIPIENT_HRECIP | 0         | HIP-Voluntary | Enrolled      | Available | Plan Change Open Enrollment | true                 | openEnrollmentDay | Dec15thCurrentYear |
      | 30872-7 | First Eligibility: CHANGED START DATE, Second Eligibility: Merge Segments | 1stDayof2MonthsBefore | highDate           | X                     | Eligible  | HRECIP           | 1         | HIP-Mandatory | Enrolled      | Available | Plan Change Open Enrollment | true                 | openEnrollmentDay | Dec15thCurrentYear |
#     After CP-32018 implemented benefit status updated to Eligible instead of Benefit Status Error with eligibility non-high date
      | 30872-8 | First Eligibility: CHANGED END DATE, Second Eligibility: Merge Segments   | 1stDayofPresentMonth  | futureDate         | null                  | Eligible  | HRECIP           | 1         | HIP-Mandatory | Enrolled      | Available | Plan Change Open Enrollment | true                 | openEnrollmentDay | Dec15thCurrentYear |

 # with changes from CP-41141, CP-41142, CP-41143 this scenario is no longer valid so removing tags API-EE-IN and IN-EB-API-Regression
  @API-CP-30872 @API-CP-30872-02 @sobir
  Scenario: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 3 Create or Update) 6.0 7.0 (Non HIP)
  6.0 Two Inbound BASE Segments: Update Matched Eligibility Segment, Program Change or Gap in Program
  7.0 Two Inbound BASE Segments: Add New Eligibility Segment, Program Change or Gap in Program
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30872-9 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30872-9.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | genericFieldText1            | Eligible              |
      | coverageCode                 | cc01                  |
      | fileSource                   | RECIPIENT             |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 30872-9.consumerId    |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofLastMonth    |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | 1stDayof2MonthsBefore |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    Then I send API call with name "30872-9a" for create Eligibility and Enrollment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30872-9.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | lastDayofPresentMonth |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | genericFieldText1            | Eligible              |
      | coverageCode                 | cc01                  |
      | fileSource                   | RECIPIENT             |
    And User provide one more eligibility details
      | consumerId                   | 30872-9.consumerId |
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
      | categoryCode                 | 10                 |
      | genericFieldText1            | Eligible           |
      | coverageCode                 | cc01               |
      | fileSource                   | RECIPIENT          |
    Then I send API call with name "30872-9b" for create Eligibility and Enrollment
    When I initiated get eligibility by consumer id "30872-9.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in previously created Eligibility Record
      | eligibilityStartDate  | 1stDayof2MonthsBefore |
      | eligibilityEndDate    | lastDayofPresentMonth |
      | categoryCode          | 10                    |
      | programCode           | R                     |
      | subProgramTypeCode    | HoosierHealthwise     |
      | eligibilityStatusCode | M                     |
      | genericFieldText1     | Eligible              |
      | genericFieldText2     | 0                     |
      | createdOn             | current               |
      | createdBy             | 597                   |
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | 1stDayofNextMonth  |
      | eligibilityEndDate    | highDate           |
      | categoryCode          | 10                 |
      | programCode           | A                  |
      | subProgramTypeCode    | HoosierCareConnect |
      | eligibilityStatusCode | V                  |
      | genericFieldText1     | Eligible           |
      | genericFieldText2     | 0                  |
      | createdOn             | current            |
      | createdBy             | 597                |
    And I initiated get benefit status by consumer id "30872-9.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | HCC-Voluntary                                                           |
      | population          | HCC-Voluntary                                                           |
      | benefitStatus       | Eligible                                                                |
      | eligibilityScenario | First Eligibility: CHANGED END DATE, Second Eligibility: Program Change |
      | timeframe           | FUTURE                                                                  |
    Then I Verify Consumer Actions as following data
      | [0].action               | Required                     |
      | [0].consumerAction       | Enroll                       |
      | [0].planSelectionAllowed | true                         |
      | [0].changeAllowedFrom    | 1stDayofNextMonth            |
      | [0].changeAllowedUntil   | 60DayFromFirstDayOfNextMonth |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                                   |
      | consumerId    | 30872-9.consumerId                                       |
      | correlationId | 30872-9a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                     |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                                 |
      | consumerId    | 30872-9.consumerId                                       |
#      | correlationId | 30872-9b.eligibilities.[0].coreEligibility.correlationId |
      | correlationId | null |
      | consumerFound | true                                                     |
      | DPBI          |[blank]|

  # I'm not able to perform plan change for HIP since new plan can be only in future and enrollment can be only in past (recordId4)
#  @API-CP-30872 @API-CP-30872-02 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 3 Create or Update) 6.0 7.0 (HIP)
  6.0 Two Inbound BASE Segments: Update Matched Eligibility Segment, Program Change or Gap in Program
  7.0 Two Inbound BASE Segments: Add New Eligibility Segment, Program Change or Gap in Program
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30872-10 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30872-10.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | genericFieldText1            | null                  |
      | coverageCode                 | cc01                  |
      | fileSource                   | RECIPIENT             |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 30872-10.consumerId   |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofLastMonth    |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | 1stDayofPresentMonth  |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    Then I send API call with name "30872-10a" for create Eligibility and Enrollment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30872-10.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | lastDayofPresentMonth |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | genericFieldText1            | Eligible              |
      | coverageCode                 | cc01                  |
      | fileSource                   | RECIPIENT             |
    And User provide one more eligibility details
      | consumerId                   | 30872-10.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 21                  |
      | eligibilityRecordId          | 21                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth   |
      | eligibilityEndDate           | highDate            |
      | programCode                  | H                   |
      | subProgramTypeCode           | HealthyIndianaPlan  |
      | eligibilityStatusCode        | V                   |
      | categoryCode                 | 10                  |
      | genericFieldText1            | Eligible            |
      | coverageCode                 | cc01                |
      | fileSource                   | HRECIP              |
    Then I send API call with name "30872-10b" for create Eligibility and Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30872-10.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | recordId                     | 4                   |
      | enrollmentRecordId           | 4                   |
      | isEnrollmentProviderRequired | no                  |
      | eligibilityStartDate         | 1stDayofNextMonth   |
      | eligibilityEndDate           | highDate            |
      | enrollmentStartDate          | 1stDayofNextMonth   |
      | enrollmentEndDate            | highDate            |
      | txnStatus                    | Accepted            |
      | programCode                  | H                   |
      | subProgramTypeCode           | HealthyIndianaPlan  |
      | eligibilityStatusCode        | V                   |
      | planCode                     | 455701400           |
      | planId                       | 104                 |
      | serviceRegionCode            | Statewide           |
      | anniversaryDate              | anniversaryDate     |
      | channel                      | SYSTEM_INTEGRATION  |
      | rejectionReason              | test1               |
    And I run create Eligibility and Enrollment API
    When I initiated get eligibility by consumer id "30872-10.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in previously created Eligibility Record
      | eligibilityStartDate  | 1stDayof2MonthsBefore |
      | eligibilityEndDate    | lastDayofPresentMonth |
      | categoryCode          | 10                    |
      | programCode           | R                     |
      | subProgramTypeCode    | HoosierHealthwise     |
      | eligibilityStatusCode | M                     |
      | genericFieldText1     | Eligible              |
      | genericFieldText2     | 0                     |
      | createdOn             | current               |
      | createdBy             | 597                   |
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | 1stDayofNextMonth  |
      | eligibilityEndDate    | highDate           |
      | categoryCode          | 10                 |
      | programCode           | H                  |
      | subProgramTypeCode    | HealthyIndianaPlan |
      | eligibilityStatusCode | V                  |
      | genericFieldText1     | Eligible           |
      | genericFieldText2     | 0                  |
      | createdOn             | current            |
      | createdBy             | 597                |
    And I initiated get benefit status by consumer id "30872-10.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | HIP-Voluntary                               |
      | population          | HIP-Voluntary                               |
      | benefitStatus       | Enrolled                                    |
      | eligibilityScenario | Program Sub-Program Change With Date Change |
      | timeframe           | Active                                      |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available                   |
      | [0].consumerAction       | Plan Change Open Enrollment |
      | [0].planSelectionAllowed | true                        |
      | [0].changeAllowedFrom    | openEnrollmentDay           |
      | [0].changeAllowedUntil   | Dec15thCurrentYear          |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                                    |
      | consumerId    | 30872-10.consumerId                                       |
      | correlationId | 30872-10a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                      |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                                  |
      | consumerId    | 30872-10.consumerId                                       |
      | correlationId | 30872-10b.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                      |
      | DPBI          |[blank]|

 # with changes from CP-41141, CP-41142, CP-41143 this scenario is no longer valid so removing tags API-EE-IN and IN-EB-API-Regression
  @API-CP-30872 @API-CP-30872-03 @sobir
  Scenario: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 3 Create or Update) 8.0 (Non HIP)
  8.0 Two Inbound BASE Segments: Base Aid Category Change
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30872-11 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30872-11.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | genericFieldText1            | Eligible              |
      | coverageCode                 | cc01                  |
      | fileSource                   | RECIPIENT             |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 30872-11.consumerId   |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofLastMonth    |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | 1stDayof2MonthsBefore |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    Then I send API call with name "30872-11a" for create Eligibility and Enrollment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30872-11.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | lastDayofPresentMonth |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | V                     |
      | categoryCode                 | 10                    |
      | genericFieldText1            | Eligible              |
      | coverageCode                 | cc01                  |
      | fileSource                   | RECIPIENT             |
    And User provide one more eligibility details
      | consumerId                   | 30872-11.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 21                  |
      | eligibilityRecordId          | 21                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth   |
      | eligibilityEndDate           | highDate            |
      | programCode                  | R                   |
      | subProgramTypeCode           | HoosierHealthwise   |
      | eligibilityStatusCode        | V                   |
      | categoryCode                 | 4                   |
      | genericFieldText1            | Potential Plus      |
      | coverageCode                 | cc01                |
      | fileSource                   | RECIPIENT           |
    Then I send API call with name "30872-11b" for create Eligibility and Enrollment
    When I initiated get eligibility by consumer id "30872-11.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in previously created Eligibility Record
      | eligibilityStartDate  | 1stDayof2MonthsBefore |
      | eligibilityEndDate    | highDate              |
      | categoryCode          | 4                     |
      | programCode           | R                     |
      | subProgramTypeCode    | HoosierHealthwise     |
      | eligibilityStatusCode | V                     |
      | genericFieldText1     | Eligible              |
      | genericFieldText2     | 0                     |
      | createdOn             | current               |
      | createdBy             | 597                   |
      | updatedOn             | current               |
      | updatedBy             | 597                   |
    And I initiated get benefit status by consumer id "30872-11.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | HHW-Voluntary                                                                     |
      | population          | HHW-Voluntary                                                                     |
      | benefitStatus       | Eligible                                                                          |
      | eligibilityScenario | First Eligibility: CHANGED END DATE, Second Eligibility: BASE AID CATEGORY CHANGE |
      | timeframe           | Active                                                                            |
    Then I Verify Consumer Actions as following data
      | [0].action               | Required                        |
      | [0].consumerAction       | Enroll                          |
      | [0].planSelectionAllowed | true                            |
      | [0].changeAllowedFrom    | 1stDayof2MonthsBefore           |
      | [0].changeAllowedUntil   | 14DaysFrom1stDayof2MonthsBefore |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                                    |
      | consumerId    | 30872-11.consumerId                                       |
      | correlationId | 30872-11a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                      |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                                  |
      | consumerId    | 30872-11.consumerId                                       |
#      | correlationId | 30872-11b.eligibilities.[0].coreEligibility.correlationId |
      | correlationId | null |
      | consumerFound | true                                                      |
      | DPBI          |[blank]|

# I'm not able to perform Base aid category change for HIP since new plan can be only in future and enrollment can be only in past (recordId4)
#  @API-CP-30872 @API-CP-30872-03 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 3 Create or Update) 8.0 (HIP)
  8.0 Two Inbound BASE Segments: Base Aid Category Change
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30872-12 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30872-12.consumerId   |
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
      | genericFieldText1            | Denied                |
      | coverageCode                 | cc01                  |
      | fileSource                   | HRECIP                |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 30872-12.consumerId      |
      | startDate         | 1stDayof2MonthsBefore    |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    Then I send API call with name "30872-12a" for create Eligibility and Enrollment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30872-12.consumerId   |
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
      | eligibilityStatusCode        | V                     |
      | categoryCode                 | 10                    |
      | genericFieldText1            | Eligible              |
      | coverageCode                 | cc01                  |
      | fileSource                   | HRECIP                |
    And User provide one more eligibility details
      | consumerId                   | 30872-12.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 21                  |
      | eligibilityRecordId          | 21                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth   |
      | eligibilityEndDate           | highDate            |
      | programCode                  | H                   |
      | subProgramTypeCode           | HealthyIndianaPlan  |
      | eligibilityStatusCode        | V                   |
      | categoryCode                 | 4                   |
      | genericFieldText1            | Eligible            |
      | coverageCode                 | cc01                |
      | fileSource                   | HRECIP              |
    Then I send API call with name "30872-12b" for create Eligibility and Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30872-12.consumerId   |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | enrollmentRecordId           | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | H                     |
      | subProgramTypeCode           | HealthyIndianaPlan    |
      | eligibilityStatusCode        | V                     |
      | planCode                     | 455701400             |
      | planId                       | 104                   |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | rejectionReason              | test1                 |
    And I run create Eligibility and Enrollment API
    When I initiated get eligibility by consumer id "30872-12.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in previously created Eligibility Record
      | eligibilityStartDate  | 1stDayof2MonthsBefore |
      | eligibilityEndDate    | highDate              |
      | categoryCode          | 4                     |
      | programCode           | H                     |
      | subProgramTypeCode    | HealthyIndianaPlan    |
      | eligibilityStatusCode | V                     |
      | genericFieldText1     | Eligible              |
      | genericFieldText2     | 0                     |
      | createdOn             | current               |
      | createdBy             | 597                   |
      | updatedOn             | current               |
      | updatedBy             | 597                   |
    And I initiated get benefit status by consumer id "30872-12.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | HIP-Voluntary                                                                     |
      | population          | HIP-Voluntary                                                                     |
      | benefitStatus       | Eligible                                                                          |
      | eligibilityScenario | First Eligibility: CHANGED END DATE, Second Eligibility: BASE AID CATEGORY CHANGE |
      | timeframe           | Active                                                                            |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available                   |
      | [0].consumerAction       | Plan Change Open Enrollment |
      | [0].planSelectionAllowed | true                        |
      | [0].changeAllowedFrom    | openEnrollmentDay           |
      | [0].changeAllowedUntil   | Dec15thCurrentYear          |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                                    |
      | consumerId    | 30872-12.consumerId                                       |
      | correlationId | 30872-12a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                      |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                                  |
      | consumerId    | 30872-12.consumerId                                       |
      | correlationId | 30872-12a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                      |
      | DPBI          |[blank]|

 # with changes from CP-41141, CP-41142, CP-41143 this scenario is no longer valid so removing tags API-EE-IN and IN-EB-API-Regression
  @API-CP-30872 @API-CP-30872-04 @sobir
  Scenario: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 3 Create or Update) 8.0 10.0 (Non HIP Error Base Change overlaping)
  8.0 Two Inbound BASE Segments: Base Aid Category Change
  10.0 Set the “Check CORE Indicator” when Conditions are Found
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30872-13 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30872-13.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 30872-13.consumerId      |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    Then I send API call with name "30872-13a" for create Eligibility and Enrollment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30872-13.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    And User provide one more eligibility details
      | consumerId                   | 30872-13.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 4                    |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    Then I send API call with name "30872-13b" for create Eligibility and Enrollment
    When I initiated get eligibility by consumer id "30872-13.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in previously created Eligibility Record
      | genericFieldText2 | 2 |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                                    |
      | consumerId    | 30872-13.consumerId                                       |
      | correlationId | 30872-13b.eligibilities.[0].coreEligibility.correlationId |
      | status        | fail                                                      |
      | errorStack    | Different BASE Aid-Category with Overlapping Timeframe    |

 # with changes from CP-41141, CP-41142, CP-41143 this scenario is no longer valid so removing tags API-EE-IN and IN-EB-API-Regression
  @API-CP-30872 @API-CP-30872-04 @sobir
  Scenario: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 3 Create or Update) 8.0 10.0 (Error Non HIP Base change with 2 segments)
  8.0 Two Inbound BASE Segments: Base Aid Category Change
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30872-14 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30872-14.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | genericFieldText1            | Eligible              |
      | coverageCode                 | cc01                  |
      | fileSource                   | RECIPIENT             |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 30872-14.consumerId   |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofLastMonth    |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | 1stDayof2MonthsBefore |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30872-14.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | lastDayofPresentMonth |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | V                     |
      | categoryCode                 | 4                     |
      | genericFieldText1            | Eligible              |
      | coverageCode                 | cc01                  |
      | fileSource                   | RECIPIENT             |
    And User provide one more eligibility details
      | consumerId                   | 30872-14.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 21                  |
      | eligibilityRecordId          | 21                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth   |
      | eligibilityEndDate           | highDate            |
      | programCode                  | R                   |
      | subProgramTypeCode           | HoosierHealthwise   |
      | eligibilityStatusCode        | V                   |
      | categoryCode                 | 4                   |
      | genericFieldText1            | Potential Plus      |
      | coverageCode                 | cc01                |
      | fileSource                   | RECIPIENT           |
    Then I send API call with name "30872-14a" for create Eligibility and Enrollment
    When I initiated get eligibility by consumer id "30872-14.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in previously created Eligibility Record
      | genericFieldText2 | 2 |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                                    |
      | consumerId    | 30872-14.consumerId                                       |
      | correlationId | 30872-14a.eligibilities.[0].coreEligibility.correlationId |
      | status        | fail                                                      |
      | errorStack    | Base Category Changewith two segments                    |


  @API-CP-30872 @API-CP-30872-05 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 3 Create or Update) 9.0 (Add Added Aide Category to existed Eligibility)
  9.0 One or Two Inbound ADDED Aid Category(s)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30872-15 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30872-15.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth   |
      | endDate             | highDate               |
      | segmentDetailValue1 | 5                      |
      | segmentDetailValue2 | null                   |
      | segmentDetailValue3 | 1stDayofPresentMonth:: |
      | segmentDetailValue4 | highDate::             |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY           |
    And I run create Eligibility and Enrollment API
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "30872-15.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console     |
      | [0].consumerId                | 30872-15.consumerId  |
      | [0].segmentTypeCode           | AID_CATEGORY         |
      | [0].startDate                 | 1stDayofPresentMonth |
      | [0].endDate                   | highDate             |
      | [0].segmentDetailValue1       | 5                    |
      | [0].segmentDetailValue2       | null                 |
      | [0].segmentDetailValue3       | 1stDayofPresentMonth |
      | [0].segmentDetailValue4       | highDate             |
      | [0].segmentDetailValue5       | null                 |
      | [0].segmentDetailValue6       | null                 |
      | [0].segmentDetailValue7       | null                 |
      | [0].segmentDetailValue8       | null                 |
      | [0].segmentDetailValue9       | null                 |
      | [0].segmentDetailValue10      | null                 |
      | [0].createdOn                 | current              |
      | [0].createdBy                 | null                 |
      | [0].updatedOn                 | current              |
      | [0].updatedBy                 | null                 |
      | [0].correlationId             | null                 |
      | [0].uiid                      | null                 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30872-15.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth   |
      | endDate             | highDate               |
      | segmentDetailValue1 | 7                      |
      | segmentDetailValue2 | null                   |
      | segmentDetailValue3 | 1stDayofPresentMonth:: |
      | segmentDetailValue4 | highDate::             |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY           |
    Then I send API call with name "30872-15a" for create Eligibility and Enrollment
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "30872-15.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console     |
      | [0].consumerId                | 30872-15.consumerId  |
      | [0].segmentTypeCode           | AID_CATEGORY         |
      | [0].startDate                 | 1stDayofPresentMonth |
      | [0].endDate                   | highDate             |
      | [0].segmentDetailValue1       | 5                    |
      | [0].segmentDetailValue2       | null                 |
      | [0].segmentDetailValue3       | 1stDayofPresentMonth |
      | [0].segmentDetailValue4       | highDate             |
      | [0].segmentDetailValue5       | null                 |
      | [0].segmentDetailValue6       | null                 |
      | [0].segmentDetailValue7       | null                 |
      | [0].segmentDetailValue8       | null                 |
      | [0].segmentDetailValue9       | null                 |
      | [0].segmentDetailValue10      | null                 |
      | [0].createdOn                 | current              |
      | [0].createdBy                 | null                 |
      | [0].updatedOn                 | current              |
      | [0].updatedBy                 | null                 |
      | [0].correlationId             | null                 |
      | [0].uiid                      | null                 |
      # * * * * * * *
      | [1].otherEligbilitySegmentsId | print in console     |
      | [1].consumerId                | 30872-15.consumerId  |
      | [1].segmentTypeCode           | AID_CATEGORY         |
      | [1].startDate                 | 1stDayofPresentMonth |
      | [1].endDate                   | highDate             |
      | [1].segmentDetailValue1       | 7                    |
      | [1].segmentDetailValue2       | null                 |
      | [1].segmentDetailValue3       | 1stDayofPresentMonth |
      | [1].segmentDetailValue4       | highDate             |
      | [1].segmentDetailValue5       | null                 |
      | [1].segmentDetailValue6       | null                 |
      | [1].segmentDetailValue7       | null                 |
      | [1].segmentDetailValue8       | null                 |
      | [1].segmentDetailValue9       | null                 |
      | [1].segmentDetailValue10      | null                 |
      | [1].createdOn                 | current              |
      | [1].createdBy                 | null                 |
      | [1].updatedOn                 | current              |
      | [1].updatedBy                 | null                 |
      | [1].correlationId             | null                 |
      | [1].uiid                      | null                 |
    Then I will verify business events are generated with data
      | eventName     | OTHER_ELIGIBILITY_SAVE_EVENT                              |
      | consumerId    | 30872-15.consumerId                                       |
      | correlationId | 30872-15a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                      |
      | DPBI          |[blank]|


  @API-CP-30872 @API-CP-30872-05 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 3 Create or Update) 9.0 (Update Added Aide Category to existed Eligibility)
  9.0 One or Two Inbound ADDED Aid Category(s)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30872-16 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30872-16.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth   |
      | endDate             | highDate               |
      | segmentDetailValue1 | 5                      |
      | segmentDetailValue2 | null                   |
      | segmentDetailValue3 | 1stDayofPresentMonth:: |
      | segmentDetailValue4 | highDate::             |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY           |
    And I run create Eligibility and Enrollment API
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "30872-16.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console     |
      | [0].consumerId                | 30872-16.consumerId  |
      | [0].segmentTypeCode           | AID_CATEGORY         |
      | [0].startDate                 | 1stDayofPresentMonth |
      | [0].endDate                   | highDate             |
      | [0].segmentDetailValue1       | 5                    |
      | [0].segmentDetailValue2       | null                 |
      | [0].segmentDetailValue3       | 1stDayofPresentMonth |
      | [0].segmentDetailValue4       | highDate             |
      | [0].segmentDetailValue5       | null                 |
      | [0].segmentDetailValue6       | null                 |
      | [0].segmentDetailValue7       | null                 |
      | [0].segmentDetailValue8       | null                 |
      | [0].segmentDetailValue9       | null                 |
      | [0].segmentDetailValue10      | null                 |
      | [0].createdOn                 | current              |
      | [0].createdBy                 | null                 |
      | [0].updatedOn                 | current              |
      | [0].updatedBy                 | null                 |
      | [0].correlationId             | null                 |
      | [0].uiid                      | null                 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30872-16.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | genericFieldText1            | Eligible              |
      | coverageCode                 | cc01                  |
      | fileSource                   | RECIPIENT             |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | highDate                |
      | segmentDetailValue1 | 5                       |
      | segmentDetailValue2 | null                    |
      | segmentDetailValue3 | 1stDayof2MonthsBefore:: |
      | segmentDetailValue4 | highDate::              |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY            |
    Then I send API call with name "30872-16a" for create Eligibility and Enrollment
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "30872-16.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console      |
      | [0].consumerId                | 30872-16.consumerId   |
      | [0].segmentTypeCode           | AID_CATEGORY          |
      | [0].startDate                 | 1stDayof2MonthsBefore |
      | [0].endDate                   | highDate              |
      | [0].segmentDetailValue1       | 5                     |
      | [0].segmentDetailValue2       | null                  |
      | [0].segmentDetailValue3       | 1stDayofPresentMonth  |
      | [0].segmentDetailValue4       | highDate              |
      | [0].segmentDetailValue5       | null                  |
      | [0].segmentDetailValue6       | null                  |
      | [0].segmentDetailValue7       | null                  |
      | [0].segmentDetailValue8       | null                  |
      | [0].segmentDetailValue9       | null                  |
      | [0].segmentDetailValue10      | null                  |
      | [0].createdOn                 | current               |
      | [0].createdBy                 | null                  |
      | [0].updatedOn                 | current               |
      | [0].updatedBy                 | 597                   |
      | [0].correlationId             | null                  |
      | [0].uiid                      | null                  |
    Then I will verify business events are generated with data
      | eventName     | OTHER_ELIGIBILITY_UPDATE_EVENT                            |
      | consumerId    | 30872-16.consumerId                                       |
      | correlationId | 30872-16a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                      |
      | DPBI          |[blank]|


  @API-CP-30872 @API-CP-30872-05 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 3 Create or Update) 9.0 (Save Added Aide Category without inbound eligibility to existed Eligibility)
  9.0 One or Two Inbound ADDED Aid Category(s)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30872-17 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30872-17.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | genericFieldText1            | Eligible              |
      | coverageCode                 | cc01                  |
      | fileSource                   | RECIPIENT             |
    And I run create Eligibility and Enrollment API
    And I provide dummy enrollment and eligibility information to create enrollment
      | consumerId            | 30872-17.consumerId |
      | isEligibilityRequired | yes                 |
      | isEnrollemntRequired  | no                  |
      | createdBy             | 597                 |
      | recordId              | 21                  |
      | genericFieldText1     | Eligible            |
      | fileSource            | RECIPIENT           |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | highDate                |
      | segmentDetailValue1 | 5                       |
      | segmentDetailValue2 | null                    |
      | segmentDetailValue3 | 1stDayof2MonthsBefore:: |
      | segmentDetailValue4 | highDate::              |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY            |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | highDate                |
      | segmentDetailValue1 | 7                       |
      | segmentDetailValue2 | null                    |
      | segmentDetailValue3 | 1stDayof2MonthsBefore:: |
      | segmentDetailValue4 | highDate::              |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY            |
    Then I send API call with name "30872-17a" for create Eligibility and Enrollment
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "30872-17.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console      |
      | [0].consumerId                | 30872-17.consumerId   |
      | [0].segmentTypeCode           | AID_CATEGORY          |
      | [0].startDate                 | 1stDayof2MonthsBefore |
      | [0].endDate                   | highDate              |
      | [0].segmentDetailValue1       | 5                     |
      | [0].segmentDetailValue2       | null                  |
      | [0].segmentDetailValue3       | 1stDayof2MonthsBefore |
      | [0].segmentDetailValue4       | highDate              |
      | [0].segmentDetailValue5       | null                  |
      | [0].segmentDetailValue6       | null                  |
      | [0].segmentDetailValue7       | null                  |
      | [0].segmentDetailValue8       | null                  |
      | [0].segmentDetailValue9       | null                  |
      | [0].segmentDetailValue10      | null                  |
      | [0].createdOn                 | current               |
      | [0].createdBy                 | null                  |
      | [0].updatedOn                 | current               |
      | [0].updatedBy                 | null                  |
      | [0].correlationId             | null                  |
      | [0].uiid                      | null                  |
      # * * * * * * *
      | [1].otherEligbilitySegmentsId | print in console      |
      | [1].consumerId                | 30872-17.consumerId   |
      | [1].segmentTypeCode           | AID_CATEGORY          |
      | [1].startDate                 | 1stDayof2MonthsBefore |
      | [1].endDate                   | highDate              |
      | [1].segmentDetailValue1       | 7                     |
      | [1].segmentDetailValue2       | null                  |
      | [1].segmentDetailValue3       | 1stDayof2MonthsBefore |
      | [1].segmentDetailValue4       | highDate              |
      | [1].segmentDetailValue5       | null                  |
      | [1].segmentDetailValue6       | null                  |
      | [1].segmentDetailValue7       | null                  |
      | [1].segmentDetailValue8       | null                  |
      | [1].segmentDetailValue9       | null                  |
      | [1].segmentDetailValue10      | null                  |
      | [1].createdOn                 | current               |
      | [1].createdBy                 | null                  |
      | [1].updatedOn                 | current               |
      | [1].updatedBy                 | null                  |
      | [1].correlationId             | null                  |
      | [1].uiid                      | null                  |
    Then I will verify business events are generated with data
      | eventName     | OTHER_ELIGIBILITY_SAVE_EVENT                              |
      | consumerId    | 30872-17.consumerId                                       |
      | correlationId | 30872-17a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                      |
      | DPBI          |[blank]|


  @API-CP-30872 @API-CP-30872-05 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 3 Create or Update) 9.0 10.0 (Error no eligibility exists in system)
  9.0 One or Two Inbound ADDED Aid Category(s)
  10.0 Set the “Check CORE Indicator” when Conditions are Found
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30872-18 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide dummy enrollment and eligibility information to create enrollment
      | consumerId            | 30872-18.consumerId |
      | isEligibilityRequired | yes                 |
      | isEnrollemntRequired  | no                  |
      | createdBy             | 597                 |
      | recordId              | 21                  |
      | genericFieldText1     | Eligible            |
      | fileSource            | HRECIP              |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | highDate                |
      | segmentDetailValue1 | 5                       |
      | segmentDetailValue2 | null                    |
      | segmentDetailValue3 | 1stDayof2MonthsBefore:: |
      | segmentDetailValue4 | highDate::              |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY            |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | highDate                |
      | segmentDetailValue1 | 7                       |
      | segmentDetailValue2 | null                    |
      | segmentDetailValue3 | 1stDayof2MonthsBefore:: |
      | segmentDetailValue4 | highDate::              |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY            |
    Then I send API call with name "30872-18a" for create Eligibility and Enrollment
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                                    |
      | consumerId    | 30872-18.consumerId                                       |
      | correlationId | 30872-18a.eligibilities.[0].coreEligibility.correlationId |
      | status        | FAIL                                                      |
      | errorStack    | No BASE eligibility segment for ADDED aid-category code   |


  @API-CP-30872 @API-CP-30872-06 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario Outline: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 3 Create or Update) 10.0 (ADDED aid-category for Medicare J or L)
  10.0 Set the “Check CORE Indicator” when Conditions are Found
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
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
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
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth   |
      | endDate             | highDate               |
      | segmentDetailValue1 | <AddedAidCategory>     |
      | segmentDetailValue2 | null                   |
      | segmentDetailValue3 | 1stDayofPresentMonth:: |
      | segmentDetailValue4 | highDate::             |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY           |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in previously created Eligibility Record
      | eligibilityStartDate  | 1stDayofPresentMonth |
      | eligibilityEndDate    | highDate             |
      | categoryCode          | 10                   |
      | programCode           | R                    |
      | subProgramTypeCode    | HoosierHealthwise    |
      | eligibilityStatusCode | M                    |
      | genericFieldText1     | Eligible             |
      | genericFieldText2     | 2                    |
      | createdOn             | current              |
      | createdBy             | 597                  |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | HHW-Mandatory |
      | population          | HHW-Mandatory |
      | benefitStatus       | Eligible      |
      | eligibilityScenario | EXACT MATCH   |
      | timeframe           | Active        |
    Then I Verify Consumer Actions as following data
      | [0].action               | Required                 |
      | [0].consumerAction       | Enroll                   |
      | [0].planSelectionAllowed | true                     |
      | [0].changeAllowedFrom    | 1stDayofPresentMonth     |
      | [0].changeAllowedUntil   | 14DayFromFirstDayOfMonth |
    Examples: ADDED aid-category for Medicare (J or L)
      | name     | AddedAidCategory |
      | 30872-19 | J                |
      | 30872-20 | L                |


























