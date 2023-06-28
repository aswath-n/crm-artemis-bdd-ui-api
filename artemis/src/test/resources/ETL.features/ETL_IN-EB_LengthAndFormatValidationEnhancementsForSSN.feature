Feature: ETL IN-EB Length and Format Validation Enhancements fo SSN

  @ETL-CP-31972 @ETL-CP-31972-1 @ETL-EE-IN @IN-EB-ETL-Regression @elshan
  Scenario: 1.0 Social Security Number Validation - Rules (Valid SSN)
    Given I create new file line by name "31972-1" with data
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |
    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 31972-1 |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate if the valid SSN passed to Case & Consumer

  @ETL-CP-31972 @ETL-CP-31972-2 @ETL-EE-IN @IN-EB-ETL-Regression @elshan
  Scenario: 2.0 Social Security Number is NULL
    Given I create new file line by name "31972-2" with data
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |
      | RecipSSN                    | null           |
    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 31972-2 |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate if ETL passed NULL SSN to Case & Consumer and saved the record

  @ETL-CP-31972 @ETL-CP-31972-3 @ETL-EE-IN @IN-EB-ETL-Regression @elshan
  Scenario: 2.1 Social Security Number is invalid
    Given I create new file line by name "31972-3" with data
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |
      | RecipSSN                    | 135-22388      |
    And I create new file line by name "31972-4" with data
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |
      | RecipSSN                    | 12382238       |
    And I create new file line by name "31972-5" with data
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |
      | RecipSSN                    | 000146688      |
    And I create new file line by name "31972-6" with data
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |
      | RecipSSN                    | 34614668A      |
    And I create new file line by name "31972-7" with data
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |
      | RecipSSN                    | #00833388      |
    And I create new file line by name "31972-8" with data
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |
      | RecipSSN                    | 34682835#      |
    And I create new file line by name "31972-9" with data
      | CurrentAidEligEffectiveDate | currentETLver  |
      | CurrentAidEligEndDate       | highDateETLver |
      | RecipSSN                    | #123 8608      |
    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 31972-3 |
      | 31972-4 |
      | 31972-5 |
      | 31972-6 |
      | 31972-7 |
      | 31972-8 |
      | 31972-9 |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    Then I validate ETL error or warning message for invalid SSN
      | 31972-3 | SSN:empty or null or doesn't meet length=9 |
      | 31972-4 | SSN:empty or null or doesn't meet length=9 |
      | 31972-5 | SSN:empty or null or doesn't meet length=9 |
      | 31972-7 | SSN:empty or null or doesn't meet length=9 |
      | 31972-8 | SSN:empty or null or doesn't meet length=9 |
      | 31972-9 | SSN:empty or null or doesn't meet length=9 |





