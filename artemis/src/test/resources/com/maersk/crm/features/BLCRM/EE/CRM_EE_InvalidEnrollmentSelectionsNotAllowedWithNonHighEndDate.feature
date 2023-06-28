Feature: Baseline Do not allow invalid enrollment selections on consumers with non-high end dated eligibility

  @CP-33908 @ui-ee @crm-regression @kursat
  Scenario: Baseline Display Error message for each invalid enrollment selection - Enroll and Edit
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 33908-05 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33908-05.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 21                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
     #   ************** CONSUMER 2 **********************************************
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 33908-06 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33908-06.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 21                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 25thofLastMonth     |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
    #   ************ UI START ************************************************
    Given I logged into CRM and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "33908-05.firstName" and Last Name as "33908-05.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "ENROLL" Button for a consumer first name "33908-05.firstName" and last name "33908-05.lastName"
    And Wait for 3 seconds
    When I click Add Case Members Button
    When I click Add Case Members with First Name as "33908-06.firstName" and Last Name as "33908-06.lastName"
    And I select A plan from Available Plans
    And I change start date to 4 month earlier to day 15 on calendar
    And I click submit button on enrollment update
    # 6.0 Display Error message for each invalid enrollment selection
    Then I verify enrollment selection dates are not valid message is displayed
    Then I verify enrollment selection dates are not valid warning message includes consumer "33908-05"
    Then I verify enrollment selection dates are not valid warning message includes consumer "33908-06"
    And I click "ENROLL" Button for a consumer first name "33908-05.firstName" and last name "33908-05.lastName"
    And Wait for 3 seconds
    When I click Add Case Members Button
    When I click Add Case Members with First Name as "33908-06.firstName" and Last Name as "33908-06.lastName"
    And I select A plan from Available Plans
    And I change start date to 2 month earlier to day 15 on calendar
    And I click submit button on enrollment update
    Then I verify enrollment selection dates are not valid message is displayed
    Then I verify enrollment selection dates are not valid warning message includes consumer "33908-06"
    # 3.0 Validate each selected consumer’s new enrollment selection when user clicks Submit button
    # 5.0 Navigate back to P&B screen
    Then I verify program & benefit info page for consumer first name "33908-05.firstName" and last name "33908-05.lastName" with data
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | Medicaid-General Population |
      | CURRENT ELIGIBILITY.SERVICE REGION      | East                        |
      | CURRENT ELIGIBILITY.START DATE          | firstdayofLastMonth         |
      | CURRENT ELIGIBILITY.END DATE            | -- --                       |
      | CURRENT ELIGIBILITY.ENROLL BUTTON       | hidden                      |
      | CURRENT ELIGIBILITY.PCP SELECT BUTTON   | hidden                      |
      #*********
      | CURRENT ENROLLMENT.PLAN NAME            | selectedPlanName            |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Selection Made              |
      | CURRENT ENROLLMENT.CHANNEL              | Phone                       |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                       |
      | CURRENT ENROLLMENT.START DATE           | 15thofLastMonthUIver        |
      | CURRENT ENROLLMENT.END DATE             | -- --                       |
      | CURRENT ENROLLMENT.EDIT BUTTON          | is displayed                |
      | CURRENT ENROLLMENT.DISREGARD BUTTON     | is displayed                |
    And I click "ENROLL" Button for a consumer first name "33908-06.firstName" and last name "33908-06.lastName"
    And Wait for 2 seconds
    And I select A plan from Available Plans
    And I change start date to 2 month earlier to day 25 on calendar
    And I click submit button on enrollment update
    # 3.0 Validate each selected consumer’s new enrollment selection when user clicks Submit button
    And I click current enrollment "EDIT" Button for a consumer first name "33908-06.firstName" and last name "33908-06.lastName"
    And Wait for 2 seconds
    And I change start date to 2 month earlier to day 24 on calendar
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I verify enrollment selection dates are not valid message is displayed
    Then I verify enrollment selection dates are not valid warning message includes consumer "33908-06"


  @CP-33908 @ui-ee @crm-regression @kursat
  Scenario: Baseline Display Error message for each invalid enrollment selection - Plan Change
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 33908-07 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33908-07.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 21                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33908-07.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | recordId                     | 4                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | planCode                     | 84                  |
      | planId                       | 145                 |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
     #   **********************CONSUMER 2 **************************************
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 33908-08 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33908-08.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 21                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33908-08.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | recordId                     | 4                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | planCode                     | 84                  |
      | planId                       | 145                 |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
     #   ***************** UI START *******************************************
    Given I logged into CRM and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "33908-07.firstName" and Last Name as "33908-07.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "PLAN CHANGE" Button for a consumer first name "33908-08.firstName" and last name "33908-08.lastName"
    And Wait for 3 seconds
    And I select A plan from Available Plans
    And I change start date to 4 month earlier to day 15 on calendar
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    # 6.0 Display Error message for each invalid enrollment selection
    Then I verify enrollment selection dates are not valid message is displayed
    Then I verify enrollment selection dates are not valid warning message includes consumer "33908-08"
    And I click "PLAN CHANGE" Button for a consumer first name "33908-08.firstName" and last name "33908-08.lastName"
    And Wait for 3 seconds
    And I select A plan from Available Plans
    And I change start date to 2 month earlier to day 15 on calendar
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    # 4.0 Validate each selected consumer’s plan change selection when user clicks Submit button
    # 5.0 Navigate back to P&B screen
    Then I verify program & benefit info page for consumer first name "33908-08.firstName" and last name "33908-08.lastName" with data
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | Medicaid-General Population |
      | CURRENT ELIGIBILITY.SERVICE REGION      | East                        |
      | CURRENT ELIGIBILITY.START DATE          | firstdayofLastMonth         |
      | CURRENT ELIGIBILITY.END DATE            | -- --                       |
      | CURRENT ELIGIBILITY.PLAN CHANGE BUTTON  | hidden                      |
      | CURRENT ELIGIBILITY.PCP SELECT BUTTON   | hidden                      |










