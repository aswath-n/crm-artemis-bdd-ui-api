@DCEBAutoAssignmentSREvents
Feature: Validation of DC-EB Auto Assignment SR Event

  @CP-37878 @CP-37878-02 @moldir @events-wf
  Scenario Outline: Validate TASK_SAVE_EVENT for DC-EB AA SR
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "37878-02" with following payload
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
    When I have a request to create External task with the following values
      | correlationId   |[blank]|
      | taskTypeId      | 16277 |
      | createdBy       | 9056  |
      | taskTriggerDate |[blank]|
      | CONSUMER        |[blank]|
      | CASE            |[blank]|
    Then I send the request for the External task endpoint
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
      | eventName       | correlationId |
      | TASK_SAVE_EVENT | apiClassUtilTraceId |

  @CP-37878 @CP-37878-03 @moldir @events-wf
  Scenario Outline: Validate LINK_EVENT for DC-EB AA SR
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "37878-03" with following payload
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
    When I have a request to create External task with the following values
      | correlationId   |[blank]|
      | taskTypeId      | 16277 |
      | createdBy       | 9056  |
      | taskTriggerDate |[blank]|
      | CONSUMER        |[blank]|
      | CASE            |[blank]|
    Then I send the request for the External task endpoint
    Then I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    When I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "SR" from "CUSTOM_PROVIDED,srToConsumer,consumerToSR,caseToSR,srToCase"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName  | correlationId       |
      | LINK_EVENT | apiClassUtilTraceId |

  @CP-37886 @CP-37886-01 @CP-37886-02 @CP-37887 @CP-37887-01 @CP-37887-02 @CP-37888 @CP-37888-01 @CP-37888-02 @moldir @events-wf
  Scenario Outline: Validate TASK_UPDATE_EVENT for DC-EB Close AA SR
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "37886-01" with following payload
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
      | taskTypeId      | 16277 |
      | createdBy       | 9056  |
      | taskTriggerDate |[blank]|
      | taskInfo        | null::|
      | taskDetails[0].selectionFieldName| Disposition |
      | externalLinks[0].externalRefType | CONSUMER |
      | externalLinks[1].externalRefType | CASE     |
    And Wait for 3 seconds
    And I run external createORupdate sr api call with data
      | taskTypeId      | 16277 |
      | createdBy       | 9056  |
      | taskId          |[blank]|
      | taskInfo        | null::|
      | taskDetails[0].selectionFieldName | Disposition   |
      | taskDetails[0].selectionVarchar   | <disposition> |
    Then I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    When I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for SR has all the proper data
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | disposition                    | eventName         | correlationId     |
      | Selection Made                 | TASK_UPDATE_EVENT | TASK_UPDATE_EVENT |
#      | Selection Accepted             | TASK_UPDATE_EVENT | TASK_UPDATE_EVENT |
#      | Cancelled - Loss of Eligibility| TASK_UPDATE_EVENT | TASK_UPDATE_EVENT |
