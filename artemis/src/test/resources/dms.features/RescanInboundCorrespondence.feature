Feature: Rescan Inbound Correspondence

  @CP-15720 @CP-15720-1 @ui-ecms1 @James
  Scenario: Verify Rescanned Inbound Document link tasks from Original
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12544 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType | RECEIVED |
      | Channel     | Mail     |
    When I send the request to create the Inbound Correspondence Save Event
    And I should see that Tasks for the following Task Type Ids have been created
      | 12544 |
    And I receive a Rescanned Inbound Document with RescanOfDocumentId "GETNEWRESCANINBOUNDDOCID"
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | GETNEWRESCANINBOUNDDOCID |
    When I click on the Inbound Document CID link "firstAvailable"
    Then I should see that the tasks linked to the original Inbound Correspondence are now linked to the Rescanned Inbound Correspondence

  @CP-15720 @CP-15720-2 @ui-ecms1 @James
  Scenario: Verify Rescanned Inbound Document links multiple tasks from Original
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12544 |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12542 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType | RECEIVED |
      | Channel     | Mail     |
    When I send the request to create the Inbound Correspondence Save Event
    And I should see that Tasks for the following Task Type Ids have been created
      | 12544 |
      | 12542 |
    And I receive a Rescanned Inbound Document with RescanOfDocumentId "GETNEWRESCANINBOUNDDOCID"
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | GETNEWRESCANINBOUNDDOCID |
    When I click on the Inbound Document CID link "firstAvailable"
    Then I should see that the tasks linked to the original Inbound Correspondence are now linked to the Rescanned Inbound Correspondence

  @CP-15720 @CP-15720-3 @ui-ecms1 @James
  Scenario: Verify Rescanned Inbound Document link does not link Cancelled tasks
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12542 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType | RECEIVED |
      | Channel     | Mail     |
    When I send the request to create the Inbound Correspondence Save Event
    And I should see that Tasks for the following Task Type Ids have been created
      | 12542 |
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the link to the task that is linked to the Inbound Document
    And I click Edit task type button
    And I cancel the task of task type "Inbound Task"
    And I receive a Rescanned Inbound Document with RescanOfDocumentId "GETNEWRESCANINBOUNDDOCID"
    Then I should see that the Cancelled task is not linked to the Rescanned Inbound Document
    And I should see that a new task of the following task type id has been created and linked to the Rescanned Inbound Document
      | 12542 |

  @CP-15720 @CP-15720-4 @ui-ecms-coverva @James
  Scenario: Verify Rescanned VACV Inbound Document link tasks from Original
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have an Inbound Correspondence that needs to be Rescanned with the following values
      | documentType | VACV Translation Request |
      | ProcessType  | INBOUND CORRESPONDENCE   |
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | VACV Translation Request |
    When I send the request to create the Inbound Correspondence Save Event
    And I receive a Rescanned Inbound Document with the following values
      | RescanOfDocumentId | OriginalDocumentId       |
      | documentType       | VACV Translation Request |
      | ProcessType        | RESCAN                   |
    And I have an Inbound Correspondence Save event Request with the following values
      | Channel            | Mail                     |
      | ProcessType        | RESCAN                   |
      | RescanOfDocumentId | OriginalDocumentId       |
      | documentHandle     | GETNEWRESCANINBOUNDDOCID |
      | documentType       | VACV Translation Request |
    When I send the request to create the Inbound Correspondence Save Event
    Given I logged into CRM and select a project "COVER-VA"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | GETNEWRESCANINBOUNDDOCID |
    When I click on the Inbound Document CID link "firstAvailable"
    Then I should see that the tasks linked to the original Inbound Correspondence are now linked to the Rescanned Inbound Correspondence

  @CP-15720 @CP-15720-5 @ui-ecms-coverva @James
  Scenario: Verify Rescanned VACV original Inbound Document is updated properly
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have an Inbound Correspondence that needs to be Rescanned with the following values
      | documentType | VACV Translation Request |
      | ProcessType  | INBOUND CORRESPONDENCE   |
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | VACV Translation Request |
    When I send the request to create the Inbound Correspondence Save Event
    And I receive a Rescanned Inbound Document with the following values
      | RescanOfDocumentId | OriginalDocumentId       |
      | documentType       | VACV Translation Request |
      | ProcessType        | RESCAN                   |
    And I have an Inbound Correspondence Save event Request with the following values
      | Channel            | Mail                     |
      | ProcessType        | RESCAN                   |
      | RescanOfDocumentId | OriginalDocumentId       |
      | documentHandle     | GETNEWRESCANINBOUNDDOCID |
      | documentType       | VACV Translation Request |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see that the orignal Inbound Document has been updated with the following values
      | status                | RESCANNED                |
      | updatedOn             | Today                    |
      | updatedBy             | Ecms Service             |
      | ReplacementDocumentId | GETNEWRESCANINBOUNDDOCID |
    Then I should see that the orignal Inbound Document has been updated with the following values
      | status                | RESCANNED                |
      | updatedOn             | Today                    |
      | updatedBy             | Ecms Service             |
      | ReplacementDocumentId | GETNEWRESCANINBOUNDDOCID |

  @CP-15720 @CP-15720-6 @ui-ecms-coverva @James
  Scenario: Verify Rescanned VACV original Inbound Document is updated and displayed properly
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have an Inbound Correspondence that needs to be Rescanned with the following values
      | documentType | VACV Translation Request |
      | ProcessType  | INBOUND CORRESPONDENCE   |
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | VACV Translation Request |
    When I send the request to create the Inbound Correspondence Save Event
    And I receive a Rescanned Inbound Document with the following values
      | RescanOfDocumentId | OriginalDocumentId       |
      | documentType       | VACV Translation Request |
      | ProcessType        | RESCAN                   |
    And I have an Inbound Correspondence Save event Request with the following values
      | Channel            | Mail                     |
      | ProcessType        | RESCAN                   |
      | RescanOfDocumentId | OriginalDocumentId       |
      | documentHandle     | GETNEWRESCANINBOUNDDOCID |
      | documentType       | VACV Translation Request |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see that the orignal Inbound Document has been updated with the following values
      | status                | RESCANNED                |
      | updatedOn             | Today                    |
      | updatedBy             | Ecms Service             |
      | ReplacementDocumentId | GETNEWRESCANINBOUNDDOCID |
    Given I logged into CRM and select a project "COVER-VA"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    Then I should see that the following values are showing in the Inbound Document Details page of the original document
      | status                | RESCANNED                |
      | updatedOn             | Today                    |
      | updatedBy             | Ecms Service             |
      | ReplacementDocumentId | GETNEWRESCANINBOUNDDOCID |

  @CP-15720 @CP-15720-7 @ui-ecms-coverva @James
  Scenario: Verify Rescanned VACV Inbound Document will not link Cancelled task
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have an Inbound Correspondence that needs to be Rescanned with the following values
      | documentType | VACV Authorized Representative Form |
      | ProcessType  | INBOUND CORRESPONDENCE              |
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE              |
      | Channel      | Mail                                |
      | documentType | VACV Authorized Representative Form |
    When I send the request to create the Inbound Correspondence Save Event
    Given I logged into CRM and select a project "COVER-VA"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I hide the call management tab out of the way
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the link to the task that is linked to the Inbound Document
    And I click Edit task type button
    And I cancel the task of task type "AUTHORIZEDREPRESENTATIVEFORM"
    And I receive a Rescanned Inbound Document with the following values
      | RescanOfDocumentId | OriginalDocumentId                  |
      | documentType       | VACV Authorized Representative Form |
      | ProcessType        | RESCAN                              |
    And I have an Inbound Correspondence Save event Request with the following values
      | Channel            | Mail                                |
      | ProcessType        | RESCAN                              |
      | RescanOfDocumentId | OriginalDocumentId                  |
      | documentHandle     | GETNEWRESCANINBOUNDDOCID            |
      | documentType       | VACV Authorized Representative Form |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see that the orignal Inbound Document has been updated with the following values
      | status                | RESCANNED                |
      | updatedOn             | Today                    |
      | updatedBy             | Ecms Service             |
      | ReplacementDocumentId | GETNEWRESCANINBOUNDDOCID |
    Then I should see that the Cancelled task is not linked to the Rescanned Inbound Document
    And I should see that a new task of the following task type id has been created and linked to the Rescanned Inbound Document
      | 13520 |

  @CP-15720 @CP-15720-8 @ui-ecms-coverva @James
  Scenario: Verify Rescanned VACV Inbound Document will not link Completed task
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have an Inbound Correspondence that needs to be Rescanned with the following values
      | documentType | VACV Authorized Representative Form |
      | ProcessType  | INBOUND CORRESPONDENCE              |
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE              |
      | Channel      | Mail                                |
      | documentType | VACV Authorized Representative Form |
    When I send the request to create the Inbound Correspondence Save Event
    Given I logged into CRM and select a project "COVER-VA"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I hide the call management tab out of the way
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the link to the task that is linked to the Inbound Document
    And I click Edit task type button
    And I change the status to complete of the task with task type "AUTHORIZEDREPRESENTATIVEFORM"
    And I receive a Rescanned Inbound Document with the following values
      | RescanOfDocumentId | OriginalDocumentId                  |
      | documentType       | VACV Authorized Representative Form |
      | ProcessType        | RESCAN                              |
    And I have an Inbound Correspondence Save event Request with the following values
      | Channel            | Mail                                |
      | ProcessType        | RESCAN                              |
      | RescanOfDocumentId | OriginalDocumentId                  |
      | documentHandle     | GETNEWRESCANINBOUNDDOCID            |
      | documentType       | VACV Authorized Representative Form |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see that the orignal Inbound Document has been updated with the following values
      | status                | RESCANNED                |
      | updatedOn             | Today                    |
      | updatedBy             | Ecms Service             |
      | ReplacementDocumentId | GETNEWRESCANINBOUNDDOCID |
    Then I should see that the Cancelled task is not linked to the Rescanned Inbound Document
    And I should see that a new task of the following task type id has been created and linked to the Rescanned Inbound Document
      | 13520 |

  @CP-15720 @CP-15720-9  @ui-ecms2 @James
  Scenario: Verify Rescanned Inbound Document when multiple tasks will not link the Cancelled ones
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12542 |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12544 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType | RECEIVED |
      | Channel     | Mail     |
    When I send the request to create the Inbound Correspondence Save Event
    And I should see that Tasks for the following Task Type Ids have been created
      | 12542 |
      | 12544 |
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the link to the task type "Inbound Task"
    And I click Edit task type button
    And I cancel the task of task type "Inbound Task"
    And I receive a Rescanned Inbound Document with RescanOfDocumentId "GETNEWRESCANINBOUNDDOCID"
    Then I should see that the Cancelled task from the origin Inbound Document is not linked to the Rescanned Inbound Document
    And I should see that a new task of the following task type id has been created and linked to the Rescanned Inbound Document
      | 12542 |
      | 12544 |

  @CP-2675 @CP-2675-1  @ui-ecms2 @James
  Scenario: Verify Rescan button will be available when a Mail Document is received by connection point
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | FormVersion  | 3                       |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | COMPLETE                |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    Then I should see that the Request Rescan button is available in the Inbound Correspondence Details Page

  @CP-2675 @CP-2675-2  @ui-ecms2 @James
  Scenario: Verify clicking Request Rescan button will update document properly
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | FormVersion  | 3                       |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | COMPLETE                |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    Then I click on the Rescan button in the Inbound Correspondence Details Page
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    And I should see that the following values exist for the Inbound Document that was previously retrieved
      | Status     | RESCAN REQUESTED |
      | STATUSDATE | Today            |

  @CP-2675 @CP-2675-3  @ui-ecms2 @James
  Scenario: Verify Rescan button not be visible after clicking on it
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | FormVersion  | 3                       |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | COMPLETE                |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I should see that the Request Rescan button is available in the Inbound Correspondence Details Page
    And I click on the Rescan button in the Inbound Correspondence Details Page
    Then I should see that the Cancel Rescan button is available in the Inbound Correspondence Details Page

  @CP-2675 @CP-2675-4 @API-ECMS @James
  Scenario: Verify when rescan is requested that the Inbound Document is updated properly
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Returned Mail  |
      | Language     | SPANISH                |
      | Channel      | EMAIL                  |
      | Status       | COMPLETE               |
      | Origin       | WEB PORTAL             |
      | FromName     | AUTOMATION             |
      | FormVersion  | 3                      |
      | ProcessType  | INBOUND CORRESPONDENCE |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | status | RESCAN REQUESTED |
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    Then I should see that the following values exist for the Inbound Document that was previously retrieved
      | STATUS     | RESCAN REQUESTED |
      | STATUSDATE | Today            |

  @CP-20265 @CP-20265-1  @ui-ecms2 @James
  Scenario: Verify Cancel Rescan button appears after clicking rescan button
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | FormVersion  | 3                       |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | COMPLETE                |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I should see that the Request Rescan button is available in the Inbound Correspondence Details Page
    And I click on the Rescan button in the Inbound Correspondence Details Page
    Then I should see that the Cancel Rescan button is available in the Inbound Correspondence Details Page

  @CP-20265 @CP-20265-2  @ui-ecms2 @James
  Scenario: Verify Rescan button appears after clicking cancel rescan button
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | FormVersion  | 3                       |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | COMPLETE                |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I should see that the Request Rescan button is available in the Inbound Correspondence Details Page
    And I click on the Rescan button in the Inbound Correspondence Details Page
    And I should see that the Cancel Rescan button is available in the Inbound Correspondence Details Page
    And I click on the Cancel Rescan button in the Inbound Correspondence Details Page
    Then I should see that the Request Rescan button is available in the Inbound Correspondence Details Page

  @CP-20265 @CP-20265-3  @ui-ecms2 @James
  Scenario: Verify clicking cancel rescan button will revert Inbound Correspondence status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | 7347                    |
      | ConsumerID   | 17500,17519             |
      | FormVersion  | 3                       |
      | Language     | SPANISH                 |
      | Channel      | MAIL                    |
      | Status       | TRANSMITTING            |
      | Origin       | WEB PORTAL              |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | status | COMPLETE |
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I should see that the Request Rescan button is available in the Inbound Correspondence Details Page
    And I click on the Rescan button in the Inbound Correspondence Details Page
    And I should see that the Cancel Rescan button is available in the Inbound Correspondence Details Page
    And I click on the Cancel Rescan button in the Inbound Correspondence Details Page
    Then I should see that the Request Rescan button is available in the Inbound Correspondence Details Page
    And I retrieve the Inbound Correspondence Details for document "createdFromDigital"
    And I should see that the following values exist for the Inbound Document that was previously retrieved
      | Status | COMPLETE |

  @CP-21760 @CP-21760-1  @ui-ecms2 @James
  Scenario: Verify Rescanned Inbound Document link service requests from Original
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 13241 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType | RECEIVED |
      | Channel     | Mail     |
    When I send the request to create the Inbound Correspondence Save Event
    And I should see that Tasks for the following Task Type Ids have been created
      | 13241 |
    And I receive a Rescanned Inbound Document with RescanOfDocumentId "GETNEWRESCANINBOUNDDOCID"
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | GETNEWRESCANINBOUNDDOCID |
    When I click on the Inbound Document CID link "firstAvailable"
    Then I should see that the Service Request linked to the original Inbound Correspondence are now linked to the Rescanned Inbound Correspondence
