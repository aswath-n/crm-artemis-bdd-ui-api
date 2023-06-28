@CreateTask
Feature: Create Task Functionality Check for DC EB Tenant

  @CP-40294 @CP-40294-01 @crm-regression @ozgen @ui-wf-dceb
  Scenario: Verification of Complaint Task on Create Task Page and Task Save and Link Events
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    When I searched customer have First Name as "Janet" and Last Name as "Erdman"
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Complaint" task page
    Then I verify create task fields displayed
    Then I verify task type is selected as "Complaint"
    Then I verify "taskStatus" single select drop down value
      | Complete  |
      | Created   |
      | Escalated |
    Then I verify "taskPriority" single select drop down value
      | 1 |
      | 2 |
      | 3 |
      | 4 |
      | 5 |
    Then I verify "complaintType" single select drop down value
      | Dental         |
      | EB             |
      | MCO            |
      | Prescription   |
      | Provider       |
      | Transportation |
    Then I verify "complaintAbout" single select drop down value
      | Emergent Situation     |
      | maersk                |
      | Other-Ombudsman Office |
    And I will provide following information before creating task
      | taskInfo | t1 |
    And I click on save button on task edit page
    And I verify task mandatory fields error message "REFERRED TO"
    And I verify task mandatory fields error message "COMPLAINT TYPE"
    And I verify task mandatory fields error message "NAME"
    And I verify task mandatory fields error message "INCIDENT DATE"
    And I verify task mandatory fields error message "PREFERRED PHONE"
    And I verify minimum lenght error message "TASK INFORMATION"
    And I will provide following information before creating task
      | taskInfo       | new task   |
      | complaintType  | Dental     |
      | referredTo     | maersk    |
      | name           | Luis       |
      | incidentDate   | today      |
      | preferredPhone | 7892341908 |
    And I click on save button in create task page
    When I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate Event get api for trace id "Created"
    And I will run the Event GET API and get the payload
    Then I verify TASK_SAVE_EVENT for TASK has all the proper data
    And I will check "TASK_SAVE_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "TASK_SAVE_EVENT" publish to DPBI
    When I initiated mars search events api
    And I can provide event information to search event as
      | eventName  |
      | LINK_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 10   | 0    | eventId,desc |
    Then I received "10" events from search event API
    And there is event in search event results for "WF" with values
      | externalId    | internalRefType | externalRefType   |
      | TASKID        | CONSUMER        | TASK              |
    And there is event in search event results for "WF" with values
      | externalId  | internalRefType | externalRefType |
      | TASKID      | CASE            | TASK            |
    And there is event in search event results for "WF" with values
      | externalId  | internalRefType | externalRefType |
      | CONSUMERID  | TASK            | CONSUMER        |
    And there is event in search event results for "WF" with values
      | externalId  | internalRefType  | externalRefType |
      | CASEID      | TASK             | CASE            |
    And I verify 4 events found for "WF" with above values

  @CP-40294 @CP-40294-02 @crm-regression @ozgen @ui-wf-dceb
  Scenario: Verification of Disposition values of Complaint Task on Create Task Page
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Complaint" task page
    And I will provide following information before creating task
      | status       | Complete   |
    Then I verify "disposition" single select drop down value
     | Complaint Invalid            |
     | Complaint Resolved           |
     | Member Satisfied             |
     | Referred to Ombudsman office |

  @CP-41708 @CP-41708-01 @crm-regression @ozgen @ui-wf-dceb
  Scenario: Verification of New Address Correspondence Request Task
    Given I logged into CRM with "Service Tester 2" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I navigate to "New Address Correspondence Request" task page
    Then I Verify "Correspondence Type" multi select DD value in search page "Alliance Enrollment Packet, Case Consumer, DCHF Enrollment Packet"
    And I will provide following information before creating task
      | name                       | Mark Luis                                            |
      | City                       | Fairfax                                              |
      | State                      | Virginia                                             |
      | ARZipCode                  | 20152                                                |
      | outboundCorrespondenceType | Case,Alliance Enrollment Packet,DCHF Reminder Notice |
    And I click on save button on task edit page
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete                |
      | reasonForEdit  | Entered Additional Info |
      | actionTaken    | No Action Needed        |
      | disposition    | Denied                  |
    And I mouse over to +# to verify all selected values are displayed
    Then I verify that outbound correspondence type is not editable and read only
    And I click on save button on task edit page
    And I see "A note is required when the request has been denied." alert message displayed
    And I click on ok button to inactivate user
    And I will provide information to task or SR note component
      | noteValue | String|
    And click on save button present in task or SR note component
    And I click on save button on task edit page

  @CP-41708 @CP-41708-02 @crm-regression @ozgen @ui-wf-dceb
  Scenario: Mandatory field as Correspondence Type dropdown
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I navigate to "New Address Correspondence Request" task page
    And I will provide following information before creating task
      | name      | Mark Luis |
      | City      | Fairfax   |
      | State     | Virginia  |
      | ARZipCode | 20152     |
    And I click on save button on task edit page
    And I verify task mandatory fields error message "OUTBOUND CORRESPONDENCE TYPE"




