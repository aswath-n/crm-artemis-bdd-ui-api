Feature: [DCEB] ETL: EE: 834 Response File Processing ETL Orchestration - RecordID validations

  @ETL-CP-38641 @ETL-38641-1.0 @ETL-CP-38641-2.0.1 @ETL-EE-DC @DC-EB-ETL-Regression @turcan
  Scenario: ETL Eligibility and Enrollment API request -New Enrollment Accept Response- Record ID 15
    Given I create header for 834 file with data
      | FileType | 2439999981 |
    Given I create new 834 file line by name "38641-1a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ActionCode               | E                           |
      | TransactionType          | C                           |
    Given I create new 834 file line by name "38641-1b" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ActionCode               | E                           |
      | TransactionType          | C                           |
    Given I create new 834 file line by name "38641-1c" with data
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
      | 38641-1a |
      | 38641-1b |
      | 38641-1c |
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_dailyEligibleJob"
    Given I create header for 834 file with data
      | FileType | PSI.202208 |
    Given I create new 834 file line by name "38641-2a" with data
      | SubscriberNumber         | 38641-1a.SubscriberNumber   |
      | CaseNumber               | 38641-1a.CaseNumber         |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ActionCode               | U                           |
      | TransactionType          | C                           |
    Given I create new 834 file line by name "38641-2b" with data
      | SubscriberNumber         | 38641-1b.SubscriberNumber   |
      | CaseNumber               | 38641-1b.CaseNumber         |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ActionCode               | U                           |
      | TransactionType          | E                           |
    Given I create new 834 file line by name "38641-2c" with data
      | SubscriberNumber         | 38641-1c.SubscriberNumber   |
      | CaseNumber               | 38641-1c.CaseNumber         |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ActionCode               | U                           |
      | TransactionType          | G                           |
    Given I create footer for 834 file with data
      | NumberOfISegment | 3 |
    And I create 834 "DC" file and send to S3 bucket "max-etl-dceb-non-prod" S3folder "QE/dailyEligible/input/" with names list with data
      | 38641-2a |
      | 38641-2b |
      | 38641-2c |
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_dailyEligibleJob"
    Then I validate response code on consumer result table
    Then I validate enrollment record id's in database with data based on transaction type and code
      | 38641-2a | 15 |
      | 38641-2b | 15 |
      | 38641-2c | 15 |

  @ETL-CP-38641 @ETL-CP-39571-1.0 @ETL-CP-38641-2.0.2 @ETL-EE-DC @DC-EB-ETL-Regression @turcan
  Scenario: ETL Eligibility and Enrollment API request -New Enrollment Reject Response- Record ID 16
    Given I create header for 834 file with data
      | FileType | 2439999981 |
    Given I create new 834 file line by name "38641-3a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ActionCode               | E                           |
      | TransactionType          | C                           |
    Given I create new 834 file line by name "38641-3b" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ActionCode               | E                           |
      | TransactionType          | C                           |
    Given I create new 834 file line by name "38641-3c" with data
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
      | 38641-3a |
      | 38641-3b |
      | 38641-3c |
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_dailyEligibleJob"
    Given I create header for 834 file with data
      | FileType | PSI.202208 |
    Given I create new 834 file line by name "38641-4a" with data
      | SubscriberNumber         | 38641-3a.SubscriberNumber   |
      | CaseNumber               | 38641-3a.CaseNumber         |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ActionCode               | E                           |
      | TransactionType          | C                           |
      | ErrorCode                | 003                         |
    Given I create new 834 file line by name "38641-4b" with data
      | SubscriberNumber         | 38641-3b.SubscriberNumber   |
      | CaseNumber               | 38641-3b.CaseNumber         |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ActionCode               | E                           |
      | TransactionType          | E                           |
      | ErrorCode                | 003                         |
    Given I create new 834 file line by name "38641-4c" with data
      | SubscriberNumber         | 38641-3c.SubscriberNumber   |
      | CaseNumber               | 38641-3c.CaseNumber         |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ActionCode               | E                           |
      | TransactionType          | G                           |
      | ErrorCode                | 003                         |
    Given I create footer for 834 file with data
      | NumberOfISegment | 3 |
    And I create 834 "DC" file and send to S3 bucket "max-etl-dceb-non-prod" S3folder "QE/dailyEligible/input/" with names list with data
      | 38641-4a |
      | 38641-4b |
      | 38641-4c |
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_dailyEligibleJob"
    Then I validate enrollment record id's in database with data based on transaction type and code
      | 38641-4a | 16 |
      | 38641-4b | 16 |
      | 38641-4c | 16 |

  @ETL-CP-38641 @ETL-CP-38641-2.0.3 @ETL-CP-39390-2.0  @ETL-EE-DC @DC-EB-ETL-Regression @turcan
  Scenario: ETL Eligibility and Enrollment API request -Plan Change Accept Response- Record ID 17
    Given I create header for 834 file with data
      | FileType | 2439999981 |
    Given I create new 834 file line by name "38641-5a" with data
      | EligibilityStartDate     | yesterdayETLver               |
      | EligibilityEndDate       | lastDayOfThePresentYearETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver               |
      | PlanEnrollmentEndDate    | lastDayOfThePresentYearETLver |
      | MaintenanceEffectiveDate | yesterdayETLver               |
      | ActionCode               | E                             |
      | TransactionType          | C                             |
      | PlanCode                 | 044733300                     |
    Given I create footer for 834 file with data
      | NumberOfISegment | 1 |
    And I create 834 "DC" file and send to S3 bucket "max-etl-dceb-non-prod" S3folder "QE/dailyEligible/input/" with names list with data
      | 38641-5a |
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_dailyEligibleJob"
    Given I create header for 834 file with data
      | FileType | PSI.202208 |
    Given I create new 834 file line by name "38641-6b" with data
      | SubscriberNumber         | 38641-5a.SubscriberNumber     |
      | CaseNumber               | 38641-5a.CaseNumber           |
      | EligibilityStartDate     | currentETLver                 |
      | EligibilityEndDate       | lastDayOfThePresentYearETLver |
      | PlanEnrollmentStartDate  | currentETLver                 |
      | PlanEnrollmentEndDate    | lastDayOfThePresentYearETLver |
      | MaintenanceEffectiveDate | yesterdayETLver               |
      | ActionCode               | U                             |
      | TransactionType          | C                             |
      | PlanCode                 | 055558200                     |
    Given I create new 834 file line by name "38641-6a" with data
      | SubscriberNumber         | 38641-5a.SubscriberNumber |
      | CaseNumber               | 38641-5a.CaseNumber       |
      | EligibilityStartDate     | yesterdayETLver           |
      | EligibilityEndDate       | lastDayofNextMonthETLver  |
      | PlanEnrollmentStartDate  | yesterdayETLver           |
      | PlanEnrollmentEndDate    | lastDayofNextMonthETLver  |
      | MaintenanceEffectiveDate | yesterdayETLver           |
      | ActionCode               | U                         |
      | TransactionType          | J                         |
      | PlanCode                 | 044733300                 |
    Given I create footer for 834 file with data
      | NumberOfISegment | 2 |
    And I create 834 "DC" file and send to S3 bucket "max-etl-dceb-non-prod" S3folder "QE/dailyEligible/input/" with names list with data
      | 38641-6a |
      | 38641-6b |
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_dailyEligibleJob"
    Then I validate enrollment record id's in database with data based on transaction type and code
      | 38641-6a | 17 |

  @ETL-CP-38641 @ETL-CP-38641-2.0.4 @ETL-CP-39571-1.0  @ETL-EE-DC @DC-EB-ETL-Regression @turcan
  Scenario: ETL Eligibility and Enrollment API request -Plan Change Reject Response- Record ID 18
    Given I create header for 834 file with data
      | FileType | 2439999981 |
    Given I create new 834 file line by name "38641-7a" with data
      | EligibilityStartDate     | yesterdayETLver               |
      | EligibilityEndDate       | lastDayOfThePresentYearETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver               |
      | PlanEnrollmentEndDate    | lastDayOfThePresentYearETLver |
      | MaintenanceEffectiveDate | yesterdayETLver               |
      | ActionCode               | E                             |
      | TransactionType          | C                             |
    Given I create footer for 834 file with data
      | NumberOfISegment | 1 |
    And I create 834 "DC" file and send to S3 bucket "max-etl-dceb-non-prod" S3folder "QE/dailyEligible/input/" with names list with data
      | 38641-7a |
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_dailyEligibleJob"
    Given I create header for 834 file with data
      | FileType | PSI.202208 |
    Given I create new 834 file line by name "38641-8a" with data
      | SubscriberNumber         | 38641-7a.SubscriberNumber     |
      | CaseNumber               | 38641-7a.CaseNumber           |
      | EligibilityStartDate     | currentETLver                 |
      | EligibilityEndDate       | lastDayOfThePresentYearETLver |
      | PlanEnrollmentStartDate  | currentETLver                 |
      | PlanEnrollmentEndDate    | lastDayOfThePresentYearETLver |
      | MaintenanceEffectiveDate | yesterdayETLver               |
      | ActionCode               | E                             |
      | TransactionType          | C                             |
      | ErrorCode                | 003                           |
    Given I create new 834 file line by name "38641-8b" with data
      | SubscriberNumber         | 38641-7a.SubscriberNumber |
      | CaseNumber               | 38641-7a.CaseNumber       |
      | EligibilityStartDate     | yesterdayETLver           |
      | EligibilityEndDate       | lastDayofNextMonthETLver  |
      | PlanEnrollmentStartDate  | yesterdayETLver           |
      | PlanEnrollmentEndDate    | lastDayofNextMonthETLver  |
      | MaintenanceEffectiveDate | yesterdayETLver           |
      | ActionCode               | E                         |
      | TransactionType          | J                         |
      | ErrorCode                | 003                       |
    Given I create footer for 834 file with data
      | NumberOfISegment | 2 |
    And I create 834 "DC" file and send to S3 bucket "max-etl-dceb-non-prod" S3folder "QE/dailyEligible/input/" with names list with data
      | 38641-8a |
      | 38641-8b |
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_dailyEligibleJob"
    Then I validate enrollment record id's in database with data based on transaction type and code
      | 38641-8a | 18 |

