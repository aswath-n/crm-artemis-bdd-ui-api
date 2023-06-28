Feature: Integrate User provisioning in CP UI

  @CP-30048 @CP-30048-01 @CP-32428 @aikanysh @crm-regression @ui-core
  Scenario: Integrate User provisioning in CP UI
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click on configure button for user provisioning
    And I verify user provisioning screen is displayed

