Feature: API-CRM: View Contact Record History Feature

  @API-CP-462 @API-CP-462-01 @asad @API-CRM @API-CRM-Regression @API-CRM-SmokeTest #Scenario Outline fails because of the defect CP-9225
  Scenario Outline: View Edited By user name using API
    Given I create new contact record for Contact Record Edit using API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I edit the contact Record using API
    Then I validate the edit history for Contact Record
    Examples:
      |projectName|
      ||

    

  @API-CP-462 @API-CP-462-02 @asad @API-CRM @API-CRM-Regression @API-CRM-SmokeTest  #Scenario Outline fails because of the defect CP-9225
  Scenario Outline: View Full Edited details using API
    Given I create new contact record for Contact Record Edit using API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I edit the contact Record using API
    Then I validate the Edited By Fields from edit history for Contact Record
    Examples:
      |projectName|
      ||
