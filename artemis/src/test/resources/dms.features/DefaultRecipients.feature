Feature: Retrieving Default Recipients and Destinations Before Fulfillment

  @CP-29107 @CP-29107-01 @API-ECMS @James
  Scenario: Correspondence Recipient record is created associated with the Correspondence Order
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual            |
      | mailDestination | random active mailing address |
    When I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | default           |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a "Mail" notification that has the most updated contact information with the following values
      | firstName    | from caseCorrespondence API |
      | lastName     | from caseCorrespondence API |
      | role         | from caseCorrespondence API |
      | consumerId   | from caseCorrespondence API |
      | addressLine  | from caseCorrespondence API |
      | addressLine2 | from caseCorrespondence API |
      | city         | from caseCorrespondence API |
      | state        | from caseCorrespondence API |
      | zip          | from caseCorrespondence API |
      | language     | from request                |
      | templateId   | ID of template used         |

  @CP-29107 @CP-35129 @CP-29107-01b @API-ECMS @James
  Scenario: Correspondence Recipient record is created for all channels
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole          | Primary Individual    |
      | mailDestination       | random active address |
      | emailDestination      | random active address |
      | secondTextDestination | random active address |
      | secondFaxDestination  | random active address |
    When I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | allChCase         |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a Mail, Email, Text, Fax notification that has the most updated contact information with the following values

  @CP-29107 @CP-35129 @CP-29107-01c @API-ECMS @James
  Scenario: Verify the language specified on Correspondence Request is the language that is used to generate data object for all eligible channels
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole          | Primary Individual    |
      | mailDestination       | random active address |
      | emailDestination      | random active address |
      | secondTextDestination | random active address |
      | secondFaxDestination  | random active address |
      | writtenLanguage       | Spanish               |
    When I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | allChCase         |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a Mail, Email, Text, Fax notification that has the most updated contact information with the following values
    And I should see the language used for all notifications that were created is "Spanish" in the letter data that is generated

  @CP-29107 @CP-35129 @CP-29107-01d @API-ECMS @James
  Scenario: Verify the language for project is the language that is used to generate data object for all eligible channels
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole          | Primary Individual    |
      | mailDestination       | random active address |
      | emailDestination      | random active address |
      | secondTextDestination | random active address |
      | secondFaxDestination  | random active address |
      | writtenLanguage       | null                  |
    When I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | allChCase         |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | writtenLanguage                 | null              |
    And I the default language for the project "BLCRM" is "English"
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a Mail, Email, Text, Fax notification that has the most updated contact information with the following values
    And I should see the language used for all notifications that were created is "English" in the letter data that is generated

  @CP-29107 @CP-35129 @CP-29107-01e @API-ECMS @James
  Scenario: Verify the preferred language on a case is the language that is used to generate data object for all eligible channels
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole          | Primary Individual    |
      | mailDestination       | random active address |
      | emailDestination      | random active address |
      | secondTextDestination | random active address |
      | secondFaxDestination  | random active address |
      | writtenLanguage       | Spanish               |
    When I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | allChCase         |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | writtenLanguage                 | null              |
    And I the default language for the project "BLCRM" is "English"
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a Mail, Email, Text, Fax notification that has the most updated contact information with the following values
    And I should see the language used for all notifications that were created is "Spanish" in the letter data that is generated

  @CP-29107 @CP-29107-02 @API-ECMS @James
  Scenario: Correspondence Recipient record is created associated with the Correspondence Order but with Errors
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual |
      | mailDestination | null               |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | allChCase         |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the Outbound Correspondence is updated with the following values but Notifications are not created
      | status       | Error                |
      | statusReason | No Valid Destination |
    And I should see the Outbound Correspondence Notification is created with the following values
      | status       | Precluded                   |
      | statusReason | No active destination found |


  @CP-29107 @CP-29107-03 @API-ECMS @James
  Scenario: Correspondence Recipient record is NOT created associated with the Correspondence Order
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Member |
      | mailDestination | null   |
    When I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | default           |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the Outbound Correspondence is updated with the following values but Notifications are not created
      | status       | Error                          |
      | statusReason | No default recipient available |

  @CP-29107 @CP-29107-04 @API-ECMS @James
  Scenario: Correspondence Recipient record is pulling latest address
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual            |
      | mailDestination | random active mailing address |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | default           |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
    And I have a consumer on a case that wants to send an Outbound Correspondence but has changed his "mailDestination" contact information
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a "Mail" notification that has the most updated contact information with the following values
      | firstName    | from caseCorrespondence API |
      | lastName     | from caseCorrespondence API |
      | role         | from caseCorrespondence API |
      | consumerId   | from caseCorrespondence API |
      | addressLine  | from caseCorrespondence API |
      | addressLine2 | from caseCorrespondence API |
      | city         | from caseCorrespondence API |
      | state        | from caseCorrespondence API |
      | zip          | from caseCorrespondence API |
      | language     | from request                |
      | templateId   | ID of template used         |

  @CP-35129 @CP-35295 @CP-35129-1a @API-ECMS @James
  Scenario Outline: Correspondence Recipient record regarding a case is pulling latest address for mail, email, text, fax
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole         | Primary Individual    |
      | <ChannelDestination> | random active address |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | allChCase         |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    And I have a consumer on a case that wants to send an Outbound Correspondence but has changed his "<ChannelDestination>" contact information
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a "<Channel>" notification that has the most updated contact information with the following values
      | firstName   | from caseCorrespondence API |
      | lastName    | from caseCorrespondence API |
      | role        | from caseCorrespondence API |
      | consumerId  | from caseCorrespondence API |
      | destination | from caseCorrespondence API |

    Examples:
      | ChannelDestination    | Channel    |
      | mailDestination       | mail       |
      | emailDestination      | email      |
      | secondTextDestination | secondText |
      | secondFaxDestination  | secondFax  |

  @CP-35129 @CP-35295 @CP-35129-1b @API-ECMS @James
  Scenario Outline: Correspondence Recipient record regarding a case with a consumer is pulling latest address for mail, email, text, fax
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole         | Primary Individual    |
      | <ChannelDestination> | random active address |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | allChCC           |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
    And I have a consumer on a case that wants to send an Outbound Correspondence but has changed his "<ChannelDestination>" contact information
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a "<Channel>" notification that has the most updated contact information with the following values
      | firstName   | from caseCorrespondence API |
      | lastName    | from caseCorrespondence API |
      | role        | from caseCorrespondence API |
      | consumerId  | from caseCorrespondence API |
      | destination | from caseCorrespondence API |

    Examples:
      | ChannelDestination    | Channel    |
      | mailDestination       | mail       |
      | emailDestination      | email      |
      | secondTextDestination | secondText |
      | secondFaxDestination  | secondFax  |

  @CP-35129 @CP-35295 @CP-35129-1c @API-ECMS @James
  Scenario Outline: Correspondence Recipient record regarding a case with multiple consumers is pulling latest address for mail, email, text, fax
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole         | Primary Individual    |
      | <ChannelDestination> | random active address |
    And I add another "Primary Individual" on the "previouslyCreated" case that will receive an Outbound Correspondence
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | allChCCS          |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | secondRegardingConsumerId       | previouslyCreated |
    And I have a consumer on a case that wants to send an Outbound Correspondence but has changed his "<ChannelDestination>" contact information
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a "<Channel>" notification that has the most updated contact information with the following values
      | firstName   | from caseCorrespondence API |
      | lastName    | from caseCorrespondence API |
      | role        | from caseCorrespondence API |
      | consumerId  | from caseCorrespondence API |
      | destination | from caseCorrespondence API |

    Examples:
      | ChannelDestination    | Channel    |
      | mailDestination       | mail       |
      | emailDestination      | email      |
      | secondTextDestination | secondText |
      | secondFaxDestination  | secondFax  |

  @CP-35129 @CP-35295 @CP-35129-1d @API-ECMS @James
  Scenario: Correspondence Recipient record regarding a case with more than one recipient is pulling latest address for mail, email, text, fax
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole          | Primary Individual    |
      | mailDestination       | random active address |
      | emailDestination      | random active address |
      | secondTextDestination | random active address |
      | secondFaxDestination  | random active address |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | allChCase         |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    And I add another "Primary Individual" on the "previouslyCreated" case with a new "Mail,Fax,Text,Email" that will receive an Outbound Correspondence
    When I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a Mail, Email, Text, Fax notification for each recipient that has the most updated contact information with the following values

  @CP-35129 @CP-35295 @CP-35129-2 @API-ECMS @James
  Scenario Outline: Correspondence Recipient record is created associated with the Correspondence Order but with Errors for mail, email, text, fax
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole         | Primary Individual |
      | <ChannelDestination> | null               |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | allChCase         |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the Outbound Correspondence is updated with the following values but Notifications are not created
      | status       | Error                |
      | statusReason | No Valid Destination |
    And I should see the Outbound Correspondence Notification is created with the following values
      | status       | Precluded                   |
      | statusReason | No active destination found |
    And I should see that no letter data was created for the "previouslyCreated" Notification for "<Channel>" channel

    Examples:
      | ChannelDestination | Channel |
      | mailDestination    | mail    |
      | emailDestination   | email   |
      | textDestination    | text    |
      | faxDestination     | fax     |

  @CP-35129 @CP-35129-3 @API-ECMS @James
  Scenario: Correspondence Recipient record is NOT created associated with the Correspondence Order mail, email, text, fax
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Member |
      | mailDestination | null   |
    When I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | default           |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the Outbound Correspondence is updated with the following values but Notifications are not created
      | status       | Error                          |
      | statusReason | No default recipient available |


  @CP-35295 @CP-35294-1 @CP-35294 @API-ECMS @James
  Scenario: Verify when mandatory mail channel has unusable destination because paperless preference, then Correspondence status is Requested
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole             | Primary Individual    |
      | mailDestination          | random active address |
      | emailDestination         | random active address |
      | correspondencePreference | Paperless             |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | mailManCa         |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    When I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a "Mail" notification that has the most updated contact information with the following values
      | firstName   | from caseCorrespondence API |
      | lastName    | from caseCorrespondence API |
      | role        | from caseCorrespondence API |
      | consumerId  | from caseCorrespondence API |
      | destination | from caseCorrespondence API |
    And I should see that a letter data record is created for the notification that has a "Mail" Channel
    Then I should see the Outbound Correspondence Order is updated to the following status with status message
      | status        | Requested |
      | statusMessage | null      |
    And I should see the Outbound Correspondence Notification for "text" channel is created with the following values
      | status | Precluded |
    And I should see the Outbound Correspondence Notification for "fax" channel is created with the following values
      | status | Precluded |

  @CP-35295 @CP-35294-2 @CP-35294 @API-ECMS @James
  Scenario: Verify when mandatory mail channel has unusable destination and not Paperless preference, then Correspondence status is Error
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | mailDestination  | null                  |
      | emailDestination | random active address |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | mailManCa         |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    When I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a "Mail" notification that has the most updated contact information with the following values
      | firstName   | from caseCorrespondence API |
      | lastName    | from caseCorrespondence API |
      | role        | from caseCorrespondence API |
      | consumerId  | from caseCorrespondence API |
      | destination | from caseCorrespondence API |
    And I should see the Outbound Correspondence Notification for "Mail" channel is created with the following values
      | status       | Precluded |
      | statusReason | null      |
    And I should see that no letter data was created for the "previouslyCreated" Notification for "Mail" channel
    Then I should see the Outbound Correspondence Order is updated to the following status with status message
      | status        | Error                |
      | statusMessage | No Valid Destination |
    And I should see the Outbound Correspondence Notification for "text" channel is created with the following values
      | status | Precluded |
    And I should see the Outbound Correspondence Notification for "fax" channel is created with the following values
      | status | Precluded |

  @CP-35295 @CP-35294-3 @CP-35294 @API-ECMS @James
  Scenario: Verify when mandatory text channel has usable destination, then Correspondence status is Requested
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | textDestination  | random active address |
      | emailDestination | random active address |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | textManCa         |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    When I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a "text" notification that has the most updated contact information with the following values
      | firstName   | from caseCorrespondence API |
      | lastName    | from caseCorrespondence API |
      | role        | from caseCorrespondence API |
      | consumerId  | from caseCorrespondence API |
      | destination | from caseCorrespondence API |
    And I should see that a letter data record is created for the notification that has a "text" Channel
    Then I should see the Outbound Correspondence Order is updated to the following status with status message
      | status        | Requested |
      | statusMessage | null      |
    And I should see the Outbound Correspondence Notification for "mail" channel is created with the following values
      | status | Precluded |
    And I should see the Outbound Correspondence Notification for "fax" channel is created with the following values
      | status | Precluded |

  @CP-35295 @CP-35294-4 @CP-35294 @API-ECMS @James
  Scenario: Verify when mandatory text channel has unusable destination and not Paperless preference, then Correspondence status is Error
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | textDestination  | null                  |
      | emailDestination | random active address |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | textManCa         |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    When I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a "email" notification that has the most updated contact information with the following values
      | firstName   | from caseCorrespondence API |
      | lastName    | from caseCorrespondence API |
      | role        | from caseCorrespondence API |
      | consumerId  | from caseCorrespondence API |
      | destination | from caseCorrespondence API |
    And I should see the Outbound Correspondence Notification for "text" channel is created with the following values
      | status | Precluded |
    And I should see that no letter data was created for the "previouslyCreated" Notification for "text" channel
    Then I should see the Outbound Correspondence Order is updated to the following status with status message
      | status        | Error                |
      | statusMessage | No Valid Destination |
    And I should see the Outbound Correspondence Notification for "mail" channel is created with the following values
      | status | Precluded |
    And I should see the Outbound Correspondence Notification for "fax" channel is created with the following values
      | status | Precluded |

  @CP-35295 @CP-35294-5 @CP-35294 @API-ECMS @James
  Scenario: Verify when mandatory fax channel has usable destination, then Correspondence status is Requested
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | faxDestination   | random active address |
      | emailDestination | random active address |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | faxManCa          |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    When I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a "fax" notification that has the most updated contact information with the following values
      | firstName   | from caseCorrespondence API |
      | lastName    | from caseCorrespondence API |
      | role        | from caseCorrespondence API |
      | consumerId  | from caseCorrespondence API |
      | destination | from caseCorrespondence API |
    And I should see that a letter data record is created for the notification that has a "fax" Channel
    Then I should see the Outbound Correspondence Order is updated to the following status with status message
      | status        | Requested |
      | statusMessage | null      |
    And I should see the Outbound Correspondence Notification for "mail" channel is created with the following values
      | status | Precluded |
    And I should see the Outbound Correspondence Notification for "text" channel is created with the following values
      | status | Precluded |

  @CP-35295 @CP-35294-6 @CP-35294 @API-ECMS @James
  Scenario: Verify when mandatory fax channel has unusable destination and not Paperless preference, then Correspondence status is Error
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | faxDestination   | null                  |
      | emailDestination | random active address |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | faxManCa          |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    When I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a "email" notification that has the most updated contact information with the following values
      | firstName   | from caseCorrespondence API |
      | lastName    | from caseCorrespondence API |
      | role        | from caseCorrespondence API |
      | consumerId  | from caseCorrespondence API |
      | destination | from caseCorrespondence API |
    And I should see the Outbound Correspondence Notification for "fax" channel is created with the following values
      | status       | Precluded |
      | statusReason | null      |
    And I should see that no letter data was created for the "previouslyCreated" Notification for "fax" channel
    Then I should see the Outbound Correspondence Order is updated to the following status with status message
      | status        | Error                |
      | statusMessage | No Valid Destination |
    And I should see the Outbound Correspondence Notification for "mail" channel is created with the following values
      | status | Precluded |
    And I should see the Outbound Correspondence Notification for "text" channel is created with the following values
      | status | Precluded |


  @CP-35295 @CP-35294-7 @CP-35294 @API-ECMS @James
  Scenario: Verify when mandatory email channel has usable destination, then Correspondence status is Requested
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | faxDestination   | random active address |
      | emailDestination | random active address |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | emailManCa        |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    When I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a "email" notification that has the most updated contact information with the following values
      | firstName   | from caseCorrespondence API |
      | lastName    | from caseCorrespondence API |
      | role        | from caseCorrespondence API |
      | consumerId  | from caseCorrespondence API |
      | destination | from caseCorrespondence API |
    And I should see that a letter data record is created for the notification that has a "email" Channel
    Then I should see the Outbound Correspondence Order is updated to the following status with status message
      | status        | Requested |
      | statusMessage | null      |
    And I should see the Outbound Correspondence Notification for "mail" channel is created with the following values
      | status | Precluded |
    And I should see the Outbound Correspondence Notification for "text" channel is created with the following values
      | status | Precluded |

  @CP-35295 @CP-35294-8 @CP-35294 @API-ECMS @James
  Scenario: Verify when mandatory email channel has unusable destination and not Paperless preference, then Correspondence status is Error
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | faxDestination   | random active address |
      | emailDestination | null                  |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | emailManCa        |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    When I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I should see the Outbound Correspondence Notification for "email" channel is created with the following values
      | status | Precluded |
    And I should see that no letter data was created for the "previouslyCreated" Notification for "email" channel
    Then I should see the Outbound Correspondence Order is updated to the following status with status message
      | status        | Error                |
      | statusMessage | No Valid Destination |
    And I should see the Outbound Correspondence Notification for "mail" channel is created with the following values
      | status | Precluded |
    And I should see the Outbound Correspondence Notification for "text" channel is created with the following values
      | status | Precluded |


############################## CP-36823 ############################################

  @CP-36823 @CP-36823-01 @API-ECMS @Keerthi
  Scenario: Enhance Precluded Status to include No active destination found Reason
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole            | Primary Individual |
      | mailDestination         | null               |
      | consentType_OptIn_false | Mail               |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | MailMnWn          |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the Outbound Correspondence is updated with the following values but Notifications are not created
      | status       | Error                |
      | statusReason | No Valid Destination |
    And I should see the Outbound Correspondence Notification is created with the following values for "Mail" channel for "previouslycreated" Outbound Correspondence
      | status       | Precluded                   |
      | statusReason | No active destination found |


  @CP-36823 @CP-36823-02 @API-ECMS @Keerthi
  Scenario: Enhance Precluded Status to include Paperless Preference  Reason
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole             | Primary Individual    |
      | mailDestination          | random active address |
      | correspondencePreference | true                  |
      | consentType_OptIn_false  | Mail                  |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | MailMnWn          |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the Outbound Correspondence is updated with the following values but Notifications are not created
      | status       | Error                |
      | statusReason | No Valid Destination |
    And I should see the Outbound Correspondence Notification is created with the following values for "Mail" channel for "previouslycreated" Outbound Correspondence
      | status       | Precluded            |
      | statusReason | Paperless Preference |


  @CP-36823 @CP-36823-03 @API-ECMS @Keerthi
  Scenario: Enhance Precluded Status to include Consumer opted out Reason
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole            | Primary Individual    |
      | mailDestination         | random active address |
      | consentType_OptIn_false | Mail                  |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | MailMnWn          |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the Outbound Correspondence is updated with the following values but Notifications are not created
      | status       | Error                |
      | statusReason | No Valid Destination |
    And I should see the Outbound Correspondence Notification is created with the following values for "Mail" channel for "previouslycreated" Outbound Correspondence
      | status       | Precluded          |
      | statusReason | Consumer opted out |

  @CP-42588 @CP-42588-01 @API-ECMS @James
  Scenario Outline: Verify External Ids coming are stored on recipient record during Provisioning
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole                 | Primary Individual             |
      | mailDestination              | random active address          |
      | emailDestination             | random active address          |
      | secondTextDestination        | random active address          |
      | secondFaxDestination         | random active address          |
      | writtenLanguage              | Spanish                        |
      | caseIdentificationNumberType | <caseIdentificationNumberType> |
      | identificationNumberType     | <identificationNumberType>     |
    When I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | allChCase         |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | writtenLanguage                 | null              |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I store the following values from case correspondence for the previously created "caseId" for verification purposes
      | <ExternalCaseId>     |
      | <ExternalConsumerId> |
    Then I should see that the "/correspondences/{correspondenceid}" end point is showing the externalIds for the "newlyProvisionedRecipient" Outbound Correspondence Notification
    Then I should see that the "newlyProvisionedRecipient" letter data contains the following values
      | <ExternalCaseId>     |
      | <ExternalConsumerId> |
    Examples:
      | ExternalCaseId         | ExternalConsumerId         | caseIdentificationNumberType | identificationNumberType |
      | externalCaseIdCHIP     | externalConsumerIdCHIP     | CHIP                         | CHIP                     |
      | externalCaseIdMedicaid | externalConsumerIdMedicaid | Medicaid                     | Medicaid                 |
      | externalCaseIdMedicaid | externalConsumerIdCHIP     | Medicaid                     | CHIP                     |
      | externalCaseIdCHIP     | externalConsumerIdMedicaid | CHIP                         | Medicaid                 |


  @CP-42588 @CP-42588-01DCEB @api-ecms-dceb @James
  Scenario: Verify External Ids coming are stored on recipient record during Provisioning for dceb
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "42258-09" with following payload
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
    When I initiate and run Sync Contacts API
    When I initiate contacts V2 API
    When I add this contact optin "mail" in to the payload
    And I send request to consumer reported V2 API
    When I run reevaluatepi API for V_2
    When I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | allChCase         |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | writtenLanguage                 | null              |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I store the following values from case correspondence for the previously created "caseId" for verification purposes
      | externalCaseIdMedicaid     |
      | externalConsumerIdMedicaid |
    Then I should see that the "/correspondences/{correspondenceid}" end point is showing the externalIds for the "newlyProvisionedRecipient" Outbound Correspondence Notification
    Then I should see that the "newlyProvisionedRecipient" letter data contains the following values
      | externalCaseIdMedicaid     |
      | externalConsumerIdMedicaid |

  @CP-42257 @CP-42257-01 @CP-42258 @CP-42258-01 @API-ECMS @burak
  Scenario Outline: Verify when mandatory mail channel has unusable destination , Reason is Paperless Preference (BLCRM)
    #Verify Mail Flag for Letter Data and Letter Data Save Event
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole             | Primary Individual    |
      | mailDestination          | random active address |
      | correspondencePreference | Paperless             |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | <MMSCode>         |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    When I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a "Mail" notification that has the most updated contact information with the following values
      | firstName   | from caseCorrespondence API |
      | lastName    | from caseCorrespondence API |
      | role        | from caseCorrespondence API |
      | consumerId  | from caseCorrespondence API |
      | destination | from caseCorrespondence API |
    And I should see the Outbound Correspondence Notification for "mail" channel is created with the following values
      | status       | <expectedStatus>       |
      | statusReason | <expectedStatusReason> |
    And I verify "previouslyCreated" letter data contains mail flag as "<mailFlag>"
    And I should see that the mailFlag for "LETTER_DATA_SAVE_EVENT" exists as "<mailFlag>"
    Examples:
      | MMSCode  | expectedStatus | expectedStatusReason | mailFlag       |
      | MailMyWy | Requested      | null                 | true           |
      | MailMnWy | Requested      | Paperless Preference | false          |
      | MailMyWn | Requested      | null                 | true           |
      | MailMnWn | Precluded      | Paperless Preference | no letter data |

  @CP-42257 @CP-42257-02 @CP-42258 @CP-42258-02 @API-ECMS @burak
  Scenario Outline: Verify when mandatory mail channel has unusable destination , Reason is Consumer opted out (BLCRM)
        #Verify Mail Flag for Letter Data and Letter Data Save Event
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole            | Primary Individual    |
      | mailDestination         | random active address |
      | consentType_OptIn_false | Mail                  |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | <MMSCode>         |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    When I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a "Mail" notification that has the most updated contact information with the following values
      | firstName   | from caseCorrespondence API |
      | lastName    | from caseCorrespondence API |
      | role        | from caseCorrespondence API |
      | consumerId  | from caseCorrespondence API |
      | destination | from caseCorrespondence API |
    And I should see the Outbound Correspondence Notification for "mail" channel is created with the following values
      | status       | <expectedStatus>       |
      | statusReason | <expectedStatusReason> |
    And I verify "previouslyCreated" letter data contains mail flag as "<mailFlag>"
    And I should see that the mailFlag for "LETTER_DATA_SAVE_EVENT" exists as "<mailFlag>"
    Examples:
      | MMSCode  | expectedStatus | expectedStatusReason | mailFlag       |
      | MailMyWy | Requested      | Consumer opted out   | false          |
      | MailMnWy | Requested      | Consumer opted out   | false          |
      | MailMyWn | Precluded      | Consumer opted out   | no letter data |
      | MailMnWn | Precluded      | Consumer opted out   | no letter data |

  @CP-42257 @CP-42257-03 @CP-44307 @CP-44307-01 @CP-44137 @CP-44137-01 @API-ECMS @burak
  Scenario Outline: Verify when mandatory mail channel has unusable destination , Reason is No active destination found (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual |
      | mailDestination | null               |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | <MMSCode>         |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    When I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I should see the Outbound Correspondence Notification for "mail" channel is created with the following values
      | status       | <expectedStatus>       |
      | statusReason | <expectedStatusReason> |
    And I verify "previouslyCreated" letter data contains mail flag as "<mailFlag>"
    And Wait for 3 seconds
    And I should see that the mailFlag for "LETTER_DATA_SAVE_EVENT" exists as "<mailFlag>"
    Examples:
      | MMSCode  | expectedStatus | expectedStatusReason        | mailFlag       |
      | MailMyWy | Requested      | No active destination found | false          |
      | MailMnWy | Requested      | No active destination found | false          |
      | MailMyWn | Precluded      | No active destination found | no letter data |
      | MailMnWn | Precluded      | No active destination found | no letter data |

  @CP-42258 @CP-42258-03 @CP-44307 @CP-44307-02 @API-ECMS @burak
  Scenario: Verify mail flag is true if mail letter data created at the request time (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | mailDestination | random active address |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | MailMyWy          |
      | caseId                          | previouslyCreated |
      | recipients                      | random            |
      | channel                         | Mail              |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I verify "previouslyCreated" letter data contains mail flag as "true"
    And Wait for 3 seconds
    And I should see that the mailFlag for "LETTER_DATA_SAVE_EVENT" exists as "true"

  @CP-42258 @CP-42258-04 @API-ECMS @burak
  Scenario: Verify there is no mail flag for Letter Data and Letter Data Save Event if the channel is not Mail (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Fax,Email,Text"
    And I verify "previouslyCreated" letter data contains mail flag as "NO_MAIL_FLAG"
    And Wait for 3 seconds
    And I should see that the mailFlag for "LETTER_DATA_SAVE_EVENT" exists as "NO_MAIL_FLAG"

  @CP-42257 @CP-42257-04DCEB @CP-42258 @CP-42258-05 @api-ecms-dceb @burak
  Scenario Outline: Verify when mandatory mail channel has unusable destination , Reason is Paperless Preference (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "42258-09" with following payload
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
    When I initiate and run Sync Contacts API
    When I initiate contacts V2 API
    When I add this contact optin "<optIn>" in to the payload
    And I send request to consumer reported V2 API
    When I run reevaluatepi API for V_2
    When I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | <MMSCode>         |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | writtenLanguage                 | null              |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a "Mail" notification that has the most updated contact information with the following values
      | firstName   | from caseCorrespondence API |
      | lastName    | from caseCorrespondence API |
      | role        | from caseCorrespondence API |
      | consumerId  | from caseCorrespondence API |
      | destination | from caseCorrespondence API |
    And I should see the Outbound Correspondence Notification for "mail" channel is created with the following values
      | status       | <expectedStatus>       |
      | statusReason | <expectedStatusReason> |
    And I verify "previouslyCreated" letter data contains mail flag as "<mailFlag>"
    And I should see that the mailFlag for "LETTER_DATA_SAVE_EVENT" exists as "<mailFlag>"
    Examples:
      | MMSCode  | expectedStatus | expectedStatusReason | mailFlag       | optIn|
      | MailMyWy | Requested      | null                 | true           | mail  |
      | MailMnWy | Requested      | Paperless Preference | false          | phone |
      | MailMyWn | Requested      | null                 | true           | mail  |
      | MailMnWn | Precluded      | Paperless Preference | no letter data | phone |

#  @CP-42257 @CP-42257-05 @CP-42258 @CP-42258-06 @api-ecms-dceb @burak # currently failing due toCP-44688
  Scenario Outline: Verify when mandatory mail channel has unusable destination , Reason is Consumer opted out (DC-EB)
             #Verify Mail Flag for Letter Data and Letter Data Save Event
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "42257-05" with following payload
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
    ##Need to opt out consumer from mail channel for this scenario
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | <MMSCode>         |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    When I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a "Mail" notification that has the most updated contact information with the following values
      | firstName   | from caseCorrespondence API |
      | lastName    | from caseCorrespondence API |
      | role        | from caseCorrespondence API |
      | consumerId  | from caseCorrespondence API |
      | destination | from caseCorrespondence API |
    And I should see the Outbound Correspondence Notification for "mail" channel is created with the following values
      | status       | <expectedStatus>       |
      | statusReason | <expectedStatusReason> |
    And I verify "previouslyCreated" letter data contains mail flag as "<mailFlag>"
    And I should see that the mailFlag for "LETTER_DATA_SAVE_EVENT" exists as "<mailFlag>"
    Examples:
      | MMSCode  | expectedStatus | expectedStatusReason | mailFlag       |
      | MailMyWy | Requested      | Consumer opted out   | false          |
      | MailMnWy | Requested      | Consumer opted out   | false          |
      | MailMyWn | Precluded      | Consumer opted out   | no letter data |
      | MailMnWn | Precluded      | Consumer opted out   | no letter data |

  @CP-42258 @CP-42258-08 @api-ecms-dceb @burak
  Scenario: Verify mail flag is true if mail letter data created at the request time (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "42258-08" with following payload
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
      | correspondenceDefinitionMMSCode | MailMyWy          |
      | caseId                          | previouslyCreated |
      | recipients                      | random            |
      | channel                         | Mail              |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I verify "previouslyCreated" letter data contains mail flag as "true"
    And Wait for 3 seconds
    And I should see that the mailFlag for "LETTER_DATA_SAVE_EVENT" exists as "true"

  @CP-42258 @CP-42258-09 @api-ecms-dceb @burak
  Scenario: Verify there is no mail flag for Letter Data and Letter Data Save Event if the channel is not Mail (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "42258-09" with following payload
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
      | channel                         | Fax,Email,Text    |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I verify "previouslyCreated" letter data contains mail flag as "NO_MAIL_FLAG"
    And Wait for 3 seconds
    And I should see that the mailFlag for "LETTER_DATA_SAVE_EVENT" exists as "NO_MAIL_FLAG"

  @CP-44307 @CP-44307-03 @CP-44137 @api-ecms-dceb @burak
  Scenario Outline: Verify when mandatory mail channel has unusable destination , Reason is No active destination found (DC-EB)
       #Verify Mail Flag for Letter Data and Letter Data Save Event
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "CP-44307" with following payload
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
    When I initiate and run Sync Contacts API without Mailing address
    When I run reevaluatepi API for V_2
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | <MMSCode>         |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    When I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I should see the Outbound Correspondence Notification for "mail" channel is created with the following values
      | status       | <expectedStatus>       |
      | statusReason | <expectedStatusReason> |
    And I verify "previouslyCreated" letter data contains mail flag as "<mailFlag>"
    And Wait for 3 seconds
    And I should see that the mailFlag for "LETTER_DATA_SAVE_EVENT" exists as "<mailFlag>"
    Examples:
      | MMSCode  | expectedStatus | expectedStatusReason        | mailFlag       |
      | MailMyWy | Requested      | No active destination found | false          |
      | MailMnWy | Requested      | No active destination found | false          |
      | MailMyWn | Precluded      | No active destination found | no letter data |
      | MailMnWn | Precluded      | No active destination found | no letter data |