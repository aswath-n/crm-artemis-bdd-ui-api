Feature: View Consumer PRofile on PRogram Benefits Tab

  @CP-72 @CP-72-01 @shruti @crm-regression
  Scenario: View Consumer Profile on P&B info tab
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Debra" and Last Name as "Johnson"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify each consumer section is expanded by default in program and benefit info page
    When I click on carrot sign for each consumer section in program and benefit info page
    Then I verify each consumer section is collapsed in program and benefit info page
    And I verify sort order consumer eligibility enrolled and not eligible


  @CP-72 @CP-72-02 @shruti @ui-ee @crm-regression @kursat
  Scenario: Navigate Away-Consumer External ID
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Debra" and Last Name as "Johnson"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify user is navigate to Demographic info tab after clicking on external consumer id


  @CP-72 @CP-72-03 @shruti @ui-ee @ui-ee-smoke @kursat
  Scenario: Verify Data Elements for Consumer section on P&B info tab
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 72-03 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 72-03.consumerId  |
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
    Given I logged into CRM and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "72-03.firstName" and Last Name as "72-03.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify following data elements are displayed for consumer
      | Data Elements                                                                                |
      | Consumer External ID (the Medicaid ID or the CHIP ID)                                        |
      | Consumer Name (First Name Last Name)                                                         |
      | Consumer Age (years)                                                                         |
      | Consumer Gender                                                                              |
      | Benefit Status                                                                               |
      | Eligibility Status                                                                           |
      | Calendar icon of Important Dates (select icon)                                               |
      | Countdown of Days Left to Enroll (calendar days)                                             |
      | Icon to the left of Countdown to be used as a hover-over to display information to the user  |
      | Icon to the right of Countdown to be used as a hover-over to display information to the user |


  @CP-72 @CP-72-04 @shruti @crm-regression
  Scenario: Hover Over - Calendar icon of Important Dates for Consumer section on P&B info tab
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Debra" and Last Name as "Johnson"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify tool tip text for each consumer by hovering the mouse pointer over the Calendar icon of Important Dates


  @CP-72 @CP-72-05 @shruti @crm-regression
  Scenario: Hover Over - Icon for Countdown of Days Left to Enroll for Consumer section on P&B info tab
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Debra" and Last Name as "Johnson"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify tool tip text for each consumer by hovering the mouse pointer over the notification icon

  @CP-72 @CP-72-06 @shruti @crm-regression
  Scenario: Verify alt text is displayed for benefit Status and Eligibility status for each consumer on P&B info tab
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Debra" and Last Name as "Johnson"
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify alternative text is displayed for benefit Status and Eligibility status for each consumer

  @CP-14096 @CP-14096-01 @crm-regression @Olga
  Scenario: Verify when Plan Change button is available plan change pre-lockin important dates are displayed
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
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
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
      | planId                       | 1895              |
    And I run create Eligibility and Enrollment API
    When I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I hover over the "calendar icon" I see "PRE-LOCKIN - WINDOW" text is displayed
    Then I verify "Plan Change" button is displayed

   @CP-14096 @CP-14096-02 @crm-regression @Olga
    Scenario: Verify Plan Change button is not available plan change pre-lockin important dates are not displayed
      Given I logged into CRM and click on initiate contact
      When I searched customer have First Name as "FnMhdQY" and Last Name as "LnZcNOt"
      And I link the contact to an existing Case or Consumer Profile
      And I click on "Program & Benefit Info" Tab on Contact Dashboard Page
      Then I hover over the "calendar icon" I see "PRE-LOCKIN - WINDOW dates" text is not-displayed
      Then  I verify "Plan Change Button" is not displayed

    @CP-14247 @CP-14247-01 @crm-regression @Olga
  Scenario:  Verify when Plan Change button is available plan change anniversary important dates are displayed
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
      And I run create Eligibility and Enrollment API
      And I provide the enrollment and eligibility information to create enrollment
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
        | planId                       | 1895              |
      And I run create Eligibility and Enrollment API
      When I logged into CRM and click on initiate contact
      When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
      And I link the contact to an existing Case or Consumer Profile
      When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I hover over the "calendar icon" I see "ANNIVERSARY - WINDOW" text is displayed
    Then I verify "Plan Change" button is displayed

    @CP-14247 @CP-14247-02 @crm-regression @Olga
   Scenario: Verify Plan Change button is not available plan change anniversary important dates are not displayed
     Given I logged into CRM and click on initiate contact
     When I searched customer have First Name as "FnMhdQY" and Last Name as "LnZcNOt"
     And I link the contact to an existing Case or Consumer Profile
     And I click on "Program & Benefit Info" Tab on Contact Dashboard Page
     Then I hover over the "calendar icon" I see "ANNIVERSARY - WINDOW dates" text is not-displayed
     Then  I verify "Plan Change Button" is not displayed

