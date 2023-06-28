Feature: Validation of Task Permissions Functionality

  @CP-11767 @CP-11767-01 @CP-12692 @CP-12692-02 @Basha @crm-regression @ui-wf
  Scenario: Verification of the Initate button for permission set to the work
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When I navigate to "Inbound Task" task page
    And I will provide following information before creating task
      | taskInfo | Test CP11767       |
      | assignee | Service AccountTwo |
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I Verify "Inbound Task" task with "Created" status does have Initiate button
    And I change task status to "OnHold"
    And I click task id to get the results in descending order
    Then I Verify "Inbound Task" task with "OnHold" status does have Initiate button
    And I change task status to "Open"
    And I click task id to get the results in descending order
    Then I Verify "Inbound Task" task with "Open" status does have Initiate button
    And Close the soft assertions

  @CP-11767 @CP-11767-02 @Basha @crm-regression @ui-wf
  Scenario: Verification of the No Initiate button for No permission set to the work
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    When I navigate to "Correspondence" task page
    And I will provide following information before creating task
      | taskInfo | Test CP-10688      |
      | assignee | Service AccountTwo |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I Verify "Correspondence" task with "Created" status does not have Initiate button
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority | 4                       |
      | status   | Escalated               |
      | assignee | Service AccountTwo      |
      | taskInfo | Test CP-12963 Task Info |
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    Then I am able to navigate back to My Task or Work Queue page
    And I click task id to get the results in descending order
    Then I Verify "Correspondence" task with "Escalated" status does not have Initiate button
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority        | 1                       |
      | status          | OnHold                  |
      | reasonForOnHold | Missing Information     |
      | assignee        | Service AccountTwo      |
      | taskInfo        | Test CP-12963 Task Info |
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    Then I am able to navigate back to My Task or Work Queue page
    And I click task id to get the results in descending order
    Then I Verify "Correspondence" task with "OnHold" status does not have Initiate button
    And Close the soft assertions

  @CP-11767 @CP-11767-03 @Basha @crm-regression @ui-wf
  Scenario: Verification of the Initiate button for permission set to the Escalated
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When I navigate to "General" task page
    And I will provide following information before creating task
      | status   | Escalated          |
      | assignee | Service AccountOne |
      | taskInfo | Test CP11767       |
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I Verify "General" task with "Escalated" status does have Initiate button
    And I click on initiate randomly
    Then Verify task slider is displayed
    And I click on save in Task Slider
    And Close the soft assertions

  @CP-11767 @CP-11767-04 @Basha @crm-regression @ui-wf
  Scenario: Verification of the No Initiate button for permission set to the Escalated
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    When I navigate to "Inbound Task" task page
    And I will provide following information before creating task
      | status   | Escalated          |
      | assignee | Service AccountTwo |
      | taskInfo | Test CP-11767      |
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I Verify "Inbound Task" task with "Escalated" status does not have Initiate button
    And Close the soft assertions

  @CP-12692 @CP-12692-01 @crm-regression @ui-wf @vidya
  Scenario Outline: verify tasks with create status has initiate button since task type has permission to work
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    Then I verify tasks are have initiate button since searched parameter has permission to work
    And Close the soft assertions
    Examples:
      | taskId | taskType     | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      || Inbound Task || Created || 3        ||            ||                || false         ||            ||          ||           ||

  @CP-12692 @CP-12692-03 @crm-regression @ui-wf @vidya #failed due to CP-14765
  Scenario Outline: verify tasks with create status does not has initiate button since task type does not have permission to wor
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    Then I verify tasks are does not have initiate button since searched parameter does not have permission to work
    And Close the soft assertions
    Examples:
      | taskId | taskType       | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      || Correspondence || Created || 3        ||            ||                || false         ||            ||          ||           ||

  @CP-12692 @CP-12692-04 @crm-regression @ui-wf @vidya #failed due to CP-14765
  Scenario Outline: verify tasks with On-Hold status does not has initiate button since task type does not have permission to wor
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    Then I verify tasks are does not have initiate button since searched parameter does not have permission to work
    And Close the soft assertions
    Examples:
      | taskId | taskType       | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || Correspondence || OnHold ||          ||            ||                || false         ||            ||          ||           ||

  @CP-12692 @CP-12692-05 @crm-regression @ui-wf @vidya
  Scenario Outline: verify tasks with Escalated status has initiate button since task type has permission to work
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify tasks are have initiate button since searched parameter has permission to work
    And Close the soft assertions
    Examples:
      | taskId  | taskType     | srType  | status    | statusDate | Priority | dueDate | searchCase | caseId  | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source  | assignee | createdBy | createdOn | contactReason |
      |  | Inbound Task |  | Escalated |     |          |  |            |  |                |     | true         |        |            |  |    Service AccountOne       |    |           |        |

  @CP-12692 @CP-12692-06 @crm-regression @ui-wf @vidya #failed due to CP-14765
  Scenario Outline: verify tasks with Escalated status does not has initiate button since task type does not have permission to wor
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    Then I verify tasks are does not have initiate button since searched parameter does not have permission to work
    And Close the soft assertions
    Examples:
      | taskId | taskType     | srType | status    | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      || Inbound Task || Escalated || 3        ||            ||                || false         ||            ||          ||           ||

  @CP-12692 @CP-12692-07 @crm-regression @ui-wf @vidya
  Scenario Outline: verify tasks with Open status and assignee to me has initiate button
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    Then I verify tasks are have initiate button since searched parameter has permission to work
    And I scroll down the page
    And I verify tasks have "Service AccountTwo" on expanded view
    And Close the soft assertions
    Examples:
      | taskId  | taskType | srType  | status | statusDate | Priority | dueDate | searchCase | caseId  | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source  | assignee           | createdBy | createdOn | contactReason |
      |  |          |  | Open   |     |          |  |            |  |                |     | true          |     |            |  | Service AccountTwo |    | today     |        |

  @CP-12692 @CP-12692-08 @crm-regression @ui-wf @vidya
  Scenario Outline: verify tasks with Open status and not assingee to me does not have initiate button
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    Then I verify tasks are does not have initiate button since searched parameter does not have permission to work
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee           | createdBy | createdOn |contactReason|
      ||          || Open   ||          ||            ||                || true          ||            || Service AccountOne ||           ||

    #Mute the below test case because of test data issue
  #@CP-12692 @CP-12692-09 @crm-regression @ui-wf @vidya #failed due to CP-14765
  Scenario Outline: verify tasks with In-Progress status and assignee to me has initiate button
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify tasks are have initiate button since searched parameter has permission to work
    And I verify tasks have "Service AccountTwo" on expanded view
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status      | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee           | createdBy | createdOn |contactReason|
      ||          || In-Progress ||          ||            ||                || true          ||            || Service AccountTwo ||           ||

  #@CP-12692 @CP-12692-10 @crm-regression @ui-wf @vidya #failed due to CP-14767 due to assignee
  Scenario Outline: verify tasks with In-Progress status and not assingee to me does not have initiate button
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify tasks are does not have initiate button since searched parameter does not have permission to work
    And I verify tasks have "Service AccountOne" on expanded view
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status      | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee           | createdBy | createdOn |contactReason|
      ||          || In-Progress ||          ||            ||                || true          ||            || Service AccountOne ||           ||


#  @CP-12692 @CP-12692-12 @crm-regression @ui-wf @vidya #failed due to CP-33709 functionality gap
  Scenario Outline: Verification of status drop down value when we are editing the task with Open status
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    When I navigate to "My Task" page
    And I will ensure assignee has at least one In-Progress with task type "Inbound Task"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    Then I will verify user gets error message pop up is displayed
    Then I will click on cancel button
    When I navigate to "My Task" page
    And Initiate a task which is status "In-Progress"
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    When I navigate to "Task Search" page
    And I will search with taskId
    And I click on initiate button in task search page
    And I Verify Task slider is Expand
    And I click on save in Task Slider
    And I verify Success message is displayed for task
    And Close the soft assertions
    Examples:
      | taskId | taskType     | srtype | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      | 9198   | Inbound Task ||        ||          ||            ||                || false         ||            ||          ||           ||


  @CP-21363 @CP-21363-1 @CP-21363-1.1 @CP-21363-02 @CP-21363-03 @ui-wf-nj @nj-regression @kamil
  Scenario: Validate Task - Subsidy Escalation fields
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Subsidy Escalation" task page
    And I will provide following information before creating task
      | assignee | Service AccountOne |
      | status   | Complete           |
    Then I verify "reason" single select drop down value
      | Medicaid Denial     |
      | Updated Application |
    Then I verify "disposition" single select drop down value
      | Resolved          |
      | Unable to Resolve |
    And I verify "actionTaken" multi select drop down value
      | Subsidies Applied                              |
      | Unable to Apply Subsidies, Escalated to Client |
      | Unable to Apply Subsidies, System Issue        |
    And I will provide following information before creating task
      | reason      | Medicaid Denial   |
      | actionTaken | Subsidies Applied |
      | disposition | Resolved          |
    And I click on save button on task edit page
    And I will get "Complete" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    When I navigate to "Task Search" page
    Then I will search for newly created task on Task Search
    And I click on search button on task search page
    And In search result click on task id to navigate to view page
    Then I verify task following fields are present on view page
      | REASON       |
      | ACTION TAKEN |
      | DISPOSITION  |
    And I click on edit button on view task page
    Then I verify "reason" single select drop down value
      | Medicaid Denial     |
      | Updated Application |
    Then I verify "disposition" single select drop down value
      | Resolved          |
      | Unable to Resolve |
    And I verify "actionTaken" multi select drop down value
      | Subsidies Applied                              |
      | Unable to Apply Subsidies, Escalated to Client |
      | Unable to Apply Subsidies, System Issue        |
    And Close the soft assertions

  @CP-20849 @CP-20849-1.1 @CP-20849-02 @CP-20849-03 @ui-wf-nj @nj-regression @kamil
  Scenario: Validate Task - Enrollment Escalation fields
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Enrollment Escalation" task page
    And I will provide following information before creating task
      | assignee | Service AccountOne |
      | status   | Complete           |
    Then I verify "reason" single select drop down value
      | Change Coverage End Date   |
      | Change Coverage Start Date |
      | Enrollment Cancellation    |
      | Medicaid Denial            |
      | Updated Application        |
    And Verify Plan StartDate and Plan EffectiveDate
    Then I verify "disposition" single select drop down value
      | Resolved          |
      | Unable to Resolve |
    And I verify "actionTaken" multi select drop down value
      | Cancelled Enrollment       |
      | Change Coverage End Date   |
      | Change Coverage Start Date |
      | Did Not Cancel Enrollment  |
    And I will provide following information before creating task
      | reason      | Medicaid Denial      |
      | actionTaken | Cancelled Enrollment |
      | disposition | Resolved             |
    And I click on save button on task edit page
    And I will get "Complete" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    When I navigate to "Task Search" page
    Then I will search for newly created task on Task Search
    And I click on search button on task search page
    And In search result click on task id to navigate to view page
    Then Validate View Task UI fields Display fields
      | REASON              |
      | ACTION TAKEN        |
      | COVERAGE END DATE   |
      | COVERAGE START DATE |
      | COVERAGE END DATE   |
      | COVERAGE START DATE |
      | DISPOSITION         |
    And I click on edit button on view task page
    Then I verify "reason" single select drop down value
      | Change Coverage End Date   |
      | Change Coverage Start Date |
      | Enrollment Cancellation    |
      | Medicaid Denial            |
      | Updated Application        |
    And Verify Plan StartDate and Plan EffectiveDate
    Then I verify "disposition" single select drop down value
      | Resolved          |
      | Unable to Resolve |
    And I verify "actionTaken" multi select drop down value
      | Cancelled Enrollment       |
      | Change Coverage End Date   |
      | Change Coverage Start Date |
      | Did Not Cancel Enrollment  |
    And Close the soft assertions

  @CP-12692 @CP-12692-11 @crm-regression @ui-wf @vidya
  Scenario Outline: verify tasks with Complete and Cancelled status does not has initiate button
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    Then I select item count in search result page as "show 20"
    And I verify task search results are displayed
    Then I verify tasks are does not have initiate button since searched parameter does not have permission to work
    And Close the soft assertions
    Examples:
      | taskId | taskType     | srType | status    | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      |        |              |        | Complete  |            | 5        |         |            |        |                |            | false         |            |            |        |          |           |           |               |
      |        | Calendar Day |        | Cancelled |            |          |         |            |        |                |            | false         |            |            |        |          |           |           |               |
