@viewMyTask
Feature: Edit and View My Task/work Queue/TSR

  # Added logic in common function so it will cover existing scripts validation in edit feature for BLCRM and NJ-SBE
  @CP-13659 @CP-13659-01 @CP-13621 @CP-13621-01 @CP-10174 @CP-10174-02 @vidya @crm-regression @ui-wf
  Scenario: Verifying Reason  For Cancel is present and Reason for Edit and Hold is not Present in view task details page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General" task page
    And I will provide following information before creating task
      | assignee          |Service AccountOne|
    And I click on save button in create task page
    When I navigate to "My Task" page
 #   And I make sure assignee contains at least one "General" task if not I will create task
 #   And I ensure My task page has at least one task with type "General" and I navigate to view task
    And I navigate to newly created task by clicking on TaskID column header
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    Then Wait for 2 seconds
    And I will update the following information in edit task page
      | status              |Cancelled|
      | reasonForCancel     |Created Incorrectly|
      | assignee            |Service AccountTwo|
      | taskInfo            |Test CP-13659|
#      | taskNote            |Test CP-13659|
      | reasonForEdit       |Corrected Data Entry|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

    # Added logic in common function so it will cover for all other status also by using existing scripts in edit feature
  @CP-13659 @CP-13659-02 @CP-13621 @CP-13621-02 @CP-10174 @CP-10174-03 @vidya @crm-regression @ui-wf
  Scenario: Verifying Reason For Cancel and Hold is not present and Reason For Edit is Present in view task details page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General" task page
    And I will provide following information before creating task
      | assignee          |Service AccountOne|
    And I click on save button in create task page
    When I navigate to "My Task" page
 #   And I make sure assignee contains at least one "General" task if not I will create task
 #   And I ensure My task page has at least one task with type "General" and I navigate to view task
    And I navigate to newly created task by clicking on TaskID column header
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status              |Complete|
      | assignee            |Service AccountTwo|
      | taskInfo            |Test CP-13659|
#      | taskNote            |Test CP-13659|
      | reasonForEdit       |Corrected Data Entry|
      | disposition         |User closed|
    And I click on save button on task edit page
#    And I verify Success message is displayed for task update
    And I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-17646 @CP-17646-02 @vidya @crm-regression @ui-wf
  Scenario: Verification of Reason drop down values in Dropdown One task edit page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Dropdowns One" task page
    And I will provide following information before creating task
      | taskInfo          |Test CP-17646|
      | assignee          |Service AccountOne|
      | complaintAbout    |Customer Service|
      | contactReason     |Information Request|
      | reason            |Appeal/IDR|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I verify Reason drop down value
      | Appeal/IDR  |
      | Complaint   |
      | Enrollment  |
    And I will update the following information in edit task page
      | status              |Cancelled|
      | reasonForCancel     |Created Incorrectly|
      | reasonForEdit       |Corrected Data Entry|
      | reason              |Enrollment|
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-17646 @CP-17646-03 @vidya @crm-regression @ui-wf
  Scenario: Verification of Reason drop down values in Dropdown Two task edit page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Dropdowns Two" task page
    And I will provide following information before creating task
      | taskInfo          |Test CP-17646|
      | complaintAbout    |Coverage|
      | contactReason     |Information Request|
      | reason            |Customer Service|
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    When I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I verify Reason drop down value
      | Customer Service         |
      | Customer Service - Agent |
      | Member Portal            |
      | Other                    |
      | Timeliness               |
    And I will update the following information in edit task page
      | status              |Cancelled|
      | reasonForCancel     |Created Incorrectly|
      | reasonForEdit       |Corrected Data Entry|
      | reason              |Member Portal|
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

