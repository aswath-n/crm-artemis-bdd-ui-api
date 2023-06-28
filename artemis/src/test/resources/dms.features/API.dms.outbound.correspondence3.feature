@DMS-35
Feature: Outbound Correspondence Request Part 3


  @CP-38900 @CP-38900-1a @API-ECMS @James
  Scenario: Verify 5 digit zip codes are stored on recipient information record
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | mailDestination | random active address |
      | writtenLanguage | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | CAONLY            |
      | caseId                          | previouslyCreated |
      | recipients                      | random            |
      | channel                         | Mail              |
      | zip                             | 12345             |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    Then I should see following values on the "previouslyCreated" Outbound Correspondence record
      | zip | 12345 |
    And I should see the following values in the "previouslyCreated" Outbound Correspondence letter data record
      | zip | 12345 |

  @CP-38900 @CP-38900-1b @API-ECMS @James
  Scenario: Verify 9 digit zip codes are stored on recipient information record
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | mailDestination | random active address |
      | writtenLanguage | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | CAONLY            |
      | caseId                          | previouslyCreated |
      | recipients                      | random            |
      | channel                         | Mail              |
      | zip                             | 123456789             |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    Then I should see following values on the "previouslyCreated" Outbound Correspondence record
      | zip | 123456789 |
    And I should see the following values in the "previouslyCreated" Outbound Correspondence letter data record
      | zip | 123456789 |

  @CP-38900 @CP-38900-1c @API-ECMS @James
  Scenario: Verify 9 with dash digit zip codes are stored on recipient information record
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | mailDestination | random active address |
      | writtenLanguage | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | CAONLY            |
      | caseId                          | previouslyCreated |
      | recipients                      | random            |
      | channel                         | Mail              |
      | zip                             | 12345-1234             |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    Then I should see following values on the "previouslyCreated" Outbound Correspondence record
      | zip | 12345-1234 |
    And I should see the following values in the "previouslyCreated" Outbound Correspondence letter data record
      | zip | 12345-1234 |


  @CP-38900 @CP-38900-2 @ui-ecms1 @James
  Scenario Outline: Verify zip codes are visible in UI
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | mailDestination | random active address |
      | writtenLanguage | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | CAONLY            |
      | caseId                          | previouslyCreated |
      | recipients                      | random            |
      | channel                         | Mail              |
      | zip                             | <zip>             |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | CORRESPONDENCEID | previouslyCreated |
    When I select the show details caret for an Outbound Correspondence
    Then I should see the "Mail" destination is visible on the Outbound Correspondence Search Results
    When I click on the "first available" Outbound Correspondence
    And I should see the "Mail" destination is visible on the Outbound Correspondence Details Page

    Examples:
      | zip        |
      | 12345      |
      | 12345-1234 |
      | 123456789  |