@projectroleController
Feature: API-Tenant Manager: Project Role Controller

  @auxiliaryService @search-services-AS @event-api-AS @lookup-api-AS @API-CRM-751 @API-CRM-751_1 @API-TM @API-TM-Regression @API-Project-role @Vinuta @tenantManagerAPI @tm-api-TM
  Scenario Outline: Create Project Role API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Create Project Role API
    When I can provide role details with "<projectId>" "<roleName>" "<roleDesc>"
    And I run the create project role API
    Then I can search the project role by role name to validate is "<success>"
    Examples:
      | projectId | roleName | roleDesc | success |projectName|
      | 8098         | {random} | {random} | TRUE    |[blank]|

  @auxiliaryService @search-services-AS @event-api-AS @lookup-api-AS @API-CRM-751 @API-CRM-751_2 @API-TM @API-TM-Regression @API-Project-role @Vinuta @tenantManagerAPI @tm-api-TM
  Scenario Outline: Validate Duplicate Project Role is not created by API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Create Project Role API
    When I can provide role details with "<projectId>" "<roleName>" "<roleDesc>"
    And I run the create project role API
    Then I can search the project role by role name to validate is "<success>"
    Given I initiated Create Project Role API
    And I run the create project role API
    Then I can verify the duplicate role error message on API response
    Examples:
      | projectId | roleName | roleDesc | success |projectName|
      | 8098         | {random} | {random} | TRUE    |[blank]|

  @auxiliaryService @search-services-AS @API-CRM-752 @API-CRM-752_1 @API-TM @API-TM-Regression @API-Project-role @Vinuta @tenantManagerAPI @tm-api-TM
  Scenario Outline: Project role search API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Project Role By Project ID via API "<projectId>"
    Then I can verify on project role search API response will be "<success>"
    And I can verify each role has all required information displayed
    Examples:
      | projectId | success |projectName|
      | 8098         | TRUE    |[blank]|

  @auxiliaryService @search-services-AS @event-api-AS @lookup-api-AS @API-CRM-752 @API-CRM-752_2 @API-TM @API-TM-Regression @API-Project-role @Vinuta @tenantManagerAPI @tm-api-TM
  Scenario Outline: Project role search API by role name
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Create Project Role API
    When I can provide role details with "<projectId>" "<roleName>" "<roleDesc>"
    And I run the create project role API
    And I initiated Search Project Role API
    And I can Search Project Role by "projectId" with value "<projectId>"
    And I can Search Project Role by "roleName" with value "<roleName>"
    And I can Search Project Role by "modified_date" with value "<modified_date>"
    And I can Search Project Role by "creation_date" with value "<creation_date>"
    And I run get roles of a project via API
    Then I verify each role has exact role name as the search criteria
    Examples:
      | projectId | roleName | roleDesc |creation_date|modified_date|projectName|
      | 8098         | {random} | {random} |[blank]|             |[blank]|

  @auxiliaryService @search-services-AS @event-api-AS @lookup-api-AS @API-CRM-752 @API-CRM-752_3 @API-TM @API-TM-Regression @API-Project-role @Vinuta @tenantManagerAPI @tm-api-TM
  Scenario Outline: Project role search API by role description
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Create Project Role API
    When I can provide role details with "<projectId>" "<roleName>" "<roleDesc>"
    And I run the create project role API
    And I initiated Search Project Role API
    And I can Search Project Role by "projectId" with value "<projectId>"
    And I can Search Project Role by "roleDesc" with value "<roleDesc>"
    And I run get roles of a project via API
    Then I verify each role has exact role name as the search criteria
    Examples:
      | projectId | roleName | roleDesc |projectName|
      | 8098        | {random} | {random} |[blank]|

  @auxiliaryService @search-services-AS @event-api-AS @lookup-api-AS @API-CRM-752 @API-CRM-752_4 @API-TM @API-TM-Regression @API-Project-role @Vinuta @tenantManagerAPI @tm-api-TM
  Scenario Outline: Project role search API by role status Active
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Create Project Role API
    When I can provide role details with "<projectId>" "<roleName>" "<roleDesc>"
    And I run the create project role API
    And I initiated Search Project Role API
    And I can Search Project Role by "roleStatus" with value "Active"
    And I run get roles of a project via API
    Then I can verify "roleStatus" with value "Active" on project role API response will be "success"
    Examples:
      | projectId | roleName | roleDesc |projectName|
      | 8098         | {random} | {random} |[blank]|

  @auxiliaryService @search-services-AS @event-api-AS @lookup-api-AS @API-CRM-752 @API-CRM-752_5 @API-TM @API-TM-Regression @API-Project-role @Vinuta @tenantManagerAPI @tm-api-TM
  Scenario Outline: Project role search API by role status Inactive
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Create Project Role API
    When I can provide role details with "<projectId>" "<roleName>" "<roleDesc>"
    And I run the create project role API
    And I initiated Search Project Role API
    And I can Search Project Role by "roleStatus" with value "Inactive"
    And I run get roles of a project via API
    Then I can verify "roleStatus" with value "Inactive" on project role API response will be "success"
    Examples:
      | projectId | roleName | roleDesc |projectName|
      | 8098         | {random} | {random} |[blank]|

  @auxiliaryService @search-services-AS @event-api-AS @API-CRM-752 @API-CRM-752_6 @API-TM @API-TM-Regression @API-Project-role @Vinuta @tenantManagerAPI @tm-api-TM
  Scenario Outline: Project role search API by role status All
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Create Project Role API
    When I can provide role details with "<projectId>" "<roleName>" "<roleDesc>"
    And I run the create project role API
    And I initiated Search Project Role API
    And I can Search Project Role by "roleStatus" with value "All"
    And I run get roles of a project via API
    Then I can verify "roleStatus" with value "All" on project role API response will be "success"
    Examples:
      | projectId | roleName | roleDesc |projectName|
      | 8098         | {random} | {random} |[blank]|

  @auxiliaryService @search-services-AS @event-api-AS @API-CRM-904 @API-TM @API-TM-Regression @API-Project-role @Muhabbat @tenantManagerAPI @tm-api-TM
  Scenario Outline: Associate Permission to a Role when Creating Project Role API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Create Project Role API
    When I provide role details "<projectId>" "<roleName>" "<roleDesc>" with "<permission>"
    And I run the create project role API
    Then I can search the project role by role name to validate is "<success>"
    Examples:
      | projectId | roleName | roleDesc | permission | success |projectName|
      | 8098         | {random} | {random} | admin      | TRUE    |[blank]|

  @API-TM-1769 @API-TM-1769-01 @API-TM-Regression @mital
  Scenario Outline: Verify clone permission of role to another role API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I initiated clone role permissions API
    When I create a request payload with data for cloning permission for given data
      | sourceRoleId   | 4441         |
      | sourceRoleName | Csr  |
      | targetRoleId   | 761         |
      | targetRoleName | Testmanager |
    Then I verify the response status code "200" with creation status "success"
    Examples:
      | projectName |
      | Regression  |

  @API-TM-CP-41970 @API-TM-CP-41970-01 @API-TM-Regression @mital
  Scenario Outline: Verify API to get Supervisor and Team Details
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I initiated get Supervisor and Team Details API with "<projectId>" and "<userId>"
    And I run the get Supervisor and Team Details API
    Then I verify the response status code "200" with creation status "success"
    And I verify the API returns details of team whose supervisor is "<userId>"
    Then I verify "<userId>" is a supervisor for following teams
      |"Inbound A"|
      |"Inbound B"|
      |"Test"    |
    Examples:
      | projectName | projectId | userId |
      | Regression  | 92        | 1066   |

