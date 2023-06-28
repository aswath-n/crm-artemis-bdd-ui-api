#@ui-wf
Feature: Validation of Multi Tenancy testing for Task and SR

  @multiTenancy @vidya
  Scenario Outline: Save Button functionality
    Given I logged into CRM with "Service Account 1" and select a project "<tenant_1>"
    When I navigate to "<taskType>" task page
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    When I will get the Authentication token for "<tenant_1>" in "CRM"
    And I Initiate Event Get Api for trace_id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify "<eventName>" is generated for "<tenant_1>" Tenant
    And I verify "<eventName>" is not generated for "<tenant_2>" Tenant with "<correlationId>"
    And I verify "<eventName>" is not generated for "<tenant_3>" Tenant with "<correlationId>"
    And I verify "<eventName>" is not generated for "<tenant_4>" Tenant with "<correlationId>"
    Then I verify Task with "<taskTypeId>" is saved in task table present in "<tenant_1>" Tenant
    And I verify Task with "<taskTypeId>" is not saved in task table present in "<tenant_2>" Tenant
    And I verify Task with "<taskTypeId>" is not saved in task table present in "<tenant_3>" Tenant
    And I verify Task with "<taskTypeId>" is not saved in task table present in "<tenant_4>" Tenant
    Then I verify task with "<taskTypeId>" is present in task search for "<tenant_1>"
    And I verify task with "<taskTypeId>" is not present in task search for "<tenant_2>"
    And I verify task with "<taskTypeId>" is not present in task search for "<tenant_3>"
    And I verify task with "<taskTypeId>" is not present in task search for "<tenant_4>"
    And Close the soft assertions
    Examples:
      | eventName       | correlationId |taskType                           |tenant_1|tenant_2|tenant_3|tenant_4|taskTypeId|
      | TASK_SAVE_EVENT | Created       |Follow-up on Appeal                |BLCRM   |CoverVA |NJ-SBE  |IN-EB   |13369     |
      | TASK_SAVE_EVENT | Created       |GCNJ Appeals Acknowledgement Letter|NJ-SBE  |CoverVA |BLCRM   |IN-EB   |15441     |
      | TASK_SAVE_EVENT | Created       |Appeal Withdrawal                  |CoverVA |BLCRM   |NJ-SBE  |IN-EB   |13539     |
      | TASK_SAVE_EVENT | Created       |TBI Report                         |IN-EB   |CoverVA |NJ-SBE  |BLCRM   |15401     |

  @multiTenancy @vidya
  Scenario Outline: Save Button functionality
    Given I logged into CRM with "Service Account 1" and select a project "<tenant_1>"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "","<taskType>","Created","today","","","","","","","","","","","","","","",""
    And I click on search button on task search page
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Cancelled|
      | reasonForCancel         |Created Incorrectly|
    And I click on save button on task edit page
    When I will get the Authentication token for "<tenant_1>" in "CRM"
    And I Initiate Event Get Api for trace_id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify "<eventName>" is generated for "<tenant_1>" Tenant
    And I verify "<eventName>" is not generated for "<tenant_2>" Tenant with "<correlationId>"
    And I verify "<eventName>" is not generated for "<tenant_3>" Tenant with "<correlationId>"
    And I verify "<eventName>" is not generated for "<tenant_4>" Tenant with "<correlationId>"
    Then I verify Task with "<taskTypeId>" is saved in task table present in "<tenant_1>" Tenant
    Then I verify Task with "<taskTypeId>" is not saved in task table present in "<tenant_2>" Tenant
    Then I verify Task with "<taskTypeId>" is not saved in task table present in "<tenant_3>" Tenant
    Then I verify Task with "<taskTypeId>" is not saved in task table present in "<tenant_4>" Tenant
    Then I verify task with "<taskTypeId>" is present in task search for "<tenant_1>"
    And I verify task with "<taskTypeId>" is not present in task search for "<tenant_2>"
    And I verify task with "<taskTypeId>" is not present in task search for "<tenant_3>"
    And I verify task with "<taskTypeId>" is not present in task search for "<tenant_4>"
    And Close the soft assertions
    Examples:
      | eventName         | correlationId |taskType                            |tenant_1|tenant_2|tenant_3|tenant_4|taskTypeId|
      | TASK_UPDATE_EVENT | Task_update   |Follow-up on Appeal                 |BLCRM   |CoverVA |NJ-SBE  |IN-EB   |13369     |
      | TASK_UPDATE_EVENT | Task_update   |GCNJ Appeals Acknowledgement Letter |NJ-SBE  |CoverVA |BLCRM   |IN-EB   |15441     |
      | TASK_UPDATE_EVENT | Task_update   |Appeal Withdrawal                   |CoverVA |BLCRM   |NJ-SBE  |IN-EB   |13539     |
      | TASK_UPDATE_EVENT | Task_update   |TBI Report                          |IN-EB   |CoverVA |NJ-SBE  |BLCRM   |15401     |

  @multiTenancy @vidya
  Scenario Outline: Save Button functionality
    Given I logged into CRM with "Service Account 1" and select a project "<tenant_1>"
    When I navigate to "<taskType>" service request page
    And I click on save button in create service request page
    When I will get the Authentication token for "<tenant_1>" in "CRM"
    And I Initiate Event Get Api for trace_id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify "<eventName>" is generated for "<tenant_1>" Tenant for SR with "<taskTypeId>"
    And I verify "<eventName>" is not generated for "<tenant_2>" Tenant with "<correlationId>"
    And I verify "<eventName>" is not generated for "<tenant_3>" Tenant with "<correlationId>"
    And I verify "<eventName>" is not generated for "<tenant_4>" Tenant with "<correlationId>"
    Then I verify Task with "<taskTypeId>" is saved in task table present in "<tenant_1>" Tenant
    And I verify Task with "<taskTypeId>" is not saved in task table present in "<tenant_2>" Tenant
    And I verify Task with "<taskTypeId>" is not saved in task table present in "<tenant_3>" Tenant
    And I verify Task with "<taskTypeId>" is not saved in task table present in "<tenant_4>" Tenant
    Then I verify task with "<taskTypeId>" is present in task search for "<tenant_1>"
    And I verify task with "<taskTypeId>" is not present in task search for "<tenant_2>"
    And I verify task with "<taskTypeId>" is not present in task search for "<tenant_3>"
    And I verify task with "<taskTypeId>" is not present in task search for "<tenant_4>"
    And Close the soft assertions
    Examples:
      | eventName       | correlationId |taskType               |tenant_1|tenant_2|tenant_3|tenant_4|taskTypeId|
      | TASK_SAVE_EVENT | Open          |General Service Request|BLCRM   |CoverVA |NJ-SBE  |IN-EB   |13241     |
      | TASK_SAVE_EVENT | Open          |Complaint SR           |CoverVA |BLCRM   |NJ-SBE  |IN-EB   |13495     |
      | TASK_SAVE_EVENT | Open          |HCC Outbound Call SR   |IN-EB   |CoverVA |NJ-SBE  |BLCRM   |15414     |

  @multiTenancy @vidya
  Scenario Outline: Save Button functionality
    Given I logged into CRM with "Service Account 1" and select a project "<tenant_1>"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "","<taskType>","Open","today","","","","","","","","","","","","","","",""
    And I click on search button on task search page
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will update the following information in edit task page
      | status        |Closed              |
      | reasonForEdit |Corrected Data Entry|
      | disposition   |<disposition>       |
    And I click on save button on task edit page
    When I will get the Authentication token for "<tenant_1>" in "CRM"
    And I Initiate Event Get Api for trace_id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify "<eventName>" is generated for "<tenant_1>" Tenant for SR with "<taskTypeId>"
    And I verify "<eventName>" is not generated for "<tenant_2>" Tenant with "<correlationId>"
    And I verify "<eventName>" is not generated for "<tenant_3>" Tenant with "<correlationId>"
    And I verify "<eventName>" is not generated for "<tenant_4>" Tenant with "<correlationId>"
    Then I verify Task with "<taskTypeId>" is saved in task table present in "<tenant_1>" Tenant
    Then I verify Task with "<taskTypeId>" is not saved in task table present in "<tenant_2>" Tenant
    Then I verify Task with "<taskTypeId>" is not saved in task table present in "<tenant_3>" Tenant
    Then I verify Task with "<taskTypeId>" is not saved in task table present in "<tenant_4>" Tenant
    Then I verify task with "<taskTypeId>" is present in task search for "<tenant_1>"
    And I verify task with "<taskTypeId>" is not present in task search for "<tenant_2>"
    And I verify task with "<taskTypeId>" is not present in task search for "<tenant_3>"
    And I verify task with "<taskTypeId>" is not present in task search for "<tenant_4>"
    And Close the soft assertions
    Examples:
      | eventName         | correlationId |taskType               |tenant_1|tenant_2|tenant_3|tenant_4|taskTypeId|disposition      |
      | TASK_UPDATE_EVENT | Task_update   |General Service Request|BLCRM   |CoverVA |NJ-SBE  |IN-EB   |13241     |General SR Closed|
      | TASK_UPDATE_EVENT | Task_update   |Complaint SR           |CoverVA |BLCRM   |NJ-SBE  |IN-EB   |13495     |Resolved         |
      | TASK_UPDATE_EVENT | Task_update   |HCC Outbound Call SR   |IN-EB   |CoverVA |NJ-SBE  |BLCRM   |15414     |Invalid          |
