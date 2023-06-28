Feature: API-CRM: Contact Record History Feature

  @API-CORE @API-CRM-SmokeTest @API-CRM-Regression
  Scenario: Get Autentication token for entire feature
    When I will get the Authentication token for "BLCRM" in "CRM"

  @API-CP-704 @API-CP-704-01 @asad @API-CORE  @API-CRM-Regression
  Scenario Outline: View Edited By user name using API
    Given I create new unidentified contact record for Contact Record Edit using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I edit the unidentified contact Record using API
    Then I validate the Edited By Field from edit history
    Examples:
      | projectName |
      |[blank]|