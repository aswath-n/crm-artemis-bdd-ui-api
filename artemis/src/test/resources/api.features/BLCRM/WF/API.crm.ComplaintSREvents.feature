@ComplaintSREvents
Feature: Complaint SR Events For CoverVA

  @CP-18749 @CP-18749-03 @kamil @events-wf
  Scenario Outline: Validate in TASK_SAVE_EVENT for Review Complaint Task for Complaint SR
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Complaint SR" service request page
    And I will provide following information before creating task
      | taskInfo | Test CP-18749 |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_SAVE_EVENT for SR has all the proper data
    And I verify task details information for a Task which is created when SR got created
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | correlationId |
      | TASK_SAVE_EVENT | Open          |

  @CP-27696 @CP-27696-08 @CP-22677 @CP-22677-08 @ruslan @events-wf
  Scenario Outline: Validate LINK_EVENT & UNLINK_EVENT for Manually Link Service Request to Service Request for IN-EB
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout | maersk          |
      | incidentDate   | today            |
      | memberName     | Ruslan           |
      | preferredPhone | 1234567890       |
      | complaintType  | Customer Service |
      | priority       | 5                |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    When I navigate to "Just Cause" service request page
    And I will provide following information before creating task
      | rid                 | 521521521512         |
      | contactName         | Alewa                |
      | contactPhone        | 5215215215           |
      | currentPlan         | Anthem               |
      | desiredPlanJC       | Anthem               |
      | programRequired     | Healthy Indiana Plan |
      | missingInfoRequired | No                   |
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","Customer Service Complaint","Open","<statusDate>","5","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","true","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","today","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I click on cancel button on task search page
    And I click on edit service request button
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And I click task id to get the results in descending order in Task Search
    And I scroll down the page
    And I click on Link button in task search
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    And I store sr id on edit page
    And I initiate Event get api for trace id "manually_link_sr_to_sr"
    And I will run the Event GET API and get the payload
    And I verify "LINK_EVENT" generated for "Customer Service Complaint" task : "SERVICE_REQUEST TO SERVICE_REQUEST,SERVICE_REQUEST TO SERVICE_REQUEST"
    And I will check "LINK_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "LINK_EVENT" publish to DPBI
    And I click on edit service request button
    And I click on save button on task edit page
    And I unlink "Just Cause" from link section
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    And I initiate Event get api for trace id "manually_unlink_sr_from_sr"
    And I will run the Event GET API and get the payload
    And I verify "UNLINK_EVENT" generated for "Customer Service Complaint" task : "SERVICE_REQUEST TO SERVICE_REQUEST,SERVICE_REQUEST TO SERVICE_REQUEST"
    And I will check "UNLINK_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "UNLINK_EVENT" publish to DPBI
    Examples:
      | taskId | taskType | srType     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      |        |          | Just Cause |        |            |          |         |            |        |                |            | true          |            |            |        |          |           | today     |               |


  @CP-27696 @CP-27696-12 @CP-22677 @CP-22677-08 @ruslan @events-wf
  Scenario Outline: Validate LINK_EVENT & UNLINK_EVENT for Manually Link Service Request to Service Request for IN-EB
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout | maersk          |
      | incidentDate   | today            |
      | memberName     | Ruslan           |
      | preferredPhone | 1234567890       |
      | complaintType  | Customer Service |
      | priority       | 5                |
    And I click on save button in create service request page
    When I navigate to "Just Cause" service request page
    And I will provide following information before creating task
      | rid                 | 521521521512         |
      | contactName         | Alewa                |
      | contactPhone        | 5215215215           |
      | currentPlan         | Anthem               |
      | desiredPlanJC       | Anthem               |
      | programRequired     | Healthy Indiana Plan |
      | missingInfoRequired | No                   |
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","Customer Service Complaint","<status>","<statusDate>","5","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","true","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","today","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I initiated search records API
    And I validate serviceRequestInd value for "SR"
    And I click on edit service request button
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I click on cancel button on task search page
    And I click on edit service request button
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And I click task id to get the results in descending order in Task Search
    And I click on Link button in task search
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    And I store sr id on edit page
    And I initiate Event get api for trace id "manually_link_sr_to_sr"
    And I will run the Event GET API and get the payload
    And I verify "LINK_EVENT" generated for "Just Cause" task : "SERVICE_REQUEST TO SERVICE_REQUEST,SERVICE_REQUEST TO SERVICE_REQUEST"
    And I will check "LINK_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "LINK_EVENT" publish to DPBI
    And I click on edit service request button
    And I click on save button on task edit page
    And I unlink "Just Cause" from link section
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    And I initiate Event get api for trace id "manually_unlink_sr_from_sr"
    And I will run the Event GET API and get the payload
    And I verify "UNLINK_EVENT" generated for "General Service Request" task : "SERVICE_REQUEST TO SERVICE_REQUEST,SERVICE_REQUEST TO SERVICE_REQUEST"
    And I will check "UNLINK_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "UNLINK_EVENT" publish to DPBI
    Examples:
      | taskId | taskType | srType     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      |        |          | Just Cause |        |            |          |         |            |        |                |            | true          |            |            |        |          |           | today     |               |