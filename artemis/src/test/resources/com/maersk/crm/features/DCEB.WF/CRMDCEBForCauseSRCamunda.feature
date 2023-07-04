Feature: DC-EB For Cause SR Camunda

  @CP-39088 @CP-39088-01 @CP-39355 @CP-39355-01 @sr-camunda @ruslan
  Scenario Outline: Create FC Missing Information Task and link to FC Cause SR task along with case & consumer
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "42592-01" with following payload
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
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I navigate to "For Cause SR" service request page
    And I will provide following information before creating task
      | priority            | 2                      |
      | callerName          | CAMUNDSKIY             |
      | memberNameFc        | Vasiliy                |
      | preferredPhone      | 5215215215             |
      | missingInfoRequired | Yes                    |
      | requestReason       | Dissatisfied           |
      | requestedPlan       | AMERIHEALTH CARITAS DC |
    And I click on save button in create service request page
    Then I verify 'Case is required' error message displayed for FC SR
    Then I close the error popup
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for newly created consumer by firstname and lastname
    And I click on Search option
    And I will expand the consumer record
    And I see Link Record Case button get displayed
    And I verify 'Link to Case Only' checkbox disabled
    When I click on Link Case button
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    Then I verify "For Cause SR" link section has all the business object linked : "Task,Case"
    When I click id of "FC Missing Information" in Links section
    Then I verify "FC Missing Information" link section has all the business object linked : "Service Request,Case"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | Consumer Not Reached |
    And I click on save button on task edit page
    And Wait for 250 seconds
    And I click on id of "For Cause SR" in Links section of "Task" page
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | Consumer Not Reached |
    And I click on save button on task edit page
    And Wait for 250 seconds
    And I click on id of "For Cause SR" in Links section of "Task" page
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | Consumer Not Reached |
    And I click on save button on task edit page
    And I click on id of "For Cause SR" in Links section of "Task" page
    And I store sr id on edit page
    Then I verify link section contains 3 "FC Missing Information"
    When I will get the Authentication token for "DC-EB" in "CRM"
    Then I verify the sr status is updated to close and disposition is set to "MI Not Received"
    And I initiated bpm process get api by service request id for process name "DCEB_For_Cause_SR"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType       | status | statusDate | priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy          | createdOn | contactReason |
      |        |          | For Cause SR | Open   |            | 2        |         |            |        |                |            | true          |            |            |        |          | Service AccountOne | today     |               |

  @CP-38933 @CP-38933-01 @CP-39465 @CP-39465-01 @CP-38937 @CP-38937-01 @CP-42578 @CP-42578-01 @CP-42963 @sr-camunda @ruslan
  Scenario Outline: Verify Date State Notified is updated to system date when FC Review task is completed with disposition Sent To District For Approval
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "Barbara" and Last Name as "Walters"
    And I link the contact to an existing Case or Consumer Profile
    And I store external case id from Demographic Info Page
    And I store external consumer id from Demographic Info Page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "For Cause SR" service request page
    And I will provide following information before creating task
      | callerName          | CAMUNDSKIY             |
      | memberNameFc        | Vasiliy                |
      | preferredPhone      | 5215215215             |
      | missingInfoRequired | No                     |
      | requestReason       | Dissatisfied           |
      | requestedPlan       | AMERIHEALTH CARITAS DC |
      | districtDisposition | <districtDisposition>  |
    And I click on save button in create service request page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    When I click id of "FC Review" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                      |
      | reasonForEdit | Corrected Data Entry          |
      | disposition   | Sent To District For Approval |
    And I click on save button on task edit page
    And Wait for 250 seconds
    And I click on id of "For Cause SR" in Links section of "Task" page
    Then I verify state notified date is updated to current date on the view page
    When I click id of "FC District Outreach" in Links section
    Then I verify "FC District Outreach" link section has all the business object linked : "Service Request,Case"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                      |
      | reasonForEdit | Corrected Data Entry          |
      | disposition   | District Reached Successfully |
    And I click on save button on task edit page
    And Wait for 250 seconds
    And I click on id of "For Cause SR" in Links section of "Task" page
    When I click id of "FC District 2nd Outreach" in Links section
    Then I verify "FC District 2nd Outreach" link section has all the business object linked : "Service Request,Case"
    And I click on id of "For Cause SR" in Links section of "Task" page
    And I click on edit service request button
    And I will update the following information in edit task page
      | cancelSR      | true                 |
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    Then I verify "For Cause SR" contains : "FC Review,FC District Outreach,FC District 2nd Outreach,District Outreach - Cancel SR" on "SR" link section
    When I click id of "FC District 2nd Outreach" in Links section
    Then I verify the "STATUS" updated to "Cancelled"
    And Close the soft assertions
    Examples:
      | districtDisposition |
      ||
      | Sent To MCO         |

  @CP-39466 @CP-39466-01 @sr-camunda @ruslan
  Scenario: Close For Cause SR after the District Outreach task is completed
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "Alexandrea" and Last Name as "Harber"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "For Cause SR" service request page
    And I will provide following information before creating task
      | callerName          | Kaspiyskiy             |
      | memberNameFc        | Vasya                  |
      | preferredPhone      | 5215215215             |
      | missingInfoRequired | No                     |
      | requestReason       | Dissatisfied           |
      | requestedPlan       | AMERIHEALTH CARITAS DC |
      | districtDisposition ||
    And I click on save button in create service request page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    When I click id of "FC Review" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                      |
      | reasonForEdit | Corrected Data Entry          |
      | disposition   | Sent To District For Approval |
    And I click on save button on task edit page
    And Wait for 250 seconds
    And I click on id of "For Cause SR" in Links section of "Task" page
    When I click id of "FC District Outreach" in Links section
    Then I verify "FC District Outreach" link section has all the business object linked : "Service Request,Case"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                        |
      | reasonForEdit | Corrected Data Entry            |
      | disposition   | District Reached Unsuccessfully |
    And I click on save button on task edit page
    And I click on id of "For Cause SR" in Links section of "Task" page
    And I store sr id on edit page
    When I will get the Authentication token for "DC-EB" in "CRM"
    Then I verify the sr status is updated to close and disposition is set to "Pending"
    And I initiated bpm process get api by service request id for process name "DCEB_For_Cause_SR"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions