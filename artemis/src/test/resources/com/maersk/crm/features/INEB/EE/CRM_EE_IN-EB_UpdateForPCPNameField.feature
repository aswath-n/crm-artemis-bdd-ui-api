@INEB-UI-RERUN-0424
Feature: IN-EB Program & Benefits Screen: Update for "PCP Name" field on Enrollment Segment

  @CP-33459 @CP-33459-01 @CP-33459-02 @CP-33459-03 @CP-23933-03 @ui-ee-in @crm-regression @IN-EB-UI-Regression @kursat
  Scenario: IN-EB Program & Benefits Screen: Update for "PCP Name" field on Enrollment Segment - HHW
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 33459-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33459-01.consumerId      |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | enrollmentRecordId           | 21                       |
      | isEnrollmentProviderRequired | yes                      |
      | providerNpi                  | 100079260A               |
      | providerStartDate            | 1stDayofPresentMonth     |
      | providerEndDate              | 90DayFromFirstDayOfMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | highDate                 |
      | enrollmentStartDate          | 1stDayofPresentMonth     |
      | enrollmentEndDate            | highDate                 |
      | txnStatus                    | Accepted                 |
      | programCode                  | R                        |
      | subProgramTypeCode           | HoosierHealthwise        |
      | eligibilityStatusCode        | M                        |
      | categoryCode                 | 10                       |
      | genericFieldText1            | null                     |
      | coverageCode                 | cc01                     |
      | planCode                     | 400752220                |
      | planId                       | 104                      |
      | serviceRegionCode            | Statewide                |
      | anniversaryDate              | anniversaryDate          |
      | channel                      | SYSTEM_INTEGRATION       |
      | fileSource                   | RECIPIENT                |
    And I run create Eligibility and Enrollment API
    #***********************************************************
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 33459-02 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33459-02.consumerId      |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | enrollmentRecordId           | 21                       |
      | isEnrollmentProviderRequired | yes                      |
      | providerNpi                  | 123456789                |
      | providerStartDate            | 1stDayofPresentMonth     |
      | providerEndDate              | 90DayFromFirstDayOfMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | highDate                 |
      | enrollmentStartDate          | 1stDayofPresentMonth     |
      | enrollmentEndDate            | highDate                 |
      | txnStatus                    | Accepted                 |
      | programCode                  | R                        |
      | subProgramTypeCode           | HoosierHealthwise        |
      | eligibilityStatusCode        | M                        |
      | categoryCode                 | 10                       |
      | genericFieldText1            | null                     |
      | coverageCode                 | cc01                     |
      | planCode                     | 400752220                |
      | planId                       | 104                      |
      | serviceRegionCode            | Statewide                |
      | anniversaryDate              | anniversaryDate          |
      | channel                      | SYSTEM_INTEGRATION       |
      | fileSource                   | RECIPIENT                |
    And I run create Eligibility and Enrollment API
    #***********************************************************
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 33459-03 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33459-03.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | enrollmentRecordId           | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | null                 |
      | coverageCode                 | cc01                 |
      | planCode                     | 400752220            |
      | planId                       | 104                  |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | fileSource                   | RECIPIENT            |
    And I run create Eligibility and Enrollment API
    #***********************************************************
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "33459-01.firstName" and Last Name as "33459-01.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "33459-01.firstName" and last name "33459-01.lastName" with data
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HHW-Mandatory             |
      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18         |
      | CURRENT ELIGIBILITY.RCP                 | No                        |
      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                    |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayofPresentMonthUIver |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver             |
      #*********
      | CURRENT ENROLLMENT.PLAN NAME            | ANTHEM                    |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Accepted                  |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration        |
      | CURRENT ENROLLMENT.PCP NAME             | HOUSTON PAUL              |
      | CURRENT ENROLLMENT.START DATE           | 1stDayofPresentMonthUIver |
      | CURRENT ENROLLMENT.END DATE             | highDateUIver             |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | hidden                    |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | hidden                    |
    Then I verify program & benefit info page for consumer first name "33459-02.firstName" and last name "33459-02.lastName" with data
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HHW-Mandatory             |
      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18         |
      | CURRENT ELIGIBILITY.RCP                 | No                        |
      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                    |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayofPresentMonthUIver |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver             |
      #*********
      | CURRENT ENROLLMENT.PLAN NAME            | ANTHEM                    |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Accepted                  |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration        |
      | CURRENT ENROLLMENT.PCP NAME             | 123456789                 |
      | CURRENT ENROLLMENT.START DATE           | 1stDayofPresentMonthUIver |
      | CURRENT ENROLLMENT.END DATE             | highDateUIver             |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | hidden                    |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | hidden                    |
    Then I verify program & benefit info page for consumer first name "33459-03.firstName" and last name "33459-03.lastName" with data
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HHW-Mandatory             |
      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18         |
      | CURRENT ELIGIBILITY.RCP                 | No                        |
      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                    |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayofPresentMonthUIver |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver             |
      #*********
      | CURRENT ENROLLMENT.PLAN NAME            | ANTHEM                    |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Accepted                  |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration        |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                     |
      | CURRENT ENROLLMENT.START DATE           | 1stDayofPresentMonthUIver |
      | CURRENT ENROLLMENT.END DATE             | highDateUIver             |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | hidden                    |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | hidden                    |


  @CP-33459 @CP-33459-01 @CP-33459-02 @CP-33459-03 @ui-ee-in @crm-regression @IN-EB-UI-Regression @kursat
  Scenario: IN-EB Program & Benefits Screen: Update for "PCP Name" field on Enrollment Segment - HCC
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 33459-04 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33459-04.consumerId      |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | enrollmentRecordId           | 21                       |
      | isEnrollmentProviderRequired | yes                      |
      | providerNpi                  | 100065690A               |
      | providerStartDate            | 1stDayofPresentMonth     |
      | providerEndDate              | 90DayFromFirstDayOfMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | highDate                 |
      | enrollmentStartDate          | 1stDayofPresentMonth     |
      | enrollmentEndDate            | highDate                 |
      | txnStatus                    | Accepted                 |
      | programCode                  | A                        |
      | subProgramTypeCode           | HoosierCareConnect       |
      | eligibilityStatusCode        | M                        |
      | categoryCode                 | 10                       |
      | genericFieldText1            | null                     |
      | coverageCode                 | cc01                     |
      | planCode                     | 499254630                |
      | planId                       | 104                      |
      | serviceRegionCode            | Statewide                |
      | anniversaryDate              | anniversaryDate          |
      | channel                      | SYSTEM_INTEGRATION       |
      | fileSource                   | RECIPIENT                |
    And I run create Eligibility and Enrollment API
    #***********************************************************
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 33459-05 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33459-05.consumerId      |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | enrollmentRecordId           | 21                       |
      | isEnrollmentProviderRequired | yes                      |
      | providerNpi                  | 123456789                |
      | providerStartDate            | 1stDayofPresentMonth     |
      | providerEndDate              | 90DayFromFirstDayOfMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | highDate                 |
      | enrollmentStartDate          | 1stDayofPresentMonth     |
      | enrollmentEndDate            | highDate                 |
      | txnStatus                    | Accepted                 |
      | programCode                  | A                        |
      | subProgramTypeCode           | HoosierCareConnect       |
      | eligibilityStatusCode        | M                        |
      | categoryCode                 | 10                       |
      | genericFieldText1            | null                     |
      | coverageCode                 | cc01                     |
      | planCode                     | 499254630                |
      | planId                       | 104                      |
      | serviceRegionCode            | Statewide                |
      | anniversaryDate              | anniversaryDate          |
      | channel                      | SYSTEM_INTEGRATION       |
      | fileSource                   | RECIPIENT                |
    And I run create Eligibility and Enrollment API
    #***********************************************************
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 33459-06 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33459-06.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | enrollmentRecordId           | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | null                 |
      | coverageCode                 | cc01                 |
      | planCode                     | 499254630            |
      | planId                       | 104                  |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | fileSource                   | RECIPIENT            |
    And I run create Eligibility and Enrollment API
    #***********************************************************
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "33459-04.firstName" and Last Name as "33459-04.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "33459-04.firstName" and last name "33459-04.lastName" with data
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HCC-Mandatory             |
      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18         |
      | CURRENT ELIGIBILITY.RCP                 | No                        |
      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                    |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayofPresentMonthUIver |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver             |
      #*********
      | CURRENT ENROLLMENT.PLAN NAME            | ANTHEM HCC                |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Accepted                  |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration        |
      | CURRENT ENROLLMENT.PCP NAME             | FISCH JONATHAN          |
      | CURRENT ENROLLMENT.START DATE           | 1stDayofPresentMonthUIver |
      | CURRENT ENROLLMENT.END DATE             | highDateUIver             |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | hidden                    |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | hidden                    |
    Then I verify program & benefit info page for consumer first name "33459-05.firstName" and last name "33459-05.lastName" with data
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HCC-Mandatory             |
      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18         |
      | CURRENT ELIGIBILITY.RCP                 | No                        |
      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                    |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayofPresentMonthUIver |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver             |
      #*********
      | CURRENT ENROLLMENT.PLAN NAME            | ANTHEM HCC                |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Accepted                  |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration        |
      | CURRENT ENROLLMENT.PCP NAME             | 123456789                 |
      | CURRENT ENROLLMENT.START DATE           | 1stDayofPresentMonthUIver |
      | CURRENT ENROLLMENT.END DATE             | highDateUIver             |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | hidden                    |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | hidden                    |
    Then I verify program & benefit info page for consumer first name "33459-06.firstName" and last name "33459-06.lastName" with data
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HCC-Mandatory             |
      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18         |
      | CURRENT ELIGIBILITY.RCP                 | No                        |
      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                    |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayofPresentMonthUIver |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver             |
      #*********
      | CURRENT ENROLLMENT.PLAN NAME            | ANTHEM HCC                |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Accepted                  |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration        |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                     |
      | CURRENT ENROLLMENT.START DATE           | 1stDayofPresentMonthUIver |
      | CURRENT ENROLLMENT.END DATE             | highDateUIver             |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | hidden                    |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | hidden                    |


  @CP-33459 @CP-33459-01 @CP-33459-02 @CP-33459-03 @ui-ee-in @crm-regression @IN-EB-UI-Regression @kursat
  Scenario: IN-EB Program & Benefits Screen: Update for "PCP Name" field on Enrollment Segment - HIP
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 33459-07 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33459-07.consumerId      |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | enrollmentRecordId           | 21                       |
      | isEnrollmentProviderRequired | yes                      |
      | providerNpi                  | 100048440A               |
      | providerStartDate            | 1stDayofPresentMonth     |
      | providerEndDate              | 90DayFromFirstDayOfMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | highDate                 |
      | enrollmentStartDate          | 1stDayofPresentMonth     |
      | enrollmentEndDate            | highDate                 |
      | txnStatus                    | Accepted                 |
      | programCode                  | H                        |
      | subProgramTypeCode           | HealthyIndianaPlan       |
      | eligibilityStatusCode        | M                        |
      | categoryCode                 | 10                       |
      | genericFieldText1            | null                     |
      | coverageCode                 | cc01                     |
      | planCode                     | 455701400                |
      | planId                       | 104                      |
      | serviceRegionCode            | Statewide                |
      | anniversaryDate              | anniversaryDate          |
      | channel                      | SYSTEM_INTEGRATION       |
      | fileSource                   | RECIPIENT                |
    And I run create Eligibility and Enrollment API
    #***********************************************************
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 33459-08 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33459-08.consumerId      |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | enrollmentRecordId           | 21                       |
      | isEnrollmentProviderRequired | yes                      |
      | providerNpi                  | 123456789                |
      | providerStartDate            | 1stDayofPresentMonth     |
      | providerEndDate              | 90DayFromFirstDayOfMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | highDate                 |
      | enrollmentStartDate          | 1stDayofPresentMonth     |
      | enrollmentEndDate            | highDate                 |
      | txnStatus                    | Accepted                 |
      | programCode                  | H                        |
      | subProgramTypeCode           | HealthyIndianaPlan       |
      | eligibilityStatusCode        | M                        |
      | categoryCode                 | 10                       |
      | genericFieldText1            | null                     |
      | coverageCode                 | cc01                     |
      | planCode                     | 455701400                |
      | planId                       | 104                      |
      | serviceRegionCode            | Statewide                |
      | anniversaryDate              | anniversaryDate          |
      | channel                      | SYSTEM_INTEGRATION       |
      | fileSource                   | RECIPIENT                |
    And I run create Eligibility and Enrollment API
    #***********************************************************
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 33459-09 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33459-09.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | enrollmentRecordId           | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | null                 |
      | coverageCode                 | cc01                 |
      | planCode                     | 455701400            |
      | planId                       | 104                  |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | fileSource                   | RECIPIENT            |
    And I run create Eligibility and Enrollment API
    #***********************************************************
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "33459-07.firstName" and Last Name as "33459-07.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "33459-07.firstName" and last name "33459-07.lastName" with data
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HIP-Mandatory             |
#      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18         |
#      | CURRENT ELIGIBILITY.RCP                 | No                        |
#      | CURRENT ELIGIBILITY.HIP STATUS          | -- --                     |
#      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                    |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayofPresentMonthUIver |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver             |
      #*********
      | CURRENT ENROLLMENT.PLAN NAME            | ANTHEM HIP                |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Accepted                  |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration        |
      | CURRENT ENROLLMENT.PCP NAME             | FERRARA THOMAS            |
      | CURRENT ENROLLMENT.START DATE           | 1stDayofPresentMonthUIver |
      | CURRENT ENROLLMENT.END DATE             | highDateUIver             |
#      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | hidden                    |
#      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | hidden                    |
    Then I verify program & benefit info page for consumer first name "33459-08.firstName" and last name "33459-08.lastName" with data
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HIP-Mandatory             |
#      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18         |
#      | CURRENT ELIGIBILITY.RCP                 | No                        |
#      | CURRENT ELIGIBILITY.HIP STATUS          | -- --                     |
#      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                    |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayofPresentMonthUIver |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver             |
      #*********
      | CURRENT ENROLLMENT.PLAN NAME            | ANTHEM HIP                |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Accepted                  |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration        |
      | CURRENT ENROLLMENT.PCP NAME             | 123456789                 |
      | CURRENT ENROLLMENT.START DATE           | 1stDayofPresentMonthUIver |
      | CURRENT ENROLLMENT.END DATE             | highDateUIver             |
#      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | hidden                    |
#      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | hidden                    |
    Then I verify program & benefit info page for consumer first name "33459-09.firstName" and last name "33459-09.lastName" with data
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HIP-Mandatory             |
#      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18         |
#      | CURRENT ELIGIBILITY.RCP                 | No                        |
#      | CURRENT ELIGIBILITY.HIP STATUS          | -- --                     |
#      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                    |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayofPresentMonthUIver |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver             |
      #*********
      | CURRENT ENROLLMENT.PLAN NAME            | ANTHEM HIP                |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Accepted                  |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration        |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                     |
      | CURRENT ENROLLMENT.START DATE           | 1stDayofPresentMonthUIver |
      | CURRENT ENROLLMENT.END DATE             | highDateUIver             |
#      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | hidden                    |
#      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | hidden                    |