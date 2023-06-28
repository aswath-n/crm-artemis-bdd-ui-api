Feature: API: Third Party Contact Record Details Feature

  @API-CP-147 @API-CP-147-01 @asad @crmCoreAPI @contact-record-api-CRMC @API-CRM-SmokeTest @API-CRM-Regression
  Scenario Outline: Viewing Inbound third Party Contact Record for API
    Given I Initiated Create consumer API for creating consumer for third party contact Record
     When I will get the Authentication token for "<projectName>" in "CRM"
    When I create consumer for "Inbound" Third Party Contact using API
    And I initiate and search for third party contact record details
    Then I am able to verify the complete "Inbound" third party Contact Record
    Examples:
      |projectName|
      ||

  @API-CP-147 @API-CP-147-02 @asad @crmCoreAPI @contact-record-api-CRMC @API-CRM-Regression
  Scenario Outline: Viewing Outbound third Party Contact Record for API
    Given I Initiated Create consumer API for creating consumer for third party contact Record
     When I will get the Authentication token for "<projectName>" in "CRM"
    When I create consumer for "Outbound" Third Party Contact using API
    And I initiate and search for third party contact record details
    Then I am able to verify the complete "Outbound" third party Contact Record
    Examples:
      |projectName|
      ||