Feature: API: Saved/Updated from Audit Daily Eligible Files

  @API-CP-15074-01 @API-CC @API-CRM-Regression
  Scenario: DE File saves new case and contact information is captured
    When I will get the Authentication token for "BLCRM" in "CRM"
    Given I initiated Case Loader API for Create New Consumer Contact Information
    When I run Case Loader API for Create New Consumer Contact Information without CaseIdNo
    When I get consumer Id of the created consumer for Contact Information
    When I capture the contact information of the created consumer
    Then I Verify created onBy and updated onBy fields for Create New Consumer Contact Information

  @API-CP-11311 @API-CP-11311-01 @API-CC @API-CRM-Regression @events-cc
  Scenario: File saves new case and audit is tracked and captures updated/crated(By/On)
    When I will get the Authentication token for "BLCRM" in "CRM"
    Given I initiated Case Loader API for Create New Consumer Contact Information
    When I run Case Loader API for Create New Consumer Contact Information without CaseIdNo
    When I get consumer Id of the created consumer for Contact Information
    When I search "case_save_event" endpoint for DBPI project "BLCRM"
    Then I verify case_save_event payload contains UpdatedByOn CraetedByOn

  @API-CP-11311 @API-CP-11311-02 @API-CC @API-CRM-Regression @events-cc
  Scenario: DE File updates a consumer’s demographic info and an audit is tracked
    When I will get the Authentication token for "" in "CRM"
    Given I initiated Case Loader API for Create New Case
    When I run Case Loader API for Create New Case and Customer
    And Wait for 5 seconds
    Then I will publish an "CONSUMER_SAVE_EVENT" event for DPBI to consume for reporting
    Then I verify consumer_save_event payload contains UpdatedByOn CreatedByOn

  #muted due to duplicate scenario
 # @API-CP-11311 @API-CP-11311-03 @API-EE @API-CC @API-CRM-Regression
 # Scenario: DE File updates a consumer’s demographic info and an audit is tracke
 #   Given I initiated Case Loader API for Create New Case
  #  When I will get the Authentication token for "" in "CRM"
  #  When I run Case Loader API for Create New Case and Customer
  #  Then I will publish an "CONSUMER_SAVE_EVENT" event for DPBI to consume for reporting
  #  Then I verify consumer_save_event payload contains UpdatedByOn CreatedByOn
