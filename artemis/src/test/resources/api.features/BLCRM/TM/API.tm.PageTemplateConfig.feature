@TMENhancement
Feature: API-Tenant Manager: Tenant Manager Enhancement Page,Template,Grid,GridColumns,Fields

  @API-CP-36491 @API-CP-36491-01 @mital @tenantManagerAPI @API-TM-Regression
  Scenario Outline: Verify APIs for Create Page, get page by PageId & get pages in given ProjectId
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    #Verify APIs for Create Page
    When I initiated Create Page API
    And User send Api call with payload "createPageApi" to create page
      | name      | random      |
      | key       | random      |
      | projectId | <projectId> |
    Then I verify the response status code success with page creation status
    #Verify APIs for getting page by PageId
    When I initiated get page by pageid
    And I verify page details are getting fetched
    #Verify APIs for getting pages in given ProjectId
    When I initiated get list of pages API in project ID "<projectId>"
    Then I verify the pages got created and can be fetched
    Examples:
      | projectName | projectId |
      |[blank]| 8635      |

  @API-CP-36491 @API-CP-36491-02 @mital @tenantManagerAPI @API-TM-Regression
  Scenario Outline: Verify creation of Create template APIs,get template by templateId & get templates in given pageId
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    #Verify create Template API
    When I initiated Create template API
    And User send Api call with payload "createTemplateApi" to create template
      | name         | random         |
      | key          | random         |
      | pageId       | <pageId>       |
      | templateType | <templateType> |
    Then I verify the response status code success with template creation status
    #Verify get template by template Id API
    When I initiated get template by templateId
    And I verify template details are getting fetched
    #Verify get templates for given page Id
    When I initiated get list of templates API in page ID "<pageId>"
    Then I verify the templates got created and can be fetched

    Examples:
      | pageId | templateType |
      | 5852   | GRID         |
      | 5852   | FORM         |

  @API-CP-36332 @API-CP-36332-01 @mital @tenantManagerAPI @API-TM-Regression
  Scenario Outline: Verify APIs for Create fields, get field by fieldId & get fields in given ProjectId
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    #Verify APIs for Create field
    When I initiated Create Field API
    And User send Api call with payload "createFieldApi" to create field
      | name       | random       |
      | key        | random       |
      | templateId | <templateId> |
      | fieldType  | <fieldType>  |
    Then I verify the response status code success with field creation status
    #Verify APIs for getting field by fieldId
    When I initiated get field by fieldId
    And I verify field details are getting fetched
    #Verify APIs for getting fields in given ProjectId
    When I initiated get list of fields API in project
    Then I verify the fields got created and can be fetched
    Examples:
      | projectName | templateId | fieldType             |
      |[blank]| 1          | Autocomplete Dropdown |

  @API-CP-36330 @API-CP-36330-01 @mital @tenantManagerAPI @API-TM-Regression
  Scenario Outline: Verify APIs for Create fields of UI field types
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    #Verify APIs for Create field
    When I initiated Create Field API
    And User send Api call with payload "createFieldApi" to create field
      | name       | random       |
      | key        | random       |
      | templateId | <templateId> |
      | fieldType  | <fieldType>  |
    Then I verify the response status code success with field creation status
    #Verify APIs for getting field by fieldId
    When I initiated get field by fieldId
    And I verify field details are getting fetched
    #Verify APIs for getting fields in given ProjectId
    When I initiated get list of fields API in project
    Then I verify the fields got created and can be fetched
    Examples:
      | projectName | templateId | fieldType             |
      |[blank]| 1          | Textfield             |
      |[blank]| 1          | Timepicker            |
      |[blank]| 1          | Radio Button          |
      |[blank]| 1          | Dropdown              |
      |[blank]| 1          | Checkbox              |
      |[blank]| 1          | Autocomplete Dropdown |


  @API-CP-36334 @API-CP-36334-01 @mital @tenantManagerAPI @API-TM-Regression
  Scenario Outline: Verify APIs for Create grids, get grid by gridId
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
    #Verify APIs for Create grid
    When I initiated Create Grid API
    And User send Api call with payload "createGridApi" to create grid
      | name | random |
      | key  | random |
    Then I verify the response status code success with grid creation status
    #Verify APIs for getting grid by gridId
    When I initiated get grid by gridId
    And I verify grid details are getting fetched
    Examples:
      | pageId | templateType |
      | 5852   | GRID         |

  @API-CP-36334 @API-CP-36334-02 @mital @tenantManagerAPI @API-TM-Regression
  Scenario Outline: Verify APIs for Create grids columns, get gridColumns by columnId
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    #Verify APIs for Create grid column
    Then I initiated Create Grid column API
    And User send Api call with payload "createGridColumnApi" to create grid column
      | name   | random   |
      | key    | random   |
      | gridId | <gridId> |
    Then I verify the response status code success with grid column creation status
    #Verify APIs for getting grid column by columnId
    When I initiated get column details by columnId
    And I verify column details are getting fetched
    Examples:
      | gridId |
      | 1      |

  @API-CP-40069 @API-CP-40069-01 @mital @tenantManagerAPI @API-TM-Regression
  Scenario Outline: Verify api to return list of fields by page,template and tenant in CP
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated get Page,Template,Field,Grid details API
    And User send Api call with payload "apiGetObjectDetails" to get page, template, field, grid details
      | projectName | <projectName> |
      | pageKey     | <pageKey>     |
      | templateKey | <templateKey> |
    Then I verify the response status code success for getting details for page,template,field,grid
    And I verify the response status as "<status>" for template in project "<projectName>"
    Examples:
      | projectName | pageKey       | templateKey        | status  |
      | BLCRM       | ActiveContact | RegressionTemplate | success |
      | TestProject | ActiveContact | testTemplate       | fail    |

  @API-CP-40069 @API-CP-40069-02 @mital @tenantManagerAPI @API-TM-Regression
  Scenario Outline: Verify api to return list of fields by page,template and tenant in CP- pass roleId to get permissions
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated get Page,Template,Field,Grid details API
    And User send Api call with payload "apiGetObjectDetailsWithPermission" to get page, template, field, grid details
      | projectName | <projectName> |
      | pageKey     | <pageKey>     |
      | templateKey | <templateKey> |
      | roleId      | <roleId>      |
    Then I verify the response status code success for getting details for page,template,field,grid
    Then I verify the response status as "<status>" and permissions for template
    Examples:
      | projectName | pageKey       | templateKey        | roleId | status  |
      | BLCRM       | ActiveContact | RegressionTemplate | 346    | success |
      | TestProject | ActiveContact | testTemplate       | 854    | Fail    |
