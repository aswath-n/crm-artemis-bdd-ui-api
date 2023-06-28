Feature: Update Outbound Correspondence Requests

  @CP-10027 @CP-10027-01 @CP-37140 @CP-37140-01 @API-ECMS @James @CP-43072
  Scenario: Verify Outbound Correspondences with equal regarding values and body data will not be processed
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId     | Random  |
      | ConsumerId | Random  |
      | bodyData   | Random  |
      | type       | CCSONLY |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | CaseId     | Same |
      | ConsumerId | Same |
      | bodyData   | Same |
      | type       | CCSONLY |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-10027 @CP-10027-02 @API-ECMS @James @CP-43072
  Scenario: Verify Outbound Correspondences with different body data will update the body data of existing Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId     | Random |
      | ConsumerId | Random |
      | bodyData   | Random |
      | type       | CCSONLY |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | CaseId     | Same      |
      | ConsumerId | Same      |
      | bodyData   | Different |
      | type       | CCSONLY |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the Outbound Correspondence was updated with the following values
      | bodyData | Different |

  @CP-10027 @CP-10027-03 @API-ECMS @James @CP-43072
  Scenario: Verify Outbound Correspondences with different consumer Ids will update the anchor of existing Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId     | Random |
      | ConsumerId | Random |
      | bodyData   | Random |
      | type       | CCSONLY |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | CaseId     | Same      |
      | ConsumerId | Different |
      | bodyData   | Same      |
      | type       | CCSONLY |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the Outbound Correspondence was updated with the following values
      | ConsumerId | Different |

  @CP-10027 @CP-10027-04 @API-ECMS @James @CP-43072
  Scenario: Verify Outbound Correspondences with different type will create a new correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId     | Random |
      | ConsumerId | Random |
      | bodyData   | Random |
      | type       | CCSONLY |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | CaseId     | Same      |
      | ConsumerId | Same      |
      | bodyData   | Same      |
      | type       | Different |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence

  @CP-10027 @CP-10027-05 @API-ECMS @James @CP-43072
  Scenario Outline: Verify Outbound Correspondences will be created when existing correspondence is cancelled even if all values are same
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId     | Random |
      | ConsumerId | Random |
      | bodyData   | Random |
      | type       | CCSONLY |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I change the Correspondence status of the Outbound Correspondence to "<Status>"
    When I create Outbound Correspondence with regarding values consisting of the following
      | CaseId     | Same |
      | ConsumerId | Same |
      | bodyData   | Same |
      | type       | CCSONLY |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence
    Examples:
      | Status   |
      | Canceled |
      | Error    |

  @CP-10027 @CP-10027-06 @API-ECMS @Keerthi @CP-43072
  Scenario: Verify Outbound Correspondences with same CaseId,ConsumerIds as regarding value will not be processed with recipients(BLCRM)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CCSONLY           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | 1,2               |
      | channelType                     | Mail              |
      | firstName                       | test              |
      | lastName                        | test              |
      | city                            | brooklyn          |
      | state                           | NY                |
      | zipcode                         | 11202             |
      | streetAddress                   | test lane 1       |
    And I send the custom request OB Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CCSONLY           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | 1,2               |
      | channelType                     | Mail              |
      | firstName                       | test              |
      | lastName                        | test              |
      | city                            | brooklyn          |
      | state                           | NY                |
      | zipcode                         | 11202             |
      | streetAddress                   | test lane 1       |
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-38411 @CP-38411-01 @API-ECMS @James
  Scenario: Verify Outbound Correspondence Notification will be updated to ready to view when patched
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | writtenLanguage  | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | emailOnly         |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients                      | random            |
      | channel                         | Email             |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I see that a Document has been uploaded to Onbase for the "previouslyCreated" Outbound Correspondence Notification
    And I receive an update to the "previouslyCreated" Outbound Correspondence Notification that it is read to be viewed
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Updated By       | ECMS Service          |
      | Updated Date     | current date and hour |
      | objectReceivedOn | current date and hour |
    And I retrieve the "NOTIFICATION_UPDATE_EVENT_API" event that is produced by trace id
    Then I should see the payload for the "NOTIFICATION_UPDATE_EVENT_PATCH" is as expected
    And I should see that the event mapping for "NOTIFICATION_UPDATE_EVENT" exists
    And I should see that the "NOTIFICATION_UPDATE_EVENT_PATCH" record is produced to DPBI

  @CP-38411 @CP-38411-02 @API-ECMS @James
  Scenario: Verify Outbound Correspondence Notification update requires notification Id
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I receive an update to the "previouslyCreated" Outbound Correspondence Notification that it is read to be viewed with "0000000" notification Id
    Then I should see Outbound Correspondence Notification patch endpoint returns "Notification ID not found" message

  @CP-38411 @CP-38411-03 @API-ECMS @James
  Scenario: Verify Outbound Correspondence Notification update requires a valid notification Id
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I receive an update to the "previouslyCreated" Outbound Correspondence Notification that it is read to be viewed with "xxxxxxx" notification Id
    Then I should see Outbound Correspondence Notification patch endpoint returns "Notification id is invalid" message

  @CP-38411 @CP-38411-04 @ui-ecms1 @James
  Scenario: Verify Outbound Correspondence Notification can be view in CP
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | mailDestination | random active address |
      | writtenLanguage | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | CAONLY            |
      | caseId                          | previouslyCreated |
      | recipients                      | random            |
      | channel                         | Mail              |
      | zip                             | 12345             |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I see that a Document has been uploaded to Onbase for the "previouslyCreated" Outbound Correspondence Notification for "Mail" Channel
    And I receive an update to the "previouslyCreated" Outbound Correspondence Notification that it is read to be viewed
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I click on the view icon for the correspondence "CreatedFromAPI"
    Then I should see the Mail notification is viewed

    ######################################## CP-37140 #########################################

  @CP-37140 @CP-37140-02 @API-ECMS @Keerthi
  Scenario: Verify Outbound Correspondences with Force-Create-Indicator Parameter is False will not create a new order in BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId     | Random |
      | ConsumerId | Random |
      | bodyData   | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | CaseId                 | Same  |
      | ConsumerId             | Same  |
      | bodyData               | Same  |
      | Force-Create-Indicator | false |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-37140 @CP-37140-03 @API-ECMS @Keerthi
  Scenario: Verify Outbound Correspondences with Force-Create-Indicator Parameter is true will create a new order in BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId     | Random |
      | ConsumerId | Random |
      | bodyData   | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | CaseId                 | Same |
      | ConsumerId             | Same |
      | bodyData               | Same |
      | Force-Create-Indicator | true |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence

  @CP-37140 @CP-37140-04 @API-ECMS @Keerthi
  Scenario: Verify Outbound Correspondences with Invalid Force-Create-Indicator Parameter will throw warning message in BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId     | Random |
      | ConsumerId | Random |
      | bodyData   | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | CaseId                 | Same    |
      | ConsumerId             | Same    |
      | bodyData               | Same    |
      | Force-Create-Indicator | test123 |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the warning message while creating an Outbound Correspondence with invalid header

  @CP-37140 @CP-37140-1.1 @api-ecms-ineb @Keerthi
  Scenario: Verify Outbound Correspondences with equal regarding values and body data will not create a new order in IN-EB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    #And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | CaseId     | Same |
      | ConsumerId | Same |
      | bodyData   | Same |
   # And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-37140 @CP-37140-2.1  @api-ecms-ineb @Keerthi
  Scenario: Verify Outbound Correspondences with Force-Create-Indicator Parameter is False will not create a new order in IN-EB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    #And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | CaseId                 | Same  |
      | ConsumerId             | Same  |
      | bodyData               | Same  |
      | Force-Create-Indicator | false |
    #And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-37140 @CP-37140-3.1  @api-ecms-ineb @Keerthi
  Scenario: Verify Outbound Correspondences with Force-Create-Indicator Parameter is true will create a new order in IN-EB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    #And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | CaseId                 | Same |
      | ConsumerId             | Same |
      | bodyData               | Same |
      | Force-Create-Indicator | true |
    #And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence

  @CP-37140 @CP-37140-4.1 @api-ecms-ineb @Keerthi
  Scenario: Verify Outbound Correspondences with Invalid Force-Create-Indicator Parameter will throw warning message in IN-EB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    #And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | CaseId                 | Same    |
      | ConsumerId             | Same    |
      | bodyData               | Same    |
      | Force-Create-Indicator | test123 |
    #And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the warning message while creating an Outbound Correspondence with invalid header


  @CP-12307 @CP-12307-01 @API-ECMS @James
  Scenario: Verify Outbound Correspondence Notification will be updated to has been read when patched
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | writtenLanguage  | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | emailOnly         |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients                      | random            |
      | channel                         | Email             |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I see that a Document has been uploaded to Onbase for the "previouslyCreated" Outbound Correspondence Notification
    And I receive an update to the "previouslyCreated" Outbound Correspondence Notification for digitallyRead as "true"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Updated By   | ECMS Service          |
      | Updated Date | current date and hour |
    And I retrieve the "NOTIFICATION_UPDATE_EVENT_VIEWED_PATCH" event that is produced by trace id
    Then I should see the payload for the "NOTIFICATION_UPDATE_EVENT_VIEWED_PATCH" is as expected
    And I should see that the event mapping for "NOTIFICATION_UPDATE_EVENT" exists
    And I should see that the "NOTIFICATION_UPDATE_EVENT_PATCH" record is produced to DPBI

  @CP-12307 @CP-12307-01b @api-ecms-dceb @James
  Scenario: Verify Outbound Correspondence Notification will be updated to has been read when patched for dceb
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | emailOnly                    |
      | caseId                          | DCEBcaseid                   |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients                      | random                       |
      | channel                         | Email                        |
      | language                        | English                      |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I see that a Document has been uploaded to Onbase for the "previouslyCreated" Outbound Correspondence Notification
    And I receive an update to the "previouslyCreated" Outbound Correspondence Notification for digitallyRead as "true"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Updated By   | ECMS Service          |
      | Updated Date | current date and hour |
    And I retrieve the "NOTIFICATION_UPDATE_EVENT_VIEWED_PATCH" event that is produced by trace id
    Then I should see the payload for the "NOTIFICATION_UPDATE_EVENT_VIEWED_PATCH" is as expected
    And I should see that the event mapping for "NOTIFICATION_UPDATE_EVENT" exists
    And I should see that the "NOTIFICATION_UPDATE_EVENT_PATCH" record is produced to DPBI

  @CP-12307 @CP-12307-02 @API-ECMS @James
  Scenario: Verify Outbound Correspondence Notification update has been viewed requires notification Id
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I receive an update to the "previouslyCreated" Outbound Correspondence Notification that it been viewed with "0000000" notification Id
    Then I should see Outbound Correspondence Notification patch endpoint returns "Notification ID not found" message

  @CP-12307 @CP-12307-03 @API-ECMS @James
  Scenario: Verify Outbound Correspondence Notification update has been viewed requires a valid notification Id
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I receive an update to the "previouslyCreated" Outbound Correspondence Notification that it been viewed with "xxxxxxx" notification Id
    Then I should see Outbound Correspondence Notification patch endpoint returns "Notification id is invalid" message

  @CP-41806 @CP-41806-01 @API-ECMS @James
  Scenario: Verify Outbound Correspondence Notification digitallyRead value is returned by searchcorrespondence endpoint
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | writtenLanguage  | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | emailOnly         |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients                      | random            |
      | channel                         | Email             |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I receive an update to the "previouslyCreated" Outbound Correspondence Notification for digitallyRead as "true"
    Then I should see that the "/searchcorrespondence" end point is showing the digitallyRead for the "previouslyCreated" Outbound Correspondence Notification

  @CP-41806 @CP-41806-01b @api-ecms-dceb @James
  Scenario: Verify Outbound Correspondence Notification digitallyRead value is returned by searchcorrespondence endpoint for dceb
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | emailOnly                    |
      | caseId                          | DCEBcaseid                   |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients                      | random                       |
      | channel                         | Email                        |
      | language                        | English                      |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    When I receive an update to the "previouslyCreated" Outbound Correspondence Notification for digitallyRead as "true"
    Then I should see that the "/searchcorrespondence" end point is showing the digitallyRead for the "previouslyCreated" Outbound Correspondence Notification

  @CP-41806 @CP-41806-02 @API-ECMS @James
  Scenario: Verify Outbound Correspondence Notification digitallyRead value is returned by global search correspondence endpoint
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | writtenLanguage  | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | emailOnly         |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients                      | random            |
      | channel                         | Email             |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I receive an update to the "previouslyCreated" Outbound Correspondence Notification for digitallyRead as "true"
    Then I should see that the "/outboundcorrespondence" end point is showing the digitallyRead for the "previouslyCreated" Outbound Correspondence Notification

  @CP-41806 @CP-41806-02b @api-ecms-dceb @James
  Scenario: Verify Outbound Correspondence Notification digitallyRead value is returned by global search correspondence endpoint for dceb
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | emailOnly                    |
      | caseId                          | DCEBcaseid                   |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients                      | random                       |
      | channel                         | Email                        |
      | language                        | English                      |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I receive an update to the "previouslyCreated" Outbound Correspondence Notification for digitallyRead as "true"
    Then I should see that the "/outboundcorrespondence" end point is showing the digitallyRead for the "previouslyCreated" Outbound Correspondence Notification

  @CP-41806 @CP-41806-03 @API-ECMS @James
  Scenario: Verify Outbound Correspondence Notification digitallyRead value is returned by /correspondences/{correspondenceid}/notifications endpoint
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | writtenLanguage  | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | emailOnly         |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients                      | random            |
      | channel                         | Email             |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I receive an update to the "previouslyCreated" Outbound Correspondence Notification for digitallyRead as "true"
    Then I should see that the "/correspondences/{correspondenceid}/notifications" end point is showing the digitallyRead for the "previouslyCreated" Outbound Correspondence Notification

  @CP-41806 @CP-41806-03b @api-ecms-dceb @James
  Scenario: Verify Outbound Correspondence Notification digitallyRead value is returned by /correspondences/{correspondenceid}/notifications endpoint for dceb
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | emailOnly                    |
      | caseId                          | DCEBcaseid                   |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients                      | random                       |
      | channel                         | Email                        |
      | language                        | English                      |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I receive an update to the "previouslyCreated" Outbound Correspondence Notification for digitallyRead as "true"
    Then I should see that the "/correspondences/{correspondenceid}/notifications" end point is showing the digitallyRead for the "previouslyCreated" Outbound Correspondence Notification

  @CP-41806 @CP-41806-04 @API-ECMS @James
  Scenario: Verify Outbound Correspondence Notification digitallyRead value is returned by /correspondences/{correspondenceid} endpoint
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | writtenLanguage  | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | emailOnly         |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients                      | random            |
      | channel                         | Email             |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I receive an update to the "previouslyCreated" Outbound Correspondence Notification for digitallyRead as "true"
    Then I should see that the "/correspondences/{correspondenceid}" end point is showing the digitallyRead for the "previouslyCreated" Outbound Correspondence Notification

  @CP-41806 @CP-41806-04b @api-ecms-dceb @James
  Scenario: Verify Outbound Correspondence Notification digitallyRead value is returned by /correspondences/{correspondenceid} endpoint for dceb
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | emailOnly                    |
      | caseId                          | DCEBcaseid                   |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients                      | random                       |
      | channel                         | Email                        |
      | language                        | English                      |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I receive an update to the "previouslyCreated" Outbound Correspondence Notification for digitallyRead as "true"
    Then I should see that the "/correspondences/{correspondenceid}" end point is showing the digitallyRead for the "previouslyCreated" Outbound Correspondence Notification

  @CP-41806 @CP-41806-05 @API-ECMS @James
  Scenario: Verify Outbound Correspondence Notification digitallyRead value is returned when included in create new notification request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | writtenLanguage  | English               |
      | Fax              | secondFaxDestination  |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | emailOnly         |
      | digitallyRead                   | true              |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients                      | random            |
      | channel                         | Email             |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I create a Notification with the "previouslyCreated" new Outbound Correspondence with the following values
      | digitallyRead          | true                       |
      | language               | English                    |
      | recipient              | previouslyCreated          |
      | destination            | NewNotificationDestination |
      | NewNotificationChannel | Fax                        |
    Then I should see that the "/correspondences/{correspondenceid}" end point is showing the digitallyRead for the "newlyCreatedNotification" Outbound Correspondence Notification

  @CP-41806 @CP-41806-05b @api-ecms-dceb @James
  Scenario: Verify Outbound Correspondence Notification digitallyRead value is returned when included in create new notification request for dceb
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase  |
      | caseId                          | DCEBcaseid |
      | recipients                      | random     |
      | channel                         | Email      |
      | language                        | English    |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I create a Notification with the "previouslyCreated" new Outbound Correspondence with the following values
      | digitallyRead          | true                       |
      | language               | English                    |
      | recipient              | previouslyCreated          |
      | destination            | NewNotificationDestination |
      | NewNotificationChannel | Text                       |
    Then I should see that the "/correspondences/{correspondenceid}" end point is showing the digitallyRead for the "newlyCreatedNotification" Outbound Correspondence Notification

  @CP-41806 @CP-41806-06 @API-ECMS @James
  Scenario: Verify Outbound Correspondence Notification digitallyRead value is returned when included in create correspondence bulk insert request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY            |
      | digitallyRead                   | true              |
      | language                        | English           |
      | status                          | Requested         |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | createdBy                       | 2492              |
      | notificationLanguage            | English           |
    And I validate the Bulk Outbound Correspondence is created
    Then I should see that the "/correspondences/{correspondenceid}" end point is showing the digitallyRead for the "previouslyCreatedBulk" Outbound Correspondence Notification

  @CP-41806 @CP-41806-06bDCEB @api-ecms-dceb @James
  Scenario: Verify Outbound Correspondence Notification digitallyRead value is returned when included in create correspondence bulk insert request for dceb
    Given I will get the Authentication token for "DC-EB" in "CRM"
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY    |
      | language                        | English   |
      | status                          | Requested |
      | caseId                          | 123       |
      | regardingConsumerId             | 321       |
      | createdBy                       | 2492      |
      | digitallyRead                   | true      |
      | notificationLanguage            | English   |
    And I validate the Bulk Outbound Correspondence is created
    Then I should see that the "/correspondences/{correspondenceid}" end point is showing the digitallyRead for the "previouslyCreatedBulk" Outbound Correspondence Notification

  @CP-42186 @CP-42186-02 @API-ECMS @James
  Scenario: Verify Outbound Correspondence recipientInfo/externalIds element is returned when included in create correspondence bulk insert request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | allChCase         |
      | digitallyRead                   | true              |
      | language                        | English           |
      | status                          | Requested         |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | createdBy                       | 2492              |
      | notificationLanguage            | English           |
      | externalCaseIdCHIP              | random            |
      | externalConsumerIdCHIP          | random            |
      | externalCaseIdMedicaid          | random            |
      | externalConsumerIdMedicaid      | random            |
    And I validate the Bulk Outbound Correspondence is created
    Then I should see that the "/correspondences/{correspondenceid}" end point is showing the externalIds for the "previouslyCreatedBulk" Outbound Correspondence Notification

  @CP-42186 @CP-42186-03a @API-ECMS @James
  Scenario Outline: Verify POST /correspondences API stores what is passed in recipientInfo/externalIds element
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole         | Primary Individual    |
      | <channelDestination> | random active address |
      | writtenLanguage      | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | recipients                      | random            |
      | channel                         | <Channel>         |
      | language                        | English           |
      | externalCaseIdCHIP              | random            |
      | externalConsumerIdCHIP          | random            |
      | externalCaseIdMedicaid          | random            |
      | externalConsumerIdMedicaid      | random            |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    Then I should see that the "/correspondences/{correspondenceid}" end point is showing the externalIds for the "previouslyCreated" Outbound Correspondence Notification
    And I retrieve the "CORRESPONDENCE_RECIPIENT_SAVE_EVENT_EXTERNALIDS" event that is produced by trace id
    Then I should see the payload for the "CORRESPONDENCE_RECIPIENT_SAVE_EVENT_EXTERNALIDS" is as expected
    And I should see that the event mapping for "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" exists
    And I should see that the "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" record is produced to DPBI
    Examples:
      | Channel | channelDestination    |
      | Mail    | mailDestination       |
      | Text    | secondTextDestination |
      | Fax     | secondFaxDestination  |
      | Email   | emailDestination      |


  @CP-42186 @CP-42186-03b @API-ECMS @James
  Scenario: Verify POST /correspondences API stores what is passed in recipientInfo/externalIds element for each recipient
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | writtenLanguage  | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | externalCaseIdCHIP              | random            |
      | externalConsumerIdCHIP          | random            |
      | externalCaseIdMedicaid          | random            |
      | externalConsumerIdMedicaid      | random            |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients2                     | newExternalIds    |
      | recipients                      | random            |
      | channel                         | Email             |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    Then I should see that the "/correspondences/{correspondenceid}" end point is showing the externalIds for the "previouslyCreated" Outbound Correspondence Notification for each recipient

  @CP-42186 @CP-42186-04a @API-ECMS @James
  Scenario Outline: Verify POST /correspondences/{correspondenceid}/notifications API stores what is passed in recipientInfo/externalIds element but new recipient record is not created
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole         | Primary Individual    |
      | <channelDestination> | random active address |
      | writtenLanguage      | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | recipients                      | random            |
      | channel                         | <Channel>         |
      | language                        | English           |
      | externalCaseIdCHIP              | random            |
      | externalConsumerIdCHIP          | random            |
      | externalCaseIdMedicaid          | random            |
      | externalConsumerIdMedicaid      | random            |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    When I create a Notification with the "previouslyCreated" new Outbound Correspondence with the following values
      | destination                | NewNotificationDestination |
      | NewNotificationChannel     | <NewNotificationChannel>   |
      | recipient                  | previouslyCreated          |
      | externalCaseIdCHIP         | invalid                    |
      | externalConsumerIdCHIP     | invalid                    |
      | externalCaseIdMedicaid     | invalid                    |
      | externalConsumerIdMedicaid | invalid                    |
    Then I should see that the "/correspondences/{correspondenceid}" end point is showing the externalIds for the "newlyCreatedNotification" Outbound Correspondence Notification
    Examples:
      | Channel | channelDestination    | NewNotificationChannel |
      | Mail    | mailDestination       | Email                  |
      | Mail    | mailDestination       | Text                   |
      | Mail    | mailDestination       | Fax                    |
      | Text    | secondTextDestination | Mail                   |
      | Text    | secondTextDestination | Email                  |
      | Text    | secondTextDestination | Fax                    |
      | Fax     | secondFaxDestination  | Mail                   |
      | Fax     | secondFaxDestination  | Email                  |
      | Fax     | secondFaxDestination  | Text                   |
      | Email   | emailDestination      | Mail                   |
      | Email   | emailDestination      | Fax                    |
      | Email   | emailDestination      | Text                   |

  @CP-42186 @CP-42186-04b @API-ECMS @James
  Scenario Outline: Verify POST /correspondences/{correspondenceid}/notifications API stores what is passed in recipientInfo/externalIds element when a new recipient record is created
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole         | Primary Individual    |
      | <channelDestination> | random active address |
      | writtenLanguage      | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients                      | random            |
      | channel                         | <Channel>         |
      | language                        | English           |
      | externalCaseIdCHIP              | random            |
      | externalConsumerIdCHIP          | random            |
      | externalCaseIdMedicaid          | random            |
      | externalConsumerIdMedicaid      | random            |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    When I create a Notification with the "previouslyCreated" new Outbound Correspondence with the following values
      | destination                           | NewNotificationDestination |
      | NewlyCreatedCorrespondenceRecipientId | true                       |
      | NewNotificationChannel                | <Channel>                  |
      | recipient                             | previouslyCreated          |
      | notificationsExpected                 | 2                          |
      | externalCaseIdCHIP                    | invalid                    |
      | externalConsumerIdCHIP                | invalid                    |
      | externalCaseIdMedicaid                | invalid                    |
      | externalConsumerIdMedicaid            | invalid                    |
    Then I should see that the "/correspondences/{correspondenceid}" end point is showing the externalIds for the "newlyCreatedNotificationAndRecipient" Outbound Correspondence Notification
    Examples:
      | Channel | channelDestination    |
      | Mail    | mailDestination       |
      | Text    | secondTextDestination |
      | Fax     | secondFaxDestination  |
      | Email   | emailDestination      |

  @CP-42186 @CP-42186-05a @API-ECMS @James
  Scenario: Verify recipientInfo/externalIds element is retrieved from /correspondences/{correspondenceid}/notifications API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | writtenLanguage  | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | externalCaseIdCHIP              | random            |
      | externalConsumerIdCHIP          | random            |
      | externalCaseIdMedicaid          | random            |
      | externalConsumerIdMedicaid      | random            |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients                      | random            |
      | channel                         | Email             |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    Then I should see that the "/correspondences/{correspondenceid}/notifications" end point is showing the externalIds for the "previouslyCreated" Outbound Correspondence Notification

  @CP-42186 @CP-42186-05b @API-ECMS @James
  Scenario: Verify recipientInfo/externalIds element is retrieved from /correspondences/{correspondenceid}/notifications API element for each recipient
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | writtenLanguage  | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | externalCaseIdCHIP              | random            |
      | externalConsumerIdCHIP          | random            |
      | externalCaseIdMedicaid          | random            |
      | externalConsumerIdMedicaid      | random            |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients2                     | newExternalIds    |
      | recipients                      | random            |
      | channel                         | Email             |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    Then I should see that the "/correspondences/{correspondenceid}/notifications" end point is showing the externalIds for the "previouslyCreated" Outbound Correspondence Notification for each recipient


  @CP-42186 @CP-42186-06a @API-ECMS @James
  Scenario: Verify Outbound Correspondence recipientInfo/externalIds element is returned by global search correspondence endpoint
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | writtenLanguage  | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | externalCaseIdCHIP              | random            |
      | externalConsumerIdCHIP          | random            |
      | externalCaseIdMedicaid          | random            |
      | externalConsumerIdMedicaid      | random            |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients                      | random            |
      | channel                         | Email             |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    Then I should see that the "/outboundcorrespondence" end point is showing the externalIds for the "previouslyCreated" Outbound Correspondence Notification

  @CP-42186 @CP-42186-06b @API-ECMS @James
  Scenario: Verify Outbound Correspondence recipientInfo/externalIds element is returned by global search correspondence endpoint for each recipient
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | writtenLanguage  | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | externalCaseIdCHIP              | random            |
      | externalConsumerIdCHIP          | random            |
      | externalCaseIdMedicaid          | random            |
      | externalConsumerIdMedicaid      | random            |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients                      | random            |
      | channel                         | Email             |
      | recipients2                     | newExternalIds    |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    Then I should see that the "/outboundcorrespondence" end point is showing the externalIds for the "previouslyCreated" Outbound Correspondence Notification for each recipient


  @CP-42186 @CP-42186-07a @API-ECMS @James
  Scenario: Verify Outbound Correspondence recipientInfo/externalIds element is returned by searchcorrespondence endpoint
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | writtenLanguage  | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | externalCaseIdCHIP              | random            |
      | externalConsumerIdCHIP          | random            |
      | externalCaseIdMedicaid          | random            |
      | externalConsumerIdMedicaid      | random            |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients                      | random            |
      | channel                         | Email             |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    Then I should see that the "/searchcorrespondence" end point is showing the externalIds for the "previouslyCreated" Outbound Correspondence Notification

  @CP-42186 @CP-42186-07b @API-ECMS @James
  Scenario: Verify Outbound Correspondence recipientInfo/externalIds element is returned by searchcorrespondence endpoint for each recipient
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | writtenLanguage  | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | externalCaseIdCHIP              | random            |
      | externalConsumerIdCHIP          | random            |
      | externalCaseIdMedicaid          | random            |
      | externalConsumerIdMedicaid      | random            |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients                      | random            |
      | channel                         | Email             |
      | recipients2                     | newExternalIds    |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    Then I should see that the "/searchcorrespondence" end point is showing the externalIds for the "previouslyCreated" Outbound Correspondence Notification for each recipient

  @CP-42186 @CP-42186-02DCEB @api-ecms-dceb @James
  Scenario: Verify Outbound Correspondence recipientInfo/externalIds element is returned when included in create correspondence bulk insert request
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | allChCase         |
      | digitallyRead                   | true              |
      | language                        | English           |
      | status                          | Requested         |
      | caseId                          | DCEBcaseid        |
      | regardingConsumerId             | previouslyCreated |
      | createdBy                       | 2492              |
      | notificationLanguage            | English           |
      | externalCaseIdCHIP              | random            |
      | externalConsumerIdCHIP          | random            |
      | externalCaseIdMedicaid          | random            |
      | externalConsumerIdMedicaid      | random            |
    And I validate the Bulk Outbound Correspondence is created
    Then I should see that the "/correspondences/{correspondenceid}" end point is showing the externalIds for the "previouslyCreatedBulk" Outbound Correspondence Notification

  @CP-42186 @CP-42186-03aDCEB @api-ecms-dceb @James
  Scenario Outline: Verify POST /correspondences API stores what is passed in recipientInfo/externalIds element
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | recipients                      | random            |
      | channel                         | <Channel>         |
      | language                        | English           |
      | externalCaseIdCHIP              | random            |
      | externalConsumerIdCHIP          | random            |
      | externalCaseIdMedicaid          | random            |
      | externalConsumerIdMedicaid      | random            |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    Then I should see that the "/correspondences/{correspondenceid}" end point is showing the externalIds for the "previouslyCreated" Outbound Correspondence Notification
    Examples:
      | Channel |
      | Mail    |
      | Text    |
      | Fax     |
      | Email   |


  @CP-42186 @CP-42186-03bDCEB @api-ecms-dceb @James
  Scenario: Verify POST /correspondences API stores what is passed in recipientInfo/externalIds element for each recipient
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase                    |
      | externalCaseIdCHIP              | random                       |
      | externalConsumerIdCHIP          | random                       |
      | externalCaseIdMedicaid          | random                       |
      | externalConsumerIdMedicaid      | random                       |
      | caseId                          | previouslyCreated            |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients2                     | newExternalIds               |
      | recipients                      | random                       |
      | channel                         | Email                        |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    Then I should see that the "/correspondences/{correspondenceid}" end point is showing the externalIds for the "previouslyCreated" Outbound Correspondence Notification for each recipient

  @CP-42186 @CP-42186-04aDCEB @api-ecms-dceb @James
  Scenario Outline: Verify POST /correspondences/{correspondenceid}/notifications API stores what is passed in recipientInfo/externalIds element but new recipient record is not created
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | recipients                      | random            |
      | channel                         | <Channel>         |
      | language                        | English           |
      | externalCaseIdCHIP              | random            |
      | externalConsumerIdCHIP          | random            |
      | externalCaseIdMedicaid          | random            |
      | externalConsumerIdMedicaid      | random            |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    When I create a Notification with the "previouslyCreated" new Outbound Correspondence with the following values
      | destination                | NewNotificationDestination |
      | NewNotificationChannel     | <NewNotificationChannel>   |
      | recipient                  | previouslyCreated          |
      | externalCaseIdCHIP         | invalid                    |
      | externalConsumerIdCHIP     | invalid                    |
      | externalCaseIdMedicaid     | invalid                    |
      | externalConsumerIdMedicaid | invalid                    |
    Then I should see that the "/correspondences/{correspondenceid}" end point is showing the externalIds for the "newlyCreatedNotification" Outbound Correspondence Notification
    Examples:
      | Channel | NewNotificationChannel |
      | Mail    | Email                  |
      | Mail    | Text                   |
      | Mail    | Fax                    |
      | Text    | Mail                   |
      | Text    | Email                  |
      | Text    | Fax                    |
      | Fax     | Mail                   |
      | Fax     | Email                  |
      | Fax     | Text                   |
      | Email   | Mail                   |
      | Email   | Fax                    |
      | Email   | Text                   |

  @CP-42186 @CP-42186-04bDCEB @api-ecms-dceb @James
  Scenario Outline: Verify POST /correspondences/{correspondenceid}/notifications API stores what is passed in recipientInfo/externalIds element when a new recipient record is created
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase                    |
      | caseId                          | previouslyCreated            |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients                      | random                       |
      | channel                         | <Channel>                    |
      | language                        | English                      |
      | externalCaseIdCHIP              | random                       |
      | externalConsumerIdCHIP          | random                       |
      | externalCaseIdMedicaid          | random                       |
      | externalConsumerIdMedicaid      | random                       |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    When I create a Notification with the "previouslyCreated" new Outbound Correspondence with the following values
      | destination                           | NewNotificationDestination |
      | NewlyCreatedCorrespondenceRecipientId | true                       |
      | NewNotificationChannel                | <Channel>                  |
      | recipient                             | previouslyCreated          |
      | notificationsExpected                 | 2                          |
      | externalCaseIdCHIP                    | invalid                    |
      | externalConsumerIdCHIP                | invalid                    |
      | externalCaseIdMedicaid                | invalid                    |
      | externalConsumerIdMedicaid            | invalid                    |
    Then I should see that the "/correspondences/{correspondenceid}" end point is showing the externalIds for the "newlyCreatedNotificationAndRecipient" Outbound Correspondence Notification
    Examples:
      | Channel |
      | Mail    |
      | Text    |
      | Fax     |
      | Email   |

  @CP-42186 @CP-42186-05aDCEB @api-ecms-dceb @James
  Scenario: Verify recipientInfo/externalIds element is retrieved from /correspondences/{correspondenceid}/notifications API
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase                    |
      | externalCaseIdCHIP              | random                       |
      | externalConsumerIdCHIP          | random                       |
      | externalCaseIdMedicaid          | random                       |
      | externalConsumerIdMedicaid      | random                       |
      | caseId                          | previouslyCreated            |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients                      | random                       |
      | channel                         | Email                        |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    Then I should see that the "/correspondences/{correspondenceid}/notifications" end point is showing the externalIds for the "previouslyCreated" Outbound Correspondence Notification

  @CP-42186 @CP-42186-05bDCEB @api-ecms-dceb @James
  Scenario: Verify recipientInfo/externalIds element is retrieved from /correspondences/{correspondenceid}/notifications API element for each recipient
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase                    |
      | externalCaseIdCHIP              | random                       |
      | externalConsumerIdCHIP          | random                       |
      | externalCaseIdMedicaid          | random                       |
      | externalConsumerIdMedicaid      | random                       |
      | caseId                          | previouslyCreated            |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients2                     | newExternalIds               |
      | recipients                      | random                       |
      | channel                         | Email                        |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    Then I should see that the "/correspondences/{correspondenceid}/notifications" end point is showing the externalIds for the "previouslyCreated" Outbound Correspondence Notification for each recipient


  @CP-42186 @CP-42186-06aDCEB @api-ecms-dceb @James
  Scenario: Verify Outbound Correspondence recipientInfo/externalIds element is returned by global search correspondence endpoint
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase                    |
      | externalCaseIdCHIP              | random                       |
      | externalConsumerIdCHIP          | random                       |
      | externalCaseIdMedicaid          | random                       |
      | externalConsumerIdMedicaid      | random                       |
      | caseId                          | previouslyCreated            |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients                      | random                       |
      | channel                         | Email                        |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    Then I should see that the "/outboundcorrespondence" end point is showing the externalIds for the "previouslyCreated" Outbound Correspondence Notification

  @CP-42186 @CP-42186-06bDCEB @api-ecms-dceb @James
  Scenario: Verify Outbound Correspondence recipientInfo/externalIds element is returned by global search correspondence endpoint for each recipient
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase                    |
      | externalCaseIdCHIP              | random                       |
      | externalConsumerIdCHIP          | random                       |
      | externalCaseIdMedicaid          | random                       |
      | externalConsumerIdMedicaid      | random                       |
      | caseId                          | previouslyCreated            |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients                      | random                       |
      | channel                         | Email                        |
      | recipients2                     | newExternalIds               |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    Then I should see that the "/outboundcorrespondence" end point is showing the externalIds for the "previouslyCreated" Outbound Correspondence Notification for each recipient


  @CP-42186 @CP-42186-07aDCEB @api-ecms-dceb @James
  Scenario: Verify Outbound Correspondence recipientInfo/externalIds element is returned by searchcorrespondence endpoint
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase                    |
      | externalCaseIdCHIP              | random                       |
      | externalConsumerIdCHIP          | random                       |
      | externalCaseIdMedicaid          | random                       |
      | externalConsumerIdMedicaid      | random                       |
      | caseId                          | previouslyCreated            |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients                      | random                       |
      | channel                         | Email                        |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    Then I should see that the "/searchcorrespondence" end point is showing the externalIds for the "previouslyCreated" Outbound Correspondence Notification

  @CP-42186 @CP-42186-07bDCEB @api-ecms-dceb @James
  Scenario: Verify Outbound Correspondence recipientInfo/externalIds element is returned by searchcorrespondence endpoint for each recipient
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase                    |
      | externalCaseIdCHIP              | random                       |
      | externalConsumerIdCHIP          | random                       |
      | externalCaseIdMedicaid          | random                       |
      | externalConsumerIdMedicaid      | random                       |
      | caseId                          | previouslyCreated            |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients                      | random                       |
      | channel                         | Email                        |
      | recipients2                     | newExternalIds               |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    Then I should see that the "/searchcorrespondence" end point is showing the externalIds for the "previouslyCreated" Outbound Correspondence Notification for each recipient

  @CP-42187 @CP-42187-01a @API-ECMS @James
  Scenario Outline: Verify recipientInfo/externalIds element passed into letter data
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole         | Primary Individual    |
      | <channelDestination> | random active address |
      | writtenLanguage      | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | recipients                      | random            |
      | channel                         | <Channel>         |
      | language                        | English           |
      | externalCaseIdCHIP              | random            |
      | externalConsumerIdCHIP          | random            |
      | externalCaseIdMedicaid          | random            |
      | externalConsumerIdMedicaid      | random            |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    Then I should see that the "previouslyCreated" letter data contains the following values
      | externalCaseIdCHIP         | random |
      | externalConsumerIdCHIP     | random |
      | externalCaseIdMedicaid     | random |
      | externalConsumerIdMedicaid | random |
    And I retrieve the "LETTER_DATA_SAVE_EVENT" event that is produced by trace id
    Then I should see the payload for the "LETTER_DATA_SAVE_EVENT_EXTERNALIDS" is as expected
    And I should see that the event mapping for "LETTER_DATA_SAVE_EVENT" exists
    And I should see that the "LETTER_DATA_SAVE_EVENT" record is produced to DPBI
    Examples:
      | Channel | channelDestination    |
      | Mail    | mailDestination       |
      | Text    | secondTextDestination |
      | Fax     | secondFaxDestination  |
      | Email   | emailDestination      |


  @CP-42187 @CP-42187-01b @API-ECMS @James
  Scenario Outline: Verify recipientInfo/externalIds element passed into letter data for each recipient
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole         | Primary Individual    |
      | <channelDestination> | random active address |
      | writtenLanguage      | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | recipients                      | random            |
      | channel                         | <Channel>         |
      | language                        | English           |
      | externalCaseIdCHIP              | random            |
      | externalConsumerIdCHIP          | random            |
      | externalCaseIdMedicaid          | random            |
      | externalConsumerIdMedicaid      | random            |
      | recipients2                     | newExternalIds    |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    Then I should see that the "previouslyCreatedSecondRecipient" letter data contains the following values
      | externalCaseIdCHIP         | random |
      | externalConsumerIdCHIP     | random |
      | externalCaseIdMedicaid     | random |
      | externalConsumerIdMedicaid | random |
    And I retrieve the "LETTER_DATA_SAVE_EVENT" event that is produced by trace id
    Then I should see the payload for the "LETTER_DATA_SAVE_EVENT_EXTERNALIDS" is as expected
    And I should see that the event mapping for "LETTER_DATA_SAVE_EVENT" exists
    And I should see that the "LETTER_DATA_SAVE_EVENT" record is produced to DPBI
    Examples:
      | Channel | channelDestination    |
      | Mail    | mailDestination       |
      | Text    | secondTextDestination |
      | Fax     | secondFaxDestination  |
      | Email   | emailDestination      |

  @CP-42187 @CP-42187-01aDCEB @api-ecms-dceb @James
  Scenario Outline: Verify recipientInfo/externalIds element passed into letter data for dceb
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase                    |
      | caseId                          | previouslyCreated            |
      | recipients                      | random                       |
      | channel                         | <Channel>                    |
      | language                        | English                      |
      | emailAddress                    | workpcautomation@outlook.com |
      | externalCaseIdCHIP              | random                       |
      | externalConsumerIdCHIP          | random                       |
      | externalCaseIdMedicaid          | random                       |
      | externalConsumerIdMedicaid      | random                       |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    Then I should see that the "previouslyCreated" letter data contains the following values
      | externalCaseIdCHIP         | random |
      | externalConsumerIdCHIP     | random |
      | externalCaseIdMedicaid     | random |
      | externalConsumerIdMedicaid | random |
    And I retrieve the "LETTER_DATA_SAVE_EVENT" event that is produced by trace id
    Then I should see the payload for the "LETTER_DATA_SAVE_EVENT_EXTERNALIDS" is as expected
    And I should see that the event mapping for "LETTER_DATA_SAVE_EVENT" exists
    And I should see that the "LETTER_DATA_SAVE_EVENT" record is produced to DPBI
    Examples:
      | Channel |
      | Mail    |
      | Text    |
      | Email   |


  @CP-42187 @CP-42187-01bDCEBa @api-ecms-dceb @James
  Scenario: Verify recipientInfo/externalIds element passed into letter data for each recipient for dceb for Mail
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase                    |
      | externalCaseIdCHIP              | random                       |
      | externalConsumerIdCHIP          | random                       |
      | externalCaseIdMedicaid          | random                       |
      | externalConsumerIdMedicaid      | random                       |
      | caseId                          | previouslyCreated            |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients2                     | newExternalIds               |
      | recipients                      | random                       |
      | channel                         | Mail                         |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    Then I should see that the "previouslyCreatedSecondRecipient" letter data contains the following values
      | externalCaseIdCHIP         | random |
      | externalConsumerIdCHIP     | random |
      | externalCaseIdMedicaid     | random |
      | externalConsumerIdMedicaid | random |
    And I retrieve the "LETTER_DATA_SAVE_EVENT" event that is produced by trace id
    Then I should see the payload for the "LETTER_DATA_SAVE_EVENT_EXTERNALIDS" is as expected
    And I should see that the event mapping for "LETTER_DATA_SAVE_EVENT" exists
    And I should see that the "LETTER_DATA_SAVE_EVENT" record is produced to DPBI


  @CP-42187 @CP-42187-01bDCEBb @api-ecms-dceb @James
  Scenario: Verify recipientInfo/externalIds element passed into letter data for each recipient for dceb for Text
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase                    |
      | externalCaseIdCHIP              | random                       |
      | externalConsumerIdCHIP          | random                       |
      | externalCaseIdMedicaid          | random                       |
      | externalConsumerIdMedicaid      | random                       |
      | caseId                          | previouslyCreated            |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients2                     | newExternalIds               |
      | recipients                      | random                       |
      | channel                         | Text                         |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    Then I should see that the "previouslyCreatedSecondRecipient" letter data contains the following values
      | externalCaseIdCHIP         | random |
      | externalConsumerIdCHIP     | random |
      | externalCaseIdMedicaid     | random |
      | externalConsumerIdMedicaid | random |
    And I retrieve the "LETTER_DATA_SAVE_EVENT" event that is produced by trace id
    Then I should see the payload for the "LETTER_DATA_SAVE_EVENT_EXTERNALIDS" is as expected
    And I should see that the event mapping for "LETTER_DATA_SAVE_EVENT" exists
    And I should see that the "LETTER_DATA_SAVE_EVENT" record is produced to DPBI

  @CP-42187 @CP-42187-01bDCEBc @api-ecms-dceb @James
  Scenario: Verify recipientInfo/externalIds element passed into letter data for each recipient for dceb for Email
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase                    |
      | externalCaseIdCHIP              | random                       |
      | externalConsumerIdCHIP          | random                       |
      | externalCaseIdMedicaid          | random                       |
      | externalConsumerIdMedicaid      | random                       |
      | caseId                          | previouslyCreated            |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients2                     | newExternalIds               |
      | recipients                      | random                       |
      | channel                         | Email                        |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    Then I should see that the "previouslyCreatedSecondRecipient" letter data contains the following values
      | externalCaseIdCHIP         | random |
      | externalConsumerIdCHIP     | random |
      | externalCaseIdMedicaid     | random |
      | externalConsumerIdMedicaid | random |
    And I retrieve the "LETTER_DATA_SAVE_EVENT" event that is produced by trace id
    Then I should see the payload for the "LETTER_DATA_SAVE_EVENT_EXTERNALIDS" is as expected
    And I should see that the event mapping for "LETTER_DATA_SAVE_EVENT" exists
    And I should see that the "LETTER_DATA_SAVE_EVENT" record is produced to DPBI

  @CP-42187 @CP-42187-02 @API-ECMS @James
  Scenario Outline: Verify POST /correspondences/{correspondenceid}/notifications API stores what is passed in recipientInfo/externalIds element when a new recipient record is created
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole         | Primary Individual    |
      | <channelDestination> | random active address |
      | writtenLanguage      | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | recipients                      | random            |
      | channel                         | <Channel>         |
      | language                        | English           |
      | externalCaseIdCHIP              | random            |
      | externalConsumerIdCHIP          | random            |
      | externalCaseIdMedicaid          | random            |
      | externalConsumerIdMedicaid      | random            |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    When I create a Notification with the "previouslyCreated" new Outbound Correspondence with the following values
      | destination                           | NewNotificationDestination |
      | NewlyCreatedCorrespondenceRecipientId | true                       |
      | NewNotificationChannel                | <Channel>                  |
      | recipient                             | previouslyCreated          |
      | notificationsExpected                 | 2                          |
      | externalCaseIdCHIP                    | invalid                    |
      | externalConsumerIdCHIP                | invalid                    |
      | externalCaseIdMedicaid                | invalid                    |
      | externalConsumerIdMedicaid            | invalid                    |
    Then I should see that the "newlyCreatedRecipient" letter data contains the following values
      | externalCaseIdCHIP         | random    |
      | externalConsumerIdCHIP     | random    |
      | externalCaseIdMedicaid     | random    |
      | externalConsumerIdMedicaid | random    |
      | NewNotificationChannel     | <Channel> |
    And I retrieve the "LETTER_DATA_SAVE_EVENT" event that is produced by trace id
    Then I should see the payload for the "LETTER_DATA_SAVE_EVENT_EXTERNALIDS" is as expected
    And I should see that the event mapping for "LETTER_DATA_SAVE_EVENT" exists
    And I should see that the "LETTER_DATA_SAVE_EVENT" record is produced to DPBI
    Examples:
      | Channel | channelDestination    |
      | Mail    | mailDestination       |
      | Text    | secondTextDestination |
      | Fax     | secondFaxDestination  |
      | Email   | emailDestination      |

  @CP-42187 @CP-42187-02DCEB @api-ecms-dceb @James
  Scenario Outline: Verify POST /correspondences/{correspondenceid}/notifications API stores what is passed in recipientInfo/externalIds element when a new recipient record is created for dceb
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase                    |
      | caseId                          | previouslyCreated            |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients                      | random                       |
      | channel                         | <Channel>                    |
      | language                        | English                      |
      | externalCaseIdCHIP              | random                       |
      | externalConsumerIdCHIP          | random                       |
      | externalCaseIdMedicaid          | random                       |
      | externalConsumerIdMedicaid      | random                       |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    When I create a Notification with the "previouslyCreated" new Outbound Correspondence with the following values
      | destination                           | NewNotificationDestination |
      | NewlyCreatedCorrespondenceRecipientId | true                       |
      | NewNotificationChannel                | <Channel>                  |
      | recipient                             | previouslyCreated          |
      | notificationsExpected                 | 2                          |
      | externalCaseIdCHIP                    | invalid                    |
      | externalConsumerIdCHIP                | invalid                    |
      | externalCaseIdMedicaid                | invalid                    |
      | externalConsumerIdMedicaid            | invalid                    |
    Then I should see that the "newlyCreatedRecipient" letter data contains the following values
      | externalCaseIdCHIP         | random |
      | externalConsumerIdCHIP     | random |
      | externalCaseIdMedicaid     | random |
      | externalConsumerIdMedicaid | random |
    And I retrieve the "LETTER_DATA_SAVE_EVENT" event that is produced by trace id
    Then I should see the payload for the "LETTER_DATA_SAVE_EVENT_EXTERNALIDS" is as expected
    And I should see that the event mapping for "LETTER_DATA_SAVE_EVENT" exists
    And I should see that the "LETTER_DATA_SAVE_EVENT" record is produced to DPBI
    Examples:
      | Channel |
      | Mail    |
      | Text    |
      | Email   |