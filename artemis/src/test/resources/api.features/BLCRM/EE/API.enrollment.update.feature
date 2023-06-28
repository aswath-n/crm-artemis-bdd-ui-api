@API-CP-67
Feature: API: Enrollment Feature

  @asad @API-CP-67-01 @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression # CP-67-01 will fail since there are null fields in response. Defect for this is CP-8167
  Scenario Outline: Update Enrollment Information
    Given I initiated Enrollment Update API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Enrollment Update API
    Then I will verify the Enrollment data updated
    Examples:
      | projectName |
      |[blank]|

  @asad @API-CP-67-02 @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Update Medical Plan Information
    Given I initiated Enrollment Update API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Enrollment Update API
    Then I will verify the Medical plan data updated
    Examples:
      | projectName |
      |[blank]|

  @asad @API-CP-67-03 @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Update Provider Information
    Given I initiated Enrollment Update API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Enrollment Update API
    Then I will verify the Provider data updated
    Examples:
      | projectName |
      |[blank]|

  @asad @API-CP-67-04 @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Update Dental Plan Information
    Given I initiated Enrollment Update API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Enrollment Update API
    Then I will verify the Dental plan data updated
    Examples:
      | projectName |
      |[blank]|

  @asad @API-CP-67-05 @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Update Dentist Information
    Given I initiated Enrollment Update API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Enrollment Update API
    Then I will verify the Dentist Information Updated
    Examples:
      | projectName |
      |[blank]|

  @asad @API-CP-67-06 @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Update Behavioral Health Information
    Given I initiated Enrollment Update API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Enrollment Update API
    Then I will verify the Behavioral health data updated
    Examples:
      | projectName |
      |[blank]|

  @asad @API-CP-67-07 @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression #CP-67: Events Scenario Outline CP-67-07 will fail since events are not generating. Defect for this is CP-8366
  Scenario Outline: Publish an ENROLLMENT_UPDATE_EVENT event for DPBI to consume
    Given I initiated Enrollment Update API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Enrollment Update API
    Then I will verify an "ENROLLMENT_UPDATE_EVENT" for DPBI is created for enrollment update
    Examples:
      | projectName |
      |[blank]|

  @API-CP-16979 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario Outline: Decide Disenroll Request Accept Response Scenario
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "<name>1" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>1.consumerId   |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
      | planId                       | 145                  |
      | serviceRegionCode            | East                 |
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "<name>2" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | <name>1.consumerId |
      | [0].planId             | 145                |
      | [0].planCode           | 85                 |
      | [0].startDate          | fdnxtmth::         |
      | [0].subProgramTypeCode | MEDICAIDGF         |
      | [0].serviceRegionCode  | East               |
      | [0].selectionReason    | delete::           |
      | [0].anniversaryDate    | anniversaryDate::  |
      | [0].channel            | SYSTEM_INTEGRATION |
      | [0].planEndDateReason  | PLAN_TERMINATED    |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "<name>1.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "<name>1a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | <name>1.consumerId                |
      | [0].planId             | delete::                          |
      | [0].planCode           | 84                                |
      | [0].enrollmentId       | <name>1a.discontinuedEnrollmentId |
      | [0].status             | DISENROLLED                       |
      | [0].txnStatus          | DISENROLLED                       |
      | [0].startDate          | fdofmnth::                        |
      | [0].endDate            | lstdaymnth::                      |
      | [0].enrollmentType     | delete::                          |
      | [0].subProgramTypeCode | MEDICAIDGF                        |
      | [0].serviceRegionCode  | East                              |
      | [1].planId             | delete::                          |
      | [1].planCode           | 85                                |
      | [1].consumerId         | <name>1.consumerId                |
      | [1].enrollmentId       | <name>1a.selectedEnrollmentId     |
      | [1].status             | DISREGARDED                       |
      | [1].txnStatus          | DISREGARDED                       |
      | [1].startDate          | fdnxtmth::                        |
      | [1].subProgramTypeCode | MEDICAIDGF                        |
      | [1].serviceRegionCode  | East                              |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>1.consumerId       |
      | isEligibilityRequired        | no                       |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | isEnrollmentProviderRequired | no                       |
      | recordId                     | 19                       |
      | enrollmentStartDate          | <date>                   |
      | anniversaryDate              | anniversaryDate          |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted                 |
      | programCode                  | M                        |
      | planCode                     | <planCode>               |
      | planId                       | 145                      |
      | serviceRegionCode            | East                     |
      | rejectionReason              | Consumer is not eligible |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "<eligibilityScenario>","<enrollmentScenario>"
    Examples: enrollment scenario differences based on Plan Code and Plane Start Date is as enrollment selection
      | name | date                 | planCode | enrollmentScenario | eligibilityScenario         |
      | LZ   | 1stDayofPresentMonth | 84       | Disenroll Accept   | New Retroactive Eligibility |
      | ZX   | 1stDayofNextMonth    | 84       | Not Found          | Not Found                   |
      | XC   | 1stDayofPresentMonth | 85       | Not Found          | Not Found                   |

  @API-CP-14618 @API-CP-14618-01 @API-CP-14618-02 @API-CP-14618-03 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: Create or Update Enrollment Data based on Disenroll Request Accept Response Scenario
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "CV1" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | CV1.consumerId       |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
      | planId                       | 145                  |
      | serviceRegionCode            | East                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | CV1.consumerId     |
      | [0].planId             | 145                |
      | [0].planCode           | 85                 |
      | [0].startDate          | fdnxtmth::         |
      | [0].subProgramTypeCode | MEDICAIDGF         |
      | [0].serviceRegionCode  | East               |
      | [0].selectionReason    | delete::           |
      | [0].anniversaryDate    | anniversaryDate::  |
      | [0].channel            | SYSTEM_INTEGRATION |
      | [0].planEndDateReason  | PLAN_TERMINATED    |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "CV1.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "CV1a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | CV1.consumerId                |
      | [0].planId             | delete::                      |
      | [0].planCode           | 84                            |
      | [0].enrollmentId       | CV1a.discontinuedEnrollmentId |
      | [0].status             | ACCEPTED                      |
      | [0].txnStatus          | DISENROLL_SUBMITTED           |
      | [0].startDate          | fdofmnth::                    |
      | [0].endDate            | lstdaymnth::                  |
      | [0].enrollmentType     | delete::                      |
      | [0].subProgramTypeCode | MEDICAIDGF                    |
      | [0].serviceRegionCode  | East                          |
      | [1].planId             | delete::                      |
      | [1].planCode           | 85                            |
      | [1].consumerId         | CV1.consumerId                |
      | [1].enrollmentId       | CV1a.selectedEnrollmentId     |
      | [1].status             | SUBMITTED_TO_STATE            |
      | [1].txnStatus          | SUBMITTED_TO_STATE            |
      | [1].startDate          | fdnxtmth::                    |
      | [1].subProgramTypeCode | MEDICAIDGF                    |
      | [1].serviceRegionCode  | East                          |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | CV1.consumerId           |
      | isEligibilityRequired        | no                       |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | isEnrollmentProviderRequired | no                       |
      | recordId                     | 19                       |
      | enrollmentStartDate          | 1stDayofPresentMonth     |
      | anniversaryDate              | anniversaryDate          |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted                 |
      | programCode                  | M                        |
      | planCode                     | 84                       |
      | planId                       | 145                      |
      | serviceRegionCode            | East                     |
      | rejectionReason              | Consumer is not eligible |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "CV1.consumerId"
    And I run get enrollment api
    Then I verify selection status is displayed as "DISENROLLED"
    Then I will verify business events are generated with data
      | eventName           | ENROLLMENT_UPDATE_EVENT |
      | enrollmentStartDate | 1stDayofPresentMonth    |
      | selectionReason     | null                    |
      | enrollmentType      | MEDICAL                 |
      | channel             | SYSTEM_INTEGRATION      |
      | anniversaryDate     | anniversaryDate         |
    # @API-CP-14618-02
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "CV1.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status records are displayed with benefit status "Eligible"
    # @API-CP-14618-03
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable |
      | [0].consumerAction       | null        |
      | [0].planSelectionAllowed | false       |

  @API-CP-14619 @API-CP-14619-01 @API-CP-14619-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: Capture Disenroll Request Accept Response Business Event
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | QR1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | QR1.consumerId       |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | QR1.consumerId       |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
      | planId                       | 145                  |
      | serviceRegionCode            | East                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | QR1.consumerId     |
      | [0].planId             | 145                |
      | [0].planCode           | 85                 |
      | [0].startDate          | fdnxtmth::         |
      | [0].subProgramTypeCode | MEDICAIDGF         |
      | [0].serviceRegionCode  | East               |
      | [0].selectionReason    | delete::           |
      | [0].anniversaryDate    | anniversaryDate::  |
      | [0].channel            | SYSTEM_INTEGRATION |
      | [0].planEndDateReason  | PLAN_TERMINATED    |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "QR1.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "QR1a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | QR1.consumerId                |
      | [0].planId             | delete::                      |
      | [0].planCode           | 84                            |
      | [0].enrollmentId       | QR1a.discontinuedEnrollmentId |
      | [0].status             | ACCEPTED                      |
      | [0].txnStatus          | DISENROLL_SUBMITTED           |
      | [0].startDate          | fdofmnth::                    |
      | [0].endDate            | lstdaymnth::                  |
      | [0].enrollmentType     | delete::                      |
      | [0].subProgramTypeCode | MEDICAIDGF                    |
      | [0].serviceRegionCode  | East                          |
      | [1].planId             | delete::                      |
      | [1].planCode           | 85                            |
      | [1].consumerId         | QR1.consumerId                |
      | [1].enrollmentId       | QR1a.selectedEnrollmentId     |
      | [1].status             | SUBMITTED_TO_STATE            |
      | [1].txnStatus          | SUBMITTED_TO_STATE            |
      | [1].startDate          | fdnxtmth::                    |
      | [1].subProgramTypeCode | MEDICAIDGF                    |
      | [1].serviceRegionCode  | East                          |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | QR1.consumerId           |
      | isEligibilityRequired        | no                       |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | isEnrollmentProviderRequired | no                       |
      | recordId                     | 19                       |
      | enrollmentStartDate          | 1stDayofPresentMonth     |
      | anniversaryDate              | anniversaryDate          |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted                 |
      | programCode                  | M                        |
      | planCode                     | 84                       |
      | planId                       | 145                      |
      | serviceRegionCode            | East                     |
      | rejectionReason              | Consumer is not eligible |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "QR1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","Disenroll Accept"
    Then I will verify business events are generated with data
      | eventName           | DISENROLL_REQUEST_ACCEPT_RESPONSE |
      | channel             | SYSTEM_INTEGRATION                |
      | createdBy           | 597                               |
      | processDate         | current                           |
      | externalCaseId      | QR1.caseIdentificationNumber      |
      | externalConsumerId  | QR1.externalConsumerId            |
      | consumerId          | QR1.consumerId                    |
      | consumerName        | QR1                               |
      #  @API-CP-14619-02
      | enrollmentStartDate | 1stDayofPresentMonth              |
      | enrollmentEndDate   |[blank]|
      | planChangeReason    | null                              |
      | planCode            | 84                                |
      | enrollmentType      | MEDICAL                           |
      | selectionStatus     | DISENROLLED                       |
      | consumerPopulation  | MEDICAID-GENERAL                  |
      | benefitStatus       | Eligible                          |

  @API-CP-16980 @API-CP-16980-01 @API-CP-16980-02 @API-CP-16980-03 @API-CP-16980-04 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario Outline: Update to Create or Update Enrollment Data based on Disenroll Request Accept Response Scenario
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "<name>1" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>1.consumerId   |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
      | planId                       | 145                  |
      | serviceRegionCode            | East                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | <name>1.consumerId |
      | [0].planId             | 145                |
      | [0].planCode           | 85                 |
      | [0].startDate          | fdnxtmth::         |
      | [0].subProgramTypeCode | MEDICAIDGF         |
      | [0].serviceRegionCode  | East               |
      | [0].selectionReason    | delete::           |
      | [0].anniversaryDate    | anniversaryDate::  |
      | [0].channel            | SYSTEM_INTEGRATION |
      | [0].planEndDateReason  | PLAN_TERMINATED    |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "<name>1.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "<name>1a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | <name>1.consumerId                |
      | [0].planId             | delete::                          |
      | [0].planCode           | 84                                |
      | [0].enrollmentId       | <name>1a.discontinuedEnrollmentId |
      | [0].status             | <disenrollStatus>                 |
      | [0].txnStatus          | DISENROLL_SUBMITTED               |
      | [0].startDate          | fdofmnth::                        |
      | [0].endDate            | lstdaymnth::                      |
      | [0].enrollmentType     | delete::                          |
      | [0].subProgramTypeCode | MEDICAIDGF                        |
      | [0].serviceRegionCode  | East                              |
      | [1].planId             | delete::                          |
      | [1].planCode           | 85                                |
      | [1].consumerId         | <name>1.consumerId                |
      | [1].enrollmentId       | <name>1a.selectedEnrollmentId     |
      | [1].status             | <selectionStatus>                 |
      | [1].txnStatus          | <selectionStatus>                 |
      | [1].startDate          | fdnxtmth::                        |
      | [1].subProgramTypeCode | MEDICAIDGF                        |
      | [1].serviceRegionCode  | East                              |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>1.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | isEnrollmentProviderRequired | no                 |
      | recordId                     | 19                 |
      | enrollmentStartDate          | 1stDayofNextMonth  |
      | anniversaryDate              | anniversaryDate    |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted           |
      | programCode                  | M                  |
      | planCode                     | 85                 |
      | planId                       | 145                |
      | serviceRegionCode            | East               |
      | channel                      | SYSTEM_INTEGRATION |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","Disenroll Accept"
    # @API-CP-16980-03
    Then I verify benefit status records are displayed with benefit status "Enrolled"
    # @API-CP-16980-04
    Then I Verify Consumer Actions as following data
      | [0] | empty |
    And Wait for 5 seconds
    And I verify enrollment by consumer id "<name>1.consumerId" with data
      | medicalPlanBeginDate | 1stDayofPresentMonth |
      | enrollmentType       | MEDICAL              |
      | channel              | SYSTEM_INTEGRATION   |
      | anniversaryDate      | anniversaryDate      |
      | planCode             | 84                   |
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | <name>1.consumerId      |
      | consumerFound | true                    |
      | recordId      | <eventRecordId>         |
    # recordId = 19 means ENROLLMENT_UPDATE_EVENT was published for Plan Change Reject
    Examples:  consumer’s enrollment segment is in a Selection Status of “Accepted“ OR “Disenrolled“
      | name | disenrollStatus | selectionStatus    | eventRecordId |
      | RS   | ACCEPTED        | SUBMITTED_TO_STATE | 19            |
      # @API-CP-16980-02
      | RD   | DISENROLLED     | DISREGARDED        | null          |