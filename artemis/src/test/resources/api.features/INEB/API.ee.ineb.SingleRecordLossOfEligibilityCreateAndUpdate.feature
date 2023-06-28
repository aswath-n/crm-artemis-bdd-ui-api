Feature: IN-EB ELIGIBILITY Single Record Loss of Eligibility All populations


  @API-CP-24296 @API-CP-24296-01 @API-EE-IN @kursat
  Scenario Outline: IN-EB Eligibility Create or Update Data: 21 Single record with Non-High End Date (Loss of Eligibility) 1.0 10.0 (Non HIP)
  1.0 One Inbound BASE Segment: Create New Eligibility Segment from BASE Eligibility Segment
  10.0 Set the “Check CORE Indicator” when Conditions are Found
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
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | <eligibilityEndDate>     |
      | programCode                  | <programCode>            |
      | subProgramTypeCode           | <subProgramTypeCode>     |
      | eligibilityStatusCode        | <eligibilityStatusCode1> |
      | categoryCode                 | 10                       |
      | coverageCode                 | cc01                     |
      | fileSource                   | RECIPIENT                |
      | requestedBy                  | 1                        |
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
      | eligibilityStartDate  | 1stDayofPresentMonth     |
      | eligibilityEndDate    | <eligibilityEndDate>     |
      | categoryCode          | 10                       |
      | programCode           | <programCode>            |
      | subProgramTypeCode    | <subProgramTypeCode>     |
      | eligibilityStatusCode | <eligibilityStatusCode1> |
      | genericFieldText2     | <checkCore1>             |
      | createdOn             | current                  |
      | createdBy             | 1                        |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    # When CP-32018 implemented,below commented out validations will be working
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
    # CP-24296 AC 10.0 - eligibility updated with below to set the check core indicator to off
    And I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                       |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | isEnrollmentProviderRequired | no                       |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | <eligibilityEndDate>     |
      | programCode                  | <programCode>            |
      | subProgramTypeCode           | <subProgramTypeCode>     |
      | eligibilityStatusCode        | <eligibilityStatusCode2> |
      | categoryCode                 | 10                       |
      | coverageCode                 | cc01                     |
      | fileSource                   | RECIPIENT                |
    And I run create Eligibility and Enrollment API
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in previously created Eligibility Record
      | genericFieldText2 | <checkCore2> |
    Examples: Different populations
      | name    | programCode | subProgramTypeCode | eligibilityStatusCode1 | checkCore1 | population    | changeAllowedUntil        | eligibilityStatusCode2 | checkCore2 | eligibilityEndDate |
      | 24296-1 | A           | HoosierCareConnect | M                      | 0          | HCC-Mandatory | 60DaysFromFirstDayOfMonth | X                      | 1          | future             |
      | 24296-2 | A           | HoosierCareConnect | V                      | 0          | HCC-Voluntary | 60DaysFromFirstDayOfMonth | X                      | 1          | future             |
      | 24296-3 | A           | HoosierCareConnect | X                      | 1          | HCC-Mandatory | 60DaysFromFirstDayOfMonth | M                      | 0          | future             |
      | 24296-4 | R           | HoosierHealthwise  | M                      | 0          | HHW-Mandatory | 14DayFromFirstDayOfMonth  | X                      | 1          | future             |
      | 24296-5 | R           | HoosierHealthwise  | V                      | 0          | HHW-Voluntary | 14DayFromFirstDayOfMonth  | X                      | 1          | future             |
      | 24296-6 | R           | HoosierHealthwise  | X                      | 1          | HHW-Mandatory | 14DayFromFirstDayOfMonth  | M                      | 0          | future             |


  @API-CP-24296 @API-CP-24296-01 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: IN-EB Eligibility Create or Update Data: 21 Single record with Non-High End Date (Loss of Eligibility) 1.0 10.0 (HIP)
  1.0 One Inbound BASE Segment: Create New Eligibility Segment from BASE Eligibility Segment
  10.0 Set the “Check CORE Indicator” when Conditions are Found
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
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | <eligibilityEndDate>     |
      | programCode                  | H                        |
      | subProgramTypeCode           | HealthyIndianaPlan       |
      | eligibilityStatusCode        | <eligibilityStatusCode1> |
      | categoryCode                 | 10                       |
      | genericFieldText1            | Eligible                 |
      | coverageCode                 | cc01                     |
      | fileSource                   | HRECIP                   |
      | requestedBy                  | 1                        |
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
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | no                       |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | recordId                     | 4                        |
      | enrollmentRecordId           | 4                        |
      | isEnrollmentProviderRequired | no                       |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | <eligibilityEndDate>     |
      | enrollmentStartDate          | 1stDayofPresentMonth     |
      | enrollmentEndDate            | <eligibilityEndDate>     |
      | txnStatus                    | Accepted                 |
      | programCode                  | H                        |
      | subProgramTypeCode           | HealthyIndianaPlan       |
      | eligibilityStatusCode        | <eligibilityStatusCode1> |
      | planCode                     | 455701400                |
      | planId                       | 104                      |
      | serviceRegionCode            | Statewide                |
      | anniversaryDate              | anniversaryDate          |
      | channel                      | SYSTEM_INTEGRATION       |
      | rejectionReason              | test1                    |
    And I run create Eligibility and Enrollment API
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | 1stDayofPresentMonth     |
      | eligibilityEndDate    | <eligibilityEndDate>     |
      | categoryCode          | 10                       |
      | programCode           | H                        |
      | subProgramTypeCode    | HealthyIndianaPlan       |
      | eligibilityStatusCode | <eligibilityStatusCode1> |
      | genericFieldText1     | Eligible                 |
      | genericFieldText2     | <checkCore1>             |
      | createdOn             | current                  |
      | createdBy             | 1                        |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    # When CP-32018 implemented,below commented out validations will be working
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
   # CP-24296 AC 10.0 - eligibility updated with below to set the check core indicator to off
    And I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                       |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | isEnrollmentProviderRequired | no                       |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | <eligibilityEndDate>     |
      | programCode                  | H                        |
      | subProgramTypeCode           | HealthyIndianaPlan       |
      | eligibilityStatusCode        | <eligibilityStatusCode2> |
      | categoryCode                 | 10                       |
      | coverageCode                 | cc01                     |
      | fileSource                   | RECIPIENT                |
    And I run create Eligibility and Enrollment API
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in previously created Eligibility Record
      | genericFieldText2 | <checkCore2> |
    Examples: Different populations
      | name     | eligibilityStatusCode1 | checkCore1 | population    | eligibilityStatusCode2 | checkCore2 | eligibilityEndDate |
      | 24296-7  | null                   | 1          | HIP-Mandatory | M                      | 0          | future             |
      | 24296-8  | M                      | 0          | HIP-Mandatory | null                   | 1          | future             |
      | 24296-9  | V                      | 0          | HIP-Voluntary | X                      | 1          | future             |
      | 24296-10 | X                      | 1          | HIP-Mandatory | V                      | 0          | future             |


  @API-CP-24296 @API-CP-24296-02 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: IN-EB Eligibility Create or Update Data: 21 Single record with Non-High End Date (Loss of Eligibility) 2.0 10.0 (Non HIP)
  2.0 One Inbound BASE Segment: Update Matched Eligibility Segment
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
      | eligibilityStartDate         | <eligibilityStartDate1> |
      | eligibilityEndDate           | <eligibilityEndDate1>   |
      | programCode                  | A                       |
      | subProgramTypeCode           | HoosierCareConnect      |
      | eligibilityStatusCode        | M                       |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
      | fileSource                   | RECIPIENT               |
      | requestedBy                  | 1                       |
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
      | eligibilityStartDate         | <eligibilityStartDate2> |
      | eligibilityEndDate           | <eligibilityEndDate2>   |
      | programCode                  | A                       |
      | subProgramTypeCode           | HoosierCareConnect      |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
      | fileSource                   | RECIPIENT               |
      | requestedBy                  | 2                       |
    And I run create Eligibility and Enrollment API
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | <eligibilityStartDate2> |
      | eligibilityEndDate    | <eligibilityEndDate2>   |
      | categoryCode          | 10                      |
      | programCode           | A                       |
      | subProgramTypeCode    | HoosierCareConnect      |
      | eligibilityStatusCode | <eligibilityStatusCode> |
      | genericFieldText2     | <checkCore>             |
      | updatedOn             | current                 |
      | updatedBy             | 2                       |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    # When CP-32018 implemented,below commented out validations will be working
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
      | name     | scenario           | eligibilityStartDate1 | eligibilityEndDate1 | eligibilityStartDate2 | eligibilityEndDate2 | eligibilityStatusCode | checkCore | population    | benefitStatus | action   | consumerAction | planSelectionAllowed | changeAllowedFrom     | changeAllowedUntil              |
      | 24296-11 | EXACT MATCH        | 1stDayofPresentMonth  | futureDate          | 1stDayofPresentMonth  | futureDate          | V                     | 0         | HCC-Voluntary | Eligible      | Required | Enroll         | true                 | 1stDayofPresentMonth  | 60DaysFromFirstDayOfMonth       |
      | 24296-12 | CHANGED START DATE | 1stDayofPresentMonth  | futureDate          | 1stDayof2MonthsBefore | futureDate          | X                     | 1         | HCC-Mandatory | Eligible      | Required | Enroll         | true                 | 1stDayof2MonthsBefore | 60DaysFrom1stDayof2MonthsBefore |
      | 24296-13 | CHANGED END DATE   | 1stDayofPresentMonth  | futureDate          | 1stDayofPresentMonth  | highDate            | M                     | 0         | HCC-Mandatory | Eligible      | Required | Enroll         | true                 | 1stDayofPresentMonth  | 60DaysFromFirstDayOfMonth       |


  @API-CP-24296 @API-CP-24296-02 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: IN-EB Eligibility Create or Update Data: 21 Single record with Non-High End Date (Loss of Eligibility) 2.0 10.0 (HIP)
  2.0 One Inbound BASE Segment: Update Matched Eligibility Segment
  10.0 Set the “Check CORE Indicator” when Conditions are Found
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
      | eligibilityEndDate           | <eligibilityEndDate1>    |
      | programCode                  | H                        |
      | subProgramTypeCode           | HealthyIndianaPlan       |
      | eligibilityStatusCode        | <eligibilityStatusCode1> |
      | categoryCode                 | 10                       |
      | genericFieldText1            | <HIPstatus1>             |
      | coverageCode                 | cc01                     |
      | fileSource                   | HRECIP                   |
      | requestedBy                  | 1                        |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
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
      | genericFieldText1            | <HIPstatus2>             |
      | coverageCode                 | cc01                     |
      | fileSource                   | <fileSource>             |
      | requestedBy                  | 2                        |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | no                       |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | recordId                     | 4                        |
      | enrollmentRecordId           | 4                        |
      | isEnrollmentProviderRequired | no                       |
      | eligibilityStartDate         | <eligibilityStartDate2>  |
      | eligibilityEndDate           | <eligibilityEndDate2>    |
      | enrollmentStartDate          | <eligibilityStartDate2>  |
      | enrollmentEndDate            | <eligibilityEndDate2>    |
      | txnStatus                    | Accepted                 |
      | programCode                  | H                        |
      | subProgramTypeCode           | HealthyIndianaPlan       |
      | eligibilityStatusCode        | <eligibilityStatusCode2> |
      | planCode                     | 455701400                |
      | planId                       | 104                      |
      | serviceRegionCode            | Statewide                |
      | anniversaryDate              | anniversaryDate          |
      | channel                      | SYSTEM_INTEGRATION       |
      | rejectionReason              | test1                    |
    And I run create Eligibility and Enrollment API
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | <eligibilityStartDate2>  |
      | eligibilityEndDate    | <eligibilityEndDate2>    |
      | categoryCode          | 10                       |
      | programCode           | H                        |
      | subProgramTypeCode    | HealthyIndianaPlan       |
      | eligibilityStatusCode | <eligibilityStatusCode2> |
      | genericFieldText1     | <HIPstatus2>             |
      | genericFieldText2     | <checkCore>              |
      | updatedOn             | current                  |
      | updatedBy             | 2                        |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    # When CP-32018 implemented,below commented out validations will be working
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
      | name     | scenario           | eligibilityStartDate1 | eligibilityStartDate2 | eligibilityEndDate1 | eligibilityEndDate2 | eligibilityStatusCode1 | eligibilityStatusCode2 | HIPstatus1 | HIPstatus2     | fileSource       | checkCore | population    | benefitStatus | action      | consumerAction              | planSelectionAllowed | changeAllowedFrom | changeAllowedUntil |
      | 24296-14 | CHANGED END DATE   | 1stDayofPresentMonth  | 1stDayofPresentMonth  | futureDate          | highDate            | X                      | M                      | null       | Eligible       | HRECIP           | 0         | HIP-Mandatory | Enrolled      | Available   | Plan Change Open Enrollment | true                 | openEnrollmentDay | Dec15thCurrentYear |
      | 24296-15 | EXACT MATCH        | 1stDayofPresentMonth  | 1stDayofPresentMonth  | futureDate          | futureDate          | null                   | M                      | null       | Eligible       | RECIPIENT_HRECIP | 0         | HIP-Mandatory | Enrolled      | Available   | Plan Change Open Enrollment | true                 | openEnrollmentDay | Dec15thCurrentYear |
      | 24296-16 | CHANGED START DATE | 1stDayofPresentMonth  | 1stDayof2MonthsBefore | futureDate          | futureDate          | X                      | V                      | null       | Potential Plus | RECIPIENT_HRECIP | 0         | HIP-Voluntary | Enrolled      | Unavailable | null                        | false                | null              | null               |
      | 24296-17 | CHANGED START DATE | 1stDayofPresentMonth  | 1stDayof2MonthsBefore | futureDate          | futureDate          | M                      | X                      | null       | Eligible       | HRECIP           | 1         | HIP-Mandatory | Enrolled      | Available   | Plan Change Open Enrollment | true                 | openEnrollmentDay | Dec15thCurrentYear |
      | 24296-18 | CHANGED START DATE | 1stDayofPresentMonth  | 1stDayof2MonthsBefore | futureDate          | futureDate          | M                      | null                   | null       | Eligible       | RECIP            | 1         | HIP-Mandatory | Enrolled      | Available   | Plan Change Open Enrollment | true                 | openEnrollmentDay | Dec15thCurrentYear |


  @API-CP-24296 @API-CP-24296-03 @API-CP-24296-04 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: IN-EB Eligibility Create or Update Data: 21 Single record with Non-High End Date (Loss of Eligibility) 3.0 4.0 (Non HIP)
  3.0 One Inbound BASE Segment Update Matched Eligibility Segment, Program Change
  4.0 One Inbound BASE Segment Add New Eligibility Segment for Program Change
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
      | eligibilityEndDate           | <eligibilityEndDate1>    |
      | programCode                  | <programCode1>           |
      | subProgramTypeCode           | <subProgramTypeCode1>    |
      | eligibilityStatusCode        | <eligibilityStatusCode1> |
      | categoryCode                 | 10                       |
      | coverageCode                 | cc01                     |
      | fileSource                   | RECIPIENT                |
      | requestedBy                  | 1                        |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | <name>.consumerId        |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
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
      | eligibilityStartDate         | <eligibilityStartDate2>  |
      | eligibilityEndDate           | <eligibilityEndDate2>    |
      | programCode                  | <programCode2>           |
      | subProgramTypeCode           | <subProgramTypeCode2>    |
      | eligibilityStatusCode        | <eligibilityStatusCode2> |
      | categoryCode                 | 10                       |
      | coverageCode                 | cc01                     |
      | fileSource                   | RECIPIENT                |
      | requestedBy                  | 2                        |
    Then I send API call with name "<name>b" for create Eligibility and Enrollment
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in previously created Eligibility Record
      | eligibilityStartDate  | <eligibilityStartDate1>  |
      | eligibilityEndDate    | current                  |
      | categoryCode          | 10                       |
      | programCode           | <programCode1>           |
      | subProgramTypeCode    | <subProgramTypeCode1>    |
      | eligibilityStatusCode | <eligibilityStatusCode1> |
      | genericFieldText2     | <checkCore1>             |
      | createdOn             | current                  |
      | createdBy             | 1                        |
      | updatedOn             | current                  |
      | updatedBy             | 2                        |
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | <eligibilityStartDate2>  |
      | eligibilityEndDate    | <eligibilityEndDate2>    |
      | categoryCode          | 10                       |
      | programCode           | <programCode2>           |
      | subProgramTypeCode    | <subProgramTypeCode2>    |
      | eligibilityStatusCode | <eligibilityStatusCode2> |
      | genericFieldText2     | <checkCore2>             |
      | updatedOn             | current                  |
      | updatedBy             | 2                        |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    # When CP-32018 implemented,below commented out validations will be working
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

    Examples: Different populations
      | name     | scenario                                    | eligibilityStartDate1 | eligibilityStartDate2 | eligibilityEndDate1 | eligibilityEndDate2 | programCode1 | programCode2 | subProgramTypeCode1 | subProgramTypeCode2 | eligibilityStatusCode1 | eligibilityStatusCode2 | checkCore1 | checkCore2 | population    | benefitStatus | action   | consumerAction | planSelectionAllowed | changeAllowedFrom    | changeAllowedUntil        |
      | 24296-19 | Program Sub-Program Change                  | 1stDayofPresentMonth  | 1stDayofPresentMonth  | future              | future              | R            | A            | HoosierHealthwise   | HoosierCareConnect  | M                      | V                      | 0          | 0          | HCC-Voluntary | Eligible      | Required | Enroll         | true                 | 1stDayofPresentMonth | 60DaysFromFirstDayOfMonth |
      | 24296-20 | Program Sub-Program Change                  | 1stDayofPresentMonth  | 1stDayofPresentMonth  | future              | future              | R            | A            | HoosierHealthwise   | HoosierCareConnect  | M                      | X                      | 0          | 1          | HCC-Mandatory | Eligible      | Required | Enroll         | true                 | 1stDayofPresentMonth | 60DaysFromFirstDayOfMonth |
      | 24296-21 | Program Sub-Program Change With Date Change | 1stDayofPresentMonth  | 1stDayofPresentMonth  | future              | highDate            | R            | A            | HoosierHealthwise   | HoosierCareConnect  | X                      | M                      | 1          | 0          | HCC-Mandatory | Eligible      | Required | Enroll         | true                 | 1stDayofPresentMonth | 60DaysFromFirstDayOfMonth |
      | 24296-22 | Program Sub-Program Change With Date Change | 1stDayofPresentMonth  | 1stDayofPresentMonth  | future              | highDate            | A            | R            | HoosierCareConnect  | HoosierHealthwise   | V                      | X                      | 0          | 1          | HHW-Mandatory | Eligible      | Required | Enroll         | true                 | 1stDayofPresentMonth | 14DayFromFirstDayOfMonth  |
      | 24296-23 | Program Sub-Program Change                  | 1stDayofPresentMonth  | 1stDayofPresentMonth  | future              | future              | A            | R            | HoosierCareConnect  | HoosierHealthwise   | X                      | M                      | 1          | 0          | HHW-Mandatory | Eligible      | Required | Enroll         | true                 | 1stDayofPresentMonth | 14DayFromFirstDayOfMonth  |
      | 24296-24 | Program Sub-Program Change With Date Change | 1stDayofPresentMonth  | 1stDayofPresentMonth  | future              | highDate            | A            | R            | HoosierCareConnect  | HoosierHealthwise   | M                      | M                      | 0          | 0          | HHW-Mandatory | Eligible      | Required | Enroll         | true                 | 1stDayofPresentMonth | 14DayFromFirstDayOfMonth  |


  @API-CP-24296 @API-CP-24296-03 @API-CP-24296-04 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: IN-EB Eligibility Create or Update Data: 21 Single record with Non-High End Date (Loss of Eligibility) 3.0 4.0 (HIP)
  3.0 One Inbound BASE Segment Update Matched Eligibility Segment, Program Change
  4.0 One Inbound BASE Segment Add New Eligibility Segment for Program Change
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
      | eligibilityEndDate           | <eligibilityEndDate1>    |
      | programCode                  | <programCode1>           |
      | subProgramTypeCode           | <subProgramTypeCode1>    |
      | eligibilityStatusCode        | <eligibilityStatusCode1> |
      | categoryCode                 | 10                       |
      | genericFieldText1            | Eligible                 |
      | coverageCode                 | cc01                     |
      | fileSource                   | <fileSource1>            |
      | requestedBy                  | 1                        |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | <name>.consumerId     |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofLastMonth    |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | 1stDayofPresentMonth  |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
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
      | eligibilityStartDate         | <eligibilityStartDate2>  |
      | eligibilityEndDate           | <eligibilityEndDate2>    |
      | programCode                  | <programCode2>           |
      | subProgramTypeCode           | <subProgramTypeCode2>    |
      | eligibilityStatusCode        | <eligibilityStatusCode2> |
      | categoryCode                 | 10                       |
      | genericFieldText1            | Eligible                 |
      | coverageCode                 | cc01                     |
      | fileSource                   | <fileSource2>            |
      | requestedBy                  | 2                        |
    Then I send API call with name "<name>b" for create Eligibility and Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | no                       |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | recordId                     | 4                        |
      | enrollmentRecordId           | 4                        |
      | isEnrollmentProviderRequired | no                       |
      | eligibilityStartDate         | <eligibilityStartDate2>  |
      | eligibilityEndDate           | <eligibilityEndDate2>    |
      | enrollmentStartDate          | <eligibilityStartDate2>  |
      | enrollmentEndDate            | <eligibilityEndDate2>    |
      | txnStatus                    | Accepted                 |
      | programCode                  | <programCode2>           |
      | subProgramTypeCode           | <subProgramTypeCode2>    |
      | eligibilityStatusCode        | <eligibilityStatusCode2> |
      | planCode                     | 455701400                |
      | planId                       | 104                      |
      | serviceRegionCode            | Statewide                |
      | anniversaryDate              | anniversaryDate          |
      | channel                      | SYSTEM_INTEGRATION       |
      | rejectionReason              | test1                    |
    And I run create Eligibility and Enrollment API
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in previously created Eligibility Record
      | eligibilityStartDate  | <eligibilityStartDate1>  |
      | eligibilityEndDate    | current                  |
      | categoryCode          | 10                       |
      | programCode           | <programCode1>           |
      | subProgramTypeCode    | <subProgramTypeCode1>    |
      | eligibilityStatusCode | <eligibilityStatusCode1> |
      | genericFieldText1     | Eligible                 |
      | genericFieldText2     | <checkCore1>             |
      | createdOn             | current                  |
      | createdBy             | 1                        |
      | updatedOn             | current                  |
      | updatedBy             | 2                        |
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | <eligibilityStartDate2>  |
      | eligibilityEndDate    | <eligibilityEndDate2>    |
      | categoryCode          | 10                       |
      | programCode           | <programCode2>           |
      | subProgramTypeCode    | <subProgramTypeCode2>    |
      | eligibilityStatusCode | <eligibilityStatusCode2> |
      | genericFieldText1     | Eligible                 |
      | genericFieldText2     | <checkCore2>             |
      | updatedOn             | current                  |
      | updatedBy             | 2                        |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    # When CP-32018 implemented,below commented out validations will be working
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

    Examples: Different populations
      | name     | scenario                                    | eligibilityStartDate1 | eligibilityStartDate2 | eligibilityEndDate1 | eligibilityEndDate2 | fileSource1 | fileSource2 | programCode1 | programCode2 | subProgramTypeCode1 | subProgramTypeCode2 | eligibilityStatusCode1 | eligibilityStatusCode2 | checkCore1 | checkCore2 | population    | benefitStatus | action    | consumerAction              | planSelectionAllowed | changeAllowedFrom    | changeAllowedUntil        |
      | 24296-25 | Program Sub-Program Change With Date Change | 1stDayof2MonthsBefore | 1stDayofPresentMonth  | future              | future              | RECIPIENT   | HRECIP      | R            | H            | HoosierHealthwise   | HealthyIndianaPlan  | M                      | V                      | 0          | 0          | HIP-Voluntary | Enrolled      | Available | Plan Change Open Enrollment | true                 | openEnrollmentDay    | Dec15thCurrentYear        |
      | 24296-26 | Program Sub-Program Change                  | 1stDayof2MonthsBefore | 1stDayof2MonthsBefore | future              | future              | RECIPIENT   | HRECIP      | R            | H            | HoosierHealthwise   | HealthyIndianaPlan  | M                      | X                      | 0          | 1          | HIP-Mandatory | Enrolled      | Available | Plan Change Open Enrollment | true                 | openEnrollmentDay    | Dec15thCurrentYear        |
      | 24296-27 | Program Sub-Program Change With Date Change | 1stDayof2MonthsBefore | 1stDayof2MonthsBefore | future              | highDate            | RECIPIENT   | HRECIP      | R            | H            | HoosierHealthwise   | HealthyIndianaPlan  | X                      | M                      | 1          | 0          | HIP-Mandatory | Enrolled      | Available | Plan Change Open Enrollment | true                 | openEnrollmentDay    | Dec15thCurrentYear        |
    # QA Investigate: Below 3 rows not working. Need to look into this to see if this is an invalid scenario or a defect. Plan Change Open Enrollment is missing in benefit status! - FAIL!
#      | 24296-28 | Program Sub-Program Change With Date Change | 1stDayof2MonthsBefore | 1stDayofPresentMonth  | future              | highDate            | HRECIP      | RECIPIENT   | H            | A            | HealthyIndianaPlan  | HoosierCareConnect  | V                      | X                      | 0          | 1          | HCC-Mandatory | Enrolled      | Available | Plan Change Open Enrollment | true                 | 1stDayofPresentMonth | 60DaysFromFirstDayOfMonth |
#      | 24296-29 | Program Sub-Program Change                  | 1stDayof2MonthsBefore | 1stDayof2MonthsBefore | future              | future              | HRECIP      | RECIPIENT   | H            | A            | HealthyIndianaPlan  | HoosierCareConnect  | X                      | M                      | 1          | 0          | HCC-Mandatory | Enrolled      | Available | Plan Change Open Enrollment | true                 | 1stDayofPresentMonth | 60DaysFromFirstDayOfMonth |
#      | 24296-30 | Program Sub-Program Change With Date Change | 1stDayof2MonthsBefore | 1stDayofPresentMonth  | future              | highDate            | HRECIP      | RECIPIENT   | H            | A            | HealthyIndianaPlan  | HoosierCareConnect  | M                      | V                      | 0          | 0          | HCC-Voluntary | Enrolled      | Available | Plan Change Open Enrollment | true                 | 1stDayofPresentMonth | 60DaysFromFirstDayOfMonth |


  @API-CP-24296 @API-CP-24296-05 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: IN-EB Eligibility Create or Update Data: 21 Single record with Non-High End Date (Loss of Eligibility) 5.0 (HIP and Non HIP)
  5.0 One Inbound BASE Segment: Base Aid Category Change
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
      | eligibilityEndDate           | <eligibilityEndDate1>    |
      | programCode                  | <programCode1>           |
      | subProgramTypeCode           | <subProgramTypeCode1>    |
      | eligibilityStatusCode        | <eligibilityStatusCode1> |
      | categoryCode                 | 10                       |
      | genericFieldText1            | Eligible                 |
      | coverageCode                 | cc01                     |
      | fileSource                   | <fileSource1>            |
      | requestedBy                  | 1                        |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | <name>.consumerId        |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
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
      | eligibilityStartDate         | <eligibilityStartDate2>  |
      | eligibilityEndDate           | <eligibilityEndDate2>    |
      | programCode                  | <programCode2>           |
      | subProgramTypeCode           | <subProgramTypeCode2>    |
      | eligibilityStatusCode        | <eligibilityStatusCode2> |
      | categoryCode                 | 4                        |
      | genericFieldText1            | Eligible                 |
      | coverageCode                 | cc01                     |
      | fileSource                   | <fileSource2>            |
      | requestedBy                  | 2                        |
    Then I send API call with name "<name>b" for create Eligibility and Enrollment
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | <eligibilityStartDate2>  |
      | eligibilityEndDate    | <eligibilityEndDate2>    |
      | categoryCode          | <categoryCode>           |
      | programCode           | <programCode2>           |
      | subProgramTypeCode    | <subProgramTypeCode2>    |
      | eligibilityStatusCode | <eligibilityStatusCode2> |
      | genericFieldText2     | <checkCore>              |
      | updatedOn             | current                  |
      | updatedBy             | 2                        |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    # When CP-32018 implemented,below commented out validations will be working
    Then I verify latest benefit status information with data
      | programPopulation   | <population> |
      | population          | <population> |
#      | benefitStatus       | <benefitStatus>                                    |
      | eligibilityScenario | <scenario>   |
      | timeframe           | Active       |
#    Then I Verify Consumer Actions as following data
#      | [0].action               | <action>               |
#      | [0].consumerAction       | <consumerAction>       |
#      | [0].planSelectionAllowed | <planSelectionAllowed> |
#      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
#      | [0].changeAllowedUntil   | <changeAllowedUntil>   |
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

    Examples: Different populations
    # QA Investigate: Below data rows need to be checked if they are valid. There are issues with Benefit Status and actions
      | name     | scenario             | eligibilityStartDate1 | eligibilityStartDate2 | eligibilityEndDate1 | eligibilityEndDate2 | fileSource1 | fileSource2 | programCode1 | programCode2 | subProgramTypeCode1 | subProgramTypeCode2 | eligibilityStatusCode1 | eligibilityStatusCode2 | checkCore | population    | categoryCode | benefitStatus | action    | consumerAction              | planSelectionAllowed | changeAllowedFrom    | changeAllowedUntil        |
      | 24296-31 | Base Category Change | 1stDayofPresentMonth  | 1stDayofPresentMonth  | future              | future              | RECIPIENT   | RECIPIENT   | R            | R            | HoosierHealthwise   | HoosierHealthwise   | M                      | X                      | 1         | HHW-Mandatory | 4            | Enrolled      | Available | Plan Change Open Enrollment | true                 | 1stDayofPresentMonth | 60DaysFromFirstDayOfMonth |
      | 24296-32 | Base Category Change | 1stDayofPresentMonth  | 1stDayofPresentMonth  | future              | future              | HRECIP      | HRECIP      | H            | H            | HealthyIndianaPlan  | HealthyIndianaPlan  | M                      | M                      | 0         | HIP-Mandatory | 4            | Enrolled      | Available | Plan Change Open Enrollment | true                 | 1stDayofPresentMonth | 60DaysFromFirstDayOfMonth |
      | 24296-33 | Base Category Change | 1stDayofPresentMonth  | 1stDayofPresentMonth  | future              | future              | HRECIP      | HRECIP      | A            | A            | HoosierCareConnect  | HoosierCareConnect  | X                      | V                      | 0         | HCC-Voluntary | 4            | Enrolled      | Available | Plan Change Open Enrollment | true                 | 1stDayofPresentMonth | 60DaysFromFirstDayOfMonth |


