@CP-22182
Feature: Display History of Links Edits Made to Inbound Correspondence

  @CP-22182-00 @ui-ecms2 @Keerthi
  Scenario: Create an Inbound Document with a valid CaseId,ConsumerID,OB NotificationID and TaskID links
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | RECEIVED          |
      | CASEID         | Random            |
      | CONSUMERID     | Random            |
      | CHANNEL        | Mail              |
    When I send the request to create the Inbound Correspondence Save Event


  @CP-22182-01 @ui-ecms2 @Keerthi
  Scenario: Validate Edit History Tab columns names in CP
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on a CID of "first available" Inbound Search Results
    And I click on the HISTORY tab of the Inbound Correspondence Details Page
    And I validate Edit History tab columns names
      | EDITED ON      |
      | EDITED BY      |
      | FIELD LABEL    |
      | PREVIOUS VALUE |
      | UPDATED VALUE  |

  @CP-22182-02 @ui-ecms2 @Keerthi
  Scenario: Validate Viewing Link History values from api call
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate and run Get Inbound Correspondence inactive Links Call for "fromRequest"
    And I store the inactive links api call details to 2D array
      | EDITED ON      |
      | EDITED BY      |
      | FIELD LABEL    |
      | PREVIOUS VALUE |
      | UPDATED VALUE  |

  @CP-22182-03 @ui-ecms2 @Keerthi
  Scenario: Validate Viewing Link History column values from CP
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | 33127 |
    And I click on a CID of "first available" Inbound Search Results
    And I click on the HISTORY tab of the Inbound Correspondence Details Page
    And I validate Link History column values
      | EDITED ON      |
      | EDITED BY      |
      | FIELD LABEL    |
      | PREVIOUS VALUE |
      | UPDATED VALUE  |
    And I compare the Inactive Api values to CP Link History column values


  @CP-22182-04 @ui-ecms2 @Keerthi
  Scenario: Unlinking Link History values from cp
    Given I will get the Authentication token for "<projectName>" in "CRM"
   And I Unlink the "previouslyCreated" Task from the Inbound Correspondence
    And I Unlink the "previouslyCreated" Task from the Inbound Correspondence
    And I Unlink the "previouslyCreated" Task from the Inbound Correspondence


  @CP-22182-05 @ui-ecms2 @Keerthi
  Scenario: Validate Viewing UnLink History values from api call
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate and run Get Inbound Correspondence inactive Links Call for "fromRequest"
    And I store the inactive unlinks api call details to 2D array
      | EDITED ON      |
      | EDITED BY      |
      | FIELD LABEL    |
      | PREVIOUS VALUE |
      | UPDATED VALUE  |

  @CP-22182-06 @ui-ecms2 @Keerthi
  Scenario: Validate Viewing UnLink History column values from CP
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on a CID of "first available" Inbound Search Results
    And I click on the HISTORY tab of the Inbound Correspondence Details Page
    And I validate Link History column values
      | EDITED ON      |
      | EDITED BY      |
      | FIELD LABEL    |
      | PREVIOUS VALUE |
      | UPDATED VALUE  |
    And I compare the Inactive Api values to CP Link History column values

  @CP-22182-07 @ui-ecms2 @Keerthi
  Scenario: Validate reverse chronological order of Edited On column
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on a CID of "first available" Inbound Search Results
    And I click on the HISTORY tab of the Inbound Correspondence Details Page
   And I validate reverse chronological order of Edited On column

