Feature: For Cause SR Flow Events for DC-EB

  @CP-39320 @CP-39320-02 @ruslan @events-wf
  Scenario Outline: verify TASK_UPDATE_EVENT when SR Status updated to closed systematically
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "Barone" and Last Name as "Richard"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "For Cause SR" service request page
    And I will provide following information before creating task
      | callerName          | Kaspiyskiy             |
      | memberNameFc        | Vasya                  |
      | preferredPhone      | 5215215215             |
      | missingInfoRequired | No                     |
      | requestReason       | Dissatisfied           |
      | requestedPlan       | AMERIHEALTH CARITAS DC |
    And I click on save button in create service request page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    When I click id of "FC Review" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                  |
      | reasonForEdit | Corrected Data Entry      |
      | disposition   | Invalid For Cause Request |
    And I click on save button on task edit page
    And I click on id of "For Cause SR" in Links section of "Task" page
    When I click id of "Invalid FC Member Notification" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | Consumer Not Reached |
    And I click on save button on task edit page
    And I click on id of "For Cause SR" in Links section of "Task" page
    And I store sr id on edit page
    When I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate Event get api for trace id "<eventName>"
    And I will run the Event GET API and get the payload
    And I verify TASK_UPDATE_EVENT for SR has all the proper data
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    And Close the soft assertions
    Examples:
      | eventName       |
      | TASK_UPDATE_EVENT |