Feature: Tasks Created By Receiving Inbound Documents Part 2

  @CP-16060 @CP-16060-1 @API-ECMS @James
  Scenario: Verify that 2 task rules of rank 1 with no required fields will produce 2 tasks
    Given I will get the Authentication token for "<projectName>" in "CRM"
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
      | Channel     | Email    |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see that Tasks for the following Task Type Ids have been created
      | 12544 |
      | 12542 |

  @CP-16060 @CP-16060-2 @API-ECMS @James
  Scenario: Verify when 1 task rule requires case id, 1 requires consumer id, 2 tasks will be created when both found in event
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
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | CaseID      | random   |
      | ConsumerID  | random   |
      | ProcessType | RECEIVED |
      | Channel     | Email    |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see that Tasks for the following Task Type Ids have been created
      | 12544 |
      | 12542 |

  @CP-16060 @CP-16060-3 @API-ECMS @James
  Scenario: Verify when 1 task rule requires case id, 1 requires consumer id, case id requiring task type will be created when case id only is found in event
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
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | CaseID      | random   |
      | ProcessType | RECEIVED |
      | Channel     | Email    |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see that Tasks for the following Task Type Ids have been created
      | 12544 |

  @CP-16060 @CP-16060-4 @API-ECMS @James
  Scenario: Verify when 1 task rule requires case id, 1 requires consumer id, consumer id requiring task type will be created when consumer id only is found in event
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
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ConsumerID  | random   |
      | ProcessType | RECEIVED |
      | Channel     | Email    |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see that Tasks for the following Task Type Ids have been created
      | 12542 |

  @CP-16060 @CP-16060-5 @API-ECMS @James
  Scenario: Verify when 1 task rule requires case id, 1 requires consumer id, no consumer id and no case id requiring task type will be created when neither is found in event
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
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType | RECEIVED |
      | Channel     | Email    |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see that Tasks for the following Task Type Ids have been created
      | 12541 |
      | 12797 |

  @CP-16060 @CP-16060-6 @API-ECMS @James
  Scenario: Verify when 1 task rule requires both case id and consumer id and an equal rank requiring 1 task rule requiring both and case id will be created when both found in event
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
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | CaseID      | random   |
      | ConsumerID  | random   |
      | ProcessType | RECEIVED |
      | Channel     | Email    |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see that Tasks for the following Task Type Ids have been created
      | 12544 |
      | 12542 |

  @CP-16060 @CP-16060-7 @API-ECMS @James
  Scenario: Verify when 1 task rule requires both case id and consumer id and an equal rank requiring 1 task rule requiring both only case id task rule will be created when only case id found in event
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
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | CaseID      | random   |
      | ProcessType | RECEIVED |
      | Channel     | Email    |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see that Tasks for the following Task Type Ids have been created
      | 12542 |

  @CP-16060 @CP-16060-8 @API-ECMS @James
  Scenario: Verify when 1 task rule requires both case id and consumer id and an equal rank requiring 1 task rule requiring both only consumer id task rule will be created when only consumer id found in event
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
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ConsumerID  | random   |
      | ProcessType | RECEIVED |
      | Channel     | Email    |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see that Tasks for the following Task Type Ids have been created
      | 12541 |

  @CP-16060 @CP-16060-9 @API-ECMS @James
  Scenario: Verify when 1 task rule requires both case id and consumer id and an equal rank requiring 1 task rule requiring both only no requirement task rule will be created when neither is found in event
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
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType | RECEIVED |
      | Channel     | Email    |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see that Tasks for the following Task Type Ids have been created
      | 12797 |

  @CP-21614 @CP-21614-1 @ui-ecms1 @James
  Scenario: Verify links component refreshes tasks visible when type is changed
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
      | ProcessType | COMPLETE |
      | CHANNEL     | Mail     |
    And I send the request to create the Inbound Correspondence Save Event
    When I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on the Inbound Document CID link "firstAvailable"
    When I click on the edit button on the Inbound Document Details Page
    And I select "maersk Case + Consumers" from the type drop down on the Inbound Document Details Page
    And I should see a pop up message saying "If you change the Correspondence Type the system may create a new workflow item."
    And I click on the save button to save changes for the Inbound Document
    Then I should see the new task in the Links Component of the Inbound Document Details Page

  @CP-21354 @CP-21354-01 @API-ECMS @ECMS-SMOKE @James
  Scenario: Verify an event will be received by connection even when a Inbound Document type does not have a type rule configured
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV Unknown"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | RECEIVED     |
      | CHANNEL      | Mail         |
      | documentType | VACV Unknown |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see the event from onbase has been received by Connection Point

  @CP-18873 @CP-18873-1 @ui-ecms1 @James
  Scenario: Verify pop up when task exists pop up
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12544 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType | COMPLETE |
      | CHANNEL     | Mail     |
    And I send the request to create the Inbound Correspondence Save Event
    When I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on the Inbound Document CID link "firstAvailable"
    When I click on the edit button on the Inbound Document Details Page
    And I select "maersk Case + Consumers" from the type drop down on the Inbound Document Details Page
    Then I should see a pop up message saying "If you change the Correspondence Type the system may create a new workflow item. You will need to take the appropriate action to finalize the existing workflow item(s) already linked to this Correspondence."

  @CP-18873 @CP-18873-2 @ui-ecms1 @James
  Scenario: Verify pop up when no task exists pop up
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    When I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on the Inbound Document CID link "firstAvailable"
    When I click on the edit button on the Inbound Document Details Page
    And I select "maersk Case + Consumers" from the type drop down on the Inbound Document Details Page
    Then I should see a pop up message saying "If you change the Correspondence Type the system may create a new workflow item."

  @CP-20941 @CP-20941-1 @API-ECMS @James
  Scenario: Verify keyword records with consumer Id are counted as keywords of consumer Id
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumers Requiring Metadata"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumers Requiring Metadata" with the following properties;
      | rank                 | 1          |
      | requiredDataElements | ConsumerID |
      | taskTypeID           | 12544      |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumers Requiring Metadata"
    And I have an Inbound Correspondence Save event Request with the following values
      | ConsumerIDs | 17500    |
      | ProcessType | COMPLETE |
      | CHANNEL     | Mail     |
    And I send the request to create the Inbound Correspondence Save Event
    Then I should see that Tasks for the following Task Type Ids have been created
      | 12544 |

  @CP-20941 @CP-20941-2 @API-ECMS @James
  Scenario: Verify keyword records with consumer Id are linked to the consumer
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumers Requiring Metadata"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumers Requiring Metadata" with the following properties;
      | rank                 | 1          |
      | requiredDataElements | ConsumerID |
      | taskTypeID           | 12544      |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumers Requiring Metadata"
    And I have an Inbound Correspondence Save event Request with the following values
      | ConsumerIDs  | RANDOM   |
      | ProcessType  | COMPLETE |
      | CHANNEL      | Mail     |
      | documentDate | Today    |
    And I send the request to create the Inbound Correspondence Save Event
    Then I should see there is a link between Inbound Correspondence Document "previouslyCreated" and Consumer Id "RANDOM"

  @CP-20758 @CP-20758-1 @API-ECMS @ECMS-SMOKE @James
  Scenario: Verify duplicate tasks are not created
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1          |
      | requiredDataElements | ConsumerID |
      | taskTypeID           | 12544      |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ConsumerID  | RANDOM   |
      | ProcessType | COMPLETE |
      | CHANNEL     | Mail     |
    And I send the request to create the Inbound Correspondence Save Event
    And I update the Inbound Correspondence "inboundDocumentId" with the following values
      | documentType | maersk Case + Consumers |
    And I see that a specific amount of tasks have been created
    When I update the Inbound Correspondence "inboundDocumentId" with the following values
      | documentType | maersk Case + Consumer |
    Then I should see the same amount of tasks are linked to the Inbound Document as before the type update

  @CP-14486 @CP-14486-1 @API-ECMS @James
  Scenario: Verify document handle is required from onbase
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumers Requiring Metadata"
    And I have an Inbound Correspondence Save event Request with the following values
      | ConsumerIDs    | 17500    |
      | ProcessType    | COMPLETE |
      | CHANNEL        | Mail     |
      | documentHandle | null     |
    And I send the request to create the Inbound Correspondence Save Event
    Then I should see the response from Connection Point is status "400" with message " Document ID missing or is not a positive integer"

  @CP-14486 @CP-14486-2 @API-ECMS @James
  Scenario: Verify document handle should be a positive from onbase
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumers Requiring Metadata"
    And I have an Inbound Correspondence Save event Request with the following values
      | ConsumerIDs    | 17500    |
      | ProcessType    | COMPLETE |
      | CHANNEL        | Mail     |
      | documentHandle | -151     |
    And I send the request to create the Inbound Correspondence Save Event
    Then I should see the response from Connection Point is status "400" with message " Document ID missing or is not a positive integer"

  @CP-14486 @CP-14486-3 @API-ECMS @James
  Scenario: Verify document type is required from onbase
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumers Requiring Metadata"
    And I have an Inbound Correspondence Save event Request with the following values
      | ConsumerIDs  | 17500                  |
      | ProcessType  | COMPLETE               |
      | CHANNEL      | Mail                   |
      | documentType | maersk Not Right Type |
    And I send the request to create the Inbound Correspondence Save Event
    Then I should see the response from Connection Point is status "400" with message " Document Type missing or not a configured Document Type value (in CP)"

  @CP-14486 @CP-14486-4 @API-ECMS @James
  Scenario: Verify document date is required from onbase
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumers Requiring Metadata"
    And I have an Inbound Correspondence Save event Request with the following values
      | ConsumerIDs  | 17500                                       |
      | ProcessType  | COMPLETE                                    |
      | CHANNEL      | Mail                                        |
      | documentDate | null                                        |
      | documentType | maersk Case + Consumers Requiring Metadata |
    And I send the request to create the Inbound Correspondence Save Event
    Then I should see the response from Connection Point is status "400" with message " Document Date missing or does not contain a date"

  @CP-14486 @CP-14486-5 @API-ECMS @James
  Scenario: Verify document date is a valid iso format from onbase
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumers Requiring Metadata"
    And I have an Inbound Correspondence Save event Request with the following values
      | ConsumerIDs  | 17500                                       |
      | ProcessType  | COMPLETE                                    |
      | CHANNEL      | Mail                                        |
      | documentDate | 12345                                       |
      | documentType | maersk Case + Consumers Requiring Metadata |
    And I send the request to create the Inbound Correspondence Save Event
    Then I should see the response from Connection Point is status "400" with message "Document Date is invalid"

  @CP-14486 @CP-14486-6 @API-ECMS @James
  Scenario: Verify document channel is required from onbase
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumers Requiring Metadata"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | COMPLETE                                    |
      | documentType | maersk Case + Consumers Requiring Metadata |
    And I send the request to create the Inbound Correspondence Save Event
    Then I should see the response from Connection Point is status "400" with message " Channel missing"

  @CP-14486 @CP-14486-7 @API-ECMS @James
  Scenario: Verify document status is required from onbase
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumers Requiring Metadata"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | COMPLETE                                    |
      | CHANNEL      | MAIL                                        |
      | STATUS       | null                                        |
      | documentType | maersk Case + Consumers Requiring Metadata |
    And I send the request to create the Inbound Correspondence Save Event
    Then I should see the response from Connection Point is status "400" with message " Status is missing"

  @CP-14486 @CP-14486-8 @API-ECMS @James
  Scenario: Verify document status set on is required from onbase
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumers Requiring Metadata"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | COMPLETE                                    |
      | CHANNEL      | MAIL                                        |
      | StatusSetOn  | null                                        |
      | documentType | maersk Case + Consumers Requiring Metadata |
    And I send the request to create the Inbound Correspondence Save Event
    Then I should see the response from Connection Point is status "400" with message " Status Set On is missing or does not contain a timestamp"

  @CP-14486 @CP-14486-9 @API-ECMS @James
  Scenario: Verify document status set on contains a time stamp
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumers Requiring Metadata"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | COMPLETE                                    |
      | CHANNEL      | MAIL                                        |
      | StatusSetOn  | 2021-01-01                                  |
      | documentType | maersk Case + Consumers Requiring Metadata |
    And I send the request to create the Inbound Correspondence Save Event
    Then I should see the response from Connection Point is status "400" with message " Invalid Status Set On"

  @CP-23503 @CP-23503-1 @API-ECMS @James
  Scenario: Verify able to retrieve active links
    Given I will get the Authentication token for "<projectName>" in "CRM"
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
      | Channel     | Email    |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should see the bff links api returns active links for the "previouslyCreated" Inbound Correspondence

  @CP-23503 @CP-23503-2 @API-ECMS @James
  Scenario: Verify able to retrieve inactive links
    Given I will get the Authentication token for "<projectName>" in "CRM"
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
      | Channel     | Email    |
    When I send the request to create the Inbound Correspondence Save Event
    And I Unlink the "previouslyCreated" Task from the Inbound Correspondence
    Then I should see the bff links api returns ALL links for the "previouslyCreated" Inbound Correspondence
