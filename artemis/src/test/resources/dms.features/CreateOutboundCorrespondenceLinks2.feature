Feature: Creating Outbound Correspondence from CP Links section BLCRM2

  @CP-28601 @CP-28601-07  @ui-ecms1 @RuslanL
  Scenario: Verify the Links section not appears on OB correspondence request page (from my Task - context of consumer on a case)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I navigate to "My Task" page
    And I will search for the task with status "In-Progress"
    And I click on the priority in dashboard
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I should NOT see the Links section under Save button
    When I select an Outbound Correspondence Type
    Then I should NOT see the Links section under Save button

  @CP-28601 @CP-28601-08 @ui-ecms1 @RuslanL
  Scenario: Verify the Links section not appears on OB correspondence request page (from my Task - context of a consumer without a case)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer not on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I navigate to "My Task" page
    And I will search for the task with status "In-Progress"
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I should NOT see the Links section under Save button
    When I select an Outbound Correspondence Type
    Then I should NOT see the Links section under Save button

  @CP-31609 @CP-31609-01 @ui-ecms2 @RuslanL
  Scenario: Verify link to Application type Medical Assistance on OB Correspondence details screen
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have request to create Application with type "Medical Assistance" and name "Application for Medical Assistance"
    Then I send a request to create Application
    And I retrieve the Application details by application ID
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | CasConsApp        |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
      | applicationID                   | previouslyCreated |
    When I send the request for an Outbound Correspondence to the service
    And I retrieve the Outbound Correspondence by Correspondence ID
    Given I logged into CRM
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | CORRESPONDENCEID | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate Application under links section contains following values
      | ApplicationID |
      | Name          |
      | Type          |
      | StatusDate    |
      | Status        |

  @CP-31609 @CP-31609-02 @ui-ecms2 @RuslanL
  Scenario: Verify link to Application type Long Term Care on OB Correspondence details screen
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have request to create Application with type "Long Term Care" and name "Application for Long Term Care"
    Then I send a request to create Application
    And I retrieve the Application details by application ID
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | CasConsApp        |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
      | applicationID                   | previouslyCreated |
    When I send the request for an Outbound Correspondence to the service
    And I retrieve the Outbound Correspondence by Correspondence ID
    Given I logged into CRM
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | CORRESPONDENCEID | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate Application under links section contains following values
      | ApplicationID |
      | Name          |
      | Type          |
      | StatusDate    |
      | Status        |

  @CP-31609 @CP-31609-03 @ui-ecms2 @RuslanL
  Scenario: Verify link to Application type Medical Assistance after updating Application status on OB Correspondence details screen
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have request to create Application with type "Medical Assistance" and name "Application for Medical Assistance"
    Then I send a request to create Application
    And I retrieve the Application details by application ID
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | CasConsApp        |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
      | applicationID                   | previouslyCreated |
    When I send the request for an Outbound Correspondence to the service
    And I retrieve the Outbound Correspondence by Correspondence ID
    Given I logged into CRM
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | CORRESPONDENCEID | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate Application under links section contains following values
      | ApplicationID |
      | Name          |
      | Type          |
      | StatusDate    |
      | Status        |
    And I update Application status to "Withdrawn" with reason "Already Receiving Services"
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | CORRESPONDENCEID |previouslyCreated|
    When I click on the "first available" Outbound Correspondence
    And I validate Application under links section contains following values
      | ApplicationID |
      | Name          |
      | Type          |
      | StatusDate    |
      | Status        |

  @CP-31609 @CP-31609-04 @ui-ecms2 @RuslanL
  Scenario: Verify link to Application type Long Term Care after updating Application status on OB Correspondence details screen
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have request to create Application with type "Long Term Care" and name "Application for Long Term Care"
    Then I send a request to create Application
    And I retrieve the Application details by application ID
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | CasConsApp        |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
      | applicationID                   | previouslyCreated |
    When I send the request for an Outbound Correspondence to the service
    And I retrieve the Outbound Correspondence by Correspondence ID
    Given I logged into CRM
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | CORRESPONDENCEID | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate Application under links section contains following values
      | ApplicationID |
      | Name          |
      | Type          |
      | StatusDate    |
      | Status        |
    And I update Application status to "Withdrawn" with reason "Already Receiving Services"
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | CORRESPONDENCEID |previouslyCreated|
    When I click on the "first available" Outbound Correspondence
    And I validate Application under links section contains following values
      | ApplicationID |
      | Name          |
      | Type          |
      | StatusDate    |
      | Status        |