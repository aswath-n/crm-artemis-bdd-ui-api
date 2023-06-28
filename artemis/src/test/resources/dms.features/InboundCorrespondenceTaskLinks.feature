Feature: Inbound Correspondence Task Links

  @CP-18508 @CP-18508-1 @API-ECMS @James
  Scenario: Verify if Inbound Documents in same set will link to same task of same type in created status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12544 |
      | LINKSAMESETTASK      | true  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumers"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumers" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12544 |
      | LINKSAMESETTASK      | true  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumers" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | SetId        | Random                  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | INBOUND CORRESPONDENCE   |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
      | documentType   | maersk Case + Consumer  |
      | SetId          | Random                   |
    And I send the request to create the Inbound Correspondence Save Event
    And I should see the following types of links have been linked to the Inbound Correspondence that was "createdFromDigital"
      | task | 12544 |
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers |
      | Language     | SPANISH                  |
      | Channel      | EMAIL                    |
      | Status       | COMPLETE                 |
      | FromName     | AUTOMATION               |
      | SetId        | PreviouslyCreated        |
    When I send the request for the Inbound Correspondence in the same set as the previously created document
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | INBOUND CORRESPONDENCE   |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
      | documentType   | maersk Case + Consumers |
      | SetId          | PreviouslyCreated        |
      | DOCUMENTHANDLE | SameSetInboundDocument   |
    And I send the request to create the Inbound Correspondence Save Event
    Then I should see the "previouslyCreated" tasks on the first Inbound Correspondence received in a set is linked to the newly received to the Inbound Correspondence in the same set

  @CP-18508 @CP-18508-2 @API-ECMS @James
  Scenario: Verify if Inbound Documents in same set will NOT link to tasks that are different types
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12544 |
      | LINKSAMESETTASK      | true  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumers"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumers" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12797 |
      | LINKSAMESETTASK      | true  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumers" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | SetId        | Random                  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | INBOUND CORRESPONDENCE   |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
      | documentType   | maersk Case + Consumer  |
    And I send the request to create the Inbound Correspondence Save Event
    And I should see the following types of links have been linked to the Inbound Correspondence that was "createdFromDigital"
      | task | 12544 |
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers |
      | Language     | SPANISH                  |
      | Channel      | EMAIL                    |
      | Status       | COMPLETE                 |
      | FromName     | AUTOMATION               |
      | SetId        | PreviouslyCreated        |
    When I send the request for the Inbound Correspondence in the same set as the previously created document
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | INBOUND CORRESPONDENCE   |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
      | documentType   | maersk Case + Consumers |
      | SetId          | PreviouslyCreated        |
      | DOCUMENTHANDLE | SameSetInboundDocument   |
    And I send the request to create the Inbound Correspondence Save Event
    Then I should see the "previouslyCreated" tasks on the first Inbound Correspondence received in a set is NOT linked to the newly received to the Inbound Correspondence in the same set

  @CP-18508 @CP-18508-3 @ui-ecms1 @James
  Scenario: Verify if Inbound Documents in same set will NOT link to tasks are cancelled
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12542 |
      | LINKSAMESETTASK      | true  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumers"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumers" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12542 |
      | LINKSAMESETTASK      | true  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumers" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | SetId        | Random                  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | INBOUND CORRESPONDENCE   |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
      | documentType   | maersk Case + Consumer  |
    And I send the request to create the Inbound Correspondence Save Event
    And I should see the following types of links have been linked to the Inbound Correspondence that was "createdFromDigital"
      | task | 12542 |
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the link to the task that is linked to the Inbound Document
    And I click Edit task type button
    And I cancel the task of task type "Inbound Task"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers |
      | Language     | SPANISH                  |
      | Channel      | EMAIL                    |
      | Status       | COMPLETE                 |
      | FromName     | AUTOMATION               |
      | SetId        | PreviouslyCreated        |
    When I send the request for the Inbound Correspondence in the same set as the previously created document
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | INBOUND CORRESPONDENCE   |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
      | documentType   | maersk Case + Consumers |
      | SetId          | PreviouslyCreated        |
      | DOCUMENTHANDLE | SameSetInboundDocument   |
    And I send the request to create the Inbound Correspondence Save Event
    Then I should see the "previouslyCreated" tasks on the first Inbound Correspondence received in a set is NOT linked to the newly received to the Inbound Correspondence in the same set

  @CP-18508 @CP-18508-4 @ui-ecms1 @James
  Scenario: Verify if Inbound Documents in same set will NOT link to tasks are completed
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumers"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumers" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12542 |
      | LINKSAMESETTASK      | true  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumers" - if not already setup as intended in the request
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12542 |
      | LINKSAMESETTASK      | true  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | SetId        | Random                  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | INBOUND CORRESPONDENCE   |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
      | documentType   | maersk Case + Consumer  |
      | SetId          | Random                   |
    And I send the request to create the Inbound Correspondence Save Event
    And I should see the following types of links have been linked to the Inbound Correspondence that was "createdFromDigital"
      | task | 12542 |
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the link to the task that is linked to the Inbound Document
    And I click Edit task type button
    And I change the status to complete of the task with task type "Complete Task"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers |
      | Language     | SPANISH                  |
      | Channel      | EMAIL                    |
      | Status       | COMPLETE                 |
      | FromName     | AUTOMATION               |
      | SetId        | PreviouslyCreated        |
    When I send the request for the Inbound Correspondence in the same set as the previously created document
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | INBOUND CORRESPONDENCE   |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
      | documentType   | maersk Case + Consumers |
      | SetId          | PreviouslyCreated        |
      | DOCUMENTHANDLE | SameSetInboundDocument   |
    And I send the request to create the Inbound Correspondence Save Event
    Then I should see the "previouslyCreated" tasks on the first Inbound Correspondence received in a set is NOT linked to the newly received to the Inbound Correspondence in the same set

  @CP-18511 @CP-18511-1 @API-ECMS @James
  Scenario: Verify when a Inbound Document is updated that 2 task rules of rank 1 with no required fields will produce 2 tasks
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12797 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers |
      | Language     | SPANISH                  |
      | Channel      | EMAIL                    |
      | Status       | COMPLETE                 |
      | FromName     | AUTOMATION               |
      | ProcessType  | INBOUND CORRESPONDENCE   |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | RECEIVED                 |
      | Channel        | Email                    |
      | documentType   | maersk Case + Consumers |
      | documentHandle | InboundDocumentIdDigital |
    When I send the request to create the Inbound Correspondence Save Event
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | documentType | maersk Case + Consumer |
    Then I should see a task of the following task types have been linked to the "previouslyCreated" Inbound Correspondence
      | 12797 |

  @CP-18511 @CP-18511-2 @API-ECMS @James
  Scenario: Verify when a Inbound Document is updated so when 1 task rule requires case id, 1 requires consumer id, 2 tasks will be created when both found in event
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1      |
      | requiredDataElements | CaseID |
      | taskTypeID           | 12544  |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1          |
      | requiredDataElements | ConsumerID |
      | taskTypeID           | 12542      |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 2     |
      | requiredDataElements | null  |
      | taskTypeID           | 12541 |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 2     |
      | requiredDataElements | null  |
      | taskTypeID           | 12797 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers |
      | Language     | SPANISH                  |
      | Channel      | EMAIL                    |
      | Status       | COMPLETE                 |
      | FromName     | AUTOMATION               |
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | CaseID       | random                   |
      | ConsumerID   | random                   |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | documentType | maersk Case + Consumer |
    Then I should see a task of the following task types have been linked to the "previouslyCreated" Inbound Correspondence
      | 12544 |
      | 12542 |

  @CP-18511 @CP-18511-3 @API-ECMS @James
  Scenario: Verify when a Inbound Document is updated so when 1 task rule requires case id, 1 requires consumer id, case id requiring task type will be created when case id only is found in event
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1      |
      | requiredDataElements | CaseID |
      | taskTypeID           | 12544  |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1          |
      | requiredDataElements | ConsumerID |
      | taskTypeID           | 12542      |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 2     |
      | requiredDataElements | null  |
      | taskTypeID           | 12541 |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 2     |
      | requiredDataElements | null  |
      | taskTypeID           | 12797 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers |
      | Language     | SPANISH                  |
      | Channel      | EMAIL                    |
      | Status       | COMPLETE                 |
      | FromName     | AUTOMATION               |
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | CaseID       | random                   |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | documentType | maersk Case + Consumer |
    Then I should see a task of the following task types have been linked to the "previouslyCreated" Inbound Correspondence
      | 12544 |

  @CP-18511 @CP-18511-4 @API-ECMS @James
  Scenario: Verify when a Inbound Document is updated so when 1 task rule requires case id, 1 requires consumer id, consumer id requiring task type will be created when consumer id only is found in event
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1      |
      | requiredDataElements | CaseID |
      | taskTypeID           | 12544  |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1          |
      | requiredDataElements | ConsumerID |
      | taskTypeID           | 12542      |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 2     |
      | requiredDataElements | null  |
      | taskTypeID           | 12541 |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 2     |
      | requiredDataElements | null  |
      | taskTypeID           | 12797 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers |
      | Language     | SPANISH                  |
      | Channel      | EMAIL                    |
      | Status       | COMPLETE                 |
      | FromName     | AUTOMATION               |
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | ConsumerID   | random                   |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ConsumerID     | random                   |
      | ProcessType    | RECEIVED                 |
      | Channel        | Email                    |
      | documentHandle | InboundDocumentIdDigital |
    When I send the request to create the Inbound Correspondence Save Event
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | documentType | maersk Case + Consumer |
    Then I should see a task of the following task types have been linked to the "previouslyCreated" Inbound Correspondence
      | 12542 |

  @CP-18511 @CP-18511-5 @API-ECMS @James
  Scenario: Verify when a Inbound Document is updated so when 1 task rule requires case id, 1 requires consumer id, no consumer id and no case id requiring task type will be created when neither is found in event
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1      |
      | requiredDataElements | CaseID |
      | taskTypeID           | 12544  |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1          |
      | requiredDataElements | ConsumerID |
      | taskTypeID           | 12542      |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 2     |
      | requiredDataElements | null  |
      | taskTypeID           | 12541 |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 2     |
      | requiredDataElements | null  |
      | taskTypeID           | 12797 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers |
      | Language     | SPANISH                  |
      | Channel      | EMAIL                    |
      | Status       | COMPLETE                 |
      | FromName     | AUTOMATION               |
      | ProcessType  | INBOUND CORRESPONDENCE   |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | RECEIVED                 |
      | Channel        | Email                    |
      | documentHandle | InboundDocumentIdDigital |
    When I send the request to create the Inbound Correspondence Save Event
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | documentType | maersk Case + Consumer |
    Then I should see a task of the following task types have been linked to the "previouslyCreated" Inbound Correspondence
      | 12541 |
      | 12797 |

  @CP-18511 @CP-18511-6 @API-ECMS @James
  Scenario: Verify when a Inbound Document is updated so when 1 task rule requires both case id and consumer id and an equal rank requiring 1 task rule requiring both and case id will be created when both found in event
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1                 |
      | requiredDataElements | CaseID,ConsumerID |
      | taskTypeID           | 12544             |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1      |
      | requiredDataElements | CaseID |
      | taskTypeID           | 12542  |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 2          |
      | requiredDataElements | ConsumerID |
      | taskTypeID           | 12541      |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 3     |
      | requiredDataElements | null  |
      | taskTypeID           | 12797 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers |
      | Language     | SPANISH                  |
      | Channel      | EMAIL                    |
      | Status       | COMPLETE                 |
      | FromName     | AUTOMATION               |
      | ProcessType  | INBOUND CORRESPONDENCE   |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | RECEIVED                 |
      | Channel        | Email                    |
      | CaseID         | random                   |
      | ConsumerID     | random                   |
      | documentHandle | InboundDocumentIdDigital |
    When I send the request to create the Inbound Correspondence Save Event
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | documentType | maersk Case + Consumer |
    Then I should see a task of the following task types have been linked to the "previouslyCreated" Inbound Correspondence
      | 12544 |
      | 12542 |

  @CP-18511 @CP-18511-7 @API-ECMS @James
  Scenario: Verify when a Inbound Document is updated so when 1 task rule requires both case id and consumer id and an equal rank requiring 1 task rule requiring both only case id task rule will be created when only case id found in event
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1                 |
      | requiredDataElements | CaseID,ConsumerID |
      | taskTypeID           | 12544             |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1      |
      | requiredDataElements | CaseID |
      | taskTypeID           | 12542  |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 2          |
      | requiredDataElements | ConsumerID |
      | taskTypeID           | 12541      |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 3     |
      | requiredDataElements | null  |
      | taskTypeID           | 12797 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers |
      | Language     | SPANISH                  |
      | Channel      | EMAIL                    |
      | Status       | COMPLETE                 |
      | FromName     | AUTOMATION               |
      | ProcessType  | INBOUND CORRESPONDENCE   |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | RECEIVED                 |
      | Channel        | Email                    |
      | CaseID         | random                   |
      | documentHandle | InboundDocumentIdDigital |
    When I send the request to create the Inbound Correspondence Save Event
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | documentType | maersk Case + Consumer |
    Then I should see a task of the following task types have been linked to the "previouslyCreated" Inbound Correspondence
      | 12542 |

  @CP-18511 @CP-18511-8 @API-ECMS @James
  Scenario: Verify when a Inbound Document is updated so when 1 task rule requires both case id and consumer id and an equal rank requiring 1 task rule requiring both only consumer id task rule will be created when only consumer id found in event
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1                 |
      | requiredDataElements | CaseID,ConsumerID |
      | taskTypeID           | 12544             |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1      |
      | requiredDataElements | CaseID |
      | taskTypeID           | 12542  |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 2          |
      | requiredDataElements | ConsumerID |
      | taskTypeID           | 12541      |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 3     |
      | requiredDataElements | null  |
      | taskTypeID           | 12797 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers |
      | Language     | SPANISH                  |
      | Channel      | EMAIL                    |
      | Status       | COMPLETE                 |
      | FromName     | AUTOMATION               |
      | ProcessType  | INBOUND CORRESPONDENCE   |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | RECEIVED                 |
      | Channel        | Email                    |
      | ConsumerID     | random                   |
      | documentHandle | InboundDocumentIdDigital |
    When I send the request to create the Inbound Correspondence Save Event
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | documentType | maersk Case + Consumer |
    Then I should see a task of the following task types have been linked to the "previouslyCreated" Inbound Correspondence
      | 12541 |

  @CP-18511 @CP-18511-9 @API-ECMS @James
  Scenario: Verify when a Inbound Document is updated so when 1 task rule requires both case id and consumer id and an equal rank requiring 1 task rule requiring both only no requirement task rule will be created when neither is found in event
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1                 |
      | requiredDataElements | CaseID,ConsumerID |
      | taskTypeID           | 12544             |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1      |
      | requiredDataElements | CaseID |
      | taskTypeID           | 12542  |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 2          |
      | requiredDataElements | ConsumerID |
      | taskTypeID           | 12541      |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 3     |
      | requiredDataElements | null  |
      | taskTypeID           | 12797 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers |
      | Language     | SPANISH                  |
      | Channel      | EMAIL                    |
      | Status       | COMPLETE                 |
      | FromName     | AUTOMATION               |
      | ProcessType  | INBOUND CORRESPONDENCE   |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | RECEIVED                 |
      | Channel        | Email                    |
      | documentHandle | InboundDocumentIdDigital |
    When I send the request to create the Inbound Correspondence Save Event
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | documentType | maersk Case + Consumer |
    Then I should see a task of the following task types have been linked to the "previouslyCreated" Inbound Correspondence
      | 12797 |

  @CP-18511 @CP-18511-10 @API-ECMS @James
  Scenario: Verify that task is not created if it is ALREADY WORKED when the Inbound Document is updated
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12544 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumers |
      | Language     | SPANISH                  |
      | Channel      | EMAIL                    |
      | Status       | COMPLETE                 |
      | FromName     | AUTOMATION               |
      | ProcessType  | ALREADY WORKED           |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | ALREADY WORKED           |
      | CHANNEL        | Mail                     |
      | documentType   | maersk Case + Consumers |
      | documentHandle | InboundDocumentIdDigital |
    When I send the request to create the Inbound Correspondence Save Event
    And I update the Inbound Correspondence "previouslyCreated" with the following values
      | documentType | maersk Case + Consumer |
    Then I should see that there are no tasks linked to the "previouslyCreated" Inbound Correspondence

  @CP-22506 @CP-22506-01 @ui-ecms1 @RuslanL
  Scenario: Verify new task linked to consumer and task is available on the task and service request page when inbound type is changed
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12544 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | previouslyCreated       |
      | ConsumerID   | previouslyCreated       |
      | Language     | ENGLISH                 |
      | Channel      | MAIL                    |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | documentType   | maersk Case + Consumer  |
      | CaseID         | previouslyCreated        |
      | ConsumerID     | previouslyCreated        |
      | Channel        | MAIL                     |
      | ProcessType    | RECEIVED                 |
      | documentHandle | InboundDocumentIdDigital |
    And I send the request to create the Inbound Correspondence Save Event
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumers"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumers" with the following properties;
      | rank                 | 1          |
      | requiredDataElements | ConsumerID |
      | taskTypeID           | 12542      |
      | LINKSAMESETTASK      | true       |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumers" - if not already setup as intended in the request
    When I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    And I click on the Inbound Document CID link "firstAvailable"
    And I store all tasks under Links section
    When I click on the edit button on the Inbound Document Details Page
    And I select "maersk Case + Consumers" from the type drop down on the Inbound Document Details Page
    And I click on the save button to save changes for the Inbound Document
    When I click on newly added task after type changed
    Then I should be navigated to Task Details Page
    And I should see a Link to the "Consumer profile" in the Links Section
    And I navigate to Task and service Request Page
    And I should see new task added to the task list

  @CP-22506 @CP-22506-02 @ui-ecms1 @RuslanL
  Scenario: Verify new task linked to case and task is available on the task and service request page when inbound type is changed
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12544 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | previouslyCreated       |
      | ConsumerID   | previouslyCreated       |
      | Language     | ENGLISH                 |
      | Channel      | MAIL                    |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | documentType   | maersk Case + Consumer  |
      | CaseID         | previouslyCreated        |
      | ConsumerID     | previouslyCreated        |
      | Channel        | MAIL                     |
      | ProcessType    | RECEIVED                 |
      | documentHandle | InboundDocumentIdDigital |
    And I send the request to create the Inbound Correspondence Save Event
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumers"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumers" with the following properties;
      | rank                 | 1          |
      | requiredDataElements | ConsumerID |
      | taskTypeID           | 12542      |
      | LINKSAMESETTASK      | true       |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumers" - if not already setup as intended in the request
    When I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    And I click on the Inbound Document CID link "firstAvailable"
    And I store all tasks under Links section
    When I click on the edit button on the Inbound Document Details Page
    And I select "maersk Case + Consumers" from the type drop down on the Inbound Document Details Page
    And I click on the save button to save changes for the Inbound Document
    When I click on newly added task after type changed
    Then I should be navigated to Task Details Page
    And I should see a Link to the "Case" in the Links Section
    And I navigate to Task and service Request Page
    And I should see new task added to the task list

  @CP-22506 @CP-22506-03 @ui-ecms1 @RuslanL
  Scenario: Verify link from the Application to the new Task and from new Task to Application when inbound type is changed
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have request to create Application with type "Medical Assistance" and name "Application for Medical Assistance"
    Then I send a request to create Application
    And I retrieve the Application details by application ID
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12544 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType  | maersk Case + Consumer |
      | CaseID        | previouslyCreated       |
      | ApplicationID | previouslyCreated       |
      | ConsumerID    | previouslyCreated       |
      | Language      | ENGLISH                 |
      | Channel       | MAIL                    |
      | Status        | COMPLETE                |
      | ProcessType   | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | documentType   | maersk Case + Consumer  |
      | CaseID         | previouslyCreated        |
      | ConsumerID     | previouslyCreated        |
      | ApplicationID  | previouslyCreated        |
      | Channel        | MAIL                     |
      | ProcessType    | RECEIVED                 |
      | documentHandle | InboundDocumentIdDigital |
    And I send the request to create the Inbound Correspondence Save Event
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumers"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumers" with the following properties;
      | rank                 | 1          |
      | requiredDataElements | ConsumerID |
      | taskTypeID           | 12542      |
      | LINKSAMESETTASK      | true       |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumers" - if not already setup as intended in the request
    When I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    And I click on the Inbound Document CID link "firstAvailable"
    And I store all tasks under Links section
    When I click on the edit button on the Inbound Document Details Page
    And I select "maersk Case + Consumers" from the type drop down on the Inbound Document Details Page
    And I click on the save button to save changes for the Inbound Document
    When I click on newly added task after type changed
    Then I should be navigated to Task Details Page
    And I verify link from new Task to Application is created
    And I verify link from Application to new Task is created

  @CP-33805 @CP-33805-01 @API-ECMS @RuslanL
  Scenario: I verify the response from Get Inbound Correspondence definition by project Id returns task rules and task type name (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1                 |
      | requiredDataElements | CaseID,ConsumerID |
      | taskTypeID           | 15003             |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 2          |
      | requiredDataElements | ConsumerID |
      | taskTypeID           | 12736      |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 3     |
      | requiredDataElements | null  |
      | taskTypeID           | 12797 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumers"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumers" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 15717 |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumers" with the following properties;
      | rank                 | 1          |
      | requiredDataElements | ConsumerID |
      | taskTypeID           | 15003      |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumers" - if not already setup as intended in the request
    When I retrieve and store task rules for each inbound correspondence definition
    Then I verify the response from Get Inbound Correspondence definition by project Id returns task rules and task type name

  @CP-33805 @CP-33805-02 @api-ecms-coverva @RuslanL
  Scenario: I verify the response from Get Inbound Correspondence definition by project Id returns task rules and task type name (CoverVA)
    Given I will get the Authentication token for "CoverVA" in "CRM"
    When I retrieve and store task rules for each inbound correspondence definition
    Then I verify the response from Get Inbound Correspondence definition by project Id returns task rules and task type name

  @CP-33805 @CP-33805-03 @api-ecms-ineb-REGRESSION @RuslanL
  Scenario: I verify the response from Get Inbound Correspondence definition by project Id returns task rules and task type name (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    When I retrieve and store task rules for each inbound correspondence definition
    Then I verify the response from Get Inbound Correspondence definition by project Id returns task rules and task type name

  @CP-33805 @CP-33805-04 @ui-ecms-nj @RuslanL
  Scenario: I verify the response from Get Inbound Correspondence definition by project Id returns task rules and task type name (NJ-EB)
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    When I retrieve and store task rules for each inbound correspondence definition
    Then I verify the response from Get Inbound Correspondence definition by project Id returns task rules and task type name

  @CP-34111 @CP-34111-01 @API-ECMS @RuslanL
  Scenario: Verify link from Task to Application when inbound document is received
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have request to create Application with type "Medical Assistance" and name "Application for Medical Assistance"
    Then I send a request to create Application
    And I retrieve the Application details by application ID
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType  | maersk Eligibility    |
      | ApplicationID | previouslyCreated      |
      | Language      | ENGLISH                |
      | Channel       | MAIL                   |
      | Status        | COMPLETE               |
      | ProcessType   | INBOUND CORRESPONDENCE |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | documentType   | maersk Eligibility      |
      | ApplicationID  | previouslyCreated        |
      | Channel        | MAIL                     |
      | ProcessType    | RECEIVED                 |
      | documentHandle | InboundDocumentIdDigital |
    And I send the request to create the Inbound Correspondence Save Event
    And I verify link from new Task to Application is created
    And I retrieve the "LINK_EVENT_TASK_TO_APPLICATION" event that is produced by trace id
    Then I should see the payload for the "LINK_EVENT_TASK_TO_APPLICATION" is as expected

  @CP-34111 @CP-34111-02 @API-ECMS @RuslanL
  Scenario: Verify link from Task to Cases when inbound document is received with the Application ID of that Application but contains no Case ID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have request to create Application with type "Medical Assistance" and name "Application for Medical Assistance"
    Then I send a request to create Application
    And I retrieve the Application details by application ID
    When I have request to link "previouslyCreated" Application to the multiple cases 3
    Then I send a request to link created Application to the cases
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType  | maersk Eligibility    |
      | ApplicationID | previouslyCreated      |
      | Language      | ENGLISH                |
      | Channel       | MAIL                   |
      | Status        | COMPLETE               |
      | ProcessType   | INBOUND CORRESPONDENCE |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | documentType   | maersk Eligibility      |
      | ApplicationID  | previouslyCreated        |
      | Channel        | MAIL                     |
      | ProcessType    | RECEIVED                 |
      | documentHandle | InboundDocumentIdDigital |
    And I send the request to create the Inbound Correspondence Save Event
    And I verify link from new Task to Case(s) is created
    And I retrieve the "LINK_EVENT_TASK_TO_CASES" event that is produced by trace id
    Then I should see the payload for the "LINK_EVENT_TASK_TO_CASES" is as expected

  @CP-34111 @CP-34111-03 @API-ECMS @RuslanL
  Scenario: Verify link from Task to only one case when inbound document is received with the Application ID and Case ID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have request to create Application with type "Medical Assistance" and name "Application for Medical Assistance"
    Then I send a request to create Application
    And I retrieve the Application details by application ID
    When I have request to link "previouslyCreated" Application to the multiple cases 3
    Then I send a request to link created Application to the cases
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType  | maersk Eligibility             |
      | ApplicationID | previouslyCreated               |
      | CaseID        | previouslyCreatedForApplication |
      | Language      | ENGLISH                         |
      | Channel       | MAIL                            |
      | Status        | COMPLETE                        |
      | ProcessType   | INBOUND CORRESPONDENCE          |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | documentType   | maersk Eligibility             |
      | ApplicationID  | previouslyCreated               |
      | CaseID         | previouslyCreatedForApplication |
      | Channel        | MAIL                            |
      | ProcessType    | RECEIVED                        |
      | documentHandle | InboundDocumentIdDigital        |
    And I send the request to create the Inbound Correspondence Save Event
    And I verify link from new Task to Case(s) is created
    And I retrieve the "LINK_EVENT_TASK_TO_CASES" event that is produced by trace id
    Then I should see the payload for the "LINK_EVENT_TASK_TO_CASES" is as expected
