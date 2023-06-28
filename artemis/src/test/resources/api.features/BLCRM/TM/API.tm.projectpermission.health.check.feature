Feature: API-Tenant Manager: Project Permission Controller

  @auxiliaryService @search-services-AS @API-HealthCheck @API-TM-SmokeTest @Sujoy  @tenantManagerAPI @tm-api-TM 
  Scenario Outline: Get Project Permission list using API Health Check GET mars/tm/project/permission/{projectId}
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I can get project id for permission by project name "<projectName>"
    When I initiated get project permission API by Projecr ID ""
    And I run get project permission API with Query parameters "<page>","<size>" and "<sort>"
    Then I can verify get permission response success is "<Success>"
    And I can verify get permission list size is equal or less than "<size>"
    Examples:
      | projectName   | page | size | sort                | Success |
      |[blank]| 0    | 5    | permissionGroupName | True    |

  @API-HealthCheck @API-TM-SmokeTest @Shruti  @tenantManagerAPI @tm-api-TM 
  Scenario Outline: Get Project Permission group details using API Health Check
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated get project permission group details API by User ID "<UserId>" and Project Id "<projectId>"
    When I run get project permission group details API
    Then I can verify get permission response success is "<Success>"

    Examples:
      | projectId   | UserId          | Success | projectName   |
      |[blank]|Scv_mars_user_1  |True     |[blank]|
