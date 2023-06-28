@TM_ProjectPermissionController
Feature: API-Tenant Manager: Project Permission Controller

  @API-CRM-903 @API-CRM @API-TM-Regression @API-Project-permission @Sujoy @tenantManagerAPI @tm-api-TM 
  Scenario Outline: View Project Permission list using API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I can get project id for permission by project name "<projectName>"
    When I initiated get project permission API by Projecr ID ""
    And I run get project permission API with Query parameters "<page>","<size>" and "<sort>"
    Then I can verify get permission response success is "<Success>"
    And I can verify get permission list size is equal or less than "<size>"
    Examples:
      | projectName | page | size | sort                | Success |
      | Regression       | 0    | 5    | permissionGroupName | True    |

#    @API-CRM-905 @API-TM @API-TM-Regression @API-Project-permission @Sujoy
#    Scenario Outline: Create Project Permission API
#        Given I initiated Create Project Permission API
#        And I run the create project permission API
#
#        Examples:
#            |projectId|roleName |roleDesc    |success|
#            |{random} |{random} |{random}    |TRUE   |

  @auxiliaryService @search-services-AS @event-api-AS @lookup-api-AS @API-CRM-902 @API-TM @API-TM-Regression @API-Project-permission @Muhabbat  @tenantManagerAPI @tm-api-TM
  Scenario Outline: Create Project Group Permission API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated and run Create Project Role API for Project Permission Group with "<projectId>" "<roleName>" "<roleDesc>"
    And I initiated Create Project Permission API
    And I run the create project permission API with "<permissionName>" "<permissionDescription>"
    Then I verify successful creation of project permission
    Examples:
      | projectId | roleName | roleDesc | permissionName | permissionDescription | projectName|
      | 8635        | {random} | {random} | {random}       | {random}              |[blank]|

  @auxiliaryService @search-services-AS @lookup-api-AS @event-api-AS @API-CRM-902 @API-TM @API-TM-Regression @API-Project-permission @Muhabbat @thisfail  @tenantManagerAPI @tm-api-TM 
  Scenario Outline: Creating Duplicate Project Group Permission API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated and run Create Project Role API for Project Permission Group with "<projectId>" "<roleName>" "<roleDesc>"
    And I initiated Create Project Permission API
    And I run the create project permission API with "<permissionName>" "<permissionDescription>"
    Then I verify successful creation of project permission
    And I initiated Create Project Permission API
    When I re-run the create project permission API
    Then I can verify the duplicate project permission message on API response
    Examples:
      | projectId | roleName | roleDesc | permissionName | permissionDescription | projectName|
      | 44        | {random} | {random} | {random}       | {random}              |[blank]|

#  @auxiliaryService @search-services-AS @API-CRM-902 @API-TM @API-TM-Regression @API-Project-permission @Muhabbat  @tenantManagerAPI @tm-api-TM
  Scenario Outline: Adding Configurations to Permission Group
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Search Project API and Run by ID "<projectId>"
    And I initiated and run Create Project Role API for Project Permission Group with "<projectId>" "<roleName>" "<roleDesc>"
    And I initiated Create Project Permission API
    And I run the create project permission API with "<permissionName>" "<permissionDescription>"
    And I initiated Config Permission API
    And I run Config Permission API with

    Examples:
      | projectId | roleName | roleDesc | permissionName | permissionDescription | projectName|
      | 8635        | {random} | {random} | {random}       | {random}              |[blank]|


  @auxiliaryService @event-api-AS @API-EB-224 @API-EB-224-1 @API-TM @API-TM-Regression @API-Project-permission @shruti  @tenantManagerAPI @tm-api-TM 
  Scenario Outline: Adding Access Permissions to GIRD at Grid level
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Search Project API and Run by ID "<projectId>"
    And I initiated and run Create Project Role API for Project Permission Group with "<projectId>" "<roleName>" "<roleDesc>"
    And I initiated Create Project Permission API
    And I run the create project permission API with "<permissionName>" "<permissionDescription>"
    And I initiated Config Permission API
    When I run Config Permission API using grid payload to update with "<TypeOfPermission>" permission for key "<GridKey>" grid "<GridId>" and section "<SectionId>"
    Then I verify success status for group permission api
    Given I initiated get project permission group details API by User ID "<UserId>" and Project Id "<projectId>"
    When I run get project permission group details API
    Then I verify permission "<TypeOfPermission>" is updated for section "<SectionId>" and grid "<GridId>"

    Examples:
      | projectId | roleName | roleDesc | permissionName | permissionDescription |TypeOfPermission|GridKey                  |GridId |SectionId|UserId| projectName|
      |  8635         | {random} | {random} | {random}       | {random}              |View            |demographicCaseMemberGrid|10    |31      |Scv_mars_user_1|[blank]|



  @auxiliaryService @event-api-AS @API-EB-224 @API-EB-224-2 @API-TM @API-TM-Regression @API-Project-permission @shruti  @tenantManagerAPI @tm-api-TM 
  Scenario Outline: Adding Access Permissions to GIRD at Section level
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Search Project API and Run by ID "<projectId>"
    And I initiated and run Create Project Role API for Project Permission Group with "<projectId>" "<roleName>" "<roleDesc>"
    And I initiated Create Project Permission API
    And I run the create project permission API with "<permissionName>" "<permissionDescription>"
    And I initiated Config Permission API
    When I run Config Permission API using section payload to update with "<TypeOfPermission>" permission for key "<SectionKey>" section "<SectionId>" and page "<pageId>"
    Then I verify success status for group permission api
    Given I initiated get project permission group details API by User ID "<UserId>" and Project Id "<projectId>"
    When I run get project permission group details API
    Then I verify permission "<TypeOfPermission>" is updated for page "<pageId>" and section "<SectionId>"

    Examples:
      | projectId | roleName | roleDesc | permissionName | permissionDescription |TypeOfPermission|SectionKey           |pageId |SectionId|UserId| projectName|
      | 8635          | {random} | {random} | {random}       | {random}              |View            |addCaseMember        |5    |27      |Scv_mars_user_1|[blank]|

  @auxiliaryService @event-api-AS @API-EB-224 @API-EB-224-3 @API-TM @API-TM-Regression @API-Project-permission @shruti  @tenantManagerAPI @tm-api-TM 
  Scenario Outline: Adding Access Permissions to GIRD at Page level
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Search Project API and Run by ID "<projectId>"
    And I initiated and run Create Project Role API for Project Permission Group with "<projectId>" "<roleName>" "<roleDesc>"
    And I initiated Create Project Permission API
    And I run the create project permission API with "<permissionName>" "<permissionDescription>"
    And I initiated Config Permission API
    When I run Config Permission API using page payload to update with "<TypeOfPermission>" permission for key "<SectionKey>" and page "<pageId>"
    Then I verify success status for group permission api
    Given I initiated get project permission group details API by User ID "<UserId>" and Project Id "<projectId>"
    When I run get project permission group details API
    Then I verify permission "<TypeOfPermission>" is updated for page "<pageId>"

    Examples:
      | projectId | roleName | roleDesc | permissionName | permissionDescription |TypeOfPermission|SectionKey           |pageId |UserId| projectName|
      |  8635         | {random} | {random} | {random}       | {random}              |View            |addCaseMember        |5    |Scv_mars_user_1|[blank]|


  @API-CP-37683 @API-CP-37683-01 @CP-38169 @mital @tenantManagerAPI @API-TM-Regression
  Scenario Outline: Verify API to Configure permissions on newly created configurations - Page
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated Create Page API
    And User send Api call with payload "createPageApi" to create page
      | name      | random |
      | key       | random |
      | projectId | <projectId>     |
    Then I verify the response status code success with page creation status
    When I initiated get page by pageid
    And I verify page details are getting fetched
    And I initiated Create Project Permission API v2
    And User send Api call with payload "configPermissionsv2" to configure permission for "page"
      | read         | true  |
      | write        | true  |
      | noPermission | false |
      | applyMask    | false |
    Then I verify the response status code success with permission configuration status
    Examples:
      | projectName | projectId |
      |[blank]| 8635        |

  @API-CP-37683 @API-CP-37683-02 @CP-38169 @mital @tenantManagerAPI @API-TM-Regression
  Scenario Outline: Verify API to Configure permissions on newly created configurations - Template
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated Create template API
    And User send Api call with payload "createTemplateApi" to create template
      | name         | random         |
      | key          | random         |
      | pageId       | <pageId>       |
      | templateType | <templateType> |
    Then I verify the response status code success with template creation status
    When I initiated get template by templateId
    And I verify template details are getting fetched
    And I initiated Create Project Permission API v2
    And User send Api call with payload "configPermissionsv2" to configure permission for "template"
      | read         | true  |
      | write        | true  |
      | noPermission | false |
      | applyMask    | false |
    Then I verify the response status code success with permission configuration status
    Examples:
      | pageId | templateType |
      | 5868   | GRID         |
      | 5868   | FORM         |

  @API-CP-37683 @API-CP-37683-03 @CP-38169 @mital @tenantManagerAPI @API-TM-Regression
  Scenario Outline: Verify API to Configure permissions on newly created configurations - Field
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated Create Field API
    And User send Api call with payload "createFieldApi" to create field
      | name       | random       |
      | key        | random       |
      | templateId | <templateId> |
      | fieldType  | <fieldType>  |
    Then I verify the response status code success with field creation status
    When I initiated get field by fieldId
    And I verify field details are getting fetched
    And I initiated Create Project Permission API v2
    And User send Api call with payload "configPermissionsv2" to configure permission for "field"
      | read         | true  |
      | write        | true  |
      | noPermission | false |
      | applyMask    | false |
    Then I verify the response status code success with permission configuration status
    Examples:
      | projectName | templateId | fieldType             |
      |[blank]| 36         | Autocomplete Dropdown |

  @API-CP-37683 @API-CP-37683-04 @CP-38169 @mital @tenantManagerAPI @API-TM-Regression
  Scenario Outline: Verify API to Configure permissions on newly created configurations - Grid
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated Create template API
    And User send Api call with payload "createTemplateApi" to create template
      | name         | random         |
      | key          | random         |
      | pageId       | <pageId>       |
      | templateType | <templateType> |
    Then I verify the response status code success with template creation status
    When I initiated get template by templateId
    And I verify template details are getting fetched
    When I initiated Create Grid API
    And User send Api call with payload "createGridApi" to create grid
      | name       | random       |
      | key        | random       |
    Then I verify the response status code success with grid creation status
    When I initiated get grid by gridId
    And I verify grid details are getting fetched
    And I initiated Create Project Permission API v2
    And User send Api call with payload "configPermissionsv2" to configure permission for "grid"
      | read         | true  |
      | write        | true  |
      | noPermission | false |
      | applyMask    | false |
    Then I verify the response status code success with permission configuration status
    Examples:
      | pageId | templateType |
      | 5868   | GRID         |

  @API-CP-37683 @API-CP-37683-05 @CP-38169 @mital @tenantManagerAPI @API-TM-Regression
  Scenario Outline: Verify API to Configure permissions on newly created configurations - Grid Column
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Then I initiated Create Grid column API
    And User send Api call with payload "createGridColumnApi" to create grid column
      | name       | random       |
      | key        | random       |
      | gridId | <gridId> |
    Then I verify the response status code success with grid column creation status
    When I initiated get column details by columnId
    And I verify column details are getting fetched
    And I initiated Create Project Permission API v2
    And User send Api call with payload "configPermissionsv2" to configure permission for "gridColumn"
      | read         | true  |
      | write        | true  |
      | noPermission | false |
      | applyMask    | false |
    Then I verify the response status code success with permission configuration status
    Examples:
      | gridId |
      | 22      |