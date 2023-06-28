Feature: API-Tenant Manager: Project Role Controller

  @auxiliaryService @search-services-AS @event-api-AS @lookup-api-AS @API-HealthCheck @API-TM-SmokeTest @Sujoy @tenantManagerAPI @tm-api-TM 
  Scenario Outline: Create Project Role API PUT mars/tm/project/role
    Given I initiated Create Project Role API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I can provide role details with "<projectId>" "<roleName>" "<roleDesc>"
    And I run the create project role API
    Then I can search the project role by role name to validate is "<success>"
    Examples:
        |projectId  |roleName |roleDesc    |success|projectName|
        |90          |{random} |{random}    |TRUE   |[blank]|

  @auxiliaryService @search-services-AS @lookup-api-AS @API-HealthCheck @API-TM-SmokeTest @Vinuta @tenantManagerAPI @tm-api-TM 
  Scenario Outline: Search Project role API  GET mars/tm/project/role/{projectId}
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Project Role By Project ID via API "<projectId>"
    Then I can verify on project role search API response will be "<success>"
    And I can verify each role has all required information displayed
    Examples:
        | projectId | success |projectName|
        | 90         | TRUE    |[blank]|

