Feature:Add Notification from UI

  @CP-43365 @CP-43365-01 @ui-ecms2 @Keerthi
  Scenario: validate Add notification not displayed for Canceled OutboundCorrespondence (BLCRM)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual            |
      | mailDestination | random active mailing address |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | MailMnWn          |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I update Outbound Correspondence CId "previouslyCreated" to status "Canceled"
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate Add Notification button is "not displayed"

  @CP-43365 @CP-43365-02 @ui-ecms2 @Keerthi
  Scenario: validate Add notification displayed for Precluded Notification (BLCRM)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole            | Primary Individual |
      | mailDestination         | null               |
      | consentType_OptIn_false | Mail               |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | MailMnWn          |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate Add Notification button is "1 displayed"

  @CP-43365 @CP-43365-03 @ui-ecms2 @Keerthi
  Scenario: validate Add notification displayed for multiple recipients (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | mailDestination  | random active address |
      | writtenLanguage  | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | emailAddress                    | email2            |
      | recipients                      | random            |
      | recipients2                     | email             |
      | channel                         | Email,Mail        |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate Add Notification button is "2 displayed"

  @CP-43365 @CP-43365-04 @ui-ecms2 @Keerthi
  Scenario: validate Add notification Channel, Language and Destination dropdown values for Mail channel (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I have active languages and channels for "allChCase"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual            |
      | mailDestination | random active mailing address |
      | writtenLanguage | English                       |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | consumerId                      | previouslyCreated |
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
    And I validate Add Notification button is "clicked"
    Then I validate following values for Add or Resend Notification
      | ChannelList                         | activechannels                              |
      | SelectChannel                       | Mail                                        |
      | LanguageList                        | Mail                                        |
      | AddNotification_MailDestinationList | Active Destinations from casecorrespondence |

  @CP-43365 @CP-43365-05 @ui-ecms2 @Keerthi
  Scenario: validate Add notification Channel, Language and Destination dropdown values for Email channel (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I have active languages and channels for "allChCase"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | writtenLanguage  | English               |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | consumerId                      | previouslyCreated |
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
    And I validate Add Notification button is "clicked"
    Then I validate following values for Add or Resend Notification
      | SelectChannel                        | Email                                       |
      | LanguageList                         | Email                                       |
      | AddNotification_EmailDestinationList | Active Destinations from casecorrespondence |


  @CP-43365 @CP-43365-06 @ui-ecms2 @Keerthi
  Scenario: validate Add notification Channel, Language and Destination dropdown values for Text channel (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I have active languages and channels for "allChCase"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | textDestination | random active address |
      | writtenLanguage | English               |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | consumerId                      | previouslyCreated |
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
    And I validate Add Notification button is "clicked"
    Then I validate following values for Add or Resend Notification
      | SelectChannel                       | Text                                        |
      | LanguageList                        | Text                                        |
      | AddNotification_TextDestinationList | Active Destinations from casecorrespondence |

  @CP-43365 @CP-43365-07 @ui-ecms2 @Keerthi
  Scenario: validate Add notification Channel, Language and Destination dropdown values for Fax channel (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I have active languages and channels for "allChCase"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | faxDestination  | random active address |
      | writtenLanguage | English               |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | consumerId                      | previouslyCreated |
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
    And I validate Add Notification button is "clicked"
    Then I validate following values for Add or Resend Notification
      | SelectChannel                      | Fax                                         |
      | LanguageList                       | Fax                                         |
      | AddNotification_FaxDestinationList | Active Destinations from casecorrespondence |

  @CP-43365 @CP-43365-08 @ui-ecms2 @Keerthi
  Scenario: validate Add notification Other Text Destination required message (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
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
    And I validate Add Notification button is "clicked"
    Then I validate following values for Add or Resend Notification
      | SelectChannel     | Text  |
      | SelectDestination | Other |
    And I click save for notification
    Then I validate following required fields warning msgs for Add or Resend Notification
      | Phone Number | Phone Number is required cannot be left blank |
    Then I validate following values for Add or Resend Notification
      | OtherPhoneNumber | 567 |
    And I click save for notification
    Then I validate following required fields warning msgs for Add or Resend Notification
      | PhoneNumberInvalidLength | PHONE must be 10 characters |
    Then I validate following values for Add or Resend Notification
      | OtherPhoneNumber | 0567454646 |
    And I click save for notification
    Then I validate following required fields warning msgs for Add or Resend Notification
      | PhoneNumberInvalidFormat | phone number should not begin with 0 or 1 |
    Then I validate following values for Add or Resend Notification
      | OtherPhoneNumber | 1567454646 |
    And I click save for notification
    Then I validate following required fields warning msgs for Add or Resend Notification
      | PhoneNumberInvalidFormat | phone number should not begin with 0 or 1 |
    Then I validate following values for Add or Resend Notification
      | OtherPhoneNumber | 5670454646 |
    And I click save for notification
    Then I validate following required fields warning msgs for Add or Resend Notification
      | PhoneNumberInvalidFormat | Fourth digit cannot be 0 or 1 |
    Then I validate following values for Add or Resend Notification
      | OtherPhoneNumber | 5671454646 |
    And I click save for notification
    Then I validate following required fields warning msgs for Add or Resend Notification
      | PhoneNumberInvalidFormat | Fourth digit cannot be 0 or 1 |

  @CP-43365 @CP-43365-09 @ui-ecms2 @Keerthi
  Scenario: validate Add notification Other Fax Destination required message (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
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
    And I validate Add Notification button is "clicked"
    Then I validate following values for Add or Resend Notification
      | SelectChannel     | Fax   |
      | SelectDestination | Other |
    And I click save for notification
    Then I validate following required fields warning msgs for Add or Resend Notification
      | Phone Number | Phone Number is required cannot be left blank |
    Then I validate following values for Add or Resend Notification
      | OtherFaxNumber | 567 |
    And I click save for notification
    Then I validate following required fields warning msgs for Add or Resend Notification
      | PhoneNumberInvalidLength | PHONE must be 10 characters |
    Then I validate following values for Add or Resend Notification
      | OtherFaxNumber | 0567454646 |
    And I click save for notification
    Then I validate following required fields warning msgs for Add or Resend Notification
      | PhoneNumberInvalidFormat | phone number should not begin with 0 or 1 |
    Then I validate following values for Add or Resend Notification
      | OtherFaxNumber | 1567454646 |
    And I click save for notification
    Then I validate following required fields warning msgs for Add or Resend Notification
      | PhoneNumberInvalidFormat | phone number should not begin with 0 or 1 |
    Then I validate following values for Add or Resend Notification
      | OtherFaxNumber | 5670454646 |
    And I click save for notification
    Then I validate following required fields warning msgs for Add or Resend Notification
      | PhoneNumberInvalidFormat | Fourth digit cannot be 0 or 1 |
    Then I validate following values for Add or Resend Notification
      | OtherFaxNumber | 5671454646 |
    And I click save for notification
    Then I validate following required fields warning msgs for Add or Resend Notification
      | PhoneNumberInvalidFormat | Fourth digit cannot be 0 or 1 |

  @CP-43365 @CP-43365-10 @ui-ecms2 @Keerthi
  Scenario: validate Add notification Other Email Destination required message (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
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
    And I validate Add Notification button is "clicked"
    Then I validate following values for Add or Resend Notification
      | SelectChannel     | Email |
      | SelectDestination | Other |
    And I click save for notification
    Then I validate following required fields warning msgs for Add or Resend Notification
      | EMAIL | EMAIL is required and cannot be left blank |
    Then I validate following values for Add or Resend Notification
      | OtherEmail | abc |
    And I click save for notification
    Then I validate following required fields warning msgs for Add or Resend Notification
      | EmailInvalidFormat | EMAIL is not in the correct format |

  @CP-43365 @CP-43365-11 @ui-ecms2 @Keerthi
  Scenario: validate Add notification Other Mail Destination required message (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
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
    And I validate Add Notification button is "clicked"
    Then I validate following values for Add or Resend Notification
      | SelectChannel     | Mail  |
      | SelectDestination | Other |
    And I click save for notification
    Then I validate following required fields warning msgs for Add or Resend Notification
      | ADDRESS LINE 1 | ADDRESS LINE 1 is required and cannot be left blank |
      | City           | CITY is required and cannot be left blank           |
      | STATE          | STATE is required and cannot be left blank          |
      | ZIP CODE       | ZIP CODE is required and cannot be left blank       |

  @CP-43365 @CP-43365-12 @ui-ecms2 @Keerthi
  Scenario: validate Inactive values not displayed in Destination dropdown values for all channels (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual |
      | writtenLanguage | English            |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | consumerId                      | previouslyCreated |
      | firstName                       | test fn           |
      | lastName                        | test ln           |
      | channelType                     | Email             |
      | emailAddress                    | test@gmail.com    |
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate Add Notification button is "clicked"
    Then I validate following values for Add or Resend Notification
      | SelectChannel                       | Mail  |
      | AddNotification_MailDestinationList | Other |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                        | Email |
      | AddNotification_EmailDestinationList | Other |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                       | Text  |
      | AddNotification_TextDestinationList | Other |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                      | Fax   |
      | AddNotification_FaxDestinationList | Other |

  @CP-43365 @CP-43365-13 @ui-ecms2 @Keerthi
  Scenario: Verify if the destination values in the request are NOT blank and one destination is different in the existing recipient record then a new recipient record is created (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual |
      | writtenLanguage | English            |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | consumerId                      | previouslyCreated |
      | firstName                       | test              |
      | lastName                        | test              |
      | channelType                     | Email             |
      | emailAddress                    | test@gmail.com    |
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate Add Notification button is "clicked"
    Then I validate following values for Add or Resend Notification
      | SelectChannel     | Email   |
      | SelectLanguage    | English |
      | SelectDestination | Other   |
    Then I validate following values for Add or Resend Notification
      | OtherEmail | reg@gmail.com |
    And I click save for notification
    And I validate Add Notification button is "2 displayed"
    And I copied the following nids
      | notificationId | previouslyCreated |
    And I should see that following events are produced to DPBI
      | CORRESPONDENCE_RECIPIENT_SAVE_EVENT | previouslyCreated |
      | NOTIFICATION_SAVE_EVENT             | previouslyCreated |
      | LETTER_DATA_SAVE_EVENT              | previouslyCreated |

  @CP-43365 @CP-43365-14 @ui-ecms2 @Keerthi
  Scenario: Verify if a matching destination already exists for the selected channel then a new recipient record is not created or updated (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual |
      | writtenLanguage | English            |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | consumerId                      | previouslyCreated |
      | firstName                       | test              |
      | lastName                        | test              |
      | channelType                     | Email             |
      | emailAddress                    | test@gmail.com    |
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate Add Notification button is "clicked"
    Then I validate following values for Add or Resend Notification
      | SelectChannel     | Email   |
      | SelectLanguage    | English |
      | SelectDestination | Other   |
    Then I validate following values for Add or Resend Notification
      | OtherEmail | test@gmail.com |
    And I click save for notification
    And I validate Add Notification button is "1 displayed"
    And I copied the following nids
      | notificationId | Email |
    And I should see that following events are produced to DPBI
      | NOTIFICATION_SAVE_EVENT | previouslyCreated |
      | LETTER_DATA_SAVE_EVENT  | previouslyCreated |

  @CP-43365 @CP-43365-15 @ui-ecms2 @Keerthi
  Scenario: Verify if the destination values in the request are blank in the existing recipient record then the recipient record is updated (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual |
      | writtenLanguage | English            |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | consumerId                      | previouslyCreated |
      | firstName                       | test              |
      | lastName                        | test              |
      | channelType                     | Text              |
      | textNumber                      | 9899699691        |
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate Add Notification button is "clicked"
    Then I validate following values for Add or Resend Notification
      | SelectChannel     | Email   |
      | SelectLanguage    | English |
      | SelectDestination | Other   |
    Then I validate following values for Add or Resend Notification
      | OtherEmail | reg@gmail.com |
    And I click save for notification
    And I validate Add Notification button is "1 displayed"
    And I copied the following nids
      | notificationId | Email |
    And I should see that following events are produced to DPBI
      | CORRESPONDENCE_RECIPIENT_UPDATE_EVENT | previouslyCreated |
      | NOTIFICATION_SAVE_EVENT               | previouslyCreated |
      | LETTER_DATA_SAVE_EVENT                | previouslyCreated |

    ################################## IN-EB #############################################

  @CP-43365 @CP-43365-16 @ui-ecms-ineb @Keerthi
  Scenario: validate Add notification Channel, Language and Destination dropdown values each channel (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then I have active languages and channels for "allChCase"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | caseIdentificationNumberType | State                         |
      | consumerRole                 | Primary Individual            |
      | writtenLanguage              | English                       |
      | textDestination              | random active address         |
      | emailDestination             | random active address         |
      | mailDestination              | random active mailing address |
      | faxDestination               | random active address         |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | consumerId                      | previouslyCreated |
      | firstName                       | test              |
      | lastName                        | test              |
      | channelType                     | Email             |
      | emailAddress                    | test@gmail.com    |
    And I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate Add Notification button is "clicked"
    Then I validate following values for Add or Resend Notification
      | ChannelList                         | activechannels                              |
      | SelectChannel                       | Mail                                        |
      | LanguageList                        | Mail                                        |
      | AddNotification_MailDestinationList | Active Destinations from casecorrespondence |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                        | Email                                       |
      | LanguageList                         | Email                                       |
      | AddNotification_EmailDestinationList | Active Destinations from casecorrespondence |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                      | Fax                                         |
      | LanguageList                       | Fax                                         |
      | AddNotification_FaxDestinationList | Active Destinations from casecorrespondence |

  @CP-43365 @CP-43365-17 @ui-ecms-ineb @Keerthi
  Scenario: validate Inactive values not displayed in Destination dropdown values for all channels (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | caseIdentificationNumberType | State              |
      | consumerRole                 | Primary Individual |
      | writtenLanguage              | English            |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | consumerId                      | previouslyCreated |
      | firstName                       | test              |
      | lastName                        | test              |
      | channelType                     | Email             |
      | emailAddress                    | test@gmail.com    |
    And I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate Add Notification button is "clicked"
    Then I validate following values for Add or Resend Notification
      | SelectChannel                       | Mail  |
      | AddNotification_MailDestinationList | Other |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                        | Email |
      | AddNotification_EmailDestinationList | Other |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                       | Text  |
      | AddNotification_TextDestinationList | Other |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                      | Fax   |
      | AddNotification_FaxDestinationList | Other |

  @CP-43365 @CP-43365-18 @ui-ecms-ineb @Keerthi
  Scenario: Verify if the destination values in the request are NOT blank and one destination is different in the existing recipient record then a new recipient record is created (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | caseIdentificationNumberType | State                         |
      | consumerRole                 | Primary Individual            |
      | writtenLanguage              | English                       |
      | textDestination              | random active address         |
      | emailDestination             | random active address         |
      | mailDestination              | random active mailing address |
      | faxDestination               | random active address         |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | consumerId                      | previouslyCreated |
      | firstName                       | test              |
      | lastName                        | test              |
      | channelType                     | Email             |
      | emailAddress                    | test@gmail.com    |
    And I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate Add Notification button is "clicked"
    Then I validate following values for Add or Resend Notification
      | SelectChannel     | Email   |
      | SelectLanguage    | English |
      | SelectDestination | Other   |
    Then I validate following values for Add or Resend Notification
      | OtherEmail | reg@gmail.com |
    And I click save for notification
    And I validate Add Notification button is "2 displayed"
    And I copied the following nids
      | notificationId | previouslyCreated |
    And I should see that following events are produced to DPBI
      | CORRESPONDENCE_RECIPIENT_SAVE_EVENT | previouslyCreated |
      | NOTIFICATION_SAVE_EVENT             | previouslyCreated |
      | LETTER_DATA_SAVE_EVENT              | previouslyCreated |

  @CP-43365 @CP-43365-19 @ui-ecms-ineb @Keerthi
  Scenario: Verify if a matching destination already exists for the selected channel then a new recipient record is not created or updated (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | caseIdentificationNumberType | State              |
      | consumerRole                 | Primary Individual |
      | writtenLanguage              | English            |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | consumerId                      | previouslyCreated |
      | firstName                       | test              |
      | lastName                        | test              |
      | channelType                     | Email             |
      | emailAddress                    | test@gmail.com    |
    And I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate Add Notification button is "clicked"
    Then I validate following values for Add or Resend Notification
      | SelectChannel     | Email   |
      | SelectLanguage    | English |
      | SelectDestination | Other   |
    Then I validate following values for Add or Resend Notification
      | OtherEmail | test@gmail.com |
    And I click save for notification
    And I validate Add Notification button is "1 displayed"
    And I copied the following nids
      | notificationId | Email |
    And I should see that following events are produced to DPBI
      | NOTIFICATION_SAVE_EVENT | previouslyCreated |
      | LETTER_DATA_SAVE_EVENT  | previouslyCreated |

  @CP-43365 @CP-43365-20 @ui-ecms-ineb @Keerthi
  Scenario: Verify if the destination values in the request are blank in the existing recipient record then the recipient record is updated (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | caseIdentificationNumberType | State              |
      | consumerRole                 | Primary Individual |
      | writtenLanguage              | English            |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | consumerId                      | previouslyCreated |
      | firstName                       | test              |
      | lastName                        | test              |
      | channelType                     | Text              |
      | textNumber                      | 9899699691        |
    And I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate Add Notification button is "clicked"
    Then I validate following values for Add or Resend Notification
      | SelectChannel     | Email   |
      | SelectLanguage    | English |
      | SelectDestination | Other   |
    Then I validate following values for Add or Resend Notification
      | OtherEmail | reg@gmail.com |
    And I click save for notification
    And I validate Add Notification button is "1 displayed"
    And I copied the following nids
      | notificationId | Email |
    And I should see that following events are produced to DPBI
      | CORRESPONDENCE_RECIPIENT_UPDATE_EVENT | previouslyCreated |
      | NOTIFICATION_SAVE_EVENT               | previouslyCreated |
      | LETTER_DATA_SAVE_EVENT                | previouslyCreated |

     ################################## DC-EB #############################################

  @CP-43365 @CP-43365-21 @ui-ecms-dceb @Keerthi
  Scenario: validate Add notification Channel, Language and Destination dropdown values each channel (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Then I have active languages and channels for "allChCase"
    And I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | 4705 |
    When I click on the "first available" Outbound Correspondence
    And I validate Add Notification button is "clicked"
    Then I validate following values for Add or Resend Notification
      | ChannelList                         | activechannels                                |
      | SelectChannel                       | Mail                                          |
      | LanguageList                        | Mail                                          |
      | AddNotification_MailDestinationList | Mailing 123 Street New York Texas 12435,Other |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                        | Email                         |
      | LanguageList                         | Email                         |
      | AddNotification_EmailDestinationList | Primary test3@yahoo.com,Other |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                      | Text             |
      | LanguageList                       | Text             |
      | AddNotification_FaxDestinationList | 3435370261,Other |

  @CP-43365 @CP-43365-22 @ui-ecms-dceb @Keerthi
  Scenario: validate Inactive values not displayed in Destination dropdown values for all channels (DC-EB)
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
    And I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate Add Notification button is "clicked"
    Then I validate following values for Add or Resend Notification
      | SelectChannel                       | Mail  |
      | AddNotification_MailDestinationList | Other |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                        | Email |
      | AddNotification_EmailDestinationList | Other |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                       | Text  |
      | AddNotification_TextDestinationList | Other |

  @CP-43365 @CP-43365-23 @ui-ecms-dceb @Keerthi
  Scenario: Verify if the destination values in the request are NOT blank and one destination is different in the existing recipient record then a new recipient record is created (DC-EB)
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
    And I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate Add Notification button is "clicked"
    Then I validate following values for Add or Resend Notification
      | SelectChannel     | Email   |
      | SelectLanguage    | English |
      | SelectDestination | Other   |
    Then I validate following values for Add or Resend Notification
      | OtherEmail | reg@gmail.com |
    And I click save for notification
    And I validate Add Notification button is "2 displayed"
    And I copied the following nids
      | notificationId | previouslyCreated |
    And I should see that following events are produced to DPBI
      | CORRESPONDENCE_RECIPIENT_SAVE_EVENT | previouslyCreated |
      | NOTIFICATION_SAVE_EVENT             | previouslyCreated |
      | LETTER_DATA_SAVE_EVENT              | previouslyCreated |

  @CP-43365 @CP-43365-24  @ui-ecms-dceb @Keerthi
  Scenario: Verify if a matching destination already exists for the selected channel then a new recipient record is not created or updated (DC-EB)
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
      | correspondenceDefinitionMMSCode | allChCase          |
      | caseId                          | DCEBcaseid         |
      | recipients                      | random             |
      | channel                         | Email              |
      | emailAddress                    | recipient@test.com |
      | language                        | English            |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate Add Notification button is "clicked"
    Then I validate following values for Add or Resend Notification
      | SelectChannel     | Email   |
      | SelectLanguage    | English |
      | SelectDestination | Other   |
    Then I validate following values for Add or Resend Notification
      | OtherEmail | recipient@test.com |
    And I click save for notification
    And I validate Add Notification button is "1 displayed"
    And I copied the following nids
      | notificationId | Email |
    And I should see that following events are produced to DPBI
      | NOTIFICATION_SAVE_EVENT | previouslyCreated |
      | LETTER_DATA_SAVE_EVENT  | previouslyCreated |

  @CP-43365 @CP-43365-25  @ui-ecms-dceb @Keerthi
  Scenario: Verify if the destination values in the request are blank in the existing recipient record then the recipient record is updated (DC-EB)
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
      | channel                         | Text       |
      | language                        | English    |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate Add Notification button is "clicked"
    Then I validate following values for Add or Resend Notification
      | SelectChannel     | Email   |
      | SelectLanguage    | English |
      | SelectDestination | Other   |
    Then I validate following values for Add or Resend Notification
      | OtherEmail | reg@gmail.com |
    And I click save for notification
    And I validate Add Notification button is "1 displayed"
    And I copied the following nids
      | notificationId | Email |
    And I should see that following events are produced to DPBI
      | CORRESPONDENCE_RECIPIENT_UPDATE_EVENT | previouslyCreated |
      | NOTIFICATION_SAVE_EVENT               | previouslyCreated |
      | LETTER_DATA_SAVE_EVENT                | previouslyCreated |


  @CP-42120 @CP-42120-01 @ui-ecms2 @James
  Scenario: validate language is NOT required for BLCRM
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual            |
      | mailDestination | random active mailing address |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | allChCase         |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I click on notification actions dropdown to select "RESEND"
    Then I validate following values for Add or Resend Notification
      | SelectChannel     | Email |
      | SelectDestination | Other |
    Then I validate following values for Add or Resend Notification
      | OtherEmail | reg@gmail.com |
    And I click save for notification
    And I copied the following nids
      | notificationId | Email |
    And I should see that following events are produced to DPBI
      | CORRESPONDENCE_RECIPIENT_UPDATE_EVENT | previouslyCreated |
      | NOTIFICATION_SAVE_EVENT               | previouslyCreated |
      | LETTER_DATA_SAVE_EVENT                | previouslyCreated |

  @CP-42120 @CP-42120-01INEB @ui-ecms-ineb @James
  Scenario: validate language is NOT required for INEB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual            |
      | mailDestination | random active mailing address |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | allChCase         |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I click on notification actions dropdown to select "RESEND"
    Then I validate following values for Add or Resend Notification
      | SelectChannel     | Email |
      | SelectDestination | Other |
    Then I validate following values for Add or Resend Notification
      | OtherEmail | reg@gmail.com |
    And I click save for notification
    And I copied the following nids
      | notificationId | Email |
    And I should see that following events are produced to DPBI
      | CORRESPONDENCE_RECIPIENT_UPDATE_EVENT | previouslyCreated |
      | NOTIFICATION_SAVE_EVENT               | previouslyCreated |
      | LETTER_DATA_SAVE_EVENT                | previouslyCreated |

  @CP-42120 @CP-42120-01DCEB @ui-ecms-dceb @James
  Scenario: validate language is required when resending a notification for DCEB
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
    And I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I click on notification actions dropdown to select "RESEND"
    Then I validate following values for Add or Resend Notification
      | SelectChannel     | Email |
      | SelectDestination | Other |
    Then I validate following values for Add or Resend Notification
      | OtherEmail | reg@gmail.com |
    And I click save for notification
    Then I should the save fail with error message "Language is required and cannot be left blank"

  @CP-42120 @CP-42120-02DCEB @ui-ecms-dceb @James
  Scenario: validate language is required when adding a notification for DCEB
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
    And I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate Add Notification button is "clicked"
    Then I validate following values for Add or Resend Notification
      | SelectChannel     | Email |
      | SelectDestination | Other |
    Then I validate following values for Add or Resend Notification
      | OtherEmail | reg@gmail.com |
    And I click save for notification
    Then I should the save fail with error message "Language is required and cannot be left blank"