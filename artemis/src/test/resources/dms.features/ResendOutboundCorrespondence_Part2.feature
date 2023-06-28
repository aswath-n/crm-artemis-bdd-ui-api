Feature:Resend Outbound Correspondence from UI

  @CP-3179 @CP-3179-01 @ui-ecms1 @Keerthi
  Scenario: validate Resend notification Channel, Language and Destination dropdown values for Mail and Text channel (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I have active languages and channels for "allChCase2"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual            |
      | mailDestination | random active mailing address |
      | textDestination | random active address         |
      | writtenLanguage | English                       |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase2         |
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
    Then I validated for "Requested" notification status
    And I click on notification actions dropdown to select "RESEND"
    Then I validate following values for Add or Resend Notification
      | ChannelList                         | activechannels                              |
      | SelectChannel                       | Mail                                        |
      | LanguageList                        | Mail                                        |
      | AddNotification_MailDestinationList | Active Destinations from casecorrespondence |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                       | Text                                        |
      | LanguageList                        | Text                                        |
      | AddNotification_TextDestinationList | Active Destinations from casecorrespondence |

  @CP-3179 @CP-3179-02 @ui-ecms1 @Keerthi
  Scenario: validate Resend notification Channel, Language and Destination dropdown values for Email and Fax channel (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I have active languages and channels for "allChCase2"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | faxDestination   | random active address |
      | writtenLanguage  | English               |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase2         |
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
    And I click on notification actions dropdown to select "RESEND"
    Then I validate following values for Add or Resend Notification
      | SelectChannel                        | Email                                       |
      | LanguageList                         | Email                                       |
      | AddNotification_EmailDestinationList | Active Destinations from casecorrespondence |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                      | Fax                                         |
      | LanguageList                       | Fax                                         |
      | AddNotification_FaxDestinationList | Active Destinations from casecorrespondence |

  @CP-3179 @CP-3179-03 @ui-ecms1 @Keerthi
  Scenario: validate Resend notification Other Text Destination required message (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase2         |
      | caseId                          | previouslyCreated |
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
    And I click on notification actions dropdown to select "RESEND"
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

  @CP-3179 @CP-3179-04 @ui-ecms1 @Keerthi
  Scenario: validate Resend notification Other Fax Destination required message (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase2         |
      | caseId                          | previouslyCreated |
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
    And I click on notification actions dropdown to select "RESEND"
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

  @CP-3179 @CP-3179-05 @ui-ecms1 @Keerthi
  Scenario: validate Resend notification Other Email Destination required message (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase2         |
      | caseId                          | previouslyCreated |
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
    And I click on notification actions dropdown to select "RESEND"
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

  @CP-3179 @CP-3179-06 @ui-ecms1 @Keerthi
  Scenario: validate Resend notification Other Mail Destination required message (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase2         |
      | caseId                          | previouslyCreated |
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
    And I click on notification actions dropdown to select "RESEND"
    Then I validate following values for Add or Resend Notification
      | SelectChannel     | Mail  |
      | SelectDestination | Other |
    And I click save for notification
    Then I validate following required fields warning msgs for Add or Resend Notification
      | ADDRESS LINE 1 | ADDRESS LINE 1 is required and cannot be left blank |
      | City           | CITY is required and cannot be left blank           |
      | STATE          | STATE is required and cannot be left blank          |
      | ZIP CODE       | ZIP CODE is required and cannot be left blank       |

  @CP-3179 @CP-3179-07 @ui-ecms1 @Keerthi
  Scenario: validate Inactive values not displayed and recipient address displays in destination dropdown values for all channels (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual |
      | writtenLanguage | English            |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase2         |
      | caseId                          | previouslyCreated |
      | consumerId                      | previouslyCreated |
      | firstName                       | test fn           |
      | lastName                        | test ln           |
      | channelType                     | Email             |
      | city                            | Austin            |
      | state                           | TX                |
      | zipcode                         | 78701             |
      | streetAddress                   | test lane 1       |
      | streetAddionalLine1             | test lane 2       |
      | emailAddress                    | test@gmail.com    |
      | faxNumber                       | 9899699699        |
      | textNumber                      | 9899699691        |
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I click on notification actions dropdown to select "RESEND"
    Then I validate following values for Add or Resend Notification
      | SelectChannel                       | Mail                                          |
      | AddNotification_MailDestinationList | test lane 1 test lane 2 Austin TX 78701,Other |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                        | Email                |
      | AddNotification_EmailDestinationList | test@gmail.com,Other |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                       | Text             |
      | AddNotification_TextDestinationList | 9899699691,Other |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                      | Fax              |
      | AddNotification_FaxDestinationList | 9899699699,Other |

  @CP-3179 @CP-3179-08 @ui-ecms1 @Keerthi
  Scenario: Verify if the destination values in the request are NOT blank and one destination is different in the existing recipient record then a new recipient record is created (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual |
      | writtenLanguage | English            |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase2         |
      | caseId                          | previouslyCreated |
      | consumerId                      | previouslyCreated |
      | firstName                       | test              |
      | lastName                        | test              |
      | channelType                     | Text              |
      | textNumber                      | 9899699691        |
      | emailAddress                    | test@gmail.com    |
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I click on notification actions dropdown to select "RESEND"
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

  @CP-3179 @CP-3179-09 @ui-ecms1 @Keerthi
  Scenario: Verify if a matching destination already exists for the selected channel then a new recipient record is not created or updated (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual |
      | writtenLanguage | English            |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase2         |
      | caseId                          | previouslyCreated |
      | consumerId                      | previouslyCreated |
      | firstName                       | test              |
      | lastName                        | test              |
      | channelType                     | Text              |
      | textNumber                      | 9899699691        |
      | emailAddress                    | test@gmail.com    |
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I click on notification actions dropdown to select "RESEND"
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

  @CP-3179 @CP-3179-10 @ui-ecms1 @Keerthi
  Scenario: Verify if the destination values in the request are blank in the existing recipient record then the recipient record is updated (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual |
      | writtenLanguage | English            |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase2         |
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
    And I click on notification actions dropdown to select "RESEND"
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

  @CP-3179 @CP-3179-11 @ui-ecms-ineb @Keerthi
  Scenario: validate Resend notification Channel, Language and Destination dropdown values for Mail and Text channel (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then I have active languages and channels for "allChCase"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole                 | Primary Individual            |
      | caseIdentificationNumberType | State                         |
      | mailDestination              | random active mailing address |
      | textDestination              | random active address         |
      | writtenLanguage              | English                       |
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
    And I click on notification actions dropdown to select "RESEND"
    Then I validate following values for Add or Resend Notification
      | ChannelList                         | activechannels                              |
      | SelectChannel                       | Mail                                        |
      | LanguageList                        | Mail                                        |
      | AddNotification_MailDestinationList | Active Destinations from casecorrespondence |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                       | Text                                        |
      | LanguageList                        | Text                                        |
      | AddNotification_TextDestinationList | Active Destinations from casecorrespondence |

  @CP-3179 @CP-3179-12 @ui-ecms-ineb @Keerthi
  Scenario: validate Resend notification Channel, Language and Destination dropdown values for Email and Fax channel (In-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then I have active languages and channels for "allChCase"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole                 | Primary Individual    |
      | caseIdentificationNumberType | State                 |
      | emailDestination             | random active address |
      | faxDestination               | random active address |
      | writtenLanguage              | English               |
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
    And I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I click on notification actions dropdown to select "RESEND"
    Then I validate following values for Add or Resend Notification
      | SelectChannel                        | Email                                       |
      | LanguageList                         | Email                                       |
      | AddNotification_EmailDestinationList | Active Destinations from casecorrespondence |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                      | Fax                                         |
      | LanguageList                       | Fax                                         |
      | AddNotification_FaxDestinationList | Active Destinations from casecorrespondence |

  @CP-3179 @CP-3179-13 @ui-ecms-ineb @Keerthi
  Scenario: validate Inactive values not displayed and recipient address displays in destination dropdown values for all channels (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole                 | Primary Individual |
      | caseIdentificationNumberType | State              |
      | writtenLanguage              | English            |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | consumerId                      | previouslyCreated |
      | firstName                       | test fn           |
      | lastName                        | test ln           |
      | channelType                     | Email             |
      | city                            | Austin            |
      | state                           | TX                |
      | zipcode                         | 78701             |
      | streetAddress                   | test lane 1       |
      | streetAddionalLine1             | test lane 2       |
      | emailAddress                    | test@gmail.com    |
      | faxNumber                       | 9899699699        |
      | textNumber                      | 9899699691        |
    And I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I click on notification actions dropdown to select "RESEND"
    Then I validate following values for Add or Resend Notification
      | SelectChannel                       | Mail                                          |
      | AddNotification_MailDestinationList | test lane 1 test lane 2 Austin TX 78701,Other |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                        | Email                |
      | AddNotification_EmailDestinationList | test@gmail.com,Other |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                       | Text             |
      | AddNotification_TextDestinationList | 9899699691,Other |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                      | Fax              |
      | AddNotification_FaxDestinationList | 9899699699,Other |

  @CP-3179 @CP-3179-14 @ui-ecms-ineb @Keerthi
  Scenario: Verify if the destination values in the request are NOT blank and one destination is different in the existing recipient record then a new recipient record is created (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole                 | Primary Individual |
      | caseIdentificationNumberType | State              |
      | writtenLanguage              | English            |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | consumerId                      | previouslyCreated |
      | firstName                       | test              |
      | lastName                        | test              |
      | channelType                     | Text              |
      | textNumber                      | 9899699691        |
      | emailAddress                    | test@gmail.com    |
    And I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I click on notification actions dropdown to select "RESEND"
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

  @CP-3179 @CP-3179-15 @ui-ecms-ineb @Keerthi
  Scenario: Verify if a matching destination already exists for the selected channel then a new recipient record is not created or updated (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole                 | Primary Individual |
      | caseIdentificationNumberType | State              |
      | writtenLanguage              | English            |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | consumerId                      | previouslyCreated |
      | firstName                       | test              |
      | lastName                        | test              |
      | channelType                     | Text              |
      | textNumber                      | 9899699691        |
      | emailAddress                    | test@gmail.com    |
    And I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I click on notification actions dropdown to select "RESEND"
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

  @CP-3179 @CP-3179-16 @ui-ecms-ineb @Keerthi
  Scenario: Verify if the destination values in the request are blank in the existing recipient record then the recipient record is updated (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole                 | Primary Individual |
      | caseIdentificationNumberType | State              |
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
    And I click on notification actions dropdown to select "RESEND"
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

  @CP-3179 @CP-3179-17 @ui-ecms-dceb @Keerthi
  Scenario: validate Resend notification Channel, Language and Destination dropdown values for each channel (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Then I have active languages and channels for "allChCase"
    And I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | 4823 |
    When I click on the "first available" Outbound Correspondence
    And I click on notification actions dropdown to select "RESEND"
    Then I validate following values for Add or Resend Notification
      | ChannelList                         | activechannels   |
      | SelectChannel                       | Text             |
      | LanguageList                        | Text             |
      | AddNotification_TextDestinationList | 3435370261,Other |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                        | Email                         |
      | LanguageList                         | Email                         |
      | AddNotification_EmailDestinationList | Primary test3@yahoo.com,Other |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                       | Mail                                          |
      | LanguageList                        | Mail                                          |
      | AddNotification_MailDestinationList | Mailing 123 Street New York Texas 12435,Other |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                      | Fax              |
      | LanguageList                       | Fax              |
      | AddNotification_FaxDestinationList | 7543536357,Other |


  @CP-3179 @CP-3179-18 @ui-ecms-dceb @Keerthi
  Scenario: validate Inactive values not displayed in Resend Notification Destination dropdown values for all channels (DC-EB)
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
      | channel                         | Fax        |
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
      | SelectChannel                       | Mail  |
      | AddNotification_MailDestinationList | Other |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                        | Email |
      | AddNotification_EmailDestinationList | Other |
    Then I validate following values for Add or Resend Notification
      | SelectChannel                       | Text  |
      | AddNotification_TextDestinationList | Other |

  @CP-3179 @CP-3179-19 @ui-ecms-dceb @Keerthi
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
    And I click on notification actions dropdown to select "RESEND"
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

  @CP-3179 @CP-3179-20 @ui-ecms-dceb @Keerthi
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
    And I click on notification actions dropdown to select "RESEND"
    Then I validate following values for Add or Resend Notification
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

  @CP-3179 @CP-3179-21  @ui-ecms-dceb @Keerthi
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
    And I click on notification actions dropdown to select "RESEND"
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
