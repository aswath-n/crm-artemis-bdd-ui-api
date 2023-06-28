@eventSRCoverVa
Feature: Appeal SR Events For CoverVA

  @CP-18466 @CP-18466-04 @ozgen @events-wf
  Scenario Outline: Verify Link Events Created for an Appeals SR created from UI (outside of Active Contact)
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Appeals SR" service request page
    And I will provide following information before creating task
      | taskInfo                      |  Test CP-18466               |
      | externalApplicationId         |  T5690123                    |
      | documentDueDate               |  today                       |
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "Julia" and Last Name as "Noran"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I will expand the consumer record
    When I click on Link Record Consumer button
    Then I verify task is linked with Consumer ID
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    When I click case consumer search tab
    When I searched customer have First Name as "Julia" and Last Name as "Noran"
    When I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "SR" from "Outside of Contact"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName | correlationId |
      |LINK_EVENT| Open          |

  @CP-18739 @CP-18739-06 @mark @events-wf
  Scenario Outline: Verify Link Events Created for a Complaint SR created from UI
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "Andres" and Last Name as "Cortez"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Complaint SR" service request page
    And I will provide following information before creating task
      | taskInfo              |Test CP-18739|
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "SR" from "Active Contact"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName | correlationId |
      |LINK_EVENT| Open          |

  @CP-18710 @CP-18710-03 @events-wf @ozgen
  Scenario Outline: Verification of Link Events when Review Appeal Task is updated as Completed and sytematically Fair Hearing task is created
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Appeals SR" service request page
    And I will provide following information before creating task
      | taskInfo                      |  Test CP-18527               |
      | externalApplicationId         |  T5690125                    |
      | documentDueDate               |  today                       |
      | myWorkSpaceDate               |  today                       |
      | preferredPhone                |  5189083418                  |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Appeals SR" link section has all the business object linked
    And I click id of "Review Appeal" in Links section
    Then I verify "Review Appeal" link section has all the business object linked
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Complete            |
      | disposition             | Fair Hearing        |
      | actionTaken             | Returned to DMAS    |
      | reasonForEdit           | Corrected Data Entry|
      | dmasReceivedDate        | today               |
      | coverVAReceivedDate     | today               |
      | externalApplicationId   | T5690129            |
      | caseStatus              | New Application     |
      | appealReason            | Other               |
      | businessUnit            | CPU                 |
      | appealCaseSummaryDueDate| today               |
      | appealCaseSummaryStatus | In-Progress         |
      | reviewOutcome           | Processing Delay    |
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "SR" from "Outside of Contact"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName | correlationId |
      |LINK_EVENT| Open          |

  @CP-20608 @CP-20608-01 @CP-15040 @CP-15040-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ruslan
  Scenario Outline: verify task_update_event when Appeals SR status systematically set to Closed when Review Appeal Task is updated as Completed
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Appeals SR" service request page
    And I will provide following information before creating task
      | documentDueDate | today         |
      | myWorkSpaceDate | today         |
      | preferredPhone  | 5189083424    |
    And I click on save button in create service request page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    And I navigate to view SR details page by clicking on sr id
    And I click id of "Review Appeal" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                   | Complete                     |
      | disposition              | <disposition>                |
      | actionTaken              | Admin Resolve                |
      | reasonForEdit            | Corrected Data Entry         |
      | dmasReceivedDate         | today                        |
      | coverVAReceivedDate      | today                        |
      | externalApplicationId    | T5690126                     |
      | caseStatus               | New Application              |
      | appealReason             | Other                        |
      | businessUnit             | CPU                          |
      | appealCaseSummaryDueDate | today                        |
      | appealCaseSummaryStatus  | In-Progress                  |
      | reviewOutcome            | Processing Delay             |
    And I click on save button on task edit page
    And I click id of "Appeals SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "<disposition>"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for SR has all the proper data
    Then I verify TASK_UPDATE_EVENT for Task has all the proper data
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName         | correlationId | disposition      |
      | TASK_UPDATE_EVENT | Task_update   | Withdrawn        |
      | TASK_UPDATE_EVENT | Task_update   | Returned to DMAS |

  @CP-20608 @CP-20608-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ruslan
  Scenario Outline: verify task_update_event when Appeals SR status systematically set to Closed when Fair Hearing Task is updated as Completed
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Appeals SR" service request page
    And I will provide following information before creating task
      | documentDueDate               | today            |
      | myWorkSpaceDate               | today            |
      | preferredPhone                | 5189083429       |
    And I click on save button in create service request page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    And I navigate to view SR details page by clicking on sr id
    And I click id of "Review Appeal" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Complete                     |
      | disposition             | Fair Hearing                 |
      | actionTaken             | Returned to DMAS             |
      | reasonForEdit           | Corrected Data Entry         |
      | dmasReceivedDate        | today                        |
      | coverVAReceivedDate     | today                        |
      | externalApplicationId   | T5690126                     |
      | caseStatus              | New Application              |
      | appealReason            | Other                        |
      | businessUnit            | CPU                          |
      | appealCaseSummaryDueDate| today                        |
      | appealCaseSummaryStatus | In-Progress                  |
      | reviewOutcome           | Processing Delay             |
    And I click on save button on task edit page
    And I click id of "Appeals SR" in Links section
    And I click id of "Fair Hearing" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                                     |
      | reasonForEdit | Corrected Data Entry                         |
      | disposition   | <disposition>                                |
      | interimStatus | Awaiting Decision                            |
    And I click on save button on task edit page
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for SR has all the proper data
    Then I verify TASK_UPDATE_EVENT for Task has all the proper data
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName         | correlationId         | disposition |
      | TASK_UPDATE_EVENT | disposition_abandoned | Abandoned   |