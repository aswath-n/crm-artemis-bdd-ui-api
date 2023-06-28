@editMyTask
Feature: Validate Edit Task for functionality

  @CP-13908 @CP-13908-03 @ui-wf-nj @nj-regression @vidya
  Scenario: Verify Disposition fields in Edit task page for Incomplete contact Record task type
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Incomplete contact Record" task page
    And I will provide following information before creating task
      | taskInfo                 | Test CP-13257|
      | assignee                 | Service AccountOne|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         |Complete|
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DISPOSITION"
    And I verify "disposition" single select drop down value
      | Contact Record Completed   |
      | Contact Record Updated - Incomplete |
      | Unable to Update Contact Record |
    And I will update the following information in edit task page
      | disposition    |Contact Record Completed|
      | reasonForEdit  |Corrected Data Entry|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    Then I verify view page has disposition value
    And Close the soft assertions

  @CP-13908 @CP-13908-08 @ui-wf-nj @nj-regression @vidya
  Scenario: Verify Dispostion fields in Edit task page for Outbound Call task type
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Outbound Call" task page
    And I will provide following information before creating task
      | taskInfo              | Line Break         |
      | assignee              | Service AccountTwo |
      | name                  | Ruslan             |
      | contactReason         | Announcements      |
      | preferredCallBackDate | today              |
      | preferredCallBackTime | 03:28 PM           |
      | preferredPhone        | 1234567890         |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                | Complete |
      | preferredCallBackDate | today    |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DISPOSITION"
    And I verify "disposition" single select drop down value
      | Consumer not reached |
      | Consumer reached     |
      | Dialer Call Needed   |
      | Invalid Phone Number |
    And I will update the following information in edit task page
      | disposition   | Dialer Call Needed   |
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    Then I verify view page has disposition value
    And Close the soft assertions

  @CP-13908 @CP-13908-09 @ui-wf-nj @nj-regression @vidya
  Scenario: Verify Dispostion fields in Edit task page for Review Complaint task type
    Given I logged into CRM with "Supervisor" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Review Complaint" task page
    And I will provide following information before creating task
      | taskInfo       | Test CP-10685      |
      | complaintAbout | CAC (maersk)      |
      | name           | Vidya Mithun       |
      | reason         | Customer Service   |
      | assignee       | Service AccountOne |
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         |Complete|
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DISPOSITION"
    And I verify "disposition" single select drop down value
      | Referred   |
      | Resolved   |
      | Unresolved |
    And I will update the following information in edit task page
      | disposition    |Unresolved|
      | reasonForEdit  |Corrected Data Entry|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    Then I verify view page has disposition value
    And Close the soft assertions

  @CP-12375 @CP-12375-01 @CP-17644 @CP-17644-01 @ui-wf-nj @nj-regression @vidya
  Scenario: Verify mandatory and optional fields in Edit task page for Review Complaint task type
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I navigate to "My Task" page
    And I make sure assignee contains at least one "Review Complaint" task if not I will create task
    And I click task id to get the results in descending order
    And I ensure My task page has at least one task with type "Review Complaint" and I navigate to view task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority         ||
      | status           |OnHold|
      | assignee         ||
      | taskInfo         |ABC|
      | complaintAbout  ||
      | name             ||
      | reason           ||
      | reasonForOnHold  ||
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE"
    And I verify task mandatory fields error message "REASON FOR HOLD"
    And I verify task mandatory fields error message "PRIORITY"
    And I verify task mandatory fields error message "Complaint About"
    And I verify task mandatory fields error message "Name"
    And I verify task mandatory fields error message "Reason"
    And I verify minimum lenght error message "TASK INFORMATION"
    And I scroll up the page
    And I will update the following information in edit task page
      | status         |Complete|
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DISPOSITION"
    And I verify task mandatory fields error message "REASON FOR EDIT"
    And I verify "disposition" single select drop down value
      | Referred   |
      | Resolved   |
      | Unresolved |
    And I verify Complaint About drop down value
      | CAC (maersk) |
      | Carrier       |
      | Exchange      |
      | FFM           |
      | Medicaid      |
      | Other         |
    And I verify Reason drop down value
      | Customer Service         |
      | Customer Service - Agent |
      | Member Portal            |
      | Other                    |
      | Timeliness               |
    And I will update the following information in edit task page
      | status         | Complete             |
      | assignee       | Service AccountOne   |
      | priority       | 2                    |
      | taskInfo       ||
      | disposition    | Resolved             |
      | reasonForEdit  | Corrected Data Entry |
      | complaintAbout | Exchange             |
      | name           | Vidya Mithun         |
      | reason         | Member Portal        |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-12375 @CP-12375-02 @ui-wf-nj @nj-regression @vidya
  Scenario: Verify field length in Edit task page for Review Complaint task type
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I navigate to "My Task" page
    And I make sure assignee contains at least one "Review Complaint" task if not I will create task
    And I click task id to get the results in descending order
    And I ensure My task page has at least one task with type "Review Complaint" and I navigate to view task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority         |1|
      | status           |OnHold|
      | reasonForOnHold  |Missing Information|
      | assignee         |Service AccountOne|
      | taskInfo         |maxlength|
      | complaintAbout   |Exchange|
      | name             |random|
      | reason           |Member Portal|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And Close the soft assertions

  @CP-13257 @CP-13257-03 @CP-10174 @CP-10174-01 @vidya @nj-regression @ui-wf-nj
  Scenario: Verify mandatory and optional fields in Edit task page for Incomplete Contact Record task type and Reason For Hold present on view task page
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I navigate to "My Task" page
    And I make sure assignee contains at least one "Incomplete contact Record" task if not I will create task
    And I click task id to get the results in descending order
    And I ensure My task page has at least one task with type "Incomplete contact Record" and I navigate to view task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority                 |1|
      | status                   |OnHold|
      | reasonForOnHold          |Missing Information|
      | assignee                 |Service AccountTwo|
      | taskInfo                 |maxlength|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-13257 @CP-13257-04 @vidya @nj-regression @ui-wf-nj
  Scenario: Verify mandatory and optional filelds in Edit task page for Incomplete Contact Record task type in NJ-SBE
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I navigate to "My Task" page
    And I make sure assignee contains at least one "Incomplete contact Record" task if not I will create task
    And I click task id to get the results in descending order
    And I ensure My task page has at least one task with type "Incomplete contact Record" and I navigate to view task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority                 ||
      | status                   |OnHold|
      | assignee                 ||
      | taskInfo                 |ABC|
      | reasonForOnHold          ||
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE"
    And I verify task mandatory fields error message "REASON FOR HOLD"
    And I verify task mandatory fields error message "PRIORITY"
    And I verify minimum lenght error message "TASK INFORMATION"
    And I will update the following information in edit task page
      | status         |Complete|
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DISPOSITION"
    And I verify task mandatory fields error message "REASON FOR EDIT"
    And I verify "disposition" single select drop down value
      | Contact Record Completed   |
      | Contact Record Updated - Incomplete |
      | Unable to Update Contact Record     |
    And I scroll up the page
    And I will update the following information in edit task page
      | priority      | 2                        |
      | status        | Complete                 |
      | assignee      | Service AccountOne       |
      | taskInfo      ||
      | disposition   | Contact Record Completed |
      | reasonForEdit | Corrected Data Entry     |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-15914 @CP-15914-05 @CP-23202 @CP-23202-01 @priyal @basha @nj-regression @ui-wf-nj
  Scenario: Verify newly created Review Appeal Form Task with status Escalated
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Review Appeals Form" task page
    And I will provide following information before creating task
      | taskInfo    | maxlength          |
      | assignee    | Service AccountOne |
      | status      | Escalated          |
      | actionTaken ||
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    When I expand the first row in task list
    Then I verify the task details are displayed in my task when expanded
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status      | Complete          |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify "actionTaken" multi select drop down value
      |No Action Taken                  |
      |Outbound Call Successful         |
      |Outbound Call Unsuccessful       |
    And I verify "disposition" single select drop down value
      | IDR Successful   |
      | IDR Unsuccessful |
      | Not a Valid Appeal |
    And I will update the following information in edit task page
      | taskInfo            |Line Break|
      | reasonForEdit       |Corrected Data Entry|
      | actionTaken         |Outbound Call Successful,No Action Taken|
      | disposition         |Not a Valid Appeal|
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-10690 @CP-10690-01 @ui-wf-nj @nj-regression @vidya
  Scenario: Verify mandatory and optional fields in Edit task page for Outbound Call task type
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Outbound Call" task page
    And I will provide following information before creating task
      | assignee              | Service AccountOne              |
      | contactReason         | Announcements,Change of Address |
      | name                  | Roy Johnson                     |
      | preferredCallBackDate | today                           |
      | preferredCallBackTime | 03:28 PM                        |
      | preferredPhone        | 1234567890                      |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority              ||
      | status                | OnHold |
      | assignee              ||
      | taskInfo              | ABC    |
      | contactReason         ||
      | name                  ||
      | preferredCallBackDate ||
      | preferredCallBackTime ||
      | preferredPhone        ||
      | reasonForOnHold       ||
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE"
    And I verify task mandatory fields error message "REASON FOR HOLD"
    And I verify task mandatory fields error message "PRIORITY"
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify task mandatory fields error message "Name"
    And I verify task mandatory fields error message "PREFERRED PHONE"
    And I verify minimum lenght error message "TASK INFORMATION"
    Then I verify Contact Reason drop down value
      | Announcements            |
      | Change of Address        |
      | Complaint                |
      | Courtesy Call Back       |
      | DMI                      |
      | Escalation               |
      | Inbound Document Inquiry |
      | Other                    |
      | Returned Mail            |
    And I will update the following information in edit task page
      | status                | Cancelled           |
      | reasonForCancel       | Created Incorrectly |
      | assignee              | Service AccountOne  |
      | priority              | 2                   |
      | taskInfo              | fsafafafafa         |
      | name                  | random              |
      | contactReason         | Complaint,DMI       |
      | preferredCallBackDate | today               |
      | preferredCallBackTime | 03:28 PM            |
      | preferredPhone        | 1234567890          |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions