@viewinb
Feature: View Inbound Correspondence Details part 2


  @CP-18482 @CP-18482-1 @ui-ecms2 @James
  Scenario: Verify Document Icon is Visible when the Inbound Document is part of a set
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | previouslyCreated       |
      | ConsumerID   | previouslyCreated       |
      | FormVersion  | 3                       |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | SetId        | Random                  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | previouslyCreated       |
      | ConsumerID   | previouslyCreated       |
      | FormVersion  | 3                       |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | SetId        | PreviouslyCreated       |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    Then I should see the document icon on the Inbound Document Details Page

  @CP-18482 @CP-18482-2 @ui-ecms2 @James
  Scenario: Verify Document Set Details Tab is available
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | previouslyCreated       |
      | ConsumerID   | previouslyCreated       |
      | FormVersion  | 3                       |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | SetId        | Random                  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    Then I should see the Document Set Details on the Inbound Document Details Page

  @CP-18482 @CP-18482-3 @ui-ecms2 @James
  Scenario: Verify Document Set Details Tab Labels
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | ConsumerID   | previouslyCreated       |
      | FormVersion  | 3                       |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | SetId        | Random                  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | ConsumerID   | previouslyCreated       |
      | FormVersion  | 3                       |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | SetId        | PreviouslyCreated       |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the Document Set Details Tab on the Inbound Document Details Page
    Then I should verify all the labels on the Document Set Details tab on the Inbound Document Details Page

  @CP-18482 @CP-18482-4 @ui-ecms2 @James
  Scenario: Verify Document Set Details says No Records Found
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | previouslyCreated       |
      | ConsumerID   | previouslyCreated       |
      | FormVersion  | 3                       |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the Document Set Details Tab on the Inbound Document Details Page
    Then I should verify the Document Set Details tab message says "No Records Found" on the Inbound Document Details Page

  @CP-18482 @CP-18482-5 @ui-ecms2 @James
  Scenario: Verify Document Set Details Consumer Names are dipslayed
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | previouslyCreated       |
      | ConsumerID   | previouslyCreated       |
      | FormVersion  | 3                       |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | SetId        | Random                  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | previouslyCreated       |
      | ConsumerID   | previouslyCreated       |
      | FormVersion  | 3                       |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | SetId        | PreviouslyCreated       |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the Document Set Details Tab on the Inbound Document Details Page
    Then I should see multiple Consumers Ellipsis for Inbound Documents that have more than one Consumer Id

  @CP-27596 @CP-27596-1 @ui-ecms2 @James
  Scenario: Verify changed by should display the Connection Point user name of the user who updated the document
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers |
      | FormVersion  | 3                        |
      | Language     | SPANISH                  |
      | Channel      | MAIL                     |
      | Status       | COMPLETE                 |
      | FromName     | AUTOMATION               |
      | ProcessType  | INBOUND CORRESPONDENCE   |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM
    And I click on the main navigation menu
    And I minimize Genesys popup if populates
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the Rescan button in the Inbound Correspondence Details Page
    And I click on the HISTORY tab of the Inbound Correspondence Details Page
    Then I should see the view changed by should display the Connection Point user name of the user who updated the document

  @CP-27596 @CP-27596-2 @ui-ecms2 @James
  Scenario: Verify changed by should display the raw value stored of the user not in Connection Point
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers |
      | FormVersion  | 3                        |
      | Language     | SPANISH                  |
      | Channel      | MAIL                     |
      | Status       | COMPLETE                 |
      | FromName     | AUTOMATION               |
      | ProcessType  | INBOUND CORRESPONDENCE   |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | status | RESCAN REQUESTED |
    And I logged into CRM
    And I click on the main navigation menu
    And I minimize Genesys popup if populates
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the HISTORY tab of the Inbound Correspondence Details Page
    Then I should see the view changed by should display the raw value stored of the user not in Connection Point

  @CP-15391 @CP-15391-1 @ui-ecms2 @James
  Scenario: Verify updated by will display user name of CP user when updated
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers |
      | FormVersion  | 3                        |
      | Language     | SPANISH                  |
      | Channel      | MAIL                     |
      | Status       | INDEXING                 |
      | FromName     | AUTOMATION               |
      | ProcessType  | INBOUND CORRESPONDENCE   |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | status | COMPLETE |
    And I logged into CRM
    And I click on the main navigation menu
    And I minimize Genesys popup if populates
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the Rescan button in the Inbound Correspondence Details Page
    Then I should see the Updated By value on the Inbound Correspondence Details Page shows the CP username

  @CP-15391 @CP-15391-2 @ui-ecms2 @James
  Scenario: Verify updated by will show the raw value when user is not configured CP
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers |
      | FormVersion  | 3                        |
      | Language     | SPANISH                  |
      | Channel      | MAIL                     |
      | Status       | INDEXING                 |
      | FromName     | AUTOMATION               |
      | ProcessType  | INBOUND CORRESPONDENCE   |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | status | COMPLETE |
    And I logged into CRM
    And I click on the main navigation menu
    And I minimize Genesys popup if populates
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | status | COMPLETE |
    Then I should see the Updated By value on the Inbound Correspondence Details Page shows the Raw Value that is stored


  @CP-21506 @CP-21506-01  @ui-ecms2 @burak
  Scenario: Update type of INB correspondence from UI and verify the History Tab
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | FormVersion  | 3                       |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | COMPLETE                |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "type" field with value "maersk Case" via "null"
    And I click on Edit Inbound Correspondence details Page Save button
    And I capture date and time in required format
    And I click on the HISTORY tab of the Inbound Correspondence Details Page
    And I validate the Edited On time is as per project timezone
    And I initiate and run Get Inbound Correspondence inactive Links Call for "InboundDocument"
    And I capture the task ID from inactive Links
    And I validate the History Tab values for 1 row
      | CURRENT TIME |
      | ECMS Service |
      | Task         |
      | -- --        |
      | TASK ID      |
    And I validate the History Tab values for 2 row
      | CURRENT TIME            |
      | Service AccountOne      |
      | Type                    |
      | maersk Case + Consumer |
      | maersk Case            |

  @CP-21506 @CP-21506-02 @ui-ecms2 @burak
  Scenario: Update type of INB correspondence from API and verify the History Tab
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | FormVersion  | 3                       |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | COMPLETE                |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | documentType | maersk Case |
    And I capture date and time in required format
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the HISTORY tab of the Inbound Correspondence Details Page
    And I validate the Edited On time is as per project timezone
    And I initiate and run Get Inbound Correspondence inactive Links Call for "InboundDocument"
    And I capture the task ID from inactive Links
    And I validate the History Tab values for 1 row
      | CURRENT TIME |
      | ECMS Service |
      | Task         |
      | -- --        |
      | TASK ID      |
    And I validate the History Tab values for 2 row
      | CURRENT TIME            |
      | AUTOMATION              |
      | Type                    |
      | maersk Case + Consumer |
      | maersk Case            |

  @CP-21506 @CP-21506-03  @ui-ecms2  @burak
  Scenario: Update type of INB correspondence from UI , Unlink the task and verify the History Tab
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | FormVersion  | 3                       |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | COMPLETE                |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "type" field with value "maersk Case" via "null"
    And I click on Edit Inbound Correspondence details Page Save button
    And I click the Unlink button on Correspondence
    And I verify Successfully Unlink on Correspondence
    And I click on the back arrow button
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the HISTORY tab of the Inbound Correspondence Details Page
    And I capture date and time in required format
    And I initiate and run Get Inbound Correspondence inactive Links Call for "InboundDocument"
    And I capture the task ID from inactive Links
    And I validate the Edited On time is as per project timezone
    And I validate the History Tab values for 1 row
      | CURRENT TIME       |
      | Service AccountOne |
      | Task               |
      | TASK ID            |
      | -- --              |

  @CP-21506 @CP-21506-04 @ui-ecms2 @burak
  Scenario: Update status of INB correspondence from UI and verify the History Tab
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | FormVersion  | 3                       |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | COMPLETE                |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I should see that the Request Rescan button is available in the Inbound Correspondence Details Page
    And I click on the Rescan button in the Inbound Correspondence Details Page
    And I click on Edit Inbound Correspondence details Page Save button
    And I capture date and time in required format
    And I click on the HISTORY tab of the Inbound Correspondence Details Page
    And I validate the Edited On time is as per project timezone
    And I validate the History Tab values for 1 row
      | CURRENT TIME       |
      | Service AccountOne |
      | Status             |
      | COMPLETE           |
      | RESCAN REQUESTED   |

  @CP-21506 @CP-21506-05 @ui-ecms2 @burak
  Scenario: Update status of INB correspondence from API and verify the History Tab
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | fileType     | pdf                     |
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | status | RESCAN REQUESTED |
    And I capture date and time in required format
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the HISTORY tab of the Inbound Correspondence Details Page
    And I validate the Edited On time is as per project timezone
    And I validate the History Tab values for 1 row
      | CURRENT TIME     |
      | AUTOMATION       |
      | Status           |
      | -- --            |
      | RESCAN REQUESTED |

  @CP-28556 @CP-28556-1 @ui-ecms2 @James
  Scenario: Verify that status history tab is no longer visible
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | PreviouslyCreated       |
      | Channel      | MAIL                    |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | ScannedOn    | 11/08/2021 12:00:00 AM  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the case Id that I previously created
    And I navigate to the case and contact details tab
    And I select the Inbound Correspondence that was "previouslyCreated" on the case and contact details tab
    Then I should see the status history tab is no longer visible

  @CP-28556 @CP-28556-2 @ui-ecms2 @James
  Scenario: Verify that new history tab is visible in Inbound Correspondence Details Screen
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    Then I should see the the following tabs in the Inbound Correspondence Details Screen
      | HISTORY |

  @CP-28556 @CP-28556-3 @ui-ecms2 @James
  Scenario: Verify that new history tab is visible in Inbound Correspondence Details Screen
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the HISTORY tab of the Inbound Correspondence Details Page
    Then I should see the the following labels on the "HISTORY" tab on the Inbound Correspondence Details Screen
      | EDITED ON      |
      | EDITED BY      |
      | FIELD LABEL    |
      | PREVIOUS VALUE |
      | UPDATED VALUE  |