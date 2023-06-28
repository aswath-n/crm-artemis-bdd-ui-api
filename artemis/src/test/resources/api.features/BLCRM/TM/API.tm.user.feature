@userController
Feature: API-Tenant Manager: User Controller
# review comments Can existing staff id be picked up from project instead of hard codded 439 and 441? these scenarios will need maintenance with change of project of change of environment
    #Below scripts for CP-1028 Modify Tenant Manager API to accept Array of User IDs and return User details
    @API-CP-1028 @API-CP-1028-1 @API-TM @Vidya @API-TM-Regression @tenantManagerAPI
    Scenario Outline: Validation of TM new Endpoint to accept a userId and return UserId and user Name
        When I will get the Authentication token for "<projectName>" in "Tenant Manager"
        Given I will get the staff id for maxId "login"
        And I will get the user id for current "<projectId>" and staffId ""
        When I initiated Array of user id API
        And I will send single userId in body
        And I run the Array of user id API
        Then I verify the API response parameter
        Examples:
            | projectId |projectName|
            |   6203    |[blank]|

    @API-CP-1028 @API-CP-1028-2 @API-TM @Vidya @API-TM-Regression @tenantManagerAPI
    Scenario Outline: Validation of TM new Endpoint to accept array of userIds and return array of userIds and user Names
        When I will get the Authentication token for "<projectName>" in "Tenant Manager"
        Given I will get the staff id for maxId "login"
        And I will get the user id for current "<projectId>" and staffId ""
        Given I will get the staff id for maxId "additionalLogin"
        And I will get the user id for current "<projectId>" and staffId ""
        When I initiated Array of user id API
        And I will send array of userId in body
        And I run the Array of user id API
        Then I verify the API response parameter
        Examples:
            | projectId |projectName|
            |[blank]|           |

    @API-CP-1028 @API-CP-1028-3 @API-TM @Vidya @API-TM-Regression @tenantManagerAPI
    Scenario Outline: Validation of if user Id does not exist
        When I will get the Authentication token for "<projectName>" in "Tenant Manager"
        Given I will get the staff id for maxId "login"
        And I will get the user id for current "<projectId>" and staffId ""
        Given I will get the staff id for maxId "additionalLogin"
        And I will get the user id for current "<projectId>" and staffId ""
        When I initiated Array of user id API
        And I will send array of userIds with one user Id which does not exist in body
        And I run the Array of user id API
        Then I verify the API response parameter
        Examples:
            | projectId |projectName|
            |[blank]|           |

    @API-CP-10355 @API-CP-10355-1 @API-TM @Vidya @API-TM-Regression
    Scenario Outline: Validation of user MaxId has 0 in accountExpiry in active directory
        When I will get the Authentication token for "<projectName>" in "Tenant Manager"
        Given I will initiate user active directory API for "<MaxId>"
        When I hit the GET API of user active directory
        Then I verify the response has accountExpiry 0 value
        Examples:
            | MaxId |projectName|
            | 52987 |[blank]|

    @API-CP-10355 @API-CP-10355-2 @API-TM @Vidya @API-TM-Regression
    Scenario Outline: Validation of user MaxId does not has 0 in accountExpiry in active directory
        When I will get the Authentication token for "<projectName>" in "Tenant Manager"
        Given I will initiate user active directory API for "<MaxId>"
        When I hit the GET API of user active directory
        Then I verify the response has accountExpiry other than 0 value
        Examples:
            | MaxId           |projectName|
            | SVC_mars_user_1 |[blank]|


  @API-CP-502 @API-CP-502-1 @API-TM @paramita @API-TM-Regression
  Scenario Outline: Validation of Update Single User Role API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And provide role details with "<projectId>" "<roleName>" "<roleDesc>"
    And I run create project role API and get new role ID
    And I check whether user of given maxid exits in "<projectId>" or will create user with "<MaxId>"
    And I get userID for existing maxid "<MaxId>" for "<projectId>"
    Given I initiated Update User Role API
    When I provide single user role Update details for "<projectId>" for "<MaxId>"
    And I run update user role API
    Then I verify success status for Update User Role API
    And I verify User role is updated with all required information displayed
    And I verify user role field value is updated sucessfully
    Examples:
        | projectId | roleName | roleDesc |  projectName| MaxId |
        |[blank]|  {random}| {random}|[blank]| SVC_mars_user_3|

    @API-CP-502 @API-CP-502-2 @API-TM @paramita @API-TM-Regression
    Scenario Outline: Validation of User update Role with Start date >= Current date and End date >= Start Date
        When I will get the Authentication token for "<projectName>" in "Tenant Manager"
        And provide role details with "<projectId>" "<roleName>" "<roleDesc>"
        And I run create project role API and get new role ID
        And I get userID for existing maxid "<MaxId>" for "<projectId>"
        Given I initiated Update User Role API
        When I provide role Update details with date value "<startdate>" "<enddate>" for "<projectId>" and "<MaxId>"
        And I run update user role API
        Then I verify success status for Update User Role API
        And I verify User role is updated with all required information displayed
        And I verify user role field value is updated sucessfully with date value
        Examples:
            | projectId | roleName | roleDesc |projectName|startdate            |enddate      |MaxId |
            |[blank]|  {random}| {random}|[blank]|+1                   |+2           | SVC_mars_user_3|
            |[blank]|  {random}| {random}|[blank]|currentDate          |currentDate  | SVC_mars_user_3|

    @CP-10317 @CP-10317-3 @kamil @tm-regression
    Scenario Outline:Verify in the API response that user ID is different for both users
        Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
        And I click on User List Menu
        And I click on add new user button on add new user page
        When I select Account Type as System
        Then I entering FirstName and StartDate
        And I click on save a user button
        Then Navigate back to Project List page
        And I click on User List Menu
        And I click on add new user button on add new user page
        Then Create new user with same FirstName and StartDate in different project
        And I click on Save button on project role page
        When I will get the Authentication token for "<projectName>" in "Tenant Manager"
        When I initiated User info for service provider API
        Then I sending API call to get "first" created new user UserId
        Then I sending API call to get "second" created new user UserId
        Then Verify in the API response that user ID is different for both users
        Examples:
            | correlationId             | eventName        | projectName |
            | ismaerskInternalEmployee | STAFF_SAVE_EVENT |[blank]|


    @API-CP-21406 @API-CP-21406-1 @API-TM @shruti @API-TM-Regression
    Scenario Outline: Add Non maersk User
        Given I initiated Create Project Role API
        When I will get the Authentication token for "<projectName>" in "Tenant Manager"
        And I initiated get project role by "" API
        And I run get project role by project API
        Given I initiated Update User Role API
        When I provide the details for creating or update project user
            |action|create|
            |projectId|[blank]|
            |firstName|random |
            |lastName |random |
            |email    |random |
        And I run create or update project user with role API
        Then I verify success status for create or update project Userwith  Role API
        When I provide the details for creating or update project user
            |action|update|
            |projectId|[blank]|
            |firstName|random |
            |lastName |random |
        And I run create or update project user with role API
        Then I verify error message is displayed for create or update project Userwith  Role API

        Examples:
            | projectId | roleName | roleDesc |projectName|
            |[blank]|  {random}| {random}|[blank]|

    @API-CP-27028 @API-CP-27028-01 @API-TM @shruti @API-TM-Regression
    Scenario Outline: Add Non maersk User and verify CP access via API
        Given I initiated Create Project Role API
        When I will get the Authentication token for "<projectName>" in "Tenant Manager"
        And I initiated get project role by "" API
        And I run get project role by project API
        Given I initiated Update User Role API
        When I provide the details for creating or update project user
            |action|create|
            |projectId|[blank]|
            |firstName|random |
            |lastName |random |
            |email    |<emailId> |
        And I run create or update project user with role API
        Then I verify success status for create or update project Userwith  Role API
        When I initiated get user role details by "<projectId>" and "<emailId>" API
        And I run get user role by project and emaild API
        Then I verify user role details are displayed  for the Non maersk user

        Examples:
            | projectId | roleName | roleDesc |projectName|emailId|
            |[blank]|  {random}| {random}|[blank]|       |




