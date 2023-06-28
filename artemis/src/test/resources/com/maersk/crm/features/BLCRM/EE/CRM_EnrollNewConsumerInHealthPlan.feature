@CP-90
Feature: Enroll new Consumer in a Health Plan

  Background:
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
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page

  @CP-90 @CP-90-01  @ui-ee @crm-regression @Olga
  Scenario: Verify Provider Search Panel is displayed until Update Enrollment button is clicked
    And I click "Enroll" Button for a consumer
    Then I verify "Provider Search" Pannel is displayed

  @CP-90 @CP-90-02  @CP-13747 @ui-ee @crm-regression @Olga
  Scenario: Verify Remove Plan Button is displayed after selecting a plan
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    Then I verify "Remove Plan" button is displayed

  @CP-90 @CP-90-03  @ui-ee @crm-regression @Olga
  Scenario: Verify Select Plan Reason option is not displayed for Enroll Action
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    Then I verify "Plan Reason dropdown" is not displayed

  @CP-90 @CP-90-04  @ui-ee @crm-regression @Olga
  Scenario: Verify Remove Plan Selection prior to submitting the Enrollment user is navigated back to enroll update screen prior to selecting plan
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    And I click on Remove Plan Option
    Then I verify I am still on "Enrollment Update" Page
    Then I verify all fields on the Consumer Details Panel are displayed
    Then I verify "Provider Search" Pannel is displayed
    Then I verify "Plan Name" on the Plan Available Panel is displayed
    Then I verify "Plan Type" on the Plan Available Panel is displayed
    Then I verify "Select" button is displayed for each plan


  @CP-90 @CP-90-05  @ui-ee @crm-regression @Olga
  Scenario: Verify after Selecting Plan user tries to navigate away by clicking on back arrow prior
  to submitting the Enrollment and clcik Cancel on warning message
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    And I click on Remove Plan Option
    And I navigate away by clicking on back arrow on Enrollment Update Page
    Then I see warning message pop up with an option to cancel or continue
    And I click on "cancel" on the warning message
    Then I verify I am still on "Enrollment Update" Page


  @CP-90 @CP-90-06  @ui-ee @crm-regression @Olga
  Scenario: Verify after Selecting Plan user tries to navigate away by cliking on back arrow prior
  to submitting the Enrollment and clcik continue on warning message
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    And I click on Remove Plan Option
    And I navigate away by clicking on back arrow on Enrollment Update Page
    Then I see warning message pop up with an option to cancel or continue
    And I click on "continue" on the warning message
    Then I verify I am navigated to "Program & Benefit Info" Page


  @CP-90 @CP-90-07  @ui-ee @crm-regression @Olga
  Scenario: Verify after Selecting Plan user tries to navigate away from Left Nav menu before submitting the Enrollment
  and clcik continue on warning message
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    And I click on Remove Plan Option
    And I hover over the side bar and click on "Contact Record Search" field
    Then I see warning message pop up with an option to cancel or continue
    And I click on "continue" on the warning message
    Then I verify I am navigated to "Contact Record" Page

  @CP-90 @CP-90-08 @CP-13747-02  @ui-ee @crm-regression @Olga
  Scenario: Verify after Selecting Plan user tries to navigate away from Left Nav menu before submitting the Enrollment
  and clcik cancel on warning message
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    And I click on Remove Plan Option
    And I hover over the side bar and click on "Contact Record Search" field
    And I click on "cancel" on the warning message
    Then I verify I am still on "Enrollment Update" Page

  @CP-24301 @CP-14603-01 @CP-15306 @ui-ee @sobir
  Scenario: BLCRM Enrollment Create or Update Data: Level of Care - Special Coverage
  AC 1.4 Do Not display special coverage segment ( Baseline)
    Then I verify program & benefit info page for consumer first name "fromCaseLoaderApi" and last name "fromCaseLoaderApi" with data
      | LONG TERM CARE.SPECIAL COVERAGE.HIDDEN              |[blank]|
      | HOSPICE.SPECIAL COVERAGE.HIDDEN                     |[blank]|
      | WAIVER.SPECIAL COVERAGE.HIDDEN                      |[blank]|
      | MEDICARE.SPECIAL COVERAGE.HIDDEN                    |[blank]|
      | OTHER INSURANCE - 3RD PARTY.SPECIAL COVERAGE.HIDDEN |[blank]|
      | FACILITY/PLACEMENT.SPECIAL COVERAGE.CODE.1          | 123Facility          |
      | FACILITY/PLACEMENT.SPECIAL COVERAGE.START DATE.1    | 1stDayofPresentMonth |
      | FACILITY/PLACEMENT.SPECIAL COVERAGE.END DATE.1      | -- --                |