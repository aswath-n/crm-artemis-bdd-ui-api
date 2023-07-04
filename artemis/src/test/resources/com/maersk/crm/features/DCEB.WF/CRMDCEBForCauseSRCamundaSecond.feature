Feature: DC-EB For Cause SR Camunda Second

  @CP-39465 @CP-39465-02 @sr-camunda @ruslan
  Scenario Outline: Verify FC District Outreach Task is not created with followed Disposition
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "Badru" and Last Name as "Zack"
    And I link the contact to an existing Case or Consumer Profile
    And I store external case id from Demographic Info Page
    And I store external consumer id from Demographic Info Page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "For Cause SR" service request page
    And I will provide following information before creating task
      | callerName          | CAMUNDSKIY             |
      | memberNameFc        | Vasiliy                |
      | preferredPhone      | 5215215215             |
      | missingInfoRequired | No                     |
      | requestReason       | Dissatisfied           |
      | requestedPlan       | AMERIHEALTH CARITAS DC |
      | districtDisposition | <districtDisposition>  |
    And I click on save button in create service request page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    When I click id of "FC Review" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                      |
      | reasonForEdit | Corrected Data Entry          |
      | disposition   | Sent To District For Approval |
    And I click on save button on task edit page
    And Wait for 250 seconds
    And I click on id of "For Cause SR" in Links section of "Task" page
    Then I verify "For Cause SR" contains : "FC Review" on "SR" link section
    And Close the soft assertions
    Examples:
      | districtDisposition           |
      | Cancellation Request Approved |
      | Cancellation Request Denied   |

  @CP-39465 @CP-39465-03 @sr-camunda @ruslan
  Scenario Outline: Verify FC District 2nd Outreach Task is not created with followed Disposition
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "Jimmie" and Last Name as "King"
    And I link the contact to an existing Case or Consumer Profile
    And I store external case id from Demographic Info Page
    And I store external consumer id from Demographic Info Page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "For Cause SR" service request page
    And I will provide following information before creating task
      | callerName          | CAMUNDSKIY             |
      | memberNameFc        | Vasiliy                |
      | preferredPhone      | 5215215215             |
      | missingInfoRequired | No                     |
      | requestReason       | Dissatisfied           |
      | requestedPlan       | AMERIHEALTH CARITAS DC |
      | districtDisposition |                        |
    And I click on save button in create service request page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    When I click id of "FC Review" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                      |
      | reasonForEdit | Corrected Data Entry          |
      | disposition   | Sent To District For Approval |
    And I click on save button on task edit page
    And Wait for 250 seconds
    And I click on id of "For Cause SR" in Links section of "Task" page
    Then I verify "For Cause SR" contains : "FC Review,FC District Outreach" on "SR" link section
    And I click on edit service request button
    And I will update the following information in edit task page
      | districtDisposition | <districtDisposition> |
      | reasonForEdit       | Corrected Data Entry  |
    And I click on save button on task edit page
    When I click id of "FC District Outreach" in Links section
    Then I verify "FC District Outreach" link section has all the business object linked : "Service Request,Case"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                      |
      | reasonForEdit | Corrected Data Entry          |
      | disposition   | District Reached Successfully |
    And I click on save button on task edit page
    And Wait for 250 seconds
    And I click on id of "For Cause SR" in Links section of "Task" page
    Then I verify "For Cause SR" contains : "FC Review,FC District Outreach,FC Cancellation Request Confirmation" on "SR" link section
    And Close the soft assertions
    Examples:
      | districtDisposition           |
      | Cancellation Request Approved |
      | Cancellation Request Denied   |