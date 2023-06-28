Feature: Initiate Functionality oF My Task Second

  @CP-24419 @CP-24419-01 @crm-regression @ui-wf @ruslan
  Scenario Outline: Validate error message displayed if task is not link to Case | Consumer | Case and Consumer
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When I navigate to "<taskType>" task page
    When I update status to "Complete" on task
    Then I validate the system displays an Error Message: "<errorMessage>"
    And I will provide following information before creating task
      | assignee | Service AccountEight |
    Then I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    When I update status to "Complete" on task
    Then I validate the system displays an Error Message: "<errorMessage>"
    And I click on save button on task edit page
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    And Verify task slider is displayed
    When I update status to "Complete" on task
    And I click on save in Task Slider
    Then I validate the system displays an Error Message: "<errorMessage>"
    And Close the soft assertions
    Examples:
      | taskType                             | errorMessage                                                                 |
      | Case Required Link Task              | A link to a Case is required before this task can be completed.              |
      | Consumer Required Link Task          | A link to a Consumer is required before this task can be completed.          |
      | Case And Consumer Required Link Task | A link to a Case and Consumer is required before this task can be completed. |

  @CP-24419 @CP-24419-02 @crm-regression @ui-wf @ruslan
  Scenario Outline: Validate error message displayed if task link to Case | Consumer | Case and Consumer incorrect combination
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When I navigate to "<taskType>" task page
    Then I link to "<typeLink>" using consumer as "<firstName>" and Last Name as "<lastName>"
    When I update status to "Complete" on task
    Then I validate the system displays an Error Message: "<errorMessage>"
    And I will provide following information before creating task
      | assignee | Service AccountEight |
    Then I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    When I update status to "Complete" on task
    Then I validate the system displays an Error Message: "<errorMessage>"
    And I click on save button on task edit page
  #  Then I verify Success message is displayed for task
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    And Verify task slider is displayed
    When I update status to "Complete" on task
    And I click on save in Task Slider
    Then I validate the system displays an Error Message: "<errorMessage>"
    And Close the soft assertions
    Examples:
      | taskType                             | typeLink      | firstName      | lastName    | errorMessage                                                                 |
      | Case Required Link Task              | Consumer Only | AlexandroQexke | HellerNiYAx | A link to a Case is required before this task can be completed.              |
      | Consumer Required Link Task          | Case Only     | Johnathan      | Harvey      | A link to a Consumer is required before this task can be completed.          |
      | Case And Consumer Required Link Task | Case Only     | Johnathan      | Harvey      | A link to a Case and Consumer is required before this task can be completed. |
      | Case And Consumer Required Link Task | Consumer Only | AlexandroQexke | HellerNiYAx | A link to a Case and Consumer is required before this task can be completed. |

  @CP-24419 @CP-24419-05 @crm-regression @ui-wf @ruslan
  Scenario Outline: Validate no error message is displayed in the task slider correct link combination
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When I navigate to "<taskType>" task page
    Then I link to "<typeLink>" using consumer as "<firstName>" and Last Name as "<lastName>"
    And I will provide following information before creating task
      | assignee | Service AccountEight |
    Then I click on save button in create task page
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status            | Complete               |
      | disposition       | User closed            |
      | actionTakenSingle | Did Not Reach Consumer |
    And I click on save in Task Slider
    And Close the soft assertions
    Examples:
      | taskType                             | typeLink      | firstName      | lastName    |
      | Case Required Link Task              | Case Only     | Alexandre      | Dare        |
      | Consumer Required Link Task          | Consumer Only | AlexandroQexke | HellerNiYAx |
      | Case And Consumer Required Link Task | Case Consumer | Alexandre      | Dare        |

#  refactor by priyal
  @CP-10957 @CP-10957-01 @priyal @crm-regression @ui-wf
  Scenario: Validate Contact Record Details - NOT Linked to Contact Record Details Navigation task
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I minimize Genesys popup if populates
    When I navigate to "Contact Record Details Navigation" task page
    And I will provide following information before creating task
      | taskInfo | Line Break           |
      | assignee | Service AccountEight |
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "Janet" and Last Name as "Hagenes"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    Then I see radio button is associated with a Case or Consumer
    When I select Consumer radio button
    Then I see Link Record Consumer button get displayed
    When I click on Link Record Consumer button
    And I click on save button in create task page
   # Then I verify Success message is displayed for task
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    And I click on initiate randomly
    Then Verify task slider is displayed
    Then I verify the below task details are displayed in my task
    And I will update the following information in task slider
      | status      | Complete    |
      | disposition | User closed |
      | noteValue   | random      |
    And I click on save in Task Slider
#    Then I verify task save success message
    And Close the soft assertions

  @CP-10957 @CP-10957-02 @priyal @crm-regression @ui-wf
  Scenario: Validate Contact Record Details - Linked to Contact Record Details Navigation task dssdf
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I navigate to "Contact Record Details Navigation" task page
    And I will provide following information before creating task
      | taskInfo | Line Break           |
      | assignee | Service AccountEight |
    And I click on save button in create task page
    And I click on the cancel button in the bottom
    And I click on the continue button on warning pop up
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    And I click on initiate randomly
    Then Verify task slider is displayed
    And I navigate to Contact Details
    Then I verify the link section has case consumer and task information
    And I will update the following information in task slider
      | status      | Complete    |
      | disposition | User closed |
    And I click on save in Task Slider
    Then I verify task save success message
    And Close the soft assertions

  @CP-10957 @CP-10957-03 @priyal @crm-regression @ui-wf
  Scenario: Validate Contact Record Details - NOT Linked to Contact Record Details Navigation task
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I minimize Genesys popup if populates
    When I navigate to "Contact Record Details Navigation" task page
    And I will provide following information before creating task
      | taskInfo | Line Break |
      | assignee ||
    And I click on save button in create task page
   # Then I verify Success message is displayed for task
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I click on initiate randomly
    Then Verify task slider is displayed
    Then I verify the below task details are displayed in my task
    Then I verify in view page link component is empty
    And I will update the following information in task slider
      | status          | Cancel         |
      | reasonForCancel | Duplicate Task |
    And I click on save in Task Slider
    Then I verify task save success message
    And Close the soft assertions

  @CP-25501 @CP-25501-01 @crm-regression @ui-wf-ineb @ruslan
  Scenario: IN-EB: Validate user is not able to cancel task if task created systematically
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    When I searched customer have First Name as "NANCY" and Last Name as "JALEEL"
    When I expand the record to navigate Case Consumer Record from manual view
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout | maersk          |
      | incidentDate   | today            |
      | contactName    | Ruslan           |
      | memberName     | Vasya            |
      | preferredPhone | 1234567890       |
      | complaintType  | Customer Service |
    And I click on save button in create service request page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I click on id of "QA Review Complaint" in Links section of "SR" page
    And I click on edit button on view task page
    Then I verify "task-status" field does not contains value on "edit" page
      | Cancelled |
      | Cancel    |
    And I will update the following information in edit task page
      | assignee | Service TesterTwo |
    And I click on save button on task edit page
    When If any In Progress task present then update that to Cancelled
    When I will click on back arrow on view task page
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    Then I navigate to newly created task in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    Then I verify "taskStatus" field does not contains value on "task slider" page
      | Cancelled |
      | Cancel    |
    And I will update the following information in task slider
      | status      | Complete           |
      | actionTaken | Created Evaluation |
      | disposition | Complaint Invalid  |
    And I click on save in Task Slider
#    Then I verify task save success message
    And Close the soft assertions

  @CP-25066 @CP-25066-02 @CP-25065 @CP-25065-02 @crm-regression @INEB-UI-Regression @ui-wf-ineb @vidya
  Scenario Outline: Validate User is not able to save task from slider when dispostion is invalid and invalid reason field is empty
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I will get the Authentication token for "IN-EB" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status      | Complete        |
      | actionTaken | No Action Taken |
      | disposition | Invalid         |
    And I click on save in Task Slider
    Then Verify I will see error message "Please correct the Disposition or provide the Invalid Reason on the HCC Outbound Call Task"
    And I will update the following information in task slider
      | disposition | Needs Plan Selection |
    And I click on save in Task Slider
    And Close the soft assertions
    Examples:
      | taskId | taskType          | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      || HCC Outbound Call || Created ||          ||            ||                || false         ||            ||          ||           ||

  @CP-28998 @CP-28998-03 @priyal @crm-regression @ui-wf
  Scenario Outline: Verify Systematically Closed Indicator on Task Type Configuration on TaskSlider
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click on initiate button in task search page
    Then Verify task slider is displayed
    Then I verify "taskStatus" single select drop down value in slider
      | In Progress |
      | Escalated   |
      | Cancel      |
    And I click on save in Task Slider
    And Close the soft assertions
    Examples:
      | taskId | taskType                 | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      || Member Matching Research || Created ||          ||            ||                ||               ||            ||          ||           ||

  @CP-11900 @CP-11900-04 @crm-regression @ui-wf @kamil
  Scenario: Validate "Due In" calculation for Biz days to account for configured project Holidays Work Queue
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    Then I verify Due In is calculated correct Business days by skipping the Holiday and Weekends for Task Slider
    And Close the soft assertions