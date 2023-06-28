Feature: Consumer submits Plan Change Outside Change Window

  @CP-15065 @CP-15065-01  @ui-ee-removed @alex
  Scenario: How to I initiate the Override Plan Change - user with permissions
    Given I logged into CRM with Call Center Supervisor account and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "Dixie" and Last Name as "Jane"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I will click submit button
    Then I verify new Plan Change with anticipated start date and selection status of “Selection Made”

  @CP-15065 @CP-15065-02  @ui-ee-removed  @alex
  Scenario:  Always View Calendar of Important Dates with User Override Permissions
    Given I logged into CRM with Call Center Supervisor account and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "Dixie" and Last Name as "Jane"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I will click submit button
    And I hover over the "calendar icon" I see "PRE-LOCKIN - WINDOW" text is displayed
    Then I hover over the "calendar icon" I see "ANNIVERSARY - WINDOW" text is displayed
    Then I verify new Plan Change with anticipated start date and selection status of “Selection Made”

  @CP-15065 @CP-15065-03 @ui-ee-removed @alex
  Scenario:  Calculate Plan Start Date and Plan End Date for Consumer Plan Change Actions
    Given I logged into CRM with Call Center Supervisor account and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "Dixie" and Last Name as "Jane"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I will click submit button
    Then I verify consumer enrollment action Plan Start Date is >= to a Plan Enrollment Start Date

  @CP-15065 @CP-15065-04  @ui-ee-removed  @alex
  Scenario: Adding a case member
    Given I logged into CRM with Call Center Supervisor account and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "FnRtfRR" and Last Name as "LnxkNTm"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify bellow fields are displayed on "Current Eligibility" section:
      | CONSUMER POPULATION |
      | SERVICE REGION      |
    And I click "Plan Change" Button for a consumer
    And I Add Case Members Button and Select all case members
    Then I verify  the Add Case Members button is disable

  @CP-15065 @CP-15065-05  @ui-ee-removed  @alex
  Scenario: Edit Plan Change Override Start Date
    Given I logged into CRM with Call Center Supervisor account and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "Dixie" and Last Name as "Jane"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I select A plan from Available Plans
    Then Plan Change Reasons and Plan Change Override Reasons should display in separate drop down selections
    And I verify I can't click submit button and update plan without choosing the reason
    And I select a reason from drop down on Enrollment Update page
    Then I verify bellow fields are displayed on "drop down reason" section:
    |Family Moving to Same Plan|
    |Good Cause Exception      |

  @CP-15065 @CP-15065-06  @ui-ee-removed @alex
  Scenario: Create each consumer’s new enrollment segment
    Given I logged into CRM with Call Center Supervisor account and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "Dixie" and Last Name as "Jane"
    And I click on the Contact Channel Type "Phone"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I will click submit button
    Then I verify below details on newly created future enrollment segment
      |Enrollment Start Date|
      |Enrollment End Date  |
      |Selection Status     |
      |Channel              |
      |Plan name            |

  @CP-15065 @CP-15065-07  @ui-ee-removed  @alex
  Scenario: Update each consumer’s old enrollment segment
    Given I logged into CRM with Call Center Supervisor account and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "Dixie" and Last Name as "Jane"
    And I click on the Contact Channel Type "Phone"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I will click submit button
    Then I verify bellow fields are displayed on "PRIOR ENROLLMENT DETAILS" section:
      |PLAN NAME            |
      |ENROLLMENT START DATE|
      |ENROLLMENT END DATE  |
      |ENROLLMENT END REASON|

  @CP-15065 @CP-15065-08 @ui-ee-removed   @alex
  Scenario:  Display Program & Benefit screen with save changes after clicking “Submit” button
    Given I logged into CRM with Call Center Supervisor account and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "Dixie" and Last Name as "Jane"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I will click submit button
    Then I verify previously accepted enrollment segment with an end date and selection status of “Disenroll Requested”
    Then I verify new Plan Change with anticipated start date and selection status of “Selection Made”
    Then I verify Edit & Disregard buttons are displayed on the new enrollment segment Selection Status = Selection Made

  @CP-15065 @CP-15065-09 @ui-ee-removed  @alex
  Scenario:   How to I initiate the Override Plan Change - user without  permissions
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "Dixie" and Last Name as "Jane"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then  I verify "Plan Change Button" is not displayed


