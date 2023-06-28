Feature: API-Tenant Manager: Common Controller

@API-HealthCheck @API-TM-SmokeTest @Sujoy @tenantManagerAPI @tm-api-TM
Scenario Outline: Common Health Check - GET mars/tm/common/activedirectory/{maxId}
    Given I initiated get employee detail by MaxID "<maerskID>"
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I run the employee detail API by MaxID
    Then I can verify ge user approver detail API response will be "<success>"
    Examples:
        | maerskID    | success |projectName|
        | 102040       | True    |[blank]|
