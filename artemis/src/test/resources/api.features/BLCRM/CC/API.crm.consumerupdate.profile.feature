Feature: Consumer Update Profile

  @API-CC @API-CRM-SmokeTest @API-CRM-Regression
  Scenario: Get Autentication token for entire feature
    When I will get the Authentication token for "BLCRM" in "CRM"

  @API-CP-335 @API-CP-335-01 @API-CC @API-CRM-Regression @asad
  Scenario Outline: Update Consumer using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated update consumer API for profile search
    When I provide information to update consumer for profile search
    And I run update consumer API for profile search
    And I search for consumer profile updated using API
    Then I am able to verify the updated consumer profile
    Examples:
      |projectName|
      |[blank]|

  @API-CP-335 @API-CP-335-02 @API-CC @API-CRM-Regression @asad
  Scenario Outline: Update Consumer by information using API
    Given I initiated update consumer API for profile search
     When I will get the Authentication token for "<projectName>" in "CRM"
    When I provide information to update consumer for profile search
    And I run update consumer API for profile search
    And I search for consumer profile updated using API
    Then I am able to verify the updated by information
    Examples:
      |projectName|
      |[blank]|