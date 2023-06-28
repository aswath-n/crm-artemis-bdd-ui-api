Feature: MMIS Sends Current and Prior Medicaid IDs

  @ETL-CP-38985 @ETL-CP-38985-1.0 @ETL-CP-38985-2.0 @ETL-CP-38985-3.0 @ETL-EE-DC @DC-EB-ETL-Regression @erkan
  Scenario: Send Old and New Medicaid IDs to Consumer microservice
    Given I create header for 834 file with data
      | FileType | 2439999981 |
    Given I create new 834 file line by name "38985-1a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ActionCode               | E                           |
      | TransactionType          | C                           |
    Given I create footer for 834 file with data
      | NumberOfISegment | 1 |
    And I create 834 "DC" file and send to S3 bucket "max-etl-dceb-non-prod" S3folder "QE/dailyEligible/input/" with names list with data
      | 38985-1a |
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_dailyEligibleJob"
    Then I validate prior consumer medicaid id in caseloader result payload for consumer
    Given I create header for 834 file with data
      | FileType | 2439999981 |
    Given I create new 834 file line by name "38985-2a" with data
      | PriorIdentifierNumber    | 38985-1a.MemberMedicateID   |
      | MemberMedicateID         | 38985-1a.MemberMedicateID   |
      | SubscriberNumber         | 38985-1a.MemberMedicateID   |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ActionCode               | E                           |
      | TransactionType          | C                           |
    Given I create footer for 834 file with data
      | NumberOfISegment | 1 |
    And I create 834 "DC" file and send to S3 bucket "max-etl-dceb-non-prod" S3folder "QE/dailyEligible/input/" with names list with data
      | 38985-2a |
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_dailyEligibleJob"
    Then I validate prior consumer medicaid id as null in caseloader result payload for consumer



