Feature: HOH Calculation- DCEB Reevaluate Talend Job

  @ETL-CP-40629 @ETL-EE-DC @DC-EB-ETL-Regression @turcan
  Scenario:Reevaluate INIT Staging Validations
    Given I create header for 834 file with data
      | FileType | 2439999981 |
    Given I create new 834 file line by name "40629-1a" with data
      | EligibilityStartDate     | yesterdayETLver   |
      | EligibilityEndDate       | highDateETLver-DC |
      | PlanEnrollmentStartDate  | yesterdayETLver   |
      | PlanEnrollmentEndDate    | highDateETLver-DC |
      | MaintenanceEffectiveDate | yesterdayETLver   |
      | ActionCode               | null              |
      | TransactionType          | B                 |
      | ProgramCode              | 774               |
      | SourceCode               | A                 |
      | PlanCode                 | 055558200         |
    Given I create new 834 file line by name "40629-1b" with data
      | EligibilityStartDate     | yesterdayETLver     |
      | EligibilityEndDate       | highDateETLver-DC   |
      | PlanEnrollmentStartDate  | yesterdayETLver     |
      | PlanEnrollmentEndDate    | highDateETLver-DC   |
      | MaintenanceEffectiveDate | yesterdayETLver     |
      | ActionCode               | null                |
      | TransactionType          | B                   |
      | ProgramCode              | 774                 |
      | SourceCode               | A                   |
      | CaseNumber               | 40629-1a.CaseNumber |
      | PlanCode                 | 055558200           |
    Given I create new 834 file line by name "40629-1c" with data
      | EligibilityStartDate     | yesterdayETLver     |
      | EligibilityEndDate       | highDateETLver-DC   |
      | PlanEnrollmentStartDate  | yesterdayETLver     |
      | PlanEnrollmentEndDate    | highDateETLver-DC   |
      | MaintenanceEffectiveDate | yesterdayETLver     |
      | ActionCode               | null                |
      | TransactionType          | B                   |
      | ProgramCode              | 774                 |
      | SourceCode               | A                   |
      | CaseNumber               | 40629-1a.CaseNumber |
      | PlanCode                 | 055558200           |
    Given I create new 834 file line by name "40629-1d" with data
      | EligibilityStartDate     | yesterdayETLver     |
      | EligibilityEndDate       | highDateETLver-DC   |
      | PlanEnrollmentStartDate  | yesterdayETLver     |
      | PlanEnrollmentEndDate    | highDateETLver-DC   |
      | MaintenanceEffectiveDate | yesterdayETLver     |
      | ActionCode               | null                |
      | TransactionType          | B                   |
      | ProgramCode              | 774D                |
      | SourceCode               | A                   |
      | CaseNumber               | 40629-1a.CaseNumber |
      | PlanCode                 | 055558200           |
    Given I create new 834 file line by name "40629-1d" with data
      | EligibilityStartDate     | yesterdayETLver     |
      | EligibilityEndDate       | highDateETLver-DC   |
      | PlanEnrollmentStartDate  | yesterdayETLver     |
      | PlanEnrollmentEndDate    | highDateETLver-DC   |
      | MaintenanceEffectiveDate | yesterdayETLver     |
      | ActionCode               | null                |
      | TransactionType          | B                   |
      | ProgramCode              | 774D                |
      | SourceCode               | A                   |
      | CaseNumber               | 40629-1a.CaseNumber |
      | PlanCode                 | 055558200           |
    Given I create footer for 834 file with data
      | NumberOfISegment | 5 |
    And I create 834 "DC" file and send to S3 bucket "max-etl-dceb-non-prod" S3folder "QE/dailyEligible/input/" with names list with data
      | 40629-1a |
      | 40629-1b |
      | 40629-1c |
      | 40629-1d |
      | 40629-1f |
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_dailyEligibleJob"
    And Wait for 3 seconds
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_ReevaluatePI"
    Then I validate Reevaluatepi staging table for status INPROGRESS with data
      | projectName | dceb       |
      | tenant      | DC-EB      |
      | status      | INPROGRESS |
    And I validate Reevaluatepi staging table for status COMPLETED with data
      | projectName | dceb      |
      | tenant      | DC-EB     |
      | status      | COMPLETED |

