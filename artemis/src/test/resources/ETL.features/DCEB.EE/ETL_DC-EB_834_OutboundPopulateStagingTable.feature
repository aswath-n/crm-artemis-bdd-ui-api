Feature: Outbound Staging Tables

  @ETL-CP-36558 @ETL-CP-36558-1.0 @ETL-CP-36558-2.0 @ETL-CP-36558-3.0 @DC-EB-ETL-Regression @turcan
  Scenario: ETL: E&E 834 Outbound - Populate Staging Table - Auto Assign
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 36558-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 36558-01.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |                     |
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
    Then I send API call with name "36558-01a" for update Enrollment information
      | [0].consumerId         | 36558-01.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 081080400           |
      | [0].startDate          | fdofmnth::          |
      | [0].endDate            | highdatedc::        |
      | [0].programTypeCode    | MEDICAID            |
      | [0].subProgramTypeCode | DCHF                |
      | [0].serviceRegionCode  | Northeast           |
    And Wait for 3 seconds
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_selectionExtractJob"
    Then I validate selection status in staging table for Selections with data
      | TxnStatus | SELECTION_MADE |
    Then I validate consumer data in staging table
