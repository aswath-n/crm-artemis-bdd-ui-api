Feature: UI Edit and Disregard New Enrollment Feature for In-EB


  @CP-25951 @CP-34087 @crm-regression @ui-ee-in @IN-EB-UI-Regression @kursat
  Scenario: IN-EB Edit New Enrollment for all Programs, Subprograms, and Populations
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 25951-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 25951-1.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 21                 |
      | eligibilityRecordId          | 21                 |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | yesterday          |
      | eligibilityEndDate           | highDate           |
      | txnStatus                    | Accepted           |
      | programCode                  | R                  |
      | subProgramTypeCode           | HoosierHealthwise  |
      | eligibilityStatusCode        | M                  |
      | categoryCode                 | 10                 |
      | coverageCode                 | cc01               |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 25951-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 25951-2.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 21                 |
      | eligibilityRecordId          | 21                 |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | yesterday          |
      | eligibilityEndDate           | highDate           |
      | txnStatus                    | Accepted           |
      | programCode                  | R                  |
      | subProgramTypeCode           | HoosierHealthwise  |
      | eligibilityStatusCode        | M                  |
      | categoryCode                 | 10                 |
      | coverageCode                 | cc01               |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "25951-1.firstName" and Last Name as "25951-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "ENROLL" Button for a consumer first name "25951-1.firstName" and last name "25951-1.lastName"
    Then I verify I am still on "Enrollment Update" Page
    When I click Add Case Members Button
    When I click Add Case Members with First Name as "25951-2.firstName" and Last Name as "25951-2.lastName"
    And I select A plan from Available Plans
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "25951-1.firstName" and last name "25951-1.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT         | -- --             |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | -- --             |
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | -- --             |
      | COUNTDOWN.ICON HOVER                    | -- --             |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HHW-Mandatory     |
      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18 |
      | CURRENT ELIGIBILITY.RCP                 | No                |
      | CURRENT ELIGIBILITY.START DATE          | yesterdayUIver    |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver     |
      | CURRENT ELIGIBILITY.PCP SELECT BUTTON   | hidden            |
    # 1.0  Display EDIT Button NEW ENROLLMENT Based on Role AND Change Window Countdown:   IN-EB
      | FUTURE ENROLLMENT.EDIT BUTTON           | is displayed      |
    # 3.0 Click the EDIT Button:   IN-EB
    And I click "EDIT" Button for a consumer first name "25951-1.firstName" and last name "25951-1.lastName"
    Then I verify I am still on "Enrollment Edit" Page
    # 2.0 DO NOT Display “Plan Selection Reason” (drop-down) During NEW ENROLLMENT Change Window:   IN-EB
    Then I verify plan selection reason is not displayed
    # 4.0 Edit One Consumer’s Plan Selection at a Time:   IN-EB
    Then I verify "Add Case Members button" is not displayed
    # 5.0 Select Plan Using Existing Logic to Identify Available Plans:   IN-EB
    # 6.0 Only Edit Plan:   IN-EB
    When I click on Remove Plan Option
   # There is a defect CP-34087 in plans (ADVANTAGE HEALTH SOLUTIONS INC, MDWISE CS, OPT IN DECLINED should not be displayed). Commented out until defect is fixed

    Then I verify list of all available plans on Enrollment Update Page with data
      | ANTHEM                  |
      | CARESOURCE              |
      | MANAGED HEALTH SERVICES |
      | MDWISE HH               |
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | PLAN TYPE  | Medical                           |
      | START DATE | planChangeCutoffStartDateHCCUIver |
      | END DATE   | highDateUIver                     |
    When I select A plan from Available Plans
    # 8.0 Cancel out of the Edit process:   IN-EB
    When I navigate away by clicking on back arrow on Enrollment Update Page
    When I click on continue for Disregard Enrollment
    Then I verify I am still on "CASE BENEFICIARY INFORMATION" Page
    And I click "EDIT" Button for a consumer first name "25951-1.firstName" and last name "25951-1.lastName"
    And I click on Remove Plan Option
    When I select A plan from Available Plans
    # 7.0 Submit Edited Plan Selection:   IN-EB
    And I click submit button on enrollment update
    # 7.1 Save Edited Selection:   IN-EB
    # 9.0 Indicate Only the Latest Selection for Outbound Basket:   IN-EB
    Then I verify program & benefit info page for consumer first name "25951-1.firstName" and last name "25951-1.lastName" with data
      | FUTURE ENROLLMENT.PLAN NAME        | selectedPlanName        |
      | FUTURE ENROLLMENT.SELECTION STATUS | Selection Made          |
      | FUTURE ENROLLMENT.START DATE       | cutoffStartDateHHWUIver |
      | FUTURE ENROLLMENT.END DATE         | highDateUIver           |
      | FUTURE ENROLLMENT.EDIT BUTTON      | displayed               |
      | FUTURE ENROLLMENT.DISREGARD BUTTON | displayed               |
    And I initiated get enrollment by consumer id "25951-1.consumerId"
    When I run get enrollment api
    Then I verify latest enrollment by consumer id "25951-1.consumerId" with data
      | medicalPlanEndDate   | highDate           |
      | planEndDateReason    | null               |
      | updatedBy            | 7450               |
      | updatedOn            | current            |
      | txnStatus            | SELECTION_MADE     |
      | medicalPlanBeginDate | cutoffStartDateHHW |
      | enrollmentType       | MEDICAL            |
      | selectionReason      | 02                 |
      | channel              | PHONE              |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | 25951-1.consumerId      |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    # 10.0 Create NEW ENROLLMENT EDIT Business Event:   IN-EB
    Then I will verify business events are generated with data
      # CP-23955 1.0 Free Change Business Event Required Fields
      | eventName           | ENROLLMENT_EDIT                  |
      | channel             | PHONE                            |
      | createdBy           | 7450                             |
      | processDate         | current                          |
      | externalCaseId      | 25951-1.caseIdentificationNumber |
      | externalConsumerId  | 25951-1.externalConsumerId       |
      | consumerId          | 25951-1.consumerId               |
      | consumerName        | 25951-1                          |
      | enrollmentStartDate | cutoffStartDateHHW               |
      | enrollmentEndDate   | highDate                         |
      | planSelectionReason | 02                               |
      | planChangeReason    | null                             |
      | rejectionReason     | null                             |
      | planCode            | getFromUISelected                |
      | enrollmentType      | MEDICAL                          |
      | selectionStatus     | SELECTION_MADE                   |
      | anniversaryDate     | null                             |
      | pcpNpi              | null                             |
      | pcpName             | null                             |


  @CP-29964 @ui-ee-in @crm-regression @IN-EB-UI-Regression @kursat
  Scenario: IN-EB Disregard New Enrollment for all Programs, Subprograms, and Populations
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29964-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29964-1.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 21                 |
      | eligibilityRecordId          | 21                 |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | yesterday          |
      | eligibilityEndDate           | highDate           |
      | txnStatus                    | Accepted           |
      | programCode                  | R                  |
      | subProgramTypeCode           | HoosierHealthwise  |
      | eligibilityStatusCode        | M                  |
      | categoryCode                 | 10                 |
      | coverageCode                 | cc01               |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29964-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29964-2.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 21                 |
      | eligibilityRecordId          | 21                 |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | yesterday          |
      | eligibilityEndDate           | highDate           |
      | txnStatus                    | Accepted           |
      | programCode                  | R                  |
      | subProgramTypeCode           | HoosierHealthwise  |
      | eligibilityStatusCode        | M                  |
      | categoryCode                 | 10                 |
      | coverageCode                 | cc01               |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Tester 7" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "29964-1.firstName" and Last Name as "29964-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "ENROLL" Button for a consumer first name "29964-1.firstName" and last name "29964-1.lastName"
    Then I verify I am still on "Enrollment Update" Page
    When I click Add Case Members Button
    When I click Add Case Members with First Name as "29964-2.firstName" and Last Name as "29964-2.lastName"
    And I select A plan from Available Plans
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "29964-1.firstName" and last name "29964-1.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT     | ENROLL                                   |
      | CALENDAR ICON HOVER.IMPORTANT DATES | yesterdayUIver - 14DaysFromYesterdaUIver |
      | COUNTDOWN.NUMBER OF DAYS UNTIL      | 14DaysFromYesterdaUIver                  |
      | COUNTDOWN.ICON HOVER                | Calendar days left to enroll in a plan   |
      | FUTURE ENROLLMENT.PLAN NAME         | selectedPlanName                         |
      | FUTURE ENROLLMENT.SELECTION STATUS  | Selection Made                           |
      | FUTURE ENROLLMENT.CHANNEL           | Phone                                    |
      | FUTURE ENROLLMENT.PCP NAME          | -- --                                    |
      | FUTURE ENROLLMENT.START DATE        | cutoffStartDateHHWUIver                  |
      | FUTURE ENROLLMENT.END DATE          | highDateUIver                            |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON | hidden                                   |
      | FUTURE ENROLLMENT.EDIT BUTTON       | is displayed                             |
   # 1.0  Display DISREGARD Button for PLAN CHANGE Based on Role AND Change Window Countdown:   IN-EB
      | FUTURE ENROLLMENT.DISREGARD BUTTON  | is displayed                             |
    # 2.0 Disregard One Consumer at a Time
    And I click "DISREGARD" Button for a consumer first name "29964-1.firstName" and last name "29964-1.lastName"
    # 3.0 Disregard Warning Message
    Then I verify Warning message is displayed "Are you sure you want to disregard the enrollment selection for this consumer?"
    And I click on continue for Disregard Enrollment
    # consumer 29964-2 is not affected by disregard action, We can disregard only one consumer at a time
    Then I verify program & benefit info page for consumer first name "29964-2.firstName" and last name "29964-2.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT     | ENROLL                                   |
      | CALENDAR ICON HOVER.IMPORTANT DATES | yesterdayUIver - 14DaysFromYesterdaUIver |
      | COUNTDOWN.NUMBER OF DAYS UNTIL      | 14DaysFromYesterdaUIver                  |
      | COUNTDOWN.ICON HOVER                | Calendar days left to enroll in a plan   |
      | FUTURE ENROLLMENT.PLAN NAME         | selectedPlanName                         |
      | FUTURE ENROLLMENT.SELECTION STATUS  | Selection Made                           |
      | FUTURE ENROLLMENT.CHANNEL           | Phone                                    |
      | FUTURE ENROLLMENT.PCP NAME          | -- --                                    |
      | FUTURE ENROLLMENT.START DATE        | selectedPlanStartDate                    |
      | FUTURE ENROLLMENT.END DATE          | highDateUIver                            |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON | hidden                                   |
      | FUTURE ENROLLMENT.EDIT BUTTON       | is displayed                             |
      | FUTURE ENROLLMENT.DISREGARD BUTTON  | is displayed                             |
    Then I verify program & benefit info page for consumer first name "29964-1.firstName" and last name "29964-1.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT         | ENROLL                                   |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | yesterdayUIver - 14DaysFromYesterdaUIver |
     #Removed comment out below line which failed due to defect CP-33274 before
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | 14DaysFromYesterdaUIver                  |
      | COUNTDOWN.ICON HOVER                    | Calendar days left to enroll in a plan   |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HHW-Mandatory                            |
      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18                        |
      | CURRENT ELIGIBILITY.RCP                 | No                                       |
      | CURRENT ELIGIBILITY.START DATE          | yesterdayUIver                           |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver                            |
      | CURRENT ELIGIBILITY.PCP SELECT BUTTON   | hidden                                   |
      # 5.0 Set New Enrollment Actions back to Required
      | CURRENT ELIGIBILITY.ENROLL BUTTON       | is displayed                             |
      # 4.0 Disregard the Consumer’s New Enrollment Selection
      # 6.0 Remove the Outbound Indicator
      | FUTURE ENROLLMENT.HIDDEN                |[blank]|
      # 7.0 Create DISREGARDED New Enrollment Business Event
    Then I will verify business events are generated with data
    #required fields
      | eventName           | DISREGARDED_NEW_ENROLLMENT       |
      | channel             | PHONE                            |
      | createdBy           | 8234                             |
      | processDate         | current                          |
      | externalCaseId      | 29964-1.caseIdentificationNumber |
      | externalConsumerId  | 29964-1.externalConsumerId       |
      | consumerId          | 29964-1.consumerId               |
      | consumerName        | 29964-1                          |
      #optional fields
      | enrollmentStartDate | cutoffStartDateHHW               |
      | enrollmentEndDate   | highDate                         |
      | planSelectionReason | 02                               |
      | planChangeReason    | null                             |
      | rejectionReason     | null                             |
      | planCode            | getFromUISelected                |
      | enrollmentType      | MEDICAL                          |
      | selectionStatus     | DISREGARDED                      |
      | anniversaryDate     | null                             |
      | pcpNpi              | null                             |
      | pcpName             | null                             |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | 29964-1.consumerId      |
      | consumerFound | true                    |
      | DPBI          |[blank]|


  @CP-29964 @ui-ee @crm-regression @kursat
  Scenario: BLCRM Disregard New Enrollment for all Programs, Subprograms, and Populations
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "NEWBORN" with data
      | saveConsumerInfo | 29964-3 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29964-3.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                | facilityInfo       |
      | isEnrollemntRequired         | no                 |
      | recordId                     | 3                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          | 1stDayofNextMonth  |
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | txnStatus                    | Accepted           |
      | programCode                  | M                  |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Then I send API call with name "OU" for update Enrollment information
      | [0].consumerId         | 29964-3.consumerId |
      | [0].planId             | 145                |
      | [0].planCode           | 84                 |
      | [0].startDate          | fdnxtmth::         |
      | [0].programTypeCode    | H                  |
      | [0].subProgramTypeCode | MEDICAIDMCHB       |
      | [0].anniversaryDate    | anniversaryDate::  |
    Given I created a consumer with population type "NEWBORN" with data
      | saveConsumerInfo | 29964-4 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29964-4.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                | facilityInfo       |
      | isEnrollemntRequired         | no                 |
      | recordId                     | 3                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          | 1stDayofNextMonth  |
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | txnStatus                    | Accepted           |
      | programCode                  | M                  |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Then I send API call with name "OU" for update Enrollment information
      | [0].consumerId         | 29964-4.consumerId |
      | [0].planId             | 145                |
      | [0].planCode           | 84                 |
      | [0].startDate          | fdnxtmth::         |
      | [0].programTypeCode    | H                  |
      | [0].subProgramTypeCode | MEDICAIDMCHB       |
      | [0].anniversaryDate    | anniversaryDate::  |
    And Wait for 5 seconds
    Given I logged into CRM and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "29964-3.firstName" and Last Name as "29964-3.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "29964-3.firstName" and last name "29964-3.lastName" with data
#      | CALENDAR ICON HOVER.ACTION TEXT     | ENROLL                                 |
#      | CALENDAR ICON HOVER.IMPORTANT DATES | currentUIver - 5DaysFromNowUIver       |
#      | COUNTDOWN.NUMBER OF DAYS UNTIL      | 5DaysFromNowUIver                      |
#      | COUNTDOWN.ICON HOVER                | Calendar days left to enroll in a plan |
      | FUTURE ENROLLMENT.PLAN NAME         | AMERIGROUP COMMUNITY CARE |
      | FUTURE ENROLLMENT.SELECTION STATUS  | Selection Made            |
      | FUTURE ENROLLMENT.CHANNEL           | Phone                     |
      | FUTURE ENROLLMENT.PCP NAME          | -- --                     |
      | FUTURE ENROLLMENT.START DATE        | firstdayofNextMonth       |
      | FUTURE ENROLLMENT.END DATE          | -- --                     |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON | hidden                    |
      | FUTURE ENROLLMENT.EDIT BUTTON       | is displayed              |
   # 1.0  Display DISREGARD Button for PLAN CHANGE Based on Role AND Change Window Countdown:   IN-EB
      | FUTURE ENROLLMENT.DISREGARD BUTTON  | is displayed              |
    # 2.0 Disregard One Consumer at a Time
    And I click "DISREGARD" Button for a consumer first name "29964-3.firstName" and last name "29964-3.lastName"
    # 3.0 Disregard Warning Message
    Then I verify Warning message is displayed "Are you sure you want to disregard the enrollment selection for this consumer?"
    And I click on continue for Disregard Enrollment
    Then I verify program & benefit info page for consumer first name "29964-4.firstName" and last name "29964-4.lastName" with data
#      | CALENDAR ICON HOVER.ACTION TEXT     | ENROLL                                 |
#      | CALENDAR ICON HOVER.IMPORTANT DATES | currentUIver - 5DaysFromNowUIver       |
#      | COUNTDOWN.NUMBER OF DAYS UNTIL      | 5DaysFromNowUIver                      |
#      | COUNTDOWN.ICON HOVER                | Calendar days left to enroll in a plan |
      | FUTURE ENROLLMENT.PLAN NAME         | AMERIGROUP COMMUNITY CARE |
      | FUTURE ENROLLMENT.SELECTION STATUS  | Selection Made            |
      | FUTURE ENROLLMENT.CHANNEL           | Phone                     |
      | FUTURE ENROLLMENT.PCP NAME          | -- --                     |
      | FUTURE ENROLLMENT.START DATE        | firstdayofNextMonth       |
      | FUTURE ENROLLMENT.END DATE          | -- --                     |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON | hidden                    |
      | FUTURE ENROLLMENT.EDIT BUTTON       | is displayed              |
    Then I verify program & benefit info page for consumer first name "29964-3.firstName" and last name "29964-3.lastName" with data
#      | CALENDAR ICON HOVER.ACTION TEXT         | ENROLL                                 |
#      | CALENDAR ICON HOVER.IMPORTANT DATES     | currentUIver - 5DaysFromNowUIver       |
#      | COUNTDOWN.NUMBER OF DAYS UNTIL          | 5DaysFromNowUIver                      |
#      | COUNTDOWN.ICON HOVER                    | Calendar days left to enroll in a plan |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | Medicaid-Newborn    |
      | CURRENT ELIGIBILITY.SERVICE REGION      | East                |
#      | CURRENT ELIGIBILITY.AID CATEGORY        | -- --                                  |
#      | CURRENT ELIGIBILITY.RCP                 | No                                     |
      | CURRENT ELIGIBILITY.START DATE          | firstdayofLastMonth |
      | CURRENT ELIGIBILITY.END DATE            | -- --               |
      | CURRENT ELIGIBILITY.PCP SELECT BUTTON   | is displayed        |
      # 5.0 Set New Enrollment Actions back to Required
      | CURRENT ELIGIBILITY.ENROLL BUTTON       | is displayed        |
      # 4.0 Disregard the Consumer’s New Enrollment Selection
      # 6.0 Remove the Outbound Indicator
      | FUTURE ENROLLMENT.HIDDEN                |[blank]|
      # 7.0 Create DISREGARDED New Enrollment Business Event
    Then I will verify business events are generated with data
    # required fields
      | eventName           | DISREGARDED_NEW_ENROLLMENT       |
      | channel             | PHONE                            |
      | createdBy           | 2824                             |
      | processDate         | current                          |
      | externalCaseId      | 29964-3.caseIdentificationNumber |
      | externalConsumerId  | 29964-3.externalConsumerId       |
      | consumerId          | 29964-3.consumerId               |
      | consumerName        | 29964-3                          |
    #optional fields
      | enrollmentStartDate | 1stDayofNextMonth                |
      | enrollmentEndDate   | null                             |
      | planSelectionReason | null                             |
      | planChangeReason    | null                             |
      | rejectionReason     | null                             |
      | planCode            | 84                               |
      | enrollmentType      | MEDICAL                          |
      | selectionStatus     | DISREGARDED                      |
      | anniversaryDate     | anniversaryDate                  |
      | pcpNpi              | null                             |
      | pcpName             | null                             |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | 29964-3.consumerId      |
      | consumerFound | true                    |
      | DPBI          |[blank]|


  @CP-30492 @CP-30850 @CP-34087 @ui-ee-in @crm-regression @IN-EB-UI-Regression @sobir
  Scenario: CP-30492 IN-EB Override New Enrollment for all Programs, Subprograms, and Populations for HHW
  CP-30850 IN-EB: Override New Enrollment for all Programs, Subprograms, and Populations - Business Events
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30492-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30492-1.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 21                 |
      | eligibilityRecordId          | 21                 |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | eligibilityEndDate           | highDate           |
      | txnStatus                    | Accepted           |
      | programCode                  | R                  |
      | subProgramTypeCode           | HoosierHealthwise  |
      | eligibilityStatusCode        | M                  |
      | categoryCode                 | 10                 |
      | coverageCode                 | cc01               |
    And User provide other enrollment segments details
      | recordId          | 21                 |
      | consumerId        | 30492-1.consumerId |
      | startDate         | 1stDayofLastMonth  |
      | endDate           | lastDayofLastMonth |
      | genericFieldText1 | O                  |
      | genericFieldDate1 | openEnrollmentDay  |
      | segmentTypeCode   | OPEN_ENROLLMENT    |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30492-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30492-2.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 21                 |
      | eligibilityRecordId          | 21                 |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | eligibilityEndDate           | highDate           |
      | txnStatus                    | Accepted           |
      | programCode                  | R                  |
      | subProgramTypeCode           | HoosierHealthwise  |
      | eligibilityStatusCode        | M                  |
      | categoryCode                 | 10                 |
      | coverageCode                 | cc01               |
    And User provide other enrollment segments details
      | recordId          | 21                 |
      | consumerId        | 30492-2.consumerId |
      | startDate         | 1stDayofLastMonth  |
      | endDate           | lastDayofLastMonth |
      | genericFieldText1 | O                  |
      | genericFieldDate1 | openEnrollmentDay  |
      | segmentTypeCode   | OPEN_ENROLLMENT    |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30492-3 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30492-3.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 21                 |
      | eligibilityRecordId          | 21                 |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | eligibilityEndDate           | highDate           |
      | txnStatus                    | Accepted           |
      | programCode                  | R                  |
      | subProgramTypeCode           | HoosierHealthwise  |
      | eligibilityStatusCode        | M                  |
      | categoryCode                 | 10                 |
      | coverageCode                 | cc01               |
    And User provide other enrollment segments details
      | recordId          | 21                 |
      | consumerId        | 30492-3.consumerId |
      | startDate         | 1stDayofLastMonth  |
      | endDate           | lastDayofLastMonth |
      | genericFieldText1 | O                  |
      | genericFieldDate1 | openEnrollmentDay  |
      | segmentTypeCode   | OPEN_ENROLLMENT    |
    And User provide one more other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 30492-3.consumerId    |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | highDate              |
      | genericFieldText1 | I                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | genericFieldDate2 |[blank]|
      | segmentTypeCode   | LILO                  |
      | genericFieldText2 | 300029354             |
      | genericFieldText5 | ERIC SCHALLET         |
      | genericFieldText3 | Medical               |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30492-4 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30492-4.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 30492-4.consumerId    |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofLastMonth    |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Tester 7" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "30492-1.firstName" and Last Name as "30492-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "30492-1.firstName" and last name "30492-1.lastName" with data
      # 2.0 Display Important Calendar Dates
      | CALENDAR ICON HOVER.ACTION TEXT     | ENROLL                                                |
      | CALENDAR ICON HOVER.IMPORTANT DATES | firstdayofLastMonth - 14DayFrom1stDayofLastMonthUIver |
      | COUNTDOWN.NUMBER OF DAYS UNTIL      | -- --                                                 |
      | COUNTDOWN.ICON HOVER                | -- --                                                 |
      # 1.0  Display ENROLL Button for Supervisor After the NEW ENROLLMENT Change Window HAS Expired OR is Unavailable
      | CURRENT ELIGIBILITY.ENROLL BUTTON   | is displayed                                          |
    And I click "ENROLL" Button for a consumer first name "30492-1.firstName" and last name "30492-1.lastName"
    Then I verify I am still on "ENROLLMENT OVERRIDE" Page
    Then I verify "PCP SELECT BUTTON" is not displayed
    # 3.0 Adding a Case Member to a Consumer New Enrollment Action
    When I click Add Case Members Button
    Then I verify I only see next other consumers in the dropdown list
      | 30492-2 |
    When I click Add Case Members with First Name as "30492-2.firstName" and Last Name as "30492-2.lastName"
    Then I verify  the Add Case Members button is disable
    When I click Remove Button for Case Member First Name as "30492-2.firstName" and Last Name as "30492-2.lastName"
    Then I verivy consumer by First Name as "30492-2.firstName" and Last Name as "30492-2.lastName" is not in plan selection
    When I click Add Case Members Button
    When I click Add Case Members with First Name as "30492-2.firstName" and Last Name as "30492-2.lastName"
    # 4.0 Identify Available Plans for Single and Multiple Consumers
     # There is a defect CP-34087 in plans (ADVANTAGE HEALTH SOLUTIONS INC, MDWISE CS, OPT IN DECLINED should not be displayed). Commented out until defect is fixed
    Then I verify list of all available plans on Enrollment Update Page with data
      | ANTHEM                  |
      | CARESOURCE              |
      | MANAGED HEALTH SERVICES |
      | MDWISE HH               |
    # 5.0 Calculate Plan Start Date and Plan End Date
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | PLAN TYPE  | Medical                 |
      | START DATE | cutoffStartDateHHWUIver |
      | END DATE   | highDateUIver           |
    Then I verify "PCP SELECT BUTTON" is not displayed
    # 6.0 Select Plan
    And I select A plan from Available Plans
    # 7.0 Remove Plan
    And I click on Remove Plan Option
    Then I verify I am still on "ENROLLMENT OVERRIDE" Page
    When I select A plan from Available Plans
    And I click submit button on enrollment update
    # 8.0 Display EDIT Button
    Then I verify program & benefit info page for consumer first name "30492-1.firstName" and last name "30492-1.lastName" with data
      | FUTURE ENROLLMENT.PLAN NAME        | selectedPlanName        |
      | FUTURE ENROLLMENT.SELECTION STATUS | Selection Made          |
      | FUTURE ENROLLMENT.CHANNEL          | Phone                   |
      | FUTURE ENROLLMENT.PCP NAME         | -- --                   |
      | FUTURE ENROLLMENT.START DATE       | cutoffStartDateHHWUIver |
      | FUTURE ENROLLMENT.END DATE         | highDateUIver           |
      | FUTURE ENROLLMENT.EDIT BUTTON      | is displayed            |
      | FUTURE ENROLLMENT.DISREGARD BUTTON | is displayed            |
    # Override New Enrollment for all Programs, Subprograms, and Populations - Business Events
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I save first contactId with name "30492-1a"
    Then I will verify business events are generated with data
    # required fields
      | eventName           | NEW_ENROLLMENT_OVERRIDE          |
      | channel             | PHONE                            |
      | createdBy           | 8234                             |
      | processDate         | current                          |
      | externalCaseId      | 30492-1.caseIdentificationNumber |
      | externalConsumerId  | 30492-1.externalConsumerId       |
      | consumerId          | 30492-1.consumerId               |
      | consumerName        | 30492-1                          |
    # CP-30850 optional fields
      | contactId           | 30492-1a.contactId               |
      | consumerPopulation  | HHW-Mandatory                    |
      | enrollmentStartDate | getFromUISelected                |
      | enrollmentEndDate   | highDate                         |
      | planSelectionReason | 02                               |
      | planChangeReason    | null                             |
      | rejectionReason     | null                             |
      | planCode            | getFromUISelected                |
      | enrollmentType      | MEDICAL                          |
      | selectionStatus     | SELECTION_MADE                   |
      | anniversaryDate     | null                             |
      | pcpNpi              | null                             |
      | pcpName             | null                             |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | 30492-1.consumerId    |
      | consumerFound | true                  |
      | DPBI          |[blank]|
      # 9.0 EDIT Enrollment Start Date
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click future enrollment "EDIT" Button for a consumer first name "30492-1.firstName" and last name "30492-1.lastName"
    Then I verify I am still on "ENROLLMENT OVERRIDE" Page
    And I add 4 days from selected day on calendar
    And I click submit button on enrollment update
    # 10.0 SAVE and VIEW new PLAN and/or edited ENROLLMENT START DATE
    Then I verify program & benefit info page for consumer first name "30492-1.firstName" and last name "30492-1.lastName" with data
      | FUTURE ENROLLMENT.PLAN NAME        | selectedPlanName      |
      | FUTURE ENROLLMENT.SELECTION STATUS | Selection Made        |
      | FUTURE ENROLLMENT.CHANNEL          | Phone                 |
      | FUTURE ENROLLMENT.PCP NAME         | -- --                 |
      | FUTURE ENROLLMENT.START DATE       | selectedPlanStartDate |
      | FUTURE ENROLLMENT.END DATE         | highDateUIver         |
      | FUTURE ENROLLMENT.EDIT BUTTON      | is displayed          |
      | FUTURE ENROLLMENT.DISREGARD BUTTON | is displayed          |
    # 9.0 EDIT Enrollment Start Date
    And I click future enrollment "EDIT" Button for a consumer first name "30492-2.firstName" and last name "30492-2.lastName"
    Then I verify I am still on "ENROLLMENT OVERRIDE" Page
    And I click on Remove Plan Option
    When I select A plan from Available Plans
    And I add 4 days from selected day on calendar
    And I click submit button on enrollment update
    # 10.0 SAVE and VIEW new PLAN and/or edited ENROLLMENT START DATE
    Then I verify program & benefit info page for consumer first name "30492-2.firstName" and last name "30492-2.lastName" with data
      | FUTURE ENROLLMENT.PLAN NAME        | selectedPlanName      |
      | FUTURE ENROLLMENT.SELECTION STATUS | Selection Made        |
      | FUTURE ENROLLMENT.CHANNEL          | Phone                 |
      | FUTURE ENROLLMENT.PCP NAME         | -- --                 |
      | FUTURE ENROLLMENT.START DATE       | selectedPlanStartDate |
      | FUTURE ENROLLMENT.END DATE         | highDateUIver         |
      | FUTURE ENROLLMENT.EDIT BUTTON      | is displayed          |
      | FUTURE ENROLLMENT.DISREGARD BUTTON | is displayed          |
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I save first contactId with name "30492-2a"
    Then I will verify business events are generated with data
    # required fields
      | eventName           | NEW_ENROLLMENT_EDIT_OVERRIDE     |
      | channel             | PHONE                            |
      | createdBy           | 8234                             |
      | processDate         | current                          |
      | externalCaseId      | 30492-2.caseIdentificationNumber |
      | externalConsumerId  | 30492-2.externalConsumerId       |
      | consumerId          | 30492-2.consumerId               |
      | consumerName        | 30492-2                          |
    # CP-30850 optional fields
      | contactId           | 30492-1a.contactId               |
      | enrollmentStartDate | getFromUISelected                |
      | enrollmentEndDate   | highDate                         |
      | planSelectionReason | 02                               |
      | planChangeReason    | null                             |
      | rejectionReason     | null                             |
      | planCode            | getFromUISelected                |
      | enrollmentType      | MEDICAL                          |
      | selectionStatus     | SELECTION_MADE                   |
      | anniversaryDate     | null                             |
      | pcpNpi              | null                             |
      | pcpName             | null                             |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | 30492-2.consumerId      |
      | consumerFound | true                    |
      | DPBI          |[blank]|

  @CP-30492 @CP-30850 @ui-ee-in @crm-regression @IN-EB-UI-Regression @sobir
  Scenario: CP-30492 IN-EB Override New Enrollment for all Programs, Subprograms, and Populations for HCC
  CP-30850 IN-EB: Override New Enrollment for all Programs, Subprograms, and Populations - Business Events
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30492-5 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30492-5.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 30492-5.consumerId    |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofLastMonth    |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30492-6 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30492-6.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 30492-6.consumerId    |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofLastMonth    |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30492-7 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30492-7.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 30492-7.consumerId    |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofLastMonth    |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And User provide one more other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 30492-7.consumerId    |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | highDate              |
      | genericFieldText1 | I                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | genericFieldDate2 |[blank]|
      | segmentTypeCode   | LILO                  |
      | genericFieldText2 | 300029354             |
      | genericFieldText5 | ERIC SCHALLET         |
      | genericFieldText3 | Medical               |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30492-8 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30492-8.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 21                 |
      | eligibilityRecordId          | 21                 |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | eligibilityEndDate           | highDate           |
      | txnStatus                    | Accepted           |
      | programCode                  | R                  |
      | subProgramTypeCode           | HoosierHealthwise  |
      | eligibilityStatusCode        | M                  |
      | categoryCode                 | 10                 |
      | coverageCode                 | cc01               |
    And User provide other enrollment segments details
      | recordId          | 21                 |
      | consumerId        | 30492-8.consumerId |
      | startDate         | 1stDayofLastMonth  |
      | endDate           | lastDayofLastMonth |
      | genericFieldText1 | O                  |
      | genericFieldDate1 | openEnrollmentDay  |
      | segmentTypeCode   | OPEN_ENROLLMENT    |
    And I run create Eligibility and Enrollment API
#   Below step is updated to login with service tester 7 which has a supervisor role with permission to override new enrollment
    Given I logged into CRM with "Service Tester 7" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "30492-5.firstName" and Last Name as "30492-5.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "30492-5.firstName" and last name "30492-5.lastName" with data
      # 2.0 Display Important Calendar Dates
      | CALENDAR ICON HOVER.ACTION TEXT     | ENROLL                                                            |
      | CALENDAR ICON HOVER.IMPORTANT DATES | 1stDayof2MonthsBeforeUIver - 60DaysFrom1stDayof2MonthsBeforeUIver |
      | COUNTDOWN.NUMBER OF DAYS UNTIL      | -- --                                                             |
      | COUNTDOWN.ICON HOVER                | -- --                                                             |
      # 1.0  Display ENROLL Button for Supervisor After the NEW ENROLLMENT Change Window HAS Expired OR is Unavailable
      | CURRENT ELIGIBILITY.ENROLL BUTTON   | is displayed                                                      |
    And I click "ENROLL" Button for a consumer first name "30492-5.firstName" and last name "30492-5.lastName"
    Then I verify I am still on "ENROLLMENT OVERRIDE" Page
    Then I verify "PCP SELECT BUTTON" is not displayed
    # 3.0 Adding a Case Member to a Consumer New Enrollment Action
    When I click Add Case Members Button
    Then I verify I only see next other consumers in the dropdown list
      | 30492-6 |
    When I click Add Case Members with First Name as "30492-6.firstName" and Last Name as "30492-6.lastName"
    Then I verify  the Add Case Members button is disable
    When I click Remove Button for Case Member First Name as "30492-6.firstName" and Last Name as "30492-6.lastName"
    Then I verivy consumer by First Name as "30492-6.firstName" and Last Name as "30492-6.lastName" is not in plan selection
    When I click Add Case Members Button
    When I click Add Case Members with First Name as "30492-6.firstName" and Last Name as "30492-6.lastName"
    # 4.0 Identify Available Plans for Single and Multiple Consumers
    Then I verify list of all available plans on Enrollment Update Page with data
      | ANTHEM HCC                  |
      | MANAGED HEALTH SERVICES HCC |
      | UNITED HEALTHCARE HCC       |
    # 5.0 Calculate Plan Start Date and Plan End Date
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | PLAN TYPE  | Medical                          |
      | START DATE | newEnrollCutoffStartDateHCCUIver |
      | END DATE   | highDateUIver                    |
    Then I verify "PCP SELECT BUTTON" is not displayed
    # 6.0 Select Plan
    And I select A plan from Available Plans
    # 7.0 Remove Plan
    And I click on Remove Plan Option
    Then I verify I am still on "ENROLLMENT OVERRIDE" Page
    When I select A plan from Available Plans
    And I click submit button on enrollment update
    # 8.0 Display EDIT Button
    Then I verify program & benefit info page for consumer first name "30492-5.firstName" and last name "30492-5.lastName" with data
      | FUTURE ENROLLMENT.PLAN NAME        | selectedPlanName                 |
      | FUTURE ENROLLMENT.SELECTION STATUS | Selection Made                   |
      | FUTURE ENROLLMENT.CHANNEL          | Phone                            |
      | FUTURE ENROLLMENT.PCP NAME         | -- --                            |
      | FUTURE ENROLLMENT.START DATE       | newEnrollCutoffStartDateHCCUIver |
      | FUTURE ENROLLMENT.END DATE         | highDateUIver                    |
      | FUTURE ENROLLMENT.EDIT BUTTON      | is displayed                     |
      | FUTURE ENROLLMENT.DISREGARD BUTTON | is displayed                     |
    # Override New Enrollment for all Programs, Subprograms, and Populations - Business Events
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I save first contactId with name "30492-5a"
    Then I will verify business events are generated with data
    # required fields
      | eventName           | NEW_ENROLLMENT_OVERRIDE          |
      | channel             | PHONE                            |
      | createdBy           | 8234                             |
      | processDate         | current                          |
      | externalCaseId      | 30492-5.caseIdentificationNumber |
      | externalConsumerId  | 30492-5.externalConsumerId       |
      | consumerId          | 30492-5.consumerId               |
      | consumerName        | 30492-5                          |
    # CP-30850 optional fields
      | contactId           | 30492-5a.contactId               |
      | consumerPopulation  | HCC-Mandatory                    |
      | enrollmentStartDate | getFromUISelected                |
      | enrollmentEndDate   | highDate                         |
      | planSelectionReason | 02                               |
      | planChangeReason    | null                             |
      | rejectionReason     | null                             |
      | planCode            | getFromUISelected                |
      | enrollmentType      | MEDICAL                          |
      | selectionStatus     | SELECTION_MADE                   |
      | anniversaryDate     | null                             |
      | pcpNpi              | null                             |
      | pcpName             | null                             |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | 30492-5.consumerId    |
      | consumerFound | true                  |
      | DPBI          |[blank]|
      # 9.0 EDIT Enrollment Start Date
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click future enrollment "EDIT" Button for a consumer first name "30492-5.firstName" and last name "30492-5.lastName"
    Then I verify I am still on "ENROLLMENT OVERRIDE" Page
    And I add 4 days from selected day on calendar
    And I click submit button on enrollment update
    # 10.0 SAVE and VIEW new PLAN and/or edited ENROLLMENT START DATE
    Then I verify program & benefit info page for consumer first name "30492-5.firstName" and last name "30492-5.lastName" with data
      | FUTURE ENROLLMENT.PLAN NAME        | selectedPlanName      |
      | FUTURE ENROLLMENT.SELECTION STATUS | Selection Made        |
      | FUTURE ENROLLMENT.CHANNEL          | Phone                 |
      | FUTURE ENROLLMENT.PCP NAME         | -- --                 |
      | FUTURE ENROLLMENT.START DATE       | selectedPlanStartDate |
      | FUTURE ENROLLMENT.END DATE         | highDateUIver         |
      | FUTURE ENROLLMENT.EDIT BUTTON      | is displayed          |
      | FUTURE ENROLLMENT.DISREGARD BUTTON | is displayed          |
    # 9.0 EDIT Enrollment Start Date
    And I click future enrollment "EDIT" Button for a consumer first name "30492-6.firstName" and last name "30492-6.lastName"
    Then I verify I am still on "ENROLLMENT OVERRIDE" Page
    And I click on Remove Plan Option
    When I select A plan from Available Plans
    And I add 4 days from selected day on calendar
    And I click submit button on enrollment update
    # 10.0 SAVE and VIEW new PLAN and/or edited ENROLLMENT START DATE
    Then I verify program & benefit info page for consumer first name "30492-6.firstName" and last name "30492-6.lastName" with data
      | FUTURE ENROLLMENT.PLAN NAME        | selectedPlanName      |
      | FUTURE ENROLLMENT.SELECTION STATUS | Selection Made        |
      | FUTURE ENROLLMENT.CHANNEL          | Phone                 |
      | FUTURE ENROLLMENT.PCP NAME         | -- --                 |
      | FUTURE ENROLLMENT.START DATE       | selectedPlanStartDate |
      | FUTURE ENROLLMENT.END DATE         | highDateUIver         |
      | FUTURE ENROLLMENT.EDIT BUTTON      | is displayed          |
      | FUTURE ENROLLMENT.DISREGARD BUTTON | is displayed          |
    # CP-30850 1.0 Create OVERRIDE NEW ENROLLMENT Business Event IN-EB
    Then I will verify business events are generated with data
    # required fields
      | eventName           | NEW_ENROLLMENT_EDIT_OVERRIDE     |
      | channel             | PHONE                            |
      | createdBy           | 8234                             |
      | processDate         | current                          |
      | externalCaseId      | 30492-6.caseIdentificationNumber |
      | externalConsumerId  | 30492-6.externalConsumerId       |
      | consumerId          | 30492-6.consumerId               |
      | consumerName        | 30492-6                          |
    # CP-30850 optional fields
      | contactId           | 30492-5a.contactId               |
      | enrollmentStartDate | getFromUISelected                |
      | enrollmentEndDate   | highDate                         |
      | planSelectionReason | 02                               |
      | planChangeReason    | null                             |
      | rejectionReason     | null                             |
      | planCode            | getFromUISelected                |
      | enrollmentType      | MEDICAL                          |
      | selectionStatus     | SELECTION_MADE                   |
      | anniversaryDate     | null                             |
      | pcpNpi              | null                             |
      | pcpName             | null                             |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | 30492-6.consumerId      |
      | consumerFound | true                    |
      | DPBI          |[blank]|
