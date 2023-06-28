Feature: PI Calculation- INEB Reevaluate Talend Job

  @ETL-CP-37357 @ETL-EE-IN @IN-EB-ETL-Regression @turcan
  Scenario: Reevaluate INIT Staging Validations
    Given I create new file line by name "37357-1a" with data
      | CurrentHealthCareProgram    | A               |
      | CurrentAidEligEffectiveDate | yesterdayETLver |
      | CurrentAidEligEndDate       | highDateETLver  |
    Given I create new file line by name "37357-1b" with data
      | CurrentHealthCareProgram    | A                   |
      | CurrentAidEligEffectiveDate | yesterdayETLver     |
      | CurrentAidEligEndDate       | highDateETLver      |
      | CaseNumber                  | 37357-1a.CaseNumber |
    Given I create new file line by name "37357-1c" with data
      | CurrentHealthCareProgram    | A                   |
      | CurrentAidEligEffectiveDate | yesterdayETLver     |
      | CurrentAidEligEndDate       | highDateETLver      |
      | CaseNumber                  | 37357-1a.CaseNumber |
    Given I create new file line by name "37357-1d" with data
      | CurrentHealthCareProgram    | A                   |
      | CurrentAidEligEffectiveDate | yesterdayETLver     |
      | CurrentAidEligEndDate       | highDateETLver      |
      | CaseNumber                  | 37357-1a.CaseNumber |
    Given I create new file line by name "37357-1e" with data
      | CurrentHealthCareProgram    | A                   |
      | CurrentAidEligEffectiveDate | yesterdayETLver     |
      | CurrentAidEligEndDate       | highDateETLver      |
      | CaseNumber                  | 37357-1a.CaseNumber |
    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 37357-1a |
      | 37357-1b |
      | 37357-1c |
      | 37357-1d |
      | 37357-1e |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    And Wait for 3 seconds
    When I run talent job for environment "QE" project "INEB" job name "reevaluatePI"
    Then I validate Reevaluatepi staging table for status INPROGRESS with data
      | projectName | ineb       |
      | tenant      | IN-EB      |
      | status      | INPROGRESS |
    And I validate Reevaluatepi staging table for status COMPLETED with data
      | projectName | ineb      |
      | tenant      | IN-EB     |
      | status      | COMPLETED |