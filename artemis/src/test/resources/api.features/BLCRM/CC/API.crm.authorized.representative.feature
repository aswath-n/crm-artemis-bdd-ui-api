#Feature: API: Authorized Representative Feature
#
#  @API-CP-333 @API-CP-333-01 @asad @API-CC @API-CRM-Regression
#  Scenario Outline: Verify the result of created Authorized RepresentativeStatus
#    When I will get the Authentication token for "" in "CRM"
#    Given I initiate case loader API for Authorized Representative
#    When I create case from case loader for Authorized Representative with consumer role "case-Primary Individual"
#    When I initiated Authorized Representative API
#    When I "create" Authorized Representative API based on "<startDate>" and "<endDate>"
#    Then I verify the status of "created" Authorized RepresentativeStatus in API
#    Examples:
#      | startDate  | endDate    |
#      | 2019-09-15 |[blank]|
#      | Future     | Future     |
#      | 2019-09-15 | Future     |
#      | 2019-01-01 | 2020-01-01 |
#
#  @API-CP-333 @API-CP-333-02 @asad  @API-CC @API-CRM-Regression #Fails due to CP-10858
#  Scenario Outline: Verify the result of udpated Authorized RepresentativeStatus
#    When I will get the Authentication token for "" in "CRM"
#    Given I initiate case loader API for Authorized Representative
#    When I create case from case loader for Authorized Representative with consumer role "case-Primary Individual"
#    When I initiated Authorized Representative API
#    When I "update" Authorized Representative API based on "<startDate>" and "<endDate>"
#    Then I verify the status of "updated" Authorized RepresentativeStatus in API
#    Examples:
#      | startDate  | endDate    |
#      | 2019-09-15 |[blank]|
#      | Future     | Future     |
#      | 2019-09-15 | Future     |
#      | 2019-01-01 | 2020-01-01 |