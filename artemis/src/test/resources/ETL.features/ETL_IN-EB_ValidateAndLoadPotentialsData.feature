Feature: IN-EB Validate and Load Potentials Data

  @ETL-CP-24304 @ETL-CP-24304-1.0 @ETL-EE-IN @IN-EB-ETL-Regression @elshan
  Scenario:ETL: IN-EB Validate and Load Potentials Data - 1.0 Validate Potential Data Fields
    Given I create new file line by name "24304-1a" with data
      | DteAdded        | InvalidStartDateETLver |
      | CdeReason       | C                      |
      | CdeHealthSubpgm | A                      |
    And I create new file line by name "24304-1b" with data
      | DteAdded        | currentETLver |
      | CdeReason       | 2             |
      | CdeHealthSubpgm | A             |
    And I create new file line by name "24304-1c" with data
      | DteAdded        | currentETLver |
      | CdeReason       | C             |
      | CdeHealthSubpgm | Z             |
    And I create "POTENTIAL" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24304-1a |
      | 24304-1b |
      | 24304-1c |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate error messages on last ETL potential staging table in database with data
      | 24304-1a | Invalid Date Format or Missing.                               |
      | 24304-1b | Should be a valid reason code from ENUM_POTENTIALS            |
      | 24304-1c | Should be a valid sub-program code from ENUM_SUB_PROGRAM_TYPE |

  @ETL-CP-24304 @ETL-CP-24304-2.0 @ETL-EE-IN @IN-EB-ETL-Regression @elshan
  Scenario:ETL: IN-EB Validate and Load Potentials Data - 2.0 Identify Up-to-date Potential Record (multiple records with the same Medicaid Id, the last record on the file is to be utilize)
    Given I create new file line by name "24304-2a" with data
      | CurrentHealthCareProgram    | H                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
      | BenefitEffectiveDate        | currentETLver              |
      | BenefitEndDate              | highDateETLver             |
    Given I create new file line by name "24304-2b" with data
      | CurrentHealthCareProgram    | H                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | currentETLver              |
      | BenefitEndDate              | highDateETLver             |
    And I create "HRECIP" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24304-2a |
      | 24304-2b |
    Given I create new file line by name "24304-4a" with data
      | IdMedicaid      | 24304-2a.MemberId |
      | DteAdded        | currentETLver     |
      | CdeReason       | C                 |
      | CdeHealthSubpgm | A                 |
    And I create new file line by name "24304-4b" with data
      | IdMedicaid      | 24304-2b.MemberId |
      | DteAdded        | currentETLver     |
      | CdeReason       | C                 |
      | CdeHealthSubpgm | A                 |
    And I create new file line by name "24304-4c" with data
      | IdMedicaid      | 24304-2b.MemberId |
      | DteAdded        | currentETLver     |
      | CdeReason       | C                 |
      | CdeHealthSubpgm | A                 |
    And I create new file line by name "24304-4d" with data
      | IdMedicaid      | 24304-2b.MemberId |
      | DteAdded        | currentETLver     |
      | CdeReason       | C                 |
      | CdeHealthSubpgm | A                 |
    And I create new file line by name "24304-4e" with data
      | IdMedicaid      | 24304-2b.MemberId |
      | DteAdded        | currentETLver     |
      | CdeReason       | C                 |
      | CdeHealthSubpgm | A                 |
    And I create new file line by name "24304-4f" with data
      | IdMedicaid      | 24304-2b.MemberId |
      | DteAdded        | currentETLver     |
      | CdeReason       | B                 |
      | CdeHealthSubpgm | A                 |
    And I create "POTENTIAL" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24304-4a |
      | 24304-4b |
      | 24304-4c |
      | 24304-4d |
      | 24304-4e |
      | 24304-4f |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I Validate only the last record on the file was processed from multiple lines with the same Medicaid ID






