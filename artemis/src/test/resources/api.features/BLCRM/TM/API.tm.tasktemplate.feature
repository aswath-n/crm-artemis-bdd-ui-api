Feature: API-Tenant Manager: Task Type Controller

  @auxiliaryService @event-api-AS @API-CRM-1400 @API-CRM-1400_1 @API-TM @API-TM-Regression @API-Project-Task-Template @Sujoy @tenantManagerAPI @task-template-api-TM
  Scenario Outline: Create Task Template API
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated Create Task Template API
    When I can provide details with following information to create task template:
      | projectId           |[blank]|
      | templateName        | TestTemplate    |
      | templateDescription | TestTemplate    |
      | createdBy           | Test Automation |
      | taskFieldVO      |[blank]|
    And I can run create task template API
    Then I can verify the newly created Task Template based on projectId "" and taskTemplateId ""
    Examples:
      | projectName |
      |[blank]|
 # --- This validation implemented at UI level. Hence, API is allowing to crate template with empty description --
#  @auxiliaryService @event-api-AS @API-CRM-1400 @API-CRM-1400_2 @API-TM @API-TM-Regression @API-Project-Task-Template @Sujoy @tenantManagerAPI @task-template-api-TM
  Scenario Outline: Create Task Template API with no description
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated Create Task Template API
    When I can provide details with following information to create task template:
      | projectId           |[blank]|
      | templateName        | TestTemplate    |
      | templateDescription |[blank]|
      | createdBy           | Test Automation |
      | taskFieldNames      |[blank]|
    And I can run create task template API
    Then I can verify the newly created Task Template based on projectId "" and taskTemplateId ""
    Examples:
      | projectName |
      |[blank]|

 # --- This validation implemented at UI level. Hence, API is allowing to crate template with empty name --
#  @auxiliaryService @event-api-AS @API-CRM-1400 @API-CRM-1400_3 @API-TM @API-TM-Regression @API-Project-Task-Template @Sujoy @tenantManagerAPI @task-template-api-TM
  Scenario Outline: Verify system will not create Task Template with empty template name using API
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated Create Task Template API
    When I can provide details with following information to create task template:
      | projectId           |[blank]|
      | templateName        |[blank]|
      | templateDescription | TestTemplate    |
      | createdBy           | Test Automation |
      | taskFieldNames      |[blank]|
    And I can run create task template API
    Then I can verify the newly created Task Template based on projectId "" and taskTemplateId ""
    Examples:
      | projectName |
      |[blank]|

## -- AC# 3-10: All field validations are UI level. There is no API level validation for AC#3 - 10. Hence skipping all the related tests. --

#    @API-CRM-1400 @API-CRM-1400_4 @API-TM @API-TM-Regression @API-Project-Task-Template @Sujoy
#    Scenario: Create Task Template API with required fields using API
#        Given I initiated Create Task Template API
#        When I can provide details with following information to create task template:
#            |projectId              |431                     |
#            |templateName           | TestTemplate          |
#            |templateDescription    | TestTemplate          |
#            |createdBy              |Test Automation        |
#            |taskFieldNames         |"Assignee","Created On","Due In","Due Date","Priority","Source","Status","Status Date","Task ID","Task Info","Task Notes","Task Note Created On","Task Note Created By","Type" |
#        And I can run create task template API
#        Then I can verify the newly created Task Template based on projectId "431" and taskTemplateId ""

  #need to make it alpha numeric 50 & 150 respectively
  @auxiliaryService @event-api-AS @API-CRM-1400 @API-CRM-1400_5 @API-TM @API-TM-Regression @API-Project-Task-Template @Sujoy @tenantManagerAPI @task-template-api-TM
  Scenario Outline: Create Task Template API with max length of Task Name and Description
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated Create Task Template API
#    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I can provide details with following information to create task template:
      | projectId           |[blank]|
      | templateName        | 12345678901234567890123456789012345690                                                                                                |
      | templateDescription | 1234567890123456789012345678901234569012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345 |
      | createdBy           |[blank]|
#      | taskFieldNames      |[blank]|
    And I can run create task template API
    Then I can verify the newly created Task Template based on projectId "" and taskTemplateId ""
    Examples:
      | projectName |
      |[blank]|

  @API-CRM-1404 @API-CRM-1404_1 @API-CP-9420-02 @API-CP-9420 @API-TM @API-TM-Regression  @shruti  @task-template-api-TM
  Scenario Outline: Create Task Type API
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I get task template id from project ""
    And I get project permission API by Project ID "" page "0" size "10" and sort "permissionGroupName"
    Given I initiated Create Task Type API
    When I can provide details with following information:
      | projectId      |[blank]|
      | priority       | 1                     |
      | createdBy      | Test Automation       |
      | dueInDays      | 100                   |
      | workingDayFlag | true                  |
      | taskTypeName   | CreateTaskType        |
      | taskTypeDesc   | Create Task Type Desc |
    And I can run create task type API
    Then I can search the Task Type to validate based on projectId "" and taskTypeId ""
    Examples:
      | projectName |
      |[blank]|

  @API-CRM-1404 @API-CRM-1404_2 @API-TM @API-TM-Regression  @shruti  @task-template-api-TM
  Scenario Outline: Create Task Type API with priority 2 and max length of Task Name and Description
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I get project permission API by Project ID "" page "0" size "10" and sort "permissionGroupName"
    And I initiated Create Task Type API
    When I can provide details with following information:
      | projectId      |[blank]|
      | priority       | 1                                                                                                                                                 |
      | createdBy      | Test Automation                                                                                                                                   |
      | dueInDays      | 100                                                                                                                                               |
      | workingDayFlag | true                                                                                                                                              |
      | taskTypeName   | 12345678901234567890123456789012345678901234567890                                                                                                |
      | taskTypeDesc   | 1234567890123456789012345678901234567890123456789067890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890 |
    And I can provide the following group permission types
      | ROUTE_NON_ESCALATED_TASK |
      | ROUTE_ESCALATED_TASK     |
    And I can run create task type API
    Then I can search the Task Type to validate based on projectId "" and taskTypeId ""
    Examples:
      | projectName |
      |[blank]|

  @API-CRM-1404 @API-CRM-1404_3 @API-TM @API-TM-Regression  @shruti  @task-template-api-TM
  Scenario Outline: Create Task Type API with priority 3 and due date type as "Calendar Days"
    Give`` I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Create Task Type API
    When I can provide details with following information:
      | projectId      |[blank]|
      | priority       | 3                     |
      | createdBy      | Test Automation       |
      | dueInDays      | 90                    |
      | workingDayFlag | false                 |
      | taskTypeName   | CreateTaskType2       |
      | taskTypeDesc   | Create Task Type Desc |
    And I can run create task type API
    Then I can search the Task Type to validate based on projectId "" and taskTypeId ""
    Examples:
      | projectName |
      |[blank]|

  @API-CRM-1409 @API-CRM-1409_1 @API-TM @API-TM-Regression  @shruti  @task-template-api-TM
  Scenario Outline: Create Task Type API with all 3 permission groups
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I get project permission API by Project ID "" page "0" size "10" and sort "permissionGroupName"
    And I initiated Create Task Type API
    When I can provide details with following information:
      | projectId      |[blank]|
      | priority       | 1               |
      | createdBy      | Test Automation |
      | dueInDays      | 100             |
      | workingDayFlag | true            |
      | taskTypeName   | {random}        |
      | taskTypeDesc   | {random}        |
    And I can provide the following group permission types
      | ROUTE_NON_ESCALATED_TASK |
      | ROUTE_NON_ESCALATED_TASK |
      | ROUTE_ESCALATED_TASK     |
      | CREATE_AND_EDIT_TASK     |
    And I can run create task type API
    Then I verify created task type details
    Then I can search the Task Type to validate based on projectId "" and taskTypeId ""
    Examples:
      | projectName |
      |[blank]|

  @API-CRM-1409 @API-CRM-1409_2 @API-TM @API-TM-Regression  @shruti  @task-template-api-TM
  Scenario Outline: Associate Mutliple permission groups  to 1 permission group type
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I get project permission API by Project ID "" page "0" size "10" and sort "permissionGroupName"
    And I initiated Create Task Type API
    When I can provide details with following information:
      | projectId      |[blank]|
      | priority       | 1               |
      | createdBy      | Test Automation |
      | dueInDays      | 100             |
      | workingDayFlag | true            |
      | taskTypeName   | {random}        |
      | taskTypeDesc   | {random}        |
    And I can provide the following group permission types
      | ROUTE_NON_ESCALATED_TASK |
      | ROUTE_NON_ESCALATED_TASK |
    And I can run create task type API
    Then I verify created task type details
    Then I can search the Task Type to validate based on projectId "" and taskTypeId ""
    Examples:
      | projectName |
      |[blank]|


  @auxiliaryService @event-api-AS @API-CRM-1409 @API-CRM-1409_3 @API-TM @API-TM-Regression @API-Project-Task-Type @shruti @tenantManagerAPI @task-template-api-TM
  Scenario Outline: Verify Created On & Created By are captured when a new task type is created
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I get project permission API by Project ID "" page "0" size "10" and sort "permissionGroupName"
    And I initiated Create Task Type API
    When I can provide details with following information:
      | projectId      |[blank]|
      | priority       | 1               |
      | createdBy      | Test Automation |
      | dueInDays      | 100             |
      | workingDayFlag | true            |
      | taskTypeName   | {random}        |
      | taskTypeDesc   | {random}        |
    And I can provide the following group permission types
      | ROUTE_ESCALATED_TASK |
      | CREATE_AND_EDIT_TASK |
    And I can run create task type API
    Then I verify created task type details
    Examples:
      | projectName |
      |[blank]|

  @auxiliaryService @event-api-AS @API-CRM-1408 @API-CRM-1408_1 @API-TM @API-TM-Regression @API-Project-Task-Type @Vinuta @tenantManagerAPI @task-template-api-TM
  Scenario Outline: Create Task Type API with multiple templates to verify end date
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I get task template id from project ""
    Given I initiated Create Task Type API
    When I can provide details with following information:
      | projectId      |[blank]|
      | priority       | 3                     |
      | createdBy      | Test Automation       |
      | dueInDays      | 90                    |
      | workingDayFlag | false                 |
      | taskTypeName   | CreateTaskType2       |
      | taskTypeDesc   | Create Task Type Desc |
    And I provide template details to task type:
    And I can run create task type API
    Then I can search the Task Type to validate based on projectId "" and taskTypeId ""
    Examples:
      | projectName |
      |[blank]|

  @auxiliaryService @event-api-AS @API-CRM-1402 @API-CRM-1402_1 @API-TM @API-TM-Regression @API-Project-Task-Template @Vinuta @tenantManagerAPI @task-template-api-TM
  Scenario Outline: Template List API to verify associated Task Types
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I get task template id from project ""
    Given I initiated Create Task Type API
    When I can provide details with following information:
      | projectId      |[blank]|
      | priority       | 3                     |
      | createdBy      | Test Automation       |
      | dueInDays      | 90                    |
      | workingDayFlag | false                 |
      | taskTypeName   | CreateTaskType new    |
      | taskTypeDesc   | Create Task Type Desc |
    And I provide template details to task type:
    And I can run create task type API
    Then I can search the Task Type to validate based on projectId "" and taskTypeId ""
    When I initiate Template List API
    Then I verify associated task type is displayed
    Examples:
      | projectName |
      |[blank]|

  @API-TM-4898 @API-TM-4898_01 @API-TM @API-TM-Regression @API-Project-Task-Type @tenantManagerAPI @task-template-api-TM @shruti
  Scenario Outline: Create SR Task Type
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I get task template id from project ""
    And I get project permission API by Project ID "" page "0" size "10" and sort "permissionGroupName"
    Given I initiated Create Task Type API
    When I can provide details with following information:
      | projectId         |[blank]|
      | priority          | 1                     |
      | createdBy         | Test Automation       |
      | dueInDays         | 100                   |
      | workingDayFlag    | true                  |
      | taskTypeName      | CreateTaskType        |
      | taskTypeDesc      | Create Task Type Desc |
      | serviceRequestInd | true                  |
      | srCategory        | random                |
    And I can run create task type API
    Then I can search the Task Type to validate based on projectId "" and taskTypeId ""
    And I verify Service request details in task type
    Examples:
      | projectName |
      |[blank]|

  @API-CP-9420 @API-CP-9420_01 @API-TM @API-TM-Regression  @shruti  @task-template-api-TM
  Scenario Outline: Verify Task Type is created with the provided ID
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated search task type by project API for ""
    When I run search task type by project API
    And I get project permission API by Project ID "" page "0" size "10" and sort "permissionGroupName"
    And I initiated Create Task Type API
    When I can provide details with following information:
      | projectId      |[blank]|
      | priority       | 1               |
      | createdBy      | Test Automation |
      | dueInDays      | 100             |
      | workingDayFlag | true            |
      | taskTypeName   | {random}        |
      | taskTypeDesc   | {random}        |
      | taskTypeId     |[blank]|
    And I can provide the following group permission types
      | ROUTE_ESCALATED_TASK |
      | CREATE_AND_EDIT_TASK |
    And I can run create task type API
    Then I verify task is created wth given task type id
    Examples:
      | projectName |
      |[blank]|

  @API-CP-9420 @API-CP-9420_03 @API-TM @API-TM-Regression  @shruti  @task-template-api-TM
  Scenario Outline: Verify error message when duplicate task type id
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated search task type by project API for ""
    When I run search task type by project API
    And I get project permission API by Project ID "" page "0" size "10" and sort "permissionGroupName"
    And I initiated Create Task Type API
    When I can provide details with following information:
      | projectId      |[blank]|
      | priority       | 1               |
      | createdBy      | Test Automation |
      | dueInDays      | 100             |
      | workingDayFlag | true            |
      | taskTypeName   | {random}        |
      | taskTypeDesc   | {random}        |
      | taskTypeId     | {existingId}    |
    And I can provide the following group permission types
      | ROUTE_ESCALATED_TASK |
      | CREATE_AND_EDIT_TASK |
    And I can run create task type API for invalid values
    Then I verify error message for duplicate task type id
    Examples:
      | projectName |
      |[blank]|