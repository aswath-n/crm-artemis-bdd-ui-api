@CP-30123
Feature: Enable Edit of Received Date Field in Inbound Correspondence view page for all tenants

# BLCRM tenant
  @CP-30123 @CP-30123-1.0 @ui-ecms2 @Keerthi
  Scenario: Validate Enabling Edit of Received Date in BLCRM
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
    And I validate Received Date is editable


  @CP-30123 @CP-30123-2.1 @CP-30579-1  @ui-ecms2 @Keerthi
  Scenario: Validate Editing Received Date via Calendar Widget and changes made will be persisted to OnBase in BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "pastdate" via "calendarwidget"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "InboundDocument"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "currentdate" via "calendarwidget"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "InboundDocument"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "futuredate" via "calendarwidget"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "InboundDocument"

  @CP-30123 @CP-30123-2.1 @CP-30579-2 @ui-ecms2 @Keerthi
  Scenario: Validate Editing Received Date via  Manual Typing and changes made will be persisted to OnBase in BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "pastdate" via "manualtyping"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "InboundDocument"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "currentdate" via "manualtyping"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "InboundDocument"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "futuredate" via "manualtyping"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "InboundDocument"

  @CP-30123 @CP-30123-2.2 @ui-ecms2 @Keerthi
  Scenario: Validate INBOUND_CORRESPONDENCE_UPDATE_EVENT for Received date field in BLCRM
    Then I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "currentdate" via "calendarwidget"
    And I click on Edit Inbound Correspondence details Page Save button
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I retrieve the "INBOUND_CORRESPONDENCE_UPDATE_EVENT" event that is produced by trace id
    Then I should see the payload for the "INBOUND_CORRESPONDENCE_UPDATE_EVENT" is as expected
    And I should see that the event mapping for "INBOUND_CORRESPONDENCE_UPDATE_EVENT" exists
    And I should see that the "INBOUND_CORRESPONDENCE_UPDATE_EVENT" record is produced to DPBI


  @CP-30123 @CP-30123-3.0 @ui-ecms2 @Keerthi
  Scenario: Validate Saving without selecting New Received Date in BLCRM
    Then I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I validate "save" without selecting New Received Date


  @CP-30123 @CP-30123-3.1 @ui-ecms2 @Keerthi
  Scenario: Validate Selecting Cancel without Selecting New Received Date in BLCRM
    Then I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I validate "cancel" without selecting New Received Date


# coverVA tenant
  @CP-30123 @CP-30123-1.0 @ui-ecms-coverva @Keerthi
  Scenario: Validate Enabling Edit of Received Date in coverVA
    Given I will get the Authentication token for "CoverVA" in "CRM"
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
    And I validate Received Date is editable

  @CP-30123 @CP-30579-1 @CP-30123-2.1 @ui-ecms-coverva @Keerthi
  Scenario: Validate Editing Received Date via Calendar Widget and changes made will be persisted to OnBase in coverVA
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Then I logged into CRM and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "pastdate" via "calendarwidget"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "InboundDocument"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "currentdate" via "calendarwidget"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "InboundDocument"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "futuredate" via "calendarwidget"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "InboundDocument"


  @CP-30123 @CP-30579-2 @CP-30123-2.1 @ui-ecms-coverva @Keerthi
  Scenario: Validate Editing Received Date via  Manual Typing and changes made will be persisted to OnBase in coverVA
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Then I logged into CRM and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "pastdate" via "manualtyping"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "InboundDocument"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "currentdate" via "manualtyping"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "InboundDocument"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "futuredate" via "manualtyping"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "InboundDocument"

  @CP-30123 @CP-30123-2.2 @ui-ecms-coverva @Keerthi
  Scenario: Validate INBOUND_CORRESPONDENCE_UPDATE_EVENT for Received date field in coverVA
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Then I logged into CRM and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "currentdate" via "calendarwidget"
    And I click on Edit Inbound Correspondence details Page Save button
    And I retrieve the "INBOUND_CORRESPONDENCE_UPDATE_EVENT" event that is produced by trace id
    Then I should see the payload for the "INBOUND_CORRESPONDENCE_UPDATE_EVENT" is as expected
    And I should see that the event mapping for "INBOUND_CORRESPONDENCE_UPDATE_EVENT" exists
    And I should see that the "INBOUND_CORRESPONDENCE_UPDATE_EVENT" record is produced to DPBI

  @CP-30123 @CP-30123-3.0 @ui-ecms-coverva @Keerthi
  Scenario: Validate Saving without selecting New Received Date in coverVA
    Then I logged into CRM and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I validate "save" without selecting New Received Date


  @CP-30123 @CP-30123-3.1 @ui-ecms-coverva @Keerthi
  Scenario: Validate Selecting Cancel without Selecting New Received Date in coverVA
    Then I logged into CRM and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I validate "cancel" without selecting New Received Date

    # NJSBE tenant
  @CP-30123 @CP-30123-1.0 @ui-ecms-nj @Keerthi
  Scenario: Validate Enabling Edit of Received Date in NJ-SBE
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
    And I validate Received Date is editable

  @CP-30123 @CP-30579-1 @CP-30123-2.1 @ui-ecms-nj @Keerthi
  Scenario: Validate Editing Received Date via Calendar Widget and changes made will be persisted to OnBase in NJ-SBE
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    Then I logged into CRM and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "pastdate" via "calendarwidget"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "InboundDocument"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "currentdate" via "calendarwidget"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "InboundDocument"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "futuredate" via "calendarwidget"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "InboundDocument"


  @CP-30123 @CP-30579-2 @CP-30123-2.1 @ui-ecms-nj @Keerthi
  Scenario: Validate Editing Received Date via  Manual Typing and changes made will be persisted to OnBase in NJ-SBE
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    Then I logged into CRM and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "pastdate" via "manualtyping"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "InboundDocument"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "currentdate" via "manualtyping"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "InboundDocument"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "futuredate" via "manualtyping"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "InboundDocument"

  @CP-30123 @CP-30123-2.2 @ui-ecms-nj @Keerthi
  Scenario: Validate INBOUND_CORRESPONDENCE_UPDATE_EVENT for Received date field in NJ-SBE
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    Then I logged into CRM and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "currentdate" via "calendarwidget"
    And I click on Edit Inbound Correspondence details Page Save button
    And I retrieve the "INBOUND_CORRESPONDENCE_UPDATE_EVENT" event that is produced by trace id
    Then I should see the payload for the "INBOUND_CORRESPONDENCE_UPDATE_EVENT" is as expected
    And I should see that the event mapping for "INBOUND_CORRESPONDENCE_UPDATE_EVENT" exists
    And I should see that the "INBOUND_CORRESPONDENCE_UPDATE_EVENT" record is produced to DPBI

  @CP-30123 @CP-30123-3.0 @ui-ecms-nj @Keerthi
  Scenario: Validate Saving without selecting New Received Date in NJ-SBE
    Then I logged into CRM and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I validate "save" without selecting New Received Date


  @CP-30123 @CP-30123-3.1 @ui-ecms-nj @Keerthi
  Scenario: Validate Selecting Cancel without Selecting New Received Date in NJ-SBE
    Then I logged into CRM and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I validate "cancel" without selecting New Received Date

     # INEB tenant
  @CP-30123 @CP-30123-1.0 @ui-ecms-ineb @Keerthi
  Scenario: Validate Enabling Edit of Received Date in IN-EB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | INEB Authorized Representative |
      | Channel      | MAIL                           |
      | Status       | COMPLETE                       |
      | FromName     | AUTOMATION                     |
      | ProcessType  | INBOUND CORRESPONDENCE         |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    Then I logged into CRM and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    And I validate Received Date is editable

  @CP-30123 @CP-30579-1 @CP-30123-2.1@ui-ecms-ineb @Keerthi
  Scenario: Validate Editing Received Date via Calendar Widget and changes made will be persisted to OnBase in IN-EB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then I logged into CRM and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "pastdate" via "calendarwidget"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "InboundDocument"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "currentdate" via "calendarwidget"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "InboundDocument"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "futuredate" via "calendarwidget"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "InboundDocument"


  @CP-30123 @CP-30579-2 @CP-30123-2.1 @ui-ecms-ineb @Keerthi
  Scenario: Validate Editing Received Date via  Manual Typing and changes made will be persisted to OnBase in IN-EB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then I logged into CRM and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "pastdate" via "manualtyping"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "InboundDocument"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "currentdate" via "manualtyping"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "InboundDocument"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "futuredate" via "manualtyping"
    And I click on Edit Inbound Correspondence details Page Save button
    And I validate Received date changes persisted to OnBase for "InboundDocument"

  @CP-30123 @CP-30123-2.2 @ui-ecms-ineb @Keerthi
  Scenario: Validate INBOUND_CORRESPONDENCE_UPDATE_EVENT for Received date field in IN-EB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then I logged into CRM and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "currentdate" via "calendarwidget"
    And I click on Edit Inbound Correspondence details Page Save button
    And I retrieve the "INBOUND_CORRESPONDENCE_UPDATE_EVENT" event that is produced by trace id
    Then I should see the payload for the "INBOUND_CORRESPONDENCE_UPDATE_EVENT" is as expected
    And I should see that the event mapping for "INBOUND_CORRESPONDENCE_UPDATE_EVENT" exists
    And I should see that the "INBOUND_CORRESPONDENCE_UPDATE_EVENT" record is produced to DPBI

  @CP-30123 @CP-30123-3.0 @ui-ecms-ineb @Keerthi
  Scenario: Validate Saving without selecting New Received Date in IN-EB
    Then I logged into CRM and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I validate "save" without selecting New Received Date


  @CP-30123 @CP-30123-3.1 @ui-ecms-ineb @Keerthi
  Scenario: Validate Selecting Cancel without Selecting New Received Date in IN-EB
    Then I logged into CRM and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I validate "cancel" without selecting New Received Date

  @CP-30604 @CP-30604-05 @crm-regression @ui-cc-in @chopa
  Scenario: Suppress Add Button on External ID in Links Component C&C Search
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I select "Case/Consumer" option from Create Links(s) dropdown
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I will not see the "Add" button for the External ID component
