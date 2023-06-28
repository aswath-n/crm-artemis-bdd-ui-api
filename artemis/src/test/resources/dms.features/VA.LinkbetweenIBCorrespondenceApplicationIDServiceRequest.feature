@CP-36285
Feature: Link between IB Correspondence and Matching External Application ID Service Request

  @CP-36285 @ui-ecms-coverva @Keerthi
  Scenario:Creating Application SR from UI
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      | applicationType       | Emergency Medicaid Services Application - CVIU |
      | externalApplicationId | random                                         |
      | myWorkSpaceDate       | -2                                             |
      |channel                | CommonHelp                                     |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch | true              |
      | dateOfContact | -2                |
      | applicationId | getFromCreatePage |
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    And I store "previouslyCreated" External ApplicationID and "previouslyCreated" Application SRID

  @CP-36285 @CP-36285-1.1 @ui-ecms-coverva @Keerthi
  Scenario: Verify VACV CPU Verification IB Correspondence linked to related Service Request regardless status of the Application SR service request
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV CPU Verification"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE                    | PROCESSEDDOCUMENT      |
      | ProcessType             | INBOUND CORRESPONDENCE |
      | Channel                 | Mail                   |
      | documentType            | VACV CPU Verification  |
      | External Application ID | previouslyCreated      |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see there is a link between Application SR "previouslyCreated" and Inbound Correspondence Document "previouslyCreated"
    Then I should see there is a link between Inbound Correspondence Document "previouslyCreated" and Application SR "previouslyCreated"
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on a CID of "first available" Inbound Search Results
    Then I should see the Inbound Document "previouslyCreated" is linked to Application SR "previouslyCreated"

  @CP-36285 @CP-36285-1.2 @api-ecms-coverva @Keerthi
  Scenario: Verify VACV CPU Application IB Correspondence linked to related Service Request regardless status of the Application SR service request
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV CPU Application"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE                    | PROCESSEDDOCUMENT      |
      | ProcessType             | INBOUND CORRESPONDENCE |
      | Channel                 | Mail                   |
      | documentType            | VACV CPU Application   |
      | External Application ID | previouslyCreated      |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see there is a link between Application SR "previouslyCreated" and Inbound Correspondence Document "previouslyCreated"
    Then I should see there is a link between Inbound Correspondence Document "previouslyCreated" and Application SR "previouslyCreated"


  @CP-36285 @ui-ecms-coverva @Keerthi
  Scenario:Updating Application SR to Closed status from UI
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      | applicationType       | Emergency Medicaid Services Application - CVIU |
      | externalApplicationId | random                                         |
      | myWorkSpaceDate       | -2                                             |
      |channel                | CommonHelp                                     |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch | true              |
      | dateOfContact | -2                |
      | applicationId | getFromCreatePage |
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    And I store "previouslyCreated" External ApplicationID and "previouslyCreated" Application SRID
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch | true              |
      | dateOfContact | -2                |
      | applicationId | getFromCreatePage |
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit             | Entered Additional Info          |
      | status                    | Closed                           |
      | applicationType           | MAGI Standard Application - CVIU |
      | applicationReceivedDate   | today                            |
      | channel                   | CommonHelp                       |
      | noOfApplicantsInHousehold | 1                                |
      | missingInfoRequired       | No                               |
      | applicationStatus         | Medicare Review Needed           |
      | disposition               | Cancelled                        |
      | facilityType              | Department of Juvenile Justice   |
    And I click on save button on task edit page

  @CP-36285 @CP-36285-1.3 @api-ecms-coverva @Keerthi
  Scenario: Verify VACV CVIU Verification IB Correspondence linked to related Service Request regardless status of the Application SR service request
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV CVIU Verification"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE                    | PROCESSEDDOCUMENT      |
      | ProcessType             | INBOUND CORRESPONDENCE |
      | Channel                 | Mail                   |
      | documentType            | VACV CVIU Verification |
      | External Application ID | previouslyCreated      |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see there is a link between Application SR "previouslyCreated" and Inbound Correspondence Document "previouslyCreated"
    Then I should see there is a link between Inbound Correspondence Document "previouslyCreated" and Application SR "previouslyCreated"


  @CP-36285 @CP-36285-1.4 @api-ecms-coverva @Keerthi
  Scenario: Verify VACV CVIU Application IB Correspondence linked to related Service Request regardless status of the Application SR service request
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV CVIU Application"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE                    | PROCESSEDDOCUMENT      |
      | ProcessType             | INBOUND CORRESPONDENCE |
      | Channel                 | Mail                   |
      | documentType            | VACV CVIU Application  |
      | External Application ID | previouslyCreated      |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see there is a link between Application SR "previouslyCreated" and Inbound Correspondence Document "previouslyCreated"
    Then I should see there is a link between Inbound Correspondence Document "previouslyCreated" and Application SR "previouslyCreated"


  @CP-36285 @CP-36285-2  @api-ecms-coverva  @Keerthi
  Scenario: Verify VACV Appeal IB Correspondence not linked to related Service Request
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV Appeal"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE                    | PROCESSEDDOCUMENT      |
      | ProcessType             | INBOUND CORRESPONDENCE |
      | Channel                 | Mail                   |
      | documentType            | VACV Appeal            |
      | External Application ID | previouslyCreated      |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should not see there is a link between Inbound Correspondence Document "previouslyCreated" and Application SR "previouslyCreated"

  @CP-36285 @CP-36285-3 @ui-ecms-coverva @Keerthi
  Scenario: Verify auto-linking should not occur when updating Correspondence Type in CP
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV Appeal"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType             | INBOUND CORRESPONDENCE |
      | Channel                 | Mail                   |
      | documentType            | VACV Appeal            |
      | External Application ID | previouslyCreated      |
    When I send the request to create the Inbound Correspondence Save Event
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on a CID of "first available" Inbound Search Results
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "type" field with value "VACV CVIU Application" via "null"
    And I click on Edit Inbound Correspondence details Page Save button
    Then I should not see there is a link between Inbound Correspondence Document "previouslyCreated" and Application SR "previouslyCreated"

  @CP-36285 @CP-36285-4.1 @api-ecms-coverva @Keerthi
  Scenario: Verify IB Correspondence does not link for blank External Application ID but creates task configured for the correspondence type.
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "VACV CVIU Application"
    And I want to add a task rule to the Inbound Correspondence Type "VACV CVIU Application" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 13493 |
      | LINKSAMESETTASK      | false |
    And I send the request to update the Inbound Correspondence Type "VACV CVIU Application" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "VACV CVIU Application"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType             | INBOUND CORRESPONDENCE |
      | Channel                 | Mail                   |
      | documentType            | VACV CVIU Application  |
      | External Application ID |previouslyCreated|
    When I send the request to create the Inbound Correspondence Save Event
    Then I should not see there is a link between Inbound Correspondence Document "previouslyCreated" and Application SR "previouslyCreated"
    And I should validate Task has been "created"

  @CP-36285 @CP-36285-4.2 @api-ecms-coverva @Keerthi
  Scenario: Verify IB Correspondence does not link for invalid External Application ID but creates task configured for the correspondence type.
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "VACV CVIU Application"
    And I want to add a task rule to the Inbound Correspondence Type "VACV CVIU Application" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 13493 |
      | LINKSAMESETTASK      | false |
    And I send the request to update the Inbound Correspondence Type "VACV CVIU Application" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "VACV CVIU Application"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType             | INBOUND CORRESPONDENCE |
      | Channel                 | Mail                   |
      | documentType            | VACV CVIU Application  |
      | External Application ID | 123@#$56               |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should not see there is a link between Inbound Correspondence Document "previouslyCreated" and Application SR "previouslyCreated"
    And I should validate Task has been "created"

  @CP-36285 @CP-36285-4.3 @api-ecms-coverva @Keerthi
  Scenario: Verify IB Correspondence does not link for multiple External Application ID but creates task configured for the correspondence type.
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "VACV CVIU Application"
    And I want to add a task rule to the Inbound Correspondence Type "VACV CVIU Application" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 13493 |
      | LINKSAMESETTASK      | false |
    And I send the request to update the Inbound Correspondence Type "VACV CVIU Application" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "VACV CVIU Application"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType             | INBOUND CORRESPONDENCE |
      | Channel                 | Mail                   |
      | documentType            | VACV CVIU Application  |
      | External Application ID | Test1234               |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should not see there is a link between Inbound Correspondence Document "previouslyCreated" and Application SR "143369"
    And I should validate Task has been "created"