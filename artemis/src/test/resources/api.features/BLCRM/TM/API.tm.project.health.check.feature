@ProjectCreation
Feature: API-Tenant Manager: Project Controller

  @API-HealthCheck @API-TM-SmokeTest @Sujoy @tenantManagerAPI @tm-api-TM @Test1234
  Scenario Outline: Create Project API Health Check PUT /mars/tm/project
    Given I initiated Create Project API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I can provide project information to create a "<Provisioning>" project on "<state>"
    And I run the create project API
    Then I can search the project by project name to validate is "<success>"
    Examples:
      |state|Provisioning|success|projectName|
      |	AK  |Active      |TRUE	 |[blank]|

  @API-HealthCheck @API-TM-SmokeTest @Sujoy @FailedSmoke0417 @tenantManagerAPI @tm-api-TM @api-smoke-devops
  Scenario Outline: Search Projects by ID - API Health Check GET /mars/tm/project/{projectId}
#    Given I initiated Search Project API
#    When I can Search Project by "<Node>" with value "<value>"
#    And I run the search project API
#    And I can get ProjectID
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Search Project API By Project ID "8166"
    And I run the search project API By ProjectID
    Then I can verify Project ID on project search API response will be "<success>"
    Examples:
      | Node           | value        | success |projectName|
      | state          | Az           | True    |[blank]|

  @API-HealthCheck @API-TM-SmokeTest @Sujoy @tenantManagerAPI @tm-api-TM
  Scenario Outline:: Validate Project Lists using API Health Check GET /mars/tm/projects
    Given I initiated Get Project List API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I can provide all blank project information
    And I run the get project list API
    Then I can search the project by project name to validate is success
    Examples:
    |projectName|
    |[blank]|

  @auxiliaryService @search-services-AS @lookup-api-AS @API-HealthCheck @API-TM-SmokeTest @Sujoy @tenantManagerAPI @tm-api-TM 
  Scenario Outline: Search Projects using API Health Check POST /mars/tm/projects
    Given I initiated Search Project API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I can Search Project by "<Node>" with value "<value>"
    And I run the search project API
    Then I can verify "<Node>" with value "<value>" on project API response will be "<success>"
    Examples:
      | Node           | value        | success |projectName|
      | state          | Tx           | True    |[blank]|


  @API-HealthCheck @API-TM-SmokeTest @Sujoy @tenantManagerAPI @tm-api-TM 
  Scenario Outline: Get approver detail health check GET mars/tm/project/approver/{projectId}
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated project approver API by project name "<projectName>"
    When I can get the project approver detail
    Then I can verify get user approver detail API response will be "<success>"
    Examples:
      | projectName    | success |projectName|
      | BLCRM          | True    |[blank]|
