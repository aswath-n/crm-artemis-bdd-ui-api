@taskSliderForCoverVA
Feature: Task slider functionality for IN-EB

  @CP-24968 @CP-24968-02 @CP-34852 @CP-34852-06 @CP-34852-02 @crm-regression @ui-wf-ineb @vidya
  Scenario: Verify Supervisor Review Complaint task is created for complaint sr in in-eb
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I send API CALL for Create CaseConsumer
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched case and consumer created by api
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | memberName     | Vidya Mithun     |
      | preferredPhone | 1234567890       |
      | complaintAbout | maersk          |
      | incidentDate   | today            |
      | complaintType  | Customer Service |
    And I click on save button in create service request page
 #   Then I verify Success message is displayed for SR
    When If any In Progress task present then update that to Cancelled
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status      | Complete           |
      | actionTaken | Created Evaluation |
      | disposition | Complaint Reviewed |
      | noteValue   | Test SliderNotes   |
    And I click on save in Task Slider
 #   Then I verify task save success message
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    Then I verify saved task note from slider
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    And I click id of "Supervisor Review Complaint" in Links section
    Then I verify "Supervisor Review Complaint" task fields are displayed
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I verify latest task displayed in "Work Queue or My Task" page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And Close the soft assertions

  @CP-28341 @CP-28341-02 @crm-regression @INEB-UI-Regression @ui-wf-ineb @priyal
  Scenario: Validate fields enable and disable functionality present in Inbound Document on Task Slider page
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Inbound Document" task page
    And I will provide following information before creating task
      | assignee    | Service TesterTwo |
      | actionTaken ||
      | other       ||
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | actionTaken | Other,Responded via Fax |
    Then Verify I will see error message "Please update Other with what action was taken."
    And click on close button on error Popup
    And I click on save in Task Slider
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I will update the following information in task slider
      | actionTaken | Other |
    Then Verify I will see error message "Please update Other with what action was taken."
    And click on close button on error Popup
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | medicaidId  | 234567651 |
      | actionTaken | Other     |
      | other       | Test      |
    And I click on save button on task edit page
    And I click on the priority in dashboard
    And I will update the following information in task slider
      | actionTaken | Other,Responded via Phone |
    And I click on save in Task Slider
    Then I verify task save success message
    And Close the soft assertions

  @CP-30419 @CP-30419-03 @CP-34970 @CP-34970-01 @CP-34970-02 @crm-regression @INEB-UI-Regression @ui-wf-ineb @vidya
  Scenario Outline: Validate disposition value for HCC Outbound Call task in slider
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click on initiate button in task search page
    And I will update the following information in task slider
      | status | Complete |
    And I verify dispostion field values in slider
      | Invalid              |
      | Needs Plan Selection |
      | Plan Selection Made  |
    And I will update the following information in task slider
      | actionTaken | Verified - Needs Call |
      | disposition | Invalid               |
    And I click on save in Task Slider
    Then Verify I will see error message "Please correct the Disposition or provide the Invalid Reason on the HCC Outbound Call Task"
    Then I verify "invalidReason" single select drop down value in slider
      | Not an HCC Member         |
      | Other                     |
      | Plan Selection Not Needed |
    And I will update the following information in task slider
      | invalidReason | Not an HCC Member |
    And I click on save in Task Slider
    Then I verify task save success message
    And Close the soft assertions
    Examples:
      | status1 | taskId | taskType          | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      ||        | HCC Outbound Call || Created ||          ||            ||                ||               ||            ||          ||           ||