Feature: [ETL] IN-EB - Validate and Load Level of Care Data

  @ETL-CP-23228 @ETL-23228-2.0 @ETL-EE-IN @IN-EB-ETL-Regression @turcan
  Scenario: Validate Level of Care Fields
    Given I create new file line by name "23228-2a" with data
      | CurrentHealthCareProgram    | H                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    Given I create new file line by name "23228-2b" with data
      | CurrentHealthCareProgram    | H                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    And I create "HRECIP" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 23228-2a |
      | 23228-2b |
    Given I create new file line by name "23228-2c" with data
      | IdMedicaid       | 23228-2a.MemberId |
      | DateEffective    | currentETLver     |
      | DateEnd          | highDateETLver    |
      | CodeHealthSubpgm | H                 |
      | TransactionType  | A                 |
    Given I create new file line by name "23228-2d" with data
      | IdMedicaid       | 23228-2b.MemberId |
      | DateEffective    | currentETLver     |
      | DateEnd          | highDateETLver    |
      | CodeHealthSubpgm | H                 |
      | TransactionType  | R                 |
    And I create "HASSIGN" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 23228-2c |
      | 23228-2d |
    Given I create new file line by name "23228-2e" with data
      | MedicaidId       | 23228-2a.MemberId |
      | LOCDateEffective | null              |
      | LOCDateEnd       | highDateETLver    |
      | CodeLevelOfCare  | 51H               |
    Given I create new file line by name "23228-2f" with data
      | MedicaidId       | 23228-2a.MemberId    |
      | LOCDateEffective | currentETLver        |
      | LOCDateEnd       | InvalidEndDateETLver |
      | CodeLevelOfCare  | ADWA                 |
    Given I create new file line by name "23228-2g" with data
      | MedicaidId       | 23228-2a.MemberId |
      | LOCDateEffective | currentETLver     |
      | LOCDateEnd       | highDateETLver    |
      | CodeLevelOfCare  | ZZZ               |
    Given I create new file line by name "23228-2h" with data
      | MedicaidId       | 23228-2a.MemberId      |
      | LOCDateEffective | InvalidStartDateETLver |
      | LOCDateEnd       | highDateETLver         |
      | CodeLevelOfCare  | 51H                    |
    And I create "LOC" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 23228-2e |
      | 23228-2f |
      | 23228-2g |
      | 23228-2h |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate error messages on last LOC staging table in database with data
      | 23228-2e | Invalid Date or Format. Record is rejected. FOR LOC_DATE_EFFECTIVE column. |
      | 23228-2f | Invalid Date or Format. Record is rejected. FOR LOC END DATE column.       |
      | 23228-2g | Invalid Level of Care Code Found. Record is rejected Incoming value: ZZZ;  |
      | 23228-2h | Invalid Date or Format. Record is rejected. FOR LOC EFF DATE column.       |

  @ETL-CP-33461 @ETL-33461-1.0 @ETL-EE-IN @IN-EB-ETL-Regression @elshan
  Scenario: 1.0 Identify Up-to-date Level of Care Record (Same Level of Care Code)
    Given I create new file line by name "33461-1" with data
      | CurrentHealthCareProgram    | A                           |
      | CurrentAidEligEffectiveDate | firstDayOfCurrentYearETLver |
      | CurrentAidEligEndDate       | highDateETLver              |
    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 33461-1 |
    Given I create new file line by name "33461-1a" with data
      | MedicaidId       | 33461-1.MemberId            |
      | LOCDateEffective | firstDayOfCurrentYearETLver |
      | LOCDateEnd       | Mar5thCurrentYearETLver     |
      | ProviderId       | 200162870                   |
      | CodeLevelOfCare  | 51H                         |
    Given I create new file line by name "33461-1b" with data
      | MedicaidId       | 33461-1.MemberId         |
      | LOCDateEffective | Mar6thCurrentYearETLver  |
      | LOCDateEnd       | Apr27thCurrentYearETLver |
      | ProviderId       | 200162870                |
      | CodeLevelOfCare  | 51H                      |
    Given I create new file line by name "33461-1c" with data
      | MedicaidId       | 33461-1.MemberId         |
      | LOCDateEffective | Apr28thCurrentYearETLver |
      | LOCDateEnd       | May31stCurrentYearETLver |
      | ProviderId       | 200162870                |
      | CodeLevelOfCare  | 51H                      |
    Given I create new file line by name "33461-1d" with data
      | MedicaidId       | 33461-1.MemberId         |
      | LOCDateEffective | June1stCurrentYearETLver |
      | LOCDateEnd       | highDateETLver           |
      | ProviderId       | 200162870                |
      | CodeLevelOfCare  | 51H                      |
    And I create "LOC" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 33461-1a |
      | 33461-1b |
      | 33461-1c |
      | 33461-1d |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I very only one LOC record processed in db with data
