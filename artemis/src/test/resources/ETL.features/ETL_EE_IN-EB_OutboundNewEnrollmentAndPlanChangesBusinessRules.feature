Feature:IN-EB: Outbound New Enrollment and Plan Changes (HIP, HHW, HCC) Business Rules

  @ETL-CP-24135 @ETL-24135-1.0 @ETL-EE-IN @IN-EB-ETL-Regression @turcan
  Scenario: Create OUTBOUND file for HIP PLAN CHANGES Business Rules
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I create new file line by name "24135-1a" with data
      | CurrentHealthCareProgram    | H               |
      | CurrentAidEligEffectiveDate | yesterdayETLver |
      | CurrentAidEligEndDate       | highDateETLver  |
    Given I create new file line by name "24135-1b" with data
      | CurrentHealthCareProgram    | H               |
      | CurrentAidEligEffectiveDate | yesterdayETLver |
      | CurrentAidEligEndDate       | highDateETLver  |
    And I create "HRECIP" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24135-1a |
      | 24135-1b |
    Given I create new file line by name "24135-1c" with data
      | IdMedicaid       | 24135-1a.MemberId |
      | DateEffective    | yesterdayETLver   |
      | DateEnd          | highDateETLver    |
      | CodeHealthSubpgm | H                 |
      | TransactionType  | A                 |
      | MCEId            | 455701400         |
      | CodeReasonStart  | HT                |
    Given I create new file line by name "24135-1d" with data
      | IdMedicaid       | 24135-1b.MemberId |
      | DateEffective    | yesterdayETLver   |
      | DateEnd          | highDateETLver    |
      | CodeHealthSubpgm | H                 |
      | TransactionType  | A                 |
      | MCEId            | 455701400         |
      | CodeReasonStart  | HT                |
    And I create "HASSIGN" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24135-1c |
      | 24135-1d |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    And I get consumerId by externalId
      | 24135-1a |
      | 24135-1b |
    Then I receive ETL memberId for "24135-1a" and perform plan transfer via API to new plan with data
      | [0].planId             | 199                  |
      | [0].planCode           | 355787430            |
      | [0].startDate          | firstDayOfNextYear:: |
      | [0].endDate            | highDate::           |
      | [0].programTypeCode    | MEDICAID             |
      | [0].subProgramTypeCode | HealthyIndianaPlan   |
      | [0].serviceRegionCode  | Statewide            |
      | [0].channel            | SYSTEM_INTEGRATION   |
      | [0].selectionReason    | 02                   |
      | [0].anniversaryDate    | anniversaryDate::    |
    When I run talent job for environment "QE" project "INEB" job name "selectionExtractJob"
    And I save enrollmentId of consumer "24135-1a"
    Then I validate record content that enrollment status is as expected with data for consumer "24135-1a"
      | 24135-1a | SELECTION_MADE |
    Then I validate enrollment status is as expected with data in enrollment table for consumer  "24135-1a"
      | 24135-1a | SUBMITTED_TO_STATE |
    Given I create new file line by name "24135-1e" with data
      | IdMedicaid             | 24135-1a.MemberId          |
      | DateEffective          | 1stDayofPresentMonthETLver |
      | DateEnd                | highDateETLver             |
      | CodeHealthSubpgm       | H                          |
      | TransactionType        | D                          |
      | MCEId                  | 455701400                  |
      | TransactionDisposition | A                          |
      | CodeReasonStart        | HT                         |
    Given I create new file line by name "24135-1f" with data
      | EnrollmentIdTransactionId | 24135-1a                 |
      | IdMedicaid                | 24135-1a.MemberId        |
      | DateEffective             | firstDayOfNextYearETLver |
      | DateEnd                   | highDateETLver           |
      | CodeHealthSubpgm          | H                        |
      | TransactionType           | A                        |
      | MCEId                     | 355787430                |
      | TransactionDisposition    | A                        |
      | CodeReasonStart           | FE                       |
    And I save enrollmentId of consumer "24135-1b"
    Given I create new file line by name "24135-1g" with data
      | EnrollmentIdTransactionId | 24135-1b          |
      | IdMedicaid                | 24135-1b.MemberId |
      | DateEffective             | currentETLver     |
      | DateEnd                   | highDateETLver    |
      | CodeHealthSubpgm          | H                 |
      | TransactionType           | D                 |
      | MCEId                     | 455701400         |
      | TransactionDisposition    | A                 |
      | CodeReasonStart           | HT                |
    And I create "HRESPONSE" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24135-1e |
      | 24135-1f |
      | 24135-1g |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleResponse"
    Then I validate enrollment status is as expected with data in enrollment table for consumer  "24135-1a"
      | 24135-1a | ACCEPTED |

  @ETL-CP-24135 @ETL-24135-2.0 @ETL-24135-3.0 @ETL-EE-IN @IN-EB-ETL-Regression @turcan
  Scenario: Create OUTBOUND file for HHW NEW ENROLLMENT and HHW PLAN CHANGES Business Rules
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I create new file line by name "24135-2a" with data
      | CurrentHealthCareProgram    | A               |
      | CurrentAidEligEffectiveDate | yesterdayETLver |
      | CurrentAidEligEndDate       | highDateETLver  |
    And I create "RECIPIENT" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24135-2a |
    Given I create new file line by name "24135-2b" with data
      | IdMedicaid       | 24135-2a.RecipientID |
      | DateEffective    | currentETLver        |
      | DateEnd          | highDateETLver       |
      | CodeHealthSubpgm | A                    |
      | TransactionType  | A                    |
      | MCEId            | 400752220            |
      | CodeReasonStart  | HT                   |
    And I create "ASSIGN" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24135-2b |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleJob"
    And I get consumerId by externalId for recipient file
      | 24135-2a |
    Then I receive ETL memberId for "24135-2a" and perform plan transfer via API to new plan with data
      | [0].planId             | 2074                 |
      | [0].planCode           | 500307680            |
      | [0].startDate          | firstDayOfNextYear:: |
      | [0].endDate            | highDate::           |
      | [0].programTypeCode    | MEDICAID             |
      | [0].subProgramTypeCode | HoosierHealthwise    |
      | [0].serviceRegionCode  | Statewide            |
      | [0].channel            | SYSTEM_INTEGRATION   |
      | [0].selectionReason    | 02                   |
      | [0].anniversaryDate    | anniversaryDate::    |
    When I run talent job for environment "QE" project "INEB" job name "selectionExtractJob"
    And I save enrollmentId of consumer "24135-2a"
    Then I validate record content that enrollment status is as expected with data for consumer "24135-2a"
      | 24135-2a | SELECTION_MADE |
    Then I validate enrollment status is as expected with data in enrollment table for consumer  "24135-2a"
      | 24135-2a | SUBMITTED_TO_STATE |
    Given I create new file line by name "24135-2c" with data
      | EnrollmentIdTransactionId | 24135-2a                 |
      | IdMedicaid                | 24135-2a.RecipientID     |
      | DateEffective             | firstDayOfNextYearETLver |
      | DateEnd                   | highDateETLver           |
      | CodeHealthSubpgm          | A                        |
      | TransactionType           | A                        |
      | MCEId                     | 500307680                |
      | TransactionDisposition    | A                        |
      | CodeReasonStart           | 94                       |
    And I create "RESPONSE" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/DailyEligible/in/" with names list
      | 24135-2c |
    When I run talent job for environment "QE" project "INEB" job name "dailyEligibleResponse"
    Then I validate enrollment status is as expected with data in enrollment table for consumer  "24135-2a"
      | 24135-2a | ACCEPTED |

