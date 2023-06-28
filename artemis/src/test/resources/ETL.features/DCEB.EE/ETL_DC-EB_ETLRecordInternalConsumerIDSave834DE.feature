Feature: ETL Record Internal Consumer ID as part of 834 DE

  @ETL-CP-43229 @ETL-EE-DC @DC-EB-ETL-Regression @turcan
  Scenario:  INTERNAL_CONSUMER_ID column will be added to ETL_Consumer_Migration table and populated by the DCEB DE job
    Given I create header for 834 file with data
      | FileType | 2439999981 |
    Given I create new 834 file line by name "43229-1a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ActionCode               | E                           |
      | TransactionType          | C                           |
    Given I create new 834 file line by name "43229-1b" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ActionCode               | E                           |
      | TransactionType          | C                           |
    Given I create new 834 file line by name "43229-1c" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ActionCode               | E                           |
      | TransactionType          | C                           |
    Given I create footer for 834 file with data
      | NumberOfISegment | 3 |
    And I create 834 "DC" file and send to S3 bucket "max-etl-dceb-non-prod" S3folder "QE/dailyEligible/input/" with names list with data
      | 43229-1a |
      | 43229-1b |
      | 43229-1c |
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_dailyEligibleJob"
    Then I validate that internal consumer ID is captured in Consumer Migration table