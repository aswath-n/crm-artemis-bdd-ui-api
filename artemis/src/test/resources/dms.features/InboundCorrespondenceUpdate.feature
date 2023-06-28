
Feature: System Process Update to Inbound Correspondence

  @CP-3096 @CP-3096-01  @Sang
  Scenario: Verify Id is required
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I send a request to update the Inbound Correspondence document without an ID
      | caseId | 13 |
    Then I should see the update failed because Id was required

#  @CP-3096 @CP-3096-02  @Sang scenario no longer valid
#  Scenario: Verify Id and one other field is required
#    Given I will get the Authentication token for "<projectName>" in "CRM"
#    When I send a request to update the Inbound Correspondence with only an id
#      | id | previouslyCreated |
#    Then I should see the update failed because one other field was required

  @CP-3096 @CP-3096-03 @API-ECMS @Sang
  Scenario: Verify that updatedBy and updatedOn are saved when udpating
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers |
      | Language     | SPANISH                  |
      | Channel      | EMAIL                    |
      | Status       | COMPLETE                 |
      | Origin       | WEB PORTAL               |
      | FromName     | AUTOMATION               |
      | FormVersion  | 3                        |
      | ProcessType  | INBOUND CORRESPONDENCE   |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | documentType          | maersk Case + Consumer |
      | status                | RECIEVED                |
      | caseId                | 13                      |
      | consumerId            | 17500, 21               |
      | replacementDocumentId | 0                       |
      | note                  | test                    |
    Then I initiate Get inbound correspondence document by "previouslyCreated" document Id and verify the following
      | updatedBy | AUTOMATION            |
      | updatedOn | current date and hour |

  @CP-3096 @CP-3096-04 @API-ECMS  @Sang
  Scenario: Verify that status element also sets statusSetOn
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | FormVersion  | 3                       |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I send a request to update the Inbound Correspondence Document
      | id     | previouslyCreated |
      | status | SENT              |
    Then I initiate Get inbound correspondence document by "previouslyCreated" document Id and verify the following
      | statusSetOn | current date and hour |

  @CP-3096 @CP-3096-05 @API-ECMS  @Sang
  Scenario: Verify that I can update status for an Inbound Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | TRANSMITTING            |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | FormVersion  | 3                       |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I send a request to update the Inbound Correspondence Document
      | id     | previouslyCreated |
      | status | INDEXED           |
    And I send a request to update the Inbound Correspondence Document
      | id     | previouslyCreated |
      | status | COMPLETE          |
    Then I initiate Get inbound correspondence document by "previouslyCreated" document Id and verify the following
      | status | COMPLETE |

  @CP-3096 @CP-3096-06 @API-ECMS  @Sang
  Scenario: Verify that I can update caseId for an Inbound Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | FormVersion  | 3                       |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I send a request to update the Inbound Correspondence Document
      | id     | previouslyCreated |
      | caseId | 9                 |
    And I send a request to update the Inbound Correspondence Document
      | id     | previouslyCreated |
      | caseId | 13                |
    Then I initiate Get inbound correspondence document by "previouslyCreated" document Id and verify the following
      | caseId | 13 |

  @CP-3096 @CP-3096-07 @API-ECMS  @Sang
  Scenario: Verify that I can clear caseId for an Inbound Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | FormVersion  | 3                       |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I send a request to update the Inbound Correspondence Document
      | id     | previouslyCreated |
      | caseId | 9                 |
    And I send a request to update the Inbound Correspondence Document
      | id                    | previouslyCreated |
      | caseId                | null              |
      | replacementDocumentId | 911               |
    Then I initiate Get inbound correspondence document by "previouslyCreated" document Id and verify the following
      | caseId | null |

  @CP-3096 @CP-3096-08 @API-ECMS  @Sang
  Scenario: Verify that I can update replacementDocumentId for an Inbound Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | FormVersion  | 3                       |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I send a request to update the Inbound Correspondence Document
      | id                    | previouslyCreated |
      | replacementDocumentId | 9                 |
    And I send a request to update the Inbound Correspondence Document
      | id                    | previouslyCreated |
      | replacementDocumentId | 13                |
    Then I initiate Get inbound correspondence document by "previouslyCreated" document Id and verify the following
      | replacementDocumentId | 13 |

  @CP-3096 @CP-3096-09 @API-ECMS  @Sang
  Scenario: Verify that I can clear replacementDocumentId for an Inbound Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | FormVersion  | 3                       |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I send a request to update the Inbound Correspondence Document
      | id                    | previouslyCreated |
      | replacementDocumentId | 9                 |
    And I send a request to update the Inbound Correspondence Document
      | id                    | previouslyCreated |
      | replacementDocumentId | null              |
      | caseId                | 13                |
    Then I initiate Get inbound correspondence document by "previouslyCreated" document Id and verify the following
      | replacementDocumentId | null |

  @CP-3096 @CP-3096-10 @API-ECMS  @Sang
  Scenario: Verify that I can update consumerId for an Inbound Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | FormVersion  | 3                       |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I send a request to update the Inbound Correspondence Document
      | id         | previouslyCreated |
      | consumerId | [9]               |
    And I send a request to update the Inbound Correspondence Document
      | id         | previouslyCreated |
      | consumerId | [13]              |
    Then I initiate Get inbound correspondence document by "previouslyCreated" document Id and verify the following
      | consumerId | [13] |

  @CP-3096 @CP-3096-11 @API-ECMS  @Sang
  Scenario: Verify that I can update multiple consumerId for an Inbound Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | FormVersion  | 3                       |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I send a request to update the Inbound Correspondence Document
      | id         | previouslyCreated |
      | consumerId | [9,13]            |
    And I send a request to update the Inbound Correspondence Document
      | id         | previouslyCreated |
      | consumerId | [21,22]           |
    Then I initiate Get inbound correspondence document by "previouslyCreated" document Id and verify the following
      | consumerId | [21,22] |

  @CP-3096 @CP-3096-12 @API-ECMS  @Sang
  Scenario: Verify that I can update some multiple consumerId for an Inbound Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | FormVersion  | 3                       |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I send a request to update the Inbound Correspondence Document
      | id         | previouslyCreated |
      | consumerId | [9,13]            |
    And I send a request to update the Inbound Correspondence Document
      | id         | previouslyCreated |
      | consumerId | [9,21,22]         |
    Then I initiate Get inbound correspondence document by "previouslyCreated" document Id and verify the following
      | consumerId | [9,21,22] |

  @CP-3096 @CP-3096-13 @API-ECMS  @Sang
  Scenario: Verify that I can add some multiple consumerId for an Inbound Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | FormVersion  | 3                       |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I send a request to update the Inbound Correspondence Document
      | id         | previouslyCreated |
      | consumerId | [9,13]            |
    And I send a request to update the Inbound Correspondence Document
      | id         | previouslyCreated |
      | consumerId | [9,13,21,22]      |
    Then I initiate Get inbound correspondence document by "previouslyCreated" document Id and verify the following
      | consumerId | [9,13,21,22] |

  @CP-3096 @CP-3096-14 @API-ECMS  @Sang
  Scenario: Verify that I can add multiple consumerId for an Inbound Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | FormVersion  | 3                       |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I send a request to update the Inbound Correspondence Document
      | id         | previouslyCreated |
      | consumerId | null              |
      | caseId     | 13                |
    And I send a request to update the Inbound Correspondence Document
      | id         | previouslyCreated |
      | consumerId | [21,22]           |
    Then I initiate Get inbound correspondence document by "previouslyCreated" document Id and verify the following
      | consumerId | [21,22] |

  @CP-3096 @CP-3096-15 @API-ECMS  @Sang
  Scenario: Verify that I can clear consumerId for an Inbound Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | FormVersion  | 3                       |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I send a request to update the Inbound Correspondence Document
      | id         | previouslyCreated |
      | consumerId | [9]               |
    And I send a request to update the Inbound Correspondence Document
      | id         | previouslyCreated |
      | consumerId | null              |
      | caseId     | 13                |
    Then I initiate Get inbound correspondence document by "previouslyCreated" document Id and verify the following
      | consumerId | null |

  @CP-3096 @CP-3096-16 @API-ECMS @Sang
  Scenario: Verify that I can clear with empty array consumerId for an Inbound Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | FormVersion  | 3                       |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I send a request to update the Inbound Correspondence Document
      | id         | previouslyCreated |
      | consumerId | [9]               |
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | consumerId | null |
    Then I initiate Get inbound correspondence document by "previouslyCreated" document Id and verify the following
      | consumerId | [] |

  @CP-3096 @CP-3096-17 @API-ECMS @Sang
  Scenario: Verify I can add a note to an Inbound Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | FormVersion  | 3                       |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    When I send a request to update the Inbound Correspondence Document
      | id   | previouslyCreated |
      | note | RandomString      |
    Then I initiate Get inbound correspondence document by "previouslyCreated" document Id and verify the following
      | note | RandomString |

  @CP-3096 @CP-3096-18 @API-ECMS @Sang
  Scenario: Verify I can add 500 free char note to an Inbound Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | FormVersion  | 3                       |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    When I send a request to update the Inbound Correspondence Document
      | id   | previouslyCreated |
      | note | RandomString500   |
    Then I initiate Get inbound correspondence document by "previouslyCreated" document Id and verify the following
      | note | RandomString500 |

  @CP-3096 @CP-3096-19 @API-ECMS  @Sang
  Scenario: Verify when only id is provided, will get error and why
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | FormVersion  | 3                       |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    When I send a request to update the Inbound Correspondence document without an ID
      | caseId | 13 |
    Then I should see the update failed because Id was required with error message
      | ID is either missing or Invalid |

  @CP-3096 @CP-3096-20 @API-ECMS  @Sang
  Scenario: Verify I am able to update the documentType of a Inbound Correspondence in NJ-SBE
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | FormVersion  | 3                       |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I send a request to update the Inbound Correspondence Document
      | id           | previouslyCreated       |
      | documentType | maersk Case + Consumer |
    And I send a request to update the Inbound Correspondence Document
      | id           | previouslyCreated |
      | documentType | maersk Case      |
    Then I initiate Get inbound correspondence document by "previouslyCreated" document Id and verify the following
      | documentType | maersk Case |

  @CP-3257 @CP-3257-1 @ui-ecms1 @James
  Scenario: Verify edit button on the Inbound Document Details Page is available
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    When I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on the Inbound Document CID link "firstAvailable"
    Then I should see the edit button on the Inbound Document Details Page is available

  @CP-3257 @CP-3257-2 @ui-ecms1 @James
  Scenario: Verify option to cancel after clicking on edit button on the Inbound Document Details Page is available
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    When I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on the Inbound Document CID link "firstAvailable"
    When I click on the edit button on the Inbound Document Details Page
    Then I should see the cancel and save button is visible on the Inbound Document Details Page

  @CP-22375 @CP-22375-01 @API-ECMS @RuslanL
  Scenario: Verify I can add element to metaData using add keyword to the inbound document
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | TRANSMITTING            |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | metaData | MAIL From_AddKeyword |
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    Then I should see that the following values exist for the Inbound Document that was previously retrieved
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | TRANSMITTING            |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | METADATA     | AddKeyword              |

  @CP-22375 @CP-22375-02 @API-ECMS @RuslanL
  Scenario: Verify I can multiple add keywords to the inbound document
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | TRANSMITTING            |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | metaData | MAIL From,MAIL Subject,MAIL Cc_AddKeyword |
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    Then I should see that the following values exist for the Inbound Document that was previously retrieved
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | TRANSMITTING            |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | METADATA     | AddKeyword              |


  @CP-22375 @CP-22375-03 @API-ECMS @RuslanL
  Scenario: Verify I can change value in metaData using replace keyword to the inbound document
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | TRANSMITTING            |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | metaData | MAIL From,MAIL Subject,MAIL Cc_AddKeyword |
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    Then I should see that the following values exist for the Inbound Document that was previously retrieved
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | TRANSMITTING            |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | METADATA     | AddKeyword              |
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | metaData | MAIL From_ReplaceKeyword |
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    Then I should see that the following values exist for the Inbound Document that was previously retrieved
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | TRANSMITTING            |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | METADATA     | ReplaceKeyword          |


  @CP-22375 @CP-22375-04 @API-ECMS @RuslanL
  Scenario: Verify I can delete element from metaData using delete keyword from the inbound document
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | TRANSMITTING            |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | metaData | MAIL From,MAIL Subject,MAIL Cc_AddKeyword |
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    Then I should see that the following values exist for the Inbound Document that was previously retrieved
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | TRANSMITTING            |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | METADATA     | AddKeyword              |
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | metaData | MAIL From_DeleteKeyword |
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    Then I should see that the following values exist for the Inbound Document that was previously retrieved
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | TRANSMITTING            |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | METADATA     | DeleteKeyword           |

  @CP-22375 @CP-22375-05 @API-ECMS @RuslanL
  Scenario: Verify I can delete multiple elements of the same type from metaData using delete keyword from the inbound document
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | TRANSMITTING            |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | metaData | MAIL Subject,MAIL Subject,MAIL From,MAIL Cc_AddKeyword |
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    Then I should see that the following values exist for the Inbound Document that was previously retrieved
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | TRANSMITTING            |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | METADATA     | AddKeyword              |
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | metaData | MAIL Subject_DeleteKeyword* |
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    Then I should see that the following values exist for the Inbound Document that was previously retrieved
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | TRANSMITTING            |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | METADATA     | DeleteKeyword*          |

  @CP-22375 @CP-22375-06 @API-ECMS @RuslanL
  Scenario: Verify I can add and delete keywords in the same request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | TRANSMITTING            |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | metaData | MAIL Status,MAIL Subject,MAIL Cc,MAIL To_AddKeyword |
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    Then I should see that the following values exist for the Inbound Document that was previously retrieved
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | TRANSMITTING            |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | METADATA     | AddKeyword              |
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | metaData | MAIL Status,MAIL Subject,MAIL Cc,MAIL To_AddKeywordDeleteKeyword |
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    Then I should see that the following values exist for the Inbound Document that was previously retrieved
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | TRANSMITTING            |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | METADATA     | AddKeywordDeleteKeyword |

  @CP-22375 @CP-22375-07 @api-ecms-coverva @RuslanL
  Scenario: Verify I can add element using add keyword to the inbound document
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | VACV Newborn Form      |
      | Channel      | WEB PORTAL             |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
    And I have the request contains Meta Data Records for Document Type "VACV Newborn Form" with random data instances for the following
      | VACVNewborn        | 1 |
      | VACVNewbornParents | 1 |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | metaData | fromEmailAddress_AddKeyword |
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    Then I should see that the following values exist for the Inbound Document that was previously retrieved
      | documentType | VACV Newborn Form      |
      | Channel      | WEB PORTAL             |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | METADATA     | AddKeyword             |
    And I should see that the following multiInstanceKeywordRecord occurrences that were previously created are present in the Inbound Document
      | VACVNewborn        | 1 |
      | VACVNewbornParents | 1 |


  @CP-22375 @CP-22375-08 @api-ecms-coverva @RuslanL
  Scenario: Verify I can change value using replace keyword to the inbound document
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | VACV Newborn Form      |
      | Channel      | WEB PORTAL             |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
    And I have the request contains Meta Data Records for Document Type "VACV Newborn Form" with random data instances for the following
      | VACVNewborn        | 1 |
      | VACVNewbornParents | 1 |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | metaData | fromEmailAddress_AddKeyword |
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    Then I should see that the following values exist for the Inbound Document that was previously retrieved
      | documentType | VACV Newborn Form      |
      | Channel      | WEB PORTAL             |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | METADATA     | AddKeyword             |
    And I should see that the following multiInstanceKeywordRecord occurrences that were previously created are present in the Inbound Document
      | VACVNewborn        | 1 |
      | VACVNewbornParents | 1 |
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | metaData | fromEmailAddress_ReplaceKeyword |
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    Then I should see that the following values exist for the Inbound Document that was previously retrieved
      | documentType | VACV Newborn Form      |
      | Channel      | WEB PORTAL             |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | METADATA     | ReplaceKeyword         |
    And I should see that the following multiInstanceKeywordRecord occurrences that were previously created are present in the Inbound Document
      | VACVNewborn        | 1 |
      | VACVNewbornParents | 1 |


  @CP-22375 @CP-22375-09 @api-ecms-coverva @RuslanL
  Scenario: Verify I can delete element using delete keyword from the inbound document
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | VACV Newborn Form      |
      | Channel      | WEB PORTAL             |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
    And I have the request contains Meta Data Records for Document Type "VACV Newborn Form" with random data instances for the following
      | VACVNewborn        | 1 |
      | VACVNewbornParents | 1 |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | metaData | fromEmailAddress_AddKeyword |
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    Then I should see that the following values exist for the Inbound Document that was previously retrieved
      | documentType | VACV Newborn Form      |
      | Channel      | WEB PORTAL             |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | METADATA     | AddKeyword             |
    And I should see that the following multiInstanceKeywordRecord occurrences that were previously created are present in the Inbound Document
      | VACVNewborn        | 1 |
      | VACVNewbornParents | 1 |
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | metaData | fromEmailAddress_DeleteKeyword |
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    Then I should see that the following values exist for the Inbound Document that was previously retrieved
      | documentType | VACV Newborn Form      |
      | Channel      | WEB PORTAL             |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | METADATA     | DeleteKeyword          |
    And I should see that the following multiInstanceKeywordRecord occurrences that were previously created are present in the Inbound Document
      | VACVNewborn        | 1 |
      | VACVNewbornParents | 1 |

  @CP-22375 @CP-22375-10 @api-ecms-coverva @RuslanL
  Scenario: Verify I can add and delete keywords in the same request
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | VACV Newborn Form      |
      | Channel      | WEB PORTAL             |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
    And I have the request contains Meta Data Records for Document Type "VACV Newborn Form" with random data instances for the following
      | VACVNewborn        | 1 |
      | VACVNewbornParents | 1 |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | metaData | fromEmailAddress,setId,language_AddKeyword |
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    Then I should see that the following values exist for the Inbound Document that was previously retrieved
      | documentType | VACV Newborn Form      |
      | Channel      | WEB PORTAL             |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | METADATA     | AddKeyword             |
    And I should see that the following multiInstanceKeywordRecord occurrences that were previously created are present in the Inbound Document
      | VACVNewborn        | 1 |
      | VACVNewbornParents | 1 |
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | metaData | fromEmailAddress,setId,language_AddKeywordDeleteKeyword |
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    Then I should see that the following values exist for the Inbound Document that was previously retrieved
      | documentType | VACV Newborn Form       |
      | Channel      | WEB PORTAL              |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | METADATA     | AddKeywordDeleteKeyword |

  @CP-22375 @CP-22375-11 @api-ecms-coverva @RuslanL
  Scenario: Verify I change value from Ready to Extracted for VACV Newman Extract Status
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | VACV Newborn Form      |
      | Channel      | WEB PORTAL             |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
    And I have the request contains Meta Data Records for Document Type "VACV Newborn Form" with random data instances for the following
      | VACVNewborn        | 1 |
      | VACVNewbornParents | 1 |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | metaData | VACV Newman Extract Status(READY)_AddKeyword |
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    Then I should see that the following values exist for the Inbound Document that was previously retrieved
      | documentType | VACV Newborn Form      |
      | Channel      | WEB PORTAL             |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | METADATA     | AddKeyword             |
    And I should see that the following multiInstanceKeywordRecord occurrences that were previously created are present in the Inbound Document
      | VACVNewborn        | 1 |
      | VACVNewbornParents | 1 |
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | metaData | VACV Newman Extract Status(EXTRACTED)_ReplaceKeyword |
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    Then I should see that the following values exist for the Inbound Document that was previously retrieved
      | documentType | VACV Newborn Form      |
      | Channel      | WEB PORTAL             |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | METADATA     | ReplaceKeyword         |
    And I should see that the following multiInstanceKeywordRecord occurrences that were previously created are present in the Inbound Document
      | VACVNewborn        | 1 |
      | VACVNewbornParents | 1 |

  @CP-22375 @CP-22375-12 @api-ecms-coverva @RuslanL
  Scenario: Verify I change value from Ready to Failed for VACV Newman Extract Status
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | VACV Newborn Form      |
      | Channel      | WEB PORTAL             |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
    And I have the request contains Meta Data Records for Document Type "VACV Newborn Form" with random data instances for the following
      | VACVNewborn        | 1 |
      | VACVNewbornParents | 1 |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | metaData | VACV Newman Extract Status(READY)_AddKeyword |
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    Then I should see that the following values exist for the Inbound Document that was previously retrieved
      | documentType | VACV Newborn Form      |
      | Channel      | WEB PORTAL             |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | METADATA     | AddKeyword             |
    And I should see that the following multiInstanceKeywordRecord occurrences that were previously created are present in the Inbound Document
      | VACVNewborn        | 1 |
      | VACVNewbornParents | 1 |
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | metaData | VACV Newman Extract Status(FAILED)_ReplaceKeyword |
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    Then I should see that the following values exist for the Inbound Document that was previously retrieved
      | documentType | VACV Newborn Form      |
      | Channel      | WEB PORTAL             |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | METADATA     | ReplaceKeyword         |
    And I should see that the following multiInstanceKeywordRecord occurrences that were previously created are present in the Inbound Document
      | VACVNewborn        | 1 |
      | VACVNewbornParents | 1 |


  @CP-34580 @CP-34580-01 @API-ECMS @RuslanL
  Scenario Outline: Verify system captures Project Name and Project ID via Correspondence Definition
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have a request to update Inbound Correspondence definition for type "maersk Case + Consumer" and "<projectName>" with the following values
      | projectId   | valid |
      | projectName | valid |
    Then I send a request to update the Inbound Correspondence definition
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | ENGLISH                 |
      | Channel      | MAIL                    |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE           | CREATEDDOCUMENT          |
      | documentType   | maersk Case + Consumer  |
      | Channel        | MAIL                     |
      | ProcessType    | RECEIVED                 |
      | documentHandle | InboundDocumentIdDigital |
    And I send the request to create the Inbound Correspondence Save Event
    And I search and verify the latest "INBOUND_CORRESPONDENCE_SAVE_EVENT"
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | status | RESCAN REQUESTED |
    And I search and verify the latest "INBOUND_CORRESPONDENCE_INDEXED_EVENT"
    Examples:
      | projectName |
      | NJ-SBE      |
      | CoverVA     |
      | IN-EB       |
      | BLCRM       |

  @CP-34580 @CP-34580-02 @API-ECMS @RuslanL
  Scenario Outline: Verify if the response is an error and no projectId and projectName are retrieved, the service reject the request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have a request to update Inbound Correspondence definition for type "maersk Case + Consumer" and "<projectName>" with the following values
      | projectId      | valid   |
      | projectName    | valid   |
      | definitionName | invalid |
    And I send a request to update the Inbound Correspondence definition
    Then I verify the service reject the request for type "maersk Case + Consumer"
#   updating definition name to valid for maersk Case + Consumer
    When I have a request to update Inbound Correspondence definition for type "maersk Case + Consumer" and "<projectName>" with the following values
      | projectId      | valid |
      | projectName    | valid |
      | definitionName | valid |
    And I send a request to update the Inbound Correspondence definition
    Examples:
      | projectName |
      | NJ-SBE      |
      | CoverVA     |
      | IN-EB       |
      | BLCRM       |
