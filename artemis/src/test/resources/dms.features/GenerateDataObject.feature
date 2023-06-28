Feature: Generate Data Object for Notification

  @CP-7320 @CP-7320-1 @API-ECMS @James
  Scenario: Verify the language specified on Correspondence Request is the language that is used to generate data object
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual            |
      | mailDestination | random active mailing address |
      | writtenLanguage | Spanish                       |
    And I the default language for the project "BLCRM" is "English"
    And I have a Outbound Correspondence with language specified as "Aleut"
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the language used is "Aleut" in the letter data that is generated

  @CP-7320 @CP-7320-2 @API-ECMS @James
  Scenario: Verify the language for the project is used when none is specified in request and on consumer profile
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual            |
      | mailDestination | random active mailing address |
      | writtenLanguage | null                          |
    And I the default language for the project "BLCRM" is "English"
    And I have a Outbound Correspondence with language specified as "null"
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the language used is "English" in the letter data that is generated

  @CP-7320 @CP-7320-3 @API-ECMS @James
  Scenario: Verify the language for the consumer is used when none is specified in request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual            |
      | mailDestination | random active mailing address |
      | writtenLanguage | Spanish                       |
    And I the default language for the project "BLCRM" is "English"
    And I have a Outbound Correspondence with language specified as "null"
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the language used is "Spanish" in the letter data that is generated

  @CP-7320 @CP-7320-4 @API-ECMS @James
  Scenario Outline: Verify the destination information is present in the letter data
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have a Outbound Correspondence with Random Destination information for "<channel>" channel
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the Random Destination information for "<channel>" channel is present in the letter data that is generated
    Examples:
      | channel |
      | Mail    |
      | Fax     |
      | Text    |
      | Email   |

  @CP-7320 @CP-7320-5 @API-ECMS @James
  Scenario: Verify the language template data is found for the channel that is selected
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual            |
      | mailDestination | random active mailing address |
      | writtenLanguage | Spanish                       |
    And I the default language for the project "BLCRM" is "English"
    And I have a Outbound Correspondence with language specified as "Aleut"
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the language template information for "Aleut" template is found on the letter data that is generated

    ########################################### CP-360000 ######################################################

  @CP-36000 @CP-36000-1 @ui-ecms2 @Keerthi
  Scenario: Verify the language field on the notification should be populated with the language specified on Correspondence Request is being fulfilled
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have a consumer that wants to create an Outbound Correspondence with a written language preference as "Spanish"
    And I the default language for the project "BLCRM" is "English"
    And I have a Outbound Correspondence with language specified as "Aleut"
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I stored details of each notification for "previouslyCreated" ob correspondence
    #Then I should see the language used is "Aleut" in the letter data that is generated
    Then I should see the language used is "Aleut" in the letter data that is generated for "Email" channel
    Then I should see the language used is "Aleut" in the letter data that is generated for "Text" channel
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I verify outbound correspondence NotificationId Language
      | previouslycreated_email_nid_language | Aleut     |
      | previouslycreated_email_nid_status   | Requested |
      | previouslycreated_text_nid_language  | Aleut     |
      | previouslycreated_text_nid_status    | Requested |


  @CP-36000 @CP-36000-2 @ui-ecms2 @Keerthi
  Scenario: Verify the language field on the notification should be populated with the language specified on Consumer is being fulfilled
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have a consumer that wants to create an Outbound Correspondence with a written language preference as "Spanish"
    And I the default language for the project "BLCRM" is "English"
    And I have a Outbound Correspondence with language specified as "null"
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I stored details of each notification for "previouslyCreated" ob correspondence
    Then I should see the language used is "Spanish" in the letter data that is generated for "Email" channel
    Then I should see the language used is "Spanish" in the letter data that is generated for "Text" channel
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I verify outbound correspondence NotificationId Language
      | previouslycreated_email_nid_language | Spanish   |
      | previouslycreated_email_nid_status   | Requested |
      | previouslycreated_text_nid_language  | Spanish   |
      | previouslycreated_text_nid_status    | Requested |


  @CP-36000 @CP-36000-3 @ui-ecms2 @Keerthi
  Scenario: Verify the language field on the notification should be populated with the language specified on default configuration is being fulfilled                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 fault project language is being fulfilled
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have a consumer that wants to create an Outbound Correspondence with a written language preference as "Russian"
    And I the default language for the project "BLCRM" is "English"
    And I have a Outbound Correspondence with language specified as "null"
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I stored details of each notification for "previouslyCreated" ob correspondence
    Then I should see the language used is "English" in the letter data that is generated for "Email" channel
    Then I should see the language used is "English" in the letter data that is generated for "Text" channel
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I verify outbound correspondence NotificationId Language
      | previouslycreated_email_nid_language | English   |
      | previouslycreated_email_nid_status   | Requested |
      | previouslycreated_text_nid_language  | English   |
      | previouslycreated_text_nid_status    | Requested |

  @CP-36000 @CP-36000-4 @ui-ecms2 @Keerthi
  Scenario: Verify the Notification status and status reason field when no template found                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               fault project language is being fulfilled
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have a consumer that wants to create an Outbound Correspondence with a written language preference as "Russian"
    And I the default language for the project "BLCRM" is "English"
    And I have a Outbound Correspondence with language specified as "Russian"
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I stored details of each notification for "previouslyCreated" ob correspondence
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I verify outbound correspondence NotificationId Language
      | previouslycreated_fax_nid_status | Error                 |
      | previouslycreated_fax_nid_reason | No available template |