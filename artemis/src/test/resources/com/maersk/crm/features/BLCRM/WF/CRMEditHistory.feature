Feature: Validate Edit History

  @CP-21392 @CP-21392-01 @CP-21392-03 @CP-21392-04 @crm-regression @ui-wf-ineb @kamil
  Scenario: Validate Edit History on View Task Details
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "","","Just Cause","Open","","2","","","","","","true","","","","","Service TesterTwo","",""
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will update the following information in edit task page
      | rid                     | 521521521512                                 |
      | grievance               | 521521521512                                 |
      | status                  | Closed                                       |
      | reasonForEdit           | Corrected Data Entry                         |
      | contactName             | Alewa                                        |
      | contactPhone            | 5215215215                                   |
      | currentPlan             | Anthem                                       |
      | desiredPlanJC           | Anthem                                       |
      | programRequired         | Healthy Indiana Plan                         |
      | reasonExplanation       | FSSA Took Corrective Action Against the Plan |
      | explanation             | Limited Access to a Primary Care Clinic      |
      | taskInfo                | Test CP-23613                                |
      | missingInfoRequired     | No                                           |
      | dateHealthPlanContacted | today                                        |
      | dateReceivedGrievance   | today                                        |
      | invalid                 | true                                         |
      | dateFollowupEmailSent   | today                                        |
      | decision                | Agree                                        |
      | dateDecisionLetterSent  | today                                        |
      | dateSateNotified        | today                                        |
      | finalDecision           | Approved                                     |
      | consumerSatisfied       | Yes                                          |
      | disposition             | Invalid                                      |
    And I click on save button on task edit page
    And I navigated to the Edit History tab ON Edit Task
    Then Verify in Edit History page EditedOn is descending order
    Then I will verify Paginate for "show 5" records in "Edit" Page
    And I will verify Paginate for "show 10" records in "Edit" Page
    And I will verify Paginate for "show 20" records in "Edit" Page
    #Then Validate Edit History shows 5 records at a time with standard pagination controls
    And Close the soft assertions

  @CP-21392 @CP-21392-05 @crm-regression @ui-wf-coverVA @kamil
  Scenario: Validate I see a grid displaying the edit history information of the task
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Appeal Remand" task page
    And I will provide following information before creating task
      | externalApplicationId   |Test19365|
      | remandReason            |Other|
      | eligibilityDecision     |Approved |
      | remandDueDate           |today|
      | receivedDate            |-1|
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    And I click on edit button on view task page
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "Kazuma" and Last Name as "Kiryu"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    When I click on Link Record Consumer button
    And I will update the following information in edit task page
      |reasonForEdit              |Corrected Case/Consumer Link|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I click on edit button on view task page
    Then I select "Unlink Case/Consumer" in the create link section
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "Frankie" and Last Name as "Kazarian"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    When I click on Link Record Consumer button
    And I will update the following information in edit task page
      |reasonForEdit              |Corrected Case/Consumer Link|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I navigated to the Edit History tab ON Edit Task
    Then Verify Edit History table reflects both transactions as one for LINKandUNLINK with ConsumerIds
      |92|
      |8 |
    And Close the soft assertions

  @CP-21392 @CP-21392-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Verify fields update displaying the edit history information of the task
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "General Escalation" task page
    And I will provide following information before creating task
      | businessUnitAssigneeTo| EscalationBU      |
      | assignee              | Service TesterTwo |
      | taskInfo              ||
      | contactRecordSingle   | Emergency         |
      | externalCaseId        ||
      | externalApplicationId | random            |
      | actionTakenSingle     ||
      | documentDueDate       | today             |
      | cpCRID                ||
      | cpSRID                ||
      | cpTaskID              ||
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                | Complete             |
      | priority              | 2                    |
      | taskInfo              | CP-21003-02          |
      | reasonForEdit         | Corrected Data Entry |
      | contactRecordSingle   | PW Emergency         |
      | externalCaseId        | 123456789            |
#      | externalApplicationId | 2uusr2345            |
      | actionTknSingle       | Pending              |
      #| documentDueDate       | 07/22/2024           |
      | cpCRID                | 123456               |
      | cpSRID                | 123456               |
      | cpTaskID              | 123456               |
      | disposition           | Resolved             |
    And I click on save button on task edit page
    #And I verify Success message is displayed for task update
    And I navigated to the Edit History tab ON Edit Task
    And I select records count in pagination dropdown as "show 20" in "Edit History" page
    Then I verify edit history page has all the edited data information
    And Close the soft assertions

  @CP-15914 @CP-15914-04 @CP-21392 @CP-21392-06 @vidya @basha @nj-regression @ui-wf-nj
  Scenario: Edit task in mytask/Task search/TSRand Link component pages
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Review Appeals Form" task page
    And I will provide following information before creating task
      | status            |Escalated|
      | taskInfo          |Test CP-15914 test|
      | assignee          |Service AccountOne|
      | actionTaken       |No Action Taken|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I Verify "Review Appeals Form" task with "Escalated" status does have Initiate button
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status              |Cancelled|
      | reasonForCancel     |Created Incorrectly|
      | actionTaken         |Outbound Call Successful|
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    Then I verify the below task details are displayed in my task
    And I navigated to the Edit History tab ON Edit Task
    Then I verify edit history page has all the edited data information
    And Close the soft assertions

  @CP-1230 @CP-1230-02 @CP-21392 @CP-21392-07 @CP-31982 @CP-31982-03 @priyal @vidya @crm-regression @ui-wf
  Scenario: Verification of View On Hold task in My Task Queue
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General" task page
    And I will provide following information before creating task
      | priority                 |4|
      | status                   |Escalated|
      | taskInfo                 |random|
    And I click on save button in create task page
    #Then I verify Success message is displayed for task
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status          | OnHold              |
      | assignee        | Service AccountOne  |
      | reasonForOnHold | Missing Information |
    And I click on save button on task edit page
    And I navigated to the Edit History tab ON Edit Task
    Then I verify edit history page has all the edited data information
    And I navigated to the Task Details tab ON Edit Task
    And I will click on back arrow on view task page
    And I click on My task tab
    And I click task id to get the results in descending order
    #And I navigate to view task just assign before on My task queue
    Then I verify status is as "OnHold"
    And Close the soft assertions


  @CP-21393 @CP-21393-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ruslan
  Scenario: Verify fields update displaying the edit history information of the sr
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType           | Ex Parte Renewal - CVIU |
      | externalApplicationId     | random                  |
      | myWorkSpaceDate           | today                   |
      | ldssReceivedDate          | today                   |
      | miReceivedDate            | today                   |
      | applicationSignatureDate  | today                   |
      | applicationReceivedDate   | today                   |
      | applicationUpdateDate     | today                   |
      | noOfApplicantsInHousehold | 1                       |
      #| noOfApprovedApplicants    | 1                       |
      | channel                   | Paper                   |
      #| decisionSource            ||
      | missingInfoRequired       | Yes                     |
      | applicationStatus         | Data Collection         |
      | externalCaseId            | 123654789               |
      #| denialReason              ||
      #| locality                  | Westmoreland            |
      | InbDocType                | Drivers License         |
      | vclDueDate                | today                   |
      | vclSentDate               | today                   |
      #| reason                    | Case Change             |
      | facilityName              | Bristol City Jail       |
      | caseWorkerFirstName       | Ruslan                  |
      | caseWorkerLastName        | Agaev                   |
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      |advanceSearch |true|
      |dateOfContact |today|
      |applicationId |getFromCreatePage|
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will update the following information in edit task page
      | status                    | Closed                  |
      | applicationType           | MAGI - CPU              |
      | reasonForEdit             | Entered Additional Info |
      | noOfApplicantsInHousehold | 2                       |
      | channel                   | RDE                     |
      | missingInfoRequired       | No                      |
      | applicationStatus         | Research                |
      | externalCaseId            | 987654321               |
      | disposition               | Referred                |
      | noOfApprovedApplicants    | 2                       |
      | caseWorkerFirstName       | Abos                    |
      | caseWorkerLastName        | Barbos                  |
      | locality                  | Bland                   |
      | reason                    | Requested by LDSS       |
    And I click on save button on task edit page
    And I navigated to the Edit History tab ON Edit Task
    Then Verify in Edit History page EditedOn is descending order
    Then I will verify Paginate for "show 5" records in "Edit" Page
    And I will verify Paginate for "show 10" records in "Edit" Page
    And I will verify Paginate for "show 20" records in "Edit" Page
    Then I verify edit history page has all the edited data information
    And Close the soft assertions