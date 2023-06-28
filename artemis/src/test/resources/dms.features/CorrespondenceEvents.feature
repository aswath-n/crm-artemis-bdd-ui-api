Feature: Correspondence events type

  @API-CP-16995 @API-CP-16995-01 @API-CP-12276 @API-CP-12276-01 @API-CP-37839 @API-CP-37839-01 @API-ECMS @RuslanL
  Scenario: Verify OUTBOUND_CORRESPONDENCE_SAVE_EVENT, NOTIFICATION_SAVE_EVENT, CORRESPONDENCE_RECIPIENT_SAVE_EVENT and LETTER_DATA_SAVE_EVENT
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | emailOnly         |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients                      | random            |
      | channel                         | Email             |
    When I send the request "previouslyCreated" to create an Outbound Correspondence and save traceId
    And I retrieve event by trace id and verify
      | OUTBOUND_CORRESPONDENCE_SAVE_EVENT  |
      | NOTIFICATION_SAVE_EVENT             |
      | CORRESPONDENCE_RECIPIENT_SAVE_EVENT |
      | LETTER_DATA_SAVE_EVENT              |

  @API-CP-16995 @API-CP-16995-02 @API-ECMS @API-CP-37839 @API-CP-37839-02 @RuslanL
  Scenario: Verify OUTBOUND_CORRESPONDENCE_UPDATE_EVENT and NOTIFICATION_UPDATE_EVENT
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                 |
      | ConsumerId   | Random                 |
      | externallink | externalRefType,Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I update Outbound Notification Id "previouslyCreated" to status "Sent"
    And I retrieve event by trace id and verify
      | OUTBOUND_CORRESPONDENCE_UPDATE_EVENT |
      | NOTIFICATION_UPDATE_EVENT            |

  @API-CP-12052 @API-CP-12052-01 @API-ECMS @RuslanL
  Scenario: Verify INBOUND_CORRESPONDENCE_SAVE_EVENT
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12544 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with no Case ID in addition to no Consumer ID
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE        | CREATEDDOCUMENT |
      | ProcessType | RECEIVED        |
      | CHANNEL     | Mail            |
    When I send the request to create the Inbound Correspondence Save Event
    And I search and verify the latest "INBOUND_CORRESPONDENCE_SAVE_EVENT"

  @API-CP-12052 @API-CP-12052-02 @API-CP-12276 @API-CP-12276-02 @API-ECMS @RuslanL
  Scenario: Verify INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | CaseID       | PreviouslyCreated       |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    And I click on the Inbound Document CID link "firstAvailable"
    When I add a new note to the Inbound Correspondence
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I search and verify the latest "INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT"

  @API-CP-12052 @API-CP-12052-03 @API-ECMS @RuslanL
  Scenario: Verify INBOUND_CORRESPONDENCE_UPDATE_EVENT
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Returned Mail  |
      | Language     | SPANISH                |
      | Channel      | EMAIL                  |
      | Status       | COMPLETE               |
      | Origin       | WEB PORTAL             |
      | FromName     | AUTOMATION             |
      | FormVersion  | 3                      |
      | ProcessType  | INBOUND CORRESPONDENCE |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | status | RESCAN REQUESTED |
    And I search and verify the latest "INBOUND_CORRESPONDENCE_UPDATE_EVENT"

  @CP-12778 @API-ECMS @Prithika
  Scenario: Stop sending correspondence configuration events
    Given I have random valid data for an Outbound Correspondence Definition
    And I will get the Authentication token for "<projectName>" in "CRM"
    When I send the Outbound Correspondence Definition to the server for project: "current" with random valid data and Body Data Structure
    When I update the Outbound Correspondence Definition and add body element
      | First Name |
    Then I retrieve the previously created Outbound Correspondence Definition by correspondence ID
    And I add channels to created Outbound Correspondence Definition
      | Mail |
    And I add channels to created Outbound Correspondence Definition
      | Email |
    And I verify events are not created for the following types
      | OUTBOUND_CORRESPONDENCE_SETTINGS_SAVE_EVENT             |
      | OUTBOUND_CORRESPONDENCE_SETTINGS_UPDATE_EVENT           |
      | OUTBOUND_CORRESPONDENCE_DEFINITION_SAVE_EVENT           |
      | OUTBOUND_CORRESPONDENCE_DEFINITION_UPDATE_EVENT         |
      | OUTBOUND_CORRESPONDENCE_DEFINITION_CHANNEL_SAVE_EVENT   |
      | OUTBOUND_CORRESPONDENCE_DEFINITION_CHANNEL_UPDATE_EVENT |
      | OUTBOUND_CORRESPONDENCE_TEMPLATE_SAVE_EVENT             |
      | OUTBOUND_CORRESPONDENCE_TEMPLATE_UPDATE_EVENT           |

    ############################################   CP-29794    #################################################

  @CP-29794 @API-CP-29794-1.0 @API-ECMS @keerthi
  Scenario: Verify INBOUND_CORRESPONDENCE_SAVE_EVENT for CREATEDDOCUMENT Type
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | null                    |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I save the Digital Correspondence Id created to Inbound Correspondence Id
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE         | CREATEDDOCUMENT         |
      | PROCESSTYPE  | INBOUND CORRESPONDENCE  |
      | CHANNEL      | EMAIL                   |
      | documentType | maersk Case + Consumer |
      | STATUS       | COMPLETE                |
    When I send the String request to create the Inbound Correspondence Save Event
    And I retrieve response for "INBOUND_CORRESPONDENCE_SAVE_EVENT" and verify for empty strings

  @CP-29794 @API-CP-29794-1.1 @API-ECMS @keerthi
  Scenario: Verify CREATEDDOCUMENT Type does not invoke task/service request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12544 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE        | CREATEDDOCUMENT |
      | ProcessType | RECEIVED        |
      | CHANNEL     | Mail            |
    When I send the request to create the Inbound Correspondence Save Event
    And I should validate Task has been "notcreated"

  @CP-29794 @API-CP-29794-1.2 @ui-ecms1 @keerthi
  Scenario: Verify CREATEDDOCUMENT Type with active case/consumer links
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE         | CREATEDDOCUMENT   |
      | ProcessType  | RECEIVED          |
      | CaseID       | PreviouslyCreated |
      | ConsumerID   | PreviouslyCreated |
      | CHANNEL      | Mail              |
      | documentDate | Today             |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see there is a link between Case Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated"
    Then I should see there is a link between Consumer Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated"
    Then I should see there is a link between Inbound Correspondence Document "previouslyCreated" and Case Id "previouslyCreated"
    Then I should see there is a link between Inbound Correspondence Document "previouslyCreated" and Consumer Id "previouslyCreated"
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on a CID of "first available" Inbound Search Results
    Then I should see the Inbound Document "fromRequest" is linked to caseId "PreviouslyCreated"
    Then I should see the Inbound Document "fromRequest" is linked to ConsumerId "PreviouslyCreated"

  @CP-29794 @API-CP-29794-1.3 @API-ECMS @keerthi
  Scenario: Verify CREATEDDOCUMENT Type does not execute re-scan logic
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12544 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE        | CREATEDDOCUMENT |
      | ProcessType | RECEIVED        |
      | Channel     | Mail            |
    When I send the request to create the Inbound Correspondence Save Event
    And I receive a Rescanned Inbound Document with RescanOfDocumentId "GETNEWRESCANINBOUNDDOCID_CREATEDDOCUMENT"
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    Then I should see the the following labels ARE HIDDEN WHEN EMPTY on the "UPPER SECTION OF INB DETAILS" tab on the Inbound Correspondence Details Screen
      | RESCANNED AS CID |


  @CP-29794 @API-CP-29794-1.4 @ui-ecms1 @keerthi
  Scenario: Verify CREATEDDOCUMENT Type with active case/consumer links for maersk Case + Consumers Requiring Metadata IB Type
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumers Requiring Metadata"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumers Requiring Metadata"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE         | CREATEDDOCUMENT   |
      | ProcessType  | RECEIVED          |
      | CaseID       | PreviouslyCreated |
      | CONSUMERIDS  | PreviouslyCreated |
      | CHANNEL      | Mail              |
      | documentDate | Today             |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see there is a link between Case Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated"
    Then I should see there is a link between Consumer Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated"
    Then I should see there is a link between Inbound Correspondence Document "previouslyCreated" and Case Id "previouslyCreated"
    Then I should see there is a link between Inbound Correspondence Document "previouslyCreated" and Consumer Id "previouslyCreated"
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on a CID of "first available" Inbound Search Results
    Then I should see the Inbound Document "fromRequest" is linked to caseId "PreviouslyCreated"
    Then I should see the Inbound Document "fromRequest" is linked to ConsumerId "PreviouslyCreated"

  @CP-29794 @API-CP-29794-1.5 @ui-ecms1 @keerthi
  Scenario: Verify CREATEDDOCUMENT Type with active case/consumer links for maersk Case + Consumer Requiring Metadata IB Type
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer Requiring Metadata"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer Requiring Metadata"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE         | CREATEDDOCUMENT   |
      | ProcessType  | RECEIVED          |
      | CaseID       | PreviouslyCreated |
      | CONSUMERIDS  | PreviouslyCreated |
      | CHANNEL      | Mail              |
      | documentDate | Today             |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see there is a link between Case Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated"
    Then I should see there is a link between Consumer Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated"
    Then I should see there is a link between Inbound Correspondence Document "previouslyCreated" and Case Id "previouslyCreated"
    Then I should see there is a link between Inbound Correspondence Document "previouslyCreated" and Consumer Id "previouslyCreated"
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on a CID of "first available" Inbound Search Results
    Then I should see the Inbound Document "fromRequest" is linked to caseId "PreviouslyCreated"
    Then I should see the Inbound Document "fromRequest" is linked to ConsumerId "PreviouslyCreated"

  @CP-29794 @API-CP-29794-2.0 @API-ECMS @keerthi
  Scenario: Verify INBOUND_CORRESPONDENCE_UPDATE_EVENT for PROCESSEDDOCUMENT Type
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12544 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with no Case ID in addition to no Consumer ID
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE        | PROCESSEDDOCUMENT |
      | ProcessType | RECEIVED          |
      | CHANNEL     | Mail              |
    When I send the request to create the Inbound Correspondence Save Event
    And I retrieve response for "INBOUND_CORRESPONDENCE_INDEXED_EVENT" and verify for empty strings

  @CP-29794 @API-CP-29794-2.1 @API-ECMS @keerthi
  Scenario: Verify PROCESSEDDOCUMENT Type invokes task/service request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12544 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE        | PROCESSEDDOCUMENT |
      | ProcessType | RECEIVED          |
      | CHANNEL     | Mail              |
    When I send the request to create the Inbound Correspondence Save Event
    And I should validate Task has been "created"

  @CP-29794 @API-CP-29794-2.2 @ui-ecms1 @keerthi
  Scenario: Verify PROCESSEDDOCUMENT Type with active case/consumer links
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE         | PROCESSEDDOCUMENT |
      | ProcessType  | RECEIVED          |
      | CaseID       | PreviouslyCreated |
      | ConsumerID   | PreviouslyCreated |
      | CHANNEL      | Mail              |
      | documentDate | Today             |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see there is a link between Case Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated"
    Then I should see there is a link between Consumer Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated"
    Then I should see there is a link between Inbound Correspondence Document "previouslyCreated" and Case Id "previouslyCreated"
    Then I should see there is a link between Inbound Correspondence Document "previouslyCreated" and Consumer Id "previouslyCreated"
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on a CID of "first available" Inbound Search Results
    Then I should see the Inbound Document "fromRequest" is linked to caseId "PreviouslyCreated"
    Then I should see the Inbound Document "fromRequest" is linked to ConsumerId "PreviouslyCreated"


  @CP-29794 @API-CP-29794-3.1 @API-ECMS @keerthi
  Scenario: Verify CREATEDDOCUMENT Type shouldn't able to inactive case/consumer links
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE         | CREATEDDOCUMENT   |
      | ProcessType  | RECEIVED          |
      | CaseID       | PreviouslyCreated |
      | ConsumerID   | PreviouslyCreated |
      | CHANNEL      | Mail              |
      | documentDate | Today             |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see there is a link between Case Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated"
    Then I should see there is a link between Consumer Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated"
    Then I should see there is a link between Inbound Correspondence Document "previouslyCreated" and Case Id "previouslyCreated"
    Then I should see there is a link between Inbound Correspondence Document "previouslyCreated" and Consumer Id "previouslyCreated"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE         | CREATEDDOCUMENT |
      | ProcessType  | RECEIVED        |
      | CHANNEL      | Mail            |
      | documentDate | Today           |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see there is a link between Case Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated"
    Then I should see there is a link between Consumer Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated"
    Then I should see there is a link between Inbound Correspondence Document "previouslyCreated" and Case Id "previouslyCreated"
    Then I should see there is a link between Inbound Correspondence Document "previouslyCreated" and Consumer Id "previouslyCreated"


  @CP-29794 @API-CP-29794-3.2 @API-ECMS @keerthi
  Scenario: Verify PROCESSEDDOCUMENT Type able to inactive case/consumer links
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE         | PROCESSEDDOCUMENT |
      | ProcessType  | RECEIVED          |
      | CaseID       | RANDOM            |
      | ConsumerID   | RANDOM            |
      | CHANNEL      | Mail              |
      | documentDate | Today             |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see there is a link between Case Id "RANDOM" and Inbound Correspondence Document "previouslyCreated"
    Then I should see there is a link between Consumer Id "RANDOM" and Inbound Correspondence Document "previouslyCreated"
    Then I should see there is a link between Inbound Correspondence Document "previouslyCreated" and Case Id "RANDOM"
    Then I should see there is a link between Inbound Correspondence Document "previouslyCreated" and Consumer Id "RANDOM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE         | PROCESSEDDOCUMENT |
      | ProcessType  | RECEIVED          |
      | CaseID       | PreviouslyCreated |
      | ConsumerID   | PreviouslyCreated |
      | CHANNEL      | Mail              |
      | documentDate | Today             |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see there is a link between Case Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated"
    Then I should see there is a link between Consumer Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated"
    Then I should see there is a link between Inbound Correspondence Document "previouslyCreated" and Case Id "previouslyCreated"
    Then I should see there is a link between Inbound Correspondence Document "previouslyCreated" and Consumer Id "previouslyCreated"

############################################   CP-39359    #################################################

  @CP-39359 @CP-39359-01 @ui-ecms1 @James
  Scenario: Verify INBOUND_CORRESPONDENCE_UPDATE_EVENT still produced from CP
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Returned Mail  |
      | Language     | SPANISH                |
      | Channel      | Mail                   |
      | Status       | COMPLETE               |
      | Origin       | WEB PORTAL             |
      | FromName     | AUTOMATION             |
      | FormVersion  | 3                      |
      | ProcessType  | INBOUND CORRESPONDENCE |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | InboundDocumentIdDigital |
    And I click on a CID of "first available" Inbound Search Results
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "currentdate" via "calendarwidget"
    And I click on Edit Inbound Correspondence details Page Save button
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I retrieve the "INBOUND_CORRESPONDENCE_UPDATE_EVENT" event that is produced by trace id
    Then I should see the payload for the "INBOUND_CORRESPONDENCE_UPDATE_EVENT" is as expected
    And I should see that the event mapping for "INBOUND_CORRESPONDENCE_UPDATE_EVENT" exists
    And I should see that the "INBOUND_CORRESPONDENCE_UPDATE_EVENT" record is produced to DPBI

  @CP-39359 @CP-39359-02 @API-ECMS @James
  Scenario: Verify INBOUND_CORRESPONDENCE_INDEXED_EVENT when PROCESSEDDOCUMENT Type is received (blcrm)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with no Case ID in addition to no Consumer ID
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE         | PROCESSEDDOCUMENT       |
      | ProcessType  | RECEIVED                |
      | CHANNEL      | Mail                    |
      | documentType | maersk Case + Consumer |
    When I send the request to create the Inbound Correspondence Save Event
    Then I verify the "INBOUND_CORRESPONDENCE_INDEXED_EVENT" while the "INBOUND_CORRESPONDENCE_UPDATE_EVENT" is not produced

  @CP-39359 @CP-39359-03 @api-ecms-nj @James
  Scenario: Verify INBOUND_CORRESPONDENCE_INDEXED_EVENT when PROCESSEDDOCUMENT Type is received (njsbe)
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE General"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE         | PROCESSEDDOCUMENT |
      | ProcessType  | RECEIVED          |
      | CHANNEL      | Mail              |
      | documentType | NJ SBE General    |
    When I send the request to create the Inbound Correspondence Save Event
    Then I verify the "INBOUND_CORRESPONDENCE_INDEXED_EVENT" while the "INBOUND_CORRESPONDENCE_UPDATE_EVENT" is not produced

  @CP-39359 @CP-39359-04 @api-ecms-ineb @James
  Scenario: Verify INBOUND_CORRESPONDENCE_INDEXED_EVENT when PROCESSEDDOCUMENT Type is received (in-eb)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "INEB Disenrollment"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE         | PROCESSEDDOCUMENT  |
      | ProcessType  | RECEIVED           |
      | CHANNEL      | Mail               |
      | documentType | INEB Disenrollment |
    When I send the request to create the Inbound Correspondence Save Event
    Then I verify the "INBOUND_CORRESPONDENCE_INDEXED_EVENT" while the "INBOUND_CORRESPONDENCE_UPDATE_EVENT" is not produced

  @CP-39359 @CP-39359-05 @api-ecms-coverva @James
  Scenario: Verify INBOUND_CORRESPONDENCE_INDEXED_EVENT when PROCESSEDDOCUMENT Type is received (coverVa)
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV Complaint"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE         | PROCESSEDDOCUMENT |
      | ProcessType  | RECEIVED          |
      | CHANNEL      | Mail              |
      | documentType | VACV Complaint    |
    When I send the request to create the Inbound Correspondence Save Event
    Then I verify the "INBOUND_CORRESPONDENCE_INDEXED_EVENT" while the "INBOUND_CORRESPONDENCE_UPDATE_EVENT" is not produced

    ############################################   CP-39367    #################################################

  @CP-39367 @CP-39367-01 @ui-ecms1 @Keerthi
  Scenario: Verify INBOUND_CORRESPONDENCE_TYPE_UPDATE_EVENT produced from CP when type alone updated
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Returned Mail  |
      | Language     | SPANISH                |
      | Channel      | Mail                   |
      | Status       | COMPLETE               |
      | Origin       | WEB PORTAL             |
      | FromName     | AUTOMATION             |
      | FormVersion  | 3                      |
      | ProcessType  | INBOUND CORRESPONDENCE |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | InboundDocument |
    And I click on a CID of "first available" Inbound Search Results
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "type" field with value "maersk Eligibility" via "null"
    And I click on Edit Inbound Correspondence details Page Save button
    And I retrieve the "INBOUND_CORRESPONDENCE_TYPE_UPDATE_EVENT" event that is produced by trace id
    Then I should see the payload for the "INBOUND_CORRESPONDENCE_TYPE_UPDATE_EVENT" is as expected
    And I should see that the event mapping for "INBOUND_CORRESPONDENCE_TYPE_UPDATE_EVENT" exists
    And I should see that the "INBOUND_CORRESPONDENCE_TYPE_UPDATE_EVENT" record is produced to DPBI

  @CP-39367 @CP-39367-02 @ui-ecms1 @Keerthi
  Scenario: Verify INBOUND_CORRESPONDENCE_TYPE_UPDATE_EVENT produced from CP when type and receiveddate updated simultaneously
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Returned Mail  |
      | Language     | SPANISH                |
      | Channel      | Mail                   |
      | Status       | COMPLETE               |
      | Origin       | WEB PORTAL             |
      | FromName     | AUTOMATION             |
      | FormVersion  | 3                      |
      | ProcessType  | INBOUND CORRESPONDENCE |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | InboundDocument |
    And I click on a CID of "first available" Inbound Search Results
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "type" field with value "maersk Eligibility" via "null"
    Then I update the Inbound Correspondence "receiveddate" field with value "pastdate" via "calendarwidget"
    And I click on Edit Inbound Correspondence details Page Save button
    And I retrieve the "INBOUND_CORRESPONDENCE_TYPE_UPDATE_EVENT" event that is produced by trace id
    Then I should see the payload for the "INBOUND_CORRESPONDENCE_TYPE_UPDATE_EVENT" is as expected
    And I should see that the event mapping for "INBOUND_CORRESPONDENCE_TYPE_UPDATE_EVENT" exists
    And I should see that the "INBOUND_CORRESPONDENCE_TYPE_UPDATE_EVENT" record is produced to DPBI

  @CP-39367 @CP-39367-03 @API-ECMS @Keerthi
  Scenario: Verify INBOUND_CORRESPONDENCE_TYPE_UPDATE_EVENT,LINK_EVENT and INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT produced from API when type,received and notes updated
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers |
      | Language     | SPANISH                  |
      | Channel      | EMAIL                    |
      | Status       | COMPLETE                 |
      | FromName     | AUTOMATION               |
      | ProcessType  | INBOUND CORRESPONDENCE   |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | RECEIVED                 |
      | Channel        | Email                    |
      | documentType   | maersk Case + Consumers |
      | documentHandle | InboundDocumentIdDigital |
    When I send the request to create the Inbound Correspondence Save Event
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | documentType | maersk Case + Consumer |
      | receivedDate | 2022-11-08              |
      | note         | testnotes               |
    Then I verify the "INBOUND_CORRESPONDENCE_TYPE_UPDATE_EVENT,INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT,LINK_EVENT,TASK_SAVE_EVENT" while the "INBOUND_CORRESPONDENCE_UPDATE_EVENT" is not produced when IB Document is updated

  @CP-39367 @CP-39367-04 @API-ECMS @Keerthi
  Scenario: Verify INBOUND_CORRESPONDENCE_UPDATE_EVENT, INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT produced from API when received and notes updated
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers |
      | Language     | SPANISH                  |
      | Channel      | EMAIL                    |
      | Status       | COMPLETE                 |
      | FromName     | AUTOMATION               |
      | ProcessType  | INBOUND CORRESPONDENCE   |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | RECEIVED                 |
      | Channel        | Email                    |
      | documentType   | maersk Case + Consumers |
      | documentHandle | InboundDocumentIdDigital |
    When I send the request to create the Inbound Correspondence Save Event
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | receivedDate | 2022-11-08 |
      | note         | testnotes  |
    Then I verify the "INBOUND_CORRESPONDENCE_UPDATE_EVENT,INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT" while the "INBOUND_CORRESPONDENCE_TYPE_UPDATE_EVENT,LINK_EVENT,TASK_SAVE_EVENT" is not produced when IB Document is updated

  @CP-39367 @CP-39367-05  @api-ecms-ineb @Keerthi
  Scenario: Verify INBOUND_CORRESPONDENCE_TYPE_UPDATE_EVENT,LINK_EVENT and INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT produced from API when type,received and notes updated for INEB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | INEB Disenrollment     |
      | Language     | SPANISH                |
      | Channel      | EMAIL                  |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | RECEIVED                 |
      | Channel        | Email                    |
      | documentType   | INEB Disenrollment       |
      | documentHandle | InboundDocumentIdDigital |
    When I send the request to create the Inbound Correspondence Save Event
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | documentType | INEB Just Cause Form |
      | receivedDate | 2022-11-08           |
      | note         | testnotes            |
    Then I verify the "INBOUND_CORRESPONDENCE_TYPE_UPDATE_EVENT,INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT,LINK_EVENT,TASK_SAVE_EVENT" while the "INBOUND_CORRESPONDENCE_UPDATE_EVENT" is not produced when IB Document is updated


  @CP-39367 @CP-39367-06 @api-ecms-nj @Keerthi
  Scenario: Verify INBOUND_CORRESPONDENCE_TYPE_UPDATE_EVENT,LINK_EVENT and INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT produced from API when type,received and notes updated for NJ-SBE
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | NJ SBE General         |
      | Language     | SPANISH                |
      | Channel      | EMAIL                  |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | RECEIVED                 |
      | Channel        | Email                    |
      | documentType   | NJ SBE General           |
      | documentHandle | InboundDocumentIdDigital |
    When I send the request to create the Inbound Correspondence Save Event
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | documentType | NJ SBE Appeal Form |
      | receivedDate | 2022-11-08         |
      | note         | testnotes          |
    Then I verify the "INBOUND_CORRESPONDENCE_TYPE_UPDATE_EVENT,INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT,LINK_EVENT,TASK_SAVE_EVENT" while the "INBOUND_CORRESPONDENCE_UPDATE_EVENT" is not produced when IB Document is updated

  @CP-39367 @CP-39367-07  @api-ecms-coverva @Keerthi
  Scenario: Verify INBOUND_CORRESPONDENCE_TYPE_UPDATE_EVENT,LINK_EVENT and INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT produced from API when type,received and notes updated for Cover-Va
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | VACV Complaint         |
      | Language     | SPANISH                |
      | Channel      | EMAIL                  |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | RECEIVED                 |
      | Channel        | Email                    |
      | documentType   | VACV Complaint           |
      | documentHandle | InboundDocumentIdDigital |
    When I send the request to create the Inbound Correspondence Save Event
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | documentType | VACV Appeal |
      | receivedDate | 2022-11-08  |
      | note         | testnotes   |
    Then I verify the "INBOUND_CORRESPONDENCE_TYPE_UPDATE_EVENT,INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT,LINK_EVENT,TASK_SAVE_EVENT" while the "INBOUND_CORRESPONDENCE_UPDATE_EVENT" is not produced when IB Document is updated
