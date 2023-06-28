Feature: UI Enrollment Actions Feature for In-EB

  @CP-23976 @ui-ee-in @crm-regression  @IN-EB-UI-Regression @sobir
  Scenario: IN-EB HIP - Open Enrollment View Consumer on Program and Benefit Info Screen
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23976-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23976-1.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 21                 |
      | eligibilityRecordId          | 21                 |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          | 1stDayofLastMonth  |
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | eligibilityEndDate           | highDate           |
      | txnStatus                    | Accepted           |
      | programCode                  | H                  |
      | subProgramTypeCode           | HealthyIndianaPlan |
      | eligibilityStatusCode        | M                  |
      | categoryCode                 | 10                 |
      | coverageCode                 | cc01               |
      | genericFieldText1            | Eligible           |
    And User provide other eligibility segments details
      | startDate           | 1stDayofLastMonth       |
      | endDate             | highDate                |
      | segmentDetailValue1 | 1                       |
      | segmentDetailValue2 | 6                       |
      | segmentDetailValue3 | 1stDayofPresentMonth    |
      | segmentDetailValue4 | lastDayOfThePresentYear |
      | segmentTypeCode     | OTHER                   |
    And User provide other enrollment segments details
      | recordId          | 21                               |
      | consumerId        | 23976-1.consumerId               |
      | startDate         | 90DaysPriorAnniversaryDueDateHCC |
      | endDate           | anniversaryDueDateHCC            |
      | genericFieldText1 | C                                |
      | genericFieldDate1 | openEnrollmentDay                |
      | segmentTypeCode   | OPEN_ENROLLMENT                  |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23976-1.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | recordId                     | 4                  |
      | enrollmentRecordId           | 4                  |
      | isEnrollmentProviderRequired | no                 |
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | eligibilityEndDate           | highDate           |
      | enrollmentStartDate          | 1stDayofLastMonth  |
      | enrollmentEndDate            | highDate           |
      | txnStatus                    | Accepted           |
      | programCode                  | H                  |
      | subProgramTypeCode           | HealthyIndianaPlan |
      | eligibilityStatusCode        | M                  |
      | planCode                     | 455701400          |
      | planId                       | 33                 |
      | serviceRegionCode            | Statewide          |
      | anniversaryDate              | anniversaryDate    |
      | channel                      | SYSTEM_INTEGRATION |
      | categoryCode                 | 10                 |
      | coverageCode                 | cc01               |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "23976-1.firstName" and Last Name as "23976-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "23976-1.firstName" and last name "23976-1.lastName" with data
      # 1.0 Consumer row information display
      | CALENDAR ICON HOVER.ACTION TEXT       | OPEN ENROLLMENT - WINDOW                           |
      | CALENDAR ICON HOVER.IMPORTANT DATES   | openEnrollmentDayUIver - openEnrollmentDueDayUIver |
#      | COUNTDOWN.NUMBER OF DAYS UNTIL        | openEnrollmentDueDayUIver                             |
      | COUNTDOWN.NUMBER OF DAYS UNTIL        | -- --                                              |
#      | COUNTDOWN.ICON HOVER                  | Calendar days left to change plan for open enrollment |
      | COUNTDOWN.ICON HOVER                  | null                                               |
      # 2.0 Current eligibility segment display
      | CURRENT ELIGIBILITY.HIP STATUS        | Eligible                                           |
      | CURRENT ELIGIBILITY.AID CATEGORY      | Children age 0-18                                  |
      | CURRENT ELIGIBILITY.RCP               | No                                                 |
      | CURRENT ELIGIBILITY.START DATE        | firstdayofLastMonth                                |
      | CURRENT ELIGIBILITY.END DATE          | highDateUIver                                      |
      # 2.1 RCP indicator within the Open Enrollment window
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                                             |
      # 3.0 Current enrollment segment display
      | CURRENT ENROLLMENT.PLAN NAME          | ANTHEM HIP                                         |
      | CURRENT ENROLLMENT.SELECTION STATUS   | Accepted                                           |
      | CURRENT ENROLLMENT.CHANNEL            | System Integration                                 |
      | CURRENT ENROLLMENT.PCP NAME           | -- --                                              |
      | CURRENT ENROLLMENT.START DATE         | firstdayofLastMonth                                |
      | CURRENT ENROLLMENT.END DATE           | highDateUIver                                      |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON  | hidden                                             |

  @CP-23974 @CP-23975 @ui-ee-in @crm-regression @ui-ee-in-smoke @IN-EB-UI-Regression @sobir
  Scenario: CP-23974 IN-EB HIP - Open Enrollment View Selection, Submit Selection, View on Program and Benefit Info Screen
  CP-23975 HIP-Open Enrollment Click Plan Change Button and Review Plans Available
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23974-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23974-1.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 21                 |
      | eligibilityRecordId          | 21                 |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          | 1stDayofLastMonth  |
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | eligibilityEndDate           | highDate           |
      | txnStatus                    | Accepted           |
      | programCode                  | H                  |
      | subProgramTypeCode           | HealthyIndianaPlan |
      | eligibilityStatusCode        | M                  |
      | categoryCode                 | 10                 |
      | coverageCode                 | cc01               |
      | genericFieldText1            | Eligible           |
    And User provide other eligibility segments details
      | startDate           | 1stDayofLastMonth       |
      | endDate             | highDate                |
      | segmentDetailValue1 | 1                       |
      | segmentDetailValue2 | 6                       |
      | segmentDetailValue3 | 1stDayofPresentMonth    |
      | segmentDetailValue4 | lastDayOfThePresentYear |
      | segmentTypeCode     | OTHER                   |
    And User provide other enrollment segments details
      | recordId          | 21                               |
      | consumerId        | 23974-1.consumerId               |
      | startDate         | 90DaysPriorAnniversaryDueDateHCC |
      | endDate           | anniversaryDueDateHCC            |
      | genericFieldText1 | C                                |
      | genericFieldDate1 | openEnrollmentDay                |
      | segmentTypeCode   | OPEN_ENROLLMENT                  |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23974-1.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | recordId                     | 4                  |
      | enrollmentRecordId           | 4                  |
      | isEnrollmentProviderRequired | no                 |
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | eligibilityEndDate           | highDate           |
      | enrollmentStartDate          | 1stDayofLastMonth  |
      | enrollmentEndDate            | highDate           |
      | txnStatus                    | Accepted           |
      | programCode                  | H                  |
      | subProgramTypeCode           | HealthyIndianaPlan |
      | eligibilityStatusCode        | M                  |
      | planCode                     | 455701400          |
      | planId                       | 33                 |
      | serviceRegionCode            | Statewide          |
      | anniversaryDate              | anniversaryDate    |
      | channel                      | SYSTEM_INTEGRATION |
      | categoryCode                 | 10                 |
      | coverageCode                 | cc01               |
    And I run create Eligibility and Enrollment API
    And I update latest consumer actions by consumerId "23974-1.consumerId" with data
      | [0].action               | Available                   |
      | [0].consumerAction       | Plan Change Open Enrollment |
      | [0].planSelectionAllowed | true                        |
      | [0].changeAllowedFrom    | 1stDayofPresentMonth        |
      | [0].changeAllowedUntil   | 90DayFromFirstDayOfMonth    |
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23974-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23974-2.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 21                 |
      | eligibilityRecordId          | 21                 |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          | 1stDayofLastMonth  |
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | eligibilityEndDate           | highDate           |
      | txnStatus                    | Accepted           |
      | programCode                  | H                  |
      | subProgramTypeCode           | HealthyIndianaPlan |
      | eligibilityStatusCode        | M                  |
      | categoryCode                 | 10                 |
      | coverageCode                 | cc01               |
      | genericFieldText1            | Eligible           |
    And User provide other eligibility segments details
      | startDate           | 1stDayofLastMonth       |
      | endDate             | highDate                |
      | segmentDetailValue1 | 1                       |
      | segmentDetailValue2 | 6                       |
      | segmentDetailValue3 | 1stDayofPresentMonth    |
      | segmentDetailValue4 | lastDayOfThePresentYear |
      | segmentTypeCode     | OTHER                   |
    And User provide other enrollment segments details
      | recordId          | 21                               |
      | consumerId        | 23974-2.consumerId               |
      | startDate         | 90DaysPriorAnniversaryDueDateHCC |
      | endDate           | anniversaryDueDateHCC            |
      | genericFieldText1 | C                                |
      | genericFieldDate1 | openEnrollmentDay                |
      | segmentTypeCode   | OPEN_ENROLLMENT                  |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23974-2.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | recordId                     | 4                  |
      | enrollmentRecordId           | 4                  |
      | isEnrollmentProviderRequired | no                 |
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | eligibilityEndDate           | highDate           |
      | enrollmentStartDate          | 1stDayofLastMonth  |
      | enrollmentEndDate            | highDate           |
      | txnStatus                    | Accepted           |
      | programCode                  | H                  |
      | subProgramTypeCode           | HealthyIndianaPlan |
      | eligibilityStatusCode        | M                  |
      | planCode                     | 455701400          |
      | planId                       | 33                 |
      | serviceRegionCode            | Statewide          |
      | anniversaryDate              | anniversaryDate    |
      | channel                      | SYSTEM_INTEGRATION |
      | categoryCode                 | 10                 |
      | coverageCode                 | cc01               |
    And I run create Eligibility and Enrollment API
    And I update latest consumer actions by consumerId "23974-2.consumerId" with data
      | [0].action               | Available                   |
      | [0].consumerAction       | Plan Change Open Enrollment |
      | [0].planSelectionAllowed | true                        |
      | [0].changeAllowedFrom    | 1stDayofPresentMonth        |
      | [0].changeAllowedUntil   | 90DayFromFirstDayOfMonth    |
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "23974-1.firstName" and Last Name as "23974-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    # CP-23975 1.0 Navigating to Plan Change
    And I click "PLAN CHANGE" Button for a consumer first name "23974-1.firstName" and last name "23974-1.lastName"
    Then I verify I am still on "Enrollment Update" Page
    # CP-23975 2.0 NO Adding case member(s)
    Then I verify "Add Case Members button" is not displayed
    # CP-23975 3.0 No Provider Search on Enrollment Update screen
    Then I verify "PCP SELECT BUTTON" is not displayed
    # CP-23975 4.0 Reviewing available plans
    Then I verify list of all available plans on Enrollment Update Page with data
#      | ICHIA ESP HIP               |
      | ANTHEM HIP                  |
      | CARESOURCE HIP              |
      | MANAGED HEALTH SERVICES HIP |
      | MDWISE AMERICHOICE HIP      |
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | PLAN TYPE  | Medical                 |
      | START DATE | firstDayOfNextYearUIver |
      | END DATE   | highDateUIver           |
    # CP-23974 1.0 Select plan (Regression)
    When I select A second plan from Available Plans
    Then I verify I am still on "Enrollment Update" Page
    # CP-23974 1.1 Remove plan (Regression)
    And I click on Remove Plan Option
    Then I verify I am still on "Enrollment Update" Page
    When I select A plan from Available Plans
    # CP-23974 2.0 Require Reason for Plan Change
    Then I verify "SUBMIT" button is not clickable
    Then I will be required to enter a “Reason” for the change
    And I click "Reason" Button for a consumer
    And I verify bellow fields are displayed on "drop down reason" section:
      | HIP Plan Change |
    And I click "Reason" Button for a consumer
    # CP-23974 3.0 Display Selected Plan(s)
    Then I verify below details on newly created current enrollment segment on Enrollment Update Page
      | PLAN NAME      | selectedPlanName        |
      | PLAN TYPE      | Medical                 |
      | SERVICE REGION | hidden                  |
      | START DATE     | firstDayOfNextYearUIver |
      | END DATE       | highDateUIver           |
    # CP-23974 3.1 No Provider Search on Enrollment Update screen
    Then I verify "PCP SELECT BUTTON" is not displayed
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    # CP-23960 6.0 Display Program & Benefit Info screen with saved changes after clicking “Submit” button
    Then I verify I am still on "PROGRAM & BENEFIT INFO" Page
    Then I verify program & benefit info page for consumer first name "23974-1.firstName" and last name "23974-1.lastName" with data
      | CURRENT ENROLLMENT.SELECTION STATUS   | Disenroll Requested          |
      | CURRENT ENROLLMENT.END DATE           | lastDayOfThePresentYearUIver |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                       |
      | FUTURE ENROLLMENT.SELECTION STATUS    | Selection Made               |
      | FUTURE ENROLLMENT.START DATE          | firstDayOfNextYearUIver      |
      | FUTURE ENROLLMENT.END DATE            | highDateUIver                |
      | FUTURE ENROLLMENT.EDIT BUTTON         | displayed                    |
      | FUTURE ENROLLMENT.DISREGARD BUTTON    | displayed                    |
    # CP-23960 4.0 Update each consumer’s prior enrollment segment
    And I verify enrollment by consumer id "23974-1.consumerId" with data
      | medicalPlanEndDate   | lastDayOfThePresentYear |
      | planEndDateReason    | PC                      |
      | updatedOn            | current                 |
      | updatedBy            | 7450                    |
      | txnStatus            | DISENROLL_REQUESTED     |
      | medicalPlanBeginDate | 1stDayofLastMonth       |
      | enrollmentType       | MEDICAL                 |
      | channel              | SYSTEM_INTEGRATION      |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | 23974-1.consumerId      |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    # CP-23960 3.0 Create each consumer’s new (requested) enrollment segment
    And I verify latest enrollment by consumer id "23974-1.consumerId" with data
      | medicalPlanBeginDate | firstDayOfNextYear |
      | medicalPlanEndDate   | highDate           |
      | enrollmentType       | MEDICAL            |
      | channel              | PHONE              |
      | planCode             | getFromUISelected  |
      | txnStatus            | SELECTION_MADE     |
      | selectionReason      | HT                 |
      | createdOn            | current            |
      | updatedOn            | current            |
      | createdBy            | 7450               |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | 23974-1.consumerId    |
      | consumerFound | true                  |
      | DPBI          |[blank]|

  @CP-25852 @ui-ee-in @crm-regression  @IN-EB-UI-Regression @sobir
  Scenario Outline: IN-EB Decide Program-Population HHW, HCC, HIP (Non-RCP)(negative)(Population Not Found)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | yes                  |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramCode>     |
      | eligibilityStatusCode        | E                    |
    # E is wrong eligibilityStatusCode -> "Population Not Found"
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "<name>.firstName" and Last Name as "<name>.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "<name>.firstName" and last name "<name>.lastName" with data
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | Population Not Found |
    Examples:
      | name    | programCode | subProgramCode     |
      | 25852-1 | R           | HoosierHealthwise  |
      | 25852-2 | A           | HoosierCareConnect |

  @CP-23968 @CP-23968-01 @CP-23968-02 @CP-23968-03 @ui-ee-in @crm-regression  @IN-EB-UI-Regression @sobir
  Scenario: Verify user is navigated to Enrollment update screen and no add case memebrr button is not displayed for 1 consumer
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23968-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | yes                  |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | M                    |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "23968-1.firstName" and Last Name as "23968-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    # 1.0 Select ENROLL button
    And I click "ENROLL" Button for a consumer first name "23968-1.firstName" and last name "23968-1.lastName"
    Then I verify I am still on "Enrollment Update" Page
    # 2.0 Add CASE MEMBER - One (1) Consumer
    Then I verify "Add Case Members button" is not displayed
    # 3.0 Reviewing available plans
    Then I verify list of all available plans on Enrollment Update Page with data
      | ANTHEM HCC                  |
      | MANAGED HEALTH SERVICES HCC |
      | UNITED HEALTHCARE HCC       |
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | START DATE | newEnrollCutoffStartDateHCCUIver |
      | END DATE   | highDateUIver                    |
      | PLAN TYPE  | Medical                          |

  @CP-23968 @CP-23968-01 @CP-23968-02 @ui-ee-in @crm-regression  @IN-EB-UI-Regression @sobir
  Scenario: Add case member button is displayed or grayed for more than 1 consumer SAME or DIFFERENT sub-program
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23968-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | yes                  |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | M                    |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23968-3 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | yes                  |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | V                    |
      # consumer 23968-4 is in DIFFERENT sub-program
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23968-4 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | yes                  |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "23968-2.firstName" and Last Name as "23968-2.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    # 1.0 Select ENROLL button
    And I click "ENROLL" Button for a consumer first name "23968-2.firstName" and last name "23968-2.lastName"
    Then I verify I am still on "Enrollment Update" Page
    # 2.1 Add CASE MEMBER(s) - More than One Consumer SAME sub-program
    When I click Add Case Members Button
    Then I verify I only see next other consumers in the dropdown list
      | 23968-3 |
      # consumer 23968-4 is not in the list because in DIFFERENT sub-program
    When I click Add Case Members with First Name as "23968-3.firstName" and Last Name as "23968-3.lastName"
    # 2.2 Add CASE MEMBER(s) - More than One Consumer DIFFERENT sub-program
    Then I verify  the Add Case Members button is disable
    # 3.0 Reviewing available plans
    Then I verify list of all available plans on Enrollment Update Page with data
      | ANTHEM HCC                  |
      | MANAGED HEALTH SERVICES HCC |
      | UNITED HEALTHCARE HCC       |
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | PLAN TYPE  | Medical                          |
      | START DATE | newEnrollCutoffStartDateHCCUIver |
      | END DATE   | highDateUIver                    |

  @CP-23967 @ui-ee-in @crm-regression  @IN-EB-UI-Regression @sobir
  Scenario: IN-EB HHW, HCC - (New) Enroll View Selection, Submit Selection, View on Program and Benefit Info Screen
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23967-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23967-1.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | M                    |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "23967-1.firstName" and Last Name as "23967-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "ENROLL" Button for a consumer first name "23967-1.firstName" and last name "23967-1.lastName"
    #1.0 Select plan (Regression)
    When I select A second plan from Available Plans
    Then I verify I am still on "Enrollment Update" Page
    # 1.3 Remove Selected Plan (Regression)
    And I click on Remove Plan Option
    Then I verify I am still on "Enrollment Update" Page
    When I select A plan from Available Plans
    Then I verify below details on newly created current enrollment segment on Enrollment Update Page
      # 1.1 View Selected Consumer (Regression)
      | SELECTED CONSUMER NAMES | 23967-1                          |
      # 1.2 View Selected Plan (Regression)
      | PLAN NAME               | selectedPlanName                 |
      | PLAN TYPE               | Medical                          |
      | SERVICE REGION          | hidden                           |
      | START DATE              | newEnrollCutoffStartDateHCCUIver |
      | END DATE                | highDateUIver                    |
    # 3.1 No Provider Search on Enrollment Update screen
    Then I verify "PCP SELECT BUTTON" is not displayed
    And I click submit button on enrollment update
    # 4.0 Program & Benefit Info screen - After Clicking “SUBMIT” Button
    Then I verify I am still on "PROGRAM & BENEFIT INFO" Page
    Then I verify program & benefit info page for consumer first name "23967-1.firstName" and last name "23967-1.lastName" with data
      | FUTURE ENROLLMENT.EDIT BUTTON      | displayed |
      | FUTURE ENROLLMENT.DISREGARD BUTTON | displayed |
    # 3.0 Create New Enroll Enrollment Segment
    And I verify latest enrollment by consumer id "23967-1.consumerId" with data
      | planCode             | getFromUISelected           |
      | txnStatus            | SELECTION_MADE              |
      | channel              | PHONE                       |
      | medicalPlanBeginDate | newEnrollCutoffStartDateHCC |
      | medicalPlanEndDate   | highDate                    |
      | enrollmentType       | MEDICAL                     |
      | selectionReason      | 02                          |
      | createdOn            | current                     |
      | createdBy            | 7450                        |

  @CP-23969 @CP-23969-01 @ui-ee-in @crm-regression  @IN-EB-UI-Regression @sobir
  Scenario: IN-EB HHW, HCC - (New) Enroll  View Consumer on Program and Benefit Info - Mandatory & Voluntary
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23969-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23969-1.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | M                    |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "23969-1.firstName" and Last Name as "23969-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "23969-1.firstName" and last name "23969-1.lastName" with data
      # 1.0 HHW/HCC Consumer information
      | CALENDAR ICON HOVER.ACTION TEXT         | ENROLL                                                 |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | firstDayofPresntMonth - 60DaysFromFirstDayOfMonthUIver |
     #Removed comment out below line which failed due to defect CP-33274 before
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | 60DaysFromFirstDayOfMonthUIver                         |
      | COUNTDOWN.ICON HOVER                    | Calendar days left to enroll in a plan                 |
      # 2.0 CURRENT ELIGIBILITY for IN-EB project  - fields to SHOW
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HCC-Mandatory                                          |
      | CURRENT ELIGIBILITY.AID CATEGORY        | -- --                                                  |
      | CURRENT ELIGIBILITY.RCP                 | No                                                     |
      | CURRENT ELIGIBILITY.START DATE          | firstDayofPresntMonth                                  |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver                                          |
      | CURRENT ELIGIBILITY.ENROLL BUTTON       | is displayed                                           |
      # 4.0 CURRENT ELIGIBILITY for IN-EB project  - fields DO NOT SHOW
      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                                                 |
      | CURRENT ELIGIBILITY.PCP SELECT BUTTON   | hidden                                                 |

  @CP-23969 @CP-23969-02 @ui-ee-in @crm-regression  @IN-EB-UI-Regression @sobir
  Scenario: IN-EB HHW, HCC - (New) Enroll  View Consumer on Program and Benefit Info - Excluded
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23969-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23969-2.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | X                    |
      | genericFieldText1            | Eligible             |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "23969-2.firstName" and Last Name as "23969-2.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "23969-2.firstName" and last name "23969-2.lastName" with data
      # 3.0 CURRENT ELIGIBILITY for IN-EB project  - fields to SHOW
      | CALENDAR ICON HOVER.ACTION TEXT         | ENROLL                                                 |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | firstDayofPresntMonth - 60DaysFromFirstDayOfMonthUIver |
     #Removed comment out below line which failed due to defect CP-33274 before
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | 60DaysFromFirstDayOfMonthUIver                         |
      | COUNTDOWN.ICON HOVER                    | Calendar days left to enroll in a plan                 |
      # new implementation according to CP-30689 X -> Check Core
      | INDICATOR.CHECK CORE                    | is displayed                                           |
      | TEXT.CHECK CORE                         | Check Core                                             |
      | CURRENT ELIGIBILITY.CONSUMER PROGRAM    | HCC                                                    |
      | CURRENT ELIGIBILITY.MANAGED CARE STATUS | Excluded                                               |

      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18                                      |
      | CURRENT ELIGIBILITY.RCP                 | No                                                     |
      | CURRENT ELIGIBILITY.START DATE          | firstDayofPresntMonth                                  |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver                                          |
      # 5.0 CURRENT ELIGIBILITY for IN-EB project  - fields DO NOT SHOW
      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                                                 |
      | CURRENT ELIGIBILITY.PCP SELECT BUTTON   | hidden                                                 |
      | CURRENT ELIGIBILITY.ENROLL BUTTON       | is displayed                                           |

  @CP-23961 @ui-ee-in @crm-regression  @IN-EB-UI-Regression @sobir
  Scenario: IN-EB: HHW, HCC - Pre-Lockin Click Plan Change Button, Add Case Members, and Review Plans Available (only one consumer on the case)
  hide the button for the following conditions
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23961-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23961-1.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 23961-1.consumerId    |
      | startDate         | 1stDayofPresentMonth  |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23961-1.consumerId      |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | enrollmentEndDate            | lastDayOfThePresentYear |
      | txnStatus                    | Accepted                |
      | programCode                  | R                       |
      | subProgramTypeCode           | HoosierHealthwise       |
      | eligibilityStatusCode        | M                       |
      | planCode                     | 400752220               |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "23961-1.firstName" and Last Name as "23961-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    # 1.0 Navigating to Plan Change functionality
    And I click "PLAN CHANGE" Button for a consumer first name "23961-1.firstName" and last name "23961-1.lastName"
    Then I verify I am still on "Enrollment Update" Page
    # 2.0 Add CASE MEMBER - One (1) Consumer
    Then I verify "Add Case Members button" is not displayed
    # 3.0 Reviewing available plans
    # There is a defect CP-34087 in plans (ADVANTAGE HEALTH SOLUTIONS INC, MDWISE CS, OPT IN DECLINED should not be displayed). Commented out until defect is fixed
#    Then I verify list of all available plans on Enrollment Update Page with data
#      | ANTHEM                  |
#      | CARESOURCE              |
#      | MANAGED HEALTH SERVICES |
#      | MDWISE HH               |
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | START DATE | cutoffStartDateHHWUIver |
      | END DATE   | highDateUIver           |
      | PLAN TYPE  | Medical                 |

  @CP-23959 @CP-23960 @CP-23961 @CP-23962 @CP-22279 @ui-ee-in @crm-regression  @IN-EB-UI-Regression @sobir @shruti
  Scenario: CP-23961 IN-EB HHW, HCC - Pre-Lockin Click Plan Change Button, Add Case Members, and Review Plans Available2 (HCC - main consumer)
  CP-23962 IN-EB HHW, HCC - Pre-Lockin View Consumer on Program and Benefit Info Screen
  CP-23960 IN-EB HHW, HCC - Pre-Lockin View Selection, Submit Selection, Refresh on Program & Benefit Info Screen
  CP-23959 IN-EB HHW, HCC - Pre-Lockin Capture Business Event for Plan Change Pre-Lockin
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23961-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23961-2.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | V                    |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 23961-2.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | openEnrollmentDay        |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23961-2.consumerId   |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | enrollmentRecordId           | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | V                    |
      | planCode                     | 499254630            |
      | planId                       | 33                   |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23961-3 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23961-3.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | V                    |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 23961-3.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | openEnrollmentDay        |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23961-3.consumerId   |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | enrollmentRecordId           | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | M                    |
      | planCode                     | 499254630            |
      | planId                       | 33                   |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23961-4 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23961-4.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 23961-4.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | openEnrollmentDay        |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23961-4.consumerId   |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | enrollmentRecordId           | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | planCode                     | 500307680            |
      | planId                       | 33                   |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "23961-2.firstName" and Last Name as "23961-2.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "23961-2.firstName" and last name "23961-2.lastName" with data
      # CP-23962 1.0 Viewing the consumer info row
      | CALENDAR ICON HOVER.ACTION TEXT         | PRE-LOCKIN - WINDOW                                   |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | firstDayofPresntMonth - 90DayFromFirstDayOfMonthUIver |
     #Removed comment out below line which failed due to defect CP-33274 before
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | 90DayFromFirstDayOfMonthUIver                         |
      | COUNTDOWN.ICON HOVER                    | Calendar days left to change plan before lock-in      |
      # CP-23962 2.0 Viewing the current eligibility segment
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HCC-Voluntary                                         |
      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18                                     |
      | CURRENT ELIGIBILITY.RCP                 | No                                                    |
      | CURRENT ELIGIBILITY.START DATE          | firstDayofPresntMonth                                 |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver                                         |
      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                                                |
      # CP-23962 3.0 Viewing the current enrollment segment
      | CURRENT ENROLLMENT.PLAN NAME            | ANTHEM HCC                                            |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Accepted                                              |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration                                    |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                                                 |
      | CURRENT ENROLLMENT.START DATE           | firstDayofPresntMonth                                 |
      | CURRENT ENROLLMENT.END DATE             | highDateUIver                                         |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | is displayed                                          |
    # CP-23961 1.0 Navigating to Plan Change functionality
    And I click "PLAN CHANGE" Button for a consumer first name "23961-2.firstName" and last name "23961-2.lastName"
    Then I verify I am still on "Enrollment Update" Page
    # CP-23961 2.0 Adding case member(s) - More than One Consumer SAME sub-program
    When I click Add Case Members Button
    Then I verify I only see next other consumers in the dropdown list
      | 23961-3 |
      # CP-23961 consumer 23961-4 is not in the list because in DIFFERENT sub-program
    When I click Add Case Members with First Name as "23961-3.firstName" and Last Name as "23961-3.lastName"
    Then I verify  the Add Case Members button is disable
    # CP-23961 2.1 Removing a case member from selection dropdown (regression)
    When I click Remove Button for Case Member First Name as "23961-3.firstName" and Last Name as "23961-3.lastName"
    Then I verivy consumer by First Name as "23961-3.firstName" and Last Name as "23961-3.lastName" is not in plan selection
    # CP-23961 3.0 Reviewing available plans
    Then I verify list of all available plans on Enrollment Update Page with data
      | ANTHEM HCC                  |
      | MANAGED HEALTH SERVICES HCC |
      | UNITED HEALTHCARE HCC       |
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | PLAN TYPE  | Medical                           |
      | START DATE | planChangeCutoffStartDateHCCUIver |
      | END DATE   | highDateUIver                     |
   # CP-23960 1.1 Select plan (Regression)
    When I select A plan from Available Plans
    Then I verify I am still on "Enrollment Update" Page
    # CP-23960 1.2 Remove plan (Regression)
    And I click on Remove Plan Option
    Then I verify I am still on "Enrollment Update" Page
    When I select A plan from Available Plans
    # CP-23960 1.0 Viewing selected Plan Change option
    Then I verify below details on newly created current enrollment segment on Enrollment Update Page
      | SELECTED CONSUMER NAMES | 23961-2                                |
      | AGE/GENDER              | 20/Female                              |
      | POPULATION              | HCC-Voluntary                          |
      | RESIDENTIAL ADDRESS     | 22 main st , Preble Indiana 46782-4345 |
      | CURRENT PLAN            | ANTHEM HCC                             |
      | CURRENT PROVIDER        | -- --                                  |
      | ELIGIBILITY DETAILS     | firstDayofPresntMonth - highDateUIver  |
      | PLAN NAME               | selectedPlanName                       |
      | PLAN TYPE               | Medical                                |
      | SERVICE REGION          | hidden                                 |
      | START DATE              | planChangeCutoffStartDateHCCUIver      |
      | END DATE                | highDateUIver                          |
    # CP-23960 1.3 Require Reason for Plan Change
    Then I verify "SUBMIT" button is not clickable
    Then I verify I am still on "ENROLLMENT UPDATE" Page
    And I click "Reason" Button for a consumer
    And I verify bellow fields are displayed on "drop down reason" section:
      | 01 - Approved Change |
    And I click "Reason" Button for a consumer
    Then I verify "PCP SELECT BUTTON" is not displayed
    # CP-23960 4.0 Display Program & Benefit Info screen with saved changes after clicking “Submit” button
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "23961-2.firstName" and last name "23961-2.lastName" with data
      | CURRENT ENROLLMENT.SELECTION STATUS   | Disenroll Requested               |
      | CURRENT ENROLLMENT.END DATE           | lastDayofpresentMonth             |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                            |
      | FUTURE ENROLLMENT.SELECTION STATUS    | Selection Made                    |
      | FUTURE ENROLLMENT.START DATE          | planChangeCutoffStartDateHCCUIver |
      | FUTURE ENROLLMENT.END DATE            | highDateUIver                     |
      | FUTURE ENROLLMENT.EDIT BUTTON         | displayed                         |
      | FUTURE ENROLLMENT.DISREGARD BUTTON    | displayed                         |
    # CP-23960 2.0 Update each consumer’s prior enrollment segment
    And I verify enrollment by consumer id "23961-2.consumerId" with data
      | medicalPlanEndDate   | lastDayofPresentMonth |
      | planEndDateReason    | 01                    |
      | updatedOn            | current               |
      | updatedBy            | 7450                  |
      | txnStatus            | DISENROLL_REQUESTED   |
      | medicalPlanBeginDate | 1stDayofPresentMonth  |
      | enrollmentType       | MEDICAL               |
      | channel              | SYSTEM_INTEGRATION    |
      | selectionReason      | null                  |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | 23961-2.consumerId      |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    # CP-23960 3.0 Create each consumer’s new (requested) enrollment segment
    And I verify latest enrollment by consumer id "23961-2.consumerId" with data
      | medicalPlanBeginDate | planChangeCutoffStartDateHCC |
      | medicalPlanEndDate   | highDate                     |
      | enrollmentType       | MEDICAL                      |
      | channel              | PHONE                        |
      | txnStatus            | SELECTION_MADE               |
      | selectionReason      | 01                           |
      | planEndDateReason    | null                         |
      | createdOn            | current                      |
      | updatedOn            | current                      |
      | createdBy            | 7450                         |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | 23961-2.consumerId    |
      | consumerFound | true                  |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
    # CP-23959 1.0 Plan Change Business Event Required Fields
      | eventName                              | PLAN_CHANGE                      |
      | channel                                | PHONE                            |
      | createdBy                              | 7450                             |
      | processDate                            | current                          |
      | externalCaseId                         | 23961-2.caseIdentificationNumber |
      | externalConsumerId                     | 23961-2.externalConsumerId       |
      | consumerId                             | 23961-2.consumerId               |
      | consumerName                           | 23961-2                          |
      # CP-23959 2.0 Plan Change Business Event Optional Enrollment fields from “Prior Enrollment”
      | previousEnrollment.enrollmentStartDate | 1stDayofPresentMonth             |
      | previousEnrollment.enrollmentEndDate   | lastDayofPresentMonth            |
      | previousEnrollment.planSelectionReason | null                             |
      | previousEnrollment.planChangeReason    | 01                               |
      | previousEnrollment.rejectionReason     | null                             |
      | previousEnrollment.planCode            | 499254630                        |
      | previousEnrollment.enrollmentType      | MEDICAL                          |
      | previousEnrollment.selectionStatus     | DISENROLL_REQUESTED              |
      | previousEnrollment.anniversaryDate     | anniversaryDate                  |
      | previousEnrollment.pcpNpi              | null                             |
      | previousEnrollment.pcpName             | null                             |
      # CP-23959 3.0 Plan Change Business Event Optional Enrollment fields from “Requested Enrollment”
      | requestedSelection.enrollmentStartDate | planChangeCutoffStartDateHCC     |
      | requestedSelection.enrollmentEndDate   | highDate                         |
      | requestedSelection.planSelectionReason | 01                               |
      | requestedSelection.planChangeReason    | null                             |
      | requestedSelection.rejectionReason     | null                             |
      | requestedSelection.planCode            | getFromUISelected                |
      | requestedSelection.enrollmentType      | MEDICAL                          |
      | requestedSelection.selectionStatus     | SELECTION_MADE                   |
      | requestedSelection.anniversaryDate     | anniversaryDate                  |
      | requestedSelection.pcpNpi              | null                             |
      | requestedSelection.pcpName             | null                             |

  @CP-23959 @CP-23960 @CP-23961 @CP-23962 @CP-34087 @ui-ee-in @crm-regression  @IN-EB-UI-Regression @sobir
  Scenario: CP-23961 IN-EB HHW, HCC - Pre-Lockin Click Plan Change Button, Add Case Members, and Review Plans Available (HHW - main consumer)
  CP-23962 IN-EB HHW, HCC - Pre-Lockin View Consumer on Program and Benefit Info Screen
  CP-23960 IN-EB HHW, HCC - Pre-Lockin View Selection, Submit Selection, Refresh on Program & Benefit Info Screen
  CP-23959 IN-EB HHW, HCC - Pre-Lockin Capture Business Event for Plan Change Pre-Lockin
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23961-5 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23961-5.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 23961-5.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | openEnrollmentDay        |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23961-5.consumerId   |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | enrollmentRecordId           | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | planCode                     | 500307680            |
      | planId                       | 33                   |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23961-6 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23961-6.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 23961-6.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | openEnrollmentDay        |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23961-6.consumerId   |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | enrollmentRecordId           | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | planCode                     | 500307680            |
      | planId                       | 33                   |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23961-7 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23961-7.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | V                    |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 23961-7.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | openEnrollmentDay        |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23961-7.consumerId   |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | enrollmentRecordId           | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | V                    |
      | planCode                     | 499254630            |
      | planId                       | 33                   |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "23961-5.firstName" and Last Name as "23961-5.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "23961-5.firstName" and last name "23961-5.lastName" with data
      # CP-23962 1.0 Viewing the consumer info row
      | CALENDAR ICON HOVER.ACTION TEXT         | PRE-LOCKIN - WINDOW                                   |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | firstDayofPresntMonth - 90DayFromFirstDayOfMonthUIver |
     #Removed comment out below line which failed due to defect CP-33274 before
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | 90DayFromFirstDayOfMonthUIver                         |
      | COUNTDOWN.ICON HOVER                    | Calendar days left to change plan before lock-in      |
      # CP-23962 2.0 Viewing the current eligibility segment
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HHW-Mandatory                                         |
      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18                                     |
      | CURRENT ELIGIBILITY.RCP                 | No                                                    |
      | CURRENT ELIGIBILITY.START DATE          | firstDayofPresntMonth                                 |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver                                         |
      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                                                |
      # CP-23962 3.0 Viewing the current enrollment segment
      | CURRENT ENROLLMENT.PLAN NAME            | MDWISE HH                                             |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Accepted                                              |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration                                    |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                                                 |
      | CURRENT ENROLLMENT.START DATE           | firstDayofPresntMonth                                 |
      | CURRENT ENROLLMENT.END DATE             | highDateUIver                                         |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | is displayed                                          |
    # CP-23961 1.0 Navigating to Plan Change functionality
    And I click "PLAN CHANGE" Button for a consumer first name "23961-5.firstName" and last name "23961-5.lastName"
    Then I verify I am still on "Enrollment Update" Page
    # CP-23961 2.0 Adding case member(s) - More than One Consumer SAME sub-program
    When I click Add Case Members Button
    Then I verify I only see next other consumers in the dropdown list
      | 23961-6 |
      # CP-23961 consumer 23961-4 is not in the list because in DIFFERENT sub-program
    When I click Add Case Members with First Name as "23961-6.firstName" and Last Name as "23961-6.lastName"
    Then I verify  the Add Case Members button is disable
    # CP-23961 2.1 Removing a case member from selection dropdown (regression)
    When I click Remove Button for Case Member First Name as "23961-6.firstName" and Last Name as "23961-6.lastName"
    Then I verivy consumer by First Name as "23961-6.firstName" and Last Name as "23961-6.lastName" is not in plan selection
    # CP-23961 3.0 Reviewing available plans
    # There is a defect CP-34087 in plans (ADVANTAGE HEALTH SOLUTIONS INC, MDWISE CS, OPT IN DECLINED should not be displayed). Commented out until defect is fixed
    Then I verify list of all available plans on Enrollment Update Page with data
      | ANTHEM                  |
      | CARESOURCE              |
      | MANAGED HEALTH SERVICES |
      | MDWISE HH               |
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | PLAN TYPE  | Medical                 |
      | START DATE | cutoffStartDateHHWUIver |
      | END DATE   | highDateUIver           |
   # CP-23960 1.1 Select plan (Regression)
    When I select A plan from Available Plans
    Then I verify I am still on "Enrollment Update" Page
    # CP-23960 1.2 Remove plan (Regression)
    And I click on Remove Plan Option
    Then I verify I am still on "Enrollment Update" Page
    When I select A plan from Available Plans
    # CP-23960 1.0 Viewing selected Plan Change option
    Then I verify below details on newly created current enrollment segment on Enrollment Update Page
      | SELECTED CONSUMER NAMES | 23961-5                                |
      | AGE/GENDER              | 20/Female                              |
      | POPULATION              | HHW-Mandatory                          |
      | RESIDENTIAL ADDRESS     | 22 main st , Preble Indiana 46782-4345 |
      | CURRENT PLAN            | MDWISE HH                              |
      | CURRENT PROVIDER        | -- --                                  |
      | ELIGIBILITY DETAILS     | firstDayofPresntMonth - highDateUIver  |
      | PLAN NAME               | selectedPlanName                       |
      | PLAN TYPE               | Medical                                |
      | SERVICE REGION          | hidden                                 |
      | START DATE              | cutoffStartDateHHWUIver                |
      | END DATE                | highDateUIver                          |
    # CP-23960 1.3 Require Reason for Plan Change
    Then I verify "SUBMIT" button is not clickable
    Then I verify I am still on "ENROLLMENT UPDATE" Page
    And I click "Reason" Button for a consumer
    And I verify bellow fields are displayed on "drop down reason" section:
      | 01 - Approved Change |
    And I click "Reason" Button for a consumer
    Then I verify "PCP SELECT BUTTON" is not displayed
    # CP-23960 4.0 Display Program & Benefit Info screen with saved changes after clicking “Submit” button
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "23961-5.firstName" and last name "23961-5.lastName" with data
      | CURRENT ENROLLMENT.SELECTION STATUS   | Disenroll Requested     |
      | CURRENT ENROLLMENT.END DATE           | lastDayofpresentMonth   |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                  |
      | FUTURE ENROLLMENT.SELECTION STATUS    | Selection Made          |
      | FUTURE ENROLLMENT.START DATE          | cutoffStartDateHHWUIver |
      | FUTURE ENROLLMENT.END DATE            | highDateUIver           |
      | FUTURE ENROLLMENT.EDIT BUTTON         | displayed               |
      | FUTURE ENROLLMENT.DISREGARD BUTTON    | displayed               |
    # CP-23960 2.0 Update each consumer’s prior enrollment segment
    And I verify enrollment by consumer id "23961-5.consumerId" with data
      | medicalPlanEndDate   | lastDayofPresentMonth |
      | planEndDateReason    | 01                    |
      | updatedOn            | current               |
      | updatedBy            | 7450                  |
      | txnStatus            | DISENROLL_REQUESTED   |
      | medicalPlanBeginDate | 1stDayofPresentMonth  |
      | enrollmentType       | MEDICAL               |
      | channel              | SYSTEM_INTEGRATION    |
      | selectionReason      | null                  |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | 23961-5.consumerId      |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    # CP-23960 3.0 Create each consumer’s new (requested) enrollment segment
    And I verify latest enrollment by consumer id "23961-5.consumerId" with data
      | medicalPlanBeginDate | cutoffStartDateHHW |
      | medicalPlanEndDate   | highDate           |
      | enrollmentType       | MEDICAL            |
      | channel              | PHONE              |
      | txnStatus            | SELECTION_MADE     |
      | selectionReason      | 01                 |
      | planEndDateReason    | null               |
      | createdOn            | current            |
      | updatedOn            | current            |
      | createdBy            | 7450               |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | 23961-5.consumerId    |
      | consumerFound | true                  |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
    # CP-23959 1.0 Plan Change Business Event Required Fields
      | eventName                              | PLAN_CHANGE                      |
      | channel                                | PHONE                            |
      | createdBy                              | 7450                             |
      | processDate                            | current                          |
      | externalCaseId                         | 23961-5.caseIdentificationNumber |
      | externalConsumerId                     | 23961-5.externalConsumerId       |
      | consumerId                             | 23961-5.consumerId               |
      | consumerName                           | 23961-5                          |
      # CP-23959 2.0 Plan Change Business Event Optional Enrollment fields from “Prior Enrollment”
      | previousEnrollment.enrollmentStartDate | 1stDayofPresentMonth             |
      | previousEnrollment.enrollmentEndDate   | lastDayofPresentMonth            |
      | previousEnrollment.planSelectionReason | null                             |
      | previousEnrollment.planChangeReason    | 01                               |
      | previousEnrollment.rejectionReason     | null                             |
      | previousEnrollment.planCode            | 500307680                        |
      | previousEnrollment.enrollmentType      | MEDICAL                          |
      | previousEnrollment.selectionStatus     | DISENROLL_REQUESTED              |
      | previousEnrollment.anniversaryDate     | anniversaryDate                  |
      | previousEnrollment.pcpNpi              | null                             |
      | previousEnrollment.pcpName             | null                             |
      # CP-23959 3.0 Plan Change Business Event Optional Enrollment fields from “Requested Enrollment”
      | requestedSelection.enrollmentStartDate | cutoffStartDateHHW               |
      | requestedSelection.enrollmentEndDate   | highDate                         |
      | requestedSelection.planSelectionReason | 01                               |
      | requestedSelection.planChangeReason    | null                             |
      | requestedSelection.rejectionReason     | null                             |
      | requestedSelection.planCode            | getFromUISelected                |
      | requestedSelection.enrollmentType      | MEDICAL                          |
      | requestedSelection.selectionStatus     | SELECTION_MADE                   |
      | requestedSelection.anniversaryDate     | anniversaryDate                  |
      | requestedSelection.pcpNpi              | null                             |
      | requestedSelection.pcpName             | null                             |

  @CP-23962 @ui-ee-in @crm-regression  @IN-EB-UI-Regression @sobir
  Scenario Outline: IN-EB HHW, HCC - Pre-Lockin View Consumer on Program and Benefit Info Screen (Excluded)
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
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | <name>.consumerId        |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | openEnrollmentDay        |
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
      | enrollmentEndDate            | lastDayOfThePresentYear |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | planCode                     | <planCode>              |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "<name>.firstName" and Last Name as "<name>.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "<name>.firstName" and last name "<name>.lastName" with data
      # new implementation according to CP-30689 X -> Check Core
      | CALENDAR ICON HOVER.ACTION TEXT         | PRE-LOCKIN - WINDOW                                       |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | 1stDayofPresentMonthUIver - 90DayFromFirstDayOfMonthUIver |
     #Removed comment out below line which failed due to defect CP-33274 before
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | 90DayFromFirstDayOfMonthUIver                             |
      | COUNTDOWN.ICON HOVER                    | Calendar days left to change plan before lock-in          |
      # 2.0 Viewing the current eligibility segment
      | INDICATOR.CHECK CORE                    | is displayed                                              |
      | TEXT.CHECK CORE                         | Check Core                                                |
      | CURRENT ELIGIBILITY.CONSUMER PROGRAM    | <consumerProgram>                                         |
      | CURRENT ELIGIBILITY.MANAGED CARE STATUS | Excluded                                                  |

      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18                                         |
      | CURRENT ELIGIBILITY.RCP                 | No                                                        |
      | CURRENT ELIGIBILITY.START DATE          | firstDayofPresntMonth                                     |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver                                             |
      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                                                    |
      # 3.0 Viewing the current enrollment segment
      | CURRENT ENROLLMENT.PLAN NAME            | <planName>                                                |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Accepted                                                  |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration                                        |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                                                     |
      | CURRENT ENROLLMENT.START DATE           | firstDayofPresntMonth                                     |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | is displayed                                              |
    Examples:
      | name    | programCode | subProgramTypeCode | eligibilityStatusCode | consumerProgram | planCode  | planName   |
      | 23962-1 | R           | HoosierHealthwise  | X                     | HHW             | 500307680 | MDWISE HH  |
      | 23962-2 | A           | HoosierCareConnect | X                     | HCC             | 499254630 | ANTHEM HCC |

  @CP-23966 @CP-23966-01 @CP-23966-02 @CP-23966-03 @ui-ee-in @crm-regression  @IN-EB-UI-Regression @bindu
  Scenario: IN-EB HHW, HCC - (New) Enroll: Capture Business Event
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23966 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | yes                  |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | M                    |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "23966.firstName" and Last Name as "23966.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "ENROLL" Button for a consumer first name "23966.firstName" and last name "23966.lastName"
    And I select A plan from Available Plans
    And I click submit button on enrollment update
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      #  @CP-23966-01
      | eventName           | NEW_ENROLLMENT                 |
      | channel             | PHONE                          |
      | createdBy           | 7450                           |
      | processDate         | current                        |
      | externalCaseId      | 23966.caseIdentificationNumber |
      | externalConsumerId  | 23966.externalConsumerId       |
      | consumerId          | 23966.consumerId               |
      | consumerName        | 23966                          |
    #  @CP-23966-02
      | enrollmentStartDate | newEnrollCutoffStartDateHCC    |
      | enrollmentEndDate   | highDate                       |
      | planSelectionReason | 02                             |
      | planCode            | getFromUISelected              |
      | enrollmentType      | MEDICAL                        |
      | selectionStatus     | SELECTION_MADE                 |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | 23966.consumerId      |
      | consumerFound | true                  |
      | DPBI          |[blank]|

  @CP-23954 @CP-23953 @CP-23952 @CP-23951 @ui-ee-in @crm-regression  @IN-EB-UI-Regression @sobir
  Scenario: CP-23954 IN-EB HHW, HCC - Anniversary View Consumer on Program and Benefit Info Screen (HHW - main consumer)
  CP-23953 IN-EB HHW, HCC - Anniversary Click Plan Change Button, Add Case Members, and Review Plans Available
  CP-23952 IN-EB HHW, HCC - Anniversary View Selection, Submit Selection, View on Program and Benefit Info Screen
  CP-23951 IN-EB HHW, HCC - Anniversary Capture Business Event for Plan Change Anniversary
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23954-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23954-1.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof6MonthsBefore |
      | eligibilityStartDate         | 1stDayof6MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 23954-1.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | C                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23954-1.consumerId      |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayof6MonthsBefore   |
      | eligibilityEndDate           | highDate                |
      | enrollmentStartDate          | 1stDayof6MonthsBefore   |
      | enrollmentEndDate            | lastDayOfThePresentYear |
      | txnStatus                    | Accepted                |
      | programCode                  | R                       |
      | subProgramTypeCode           | HoosierHealthwise       |
      | eligibilityStatusCode        | M                       |
      | planCode                     | 400752220               |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23954-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23954-2.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof6MonthsBefore |
      | eligibilityStartDate         | 1stDayof6MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 23954-2.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | C                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23954-2.consumerId      |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayof6MonthsBefore   |
      | eligibilityEndDate           | highDate                |
      | enrollmentStartDate          | 1stDayof6MonthsBefore   |
      | enrollmentEndDate            | lastDayOfThePresentYear |
      | txnStatus                    | Accepted                |
      | programCode                  | R                       |
      | subProgramTypeCode           | HoosierHealthwise       |
      | eligibilityStatusCode        | M                       |
      | planCode                     | 400752220               |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23954-3 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23954-3.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof6MonthsBefore |
      | eligibilityStartDate         | 1stDayof6MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 23954-3.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | C                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23954-3.consumerId      |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayof6MonthsBefore   |
      | eligibilityEndDate           | highDate                |
      | enrollmentStartDate          | 1stDayof6MonthsBefore   |
      | enrollmentEndDate            | lastDayOfThePresentYear |
      | txnStatus                    | Accepted                |
      | programCode                  | R                       |
      | subProgramTypeCode           | HoosierHealthwise       |
      | eligibilityStatusCode        | M                       |
      | planCode                     | 400752220               |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23954-4 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23954-4.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof6MonthsBefore |
      | eligibilityStartDate         | 1stDayof6MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 23954-4.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | C                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23954-4.consumerId      |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayof6MonthsBefore   |
      | eligibilityEndDate           | highDate                |
      | enrollmentStartDate          | 1stDayof6MonthsBefore   |
      | enrollmentEndDate            | lastDayOfThePresentYear |
      | txnStatus                    | Accepted                |
      | programCode                  | A                       |
      | subProgramTypeCode           | HoosierCareConnect      |
      | eligibilityStatusCode        | M                       |
      | planCode                     | 499254630               |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
    And I run create Eligibility and Enrollment API
    # This step changes consumer 23954-3 RCP indicator from "No" to "Yes"
    And I send independent API call with name "" to create other enrollment segment with data
      | consumerId      | 23954-3.consumerId    |
      | startDate       | 1stDayof6MonthsBefore |
      | endDate         | highDate              |
      | segmentTypeCode | LILO                  |
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "23954-1.firstName" and Last Name as "23954-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "23954-1.firstName" and last name "23954-1.lastName" with data
      # CP-23954 1.0 View consumer on the Program and Benefit Info screen
      | CALENDAR ICON HOVER.ACTION TEXT         | OPEN ENROLLMENT-ANNIVERSARY                           |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | firstDayofPresntMonth - 90DayFromFirstDayOfMonthUIver |
     #Removed comment out below line which failed due to defect CP-33274 before
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | 90DayFromFirstDayOfMonthUIver                         |
      | COUNTDOWN.ICON HOVER                    | Calendar days left in Anniversary period              |
      # CP-23954 2.0 Current eligibility segment display
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HHW-Mandatory                                         |
      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18                                     |
      | CURRENT ELIGIBILITY.RCP                 | No                                                    |
      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                                                |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayof6MonthsBeforeUIver                            |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver                                         |
      # CP-23954 3.0 Consumer’s Current enrollments segment
      | CURRENT ENROLLMENT.PLAN NAME            | ANTHEM                                                |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Accepted                                              |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration                                    |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                                                 |
      | CURRENT ENROLLMENT.START DATE           | 1stDayof6MonthsBeforeUIver                            |
      | CURRENT ENROLLMENT.END DATE             | lastDayOfThePresentYearUIver                          |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | hidden                                                |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | is displayed                                          |
    Then I verify program & benefit info page for consumer first name "23954-3.firstName" and last name "23954-3.lastName" with data
      # CP-23954 1.0 View consumer on the Program and Benefit Info screen
      | CALENDAR ICON HOVER.ACTION TEXT         | OPEN ENROLLMENT-ANNIVERSARY                           |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | firstDayofPresntMonth - 90DayFromFirstDayOfMonthUIver |
     #Removed comment out below line which failed due to defect CP-33274 before
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | 90DayFromFirstDayOfMonthUIver                         |
      | COUNTDOWN.ICON HOVER                    | Calendar days left in Anniversary period              |
      # CP-23954 2.0 Current eligibility segment display
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HHW-Mandatory                                         |
      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18                                     |
      | CURRENT ELIGIBILITY.RCP                 | Yes                                                   |
      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                                                |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayof6MonthsBeforeUIver                            |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver                                         |
      # CP-23954 3.0 Consumer’s Current enrollments segment
      | CURRENT ENROLLMENT.PLAN NAME            | ANTHEM                                                |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Accepted                                              |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration                                    |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                                                 |
      | CURRENT ENROLLMENT.START DATE           | 1stDayof6MonthsBeforeUIver                            |
      | CURRENT ENROLLMENT.END DATE             | lastDayOfThePresentYearUIver                          |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | hidden                                                |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | hidden                                                |
      # CP-23953 1.0 Navigating to Plan Change
    And I click "PLAN CHANGE" Button for a consumer first name "23954-1.firstName" and last name "23954-1.lastName"
    Then I verify I am still on "Enrollment Update" Page
    Then I verify "PCP SELECT BUTTON" is not displayed
    # CP-23953 2.0 Adding case member(s) - More than One Consumer SAME sub-program
    When I click Add Case Members Button
    Then I verify I only see next other consumers in the dropdown list
      | 23954-2 |
      # consumer 23954-4 is not in the list because in DIFFERENT sub-program
      # consumer 23954-3 is in SAME sub-program but RCP indicator is YES
    When I click Add Case Members with First Name as "23954-2.firstName" and Last Name as "23954-2.lastName"
    # CP-23953 2.1 Adding case member(s): DIFFERENT PROGRAM
    Then I verify  the Add Case Members button is disable
    # CP-23953 3.0 Reviewing available plans
    # There is a defect CP-34087 in plans (ADVANTAGE HEALTH SOLUTIONS INC, MDWISE CS, OPT IN DECLINED should not be displayed). Commented out until defect is fixed
#    Then I verify list of all available plans on Enrollment Update Page with data
#      | ANTHEM                  |
#      | CARESOURCE              |
#      | MANAGED HEALTH SERVICES |
#      | MDWISE HH               |
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | PLAN TYPE  | Medical              |
      | START DATE | HHWAnniversaryWindow |
      | END DATE   | highDateUIver        |
    When I select A plan from Available Plans
    # CP-23952 1.0 Viewing selected Plan Change option
    Then I verify below details on newly created current enrollment segment on Enrollment Update Page
      | SELECTED CONSUMER NAMES | 23954-1,23954-2                            |
      | AGE/GENDER              | 20/Female                                  |
      | POPULATION              | HHW-Mandatory                              |
      | RESIDENTIAL ADDRESS     | 22 main st , Preble Indiana 46782-4345     |
      | CURRENT PLAN            | ANTHEM                                     |
      | CURRENT PROVIDER        | -- --                                      |
      | ELIGIBILITY DETAILS     | 1stDayof6MonthsBeforeUIver - highDateUIver |
      | PLAN NAME               | selectedPlanName                           |
      | PLAN TYPE               | Medical                                    |
      | SERVICE REGION          | hidden                                     |
      | START DATE              | HHWAnniversaryWindow                       |
      | END DATE                | highDateUIver                              |
    Then I verify "PCP SELECT BUTTON" is not displayed
    # CP-23952 2.0 Require Reason for Plan Change
    Then I verify "SUBMIT" button is not clickable
    Then I verify I am still on "ENROLLMENT UPDATE" Page
    And I click "Reason" Button for a consumer
    And I verify bellow fields are displayed on "drop down reason" section:
      | 01 - Approved Change |
    And I click "Reason" Button for a consumer
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "23954-1.firstName" and last name "23954-1.lastName" with data
    # CP-23952 3.0 Update each consumer’s prior enrollment segment
      | CURRENT ENROLLMENT.END DATE           | lastDayOf3MonthsFromNowUIver      |
      | CURRENT ENROLLMENT.SELECTION STATUS   | Disenroll Requested               |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                            |
      # CP-23952 4.0 Create each consumer’s new (requested) enrollment segment
      | FUTURE ENROLLMENT.SELECTION STATUS    | Selection Made                    |
      | FUTURE ENROLLMENT.START DATE          | planChangeCutoffStartDateHCCUIver |
      | FUTURE ENROLLMENT.END DATE            | highDateUIver                     |
      | FUTURE ENROLLMENT.EDIT BUTTON         | displayed                         |
      | FUTURE ENROLLMENT.DISREGARD BUTTON    | displayed                         |
    # CP-23952 3.0 Update each consumer’s prior enrollment segment
    And I verify enrollment by consumer id "23954-1.consumerId" with data
      | medicalPlanEndDate   | lastDayOf3MonthsFromNow |
      | planEndDateReason    | 01                      |
      | updatedOn            | current                 |
      | updatedBy            | 7450                    |
      | txnStatus            | DISENROLL_REQUESTED     |
      | medicalPlanBeginDate | 1stDayof6MonthsBefore   |
      | enrollmentType       | MEDICAL                 |
      | channel              | SYSTEM_INTEGRATION      |
      | selectionReason      | null                    |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | 23954-1.consumerId      |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    # CP-23952 4.0 Create each consumer’s new (requested) enrollment segment
    And Wait for 5 seconds
    And I verify latest enrollment by consumer id "23954-1.consumerId" with data
      | medicalPlanBeginDate | planChangeCutoffStartDateHCCUIver |
      | medicalPlanEndDate   | highDate                          |
      | enrollmentType       | MEDICAL                           |
      | channel              | PHONE                             |
      | txnStatus            | SELECTION_MADE                    |
      | selectionReason      | 94                                |
      | planEndDateReason    | null                              |
      | createdOn            | current                           |
      | updatedOn            | current                           |
      | createdBy            | 7450                              |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | 23954-1.consumerId    |
      | consumerFound | true                  |
      | DPBI          |[blank]|
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I save first contactId with name "23954-1a"
    Then I will verify business events are generated with data
    # CP-23951 1.0 Plan Change Business Event Required Fields
      | eventName                              | PLAN_CHANGE                      |
      | channel                                | PHONE                            |
      | createdBy                              | 7450                             |
      | processDate                            | current                          |
      | externalCaseId                         | 23954-1.caseIdentificationNumber |
      | externalConsumerId                     | 23954-1.externalConsumerId       |
      | consumerId                             | 23954-1.consumerId               |
      | consumerName                           | 23954-1                          |
      # CP-23951 2.0 Plan Change Business Event Optional Enrollment fields from “Prior Enrollment”
      | previousEnrollment.enrollmentStartDate | 1stDayof6MonthsBefore            |
      | previousEnrollment.enrollmentEndDate   | lastDayOf3MonthsFromNow          |
      | previousEnrollment.planSelectionReason | null                             |
      | previousEnrollment.planChangeReason    | 01                               |
      | previousEnrollment.rejectionReason     | null                             |
      | previousEnrollment.planCode            | 400752220                        |
      | previousEnrollment.enrollmentType      | MEDICAL                          |
      | previousEnrollment.selectionStatus     | DISENROLL_REQUESTED              |
      | previousEnrollment.anniversaryDate     | anniversaryDate                  |
      | previousEnrollment.pcpNpi              | null                             |
      | previousEnrollment.pcpName             | null                             |
      # CP-23959 3.0 Plan Change Business Event Optional Enrollment fields from “Requested Enrollment”
      | contactId                              | 23954-1a.contactId               |
      | requestedSelection.enrollmentStartDate | 1stDayOf4MonthsFromNow           |
      | requestedSelection.enrollmentEndDate   | highDate                         |
      | requestedSelection.planSelectionReason | 94                               |
      | requestedSelection.planChangeReason    | null                             |
      | requestedSelection.rejectionReason     | null                             |
      | requestedSelection.planCode            | getFromUISelected                |
      | requestedSelection.enrollmentType      | MEDICAL                          |
      | requestedSelection.selectionStatus     | SELECTION_MADE                   |
      | requestedSelection.anniversaryDate     | anniversaryDate                  |
      | requestedSelection.pcpNpi              | null                             |
      | requestedSelection.pcpName             | null                             |

  @CP-23954 @CP-23953 @CP-23952 @CP-23951 @ui-ee-in @crm-regression  @IN-EB-UI-Regression @sobir
  Scenario: CP-23954 IN-EB HHW, HCC - Anniversary View Consumer on Program and Benefit Info Screen (HCC - main consumer)
  CP-23953 IN-EB HHW, HCC - Anniversary Click Plan Change Button, Add Case Members, and Review Plans Available
  CP-23952 IN-EB HHW, HCC - Anniversary View Selection, Submit Selection, View on Program and Benefit Info Screen
  CP-23951 IN-EB HHW, HCC - Anniversary Capture Business Event for Plan Change Anniversary
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23954-5 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23954-5.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof6MonthsBefore |
      | eligibilityStartDate         | 1stDayof6MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 23954-5.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | C                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23954-5.consumerId      |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayof6MonthsBefore   |
      | eligibilityEndDate           | highDate                |
      | enrollmentStartDate          | 1stDayof6MonthsBefore   |
      | enrollmentEndDate            | lastDayOfThePresentYear |
      | txnStatus                    | Accepted                |
      | programCode                  | A                       |
      | subProgramTypeCode           | HoosierCareConnect      |
      | eligibilityStatusCode        | M                       |
      | planCode                     | 499254630               |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23954-6 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23954-6.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof6MonthsBefore |
      | eligibilityStartDate         | 1stDayof6MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | eligibilityStatusCode        | V                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 23954-6.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | C                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23954-6.consumerId      |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayof6MonthsBefore   |
      | eligibilityEndDate           | highDate                |
      | enrollmentStartDate          | 1stDayof6MonthsBefore   |
      | enrollmentEndDate            | lastDayOfThePresentYear |
      | txnStatus                    | Accepted                |
      | programCode                  | A                       |
      | subProgramTypeCode           | HoosierCareConnect      |
      | eligibilityStatusCode        | V                       |
      | planCode                     | 499254630               |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23954-7 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23954-7.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof6MonthsBefore |
      | eligibilityStartDate         | 1stDayof6MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 23954-7.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | C                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23954-7.consumerId      |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayof6MonthsBefore   |
      | eligibilityEndDate           | highDate                |
      | enrollmentStartDate          | 1stDayof6MonthsBefore   |
      | enrollmentEndDate            | lastDayOfThePresentYear |
      | txnStatus                    | Accepted                |
      | programCode                  | A                       |
      | subProgramTypeCode           | HoosierCareConnect      |
      | eligibilityStatusCode        | M                       |
      | planCode                     | 499254630               |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23954-8 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23954-8.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof6MonthsBefore |
      | eligibilityStartDate         | 1stDayof6MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 23954-8.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | C                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23954-8.consumerId      |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayof6MonthsBefore   |
      | eligibilityEndDate           | highDate                |
      | enrollmentStartDate          | 1stDayof6MonthsBefore   |
      | enrollmentEndDate            | lastDayOfThePresentYear |
      | txnStatus                    | Accepted                |
      | programCode                  | R                       |
      | subProgramTypeCode           | HoosierHealthwise       |
      | eligibilityStatusCode        | M                       |
      | planCode                     | 400752220               |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
    And I run create Eligibility and Enrollment API
    # This step changes consumer 23954-3 RCP indicator from "No" to "Yes"
    And I send independent API call with name "" to create other enrollment segment with data
      | consumerId      | 23954-7.consumerId    |
      | startDate       | 1stDayof6MonthsBefore |
      | endDate         | highDate              |
      | segmentTypeCode | LILO                  |
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "23954-5.firstName" and Last Name as "23954-5.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "23954-5.firstName" and last name "23954-5.lastName" with data
      # CP-23954 1.0 View consumer on the Program and Benefit Info screen
      | CALENDAR ICON HOVER.ACTION TEXT         | OPEN ENROLLMENT-ANNIVERSARY                           |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | firstDayofPresntMonth - 90DayFromFirstDayOfMonthUIver |
     #Removed comment out below line which failed due to defect CP-33274 before
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | 90DayFromFirstDayOfMonthUIver                         |
      | COUNTDOWN.ICON HOVER                    | Calendar days left in Anniversary period              |
      # CP-23954 2.0 Current eligibility segment display
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HCC-Mandatory                                         |
      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18                                     |
      | CURRENT ELIGIBILITY.RCP                 | No                                                    |
      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                                                |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayof6MonthsBeforeUIver                            |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver                                         |
      # CP-23954 3.0 Consumer’s Current enrollments segment
      | CURRENT ENROLLMENT.PLAN NAME            | ANTHEM HCC                                            |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Accepted                                              |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration                                    |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                                                 |
      | CURRENT ENROLLMENT.START DATE           | 1stDayof6MonthsBeforeUIver                            |
      | CURRENT ENROLLMENT.END DATE             | lastDayOfThePresentYearUIver                          |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | hidden                                                |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | is displayed                                          |
    Then I verify program & benefit info page for consumer first name "23954-7.firstName" and last name "23954-7.lastName" with data
      # CP-23954 1.0 View consumer on the Program and Benefit Info screen
      | CALENDAR ICON HOVER.ACTION TEXT         | OPEN ENROLLMENT-ANNIVERSARY                           |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | firstDayofPresntMonth - 90DayFromFirstDayOfMonthUIver |
     #Removed comment out below line which failed due to defect CP-33274 before
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | 90DayFromFirstDayOfMonthUIver                         |
      | COUNTDOWN.ICON HOVER                    | Calendar days left in Anniversary period              |
      # CP-23954 2.0 Current eligibility segment display
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HCC-Mandatory                                         |
      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18                                     |
      | CURRENT ELIGIBILITY.RCP                 | Yes                                                   |
      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                                                |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayof6MonthsBeforeUIver                            |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver                                         |
      # CP-23954 3.0 Consumer’s Current enrollments segment
      | CURRENT ENROLLMENT.PLAN NAME            | ANTHEM HCC                                            |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Accepted                                              |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration                                    |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                                                 |
      | CURRENT ENROLLMENT.START DATE           | 1stDayof6MonthsBeforeUIver                            |
      | CURRENT ENROLLMENT.END DATE             | lastDayOfThePresentYearUIver                          |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | hidden                                                |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | hidden                                                |
      # CP-23953 1.0 Navigating to Plan Change
    And I click "PLAN CHANGE" Button for a consumer first name "23954-5.firstName" and last name "23954-5.lastName"
    Then I verify I am still on "Enrollment Update" Page
    Then I verify "PCP SELECT BUTTON" is not displayed
    # CP-23953 2.0 Adding case member(s) - More than One Consumer SAME sub-program
    When I click Add Case Members Button
    Then I verify I only see next other consumers in the dropdown list
      | 23954-6 |
      # consumer 23954-8 is not in the list because in DIFFERENT sub-program
      # consumer 23954-7 is in SAME sub-program but RCP indicator is YES
    When I click Add Case Members with First Name as "23954-6.firstName" and Last Name as "23954-6.lastName"
    # CP-23953 2.1 Adding case member(s): DIFFERENT PROGRAM
    Then I verify  the Add Case Members button is disable
    # CP-23953 3.0 Reviewing available plans
    Then I verify list of all available plans on Enrollment Update Page with data
      | ANTHEM HCC                  |
      | MANAGED HEALTH SERVICES HCC |
      | UNITED HEALTHCARE HCC       |
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | PLAN TYPE  | Medical                     |
      | START DATE | 1stDayOf3MonthsFromNowUIver |
      | END DATE   | highDateUIver               |
    When I select A plan from Available Plans
    # CP-23952 1.0 Viewing selected Plan Change option
    Then I verify below details on newly created current enrollment segment on Enrollment Update Page
      | SELECTED CONSUMER NAMES | 23954-5,23954-6                            |
      | AGE/GENDER              | 20/Female                                  |
      | POPULATION              | HCC-Mandatory                              |
      | RESIDENTIAL ADDRESS     | 22 main st , Preble Indiana 46782-4345     |
      | CURRENT PLAN            | ANTHEM HCC                                 |
      | CURRENT PROVIDER        | -- --                                      |
      | ELIGIBILITY DETAILS     | 1stDayof6MonthsBeforeUIver - highDateUIver |
      | PLAN NAME               | selectedPlanName                           |
      | PLAN TYPE               | Medical                                    |
      | SERVICE REGION          | hidden                                     |
      | START DATE              | 1stDayOf3MonthsFromNowUIver                |
      | END DATE                | highDateUIver                              |
    Then I verify "PCP SELECT BUTTON" is not displayed
    # CP-23952 2.0 Require Reason for Plan Change
    Then I verify "SUBMIT" button is not clickable
    Then I verify I am still on "ENROLLMENT UPDATE" Page
    And I click "Reason" Button for a consumer
    And I verify bellow fields are displayed on "drop down reason" section:
      | 01 - Approved Change |
    And I click "Reason" Button for a consumer
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "23954-5.firstName" and last name "23954-5.lastName" with data
    # CP-23952 3.0 Update each consumer’s prior enrollment segment
      | CURRENT ENROLLMENT.END DATE           | lastDayOf2MonthsFromNowUIver |
      | CURRENT ENROLLMENT.SELECTION STATUS   | Disenroll Requested          |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                       |
      # CP-23952 4.0 Create each consumer’s new (requested) enrollment segment
      | FUTURE ENROLLMENT.SELECTION STATUS    | Selection Made               |
      | FUTURE ENROLLMENT.START DATE          | 1stDayOf3MonthsFromNowUIver  |
      | FUTURE ENROLLMENT.END DATE            | highDateUIver                |
      | FUTURE ENROLLMENT.EDIT BUTTON         | displayed                    |
      | FUTURE ENROLLMENT.DISREGARD BUTTON    | displayed                    |
    # CP-23952 3.0 Update each consumer’s prior enrollment segment
    And I verify enrollment by consumer id "23954-5.consumerId" with data
      | medicalPlanEndDate   | lastDayOf2MonthsFromNow |
      | planEndDateReason    | 01                      |
      | updatedOn            | current                 |
      | updatedBy            | 7450                    |
      | txnStatus            | DISENROLL_REQUESTED     |
      | medicalPlanBeginDate | 1stDayof6MonthsBefore   |
      | enrollmentType       | MEDICAL                 |
      | channel              | SYSTEM_INTEGRATION      |
      | selectionReason      | null                    |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | 23954-5.consumerId      |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    # CP-23952 4.0 Create each consumer’s new (requested) enrollment segment
    And I verify latest enrollment by consumer id "23954-5.consumerId" with data
      | medicalPlanBeginDate | 1stDayOf3MonthsFromNow |
      | medicalPlanEndDate   | highDate               |
      | enrollmentType       | MEDICAL                |
      | channel              | PHONE                  |
      | txnStatus            | SELECTION_MADE         |
      | selectionReason      | 94                     |
      | planEndDateReason    | null                   |
      | createdOn            | current                |
      | updatedOn            | current                |
      | createdBy            | 7450                   |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | 23954-5.consumerId    |
      | consumerFound | true                  |
      | DPBI          |[blank]|
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I save first contactId with name "23954-5a"
    Then I will verify business events are generated with data
    # CP-23951 1.0 Plan Change Business Event Required Fields
      | eventName                              | PLAN_CHANGE                      |
      | channel                                | PHONE                            |
      | createdBy                              | 7450                             |
      | processDate                            | current                          |
      | externalCaseId                         | 23954-5.caseIdentificationNumber |
      | externalConsumerId                     | 23954-5.externalConsumerId       |
      | consumerId                             | 23954-5.consumerId               |
      | consumerName                           | 23954-5                          |
      # CP-23951 2.0 Plan Change Business Event Optional Enrollment fields from “Prior Enrollment”
      | previousEnrollment.enrollmentStartDate | 1stDayof6MonthsBefore            |
      | previousEnrollment.enrollmentEndDate   | lastDayOf2MonthsFromNow          |
      | previousEnrollment.planSelectionReason | null                             |
      | previousEnrollment.planChangeReason    | 01                               |
      | previousEnrollment.rejectionReason     | null                             |
      | previousEnrollment.planCode            | 499254630                        |
      | previousEnrollment.enrollmentType      | MEDICAL                          |
      | previousEnrollment.selectionStatus     | DISENROLL_REQUESTED              |
      | previousEnrollment.anniversaryDate     | anniversaryDate                  |
      | previousEnrollment.pcpNpi              | null                             |
      | previousEnrollment.pcpName             | null                             |
      # CP-23959 3.0 Plan Change Business Event Optional Enrollment fields from “Requested Enrollment”
      | contactId                              | 23954-5a.contactId               |
      | requestedSelection.enrollmentStartDate | 1stDayOf3MonthsFromNow           |
      | requestedSelection.enrollmentEndDate   | highDate                         |
      | requestedSelection.planSelectionReason | 94                               |
      | requestedSelection.planChangeReason    | null                             |
      | requestedSelection.rejectionReason     | null                             |
      | requestedSelection.planCode            | getFromUISelected                |
      | requestedSelection.enrollmentType      | MEDICAL                          |
      | requestedSelection.selectionStatus     | SELECTION_MADE                   |
      | requestedSelection.anniversaryDate     | anniversaryDate                  |
      | requestedSelection.pcpNpi              | null                             |
      | requestedSelection.pcpName             | null                             |

  @CP-23954 @CP-23953 @CP-23952 @CP-23951 @ui-ee-in @crm-regression  @IN-EB-UI-Regression @sobir
  Scenario Outline: CP-23954 IN-EB HHW, HCC - Anniversary View Consumer on Program and Benefit Info Screen (Excluded)
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
      | enrollmentStartDate          | 1stDayof6MonthsBefore   |
      | eligibilityStartDate         | 1stDayof6MonthsBefore   |
      | eligibilityEndDate           | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | <name>.consumerId        |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | C                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayof6MonthsBefore   |
      | eligibilityEndDate           | highDate                |
      | enrollmentStartDate          | 1stDayof6MonthsBefore   |
      | enrollmentEndDate            | lastDayOfThePresentYear |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | planCode                     | <planCode>              |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "<name>.firstName" and Last Name as "<name>.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "<name>.firstName" and last name "<name>.lastName" with data
      # CP-23954 1.0 View consumer on the Program and Benefit Info screen
      # new implementation according to CP-30689 X -> Check Core
      | CALENDAR ICON HOVER.ACTION TEXT         | OPEN ENROLLMENT-ANNIVERSARY                               |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | 1stDayofPresentMonthUIver - 90DayFromFirstDayOfMonthUIver |
     #Removed comment out below line which failed due to defect CP-33274 before
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | 90DayFromFirstDayOfMonthUIver                             |
      | COUNTDOWN.ICON HOVER                    | Calendar days left in Anniversary period                  |
      # CP-23954 2.0 Current eligibility segment display|
      | INDICATOR.CHECK CORE                    | is displayed                                              |
      | TEXT.CHECK CORE                         | Check Core                                                |
      | CURRENT ELIGIBILITY.CONSUMER PROGRAM    | <consumerProgram>                                         |
      | CURRENT ELIGIBILITY.MANAGED CARE STATUS | Excluded                                                  |

      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18                                         |
      | CURRENT ELIGIBILITY.RCP                 | No                                                        |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayof6MonthsBeforeUIver                                |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver                                             |
      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                                                    |
      # CP-23954 3.0 Consumer’s Current enrollments segment
      | CURRENT ENROLLMENT.PLAN NAME            | <planName>                                                |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Accepted                                                  |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration                                        |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                                                     |
      | CURRENT ENROLLMENT.START DATE           | 1stDayof6MonthsBeforeUIver                                |
      | CURRENT ENROLLMENT.END DATE             | lastDayOfThePresentYearUIver                              |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | hidden                                                    |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | is displayed                                              |
    Examples:
      | name     | programCode | subProgramTypeCode | eligibilityStatusCode | consumerProgram | planCode  | planName   |
      | 23954-9  | R           | HoosierHealthwise  | X                     | HHW             | 500307680 | MDWISE HH  |
      | 23954-10 | A           | HoosierCareConnect | X                     | HCC             | 499254630 | ANTHEM HCC |

  @CP-23954 @CP-23953 @CP-23952 @CP-23951 @ui-ee-in @crm-regression  @IN-EB-UI-Regression @sobir
  Scenario: CP-23954 IN-EB HHW, HCC - Anniversary View Consumer on Program and Benefit Info Screen (Calendar between Pre-lockin & Anniversary)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 23954-11 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23954-11.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof6MonthsBefore |
      | eligibilityStartDate         | 1stDayof6MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other enrollment segments details
      | recordId          | 21                        |
      | consumerId        | 23954-11.consumerId       |
      | startDate         | 1stDayOf2MonthsFromNow    |
      | endDate           | 90DaysFrom3rdMonthFromNow |
      | genericFieldText1 | C                         |
      | genericFieldDate1 | 1stDayofPresentMonth      |
      | segmentTypeCode   | OPEN_ENROLLMENT           |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23954-11.consumerId     |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayof6MonthsBefore   |
      | eligibilityEndDate           | highDate                |
      | enrollmentStartDate          | 1stDayof6MonthsBefore   |
      | enrollmentEndDate            | lastDayOfThePresentYear |
      | txnStatus                    | Accepted                |
      | programCode                  | R                       |
      | subProgramTypeCode           | HoosierHealthwise       |
      | eligibilityStatusCode        | M                       |
      | planCode                     | 400752220               |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "23954-11.firstName" and Last Name as "23954-11.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "23954-11.firstName" and last name "23954-11.lastName" with data
      # CP-23954 4.0 Display Calendar between Pre-lockin & Anniversary
      | CALENDAR ICON HOVER.ACTION TEXT         | OPEN ENROLLMENT-ANNIVERSARY                                  |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | 1stDayOf2MonthsFromNowUIver - 90DaysFrom3rdMonthFromNowUIver |
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | -- --                                                        |
      | COUNTDOWN.ICON HOVER                    | -- --                                                        |
      # CP-23954 2.0 Current eligibility segment display
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HHW-Mandatory                                                |
      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18                                            |
      | CURRENT ELIGIBILITY.RCP                 | No                                                           |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayof6MonthsBeforeUIver                                   |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver                                                |
      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                                                       |
      # CP-23954 3.0 Consumer’s Current enrollments segment
      | CURRENT ENROLLMENT.PLAN NAME            | ANTHEM                                                       |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Accepted                                                     |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration                                           |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                                                        |
      | CURRENT ENROLLMENT.START DATE           | 1stDayof6MonthsBeforeUIver                                   |
      | CURRENT ENROLLMENT.END DATE             | lastDayOfThePresentYearUIver                                 |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | hidden                                                       |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | hidden                                                       |


  @CP-24242-01 @CP-23933-04 @ui-ee-in @crm-regression  @IN-EB-UI-Regression @shruti
  Scenario Outline: Verify RCP indicator is yes or no based on LILO start date and end date
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
      | enrollmentStartDate          | 1stDayofLastMonth       |
      | eligibilityStartDate         | 1stDayofLastMonth       |
      | eligibilityEndDate           | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramCode>        |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
      | genericFieldText1            | Eligible                |
    And User provide other eligibility segments details
      | startDate           | 1stDayofLastMonth       |
      | endDate             | highDate                |
      | segmentDetailValue1 | 1                       |
      | segmentDetailValue2 | 6                       |
      | segmentDetailValue3 | 1stDayofPresentMonth    |
      | segmentDetailValue4 | lastDayOfThePresentYear |
      | segmentTypeCode     | OTHER                   |
    And User provide other enrollment segments details
      | recordId          | 21                               |
      | consumerId        | <name>.consumerId                |
      | startDate         | 90DaysPriorAnniversaryDueDateHCC |
      | endDate           | anniversaryDueDateHCC            |
      | genericFieldText1 | C                                |
      | genericFieldDate1 | openEnrollmentDay                |
      | segmentTypeCode   | OPEN_ENROLLMENT                  |
    And User provide one more other enrollment segments details
      | recordId          | 21                |
      | consumerId        | <name>.consumerId |
      | startDate         | <startDate1>      |
      | endDate           | <endDate1>        |
      | genericFieldText1 | I                 |
      | genericFieldDate1 | openEnrollmentDay |
      | genericFieldDate2 |[blank]|
      | segmentTypeCode   | LILO              |
      | genericFieldText2 | 23223             |
      | genericFieldText5 | ERIC SCHALLET     |
      | genericFieldText3 | Medical           |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "<name>.firstName" and Last Name as "<name>.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "<name>.firstName" and last name "<name>.lastName" with data
      | CURRENT ELIGIBILITY.RCP | <RCPindicator> |
    Examples:
      | name    | programCode | subProgramCode     | eligibilityStatusCode | population    | startDate1            | startDate2            | endDate1              | endDate2 | provider1 | provider2 | genericFieldText1 | RCPindicator |
      | 24242-1 | R           | HoosierHealthwise  | M                     | HHW-Mandatory | 1stDayof2MonthsBefore | 1stDayof2MonthsBefore | current               | highDate | 300029354 | 30002     | O                 | Yes          |
      | 24242-2 | A           | HoosierCareConnect | M                     | HCC-Mandatory | 1stDayof2MonthsBefore | 1stDayof2MonthsBefore | lastDayofPresentMonth | highDate | 300029354 | 30002     | O                 | Yes          |
      | 24242-3 | R           | HoosierHealthwise  | M                     | HHW-Mandatory | 1stDayofNextMonth     | 1stDayof2MonthsBefore | future                | highDate | 300029354 | 30002     | O                 | No           |
      | 24242-4 | A           | HoosierCareConnect | M                     | HCC-Mandatory | 1stDayofNextMonth     | 1stDayof2MonthsBefore | yesterday             | highDate | 300029354 | 30002     | O                 | No           |









