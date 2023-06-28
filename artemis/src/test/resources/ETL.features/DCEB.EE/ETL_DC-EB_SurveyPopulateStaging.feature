Feature: MCO file Outbound - Populate Staging Table

  @ETL-CP-38246 @ETL-EE-DC @DC-EB-ETL-Regression @turcan
  Scenario: Verify survey response submission
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo     | 38246-01 |
      | age                  | 37       |
      | consumerProfile.type | MEDICAID |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 38246-01.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | coverageCode                 | 231                 |
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | past                |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | highDate-DC         |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "38246-01a" for update Enrollment information
      | [0].consumerId         | 38246-01.consumerId |
      | [0].planId             | 55                  |
      | [0].planCode           | 044733300           |
      | [0].startDate          | fdofmnth::          |
      | [0].endDate            | highdatedc::        |
      | [0].programTypeCode    | MEDICAID            |
      | [0].subProgramTypeCode | DCHF                |
      | [0].serviceRegionCode  | Northeast           |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "38246-01a" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | fdofmnth::             |
      | [0].planId       | delete::               |
      | [0].planCode     | 044733300              |
      | [0].consumerId   | 38246-01.consumerId    |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 38246-01.consumerId  |
      | isEligibilityRequired        | no                   |
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 15                   |
      | enrollmentRecordId           | 15                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate-DC          |
      | anniversaryDate              | anniversaryDate      |
      | eligibilityStartDate         |[blank]|
      | programCode                  | MEDICAID             |
      | planCode                     | 044733300            |
      | planId                       | 53                   |
      | subProgramTypeCode           | DCHF                 |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    When I initiate survey response submission API
    And I prepare survey response for submission
      | referenceType          | Consumer                                                                           |
      | referenceId            | 38246-01.consumerId                                                                |
      | languageCode           | EN                                                                                 |
      | surveyDate             | yesterday                                                                          |
      | surveyResponse         | yes                                                                                |
      | numberOfQuestions      | 2                                                                                  |
      | 1.surveyDetailsId      | 503                                                                                |
      | 1.questionNumber       | 2                                                                                  |
      | 1.questionText         | If “Yes” tell us about the Appointment Date                                        |
      | 1.parentSurveyDetailId | 100                                                                                |
      | 2.surveyDetailsId      | 504                                                                                |
      | 2.questionNumber       | 2.a                                                                                |
      | 2.questionText         | Do you or a family member take any medicine that have been prescribed by a doctor? |
      | 2.parentSurveyDetailId | 100                                                                                |
      | status                 | SUBMITTED                                                                          |
      | createdOn              | current                                                                            |
      | createdBy              | auto                                                                               |
    When I run survey response submission
    And Wait for 3 seconds
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_00_outbound_MCO_survey"
    Then I validate survey status update on staging table
