Feature: Send Email Request to SendGrid

  @CP-36200 @CP-36200-1 @API-ECMS @James
  Scenario: Verify email notification is updated properly and sender email is taken from Ob Settings
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
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
    And the Email Notification that was created is sent to Send Grid End Point
    Then I should see that Send Grid Response was successful
    And I should see that the Email Notification was updated to "Exported" Successfully
    And I verify that the Email message was received from Send Grid for "workpcautomation@outlook.com" address
    And I verify the sender email address is taken from Outbound Correspondence Settings

  @CP-36200 @CP-36200-2 @API-ECMS @James
  Scenario: Verify email notification is updated properly and sender email is taken from channel definition
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | writtenLanguage  | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | emailOnly2        |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients                      | random            |
      | channel                         | Email             |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And the Email Notification that was created is sent to Send Grid End Point
    Then I should see that Send Grid Response was successful
    And I should see that the Email Notification was updated to "Exported" Successfully
    And I verify that the Email message was received from Send Grid for "workpcautomation@outlook.com" address
    And I verify the sender email address is taken from Outbound Correspondence Channel Definition

  @CP-36200 @CP-36200-3 @API-ECMS @James
  Scenario: Verify email notification is updated to Error when using a bad template
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | writtenLanguage  | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | badTemplat        |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients                      | random            |
      | channel                         | Email             |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And the Email Notification that was created is sent to Send Grid End Point
    Then I should see that Send Grid Response has returned an Error
    And I should see that the Email Notification was updated to "Error" Successfully

      ######################################### CP-36199 & CP-38783 ######################################################

  @CP-36199 @CP-36199-01 @CP-38783 @CP-38783-01 @ui-ecms1 @burak
  Scenario Outline: Verify Retrieval of SendGrid Template on OB Search by clicking the View Correspondence Image
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | writtenLanguage  | <Language>            |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | emailOnly         |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients                      | random            |
      | channel                         | Email             |
      | language                        | <Language>        |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And the Email Notification that was created is sent to Send Grid End Point
    Then I should see that Send Grid Response was successful
    And I should see that the Email Notification was updated to "Exported" Successfully
    And The Email Notification that was created is sent to SendGridEvent End Point
      | event          | delivered          |
      | reason         | notificationreason |
      | type           | notificationtype   |
      | notificationId | previouslyCreated  |
      | projectName    | BLCRM              |
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    And I click on the view icon for created OB correspondence
    And I switch to opened window
    Then I verify the header of the correspondence image as "<Message>"
    Then I verify body of the correspondence image contains "<Body>"
    Examples:
      | Language | Message                           | Body                                                                                |
      | English  | Subject: Thank you for signing up | Thank you for signing up to receive email notifications from BLCRM                  |
      | Spanish  | Subject: Gracias por registrarse  | Gracias por registrarse para recibir notificaciones por correo electrónico de BLCRM |

  @CP-36199 @CP-36199-02 @CP-38783 @CP-38783-02 @ui-ecms1 @burak
  Scenario Outline: Verify Retrieval of SendGrid Template on OB Search by clicking the View Notification Image
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | writtenLanguage  | <Language>            |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | emailOnly         |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients                      | random            |
      | channel                         | Email             |
      | language                        | <Language>        |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And the Email Notification that was created is sent to Send Grid End Point
    Then I should see that Send Grid Response was successful
    And I should see that the Email Notification was updated to "Exported" Successfully
    And The Email Notification that was created is sent to SendGridEvent End Point
      | event          | delivered          |
      | reason         | notificationreason |
      | type           | notificationtype   |
      | notificationId | previouslyCreated  |
      | projectName    | BLCRM              |
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    And I select the correspondence by "CREATEDFROMAPI" and select the document by "CREATEDFROMAPI"
    Then I verify the header of the correspondence image as "<Message>"
    Then I verify body of the correspondence image contains "<Body>"
    Examples:
      | Language | Message                           | Body                                                                                |
      | English  | Subject: Thank you for signing up | Thank you for signing up to receive email notifications from BLCRM                  |
      | Spanish  | Subject: Gracias por registrarse  | Gracias por registrarse para recibir notificaciones por correo electrónico de BLCRM |

  @CP-36199 @CP-36199-03 @CP-38783 @CP-38783-03 @ui-ecms1 @burak
  Scenario: Verify "Image unavailable. External Template not found. Displays" when There is no SendGrid Template found on OB Search
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | writtenLanguage  | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | badTemplat        |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients                      | random            |
      | channel                         | Email             |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I see that a Document has been uploaded to Onbase for the "previouslyCreated" Outbound Correspondence Notification
    And I receive an update to the "previouslyCreated" Outbound Correspondence Notification that it is read to be viewed
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    And I click on the view icon for created OB correspondence
    And I switch to opened window
    Then I verify body of the correspondence image contains "Image unavailable. External Template not found."

  @CP-38783 @CP-38783-04 @ui-ecms1 @burak
  Scenario Outline: Verify Retrieval of SendGrid Template on OB Details by clicking the View Notification Image
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | writtenLanguage  | <Language>            |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | emailOnly         |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients                      | random            |
      | channel                         | Email             |
      | language                        | <Language>        |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And the Email Notification that was created is sent to Send Grid End Point
    Then I should see that Send Grid Response was successful
    And I should see that the Email Notification was updated to "Exported" Successfully
    And The Email Notification that was created is sent to SendGridEvent End Point
      | event          | delivered          |
      | reason         | notificationreason |
      | type           | notificationtype   |
      | notificationId | previouslyCreated  |
      | projectName    | BLCRM              |
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I click on the view icon for created OB correspondence
    And I switch to opened window
    Then I verify the header of the correspondence image as "<Message>"
    Then I verify body of the correspondence image contains "<Body>"
    Examples:
      | Language | Message                           | Body                                                                                |
      | English  | Subject: Thank you for signing up | Thank you for signing up to receive email notifications from BLCRM                  |
      | Spanish  | Subject: Gracias por registrarse  | Gracias por registrarse para recibir notificaciones por correo electrónico de BLCRM |

  @CP-38783 @CP-38783-05 @ui-ecms1 @burak
  Scenario: Verify Retrieval of SendGrid Template on Case Contact Details by clicking the View Correspondence Image (Consumer Profile)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
    And I have a consumer not on a case that wants to send an Outbound Correspondence
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | emailConSG         |
      | regardingConsumerId             | consumerNotOnACase |
      | emailAddress                    | sendGrid           |
      | recipients                      | random             |
      | channel                         | Email              |
      | language                        | English            |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And the Email Notification that was created is sent to Send Grid End Point
    Then I should see that Send Grid Response was successful
    And I should see that the Email Notification was updated to "Exported" Successfully
    And The Email Notification that was created is sent to SendGridEvent End Point
      | event          | delivered          |
      | reason         | notificationreason |
      | type           | notificationtype   |
      | notificationId | previouslyCreated  |
      | projectName    | BLCRM              |
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Case Contact Details Tab
    And I click on the view icon for the correspondence "CREATEDFROMAPI"
    Then I verify the header of the correspondence image as "Subject: Thank you for signing up"
    Then I verify body of the correspondence image contains "Thank you for signing up to receive email notifications from BLCRM"

  @CP-38783 @CP-38783-06 @ui-ecms1 @burak
  Scenario: Verify Retrieval of SendGrid Template on Case Contact Details by clicking the View Notification Image (Consumer Profile)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
    And I have a consumer not on a case that wants to send an Outbound Correspondence
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | emailConSG         |
      | regardingConsumerId             | consumerNotOnACase |
      | emailAddress                    | sendGrid           |
      | recipients                      | random             |
      | channel                         | Email              |
      | language                        | English            |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And the Email Notification that was created is sent to Send Grid End Point
    Then I should see that Send Grid Response was successful
    And I should see that the Email Notification was updated to "Exported" Successfully
    And The Email Notification that was created is sent to SendGridEvent End Point
      | event          | delivered          |
      | reason         | notificationreason |
      | type           | notificationtype   |
      | notificationId | previouslyCreated  |
      | projectName    | BLCRM              |
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Case Contact Details Tab
    And I select the correspondence by "CREATEDFROMAPI" and select the document by "CREATEDFROMAPI"
    Then I verify the header of the correspondence image as "Subject: Thank you for signing up"
    Then I verify body of the correspondence image contains "Thank you for signing up to receive email notifications from BLCRM"

  @CP-38783 @CP-38783-07 @ui-ecms1 @burak
  Scenario: Verify Retrieval of SendGrid Template on Case Contact Details by clicking the View Correspondence Image (Case Profile)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
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
      | language                        | English           |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And the Email Notification that was created is sent to Send Grid End Point
    Then I should see that Send Grid Response was successful
    And I should see that the Email Notification was updated to "Exported" Successfully
    And The Email Notification that was created is sent to SendGridEvent End Point
      | event          | delivered          |
      | reason         | notificationreason |
      | type           | notificationtype   |
      | notificationId | previouslyCreated  |
      | projectName    | BLCRM              |
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I click on the view icon for the correspondence "CREATEDFROMAPI"
    Then I verify the header of the correspondence image as "Subject: Thank you for signing up"
    Then I verify body of the correspondence image contains "Thank you for signing up to receive email notifications from BLCRM"

  @CP-38783 @CP-38783-08 @ui-ecms1 @burak
  Scenario: Verify Retrieval of SendGrid Template on Case Contact Details by clicking the View Notification Image (Case Profile)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
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
      | language                        | English           |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And the Email Notification that was created is sent to Send Grid End Point
    Then I should see that Send Grid Response was successful
    And I should see that the Email Notification was updated to "Exported" Successfully
    And The Email Notification that was created is sent to SendGridEvent End Point
      | event          | delivered          |
      | reason         | notificationreason |
      | type           | notificationtype   |
      | notificationId | previouslyCreated  |
      | projectName    | BLCRM              |
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I select the correspondence by "CREATEDFROMAPI" and select the document by "CREATEDFROMAPI"
    Then I verify the header of the correspondence image as "Subject: Thank you for signing up"
    Then I verify body of the correspondence image contains "Thank you for signing up to receive email notifications from BLCRM"

  @CP-38783 @CP-38783-09 @ui-ecms1 @burak
  Scenario: Verify Retrieval of SendGrid Template on IB Correspondence by clicking the View Notification Image
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
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
      | language                        | English           |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And the Email Notification that was created is sent to Send Grid End Point
    Then I should see that Send Grid Response was successful
    And I should see that the Email Notification was updated to "Exported" Successfully
    And The Email Notification that was created is sent to SendGridEvent End Point
      | event          | delivered          |
      | reason         | notificationreason |
      | type           | notificationtype   |
      | notificationId | previouslyCreated  |
      | projectName    | BLCRM              |
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
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | InboundDocument |
    And I click on a CID of "first available" Inbound Search Results
    And I click on "SECOND" Inbound Correspondence application pdf view icon
    And I switch to opened window
    Then I verify the header of the correspondence image as "Subject: Thank you for signing up"
    Then I verify body of the correspondence image contains "Thank you for signing up to receive email notifications from BLCRM"

    ######################################################################CP-38577#####################################################################################

  @CP-38557 @CP-38557-01 @API-ECMS @burak
  Scenario: Verify Email Notification is queued and sent after updating Mail notification
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | mailDestination  | random active address |
      | writtenLanguage  | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | nowAvail1         |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients                      | random            |
      | channel                         | Email,Mail        |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I see that a Document has been uploaded to Onbase for the "Mail" Outbound Correspondence Notification
    And I receive an update to the "previouslyCreated" Outbound Correspondence Notification that it is read to be viewed
    And I verify that the Email message was received from Send Grid for "workpcautomation@outlook.com" address
    And I verify the sender email address is taken from Outbound Correspondence Settings
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status       | Exported              |
      | Updated By   | ECMS Service          |
      | Updated Date | current date and hour |
    And I copied the following nids
      | notificationId | Email |
    And I should see that following records are produced to DPBI
      | NOTIFICATION_UPDATE_EVENT |

  @CP-38557 @CP-38557-02 @API-ECMS @burak
  Scenario: Verify Email Notification is not queued and sent after updating Mail notification when Notification Purpose is not Now Available
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | mailDestination  | random active address |
      | writtenLanguage  | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | nowAvail3a        |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients                      | random            |
      | channel                         | Email,Mail        |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I see that a Document has been uploaded to Onbase for the "Mail" Outbound Correspondence Notification
    And I receive an update to the "previouslyCreated" Outbound Correspondence Notification that it is read to be viewed
    And I verify that the Email message not received from Send Grid for "workpcautomation@outlook.com"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status       | Requested             |
      | Updated By   | 2492                  |
      | Updated Date | current date and hour |

  @CP-38557 @CP-38557-03 @API-ECMS @burak
  Scenario: Verify Email Notification is queued and sent after updating Mail notification for different Language
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | mailDestination  | random active address |
      | writtenLanguage  | Spanish               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | nowAvail1         |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients                      | random            |
      | channel                         | Email,Mail        |
      | language                        | Spanish           |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I see that a Document has been uploaded to Onbase for the "Mail" Outbound Correspondence Notification
    And I receive an update to the "previouslyCreated" Outbound Correspondence Notification that it is read to be viewed
    And I verify that the Email message was received from Send Grid for "workpcautomation@outlook.com" address
    And I verify the sender email address is taken from Outbound Correspondence Settings
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status       | Exported              |
      | Updated By   | ECMS Service          |
      | Updated Date | current date and hour |
    And I copied the following nids
      | notificationId | mail |
    And I should see that following records are produced to DPBI
      | NOTIFICATION_UPDATE_EVENT |

  @CP-38557 @CP-38557-04 @API-ECMS @burak
  Scenario: Verify Email Notification is queued and sent after updating Mail notification for 2 Recipients
      # Verify Email Notification is not queued after updating Mail notification for 1st Recipient
      # Verify Email Notification is  queued and sent  after updating Mail notification for 2nd Recipient
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
    And I clear all old messages from "workpcautomation2@outlook.com" inbox
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | mailDestination  | random active address |
      | writtenLanguage  | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | nowAvail1         |
      | caseId                          | previouslyCreated |
      | emailAddress                    | email2            |
      | recipients                      | random            |
      | recipients2                     | email             |
      | channel                         | Email,Mail        |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I see that a Document has been uploaded to Onbase for the "Mail" Outbound Correspondence Notification
    And I receive an update to the "previouslyCreated" Outbound Correspondence Notification that it is read to be viewed
    And I verify that the Email message was received from Send Grid for "workpcautomation@outlook.com" address
    And I verify that the Email message not received from Send Grid for "workpcautomation2@outlook.com"
    And I verify the sender email address is taken from Outbound Correspondence Settings
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status       | Exported              |
      | Updated By   | ECMS Service          |
      | Updated Date | current date and hour |
    And I see that a Document has been uploaded to Onbase for the "Mail2" Outbound Correspondence Notification
    And I receive an update to the "previouslyCreated" Outbound Correspondence Notification that it is read to be viewed
    And I verify that the Email message was received from Send Grid for "workpcautomation2@outlook.com" address
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response for second Recipient
      | Status       | Exported              |
      | Updated By   | ECMS Service          |
      | Updated Date | current date and hour |

  @CP-38557 @CP-38557-05 @ui-ecms-dceb @burak
  Scenario: Verify Email Notification is queued and sent after updating Text notification (DC-EB)
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
    And I retrieve a phone number that will receive a sms text from "receiveSMS"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | nowAvail9d                   |
      | caseId                          | DCEBcaseid                   |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients                      | random                       |
      | channel                         | Email,Text                   |
      | textNumber                      | receiveSMS                   |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I see that a Document has been uploaded to Onbase for the "Text" Outbound Correspondence Notification
    And I receive an update to the "previouslyCreated" Outbound Correspondence Notification that it is read to be viewed
    And I verify that the Email message was received from Send Grid for "workpcautomation@outlook.com" address
    And I verify the sender email address is taken from Outbound Correspondence Settings
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status       | Exported              |
      | Updated By   | ECMS Service          |
      | Updated Date | current date and hour |
    And I copied the following nids
      | notificationId | Email |
    And I should see that following records are produced to DPBI
      | NOTIFICATION_UPDATE_EVENT |

  @CP-38557 @CP-38557-06 @ui-ecms-dceb @burak
  Scenario: Verify Email Notification is not queued and sent after updating Mail notification when Notification Purpose is not Now Available (DC-EB)
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
    And I clear all old messages from "workpcautomation@outlook.com" inbox
    And I retrieve a phone number that will receive a sms text from "receiveSMS"
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | nowAvail9e                   |
      | caseId                          | DCEBcaseid                   |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients                      | random                       |
      | channel                         | Email,Text                   |
      | textNumber                      | receiveSMS                   |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I see that a Document has been uploaded to Onbase for the "Text" Outbound Correspondence Notification
    And I receive an update to the "previouslyCreated" Outbound Correspondence Notification that it is read to be viewed
    And I verify that the Email message not received from Send Grid for "workpcautomation@outlook.com"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status       | Requested             |
      | Updated By   | ECMS Service          |
      | Updated Date | current date and hour |

  @CP-38557 @CP-38557-07 @ui-ecms-dceb @burak
  Scenario: Verify Email Notification is queued and sent after updating Mail notification for 2 Recipients (DC-EB)
      # Verify Email Notification is not queued after updating Mail notification for 1st Recipient
      # Verify Email Notification is  queued and sent  after updating Mail notification for 2nd Recipient
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
    And I clear all old messages from "workpcautomation@outlook.com" inbox
    And I clear all old messages from "workpcautomation2@outlook.com" inbox
    And I retrieve a phone number that will receive a sms text from "receiveSMS"
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | nowAvail9d                   |
      | caseId                          | DCEBcaseid                   |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients                      | random                       |
      | channel                         | Email,Text                   |
      | textNumber                      | receiveSMS                   |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I see that a Document has been uploaded to Onbase for the "Text" Outbound Correspondence Notification
    And I receive an update to the "previouslyCreated" Outbound Correspondence Notification that it is read to be viewed
    And I verify that the Email message was received from Send Grid for "workpcautomation@outlook.com" address
    And I verify that the Email message not received from Send Grid for "workpcautomation2@outlook.com"
    And I verify the sender email address is taken from Outbound Correspondence Settings
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status       | Exported              |
      | Updated By   | ECMS Service          |
      | Updated Date | current date and hour |
    And I see that a Document has been uploaded to Onbase for the "Text2" Outbound Correspondence Notification
    And I receive an update to the "previouslyCreated" Outbound Correspondence Notification that it is read to be viewed
    And I verify that the Email message was received from Send Grid for "workpcautomation2@outlook.com" address
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response for second Recipient
      | Status       | Exported              |
      | Updated By   | ECMS Service          |
      | Updated Date | current date and hour |