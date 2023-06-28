@DCEBHraCahmiSurveySREvents
Feature: Validation of DC-EB HRA/CAHMI Survey SR Events

  @CP-39217 @CP-39217-02 @moldir @events-wf
  Scenario Outline: Validate TASK_SAVE_EVENT for DC-EB HRA/CAHMI Survey SR
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39217-02" with following payload
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
    And I run external createORupdate sr api call with data
      | taskTypeId      | 16402 |
      | createdBy       | 9056  |
      | externalLinks[0].externalRefType  | CASE    |
      | taskDetails[0].selectionFieldName | Channel |
      | taskDetails[0].selectionVarchar   | PHONE   |
    Then I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    And I store sr info on edit page
    When I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for SR has all the proper data
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | correlationId   |
      | TASK_SAVE_EVENT | TASK_SAVE_EVENT |

  @CP-39217 @CP-39217-03 @moldir @events-wf
  Scenario Outline: Validate LINK_EVENT for DC-EB HRA/CAHMI Survey SR
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39217-03" with following payload
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
    And I run external createORupdate sr api call with data
      | taskTypeId      | 16402 |
      | createdBy       | 9056  |
      | externalLinks[0].externalRefType  | CASE    |
      | taskDetails[0].selectionFieldName | Channel |
      | taskDetails[0].selectionVarchar   | WEB     |
    Then I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    And I store sr id on edit page
    When I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "SR" from "CUSTOM_PROVIDED,srToCase,caseToSR,taskToCase,caseToTask"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName  | correlationId |
      | LINK_EVENT | LINK_EVENT    |

  @CP-39157 @CP-39157-02 @CP-39160 @CP-39160-03 @CP-39160-04 @moldir @events-wf
  Scenario Outline: Verify HRA/CAHMI Survey SR is closed when OB call task disposition is Survey Captured/Enrollment Disregarded/No Eligible member found for Survey
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39157-02" with following payload
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
    And I run external createORupdate sr api call with data
      | taskTypeId      | 16402 |
      | createdBy       | 9056  |
      | externalLinks[0].externalRefType  | CASE    |
      | taskDetails[0].selectionFieldName | Channel |
      | taskDetails[0].selectionVarchar   | WEB     |
    Then I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And Wait for 250 seconds
    And I will search with srId
    And In search result click on task id to navigate to view page
    And I click on id of "Survey Outbound Call" in Links section of "SR" page
    And I store task id on edit page
    And I click on edit button on view task page OR view SR page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | <disposition>        |
    And I click on save button on task edit page
    And I click on id of "HRA/CAHMI Survey SR" in Links section of "Task" page
    And I store sr id on edit page
    And Wait for 2 seconds
    Then I verify the sr status is updated to close and disposition is set to "<dispositionSR>"
    Then I verify "Survey Outbound Call" task status is updated to "Complete" on the link section
    When I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate Event get api for trace id "Task_update"
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for TASK has all the proper data
    And I will check "TASK_UPDATE_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "TASK_UPDATE_EVENT" publish to DPBI
    And Close the soft assertions
    Examples:
      | disposition                              | dispositionSR      |
      | Survey Captured For All Eligible Members | Survey Captured    |
#      | Enrollment Disregarded                   | Ineligible Member(s) |
#      | No Eligible Member Found For Survey      | Ineligible Member(s) |


  @CP-42489 @CP-42489-02 @crm-regression @events-wf @ruslan
  Scenario Outline: DC EB: Verify LINK_EVENT for HRA/CAHMI Survey SR
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | DCEB Enrollment Form   |
      | Language     | SPANISH                |
      | Channel      | MAIL                   |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | CaseID       | 3753                   |
      | ConsumerID   | 5868                   |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | RECEIVED                 |
      | Channel        | MAIL                     |
      | documentType   | DCEB Enrollment Form     |
      | documentHandle | InboundDocumentIdDigital |
      | CaseID         | 3753                     |
      | ConsumerID     | 5868                     |
    When I send the request to create the Inbound Correspondence Save Event
    Then I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | Survey Not Captured  |
    And I click on save button on task edit page
    And I click on CaseId on Links section
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I store static case id "3753"
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate Event get api for trace id "<eventName>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "SR" from "ECMS Side,srToCase,caseToSR"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName  | projectName |
      | LINK_EVENT | DC-EB       |

  @CP-42489 @CP-42489-03 @crm-regression @events-wf @ruslan
  Scenario Outline: DC EB: Verify Task Save Event for HRA/CAHMI Survey SR
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | DCEB Enrollment Form   |
      | Language     | SPANISH                |
      | Channel      | MAIL                   |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | CaseID       | 3753                   |
      | ConsumerID   | 5868                   |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | RECEIVED                 |
      | Channel        | MAIL                     |
      | documentType   | DCEB Enrollment Form     |
      | documentHandle | InboundDocumentIdDigital |
      | CaseID         | 3753                     |
      | ConsumerID     | 5868                     |
    When I send the request to create the Inbound Correspondence Save Event
    Then I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    When I update status to "Complete" on task
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry                        |
      | disposition   | Enrollment Captured And Survey Not Captured |
    And I click on save button on task edit page
    And I click on CaseId on Links section
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I store sr id on edit page
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate Event get api for trace id "<eventName>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for SR has all the proper data
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | projectName |
      | TASK_SAVE_EVENT | DC-EB       |