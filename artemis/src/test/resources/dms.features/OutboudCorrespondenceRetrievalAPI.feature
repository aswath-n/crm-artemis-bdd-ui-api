Feature: Outbound Correspondence Retrieval API's

  @API-CP-29108 @API-CP-29108-01 @API-ECMS @RuslanL
  Scenario: Verify body data attribute exist, when retrieving by Correspondence ID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | default    |
      | language                        | English              |
      | caseId                          | previouslyCreated    |
      | regardingConsumerId             | Random               |
      | requesterId                     | 2425                 |
      | requesterType                   | ConnectionPoint      |
      | bodyData                        | First Name,Last Name |
    When I send the request for an Outbound Correspondence to the service
    And I retrieve the Outbound Correspondence by Correspondence ID
    And I should see "bodyData" attribute in the response, when retrieving by "GET" method

  @API-CP-29108 @API-CP-29108-02 @API-ECMS @RuslanL
  Scenario: Verify body data attribute exist, when retrieving by Case ID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | default           |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | Random            |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | bodyData                        | First Name,Last Name |
    When I send the request for an Outbound Correspondence to the service
    And I retrieve the Outbound Correspondence by "previouslyCreated" Case ID
    And I should see "bodyData" attribute in the response, when retrieving by "GET" method

  @API-CP-29108 @API-CP-29108-03 @API-ECMS @RuslanL
  Scenario: Verify body data attribute exist, when searching by only one criteria
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | default           |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | Random            |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | bodyData                        | First Name,Last Name |
    When I send the request for an Outbound Correspondence to the service
    And I retrieve the Outbound Correspondence by "single" criteria search
    And I should see "bodyData" attribute in the response, when retrieving by "POST" method

  @API-CP-29108 @API-CP-29108-04 @API-ECMS @RuslanL
  Scenario: Verify body data attribute exist, when searching by multiple criteria
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | default           |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | Random            |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | bodyData                        | First Name,Last Name |
    When I send the request for an Outbound Correspondence to the service
    And I retrieve the Outbound Correspondence by "multiple" criteria search
    And I should see "bodyData" attribute in the response, when retrieving by "POST" method


