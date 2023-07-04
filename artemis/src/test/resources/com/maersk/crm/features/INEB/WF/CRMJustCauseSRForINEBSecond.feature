Feature: Validate of JustCause SR Second

  @CP-28060 @CP-28060-01 @crm-regression @ui-wf-ineb @ruslan
  Scenario Outline: IN-EB: Automatically cancel systematically linked tasks if SR is updated to closed status
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Just Cause Resolution" task page
    And I will provide following information before creating task
      | taskInfo | Test CP-22676     |
      | assignee | Service TesterTwo |
    And I click on save button in create task page
    When I navigate to "Just Cause" service request page
    And I will provide following information before creating task
      | rid                   | 521521521512                                 |
      | contactName           | Alewa                                        |
      | contactPhone          | 5215215215                                   |
      | currentPlan           | Anthem                                       |
      | desiredPlanJC         | Anthem                                       |
      | programRequired       | Healthy Indiana Plan                         |
      | missingInfoRequired   | Yes                                          |
      | reasonExplanation     | FSSA Took Corrective Action Against the Plan |
      | explanation           | Limited Access to a Primary Care Clinic      |
      | decision              | Disagree                                     |
      | dateFollowupEmailSent | today                                        |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    And I click on id of "Just Cause" in Links section of "TASK" page
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
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
      | status        | Closed               |
      | disposition   | Invalid              |
    And I click on save button on task edit page
    Then I verify "Just Cause Request" task status is updated to "Cancelled" on the link section
    And I verify "Just Cause Resolution" task status is updated to "Created" on the link section
    Examples:
      | taskId | taskType              | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee          | createdBy | createdOn |contactReason|
      |        | Just Cause Resolution |        |        |            |          |         |            |        |                |            | true          |            |            |        | Service TesterTwo |           | today     |             |


  @CP-29956 @CP-29956-01 @CP-31439 @CP-31439-01 @crm-regression @ui-wf-ineb @ruslan @priyal
  Scenario Outline: IN-EB: Add and verify required and optional fields for Multiple Additional Reason + Explanation Fields on Just Cause SR on Create Page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Just Cause" service request page
    And I will provide following information before creating task
      | reasonExplanation     | FSSA Took Corrective Action Against the Plan |
      | rid                   | 521521521512                                 |
      | contactName           | Alewa                                        |
      | contactPhone          | 5215215215                                   |
      | currentPlan           | Anthem                                       |
      | desiredPlanJC         | Anthem                                       |
      | programRequired       | Healthy Indiana Plan                         |
      | missingInfoRequired   | Yes                                          |
      | decision              | Disagree                                     |
      | dateFollowupEmailSent | today                                        |
      | priority              | 5                                            |
    And I click on save button in create service request page
    Then I verify task mandatory fields error message "EXPLANATION"
    And I will provide following information before creating task
      | explanation | Limited Access to a Primary Care Clinic |
    When I click on Add Reason
    And I will provide following information before creating task
      | explanation | Action Against the Plan |
    And I click on save button in create service request page
    Then I verify error message for reason and explanation fields "REASON"
    And I will provide following information before creating task
      | reasonExplanation | Major Language or Cultural Barriers |
    When I click on Add Reason
    And I will provide following information before creating task
      | reasonExplanation | Receiving Poor Quality of Care |
    And I click on save button in create service request page
    Then I verify error message for reason and explanation fields "EXPLANATION"
    And I will provide following information before creating task
      | explanation | Poor Quality |
    And I remove 1 row of reason and explanation
    Then I verify 1 row of reason "Major Language or Cultural Barriers" and explanation "Action Against the Plan"
    Then I verify 2 row of reason "Receiving Poor Quality of Care" and explanation "Poor Quality"
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I initiated get task by task id "getSRIDFromUi"
    And I run get task by task id API
    And I verify response contains "Reason" for the SR
      | MAJOR_LANGUAGE_OR_CULTURAL_BIAS |
      | RECEIVING_POOR_QUALITY_OF_CARE  |
    And I verify response contains "Explanation" for the SR
      | Action Against the Plan |
      | Poor Quality            |
    And I click on edit service request button
    And I remove 1 row of reason and explanation
    And I will update the following information in edit task page
      | reasonForEdit     | Corrected Data Entry |
      | reasonExplanation |                      |
      | explanation       |                      |
    And I click on save button on task edit page
#    Then I verify success message for update service request
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      |        |          | Just Cause | Open   |            | 5        |         |            |        |                |            | true          |            |            |        |          | Service TesterTwo | today     |             |

  @CP-29956 @CP-29956-02 @CP-31439 @CP-31439-02 @crm-regression @ui-wf-ineb @ruslan @priyal
  Scenario Outline: IN-EB: Add and verify required and optional fields for Multiple Additional Reason + Explanation Fields on Just Cause SR on Edit Page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry                    |
      | explanation   | Limited Access to a Primary Care Clinic |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "REASON"
    And I scroll up the page
    And I will update the following information in edit task page
      | reasonExplanation | Plan (Contractor) Did Not Provide Covered Services |
    When I click on Add Reason
    And I will update the following information in edit task page
      | reasonExplanation | Member Needs Related Services that Must Be Performed at the Same Time But Not All Services Are Available in the Plan's Network |
    And I click on save button on task edit page
    Then I verify error message for reason and explanation fields "EXPLANATION"
    And I will update the following information in edit task page
      | explanation | Action Against the Plan |
    When I click on Add Reason
    And I will update the following information in edit task page
      | explanation | Poor Quality |
    And I click on save button on task edit page
    Then I verify error message for reason and explanation fields "REASON"
    And I scroll up the page
    And I will update the following information in edit task page
      | reasonExplanation | Plan Did Not Have Providers Experienced in Member's Healthcare Needs |
    And I remove 1 row of reason and explanation
    Then I verify 1 row of reason "Member Needs Related Services that Must Be Performed at the Same Time But Not All Services Are Available in the Plan's Network" and explanation "Action Against the Plan"
    Then I verify 2 row of reason "Plan Did Not Have Providers Experienced in Member's Healthcare Needs" and explanation "Poor Quality"
    And I will update the following information in edit task page
      | dateFollowupEmailSent  | today |
      | dateDecisionLetterSent | today |
      | dateSateNotified       | today |
    And I click on save button on task edit page
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I initiated get task by task id "getSRIDFromUi"
    And I run get task by task id API
    And I verify response contains "Reason" for the SR
      | MEMBER_NEEDS_RELATED_SVCS_BUT_NOT_ALL_SVCS_AVAILABLE     |
      | PLAN_DID_NOT_HAVE_PROVIDERS_EXPERIENCED_IN_MEMBERS_NEEDS |
    And I verify response contains "Explanation" for the SR
      | Action Against the Plan |
      | Poor Quality            |
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    When I click on Add Reason
    And I click on save button on task edit page
#    Then I verify success message for update service request
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn | contactReason |
      |        |          | Just Cause | Open   |            |          |         |            |        |                |            | true          |            |            |        |          | Service TesterTwo | today     |               |

  @CP-26866 @CP-26866-01 @crm-regression @ui-wf-ineb @ruslan
  Scenario: IN-EB: Create Send Just Cause Decision Letter Task if user marks SR Withdrawn and the task was not created yet
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    When I searched customer have First Name as "NANCY" and Last Name as "KIYAN"
    When I expand the record to navigate Case Consumer Record from manual view
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Just Cause" service request page
    And I will provide following information before creating task
      | rid                 | 521521521512         |
      | contactName         | Alewa                |
      | contactPhone        | 5215215215           |
      | currentPlan         | Anthem               |
      | desiredPlanJC       | Anthem               |
      | programRequired     | Healthy Indiana Plan |
      | missingInfoRequired | Yes                  |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit | Entered Additional Info |
      | finalDecision | Withdrawn               |
    And I click on save button on task edit page
    Then I verify "Just Cause" contains : "Just Cause Request,Just Cause Decision Letter" on "SR" link section

  @CP-24962 @CP-24962-04 @CP-36910 @CP-36910-02 @crm-regression @ui-wf-ineb @ruslan
  Scenario: Validate Just Cause SR is linked to Just Cause Resolution task and Comunda flow proceeded
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click case consumer search tab
    When I searched customer have First Name as "NANCY" and Last Name as "ELIAS"
    When I expand the record to navigate Case Consumer Record from manual view
    And I store external case id from Demographic Info Page
    And I store external consumer id from Demographic Info Page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Just Cause" service request page
    And I will provide following information before creating task
      | rid                 | 521521521512         |
      | contactName         | Alewa                |
      | contactPhone        | 5215215215           |
      | currentPlan         | Anthem               |
      | desiredPlanJC       | Anthem               |
      | programRequired     | Healthy Indiana Plan |
      | missingInfoRequired | No                   |
      | decision            | Agree                |
    And I click on save button in create service request page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I store sr id on edit page
    And I click on id of "Just Cause Request" in Links section of "SR" page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | No Action Taken      |
    And I click on save button on task edit page
    When I navigate to "Just Cause Resolution" task page
    And I click on save button on task edit page
    And I click on ConsumerId on Links section
    And I navigate to Task and service Request Page
    And I navigate to newly created task in Task & Service Request Tab
    And I click on first task id from TSR tab
    And I click on edit button on view task page
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I will search with srId
    And I click on Link button in task search
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    Then I verify "Just Cause Resolution" contains : "Just Cause" on "TASK" link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                              |
      | reasonForEdit | Corrected Data Entry                  |
      | actionTaken   | Contacted Member                      |
      | disposition   | Contact Member - Successfully Reached |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "Task" page
    Then I verify "Just Cause" contains : "Just Cause Request,Just Cause Resolution,Just Cause State Discussion" on "SR" link section

  @CP-38567 @CP-38567-01 @crm-regression @ui-wf-ineb @ozgen
  Scenario: IN-EB: Create Link between Just Cause SR and Just Cause Resolution Task when inbound document is created with same IN RID exists
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    When I searched customer have First Name as "NANCY" and Last Name as "KIYAN"
    When I expand the record to navigate Case Consumer Record from manual view
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Just Cause" service request page
    And I will provide following information before creating task
      | rid                 | 361589528203         |
      | contactName         | Alexa                |
      | contactPhone        | 5215215215           |
      | currentPlan         | Anthem               |
      | desiredPlanJC       | Anthem               |
      | programRequired     | Healthy Indiana Plan |
    And I click on save button in create service request page
    And I initiate request of documentId from Put ECMS documentencoded services with following values
      | documentType              | INEB Just Cause Form   |
      | Channel                   | MAIL                   |
      | Status                    | RECEIVED               |
      | ProcessType               | INBOUND CORRESPONDENCE |
      | StatusSetOn               | current                |
      | FromEmailAddress          | testing@gmail.com      |
      | FromPhoneNumber           | 9823569012             |
      | External Consumer ID Type | MEDICAID               |
      | IN RID                    | externalId             |
    When I send PUT ECMS documentencoded services and store documentId "one" in a list
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType               | INBOUND CORRESPONDENCE |
      | documentHandle            | fromInbound            |
      | Channel                   | Mail                   |
      | Language                  | English                |
      | documentType              | INEB Just Cause Form   |
      | Status                    | COMPLETE               |
      | IN RID                    | externalId             |
    When I send the request to create the Inbound Correspondence Save Event
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | InboundDocument |
    And I click on a CID of "first available" Inbound Search Results
    Then I click on refresh button
    And Wait for 5 seconds
    Then I verify "Just Cause Resolution" task and "Just Cause" SR is linked to inbound correspondence





