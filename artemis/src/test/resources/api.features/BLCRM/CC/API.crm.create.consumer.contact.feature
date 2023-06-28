@API-CP-62
Feature: API: Create New Consumer Contact Info

  @asad @API-CP-62-01.0 @API-CRM @API-CRM-Regression @API-CP-62 @API-CC @contacts-api-CC
  Scenario: Capture Consumer Contact Information
    When I will get the Authentication token for "" in "CRM"
    Given I initiated Case Loader API for Create New Consumer Contact Information
    When I run Case Loader API for Create New Consumer Contact Information
    When I get consumer Id of the created consumer for Contact Information
    When I capture the contact information of the created consumer
    Then I will verify address email and phone information for Create New Consumer Contact Information

  @asad @API-CP-62-01.1 @API-CRM @API-CRM-Regression @API-CP-62 @API-CC @contacts-api-CC
  Scenario: Phone Number Type is Designated by File
    When I will get the Authentication token for "" in "CRM"
    Given I initiated Case Loader API for Create New Consumer Contact Information
    When I run Case Loader API for Create New Consumer Contact Information
    When I get consumer Id of the created consumer for Contact Information
    When I capture the contact information of the created consumer
    Then I will verify phone number type for Create New Consumer Contact Information

  @asad @API-CP-62-02.0 @API-CRM @API-CRM-Regression @API-CP-62 @API-CC @contacts-api-CC
  Scenario: Capturing Start Dates of Contact Info
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API for Create New Consumer Contact Information
    When I run Case Loader API for Create New Consumer Contact Information
    When I get consumer Id of the created consumer for Contact Information
    When I capture the contact information of the created consumer
    Then I will verify start date for Create New Consumer Contact Information

  @asad @API-CP-62-04.0 @API-CRM @API-CRM-Regression @API-CP-62 @API-CC @contacts-api-CC
  Scenario: Capturing Primary Phone Number Information - if only one number provided, capture as primary
    When I will get the Authentication token for "" in "CRM"
    Given I initiated Case Loader API for Create New Consumer Contact Information
    When I run Case Loader API for Create New Consumer Contact Information
    When I get consumer Id of the created consumer for Contact Information
    When I capture the contact information of the created consumer
    Then I will verify primary phone indicator for Create New Consumer Contact Information

  @asad @API-CP-62-06.1 @API-CRM @API-CP-62 @events @events-cc
  Scenario: Publish an ADDRESS_SAVE_EVENT for DPBI to consume
    When I will get the Authentication token for "" in "CRM"
    Given I initiated Case Loader API for Create New Consumer Contact Information
    When I run Case Loader API for Create New Consumer Contact Information
    Then I will verify an "ADDRESS_SAVE_EVENT" is created for "address" event for Create New Consumer Contact Information
    Then I will verify subscriber received "ADDRESS_SAVE_EVENT" event for "DPBI" is created for Create New Consumer Contact Information

  @asad @API-CP-62-06.2 @API-CRM @API-CP-62 @events @events-cc
  Scenario: Publish an EMAIL_SAVE_EVENT for DPBI to consume
    When I will get the Authentication token for "" in "CRM"
    Given I initiated Case Loader API for Create New Consumer Contact Information
    When I run Case Loader API for Create New Consumer Contact Information
    Then I will verify an "EMAIL_SAVE_EVENT" is created for "email" event for Create New Consumer Contact Information
    Then I will verify subscriber received "EMAIL_SAVE_EVENT" event for "DPBI" is created for Create New Consumer Contact Information

  @asad @API-CP-62-06.3 @API-CRM @API-CP-62 @events @events-cc
  Scenario: Publish an PHONE_SAVE_EVENT for DPBI to consume
    When I will get the Authentication token for "" in "CRM"
    Given I initiated Case Loader API for Create New Consumer Contact Information
    When I run Case Loader API for Create New Consumer Contact Information
    Then I will verify an "PHONE_SAVE_EVENT" is created for "phone" event for Create New Consumer Contact Information
    Then I will verify subscriber received "PHONE_SAVE_EVENT" event for "DPBI" is created for Create New Consumer Contact Information

  @asad @API-CP-62-07.0 @API-CRM @API-CRM-Regression @API-CP-62 @API-CC @contacts-api-CC
  Scenario: Record Created On and Created By fields
    When I will get the Authentication token for "" in "CRM"
    Given I initiated Case Loader API for Create New Consumer Contact Information
    When I run Case Loader API for Create New Consumer Contact Information
    When I get consumer Id of the created consumer for Contact Information
    When I capture the contact information of the created consumer
    Then I will verify created on and created by for Create New Consumer Contact Information
