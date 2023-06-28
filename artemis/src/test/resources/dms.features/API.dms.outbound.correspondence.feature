@DMS-35
Feature: Outbound Correspondence Request

  @DMS-35-1 @API-ECMS @api-smoke-devops @ECMS-SMOKE @James
  Scenario: Verify the following values can be specified; Correspondence Type, Language, Case ID, Regarding Consumer ID, Requester ID, and Requester Type
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | default           |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | Random            |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
    When I send the request for an Outbound Correspondence to the service
    And Wait for 3 seconds
    And I retrieve the Outbound Correspondence by Correspondence ID
    Then I should see the following values of the Outbound Correspondence Get request as were previously sent
      | correspondenceDefinitionMMSCode | default         |
      | language                        | English         |
      | caseId                          | Random          |
      | regardingConsumerId             | Random          |
      | requesterId                     | 2425            |
      | requesterType                   | ConnectionPoint |

  @DMS-35-2 @API-ECMS @James
  Scenario: Verify more than one Consumer ID can be specified
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | default           |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | Random,Random     |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
    When I send the request for an Outbound Correspondence to the service
    And I retrieve the Outbound Correspondence by Correspondence ID
    Then I should see the following values of the Outbound Correspondence Get request as were previously sent
      | correspondenceDefinitionMMSCode | default           |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | Random,Random     |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |

  @DMS-35-3 @API-ECMS @James
  Scenario: Verify more than one Notification can be specified
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I have specified more than one Notification in a request for an Outbound Correspondence
    When I send the request for an Outbound Correspondence to the service
    Then I should not get a response indicating it was a bad request

  @DMS-35-4 @API-ECMS @James
  Scenario: Verify the following channels of a Notification can be specified; Mail address, Email address, Sms/text number, Fax number
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I have specified the following types of Notification Channels in the request for an Outbound Correspondence
      | Mail  |
      | Email |
      | Text  |
      | Fax   |
    When I send the request for an Outbound Correspondence to the service
    Then I should not get a response indicating it was a bad request

  @DMS-36-1 @API-ECMS @James
  Scenario: Verify Correspondence ID is created for each successful request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I have a valid Outbound Correspondence request
    When I send the request for an Outbound Correspondence to the service
    Then I should see a Correspondence ID is generated in the response

  @DMS-36-2 @API-ECMS @James
  Scenario: Verify that a valid Outbound Correspondence Request returns a success status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I have a valid Outbound Correspondence request
    When I send the request for an Outbound Correspondence to the service
    Then I should see a success status in the response

  @DMS-36-3 @API-ECMS @James
  Scenario: Verify the current date and time is stored for each valid Outbound Correspondence Request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | default           |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | Random            |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
    When I send the request for an Outbound Correspondence to the service
    And I retrieve the Outbound Correspondence by Correspondence ID
    Then I should see the following values of the Outbound Correspondence Get request as were previously sent
      | correspondenceDefinitionMMSCode | default         |
      | language                        | English         |
      | caseId                          | Random          |
      | regardingConsumerId             | Random          |
      | requesterId                     | 2425            |
      | requesterType                   | ConnectionPoint |
    When I retrieve the Outbound Correspondence by Correspondence ID
    Then I should see a date and time was stored for the Outbound Correspondence Request

  @DMS-36-4 @API-ECMS @James
  Scenario: Verify that the status of the request is stored for every valid Outbound Correspondence Request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I have a valid Outbound Correspondence request
    And I send the request for an Outbound Correspondence to the service
    When I retrieve the Outbound Correspondence by Correspondence ID
    Then I should see the status of the Outbound Correspondence Request

  @DMS-36-5 @API-ECMS @James
  Scenario: Verify the following properties are stored for each Outbound Correspondence Request; Correspondence Type, Language, Case ID, Consumer ID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I have specified the following values in the request for an Outbound Correspondence
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | consumerId                      | Random            |
      | correspondenceDefinitionMMSCode | default           |
    And I send the request for an Outbound Correspondence to the service
    When I retrieve the Outbound Correspondence by Correspondence ID
    Then I should see the following values of the Outbound Correspondence Get request as were previously sent
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | consumerId                      | Random            |
      | correspondenceDefinitionMMSCode | default           |

  @DMS-36-6 @API-ECMS @ui-ecms1 @James
  Scenario: Verify that an Outbound Correspondence Request without Correspondence Type will return a status of failed and the reason for the failure
    Given I have an Outbound Correspondence request without a Correspondence Type
    And I will get the Authentication token for "<projectName>" in "CRM"
    When I send the request for an Outbound Correspondence to the service with no correspondence type
    Then I should see failed status and the reason for the failure in the response

  @DMS-36-7 @API-ECMS @ui-ecms1 @James
  Scenario: Verify notification has stored the following properties for each stored notification; Consumer ID, Channel, Corresponding destination
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I have specified the following values in the request for an Outbound Correspondence
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | consumerId                      | Random            |
      | channelType                     | Fax               |
      | faxNumber                       | 2323323333        |
      | correspondenceDefinitionMMSCode | default           |
    When I send the request for an Outbound Correspondence to the service
    And Wait for 2 seconds
    When I retrieve the Outbound Correspondence by Correspondence ID
    Then I should see the following values of the Outbound Correspondence Get request as were previously sent
      | consumerId  | Random     |
      | channelType | Fax        |
      | faxNumber   | 2323323333 |

  @CP-9965 @CP-9965-01 @API-ECMS @Sang
  Scenario: Retrieve Outbound Correspondence with one reciepient Body Data and verify in created events
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I have specified the following values in the request for an Outbound Correspondence
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | consumerId                      | Random            |
      | channelType                     | Fax               |
      | faxNumber                       | 2323323333        |
      | correspondenceDefinitionMMSCode | default           |
    When I send the request for an Outbound Correspondence to the service
    When I retrieve the Outbound Correspondence by Correspondence ID
    And I initiate Get letter data by the retrieved NID from OBCorrespondence
    And I verify body is created in the response of Get Letter Data
    And I initiate post search "LETTER_DATA_SAVE_EVENT" list to find the Event ID created with Correspondence Id
    And I initiate correspondence save event by the eventID found in list of Inbound Correspondence Save Event
    And I verify created Correspondence Id matches the Correspondence Id in the Letter Data Save Event

  @CP-9965 @CP-9965-02 @API-ECMS @Sang
  Scenario: Retrieve Outbound Correspondence with no reciepient Body Data and verify in created events
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate post outbound correspondence with no recipient
    When I initiate Get Correspondence by retrieved Correspondence ID
    And I initate Get letter data by the retrieved NID
    And I verify body is created in the response of Get Letter Data
    And I initiate post search "LETTER_DATA_SAVE_EVENT" list to find the Event ID created with Correspondence Id
    And I initiate correspondence save event by the eventID found in list of Inbound Correspondence Save Event
    And I verify created Correspondence Id matches the Correspondence Id in the Letter Data Save Event

