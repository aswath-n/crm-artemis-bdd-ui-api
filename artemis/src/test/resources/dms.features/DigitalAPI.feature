Feature: Validate Digital API

  @CP-18772 @CP-18772-1 @API-ECMS @James
  Scenario: Verify I can add keywords to digital endpoint
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    Then I should see that the following values exist for the Inbound Document that was previously retrieved
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |

  @CP-18772 @CP-18772-2 @API-ECMS @James
  Scenario: Verify I can add Meta Data Records keyword records to digital endpoint
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers Requiring Metadata |
    And I have the request contains Meta Data Records for Document Type "maersk Case + Consumers Requiring Metadata" with random data instances for the following
      | Consumer | 2 |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    Then I should see that the following values exist for the Inbound Document that was previously retrieved
      | documentType | maersk Case + Consumers Requiring Metadata |
    And I should see that the following multiInstanceKeywordRecord occurrences that were previously created are present in the Inbound Document
      | Consumer | 2 |

  @CP-18772 @CP-18772-3 @api-ecms-coverva @James
  Scenario: Verify I can add Meta Data keywords for VACV Newborn Form to digital endpoint
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | VACV Newborn Form      |
      | Channel      | WEB PORTAL             |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
    And I have the request contains Meta Data Records for Document Type "VACV Newborn Form" with random data instances for the following
      | VACVNewborn        | 3 |
      | VACVNewbornParents | 1 |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    Then I should see that the following values exist for the Inbound Document that was previously retrieved
      | documentType | VACV Newborn Form      |
      | Channel      | WEB PORTAL             |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
    And I should see that the following multiInstanceKeywordRecord occurrences that were previously created are present in the Inbound Document
      | VACVNewborn        | 3 |
      | VACVNewbornParents | 1 |

  @API-CP-25566 @API-CP-25566-01 @API-ECMS @RuslanL
  Scenario: Verify the endpoint will return the ConsumerID values in its array, when search by CID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers Requiring Metadata |
    And I have the request contains Meta Data Records for Document Type "maersk Case + Consumers Requiring Metadata" with random data instances for the following
      | Consumer | 3 |
    And I send the request for the Inbound Correspondence using the endpoint for Digital
    When I search for Inbound Correspondences by the following criteria
      | id | Random |
    Then I should see an array of consumerIDs in the search results, when search by "id"

  @API-CP-25566 @API-CP-25566-02 @API-ECMS @RuslanL
  Scenario: Verify the endpoint will return the ConsumerID values in its array, when search by setId
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers Requiring Metadata |
      | SetId        | random                                      |
    And I have the request contains Meta Data Records for Document Type "maersk Case + Consumers Requiring Metadata" with random data instances for the following
      | Consumer | 3 |
    And I send the request for the Inbound Correspondence using the endpoint for Digital
    When I search for Inbound Correspondences by the following criteria
      | setId | Random |
    Then I should see an array of consumerIDs in the search results, when search by "setId"

  @API-CP-25566 @API-CP-25566-03 @API-ECMS @RuslanL
  Scenario: Verify the endpoint will return the ConsumerID values in its array, when search by date received
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers Requiring Metadata |
    And I have the request contains Meta Data Records for Document Type "maersk Case + Consumers Requiring Metadata" with random data instances for the following
      | Consumer | 3 |
    And I send the request for the Inbound Correspondence using the endpoint for Digital
    When I search for Inbound Correspondences by the following criteria
      | dateReceived | Random |
    Then I should see an array of consumerIDs in the search results, when search by "dateReceived"
    
