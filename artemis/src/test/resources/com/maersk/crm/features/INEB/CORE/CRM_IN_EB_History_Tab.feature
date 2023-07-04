Feature: INEB History Tab

  @CP-11059 @ui-core-in-eb @crm-regression @IN-EB-UI-Regression @aikanysh
  Scenario Outline: IN_EB, BLCRM | View Transaction Details - Eligibility Business Events
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramCode>        |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | <name>.consumerId        |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | enrollmentEndDate            | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramCode>        |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | planCode                     | <planCode1>             |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | <name>.consumerId  |
      | [0].planId             | 26                 |
      | [0].planCode           | <planCode2>        |
      | [0].startDate          | fdnxtmth::         |
      | [0].endDate            | highDate::         |
      | [0].subProgramTypeCode | <subProgramCode>   |
      | [0].serviceRegionCode  | Statewide          |
      | [0].selectionReason    | 02                 |
      | [0].anniversaryDate    | anniversaryDate::  |
      | [0].channel            | SYSTEM_INTEGRATION |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "<name>.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "<name>a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | <name>.consumerId                |
      | [0].planId             | delete::                         |
      | [0].planCode           | <planCode1>                      |
      | [0].enrollmentId       | <name>a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED              |
      | [0].txnStatus          | DISENROLL_SUBMITTED              |
      | [0].startDate          | fdofmnth::                       |
      | [0].endDate            | lstdaymnth::                     |
      | [0].enrollmentType     | delete::                         |
      | [0].subProgramTypeCode | <subProgramCode>                 |
      | [0].serviceRegionCode  | Statewide                        |
      | [1].planId             | delete::                         |
      | [1].planCode           | <planCode2>                      |
      | [1].consumerId         | <name>.consumerId                |
      | [1].enrollmentId       | <name>a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                       |
      | [1].endDate            | highDate::                       |
      | [1].subProgramTypeCode | <subProgramCode>                 |
      | [1].serviceRegionCode  | Statewide                        |
    And Wait for 5 seconds
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I search created consumer for business events
    And I validate transaction details fields for Elibigility Business Events
    Examples:
      | name    | programCode | subProgramCode    | eligibilityStatusCode | population    | planCode1 | planCode2 |
      | 23757-1 | R           | HoosierHealthwise | M                     | HHW-Mandatory | 400752220 | 500307680 |

  @CP-14229 @ui-core-in-eb @crm-regression @IN-EB-UI-Regression @aikanysh
  Scenario Outline: IN_EB, BLCRM | View Transaction Details - Enrollment Business Events
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramCode>        |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | <name>.consumerId        |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | enrollmentEndDate            | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramCode>        |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | planCode                     | <planCode1>             |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | <name>.consumerId  |
      | [0].planId             | 26                 |
      | [0].planCode           | <planCode2>        |
      | [0].startDate          | fdnxtmth::         |
      | [0].endDate            | highDate::         |
      | [0].subProgramTypeCode | <subProgramCode>   |
      | [0].serviceRegionCode  | Statewide          |
      | [0].selectionReason    | 02                 |
      | [0].anniversaryDate    | anniversaryDate::  |
      | [0].channel            | SYSTEM_INTEGRATION |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "<name>.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "<name>a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | <name>.consumerId                |
      | [0].planId             | delete::                         |
      | [0].planCode           | <planCode1>                      |
      | [0].enrollmentId       | <name>a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED              |
      | [0].txnStatus          | DISENROLL_SUBMITTED              |
      | [0].startDate          | fdofmnth::                       |
      | [0].endDate            | lstdaymnth::                     |
      | [0].enrollmentType     | delete::                         |
      | [0].subProgramTypeCode | <subProgramCode>                 |
      | [0].serviceRegionCode  | Statewide                        |
      | [1].planId             | delete::                         |
      | [1].planCode           | <planCode2>                      |
      | [1].consumerId         | <name>.consumerId                |
      | [1].enrollmentId       | <name>a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                       |
      | [1].endDate            | highDate::                       |
      | [1].subProgramTypeCode | <subProgramCode>                 |
      | [1].serviceRegionCode  | Statewide                        |
    And Wait for 5 seconds
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I search created consumer for business events
    And I validate transaction details fields for Enrollement Business Events
    Examples:
      | name    | programCode | subProgramCode    | eligibilityStatusCode | population    | planCode1 | planCode2 |
      | 23757-1 | R           | HoosierHealthwise | M                     | HHW-Mandatory | 400752220 | 500307680 |


  @CP-11058 @CP-11058-02 @CP-11058-04 @CP-11058-05 @CP-11058-06 @CP-11058-07 @ui-core-in-eb @crm-regression @IN-EB-UI-Regression @aikanysh @khazar
  Scenario Outline: IN_EB, BLCRM | Filter Consumer's Benefit History (Business Events) (1 consumer on case)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramCode>        |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | <name>.consumerId        |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | enrollmentEndDate            | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramCode>        |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | planCode                     | <planCode1>             |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | <name>.consumerId  |
      | [0].planId             | 26                 |
      | [0].planCode           | <planCode2>        |
      | [0].startDate          | fdnxtmth::         |
      | [0].endDate            | highDate::         |
      | [0].subProgramTypeCode | <subProgramCode>   |
      | [0].serviceRegionCode  | Statewide          |
      | [0].selectionReason    | 02                 |
      | [0].anniversaryDate    | anniversaryDate::  |
      | [0].channel            | SYSTEM_INTEGRATION |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "<name>.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "<name>a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | <name>.consumerId                |
      | [0].planId             | delete::                         |
      | [0].planCode           | <planCode1>                      |
      | [0].enrollmentId       | <name>a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED              |
      | [0].txnStatus          | DISENROLL_SUBMITTED              |
      | [0].startDate          | fdofmnth::                       |
      | [0].endDate            | lstdaymnth::                     |
      | [0].enrollmentType     | delete::                         |
      | [0].subProgramTypeCode | <subProgramCode>                 |
      | [0].serviceRegionCode  | Statewide                        |
      | [1].planId             | delete::                         |
      | [1].planCode           | <planCode2>                      |
      | [1].consumerId         | <name>.consumerId                |
      | [1].enrollmentId       | <name>a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                       |
      | [1].endDate            | highDate::                       |
      | [1].subProgramTypeCode | <subProgramCode>                 |
      | [1].serviceRegionCode  | Statewide                        |
    And Wait for 5 seconds
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I search created consumer for business events
    And I verify drop down options are displayed for History screen
    And I verify Business Events dropdown values on History screen
      | All         |
      | Eligibility |
      | Enrollment  |
    And I verify Process Date Range dropdown values on History screen
      | Last 1 Month      |
      | Last 3 Months     |
      | Last 6 Months     |
      | Last 1 Year       |
      | Last 2 Years      |
      | Select Date Range |
    Then I verify Channel dropdown values on History screen
      | All                |
      | IVR                |
      | Fax                |
      | Mail               |
      | Phone              |
      | System Integration |
      | Web                |
    And I select "Eligibility" as Business Events
    And I select "Last 3 Months" as Process Data Range
    And I select "Fax" as Channel
    Then I reset filters
    And I verify drop down options are displayed for History screen
    Examples:
      | name    | programCode | subProgramCode    | eligibilityStatusCode | planCode1 | planCode2 |
      | 23757-1 | R           | HoosierHealthwise | M                     | 400752220 | 500307680 |

  @CP-11058 @CP-11058-02-01 @CP-11058-04 @CP-11058-05 @CP-11058-06 @CP-11058-07-01 @ui-core-in-eb @crm-regression @IN-EB-UI-Regression @aikanysh @khazar
  Scenario: IN_EB, BLCRM | Filter Consumer's Benefit History (Business Events) (multiple consumers on case)
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched customer have First Name as "FnHhLmn " and Last Name as "LnfqzlB"
    And I navigate to Business Events history for the consumer
    And I verify drop down options are displayed for History screen for multiple consumers
    And I verify consumer name dropdown field and select primary member
    And I verify Business Events dropdown values on History screen
      | All         |
      | Eligibility |
      | Enrollment  |
    And I verify Process Date Range dropdown values on History screen
      | Last 1 Month      |
      | Last 3 Months     |
      | Last 6 Months     |
      | Last 1 Year       |
      | Last 2 Years      |
      | Select Date Range |
    Then I verify Channel dropdown values on History screen
      | All                |
      | IVR                |
      | Fax                |
      | Mail               |
      | Phone              |
      | System Integration |
      | Web                |
    And I select "Eligibility" as Business Events
    And I select "Last 3 Months" as Process Data Range
    And I select "Fax" as Channel
    Then I reset filters
    And I verify drop down options are displayed for History screen for multiple consumers