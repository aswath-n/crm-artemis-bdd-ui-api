Feature: HHW & HCC and HIP Demographic Files - C&C DB Additions

  @ETL-CP-23617 @ETL-23617-01 @ETL-CC-IN @IN-EB-ETL-Regression @muhabbat
  Scenario:[ETL] IN-EB HHW & HCC New Demographic Fields: MaritalCode, Ward Type, County Ward, Indicator Active, PMP Paper
    Given I create new file line by name "23617-1a" with data
      | RecipWardType               | null                       |
      | CurrentHealthCareProgram    | A                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    And I create new file line by name "23617-1b" with data
      | RecipMaritalCode            | null                       |
      | CurrentHealthCareProgram    | A                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    And I create new file line by name "23617-1c" with data
      | RecipWardType               | C                          |
      | RecipCountyWard             | null                       |
      | CurrentHealthCareProgram    | A                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    And I create new file line by name "23617-1d" with data
      | RecipIndActive              | null                       |
      | CurrentHealthCareProgram    | A                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    And I create new file line by name "23617-1e" with data
      | PMPIndicator                | null                       |
      | CurrentHealthCareProgram    | A                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 23617-1a |
      | 23617-1b |
      | 23617-1c |
      | 23617-1d |
      | 23617-1e |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate error messages on last ETL demographic staging table in database with data
      | 23617-1a | RECIP_WARD_TYPE:empty or null              |
      | 23617-1b | Eligibility Status Code is missing or null |
      | 23617-1c | County Ward is missing                     |
      | 23617-1d |[blank]|
      | 23617-1e |[blank]|

  @ETL-CP-23617 @ETL-23617-01.2 @ETL-CC-IN @IN-EB-ETL-Regression @muhabbat
  Scenario:[ETL] IN-EB HHW & HCC New Demographic Fields: MaritalCode, Ward Type, County Ward, Indicator Active, PMP Paper
    Given I create new file line by name "23617-1aa" with data
      | RecipWardType               | null                       |
      | CurrentHealthCareProgram    | A                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    And I create new file line by name "23617-1bb" with data
      | RecipMaritalCode            | null                       |
      | CurrentHealthCareProgram    | A                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    And I create new file line by name "23617-1cc" with data
      | RecipWardType               | C                          |
      | RecipCountyWard             | null                       |
      | CurrentHealthCareProgram    | A                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    And I create new file line by name "23617-1dd" with data
      | RecipIndActive              | null                       |
      | CurrentHealthCareProgram    | A                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    And I create new file line by name "23617-1ee" with data
      | PMPIndicator                | null                       |
      | CurrentHealthCareProgram    | A                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    And I create "HRECIP" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 23617-1aa |
      | 23617-1bb |
      | 23617-1cc |
      | 23617-1dd |
      | 23617-1ee |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate error messages on last ETL demographic staging table in database with data
      | 23617-1aa | RECIP_WARD_TYPE:empty or null              |
      | 23617-1bb | Eligibility Status Code is missing or null |
      | 23617-1cc | County Ward is missing                     |
      | 23617-1dd |[blank]|
      | 23617-1ee |[blank]|

  @ETL-CP-23617 @ETL-23617-2.0 @ETL-CC-IN @IN-EB-ETL-Regression @muhabbat
  Scenario:[ETL] IN-EB HIP Only New Demographic Fields: Spouse Member ID, Pregnancy SDdate, Pregnancy EDate, MedFrail ConfCode, MedFrail last ConfAssesDate
    Given I create new file line by name "23617-2a" with data
      | CurrentHealthCareProgram    | H                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
      | SpouseMemberId              |[blank]|
    Given I create new file line by name "23617-2b" with data
      | CurrentHealthCareProgram    | H                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
      | PregnancyStartDate          |[blank]|
    Given I create new file line by name "23617-2c" with data
      | CurrentHealthCareProgram    | H                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
      | PregnancyEndDate            |[blank]|
    Given I create new file line by name "23617-2d" with data
      | CurrentHealthCareProgram       | H                          |
      | CurrentAidEligEffectiveDate    | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate          | highDateETLver             |
      | MedicallyFrailConfirmationCode |[blank]|
    Given I create new file line by name "23617-2e" with data
      | CurrentHealthCareProgram                  | H                          |
      | CurrentAidEligEffectiveDate               | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate                     | highDateETLver             |
      | MedicallyFrailLastConfirmedAssessmentDate |[blank]|
    And I create "HRECIP" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 23617-2a |
      | 23617-2b |
      | 23617-2c |
      | 23617-2d |
      | 23617-2e |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate error messages on last ETL demographic staging table in database with data
      | 23617-2a |[blank]|
      | 23617-2b |[blank]|
      | 23617-2c |[blank]|
      | 23617-2d |[blank]|
      | 23617-2e |[blank]|

  @ETL-CP-29121 @ETL-29121-1.0 @ETL-CC-IN @IN-EB-ETL-Regression @muhabbat
  Scenario:[ETL] IN-EB HIP Only - Update Demographic Fields: Spouse Member ID, Pregnancy SDdate, Pregnancy EDate, MedFrail ConfCode, MedFrail last ConfAssesDate
    Given I create new file line by name "29121-1a" with data
      | CurrentHealthCareProgram    | H                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
      | SpouseMemberId              |[blank]|
    Given I create new file line by name "29121-1b" with data
      | CurrentHealthCareProgram    | H                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
      | PregnancyStartDate          | null                       |
    Given I create new file line by name "29121-1c" with data
      | CurrentHealthCareProgram    | H                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
      | PregnancyEndDate            | null                       |
    Given I create new file line by name "29121-1d" with data
      | CurrentHealthCareProgram       | H                          |
      | CurrentAidEligEffectiveDate    | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate          | highDateETLver             |
      | MedicallyFrailConfirmationCode |[blank]|
    Given I create new file line by name "29121-1e" with data
      | CurrentHealthCareProgram                  | H                          |
      | CurrentAidEligEffectiveDate               | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate                     | highDateETLver             |
      | MedicallyFrailLastConfirmedAssessmentDate | null                       |
    And I create "HRECIP" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 29121-1a |
      | 29121-1b |
      | 29121-1c |
      | 29121-1d |
      | 29121-1e |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Given I create new file line by name "29121-1f" with data
      | MemberId                    | 29121-1a.MemberId          |
      | DateEffective               | currentETLver              |
      | CurrentHealthCareProgram    | H                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
      | SpouseMemberId              | 29121-1a.MemberId          |
    Given I create new file line by name "29121-1g" with data
      | MemberId                    | 29121-1b.MemberId          |
      | DateEffective               | currentETLver              |
      | CurrentHealthCareProgram    | H                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
      | PregnancyStartDate          | pastETL                    |
    Given I create new file line by name "29121-1h" with data
      | MemberId                    | 29121-1c.MemberId              |
      | DateEffective               | currentETLver                  |
      | CurrentHealthCareProgram    | H                              |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver     |
      | CurrentAidEligEndDate       | highDateETLver                 |
      | PregnancyEndDate            | lastDayOf4thMonthFromNowETLver |
    Given I create new file line by name "29121-1i" with data
      | MemberId                       | 29121-1d.MemberId          |
      | DateEffective                  | currentETLver              |
      | CurrentHealthCareProgram       | H                          |
      | CurrentAidEligEffectiveDate    | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate          | highDateETLver             |
      | MedicallyFrailConfirmationCode | Y                          |
    Given I create new file line by name "29121-1j" with data
      | MemberId                                  | 29121-1e.MemberId          |
      | CurrentHealthCareProgram                  | H                          |
      | CurrentAidEligEffectiveDate               | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate                     | highDateETLver             |
      | MedicallyFrailLastConfirmedAssessmentDate | pastETL                    |
    And I create "HRECIP" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 29121-1f |
      | 29121-1g |
      | 29121-1h |
      | 29121-1i |
      | 29121-1j |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate on last ETL HIP file in staging table in database
      | 29121-1f | MemberId                                  |
      | 29121-1g | PregnancyStartDate                        |
      | 29121-1h | PregnancyEndDate                          |
      | 29121-1i | MedicallyFrailConfirmationCode            |
      | 29121-1j | MedicallyFrailLastConfirmedAssessmentDate |

  @ETL-CP-29121 @ETL-29121-2.0 @ETL-CC-IN @IN-EB-ETL-Regression @muhabbat
  Scenario:[ETL] IN-EB HIP Only - Update Demographic Fields: Spouse Member ID, Pregnancy SDdate, Pregnancy EDate, MedFrail ConfCode, MedFrail last ConfAssesDate
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I create new file line by name "29121-2a" with data
      | CurrentHealthCareProgram    | A               |
      | CurrentAidEligEffectiveDate | yesterdayETLver |
      | CurrentAidEligEndDate       | highDateETLver  |
      | IllegalIndicator            | 1               |
    Given I create new file line by name "29121-2b" with data
      | CurrentHealthCareProgram    | A               |
      | CurrentAidEligEffectiveDate | yesterdayETLver |
      | CurrentAidEligEndDate       | highDateETLver  |
      | NativeAmerican              | N               |
    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 29121-2a |
      | 29121-2b |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Given I create new file line by name "29121-2c" with data
      | RecipientID                 | 29121-2a.RecipientID |
      | CurrentHealthCareProgram    | A                    |
      | CurrentAidEligEffectiveDate | yesterdayETLver      |
      | CurrentAidEligEndDate       | highDateETLver       |
      | IllegalIndicator            |[blank]|
    Given I create new file line by name "29121-2d" with data
      | RecipientID                 | 29121-2b.RecipientID |
      | CurrentHealthCareProgram    | A                    |
      | CurrentAidEligEffectiveDate | yesterdayETLver      |
      | CurrentAidEligEndDate       | highDateETLver       |
      | NativeAmerican              |[blank]|
    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 29121-2c |
      | 29121-2d |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate on last ETL RECIP file in staging table in database
      | 29121-2c | IllegalIndicator |
      | 29121-2d | NativeAmerican   |

  @ETL-CP-30046 @ETL-CP-30046-1.0 @ETL-CC-IN @IN-EB-ETL-Regression @chopa
  Scenario: [ETL-CC] IN-EB Verify File Source Field for HRECIP soure file
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I create new file line by name "30046-1a" with data
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    And I create "HRECIP" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 30046-1a |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate the File Source Field value for "30046-1a"
      | 30046-1a | HRECIP |

  @ETL-CP-30046 @ETL-CP-30046-2.0 @ETL-CC-IN @IN-EB-ETL-Regression @chopa
  Scenario: [ETL-CC] IN-EB Verify File Source Field for RECIPIENT soure file
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I create new file line by name "30046-2a" with data
      | RecipientID                 | 30046-2a.RecipientID       |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 30046-2a |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate the File Source Field value for "30046-2a"
      | 30046-2a | RECIPIENT |

  @ETL-CP-30046 @ETL-CP-30046-3.0 @ETL-CC-IN @IN-EB-ETL-Regression @chopa
  Scenario: [ETL-CC] IN-EB Verify File Source Field for RECIPIENT_HRECIP soure file
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I create new file line by name "30046-3a" with data
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 30046-3a |
    Given I create new file line by name "30046-4a" with data
      | memberID                    | 30046-3a.RecipientID  |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    And I create "HRECIP" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 30046-4a |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate the File Source Field value for "30046-3a"
      | 30046-3a | RECIPIENT_HRECIP |

  @ETL-CP-24620 @ETL-24620-1.0 @ETL-CC-IN @IN-EB-ETL-Regression @chopa
  Scenario:[ETL] IN-EB HHW & HCC Member Demographic File Mapping - C&C and Consumer Identification Info Validation- Current ( passing values as empty)
    Given I create new file line by name "24620-1a" with data
      | CaseNumber                  | null           |
      | CurrentHealthCareProgram    | A              |
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |
    And I create new file line by name "24620-1b" with data
      | RecipientID                              | null           |
      | CurrentHealthCareProgram                 | A              |
      | CurrentAidEligEffectiveDate              | currentETLver  |
      | CurrentAidEligEndDate                    | highDateETLver |
    And I create new file line by name "24620-1c" with data
      | CurrentHealthCareProgram    | A              |
      | CurrentAidEligEffectiveDate | null           |
      | CurrentAidEligEndDate       | highDateETLver |
    And I create new file line by name "24620-1d" with data
      | CurrentHealthCareProgram    | A              |
      | RecipLastName               | null           |
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |
    And I create new file line by name "24620-1e" with data
      | CurrentHealthCareProgram    | A              |
      | RecipSSN                    | null           |
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |
    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24620-1a |
      | 24620-1b |
      | 24620-1c |
      | 24620-1d |
      | 24620-1e |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate error messages on last ETL demographic staging table in database with data
      | 24620-1a | Case Number is missing or null                                  |
      | 24620-1b | Recipient ID is missing or null                                 |
      | 24620-1c | Eligibility Start Date is missing;Error in Curr Elig Start Date |
      | 24620-1d | Missing Last Name (cannot be NULL). Record is rejected.          |
      | 24620-1e | Missing SSN (cannot be NULL). Record is rejected.                |

  @ETL-CP-28106 @ETL-28106-1.0 @ETL-CC-IN @IN-EB-ETL-Regression @chopa
  Scenario:[ETL] IN-EB HHW & HCC and HIP Member Demographic File Mapping - C&C and Contact - Additions( passing values as empty)
    Given I create new file line by name "28106-1a" with data
      | CurrentHealthCareProgram    | A              |
      | RecipWardType               | null           |
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |
    Given I create new file line by name "28106-1b" with data
      | CurrentHealthCareProgram    | A              |
      | RecipCountyWard             | null           |
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |
    And I create "HRECIP" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 28106-1a |
      | 28106-1b |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate error messages on last ETL demographic staging table in database with data
      | 28106-1a | Ward Type is missing (cannot be NULL). Record is rejected.   |
      | 28106-1b | County Ward is missing (cannot be NULL). Record is rejected. |

  @ETL-CP-24618 @ETL-24618-1.0 @ETL-CC-IN @IN-EB-ETL-Regression @chopa
  Scenario:[ETL] IN-EB HIP Member Demographic File Mapping - C&C and Consumer Identification Info Validation- Current ( passing values as empty)
    Given I create new file line by name "24618-1a" with data
      | CaseNumber                  | null           |
      | CurrentHealthCareProgram    | A              |
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |
    Given I create new file line by name "24618-1b" with data
      | MemberId                    | null           |
      | CurrentHealthCareProgram    | A              |
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |
    And I create new file line by name "24618-1c" with data
      | CurrentHealthCareProgram    | A              |
      | MemberLastName              | null           |
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |
    And I create new file line by name "24618-1d" with data
      | CurrentHealthCareProgram    | A              |
      | MemberFirstName             | null           |
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |
    And I create new file line by name "24618-1e" with data
      | CurrentHealthCareProgram    | A              |
      | MemberBirthDate             | null           |
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |
    And I create new file line by name "24618-1f" with data
      | CurrentHealthCareProgram    | A              |
      | MemberSex                   | null           |
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |
    And I create new file line by name "24618-1g" with data
      | CurrentHealthCareProgram    | A              |
      | MemberAddrCity              | null           |
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |
    And I create new file line by name "24618-1h" with data
      | CurrentHealthCareProgram    | A              |
      | MemberAddrState             | null           |
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |
    And I create new file line by name "24618-1i" with data
      | CurrentHealthCareProgram    | A              |
      | MemberAddrZipCode           | null           |
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |
    And I create "HRECIP" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24618-1a |
      | 24618-1b |
      | 24618-1c |
      | 24618-1d |
      | 24618-1e |
      | 24618-1f |
      | 24618-1g |
      | 24618-1h |
      | 24618-1i |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate error messages on last ETL demographic staging table in database with data
      | 24618-1a | Case Number is missing or null                                     |
      | 24618-1b | Member ID is missing or null                                       |
      | 24618-1c | Missing Last Name (cannot be NULL). Record is rejected.            |
      | 24618-1d | Missing First Name (cannot be NULL). Record is rejected.           |
      | 24618-1e | Missing DOB (cannot be NULL). Record is rejected.                  |
      | 24618-1f | Recipient Sex is missing (cannot be NULL). Record is rejected.     |
      | 24618-1g | Missing Address City (cannot be NULL). Record is rejected.         |
      | 24618-1h | Missing Address State (cannot be NULL). Record is rejected.        |
      | 24618-1i | Missing Address Zip Code (cannot be NULL). Record is rejected.     |

  @ETL-CP-24620 @ETL-24620-2.0 @ETL-CC-IN @IN-EB-ETL-Regression @chopa
  Scenario:[ETL] IN-EB HHW & HCC Member Demographic File Mapping - C&C and Consumer Details Validation- Current ( passing values as empty)
    Given I create new file line by name "24620-2a" with data
      | MemberBirthDate             | null           |
      | CurrentHealthCareProgram    | A              |
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |
    And I create new file line by name "24620-2b" with data
      | MemberSex                                | null           |
      | CurrentHealthCareProgram                 | A              |
      | CurrentAidEligEffectiveDate              | currentETLver  |
      | CurrentAidEligEndDate                    | highDateETLver |
    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24620-2a |
      | 24620-2b |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate error messages on last ETL demographic staging table in database with data
      | 24620-2a | Missing DOB (cannot be NULL). Record is rejected.            |
      | 24620-2b | Missing Recipient Sex (cannot be NULL). Record is rejected.  |

  @ETL-CP-24620 @ETL-24620-3.0 @ETL-CC-IN @IN-EB-ETL-Regression @chopa
  Scenario:[ETL] IN-EB HHW & HCC Member Demographic File Mapping - C&C and Consumer Info - Address Validation- Current ( passing values as empty)
    Given I create new file line by name "24620-3a" with data
      | StreetAddress1              | null           |
      | CurrentHealthCareProgram    | A              |
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |
    And I create new file line by name "24620-3b" with data
      | MemberCounty                             | null           |
      | CurrentHealthCareProgram                 | A              |
      | CurrentAidEligEffectiveDate              | currentETLver  |
      | CurrentAidEligEndDate                    | highDateETLver |
    And I create new file line by name "24620-3c" with data
      | RecipAddrCity                            | null           |
      | CurrentHealthCareProgram                 | A              |
      | CurrentAidEligEffectiveDate              | currentETLver  |
      | CurrentAidEligEndDate                    | highDateETLver |
    And I create new file line by name "24620-3d" with data
      | RecipAddrState                           | null           |
      | CurrentHealthCareProgram                 | A              |
      | CurrentAidEligEffectiveDate              | currentETLver  |
      | CurrentAidEligEndDate                    | highDateETLver |
    And I create new file line by name "24620-3e" with data
      | RecipAddrZipCode                         | null           |
      | CurrentHealthCareProgram                 | A              |
      | CurrentAidEligEffectiveDate              | currentETLver  |
      | CurrentAidEligEndDate                    | highDateETLver |
    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24620-3a |
      | 24620-3b |
      | 24620-3c |
      | 24620-3d |
      | 24620-3e |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate error messages on last ETL demographic staging table in database with data
      | 24620-3a | STREET_ADDRESS_1:empty or null                                       |
      | 24620-3b | Missing Address County (cannot be NULL). Record is rejected.         |
      | 24620-3c | RECIP_ADDR_CITY:empty or null                                        |
      | 24620-3d | RECIP_ADDR_STATE:empty or null                                       |
      | 24620-3e | RECIP_ADDR_ZIP_CODE:empty or null                                    |