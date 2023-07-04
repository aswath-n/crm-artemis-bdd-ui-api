Feature: DC-EB Auto Assignment SR

  @CP-37878 @CP-37878-01 @CP-37889 @CP-37889-01 @CP-39361 @CP-39361-01 @CP-37891 @CP-37891-01 @moldir @crm-regression @ui-wf-dceb
  Scenario: Verify AA SR is created from API for DC-EB
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "37878-01" with following payload
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
      | taskTypeId      | 16277 |
      | createdBy       | 9056  |
      | taskTriggerDate ||
      | CONSUMER        ||
      | CASE            ||
    Then I send the request for the External task endpoint
    Then I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    Then I verify "Auto Assignment SR" link section has all the business object linked : "Consumer,Case,Outbound Correspondence,Outbound Correspondence"
    And I initiated bpm process get api by service request id for process name "DCEB_Auto_Assignment_SR"
    And I run bpm process get api by sr id
    And I will check response of service request bpm process
    And Close the soft assertions

  @CP-37886 @CP-37886-03 @CP-37887 @CP-37887-03 @CP-37888 @CP-37888-03 @moldir @crm-regression @ui-wf-dceb
  Scenario Outline: Verify AA SR is created and linked Consumer to the AA SR
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "37886-03" with following payload
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
      | taskTriggerDate ||
      | taskInfo        | null::|
      | taskDetails[0].selectionFieldName| Disposition |
      | externalLinks[0].externalRefType | CONSUMER |
      | externalLinks[1].externalRefType | CASE     |
    And Wait for 2 seconds
    And I run external createORupdate sr api call with data
      | taskTypeId      | 16277 |
      | createdBy       | 9056  |
      | taskId          ||
      | taskInfo        | null::|
      | taskDetails[0].selectionFieldName | Disposition   |
      | taskDetails[0].selectionVarchar   | <disposition> |
    Then I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    Then I verify the sr status is updated to close and disposition is set to "<dispositionSR>"
    When I will get the Authentication token for "DC-EB" in "CRM"
    And I initiated bpm process get api by service request id for process name "DCEB_Auto_Assignment_SR"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | disposition               |dispositionSR|
      | SELECTION_MADE            |Selection Made|
      | SELECTION_ACCEPTED        |Selection Accepted|
      | LOSS_OF_ELIGIBILITY       |Loss of Eligibility|
