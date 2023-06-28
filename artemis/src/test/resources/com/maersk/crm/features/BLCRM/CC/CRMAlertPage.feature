Feature: Manually create Case/Consumer Alert

  @CP-28127 @CP-28127-01 @CP-25833 @CP-25833-01 @ui-cc @muhabbat
  Scenario: Display Case Alert in Left navigation for user role WITH EDIT permission access
    Given I logged into CRM
    Then I can see Alert tab from left navigation bar

  @CP-28127 @CP-28127-02 @CP-25833 @CP-25833-02 @ui-cc @muhabbat
  Scenario: Navigate to Case Alert screen from Left navigation
    Given I logged into CRM
    When I am navigated to Case Alert Upload screen after clicking on Alert tab

  @CP-28127 @CP-28127-03 @ui-cc @muhabbat
  Scenario: Validate fields on Case Alert screen
    Given I logged into CRM
    When I validate all expected fields are present on Upload Case Alert screen

  @CP-28127 @CP-28127-04 @ui-cc @muhabbat
  Scenario: Validate Case Alert screen is not displayed for user without permission
    Given I started to login with "SVC_mars_user_2" and choose "6203 BLCRM"
    When I validate Upload Alert tab is not displayed for this user

  @CP-28679 @CP-28679-01 @ui-cc @chopa
  Scenario: Display Case/Consumer Alert Template link
    Given I logged into CRM
    When I am navigated to Case Alert Upload screen after clicking on Alert tab
    When I verify download alert template button is displayed


  @CP-25833 @CP-25833-03 @ui-cc @muhabbat
  Scenario: Validate all components are displayed on Case/Consumer Alert Landing Page
    Given I logged into CRM
    When I navigate to Alert Upload screen
    Then I validate all components are displayed on CaseConsumer Alert Landing Page

  @CP-25833 @CP-25833-04 @ui-cc @muhabbat
  Scenario: Validate Create Alert Component
    Given I logged into CRM
    When I navigate to Alert Upload screen
    Then I validate create alert component on CaseConsumer Alert Landing Page

  @CP-25833 @CP-25833-05 @ui-cc @muhabbat
  Scenario: Validate Create Alert Component Cancel button
    Given I logged into CRM
    When I navigate to Alert Upload screen
    Then I validate Cancel alert component button on CaseConsumer Alert Landing Page

  @CP-25833 @CP-25833-06 @ui-cc @muhabbat
  Scenario: Validate Alert History component
    Given I logged into CRM
    When I navigate to Alert Upload screen
    Then I validate Upload History component on CaseConsumer Alert Landing Page

  @CP-25833 @CP-25833-07 @ui-cc @muhabbat
  Scenario: Validate Alert History details
    Given I logged into CRM
    When I navigate to Alert Upload screen
    Then I validate Data captured on UI for file Upload on Alert Page

  @CP-31731 @CP-31731-01 @ui-cc @chopa
  Scenario: Validate Recently Created Alert details
    Given I logged into CRM
    When I navigate to Alert Upload screen
    Then I click on Recently Created tab
    Then I validate Recently Created component on CaseConsumer Alert Landing Page

  @CP-31731 @CP-31731-02 @ui-cc @chopa
  Scenario: Validate date range single select dropdown values
    Given I logged into CRM
    When I navigate to Alert Upload screen
    Then I click on Recently Created tab
    Then I verify date range single select dropdown containing the following options
    |Last 1 month     |
    |Last 3 month     |
    |Last 6 month     |
    |Last 1 year      |
    |Last 2 year      |
    |Select Date range|

  @CP-27134 @CP-27134-01 @ui-cc @chopa
  Scenario: Validate filtering by date range single select dropdown values in Upload Alert Screen
    Given I logged into CRM
    When I navigate to Alert Upload screen
    Then I verify date range single select dropdown containing the following options
      |Select Filter     |
      |Last 1 Month      |
      |Last 3 Months     |
      |Last 6 Months     |
      |Last 1 Year       |
      |Last 2 Years      |
      |Select Date Range |

  @CP-27134 @CP-27134-02 @ui-cc @chopa
  Scenario: Validate Start and End dates are displayed in Date Range option
    Given I logged into CRM
    When I navigate to Alert Upload screen
    Then I verify start and end dates are displayed in date range option

  @CP-27134 @CP-27134-03 @ui-cc @chopa
  Scenario: Validate Filter on Uploaded By
    Given I logged into CRM
    When I navigate to Alert Upload screen
    Then I verify I can filter by uploaded user

  @CP-27134 @CP-27134-04 @ui-cc @chopa
  Scenario: Validate Filter on Uploaded By
    Given I logged into CRM
    When I navigate to Alert Upload screen
    Then I verify Reset button is displayed for Upload Alert Screen

  @CP-28720 @CP-28720-1 @ui-cc @Beka
  Scenario: Validate download successfully file
    Given I logged into CRM
    When I navigate to Alert Upload screen
    Then I select "SuccesAlertFile.xlsx" file for upload and validate BLCRM
    And I click on upload button on upload alert page
    Then I will Download Successful Alert file
