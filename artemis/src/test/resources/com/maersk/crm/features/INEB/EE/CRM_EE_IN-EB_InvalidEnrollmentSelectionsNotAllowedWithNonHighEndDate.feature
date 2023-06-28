Feature: IN-EB Do not allow invalid enrollment selections on consumers with non-high end dated eligibility


  @CP-33908 @ui-ee-in @crm-regression @IN-EB-UI-Regression @kursat
  Scenario: IN-EB Display Error message for each invalid enrollment selection - Enroll and Edit - HCC
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 33908-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33908-01.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayofLastMonth     |
      | eligibilityStartDate         | 1stDayofLastMonth     |
      | eligibilityEndDate           | lastDayofPresentMonth |
      | txnStatus                    | Accepted              |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 33908-01.consumerId   |
      | startDate         | 1stDayofLastMonth     |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
 #   ************** CONSUMER 2 **********************************************
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 33908-02 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33908-02.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 25thofLastMonth       |
      | eligibilityStartDate         | 25thofLastMonth       |
      | eligibilityEndDate           | lastDayofPresentMonth |
      | txnStatus                    | Accepted              |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 33908-02.consumerId   |
      | startDate         | 1stDayofLastMonth     |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
     #   ************ UI START ************************************************
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "33908-01.firstName" and Last Name as "33908-01.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "ENROLL" Button for a consumer first name "33908-01.firstName" and last name "33908-01.lastName"
    And Wait for 3 seconds
    When I click Add Case Members Button
    When I click Add Case Members with First Name as "33908-02.firstName" and Last Name as "33908-02.lastName"
    And I select A plan from Available Plans
    And I change start date to 4 month earlier to day 15 on calendar
    And I click submit button on enrollment update
    # 6.0 Display Error message for each invalid enrollment selection
    Then I verify enrollment selection dates are not valid message is displayed
    Then I verify enrollment selection dates are not valid warning message includes consumer "33908-01"
    Then I verify enrollment selection dates are not valid warning message includes consumer "33908-02"
    And I click "ENROLL" Button for a consumer first name "33908-01.firstName" and last name "33908-01.lastName"
    And Wait for 3 seconds
    When I click Add Case Members Button
    When I click Add Case Members with First Name as "33908-02.firstName" and Last Name as "33908-02.lastName"
    And I select A plan from Available Plans
    And I change start date to 2 month earlier to day 15 on calendar
    And I click submit button on enrollment update
    Then I verify enrollment selection dates are not valid message is displayed
    Then I verify enrollment selection dates are not valid warning message includes consumer "33908-02"
    # 3.0 Validate each selected consumer’s new enrollment selection when user clicks Submit button
    # 5.0 Navigate back to P&B screen
    And I click "ENROLL" Button for a consumer first name "33908-02.firstName" and last name "33908-02.lastName"
    And Wait for 2 seconds
    And I select A plan from Available Plans
    And I change start date to 2 month earlier to day 25 on calendar
    And I click submit button on enrollment update
    # 3.0 Validate each selected consumer’s new enrollment selection when user clicks Submit button
    And I click current enrollment "EDIT" Button for a consumer first name "33908-02.firstName" and last name "33908-02.lastName"
    And Wait for 2 seconds
    And I change start date to 2 month earlier to day 24 on calendar
    And I click submit button on enrollment update
    Then I verify enrollment selection dates are not valid message is displayed
    Then I verify enrollment selection dates are not valid warning message includes consumer "33908-02"


  @CP-33908 @ui-ee-in @crm-regression @IN-EB-UI-Regression @kursat
  Scenario: IN-EB Display Error message for each invalid enrollment selection - Plan Change - HHW
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 33908-03 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33908-03.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayofLastMonth     |
      | eligibilityStartDate         | 1stDayofLastMonth     |
      | eligibilityEndDate           | lastDayofPresentMonth |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 33908-03.consumerId   |
      | startDate         | 1stDayofLastMonth     |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33908-03.consumerId   |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | enrollmentRecordId           | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayofLastMonth     |
      | eligibilityEndDate           | lastDayofPresentMonth |
      | enrollmentStartDate          | 1stDayofLastMonth     |
      | enrollmentEndDate            | lastDayofPresentMonth |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | planCode                     | 400752220             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
    And I run create Eligibility and Enrollment API
 #   ************************************************************
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 33908-04 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33908-04.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayofPresentMonth  |
      | eligibilityStartDate         | 1stDayofPresentMonth  |
      | eligibilityEndDate           | lastDayofPresentMonth |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 33908-04.consumerId   |
      | startDate         | 1stDayofPresentMonth  |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33908-04.consumerId   |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | enrollmentRecordId           | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayofPresentMonth  |
      | eligibilityEndDate           | lastDayofPresentMonth |
      | enrollmentStartDate          | 1stDayofPresentMonth  |
      | enrollmentEndDate            | lastDayofPresentMonth |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | planCode                     | 400752220             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "33908-03.firstName" and Last Name as "33908-03.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
     #   ************************************************************
    And I click "PLAN CHANGE" Button for a consumer first name "33908-03.firstName" and last name "33908-03.lastName"
    And Wait for 3 seconds
    When I click Add Case Members Button
    When I click Add Case Members with First Name as "33908-04.firstName" and Last Name as "33908-04.lastName"
    And I select A plan from Available Plans
    And I change start date to 4 month earlier to day 15 on calendar
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    # 6.0 Display Error message for each invalid enrollment selection
    Then I verify enrollment selection dates are not valid message is displayed
    Then I verify enrollment selection dates are not valid warning message includes consumer "33908-03"
    Then I verify enrollment selection dates are not valid warning message includes consumer "33908-04"
    And I click "PLAN CHANGE" Button for a consumer first name "33908-03.firstName" and last name "33908-03.lastName"
    When I click Add Case Members Button
    When I click Add Case Members with First Name as "33908-04.firstName" and Last Name as "33908-04.lastName"
    And I select A plan from Available Plans
    And I change start date to 2 month earlier to day 15 on calendar
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    # 6.0 Display Error message for each invalid enrollment selection
    Then I verify enrollment selection dates are not valid message is displayed
    Then I verify enrollment selection dates are not valid warning message includes consumer "33908-04"
    # 4.0 Validate each selected consumer’s plan change selection when user clicks Submit button
    # 5.0 Navigate back to P&B screen
    Then I verify program & benefit info page for consumer first name "33908-03.firstName" and last name "33908-03.lastName" with data
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HHW-Mandatory              |
      | CURRENT ELIGIBILITY.AID CATEGORY        | Children age 0-18          |
      | CURRENT ELIGIBILITY.RCP                 | No                         |
      | CURRENT ELIGIBILITY.START DATE          | firstdayofLastMonth        |
      | CURRENT ELIGIBILITY.END DATE            | lastDayofPresentMonthUIver |
      #*********
      | CURRENT ENROLLMENT.PLAN NAME            | selectedPlanName           |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Selection Made             |
      | CURRENT ENROLLMENT.CHANNEL              | Phone                      |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                      |
      | CURRENT ENROLLMENT.START DATE           | 15thofLastMonthUIver       |
      | CURRENT ENROLLMENT.END DATE             | lastDayofPresentMonthUIver |
      | CURRENT ENROLLMENT.EDIT BUTTON          | is displayed               |
      | CURRENT ENROLLMENT.DISREGARD BUTTON     | is displayed               |
