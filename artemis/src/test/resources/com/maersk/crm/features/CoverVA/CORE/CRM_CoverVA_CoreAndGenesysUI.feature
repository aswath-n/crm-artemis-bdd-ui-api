Feature: CoverVA CORE and Genesys User Interface


  @CP-37704 @CP-37704-03 @CP-42045 @CP-42045-03 @sang @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: Verify new URL login and validate login with updated CORPORATE HEADQUARTERS footer address for COVER-VA
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I should see the office Address displayed in the bottom
      | CORPORATE HEADQUARTERS - 1600 Tysons Blvd, Suite 1400 McLean, VA 22102 |

  @CP-43090 @CP-43090-02 @ui-core-cover-va @crm-regression @sang
    # CP-43090(AC 1.0, AC 2.0)
  Scenario: Verify Genesys Widget in the ConnectionPoint UI is labeled as Interaction Management for CoverVA
    Given I logged into CRM with "Service Tester 3" and select a project "CoverVA"
    Then I Validate "CALL MANAGEMENT" label for Genesys Widget "is not" displayed
    Then I Validate "INTERACTION MANAGEMENT" label for Genesys Widget "is" displayed