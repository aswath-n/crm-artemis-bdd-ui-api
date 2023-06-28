Feature: Augment Outbound Correspondence Definition with Inbound Correspondence Type

   # @CP-4638-01 = Deprecated this scenario as it was contradicting with CP-25021 story
  Scenario: Display and Verify Inbound Correspondence Type warning message
    Given I logged into Tenant Manager and set the project context "project" value "BLCRM"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I click add button on Outbound Correspondence Definition
    And I click Save button on Outbound Correspondence Definition
    Then I verify Inbound Correspondence Type warning message

  @CP-4638-02 @CP-4638 @ui-ecms1 @umid
  Scenario: Save Inbound Correspondence with the right type
    Given I logged into Tenant Manager and set the project context "project" value "BLCRM"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I add a new Correspondence Definition without a "Field"
    And I verify Success message is displayed

  @CP-4638-03 @CP-4638 @ui-ecms2 @umid
  Scenario: Update Inbound Correspondence with the right type
    Given I logged into Tenant Manager and set the project context "project" value "BLCRM"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition randomly
    And I update Outbound Correspondence Definition Type
    And I click Save button on Outbound Correspondence Definition
    And I verify Success message is displayed