@tm-events
Feature: API-Tenant Manager: Events

  @CP-653 @CP-653-1 @CP-1909 @CP-1909-01 @CP-4746 @CP-4746-01 @Vidya @events @events_smoke_level_one @events_smoke_level_two
  Scenario Outline: Validation of BUSINESS_UNIT_SAVE_EVENT payload
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit details page
    When I populate data on business unit details page "<businessUnitName>","<Desc>","<startDate>","<endDate>"
    And I click on Save button on business unit details page
    Then I verify Business Unit Successfully Created message is displayed and I go back to Business Unit List page
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify that payload contains business unit details which I recently created
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | businessUnitName | Desc     | startDate | endDate | eventName                | correlationId    | projectName |
      | {random}         | {random} | today     | today   | BUSINESS_UNIT_SAVE_EVENT | businessUnitName |[blank]|

  @CP-739 @CP-739-1 @CP-4746 @CP-4746-02 @CP-3411 @CP-3411-01 @Vidya @events @events_smoke_level_two
  Scenario Outline: Validation of TEAM_SAVE_EVENT payload
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit list  page
    And I check whether it has business unit, if not, I will create business unit "<buName>","<buDesc>","<buStartDate>","<buEndDate>"
    When I navigate to team details page
    When I populate data on team details page "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    And I click on Save button on team details page
    Then I verify Team Successfully Created message is displayed and I go back to Team List page
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify that payload contains team details which I recently created
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Examples:
      | buName   | buDesc | buStartDate | buEndDate | teamName | teamDesc | teamStartDate | teamEndDate | teamBU | eventName       | correlationId | projectName |
      | {random} |[blank]| today       | +5        | {random} | {random} | today         | today       |[blank]| TEAM_SAVE_EVENT | teamName      |[blank]|


  @CP-3564 @CP-3564-1 @CP-4746 @CP-4746-03 @Vidya @events @events_smoke_level_two
  Scenario Outline: Validation of TEAM_USER_SAVE_EVENT payload
    Given I logged into Tenant Manager and set the project context "project" value "SelectBLCRMConfig"
    When I click on Team icon from left menu bar
    And I check whether it has team value, if not, I will create a team "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    And I verify Add User button on User List section is enabled
    When I click on Add User button
    And I select any dropdown value other remaining fields get autopopulated
    And I click on team user save button
    Then I see single user is associated with a Team
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify that payload contains team user details which I recently created
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU | eventName            | correlationId  | projectName |
      | {random} | {random} | today         | +6          |[blank]| TEAM_USER_SAVE_EVENT | supervisorFlag |[blank]|

  @CP-1070 @CP-1070-01 @paramita @events
  Scenario Outline: Validation of projectStatusId on Project SAVE Event
    Given I logged into Tenant Manager Project list page
    When I click on Create a New Project
    And I will enter "<prjName>","<prjState>","<prgName>","<contractId>","<stateAgencyName>","<startDate>","<endDate>","<goLiveDate>","<provStatus>","<timeZone>" fields values
    And I click on save button on project details page
    Then I verify success message is displayed for project creation and is on same page
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify that payload contains projectStatusId details on Project Update or Save Event
    Examples:
      | prjName  | prjState | prgName  | contractId | stateAgencyName | startDate | endDate | goLiveDate | provStatus | timeZone | correlationId | projectName |
      | {random} | AZ       | {random} | {random}   | {random}        | today     | +2      | +1         | Active     | Eastern  | projectName   |[blank]|

  @CP-1070 @CP-1070-02 @paramita @events
  Scenario Outline: Validation of projectStatusId on Project Update Event
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I will enter "<prjName>","<prjState>","<prgName>","<contractId>","<stateAgencyName>","<startDate>","<endDate>","<goLiveDate>","<provStatus>","<timeZone>" fields values
    And I click on save button on project details page
    Then I verify success message is displayed for project creation and is on same page
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify that payload contains projectStatusId details on Project Update or Save Event
    Examples:
      | prjName | prjState | prgName | contractId | stateAgencyName | startDate | endDate | goLiveDate | provStatus | timeZone | correlationId | projectName |
      |[blank]|          |[blank]|            |[blank]| today     | +2      | +1         |[blank]| Eastern  | projectId     |[blank]|

  @CP-2408 @CP-2408-04 @CP-4746 @CP-4746-04 @shruti  @events @events_smoke_level_two
  Scenario Outline: Validation of TEAM_USER_UPDATE_EVENT payload
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    And I check whether it has team value, if not, I will create a team "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    When I click on Edit icon on a User contact card
    And I update the user by checking or unchecking the checkbox
    And I click on Save checkmark on the Contact Card for the User
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify that payload contains team user details which I recently updated
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    #Then I verify response has created on and updated on in UTC format
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU | eventName              | correlationId | projectName |
      | {random} | {random} | today         | +6          |[blank]| TEAM_USER_UPDATE_EVENT | teamUserId    |[blank]|

  @CP-1683 @CP-1683-1 @CP-4746 @CP-4746-05 @Vidya @events @events_smoke_level_two
  Scenario Outline: Validation of BUSINESS_UNIT_UPDATE_EVENT payload
    Given I have logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to business unit list page
    When I select "<status>" BU record or populate new BU record with "<businessUnitName>","<Desc>","<startDate>","<endDate>" if record not exists
    And I click on Edit button
    And I edit "<updateDesc>","<updateEndDate>" field
    And I click on Save button on business unit details page
    Then I see BU record get updated succesfully and navigate to Businesss Unit list page
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify that payload contains edited business unit details which I recently created
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | status | businessUnitName | Desc     | startDate | endDate | updateDesc | updateEndDate | eventName                  | correlationId  | projectName |
      | ACTIVE | {random}         | {random} | today     | today   | {random}   | +2            | BUSINESS_UNIT_UPDATE_EVENT | businessUnitId |[blank]|

  @CP-4752 @CP-4752-01 @planProvider-events @aswath @events @pp-events
  Scenario Outline: Edit Buttons Viewable on Plan Details Tab
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I verify the Go live date
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
    When I upload the service region file before to plan file
    And I click on Plans Configuration tab
    When I choose a file to upload in Plan Config tab "<FileType>"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    And I verify the file upload "<MessageType>" message
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    Then I am brought to the Plan Details Screen
    When I click on the Enrollment Information container edit button
    And I input and save changes to the given Enrollment Information fields
    Then I see the Enrollment Information changes were saved
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify that payload contains event details for plan update
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | FileType       | MessageType         | eventName         | correlationId  | projectName |
      | GA Plan Config | Upload Plan Success | PLAN_UPDATE_EVENT | pcpRequiredInd |[blank]|

  @CP-4746 @CP-4746-06 @Vidya @events @events_smoke_level_two
  Scenario Outline: Create a new Project and save the Project
    Given I logged into Tenant Manager Project list page
    When I click on Create a New Project
    And I will enter "<prjName>","<prjState>","<prgName>","<contractId>","<stateAgencyName>","<startDate>","<endDate>","<goLiveDate>","<provStatus>","<timeZone>" fields values
    And I click on save button on project details page
    Then I verify success message is displayed for project creation and is on same page
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify that payload contains project details which I recently created
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | prjName  | prjState | prgName  | contractId | stateAgencyName | startDate | endDate | goLiveDate | provStatus | timeZone | eventName          | correlationId | projectName |
      | {random} | CT       | {random} | {random}   | {random}        | today     | +2      | +1         | Active     | Eastern  | PROJECT_SAVE_EVENT | projectName   |[blank]|

  @CP-4746 @CP-4746-07 @Vidya @events @events_smoke_level_two
  Scenario Outline: verify Project update event
    Given I logged into Tenant Manager and set the project context "project" value "SelectTestRegression2"
    When I will enter "<prjName>","<prjState>","<prgName>","<contractId>","<stateAgencyName>","<startDate>","<endDate>","<goLiveDate>","<provStatus>","<timeZone>" fields values
    And I click on save button on project details page
    Then I verify success message is displayed for project creation and is on same page
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify that payload contains project updated details which I recently created
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | prjName | prjState | prgName | contractId | stateAgencyName | startDate | endDate | goLiveDate | provStatus | timeZone | eventName            | correlationId | projectName |
      |[blank]|          |[blank]|            |[blank]| today     | +2      | +1         |[blank]| Eastern  | PROJECT_UPDATE_EVENT | projectId     |[blank]|

  @CP-4746 @CP-4746-08 @Vidya @events @events_smoke_level_two @CP-11012
  Scenario Outline: verify staff save event
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I click on User List Menu
    #And I will make sure that user by <MaxID> is not there for this project
    And I click on add new user button on add new user page
    And I create User with given data <isMaxEmp>,<MaxID>,<FN>,<MN>,<LN>,<Email>,<Start Date>,<End Date>,<Acct Type>,<Acct Auth>,<Auth Date>,<Override Auth>,<Override Auth Reason>,<PHI Access>,<PHI Reason>,<PII Access>,<PII Reason>,<Status>,<Role>
    And I click on save a user button
    #step was duplicate
   # And I click on yes button on warning message for selected role for user
    Then I should see a pop-up that user is created successfully
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify that payload contains user details which I recently created
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | isMaxEmp | MaxID    | FN | MN | LN | Email | Start Date | End Date | Acct Type | Acct Auth | Auth Date | Override Auth | Override Auth Reason | PHI Access | PHI Reason | PII Access | PII Reason | Status   | Role  | eventName        | correlationId             | projectName |
      | 'Yes'    | '154265' | '' | '' | '' | ''    | ''         | ''       | ''        | ''        | ''        | ''            | ''                   | ''         | ''         | ''         | ''         | 'Active' | 'Csr' | STAFF_SAVE_EVENT | ismaerskInternalEmployee |[blank]|


  @CP-4746 @CP-4746-09 @Vidya @events @events_smoke_level_two @CP-11013
  Scenario Outline: User is created successfully with Inactive status & verify we can create duplicate user
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I click on User List Menu
    And I will make sure that user by <MaxID> is not there for this project
    And I click on add new user button on add new user page
    And I create User with given data <isMaxEmp>,<MaxID>,<FN>,<MN>,<LN>,<Email>,<Start Date>,<End Date>,<Acct Type>,<Acct Auth>,<Auth Date>,<Override Auth>,<Override Auth Reason>,<PHI Access>,<PHI Reason>,<PII Access>,<PII Reason>,<Status>,<Role>
    And I click on save a user button
    Then I should see a pop-up that user is created successfully
    When I enter search criteria for a user by "maxID" value <MaxID>
    And I enter search criteria for a user by "status" value <Status>
    And I click on Search Button on User List Page
    And I click on first user to open User Details
    Then I verify values in account inactivation "reason" dropdown
      | Change of Job Functions |
    And I set Inactive Immediately to Yes
    Then I should see pop-up with message "The user account will be deactivated when you click the Save button"
    And I click on ok button to inactivate user
    And I click on save a user button
    Then I should see message that user has been inactivated successfully
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify that payload contains user update details which I recently created
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | isMaxEmp | MaxID    | FN | MN | LN | Email | Start Date | End Date | Acct Type | Acct Auth | Auth Date | Override Auth | Override Auth Reason | PHI Access | PHI Reason | PII Access | PII Reason | Status   | Role  | eventName          | correlationId | projectName |
      | 'Yes'    | '154269' | '' | '' | '' | ''    | ''         | ''       | ''        | ''        | ''        | ''            | ''                   | ''         | ''         | ''         | ''         | 'Active' | 'Csr' | STAFF_UPDATE_EVENT | StaffUpdate   |[blank]|

  @CP-502 @CP-502-20 @paramita @events
  Scenario Outline: Validation of STAFF_UPDATE_EVENT payload when user role is updated successfully
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I click on User List Menu
    And I enter search criteria for a user by "maxID" value "<MaxID>"
    And I click on Search Button on User List Page
    And I click on first user to open User Details
    And I select existing role and update end date
    And I click on User Role Save button
    Then I see update success message "User Updated Successfully"
    And I should be navigate back to the User List screen
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify that payload contains role update details which I recently updated
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | MaxID             | eventName          | correlationId             | projectName |
      | 'SVC_mars_user_1' | STAFF_UPDATE_EVENT | ismaerskInternalEmployee |[blank]|

  @CP-10939 @CP-10939-01 @events @events_smoke_level_two @umid
  Scenario Outline: Verify that when PROJECT_SAVE_EVENT are updated, Payload captured attributes, DateFormat check
    Given I logged into Tenant Manager Project list page
    When I click on Create a New Project
    And I will enter "<prjName>","<prjState>","<prgName>","<contractId>","<stateAgencyName>","<startDate>","<endDate>","<goLiveDate>","<provStatus>","<timeZone>" fields values
    And I click on save button on project details page
    Then I verify success message is displayed for project creation and is on same page
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify that payload contains project details which I recently created
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    And I should see "<eventName>" Event was created
    Examples:
      | prjName  | prjState | prgName  | contractId | stateAgencyName | startDate | endDate | goLiveDate | provStatus | timeZone | eventName          | correlationId | projectName |
      | {random} | CT       | {random} | {random}   | {random}        |[blank]|         |[blank]| Active     | Eastern  | PROJECT_SAVE_EVENT | projectName   |[blank]|

  @CP-10943 @CP-10943-01 @events @events_smoke_level_two @umid
  Scenario Outline: Verify that when PROJECT_UPDATE_EVENT are updated, Payload captured attributes, DateFormat check
    Given I logged into Tenant Manager and set the project context "project" value "10939Events"
    When I will enter "<prjName>","<prjState>","<prgName>","<contractId>","<stateAgencyName>","<startDate>","<endDate>","<goLiveDate>","<provStatus>","<timeZone>" fields values
    And I click on save button on project details page
    Then I verify success message is displayed for project creation and is on same page
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify that payload contains project updated details which I recently created
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | prjName | prjState | prgName  | contractId | stateAgencyName | startDate | endDate | goLiveDate | provStatus | timeZone | eventName            | correlationId | projectName |
      |[blank]|          | {random} |[blank]| {random}        | today     | +2      | +1         |[blank]| Eastern  | PROJECT_UPDATE_EVENT | projectId     |[blank]|

  @CP-10943 @CP-10943-02 @events @events_smoke_level_two @umid
  Scenario Outline: PROJECT_UPDATE_EVENT captured Payload attributes, DateFormat check, Account Manager and Approver are added or edited
    Given I logged into Tenant Manager and set the project context "project" value "RefTnles"
    When I will enter "<prjName>","<prjState>","<prgName>","<contractId>","<stateAgencyName>","<startDate>","<endDate>","<goLiveDate>","<provStatus>","<timeZone>" fields values
    And I check Account Manager and Approver are added
    And I click on save button on project details page
#    Then I verify success message is displayed for project creation and is on same page
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify that payload contains project updated details which I recently created
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | prjName | prjState | prgName  | contractId | stateAgencyName | startDate | endDate | goLiveDate | provStatus | timeZone | eventName            | correlationId | projectName |
      |[blank]|          | {random} |[blank]| {random}        | today     | +2      | +1         |[blank]| Eastern  | PROJECT_UPDATE_EVENT | projectId     |[blank]|

#  @CP-11156 @CP-11156-01 @events @events_smoke_level_two @umid
  Scenario Outline: Verify PROJECT_ROLE_SAVE_EVENT is generated when new Role has been added
    Given I logged into Tenant Manager and set the project context "project" value "<projectName>"
    And I navigate to the role and add a new role
    When I add a role "<projectId>","<projectName>","<action>","<recordType>"
    And I search "PROJECT_ROLE_SAVE_EVENT" in the Get Events endpoint for DBPI
    Then I should see "PROJECT_ROLE_SAVE_EVENT" Event was created
    Examples:
      | projectId | projectName | action | recordType   |
      | 6203      | BLCRM       | Create | Project Role |

  @CP-11183 @CP-11183-01 @events @events_smoke_level_two @umid
  Scenario Outline: Verify Project_Role_Update_event is generated when Role details are updated
    Given I logged into Tenant Manager and set the project context "project" value "<projectName>"
    When I click on Role List Menu
    And I update a role "<projectId>","<projectName>","<action>","<recordType>"
    And I search "PROJECT_ROLE_UPDATE_EVENT" in the Get Events endpoint for DBPI
    Then I should see "PROJECT_ROLE_UPDATE_EVENT" Event was created
    Examples:
      | projectId | projectName | action | recordType   |
      | 6203      | BLCRM       | Update | Project Role |

#  @CP-11183 @CP-11183-02 @events @events_smoke_level_two @umid
  Scenario Outline: Verify Project_Role_Update_event is generated when Role name is updated
    Given I logged into Tenant Manager and set the project context "project" value "<projectName>"
    When I click on Role List Menu
    And I update a role with Role Name Only "<projectId>","<projectName>","<action>","<recordType>"
    And I search "PROJECT_ROLE_UPDATE_EVENT" in the Get Events endpoint for DBPI
    Then I should see "PROJECT_ROLE_UPDATE_EVENT" Event was created
    Examples:
      | projectId | projectName | action | recordType   |
      | 6203      | BLCRM       | Update | Project Role |


  @CP-11183 @CP-11183-03 @events @events_smoke_level_two @umid
  Scenario Outline: Verify Project_Role_Update_event is generated when Role Description is updated
    Given I logged into Tenant Manager and set the project context "project" value "<projectName>"
    When I click on Role List Menu
    And I update a role with Role Description Only "<projectId>","<projectName>","<action>","<recordType>"
    And I search "PROJECT_ROLE_UPDATE_EVENT" in the Get Events endpoint for DBPI
    Then I should see "PROJECT_ROLE_UPDATE_EVENT" Event was created
    Examples:
      | projectId | projectName | action | recordType   |
      | 6203      | BLCRM       | Update | Project Role |


  @CP-11386 @CP-11386-01 @kamil @events
  Scenario Outline: Verify Link_EVENT is triggered for BU & Task Type
    Given I have logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to business unit list page
    When I select "<status>" BU record or populate new BU record with "<businessUnitName>","<Desc>","<startDate>","<endDate>" if record not exists
    And I click on Edit button
    And I edit "<updateDesc>","<updateEndDate>" field
    And I associate one more TaskType and click on Save
    Then I see BU record get updated succesfully and navigate to Businesss Unit list page
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I will send API call to get Event
    Then I verify response for "BUSINESS_UNIT_UPDATE_EVENT" for Link_Event details
    And  I verify "LINK_EVENT" is generated for Task Type
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | status | businessUnitName | Desc     | startDate | endDate | updateDesc | updateEndDate | eventName  | correlationId  | projectName |
      | ACTIVE | {random}         | {random} | today     | today   | {random}   | +2            | LINK_EVENT | businessUnitId |[blank]|


  @CP-11386 @CP-11386-02 @kamil @events
  Scenario Outline: Verify UnLink_EVENT is triggered for BU & Task Type
    Given I have logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to business unit list page
    When I select "<status>" BU record or populate new BU record with "<businessUnitName>","<Desc>","<startDate>","<endDate>" if record not exists
    And I click on Edit button
    And I edit "<updateDesc>","<updateEndDate>" field
    And I disassociate one more TaskType and click on Save
    Then I see BU record get updated succesfully and navigate to Businesss Unit list page
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I will send API call to get Event
    Then I verify response for "BUSINESS_UNIT_UPDATE_EVENT" for Link_Event details
    And  I verify "UNLINK_EVENT" is generated for Task Type
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | status | businessUnitName | Desc     | startDate | endDate | updateDesc | updateEndDate | eventName    | correlationId  | projectName |
      | ACTIVE | {random}         | {random} | today     | today   | {random}   | +2            | UNLINK_EVENT | businessUnitId |[blank]|


  #Failing due to bug CP-13523
  @CP-11386 @CP-11386-03  @kamil @events
  Scenario Outline: Verify UNLINK_EVENT and Link_EVENT is triggered for BU & Task Type
    Given I have logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to business unit list page
    When I select "<status>" BU record or populate new BU record with "<businessUnitName>","<Desc>","<startDate>","<endDate>" if record not exists
    And I click on Edit button
    And I edit "<updateDesc>","<updateEndDate>" field
    Then I associate one more TaskType and disassociate one more TaskType
    Then I see BU record get updated succesfully and navigate to Businesss Unit list page
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I will send API call to get Event
    Then I verify response for "BUSINESS_UNIT_UPDATE_EVENT" for Link_Event details
    And  I verify "UNLINK_EVENT" is generated for Task Type
    And I Verify Link_EVENT for Task Type when UNLINK_EVENT and Link_EVENT is triggered
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    Then I will check the response has event Subscriber Mapping Id for "<eventName1>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName1>" and subscriberId
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | status | businessUnitName | Desc     | startDate | endDate | updateDesc | updateEndDate | eventName    | correlationId  | projectName | eventName1 |
      | ACTIVE | {random}         | {random} | today     | today   | {random}   | +2            | UNLINK_EVENT | businessUnitId |[blank]| LINK_EVENT |

  @CP-10317 @CP-10317-2 @kamil @tm-regression
  Scenario Outline:Verify payload and STAFF_SAVE_EVENT published to DPBI
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I click on User List Menu
    And I click on add new user button on add new user page
    Then I entering FirstName and StartDate
    Then I select Account Type as System
    And I click on save a user button
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify other fields should be null in the payload
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | correlationId             | eventName        | projectName |
      | ismaerskInternalEmployee | STAFF_SAVE_EVENT |[blank]|

  @CP-13824 @CP-13824-1 @basha @events
  Scenario Outline: Validation of BUSINESS_DAY_SAVE_EVENT payload
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Business Days Page
    And I see a Warning Message permanently displayed at the bottom
    And I click Add Business Days
    When I populate data on business days details page "<businessDayName>","<startDate>","<endDate>","<excludeForTask>","<excludeForSR>"
    And I select business days "<sun>","<mon>","<tue>","<wed>","<thu>","<fri>","<sat>"
    And I click on Save button on business days details page
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify that payload contains business day details which I recently created "<startDate>"
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      |businessDayName|startDate|endDate|excludeForTask|excludeForSR|sun|mon |tue |wed |thu |fri |sat|eventName              |correlationId  |projectName|
      |{random}       |+210     |+212   |true          |[blank]|   |true|true|true|[blank]|    |[blank]|BUSINESS_DAY_SAVE_EVENT|businessDayName|[blank]|


  @CP-13824 @CP-13824-2 @basha @events
  Scenario Outline: Validation of BUSINESS_DAY_UPDATE_EVENT payload
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Business Days Page
    And I click Add Business Days
    When I populate data on business days details page "<businessDayName>","<startDate>","<endDate>","<excludeForTask>","<excludeForSR>"
    And I select business days "<sun>","<mon>","<tue>","<wed>","<thu>","<fri>","<sat>"
    And I click on Save button on business days details page
    Then I verify time frame is in the future
    And I edit the fields in a future time frame "<businessDayName>","<startDate>","+215","<excludeForTask>","<excludeForSR>","<sun>","<mon>","<tue>","<wed>","<thu>","<fri>","<sat>"
    And I click on Save button on business days details page
    Then I verify time frame is in the future
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify that payload contains business day details which I recently updated "<startDate>"
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      |businessDayName|startDate|endDate|excludeForTask|excludeForSR|sun|mon |tue |wed |thu |fri |sat|eventName                |correlationId  |projectName|
      |{random}       |+213     |+214   |true          |[blank]|   |true|true|true|[blank]|    |[blank]|BUSINESS_DAY_UPDATE_EVENT|businessDaysId |[blank]|

  @CP-16654 @CP-16654-01 @mital @tm @events @CP-11156 @CP-11156-01 @events @umid
  Scenario Outline: Verify PROJECT_ROLE_SAVE_EVENT is generated when new Role has been added
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role details page
    And I populate data on project role page "<roleName>","<roleDesc>","<startDate1>","<endDate1>"
    And I click on Save button on project role page
    Then I see pop-up that role is created
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify the role details payload after role "Create"
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    Examples:
      | roleName | roleDesc        | startDate1 | endDate1 | eventName               | projectName | correlationId |
      | {random} | This is to test | today      | +1       | PROJECT_ROLE_SAVE_EVENT |[blank]| createdBy     |

  @CP-16654 @CP-16654-02 @mital @tm @events @CP-11183 @CP-11183-02 @umid
  Scenario Outline: Verify PROJECT_ROLE_UPDATE_EVENT is generated when Role name is updated
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role details page
    And I navigate to User role list screen
    Then I click on first role to open role details page
    Then I update role name with new name
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify the role details payload after role "Update"
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    Examples:
      | eventName                 | projectName | correlationId |
      | PROJECT_ROLE_UPDATE_EVENT |[blank]| createdBy     |

  @CP-12689 @CP-12689-01 @mital @tm @events
  Scenario Outline: Verify USER_SAVE_EVENT at user creation of type maersk User
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on User List Menu
    And I click on add new user button
    And I create User with given data <isMaxEmp>,<MaxID>,<FN>,<MN>,<LN>,<Email>,<Start Date>,<End Date>,<Acct Type>,<Acct Auth>,<Auth Date>,<Override Auth>,<Override Auth Reason>,<PHI Access>,<PHI Reason>,<PII Access>,<PII Reason>,<Status>,<Role>
    And I click on save a user button
    And I click on yes button on warning message for selected role for user
    Then I should see a pop-up that user is created successfully
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify the user details payload after user creation
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    Examples:
      | isMaxEmp | MaxID | FN | MN | LN | Email | Start Date | End Date | Acct Type | Acct Auth | Auth Date | Override Auth | Override Auth Reason | PHI Access | PHI Reason | PII Access | PII Reason | Status   | Role  | eventName       | correlationId             | projectName |
      | 'No'     | ''    | '' | '' | '' | ''    | ''         | ''       | ''        | ''        | ''        | ''            | ''                   | ''         | ''         | ''         | ''         | 'Active' | 'Csr' | USER_SAVE_EVENT | ismaerskInternalEmployee |[blank]|


  @CP-12689 @CP-12689-02 @mital @tm @events
  Scenario Outline: Verify USER_UPDATE_EVENT at user updation of type maersk User
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I click on User List Menu
    And I enter search criteria for a user by "accountType" value <accountType>
    And I enter search criteria for a user by "status" value <Status>
    And I click on Search Button on User List Page
    And I click on first user to open User Details
    And I update user Email ID value
    And I click on User Role Save button
    And I should be navigate back to the User List screen
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify the user details payload after user updation
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | Status   | accountType            | eventName         | correlationId             | projectName |
      | 'Active' | 'Individual - maersk' | USER_UPDATE_EVENT | ismaerskInternalEmployee |[blank]|

  @CP-12689 @CP-12689-03 @mital @tm @events
  Scenario Outline: Verify USER_UPDATE_EVENT at user updation of type maersk User for Inactivation
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I click on User List Menu
    And I enter search criteria for a user by "accountType" value <accountType>
    And I enter search criteria for a user by "status" value <Status>
    And I click on Search Button on User List Page
    And I click on first user to open User Details
    And I set Inactive Immediately to Yes
    Then I should see pop-up with message "The user account will be deactivated when you click the Save button"
    And I click on ok button to inactivate user
    And I select value "Resigned" in account inactivation dropdown
    And I click on save a user button
    And I should be navigate back to the User List screen
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify the user details payload after user updation for values
      | inactivateReason      | Resigned |
      | inactivateImmediately | true     |
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | Status   | accountType            | eventName         | correlationId             | projectName | roleName1 | activeStatus1 |
      | 'Active' | 'Individual - maersk' | USER_UPDATE_EVENT | ismaerskInternalEmployee |[blank]| Csr       | Active        |

  @CP-12689 @CP-12689-04 @mital @tm @events
  Scenario Outline: Verify USER_UPDATE_EVENT at user updation of type maersk User for reactivation
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I click on User List Menu
    And I enter search criteria for a user by "accountType" value <accountType>
    And I enter search criteria for a user by "status" value <Status>
    And I click on Search Button on User List Page
    And I click on first user to open User Details
    When I select value "Rehire" in account reactivation dropdown
    And I click on save a user button
    Then I see the error that End date must be empty to reactivate a user
    And I nullify the end date
    And I click on save a user button
    Then I should see message that user has been reactivated successfully
    And I should be navigate back to the User List screen
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify the user details payload after user updation for values
      | reactivateReason      | Rehire |
      | inactivateImmediately | false  |
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | Status     | accountType            | eventName         | correlationId             | projectName | roleName1 | activeStatus1 |
      | 'Inactive' | 'Individual - maersk' | USER_UPDATE_EVENT | ismaerskInternalEmployee |[blank]| Csr       | Inactive      |


  @CP-12689 @CP-12689-05 @mital @tm @events
  Scenario Outline: Verify USER_UPDATE_EVENT at user updation of type maersk User for start date, end date
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I click on User List Menu
    And I enter search criteria for a user by "accountType" value <accountType>
    And I enter search criteria for a user by "status" value <Status>
    And I click on Search Button on User List Page
    And I click on first user to open User Details
    When I update end date in User Details
    And I update user role with another role value
    And I click on save a user button
    And I should be navigate back to the User List screen
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify the user details payload after user updation
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | Status   | accountType            | eventName         | correlationId             | projectName |
      | 'Active' | 'Individual - maersk' | USER_UPDATE_EVENT | ismaerskInternalEmployee |[blank]|

