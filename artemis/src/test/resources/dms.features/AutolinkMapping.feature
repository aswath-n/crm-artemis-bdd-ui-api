Feature: Autolink Mapping feature

  @CP-22861 @CP-22861-01 @CP-37435 @CP-37435-01 @API-ECMS @burak
  Scenario: Create AutoLink Mapping with single values and verify the details
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I will initiate and post autolinkMap API with following values for "92" project ID
      | inboundCorrespondenceTypes | maersk Eligibility |
      | serviceRequestTypeIDs      | Random              |
      | correspondenceMetadataName | Application         |
      | serviceRequestFieldName    | Task                |
    And I verify success message along with created AutoLinkMap ID
    And I store the created AutoLinkMap ID
    And I retrieve the AutoLink Maps for "Current" project ID
    And I verify created AutoLink Map Details

  @CP-22861 @CP-22861-02 @CP-37435 @CP-37435-02 @API-ECMS @burak
  Scenario: Create AutoLink Mapping with multiple values and verify the details
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I will initiate and post autolinkMap API with following values for "92" project ID
      | inboundCorrespondenceTypes | maersk Eligibility,maersk Case+Consumer |
      | serviceRequestTypeIDs      | Random,Random                             |
      | correspondenceMetadataName | Application,Case                          |
      | serviceRequestFieldName    | Task,Service Request                      |
    And I verify success message along with created AutoLinkMap ID
    And I store the created AutoLinkMap ID
    And I retrieve the AutoLink Maps for "Current" project ID
    And I verify created AutoLink Map Details

  @CP-37436 @CP-37436-01 @CP-37435 @CP-37435-03 @API-ECMS @burak
  Scenario: Verify AutoLink Map got deleted
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I will initiate and post autolinkMap API with following values for "Current" project ID
      | inboundCorrespondenceTypes | maersk Eligibility |
      | serviceRequestTypeIDs      | Random              |
      | correspondenceMetadataName | Application         |
      | serviceRequestFieldName    | Task                |
    And I verify success message along with created AutoLinkMap ID
    And I store the created AutoLinkMap ID
    And I delete created AutoLink Map ID and verify successfully deleted message
    And I retrieve the AutoLink Maps for "Current" project ID
    And I verify created AutoLink Map doesn't exist on the list

  @CP-37436 @CP-37436-02 @API-ECMS @burak
  Scenario: Verify Delete AutoLink Map returns error when deleting non-existing Auto Link Map ID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I will initiate and post autolinkMap API with following values for "Current" project ID
      | inboundCorrespondenceTypes | maersk Eligibility |
      | serviceRequestTypeIDs      | Random              |
      | correspondenceMetadataName | Application         |
      | serviceRequestFieldName    | Task                |
    And I verify success message along with created AutoLinkMap ID
    And I store the created AutoLinkMap ID
    And I delete created AutoLink Map ID and verify successfully deleted message
    And I attempt the delete same AutoLink ID and verify error message

  @CP-37435 @CP-37436-04 @API-ECMS @burak
  Scenario: Verify Retrieve AutoLink Map API returns empty array for invalid Project Id
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I retrieve the AutoLink Maps for "754574" project ID
    And I verify retrieve AutoLink Map Endpoint returns empty array

  @CP-22861 @CP-22861-03 @API-ECMS @burak
  Scenario Outline: Verify Single AutoLink Map Error message when the field is null
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I will initiate and post autolinkMap API with following values for "Current" project ID
      | inboundCorrespondenceTypes | <inboundCorrespondenceTypes> |
      | serviceRequestTypeIDs      | <serviceRequestTypeIDs>      |
      | correspondenceMetadataName | <correspondenceMetadataName> |
      | serviceRequestFieldName    | <serviceRequestFieldName>    |
    And I verify error message for "<fieldName>" as "<errorMessage>"
    Examples:
      | inboundCorrespondenceTypes | serviceRequestTypeIDs | correspondenceMetadataName | serviceRequestFieldName | fieldName                     | errorMessage                                       |
      | null                       | Random                | Application                | Task                    | inboundCorrespondenceTypes[0] | InboundCorrespondence Type cannot be null or empty |
      |                            | Random                | Application                | Task                    | inboundCorrespondenceTypes[0] | InboundCorrespondence Type cannot be null or empty |
      | maersk Eligibility        | null                  | Application                | Task                    | serviceRequestTypeIDs[0]      | ServiceRequest Type Id cannot be null or empty     |
      | maersk Eligibility        |                       | Application                | Task                    | serviceRequestTypeIDs[0]      | ServiceRequest Type Id cannot be null or empty     |
      | maersk Eligibility        | Random                | null                       | Task                    | correspondenceMetadataName[0] | Correspondence metadata cannot be empty or null    |
      | maersk Eligibility        | Random                |                            | Task                    | correspondenceMetadataName[0] | Correspondence metadata cannot be empty or null    |
      | maersk Eligibility        | Random                | Application                | null                    | serviceRequestFieldName[0]    | Service request field name cannot be null or empty |
      | maersk Eligibility        | Random                | Application                |                         | serviceRequestFieldName[0]    | Service request field name cannot be null or empty |

  @CP-22861 @CP-22861-04 @API-ECMS @burak
  Scenario Outline: Verify Multiple AutoLink Map Error message when the field is null
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I will initiate and post autolinkMap API with following values for "Current" project ID
      | inboundCorrespondenceTypes | <inboundCorrespondenceTypes>,maersk Eligibility |
      | serviceRequestTypeIDs      | <serviceRequestTypeIDs>,Random                   |
      | correspondenceMetadataName | Application,<correspondenceMetadataName>         |
      | serviceRequestFieldName    | Task,<serviceRequestFieldName>                   |
    And I verify error message for "<fieldName>" as "<errorMessage>"
    Examples:
      | inboundCorrespondenceTypes | serviceRequestTypeIDs | correspondenceMetadataName | serviceRequestFieldName | fieldName                                                                                                       | errorMessage                                                                                                                                                                                         |
      |                            |                       | Application                | Task                    | inboundCorrespondenceTypes[0],serviceRequestTypeIDs[0]                                                          | InboundCorrespondence Type cannot be null or empty,ServiceRequest Type Id cannot be null or empty                                                                                                    |
      | maersk Case+Consumer      | Random                |                            |                         | correspondenceMetadataName[1],serviceRequestFieldName[1]                                                        | Correspondence metadata cannot be empty or null,Service request field name cannot be null or empty                                                                                                   |
      |                            |                       |                            |                         | inboundCorrespondenceTypes[0],serviceRequestTypeIDs[0],correspondenceMetadataName[1],serviceRequestFieldName[1] | InboundCorrespondence Type cannot be null or empty,ServiceRequest Type Id cannot be null or empty,Correspondence metadata cannot be empty or null,Service request field name cannot be null or empty |

    ###################################################################CP-22634######################################################################

  @CP-22634 @CP-22634-01 @API-ECMS @burak
  Scenario: Verify Inbound Document Auto Linked to the Task for single inboundCorrespondenceType Defined
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | taskTypeId | 13240             |
    Then I send the request for the External task endpoint
    And I will initiate and post autolinkMap API with following values for "Current" project ID if there is no exact match
      | inboundCorrespondenceTypes | maersk Case + Consumer |
      | serviceRequestTypeIDs      | 13240                   |
      | correspondenceMetadataName | CaseID                  |
      | serviceRequestFieldName    | Case ID                 |
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE    | PROCESSEDDOCUMENT |
      | CHANNEL | Mail              |
      | CaseID  | PreviouslyCreated |
    When I send the request to create the Inbound Correspondence Save Event
    And Wait for 12 seconds
    Then I should see there is a link between Task Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated" and total of 2 Links.
    And Close the soft assertions

  @CP-22634 @CP-22634-02 @API-ECMS @burak
  Scenario: Verify Inbound Document Auto Linked to the Task and new SR is not created  When There is a matching Task Rule with Autolink Mapping Configuration
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 16044 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | taskTypeId | 16044             |
    Then I send the request for the External task endpoint
    And I will initiate and post autolinkMap API with following values for "Current" project ID if there is no exact match
      | inboundCorrespondenceTypes | maersk Case + Consumer |
      | serviceRequestTypeIDs      | 16044                   |
      | correspondenceMetadataName | CaseID                  |
      | serviceRequestFieldName    | Case ID                 |
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | AUTOMATION              |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | CaseID       | PreviouslyCreated       |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE           | PROCESSEDDOCUMENT        |
      | ProcessType    | RECEIVED                 |
      | Channel        | Email                    |
      | documentType   | maersk Case + Consumer  |
      | documentHandle | InboundDocumentIdDigital |
      | CaseID         | PreviouslyCreated        |
    When I send the request to create the Inbound Correspondence Save Event
    And Wait for 12 seconds
    Then I should see there is a link between Task Id "previouslyCreated" and Inbound Correspondence Document "inboundDocumentId" and total of 2 Links.
    Then I should see there is a link between Inbound Correspondence Document "inboundDocumentId" and SR "previouslyCreated" total of 1 different SR Linked.
    And Close the soft assertions

  @CP-22634 @CP-22634-03 @API-ECMS @burak
  Scenario: Verify Inbound Document is Linked to the SR when there are 2 fieldMappings configured
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 13240             |
    Then I send the request for the External task endpoint
    And I will initiate and post autolinkMap API with following values for "Current" project ID if there is no exact match
      | inboundCorrespondenceTypes | maersk Case + Consumer |
      | serviceRequestTypeIDs      | 13240                   |
      | correspondenceMetadataName | ConsumerID,CaseID       |
      | serviceRequestFieldName    | Consumer ID,Case ID     |
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE       | PROCESSEDDOCUMENT |
      | CHANNEL    | Mail              |
      | CaseID     | PreviouslyCreated |
      | ConsumerID | PreviouslyCreated |
    When I send the request to create the Inbound Correspondence Save Event
    And Wait for 12 seconds
    Then I should see there is a link between Task Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated" and total of 3 Links.
    And Close the soft assertions

  @CP-22634 @CP-22634-04 @API-ECMS @burak
  Scenario: Verify Inbound Document is  linked to the SR when one of the duplicate SR is closed
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 13241             |
    Then I send the request for the External task endpoint
    Then I update task status to "Closed"
    Then I verify task status successfully updated to "Closed"
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 13241             |
    Then I send the request for the External task endpoint
    And I will initiate and post autolinkMap API with following values for "Current" project ID if there is no exact match
      | inboundCorrespondenceTypes | maersk Case + Consumer |
      | serviceRequestTypeIDs      | 13241                   |
      | correspondenceMetadataName | ConsumerID,CaseID       |
      | serviceRequestFieldName    | Consumer ID,Case ID     |
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE       | PROCESSEDDOCUMENT |
      | CHANNEL    | Mail              |
      | CaseID     | PreviouslyCreated |
      | ConsumerID | PreviouslyCreated |
    When I send the request to create the Inbound Correspondence Save Event
    And Wait for 12 seconds
    Then I should see there is a link between Task Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated" and total of 4 Links.
    And Close the soft assertions

  @CP-22634 @CP-22634-05 @API-ECMS @burak
  Scenario: Verify Inbound Document Auto Linked to the Task for single inboundCorrespondenceType Defined for Cover-Va
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | taskTypeId | 13495             |
    Then I send the request for the External task endpoint
    And I will initiate and post autolinkMap API with following values for "Current" project ID if there is no exact match
      | inboundCorrespondenceTypes | VACV Unknown |
      | serviceRequestTypeIDs      | 13495        |
      | correspondenceMetadataName | CaseID       |
      | serviceRequestFieldName    | Case ID      |
    And I have a Inbound Document that with the Inbound Document Type of "VACV Unknown"
    And I want to edit an Inbound Correspondence Type by the name of "VACV Unknown"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE    | PROCESSEDDOCUMENT |
      | CHANNEL | Mail              |
      | CaseID  | PreviouslyCreated |
    When I send the request to create the Inbound Correspondence Save Event
    And Wait for 12 seconds
    Then I should see there is a link between Task Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated" and total of 3 Links.
    And Close the soft assertions

  @CP-22634 @CP-22634-06 @API-ECMS @burak
  Scenario: Verify Inbound Document Auto Linked to the Task for single inboundCorrespondenceType Defined for IN-EB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I initiate Case Correspondence API for created Consumer
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | taskTypeId | 15414             |
    Then I send the request for the External task endpoint
    And I will initiate and post autolinkMap API with following values for "Current" project ID if there is no exact match
      | inboundCorrespondenceTypes | INEB Just Cause Form |
      | serviceRequestTypeIDs      | 15414                |
      | correspondenceMetadataName | CaseID               |
      | serviceRequestFieldName    | Case ID              |
    And I have a Inbound Document that with the Inbound Document Type of "INEB Just Cause Form"
    And I want to edit an Inbound Correspondence Type by the name of "INEB Just Cause Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE    | PROCESSEDDOCUMENT |
      | CHANNEL | Mail              |
      | CaseID  | PreviouslyCreated |
    When I send the request to create the Inbound Correspondence Save Event
    And Wait for 12 seconds
    Then I should see there is a link between Task Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated" and total of 3 Links.
    And Close the soft assertions

  @CP-22634 @CP-22634-07 @API-ECMS @burak
  Scenario: Verify Inbound Document Not Linked to the Task When the Type is not PROCESSED DOCUMENT
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | taskTypeId | 12797             |
    Then I send the request for the External task endpoint
    And I will initiate and post autolinkMap API with following values for "Current" project ID if there is no exact match
      | inboundCorrespondenceTypes | maersk Case + Consumer |
      | serviceRequestTypeIDs      | 12797                   |
      | correspondenceMetadataName | CaseID                  |
      | serviceRequestFieldName    | Case ID                 |
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE    | CREATEDDOCUMENT   |
      | CHANNEL | Mail              |
      | CaseID  | PreviouslyCreated |
    When I send the request to create the Inbound Correspondence Save Event
    And Wait for 5 seconds
    Then I should not see there is a link between Task Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated" and total of 1 Links.
    And Close the soft assertions

  @CP-22634 @CP-22634-08 @API-ECMS @burak
  Scenario: Verify Inbound Document is not Linked to the Task when Task Status is Closed
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 12797             |
    Then I send the request for the External task endpoint
    Then I update task status to "Closed"
    Then I verify task status successfully updated to "Closed"
    And I will initiate and post autolinkMap API with following values for "Current" project ID if there is no exact match
      | inboundCorrespondenceTypes | maersk Case + Consumer |
      | serviceRequestTypeIDs      | 12797                   |
      | correspondenceMetadataName | Case ID                 |
      | serviceRequestFieldName    | Case ID                 |
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE    | PROCESSEDDOCUMENT |
      | CHANNEL | Mail              |
      | CaseID  | PreviouslyCreated |
    When I send the request to create the Inbound Correspondence Save Event
    And Wait for 5 seconds
    Then I should not see there is a link between Task Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated" and total of 2 Links.
    And Close the soft assertions

  @CP-22634 @CP-22634-09 @API-ECMS @burak
  Scenario: Verify Inbound Document is Not Linked to the Task when one field is not populated for SR
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | taskTypeId | 12797             |
    Then I send the request for the External task endpoint
    And I will initiate and post autolinkMap API with following values for "Current" project ID if there is no exact match
      | inboundCorrespondenceTypes | maersk Case + Consumers |
      | serviceRequestTypeIDs      | 12797                    |
      | correspondenceMetadataName | ConsumerID,CaseID        |
      | serviceRequestFieldName    | Consumer ID,Case ID      |
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumers"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE       | PROCESSEDDOCUMENT |
      | CHANNEL    | Mail              |
      | CaseID     | PreviouslyCreated |
      | ConsumerID | PreviouslyCreated |
    When I send the request to create the Inbound Correspondence Save Event
    And Wait for 5 seconds
    Then I should not see there is a link between Task Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated" and total of 1 Links.
    And Close the soft assertions

  @CP-22634 @CP-22634-10 @API-ECMS @burak
  Scenario: Verify Inbound Document is Not Linked to the Task when one field is not populated for Inbound Document
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 13359             |
    Then I send the request for the External task endpoint
    And I will initiate and post autolinkMap API with following values for "Current" project ID if there is no exact match
      | inboundCorrespondenceTypes | maersk Case + Consumers |
      | serviceRequestTypeIDs      | 13359                    |
      | correspondenceMetadataName | ConsumerID,CaseID        |
      | serviceRequestFieldName    | Consumer ID,Case ID      |
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumers"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE       | PROCESSEDDOCUMENT |
      | CHANNEL    | Mail              |
      | ConsumerID | PreviouslyCreated |
    When I send the request to create the Inbound Correspondence Save Event
    And Wait for 5 seconds
    Then I should not see there is a link between Task Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated" and total of 2 Links.
    And Close the soft assertions

  @CP-22634 @CP-22634-11 @API-ECMS @burak
  Scenario: Verify Inbound Document is Not Linked When Matching AutoLink Mapping is in different project
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 13241             |
    Then I send the request for the External task endpoint
    And I will initiate and post autolinkMap API with following values for "91" project ID if there is no exact match
      | inboundCorrespondenceTypes | maersk Case + Consumers |
      | serviceRequestTypeIDs      | 13241                    |
      | correspondenceMetadataName | ConsumerID,CaseID        |
      | serviceRequestFieldName    | ConsumerID,CaseID        |
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumers"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE       | PROCESSEDDOCUMENT |
      | CHANNEL    | Mail              |
      | ConsumerID | PreviouslyCreated |
      | CaseID     | previouslyCreated |
    When I send the request to create the Inbound Correspondence Save Event
    And Wait for 5 seconds
    Then I should not see there is a link between Task Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated" and total of 3 Links.
    And Close the soft assertions

  @CP-22634 @CP-22634-12 @API-ECMS @burak
  Scenario: Verify Inbound Document is not linked to the SR when there are duplicate SR's
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 12797             |
    Then I send the request for the External task endpoint
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 12797             |
    Then I send the request for the External task endpoint
    And I will initiate and post autolinkMap API with following values for "Current" project ID if there is no exact match
      | inboundCorrespondenceTypes | maersk Case + Consumer |
      | serviceRequestTypeIDs      | 12797                   |
      | correspondenceMetadataName | ConsumerID,CaseID       |
      | serviceRequestFieldName    | ConsumerID,CaseID       |
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE       | PROCESSEDDOCUMENT |
      | CHANNEL    | Mail              |
      | CaseID     | PreviouslyCreated |
      | ConsumerID | PreviouslyCreated |
    When I send the request to create the Inbound Correspondence Save Event
    And Wait for 5 seconds
    Then I should not see there is a link between Task Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated" and total of 2 Links.
    And Close the soft assertions

  @CP-22634 @CP-22634-13 @API-ECMS @burak
  Scenario: Verify Inbound Document is not linked when There are more than one match through different configurations
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | taskTypeId | 15570             |
    Then I send the request for the External task endpoint
    When I have a request to create External task with the following values
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 12797             |
    Then I send the request for the External task endpoint
    And I will initiate and post autolinkMap API with following values for "Current" project ID if there is no exact match
      | inboundCorrespondenceTypes | maersk Case + Consumers |
      | serviceRequestTypeIDs      | 12797                    |
      | correspondenceMetadataName | ConsumerID               |
      | serviceRequestFieldName    | Consumer ID              |
    And I will initiate and post autolinkMap API with following values for "Current" project ID if there is no exact match
      | inboundCorrespondenceTypes | maersk Case + Consumers |
      | serviceRequestTypeIDs      | 15570                    |
      | correspondenceMetadataName | CaseID                   |
      | serviceRequestFieldName    | Case ID                  |
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumers"
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE       | PROCESSEDDOCUMENT |
      | CHANNEL    | Mail              |
      | CaseID     | PreviouslyCreated |
      | ConsumerID | PreviouslyCreated |
    When I send the request to create the Inbound Correspondence Save Event
    And Wait for 5 seconds
    Then I should not see there is a link between Task Id "previouslyCreated" and Inbound Correspondence Document "previouslyCreated" and total of 1 Links.
    And Close the soft assertions