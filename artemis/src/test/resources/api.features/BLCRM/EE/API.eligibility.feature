@API-CP-64
Feature: API: Eligibilility Feature

  @asad @API-CP-64-01 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-SmokeTest
  Scenario Outline: Capture Eligibility Information
    Given I created a consumer for eligibility record
    Given I initiated Eligibility Create API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Eligibility Create API
    Then I will capture the eligibility data
    Examples:
      |projectName|
      ||

  @asad @API-CP-64-02 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Capture Long Term Care Information
    Given I created a consumer for eligibility record
    Given I initiated Eligibility Create API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Eligibility Create API
    Then I will capture the long term care data
    Examples:
      |projectName|
      ||

  @asad @API-CP-64-03 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Capture Facility/Placement Information
    Given I created a consumer for eligibility record
    Given I initiated Eligibility Create API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Eligibility Create API
    Then I will capture the facility placement data
    Examples:
      |projectName|
      ||

  @asad @API-CP-64-04 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Capture Hospice Information
    Given I created a consumer for eligibility record
    Given I initiated Eligibility Create API
    When I run the Eligibility Create API
    Then I will capture the hospice data
    Examples:
      |projectName|
      ||

  @asad @API-CP-64-05 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Capture Medicare Information
    Given I created a consumer for eligibility record
    Given I initiated Eligibility Create API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Eligibility Create API
    Then I will capture the Medicare data
    Examples:
      |projectName|
      ||

  @asad @API-CP-64-06 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Capture Third Party Insurance Information
    Given I created a consumer for eligibility record
    Given I initiated Eligibility Create API
    When I run the Eligibility Create API
    Then I will capture the third party insurance data
    Examples:
      |projectName|
      ||

  @asad @API-CP-64-07 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Capture Waiver Information
    Given I created a consumer for eligibility record
    Given I initiated Eligibility Create API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Eligibility Create API
    Then I will capture the waiver data
    Examples:
      |projectName|
      ||

  @asad @API-CP-64-08 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Capture Special Population Information
    Given I created a consumer for eligibility record
    Given I initiated Eligibility Create API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Eligibility Create API
    Then I will capture the special population data
    Examples:
      |projectName|
      ||

  @asad @API-CP-64-09 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Capture Financial Information
    Given I created a consumer for eligibility record
    Given I initiated Eligibility Create API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Eligibility Create API
    Then I will capture the financial data
    Examples:
      |projectName|
      ||

    #CP-64: Events Scenario Outline CP-64-10 will fail since events are not generating. Defect for this is CP-8366.
  @asad @API-CP-64-10 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression @events_smoke_level_two
  Scenario Outline: Publish an ELIGIBILITY_SAVE_EVENT event for DPBI to consume
    Given I created a consumer for eligibility record
    Given I initiated Eligibility Create API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Eligibility Create API
    Then I will verify an "ELIGIBILITY_SAVE_EVENT" for DPBI is created for eligibility create
    Examples:
      |projectName|
      ||

  @asad @API-CP-64-11 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Record Created Date and Created By fields
    Given I created a consumer for eligibility record
    Given I initiated Eligibility Create API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Eligibility Create API
    Then I will check the date the eligibility record was created
    Examples:
      |projectName|
      ||
