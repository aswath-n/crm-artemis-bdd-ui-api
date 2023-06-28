Feature: View Outbound Correspondence Details in Different Contexts

  @CP-17159 @CP-17159-01  @ui-ecms1 @RuslanL
  Scenario Outline: Verify active contact screen components are not visible and the active task screen components are not visible - context consumer on a case
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case ID and consumer search result
    And I create an Outbound Correspondence
    And I have selected "Case - CAONLY" as a type
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I verify Outbound Correspondence Successfully saved
    Then I navigate to the case and contact details
    And I click on the Outbound Correspondence Id result
    And I should not see the active contact screen components
    And I should not see the active task screen components
    Examples:
      | address1        | address2 | CITY   | State    | zipcode |
      | 100 Main street |empty| Canaan | New York | 12029   |


  @CP-17159 @CP-17159-02  @ui-ecms1 @RuslanL
  Scenario: Verify active contact screen components are not visible and the active task screen components are not visible - context consumer not on a case
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer not on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I have selected "Consumer - CONONLY" as a type
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click existing address
    Then I verify Outbound Correspondence Successfully saved
    Then I navigate to the case and contact details
    And I click on the Outbound Correspondence Id result
    And I should not see the active contact screen components
    And I should not see the active task screen components


  @CP-17159 @CP-17159-03  @ui-ecms1 @RuslanL
  Scenario: Verify user navigating to Outbound Correspondence Search screen after being on Outbound Correspondence Details
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId     | Random |
      | ConsumerId | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    And I store from search results
      | correspondenceId            |
      | correspondenceSearchResults |
      | searchPage                  |
    When I click on the "first available" Outbound Correspondence
    When I click on the back arrow button on Outbound Correspondence details
    Then I should be navigated to the Outbound Correspondence Global Search Page
    And I should see on Outbound Correspondence Global Search Page the same
      | correspondenceId            |
      | correspondenceSearchResults |
      | searchPage                  |


  @CP-17159 @CP-17159-04  @ui-ecms1 @RuslanL
  Scenario: Verify user navigating to Inbound Correspondence Details screen after being on Outbound Correspondence Details
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId     | Random |
      | ConsumerId | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType   | maersk Case + Consumer |
      | Language       | SPANISH                 |
      | Channel        | EMAIL                   |
      | Status         | COMPLETE                |
      | FromName       | AUTOMATION              |
      | ProcessType    | INBOUND CORRESPONDENCE  |
      | NotificationID | PreviouslyCreated       |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | COMPLETE                 |
      | documentType   | maersk Case + Consumer  |
      | NOTIFICATIONID | previouslyCreated        |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
    When I send the request to create the Inbound Correspondence Save Event
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | InboundDocument |
    And I click on a CID of "first available" Inbound Search Results
    And I click on the link to the Outbound Correspondence from the Inbound Details page that was "previouslyCreated"
    And I should see that I have been navigated to the Outbound Correspondence that was "previouslyCreated"
    When I click on the navigate back from Outbound Correspondence Details
    Then I should see that I have been navigated back to the Inbound Details page that was "previouslyCreated"