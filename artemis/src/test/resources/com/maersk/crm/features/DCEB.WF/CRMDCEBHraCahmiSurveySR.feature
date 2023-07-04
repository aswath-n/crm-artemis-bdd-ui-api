Feature: Validation of HRA/CAHMI Survey SR

  @CP-39217 @CP-39217-01 @moldir @crm-regression @ui-wf-dceb
  Scenario: Verify HRA/CAHMI Survey SR is created from API for DC-EB
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39217-01" with following payload
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
    And I initiated bpm process get api by service request id for process name "DCEB_HRA_OR_CAHMI_SR"
    And I run bpm process get api by sr id
    And I will check response of service request bpm process
    And Close the soft assertions

  @CP-39157 @CP-39157-01 @CP-39160 @CP-39160-01 @CP-39160-02 @moldir @crm-regression @ui-wf-dceb
  Scenario Outline: Verify HRA/CAHMI Survey SR is closed when OB call task disposition is Survey Captured/Enrollment Disregarded/No Eligible member found for Survey
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39157-01" with following payload
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
    Then I verify "HRA/CAHMI Survey SR" link section has all the business object linked : "Task,Case"
    And I click on id of "Survey Outbound Call" in Links section of "SR" page
    And I store task id on edit page
    Then I verify "Survey Outbound Call" link section has all the business object linked : "Service Request,Case"
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
    And I initiated bpm process get api by service request id for process name "DCEB_HRA_OR_CAHMI_SR"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | disposition                              | dispositionSR      |
      #| Survey Captured For All Eligible Members | Survey Captured    |
      | Enrollment Disregarded                   | Ineligible Member(s) |
      #| No Eligible member Found For Survey      | Ineligible Member(s) |

  @CP-39158 @CP-39158-01 @moldir @crm-regression @ui-wf-dceb
  Scenario: Verify HRA/CAHMI Survey SR is closed when enrollment channel is Phone with 2 Survey Outbound Call
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39158-01" with following payload
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
    And Wait for 250 seconds
    And I will search with srId
    And In search result click on task id to navigate to view page
    And I click on id of "Survey Outbound Call" in Links section of "SR" page
    And I store task id on edit page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                |
      | reasonForEdit | Entered Additional Info |
      | disposition   | Consumer Not Reached    |
    And I click on save button on task edit page
    And Wait for 250 seconds
    And I click on id of "HRA/CAHMI Survey SR" in Links section of "Task" page
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                |
      | reasonForEdit | Entered Additional Info |
      | disposition   | Consumer Not Reached    |
    And I click on save button on task edit page
    And Wait for 250 seconds
    And I click on id of "HRA/CAHMI Survey SR" in Links section of "Task" page
    And I store sr id on edit page
    Then I verify the sr status is updated to close and disposition is set to "Survey Not Captured"
    When I will get the Authentication token for "DC-EB" in "CRM"
    And I initiated bpm process get api by service request id for process name "DCEB_HRA_OR_CAHMI_SR"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-39158 @CP-39158-02 @moldir @crm-regression @ui-wf-dceb
  Scenario: Verify HRA/CAHMI Survey SR is closed when when enrollment channel is NOT Phone with 3 Survey Outbound Call
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39158-02" with following payload
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
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                               |
      | reasonForEdit | Entered Additional Info                |
      | disposition   | Consumer Reached - Survey Not Captured |
    And I click on save button on task edit page
    And Wait for 250 seconds
    And I click on id of "HRA/CAHMI Survey SR" in Links section of "Task" page
    And Wait for 2 seconds
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                               |
      | reasonForEdit | Entered Additional Info                |
      | disposition   | Consumer Reached - Survey Not Captured |
    And I click on save button on task edit page
    And Wait for 250 seconds
    And I click on id of "HRA/CAHMI Survey SR" in Links section of "Task" page
    And Wait for 2 seconds
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                               |
      | reasonForEdit | Entered Additional Info                |
      | disposition   | Consumer Reached - Survey Not Captured |
    And I click on save button on task edit page
    And I click on id of "HRA/CAHMI Survey SR" in Links section of "Task" page
    And I store sr id on edit page
    And Wait for 2 seconds
    Then I verify the sr status is updated to close and disposition is set to "Survey Not Captured"
    When I will get the Authentication token for "DC-EB" in "CRM"
    And I initiated bpm process get api by service request id for process name "DCEB_HRA_OR_CAHMI_SR"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-42489 @CP-42489-01 @crm-regression @ui-wf-dceb @ruslan
  Scenario: DC EB: Trigger Survey SR when disposition Enrollment Captured And Survey Not Captured
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | DCEB Enrollment Form   |
      | Language     | SPANISH                |
      | Channel      | MAIL                  |
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
      | status        | Complete                                    |
      | reasonForEdit | Corrected Data Entry                        |
      | disposition   | Enrollment Captured And Survey Not Captured |
    And I click on save button on task edit page
    And I click on CaseId on Links section
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I store sr id on edit page
    Then I verify "HRA/CAHMI Survey SR" link section has all the business object linked : "Case"
    And Close the soft assertions

  @CP-48029 @CP-48029-01 @CP-42489 @CP-42489-01-1 @crm-regression @ui-wf-dceb @ruslan
  Scenario: DC EB: Trigger Survey SR when disposition Enrollment Survey Not Captured
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | DCEB Enrollment Form   |
      | Language     | SPANISH                |
      | Channel      | MAIL                  |
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
    Then I verify the "INBOUND CORRESPONDENCE TYPE" updated to "DCEB Enrollment Form"
    And I click on edit button on view task page
    And I verify "INBOUND CORRESPONDENCE TYPE" field is disable and not empty
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | Survey Not Captured  |
    And I click on save button on task edit page
    And I click on CaseId on Links section
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I store sr id on edit page
    Then I verify "HRA/CAHMI Survey SR" link section has all the business object linked : "Case"
    And Close the soft assertions