Feature: NJ Alerts Customization
# Scenario for AC 3.0 with GI case not in CP system is not automatable as it requires brend new GI case id each time
  @CP-31926 @CP-31926-01 @ui-cc-nj @nj-regression @muhabbat
  Scenario: Upload and Validate button for NJ Alert File
    Given I logged into CRM with "Service AccountOne" and select a project "NJ-SBE"
    And I navigate to Alert Upload screen
    Then I validate NJ-SBE Upload and Validate button is displayed for Alerts


  @CP-31926 @CP-31926-02 @ui-cc-nj @nj-regression @muhabbat
  Scenario: Upload and validate file with existing case successful file
    Given I logged into CRM with "Service AccountOne" and select a project "NJ-SBE"
    And I navigate to Alert Upload screen
    Then I select "nj-sbe_case_alerts.xlsx" file for upload and validate
    Then I see successful upload is completed for NJ-SBE GI case

  @CP-31926 @CP-31926-03 @ui-cc-nj @nj-regression @muhabbat
  Scenario: Upload and validate file with existing case file with error
    Given I logged into CRM with "Service AccountOne" and select a project "NJ-SBE"
    And I navigate to Alert Upload screen
    Then I select "nj-sbe_error_alerts.xlsx" file for upload and validate
    Then I see unsuccessful upload is completed for NJ-SBE GI case