@CP-30123
Feature: Enable Edit of Received Date Field in Inbound Correspondence view page for all tenants Part 2


  @CP-30123 @CP-30124 @ui-ecms2 @Keerthi
  Scenario: Validate Received Date Changes in the new History Tab in BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | fileType     | pdf                     |
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    Then I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "pastdate" via "manualtyping"
    And I click on Edit Inbound Correspondence details Page Save button
    And I click on the HISTORY tab of the Inbound Correspondence Details Page
    And I initiate and run Get Inbound Correspondence inactive Links Call for "InboundDocument"
    And I validate Edit History tab columns names
      | EDITED ON      |
      | EDITED BY      |
      | FIELD LABEL    |
      | PREVIOUS VALUE |
      | UPDATED VALUE  |
    And I store the inactive links api call details to 2D array
      | EDITED ON      |
      | EDITED BY      |
      | FIELD LABEL    |
      | PREVIOUS VALUE |
      | UPDATED VALUE  |
    And I validate Link History column values
      | EDITED ON      |
      | EDITED BY      |
      | FIELD LABEL    |
      | PREVIOUS VALUE |
      | UPDATED VALUE  |
    And I compare the Inactive Api values to CP Link History column values


  @CP-30123 @CP-30124 @ui-ecms-coverva @Keerthi
  Scenario: Validate Received Date Changes in the new History Tab in coverVA
    Given I will get the Authentication token for "COVER-VA" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | VACV Newborn Form      |
      | Channel      | WEB PORTAL             |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    Then I logged into CRM and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "pastdate" via "manualtyping"
    And I click on Edit Inbound Correspondence details Page Save button
    And I click on the HISTORY tab of the Inbound Correspondence Details Page
    And I initiate and run Get Inbound Correspondence inactive Links Call for "InboundDocument"
    And I validate Edit History tab columns names
      | EDITED ON      |
      | EDITED BY      |
      | FIELD LABEL    |
      | PREVIOUS VALUE |
      | UPDATED VALUE  |
    And I store the inactive links api call details to 2D array
      | EDITED ON      |
      | EDITED BY      |
      | FIELD LABEL    |
      | PREVIOUS VALUE |
      | UPDATED VALUE  |
    And I validate Link History column values
      | EDITED ON      |
      | EDITED BY      |
      | FIELD LABEL    |
      | PREVIOUS VALUE |
      | UPDATED VALUE  |
    And I compare the Inactive Api values to CP Link History column values

  @CP-30123 @CP-30124 @ui-ecms-nj @Keerthi
  Scenario: Validate Received Date Changes in the new History Tab in NJ-SBE
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | NJ SBE Appeal Form     |
      | Channel      | MAIL                   |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    Then I logged into CRM and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "pastdate" via "manualtyping"
    And I click on Edit Inbound Correspondence details Page Save button
    And I click on the HISTORY tab of the Inbound Correspondence Details Page
    And I initiate and run Get Inbound Correspondence inactive Links Call for "InboundDocument"
    And I validate Edit History tab columns names
      | EDITED ON      |
      | EDITED BY      |
      | FIELD LABEL    |
      | PREVIOUS VALUE |
      | UPDATED VALUE  |
    And I store the inactive links api call details to 2D array
      | EDITED ON      |
      | EDITED BY      |
      | FIELD LABEL    |
      | PREVIOUS VALUE |
      | UPDATED VALUE  |
    And I validate Link History column values
      | EDITED ON      |
      | EDITED BY      |
      | FIELD LABEL    |
      | PREVIOUS VALUE |
      | UPDATED VALUE  |
    And I compare the Inactive Api values to CP Link History column values

  @CP-30123 @CP-30124 @ui-ecms-ineb @Keerthi
  Scenario: Validate Received Date Changes in the new History Tab in IN-EB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | fileType     | pdf                    |
      | documentType | INEB Just Cause Form   |
      | Language     | SPANISH                |
      | Channel      | EMAIL                  |
      | ProcessType  | INBOUND CORRESPONDENCE |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    Then I logged into CRM and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "pastdate" via "manualtyping"
    And I click on Edit Inbound Correspondence details Page Save button
    And I click on the HISTORY tab of the Inbound Correspondence Details Page
    And I initiate and run Get Inbound Correspondence inactive Links Call for "InboundDocument"
    And I validate Edit History tab columns names
      | EDITED ON      |
      | EDITED BY      |
      | FIELD LABEL    |
      | PREVIOUS VALUE |
      | UPDATED VALUE  |
    And I store the inactive links api call details to 2D array
      | EDITED ON      |
      | EDITED BY      |
      | FIELD LABEL    |
      | PREVIOUS VALUE |
      | UPDATED VALUE  |
    And I validate Link History column values
      | EDITED ON      |
      | EDITED BY      |
      | FIELD LABEL    |
      | PREVIOUS VALUE |
      | UPDATED VALUE  |
    And I compare the Inactive Api values to CP Link History column values

############# CP-32340 ##############################

  @CP-32340 @CP-32340-1.1 @ui-ecms2  @Keerthi
  Scenario: Recalculating Created status task Due Dates when a Correspondence's Received Date is updated
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 3     |
      | requiredDataElements | null  |
      | taskTypeID           | 12797 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType | RECEIVED |
      | CASEID      | Random   |
      | CONSUMERID  | Random   |
      | CHANNEL     | Mail     |
    When I send the request to create the Inbound Correspondence Save Event
    Then I validate get task request API for "" status
    Then I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "pastdate" via "calendarwidget"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "fromRequest"
    Then I validate get task request API for "Created" status


  @CP-32340 @CP-32340-1.2 @ui-ecms2  @Keerthi
  Scenario: Recalculating OnHold status task Due Dates when a Correspondence's Received Date is updated
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 3     |
      | requiredDataElements | null  |
      | taskTypeID           | 12797 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType | RECEIVED |
      | CASEID      | Random   |
      | CONSUMERID  | Random   |
      | CHANNEL     | Mail     |
    When I send the request to create the Inbound Correspondence Save Event
    Then I validate get task request API for "" status
    Then I logged into CRM
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I navigate to "previouslycreatedtask" created task details page
    And I click on edit button on view task page for In-progress status
    And I will update the following information in edit task page
      | status          | OnHold              |
      | assignee        | Service AccountOne  |
      | reasonForOnHold | Application On Hold |
    And I click on save button on task edit page
    When I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "pastdate" via "calendarwidget"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "fromRequest"
    Then I validate get task request API for "OnHold" status


  @CP-32340 @CP-32340-1.3 @ui-ecms2  @Keerthi
  Scenario: Recalculating Escalated status task Due Dates when a Correspondence's Received Date is updated
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12797 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType | RECEIVED |
      | CASEID      | Random   |
      | CONSUMERID  | Random   |
      | CHANNEL     | Mail     |
    When I send the request to create the Inbound Correspondence Save Event
    Then I validate get task request API for "" status
    Then I logged into CRM
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I navigate to "previouslycreatedtask" created task details page
    And I click on edit button on view task page for In-progress status
    And I will update the following information in edit task page
      | status | Escalated |
    And I click on save button on task edit page
    When I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "pastdate" via "calendarwidget"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "fromRequest"
    Then I validate get task request API for "Escalated" status


  @CP-32340 @CP-32340-1.4 @ui-ecms1  @Keerthi
  Scenario: Recalculating Cancelled status task Due Dates when a Correspondence's Received Date is updated
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12797 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType | RECEIVED |
      | CASEID      | Random   |
      | CONSUMERID  | Random   |
      | CHANNEL     | Mail     |
    When I send the request to create the Inbound Correspondence Save Event
    Then I validate get task request API for "" status
    Then I logged into CRM
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I navigate to "previouslycreatedtask" created task details page
    And I click on edit button on view task page for In-progress status
    And I will update the following information in edit task page
      | status          | Cancelled              |
      | reasonForCancel | Duplicate Task |
    And I click on save button on task edit page
    When I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "pastdate" via "calendarwidget"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "fromRequest"
    Then I validate get task request API for "Cancelled" status
