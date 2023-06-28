Feature: IN-EB HHW-HCC-HIP RECIPIENT, HRECIP, ASSIGN, HASSIGN files TRL validations

  @ETL-CP-23668 @ETL-23668-1.0 @ETL-EE-IN @IN-EB-ETL-Regression @turcan
  Scenario: Validate TRL for Recipient file
    Given I create new file line by name "23668-1a" with data
      | CurrentHealthCareProgram    | A                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    Given I create new file line by name "23668-1b" with data
      | CurrentHealthCareProgram    | R                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    Given I create new file line by name "23668-1c" with data
      | CurrentHealthCareProgram    | A                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    Given I create new file line by name "23668-1d" with data
      | CurrentHealthCareProgram    | R                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    Given I create new file line by name "23668-1e" with data
      | CurrentHealthCareProgram    | R                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    Given I create new file line by name "23668-1f" with data
      | CurrentHealthCareProgram    | A                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 23668-1a |
      | 23668-1b |
      | 23668-1c |
      | 23668-1d |
      | 23668-1e |
      | 23668-1f |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate record content trailer in DB

  @ETL-CP-23669 @ETL-23669-1.0 @ETL-EE-IN @IN-EB-ETL-Regression @turcan
  Scenario: Validate TRL for HRECIP file
    Given I create new file line by name "23669-1a" with data
      | CurrentHealthCareProgram    | H                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    Given I create new file line by name "23669-1b" with data
      | CurrentHealthCareProgram    | H                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    Given I create new file line by name "23669-1c" with data
      | CurrentHealthCareProgram    | H                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    Given I create new file line by name "23669-1d" with data
      | CurrentHealthCareProgram    | H                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    Given I create new file line by name "23669-1e" with data
      | CurrentHealthCareProgram    | H                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    And I create "HRECIP" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 23669-1a |
      | 23669-1b |
      | 23669-1c |
      | 23669-1d |
      | 23669-1e |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate record content trailer in DB

  @ETL-CP-25095 @ETL-25095-1.0 @ETL-EE-IN @IN-EB-ETL-Regression @turcan
    Scenario:Validate TRL for ASSIGN file
      Given I create new file line by name "25095-3a" with data
        | DateEffective    | currentETLver  |
        | DateEnd          | highDateETLver |
        | CodeHealthSubpgm | R              |
        | TransactionType  | A              |
        | MCEId            | 500307680      |
        | CodeReasonStart  | 02             |
      Given I create new file line by name "25095-3b" with data
        | DateEffective    | currentETLver  |
        | DateEnd          | highDateETLver |
        | CodeHealthSubpgm | R              |
        | TransactionType  | A              |
        | MCEId            | 500307680      |
        | CodeReasonStart  | 02             |
      Given I create new file line by name "25095-3c" with data
        | DateEffective    | currentETLver  |
        | DateEnd          | highDateETLver |
        | CodeHealthSubpgm | R              |
        | TransactionType  | A              |
        | MCEId            | 500307680      |
        | CodeReasonStart  | 02             |
      Given I create new file line by name "25095-3d" with data
        | DateEffective    | currentETLver  |
        | DateEnd          | highDateETLver |
        | CodeHealthSubpgm | R              |
        | TransactionType  | A              |
        | MCEId            | 500307680      |
        | CodeReasonStart  | 02             |
      And I create "ASSIGN" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
        | 25095-3a |
        | 25095-3b |
        | 25095-3c |
        | 25095-3d |
      When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
      Then I validate record content trailer in DB

  @ETL-CP-25095 @ETL-25095-2.0 @ETL-EE-IN @IN-EB-ETL-Regression @turcan
    Scenario: Validate TRL for HASSIGN file
      Given I create new file line by name "25095-4a" with data
        | IdMedicaid       | 24311-5a.IdMedicaid |
        | DateEffective    | currentETLver       |
        | DateEnd          | highDateETLver      |
        | CodeHealthSubpgm | H                   |
        | TransactionType  | A                   |
        | MCEId            | 455701400           |
        | CodeReasonStart  | 02                  |
      Given I create new file line by name "25095-4b" with data
        | IdMedicaid       | 24311-5a.IdMedicaid |
        | DateEffective    | currentETLver       |
        | DateEnd          | highDateETLver      |
        | CodeHealthSubpgm | H                   |
        | TransactionType  | A                   |
        | MCEId            | 455701400           |
        | CodeReasonStart  | 02                  |
      Given I create new file line by name "25095-4c" with data
        | IdMedicaid       | 24311-5a.IdMedicaid |
        | DateEffective    | currentETLver       |
        | DateEnd          | highDateETLver      |
        | CodeHealthSubpgm | H                   |
        | TransactionType  | A                   |
        | MCEId            | 455701400           |
        | CodeReasonStart  | 02                  |
      Given I create new file line by name "25095-4d" with data
        | IdMedicaid       | 24311-5a.IdMedicaid |
        | DateEffective    | currentETLver       |
        | DateEnd          | highDateETLver      |
        | CodeHealthSubpgm | H                   |
        | TransactionType  | A                   |
        | MCEId            | 455701400           |
        | CodeReasonStart  | 02                  |
      Given I create new file line by name "25095-4e" with data
        | IdMedicaid       | 24311-5a.IdMedicaid |
        | DateEffective    | currentETLver       |
        | DateEnd          | highDateETLver      |
        | CodeHealthSubpgm | H                   |
        | TransactionType  | A                   |
        | MCEId            | 455701400           |
        | CodeReasonStart  | 02                  |
      And I create "HASSIGN" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
        | 25095-4a |
        | 25095-4b |
        | 25095-4c |
        | 25095-4d |
        | 25095-4e |
      When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
      Then I validate record content trailer in DB


