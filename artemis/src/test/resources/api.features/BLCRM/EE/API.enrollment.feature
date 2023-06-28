@API-CP-65
Feature: API: Enrollment Feature

  @asad @API-CP-65-01 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression # CP-65-01 will fail since there are null fields in response. Defect for this is CP-8167
  Scenario Outline: Capture Enrollment Information
    Given I created a consumer for enrollment record
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Enrollment Create API
    When I run the Enrollment Create API for "MEDICAL"
    Then I will verify the Enrollment data
    Examples:
      |projectName|
      ||

  @asad @API-CP-65-02 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Capture Medical Plan Information
    Given I created a consumer for enrollment record
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Enrollment Create API
    When I run the Enrollment Create API for "MEDICAL"
    Then I will verify the Enrollment Medical plan data
    Examples:
      |projectName|
      ||

  @asad @API-CP-65-03 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Capture Provider Information
    Given I created a consumer for enrollment record
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Enrollment Create API
    When I run the Enrollment Create API for "MEDICAL"
    Then I will verify the Enrollment Provider data
    Examples:
      |projectName|
      ||

  @asad @API-CP-65-04 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Capture Dental Plan Information
    Given I created a consumer for enrollment record
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Enrollment Create API
    When I run the Enrollment Create API for "DENTAL"
    Then I will verify the Enrollment Dental plan data
    Examples:
      |projectName|
      ||

  @asad @API-CP-65-05 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Capture Dentist Information
    Given I created a consumer for enrollment record
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Enrollment Create API
    When I run the Enrollment Create API for "DENTAL"
    Then I will verify the Enrollment Dentist Information

  @asad @API-CP-65-06 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Capture Behavioral Health Information
    Given I created a consumer for enrollment record
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Enrollment Create API
    When I run the Enrollment Create API for "BEHAVIORAL"
    Then I will verify the Enrollment Behavioral health data
    Examples:
      |projectName|
      ||

  @asad @API-CP-65-07 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression #CP-65: Events Scenario Outline CP-65-07 will fail since events are not generating. Defect for this is CP-8366
  Scenario Outline: Publish an ENROLLMENT_SAVE_EVENT event for DPBI to consume
    Given I created a consumer for enrollment record
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Enrollment Create API
    When I run the Enrollment Create API for "MEDICAL"
    Then I will verify an "ENROLLMENT_SAVE_EVENT" for DPBI is created for enrollment create
    Examples:
      |projectName|
      ||

  @asad @API-CP-65-08 @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Record Created Date and Created By fields
    Given I created a consumer for enrollment record
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Enrollment Create API
    When I run the Enrollment Create API for "MEDICAL"
    Then I will check the date the enrollment record was created
    Examples:
      |projectName|
      ||
