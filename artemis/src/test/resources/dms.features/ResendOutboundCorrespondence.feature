@CP-39530
Feature:Allow User to Select Language When Selecting Resend Notification

  @CP-39530 @CP-39530-01 @ui-ecms2 @Keerthi
  Scenario: validate Language dropdown available and initially blank while Resend notification
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId     | Random |
      | ConsumerId | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I validated for "Requested" notification status
    And I click on notification actions dropdown to select "RESEND"
    Then I validate "language" dropdown initially blank

  @CP-39530 @CP-39530-02 @ui-ecms2 @Keerthi
  Scenario: validate  Language dropdown consists of languages configured for that Channel
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I have active languages and channels for "BL01"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | BL01              |
      | caseId                          | previouslyCreated |
      | REGARDINGCONSUMERID             | previouslyCreated |
      | firstName                       | test              |
      | lastName                        | test              |
      | channelType                     | Mail              |
      | streetAddress                   | test lane 1       |
      | city                            | cedar park        |
      | state                           | TX                |
      | zipcode                         | 78613             |
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I click on notification actions dropdown to select "RESEND"
    Then I validate following values for Add or Resend Notification
      | ChannelList   | activechannels |
      | SelectChannel | Text           |
      | LanguageList  | Text           |


  @CP-39530 @CP-39530-03 @ui-ecms2 @Keerthi
  Scenario: validate save functionality with language dropdown values
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I have active languages and channels for "BL01"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | BL01              |
      | caseId                          | previouslyCreated |
      | REGARDINGCONSUMERID             | previouslyCreated |
      | firstName                       | test              |
      | lastName                        | test              |
      | channelType                     | Mail              |
      | streetAddress                   | test lane 1       |
      | city                            | cedar park        |
      | state                           | TX                |
      | zipcode                         | 78613             |
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I validated for "Requested" notification status
    And I click on notification actions dropdown to select "RESEND"
    Then I validate following values for Add or Resend Notification
      | SelectChannel     | Text         |
      | SelectLanguage    | Spanish      |
      | SelectDestination | 678-909-8767 |
    And I click save for notification
    And I stored details of each recipient notification for "previouslyCreated" ob correspondence
    And I verify outbound correspondence NotificationId Language
      | previouslycreated_text_nid_language   | Spanish |
      | previouslycreated_text_nid_templateid | fromAPI |
      | previouslycreated_text_nid_version    | fromAPI |

  @CP-39530 @CP-39530-04 @ui-ecms2 @Keerthi
  Scenario: validate save functionality without language dropdown value(default parent notification values)
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | BL01              |
      | caseId                          | previouslyCreated |
      | REGARDINGCONSUMERID             | previouslyCreated |
      | firstName                       | test              |
      | lastName                        | test              |
      | channelType                     | Mail              |
      | streetAddress                   | test lane 1       |
      | city                            | cedar park        |
      | state                           | TX                |
      | zipcode                         | 78613             |
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I validated for "Requested" notification status
    And I click on notification actions dropdown to select "RESEND"
    And I click save for notification
    And I stored details of each recipient notification for "previouslyCreated" ob correspondence
    And I verify outbound correspondence NotificationId Language
      | previouslycreated_mail_nid_language   | English |
      | previouslycreated_mail_nid_templateid | fromAPI |
      | previouslycreated_mail_nid_version    | fromAPI |


  @CP-40011 @CP-40011-03a @API-ECMS @James
  Scenario Outline: Verify if a matching destination already exists for the selected channel then a new recipient record is not created or updated
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
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    When I create a Notification with the "previouslyCreated" new Outbound Correspondence with the following values
      | destination            | sameDestination   |
      | NewNotificationChannel | <Channel>         |
      | recipient              | previouslyCreated |
    Then I should see the newly created notification is attached to the "exactSamePreviouslyCreated" recipient record
    Examples:
      | Channel | channelDestination    |
      | Mail    | mailDestination       |
      | Text    | secondTextDestination |
      | Fax     | secondFaxDestination  |
      | Email   | emailDestination      |

  @CP-40011 @CP-40011-03b @API-ECMS @James
  Scenario Outline: Verify if the destination values in the request are blank in the existing recipient record then the recipient record is updated
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
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    When I create a Notification with the "previouslyCreated" new Outbound Correspondence with the following values
      | destination            | NewNotificationDestination |
      | NewNotificationChannel | <NewNotificationChannel>   |
      | recipient              | previouslyCreated          |
    Then I should see the newly created notification is attached to the "previouslyCreatedNowUpdated" recipient record
    And I retrieve the "CORRESPONDENCE_RECIPIENT_UPDATE_EVENT" event that is produced by trace id
    Then I should see the payload for the "CORRESPONDENCE_RECIPIENT_UPDATE_EVENT" is as expected
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

  @CP-40011 @CP-40011-03c1 @API-ECMS @James
  Scenario Outline: Verify if the destination values in the request are NOT blank and destinations are different in the existing recipient record then a new recipient record is created
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
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    When I create a Notification with the "previouslyCreated" new Outbound Correspondence with the following values
      | destination                           | NewNotificationDestination |
      | NewlyCreatedCorrespondenceRecipientId | true                       |
      | NewNotificationChannel                | <Channel>                  |
      | recipient                             | previouslyCreated          |
      | notificationsExpected                 | 2                          |
    Then I should see the newly created notification is attached to the "newlyCreated" recipient record
    And I should see the Random Destination information from the newly added Notification for "<Channel>" channel is present in the letter data that is generated
    And I retrieve the "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" event that is produced by trace id
    Then I should see the payload for the "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" is as expected
    And I should see that the event mapping for "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" exists
    And I should see that the "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" record is produced to DPBI
    Examples:
      | Channel | channelDestination    |
      | Mail    | mailDestination       |
      | Text    | secondTextDestination |
      | Fax     | secondFaxDestination  |
      | Email   | emailDestination      |

  @CP-40011 @CP-40011-03c2 @API-ECMS @James
  Scenario Outline: Verify if the destination values in the request are NOT blank and one destination is different in the existing recipient record then a new recipient record is created
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole          | Primary Individual    |
      | mailDestination       | random active address |
      | emailDestination      | random active address |
      | secondTextDestination | random active address |
      | secondFaxDestination  | random active address |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase           |
      | caseId                          | previouslyCreated   |
      | emailAddress                    | sendGrid            |
      | recipients                      | random              |
      | channel                         | Mail,Email,Text,Fax |
      | language                        | English             |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    When I create a Notification with the "previouslyCreated" new Outbound Correspondence with the following values
      | destination                           | NewNotificationDestination |
      | NewlyCreatedCorrespondenceRecipientId | true                       |
      | NewNotificationChannel                | <Channel>                  |
      | recipient                             | previouslyCreated          |
      | notificationsExpected                 | 5                          |
    Then I should see the newly created notification is attached to the "newlyCreated" recipient record
    And I retrieve the "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" event that is produced by trace id
    Then I should see the payload for the "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" is as expected
    And I should see that the event mapping for "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" exists
    And I should see that the "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" record is produced to DPBI
    Examples:
      | Channel |
      | Mail    |
      | Text    |
      | Fax     |
      | Email   |

  @CP-40011 @CP-40011-03aINEB @api-ecms-ineb @James
  Scenario Outline: Verify if a matching destination already exists for the selected channel then a new recipient record is not created or updated for ineb
    Given I will get the Authentication token for "IN-EB" in "CRM"
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
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    When I create a Notification with the "previouslyCreated" new Outbound Correspondence with the following values
      | destination            | sameDestination   |
      | NewNotificationChannel | <Channel>         |
      | recipient              | previouslyCreated |
    Then I should see the newly created notification is attached to the "exactSamePreviouslyCreated" recipient record
#    And I should see the Random Destination information from the newly added Notification for "<Channel>" channel is present in the letter data that is generated
    Examples:
      | Channel | channelDestination    |
      | Mail    | mailDestination       |
      | Text    | secondTextDestination |
      | Fax     | secondFaxDestination  |
      | Email   | emailDestination      |

  @CP-40011 @CP-40011-03bINEB @api-ecms-ineb @James
  Scenario Outline: Verify if the destination values in the request are blank in the existing recipient record then the recipient record is updated for ineb
    Given I will get the Authentication token for "IN-EB" in "CRM"
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
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    When I create a Notification with the "previouslyCreated" new Outbound Correspondence with the following values
      | destination            | NewNotificationDestination |
      | NewNotificationChannel | <NewNotificationChannel>   |
      | recipient              | previouslyCreated          |
    Then I should see the newly created notification is attached to the "previouslyCreatedNowUpdated" recipient record
    And I retrieve the "CORRESPONDENCE_RECIPIENT_UPDATE_EVENT" event that is produced by trace id
    Then I should see the payload for the "CORRESPONDENCE_RECIPIENT_UPDATE_EVENT" is as expected
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

  @CP-40011 @CP-40011-03c1INEB @api-ecms-ineb @James
  Scenario Outline: Verify if the destination values in the request are NOT blank and destinations are different in the existing recipient record then a new recipient record is created for ineb
    Given I will get the Authentication token for "IN-EB" in "CRM"
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
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    When I create a Notification with the "previouslyCreated" new Outbound Correspondence with the following values
      | destination                           | NewNotificationDestination |
      | NewlyCreatedCorrespondenceRecipientId | true                       |
      | NewNotificationChannel                | <Channel>                  |
      | recipient                             | previouslyCreated          |
      | notificationsExpected                 | 2                          |
    Then I should see the newly created notification is attached to the "newlyCreated" recipient record
    And I retrieve the "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" event that is produced by trace id
    Then I should see the payload for the "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" is as expected
    And I should see that the event mapping for "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" exists
    And I should see that the "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" record is produced to DPBI
    Examples:
      | Channel | channelDestination    |
      | Mail    | mailDestination       |
      | Text    | secondTextDestination |
      | Fax     | secondFaxDestination  |
      | Email   | emailDestination      |

  @CP-40011 @CP-40011-03c2INEB @api-ecms-ineb @James
  Scenario Outline: Verify if the destination values in the request are NOT blank and one destination is different in the existing recipient record then a new recipient record is created for ineb
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole          | Primary Individual    |
      | mailDestination       | random active address |
      | emailDestination      | random active address |
      | secondTextDestination | random active address |
      | secondFaxDestination  | random active address |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase           |
      | caseId                          | previouslyCreated   |
      | emailAddress                    | sendGrid            |
      | recipients                      | random              |
      | channel                         | Mail,Email,Text,Fax |
      | language                        | English             |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    When I create a Notification with the "previouslyCreated" new Outbound Correspondence with the following values
      | destination                           | NewNotificationDestination |
      | NewlyCreatedCorrespondenceRecipientId | true                       |
      | NewNotificationChannel                | <Channel>                  |
      | recipient                             | previouslyCreated          |
      | notificationsExpected                 | 5                          |
    Then I should see the newly created notification is attached to the "newlyCreated" recipient record
    And I retrieve the "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" event that is produced by trace id
    Then I should see the payload for the "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" is as expected
    And I should see that the event mapping for "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" exists
    And I should see that the "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" record is produced to DPBI
    Examples:
      | Channel |
      | Mail    |
      | Text    |
      | Fax     |
      | Email   |

  @CP-40011 @CP-40011-03aDCEB @api-ecms-dceb @James
  Scenario Outline: Verify if a matching destination already exists for the selected channel then a new recipient record is not created for dceb
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
      | channel                         | <Channel>  |
      | language                        | English    |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    When I create a Notification with the "previouslyCreated" new Outbound Correspondence with the following values
      | destination            | sameDestination   |
      | NewNotificationChannel | <Channel>         |
      | recipient              | previouslyCreated |
    Then I should see the newly created notification is attached to the "exactSamePreviouslyCreated" recipient record
#    And I should see the Random Destination information from the newly added Notification for "<Channel>" channel is present in the letter data that is generated
    Examples:
      | Channel |
      | Mail    |
      | Text    |
      | Fax     |
      | Email   |

  @CP-40011 @CP-40011-03bDCEB @api-ecms-dceb @James
  Scenario Outline: Verify if the destination values in the request are blank in the existing recipient record then the recipient record is updated for dceb
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
      | channel                         | <Channel>  |
      | language                        | English    |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    When I create a Notification with the "previouslyCreated" new Outbound Correspondence with the following values
      | destination            | NewNotificationDestination |
      | NewNotificationChannel | <NewNotificationChannel>   |
      | recipient              | previouslyCreated          |
    Then I should see the newly created notification is attached to the "previouslyCreatedNowUpdated" recipient record
    And I retrieve the "CORRESPONDENCE_RECIPIENT_UPDATE_EVENT" event that is produced by trace id
    Then I should see the payload for the "CORRESPONDENCE_RECIPIENT_UPDATE_EVENT" is as expected
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

  @CP-40011 @CP-40011-03c1DCEB @api-ecms-dceb @James
  Scenario Outline: Verify if the destination values in the request are NOT blank and destinations are different in the existing recipient record then a new recipient record is created for dceb
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
      | channel                         | <Channel>  |
      | language                        | English    |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    When I create a Notification with the "previouslyCreated" new Outbound Correspondence with the following values
      | destination                           | NewNotificationDestination |
      | NewlyCreatedCorrespondenceRecipientId | true                       |
      | NewNotificationChannel                | <Channel>                  |
      | recipient                             | previouslyCreated          |
      | notificationsExpected                 | 2                          |
    Then I should see the newly created notification is attached to the "newlyCreated" recipient record
    And I retrieve the "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" event that is produced by trace id
    Then I should see the payload for the "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" is as expected
    And I should see that the event mapping for "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" exists
    And I should see that the "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" record is produced to DPBI
    Examples:
      | Channel |
      | Mail    |
      | Text    |
      | Fax     |
      | Email   |

  @CP-40011 @CP-40011-03c2DCEB @api-ecms-dceb @James
  Scenario Outline: Verify if the destination values in the request are NOT blank and one destination is different in the existing recipient record then a new recipient record is created for dceb
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
      | channel                         | <Channel>  |
      | language                        | English    |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    When I create a Notification with the "previouslyCreated" new Outbound Correspondence with the following values
      | destination                           | NewNotificationDestination |
      | NewlyCreatedCorrespondenceRecipientId | true                       |
      | NewNotificationChannel                | <Channel>                  |
      | recipient                             | previouslyCreated          |
      | notificationsExpected                 | 2                          |
    Then I should see the newly created notification is attached to the "newlyCreated" recipient record
    And I retrieve the "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" event that is produced by trace id
    Then I should see the payload for the "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" is as expected
    And I should see that the event mapping for "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" exists
    And I should see that the "CORRESPONDENCE_RECIPIENT_SAVE_EVENT" record is produced to DPBI
    Examples:
      | Channel |
      | Mail    |
      | Text    |
      | Fax     |
      | Email   |