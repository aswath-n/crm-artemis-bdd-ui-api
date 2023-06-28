Feature: Validation of Task Slider Functionality

  @CP-5139 @CP-5139-03 @CP-34852 @CP-34852-04 @CP-34852-02 @crm-regression @ui-wf @Basha
  Scenario: Task Search, verification of task slider is open inside task view details page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When I navigate to "General" task page
    And I will provide following information before creating task
      | assignee  | Service AccountOne |
      | taskInfo  | random             |
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    When I navigate to "My Task" page
    When If any In Progress task present then update that to Cancelled
    And I navigate to newly created task by clicking on TaskID column header
    And I expend first Task from the list by clicking in Task ID
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify save task search section is displayed
    And I click on initiate button in task search page
    Then Verify task slider is displayed
    Then I verify the field values on View Task details
    And I will update the following information in task slider
      | status          | Complete  |
      | disposition     | User closed |
      | noteValue       | Test SliderNotes  |
    And I click on save in Task Slider
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    Then I verify saved task note from slider
    And Close the soft assertions
    
  @CP-11764 @CP-11764-01 @CP-647 @CP-647-02 @vidya @crm-regression @ui-wf
  Scenario Outline: verify tasks with Complete and Cancelled status does not has initiate button for BLCRM
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I will get the Authentication token for "BLCRM" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Calendar Day" task page
    And I click on save button in create task page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","Calendar Day","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click task id to get the results in descending order in Task Search
    And I click on initiate button in task search page
    Then Verify task slider is displayed
    And I verify Active contact screen displayed with initiate call
    Then I verify the link section has case consumer and task information on Active Contact screen
    And I click on cancel button on task slider
    Then I verify warning is displayed upon clicking Cancel button on Task slider
    And I click on continue on task details warning window
    When I navigate to "Task Search" page
    And I will search with taskId
    Then In result I verify task is saved with status "Open"
    And In search result click on task id to navigate to view page
    Then I verify the link section has case consumer and contact id information
    And Close the soft assertions
    Examples:
      |taskId |taskType|srType|status   |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|contactReason|
      ||        ||         ||        ||          ||              ||false        ||          ||        ||         ||

  @CP-735 @CP-735-08 @CP-735-09 @CP-5139 @CP-5139-02 @CP-4345 @CP-4345-02 @vidya @crm-regression @ui-wf
  Scenario: Verification of all task has Initiate button
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Work Queue" page
    And I will check whether it contains at least one "General" task if not I will create task
#    Then I verify all tasks has initiate button
    And I will get the index of "General" task and click on initiate button for that
    Then I verify the field values on View Task details
    And I update the task status in task slider as "Cancel"
    And I will select reason for cancel drop down value as "Duplicate Task"
    And I click on save in Task Slider
    Then I verify user is redirected to work queue page
    And Close the soft assertions

    #Refactor By:priyal
  @CP-735 @CP-735-11 @vidya @crm-regression @crm-smoke @ui-wf
  Scenario: Verification of Re-Escalate Previously Escalated Task
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I will get the Authentication token for "BLCRM" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Inbound Task" task page
    And I will provide following information before creating task
      | status                   |Escalated|
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    And I click on save in Task Slider
    And I navigate to MyTask tab
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    And I will update the following information in task slider
      |status       |Escalated|
    And I click on save in Task Slider
    And I navigate to WorkQueue tab
    And I navigate to newly created task by clicking on TaskID column header
    Then I verify Re-Escalate previously Escalated task
    And Close the soft assertions

  @CP-10931 @CP-10931-06 @vidya @crm-regression @ui-wf
  Scenario: Work queue, verification of task slider is open inside task view details page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Incomplete Contact Record" task page
    Then I verify create task fields displayed on screen
    And I will provide following information before creating task
      | taskInfo  | Test CP-10931|
      | assignee  ||
      | Status    |Escalated     |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I will ensure there is no task in progress status
    And I navigate to WorkQueue tab
    And I navigate to newly created task by clicking on TaskID column header
    And I will get the index of "Incomplete Contact Record" task and click on initiate button for that
    Then I verify the field values on View Task details
    And I update the task status in task slider as "Cancel"
    And I will select reason for cancel drop down value as "Duplicate Task"
    And I click on save in Task Slider
    And Close the soft assertions

#Refactor By:Vidya
  @CP-10143 @CP-10143-02 @crm-regression @ui-wf @kamil
  Scenario:Verify Default navigation - Has NO linked correspondence ID
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Correspondence" task page
    And I will provide following information before creating task
      | assignee                 |Service TesterSix|
      | taskInfo                 |Test CP-10143|
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    And I verify the field values on View Task details
    And Verify PDF viewer is not initiated
    And I update the task status in task slider as "Complete"
    And I click on save in Task Slider
    And Close the soft assertions

  # refactor by priyal on 22-10-2021
  @CP-10143 @CP-10143-01 @crm-regression @ui-wf @kamil
  Scenario Outline:Verify Associated Screen - View Inbound Correspondence Details
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "Correspondence" for project ""
    And I will provide required information to create external task with "<caseId>" "<consumerId>" "<inboundId>" "<taskInfo>" "<triggerDate>"
      ||
    When I initiated external create task api
    And I run the create external task API and check the status code is "200"
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I Navigate to task which we created from api using task id and click on initiate button
    Then Verify PDF viewer is initiated for linked Correspondence ID
    And User is navigated to the View Correspondence Details UI for the linked Inbound Correspondence ID
    And I update the task status in task slider as "Complete"
    And I click on save in Task Slider
    Then I verify user is redirected to work queue page
    And Close the soft assertions
    Examples:
      |projectName|caseId|consumerId|inboundId|taskInfo|triggerDate|
      ||9299  |21470     |725      |CP-9269 |currentDate|

  # refactor by priyal on 22-10-2021
  @CP-10226 @CP-10226-01 @crm-regression @ui-wf @Basha
  Scenario Outline:Verify Open PDF Viewer for linked Inbound correspondence ID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "Correspondence" for project ""
    And I will provide required information to create external task with "<caseId>" "<consumerId>" "<inboundId>" "<inboundId1>" "<taskInfo>" "<triggerDate>"
      ||
    When I initiated external create task api
    And I run the create external task API and check the status code is "200"
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I Navigate to task which we created from api using task id and click on initiate button
    Then Verify PDF viewer is initiated for linked Correspondence ID
    And User is navigated to the View Correspondence Details UI for the linked Inbound Correspondence ID
    And I update the task status in task slider as "Complete"
    And I click on save in Task Slider
    Then I verify user is redirected to work queue page
    And Close the soft assertions
    Examples:
      |projectName|caseId|consumerId|inboundId|taskInfo|triggerDate|inboundId1|
      ||9298  |21469     |725      |CP-10226|currentDate|3544      |

  @CP-24429 @CP-24429-02 @CP-24430 @CP-24430-02 @CP-25672 @CP-25672-01 @CP-24428 @CP-24428-02 @CP-28293 @CP-28293-06 @CP-25296 @CP-25296-01 @CP-28295 @CP-28295-03 @crm-regression @ui-wf-nj @nj-regression @vidya
  Scenario: Verify Follow-Up on Appeal task is created for Appeal sr when complete the Review Appeals Form task from task slider and validate appeal sr closed when follow-up on appeal task completed
    Given I logged into CRM with "Service Account 8" and select a project "NJ-SBE"
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | NJ SBE Appeal Form       |
      |ConsumerId    |2146                      |
      |CaseId        |737                       |
    When I send the request to create the Inbound Correspondence Save Event
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with taskId
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I click on task id on the task slider
    And I click on the priority in dashboard
    And I will update the following information in task slider
      |status       |Complete|
      |actionTaken  |Outbound Call Unsuccessful|
      |disposition  |IDR Unsuccessful|
    And I click on save in Task Slider
#    Then I verify Success message is displayed for task update
    And In search result click on task id to navigate to view page
    And I click id of "Appeal" in Links section
    Then I verify "Appeal" link section has all the business object linked : "Task,Task,Task,Inbound Correspondence,Case,Consumer"
    And I click id of "Follow-Up on Appeal" in Links section
    Then I verify "Follow-Up on Appeal" task fields are displayed
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    And I verify latest task displayed in "Work Queue or My Task" page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And In search result click on task id to navigate to view page
    Then I verify "Follow-Up on Appeal" link section has all the business object linked : "Service Request,Inbound Correspondence,Case,Consumer"
    And I will click on back arrow on view task page
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I click on task id on the task slider
    And I click on the priority in dashboard
    And I will update the following information in task slider
      |status       |Complete|
      |actionTaken  |Outbound Call Unsuccessful|
      |disposition  |IDR Unsuccessful|
    And I click on save in Task Slider
#    Then I verify Success message is displayed for task update
    And In search result click on task id to navigate to view page
    And I click id of "Appeal" in Links section
    Then I verify "Appeal" link section has all the business object linked : "Task,Task,Task,Task,Inbound Correspondence,Case,Consumer"
    And I click id of "GCNJ Resolve Appeal" in Links section
    Then I verify "GCNJ Resolve Appeal" task fields are displayed
    Then I verify "GCNJ Resolve Appeal" link section has all the business object linked : "Service Request,Consumer,Inbound Correspondence,Case"
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I click on task id on the task slider
    And I click on the priority in dashboard
    And I will update the following information in task slider
      |status       |Complete|
      |disposition  |IDR Successful|
    And I click on save in Task Slider
#    Then I verify Success message is displayed for task update
    And In search result click on task id to navigate to view page
    And I click id of "Appeal" in Links section
    Then I verify the sr status is updated to close and disposition is set to "IDR Successful"
    Then I verify "Appeal" link section has all the business object linked : "Task,Task,Task,Task,Task,Inbound Correspondence,Case,Consumer"
    And I initiated bpm process get api by service request id for process name "NJSBE_Appeal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And I click id of "Generate IDR Successful Letter" in Links section
    Then I verify "Generate IDR Successful Letter" task fields are displayed
    Then I verify "Generate IDR Successful Letter" link section has all the business object linked : "Service Request,Consumer,Inbound Correspondence,Case"
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And Close the soft assertions

  @CP-28293 @CP-28293-02 @CP-24425 @CP-24425-02 @CP-25296 @CP-25296-02 @CP-28295 @CP-28295-04 @crm-regression @ui-wf-nj @nj-regression @vidya
  Scenario: Verify GCNJ Resolve Appeal task is created for Appeal sr when complete the Review Appeals Form task from task slider and check appeal sr is closed
    Given I logged into CRM with "Service Account 8" and select a project "NJ-SBE"
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | NJ SBE Appeal Form       |
      |ConsumerId    |2295                      |
      |CaseId        |776                       |
    When I send the request to create the Inbound Correspondence Save Event
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with taskId
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I click on task id on the task slider
    And I click on the priority in dashboard
    And I will update the following information in task slider
      |status       |Complete|
      |actionTaken  |Outbound Call Unsuccessful|
      |disposition  |Not a Valid Appeal|
    And I click on save in Task Slider
#    Then I verify Success message is displayed for task update
    And In search result click on task id to navigate to view page
    And I click id of "Appeal" in Links section
    Then I verify "Appeal" link section has all the business object linked : "Task,Task,Task,Inbound Correspondence,Case,Consumer"
    Then I verify the sr status is updated to close and disposition is set to "IDR Successful"
    And I initiated bpm process get api by service request id for process name "NJSBE_Appeal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And I click id of "Generate IDR Successful Letter" in Links section
    Then I verify "Generate IDR Successful Letter" task fields are displayed
    Then I verify "Generate IDR Successful Letter" link section has all the business object linked : "Service Request,Consumer,Inbound Correspondence,Case"
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And Close the soft assertions

  @CP-25774 @CP-25774-01 @CP-25774-02 @CP-25774-03 @crm-regression @INEB-UI-Regression @ui-wf-ineb @ruslan
  Scenario: Validate 'Disenrollment Date' field is required if Disposition is Approved on Create / edit page / task slider
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    When I searched customer have First Name as "CATHERINE" and Last Name as "MARCOS"
    When I click the first consumer id from the search results
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Disenrollment" task page
    And I will provide following information before creating task
      | status      | Complete |
      | disposition | Approved |
    And I click on save button in create task page
    Then I verify "DisenrollmentDate" field is enable and required
    And I will provide following information before creating task
      | disenrollmentDate | today   |
      | status            | Created |
    Then I verify "DisenrollmentDate" field is disable and cleared out
    And I will provide following information before creating task
      | assignee            | Service TesterTwo |
      | disenrollmentReason | Waiver            |
      | currentPlan         | MHS               |
      | source              | Other             |
      | other               | 123 $567 Tesr5565#$#535 |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created task in Task & Service Request Tab
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | disposition   | Approved             |
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    Then I verify "DisenrollmentDate" field is enable and required
    And  I scroll up the page
    And I will update the following information in edit task page
      | disenrollmentDate | today   |
      | status            | Created |
    Then I verify "DisenrollmentDate" field is disable and cleared out
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task
    When If any In Progress task present then update that to Cancelled
    And I will click on back arrow on view task page
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created task in Task & Service Request Tab
    And I click on initiate randomly
    And I will update the following information in task slider
      | status      | Complete |
      | disposition | Approved |
    Then I validate the system displays an Error Message: "Please provide the Disenrollment Date on the task."
    And I will update the following information in task slider
      | status      | In Progress |
    And I click on save in Task Slider
#    Then I verify task save success message
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created task in Task & Service Request Tab
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status            | Complete                |
      | disposition       | Approved                |
      | disenrollmentDate | today                   |
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    And Close the soft assertions

  @CP-25790 @CP-25790-01 @CP-25790-02 @CP-25790-03 @crm-regression @INEB-UI-Regression @ui-wf-ineb @ruslan
  Scenario: Validate fields enable and disable functionality present in DCS Request on create / edit page / task slider
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    When I searched customer have First Name as "CATHERINE" and Last Name as "MARCOS"
    When I click the first consumer id from the search results
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "DCS Request" task page
    When I update status to "Complete" on task
    And I click on save button on task edit page
    Then I verify "Decision" field is enable and required
    And I will provide following information before creating task
      | status   | Created           |
      | assignee | Service TesterTwo |
    And I click on save button in create task page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created task in Task & Service Request Tab
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status | Complete |
    And I click on save button on task edit page
    Then I verify "Decision" field is enable and required
    And I scroll up the page
    And I will update the following information in edit task page
      | status | Created |
    And I click on save button on task edit page
    When If any In Progress task present then update that to Cancelled
    And I will click on back arrow on view task page
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created task in Task & Service Request Tab
    And I click on initiate randomly
    And I will update the following information in task slider
      | status | Complete |
    Then I validate the system displays an Error Message: "Please provide the Decision on the task."
    And I will update the following information in task slider
      | status      | In Progress |
    And I click on save in Task Slider
#    Then I verify task save success message
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created task in Task & Service Request Tab
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status              | Complete             |
      | eligibilityDecision | Approved             |
      | actionTaken         | Sent Denial Email    |
      | reasonForEdit       | Corrected Data Entry |
    And I click on save button on task edit page
    And Close the soft assertions

  @CP-13418 @CP-13418-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @kamil
  Scenario:Verify Move Task Status to Open when Logging Out while Working a Task
    When I will get the Authentication token for "CoverVA" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Inbound Document" task page
    And I will provide following information before creating task
      | assignee                |Service TesterTwo|
      | taskInfo                ||
      | externalConsumerID    ||
      | externalCaseId        ||
      | externalApplicationId | T19575    |
      | actionTaken             ||
      | InbDocType              | Complaint |
      | program                 | EAP       |
    And I click on save button in create task page
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status          |Cancel|
      |reasonForCancel  |Duplicate Task|
    Then I logOut while Working a Task
    Then I logged into CRM after logged out with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    Then Verify in My Task first task status is "Open"
    And Close the soft assertions

  @CP-28302 @CP-28302-01 @CP-25286 @CP-25286-01 @CP-28303 @CP-28303-01 @CP-28060 @CP-28060-05 @crm-regression @ui-wf-nj @nj-regression @vidya @ruslan
  Scenario: Verify Systematically Close Appeal SR when Generate IDR Unsuccessful Letter Task is Systematically Created
    Given I logged into CRM with "Service Account 8" and select a project "NJ-SBE"
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | NJ SBE Appeal Form       |
      |ConsumerId    |2199                      |
      |CaseId        |753                       |
    When I send the request to create the Inbound Correspondence Save Event
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with taskId
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I click on task id on the task slider
    And I click on the priority in dashboard
    And I will update the following information in task slider
      |status       |Complete|
      |actionTaken  |Outbound Call Unsuccessful|
      |disposition  |IDR Unsuccessful|
    And I click on save in Task Slider
    And In search result click on task id to navigate to view page
    And I click id of "Appeal" in Links section
    And I click id of "Follow-Up on Appeal" in Links section
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I click on task id on the task slider
    And I click on the priority in dashboard
    And I will update the following information in task slider
      |status       |Complete|
      |actionTaken  |Outbound Call Unsuccessful|
      |disposition  |IDR Unsuccessful|
    And I click on save in Task Slider
    And In search result click on task id to navigate to view page
    And I click id of "Appeal" in Links section
    And I click id of "GCNJ Resolve Appeal" in Links section
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I click on task id on the task slider
    And I click on the priority in dashboard
    And I will update the following information in task slider
      |status       |Complete|
      |disposition  |IDR Unsuccessful|
    And I click on save in Task Slider
    And In search result click on task id to navigate to view page
    And I click id of "Appeal" in Links section
    Then I verify the sr status is updated to close and disposition is set to "IDR Unsuccessful"
    Then I verify "Appeal" link section has all the business object linked : "Task,Task,Task,Task,Task,Inbound Correspondence,Case,Consumer"
    And I initiated bpm process get api by service request id for process name "NJSBE_Appeal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And I click id of "Generate IDR Unsuccessful Letter" in Links section
    Then I verify "Generate IDR Unsuccessful Letter" task fields are displayed
    Then I verify "Generate IDR Unsuccessful Letter" link section has all the business object linked : "Service Request,Consumer,Inbound Correspondence,Case"
    When I navigate to "Task Search" page
    And I will search with taskId
    Then I verify latest task displayed in "Task Search" page
    And Close the soft assertions

  @CP-27301 @CP-27301-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
  Scenario: Validate fields enable and disable functionality present in HPE Final Decision on Create/ edit/ Task Slider page
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When If any In Progress task present then update that to Cancelled
    When I navigate to "HPE Final Decision" task page
    Then I verify "sentNOADate" field is disable and cleared out
    And I will provide following information before creating task
      | actionTaken | Sent NOA |
    And I click on save button in create task page
    Then I verify "sentNOADate" field is enable and required
    And I will provide following information before creating task
      | assignee           |Service AccountTwo|
      | actionTaken        |Corrected Exception,Uploaded to DMIS|
      | outcome            |Final Denial|
      | contactRecordSingle|Eligibility Status|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      |actionTaken |Uploaded to DMIS,Sent NOA|
    And I click on save in Task Slider
    Then Verify I will see error message "Please provide the Sent NOA Date on the task."
    And I will update the following information in task slider
      |actionTaken |Uploaded to DMIS|
    And I click on save in Task Slider
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    Then I verify "sentNOADate" field is disable and cleared out
    And I will update the following information in edit task page
      | reasonForEdit      |Corrected Data Entry|
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I will update the following information in task slider
      | actionTaken |Escalated to DMAS,Uploaded to DMIS,Sent NOA|
    And I click on save in Task Slider
    Then Verify I will see error message "Please provide the Sent NOA Date on the task."
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | actionTaken   |Sent NOA |
      | sentNOADate   |today|
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I click on the priority in dashboard
    And I will update the following information in task slider
      | status      |Complete |
      | actionTaken |Escalated to DMAS,Sent NOA|
    And I click on save in Task Slider
    Then I verify task save success message
    And Close the soft assertions