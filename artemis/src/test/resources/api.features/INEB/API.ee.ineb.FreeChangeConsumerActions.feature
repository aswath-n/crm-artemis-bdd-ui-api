Feature: API Free Change Consumer Action Overlapping ang Non Overlapping

  @API-CP-24294 @crm-regression @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario Outline: CP-24294 IN-EB Decide "Free Change" Consumer Actions
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
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | <programCode>         |
      | subProgramTypeCode           | <subProgramTypeCode>  |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
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
      | consumerId                   | <name>.consumerId     |
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
      | programCode                  | <programCode>         |
      | subProgramTypeCode           | <subProgramTypeCode>  |
      | eligibilityStatusCode        | M                     |
      | planCode                     | 399243310             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | <name>.consumerId    |
      | [0].planId             | 26                   |
      | [0].planCode           | 499254630            |
      | [0].startDate          | fdnxtmth::           |
      | [0].endDate            | highDate::           |
      | [0].subProgramTypeCode | <subProgramTypeCode> |
      | [0].serviceRegionCode  | Statewide            |
      | [0].selectionReason    | 02                   |
      | [0].anniversaryDate    | anniversaryDate::    |
      | [0].channel            | SYSTEM_INTEGRATION   |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "<name>.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "<name>a"
    And I send API call with name "" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | <name>.consumerId                |
      | [0].planId             | delete::                         |
      | [0].planCode           | 399243310                        |
      | [0].enrollmentId       | <name>a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED              |
      | [0].txnStatus          | DISENROLL_SUBMITTED              |
      | [0].startDate          | 1stDayof2MonthsBefore::          |
      | [0].endDate            | c.data[0].endDate                |
      | [0].enrollmentType     | delete::                         |
      | [0].subProgramTypeCode | <subProgramTypeCode>             |
      | [0].serviceRegionCode  | Statewide                        |
      | [1].planId             | delete::                         |
      | [1].planCode           | 499254630                        |
      | [1].consumerId         | <name>.consumerId                |
      | [1].enrollmentId       | <name>a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                       |
      | [1].endDate            | highDate::                       |
      | [1].subProgramTypeCode | <subProgramTypeCode>             |
      | [1].serviceRegionCode  | Statewide                        |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 17                   |
      | enrollmentRecordId           | 17                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | enrollmentEndDate            | highDate             |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | planCode                     | 499254630            |
      | planId                       | 26                   |
      | serviceRegionCode            | Statewide            |
      | channel                      | SYSTEM_INTEGRATION   |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth            |
      | endDate           | highDate                     |
      | genericFieldText1 | <name>a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                    |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "NO MATCH","Plan Change Enrollment Accept"
    # Implementation changed
    Then I Verify Consumer Actions as following data
      | [0].action               | Available               |
      | [0].consumerAction       | Plan Change Free Change |
      | [0].planSelectionAllowed | true                    |
      | [0].changeAllowedFrom    | 1stDayof2MonthsBefore   |
      | [0].changeAllowedUntil   | lastDayofPresentMonth   |
    Examples:
      | name    | programCode | subProgramTypeCode |
      | 23958-1 | R           | HoosierHealthwise  |
      | 23958-2 | A           | HoosierCareConnect |
