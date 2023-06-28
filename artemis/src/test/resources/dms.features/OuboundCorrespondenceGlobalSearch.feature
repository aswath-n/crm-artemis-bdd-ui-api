Feature: Outbound Correspondence Global Search

  @CP-3343-01 @CP-3343  @ui-ecms1 @James
  Scenario: Verify that there is a icon to navigate to the outbound correspondence global search screen
    Given I logged into CRM
    When I click on the main navigation menu
    Then I should see the Outbound Correspondence Search Icon

  @CP-3343-02 @CP-3343  @ui-ecms1 @James
  Scenario: Verify that when I select the outbound correspondence search icon, I am navigated to the outbound correspondence global search screen
    Given I logged into CRM
    And I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    Then I should be navigated to the Outbound Correspondence Global Search Page

  @CP-9626 @CP-9626-01  @ui-ecms1 @Sang
  Scenario: Verify that when I select details option for a Corespondence I see expected fields
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I click on edit button on view Outbound Correspondence details
    And I select "On Hold" value from "Correspondence status" dropdown
    And I select "State requested" value from "Correspondence Reason" dropdown
    And I click on Save button in outbound correspondence edit
    And I click on the back arrow button
    When I select the show details caret for an Outbound Correspondence
    Then I should see fields with correct value:
      | CREATED ON | CREATED BY | STATUS REASON | RECIPIENT(S) |
    And I should see correct values for the fields
      | 2492 | current date and hour | State requested | Bruce Wayne |

  @CP-9626 @CP-9626-02 @ui-ecms1 @Sang
  Scenario: Verify that when I select details option for a Corespondence I see in Notification expected fields
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I select the show details caret for an Outbound Correspondence
    Then I should see in the Notification field with following fields
      | CHANNEL | DESTINATION | LANGUAGE | STATUS REASON | STATUS DATE | NID |

  @CP-9626 @CP-9626-03 @ui-ecms1 @Sang
  Scenario Outline: Verify that Mail Email Text and Fax has the appropriate Destination values associated
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
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I select the show details caret for an Outbound Correspondence
    Then I should see "<Mail>" "<Email>" "<Text>" "<Fax>" have appropriate Destination values listed
    Examples:
      | Mail              | Email             | Text              | Fax               |
      | previouslyCreated | previouslyCreated | previouslyCreated | previouslyCreated |

  @CP-9626 @CP-9626-04 @ui-ecms1 @Sang
  Scenario Outline: Verify that Mail Email Text and Fax has the appropriate Status values associated
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
    And I update previous notification
      | Returned  |
      | Sent      |
      | Canceled  |
      | Requested |
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I select the show details caret for an Outbound Correspondence
    Then I should see "<Mail>" "<Email>" "<Text>" "<Fax>" have appropriate Status values listed
    Examples:
      | Mail     | Email | Text     | Fax       |
      | Returned | Sent  | Canceled | Requested |

  @CP-9626 @CP-9626-05 @ui-ecms1 @Sang
  Scenario: Show an error icon with status reason text for status that is in error
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have an Outbound Definition that has Channel is Mail plus Send Immediately is True of "FRENCH" language
    When I Create an Outbound Correspondence Request of "FRENCH" language to be sent immediately
    And I send api call to generate pdf with "previouslycreated" notificationid and "currentuser" userid
    Then I logged into CRM
    And I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | NOTIFICATIONID | sendNowNID |
    When I select the show details caret for an Outbound Correspondence
    Then I hover my mouse over the red triangle error icon
    And I should see the status reason text "Assembly Error" for the error

  @CP-9626 @CP-9626-06 @ui-ecms1 @Sang
  Scenario: Verify Mail Notification has a Notification details Icon viewable
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail"
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I select the show details caret for an Outbound Correspondence
    Then I verify Notification details contains view Icon

  @CP-9626 @CP-9626-07 @ui-ecms1 @Sang
  Scenario Outline: Verify the Notification field detail values has appropriate values associated
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
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I select the show details caret for an Outbound Correspondence
    Then I should see "<CREATED ON>" "<CREATED BY>" "<STATUS REASON>" "<RECIPIENT(S)>" have appropriate Notification detail values listed
    Examples:
      | CREATED ON        | CREATED BY        | STATUS REASON     | RECIPIENT(S)      |
      | previouslyCreated | previouslyCreated | previouslyCreated | previouslyCreated |

  @CP-9623 @CP-9623-1 @ui-ecms1 @James
  Scenario: Verify Outbound Search Results CID are hyperlinks
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    Then I should see the Outbound Correspondence CID is displayed as a hyperlink

  @CP-9623 @CP-9623-2 @ui-ecms1 @James
  Scenario: Verify Outbound Search CID navigates to Outbound Correspondence Details screen
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I should see the details page for the Outbound Correspondence

  @CP-9623 @CP-9623-3 @ui-ecms1 @James
  Scenario Outline: Verify Outbound Search Results are same Outbound Correspondence search results as before navigate away for result sets over 200
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | NOTIFICATIONSTATUS | Requested |
    When I click on the "<Page>" Outbound Correspondence
    And I should see the details page for the Outbound Correspondence
    And I click on the navigate back from Outbound Correspondence Details
    Then I should see the same Outbound Correspondence Search results as before
    Examples:
      | Page       |
      | FIRSTPAGE  |
      | RANDOMPAGE |

  @CP-20135-01 @CP-20135 @ui-ecms-coverva @James
  Scenario: Verify Outbound Search is Hidden for Cover-VA tenant
    Given I logged into CRM and select a project "COVER-VA"
    When I click on the main navigation menu
    Then I should NOT see the Outbound Correspondence Search Icon
