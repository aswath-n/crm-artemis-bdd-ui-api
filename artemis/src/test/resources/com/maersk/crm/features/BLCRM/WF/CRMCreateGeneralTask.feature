@CreateGeneraltask
Feature: Create General Task

  #AC-8.0
  @CP-146 @CP-146-10 @CP-13265 @CP-13265-01 @paramita @ui-wf @crm-regression
  Scenario: Verify Link Task to 'Case', before Save
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General" task page
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    And I verify create link button disappear from link section
    When I search for consumer have First Name as "fnaDgkL" and Last Name as "lnqOguN"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    And I select 'Link to Case Only' checkbox
    Then I see Link Record Case button get displayed
    When I click on Link Case button
    Then I verify task is linked with Case ID
    And I verify case consumer link button is not present in create link list
    And Close the soft assertions

  #AC-9.0
  @CP-146 @CP-146-11 @CP-13265 @CP-13265-02 @paramita @vidya @CoverVA-UI-Regression @ui-wf-coverVA @crm-regression
  Scenario: Verify Link Task To 'Consumer', before Save
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Appeal Final Review" task page
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    And I verify create link button disappear from link section
    When I search for consumer have First Name as "Vidya" and Last Name as "Mithun"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I select the record whose Case ID Blank
    And I will expand the consumer record
    When I click on Link Record Consumer button
    Then I verify task is linked with Consumer ID
    And I verify case consumer link button is not present in create link list
    And Close the soft assertions

  #AC-10.0 #Defect : CP-11944
  @CP-146 @CP-146-12 @CP-13265 @CP-13265-03 @paramita @vidya @ui-wf-ineb @crm-regression
  Scenario: Verify Link Task To 'Consumer on Case' before Save
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Disenrollment" task page
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    And I verify create link button disappear from link section
    When I search for consumer have First Name as "MELODY" and Last Name as "TOMAS"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    Then I see radio button is associated with a Case or Consumer
    When I select Consumer radio button
    Then I see Link Record Consumer button get displayed
    When I click on Link Record Consumer button
    Then I verify task is linked with ConsumerID and CaseID
    And I verify case consumer link button is not present in create link list
    And Close the soft assertions

  #AC-14.0
  @CP-146 @CP-146-16 @paramita @ui-wf @crm-regression
  Scenario Outline: Verify Link more than 1 Search Result to the Task cannot be done
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Address" task page
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "Emma" and Last Name as ""
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I verify link to task more than one search result cannot be done for "<condition>"
    And Close the soft assertions
    Examples:
      |condition           |
      |Case                |
      |Consumer on Case    |

  #AC-14.0
  @CP-146 @CP-146-17 @paramita @ui-wf @crm-regression
  Scenario: Verify Link more than 1 Search Result to the Task cannot be done
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Address" task page
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "Robertlrhik" and Last Name as ""
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I verify link to task more than one search result cannot be done for "Consumer"
    And Close the soft assertions

  @CP-11247 @CP-11247-01 @CP-11248 @CP-11248-01 @CP-18403 @CP-18403-01 @CP-39600 @CP-39600-01 @vidya @crm-regression @ui-wf
  Scenario: Verification of NJ task type create, view and edit functionality in BLCRM
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I navigate to "NJ" task page
    And Get the order of field displayed
    And I will provide following information before creating task
      | taskInfo               | maxlength|
      | assignee               | Service AccountEight|
      | complaintAbout         | CAC (maersk)|
      | reason                 | Customer Service|
      | name                   | Vidya Mithun |
      | externalApplicationId  | Test123 |
      | externalCaseId         | CaseId123 |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I expend first Task from the list by clicking in Task ID
    And Verify the order of fields displayed in view page is same as NJ create task page
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I verify Complaint About drop down value
      | CAC (maersk) |
      | Carrier       |
      | Exchange      |
      | FFM           |
      | Medicaid      |
      | Other         |
    And I will update the following information in edit task page
      | priority         ||
      | status           |OnHold|
      | assignee         ||
      | taskInfo         |ABC|
      | complaintAbout  ||
      | name             ||
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE"
    And I verify task mandatory fields error message "REASON FOR HOLD"
    And I verify task mandatory fields error message "PRIORITY"
    And I verify task mandatory fields error message "Complaint About"
    And I verify task mandatory fields error message "NAME"
    And I verify minimum lenght error message "TASK INFORMATION"
    And I will update the following information in edit task page
      | priority                 |1|
      | status                   |OnHold|
      | reasonForOnHold          |Missing Information|
      | assignee                 |Service AccountOne|
      | taskInfo                 |random|
      | complaintAbout           |Exchange|
      | name                     |random|
      | externalApplicationId    |random|
      | externalCaseId           |random|
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-5774 @CP-5774-01 @Basha @crm-regression @ui-wf
  Scenario: Verify All Fields create task page has all the fields and user able to save the task
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "All Fields" task page
    And I verify All Fields create task page has all the fields
    And I populate required data to create task "Information Request" "9765434567"
    And I click on save button in create task page
    #Then I verify Success message is displayed for task
    And Close the soft assertions

  @CP-5774 @CP-5774-02 @Basha @crm-regression @ui-wf
  Scenario: Verify when user select All field option in task type drop down fields as displayed properly
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General" task page
    And I select "All Fields" option in task type drop down
    Then I verify All Fields create task page has all the fields
    And I will provide following information before creating task
      | actionTaken             | No Action Taken|
      | applicationId           | random|
      | applicationSource       | Phone|
      | appointmentTime         | 03:28 PM|
      | appointmentDate         | today|
      | caseWorkerFirstName     | TagTest|
      | caseWorkerLastName      | Demo|
      | channel                 | Phone|
      | dateOfBirth             | today|
      | fromName                | fromName abc|
      | fromPhone               | 9654345676|
      | fromEmail               | fromemail123@gmail.com|
      | inboundCorrespondenceId | 76545|
      | InbDocType              ||
      | inboundCorrespondenceWorkableFlag| true|
      | informationType         | Consumer Profile Information |
      | invalidAddressReason    | PO Box Closed|
      | newAddressLine1         | newAddLine1 123|
      | newAddressCity          | Austin|
      | newAddressLine2         | newAddLine2 234|
      | newAddressState         | Montana|
      | newAddressZipCode       | 1009|
      | notificationId          | 234567|
      | oldAddressLine1         | oldAddLine1 12|
      | oldAddressCity          | Denver|
      | oldAddressLine2         | oldAddLine2 23|
      | oldAddressState         | Colorado|
      | oldAddressZipCode       | 1010|
      | outBoundCorrespondenceId| 656554|
      | outreachLocation        | Location 2|
      | planChangeReason        | Plan Change Reason 1|
      | planStartDate           | today|
      | planId                  | 87|
      | planName                | Wellcare|
      | preferredCallBackDate   | today|
      | preferredCallBackTime   | 02:20 PM|
      | language                ||
      | preferredPhone          | 8765678765|
      | program                 | CHIP|
      | providerAddressCity     | Canaan|
      | providerCounty          | Albany|
      | providerAddressLine1    | providerAddLine1 345 |
      | providerAddressLine2    | providerAddLine2 654|
      | providerAddressState    | Colorado|
      | providerAddressZipCode  | 20171|
      | providerFirstName       | proFN abc|
      | providerLastName        | proLN abc|
      | providerNpi             | 567895678|
      | providerPhone           | 9678765456|
      | providerStateId         | 1223|
      | contactReason           | Materials Request|
      | requestedNewProviderChkBx| true|
      | urgentAccessToCareChkBx | true|
      | returnedMailReason      | Mailbox Full|
      | requestedNewPlanChkBx   | true|
    And I click on save button in create task page
    #Then I verify Success message is displayed for task
    And Close the soft assertions

  @CP-13908 @CP-13908-01 @vidya @nj-regression @ui-wf-nj
  Scenario: Validate Disposition drop down values in all task types and is this mandatory field in create task page
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I navigate to "Inbound Application Data Entry" task page
    And I will provide following information before creating task
      | taskInfo                 | Test CP-10686|
      | assignee                 | Service AccountTwo|
      | externalApplicationId    | Test 123|
      | externalCaseId           | caseId 123|
      | Status                   | Complete  |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DISPOSITION"
    And I verify "disposition" single select drop down value
      | Completed     |
      | Not Completed |
    And I select "Review Complaint" option in task type drop down
    And I will provide following information before creating task
      | Status                   | Complete  |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DISPOSITION"
    And I verify "disposition" single select drop down value
      | Referred   |
      | Resolved   |
      | Unresolved |
    And I select "Process Inbound Document" option in task type drop down
    And I will provide following information before creating task
      | Status                   | Complete  |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DISPOSITION"
    And I verify "disposition" single select drop down value
      | Resolved   |
      | Unresolved |
    And I select "Returned Mail" option in task type drop down
    And I will provide following information before creating task
      | Status                   | Complete  |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DISPOSITION"
    And I verify "disposition" single select drop down value
      | Resolved   |
      | Unresolved |
    And I select "Verification Document Upload" option in task type drop down
    And I will provide following information before creating task
      | Status                   | Complete  |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DISPOSITION"
    And I verify "disposition" single select drop down value
      | Unable to Upload |
      | Uploaded   |
    And I select "Incomplete contact Record" option in task type drop down
    And I will provide following information before creating task
      | Status                   | Complete  |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DISPOSITION"
    And I verify "disposition" single select drop down value
      | Contact Record Completed   |
      | Contact Record Updated - Incomplete |
      | Unable to Update Contact Record |
    And I select "Outbound Call" option in task type drop down
    And I will provide following information before creating task
      | Status                   | Complete  |
      | contactReason           |Announcements,Change of Address|
      |name                      |Vidya Mithun|
      | preferredCallBackDate  |today|
      | preferredCallBackTime  |03:28 PM|
      | preferredPhone           |1234567890|
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DISPOSITION"
    And I minimize Genesys popup if populates
    And I verify "disposition" single select drop down value
      | Consumer not reached |
      | Consumer reached   |
      | Dialer Call Needed |
      | Invalid Phone Number |
    And I update the dispostion field in task slider as "Invalid Phone Number"
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task
    And Close the soft assertions

  @CP-13907 @CP-13907-01 @vidya @crm-regression @ui-wf
  Scenario: Validate Disposition drop down values in all task types and is this mandatory field in create task page for BLCRM project
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Incomplete Contact Record" task page
    And I will provide following information before creating task
      | Status                   | Complete  |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DISPOSITION"
    And I verify "disposition" single select drop down value
      | Contact Record Completed   |
      | Contact Record Updated - Incomplete |
      | Unable to Update Contact Record |
    And I select "General Two" option in task type drop down
    And I will provide following information before creating task
      | Status                   | Complete  |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DISPOSITION"
    And I verify "disposition" single select drop down value
      | User closed          |
      | Consumer reached     |
      | Consumer not reached |
    And I update the dispostion field in task slider as "Consumer reached"
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task
    And Close the soft assertions

    #Commentted because functionality changed due to CP-17997
  #@CP-15914 @CP-15914-06 @basha @nj-regression @ui-wf-nj
  Scenario: Validate Review Appeal Form Task Permissions to Work - Escalated
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I Verify "Review Appeals Form" task with "Escalated" status not present in task list
    And Close the soft assertions

  #Commentted because functionality changed due to CP-17997
  #@CP-15914 @CP-15914-07 @basha @nj-regression @ui-wf-nj
  Scenario: Validate Initiate button not present for Escalated status
    Given I logged into CRM with "Service Account 6" and select a project "NJ-SBE"
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I Verify "Review Appeals Form" task with "Created" status does have Initiate button
    And I click on initiate randomly
    And I update the task status in task slider as "Escalated"
    And I click on save in Task Slider
    Then I verify navigate back to My Task or Work Queue page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify tasks are does not have initiate button since searched parameter does not have permission to work

  @CP-24419 @CP-24419-03 @crm-regression @ui-wf @ruslan
  Scenario Outline: Validate no error Message is displayed in the create page correct link combination
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I navigate to "<taskType>" task page
    Then I link to "<typeLink>" using consumer as "<firstName>" and Last Name as "<lastName>"
    And I will provide following information before creating task
      | status      | Complete |
      | disposition |User closed|
    Then I click on save button in create task page
    #Then I verify Success message is displayed for task
    And Close the soft assertions
    Examples:
      | taskType                             | typeLink      | firstName      | lastName    |
      | Case Required Link Task              | Case Only     | FnNKPEA        | LneEXsw     |
      | Consumer Required Link Task          | Consumer Only | Vidya          | Mithun      |
      | Case And Consumer Required Link Task | Case Consumer | FntLLJJ        | Lnlmcxd     |

  @CP-25982 @CP-25982-06 @CP-31980 @CP-31980-05 @crm-regression @ui-wf @ruslan
  Scenario Outline: Verify if User role has permission to create SR but not edit
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "General SR 2" service request page
    And I will provide following information before creating task
      | taskInfo | Task Info |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And I verify save task search section is displayed
    And I verify "orange" icon displays on "SR"
    And In search result click on task id to navigate to view page
    Then I should not able see edit option to edit the "sr"
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType       | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      ||          | General SR 2 | Open   ||          ||            ||                || false         ||            ||          ||           ||

  @CP-25982 @CP-25982-07 @crm-regression @ui-wf @ruslan
  Scenario Outline: Verify if User role has permission to edit SR but not create sr
    Given I logged into CRM with "Service Tester 1" and select a project "BLCRM"
    When I Navigate to create task link
    Then I should not able to created "General SR 2" task
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    Then I verify SR id and edit SR button are displayed
    And Close the soft assertions
    Examples:
      | taskId | taskType|srType        | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      ||         | General SR 2 | Open   ||          ||            ||                || false         ||            ||          ||           ||

  @CP-11900 @CP-11900-06 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @kamil
  Scenario: Validate "Due In" calculation for Biz days to account for configured project Holidays on CreateTask
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Appeal Remand" task page
    Then I verify Due In is calculated correct Business days by skipping the Holiday and Weekends
    And Close the soft assertions

  @CP-11900 @CP-11900-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @kamil
  Scenario: Validate "Due In" calculation for Biz days to account for configured project Holidays
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "","","","","today","","","","","","","","","","","","","",""
    And I click on search button on task search page
    And I verify task search results are displayed
    And In search result click on task id to navigate to view page
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Then I verify Due In is calculated correct Business days by skipping the Holiday and Weekends
    And I click on edit button on view task page OR view SR page
    Then I verify Due In is calculated correct Business days by skipping the Holiday and Weekends
    And Close the soft assertions

  @CP-11900 @CP-11900-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @kamil
  Scenario: Validate "Due In" calculation for Biz days to account for configured project Holidays MyTask
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I verify Due In is calculated correct in My task List
    And Close the soft assertions

  @CP-11900 @CP-11900-05 @crm-regression @INEB-UI-Regression @ui-wf-ineb @kamil
  Scenario: Validate "Due In" calculation for Biz days to account for configured project Holidays/Task Search
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "","","","","today","","","","","","","","","","","","","",""
    And I click on search button on task search page
    And I verify task search results are displayed
    And I verify Due In is calculated correct in Task Search result List
    And Close the soft assertions