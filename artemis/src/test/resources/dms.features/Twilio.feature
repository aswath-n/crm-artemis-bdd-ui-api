Feature: Send SMS Request to Twilio

  @CP-37733 @CP-37733-1 @CP-39968 @API-ECMS @Keerthi
  Scenario: Verify Process SMS Delivered Response from Twilio (BLCRM)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | textDestination | random active address |
      | writtenLanguage | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | twilio            |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | textNumber                      | Twilio            |
      | recipients                      | random            |
      | channel                         | Text              |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And The Text Notification that was created is sent to TwilioEvent End Point
      | notificationId | previouslyCreated |
      | projectName    | BLCRM             |
      | MessageStatus  | delivered         |
      | ErrorCode      | 3000              |
      | ErrorMessage   | Test              |
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status | Sent |
    Then I verify the following values in the correspondence notification response
      | Status            | Sent                  |
      | Updated By        | ECMS Service          |
      | Updated Date      | current date and hour |
      | objectReceivedOn  | current date and hour |
      | statusChangedDate | current date and hour |
      | changedBy         | ECMS Service          |
    And I copied the following nids
      | notificationId | previouslyCreated |
    And I should see that following records are produced to DPBI
      | NOTIFICATION_UPDATE_EVENT |


  ################################### CP-37734 ###################################

  @CP-37734 @CP-37734-1 @CP-39968 @API-ECMS @Keerthi
  Scenario Outline: Verify Process SMS Failed Response from Twilio for 'Failed' MessageStatus (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | textDestination | random active address |
      | writtenLanguage | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | twilio            |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | textNumber                      | Twilio            |
      | recipients                      | random            |
      | channel                         | Text              |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And The Text Notification that was created is sent to TwilioEvent End Point
      | notificationId | previouslyCreated |
      | projectName    | <projectName>     |
      | MessageStatus  | <MessageStatus>   |
      | ErrorCode      | <ErrorCode>       |
      | ErrorMessage   | <ErrorMessage>    |
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status        | Returned                   |
      | Status Reason | <NotificationStatusReason> |
    Then I verify the following values in the correspondence notification response
      | Status            | Returned                   |
      | Status Reason     | <NotificationStatusReason> |
      | changedBy         | ECMS Service               |
      | statusChangedDate | current date and hour      |
      | Updated By        | ECMS Service               |
      | Updated Date      | current date and hour      |
      | objectReceivedOn  | null                       |
      | ErrorDetail       | <ErrorMessage>             |
    And I copied the following nids
      | notificationId | previouslyCreated |
    And I should see that following records are produced to DPBI
      | NOTIFICATION_UPDATE_EVENT |
    Examples:
      | projectName | MessageStatus | ErrorCode | NotificationStatusReason           | ErrorMessage |
      | BLCRM       | failed        | 30003     | Refused                            | 1234         |
      | BLCRM       | failed        | 30004     | Refused                            | test         |
      | BLCRM       | failed        | 30005     | Refused                            | !@#@$#$      |
      | BLCRM       | failed        | 30006     | Refused                            | 123RTid      |
      | BLCRM       | failed        | test300   | Destination Agent Rejected Message | test 123     |

  @CP-37734 @CP-37734-2 @CP-39968 @API-ECMS @Keerthi
  Scenario Outline: Verify Process SMS Failed Response from Twilio for 'undelivered' MessageStatus (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | textDestination | random active address |
      | writtenLanguage | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | twilio            |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | textNumber                      | Twilio            |
      | recipients                      | random            |
      | channel                         | Text              |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And The Text Notification that was created is sent to TwilioEvent End Point
      | notificationId | previouslyCreated |
      | projectName    | <projectName>     |
      | MessageStatus  | <MessageStatus>   |
      | ErrorCode      | <ErrorCode>       |
      | ErrorMessage   | <ErrorMessage>    |
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status        | Returned                   |
      | Status Reason | <NotificationStatusReason> |
    Then I verify the following values in the correspondence notification response
      | Status            | Returned                   |
      | Status Reason     | <NotificationStatusReason> |
      | changedBy         | ECMS Service               |
      | statusChangedDate | current date and hour      |
      | Updated By        | ECMS Service               |
      | Updated Date      | current date and hour      |
      | objectReceivedOn  | null                       |
      | ErrorDetail       | <ErrorMessage>             |
    And I copied the following nids
      | notificationId | previouslyCreated |
    And I should see that following records are produced to DPBI
      | NOTIFICATION_UPDATE_EVENT |
    Examples:
      | projectName | MessageStatus | ErrorCode | NotificationStatusReason           | ErrorMessage |
      | BLCRM       | undelivered   | 30003     | Refused                            | 1234         |
      | BLCRM       | undelivered   | 30004     | Refused                            | test         |
      | BLCRM       | undelivered   | 30005     | Refused                            | !@#@$#$      |
      | BLCRM       | undelivered   | 30006     | Refused                            | 123RTid      |
      | BLCRM       | undelivered   | test300   | Destination Agent Rejected Message | test 123     |

    ############################################## CP-37571 ############################################################

  @CP-37571 @CP-37571-1 @ui-ecms1 @James
  Scenario: Verify text notification is updated properly when sent to Twilio for fulfillment
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I retrieve a phone number that will receive a sms text from "receiveSMS"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual |
      | textDestination | receiveSMS         |
      | writtenLanguage | English            |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | textOnly          |
      | caseId                          | previouslyCreated |
      | textNumber                      | receiveSMS        |
      | recipients                      | random            |
      | channel                         | Text              |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And the Text Notification that was "previouslyCreated" is sent to Twilio end point
    Then I should see that Twilio Response was successful
#    And I should see the text message was received by "receiveSMS" from sender phone number "13605449115"
    And I should see that the text Notification was updated to "Exported" Successfully

  @CP-37571 @CP-37571-2 @ui-ecms1 @James
  Scenario: Verify text notification is updated properly when multiple notifications
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I retrieve a phone number that will receive a sms text from "receiveSMS"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual            |
      | textDestination | receiveSMS                    |
      | mailDestination | random active mailing address |
      | writtenLanguage | English                       |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | textPlus          |
      | caseId                          | previouslyCreated |
      | textNumber                      | receiveSMS        |
      | recipients                      | random            |
      | channel                         | Text              |
      | channel2                        | Mail              |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And the Text Notification that was "previouslyCreated" is sent to Twilio end point
    Then I should see that Twilio Response was successful
#    And I should see the text message was received by "receiveSMS" from sender phone number "13605449115"
    And I should see that the text Notification was updated to "Exported" Successfully
    And I should see the status "Exported" has cascaded to the Correspondence level

  @CP-37571 @CP-37571-3 @API-ECMS @James
  Scenario: Verify text notification is updated to Error when using a bad template
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I retrieve a phone number that will receive a sms text from "receiveSMS"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual |
      | textDestination | receiveSMS         |
      | writtenLanguage | English            |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | textBad           |
      | caseId                          | previouslyCreated |
      | textNumber                      | receiveSMS        |
      | recipients                      | random            |
      | channel                         | Text              |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And the Text Notification that was "previouslyCreated" is sent to Twilio end point
    Then I should see that Twilio Response has returned an Error
    And I should see that the text Notification was updated to "Error" Successfully

  @CP-37571 @CP-37571-4 @API-ECMS @James
  Scenario: Verify text notification is updated to Error when using a bad template with multiple notifications
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I retrieve a phone number that will receive a sms text from "receiveSMS"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual            |
      | textDestination | receiveSMS                    |
      | mailDestination | random active mailing address |
      | writtenLanguage | English                       |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | textBad2          |
      | caseId                          | previouslyCreated |
      | textNumber                      | receiveSMS        |
      | recipients                      | random            |
      | channel                         | Text              |
      | channel2                        | Mail              |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And the Text Notification that was "previouslyCreated" is sent to Twilio end point
    Then I should see that Twilio Response has returned an Error
    And I should see that the text Notification was updated to "Error" Successfully
    And I should see the status "Error" has cascaded to the Correspondence level
    And I should see the status reason "Export Error" has cascaded the the Correspondence level

      ################################### CP-38716 & CP-37572 ###################################

  @CP-38716 @CP-38716-01 @CP-37572 @CP-37572-01  @ui-ecms2 @burak
  Scenario: Verify Retrieval of Twilio Template on OB Search by clicking the View Correspondence Image , Verify able to see 1600 character Template
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | textDestination | random active address |
      | writtenLanguage | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | 1600TextC         |
      | caseId                          | previouslyCreated |
      | textNumber                      | Twilio            |
      | recipients                      | random            |
      | channel                         | Text              |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And The Text Notification that was created is sent to TwilioEvent End Point
      | notificationId | previouslyCreated |
      | projectName    | BLCRM             |
      | MessageStatus  | delivered         |
      | ErrorCode      | 3000              |
      | ErrorMessage   | Test              |
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    And I click on the view icon for created OB correspondence
    And I switch to opened window
    And I should see the Twilio notification is viewed as "1600"

  @CP-37572 @CP-37572-02  @ui-ecms2 @burak
  Scenario: Verify Retrieval of Twilio Template on OB Search by clicking the View Notification Image
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | textDestination | random active address |
      | writtenLanguage | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | textPlus          |
      | caseId                          | previouslyCreated |
      | textNumber                      | Twilio            |
      | recipients                      | random            |
      | channel                         | Text              |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And The Text Notification that was created is sent to TwilioEvent End Point
      | notificationId | previouslyCreated |
      | projectName    | BLCRM             |
      | MessageStatus  | delivered         |
      | ErrorCode      | 3000              |
      | ErrorMessage   | Test              |
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    And I select the correspondence by "CREATEDFROMAPI" and select the document by "CREATEDFROMAPI"
    And I should see the Twilio notification is viewed as "this is a test twilio template"

  @CP-37572 @CP-37572-03  @ui-ecms2 @burak
  Scenario: Verify Retrieval of Twilio Template on OB Details by clicking the View Notification Image
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | textDestination | random active address |
      | writtenLanguage | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | TextOnly          |
      | caseId                          | previouslyCreated |
      | textNumber                      | Twilio            |
      | recipients                      | random            |
      | channel                         | Text              |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And The Text Notification that was created is sent to TwilioEvent End Point
      | notificationId | previouslyCreated |
      | projectName    | BLCRM             |
      | MessageStatus  | delivered         |
      | ErrorCode      | 3000              |
      | ErrorMessage   | Test              |
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I click on the view icon for created OB correspondence
    And I switch to opened window
    And I should see the Twilio notification is viewed as "this is a test twilio template"

  @CP-37572 @CP-37572-04  @ui-ecms2 @burak
  Scenario: Verify Retrieval of Twilio Template on Case Contact Details by clicking the View Correspondence Image (Consumer Profile)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer not on a case that wants to send an Outbound Correspondence
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | textConTw          |
      | regardingConsumerId             | consumerNotOnACase |
      | textNumber                      | random             |
      | recipients                      | random             |
      | channel                         | Text               |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And The Text Notification that was created is sent to TwilioEvent End Point
      | notificationId | previouslyCreated |
      | projectName    | BLCRM             |
      | MessageStatus  | delivered         |
      | ErrorCode      | 3000              |
      | ErrorMessage   | Test              |
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Case Contact Details Tab
    And I click on the view icon for the correspondence "CREATEDFROMAPI"
    And I should see the Twilio notification is viewed as "this is a test twilio template"

  @CP-37572 @CP-37572-05  @ui-ecms2 @burak
  Scenario: Verify Retrieval of Twilio Template on Case Contact Details by clicking the View Notification Image (Consumer Profile)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer not on a case that wants to send an Outbound Correspondence
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | textConTw          |
      | regardingConsumerId             | consumerNotOnACase |
      | textNumber                      | random             |
      | recipients                      | random             |
      | channel                         | Text               |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And The Text Notification that was created is sent to TwilioEvent End Point
      | notificationId | previouslyCreated |
      | projectName    | BLCRM             |
      | MessageStatus  | delivered         |
      | ErrorCode      | 3000              |
      | ErrorMessage   | Test              |
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Case Contact Details Tab
    And I select the correspondence by "CREATEDFROMAPI" and select the document by "CREATEDFROMAPI"
    And I should see the Twilio notification is viewed as "this is a test twilio template"

  @CP-37572 @CP-37572-06  @ui-ecms2 @burak
  Scenario: Verify Retrieval of Twilio Template on Case Contact Details by clicking the View Correspondence Image (Case Profile)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | textDestination | random active address |
      | writtenLanguage | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | TextOnly          |
      | caseId                          | previouslyCreated |
      | textNumber                      | Twilio            |
      | recipients                      | random            |
      | channel                         | Text              |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And The Text Notification that was created is sent to TwilioEvent End Point
      | notificationId | previouslyCreated |
      | projectName    | BLCRM             |
      | MessageStatus  | delivered         |
      | ErrorCode      | 3000              |
      | ErrorMessage   | Test              |
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched case and consumer created by api
    And I click on the first case and consumer search result
    When I navigate to the case and contact details tab
    And I click on the view icon for the correspondence "CREATEDFROMAPI"
    And I should see the Twilio notification is viewed as "this is a test twilio template"

  @CP-37572 @CP-37572-07 @ui-ecms2 @burak
  Scenario: Verify Retrieval of Twilio Template on Case Contact Details by clicking the View Notification Image (Case Profile)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | textDestination | random active address |
      | writtenLanguage | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | TextOnly          |
      | caseId                          | previouslyCreated |
      | textNumber                      | Twilio            |
      | recipients                      | random            |
      | channel                         | Text              |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And The Text Notification that was created is sent to TwilioEvent End Point
      | notificationId | previouslyCreated |
      | projectName    | BLCRM             |
      | MessageStatus  | delivered         |
      | ErrorCode      | 3000              |
      | ErrorMessage   | Test              |
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched case and consumer created by api
    And I click on the first case and consumer search result
    When I navigate to the case and contact details tab
    And I select the correspondence by "CREATEDFROMAPI" and select the document by "CREATEDFROMAPI"
    And I should see the Twilio notification is viewed as "this is a test twilio template"

  @CP-37572 @CP-37572-08  @ui-ecms2 @burak
  Scenario: Verify Retrieval of Twilio Template on IB Correspondence by clicking the View Notification Image
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | textDestination | random active address |
      | writtenLanguage | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | TextPlus2         |
      | caseId                          | previouslyCreated |
      | textNumber                      | Twilio            |
      | recipients                      | random            |
      | channel                         | Text              |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And The Text Notification that was created is sent to TwilioEvent End Point
      | notificationId | previouslyCreated |
      | projectName    | BLCRM             |
      | MessageStatus  | delivered         |
      | ErrorCode      | 3000              |
      | ErrorMessage   | Test              |
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
    And I click on "SECOND" Inbound Correspondence application pdf view icon
    And I switch to opened window
    And I should see the Twilio notification is viewed as "this is a test twilio template"

  @CP-38716 @CP-38716-02  @ui-ecms2 @burak
  Scenario: Verify Process SMS Delivered Response from Twilio Template bad Template
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | textDestination | random active address |
      | writtenLanguage | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | badTextTem        |
      | caseId                          | previouslyCreated |
      | textNumber                      | Twilio            |
      | recipients                      | random            |
      | channel                         | Text              |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I see that a Document has been uploaded to Onbase for the "previouslyCreated" Outbound Correspondence Notification
    And I receive an update to the "previouslyCreated" Outbound Correspondence Notification that it is read to be viewed
    And I verify I get 404 error while viewing the Twilio Template

  @CP-38577 @CP-38577-09 @UI-ECMS @ECMS-UI-REGRESSION @burak
  Scenario: Verify Text Notification is queued and sent after updating Mail notification
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I retrieve a phone number that will receive a sms text from "receiveSMS"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | textDestination | receiveSMS            |
      | writtenLanguage | English               |
      | mailDestination | random active address |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | nowAvail1         |
      | caseId                          | previouslyCreated |
      | textNumber                      | receiveSMS        |
      | recipients                      | random            |
      | channel                         | Text,Mail         |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I see that a Document has been uploaded to Onbase for the "Mail" Outbound Correspondence Notification
    And I receive an update to the "previouslyCreated" Outbound Correspondence Notification that it is read to be viewed
#    And I should see the text message was received by "receiveSMS" from sender phone number "13605449115"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status       | Exported              |
      | Updated By   | 198                   |
      | Updated Date | current date and hour |
    And I copied the following nids
      | notificationId | Text |
    And I should see that following records are produced to DPBI
      | NOTIFICATION_UPDATE_EVENT |

  @CP-38577 @CP-38577-10 @UI-ECMS @ECMS-UI-REGRESSION @burak
  Scenario: Verify Text Notification is queued and sent after updating Mail notification for Different Language
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I retrieve a phone number that will receive a sms text from "receiveSMS"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | textDestination | receiveSMS            |
      | writtenLanguage | Spanish               |
      | mailDestination | random active address |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | nowAvail1         |
      | caseId                          | previouslyCreated |
      | textNumber                      | receiveSMS        |
      | recipients                      | random            |
      | channel                         | Text,Mail         |
      | language                        | Spanish           |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I see that a Document has been uploaded to Onbase for the "Mail" Outbound Correspondence Notification
    And I receive an update to the "previouslyCreated" Outbound Correspondence Notification that it is read to be viewed
#    And I should see the text message was received by "receiveSMS" from sender phone number "13605449115"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status       | Exported              |
      | Updated By   | 198                   |
      | Updated Date | current date and hour |
    And I copied the following nids
      | notificationId | Text |
    And I should see that following records are produced to DPBI
      | NOTIFICATION_UPDATE_EVENT |

  @CP-38577 @CP-38577-11 @UI-ECMS @ECMS-UI-REGRESSION @burak
  Scenario: Verify Text Notification is not queued and sent after updating Mail notification when Text Notification is Advanced Notice
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I retrieve a phone number that will receive a sms text from "receiveSMS"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | textDestination | receiveSMS            |
      | writtenLanguage | English               |
      | mailDestination | random active address |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | nowAvail2         |
      | caseId                          | previouslyCreated |
      | textNumber                      | receiveSMS        |
      | recipients                      | random            |
      | channel                         | Text,Mail         |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I see that a Document has been uploaded to Onbase for the "Mail" Outbound Correspondence Notification
    And I receive an update to the "previouslyCreated" Outbound Correspondence Notification that it is read to be viewed
    And I should see the text message is not received by "receiveSMS"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status       | Requested             |
      | Updated By   | 2492                  |
      | Updated Date | current date and hour |