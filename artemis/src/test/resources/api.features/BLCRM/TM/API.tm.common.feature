Feature: API-Tenant Manager: Common Controller


@API-CRM-291 @API-TM @API-Project @Sujoy @tenantManagerAPI @tm-api-TM
Scenario Outline: Get employee detail using API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated get employee detail by MaxID "<maerskID>"
    When I run the employee detail API by MaxID
    Then I can verify ge user approver detail API response will be "<success>"
    Examples:
        | maerskID    | success |projectName|
        | 102040       | True    |[blank]|
