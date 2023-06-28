Feature: Demographic File Mapping- Eligibility

  @ETL-CP-23509 @ETL-23509-2.0 @ETL-EE-IN @IN-EB-ETL-Regression @turcan
  Scenario:[ETL] IN-EB HHW & HCC Member Demographic File Mapping - Eligibility-Demographic Field Validation- Current ( passing values as empty)
    Given I create new file line by name "23509-1a" with data
      | CurrentHealthCareProgram    | null           |
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |

    And I create new file line by name "23509-1b" with data
      | CurrentManagedCareStatusInCurrentProgram | null           |
      | CurrentAidEligEffectiveDate              | currentETLver  |
      | CurrentAidEligEndDate                    | highDateETLver |
    And I create new file line by name "23509-2a" with data
      | CurrentAidEligEffectiveDate | null           |
      | CurrentAidEligEndDate       | highDateETLver |

    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 23509-1a |
      | 23509-1b |
      | 23509-2a |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate error messages on last ETL demographic staging table in database with data
      | 23509-1a | Program Code is missing or not available                        |
      | 23509-1b | Eligibility Status Code is missing or null                      |
      | 23509-2a | Eligibility Start Date is missing;Error in Curr Elig Start Date |


  @ETL-CP-23509 @ETL-23509-2.0 @ETL-EE-IN @IN-EB-ETL-Regression @turcan
  Scenario:[ETL] IN-EB HHW & HCC Member Demographic File Mapping - Eligibility-Demographic Field Validation-Current ( passing invalid format)
    Given I create new file line by name "23509-1c" with data
      | CurrentHealthCareProgram    | T              |
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |

    And I create new file line by name "23509-1d" with data
      | CurrentManagedCareStatusInCurrentProgram | D              |
      | CurrentAidEligEffectiveDate              | currentETLver  |
      | CurrentAidEligEndDate                    | highDateETLver |

    And I create new file line by name "23509-2c" with data
      | CurrentRecipAidCategory     | RN             |
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |

    And I create new file line by name "23509-2d" with data
      | CurrentAidEligEffectiveDate | InvalidStartDateETLver |
      | CurrentAidEligEndDate       | highDateETLver         |

    And I create new file line by name "23509-2e" with data
      | CurrentAidEligEffectiveDate | currentETLver        |
      | CurrentAidEligEndDate       | InvalidEndDateETLver |

    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 23509-1c |
      | 23509-1d |
      | 23509-2c |
      | 23509-2d |
      | 23509-2e |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate error messages on last ETL demographic staging table in database with data
      | 23509-1c | Program Code is missing in ENUM                      |
      | 23509-1d | Eligibility Status Code not in ENUM                  |
      | 23509-2c | Category Code not in ENUM                            |
      | 23509-2d | Error in Curr Elig Start Date Invalid Date or Format |
      | 23509-2e | Error in Curr Elig End Date Invalid Date or Format   |


  @ETL-CP-23509 @ETL-23509-2.0 @ETL-23509-2.1.2 @ETL-EE-IN @IN-EB-ETL-Regression @turcan
  Scenario:[ETL] IN-EB HHW & HCC Member Demographic File Mapping - Eligibility-Demographic Field Validation (Current and Future eligibility)-Validation - Future information from Demographic file
    Given I create new file line by name "23509-3a" with data
      | CurrentHealthCareProgram                 | R                         |
      | CurrentManagedCareStatusInCurrentProgram | M                         |
      | CurrentRecipAidCategory                  | SI                        |
      | CurrentAidEligEffectiveDate              | currentETLver             |
      | CurrentAidEligEndDate                    | null                      |
      | FutureHealthCareProgram                  | A                         |
      | FutureManagedCareStatusInCurrentProgram  | M                         |
      | FutureRecipAidCategory                   | SI                        |
      | FutureAidEligEffectiveDate               | firstdayofNextMonthETLver |
      | FutureAidEligEndDate                     | highDateETLver            |

    And I create new file line by name "23509-3b" with data
      | CurrentHealthCareProgram                 | A                           |
      | CurrentManagedCareStatusInCurrentProgram | M                           |
      | CurrentRecipAidCategory                  | SI                          |
      | CurrentAidEligEffectiveDate              | currentETLver               |
      | CurrentAidEligEndDate                    | lastDayofPresentMonthETLver |
      | FutureHealthCareProgram                  | R                           |
      | FutureManagedCareStatusInCurrentProgram  | M                           |
      | FutureRecipAidCategory                   | SI                          |
      | FutureAidEligEffectiveDate               | firstdayofNextMonthETLver   |
      | FutureAidEligEndDate                     | null                        |

    And I create new file line by name "23509-3c" with data
      | CurrentHealthCareProgram                 | R                             |
      | CurrentManagedCareStatusInCurrentProgram | M                             |
      | CurrentRecipAidCategory                  | SI                            |
      | CurrentAidEligEffectiveDate              | currentETLver                 |
      | CurrentAidEligEndDate                    | lastDayOfThePresentYearETLver |
      | FutureHealthCareProgram                  | R                             |
      | FutureManagedCareStatusInCurrentProgram  | M                             |
      | FutureRecipAidCategory                   | SI                            |
      | FutureAidEligEffectiveDate               | null                          |
      | FutureAidEligEndDate                     | highDateETLver                |
    And I create new file line by name "23509-3d" with data
      | CurrentHealthCareProgram                 | R                             |
      | CurrentManagedCareStatusInCurrentProgram | M                             |
      | CurrentRecipAidCategory                  | SI                            |
      | CurrentAidEligEffectiveDate              | null                          |
      | CurrentAidEligEndDate                    | lastDayOfThePresentYearETLver |
      | FutureHealthCareProgram                  | R                             |
      | FutureManagedCareStatusInCurrentProgram  | M                             |
      | FutureRecipAidCategory                   | SI                            |
      | FutureAidEligEffectiveDate               | null                          |
      | FutureAidEligEndDate                     | highDateETLver                |

    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 23509-3a |
      | 23509-3b |
      | 23509-3c |
      | 23509-3d |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate error messages on last ETL demographic staging table in database with data
      | 23509-3a |[blank]|
      | 23509-3b |[blank]|
      | 23509-3c |[blank]|
      | 23509-3d | Eligibility Start Date is missing;Error in Curr Elig Start Date |


  @ETL-CP-23509 @ETL-23509-2.1.1 @ETL-EE-IN @IN-EB-ETL-Regression @turcan
  Scenario:[ETL] IN-EB HHW & HCC Member Demographic File Mapping - Eligibility-Mapping for Program Type code and Sub Program Type Code
    Given I create new file line by name "23509-4a" with data
      | CurrentHealthCareProgram    | R              |
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |


    And I create new file line by name "23509-4b" with data
      | CurrentHealthCareProgram    | A              |
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |


    And I create new file line by name "23509-4c" with data
      | CurrentHealthCareProgram    | H              |
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |


    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 23509-4a |
      | 23509-4b |
      | 23509-4c |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate subprogram type code mapping in payload

  @ETL-CP-23509 @ETL-23509-2.1.3 @ETL-23509-2.1.4 @ETL-EE-IN @IN-EB-ETL-Regression @turcan
  Scenario:[ETL] IN-EB HHW & HCC Member Demographic File Mapping - Eligibility-Demographic Field Validation-Setting the Object in request-Record Id
    Given I create new file line by name "23509-5a" with data
      | CurrentHealthCareProgram                 | R                           |
      | CurrentManagedCareStatusInCurrentProgram | M                           |
      | CurrentRecipAidCategory                  | SI                          |
      | CurrentAidEligEffectiveDate              | currentETLver               |
      | CurrentAidEligEndDate                    | lastDayofPresentMonthETLver |
      | FutureHealthCareProgram                  | A                           |
      | FutureManagedCareStatusInCurrentProgram  | M                           |
      | FutureRecipAidCategory                   | SI                          |
      | FutureAidEligEffectiveDate               | firstdayofNextMonthETLver   |
      | FutureAidEligEndDate                     | highDateETLver              |


    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 23509-5a |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate core eligibility existance in payload and record id value







