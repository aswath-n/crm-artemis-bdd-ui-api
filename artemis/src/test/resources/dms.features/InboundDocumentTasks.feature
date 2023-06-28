Feature: Tasks Created By Receiving Inbound Documents


  @CP-2605 @CP-2605-01 @API-ECMS @ECMS-SMOKE @James
  Scenario: Verify a task is created when a rule has no required elements and there is not Case id or Consumer id in the event request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12544 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with no Case ID in addition to no Consumer ID
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType | RECEIVED |
      | CHANNEL     | Mail     |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should that a Task has been created containing all the values sent in the request

  @CP-2605 @CP-2605-02 @API-ECMS @James
  Scenario: Verify a task is created when a rule has no required elements and there is Case id or Consumer id in the event request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12544 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | CaseID      | random   |
      | ConsumerID  | random   |
      | ProcessType | RECEIVED |
      | CHANNEL     | Mail     |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should that a Task has been created containing all the values sent in the request


  @CP-2605 @CP-2605-03 @API-ECMS @James
  Scenario: Verify a task is created with Case and Consumer Id that Links are created in Task
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12544 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | CaseID      | random   |
      | ConsumerID  | random   |
      | ProcessType | RECEIVED |
      | CHANNEL     | Mail     |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see that a Task has been created containing the following Links
      | CASE     | fromRequest |
      | CONSUMER | fromRequest |

  @CP-2605 @CP-2605-04 @API-ECMS @James
  Scenario: Verify a task is not created when a Consumer id is required and there isn't one in the event request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1          |
      | requiredDataElements | ConsumerID |
      | taskTypeID           | 12544      |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | CaseID      | random   |
      | ProcessType | RECEIVED |
      | CHANNEL     | Mail     |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should that a Task has NOT been created

  @CP-2605 @CP-2605-05 @API-ECMS @James
  Scenario: Verify a task is not created when a Consumer id is required and there isn't a Case id or Consumer id
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1          |
      | requiredDataElements | ConsumerID |
      | taskTypeID           | 12544      |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | CaseID      | null     |
      | ConsumerID  | null     |
      | ProcessType | RECEIVED |
      | CHANNEL     | Mail     |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should that a Task has NOT been created

  @CP-2605 @CP-2605-06 @API-ECMS @James
  Scenario: Verify a task is not created when both a Case id and Consumer id is required and there is only a Case id in the request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1                 |
      | requiredDataElements | CaseID,ConsumerID |
      | taskTypeID           | 12544             |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | CaseID      | random   |
      | ProcessType | RECEIVED |
      | CHANNEL     | Mail     |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should that a Task has NOT been created

  @CP-2605 @CP-2605-07 @API-ECMS @James
  Scenario: Verify a task is not created when both a Case id and Consumer id is required and there is only a Consumer id in the request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1                 |
      | requiredDataElements | CaseID,ConsumerID |
      | taskTypeID           | 12544             |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ConsumerID  | random   |
      | ProcessType | RECEIVED |
      | CHANNEL     | Mail     |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should that a Task has NOT been created

  @CP-2605 @CP-2605-08 @API-ECMS @James
  Scenario: Verify that ZERO tasks are created when there are 2 task rules and each requires at least one element and no Case id or Consumer in the request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1      |
      | requiredDataElements | CaseID |
      | taskTypeID           | 12544  |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 2          |
      | requiredDataElements | ConsumerID |
      | taskTypeID           | 12541      |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 3                 |
      | requiredDataElements | CaseID,ConsumerID |
      | taskTypeID           | 12542             |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | CaseID      | null     |
      | ConsumerID  | null     |
      | ProcessType | RECEIVED |
      | CHANNEL     | Mail     |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should that a Task has NOT been created

  @CP-2605 @CP-2605-09 @API-ECMS @James
  Scenario: Verify that highest rank task is created when it is the only task that has all required elements in the request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1      |
      | requiredDataElements | CaseID |
      | taskTypeID           | 12544  |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 2          |
      | requiredDataElements | ConsumerID |
      | taskTypeID           | 12541      |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 3                 |
      | requiredDataElements | CaseID,ConsumerID |
      | taskTypeID           | 12542             |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | CaseID      | random   |
      | ConsumerID  | random   |
      | ProcessType | RECEIVED |
      | CHANNEL     | Mail     |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see that a Task has been created with the taskTypeID of "12544"

  @CP-2605 @CP-2605-10 @API-ECMS @James
  Scenario: Verify that when type has a task rule requiring a Consumer id and only a Consumer id is in the event request that it is the only task created
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1      |
      | requiredDataElements | CaseID |
      | taskTypeID           | 12541  |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 2          |
      | requiredDataElements | ConsumerID |
      | taskTypeID           | 12542      |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 3                 |
      | requiredDataElements | CaseID,ConsumerID |
      | taskTypeID           | 12544             |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ConsumerID  | random   |
      | ProcessType | RECEIVED |
      | CHANNEL     | Mail     |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see that a Task has been created with the taskTypeID of "12542"

  @CP-2605 @CP-2605-11 @API-ECMS @James
  Scenario: Verify that when type has a task rule requiring a Case id and only a Case id is in the event request that it is the only task created
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1      |
      | requiredDataElements | CaseID |
      | taskTypeID           | 12542  |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 2          |
      | requiredDataElements | ConsumerID |
      | taskTypeID           | 12544      |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 3                 |
      | requiredDataElements | CaseID,ConsumerID |
      | taskTypeID           | 12541             |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | CaseID      | random   |
      | ProcessType | RECEIVED |
      | CHANNEL     | Mail     |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see that a Task has been created with the taskTypeID of "12542"


  @CP-2605 @CP-2605-12 @API-ECMS @James
  Scenario: Verify that when type has a task rule requiring nothing and there is no Case id or Consumer in the request then it is the task created
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1      |
      | requiredDataElements | CaseID |
      | taskTypeID           | 12544  |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 2          |
      | requiredDataElements | ConsumerID |
      | taskTypeID           | 12541      |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 3                 |
      | requiredDataElements | CaseID,ConsumerID |
      | taskTypeID           | 12544             |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 4     |
      | requiredDataElements | null  |
      | taskTypeID           | 12542 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | CaseID      | null     |
      | ConsumerID  | null     |
      | ProcessType | RECEIVED |
      | CHANNEL     | Mail     |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see that a Task has been created with the taskTypeID of "12542"

  @CP-2605 @CP-2605-13 @API-ECMS @James
  Scenario: Verify that is not created if it is ALREADY WORKED
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1      |
      | requiredDataElements | CaseID |
      | taskTypeID           | 12544  |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 2          |
      | requiredDataElements | ConsumerID |
      | taskTypeID           | 12544      |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 3                 |
      | requiredDataElements | CaseID,ConsumerID |
      | taskTypeID           | 12544             |
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 4     |
      | requiredDataElements | null  |
      | taskTypeID           | 12542 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | CaseID      | random         |
      | ConsumerID  | random         |
      | ProcessType | ALREADY WORKED |
      | CHANNEL     | Mail           |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should that a Task has NOT been created

  @CP-2605 @CP-2605-14 @API-ECMS @James
  Scenario: Verify that when a task is created, all the configured values are in the task
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1      |
      | requiredDataElements | CaseID |
      | taskTypeID           | 12544  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | CaseID         | random                    |
      | ProcessType    | RECEIVED                  |
      | Channel        | Email                     |
      | FromEmail      | Automation2921@jadskj.com |
      | FromName       | Automation                |
      | NotificationID | random                    |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see that a Task has been created with the taskTypeID of "12544"
    And I should see that a Task has been created containing the following values
      | priority                    | 4                         |
      | dueDate                     | 6                         |
      | status                      | Created                   |
      | statusDate                  | Current_SysDate           |
      | source                      | System                    |
      | createdOn                   | Current_SysDate           |
      | createdBy                   | Ecms Service              |
      | taskType                    | 12544                     |
      | Inbound Correspondence Type | maersk Case + Consumer   |
      | Channel                     | Email                     |
      | From Email                  | Automation2921@jadskj.com |
      | From Name                   | Automation                |
      | Notification ID             | random                    |

  @CP-2605 @CP-2605-15 @ui-ecms1 @James
  Scenario: Verify a task is created, it is viewable in Connection Point
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12544 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | CaseID      | 7347     |
      | ConsumerID  | 17500    |
      | ProcessType | RECEIVED |
      | CHANNEL     | Mail     |
    And I send the request to create the Inbound Correspondence Save Event
    And I should see that a Task has been created with the taskTypeID of "12544"
    When I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on the Inbound Document CID link "firstAvailable"
    Then I should see a link to the follow entities in connection point
      | Case     | 7347       |
      | Consumer | 17500      |
      | Task     | CREATED ID |


  @CP-3251 @CP-3251-01 @API-ECMS @Sang
  Scenario: Verify Inbound Correspondence save event is created with correct values for post connection send event
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1      |
      | requiredDataElements | CaseID |
      | taskTypeID           | 12544  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE                  | CREATEDDOCUMENT          |
      | CaseID                | 3122                     |
      | ConsumerID            | 9111                     |
      | Channel               | Email                    |
      | SetId                 | random                   |
      | Status                | RECEIVED                 |
      | FromName              | Howard Johnson           |
      | FromEmail             | jamesmlacson@maersk.com |
      | FromPhoneNumber       | 7035692152               |
      | ReturnedReason        | undeliverable            |
      | NotificationID        | 68754                    |
      | FormVersion           | 67984                    |
      | Language              | Tagalog                  |
      | ReplacementDocumentId | 87848                    |
      | RescanOfDocumentId    | 87848                    |
      | Batch #               | 848                      |
      | ProcessType           | INBOUND CORRESPONDENCE   |
      | BarcodeID             | 22152                    |
    When I send the request to create the Inbound Correspondence Save Event
    And I initiate post search "INBOUND_CORRESPONDENCE_SAVE_EVENT" list to find the Event ID created
    And I initiate get inbound correspondence event save event by the eventID found in list of Inbound Correspondence Save Event
    Then I verify the values contained in the payload of the save events

  @CP-3251 @CP-3251-02 @API-ECMS @Sang
  Scenario: Verify Inbound Correspondence save event is not created when sending invalid specified JSON
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1      |
      | requiredDataElements | CaseID |
      | taskTypeID           | 12544  |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save Event with "Invalid" as value for the type
    Then I send the request to create the Inbound Correspondence Save Event then I should see status value is "FAILED"

  @CP-3251 @CP-3251-03 @API-ECMS @ECMS-SMOKE @api-smoke-devops @James
  Scenario: Verify Inbound Correspondence save event returns success response with valid request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | CHANNEL      | Mail                    |
      | documentType | maersk Case + Consumer |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see that the Inbound Correspondence Save Event returned a success response

  @CP-4504 @CP-4504-01 @API-ECMS @Sang
  Scenario: Verify the Get Inbound Correspondence Document by document ID has the correct Key and value in the response
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate Get inbound correspondence document by "17934" document Id
    Then I verify the response from Get Inbound Correspondence by document Id "17934" returns valid response

  @CP-4504 @CP-4504-02 @API-ECMS @Sang
  Scenario: Verify the Get Inbound Correspondence Document by invalid document ID gives status failed response
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate Get inbound correspondence document by "RANDOM TEN" document Id
    Then I verify the response from Get Inbound Correspondence by document by Random ten returns a Failed status