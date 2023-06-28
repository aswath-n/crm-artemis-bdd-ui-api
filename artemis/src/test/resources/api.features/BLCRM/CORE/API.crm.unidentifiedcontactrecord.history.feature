Feature: API-CRM: Unidentified Contact Record History Feature

  @API-CP-469 @API-CP-469-01 @asad @API-CRM-Regression  #Scenario Outline fails because of the defect CP-9225
  Scenario Outline: Capture User Edits for Unidentified Contact Record using API
    Given I create new unidentified contact record for Contact Record Edit using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I edit the unidentified contact Record using API
    Then I validate the Edited By Field from edit history
    Examples:
      |projectName|
      ||