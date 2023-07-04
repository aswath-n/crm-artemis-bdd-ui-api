Feature: IN-EB Updates to Enrollment End-Dates when eligibility has a non-high end date

  @CP-32722 @CP-32722-09 @CP-34087 @ui-ee-in @IN-EB-UI-Regression @kursat
  Scenario: CP-32722 Consumer Submits New Enrollment (HHW, HCC) with Eligibility Non-High End Date
  CP-32722 9.0 Consumer Submits New Enrollment (HHW, HCC)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 32722-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-1.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | yes                  |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | future               |
      | txnStatus                    | Accepted             |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 32722-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-2.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | yes                  |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | future               |
      | txnStatus                    | Accepted             |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | V                    |
      | categoryCode                 | 10                   |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 32722-3 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-3.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | yes                  |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | future               |
      | txnStatus                    | Accepted             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthWise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 32722-4 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-4.consumerId      |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | yes                     |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | lastDayOf3MonthsFromNow |
      | txnStatus                    | Accepted                |
      | programCode                  | A                       |
      | subProgramTypeCode           | HoosierCareConnect      |
      | eligibilityStatusCode        | M                       |
      | categoryCode                 | 10                      |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Account 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "32722-1.firstName" and Last Name as "32722-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    # A 1.0 Select ENROLL button
    And I click "ENROLL" Button for a consumer first name "32722-1.firstName" and last name "32722-1.lastName"
    Then I verify I am still on "Enrollment Update" Page
    # A 2.1 Add CASE MEMBER(s) - More than One Consumer SAME sub-program
    When I click Add Case Members Button
    Then I verify I only see next other consumers in the dropdown list
      | 32722-2 |
    # consumer 32722-3 is not in the list because in DIFFERENT sub-program
    # consumer 32722-4 is not in the list because has DIFFERENT eligibility end date
    When I click Add Case Members with First Name as "32722-2.firstName" and Last Name as "32722-2.lastName"
    # A 2.2 Add CASE MEMBER(s) - More than One Consumer DIFFERENT sub-program
    Then I verify  the Add Case Members button is disable
    # A 3.0 Reviewing available plans
    Then I verify list of all available plans on Enrollment Update Page with data
      | ANTHEM HCC                  |
      | MANAGED HEALTH SERVICES HCC |
      | UNITED HEALTHCARE HCC       |
#      | MDWISE HCC                  |
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | START DATE | newEnrollCutoffStartDateHCCUIver |
      | END DATE   | futureUIver                      |
      | PLAN TYPE  | Medical                          |
    # B 1.0 Select Plan (No Change)
    When I select A second plan from Available Plans
    Then I verify below details on newly created current enrollment segment on Enrollment Update Page
      # B 1.1 View Selected Consumer (No Change)
      | SELECTED CONSUMER NAMES | 32722-1,32722-2                  |
      # B 1.2 View Selected Plan (No Change)
      | PLAN NAME               | selectedPlanName                 |
      | PLAN TYPE               | Medical                          |
      # Service Region should be hidden if logged in with QA role instead of Superuser
      | SERVICE REGION          | hidden                           |
      | START DATE              | newEnrollCutoffStartDateHCCUIver |
      | END DATE                | futureUIver                      |
    # B 1.3 Remove Selected Plan (No Change)
    And I click on Remove Plan Option
    Then I verify I am still on "Enrollment Update" Page
    # B 2.0 No Provider Search on Enrollment Update screen (No Change)
    Then I verify "PCP SELECT BUTTON" is not displayed
    And I select A plan from Available Plans
    And I click submit button on enrollment update
    # B 4.0 Program & Benefit Info screen - After Clicking “SUBMIT” Button (No Change)
    Then I verify I am still on "PROGRAM & BENEFIT INFO" Page
    Then I verify program & benefit info page for consumer first name "32722-1.firstName" and last name "32722-1.lastName" with data
      | FUTURE ENROLLMENT.EDIT BUTTON      | displayed |
      | FUTURE ENROLLMENT.DISREGARD BUTTON | displayed |
    # 3.0 Create New Enroll Enrollment Segment
    And I verify latest enrollment by consumer id "32722-1.consumerId" with data
      | planCode             | getFromUISelected           |
      | status               | SELECTION_MADE              |
      | txnStatus            | SELECTION_MADE              |
      | channel              | PHONE                       |
      | medicalPlanBeginDate | newEnrollCutoffStartDateHCC |
      | medicalPlanEndDate   | future                      |
      | enrollmentType       | MEDICAL                     |
      | selectionReason      | 02                          |
      | createdOn            | current                     |
      | createdBy            | 3778                        |


  @CP-32722 @CP-32722-10 @ui-ee-in @IN-EB-UI-Regression @kursat
  Scenario:  CP-32722 Consumer Submits Plan Change Pre-Lockin (HHW, HCC) with Eligibility Non-High End Date
  CP-32722 10.0 Consumer Submits Plan Change Pre-Lockin (HHW, HCC)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 32722-5 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-5.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | future               |
      | txnStatus                    | Accepted             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 32722-5.consumerId    |
      | startDate         | 1stDayofPresentMonth  |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-5.consumerId      |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | future                  |
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
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 32722-6 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-6.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | future               |
      | txnStatus                    | Accepted             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 32722-6.consumerId    |
      | startDate         | 1stDayofPresentMonth  |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-6.consumerId      |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | future                  |
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
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 32722-7 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-7.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | yes                  |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | future               |
      | txnStatus                    | Accepted             |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 32722-7.consumerId    |
      | startDate         | 1stDayofPresentMonth  |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-7.consumerId      |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | future                  |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | enrollmentEndDate            | lastDayOfThePresentYear |
      | txnStatus                    | Accepted                |
      | programCode                  | A                       |
      | subProgramTypeCode           | HoosierCareConnect      |
      | eligibilityStatusCode        | M                       |
      | planCode                     | 399243310               |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 32722-8 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-8.consumerId      |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | yes                     |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | lastDayOf3MonthsFromNow |
      | txnStatus                    | Accepted                |
      | programCode                  | R                       |
      | subProgramTypeCode           | HoosierHealthwise       |
      | eligibilityStatusCode        | M                       |
      | categoryCode                 | 10                      |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 32722-8.consumerId    |
      | startDate         | 1stDayofPresentMonth  |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-8.consumerId      |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | lastDayOf3MonthsFromNow |
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
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "32722-5.firstName" and Last Name as "32722-5.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    # A 1.0 Navigating to Plan Change functionality (No Change)
    And I click "PLAN CHANGE" Button for a consumer first name "32722-5.firstName" and last name "32722-5.lastName"
    Then I verify I am still on "Enrollment Update" Page
    # A 2.0 Adding case member(s)
    When I click Add Case Members Button
    Then I verify I only see next other consumers in the dropdown list
      | 32722-6 |
      # consumer 32722-7 is not in the list because in DIFFERENT sub-program
      # consumer 32722-8 is not in the list because has different eligibility end date
    When I click Add Case Members with First Name as "32722-6.firstName" and Last Name as "32722-6.lastName"
    # A 2.0 Adding case member(s)
    Then I verify  the Add Case Members button is disable
    # A 3.0 Reviewing available plans
    # There is a defect CP-34087 in plans (ADVANTAGE HEALTH SOLUTIONS INC, MDWISE CS, OPT IN DECLINED should not be displayed). Commented out until defect is fixed
    Then I verify list of all available plans on Enrollment Update Page with data
      | ANTHEM                  |
      | CARESOURCE              |
      | MANAGED HEALTH SERVICES |
      | MDWISE HH               |
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | START DATE | cutoffStartDateHHWUIver |
      | END DATE   | futureUIver             |
      | PLAN TYPE  | Medical                 |
   # B 1.1 Select plan (No Change)
    When I select A second plan from Available Plans
    Then I verify below details on newly created current enrollment segment on Enrollment Update Page
      | SELECTED CONSUMER NAMES | 32722-5,32722-6         |
      # B 1.0 Viewing selected Plan Change option (No Change)
      | PLAN NAME               | selectedPlanName        |
      | PLAN TYPE               | Medical                 |
      | SERVICE REGION          | hidden                  |
      | START DATE              | cutoffStartDateHHWUIver |
      | END DATE                | futureUIver             |
    # B 1.2 Remove plan (No Change)
    And I click on Remove Plan Option
    Then I verify I am still on "Enrollment Update" Page
    Then I verify "PCP SELECT BUTTON" is not displayed
    When I select A plan from Available Plans
    # B 1.3 Require Reason for Plan Change (No Change)
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    # 4.0 Program & Benefit Info screen - After Clicking “SUBMIT” Button
    Then I verify I am still on "PROGRAM & BENEFIT INFO" Page
    Then I verify program & benefit info page for consumer first name "32722-5.firstName" and last name "32722-5.lastName" with data
      | CURRENT ENROLLMENT.PLAN NAME          | ANTHEM                     |
      | CURRENT ENROLLMENT.SELECTION STATUS   | Disenroll Requested        |
      | CURRENT ENROLLMENT.CHANNEL            | System Integration         |
      | CURRENT ENROLLMENT.PCP NAME           | -- --                      |
      | CURRENT ENROLLMENT.START DATE         | 1stDayofPresentMonthUIver  |
      | CURRENT ENROLLMENT.END DATE           | lastDayofPresentMonthUIver |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON  | hidden                     |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                     |
      #****************************
      | FUTURE ENROLLMENT.PLAN NAME           | selectedPlanName           |
      | FUTURE ENROLLMENT.SELECTION STATUS    | Selection Made             |
      | FUTURE ENROLLMENT.CHANNEL             | Phone                      |
      | FUTURE ENROLLMENT.START DATE          | cutoffStartDateHHWUIver    |
      | FUTURE ENROLLMENT.END DATE            | futureUIver                |
      | FUTURE ENROLLMENT.EDIT BUTTON         | displayed                  |
      | FUTURE ENROLLMENT.DISREGARD BUTTON    | displayed                  |
    # 3.0 Create New Enroll Enrollment Segment
    And I verify latest enrollment by consumer id "32722-5.consumerId" with data
      | planCode             | getFromUISelected  |
      | txnStatus            | SELECTION_MADE     |
      | channel              | PHONE              |
      | medicalPlanBeginDate | cutoffStartDateHHW |
      | medicalPlanEndDate   | future             |
      | enrollmentType       | MEDICAL            |
      | selectionReason      | 01                 |
      | status               | SELECTION_MADE     |
      | createdOn            | current            |
      | createdBy            | 3778               |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | 32722-5.consumerId    |
      | consumerFound | true                  |
      | DPBI          |[blank]|


  @CP-32722 @CP-32722-11 @ui-ee-in @IN-EB-UI-Regression @kursat
  Scenario: CP-32722 Consumer Submits Plan Change Anniversary (HHW, HCC) with Eligibility Non-High End Date
  CP-32722 11.0 Consumer Submits Plan Change Anniversary (HHW, HCC)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 32722-9 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-9.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof6MonthsBefore |
      | eligibilityStartDate         | 1stDayof6MonthsBefore |
      | eligibilityEndDate           | future                |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other enrollment segments details
      | recordId          | 21                             |
      | consumerId        | 32722-9.consumerId             |
      | startDate         | 90DaysBeforeLastDayofNextMonth |
      | endDate           | lastDayofNextMonth             |
      | genericFieldText1 | C                              |
      | genericFieldDate1 | 1stDayofPresentMonth           |
      | segmentTypeCode   | OPEN_ENROLLMENT                |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-9.consumerId      |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayof6MonthsBefore   |
      | eligibilityEndDate           | future                  |
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
      | saveConsumerInfo | 32722-10 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-10.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof6MonthsBefore |
      | eligibilityStartDate         | 1stDayof6MonthsBefore |
      | eligibilityEndDate           | future                |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other enrollment segments details
      | recordId          | 21                             |
      | consumerId        | 32722-10.consumerId            |
      | startDate         | 90DaysBeforeLastDayofNextMonth |
      | endDate           | lastDayofNextMonth             |
      | genericFieldText1 | C                              |
      | genericFieldDate1 | 1stDayofPresentMonth           |
      | segmentTypeCode   | OPEN_ENROLLMENT                |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-10.consumerId     |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayof6MonthsBefore   |
      | eligibilityEndDate           | future                  |
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
      | saveConsumerInfo | 32722-11 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-11.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof6MonthsBefore |
      | eligibilityStartDate         | 1stDayof6MonthsBefore |
      | eligibilityEndDate           | future                |
      | txnStatus                    | Accepted              |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other enrollment segments details
      | recordId          | 21                             |
      | consumerId        | 32722-11.consumerId            |
      | startDate         | 90DaysBeforeLastDayofNextMonth |
      | endDate           | lastDayofNextMonth             |
      | genericFieldText1 | C                              |
      | genericFieldDate1 | 1stDayofPresentMonth           |
      | segmentTypeCode   | OPEN_ENROLLMENT                |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-11.consumerId     |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayof6MonthsBefore   |
      | eligibilityEndDate           | future                  |
      | enrollmentStartDate          | 1stDayof6MonthsBefore   |
      | enrollmentEndDate            | lastDayOfThePresentYear |
      | txnStatus                    | Accepted                |
      | programCode                  | A                       |
      | subProgramTypeCode           | HoosierCareConnect      |
      | eligibilityStatusCode        | M                       |
      | planCode                     | 399243310               |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 32722-12 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-12.consumerId   |
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
      | recordId          | 21                             |
      | consumerId        | 32722-12.consumerId            |
      | startDate         | 90DaysBeforeLastDayofNextMonth |
      | endDate           | lastDayofNextMonth             |
      | genericFieldText1 | C                              |
      | genericFieldDate1 | 1stDayofPresentMonth           |
      | segmentTypeCode   | OPEN_ENROLLMENT                |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-12.consumerId     |
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
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "32722-9.firstName" and Last Name as "32722-9.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "32722-9.firstName" and last name "32722-9.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT         | OPEN ENROLLMENT-ANNIVERSARY                                   |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | 90DaysBeforeLastDayofNextMonthUIver - lastDayofNextMonthUIver |
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | lastDayofNextMonthUIver                                       |
      | COUNTDOWN.ICON HOVER                    | Calendar days left in Anniversary period                      |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HHW-Mandatory                                                 |
      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18                                             |
      | CURRENT ELIGIBILITY.RCP                 | No                                                            |
      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                                                        |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayof6MonthsBeforeUIver                                    |
      | CURRENT ELIGIBILITY.END DATE            | futureUIver                                                   |
      | CURRENT ENROLLMENT.PLAN NAME            | ANTHEM                                                        |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Accepted                                                      |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration                                            |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                                                         |
      | CURRENT ENROLLMENT.START DATE           | 1stDayof6MonthsBeforeUIver                                    |
      | CURRENT ENROLLMENT.END DATE             | lastDayOfThePresentYearUIver                                  |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | is displayed                                                  |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | is displayed                                                  |
    Then I verify program & benefit info page for consumer first name "32722-11.firstName" and last name "32722-11.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT         | OPEN ENROLLMENT-ANNIVERSARY                                   |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | 90DaysBeforeLastDayofNextMonthUIver - lastDayofNextMonthUIver |
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | lastDayofNextMonthUIver                                       |
      | COUNTDOWN.ICON HOVER                    | Calendar days left in Anniversary period                      |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HCC-Mandatory                                                 |
      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18                                             |
      | CURRENT ELIGIBILITY.RCP                 | No                                                            |
      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                                                        |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayof6MonthsBeforeUIver                                    |
      | CURRENT ELIGIBILITY.END DATE            | futureUIver                                                   |
      | CURRENT ENROLLMENT.PLAN NAME            | MANAGED HEALTH SERVICES HCC                                   |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Accepted                                                      |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration                                            |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                                                         |
      | CURRENT ENROLLMENT.START DATE           | 1stDayof6MonthsBeforeUIver                                    |
      | CURRENT ENROLLMENT.END DATE             | lastDayOfThePresentYearUIver                                  |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | is displayed                                                  |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | is displayed                                                  |
    And I click "PLAN CHANGE" Button for a consumer first name "32722-9.firstName" and last name "32722-9.lastName"
    Then I verify I am still on "Enrollment Update" Page
    Then I verify "PCP SELECT BUTTON" is not displayed
    # A 2.1 Adding case member(s): DIFFERENT PROGRAM (No Change)
    When I click Add Case Members Button
    Then I verify I only see next other consumers in the dropdown list
      | 32722-10 |
      # consumer 32722-12 is not in the list because has different eligibility end date
      # consumer 32722-11 is not in list because in DIFFERENT sub-program
    When I click Add Case Members with First Name as "32722-10.firstName" and Last Name as "32722-10.lastName"
    Then I verify  the Add Case Members button is disable
    # A 3.0 Review plans available
    # There is a defect CP-34087 in plans (ADVANTAGE HEALTH SOLUTIONS INC, MDWISE CS, OPT IN DECLINED should not be displayed). Commented out until defect is fixed
#    Then I verify list of all available plans on Enrollment Update Page with data
#      | ANTHEM                  |
#      | CARESOURCE              |
#      | MANAGED HEALTH SERVICES |
#      | MDWISE HH               |
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | PLAN TYPE  | Medical                     |
      | START DATE | 1stDayof2MonthsFromNowUIver |
      | END DATE   | futureUIver                 |
    When I select A plan from Available Plans
    #B 1.0 Viewing selected Plan Change option (No Change)
    Then I verify below details on newly created current enrollment segment on Enrollment Update Page
      | SELECTED CONSUMER NAMES | 32722-9,32722-10                         |
      | AGE/GENDER              | 20/Female                                |
      | POPULATION              | HHW-Mandatory                            |
      | RESIDENTIAL ADDRESS     | 22 main st , Preble Indiana 46782-4345   |
      | CURRENT PLAN            | ANTHEM                                   |
      | CURRENT PROVIDER        | -- --                                    |
      | ELIGIBILITY DETAILS     | 1stDayof6MonthsBeforeUIver - futureUIver |
      | PLAN NAME               | selectedPlanName                         |
      | PLAN TYPE               | Medical                                  |
      | SERVICE REGION          | hidden                                   |
      | START DATE              | 1stDayof2MonthsFromNowUIver              |
      | END DATE                | futureUIver                              |
    Then I verify "PCP SELECT BUTTON" is not displayed
    # B 2.0 Require Reason for Plan Change (No Change)
    Then I verify "SUBMIT" button is not clickable
    Then I verify I am still on "ENROLLMENT UPDATE" Page
    And I click "Reason" Button for a consumer
    And I verify bellow fields are displayed on "drop down reason" section:
      | 01 - Approved Change |
    And I click "Reason" Button for a consumer
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "32722-9.firstName" and last name "32722-9.lastName" with data
    # B 3.0 Update each consumer’s prior enrollment segment (No Change)
      | CURRENT ENROLLMENT.END DATE           | lastDayofNextMonthUIver     |
      | CURRENT ENROLLMENT.SELECTION STATUS   | Disenroll Requested         |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                      |
      # B 4.0 Create each consumer’s new (requested) enrollment segment
      | FUTURE ENROLLMENT.SELECTION STATUS    | Selection Made              |
      | FUTURE ENROLLMENT.START DATE          | 1stDayof2MonthsFromNowUIver |
      | FUTURE ENROLLMENT.END DATE            | futureUIver                 |
      | FUTURE ENROLLMENT.EDIT BUTTON         | displayed                   |
      | FUTURE ENROLLMENT.DISREGARD BUTTON    | displayed                   |
    # B 3.0 Update each consumer’s prior enrollment segment (No Change)
    And I verify enrollment by consumer id "32722-9.consumerId" with data
      | medicalPlanEndDate   | lastDayofNextMonth    |
      | planEndDateReason    | 01                    |
      | updatedOn            | current               |
      | updatedBy            | 3778                  |
      | txnStatus            | DISENROLL_REQUESTED   |
      | medicalPlanBeginDate | 1stDayof6MonthsBefore |
      | enrollmentType       | MEDICAL               |
      | channel              | SYSTEM_INTEGRATION    |
      | selectionReason      | null                  |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | 32722-9.consumerId      |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    # B 4.0 Create each consumer’s new (requested) enrollment segment
    And Wait for 5 seconds
    And I verify latest enrollment by consumer id "32722-9.consumerId" with data
      | medicalPlanBeginDate | 1stDayof2MonthsFromNow |
      | medicalPlanEndDate   | future                 |
      | enrollmentType       | MEDICAL                |
      | channel              | PHONE                  |
      | txnStatus            | SELECTION_MADE         |
      | selectionReason      | 94                     |
      | planEndDateReason    | null                   |
      | createdOn            | current                |
      | updatedOn            | current                |
      | createdBy            | 3778                   |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | 32722-9.consumerId    |
      | consumerFound | true                  |
      | DPBI          |[blank]|
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I save first contactId with name "32722-9a"
    Then I will verify business events are generated with data
    # Plan Change Business Event Required Fields
      | eventName                              | PLAN_CHANGE                      |
      | channel                                | PHONE                            |
      | createdBy                              | 3778                             |
      | processDate                            | current                          |
      | externalCaseId                         | 32722-9.caseIdentificationNumber |
      | externalConsumerId                     | 32722-9.externalConsumerId       |
      | consumerId                             | 32722-9.consumerId               |
      | consumerName                           | 32722-9                          |
      # Plan Change Business Event Optional Enrollment fields from “Prior Enrollment”
      | previousEnrollment.enrollmentStartDate | 1stDayof6MonthsBefore            |
      | previousEnrollment.enrollmentEndDate   | lastDayofNextMonth               |
      | previousEnrollment.planSelectionReason | null                             |
      | previousEnrollment.planChangeReason    | 01                               |
      | previousEnrollment.rejectionReason     | null                             |
      | previousEnrollment.planCode            | 400752220                        |
      | previousEnrollment.enrollmentType      | MEDICAL                          |
      | previousEnrollment.selectionStatus     | DISENROLL_REQUESTED              |
      | previousEnrollment.anniversaryDate     | anniversaryDate                  |
      | previousEnrollment.pcpNpi              | null                             |
      | previousEnrollment.pcpName             | null                             |
      # Plan Change Business Event Optional Enrollment fields from “Requested Enrollment”
      | contactId                              | 32722-9a.contactId               |
      | requestedSelection.enrollmentStartDate | 1stDayof2MonthsFromNow           |
      | requestedSelection.enrollmentEndDate   | future                           |
      | requestedSelection.planSelectionReason | 94                               |
      | requestedSelection.planChangeReason    | null                             |
      | requestedSelection.rejectionReason     | null                             |
      | requestedSelection.planCode            | getFromUISelected                |
      | requestedSelection.enrollmentType      | MEDICAL                          |
      | requestedSelection.selectionStatus     | SELECTION_MADE                   |
      | requestedSelection.anniversaryDate     | anniversaryDate                  |
      | requestedSelection.pcpNpi              | null                             |
      | requestedSelection.pcpName             | null                             |


  @CP-32722 @CP-32722-12 @CP-34087 @ui-ee-in @IN-EB-UI-Regression @kursat
  Scenario: CP-32722 Consumer Submits Free Change Plan Change (HHW, HCC) with Eligibility Non-High End Date
  CP-32722 12.0 Consumer Submits Free Change Plan Change (HHW, HCC)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 32722-13 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-13.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | future                |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 32722-13.consumerId   |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-13.consumerId   |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | enrollmentRecordId           | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | future                |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | future                |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | planCode                     | 400752220             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And I run create Eligibility and Enrollment API
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 32722-13.consumerId  |
      | [0].planId             | 26                   |
      | [0].planCode           | 700410350            |
      | [0].startDate          | cutoffStartDateHHW:: |
      | [0].endDate            | future::             |
      | [0].subProgramTypeCode | HoosierHealthwise    |
      | [0].serviceRegionCode  | Statewide            |
      | [0].selectionReason    | 02                   |
      | [0].anniversaryDate    | anniversaryDate::    |
      | [0].channel            | SYSTEM_INTEGRATION   |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "32722-13.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "32722-13a"
    And I send API call with name "" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 32722-13.consumerId                |
      | [0].planId             | delete::                           |
      | [0].planCode           | 400752220                          |
      | [0].enrollmentId       | 32722-13a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED                |
      | [0].txnStatus          | DISENROLL_SUBMITTED                |
      | [0].startDate          | 1stDayof2MonthsBefore::            |
      | [0].endDate            | DayBeforeCutoffStartDateHHW::      |
      | [0].enrollmentType     | delete::                           |
      | [0].subProgramTypeCode | HoosierHealthwise                  |
      | [0].serviceRegionCode  | Statewide                          |
      | [1].planId             | delete::                           |
      | [1].planCode           | 700410350                          |
      | [1].consumerId         | 32722-13.consumerId                |
      | [1].enrollmentId       | 32722-13a.selectedEnrollmentId     |
      | [1].startDate          | cutoffStartDateHHW::               |
      | [1].endDate            | future::                           |
      | [1].subProgramTypeCode | HoosierHealthwise                  |
      | [1].serviceRegionCode  | Statewide                          |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-13.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | isEnrollmentProviderRequired | no                  |
      | recordId                     | 17                  |
      | enrollmentRecordId           | 17                  |
      | enrollmentStartDate          | cutoffStartDateHHW  |
      | enrollmentEndDate            | future              |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | subProgramTypeCode           | HoosierHealthwise   |
      | planCode                     | 700410350           |
      | planId                       | 26                  |
      | serviceRegionCode            | Statewide           |
      | channel                      | SYSTEM_INTEGRATION  |
    And User provide other enrollment segments details
      | startDate         | cutoffStartDateHHW             |
      | endDate           | future                         |
      | genericFieldText1 | 32722-13a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                      |
    And I run create Eligibility and Enrollment API
    #****************************************************
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 32722-14 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-14.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | future                |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | future                  |
      | segmentDetailValue1 | 1                       |
      | segmentDetailValue2 | 6                       |
      | segmentDetailValue3 | 1stDayofPresentMonth    |
      | segmentDetailValue4 | lastDayOfThePresentYear |
      | segmentTypeCode     | OTHER                   |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 32722-14.consumerId   |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-14.consumerId   |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | enrollmentRecordId           | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | future                |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | future                |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | planCode                     | 400752220             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And I run create Eligibility and Enrollment API
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 32722-14.consumerId  |
      | [0].planId             | 26                   |
      | [0].planCode           | 700410350            |
      | [0].startDate          | cutoffStartDateHHW:: |
      | [0].endDate            | future::             |
      | [0].subProgramTypeCode | HoosierHealthwise    |
      | [0].serviceRegionCode  | Statewide            |
      | [0].selectionReason    | 02                   |
      | [0].anniversaryDate    | anniversaryDate::    |
      | [0].channel            | SYSTEM_INTEGRATION   |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "32722-14.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "32722-14a"
    And I send API call with name "" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 32722-14.consumerId                |
      | [0].planId             | delete::                           |
      | [0].planCode           | 400752220                          |
      | [0].enrollmentId       | 32722-14a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED                |
      | [0].txnStatus          | DISENROLL_SUBMITTED                |
      | [0].startDate          | 1stDayof2MonthsBefore::            |
      | [0].endDate            | DayBeforeCutoffStartDateHHW::      |
      | [0].enrollmentType     | delete::                           |
      | [0].subProgramTypeCode | HoosierHealthwise                  |
      | [0].serviceRegionCode  | Statewide                          |
      | [1].planId             | delete::                           |
      | [1].planCode           | 700410350                          |
      | [1].consumerId         | 32722-14.consumerId                |
      | [1].enrollmentId       | 32722-14a.selectedEnrollmentId     |
      | [1].startDate          | cutoffStartDateHHW::               |
      | [1].endDate            | future::                           |
      | [1].subProgramTypeCode | HoosierHealthwise                  |
      | [1].serviceRegionCode  | Statewide                          |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-14.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | isEnrollmentProviderRequired | no                  |
      | recordId                     | 17                  |
      | enrollmentRecordId           | 17                  |
      | enrollmentStartDate          | cutoffStartDateHHW  |
      | enrollmentEndDate            | future              |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | subProgramTypeCode           | HoosierHealthwise   |
      | planCode                     | 700410350           |
      | planId                       | 26                  |
      | serviceRegionCode            | Statewide           |
      | channel                      | SYSTEM_INTEGRATION  |
    And User provide other enrollment segments details
      | startDate         | cutoffStartDateHHW             |
      | endDate           | future                         |
      | genericFieldText1 | 32722-14a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                      |
    And I run create Eligibility and Enrollment API
    #********************************************
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 32722-15 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-15.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | highDate                |
      | segmentDetailValue1 | 1                       |
      | segmentDetailValue2 | 6                       |
      | segmentDetailValue3 | 1stDayofPresentMonth    |
      | segmentDetailValue4 | lastDayOfThePresentYear |
      | segmentTypeCode     | OTHER                   |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 32722-15.consumerId   |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-15.consumerId   |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | enrollmentRecordId           | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | planCode                     | 400752220             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And I run create Eligibility and Enrollment API
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 32722-15.consumerId  |
      | [0].planId             | 26                   |
      | [0].planCode           | 700410350            |
      | [0].startDate          | cutoffStartDateHHW:: |
      | [0].endDate            | highDate::           |
      | [0].subProgramTypeCode | HoosierHealthwise    |
      | [0].serviceRegionCode  | Statewide            |
      | [0].selectionReason    | 02                   |
      | [0].anniversaryDate    | anniversaryDate::    |
      | [0].channel            | SYSTEM_INTEGRATION   |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "32722-15.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "32722-15a"
    And I send API call with name "" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 32722-15.consumerId                |
      | [0].planId             | delete::                           |
      | [0].planCode           | 400752220                          |
      | [0].enrollmentId       | 32722-15a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED                |
      | [0].txnStatus          | DISENROLL_SUBMITTED                |
      | [0].startDate          | 1stDayof2MonthsBefore::            |
      | [0].endDate            | DayBeforeCutoffStartDateHHW::      |
      | [0].enrollmentType     | delete::                           |
      | [0].subProgramTypeCode | HoosierHealthwise                  |
      | [0].serviceRegionCode  | Statewide                          |
      | [1].planId             | delete::                           |
      | [1].planCode           | 700410350                          |
      | [1].consumerId         | 32722-15.consumerId                |
      | [1].enrollmentId       | 32722-15a.selectedEnrollmentId     |
      | [1].startDate          | cutoffStartDateHHW::               |
      | [1].endDate            | highDate::                         |
      | [1].subProgramTypeCode | HoosierHealthwise                  |
      | [1].serviceRegionCode  | Statewide                          |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-15.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | isEnrollmentProviderRequired | no                  |
      | recordId                     | 17                  |
      | enrollmentRecordId           | 17                  |
      | enrollmentStartDate          | cutoffStartDateHHW  |
      | enrollmentEndDate            | highDate            |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | subProgramTypeCode           | HoosierHealthwise   |
      | planCode                     | 700410350           |
      | planId                       | 26                  |
      | serviceRegionCode            | Statewide           |
      | channel                      | SYSTEM_INTEGRATION  |
    And User provide other enrollment segments details
      | startDate         | cutoffStartDateHHW             |
      | endDate           | highDate                       |
      | genericFieldText1 | 32722-15a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                      |
    And I run create Eligibility and Enrollment API
    And I send independent API call with name "" to create other enrollment segment with data
      | consumerId      | 32722-15.consumerId   |
      | startDate       | 1stDayof2MonthsBefore |
      | endDate         | highDate              |
      | segmentTypeCode | LILO                  |
    #**********************************************
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 32722-16 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-16.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | future                |
      | txnStatus                    | Accepted              |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | future                  |
      | segmentDetailValue1 | 1                       |
      | segmentDetailValue2 | 6                       |
      | segmentDetailValue3 | 1stDayofPresentMonth    |
      | segmentDetailValue4 | lastDayOfThePresentYear |
      | segmentTypeCode     | OTHER                   |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 32722-16.consumerId   |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-16.consumerId   |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | enrollmentRecordId           | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | future                |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | future                |
      | txnStatus                    | Accepted              |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | eligibilityStatusCode        | M                     |
      | planCode                     | 499254630             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And I run create Eligibility and Enrollment API
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 32722-16.consumerId  |
      | [0].planId             | 26                   |
      | [0].planCode           | 399243310            |
      | [0].startDate          | cutoffStartDateHHW:: |
      | [0].endDate            | future::             |
      | [0].subProgramTypeCode | HoosierCareConnect   |
      | [0].serviceRegionCode  | Statewide            |
      | [0].selectionReason    | 02                   |
      | [0].anniversaryDate    | anniversaryDate::    |
      | [0].channel            | SYSTEM_INTEGRATION   |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "32722-16.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "32722-16a"
    And I send API call with name "" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 32722-16.consumerId                |
      | [0].planId             | delete::                           |
      | [0].planCode           | 499254630                          |
      | [0].enrollmentId       | 32722-16a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED                |
      | [0].txnStatus          | DISENROLL_SUBMITTED                |
      | [0].startDate          | 1stDayof2MonthsBefore::            |
      | [0].endDate            | DayBeforeCutoffStartDateHHW::      |
      | [0].enrollmentType     | delete::                           |
      | [0].subProgramTypeCode | HoosierCareConnect                 |
      | [0].serviceRegionCode  | Statewide                          |
      | [1].planId             | delete::                           |
      | [1].planCode           | 399243310                          |
      | [1].consumerId         | 32722-16.consumerId                |
      | [1].enrollmentId       | 32722-16a.selectedEnrollmentId     |
      | [1].startDate          | cutoffStartDateHHW::               |
      | [1].endDate            | future::                           |
      | [1].subProgramTypeCode | HoosierCareConnect                 |
      | [1].serviceRegionCode  | Statewide                          |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-16.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | isEnrollmentProviderRequired | no                  |
      | recordId                     | 17                  |
      | enrollmentRecordId           | 17                  |
      | enrollmentStartDate          | cutoffStartDateHHW  |
      | enrollmentEndDate            | future              |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted            |
      | programCode                  | A                   |
      | subProgramTypeCode           | HoosierCareConnect  |
      | planCode                     | 399243310           |
      | planId                       | 26                  |
      | serviceRegionCode            | Statewide           |
      | channel                      | SYSTEM_INTEGRATION  |
    And User provide other enrollment segments details
      | startDate         | cutoffStartDateHHW             |
      | endDate           | future                         |
      | genericFieldText1 | 32722-16a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                      |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "32722-13.firstName" and Last Name as "32722-13.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "32722-13.firstName" and last name "32722-13.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT         | FREE CHANGE - WINDOW                               |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | 1stDayof2MonthsBeforeUIver - lastDayofpresentMonth |
      | COUNTDOWN.ICON HOVER                    | Calendar days left to plan Change                  |
      # Viewing the current eligibility segment
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HHW-Mandatory                                      |
      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18                                  |
      | CURRENT ELIGIBILITY.RCP                 | No                                                 |
      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                                             |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayof2MonthsBeforeUIver                         |
      | CURRENT ELIGIBILITY.END DATE            | futureUIver                                        |
      # Viewing the current enrollment segment
      | CURRENT ENROLLMENT.PLAN NAME            | ANTHEM                                             |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Disenrolled                                        |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration                                 |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                                              |
      | CURRENT ENROLLMENT.START DATE           | 1stDayof2MonthsBeforeUIver                         |
      | CURRENT ENROLLMENT.END DATE             | lastDayofpresentMonth                              |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | hidden                                             |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | is displayed                                       |
      # Viewing the current enrollment segment
      | FUTURE ENROLLMENT.PLAN NAME             | CARESOURCE                                         |
      | FUTURE ENROLLMENT.SELECTION STATUS      | Accepted                                           |
      | FUTURE ENROLLMENT.CHANNEL               | System Integration                                 |
      | FUTURE ENROLLMENT.PCP NAME              | -- --                                              |
      | FUTURE ENROLLMENT.START DATE            | firstdayofNextMonth                                |
      | FUTURE ENROLLMENT.END DATE              | futureUIver                                        |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON     | is displayed                                       |
      | FUTURE ENROLLMENT.PLAN CHANGE BUTTON    | is displayed                                       |
    Then I verify program & benefit info page for consumer first name "32722-15.firstName" and last name "32722-15.lastName" with data
      # Viewing the consumer info row
      | CALENDAR ICON HOVER.ACTION TEXT         | FREE CHANGE - WINDOW                               |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | 1stDayof2MonthsBeforeUIver - lastDayofpresentMonth |
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | lastDayofpresentMonth                              |
      | COUNTDOWN.ICON HOVER                    | Calendar days left to plan Change                  |
      # iewing the current eligibility segment
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HHW-Mandatory                                      |
      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18                                  |
      | CURRENT ELIGIBILITY.RCP                 | Yes                                                |
      | CURRENT ELIGIBILITY.SERVICE REGION      | hidden                                             |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayof2MonthsBeforeUIver                         |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver                                      |
      # Viewing the current enrollment segment
      | CURRENT ENROLLMENT.PLAN NAME            | ANTHEM                                             |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Disenrolled                                        |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration                                 |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                                              |
      | CURRENT ENROLLMENT.START DATE           | 1stDayof2MonthsBeforeUIver                         |
      | CURRENT ENROLLMENT.END DATE             | lastDayofpresentMonth                              |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | hidden                                             |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | hidden                                             |
      # Viewing the current enrollment segment
      | FUTURE ENROLLMENT.PLAN NAME             | CARESOURCE                                         |
      | FUTURE ENROLLMENT.SELECTION STATUS      | Accepted                                           |
      | FUTURE ENROLLMENT.CHANNEL               | System Integration                                 |
      | FUTURE ENROLLMENT.PCP NAME              | -- --                                              |
      | FUTURE ENROLLMENT.START DATE            | firstdayofNextMonth                                |
      | FUTURE ENROLLMENT.END DATE              | highDateUIver                                      |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON     | hidden                                             |
      | FUTURE ENROLLMENT.PLAN CHANGE BUTTON    | hidden                                             |
    # A 1.0 Navigating to Plan Change functionality (No Change)
    And I click future enrollment "PLAN CHANGE" Button for a consumer first name "32722-13.firstName" and last name "32722-13.lastName"
    Then I verify I am still on "Enrollment Update" Page
    Then I verify "PCP SELECT BUTTON" is not displayed
    # A 2.0 Adding case member(s)
    When I click Add Case Members Button
    Then I verify I only see next other consumers in the dropdown list
      | 32722-14 |
      # consumer 32722-15 is in SAME sub-program but different eligiblity end date
      # consumer 32722-16 is not in SAME sub-program
    When I click Add Case Members with First Name as "32722-14.firstName" and Last Name as "32722-14.lastName"
    Then I verify  the Add Case Members button is disable
    # A 2.1 Removing a case member from selection dropdown (No Change)
    When I click Remove Button for Case Member First Name as "32722-14.firstName" and Last Name as "32722-14.lastName"
    Then I verivy consumer by First Name as "32722-14.firstName" and Last Name as "32722-14.lastName" is not in plan selection
    When I click Add Case Members Button
    When I click Add Case Members with First Name as "32722-14.firstName" and Last Name as "32722-14.lastName"
    # A 3.0 Reviewing available plans
    # There is a defect CP-34087 in plans (ADVANTAGE HEALTH SOLUTIONS INC, MDWISE CS, OPT IN DECLINED should not be displayed). Commented out until defect is fixed
    Then I verify list of all available plans on Enrollment Update Page with data
      | ANTHEM                  |
      | CARESOURCE              |
      | MANAGED HEALTH SERVICES |
      | MDWISE HH               |
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | PLAN TYPE  | Medical                 |
      | START DATE | cutoffStartDateHHWUIver |
      | END DATE   | futureUIver             |
    Then I verify "PCP SELECT BUTTON" is not displayed
    # B 1.0 Viewing selected Free Change option (No Change)
    # B 1.1 Select plan (No Change)
    When I select A plan from Available Plans
    Then I verify below details on newly created current enrollment segment on Enrollment Update Page
      | SELECTED CONSUMER NAMES | 32722-13,32722-14                        |
      | AGE/GENDER              | 20/Female                                |
      | POPULATION              | HHW-Mandatory                            |
      | RESIDENTIAL ADDRESS     | 22 main st , Preble Indiana 46782-4345   |
      | CURRENT PLAN            | CARESOURCE                               |
      | CURRENT PROVIDER        | -- --                                    |
      | ELIGIBILITY DETAILS     | 1stDayof2MonthsBeforeUIver - futureUIver |
      | PLAN NAME               | selectedPlanName                         |
      | PLAN TYPE               | Medical                                  |
      | SERVICE REGION          | hidden                                   |
      | START DATE              | cutoffStartDateHHWUIver                  |
      | END DATE                | futureUIver                              |
    Then I verify "PCP SELECT BUTTON" is not displayed
    # B 1.2 Remove plan (Regression) (No Change)
    And I click on Remove Plan Option
    Then I verify I am still on "Enrollment Update" Page
    When I select A plan from Available Plans
    # B 1.3 Require Reason for Free Change (No Change)
    Then I verify "SUBMIT" button is not clickable
    Then I verify I am still on "ENROLLMENT UPDATE" Page
    And I click "Reason" Button for a consumer
    And I verify bellow fields are displayed on "drop down reason" section:
      | 01 - Approved Change |
    And I click "Reason" Button for a consumer
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    # B 4.0 Display Program & Benefit Info screen with saved changes after clicking “Submit” button (No Change)
    Then I verify program & benefit info page for consumer first name "32722-13.firstName" and last name "32722-13.lastName" with data
      | FUTURE ENROLLMENT.SELECTION STATUS                        | Selection Made                   |
      | FUTURE ENROLLMENT.START DATE                              | cutoffStartDateHHWUIver          |
      | FUTURE ENROLLMENT.END DATE                                | futureUIver                      |
      | FUTURE ENROLLMENT.EDIT BUTTON                             | displayed                        |
      | FUTURE ENROLLMENT.DISREGARD BUTTON                        | displayed                        |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.SELECTION STATUS | Disenroll Requested              |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.START DATE       | cutoffStartDateHHWUIver          |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.END DATE         | DayBeforeCutoffStartDateHHWUIver |
    And I initiated get enrollment by consumer id "32722-13.consumerId"
    When I run get enrollment api
    And I save FUTURE enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "32722-13b"
      # B 2.0 Update each consumer’s prior enrollment segment (No Change)
    And I verify enrollmentID "32722-13b.futureDiscontinuedEnrollmentId" by consumer id "32722-13.consumerId" with data
      | medicalPlanEndDate   | lastDayofPresentMonth |
      | planEndDateReason    | 01                    |
      | updatedBy            | 3778                  |
      | updatedOn            | current               |
      | txnStatus            | DISENROLL_REQUESTED   |
      | medicalPlanBeginDate | cutoffStartDateHHW    |
      | enrollmentType       | MEDICAL               |
      | selectionReason      | null                  |
      | channel              | SYSTEM_INTEGRATION    |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | 32722-13.consumerId     |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    # B 3.0 Create each consumer’s new (requested) enrollment segment
    And I verify enrollmentID "32722-13b.futureSelectedEnrollmentId" by consumer id "32722-13.consumerId" with data
      | medicalPlanBeginDate | cutoffStartDateHHW |
      | medicalPlanEndDate   | future             |
      | enrollmentType       | MEDICAL            |
      | channel              | PHONE              |
      | txnStatus            | SELECTION_MADE     |
      | selectionReason      | 01                 |
      | planEndDateReason    | null               |
      | createdOn            | current            |
      | createdBy            | 3778               |
      | updatedOn            | current            |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | 32722-13.consumerId   |
      | consumerFound | true                  |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      # Free Change Business Event Required Fields
      | eventName                              | FREE_CHANGE                       |
      | channel                                | PHONE                             |
      | createdBy                              | 3778                              |
      | processDate                            | current                           |
      | externalCaseId                         | 32722-13.caseIdentificationNumber |
      | externalConsumerId                     | 32722-13.externalConsumerId       |
      | consumerId                             | 32722-13.consumerId               |
      | consumerName                           | 32722-13                          |
      # Free Change Business Event Optional Enrollment fields from “Prior Enrollment”
      | previousEnrollment.enrollmentStartDate | cutoffStartDateHHW                |
      | previousEnrollment.enrollmentEndDate   | DayBeforeCutoffStartDateHHW       |
      | previousEnrollment.planSelectionReason | null                              |
      | previousEnrollment.planChangeReason    | 01                                |
      | previousEnrollment.rejectionReason     | null                              |
      | previousEnrollment.planCode            | 700410350                         |
      | previousEnrollment.enrollmentType      | MEDICAL                           |
      | previousEnrollment.selectionStatus     | DISENROLL_REQUESTED               |
      | previousEnrollment.anniversaryDate     | anniversaryDate                   |
      | previousEnrollment.pcpNpi              | null                              |
      | previousEnrollment.pcpName             | null                              |
      # Free Change Business Event Optional Enrollment fields from “Requested Enrollment”
      | requestedSelection.enrollmentStartDate | cutoffStartDateHHW                |
      | requestedSelection.enrollmentEndDate   | future                            |
      | requestedSelection.planSelectionReason | 01                                |
      | requestedSelection.planChangeReason    | null                              |
      | requestedSelection.rejectionReason     | null                              |
      | requestedSelection.planCode            | getFromUISelected                 |
      | requestedSelection.enrollmentType      | MEDICAL                           |
      | requestedSelection.selectionStatus     | SELECTION_MADE                    |
      | requestedSelection.anniversaryDate     | anniversaryDate                   |
      | requestedSelection.pcpNpi              | null                              |
      | requestedSelection.pcpName             | null                              |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | 32722-13.consumerId   |
      | correlationId | null                  |
      | consumerFound | true                  |
      | DPBI          |[blank]|


  @CP-32722 @CP-32722-13 @ui-ee-in @IN-EB-UI-Regression @kursat
  Scenario: CP-32722 Consumer Submits Plan Open Enrollment (HIP) with Eligibility Non-High End Date
  CP-32722 13.0 Consumer Submits Plan Open Enrollment (HIP)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 32722-17 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-17.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 21                  |
      | eligibilityRecordId          | 21                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | lastDayOfNextYear   |
      | txnStatus                    | Accepted            |
      | programCode                  | H                   |
      | subProgramTypeCode           | HealthyIndianaPlan  |
      | eligibilityStatusCode        | M                   |
      | categoryCode                 | 10                  |
      | coverageCode                 | cc01                |
      | genericFieldText1            | Eligible            |
    And User provide other eligibility segments details
      | startDate           | 1stDayofLastMonth       |
      | endDate             | lastDayOfNextYear       |
      | segmentDetailValue1 | 1                       |
      | segmentDetailValue2 | 6                       |
      | segmentDetailValue3 | 1stDayofPresentMonth    |
      | segmentDetailValue4 | lastDayOfThePresentYear |
      | segmentTypeCode     | OTHER                   |
    And User provide other enrollment segments details
      | recordId          | 21                               |
      | consumerId        | 32722-17.consumerId              |
      | startDate         | 90DaysPriorAnniversaryDueDateHCC |
      | endDate           | anniversaryDueDateHCC            |
      | genericFieldText1 | C                                |
      | genericFieldDate1 | openEnrollmentDay                |
      | segmentTypeCode   | OPEN_ENROLLMENT                  |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-17.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | recordId                     | 4                   |
      | enrollmentRecordId           | 4                   |
      | isEnrollmentProviderRequired | no                  |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | lastDayOfNextYear   |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | enrollmentEndDate            | lastDayOfNextYear   |
      | txnStatus                    | Accepted            |
      | programCode                  | H                   |
      | subProgramTypeCode           | HealthyIndianaPlan  |
      | eligibilityStatusCode        | M                   |
      | planCode                     | 455701400           |
      | planId                       | 33                  |
      | serviceRegionCode            | Statewide           |
      | anniversaryDate              | anniversaryDate     |
      | channel                      | SYSTEM_INTEGRATION  |
      | categoryCode                 | 10                  |
      | coverageCode                 | cc01                |
    And I run create Eligibility and Enrollment API
    And I update latest consumer actions by consumerId "32722-17.consumerId" with data
      | [0].action               | Available                   |
      | [0].consumerAction       | Plan Change Open Enrollment |
      | [0].planSelectionAllowed | true                        |
      | [0].changeAllowedFrom    | 1stDayofPresentMonth        |
      | [0].changeAllowedUntil   | 90DayFromFirstDayOfMonth    |
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 32722-18 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-18.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 21                  |
      | eligibilityRecordId          | 21                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | lastDayOfNextYear   |
      | txnStatus                    | Accepted            |
      | programCode                  | H                   |
      | subProgramTypeCode           | HealthyIndianaPlan  |
      | eligibilityStatusCode        | M                   |
      | categoryCode                 | 10                  |
      | coverageCode                 | cc01                |
      | genericFieldText1            | Eligible            |
    And User provide other eligibility segments details
      | startDate           | 1stDayofLastMonth       |
      | endDate             | lastDayOfNextYear       |
      | segmentDetailValue1 | 1                       |
      | segmentDetailValue2 | 6                       |
      | segmentDetailValue3 | 1stDayofPresentMonth    |
      | segmentDetailValue4 | lastDayOfThePresentYear |
      | segmentTypeCode     | OTHER                   |
    And User provide other enrollment segments details
      | recordId          | 21                               |
      | consumerId        | 32722-18.consumerId              |
      | startDate         | 90DaysPriorAnniversaryDueDateHCC |
      | endDate           | anniversaryDueDateHCC            |
      | genericFieldText1 | C                                |
      | genericFieldDate1 | openEnrollmentDay                |
      | segmentTypeCode   | OPEN_ENROLLMENT                  |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 32722-18.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | recordId                     | 4                   |
      | enrollmentRecordId           | 4                   |
      | isEnrollmentProviderRequired | no                  |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | lastDayOfNextYear   |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | enrollmentEndDate            | lastDayOfNextYear   |
      | txnStatus                    | Accepted            |
      | programCode                  | H                   |
      | subProgramTypeCode           | HealthyIndianaPlan  |
      | eligibilityStatusCode        | M                   |
      | planCode                     | 455701400           |
      | planId                       | 33                  |
      | serviceRegionCode            | Statewide           |
      | anniversaryDate              | anniversaryDate     |
      | channel                      | SYSTEM_INTEGRATION  |
      | categoryCode                 | 10                  |
      | coverageCode                 | cc01                |
    And I run create Eligibility and Enrollment API
    And I update latest consumer actions by consumerId "32722-18.consumerId" with data
      | [0].action               | Available                   |
      | [0].consumerAction       | Plan Change Open Enrollment |
      | [0].planSelectionAllowed | true                        |
      | [0].changeAllowedFrom    | 1stDayofPresentMonth        |
      | [0].changeAllowedUntil   | 90DayFromFirstDayOfMonth    |
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "32722-17.firstName" and Last Name as "32722-17.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    # A 1.0 Navigating to Plan Change (No Change)
    And I click "PLAN CHANGE" Button for a consumer first name "32722-17.firstName" and last name "32722-17.lastName"
    Then I verify I am still on "Enrollment Update" Page
    # A 2.0 NO Adding case member(s) (No Change)
    Then I verify "Add Case Members button" is not displayed
    # A 3.0 No Provider Search on Enrollment Update screen (No Change)
    Then I verify "PCP SELECT BUTTON" is not displayed
    # A 4.0 Reviewing available plans
    Then I verify list of all available plans on Enrollment Update Page with data
      | ANTHEM HIP                  |
      | CARESOURCE HIP              |
    #  | ICHIA ESP HIP               |
      | MANAGED HEALTH SERVICES HIP |
      | MDWISE AMERICHOICE HIP      |
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | PLAN TYPE  | Medical                 |
      | START DATE | firstDayOfNextYearUIver |
      | END DATE   | lastDayOfNextYearUIver  |
    # B 1.0 Select plan (No Change)
    When I select A second plan from Available Plans
    Then I verify I am still on "Enrollment Update" Page
    # B 1.1 Remove plan (No Change)
    And I click on Remove Plan Option
    Then I verify I am still on "Enrollment Update" Page
    When I select A plan from Available Plans
    # B 2.0 Require Reason for Plan Change (No Change)
    Then I verify "SUBMIT" button is not clickable
    Then I will be required to enter a “Reason” for the change
    And I click "Reason" Button for a consumer
    And I verify bellow fields are displayed on "drop down reason" section:
      | HIP Plan Change |
    And I click "Reason" Button for a consumer
    # B 3.0 Display Selected Plan(s)  (No Change)
    Then I verify below details on newly created current enrollment segment on Enrollment Update Page
      | PLAN NAME      | selectedPlanName        |
      | PLAN TYPE      | Medical                 |
      | SERVICE REGION | hidden                  |
      | START DATE     | firstDayOfNextYearUIver |
      | END DATE       | lastDayOfNextYearUIver  |
    # B 3.1 No Provider Search on Enrollment Update screen (No Change)
    Then I verify "PCP SELECT BUTTON" is not displayed
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    # B 6.0 Display Program & Benefit Info screen with saved changes after clicking “Submit” button (No Change)
    Then I verify I am still on "PROGRAM & BENEFIT INFO" Page
    Then I verify program & benefit info page for consumer first name "32722-17.firstName" and last name "32722-17.lastName" with data
      | CURRENT ENROLLMENT.SELECTION STATUS   | Disenroll Requested          |
      | CURRENT ENROLLMENT.END DATE           | lastDayOfThePresentYearUIver |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                       |
      | FUTURE ENROLLMENT.SELECTION STATUS    | Selection Made               |
      | FUTURE ENROLLMENT.START DATE          | firstDayOfNextYearUIver      |
      | FUTURE ENROLLMENT.END DATE            | lastDayOfNextYearUIver       |
      | FUTURE ENROLLMENT.EDIT BUTTON         | displayed                    |
      | FUTURE ENROLLMENT.DISREGARD BUTTON    | displayed                    |
    # B 4.0 Update each consumer’s prior enrollment segment (No Change)
    And I verify enrollment by consumer id "32722-17.consumerId" with data
      | medicalPlanEndDate   | lastDayOfThePresentYear |
      | planEndDateReason    | PC                      |
      | updatedOn            | current                 |
      | updatedBy            | 3778                    |
      | txnStatus            | DISENROLL_REQUESTED     |
      | medicalPlanBeginDate | 1stDayofLastMonth       |
      | enrollmentType       | MEDICAL                 |
      | channel              | SYSTEM_INTEGRATION      |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | 32722-17.consumerId     |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    # B 5.0 Create each consumer’s new (requested) enrollment segment
    And I verify latest enrollment by consumer id "32722-17.consumerId" with data
      | medicalPlanBeginDate | firstDayOfNextYear |
      | medicalPlanEndDate   | lastDayOfNextYear  |
      | enrollmentType       | MEDICAL            |
      | channel              | PHONE              |
      | planCode             | getFromUISelected  |
      | txnStatus            | SELECTION_MADE     |
      | selectionReason      | HT                 |
      | createdOn            | current            |
      | updatedOn            | current            |
      | createdBy            | 3778               |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | 32722-17.consumerId   |
      | consumerFound | true                  |
      | DPBI          |[blank]|