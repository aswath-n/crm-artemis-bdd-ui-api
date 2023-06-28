Feature: Caseloader -MMIS Sends Demographic Record

  @API-CP-25775 @API-CP-25775-01 @API-CP-24238 @API-EE-IN @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti @oe @events-cc @fixevent
  Scenario: Verify Consumer is created without case when Record ID 101 is passed
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with enrollment and eligibility information
      | recordId              | 1                     |
      | isEligibilityRequired | yes                   |
      | eligibilityStatusCode | V                     |
      | isEnrollmentRequired  | no                    |
      | enrollmentStartDate   | firstDayofPresntMonth |
      | case.recordId         | 101                   |
#    Then I will publish an "CONSUMER_SAVE_EVENT" event for DPBI to consume for reporting
    Given I initiated Case Consumer Search API for Consumer Match
    When I run Consumer Search API using "consumerSSN"
    Then I verify consumer details  without an External Case ID

  @API-CP-23890 @API-CP-23890-01  @API-EE-IN @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti @events-cc @events-bs-cc @fixevent
  Scenario: Verify Consumer is updated when match is found for SSN
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with enrollment and eligibility information
      | recordId              | 1                     |
      | isEligibilityRequired | yes                   |
      | eligibilityStatusCode | V                     |
      | isEnrollmentRequired  | no                    |
      | enrollmentStartDate   | firstDayofPresntMonth |
      | saveConsumerInfo      | 23890-1               |
#    Then I will publish an "CONSUMER_SAVE_EVENT" event for DPBI to consume for reporting
    Given I initiated Case Consumer Search API for Consumer Match
    When I run Consumer Search API using "consumerSSN"
    Then I verify consumer details  without an External Case ID
    When I Match Demographic Record to Consumer in ConnectionPoint
      | consumerSSN        | random |
      | externalConsumerId |[blank]|
    Then I will verify business events are generated with data
      | eventName     | CONSUMER_SAVE_EVENT   |
      | consumerId    | 23890-1.consumerId    |
      | correlationId | 23890-1.correlationId |
      | consumerFound | true                  |
      | DPBI          |[blank]|

  @API-CP-23890 @API-CP-23890-02 @API-EE-IN @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti @events-cc @events-bs-cc @fixevent
  Scenario: Verify New Consumer is created when match is found for first name,last name and DOB and SSN & External consumer ID are not matched
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with enrollment and eligibility information
      | recordId              | 1                     |
      | isEligibilityRequired | yes                   |
      | eligibilityStatusCode | V                     |
      | isEnrollmentRequired  | no                    |
      | enrollmentStartDate   | firstDayofPresntMonth |
      | saveConsumerInfo      | 23890-2               |
#    Then I will publish an "CONSUMER_SAVE_EVENT" event for DPBI to consume for reporting
    Given I initiated Case Consumer Search API for Consumer Match
    When I run Consumer Search API using "consumerSSN"
    Then I verify consumer details  without an External Case ID
    When I Match Demographic Record to Consumer in ConnectionPoint
      | consumerSSN        | random |
      | externalConsumerId | random |
    Then I will verify business events are generated with data
      | eventName     | CONSUMER_SAVE_EVENT   |
      | consumerId    | 23890-2.consumerId    |
      | correlationId | 23890-2.correlationId |
      | consumerFound | true                  |
      | DPBI          |[blank]|
# Functionality changed  refer CP-32874
  @API-CP-23890 @API-CP-23890-03  @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti @events-cc @events-bs-cc
  Scenario: Verify Error message is displayed if more than 1 match found for SSN
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with enrollment and eligibility information
      | recordId              | 1                     |
      | isEligibilityRequired | yes                   |
      | eligibilityStatusCode | V                     |
      | isEnrollmentRequired  | no                    |
      | enrollmentStartDate   | firstDayofPresntMonth |
      | saveConsumerInfo      | 23890-4               |
    Then I will verify business events are generated with data
      | eventName     | CONSUMER_SAVE_EVENT   |
      | consumerId    | 23890-4.consumerId    |
      | correlationId | 23890-4.correlationId |
      | consumerFound | true                  |
      | DPBI          |[blank]|
    Given I initiated Case Consumer Search API for Consumer Match
    When I run Consumer Search API using "consumerSSN"
    Then I verify consumer details  without an External Case ID
    When I Match Demographic Record to Consumer in ConnectionPoint
      | consumerSSN        | 360005198 |
      | externalConsumerId | random    |
    Then I verify case loader error message for multiple ssn matching "Duplicate SSN with different Medicaid IDs"
# Functionality changed refer CP-32874
  @API-CP-23890 @API-CP-23890-03-02 @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti @events-cc
  Scenario: Verify Error message is displayed if more than 1 match found for SSN- error2
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with enrollment and eligibility information
      | recordId              | 1                     |
      | isEligibilityRequired | yes                   |
      | eligibilityStatusCode | V                     |
      | isEnrollmentRequired  | no                    |
      | enrollmentStartDate   | firstDayofPresntMonth |
#    Then I will publish an "CONSUMER_SAVE_EVENT" event for DPBI to consume for reporting
    Given I initiated Case Consumer Search API for Consumer Match
    When I run Consumer Search API using "consumerSSN"
    Then I verify consumer details  without an External Case ID
    When I Match Demographic Record to Consumer in ConnectionPoint
      | consumerSSN        | 836794679 |
      | externalConsumerId |[blank]|
    Then I verify case loader error message for multiple ssn matching "Consumer not matched when additional record(s) found for SSN with different Medicaid ID(s)"
# Functionality changed refer CP-32874
  @API-CP-23890 @API-CP-23890-05 @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti @events-cc
  Scenario: Verify Error message is displayed if more than 1 match found for SSN-2
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with enrollment and eligibility information
      | recordId              | 1                     |
      | isEligibilityRequired | yes                   |
      | eligibilityStatusCode | V                     |
      | isEnrollmentRequired  | no                    |
      | enrollmentStartDate   | firstDayofPresntMonth |
#    Then I will publish an "CONSUMER_SAVE_EVENT" event for DPBI to consume for reporting
    When I Match Demographic Record to Consumer in ConnectionPoint
      | consumerSSN        | 529853310 |
      | externalConsumerId | x565142   |
    Then I verify case loader error message for multiple ssn matching "Consumer matched when additional record(s) found for SSN with different Medicaid ID(s)"

  @API-CP-23890 @API-CP-23890-04 @API-EE-IN @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti @events-cc @events-bs-cc
  Scenario: Verify Consumer details are updated when a match is found on SSN/MEDICAID ID
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with enrollment and eligibility information
      | recordId              | 1                     |
      | isEligibilityRequired | yes                   |
      | eligibilityStatusCode | V                     |
      | isEnrollmentRequired  | no                    |
      | enrollmentStartDate   | firstDayofPresntMonth |
      | case.recordId         | 101                   |
      | saveConsumerInfo      | 23890-3               |
    Then I will verify business events are generated with data
      | eventName     | CONSUMER_SAVE_EVENT   |
      | consumerId    | 23890-3.consumerId    |
      | correlationId | 23890-3.correlationId |
      | consumerFound | true                  |
      | DPBI          |[blank]|
    Given I initiated Case Consumer Search API for Consumer Match
    When I run Consumer Search API using "consumerSSN"
    Then I verify consumer details  without an External Case ID
    When I Match Demographic Record to Consumer in ConnectionPoint
      | consumerSSN        | random |
      | externalConsumerId |[blank]|
      | consumerFirstName  | random |
      | consumerLastName   | random |
    Then I will verify business events are generated with data
      | eventName     | CONSUMER_UPDATE_EVENT |
      | consumerId    | 23890-3.consumerId    |
      | correlationId | 23890-3.correlationId |
      | consumerFound | true                  |
      | DPBI          |[blank]|

  @API-CP-23891 @API-CP-23891-01 @API-EE-IN @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti @events-cc @events-bs-cc
  Scenario: Verify Case and Consumer is created for no match
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with enrollment and eligibility information
      | recordId              | 1                     |
      | isEligibilityRequired | no                    |
      | eligibilityStatusCode | V                     |
      | isEnrollmentRequired  | no                    |
      | enrollmentStartDate   | firstDayofPresntMonth |
      | saveConsumerInfo      | 23891-1               |
    Then I will verify business events are generated with data
      | eventName     | CONSUMER_SAVE_EVENT   |
      | consumerId    | 23891-1.consumerId    |
#      | correlationId | 23891-1.correlationId |
      | consumerFound | true                  |
      | DPBI          |[blank]|
    Then I will publish an "CASE_SAVE_EVENT" event for DPBI to consume for reporting

#Removing regression tag
  @API-CP-23892 @API-CP-23892-01 @API-EE-IN @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti @events-cc
  Scenario: Verify External Consumer ID is updated if the original value is NULL in CP
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with enrollment and eligibility information
      | recordId              | 1                     |
      | isEligibilityRequired | no                    |
      | eligibilityStatusCode | V                     |
      | isEnrollmentRequired  | no                    |
      | enrollmentStartDate   | firstDayofPresntMonth |
#    Then I will publish an "CONSUMER_SAVE_EVENT" event for DPBI to consume for reporting
    Given I initiated Case Consumer Search API for Consumer Match
    When I run Consumer Search API using "consumerSSN"
    Then I verify consumer details  without an External Case ID
    When I Match Demographic Record to Consumer in ConnectionPoint
      | consumerSSN        | 495114456 |
      | externalConsumerId | xeerr123  |
      | consumerFirstName  | Alex      |
      | consumerLastName   | Karev     |
    Then I will publish an "CONSUMER_UPDATE_EVENT" event for DPBI to consume for reporting
    Then I will verify External Consumer ID "xeerr123" is updated for the consumer with ssn "495114456"

  @API-CP-23892 @API-CP-23892-02 @API-EE-IN @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify old case is end dated and new cases is started for different caseIdentificationNumber
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with enrollment and eligibility information
      | recordId              | 1                     |
      | isEligibilityRequired | no                    |
      | eligibilityStatusCode | V                     |
      | isEnrollmentRequired  | no                    |
      | enrollmentStartDate   | firstDayofPresntMonth |
#    Then I will publish an "CONSUMER_SAVE_EVENT" event for DPBI to consume for reporting
    Given I initiated Case Consumer Search API for Consumer Match
    When I run Consumer Search API using "consumerSSN"
    Then I verify consumer details  without an External Case ID
    When I Match Demographic Record to Consumer in ConnectionPoint
      | consumerSSN              |[blank]|
      | externalConsumerId       |[blank]|
      | caseIdentificationNumber | random |
    And I verify effective end date is populated for old case


  @API-CP-23892 @API-CP-23892-03 @API-EE-IN @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti @events-cc @fixevent
  Scenario: Add consumer with no case to a case when the caseId is passed
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with enrollment and eligibility information
      | recordId              | 1                     |
      | isEligibilityRequired | no                    |
      | eligibilityStatusCode | V                     |
      | isEnrollmentRequired  | no                    |
      | enrollmentStartDate   | firstDayofPresntMonth |
      | case.recordId         | 101                   |
#    Then I will publish an "CONSUMER_SAVE_EVENT" event for DPBI to consume for reporting
    Given I initiated Case Consumer Search API for Consumer Match
    When I run Consumer Search API using "consumerSSN"
    Then I verify consumer details  without an External Case ID
    When I Match Demographic Record to Consumer in ConnectionPoint
      | consumerSSN              |[blank]|
      | externalConsumerId       |[blank]|
      | caseIdentificationNumber | random |
      | case.recordId            | null   |
    Then I verify new case id created when different CaseIdentificationNumber is passed


  @API-CP-25334 @API-CP-25334-01 @API-EE-IN @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti @oe @events-cc @fixevent
  Scenario: Verify Consumer is created with out of state indiciator additional field
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with enrollment and eligibility information
      | recordId              | 1                     |
      | isEligibilityRequired | yes                   |
      | eligibilityStatusCode | V                     |
      | isEnrollmentRequired  | no                    |
      | enrollmentStartDate   | firstDayofPresntMonth |
#    Then I will publish an "CONSUMER_SAVE_EVENT" event for DPBI to consume for reporting
    Given I initiated Case Consumer Search API for Consumer Match
    When I run Consumer Search API using "consumerSSN"
    Then I verify consumer details  without an External Case ID

  @API-CP-23619 @API-CP-23619-01 @API-EE-IN @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti @oe @events-cc
  Scenario: Verify Consumer is created with Project Field
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with enrollment and eligibility information
      | recordId                                  | 1                     |
      | isEligibilityRequired                     | yes                   |
      | eligibilityStatusCode                     | V                     |
      | isEnrollmentRequired                      | no                    |
      | enrollmentStartDate                       | firstDayofPresntMonth |
      | medicallyFrailConfirmationCode            | MCD23                 |
      | medicallyFrailLastConfirmedAssessmentDate | future                |
      | maritalCode                               | Single                |
      | recipientCountyWard                       | HOWARD3               |
      | recipientWardType                         | Test2                 |
      | pregnancyStartDate                        | 1stDayofPresentMonth  |
      | pregnancyEndDate                          | future                |
      | mergeReason                               |[blank]|
      | pmpPaper                                  | test2                 |
      | spouseExternalConsumerId                  | 226                   |
     #Then I will publish an "CONSUMER_SAVE_EVENT" event for DPBI to consume for reporting
    Given I initiated Case Consumer Search API for Consumer Match
    When I run Consumer Search API using "consumerSSN"
    Then I verify consumer project fields details


  @API-CP-27692 @API-CP-27692-01 @API-EE-IN @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti @events-cc @fixevent
  Scenario: Verify Consumer is updated when match is found for SSN-2 update project fields
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with enrollment and eligibility information
      | recordId              | 1                     |
      | isEligibilityRequired | yes                   |
      | eligibilityStatusCode | V                     |
      | isEnrollmentRequired  | no                    |
      | enrollmentStartDate   | firstDayofPresntMonth |
      | case.recordId         | 101                   |
#    Then I will publish an "CONSUMER_SAVE_EVENT" event for DPBI to consume for reporting
    Given I initiated Case Consumer Search API for Consumer Match
    When I run Consumer Search API using "consumerSSN"
    Then I verify consumer details  without an External Case ID
    When I Match Demographic Record to Consumer in ConnectionPoint
      | consumerSSN               |[blank]|
      | externalConsumerId        |[blank]|
      | consumerFirstName         | random               |
      | consumerLastName          | random               |
      | medicaidIdActiveIndicator | true                 |
      | pregnancyStartDate        | 1stDayofPresentMonth |
      | pregnancyEndDate          | future               |
    Given I initiated Case Consumer Search API for Consumer Match
    When I run Consumer Search API using "consumerSSN"
    Then I verify consumer project fields details

  @API-CP-27254 @API-CP-27254-01  @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Caseloader allows other Eligibility Segments
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with enrollment and eligibility information
      | recordId                               | 1                     |
      | isEligibilityRequired                  | yes                   |
      | eligibilityStatusCode                  | V                     |
      | isEnrollmentRequired                   | no                    |
      | enrollmentStartDate                    | firstDayofPresntMonth |
      | isOtherEligibilitySegmentsRequired     | Yes                   |
      | otherElgSegment[0].startDate           | 1stDayofLastMonth     |
      | otherElgSegment[0].endDate             | highDate              |
      | otherElgSegment[0].segmentDetailValue2 | NHN                   |
      | otherElgSegment[0].segmentTypeCode     | FACILITY              |
#      | otherElgSegment[1].startDate           | 1stDayofLastMonth     |
#      | otherElgSegment[1].endDate             | highDate              |
#      | otherElgSegment[1].segmentDetailValue2 | NHN                   |
#      | otherElgSegment[1].segmentTypeCode     | FACILITY              |
      | isOtherEnrollmentSegmentsRequired      | No                    |
      | otherEnrollSegment[0].startDate        | 1stDayofLastMonth     |
      | otherEnrollSegment[0].endDate          | highDate              |
      | otherEnrollSegment[0].segmentTypeCode  | LiL1                  |
      | otherEnrollSegment[1].startDate        | 1stDayofLastMonth     |
      | otherEnrollSegment[1].endDate          | highDate              |
      | otherEnrollSegment[1].segmentTypeCode  | LIL0                  |
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "" with data
      | [0].otherEligbilitySegmentsId | print in console        |
      | [0].consumerId                |[blank]|
      | [0].segmentTypeCode           | OTHER                   |
      | [0].startDate                 | 1stDayofPresentMonth    |
      | [0].endDate                   | lastDayOfThePresentYear |
      | [0].segmentDetailValue1       | 1                       |
      | [0].segmentDetailValue2       | 6                       |
      | [0].segmentDetailValue3       | 1stDayofPresentMonth    |
      | [0].segmentDetailValue4       | lastDayOfThePresentYear |
      | [0].segmentDetailValue5       | null                    |
      | [0].segmentDetailValue6       | null                    |
      | [0].segmentDetailValue7       | null                    |
      | [0].segmentDetailValue8       | null                    |
      | [0].segmentDetailValue9       | null                    |
      | [0].segmentDetailValue10      | null                    |
      | [0].createdOn                 | current                 |
      | [0].createdBy                 | 597                     |
      | [0].updatedOn                 | null                    |
      | [0].updatedBy                 | null                    |
      | [0].uiid                      | null                    |

  @API-CP-21257 @API-CP-21257-01  @API-EE-IN @IN-EB-API-Regression @shruti @events-cc @fixevent
  Scenario: Verify Caseloader Multiple Enhancements
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with enrollment and eligibility information
      | recordId              | 1                     |
      | isEligibilityRequired | yes                   |
      | eligibilityStatusCode | V                     |
      | isEnrollmentRequired  | no                    |
      | enrollmentStartDate   | firstDayofPresntMonth |
#    Then I will publish an "CONSUMER_SAVE_EVENT" event for DPBI to consume for reporting
    Given I initiated Case Consumer Search API for Consumer Match
    When I run Consumer Search API using "consumerSSN"
    Then I verify consumer details  without an External Case ID

  @API-CP-29651 @API-CP-29651-01  @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario: Verify Caseloader allows other Eligibility Segments and Enrollment without Enrollment Segment
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with enrollment and eligibility information
      | recordId                               | 21                    |
      | eligibilityRecordId                    | 21                    |
      | isEligibilityRequired                  | yes                   |
      | eligibilityStatusCode                  | V                     |
      | isEnrollmentRequired                   | no                    |
      | enrollmentStartDate                    | firstDayofPresntMonth |
      | isOtherEligibilitySegmentsRequired     | Yes                   |
      | otherElgSegment[0].startDate           | 1stDayofLastMonth     |
      | otherElgSegment[0].endDate             | highDate              |
      | otherElgSegment[0].segmentDetailValue2 | NHN                   |
      | otherElgSegment[0].updatedOn           | current               |
      | otherElgSegment[0].segmentTypeCode     | FACILITY              |
      | otherElgSegment[1].startDate           | 1stDayofLastMonth     |
      | otherElgSegment[1].endDate             | highDate              |
      | otherElgSegment[1].segmentDetailValue2 | NHN                   |
      | otherElgSegment[1].segmentTypeCode     | FACILITY              |
      | isOtherEnrollmentSegmentsRequired      | yes                   |
      | otherEnrollSegment[0].startDate        | 1stDayofLastMonth     |
      | otherEnrollSegment[0].endDate          | highDate              |
      | otherEnrollSegment[0].segmentTypeCode  | LiL1                  |
      | otherEnrollSegment[1].startDate        | 1stDayofLastMonth     |
      | otherEnrollSegment[1].endDate          | highDate              |
      | otherEnrollSegment[1].segmentTypeCode  | LIL0                  |
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "" with data
      | [0].otherEligbilitySegmentsId | print in console  |
      | [0].consumerId                | <name>.consumerId |
      | [0].segmentTypeCode           | FACILITY          |
      | [0].startDate                 | 1stDayofLastMonth |
      | [0].endDate                   | highDate          |
#      | [0].segmentDetailValue1       |[blank]|
      | [0].segmentDetailValue2       | NHN               |
      | [0].segmentDetailValue3       | null              |
      | [0].segmentDetailValue4       | null              |
      | [0].segmentDetailValue5       | null              |
      | [0].segmentDetailValue6       | null              |
      | [0].segmentDetailValue7       | null              |
      | [0].segmentDetailValue8       | null              |
      | [0].segmentDetailValue9       | null              |
      | [0].segmentDetailValue10      | null              |
      | [0].createdOn                 | current           |
      | [0].createdBy                 | 597               |



  @API-CP-29270 @API-CP-29270-01 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario: Verify Caseloader consumer processing is not performed for Record ID 100
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with enrollment and eligibility information
      | recordId              | 21                    |
      | isEligibilityRequired | no                    |
      | eligibilityStatusCode | V                     |
      | isEnrollmentRequired  | no                    |
      | enrollmentStartDate   | firstDayofPresntMonth |
#      | saveConsumerInfo      | 29270-1               |
    Given I created a consumer with population type "IN-EB-CONSUMER" with enrollment and eligibility information
      | case.recordId                          | 100                   |
      | isEligibilityRequired                  | yes                   |
      | eligibilityStatusCode                  | V                     |
      | isEnrollmentRequired                   | No                    |
      | enrollmentStartDate                    | firstDayofPresntMonth |
      | eligibilityRecordId                    | 21                    |
      | isOtherEligibilitySegmentsRequired     | Yes                   |
      | otherElgSegment[0].startDate           | 1stDayofLastMonth     |
      | otherElgSegment[0].endDate             | highDate              |
      | otherElgSegment[0].segmentDetailValue2 | NHN                   |
      | otherElgSegment[0].segmentTypeCode     | FACILITY              |

  @API-CP-29646 @API-CP-29646-01  @API-EE-IN @IN-EB-API-Regression @shruti @events-cc
  Scenario: New Fields . Field Source at consumer and core eligibility level,
  source & correlationID  for contacts and uiid and correlation for other enrollment segment.
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with enrollment and eligibility information
      | recordId                               | 21                    |
      | eligibilityRecordId                    | 21                    |
      | isEligibilityRequired                  | yes                   |
      | eligibilityStatusCode                  | V                     |
      | isEnrollmentRequired                   | no                    |
      | enrollmentStartDate                    | firstDayofPresntMonth |
      | isOtherEligibilitySegmentsRequired     | Yes                   |
      | otherElgSegment[0].startDate           | 1stDayofLastMonth     |
      | otherElgSegment[0].endDate             | highDate              |
      | otherElgSegment[0].segmentDetailValue2 | NHN                   |
      | otherElgSegment[0].segmentTypeCode     | FACILITY              |
      | otherElgSegment[1].startDate           | 1stDayofLastMonth     |
      | otherElgSegment[1].endDate             | highDate              |
      | otherElgSegment[1].segmentDetailValue2 | NHN                   |
      | otherElgSegment[1].segmentTypeCode     | FACILITY              |
      | isOtherEnrollmentSegmentsRequired      | yes                   |
      | otherEnrollSegment[0].startDate        | 1stDayofLastMonth     |
      | otherEnrollSegment[0].endDate          | highDate              |
      | otherEnrollSegment[0].segmentTypeCode  | LiL1                  |
      | otherEnrollSegment[1].startDate        | 1stDayofLastMonth     |
      | otherEnrollSegment[1].endDate          | highDate              |
      | otherEnrollSegment[1].segmentTypeCode  | LIL0                  |
#    Then I will publish an "CONSUMER_SAVE_EVENT" event for DPBI to consume for reporting
    Given I initiated Case Consumer Search API for Consumer Match
    When I run Consumer Search API using "consumerSSN"
    Then I verify consumer details  without an External Case ID





