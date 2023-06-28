Feature: ETL IN-EB Map Inbound Record Types to Internal Scenarios (Default Consumer, Reconciliation, Responses, 21, 15, 16, 17, 18, 99, 100, 101)


  @ETL-CP-24311 @ETL-24311-4.0 @ETL-EE-IN @IN-EB-ETL-Regression @turcan
  Scenario: Verify the Program/Sub-Program for each Assignment Record A.C 4.0
    Given I create new file line by name "24311-4a" with data
      | DateEffective    | currentETLver  |
      | DateEnd          | highDateETLver |
      | CodeHealthSubpgm | R              |
      | TransactionType  | A              |
      | MCEId            | 500307680      |
      | CodeReasonStart  | 02             |
    Given I create new file line by name "24311-4b" with data
      | DateEffective    | currentETLver  |
      | DateEnd          | highDateETLver |
      | CodeHealthSubpgm | A              |
      | TransactionType  | A              |
      | MCEId            | 399243310      |
      | CodeReasonStart  | 02             |
    Given I create new file line by name "24311-4c" with data
      | DateEffective    | currentETLver  |
      | DateEnd          | highDateETLver |
      | CodeHealthSubpgm | Z              |
      | TransactionType  | A              |
      | MCEId            | 599347220      |
      | CodeReasonStart  | 02             |
    And I create "ASSIGN" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24311-4a |
      | 24311-4b |
      | 24311-4c |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate error messages on last ETL ASSIGN UPDATE staging table in database with data
      | 24311-4a |[blank]|
      | 24311-4b |[blank]|
      | 24311-4c | Program Code Invalid |

    Given I create new file line by name "24311-4d" with data
      | DateEffective    | currentETLver  |
      | DateEnd          | highDateETLver |
      | CodeHealthSubpgm | H              |
      | TransactionType  | A              |
      | MCEId            | 455701400      |
      | CodeReasonStart  | 02             |
    Given I create new file line by name "24311-4e" with data
      | DateEffective    | currentETLver  |
      | DateEnd          | highDateETLver |
      | CodeHealthSubpgm | X              |
      | TransactionType  | A              |
      | MCEId            | 455701400      |
      | CodeReasonStart  | 02             |
    And I create "HASSIGN" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24311-4d |
      | 24311-4e |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate error messages on last ETL ASSIGN UPDATE staging table in database with data
      | 24311-4d |[blank]|
      | 24311-4e | Program Code Invalid |

  @ETL-CP-24311 @ETL-24311-5.0.1 @ETL-28612-5.0.1 @ETL-32277-1.0 @ETL-32277-2.0 @ETL-EE-IN @IN-EB-ETL-Regression @turcan
  Scenario: When Sent, Keep Multiple Assignment Records for each Consumer A.C. 5.0
    Given I create new file line by name "24311-5a" with data
      | DateEffective    | currentETLver               |
      | DateEnd          | lastDayofPresentMonthETLver |
      | CodeHealthSubpgm | R                           |
      | TransactionType  | A                           |
      | MCEId            | 500307680                   |
      | CodeReasonStart  | 02                          |
    Given I create new file line by name "24311-5b" with data
      | IdMedicaid       | 24311-5a.IdMedicaid       |
      | DateEffective    | firstdayofNextMonthETLver |
      | DateEnd          | lastDayofNextMonthETLver  |
      | CodeHealthSubpgm | R                         |
      | TransactionType  | C                         |
      | MCEId            | 500307680                 |
      | CodeReasonStart  | 02                        |
    Given I create new file line by name "24311-5c" with data
      | IdMedicaid       | 24311-5a.IdMedicaid       |
      | DateEffective    | firstdayofNextMonthETLver |
      | DateEnd          | lastDayofNextMonthETLver  |
      | CodeHealthSubpgm | R                         |
      | TransactionType  | D                         |
      | MCEId            | 500307680                 |
      | CodeReasonStart  | 02                        |
    Given I create new file line by name "24311-5d" with data
      | IdMedicaid       | 24311-5a.IdMedicaid |
      | DateEffective    | yesterdayETLver     |
      | DateEnd          | highDateETLver      |
      | CodeHealthSubpgm | R                   |
      | TransactionType  | A                   |
      | MCEId            | 500307680           |
      | CodeReasonStart  | 02                  |
    And I create "ASSIGN" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24311-5a |
      | 24311-5b |
      | 24311-5c |
      | 24311-5d |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate multiple response records in payload with data
      | 28612-5a | A |

  @ETL-CP-24311 @ETL-24311-5.0.2 @ETL-28612-5.0.2 @ETL-32279-1.0 @ETL-32279-2.0 @ETL-EE-IN @IN-EB-ETL-Regression @turcan
  Scenario: When Sent, Keep Multiple Assignment Records for each Consumer A.C. 5.0- All D transaction Types
    Given I create new file line by name "24311-5e" with data
      | DateEffective    | currentETLver               |
      | DateEnd          | lastDayofPresentMonthETLver |
      | CodeHealthSubpgm | R                           |
      | TransactionType  | D                           |
      | MCEId            | 500307680                   |
      | CodeReasonStart  | 02                          |
    Given I create new file line by name "24311-5f" with data
      | IdMedicaid       | 24311-5e.IdMedicaid       |
      | DateEffective    | firstdayofNextMonthETLver |
      | DateEnd          | lastDayofNextMonthETLver  |
      | CodeHealthSubpgm | R                         |
      | TransactionType  | D                         |
      | MCEId            | 500307680                 |
      | CodeReasonStart  | 02                        |
    Given I create new file line by name "24311-5g" with data
      | IdMedicaid       | 24311-5e.IdMedicaid       |
      | DateEffective    | firstdayofNextMonthETLver |
      | DateEnd          | lastDayofNextMonthETLver  |
      | CodeHealthSubpgm | R                         |
      | TransactionType  | D                         |
      | MCEId            | 500307680                 |
      | CodeReasonStart  | 02                        |
    Given I create new file line by name "24311-5h" with data
      | IdMedicaid       | 24311-5e.IdMedicaid |
      | DateEffective    | yesterdayETLver     |
      | DateEnd          | highDateETLver      |
      | CodeHealthSubpgm | R                   |
      | TransactionType  | D                   |
      | MCEId            | 500307680           |
      | CodeReasonStart  | 02                  |
    And I create "ASSIGN" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24311-5e |
      | 24311-5f |
      | 24311-5g |
      | 24311-5h |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate multiple response records in payload with data
      | 28612-5a | D |

  @ETL-CP-24311 @ETL-24311-6.0 @ETL-EE-IN @IN-EB-ETL-Regression @turcan
  Scenario: When Sent, Keep Multiple Level of Care (LOC) Records for each Consumer A.C. 6.0
    Given I create new file line by name "24311-6a" with data
      | CurrentHealthCareProgram    | H                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    Given I create new file line by name "24311-6b" with data
      | CurrentHealthCareProgram    | H                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    And I create "HRECIP" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24311-6a |
      | 24311-6b |
    Given I create new file line by name "24311-6c" with data
      | IdMedicaid       | 24311-6a.MemberId |
      | DateEffective    | currentETLver     |
      | DateEnd          | highDateETLver    |
      | CodeHealthSubpgm | H                 |
      | TransactionType  | A                 |
    Given I create new file line by name "24311-6d" with data
      | IdMedicaid       | 24311-6b.MemberId |
      | DateEffective    | currentETLver     |
      | DateEnd          | highDateETLver    |
      | CodeHealthSubpgm | H                 |
      | TransactionType  | R                 |
    And I create "HASSIGN" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24311-6c |
      | 24311-6d |
    Given I create new file line by name "24311-6e" with data
      | MedicaidId       | 24311-6a.MemberId |
      | LOCDateEffective | currentETLver     |
      | LOCDateEnd       | highDateETLver    |
      | CodeLevelOfCare  | 51H               |
    Given I create new file line by name "24311-6f" with data
      | MedicaidId       | 24311-6a.MemberId |
      | LOCDateEffective | currentETLver     |
      | LOCDateEnd       | highDateETLver    |
      | CodeLevelOfCare  | ADWA              |
    Given I create new file line by name "24311-6g" with data
      | MedicaidId       | 24311-6a.MemberId |
      | LOCDateEffective | currentETLver     |
      | LOCDateEnd       | highDateETLver    |
      | CodeLevelOfCare  | NHN               |
    And I create "LOC" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24311-6e |
      | 24311-6f |
      | 24311-6g |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate LOC eligibility in payload with data
      | 24311-6e | HOSPICE  |
      | 24311-6f | WAIVER   |
      | 24311-6g | FACILITY |

  @ETL-CP-24311 @ETL-24311-7.0 @ETL-EE-IN @IN-EB-ETL-Regression @turcan
  Scenario: When Sent, Keep Multiple Lockin Lockout (LILO) Records for each Consumer
    Given I create new file line by name "24311-7a" with data
      | CurrentHealthCareProgram    | H                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    Given I create new file line by name "24311-7b" with data
      | CurrentHealthCareProgram    | H                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    And I create "HRECIP" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24311-7a |
      | 24311-7b |
    Given I create new file line by name "24311-7c" with data
      | IdMedicaid       | 24311-7a.MemberId |
      | DateEffective    | currentETLver     |
      | DateEnd          | highDateETLver    |
      | CodeHealthSubpgm | H                 |
      | TransactionType  | A                 |
    Given I create new file line by name "24311-7d" with data
      | IdMedicaid       | 24311-7b.MemberId |
      | DateEffective    | currentETLver     |
      | DateEnd          | highDateETLver    |
      | CodeHealthSubpgm | H                 |
      | TransactionType  | R                 |
    And I create "HASSIGN" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24311-7c |
      | 24311-7d |
    Given I create new file line by name "24311-7e" with data
      | MedicaidId            | 24311-7a.MemberId |
      | MemberDateEffective   | currentETLver     |
      | MemberDateEnd         | highDateETLver    |
      | ProviderDateEffective | currentETLver     |
      | ProviderDateEnd       | highDateETLver    |
    Given I create new file line by name "24311-7f" with data
      | MedicaidId            | 24311-7a.MemberId |
      | MemberDateEffective   | currentETLver     |
      | MemberDateEnd         | highDateETLver    |
      | ProviderDateEffective | currentETLver     |
      | ProviderDateEnd       | highDateETLver    |
    Given I create new file line by name "24311-7g" with data
      | MedicaidId            | 24311-7a.MemberId |
      | MemberDateEffective   | currentETLver     |
      | MemberDateEnd         | highDateETLver    |
      | ProviderDateEffective | currentETLver     |
      | ProviderDateEnd       | highDateETLver    |
    And I create "LOCKIN" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24311-7e |
      | 24311-7f |
      | 24311-7g |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate LILO enrollment in payload with data
      | 24311-7e | LILO |
      | 24311-7f | LILO |
      | 24311-7g | LILO |

  @ETL-CP-24311 @ETL-24311-9.0 @ETL-EE-IN @IN-EB-ETL-Regression @turcan
  Scenario: Map Inbound Records to Internal Scenario Code A.C. 9.0 (Enrollment Record Id 15,16,17,18 and Case 100)
    Given I create new file line by name "24311-9a" with data
      | CurrentHealthCareProgram    | A                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    Given I create new file line by name "24311-9b" with data
      | CurrentHealthCareProgram    | R                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24311-9a |
      | 24311-9b |
    Given I create new file line by name "24311-9c" with data
      | IdMedicaid       | 24311-9a.RecipientID |
      | DateEffective    | currentETLver        |
      | DateEnd          | highDateETLver       |
      | CodeHealthSubpgm | A                    |
      | TransactionType  | A                    |
      | MCEId            | 499254630            |
      | CodeReasonStart  | 02                   |
    Given I create new file line by name "24311-9d" with data
      | IdMedicaid       | 24311-9b.RecipientID |
      | DateEffective    | currentETLver        |
      | DateEnd          | highDateETLver       |
      | CodeHealthSubpgm | R                    |
      | TransactionType  | A                    |
      | MCEId            | 500307680            |
      | CodeReasonStart  | 02                   |
    And I create "ASSIGN" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24311-9c |
      | 24311-9d |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Given I create new file line by name "24311-9e" with data
      | IdMedicaid             | 24311-9a.RecipientID |
      | DateEffective          | currentETLver        |
      | DateEnd                | highDateETLver       |
      | CodeHealthSubpgm       | A                    |
      | TransactionType        | A                    |
      | MCEId                  | 499254630            |
      | TransactionDisposition | A                    |
      | CodeReasonStart        | 02                   |
    Given I create new file line by name "24311-9f" with data
      | IdMedicaid             | 24311-9b.RecipientID |
      | DateEffective          | currentETLver        |
      | DateEnd                | highDateETLver       |
      | CodeHealthSubpgm       | R                    |
      | TransactionType        | A                    |
      | MCEId                  | 500307680            |
      | TransactionDisposition | R                    |
      | CodeReasonStart        | 02                   |
    And I create "RESPONSE" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24311-9e |
      | 24311-9f |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleResponse"
    Then I validate enrollment record id's in database with data
      | 24311-9e | 15 |
      | 24311-9f | 16 |
    Given I create new file line by name "24311-9h" with data
      | IdMedicaid       | 24311-9a.RecipientID |
      | DateEffective    | currentETLver        |
      | DateEnd          | highDateETLver       |
      | CodeHealthSubpgm | A                    |
      | TransactionType  | A                    |
      | MCEId            | 399243310            |
      | CodeReasonStart  | 94                   |
    Given I create new file line by name "24311-9j" with data
      | IdMedicaid       | 24311-9b.RecipientID |
      | DateEffective    | currentETLver        |
      | DateEnd          | highDateETLver       |
      | CodeHealthSubpgm | R                    |
      | TransactionType  | A                    |
      | MCEId            | 399243310            |
      | CodeReasonStart  | 94                   |
    And I create "ASSIGN" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24311-9h |
      | 24311-9j |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Given I create new file line by name "24311-9k" with data
      | IdMedicaid             | 24311-9a.RecipientID |
      | DateEffective          | currentETLver        |
      | DateEnd                | highDateETLver       |
      | CodeHealthSubpgm       | A                    |
      | TransactionType        | A                    |
      | MCEId                  | 399243310            |
      | TransactionDisposition | A                    |
      | CodeReasonStart        | 94                   |
    Given I create new file line by name "24311-9l" with data
      | IdMedicaid             | 24311-9b.RecipientID |
      | DateEffective          | currentETLver        |
      | DateEnd                | highDateETLver       |
      | CodeHealthSubpgm       | R                    |
      | TransactionType        | A                    |
      | MCEId                  | 399243310            |
      | TransactionDisposition | R                    |
      | CodeReasonStart        | 94                   |
    And I create "RESPONSE" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24311-9k |
      | 24311-9l |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleResponse"
    Then I validate enrollment record id's in database with data
      | 24311-9k | 17 |
      | 24311-9l | 18 |

  @ETL-CP-24311 @ETL-24311-9.0 @ETL-CP-33454 @ETL-EE-IN @IN-EB-ETL-Regression @turcan
  Scenario: Map Inbound Records to Internal Scenario Code A.C. 9.0 (Enrollment Record Id 17,18 and Case 100) HIP
    Given I create new file line by name "24311-10a" with data
      | CurrentHealthCareProgram    | H                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    Given I create new file line by name "24311-10b" with data
      | CurrentHealthCareProgram    | H                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    And I create "HRECIP" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24311-10a |
      | 24311-10b |
    Given I create new file line by name "24311-10c" with data
      | IdMedicaid       | 24311-10a.MemberId |
      | DateEffective    | currentETLver      |
      | DateEnd          | highDateETLver     |
      | CodeHealthSubpgm | H                  |
      | TransactionType  | A                  |
      | MCEId            | 455701400          |
      | CodeReasonStart  | HT                 |
    Given I create new file line by name "24311-10d" with data
      | IdMedicaid       | 24311-10b.MemberId |
      | DateEffective    | currentETLver      |
      | DateEnd          | highDateETLver     |
      | CodeHealthSubpgm | H                  |
      | TransactionType  | A                  |
      | MCEId            | 455701400          |
      | CodeReasonStart  | HT                 |
    And I create "HASSIGN" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24311-10c |
      | 24311-10d |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Given I create new file line by name "24311-10k" with data
      | IdMedicaid             | 24311-10a.MemberId |
      | DateEffective          | currentETLver      |
      | DateEnd                | highDateETLver     |
      | CodeHealthSubpgm       | H                  |
      | TransactionType        | A                  |
      | MCEId                  | 755726440          |
      | TransactionDisposition | A                  |
      | CodeReasonStart        | FE                 |
    Given I create new file line by name "24311-10l" with data
      | IdMedicaid             | 24311-10a.MemberId |
      | DateEffective          | currentETLver      |
      | DateEnd                | highDateETLver     |
      | CodeHealthSubpgm       | H                  |
      | TransactionType        | D                  |
      | MCEId                  | 455701400          |
      | TransactionDisposition | A                  |
      | CodeReasonStart        | HT                 |
    Given I create new file line by name "24311-10m" with data
      | IdMedicaid             | 24311-10b.MemberId |
      | DateEffective          | currentETLver      |
      | DateEnd                | highDateETLver     |
      | CodeHealthSubpgm       | H                  |
      | TransactionType        | A                  |
      | MCEId                  | 755726440          |
      | TransactionDisposition | R                  |
      | CodeReasonStart        | TE                 |
    Given I create new file line by name "24311-10n" with data
      | IdMedicaid             | 24311-10b.MemberId |
      | DateEffective          | currentETLver      |
      | DateEnd                | highDateETLver     |
      | CodeHealthSubpgm       | H                  |
      | TransactionType        | D                  |
      | MCEId                  | 455701400          |
      | TransactionDisposition | R                  |
      | CodeReasonStart        | HT                 |
    And I create "HRESPONSE" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24311-10k |
      | 24311-10l |
      | 24311-10m |
      | 24311-10n |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleResponse"
    Then I validate enrollment record id's in database with data
      | 24311-10k | 17 |
      | 24311-10l | 18 |


  @ETL-CP-33462 @ETL-EE-IN @IN-EB-ETL-Regression @Beka
  Scenario: ETL-CC Demographic Field Validation
    Given I create new file line by name "33462-1a" with data
      | CurrentHealthCareProgram    | A                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
      | RecipWardType               | null                       |
    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 33462-1a |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate error messages on last ETL ASSIGN UPDATE staging table in database with data new CC
      | 33462-1a | RECIP_WARD_TYPE:empty or null |

  @ETL-CP-25568 @ETL-CP-25568-1 @ETL-EE-IN @IN-EB-ETL-Regression @Beka
  Scenario: ETL-CC End-Date Consumer’s Role on the Case (Loss of Eligibility)
    Given I create new file line by name "25568-1a" with data
      | CurrentHealthCareProgram    | A                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 25568-1a |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"

    Given I create new file line by name "25568-1b" with data
      | RecipientID                 | 25568-1a.RecipientID       |
      | CurrentHealthCareProgram    | A                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | pastETL                    |
    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 25568-1b |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate LOC eligibility in payload with data new
      | 25568-1b | 13 |

  @ETL-CP-25568 @ETL-CP-25568-2 @ETL-EE-IN @IN-EB-ETL-Regression @Beka
  Scenario: ETL-CC End-Date Consumer’s Role on the Case (Loss of Eligibility) HRECIP
    Given I create new file line by name "25568-2a" with data
      | CurrentHealthCareProgram    | H                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    And I create "HRECIP" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 25568-2a |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"

    Given I create new file line by name "25568-2b" with data
      | MemberId                    | 25568-2a.MemberId          |
      | CurrentHealthCareProgram    | H                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | pastETL                    |
    And I create "HRECIP" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 25568-2b |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate LOC eligibility in payload with data new
      | 25568-2b | 13 |

  @ETL-CP-33892 @ETL-CP-33892-1 @ETL-EE-IN @IN-EB-ETL-Regression @turcan
  Scenario: Missing case number in RECIPIENT file should set record ID 1O1
    Given I create new file line by name "33892-1a" with data
      | CurrentHealthCareProgram    | A                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
      | CaseNumber                  | null                       |
    Given I create new file line by name "33892-1b" with data
      | CurrentHealthCareProgram    | R                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
      | CaseNumber                  | null                       |
    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 33892-1a |
      | 33892-1b |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate case record id's in database with data
      | 33892-1a | 101 |
      | 33892-1b | 101 |

  @ETL-CP-24311 @ETL-24311-8.0 @ETL-28612-8.0 @ETL-EE-IN @IN-EB-ETL-Regression @turcan
  Scenario: When Sent Keep Multiple Response Records for Each Consumer
    Given I create new file line by name "28612-1a" with data
      | DateEffective          | currentETLver  |
      | DateEnd                | highDateETLver |
      | CodeHealthSubpgm       | A              |
      | TransactionType        | A              |
      | MCEId                  | 499254630      |
      | TransactionDisposition | A              |
      | CodeReasonStart        | 02             |
    Given I create new file line by name "28612-1b" with data
      | IdMedicaid             | 28612-1a.IdMedicaid |
      | DateEffective          | currentETLver       |
      | DateEnd                | highDateETLver      |
      | CodeHealthSubpgm       | R                   |
      | TransactionType        | C                   |
      | MCEId                  | 500307680           |
      | TransactionDisposition | A                   |
      | CodeReasonStart        | 02                  |
    Given I create new file line by name "28612-1c" with data
      | IdMedicaid             | 28612-1a.IdMedicaid |
      | DateEffective          | currentETLver       |
      | DateEnd                | highDateETLver      |
      | CodeHealthSubpgm       | R                   |
      | TransactionType        | D                   |
      | MCEId                  | 500307680           |
      | TransactionDisposition | A                   |
      | CodeReasonStart        | 02                  |
    And I create "RESPONSE" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 28612-1a |
      | 28612-1b |
      | 28612-1c |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleResponse"
    Then I validate multiple response records in payload with data
      | 28612-1a | A |
      | 28612-1b | C |
      | 28612-1c | D |








