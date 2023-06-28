Feature: API: Create & Update MMIS scenarios

  @API-CP-10281 @API-CP-10281-01 @API-CP-8781 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Scenario output =New Eligibility, No Enrollment , recordId =1, Verify Record Created & Event generated
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN" with data
      | saveConsumerInfo | 10281-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 10281-01.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | categoryCode                 | 789Aidcode          |
      | isEnrollemntRequired         | no                  |
      | recordId                     | 1                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth   |
      | eligibilityEndDate           | future              |
      | eligibilityStatusCode        | Mandatory           |
      | exemptionCode                | E00                 |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | coverageCode                 | CC01                |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    Then I verify scenario output in the benefit status records are displayed as "New Eligibility","null"
    When I initiated get eligibility by consumer id "10281-01.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | categoryCode          | 789Aidcode        |
      | programCode           | M                 |
      | coverageCode          | CC01              |
      | eligibilityStatusCode | Mandatory         |
      | exemptionCode         | E00               |
      | eligibilityStartDate  | 1stDayofNextMonth |
      | eligibilityEndDate    | future            |
      | Created on            |[blank]|
      | createdBy             | 597               |
      | Updated On            |[blank]|
      | updatedBy             | 597               |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT |
      | consumerId    | 10281-01.consumerId    |
      | correlationId | null                   |
      | consumerFound | true                   |
      | DPBI          |[blank]|

  @API-CP-10282 @API-CP-10282-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Scenario output =New Enrollment, No Eligibility , recordId =2, Verify Record Created & Event generated
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN" with data
      | saveConsumerInfo | 10282 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 10282.consumerId  |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | categoryCode                 | 789Aidcode        |
      | isEnrollemntRequired         | no                |
      | recordId                     | 1                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth |
      | eligibilityEndDate           | future            |
      | eligibilityStatusCode        | M                 |
      | exemptionCode                | E00               |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | coverageCode                 | CC01              |
      | serviceRegionCode            | Atlanta           |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 10282.consumerId  |
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 2                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         | 1stDayofNextMonth |
      | anniversaryDate              | anniversaryDate   |
      | eligibilityEndDate           | future            |
      | eligibilityStatusCode        | M                 |
      | exemptionCode                | E00               |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | lockInStartDate              | past              |
      | lockInEndDate                | future            |
      | lockInStatusCode             | ABC               |
      | lockInExemptionReason        | test              |
      | selectionReason              | test              |
      | serviceRegionCode            | Atlanta           |
    Then I send API call with name "10282a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "10282.consumerId"
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer "10282.consumerId"
    Then I verify scenario output in the benefit status records are displayed as "New Eligibility","New Enrollment"
    When I initiated get enrollment by consumer id "10282.consumerId"
    And I run get enrollment api
    Then I verify latest enrollment by consumer id "10282.consumerId" with data
      | planCode              | 84                |
      | enrollmentStartDate   | 1stDayofNextMonth |
      | enrollmentEndDate     | null              |
      | anniversaryDate       | anniversaryDate   |
      | enrollmentType        | MEDICAL           |
      | selectionReason       | test              |
      | serviceRegionCode     | East              |
      | autoAssignIndicator   | a                 |
      | lockInStartDate       | past              |
      | lockInEndDate         | future            |
      | lockInStatusCode      | ABC               |
      | lockInExemptionReason | test              |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                |
      | correlationId | 10282a.enrollments.[0].correlationId |
      | consumerId    | 10282.consumerId                     |
      | consumerFound | true                                 |
      | DPBI          |[blank]|


  @API-CP-8778 @API-CP-8778-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Scenario output =New Retroactive Eligibility, No Enrollment , recordId =3, MMIs scenario
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN" with data
      | saveConsumerInfo | 8778-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 8778-01.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | categoryCode                 | 789Aidcode           |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | future               |
      | eligibilityStatusCode        | M                    |
      | exemptionCode                | E00                  |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | coverageCode                 | CC01                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","null"
    When I initiated get eligibility by consumer id "8778-01.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | categoryCode          | 789Aidcode           |
      | programCode           | M                    |
      | coverageCode          | CC01                 |
      | eligibilityStatusCode | M                    |
      | exemptionCode         | E00                  |
      | eligibilityStartDate  | 1stDayofPresentMonth |
      | eligibilityEndDate    | future               |
      | Created on            |[blank]|
      | createdBy             | 597                  |
      | Updated On            |[blank]|
      | updatedBy             | 597                  |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT |
      | consumerId    | 8778-01.consumerId     |
      | correlationId | null                   |
      | consumerFound | true                   |
      | DPBI          |[blank]|

  @API-CP-8700 @API-CP-8700-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Scenario output =New Retroactive Enrollment , Retroactive  Enrollment , recordId =4, MMIs scenario
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN" with data
      | saveConsumerInfo | 8700-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 8700-01.consumerId |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | categoryCode                 | 789Aidcode           |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | future               |
      | eligibilityStatusCode        | M                    |
      | exemptionCode                | E00                  |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | coverageCode                 | CC01                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 8700-01.consumerId |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | anniversaryDate              | future               |
      | eligibilityEndDate           | future               |
      | eligibilityStatusCode        | M                    |
      | exemptionCode                | E00                  |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
      | lockInStartDate              | past                 |
      | lockInEndDate                | future               |
      | lockInStatusCode             | ABC                  |
      | lockInExemptionReason        | test                 |
      | selectionReason              | test                 |
      | serviceRegionCode            | Atlanta              |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "8700-01.consumerId"
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer "8700-01.consumerId"
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","New Retroactive Enrollment"
    And I verify enrollment by consumer id "8700-01.consumerId" with data
      | planCode              | 84                |
      | enrollmentStartDate   | 1stDayofLastMonth |
      | enrollmentEndDate     | null              |
      | anniversaryDate       | future            |
      | enrollmentType        | MEDICAL           |
      | selectionReason       | test              |
      | serviceRegionCode     | East              |
      | autoAssignIndicator   | a                 |
      | lockInEndDate         | future            |
      | lockInStatusCode      | ABC               |
      | lockInExemptionReason | test              |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | 8700-01.consumerId    |
      | consumerFound | true                  |
      | DPBI          |[blank]|

  @API-CP-10728 @API-CP-10728-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Scenario output =Retroactive Eligibility Date Change, No Enrollment , recordId =5, Verify Record Created & Event generated
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN" with data
      | saveConsumerInfo | 10728-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 10728-01.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | categoryCode                 | 789Aidcode           |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityStatusCode        | M                    |
      | exemptionCode                | E00                  |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | coverageCode                 | CC01                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | categoryCode                 | 789Aidcode        |
      | isEnrollemntRequired         | no                |
      | recordId                     | 5                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityStatusCode        | M                 |
      | exemptionCode                | E00               |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | coverageCode                 | CC01              |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    Then I verify scenario output in the benefit status records are displayed as "Retroactive Eligibility Date Change","null"
    When I initiated get eligibility by consumer id "10728-01.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | categoryCode          | 789Aidcode        |
      | programCode           | M                 |
      | coverageCode          | CC01              |
      | eligibilityStatusCode | M                 |
      | exemptionCode         | E00               |
      | eligibilityStartDate  | 1stDayofLastMonth |
      | Created on            |[blank]|
      | createdBy             | 597               |
      | Updated On            |[blank]|
      | updatedBy             | 597               |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT |
      | consumerId    | 10728-01.consumerId      |
      | correlationId | null                     |
      | consumerFound | true                     |
      | DPBI          |[blank]|

  @API-CP-15761 @API-CP-15761-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Loss of Eligibility
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 13                |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | current           |
      | eligibilityEndReason         | test              |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    Then I verify scenario output in the benefit status records are displayed as "Loss of Eligibility","null"


  @API-CP-15768 @API-CP-15768-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Disenroll with Loss of Eligibility
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | anniversaryDate              | anniversaryDate   |
    Then I send API call with name "TU1" for create Eligibility and Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | TU1.consumerId    |
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 145               |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | TU1.consumerId    |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 14                |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | future            |
      | enrollmentEndDate            | future            |
      | planEndDateReason            | test              |
      | eligibilityEndReason         | test              |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 145               |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "TU1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Disenroll with Loss of Eligibility","Disenroll with Loss of Eligibility"

#duplicate scenario
  @API-CP-15064 @API-CP-15064-01 @API-EE-ignore @API-CRM-ignore @eligibility-enrollment-ms-EE @kamil
  Scenario: Verify MMIS Sends New Enrollment Reject Response for system processing
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN"
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
    Then I send API call with name "QR1" for create Eligibility and Enrollment
    And Wait for 5 seconds
    Then I send API call with name "EI" for update Enrollment information
      | [0].consumerId | QR1.consumerId |
      | [0].planId     | 145            |
      | [0].planCode   | 84             |
      | [0].startDate  | fdofmnth::     |
    And I send API call with name "PO" for process Outbound Update
      | [0].planId       | 145                    |
      | [0].planCode     | 84                     |
      | [0].consumerId   | QR1.consumerId         |
      | [0].startDate    | fdofmnth::             |
      | [0].enrollmentId | c.data[0].enrollmentId |
    And I send API call with name "ST" for trigger Enrollment Start
      | consumerId                         | QR1.consumerId               |
      | enrollments.[0].consumerId         | QR1.consumerId               |
      | enrollments.[0].subProgramTypeCode | c.data[0].subProgramTypeCode |
      | enrollments.[0].planId             | 145                          |
      | enrollments.[0].planCode           | 84                           |
      | enrollments.[0].rejectionReason    | Consumer is not eligible     |
      | recordId                           | int::16                      |
      | enrollments.[0].startDate          | fdofmnth::                   |
      | enrollments.[0].endDate            | null::                       |
      | enrollments.[0].createdOn          | delete::                     |
    And Wait for 5 seconds
    Then I run API call to get benefit status by consumer id "QR1.consumerId"
    And I verify Enrollment Scenario is "New Enrollment Reject"

#duplicate scenario
  @API-CP-15064 @API-CP-15064-02 @API-EE-ignore @API-CRM-Regression-ignore @eligibility-enrollment-ms-EE @kamil
  Scenario: Verify MMIS NOT Sends New Enrollment Reject Response for system processing
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN"
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
    Then I send API call with name "QT1" for create Eligibility and Enrollment
    And Wait for 5 seconds
    Then I send API call with name "ue" for update Enrollment information
      | [0].consumerId | QT1.consumerId |
      | [0].planId     | 145            |
      | [0].planCode   | 84             |
      | [0].startDate  | fdofmnth::     |
    And I send API call with name "uo" for process Outbound Update
      | [0].planId       | 145                    |
      | [0].planCode     | 84                     |
      | [0].consumerId   | QT1.consumerId         |
      | [0].startDate    | fdofmnth::             |
      | [0].enrollmentId | c.data[0].enrollmentId |
    And I send API call with name "ST1" for trigger Enrollment Start
      | consumerId                         | QT1.consumerId               |
      | enrollments.[0].consumerId         | QT1.consumerId               |
      | enrollments.[0].subProgramTypeCode | c.data[0].subProgramTypeCode |
      | enrollments.[0].planId             | 133                          |
      | enrollments.[0].planCode           | 99                           |
      | enrollments.[0].rejectionReason    | Consumer is not eligible     |
      | recordId                           | int::16                      |
      | enrollments.[0].startDate          | fdofmnth::                   |
      | enrollments.[0].endDate            | null::                       |
      | enrollments.[0].createdOn          | delete::                     |
    Then I run API call to get benefit status by consumer id "QT1.consumerId"
    And I verify Enrollment Scenario is "null"


  @API-CP-10983 @API-CP-10983-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @kamil
  Scenario: Verify Update the consumer’s enrollment selection
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | facilityInfo         |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    Then I send API call with name "ND2" for create Eligibility and Enrollment
    And Wait for 5 seconds
    Then I send API call with name "C" for update Enrollment information
      | [0].consumerId | ND2.consumerId |
      | [0].planId     | int::145       |
      | [0].planCode   | 84             |
      | [0].startDate  | fdofmnth::     |
    And I send API call with name "PO2" for process Outbound Update
      | [0].planId       | 145                    |
      | [0].planCode     | 84                     |
      | [0].consumerId   | ND2.consumerId         |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].startDate    | fdofmnth::             |
      | [0].status       | Rejected               |
      | [0].txnStatus    | Rejected               |
    And I send API call with name "ST2" for trigger Enrollment Start
      | consumerId                         | C.data[0].consumerId         |
      | enrollments.[0].consumerId         | C.data[0].consumerId         |
      | enrollments.[0].subProgramTypeCode | C.data[0].subProgramTypeCode |
      | enrollments.[0].planId             | 145                          |
      | enrollments.[0].planCode           | 84                           |
      | enrollments.[0].rejectionReason    | Consumer is not eligible     |
      | recordId                           | int::16                      |
      | enrollments.[0].startDate          | fdofmnth::                   |
      | enrollments.[0].endDate            | null::                       |
      | enrollments.[0].createdOn          | delete::                     |
    And Wait for 5 seconds
    And I verify "ENROLLMENT_UPDATE_EVENT" events are generated in DPBI with status "Rejected"


  @API-CP-10983 @API-CP-10983-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @kamil
  Scenario: Verify Decide Benefit Status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | facilityInfo         |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    Then I send API call with name "ND3" for create Eligibility and Enrollment
    And Wait for 5 seconds
    Then I send API call with name "C" for update Enrollment information
      | [0].consumerId | ND3.consumerId |
      | [0].planId     | int::145       |
      | [0].planCode   | 84             |
      | [0].startDate  | fdofmnth::     |
    And I send API call with name "PO3" for process Outbound Update
      | [0].planId       | 145                    |
      | [0].planCode     | 84                     |
      | [0].consumerId   | ND3.consumerId         |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].startDate    | fdofmnth::             |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And I send API call with name "ST3" for trigger Enrollment Start
      | consumerId                         | ND3.consumerId               |
      | enrollments.[0].consumerId         | ND3.consumerId               |
      | enrollments.[0].subProgramTypeCode | C.data[0].subProgramTypeCode |
      | enrollments.[0].planId             | 145                          |
      | enrollments.[0].planCode           | 84                           |
      | enrollments.[0].rejectionReason    | Consumer is not eligible     |
      | recordId                           | int::16                      |
      | enrollments.[0].startDate          | fdofmnth::                   |
      | enrollments.[0].endDate            | null::                       |
      | enrollments.[0].createdOn          | delete::                     |
    And Wait for 10 seconds
    And I initiated get benefit status by consumer id "ND3.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","New Enrollment Reject"

  @API-CP-10983 @API-CP-10983-03 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @kamil
  Scenario: Verify Re-determine Consumer Actions and Change Window
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | facilityInfo         |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    Then I send API call with name "ND4" for create Eligibility and Enrollment
    And Wait for 5 seconds
    Then I send API call with name "C" for update Enrollment information
      | [0].consumerId | ND4.consumerId |
      | [0].planId     | int::145       |
      | [0].planCode   | 84             |
      | [0].startDate  | fdofmnth::     |
    And I send API call with name "PO4" for process Outbound Update
      | [0].planId       | 145                    |
      | [0].planCode     | 84                     |
      | [0].consumerId   | ND4.consumerId         |
      | [0].startDate    | fdofmnth::             |
      | [0].enrollmentId | c.data[0].enrollmentId |
    And I send API call with name "ST4" for trigger Enrollment Start
      | consumerId                         | C.data[0].consumerId         |
      | enrollments.[0].consumerId         | C.data[0].consumerId         |
      | enrollments.[0].subProgramTypeCode | C.data[0].subProgramTypeCode |
      | enrollments.[0].planId             | 145                          |
      | enrollments.[0].planCode           | 84                           |
      | enrollments.[0].rejectionReason    | Consumer is not eligible     |
      | recordId                           | int::16                      |
      | enrollments.[0].startDate          | fdofmnth::                   |
      | enrollments.[0].endDate            | null::                       |
      | enrollments.[0].createdOn          | delete::                     |
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "ND4.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","New Enrollment Reject"
    Then Verify Consumer Action is "Required"


  @API-CP-15063 @API-CP-15063-01 @API-CP-15063-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @kamil
  Scenario: Capture New Enrollment Reject Response Business Event Required Fields
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN" with data
      | saveConsumerInfo | 15063-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15063-01.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                | facilityInfo         |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Then I send API call with name "C" for update Enrollment information
      | [0].consumerId | 15063-01.consumerId |
      | [0].planId     | int::145            |
      | [0].planCode   | 84                  |
      | [0].startDate  | fdofmnth::          |
    And I send API call with name "PO5" for process Outbound Update
      | [0].planId       | 145                    |
      | [0].planCode     | 84                     |
      | [0].consumerId   | 15063-01.consumerId    |
      | [0].startDate    | fdofmnth::             |
      | [0].enrollmentId | c.data[0].enrollmentId |
    And I send API call with name "ST5" for trigger Enrollment Start
      | consumerId                         | 15063-01.consumerId          |
      | enrollments.[0].consumerId         | 15063-01.consumerId          |
      | enrollments.[0].subProgramTypeCode | C.data[0].subProgramTypeCode |
      | enrollments.[0].planId             | 145                          |
      | enrollments.[0].planCode           | 84                           |
      | enrollments.[0].rejectionReason    | Consumer is not eligible     |
      | recordId                           | int::16                      |
      | enrollments.[0].startDate          | fdofmnth::                   |
      | enrollments.[0].endDate            | null::                       |
      | enrollments.[0].createdOn          | delete::                     |
    And Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName           | NEW_ENROLLMENT_REJECT_RESPONSE    |
      | channel             | SYSTEM_INTEGRATION                |
      | createdBy           | 2893                              |
      | processDate         | current                           |
      | externalCaseId      | 15063-01.caseIdentificationNumber |
      | externalConsumerId  | 15063-01.externalConsumerId       |
      | consumerId          | 15063-01.consumerId               |
      | consumerName        | 15063-01                          |
      #  @API-CP-15063-02
      | linkedObjects       |[blank]|
      | enrollmentStartDate | 1stDayofPresentMonth              |
      | enrollmentEndDate   | null                              |
      | planChangeReason    | null                              |
      | rejectionReason     | Consumer is not eligible          |
      | planCode            | 84                                |
      | enrollmentType      | MEDICAL                           |
      | selectionStatus     | REJECTED                          |
      | consumerPopulation  | NEWBORN                           |
      | benefitStatus       | Eligible                          |

  @API-CP-14600 @API-CP-114600-01 @API-CP-114600-02 @API-CP-114600-03 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @kamil @sobir
  Scenario Outline:  Decide Scenario is MMIS Sends Plan Change Enrollment Accept Response
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
    Then I send API call with name "<name>" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
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
      | [0].consumerId         | <name>.consumerId  |
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
    And I initiated get enrollment by consumer id "<name>.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "<name>a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | <name>.consumerId                |
      | [0].planId             | delete::                         |
      | [0].planCode           | 84                               |
      | [0].enrollmentId       | <name>a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED              |
      | [0].txnStatus          | DISENROLL_SUBMITTED              |
      | [0].startDate          | fdofmnth::                       |
      | [0].endDate            | lstdaymnth::                     |
      | [0].enrollmentType     | delete::                         |
      | [0].subProgramTypeCode | MEDICAIDGF                       |
      | [0].serviceRegionCode  | East                             |
      | [1].planId             | delete::                         |
      | [1].planCode           | 85                               |
      | [1].consumerId         | <name>.consumerId                |
      | [1].enrollmentId       | <name>a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                       |
      | [1].subProgramTypeCode | MEDICAIDGF                       |
      | [1].serviceRegionCode  | East                             |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId |
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | isEnrollmentProviderRequired | no                |
      | recordId                     | 17                |
      | enrollmentStartDate          | <startDate>       |
      | anniversaryDate              | anniversaryDate   |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | <planCode>        |
      | planId                       | 145               |
      | serviceRegionCode            | East              |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "<eligibilityScenario>","<enrollmentScenario>"
    Examples: Enrollment scenario examples "MMIS Sends Plan Change Enrollment Accept Response" or "Scenario Not Found"
      | name    | startDate            | planCode | eligibilityScenario         | enrollmentScenario            |
      | 14600-1 | 1stDayofNextMonth    | 85       | New Retroactive Eligibility | Plan Change Enrollment Accept |
      | 14600-2 | 1stDayofPresentMonth | 85       | Not Found                   | Not Found                     |
      | 14600-3 | 1stDayofNextMonth    | 87       | Not Found                   | Not Found                     |

  @API-CP-14601 @API-CP-14601-01 @API-CP-14601-02 @API-CP-14601-03 @API-CP-14601-04 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: Verify ENROLLMENT_UPDATE_EVENT event in each step of Plan Change
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
    Then I send API call with name "14601" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14601.consumerId     |
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
      | [0].consumerId         | 14601.consumerId   |
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
    And I initiated get enrollment by consumer id "14601.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "14601a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 14601.consumerId                |
      | [0].planId             | delete::                        |
      | [0].planCode           | 84                              |
      | [0].enrollmentId       | 14601a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED             |
      | [0].txnStatus          | DISENROLL_SUBMITTED             |
      | [0].startDate          | fdofmnth::                      |
      | [0].endDate            | lstdaymnth::                    |
      | [0].enrollmentType     | delete::                        |
      | [0].subProgramTypeCode | MEDICAIDGF                      |
      | [0].serviceRegionCode  | East                            |
      | [1].planId             | delete::                        |
      | [1].planCode           | 85                              |
      | [1].consumerId         | 14601.consumerId                |
      | [1].enrollmentId       | 14601a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                      |
      | [1].subProgramTypeCode | MEDICAIDGF                      |
      | [1].serviceRegionCode  | East                            |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14601.consumerId  |
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | isEnrollmentProviderRequired | no                |
      | recordId                     | 17                |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | anniversaryDate              | anniversaryDate   |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 85                |
      | planId                       | 145               |
      | serviceRegionCode            | East              |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName           | ENROLLMENT_UPDATE_EVENT |
      | consumerId          | 14601.consumerId        |
      | correlationId       | null                    |
      | consumerFound       | true                    |
      | enrollmentStartDate | 1stDayofNextMonth       |
      | enrollmentEndDate   | null                    |
      | enrollmentType      | MEDICAL                 |
      | channel             | SYSTEM_INTEGRATION      |
    # @API-CP-14601-04
    And I initiated get benefit status by consumer id "14601.consumerId"
    And I run get enrollment api
    Then I verify benefit status records are displayed with benefit status "Enrolled"
    And Wait for 5 seconds
    Then I Verify Consumer Actions as following data
      | [0].action               | Available               |
      | [0].consumerAction       | Plan Change Anniversary |
      | [0].planSelectionAllowed | true                    |
      | [1].action               | Available               |
      | [1].consumerAction       | Plan Change Pre-lockin  |
      | [1].planSelectionAllowed | true                    |

  @API-CP-14602 @API-CP-16190 @API-CP-14602-01 @API-CP-14602-02 @API-CP-14602-04 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: Verify ENROLLMENT_UPDATE_EVENT event after MMIS sends Plan Change Accept Response
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 14602-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14602-01.consumerId  |
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
      | consumerId                   | 14602-01.consumerId  |
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
      | [0].consumerId         | 14602-01.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 85                  |
      | [0].startDate          | fdnxtmth::          |
      | [0].subProgramTypeCode | MEDICAIDGF          |
      | [0].serviceRegionCode  | East                |
      | [0].selectionReason    | delete::            |
      | [0].anniversaryDate    | anniversaryDate::   |
      | [0].channel            | SYSTEM_INTEGRATION  |
      | [0].planEndDateReason  | PLAN_TERMINATED     |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "14602-01.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "14602-01b"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 14602-01.consumerId                |
      | [0].planId             | delete::                           |
      | [0].planCode           | 84                                 |
      | [0].enrollmentId       | 14602-01b.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED                |
      | [0].txnStatus          | DISENROLL_SUBMITTED                |
      | [0].startDate          | fdofmnth::                         |
      | [0].endDate            | lstdaymnth::                       |
      | [0].enrollmentType     | delete::                           |
      | [0].subProgramTypeCode | MEDICAIDGF                         |
      | [0].serviceRegionCode  | East                               |
      | [1].planId             | delete::                           |
      | [1].planCode           | 85                                 |
      | [1].consumerId         | 14602-01.consumerId                |
      | [1].enrollmentId       | 14602-01b.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                         |
      | [1].subProgramTypeCode | MEDICAIDGF                         |
      | [1].serviceRegionCode  | East                               |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14602-01.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | isEnrollmentProviderRequired | no                  |
      | recordId                     | 17                  |
      | enrollmentStartDate          | 1stDayofNextMonth   |
      | anniversaryDate              | anniversaryDate     |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | planCode                     | 85                  |
      | planId                       | 145                 |
      | serviceRegionCode            | East                |
    Then I send API call with name "14602-01a" for create Eligibility and Enrollment
    And Wait for 10 seconds
    And I initiated get benefit status by consumer id "14602-01.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","Plan Change Enrollment Accept"
    And Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName                              | PLAN_CHANGE_ACCEPT_RESPONSE       |
#      | correlationId                          | 14602-01a.traceid                 |
      | correlationId                          | null                              |
      | channel                                | SYSTEM_INTEGRATION                |
      | createdBy                              | 597                               |
      | processDate                            | current                           |
      | externalCaseId                         | 14602-01.caseIdentificationNumber |
      | externalConsumerId                     | 14602-01.externalConsumerId       |
      | consumerId                             | 14602-01.consumerId               |
      | consumerName                           | 14602-01                          |
      | consumerPopulation                     | MEDICAID-GENERAL                  |
      | benefitStatus                          | Enrolled                          |
      #  @API-CP-14602-02
#      | RequestedEnrollmentStartDate moved dawn to @API-CP-14602-04 to compare old and new values
      | requestedSelection.enrollmentEndDate   | null                              |
      | requestedSelection.planCode            | 85                                |
      | requestedSelection.enrollmentType      | MEDICAL                           |
      #  @API-CP-14602-04
      | previousEnrollment.enrollmentStartDate | 1stDayofPresentMonth              |
      | previousEnrollment.planChangeReason    | PLAN_TERMINATED                   |
      | previousEnrollment.selectionStatus     | DISENROLLED                       |
      | requestedSelection.enrollmentStartDate | 1stDayofNextMonth                 |
      | requestedSelection.planChangeReason    | null                              |
      | requestedSelection.selectionStatus     | ACCEPTED                          |

  #duplicate and incomplete scenario
  @API-CP-14601 @API-CP-14601-01 @API-CP-14601-02 @API-CP-14601-03 @API-CP-14601-04 @API-EE-ignore @API-CRM-Regression-ignore @eligibility-enrollment-ms-EE-ignore @sobir
  Scenario: Verify ENROLLMENT_UPDATE_EVENT event in each step of Plan Change - ignore duplicate scenario
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
    Then I send API call with name "GH1" for create Eligibility and Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 21                 |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayoflasttMonth |
      | txnStatus                    | Accepted           |
      | programCode                  | M                  |
      | anniversaryDate              | anniversaryDate    |
