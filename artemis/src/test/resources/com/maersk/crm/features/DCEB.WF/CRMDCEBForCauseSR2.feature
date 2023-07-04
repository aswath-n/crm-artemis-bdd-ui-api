Feature: DC-EB For Cause SR Two

  @CP-41602 @CP-41602-01 @ozgen @crm-regression @ui-wf-dceb
  Scenario: Verification of Cancel SR and Update All Checkboxes and their functionality in Create For Cause SR page
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I navigate to "For Cause SR" service request page
    Then I verify that update all and cancel sr checkboxes are displayed
    And Wait for 3 seconds
    And I will provide following information before creating task
      | requestReason              | Dissatisfied          |
      | memberNameForRequestReason | First consumer        |
      | notesForRequestReason      | checking note         |
      | requestedPlan              | MEDSTAR FAMILY CHOICE |
      | requestedEffectiveDate     | today                 |
      | arcIndicator               | Yes                   |
      | districtDisposition        | Pending               |
    Then I click on "Add Request Reason" button on Create Task
    Then I click on "Update All" button on Create Task
    And I verify both request reason are getting populated
    And I will provide following information before creating task
      | requestReason                    | To Maintain Family Unity |
      | memberNameForRequestReason       | UpdatedName              |
      | notesForRequestReason            | updated note             |
      | requestedPlan                    | AMERIHEALTH CARITAS DC   |
      | requestedEffectiveDate           | today                    |
      | arcIndicator                     | No                       |
      | districtDisposition              | Approved                 |
      | notesForRequestReasonSecond      | second member notes      |
      | memberNameForRequestReasonSecond | second consumer          |
    Then I verify that second member request reasons are not getting changed
    Then I click on "Update All" button on Create Task
    Then I verify that second member request reasons are not getting changed
#not deployed to qarls due to CP-43040
  #@CP-39272 @CP-39272-01 @CP-39272-02 @moldir @crm-regression @ui-wf-dceb
  Scenario Outline: Create FC Member notification Task
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "CP-39272-01" with following payload
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
      | priority            | 3                      |
      | preferredPhone      | 5435435435             |
      | missingInfoRequired | No                     |
      | requestReason       | Dissatisfied           |
      | requestedPlan       | AMERIHEALTH CARITAS DC |
      | districtDisposition ||
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer by consumerId
    And I click on Search option
    And I will expand the consumer record
    When I click on Link Case button
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "","","For Cause SR","Open","","3","","","","","","true","","","","","Service AccountOne","today",""
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will update the following information in edit task page
      | districtDisposition | <districtDisposition> |
      | reasonForEdit       | Corrected Data Entry  |
    And I click on save button on task edit page
    Then I verify "For Cause SR" contains : "FC Review,FC Member Notification" on "SR" link section
    When I click id of "FC Member Notification" in Links section
    Then I verify "FC Member Notification" link section has all the business object linked : "Service Request,Case"
    And Close the soft assertions
    Examples:
      | districtDisposition |
      | Approved            |
      | Denied              |

  @CP-42592 @CP-42592-01 @CP-39096-04 @moldir @crm-regression @ui-wf-dceb
  Scenario Outline: Creating and linking the FC SR to the Case only
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
      | priority            | 3                      |
      | callerName          | Ben Franklin           |
      | preferredPhone      | 5215215215             |
      | missingInfoRequired | No                     |
      | requestReason       | Dissatisfied           |
      | memberNameForRequestReason| Ben Franklin     |
      | requestedPlan       | AMERIHEALTH CARITAS DC |
      | districtDisposition ||
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
    Then I verify task is linked with Case ID
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType       | status | statusDate | priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy          | createdOn | contactReason |
      ||          | For Cause SR | Open   || 3        ||            ||                || true          ||            ||          | Service AccountOne | today     ||

  @CP-41154 @CP-41154-01 @CP-41154-02 @moldir @crm-regression @ui-wf-dceb
  Scenario Outline: Create FC Cancellation Request Confirmation task
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "CP-39272-01" with following payload
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
      | priority            | 1                      |
      | callerName          | Tommy Jerry            |
      | preferredPhone      | 5435435435             |
      | missingInfoRequired | No                     |
      | requestReason       | Dissatisfied           |
      | memberNameForRequestReason| Tommy Jerry      |
      | requestedPlan       | AMERIHEALTH CARITAS DC |
      | districtDisposition ||
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for newly created consumer by firstname and lastname
    And I click on Search option
    And I will expand the consumer record
    When I click on Link Case button
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "","","For Cause SR","Open","","1","","","","","","true","","","","","Service AccountOne","today",""
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will update the following information in edit task page
      | districtDisposition | <districtDisposition> |
      | reasonForEdit       | Corrected Data Entry  |
    And I click on save button on task edit page
    Then I verify "For Cause SR" contains : "FC Review,FC Cancellation Request Confirmation" on "SR" link section
    And I click id of "FC Review" in Links section
    Then I verify task's status is Cancelled
    And I click id of "For Cause SR" in Links section
    When I click id of "FC Cancellation Request Confirmation" in Links section
    Then I verify "FC Cancellation Request Confirmation" link section has all the business object linked : "Service Request,Case"
    And Close the soft assertions
    Examples:
      | districtDisposition           |
      | Cancellation Request Approved |
      | Cancellation Request Denied   |
