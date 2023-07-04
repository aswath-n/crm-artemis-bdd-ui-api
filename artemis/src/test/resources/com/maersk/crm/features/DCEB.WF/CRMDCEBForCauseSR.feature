Feature: DC-EB For Cause SR

  @CP-38932 @CP-38932-01 @CP-38934 @CP-38934-01 @CP-39320 @CP-39320-01 @CP-42577 @CP-42577-01 @crm-regression @ui-wf-dceb @ruslan
  Scenario: Create FC Review Task and link to FC Cause SR task along with case & consumer
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "Alexandra" and Last Name as "McClure"
    And I link the contact to an existing Case or Consumer Profile
    And I store external case id from Demographic Info Page
    And I store external consumer id from Demographic Info Page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
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
    Then I verify "For Cause SR" link section has all the business object linked : "Task,Case,Contact Record"
    When I click id of "FC Review" in Links section
    Then I verify "FC Review" link section has all the business object linked : "Service Request,Case"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                  |
      | reasonForEdit | Corrected Data Entry      |
      | disposition   | Invalid For Cause Request |
    And I click on save button on task edit page
    And I click on id of "For Cause SR" in Links section of "Task" page
    When I click id of "Invalid FC Member Notification" in Links section
    Then I verify "Invalid FC Member Notification" link section has all the business object linked : "Service Request,Case"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | Consumer Not Reached |
    And I click on save button on task edit page
    And I click on id of "For Cause SR" in Links section of "Task" page
    And I store sr id on edit page
    When I will get the Authentication token for "DC-EB" in "CRM"
    Then I verify the sr status is updated to close and disposition is set to "Invalid For Cause Request"
    And I initiated bpm process get api by service request id for process name "DCEB_For_Cause_SR"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-40642 @CP-40642-01 @crm-regression @ui-wf-dceb @ruslan
  Scenario: Create FC Missing Information Task and link to FC Cause SR task along with case & consumer
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "Barone" and Last Name as "Richard"
    And I link the contact to an existing Case or Consumer Profile
    And I store external case id from Demographic Info Page
    And I store external consumer id from Demographic Info Page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "For Cause SR" service request page
    And I will provide following information before creating task
      | callerName          | Kaspiyskiy             |
      | memberNameFc        | Vasya                  |
      | preferredPhone      | 5215215215             |
      | missingInfoRequired | Yes                    |
      | requestReason       | Dissatisfied           |
      | requestedPlan       | AMERIHEALTH CARITAS DC |
    And I click on save button in create service request page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    When I click id of "FC Missing Information" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | MI Received          |
    And I click on save button on task edit page
    And I click on id of "For Cause SR" in Links section of "Task" page
    Then I verify the "MISSING INFO REQUIRED" updated to "NO"
    Then I verify "For Cause SR" contains : "FC Missing Information,FC Review" on "SR" link section

  @CP-39096 @CP-39096-01 @CP-39096-02 @CP-39096-03 @crm-regression @ui-wf-dceb @kamil
  Scenario: Verify manager should be able to create the For Cause SR with the following fields
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I navigate to "For Cause SR" service request page
    Then I verify that update all and cancel sr checkboxes are displayed
    And Wait for 3 seconds
    Then I verify text box Date and Time field value and error message for following fields
      | CALLER NAME |
    And I will provide following information before creating task
      | preferredPhone          | 5215215215             |
      | missingInfoRequired     | Yes                    |
      | requestReason           | Dissatisfied           |
      | requestedPlan           | AMERIHEALTH CARITAS DC |
      | lockedInPolicyExplained | Yes                    |
    Then I verify "requestReason" single select drop down value
      | Dissatisfied                                         |
      | Dentist Does Not Cover Services                      |
      | Dentist Does Not Participate With MCO                |
      | To Maintain Family Unity                             |
      | Hospital/Facility Does Not Accept MCO                |
      | Member Reports That They Were Unaware Of Plan Change |
      | MCO Does Not Cover Medical Supplies                  |
      | MCO Does Not Cover Prescriptions                     |
      | MCO Does Not Cover Procedures/Services               |
      | PCP Is No Longer Accepting New Patients              |
      | PCP Does Not Participate With MCO                    |
      | Specialist Is No Longer Accepting New Patients       |
      | Specialist Does Not Participate With MCO             |
    Then I verify "districtDisposition" single select drop down value
      | Approved                      |
      | Cancellation Request Approved |
      | Cancellation Request Denied   |
      | Denied                        |
      | Pending                       |
      | Sent To MCO                   |
    Then I verify that update all and cancel sr checkboxes are displayed
    And I click on save button in create service request page
    And Close the soft assertions

  @CP-39466 @CP-39466-02 @crm-regression @ui-wf-dceb @ruslan
  Scenario Outline: Close For Cause SR after the FC Member Notification task completed
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "Alexander" and Last Name as "Hansen"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "For Cause SR" service request page
    And I will provide following information before creating task
      | callerName          | Kutakberdiev           |
      | memberNameFc        | Ishakman               |
      | preferredPhone      | 5215215215             |
      | missingInfoRequired | No                     |
      | requestReason       | Dissatisfied           |
      | requestedPlan       | AMERIHEALTH CARITAS DC |
    And I click on save button in create service request page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I click on edit service request button
    And I scroll down the page
    And I will update the following information in edit task page
      | districtDisposition | <districtDisposition> |
      | reasonForEdit       | Corrected Data Entry  |
    And I click on save button on task edit page
    When I click id of "FC Member Notification" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                       |
      | reasonForEdit | Corrected Data Entry           |
      | disposition   | <disposition> |
    And I click on save button on task edit page
    And I click on id of "For Cause SR" in Links section of "Task" page
    And I store sr id on edit page
    When I will get the Authentication token for "DC-EB" in "CRM"
    Then I verify the sr status is updated to close and disposition is set to "Complete"
    And I initiated bpm process get api by service request id for process name "DCEB_For_Cause_SR"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | districtDisposition | disposition                    |
#      | Approved            | Consumer Notified Successfully |
      | Denied              | Consumer Not Reached           |

  @CP-39466 @CP-39466-03 @crm-regression @ui-wf-dceb @ruslan
  Scenario Outline: Close For Cause SR after the FC Cancellation Request Confirmation task is completed
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "Baby" and Last Name as "One"
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
    And I click on edit service request button
    And I scroll up the page
    And I will update the following information in edit task page
      | districtDisposition | <districtDisposition> |
      | reasonForEdit       | Corrected Data Entry  |
    And I click on save button on task edit page
    When I click id of "FC Cancellation Request Confirmation" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | Consumer Not Reached |
    And I click on save button on task edit page
    And I click on id of "For Cause SR" in Links section of "Task" page
    And I store sr id on edit page
    Then I verify the "DISTRICT DISPOSITION" updated to "Cancellation Request Approved"
    When I will get the Authentication token for "DC-EB" in "CRM"
    Then I verify the sr status is updated to close and disposition is set to "Complete"
    And I initiated bpm process get api by service request id for process name "DCEB_For_Cause_SR"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | districtDisposition           |
      | Cancellation Request Approved |

  @CP-39466 @CP-39466-03-1 @crm-regression @ui-wf-dceb @ruslan
  Scenario Outline: Remain For Cause SR status Open after the FC Cancellation Request Confirmation task is completed
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
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit       | Corrected Data Entry  |
      | districtDisposition | <districtDisposition> |
    And I click on save button on task edit page
    When I click id of "FC Cancellation Request Confirmation" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | Consumer Not Reached |
    And I click on save button on task edit page
    And I click on id of "For Cause SR" in Links section of "Task" page
    Then I verify the "DISTRICT DISPOSITION" updated to "Cancellation Request Denied"
    Then I verify the "STATUS" updated to "Open"
    And Close the soft assertions
    Examples:
      | districtDisposition           |
      | Cancellation Request Denied   |

  @CP-39466 @CP-39466-04 @crm-regression @ui-wf-dceb @ruslan
  Scenario: Close For Cause SR after the For Cause Cancel SR checkbox was marked true
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "BarttestBeka" and Last Name as "HayestestBeka"
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
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
      | cancelSR      | true                 |
    And I click on save button on task edit page
    And I store sr id on edit page
    When I will get the Authentication token for "DC-EB" in "CRM"
    Then I verify the sr status is updated to close and disposition is set to "For Cause Request Cancelled"
    And I initiated bpm process get api by service request id for process name "DCEB_For_Cause_SR"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions