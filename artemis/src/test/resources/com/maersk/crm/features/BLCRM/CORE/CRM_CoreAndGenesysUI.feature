Feature: CORE and Genesys User Interface


  @CP-37704 @CP-37704-01 @CP-42045 @CP-42045-01 @ui-core @crm-regression @sang
  Scenario: Verify new URL login and validate login with updated CORPORATE HEADQUARTERS footer address for BLCRM
    Given I logged into the CRM Application
    And I should see the office Address displayed in the bottom
      | CORPORATE HEADQUARTERS - 1600 Tysons Blvd, Suite 1400 McLean, VA 22102 |

  @CP-43090 @CP-43090-01 @ui-core @crm-regression @sang
    # CP-43090(AC 1.0, AC 2.0)
    Scenario: Verify Genesys Widget in the ConnectionPoint UI is labeled as Interaction Management
    Given I logged into CRM with "Service Account 5" and select a project "BLCRM"
    Then I Validate "CALL MANAGEMENT" label for Genesys Widget "is not" displayed
    Then I Validate "INTERACTION MANAGEMENT" label for Genesys Widget "is" displayed

  @CP-5095 @CRM-5095-01 @aikanysh @crm-regression @ui-core
  Scenario: Changing the Start Time and Duration label to icons
    Given I logged into CRM and click on initiate contact
    And I verify that Start Time Icon is displayed
    And I verify that Duration Icon is displayed

  @CP-5095 @CRM-5095-02 @aikanysh @crm-regression @ui-core
  Scenario: Changing the Initiate and End Contact to button with text and in different color
    Given I logged into CRM
    And I verify that "INITIATE CONTACT" button is displayed
    And I click on "INITIATE CONTACT" and verify that "END CONTACT" is displayed