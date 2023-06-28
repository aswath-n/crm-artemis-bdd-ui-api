Feature: Consumer Update Profile

  @CP-335 @CP-335-01.0 @asad @crm-regression @ui-cc
  Scenario: Update Consumer
    Given I logged into CRM and click on initiate contact
    When I create a consumer for case consumer profile search
    And I navigate to Case Consumer Search Page
    And I search for consumer profile created
    And I click on consumer record to navigate Profile Details
    Then I click on Edit button for Profile Details
    Then I am able to edit the Consumer profile
    And I click on Save button for Profile Details

  @CP-335 @CP-335-01.1 @asad  @crm-regression @ui-cc
  Scenario: Required Information Validation
    Given I logged into CRM and click on initiate contact
    When I create a consumer for case consumer profile search
    And I navigate to Case Consumer Search Page
    And I search for consumer profile created
    And I click on consumer record to navigate Profile Details
    Then I click on Edit button for Profile Details
    Then I am able to validate the required fields information

  @CP-335 @CP-335-01.2 @asad @crm-regression @ui-cc
  Scenario: Field Level Validation
    Given I logged into CRM and click on initiate contact
    When I create a consumer for case consumer profile search
    And I navigate to Case Consumer Search Page
    And I search for consumer profile created
    And I click on consumer record to navigate Profile Details
    Then I click on Edit button for Profile Details
    Then I am able to validate the fields format

  @CP-335 @CP-335-01.3 @asad @crm-regression @ui-cc
  Scenario: Save Button
    Given I logged into CRM and click on initiate contact
    When I create a consumer for case consumer profile search
    And I navigate to Case Consumer Search Page
    And I search for consumer profile created
    And I click on consumer record to navigate Profile Details
    Then I click on Edit button for Profile Details
    Then I am able to edit the Consumer profile
    And I click on Save button for Profile Details

  @CP-335 @CP-335-01.4 @asad @crm-regression @ui-cc
  Scenario: Cancel Button after clicking on Cancel button
    Given I logged into CRM and click on initiate contact
    When I create a consumer for case consumer profile search
    And I navigate to Case Consumer Search Page
    And I search for consumer profile created
    And I click on consumer record to navigate Profile Details
    Then I click on Edit button for Profile Details
    Then I am able to edit the Consumer profile
    Then I click on Cancel button on Profile Details page and "Cancel"

  @CP-335 @CP-335-01.5 @asad @crm-regression @ui-cc
  Scenario: Continue Button after clicking on Cancel button
    Given I logged into CRM and click on initiate contact
    When I create a consumer for case consumer profile search
    And I navigate to Case Consumer Search Page
    And I search for consumer profile created
    And I click on consumer record to navigate Profile Details
    Then I click on Edit button for Profile Details
    Then I am able to edit the Consumer profile
    Then I click on Cancel button on Profile Details page and "Continue"

  @CP-335 @CP-335-01.5 @asad @crm-regression @ui-cc
  Scenario: Inactivate Immediately Button
    Given I logged into CRM and click on initiate contact
    When I create a consumer for case consumer profile search
    And I navigate to Case Consumer Search Page
    And I search for consumer profile created
    And I click on consumer record to navigate Profile Details
    Then I click on Edit button for Profile Details
    Then I select the Inactivate Immediately Button and verify consumer status


  @CP-335 @CP-335-020 @asad @crm-regression @ui-cc
  Scenario Outline: Verification of Consumer Status
    Given I logged into CRM and click on initiate contact
    When I create a consumer for case consumer profile search
    And I navigate to Case Consumer Search Page
    And I search for consumer profile created
    And I click on consumer record to navigate Profile Details
    Then I click on Edit button for Profile Details
    Then I am able to check the consumer status based on "<startDate>" and "<endDate>" values
    Examples:
      | startDate  | endDate    |
      | 09/15/2019 | 09/15/2025|
      | Future     | Future     |
      | 09/15/2019 | Future     |
      | 01/01/2019 | 01/01/2020 |

  @CP-335 @CP-335-03.0 @asad @crm-regression @ui-cc
  Scenario: Age Update
    Given I logged into CRM and click on initiate contact
    When I create a consumer for case consumer profile search
    And I navigate to Case Consumer Search Page
    And I search for consumer profile created
    And I click on consumer record to navigate Profile Details
    Then I click on Edit button for Profile Details
    Then I am able to edit the consumer dob and validate the age has been updated

  @CP-335 @CP-335-03.1 @asad @crm-regression @ui-cc
  Scenario: Consumer SSN
    Given I logged into CRM and click on initiate contact
    When I create a consumer for case consumer profile search
    And I navigate to Case Consumer Search Page
    And I search for consumer profile created
    And I click on consumer record to navigate Profile Details
    Then I click on Edit button for Profile Details
    Then I am able to edit the consumer ssn and validate the ssn has been updated

  @CP-335 @CP-335-03.2 @asad @crm-regression @ui-cc
  Scenario: Consumer Name Fields
    Given I logged into CRM and click on initiate contact
    When I create a consumer for case consumer profile search
    And I navigate to Case Consumer Search Page
    And I search for consumer profile created
    And I click on consumer record to navigate Profile Details
    And I click on Edit button for Profile Details
    Then I am able to edit the consumer Name fields and validate the Name fields are updated

  @CP-335 @CP-335-040 @asad
  Scenario: Capture Update Information
    Given I logged into CRM and click on initiate contact
    When I create a consumer for case consumer profile search
    And I navigate to Case Consumer Search Page
    And I search for consumer profile created
    Then I am able to save and capture the updated by information