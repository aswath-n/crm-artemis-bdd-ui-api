@viewinb
Feature: View Inbound Correspondence Details part1

#  @CP-2953-01 @ui-ecms1 @umid DEPRECATED
  Scenario: See the Inbound Correspondence details from Case
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Jane" and Last Name as "Lady"
    And I link the contact to an existing Case or Consumer Profile
    When I navigate to the case and contact details tab
    And I click on given Inbound Correspondence hyperlink "7735"
    Then I store all the possible VIEW INBOUND CORRESPONDENCE DETAILS
    And I verify all the VIEW INBOUND CORRESPONDENCE DETAILS from BFF ECMS CORRESPONDENCE INBOUND DOCUMENT "7735"


#  @CP-2953-02 @ui-ecms1 @James DEPRECATED
  Scenario: Verify Inbound Correspondence Details from Inbound Search
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | 7735 |
    And I click on a CID of "first available" Inbound Search Results
    Then I should see the details page for the Outbound Correspondence
    And I verify all the VIEW INBOUND CORRESPONDENCE DETAILS from BFF ECMS CORRESPONDENCE INBOUND DOCUMENT "7735"


  @CP-2957 @CP-2957-1 @ui-ecms1 @kamil
  Scenario: Verify note save captures created by/on
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on the Inbound Document CID link "firstAvailable"
    When I add a new note to the Inbound Correspondence
    Then i should see that the system captures the following data
      | Created On |
      | Created By |


  @CP-2957 @CP-2957-2 @ui-ecms1 @kamil
  Scenario: Verify note save captures 500 char
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on the Inbound Document CID link "firstAvailable"
    When I add a new note to the Inbound Correspondence
    Then i should see that the system allows 500 free text characters in the note field


  @CP-2957 @CP-2957-3 @ui-ecms1 @kamil
  Scenario: Verify note sort by most recent first
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on the Inbound Document CID link "firstAvailable"
    When I add a new note to the Inbound Correspondence
    And I add a second new note to the Inbound Correspondence
    Then I should see the most recent note at the top


  @CP-2957 @CP-2957-4 @ui-ecms1 @kamil
  Scenario: Verify can cancel a note
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on the Inbound Document CID link "firstAvailable"
    When I add a new note to the Inbound Correspondence but dont save
    Then I should see that I am able to cancel the note


  @CP-2957 @CP-2957-5 @ui-ecms1 @kamil
  Scenario: Verify can navigate message
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on the Inbound Document CID link "firstAvailable"
    And I add a new note to the Inbound Correspondence but dont save
    When I attempt to navigate away from the page
    Then I should see that a warning message will come warning about navigating away


  @CP-2957 @CP-2957-6 @ui-ecms1 @kamil
  Scenario: Verify can navigate canceled
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on the Inbound Document CID link "firstAvailable"
    And I add a new note to the Inbound Correspondence but dont save
    When I attempt to navigate away from the page
    And I click cancel in Warning Message popup
    Then I should see that my data is intact


  @CP-2957 @CP-2957-7 @ui-ecms1 @kamil
  Scenario: Verify can navigate confirm
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on the Inbound Document CID link "firstAvailable"
    And I add a new note to the Inbound Correspondence but dont save
    When I attempt to navigate away from the page
    And I click discard my changes
    Then I should see that my changes are gone and navigated away from page

  @CP-8482 @CP-8482-1 @ui-ecms1 @James
  Scenario: Verify that new tabs are visible in Inbound Correspondence Details Screen
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    Then I should see the the following tabs in the Inbound Correspondence Details Screen
      | SOURCE DETAILS |
      | METADATA       |
      | HISTORY        |

  @CP-8482 @CP-8482-2 @ui-ecms1 @James
  Scenario: Verify that new tabs are visible in Inbound Correspondence Details Screen when navigated from Case Search
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | PreviouslyCreated       |
      | Channel      | MAIL                    |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | ScannedOn    | 11/08/2021 12:00:00 AM  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the case Id that I previously created
    And I navigate to the case and contact details tab
    And I select the Inbound Correspondence that was "previouslyCreated" on the case and contact details tab
    Then I should see the the following tabs in the Inbound Correspondence Details Screen
      | SOURCE DETAILS |
      | METADATA       |
      | HISTORY        |

  @CP-8482 @CP-8482-3 @ui-ecms1 @James
  Scenario: Verify that labels and values for SOURCE DETAILS TAB are visible in Inbound Correspondence Details Screen
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType     | maersk Case + Consumer        |
      | CaseID           | 7347                           |
      | ConsumerID       | 17500,17519                    |
#      | FormVersion  | 3                       | removed for CP-29740
      | Language         | SPANISH                        |
      | Channel          | MAIL                           |
      | Status           | COMPLETE                       |
      | FromName         | AUTOMATION                     |
      | FromEmailAddress | AUTOMATION@automatdsa12345.com |
      | FromPhoneNumber  | 5554443333                     |
      | Batch #          | 12345                          |
      | ProcessType      | INBOUND CORRESPONDENCE         |
      | ScannedOn        | 11/08/2021 12:00:00 AM         |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    Then I should see the the following labels on the "SOURCE DETAILS" tab on the Inbound Correspondence Details Screen
      | SOURCE BATCH ID   |
      | SCAN BATCH CLASS  |
      | SCAN TIMESTAMP    |
      | FROM EMAIL        |
      | FROM PHONE NUMBER |
      | FROM NAME         |
      | RESCAN BUTTON     |
    And I should see the all the following values on the "SOURCE DETAILS" tab on the Inbound Correspondence Details Screen are correct
      | SOURCE BATCH ID   |
      | SCAN BATCH CLASS  |
      | SCAN TIMESTAMP    |
      | CHANNEL           |
      | FROM EMAIL        |
      | FROM PHONE NUMBER |
      | FROM NAME         |
      | RESCAN BUTTON     |

  @CP-8482 @CP-8482-4 @ui-ecms1 @James
  Scenario: Verify that labels and values for SOURCE DETAILS TAB are visible in Inbound Correspondence Details Screen from Case Search
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType     | maersk Case + Consumer |
      | CaseID           | PreviouslyCreated       |
      | Language         | SPANISH                 |
      | Channel          | MAIL                    |
      | Status           | COMPLETE                |
      | FromName         | AUTOMATION              |
      | FromEmailAddress | automation@uaosdfij.com |
      | FromPhoneNumber  | 5553331234              |
      | Batch #          | 12345                   |
      | ProcessType      | INBOUND CORRESPONDENCE  |
      | ScannedOn        | 11/08/2021 12:00:00 AM  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the case Id that I previously created
    And I navigate to the case and contact details tab
    And I select the Inbound Correspondence that was "previouslyCreated" on the case and contact details tab
    Then I should see the the following labels on the "SOURCE DETAILS" tab on the Inbound Correspondence Details Screen
      | SOURCE BATCH ID   |
      | SCAN BATCH CLASS  |
      | SCAN TIMESTAMP    |
      | FROM EMAIL        |
      | FROM PHONE NUMBER |
      | FROM NAME         |
      | RESCAN BUTTON     |
    And I should see the all the following values on the "SOURCE DETAILS" tab on the Inbound Correspondence Details Screen are correct
      | SOURCE BATCH ID   |
      | SCAN BATCH CLASS  |
      | SCAN TIMESTAMP    |
      | CHANNEL           |
      | FROM EMAIL        |
      | FROM PHONE NUMBER |
      | FROM NAME         |
      | RESCAN BUTTON     |

  @CP-8482 @CP-8482-5 @ui-ecms1 @James
  Scenario: Verify that labels and values for UPPER SECTION OF INB DETAILS are visible in Inbound Correspondence Details Screen
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType          | maersk Returned Mail   |
      | CaseID                | PreviouslyCreated       |
      | Language              | SPANISH                 |
      | Channel               | MAIL                    |
      | Status                | INDEXING                |
      | FromName              | AUTOMATION              |
      | FromEmailAddress      | automation@uaosdfij.com |
      | ProcessType           | INBOUND CORRESPONDENCE  |
      | ScannedOn             | 11/08/2021 12:00:00 AM  |
      | ReplacementDocumentId | 123                     |
      | RescanOfDocumentId    | 547                     |
      | NotificationID        | 8558                    |
      | ReturnedReason        | INVALID ADDRESS         |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | status | Complete |
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the case Id that I previously created
    And I navigate to the case and contact details tab
    And I select the Inbound Correspondence that was "previouslyCreated" on the case and contact details tab
    Then I should see the the following labels on the "UPPER SECTION OF INB DETAILS" tab on the Inbound Correspondence Details Screen
      | CREATED BY               |
      | CREATED on               |
      | TYPE                     |
      | PAGE COUNT               |
      | RECEIVED DATE            |
      | STATUS                   |
      | STATUS DATE              |
      | RESCANNED AS CID         |
      | RESCAN OF CID            |
      | RETURNED NOTIFICATION ID |
      | RETURNED REASON          |
      | LANGUAGE                 |
    And I should see the all the following values on the "UPPER SECTION OF INB DETAILS" tab on the Inbound Correspondence Details Screen are correct
      | CREATED BY               |
      | CREATED on               |
      | TYPE                     |
      | PAGE COUNT               |
      | RECEIVED DATE            |
      | STATUS                   |
      | STATUS DATE              |
      | RESCANNED AS CID         |
      | RESCAN OF CID            |
      | RETURNED NOTIFICATION ID |
      | RETURNED REASON          |
      | LANGUAGE                 |

  @CP-8482 @CP-8482-6 @ui-ecms1 @James
  Scenario: Verify that labels and values for UPPER SECTION OF INBOUND DETAILS are visible in Inbound Correspondence Details Screen from Case Search
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType          | maersk Returned Mail  |
      | CaseID                | 18024                  |
      | Language              | SPANISH                |
      | Channel               | EMAIL                  |
      | Status                | COMPLETE               |
      | FromName              | AUTOMATION             |
#      | FormVersion           | 3                      | removed for CP-29740
      | ProcessType           | INBOUND CORRESPONDENCE |
      | RescanOfDocumentId    | Random                 |
      | ReplacementDocumentId | Random                 |
      | NotificationId        | Random                 |
      | ReturnedReason        | INVALID ADDRESS        |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | status | TRANSMITTING |
    And I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Jane" and Last Name as "Lady"
    And I link the contact to an existing Case or Consumer Profile
    And I navigate to the case and contact details tab
    When I click on given Inbound Correspondence hyperlink "fromRequest"
    Then I should see the the following labels on the "UPPER SECTION OF INB DETAILS" tab on the Inbound Correspondence Details Screen
      | CREATED BY               |
      | CREATED ON               |
      | UPDATED BY               |
      | UPDATED ON               |
      | TYPE                     |
      | PAGE COUNT               |
      | RECEIVED DATE            |
      | STATUS                   |
      | STATUS DATE              |
      | RESCANNED AS CID         |
      | RESCAN OF CID            |
      | RETURNED NOTIFICATION ID |
      | RETURNED REASON          |
      | LANGUAGE                 |
      | FORM VERSION             |
    And I should see the all the following values on the "UPPER SECTION OF INB DETAILS" tab on the Inbound Correspondence Details Screen are correct
      | CREATED BY               |
      | CREATED ON               |
      | UPDATED BY               |
      | UPDATED ON               |
      | TYPE                     |
      | PAGE COUNT               |
      | RECEIVED DATE            |
      | STATUS                   |
      | STATUS DATE              |
      | RESCANNED AS CID         |
      | RESCAN OF CID            |
      | RETURNED NOTIFICATION ID |
      | RETURNED REASON          |
      | LANGUAGE                 |
      | FORM VERSION             |

  @CP-8482 @CP-8482-7 @ui-ecms1 @James
  Scenario: Verify that labels and values that should be hidden for UPPER SECTION OF INB DETAILS are hidden in Inbound Correspondence Details Screen
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Channel      | MAIL                    |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    Then I should see the the following labels ARE HIDDEN WHEN EMPTY on the "UPPER SECTION OF INB DETAILS" tab on the Inbound Correspondence Details Screen
      | RESCANNED AS CID         |
      | RESCAN OF CID            |
      | RETURNED NOTIFICATION ID |
      | RETURNED REASON          |
      | LANGUAGE                 |
      | FORM VERSION             |

  @CP-8482 @CP-8482-8 @ui-ecms1 @James
  Scenario: Verify that labels and values that should be hidden for UPPER SECTION OF INB DETAILS are hidden in Inbound Correspondence Details Screen from Case Search
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Channel      | MAIL                    |
      | Status       | COMPLETE                |
      | CaseID       | PreviouslyCreated       |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the case Id that I previously created
    And I navigate to the case and contact details tab
    And I select the Inbound Correspondence that was "previouslyCreated" on the case and contact details tab
    Then I should see the the following labels ARE HIDDEN WHEN EMPTY on the "UPPER SECTION OF INB DETAILS" tab on the Inbound Correspondence Details Screen
      | RESCANNED AS CID         |
      | RESCAN OF CID            |
      | RETURNED NOTIFICATION ID |
      | RETURNED REASON          |
      | LANGUAGE                 |
      | FORM VERSION             |

#  @CP-8482 @CP-8482-9 @ui-ecms1 @James removed for CP-29740
  Scenario: Verify that labels and FORM VERSION for UPPER SECTION OF INB DETAILS are visible in Inbound Correspondence Details Screen
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | 36294 |
    When I click on the Inbound Document CID link "firstAvailable"
    Then I should see the the following labels on the "UPPER SECTION OF INB DETAILS" tab on the Inbound Correspondence Details Screen
      | FORM VERSION |
    And I should see the all the following values on the "UPPER SECTION OF INB DETAILS" tab on the Inbound Correspondence Details Screen are correct
      | FORM VERSION |

#  @CP-8482 @CP-8482-10 @ui-ecms1 @James Removed for CP-29740
  Scenario: Verify that labels and FORM VERSION for UPPER SECTION OF INB DETAILS are visible in Inbound Correspondence Details Screen from Case Search
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | 18024                   |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | FormVersion  | 3                       |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Jane" and Last Name as "Lady"
    And I link the contact to an existing Case or Consumer Profile
    And I navigate to the case and contact details tab
    When I click on given Inbound Correspondence hyperlink "fromRequest"
    Then I should see the the following labels on the "UPPER SECTION OF INB DETAILS" tab on the Inbound Correspondence Details Screen
      | FORM VERSION |
    And I should see the all the following values on the "UPPER SECTION OF INB DETAILS" tab on the Inbound Correspondence Details Screen are correct
      | FORM VERSION |


  @CP-8481 @CP-8481-1 @ui-ecms1 @Nehal
  Scenario: Verify the hyperlink to replacement item is available
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I receive a Rescanned Inbound Document with RescanOfDocumentId "GETNEWRESCANINBOUNDDOCID"
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    Then Verify the RESCANNED AS CID field is populated and is a hyperlink

  @CP-8481 @CP-8481-2 @ui-ecms1 @Nehal
  Scenario: Verify the view replacement when user selects hyperlink
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I receive a Rescanned Inbound Document with RescanOfDocumentId "GETNEWRESCANINBOUNDDOCID"
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    Then I click on the RESCANNED AS CID to navigate to rescanned document
    Then  The system displays the View Inbound Correspondence Details screen for that item


  @CP-8481 @CP-8481-3 @ui-ecms1 @Nehal
  Scenario: Verify the hyperlink to original item is available
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I receive a Rescanned Inbound Document with RescanOfDocumentId "GETNEWRESCANINBOUNDDOCID"
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    Then I click on the RESCANNED AS CID to navigate to rescanned document
    Then The Rescan of CID field is populated and is a hyperlink that can be selected

  @CP-8481 @CP-8481-4 @ui-ecms1 @Nehal
  Scenario: Verify the view original when user selects hyperlink
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I receive a Rescanned Inbound Document with RescanOfDocumentId "GETNEWRESCANINBOUNDDOCID"
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    Then I click on the RESCANNED AS CID to navigate to rescanned document
    Then I click on the RESCANNED AS CID to navigate to rescanned document
    Then The system displays the Original View Inbound Correspondence Details screen for that item

  @CP-19611 @CP-19611-1 @ui-ecms1 @James
  Scenario: Verify Document Icon is Visible when Document is in a set
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | FormVersion  | 3                       |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | SetId        | Random                  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | FormVersion  | 3                       |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | SetId        | PreviouslyCreated       |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    Then I should see the document icon on the Inbound Document Details Page

  @CP-19611 @CP-19611-2 @ui-ecms1 @James
  Scenario: Verify view icon for each Document in a set
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | FormVersion  | 3                       |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | SetId        | Random                  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | FormVersion  | 3                       |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | SetId        | PreviouslyCreated       |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the Document Set Details Tab on the Inbound Document Details Page
    Then I should see that there is a view icon for each Inbound Document for the set

  @CP-20118 @CP-20118-1 @ui-ecms1 @James
  Scenario: Verify view icon is next to CID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | FormVersion  | 3                       |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | SetId        | Random                  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    Then I should see the view Inbound Document is next to the CID

  @CP-20118 @CP-20118-2 @ui-ecms1 @James
  Scenario: Verify all documents in set has a view icon
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | FormVersion  | 3                       |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | SetId        | Random                  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | FormVersion  | 3                       |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | SetId        | PreviouslyCreated       |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the Document Set Details Tab on the Inbound Document Details Page
    Then I should see that there is a view icon for each Inbound Document for the set