Feature: Publish Links to Inbound Correspondence

  @CP-4810 @CP-4810-1 @ui-ecms2 @james
  Scenario: Verify that when an Inbound Document is sent with a valid Case Id creates a link from Case to Inbound Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | previouslyCreated       |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | RECEIVED                 |
      | documentType   | maersk Case + Consumer  |
      | CaseID         | PreviouslyCreated        |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
      | documentDate   | Today                    |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see there is a link between Case Id "previouslyCreated" and Inbound Correspondence Document "InboundDocumentIdDigital"

  @CP-4810 @CP-4810-2 @ui-ecms2 @james
  Scenario: Verify that when an Inbound Document is sent with a valid Case Id creates a link from Inbound Correspondence to Case
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | previouslyCreated       |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | RECEIVED                 |
      | documentType   | maersk Case + Consumer  |
      | CaseID         | PreviouslyCreated        |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
      | documentDate   | Today                    |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see there is a link between Inbound Correspondence Document "InboundDocumentIdDigital" and Case Id "previouslyCreated"


  @CP-4810 @CP-4810-3 @ui-ecms2 @james
  Scenario: Verify that a link from an Inb Document and a case is visible in the links section of Inbound Correspondence Details
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | previouslyCreated       |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | RECEIVED                 |
      | documentType   | maersk Case + Consumer  |
      | CaseID         | PreviouslyCreated        |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
      | documentDate   | Today                    |
    When I send the request to create the Inbound Correspondence Save Event
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | InboundDocument |
    And I click on a CID of "first available" Inbound Search Results
    Then I should see the Inbound Document "previouslyCreated" is linked to caseId "previouslyCreated"

  @CP-4810 @CP-4810-4 @ui-ecms2 @james
  Scenario: Verify that when an Inbound Document is sent with a valid Consumer Id creates a link from Inbound Correspondence to Consumer
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | previouslyCreated       |
      | ConsumerID   | previouslyCreated       |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | RECEIVED                 |
      | documentType   | maersk Case + Consumer  |
      | CaseID         | PreviouslyCreated        |
      | ConsumerID     | PreviouslyCreated        |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
      | documentDate   | Today                    |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see there is a link between Inbound Correspondence Document "InboundDocumentIdDigital" and Consumer Id "previouslyCreated"

  @CP-4810 @CP-4810-5 @ui-ecms2 @james
  Scenario: Verify that when an Inbound Document is sent with a valid Consumer Id creates a link from Consumer to Inbound Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType | RECEIVED |
      | ConsumerID  | random   |
      | CHANNEL     | Mail     |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see there is a link between Consumer Id "RANDOM" and Inbound Correspondence Document "previouslyCreated"

  @CP-4810 @CP-4810-6 @ui-ecms2 @james
  Scenario: Verify that when an Inbound Document is sent with a valid Consumer Id creates a link from Consumer to Inbound Correspondence and is visible in Inbound Correspondence Details Page
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType | RECEIVED |
      | ConsumerID  | 17500    |
      | CHANNEL     | Mail     |
    And I send the request to create the Inbound Correspondence Save Event
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on a CID of "first available" Inbound Search Results
    Then I should see the Inbound Document "fromRequest" is linked to ConsumerId "17500"

  @CP-4810 @CP-4810-7 @ui-ecms2 @james
  Scenario: Verify that when an Inbound Document is sent with a valid Notification Id that a link from Inbound to Outbound Correspondence is created
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | RECEIVED          |
      | NOTIFICATIONID | previouslyCreated |
      | CHANNEL        | Mail              |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see there is a link between Notification Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated"

  @CP-4810 @CP-4810-8 @ui-ecms2 @james
  Scenario: Verify that when an Inbound Document is sent with a valid Notification Id creates a link from Outbound Correspondence to Inbound Correspondence and is visible in Inbound Correspondence Details Page
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | RECEIVED          |
      | NOTIFICATIONID | previouslyCreated |
      | CHANNEL        | Mail              |
    And I send the request to create the Inbound Correspondence Save Event
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on a CID of "first available" Inbound Search Results
    Then I should see the Inbound Document "fromRequest" is linked to Outbound Correspondence "fromRequest"

  @CP-4810 @CP-4810-9 @ui-ecms2 @james
  Scenario: Verify that when an Inbound Document is sent with a valid Notification Id that a link from Outbound to Inbound Correspondence is created
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | RECEIVED          |
      | NOTIFICATIONID | previouslyCreated |
      | CHANNEL        | Mail              |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see there is a link between Inbound Correspondence Id "previouslyCreated" and Outbound Correspondence "previouslyCreated"

  @CP-4810 @CP-4810-10 @ui-ecms2 @james
  Scenario: Verify that when an Inbound Document is sent with a valid Notification Id that a link from Outbound to Inbound Correspondence is visible in Outbound Correspondence Details
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | RECEIVED          |
      | NOTIFICATIONID | previouslyCreated |
      | CHANNEL        | Mail              |
    When I send the request to create the Inbound Correspondence Save Event
    And I logged into CRM
    And I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I should see the details page for the Outbound Correspondence
    And I should see the Outbound Correspondence "previouslyCreated" link to Inbound Correspondence "previouslyCreated" in the links section

  @CP-140 @CP-140-1 @ui-ecms2 @james
  Scenario: Verify that a link from an Inb Document and a case can be seen in Inbound Correspondence Details
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And the Inbound Document "fromRequest" is linked to caseId "7347"
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on a CID of "first available" Inbound Search Results
    Then I should see the Inbound Document "fromRequest" is linked to caseId "7347"

  @CP-140 @CP-140-2 @ui-ecms2 @james
  Scenario: Verify that a link from an Outbound Correspondence and a case can be seen in Outbound Correspondence Details Page
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    And I have Outbound Correspondence "previouslyCreated" is linked to caseId "previouslyCreated"
    And I logged into CRM
    And I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I should see the details page for the Outbound Correspondence
    And I should see the Outbound Correspondence "previouslyCreated" link to caseId "previouslyCreated" in the links section

  @CP-2958 @CP-2958-1 @ui-ecms2 @james
  Scenario: Verify that a link component is present from Inbound Correspondence Details Page
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And the Inbound Document "fromRequest" is linked to caseId "7347"
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on a CID of "first available" Inbound Search Results
    Then I should see the Inbound Document Links component is present Inbound Correspondence Details Page

  @CP-8483 @CP-8483-01 @ui-ecms2 @james
  Scenario: Verify link from Inbound Correspondence details page to an Outbound Correspondence
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
      | ProcessType    | RECEIVED                 |
      | documentHandle | InboundDocumentIdDigital |
      | documentType   | maersk Case + Consumer  |
      | NOTIFICATIONID | previouslyCreated        |
      | CHANNEL        | Mail                     |
      | documentDate   | Today                    |
    When I send the request to create the Inbound Correspondence Save Event
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | InboundDocument |
    And I click on a CID of "first available" Inbound Search Results
    Then I should see the Inbound Document "fromRequest" is linked to Outbound Correspondence "fromRequest"

  @CP-8483 @CP-8483-02 @ui-ecms2 @james
  Scenario: Verify I can navigate to the Outbound Correspondence from the Inbound Document Details Page Link
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
      | NOTIFICATIONID | previouslyCreated        |
      | documentType   | maersk Case + Consumer  |
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
    Then I should see that I have been navigated to the Outbound Correspondence that was "previouslyCreated"

  @CP-8483 @CP-8483-03 @ui-ecms2 @james
  Scenario: Verify I can navigate back to the Inbound document from back button
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


  ################ CP-19880 ######################
  @CP-19880 @CP-19880-1.1 @ui-ecms2 @Keerthi
  Scenario: Verify link to Application type  Medical Assistance before and after updating Application status on IB Correspondence details screen for BLCRM
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I have request to create Application with type "Medical Assistance" and name "Application for Medical Assistance"
    Then I send a request to create Application
    And I retrieve the Application details by application ID
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType   | RECEIVED          |
      | CHANNEL       | Mail              |
      | ApplicationID | previouslyCreated |
    When I send the request to create the Inbound Correspondence Save Event
    Then I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I validate Application under links section contains following values
      | ApplicationID |
      | Name          |
      | Type          |
      | StatusDate    |
      | Status        |
    And I update Application status to "Withdrawn" with reason "Already Receiving Services"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID |previouslyCreated|
    When I click on the Inbound Document CID link "firstAvailable"
    And I validate Application under links section contains following values
      | ApplicationID |
      | Name          |
      | Type          |
      | StatusDate    |
      | Status        |

  @CP-19880 @CP-19880-1.2 @ui-ecms2 @Keerthi
  Scenario: Verify link to Application type Long Term Care before and after updating Application status on IB Correspondence details screen for BLCRM
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I have request to create Application with type "Long Term Care" and name "Application for Long Term Care"
    Then I send a request to create Application
    And I retrieve the Application details by application ID
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType   | RECEIVED          |
      | CHANNEL       | Mail              |
      | ApplicationID | previouslyCreated |
    When I send the request to create the Inbound Correspondence Save Event
    Then I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I validate Application under links section contains following values
      | ApplicationID |
      | Name          |
      | Type          |
      | StatusDate    |
      | Status        |
    And I update Application status to "Withdrawn" with reason "Already Receiving Services"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID |previouslyCreated|
    When I click on the Inbound Document CID link "firstAvailable"
    And I validate Application under links section contains following values
      | ApplicationID |
      | Name          |
      | Type          |
      | StatusDate    |
      | Status        |

  @CP-21569 @CP-21569-1 @API-ECMS @James
  Scenario: Verify Linking the inbound correspondence within a set to Service Request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 13241 |
      | LINKSAMESETTASK      | true  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumers"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumers" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 13241 |
      | LINKSAMESETTASK      | true  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumers" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | SetId        | Random                  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | INBOUND CORRESPONDENCE   |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
      | documentType   | maersk Case + Consumer  |
      | SetId          | Random                   |
    And I send the request to create the Inbound Correspondence Save Event
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | SetId        | PreviouslyCreated       |
    When I send the request for the Inbound Correspondence in the same set as the previously created document
    When I update the Inbound Correspondence "previouslyCreated" with the following values
      | documentType | maersk Case + Consumers |
    Then I should see the "previouslyCreated" tasks on the first Inbound Correspondence received in a set is linked to the newly received to the Inbound Correspondence in the same set


#  @CP-21569 @CP-21569-2 @ui-ecms2 @James invalid scenario since CP-22869
  Scenario: Verify if Inbound Documents in same set will NOT link to tasks are closed and create New Service Request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 13241 |
      | LINKSAMESETTASK      | true  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumers"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumers" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 13241 |
      | LINKSAMESETTASK      | true  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumers" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | SetId        | Random                  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | INBOUND CORRESPONDENCE   |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
      | documentType   | maersk Case + Consumer  |
    And I send the request to create the Inbound Correspondence Save Event
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the link to the Service Request that is linked to the Inbound Document
    And I click Edit task type button
    And I close the Service Request of task type "Inbound Service Request"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | SetId        | PreviouslyCreated       |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence in the same set as the previously created document
    When I update the Inbound Correspondence "SameSetInboundDocument" with the following values
      | documentType | maersk Case + Consumers |
    Then I should see the "SameSetInboundDocument" tasks on the first Inbound Correspondence received in a set is NOT linked to the newly received to the Inbound Correspondence in the same set


  @CP-21569 @CP-21569-3 @ui-ecms2 @James
  Scenario: Verify if Inbound Documents in same set will create New Service Request when one is not already linked
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 13241 |
      | LINKSAMESETTASK      | true  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumers"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumers" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 13241 |
      | LINKSAMESETTASK      | true  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumers" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | SetId        | Random                  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | SetId        | PreviouslyCreated       |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence in the same set as the previously created document
    When I update the Inbound Correspondence "SameSetInboundDocument" with the following values
      | documentType | maersk Case + Consumers |
    Then I should see the "Service Request" has been linked to the "SameSetInboundDocument" Inbound Correspondence


  @CP-21569 @CP-21569-4 @ui-ecms2 @James
  Scenario: Verify if Inbound Documents not in same set will create New Service Request when one is not already linked
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 13241 |
      | LINKSAMESETTASK      | true  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumers"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumers" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 13241 |
      | LINKSAMESETTASK      | true  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumers" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | INBOUND CORRESPONDENCE   |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
      | documentType   | maersk Case + Consumer  |
    And I send the request to create the Inbound Correspondence Save Event
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
    When I send the request for the Inbound Correspondence in the same set as the previously created document
    When I update the Inbound Correspondence "previouslyCreated" with the following values
      | documentType | maersk Case + Consumers |
    Then I should see the "Service Request" has been linked to the Inbound Correspondence

  @CP-21569 @CP-21569-5 @CP-5794-07 @ui-ecms2 @James
  Scenario: Verify if updating an Inbound Documents not in same set will not create New Service Request when one is already present
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 13241 |
      | LINKSAMESETTASK      | true  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumers"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumers" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 13241 |
      | LINKSAMESETTASK      | true  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumers" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers |
      | Language     | SPANISH                  |
      | Channel      | EMAIL                    |
      | Status       | COMPLETE                 |
      | ProcessType  | INBOUND CORRESPONDENCE   |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | INBOUND CORRESPONDENCE   |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
      | documentType   | maersk Case + Consumers |
    And I send the request to create the Inbound Correspondence Save Event
    And I should see the "Service Request" has been linked to the Inbound Correspondence
    When I update the Inbound Correspondence "previouslyCreated" with the following values
      | documentType | maersk Case + Consumer |
    Then I should see the "Inbound Correspondence" is linked to the same "Service Request"

  @CP-21569 @CP-21569-6 @ui-ecms2 @James
  Scenario: Verify if updating an Inbound Documents not in same set will not create New Task when one is already present
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12797 |
      | LINKSAMESETTASK      | true  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumers"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumers" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12797 |
      | LINKSAMESETTASK      | true  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumers" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | INBOUND CORRESPONDENCE   |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
      | documentType   | maersk Case + Consumer  |
    And I send the request to create the Inbound Correspondence Save Event
    When I update the Inbound Correspondence "previouslyCreated" with the following values
      | documentType | maersk Case + Consumers |
    Then I should see that Tasks for the following Task Type Ids have been created
      | 12797 |


  @CP-21569 @CP-21569-7 @ui-ecms2 @James
  Scenario: Verify if Inbound Documents to a case and sr is created that case will link to sr
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 13241 |
      | LINKSAMESETTASK      | true  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumers"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumers" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 13241 |
      | LINKSAMESETTASK      | true  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumers" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers |
      | Language     | SPANISH                  |
      | Channel      | EMAIL                    |
      | Status       | COMPLETE                 |
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | CaseID       | PreviouslyCreated        |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | INBOUND CORRESPONDENCE   |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
      | documentType   | maersk Case + Consumers |
      | CaseID         | PreviouslyCreated        |
    And I send the request to create the Inbound Correspondence Save Event
    When I update the Inbound Correspondence "previouslyCreated" with the following values
      | documentType | maersk Case + Consumer |
    Then I should see the "CASE" is linked to the same "Case to Service Request"


  ################ CP-33980 ######################

  @CP-33980 @CP-33980-1.1 @ui-ecms1 @Keerthi
  Scenario:Verify that when an IB is sent with ApplicationID associated with CaseId, creates a link from ApplicationCaseID to Inbound Correspondence
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureDate | applicationSignatureExistsInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | CURRENT YYYYMMDD         | true                          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and run Get Application Links Call
    And I will get "Case" ID from the application links response
    And I want to edit an Inbound Correspondence Type by the name of "maersk Eligibility"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Eligibility"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType   | RECEIVED             |
      | CHANNEL       | Mail                 |
      | ApplicationID | previouslyCreatedATS |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see there is a link between Inbound Correspondence Document "previouslyCreated" and Case Id "ApplicationCaseId"
    Then I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on a CID of "first available" Inbound Search Results
    Then I should see the Inbound Document "fromRequest" is linked to caseId "ApplicationCaseId"

  @CP-33980 @CP-33980-1.2 @ui-ecms1 @Keerthi
  Scenario:Verify that when an IB is sent with a valid CaseId and ApplicationID associated with CaseId, creates a link from IBdocumentCaseID to Inbound Correspondence
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureDate | applicationSignatureExistsInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | CURRENT YYYYMMDD         | true                          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and run Get Application Links Call
    And I will get "Case" ID from the application links response
    And I want to edit an Inbound Correspondence Type by the name of "maersk Eligibility"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Eligibility"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType   | RECEIVED             |
      | CaseID        | random               |
      | CHANNEL       | Mail                 |
      | ApplicationID | previouslyCreatedATS |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see there is a link between Inbound Correspondence Document "previouslyCreated" and Case Id "RANDOM"
    Then I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on a CID of "first available" Inbound Search Results
    Then I should see the Inbound Document "fromRequest" is linked to caseId "RANDOM"

################ CP-22869 ######################

  @CP-22869 @CP-22869-1 @ui-ecms1 @Keerthi
  Scenario: Verify Linking to Existing Service Request upon update (Part of a set)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 13241 |
      | LINKSAMESETTASK      | true  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumers"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumers" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 13241 |
      | LINKSAMESETTASK      | true  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumers" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | SetId        | Random                  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | INBOUND CORRESPONDENCE   |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
      | documentType   | maersk Case + Consumer  |
      | SetId          | PreviouslyCreated        |
    And I send the request to create the Inbound Correspondence Save Event
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers |
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Language     | SPANISH                  |
      | Channel      | EMAIL                    |
      | Status       | COMPLETE                 |
      | FromName     | AUTOMATION               |
      | SetId        | PreviouslyCreated        |
    When I send the request for the Inbound Correspondence in the same set as the previously created document
    When I update the Inbound Correspondence "SameSetInboundDocument" with the following values
      | documentType | maersk Case + Consumer |
    Then I should see the "previouslyCreated" tasks on the first Inbound Correspondence received in a set is linked to the newly received to the Inbound Correspondence in the same set
    Then I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | SameSetInboundDocument |
    And I click on a CID of "first available" Inbound Search Results
    Then I should see the Inbound Document "SameSetInboundDocument" is linked to TaskId "previouslycreatedServicetaskId"


  @CP-22869 @CP-22869-2 @ui-ecms1 @Keerthi
  Scenario: Verify Linking to a New Service Request upon receiving new document (part of a set)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 13241 |
      | LINKSAMESETTASK      | true  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | SetId        | Random                  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | INBOUND CORRESPONDENCE   |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
      | documentType   | maersk Case + Consumer  |
      | SetId          | PreviouslyCreated        |
    And I send the request to create the Inbound Correspondence Save Event
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | SetId        | PreviouslyCreated       |
    When I send the request for the Inbound Correspondence in the same set as the previously created document
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | INBOUND CORRESPONDENCE  |
      | CHANNEL        | Mail                    |
      | documentHandle | SameSetInboundDocument  |
      | documentType   | maersk Case + Consumer |
      | SetId          | PreviouslyCreated       |
    And I send the request to create the Inbound Correspondence Save Event
    Then I should see the "previouslyCreated" tasks on the first Inbound Correspondence received in a set is linked to the newly received to the Inbound Correspondence in the same set
    Then I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | SameSetInboundDocument |
    And I click on a CID of "first available" Inbound Search Results
    Then I should see the Inbound Document "SameSetInboundDocument" is linked to TaskId "previouslycreatedServicetaskId"


  @CP-2925 @CP-15393 @CP-15393-1 @CP-2925-1a @ui-ecms1 @james
  Scenario: Verify that I can unlink a case from an Inbound Document
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
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | INBOUND CORRESPONDENCE   |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
      | documentType   | maersk Case + Consumer  |
      | CaseID         | PreviouslyCreated        |
    And I send the request to create the Inbound Correspondence Save Event
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    And I click on the Inbound Document CID link "firstAvailable"
    And I should see the Inbound Document "previouslyCreated" is linked to caseId "previouslyCreated"
    And I click the Unlink button on Correspondence
    And I verify Successfully Unlink on Correspondence
    And I should see that the "caseId" has been unlinked from the Inbound Document
    And I should see that the "caseId" keyword value has been removed from the Inbound Document
    And I should see the previously linked Task is still Linked to the Inbound Document


  @CP-2925 @CP-15393-2 @CP-2925-2a @ui-ecms1 @james
  Scenario: Verify that I can unlink a consumer from an Inbound Document
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | ConsumerID   | PreviouslyCreated       |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | INBOUND CORRESPONDENCE   |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
      | documentType   | maersk Case + Consumer  |
      | ConsumerID     | previouslyCreated        |
    And I send the request to create the Inbound Correspondence Save Event
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    And I click on a CID of "first available" Inbound Search Results
    And I should see the Inbound Document "previouslyCreated" is linked to ConsumerId "previouslyCreated"
    And I click the Unlink button on Correspondence
    And I verify Successfully Unlink on Correspondence
    And I should see that the "consumerId" has been unlinked from the Inbound Document
    And I should see that the "consumerId" keyword value has been removed from the Inbound Document
    And I should see the previously linked Task is still Linked to the Inbound Document