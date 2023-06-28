@API-CP-66
Feature: API: Eligibility Feature

  @asad @API-CP-66-01 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Update Long Term Care Information
    Given I initiated Eligibility API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I update long term care info
    And I run the Eligibility API
    Then I will verify the long term care data is updated
    Examples:
      |projectName|
      ||

  @asad @API-CP-66-02 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Update Facility/Placement Information
    Given I initiated Eligibility API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I update facility info
    And I run the Eligibility API
    Then I will verify the facility data is updated
    Examples:
      |projectName|
      ||

  @asad @API-CP-66-03 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Update Hospice Information
    Given I initiated Eligibility API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I update hospice info
    And I run the Eligibility API
    Then I will verify the hospice placement data is updated
    Examples:
      |projectName|
      ||

  @asad @API-CP-66-04 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Update Third Party Insurance Information
    Given I initiated Eligibility API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I update third party insurance info
    And I run the Eligibility API
    Then I will verify the third party insurance is updated
    Examples:
      |projectName|
      ||

  @asad @API-CP-66-05 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Update Waiver Information
    Given I initiated Eligibility API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I update waiver info
    And I run the Eligibility API
    Then I will verify the waiver data is updated
    Examples:
      |projectName|
      ||

  @asad @API-CP-66-06 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Update Special Population Information
    Given I initiated Eligibility API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I update special population data
    And I run the Eligibility API
    Then I will verify the special population data is updated
    Examples:
      |projectName|
      ||
  @asad @API-CP-66-07 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Update Financial Information
    Given I initiated Eligibility API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I update financial data
    And I run the Eligibility API
    Then I will verify the financial data is updated
    Examples:
      |projectName|
      ||

 #CP-66: Events Scenario Outline CP-66-08 will fail since events are not generating. Defect for this is CP-8366
  @asad @API-CP-66-08 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression @022420201 @events_smoke_level_two
  Scenario Outline: Publish an ELIGIBILITY_UPDATE_EVENT event for DPBI to consume
    Given I initiated Eligibility API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I update waiver info
    And I run the Eligibility API
    Then I will verify an "ELIGIBILITY_UPDATE_EVENT" for DPBI is created for elgibility update
    Examples:
      |projectName|
      ||
