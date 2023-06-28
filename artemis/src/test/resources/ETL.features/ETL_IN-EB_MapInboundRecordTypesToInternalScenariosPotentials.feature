Feature: IN-EB Map Inbound Record Types to Internal Scenarios (Potentials, 21)

  @ETL-CP-24696 @ETL-CP-24696-2.0.1 @ETL-EE-IN @IN-EB-ETL-Regression @turcan
  Scenario: Map Inbound Records to Internal Scenario Code (Only POTENTIAL record received -Case:100 Eligibility:21)
    Given I create new file line by name "24696-1a" with data
      | DteAdded        | currentETLver |
      | CdeReason       | C             |
      | CdeHealthSubpgm | A             |

    And I create new file line by name "24696-1b" with data
      | DteAdded        | currentETLver |
      | CdeReason       | C             |
      | CdeHealthSubpgm | A             |

    And I create new file line by name "24696-1c" with data
      | DteAdded        | currentETLver |
      | CdeReason       | C             |
      | CdeHealthSubpgm | A             |

    And I create "POTENTIAL" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24696-1a |
      | 24696-1b |
      | 24696-1c |

    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate eligibility record id's in database with data
      | 24696-1a | 21 |
      | 24696-1b | 21 |
      | 24696-1c | 21 |
    And I validate case level record id's in database with data
      | 24696-1a | 100 |
      | 24696-1b | 100 |
      | 24696-1c | 100 |

  @ETL-CP-24696 @ETL-CP-24696-2.0.2 @ETL-EE-IN @IN-EB-ETL-Regression @turcan
  Scenario:Map Inbound Records to Internal Scenario Code (POTENTIAL and RECIPIENT records received- Case:NULL Eligibility:21)
    Given I create new file line by name "24696-2a" with data
      | CurrentHealthCareProgram    | R                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    Given I create new file line by name "24696-2b" with data
      | CurrentHealthCareProgram    | A                          |
      | CurrentAidEligEffectiveDate | 1stDayofPresentMonthETLver |
      | CurrentAidEligEndDate       | highDateETLver             |
    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24696-2a |
      | 24696-2b |
    Given I create new file line by name "24696-3a" with data
      | IdMedicaid      | 24696-2a.RecipientID      |
      | DteAdded        | firstdayofNextMonthETLver |
      | CdeReason       | C                         |
      | CdeHealthSubpgm | A                         |

    And I create new file line by name "24696-3b" with data
      | IdMedicaid      | 24696-2a.RecipientID |
      | DteAdded        | currentETLver        |
      | CdeReason       | C                    |
      | CdeHealthSubpgm | R                    |

    And I create new file line by name "24696-3c" with data
      | IdMedicaid      | 24696-2b.RecipientID |
      | DteAdded        | currentETLver        |
      | CdeReason       | C                    |
      | CdeHealthSubpgm | R                    |

    And I create new file line by name "24696-3d" with data
      | IdMedicaid      | 24696-2b.RecipientID      |
      | DteAdded        | firstdayofNextMonthETLver |
      | CdeReason       | C                         |
      | CdeHealthSubpgm | A                         |

    And I create "POTENTIAL" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24696-3a |
      | 24696-3b |
      | 24696-3c |
      | 24696-3d |

    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate eligibility record id's in database with data
      | 24696-3a | 21 |
      | 24696-3b | 21 |
    And I validate case level record id's in database with data
      | 24696-3a | null |
      | 24696-3b | null |

