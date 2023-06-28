Feature: Validation Task Link and UnLink functionality

  @CP-2141 @CP-2141-01 @CP-647 @CP-647-01 @CP-22824 @CP-22824-01 @CP-1233 @CP-1233-01 @vidya @crm-regression @ui-wf
  Scenario: verify task is linked to case consumer and contact record does not have unlink button in edit task page and consumer name displaed in link section
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "John" and Last Name as "Johnson"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "General" task page
    And I will provide following information before creating task
      | taskInfo                 ||
      | assignee                 ||
    And I click on save button in create task page
    And I navigate to newly created task in Task & Service Request Tab
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    Then I verify the link section has case consumer and contact id information
    Then I verify unlink button is not displayed
    And I will update the following information in edit task page
      | reasonForEdit  | Corrected Data Entry|
    And I click on save button on task edit page
    And I Navigate to Contact Record details page
    Then I verify the link section has case consumer and task information
    And I click on id of "General" in Links section of "Contact Record" page
    And I verify should I navigated to view task page
    And I click on Active Contact widget
    Then I verify the link section has case consumer and task information
    And I click on id of "General" in Links section of "Active Contact Record" page
    And I verify should I navigated to view task page
    When I navigate to Contact Record search
    And I searched customer with Contact Record ID "getFromUI"
    When I click on first Contact Record ID on Contact Record
    Then I verify the link section has case consumer and task information
    And I click on id of "General" in Links section of "Contact Record" page
    And I verify should I navigated to view task page
    And Close the soft assertions

  @CP-2141 @CP-2141-02 @CP-22824 @CP-22824-02 @vidya @crm-regression @ui-wf
  Scenario: verify task is linked to consumer and contact record does not have unlink button in edit task page and consumer name displaed in link section
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "Test" and Last Name as "Parker"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "General" task page
    And I will provide following information before creating task
      | taskInfo                 ||
      | assignee                 ||
    And I click on save button in create task page
    And I navigate to newly created task in Task & Service Request Tab
    And I expend first Task from the list by clicking in Task ID
    And I verify the link ids are displayed
    And I click on edit button on view task page
    Then I verify unlink button is not displayed
    And Close the soft assertions

  @CP-13133 @CP-13133-01 @CP-2141 @CP-2141-03 @CP-22824 @CP-22824-03 @crm-regression @ui-wf @vidya
  Scenario Outline:Verify in task view page all dynamic fields are displayed if we not pass those in request body and consumer name displaed in link section
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Other"
    And Get the task type information of "Correspondence" for project ""
    And I will provide required information to create external task with "<caseId>" "<consumerId>" "<inboundId>" "<taskInfo>" "<triggerDate>"
      ||
    When I initiated external create task api
    And I run the create external task API and check the status code is "200"
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    Then Verify all the dynamic fields of templates are displayed with null value
      |APPLICATION SOURCE|
      |FROM EMAIL        |
      |FROM NAME         |
      |FROM PHONE        |
      |INBOUND CORRESPONDENCE ID|
      |INBOUND CORRESPONDENCE TYPE|
      |INVALID ADDRESS REASON     |
      |NOTIFICATION ID            |
      |OUTBOUND CORRESPONDENCE ID |
      |OUTBOUND CORRESPONDENCE TYPE|
      |RETURNED MAIL REASON        |
    And I click on edit button on view task page
    Then I verify unlink button is not displayed
    And Close the soft assertions
    Examples:
      |projectName|caseId|consumerId|inboundId  |taskInfo|triggerDate|
      ||9190  |21204     |takeFromAPI|CP-13133|currentDate|

  @CP-146 @CP-146-13 @CP-2141 @CP-2141-04 @CP-22824 @CP-22824-04 @vidya @paramita @ui-wf @crm-regression
  Scenario: Verify Link Task to 'Case' in View Task Details, after Save and consumer name displaed in link section
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "General" task page
    And I will provide following information before creating task
      | taskInfo                 |CP146|
      | assignee                 |Service AccountOne|
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "FnCCFZu" and Last Name as "Lnhjhcu"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    And I select 'Link to Case Only' checkbox
    Then I see Link Record Case button get displayed
    When I click on Link Case button
    Then I verify task is linked with Case ID
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I navigate to View Task details
    Then I verify task is linked with Case ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    Then I verify unlink button is displayed
    When I click on unlink button
    And I will check weather case or consumer is unlinked from task
    And I click on save button on task edit page
    And I verify should I navigated to view task page
    Then I verify in view page link component is empty
    And Close the soft assertions

  @CP-146 @CP-146-14 @CP-2141 @CP-2141-05 @CP-22824 @CP-22824-05 @vidya @paramita @ui-wf @crm-regression
  Scenario: Verify Link Task To 'Consumer on Case' after Save and consumer name displaed in link section
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General" task page
    And I will provide following information before creating task
      | taskInfo                 |CP2141|
      | assignee                 |Service AccountOne|
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "FnEIXno" and Last Name as "LnLaKwh"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    Then I see radio button is associated with a Case or Consumer
    When I select Consumer radio button
    Then I see Link Record Consumer button get displayed
    When I click on Link Record Consumer button
    Then I verify task is linked with ConsumerID and CaseID
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I navigate to View Task details
    Then I verify task is linked with ConsumerID and CaseID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    Then I verify unlink button is displayed
    When I click on unlink button
    And I will check weather case or consumer is unlinked from task
    And I click on save button on task edit page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "FnJCwLk" and Last Name as "LnkFNVK"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    Then I see radio button is associated with a Case or Consumer
    When I select Consumer radio button
    Then I see Link Record Consumer button get displayed
    When I click on Link Record Consumer button
    Then I verify task is linked with ConsumerID and CaseID
    And I click on save button on task edit page
    And I verify should I navigated to view task page
    Then I verify task is linked with ConsumerID and CaseID
    And Close the soft assertions

  #AC-13.0
  @CP-146 @CP-146-15 @CP-2141 @CP-2141-06 @CP-968 @CP-968-06 @CP-22824 @CP-22824-06 @vidya @paramita @ui-wf @crm-regression
  Scenario: Verify Link Task To 'Consumer' in View Task Details, after Save and consumer name displaed in link section
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General" task page
    And I will provide following information before creating task
      | taskInfo                 |CP146 and CP2141|
      | assignee                 |Service AccountOne|
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "AllievTDsP" and Last Name as "KautzerJIpJw"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I select the record whose Case ID Blank
    When I click on Link Record Consumer button
    Then I verify task is linked with Consumer ID
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify task is linked with Consumer ID
    And I click on edit button on view task page
    Then I verify unlink button is displayed
    When I click on unlink button
    And I will check weather case or consumer is unlinked from task
    And I click on cancel button on create task type screen
    Then I verify warning popup is displayed with message
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | Task Details |
    And I will check weather case or consumer is unlinked from task
    And I click on cancel button on create task type screen
    Then I verify warning popup is displayed with message
    And I click on continue button on warning message
    Then I verify should I navigated to view task page
    Then I verify task is linked with Consumer ID
    And Close the soft assertions

  @CP-13133 @CP-13133-02 @CP-2141 @CP-2141-07 @CP-968 @CP-968-07 @CP-22824 @CP-22824-07 @crm-regression @ui-wf @vidya
  Scenario Outline:Verify in view task page extra field is not displayed even if we pass that in request body and consumer name displaed in link section
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Other"
    And Get the task type information of "Correspondence" for project ""
    And I will provide required information to create external task with "<caseId>" "<consumerId>" "<inboundId>" "<taskInfo>" "<triggerDate>"
      |External Case ID|
      |Test 1234       |
    When I initiated external create task api
    And I run the create external task API and check the status code is "200"
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I Navigate to view task details page by using taskId which we created from api
    Then Verify all the dynamic fields of templates are displayed with null value
      |APPLICATION SOURCE|
      |FROM EMAIL        |
      |FROM NAME         |
      |FROM PHONE        |
      |INBOUND CORRESPONDENCE ID|
      |INBOUND CORRESPONDENCE TYPE|
      |INVALID ADDRESS REASON     |
      |NOTIFICATION ID            |
      |OUTBOUND CORRESPONDENCE ID |
      |OUTBOUND CORRESPONDENCE TYPE|
      |RETURNED MAIL REASON        |
    And I click on edit button on view task page
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "FnCCFZu" and Last Name as "Lnhjhcu"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    And I select 'Link to Case Only' checkbox
    Then I see Link Record Case button get displayed
    When I click on Link Case button
    Then I verify task is linked with Case ID
    And I verify task is linked with Inbound ID
    And I click on save button on task edit page
    And I verify task mandatory fields error message "REASON FOR EDIT"
    And I select value in "REASON FOR EDIT" drop down
    And I click on save button on task edit page
    And I verify should I navigated to view task page
    Then I verify task is linked with Case ID
    And I verify task is linked with Inbound ID
    And Close the soft assertions
    Examples:
      |projectName|caseId|consumerId|inboundId  |taskInfo|triggerDate|
      ||      ||takeFromAPI|CP 13133|currentDate|

#   refactoring by priyal
  @CP-22014 @CP-22014-02 @CP-22824 @CP-22824-08 @CP-21344 @CP-21344-02 @priyal @vidya @crm-regression @ui-wf
  Scenario Outline: verify SR is linked to consumer and contact record does not have unlink button in edit task page and consumer name displaed in link section
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I verify due in days after close status for SR
    And In search result click on task id to navigate to view page
    And I verify due in days after close status for SR
    And I click on edit service request button
    And I verify due in days after close status for SR
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
      | disposition   |User closed|
    And If any case consumer already linked and unlink that
    And I click on save button on task edit page
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit       |Corrected Data Entry|
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "FnCCFZu" and Last Name as "Lnhjhcu"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    Then I see radio button is associated with a Case or Consumer
    When I select Consumer radio button
    Then I see Link Record Consumer button get displayed
    When I click on Link Record Consumer button
    Then I verify task is linked with ConsumerID and CaseID
    And I click on save button on task edit page
    And I verify should I navigated to view SR page
    Then I verify task is linked with ConsumerID and CaseID
    And Close the soft assertions
    Examples:
      |taskId|taskType |srType        |status |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|contactReason|
      ||         |General Two SR|Closed ||        ||          ||              ||false        ||          ||        ||         ||

  @CP-22676 @CP-22676-01 @CP-29557 @CP-29557-01 @crm-regression @ui-wf-ineb @ruslan @priyal
  Scenario Outline: Validate Manually Link & Unlink a Task to a Service Request
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "DCS Request" task page
    And I will provide following information before creating task
      | taskInfo | Test CP-22676     |
      | assignee | Service TesterTwo |
    And I click on save button in create task page
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout | maersk          |
      | incidentDate   | today            |
      | contactName    | Priyal           |
      | memberName     | Priyal           |
      | preferredPhone | 1234567890       |
      | complaintType  | Customer Service |
      | priority       | 2                |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","Customer Service Complaint","Open","<statusDate>","2","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","true","<consumerFN>","<consumerLN>","<source>","<assignee>","Service TesterTwo","today","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I click on cancel button on task search page
    Then I verify "Customer Service Complaint" contains : "QA Review Complaint" on "SR" link section
    And I click on edit service request button
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I populate required fields to do task search "<taskId>","DCS Request","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","true","<consumerFN>","<consumerLN>","<source>","Service TesterTwo","<createdBy>","today","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And I click task id to get the results in descending order in Task Search
    And I click on Link button in task search
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    Then I verify "Customer Service Complaint" contains : "QA Review Complaint,DCS Request" on "SR" link section
    And I click on id of "DCS Request" in Links section of "Task" page
    Then I verify "DCS Request" contains : "Customer Service Complaint" on "Task" link section
    And I click on id of "Customer Service Complaint" in Links section of "Task" page
    And I click on edit service request button
    And I unlink "DCS Request" from link section
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    Then I verify "Customer Service Complaint" contains : "QA Review Complaint" on "SR" link section
    And I click on id of "QA Review Complaint" in Links section of "Task" page
    Then I verify "QA Review Complaint" contains : "Customer Service Complaint" on "Task" link section
    And Close the soft assertions
    Examples:
      | taskId | taskType|srType| status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      ||         ||        ||          ||            ||                ||               ||            ||          ||           ||

  @CP-22676 @CP-22676-03 @CP-29557 @CP-29557-03 @crm-regression @ui-wf-ineb @ruslan @priyal
  Scenario: Validate user cannot unlink Task/SR when created through Cawemo Workflow
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    When I searched customer have First Name as "NANCY" and Last Name as "GREG"
    When I click the first consumer id from the search results
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout | maersk          |
      | incidentDate   | today            |
      | contactName    | Priyal           |
      | memberName     | Priyal           |
      | preferredPhone | 1234567890       |
      | complaintType  | Customer Service |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I click on edit service request button
    Then I verify unlink button is not displayed for "QA Review Complaint" in link the section
    And I click on cancel button on create task type screen
    And I click on continue button on warning message
    When I click id of "QA Review Complaint" in Links section
    And I click on edit button on view task page
    Then I verify unlink button is not displayed for "Customer Service Complaint" in link the section
    And Close the soft assertions

  @CP-22676 @CP-22676-02 @crm-regression @ui-wf-ineb @ruslan
  Scenario: Validate Manually Link & Unlink a Task to a Service Request on create page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    When I searched customer have First Name as "NANCY" and Last Name as "GREG"
    When I click the first consumer id from the search results
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout | maersk          |
      | incidentDate   | today            |
      | contactName    | Robert           |
      | memberName     | Mladshiy         |
      | preferredPhone | 1234567890       |
      | complaintType  | Customer Service |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I store sr id on edit page
    When I navigate to "Guardianship Form" task page
    And I click on unlink button
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I will search with srId
    And I click on Link button in task search
    And I will provide following information before creating task
      | priority | 4                 |
      | taskInfo | Test CP-22676-02  |
      | assignee | Service TesterTwo |
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    Then I verify "Customer Service Complaint" contains : "QA Review Complaint,Guardianship Form" on "SR" link section
    When I click id of "Guardianship Form" in Links section
    Then I verify "Guardianship Form" contains : "Customer Service Complaint" on "TASK" link section
    And I click on id of "Customer Service Complaint" in Links section of "Task" page
    And I click on edit service request button
    And I unlink "Guardianship Form" from link section
    And I scroll up the page
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    Then I verify "Customer Service Complaint" contains : "QA Review Complaint" on "SR" link section
    And I click on id of "QA Review Complaint" in Links section of "Task" page
    Then I verify "QA Review Complaint" contains : "Customer Service Complaint" on "TASK" link section
    And Close the soft assertions

  @CP-1233 @CP-1233-02 @vidya @crm-regression @ui-wf
  Scenario: verify task is linked to case consumer and contact record does not have unlink button in edit task page and consumer name displaed in link section
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "FnaaiEl" and Last Name as "LnqUbsK"
    And I link the contact to an existing Case or Consumer Profile
    When I navigate to "General" task page
    And I will provide following information before creating task
      | taskInfo                 ||
      | assignee                 ||
    And I click on save button in create task page
    When  I should see following dropdown options for "contact reason" field displayed
      | Complaint - Customer Service |
    And  I choose "Escalated" option for Contact Action field
    And I enter additional comments "First saved reason and action"
    Then I click save button for reason and action
    And I click on the Contact Channel Type "Phone"
    And I enter contact phone number "3334445566"
    Then I select "Program A" as contact program type
    Then I click on the contact dispotions "Complete"
    When I click on the close button on the Header
    And I scroll the Page to the Bottom
    And I click on the Close button in the bottom
    When I navigate to Contact Record search
    And I searched customer with Contact Record ID "getFromUI"
    When I click on first Contact Record ID on Contact Record
    Then I verify the link section has case consumer and task information
    And I click on id of "General" in Links section of "Contact Record" page
    And I verify should I navigated to view task page
    And I click on initiate a contact button
    When I searched customer have First Name as "FnaaiEl" and Last Name as "LnqUbsK"
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I click on the previous created contact record Id
    Then I verify the link section has case consumer and task information
    And Close the soft assertions

  @CP-22015 @CP-22015-03 @CP-37763 @CP-37763-03 @priyal @crm-regression @ui-wf
  Scenario: verify Task is linked to case and consumer with unlink button in edit task page and verify details on UI and API
    When I will get the Authentication token for "BLCRM" in "Tenant Manager"
    And I initiated Business Unit By Project ID via API "6203"
    Then I can verify Business Unit get API response will be "success"
    Then I get the business unit data from TM
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General" task page
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    Then I verify task table record for the task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Complete|
      | reasonForEdit           | Corrected Data Entry |
      | disposition             | User closed|
      | businessUnitAssigneeTo  | Test Blcrm |
    And I click on save button on task edit page
    And I verify the updated information in view task details page
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    Then I verify task table record for the task
    When I navigate to "Task Search" page
    And I will search with taskId
    And I click on search button on task search page
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
      | taskInfo      | Test CP-22015|
    And If any case consumer already linked and unlink that
    And I click on save button on task edit page
    And I verify the updated information in view task details page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | reasonForEdit       |Corrected Data Entry|
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "FnCCFZu" and Last Name as "Lnhjhcu"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    Then I see radio button is associated with a Case or Consumer
    When I select Consumer radio button
    Then I see Link Record Consumer button get displayed
    When I click on Link Record Consumer button
    Then I verify task is linked with ConsumerID and CaseID
    And I click on save button on task edit page
    Then I verify task is linked with ConsumerID and CaseID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    Then I verify unlink button is displayed
    When I click on unlink button
    And I will check weather case or consumer is unlinked from task
    And I click on save button on task edit page
    And Close the soft assertions