Feature: View OB Correspondence Icon to Links Component of IB Correspondence

  @CP-23923 @CP-23923-01 @ui-ecms2 @RuslanL
  Scenario: Verify a view image Icon will be available to the right of the Unlink icon
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail"
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
    And I should see a view image Icon to the right of the Unlink icon
    Then I should see the Mail notification is viewed

  @CP-23923 @CP-23923-02 @ui-ecms2 @RuslanL
  Scenario: Verify a view image Icon will be unavailable when Object Received On equal to null
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail" and without Object File
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
    And I should not see a view image Icon to the right of the Unlink icon

  @CP-23923 @CP-23923-03 @ui-ecms2 @RuslanL
  Scenario: Verify the system will display the image of the first notification in the proper order (Mail, Email, Text)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
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
    And I should see a view image Icon to the right of the Unlink icon
    Then I should see the Mail notification is viewed

  @CP-23923 @CP-23923-04 @ui-ecms2 @RuslanL
  Scenario: Verify the system will display the image of the first notification in the proper order (Email, Text)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Email,Text"
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
    And I should see a view image Icon to the right of the Unlink icon
    Then I should see the Email notification is viewed