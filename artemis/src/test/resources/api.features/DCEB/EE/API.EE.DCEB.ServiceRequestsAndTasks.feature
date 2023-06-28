Feature: Service Requests and Tasks

  @API-CP-42879 @API-DC-EE @DC-EB-API-Regression @kursat
  Scenario: DC EB: Enrollment Receives Choice Selection from Consumer, Workflow Closes AA SR
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 42879-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 42879-01.consumerId |
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
    Then I send API call with name "42879-01a" for update Enrollment information
      | [0].consumerId         | 42879-01.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 081080400           |
      | [0].startDate          | fdofmnth::          |
      | [0].endDate            | highdatedc::        |
      | [0].programTypeCode    | MEDICAID            |
      | [0].subProgramTypeCode | DCHF                |
      | [0].serviceRegionCode  | Northeast           |
    And Wait for 3 seconds
    Then I verify latest task information with name "" for consumer id "42879-01.consumerId" with data
      | find_taskTypeId       | 16277              |
      | this_taskTypeId_count | 1                  |
      | taskStatus            | Closed             |
