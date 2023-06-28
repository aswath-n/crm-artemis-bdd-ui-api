Feature: API: Manual Contact Record Search Feature

  @API-CORE @API-CRM-SmokeTest @API-CRM-Regression
  Scenario: Get Autentication token for entire feature
    When I will get the Authentication token for "BLCRM" in "CRM"

  @API-CP-132 @API-CP-135 @API-CP-132 @API-CP-135 @API-CP-143 @asad @API-CORE @API-CRM-Regression
  Scenario Outline: Search Criteria for Contact Record API
    Given I create consumer and link Contact Record through api
     When I will get the Authentication token for "<projectName>" in "CRM"
    When I run contact search api for Contact Record
    Then I validate the search results for Contact Record
    Examples:
      |projectName|
      |[blank]|