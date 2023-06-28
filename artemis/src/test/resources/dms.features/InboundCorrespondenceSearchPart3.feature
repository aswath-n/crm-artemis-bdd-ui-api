Feature: Inbound Correspondence Search UI Part 3


  @CP-21025 @CP-21025-1 @ui-ecms1 @James
  Scenario: Verify that I can search by more than one CHANNEL
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CHANNEL1 | Text  |
      | CHANNEL2 | Email |
    Then I should see that all the inbound correspondence search results have the following values
      | CHANNEL1 | Text  |
      | CHANNEL2 | Email |

  @CP-21025 @CP-21025-2 @ui-ecms1 @James
  Scenario: Verify that I can search by multiple CHANNELS
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CHANNEL1            | Mail       |
      | CHANNEL2            | Fax        |
      | CASEID              | 60690      |
      | CUSTOMEDATERECEIVED | 02/09/2022 |
    Then I should see that all the inbound correspondence search results have the following values
      | CHANNEL1 | Mail  |
      | CHANNEL2 | FAX   |
      | CASEID   | 60690 |

  @CP-20565 @CP-20565-1 @ui-ecms1 @James
  Scenario: Verify that type drop down is in alphabetical order
    Given I logged into CRM
    And I click on the main navigation menu
    When I should see the Inbound Correspondence Search Icon
    Then I should see the Type drop down is in alphabetical order

  @CP-20565 @ui-ecms1 @James @CP-20565-02
  Scenario: Verify that type drop down in Inbound Details Page is in alphabetical order
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    When I click on the edit button on the Inbound Document Details Page
    Then I should see the Type drop down in the Inbound Document Details page is in alphabetical order

  @CP-8958 @ui-ecms1 @James @CP-8958-1
  Scenario: Verify channels are pulled from Inbound Settings API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I logged into CRM
    And I click on the main navigation menu
    When I should see the Inbound Correspondence Search Icon
    Then I verify channel drop down value in the Inbound Correspondence Search
      | Inbound Settings API |

  @CP-22376 @CP-22376-01 @API-ECMS @James
  Scenario: Verify I am to search for metadata from Inbound Search API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | MAIL From    | Random                  |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    And I send the request for the Inbound Correspondence using the endpoint for Digital
    When I search for Inbound Correspondences by the following criteria
      | METADATA:MAIL From | Random |
    Then I should see the "previouslyCreated" Inbound Correspondence is in the search results

  @CP-22376 @CP-22376-02 @API-ECMS @James
  Scenario: Verify I am to search for metadata and case id from Inbound Search API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | MAIL From    | Random                  |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    And I send the request for the Inbound Correspondence using the endpoint for Digital
    When I search for Inbound Correspondences by the following criteria
      | METADATA:MAIL From | Random |
      | caseId             | Random |
    Then I should see the "previouslyCreated" Inbound Correspondence is in the search results

  @CP-22376 @CP-22376-03 @API-ECMS @James
  Scenario: Verify I am to search for metadata and channel from Inbound Search API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | MAIL From    | Random                  |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    And I send the request for the Inbound Correspondence using the endpoint for Digital
    When I search for Inbound Correspondences by the following criteria
      | METADATA:MAIL From | Random |
      | channel            | Email  |
    Then I should see the "previouslyCreated" Inbound Correspondence is in the search results

  @CP-22376 @CP-22376-04 @API-ECMS @James
  Scenario: Verify I am to search for metadata and caseId and consumerId from Inbound Search API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | ConsumerID   | Random                  |
      | CaseID       | Random                  |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | MAIL From    | Random                  |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    And I send the request for the Inbound Correspondence using the endpoint for Digital
    When I search for Inbound Correspondences by the following criteria
      | METADATA:MAIL From | Random            |
      | consumerId         | previouslyCreated |
      | caseId             | previouslyCreated |
    Then I should see the "previouslyCreated" Inbound Correspondence is in the search results

  @CP-22376 @CP-22376-05 @API-ECMS @James
  Scenario: Verify I am to search for metadata and filter out unwanted case and consumer ids from Inbound Search API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | ConsumerID   | 123                     |
      | CaseID       | 123                     |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | MAIL From    | Random                  |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    And I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | ConsumerID   | 456                     |
      | CaseID       | 456                     |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | MAIL From    | Same                    |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    And I send the request for another Inbound Correspondence using the endpoint for Digital
    When I search for Inbound Correspondences by the following criteria
      | METADATA:MAIL From | Random |
      | consumerId         | 123    |
      | caseId             | 123    |
    Then I should see the "previouslyCreated" Inbound Correspondence is in the search results
    And I should not see the "second" Inbound Correspondence in the search results

  @CP-22376 @CP-22376-06 @API-ECMS @James
  Scenario: Verify I am to search for metadata and filter out unwanted channel criteria from Inbound Search API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | Mail                    |
      | Status       | COMPLETE                |
      | MAIL From    | Random                  |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    And I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | MAIL From    | Same                    |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    And I send the request for another Inbound Correspondence using the endpoint for Digital
    When I search for Inbound Correspondences by the following criteria
      | METADATA:MAIL From | Random |
      | channel            | Mail   |
    Then I should see the "previouslyCreated" Inbound Correspondence is in the search results
    And I should not see the "second" Inbound Correspondence in the search results

  @CP-22376 @CP-22376-07 @API-ECMS @James
  Scenario: Verify I am to search for metadata and filter out unwanted name criteria from Inbound Search API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | Mail                    |
      | Status       | COMPLETE                |
      | MAIL From    | Random                  |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | FromName     | firstName               |
    And I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | MAIL From    | Same                    |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | FromName     | secondName              |
    And I send the request for another Inbound Correspondence using the endpoint for Digital
    When I search for Inbound Correspondences by the following criteria
      | METADATA:MAIL From | Random    |
      | channel            | Mail      |
      | fromName           | firstName |
    Then I should see the "previouslyCreated" Inbound Correspondence is in the search results
    And I should not see the "second" Inbound Correspondence in the search results

  @CP-22376 @CP-22376-08 @API-ECMS @James
  Scenario: Verify I am to search for metadata and filter out unwanted email criteria from Inbound Search API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType     | maersk Case + Consumer       |
      | Language         | SPANISH                       |
      | Channel          | Mail                          |
      | Status           | COMPLETE                      |
      | MAIL From        | Random                        |
      | ProcessType      | INBOUND CORRESPONDENCE        |
      | FromEmailAddress | firstemail@automationasdf.com |
    And I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType     | maersk Case + Consumer        |
      | Language         | SPANISH                        |
      | Channel          | EMAIL                          |
      | Status           | COMPLETE                       |
      | MAIL From        | Same                           |
      | ProcessType      | INBOUND CORRESPONDENCE         |
      | FromEmailAddress | secondemail@automationasdf.com |
    And I send the request for another Inbound Correspondence using the endpoint for Digital
    When I search for Inbound Correspondences by the following criteria
      | METADATA:MAIL From | Random                        |
      | channel            | Mail                          |
      | fromEmailAddress   | firstemail@automationasdf.com |
    Then I should see the "previouslyCreated" Inbound Correspondence is in the search results
    And I should not see the "second" Inbound Correspondence in the search results