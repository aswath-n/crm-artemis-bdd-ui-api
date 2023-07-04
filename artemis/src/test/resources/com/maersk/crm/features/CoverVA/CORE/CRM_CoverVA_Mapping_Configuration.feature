Feature: CoverVA: Mapping Configuration

  @CP-47077 @CP-47077-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
    # CP-45384(AC 2.0)
  Scenario Outline::  FE : Display Mapping Configuration - EffectiveEndDate and UI validation
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiate GET Facility Name Fields API and validate status code
    And I verify the following field "ENUM_FACILITY_NAME.effectiveEndDate" in response
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I navigate to business unit "CVIU - DJJ"
    And Verify the facility name field value in dropdown in UI and in api response
    Examples:
      | projectName |
      | CoverVA     |
