Feature: API-Provider Update

  @API-CP-23462 @API-CP-23462-01 @API-PP @API-PP-Regression @sobir
  Scenario: Use normalized physical address for provider update
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for creating provider with name "23462-2" with data
      | providers[0].npi | random |
      | providers[0].firstName | random |
      | providers[0].lastName | random |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after creating provider with data
      | warnings | null |

  @API-CP-27422 @API-CP-23462 @CP-23462-02 @API-PP @API-PP-Regression @sobir
  Scenario: Provider Update Use Raw Physical Address when Geo-Locate is Unavailable (BLCRM)
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for creating provider with name "23462-2" with data
      | providers[0].firstName                       | David             |
      | providers[0].lastName                        | Wonder            |
      | providers[0].npi                             | npi::             |
      | providers[0].providerAddress[0].addressLine1 | AAAA BBBB CCCC |
      | providers[0].providerAddress[0].city         | AtlantaWrong           |
      | providers[0].providerAddress[0].state        | AAA                |
      | providers[0].providerAddress[0].zipCode      |[blank]|
      | providers[0].providerAddress[0].countyCd     |[blank]|
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after creating provider with data
      | warnings | WAR066 |
    # @CP-27422 Search Providers with invalid / RAW address
    When I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.npi | 23462-2.providers[0].npi |
    Then I verify the response status code "200" with creation status "success"
    Then I verify first found provider search result from api with data
      | firstName | 23462-2.providers[0].firstName |
      | lastName  | 23462-2.providers[0].lastName  |
      | npi       | 23462-2.providers[0].npi       |

  @API-CP-27422 @API-CP-23462 @API-CP-23462-02 @API-PP @API-PP-Regression @sobir
  Scenario: Provider Update Use Raw Physical Address when Geo-Locate is Unavailable (IN-EB)
    When I will get the Authentication token for "IN-EB" in "Tenant Manager"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for creating provider with name "23462-3" with data
      | providers[0].firstName                       | David             |
      | providers[0].lastName                        | Wonder            |
      | providers[0].npi                             | random            |
      | providers[0].stateProviderId                 | stateProviderId:: |
      | providers[0].planCode                        | 400752220         |
      | providers[0].planName                        | ANTHEM            |
      | providers[0].providerAddress[0].addressLine1 | 123 wrong address |
      | providers[0].providerAddress[0].city         | Indianopolis      |
      | providers[0].providerAddress[0].state        | IN                |
      | providers[0].providerAddress[0].zipCode      | 12345             |
      | providers[0].providerAddress[0].countyCd     | 060               |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after creating provider with data
      | warnings | WAR066 |
    # @CP-27422 Search Providers with invalid / RAW address
    When I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.npi             | null::                               |
      | providerSearch.stateProviderId | 23462-3.providers[0].stateProviderId |
    Then I verify the response status code "200" with creation status "success"
    Then I verify first found provider search result from api with data
      | firstName       | 23462-3.providers[0].firstName       |
      | lastName        | 23462-3.providers[0].lastName        |
      | stateProviderId | 23462-3.providers[0].stateProviderId |

  @API-CP-23229 @API-CP-23229-01 @API-PP @API-PP-Regression @tenantManagerAPI @sobir
  Scenario: IN - Insert/Update Provider Data Scenarios 1.0 1.1 1.2
    When I will get the Authentication token for "IN-EB" in "Tenant Manager"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for creating provider with name "23229-1" with data
      | providers[0].firstName                       | Sobir                   |
      | providers[0].lastName                        | Wonder                  |
      | providers[0].middleName                      | Smith                   |
      | providers[0].stateProviderId                 | stateProviderId::       |
      | providers[0].planCode                        | 400752220               |
      | providers[0].planName                        | ANTHEM                  |
      | providers[0].providerAddress[0].addressLine1 | 1815 N Capitol Ave,#304 |
      | providers[0].providerAddress[0].city         | Indianapolis            |
      | providers[0].providerAddress[0].state        | IN                      |
      | providers[0].providerAddress[0].zipCode      | 46202                   |
      | providers[0].providerAddress[0].countyCd     | 060                     |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after creating provider with data
      | errors   | null |
      | warnings | null |
  # 1.0 Determine if mismatch duplicate record scenario
    And User send Api call with payload "createProvider" for creating provider with name "" with data
      | providers[0].firstName                       | Sobir                                |
      | providers[0].lastName                        | Wonder                               |
      | providers[0].middleName                      | SmithNEW                             |
      | providers[0].stateProviderId                 | 23229-1.providers[0].stateProviderId |
      | providers[0].planCode                        | 400752220                            |
      | providers[0].planName                        | ANTHEM                               |
      | providers[0].providerAddress[0].addressLine1 | 1815 N Capitol Ave,#304              |
      | providers[0].providerAddress[0].city         | Indianapolis                         |
      | providers[0].providerAddress[0].state        | IN                                   |
      | providers[0].providerAddress[0].zipCode      | 46202                                |
      | providers[0].providerAddress[0].countyCd     | 060                                  |
    Then I verify the response status code "400" with creation status "fail"
    Then I verify errors and warnings in the response after updating provider with data
      | errors   | ERR033 |
      | warnings | null   |
  # 1.0 Determine if mismatch duplicate record scenario
    And User send Api call with payload "createProvider" for creating provider with name "" with data
      | providers[0].firstName                       | SobirNEW                             |
      | providers[0].lastName                        | Wonder                               |
      | providers[0].middleName                      | Smith                                |
      | providers[0].stateProviderId                 | 23229-1.providers[0].stateProviderId |
      | providers[0].planCode                        | 400752220                            |
      | providers[0].planName                        | ANTHEM                               |
      | providers[0].providerAddress[0].addressLine1 | 1815 N Capitol Ave,#304              |
      | providers[0].providerAddress[0].city         | Indianapolis                         |
      | providers[0].providerAddress[0].state        | IN                                   |
      | providers[0].providerAddress[0].zipCode      | 46202                                |
      | providers[0].providerAddress[0].countyCd     | 060                                  |
    Then I verify the response status code "400" with creation status "fail"
    Then I verify errors and warnings in the response after updating provider with data
      | errors   | ERR032 |
      | warnings | null   |
  # 1.0 Determine if mismatch duplicate record scenario
    And User send Api call with payload "createProvider" for creating provider with name "" with data
      | providers[0].firstName                       | Sobir                                |
      | providers[0].lastName                        | WonderNEW                            |
      | providers[0].middleName                      | Smith                                |
      | providers[0].stateProviderId                 | 23229-1.providers[0].stateProviderId |
      | providers[0].planCode                        | 400752220                            |
      | providers[0].planName                        | ANTHEM                               |
      | providers[0].providerAddress[0].addressLine1 | 1815 N Capitol Ave,#304              |
      | providers[0].providerAddress[0].city         | Indianapolis                         |
      | providers[0].providerAddress[0].state        | IN                                   |
      | providers[0].providerAddress[0].zipCode      | 46202                                |
      | providers[0].providerAddress[0].countyCd     | 060                                  |
    Then I verify the response status code "400" with creation status "fail"
    Then I verify errors and warnings in the response after updating provider with data
      | errors   | ERR031 |
      | warnings | null   |
  # 1.0 Determine if mismatch duplicate record scenario
    And User send Api call with payload "createProvider" for creating provider with name "" with data
      | providers[0].firstName                       | SobirNEW                             |
      | providers[0].lastName                        | WonderNEW                            |
      | providers[0].middleName                      | SmithNEW                             |
      | providers[0].stateProviderId                 | 23229-1.providers[0].stateProviderId |
      | providers[0].planCode                        | 400752220                            |
      | providers[0].planName                        | ANTHEM                               |
      | providers[0].providerAddress[0].addressLine1 | 1815 N Capitol Ave,#304              |
      | providers[0].providerAddress[0].city         | Indianapolis                         |
      | providers[0].providerAddress[0].state        | IN                                   |
      | providers[0].providerAddress[0].zipCode      | 46202                                |
      | providers[0].providerAddress[0].countyCd     | 060                                  |
    Then I verify the response status code "400" with creation status "fail"
    Then I verify errors and warnings in the response after updating provider with data
      | errors   | ERR031, ERR032, ERR033 |
      | warnings | null                   |
    # 1.1 Different Plan Code Scenario
    And User send Api call with payload "createProvider" for creating provider with name "" with data
      | providers[0].firstName                       | Sobir                                |
      | providers[0].lastName                        | Wonder                               |
      | providers[0].middleName                      | Smith                                |
      | providers[0].stateProviderId                 | 23229-1.providers[0].stateProviderId |
      | providers[0].planCode                        | 700410350                            |
      | providers[0].planName                        | CARESOURCE                           |
      | providers[0].providerAddress[0].addressLine1 | 1815 N Capitol Ave,#304              |
      | providers[0].providerAddress[0].city         | Indianapolis                         |
      | providers[0].providerAddress[0].state        | IN                                   |
      | providers[0].providerAddress[0].zipCode      | 46202                                |
      | providers[0].providerAddress[0].countyCd     | 060                                  |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after updating provider with data
      | errors   | null |
      | warnings | null |
    # 1.2 Different Provider ID Scenario
    And User send Api call with payload "createProvider" for creating provider with name "" with data
      | providers[0].firstName                       | Sobir                   |
      | providers[0].lastName                        | Wonder                  |
      | providers[0].middleName                      | Smith                   |
      | providers[0].stateProviderId                 | stateProviderId::       |
      | providers[0].planCode                        | 400752220               |
      | providers[0].planName                        | ANTHEM                  |
      | providers[0].providerAddress[0].addressLine1 | 1815 N Capitol Ave,#304 |
      | providers[0].providerAddress[0].city         | Indianapolis            |
      | providers[0].providerAddress[0].state        | IN                      |
      | providers[0].providerAddress[0].zipCode      | 46202                   |
      | providers[0].providerAddress[0].countyCd     | 060                     |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after updating provider with data
      | errors   | null |
      | warnings | null |

  @API-CP-23229 @API-CP-23229-02 @API-PP @API-PP-Regression @sobir
  Scenario: IN - Insert/Update Provider Data Scenarios 2.0 2.1 2.2 2.3 2.4 2.5
    When I will get the Authentication token for "IN-EB" in "Tenant Manager"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for creating provider with name "23229-2" with data
      | providers[0].firstName                       | Sobir                    |
      | providers[0].lastName                        | Wonder                   |
      | providers[0].middleName                      | Smith                    |
      | providers[0].stateProviderId                 | stateProviderId::        |
      | providers[0].planCode                        | 400752220                |
      | providers[0].planName                        | ANTHEM                   |
      | providers[0].groupName                       | MEDICINE WITHOUT BORDERS |
      | providers[0].groupId                         | providerGroupId::        |
      | providers[0].providerAddress[0].addressLine1 | 1815 N Capitol Ave,#304  |
      | providers[0].providerAddress[0].city         | Indianapolis             |
      | providers[0].providerAddress[0].state        | IN                       |
      | providers[0].providerAddress[0].zipCode      | 46202                    |
      | providers[0].providerAddress[0].countyCd     | 060                      |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after creating provider with data
      | errors   | null |
      | warnings | null |
    # 2.1 Different Group Group Name Scenario
    And User send Api call with payload "createProvider" for creating provider with name "" with data
      | providers[0].firstName                       | Sobir                                |
      | providers[0].lastName                        | Wonder                               |
      | providers[0].middleName                      | Smith                                |
      | providers[0].stateProviderId                 | 23229-2.providers[0].stateProviderId |
      | providers[0].planCode                        | 400752220                            |
      | providers[0].planName                        | ANTHEM                               |
      | providers[0].groupName                       | MEDICINE WITHOUT BORDERS NEW         |
      | providers[0].groupId                         | 23229-2.providers[0].groupId         |
      | providers[0].providerAddress[0].addressLine1 | 1815 N Capitol Ave,#304              |
      | providers[0].providerAddress[0].city         | Indianapolis                         |
      | providers[0].providerAddress[0].state        | IN                                   |
      | providers[0].providerAddress[0].zipCode      | 46202                                |
      | providers[0].providerAddress[0].countyCd     | 060                                  |
    Then I verify the response status code "400" with creation status "fail"
    Then I verify errors and warnings in the response after updating provider with data
      | errors   | ERR035 |
      | warnings | null   |
    # 2.1 Different Group ID or Group Name Scenario
    And User send Api call with payload "createProvider" for creating provider with name "" with data
      | providers[0].firstName                       | Sobir                                |
      | providers[0].lastName                        | Wonder                               |
      | providers[0].middleName                      | Smith                                |
      | providers[0].stateProviderId                 | 23229-2.providers[0].stateProviderId |
      | providers[0].planCode                        | 400752220                            |
      | providers[0].planName                        | ANTHEM                               |
      | providers[0].groupName                       | MEDICINE WITHOUT BORDERS             |
      | providers[0].groupId                         | providerGroupId::                    |
      | providers[0].providerAddress[0].addressLine1 | 1815 N Capitol Ave,#304              |
      | providers[0].providerAddress[0].city         | Indianapolis                         |
      | providers[0].providerAddress[0].state        | IN                                   |
      | providers[0].providerAddress[0].zipCode      | 46202                                |
      | providers[0].providerAddress[0].countyCd     | 060                                  |
    Then I verify the response status code "400" with creation status "fail"
    Then I verify errors and warnings in the response after updating provider with data
      | errors   | ERR035 |
      | warnings | null   |
    # 2.1 Different Group ID and Group Name Scenario
    And User send Api call with payload "createProvider" for creating provider with name "" with data
      | providers[0].firstName                       | Sobir                                |
      | providers[0].lastName                        | Wonder                               |
      | providers[0].middleName                      | Smith                                |
      | providers[0].stateProviderId                 | 23229-2.providers[0].stateProviderId |
      | providers[0].planCode                        | 400752220                            |
      | providers[0].planName                        | ANTHEM                               |
      | providers[0].groupName                       | MEDICINE WITHOUT BORDERS NEW         |
      | providers[0].groupId                         | providerGroupId::                    |
      | providers[0].providerAddress[0].addressLine1 | 1815 N Capitol Ave,#304              |
      | providers[0].providerAddress[0].city         | Indianapolis                         |
      | providers[0].providerAddress[0].state        | IN                                   |
      | providers[0].providerAddress[0].zipCode      | 46202                                |
      | providers[0].providerAddress[0].countyCd     | 060                                  |
    Then I verify the response status code "400" with creation status "fail"
    Then I verify errors and warnings in the response after updating provider with data
      | errors   | ERR035, ERR035 |
      | warnings | null           |
    # 2.2 Different Location Scenario
    And User send Api call with payload "createProvider" for creating provider with name "" with data
      | providers[0].firstName                       | Sobir                                |
      | providers[0].lastName                        | Wonder                               |
      | providers[0].middleName                      | Smith                                |
      | providers[0].stateProviderId                 | 23229-2.providers[0].stateProviderId |
      | providers[0].planCode                        | 400752220                            |
      | providers[0].planName                        | ANTHEM                               |
      | providers[0].groupName                       | MEDICINE WITHOUT BORDERS             |
      | providers[0].groupId                         | 23229-2.providers[0].groupId         |
      | providers[0].providerAddress[0].addressLine1 | 8801 N MERIDIAN STREET               |
      | providers[0].providerAddress[0].city         | Indianapolis                         |
      | providers[0].providerAddress[0].state        | IN                                   |
      | providers[0].providerAddress[0].zipCode      | 46260                                |
      | providers[0].providerAddress[0].countyCd     | 049                                  |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after updating provider with data
      | errors   | null |
      | warnings | null |
    # 2.3 Different Group ID/Name and Location Scenario
    And User send Api call with payload "createProvider" for creating provider with name "" with data
      | providers[0].firstName                       | Sobir                                |
      | providers[0].lastName                        | Wonder                               |
      | providers[0].middleName                      | Smith                                |
      | providers[0].stateProviderId                 | 23229-2.providers[0].stateProviderId |
      | providers[0].planCode                        | 400752220                            |
      | providers[0].planName                        | ANTHEM                               |
      | providers[0].groupName                       | MEDICINE WITHOUT BORDERS NEW         |
      | providers[0].groupId                         | providerGroupId::                    |
      | providers[0].providerAddress[0].addressLine1 | 7901 MORNINGSIDE DR                  |
      | providers[0].providerAddress[0].city         | Indianapolis                         |
      | providers[0].providerAddress[0].state        | IN                                   |
      | providers[0].providerAddress[0].zipCode      | 46240                                |
      | providers[0].providerAddress[0].countyCd     | 6381                                 |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after updating provider with data
      | errors   | null |
      | warnings | null |
    # 2.4 Removal of Group ID and Group Name Scenario
    And User send Api call with payload "createProvider" for creating provider with name "" with data
      | providers[0].firstName                       | Sobir                                |
      | providers[0].lastName                        | Wonder                               |
      | providers[0].middleName                      | Smith                                |
      | providers[0].stateProviderId                 | 23229-2.providers[0].stateProviderId |
      | providers[0].planCode                        | 400752220                            |
      | providers[0].planName                        | ANTHEM                               |
      | providers[0].groupName                       | null::                               |
      | providers[0].groupId                         | null::                               |
      | providers[0].providerAddress[0].addressLine1 | 1815 N Capitol Ave,#304              |
      | providers[0].providerAddress[0].city         | Indianapolis                         |
      | providers[0].providerAddress[0].state        | IN                                   |
      | providers[0].providerAddress[0].zipCode      | 46202                                |
      | providers[0].providerAddress[0].countyCd     | 060                                  |
    Then I verify the response status code "400" with creation status "fail"
    Then I verify errors and warnings in the response after updating provider with data
      | errors   | ERR035, ERR035 |
      | warnings | null           |
    # 2.5 Removal of Group ID and Group Name with Different Location Scenario
    And User send Api call with payload "createProvider" for creating provider with name "" with data
      | providers[0].firstName                       | Sobir                                |
      | providers[0].lastName                        | Wonder                               |
      | providers[0].middleName                      | Smith                                |
      | providers[0].stateProviderId                 | 23229-2.providers[0].stateProviderId |
      | providers[0].planCode                        | 400752220                            |
      | providers[0].planName                        | ANTHEM                               |
      | providers[0].groupName                       | null::                               |
      | providers[0].groupId                         | null::                               |
      | providers[0].providerAddress[0].addressLine1 | 3850 SHORE DR #117                   |
      | providers[0].providerAddress[0].city         | Indianapolis                         |
      | providers[0].providerAddress[0].state        | IN                                   |
      | providers[0].providerAddress[0].zipCode      | 46254                                |
      | providers[0].providerAddress[0].countyCd     | 0049                                 |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after updating provider with data
      | errors   | null |
      | warnings | null |
  # 2.0 Retrieve Geo-Codes
    And User send Api call with payload "createProvider" for creating provider with name "" with data
      | providers[0].firstName                       | Sobir                                |
      | providers[0].lastName                        | Wonder                               |
      | providers[0].middleName                      | Smith                                |
      | providers[0].stateProviderId                 | 23229-2.providers[0].stateProviderId |
      | providers[0].planCode                        | 400752220                            |
      | providers[0].planName                        | ANTHEM                               |
      | providers[0].groupName                       | MEDICINE WITHOUT BORDERS             |
      | providers[0].groupId                         | 23229-2.providers[0].groupId         |
      | providers[0].providerAddress[0].addressLine1 | 123 wrong address                    |
      | providers[0].providerAddress[0].city         | Indianopolis                         |
      | providers[0].providerAddress[0].state        | IN                                   |
      | providers[0].providerAddress[0].zipCode      | 12345                                |
      | providers[0].providerAddress[0].countyCd     | 060                                  |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after updating provider with data
      | errors   | null   |
      | warnings | WAR066 |


  @API-CP-13356 @API-CP-13220 @API-PP @API-PP-Regression @sobir
  Scenario: CP-13356 Provider Search API Enhancement - Match Size Parameter w/ # of Response Results
  CP-13220 Provider Search API Enhancement - Add Total Records to Response
    When I will get the Authentication token for "BLCRM" in "Tenant Manager"
    When I initiate digital Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.zipCode | 30339   |
      | providerSearch.city    | Atlanta |
      | providerSearch.state   | GA      |
      | from                   | 0       |
      | size                   | 3       |
    Then I verify provider search response from api with data
      | providers count | 3 |
      | totalElements   | 3 |

  @API-CP-13991 @API-CP-13991-01 @API-PP @API-PP-Regression @sobir
  Scenario: Plan & Provider Contract Change 1.0 Remove planProvderId from Request
    When I will get the Authentication token for "BLCRM" in "Tenant Manager"
    When I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.planProviderId | delete:: |
      | providerSearch.city           | Atlanta  |
      | providerSearch.state          | GA       |
    Then I verify provider search response from api with data
      | status          | success |
      | providers count | > 1     |

  @API-CP-13991 @API-CP-28665 @API-CP-13991-03 @API-PP @API-PP-Regression @sobir
  Scenario: Plan & Provider Contract Change 3.0 Mandatory Configuration
    When I will get the Authentication token for "BLCRM" in "Tenant Manager"
    When I initiate Provider Search API
    # AC 1.1 State or Country Mandatory field (Updated) AC 2.0 City or Zip as Mandatory Field
    # country and state both included (positive)
    And I create a request payload with data for search field values and post
      | providerSearch.country  | USA   |
      | providerSearch.state    | GA    |
      | providerSearch.zipCode  | 30339 |
      | providerSearch.distance | 15    |
    Then I verify provider search response from api with data
      | status          | success |
      | providers count | > 1     |
    # only country included(positive)
    And I create a request payload with data for search field values and post
      | providerSearch.country  | USA   |
      | providerSearch.zipCode  | 30339 |
      | providerSearch.distance | 15    |
    Then I verify provider search response from api with data
      | status          | success |
      | providers count | > 1     |
    # only state included(positive)
    And I create a request payload with data for search field values and post
      | providerSearch.state    | GA    |
      | providerSearch.zipCode  | 30339 |
      | providerSearch.distance | 15    |
    Then I verify provider search response from api with data
      | status          | success |
      | providers count | > 1     |
    # country and state both NOT included (negative)
    And I create a request payload with data for search field values and post
      | providerSearch.zipCode  | 30339 |
      | providerSearch.distance | 15    |
    Then I verify provider search response from api with data
      | status    | fail                     |
      | errorCode | Mandatory fields Missing |
    #************************************************************
    # If City is provided then State must be in request (City + state) (positive)
    # If City and ZIP both are provided then State or Country must be in request (City + ZIP + State OR City + ZIP + Country) (positive)
    # (City + ZIP + State)
    And I create a request payload with data for search field values and post
      | providerSearch.city     | Atlanta |
      | providerSearch.state    | GA      |
      | providerSearch.zipCode  | 30339   |
      | providerSearch.distance | 15      |
    Then I verify provider search response from api with data
      | status          | success |
      | providers count | > 1     |
    # (City + ZIP + Country)
    And I create a request payload with data for search field values and post
      | providerSearch.city     | Atlanta |
      | providerSearch.country  | USA     |
      | providerSearch.zipCode  | 30339   |
      | providerSearch.distance | 15      |
    Then I verify provider search response from api with data
      | status          | success |
      | providers count | > 1     |
    #***********************************************
    # If City is provided then State must be in request (City + state) (negative)
    And I create a request payload with data for search field values and post
      | providerSearch.city     | Atlanta |
      | providerSearch.zipCode  | 30339   |
      | providerSearch.distance | 15      |
    Then I verify provider search response from api with data
      | status    | fail                     |
      | errorCode | Mandatory fields Missing |
    And I create a request payload with data for search field values and post
      | providerSearch.state | GA |
    Then I verify provider search response from api with data
      | status          | success |
      | providers count | > 1     |
    #*******************************************************************
    # CP-28665 AC 2.0 City or Zip as NOT Mandatory Field
    And I create a request payload with data for search field values and post
      | providerSearch.country  | USA |
      | providerSearch.state    | GA  |
      | providerSearch.distance | 15  |
    Then I verify provider search response from api with data
      | status          | success |
      | providers count | > 1     |
    #**********************************************************
    # CP-28665 AC 3.0 Country Rules
    And I create a request payload with data for search field values and post
      | providerSearch.state    | GA      |
      | providerSearch.city     | Atlanta |
      | providerSearch.distance | 15      |
    Then I verify provider search response from api with data
      | status          | success |
      | providers count | > 1     |

  @API-CP-13991 @API-PP @API-PP-Regression @sobir
  Scenario: Provider Search: Searching by city sometimes is returning no results
    When I will get the Authentication token for "BLCRM" in "Tenant Manager"
    When I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.zipCode  | 30339 |
      | providerSearch.state    | GA    |
      | providerSearch.distance | 15    |
    Then I verify first found provider search result from api with data
      | providerPhoneNumber is present in Mailing address       |[blank]|
      | providerPhoneNumber is present in Physical address      |[blank]|
      | normalizedPhysicalAddress is present in providerAddress |[blank]|

  @API-CP-27246 @API-PP @API-PP-Regression @sobir
  Scenario: Plan & Provider Contract Change 4.0 Move Phone# from Normalized address to Physical and Mailing
    When I will get the Authentication token for "BLCRM" in "Tenant Manager"
    When I initiate digital Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.countyName | bibb |
      | providerSearch.state      | GA   |
      | providerSearch.country    | USA  |
      | from                      | 0    |
      | size                      | 100  |
    Then I verify provider search response from api with data
      | status          | success |
      | providers count | > 1     |
    And I create a request payload with data for search field values and post
      | providerSearch.city     | Macon |
      | providerSearch.state    | GA    |
      | providerSearch.country  | USA   |
      | providerSearch.distance | 25    |
      | from                    | 0     |
      | size                    | 100   |
    Then I verify provider search response from api with data
      | status          | success |
      | providers count | > 1     |


  @API-CP-20538 @API-CP-20538-01 @planProviderAPI @API-TM @API-PP-Regression @planProvider @mital
  Scenario:  Validate the Subprogram type
    When I will get the Authentication token for "BLCRM" in "Tenant Manager"
    Given I initiated Plan Management for service region API
    Given I get the jwt token
    When I upload the service regions file "Region_Configuration_BLCRM_AC_Sub-Program-Type.xlsx" with fileStatus "success"
    Then I verify the file upload message "Upload Successful - Please Upload Plan File"
    Given I initiated Plan Management for Plan API
    When I upload the plan regions file "Plan SubProgram Type different characters BLCRM" with fileStatus "Upload Successful"
    Then I verify the plan file upload message "Upload Successful"
    Given I initiated sub get program type via API
    When I can provide details with following information to get sub program type
      | serviceRegion |  projectName |
      | programType   |[blank]|
    When I run get subprogram Type
    Then I can verify get Sub Program Type API response is not empty
    Then I verify no. of subprogram types records match with uploaded file

  @API-CP-20538 @API-CP-20538-02 @planProviderAPI @API-TM @API-PP-Regression @planProvider @mital
  Scenario:  Validate the Program type
    When I will get the Authentication token for "BLCRM" in "Tenant Manager"
    Given I initiated Plan Management for service region API
   # Given I get the jwt token
    When I upload the service regions file "Region_Configuration_BLCRM_AC_Program-Type.xlsx" with fileStatus "success"
    Then I verify the file upload message "Upload Successful - Please Upload Plan File"
    Given I initiated Plan Management for Plan API
    When I upload the plan regions file "Plan Program Type different characters BLCRM" with fileStatus "Upload Successful"
    Then I verify the plan file upload message "Upload Successful"
    Given I initiated get program type via API
    When I run get Program Type
    Then I can verify get Program Type API response is not empty
    Then I verify no. of program types records match with uploaded file

  @API-CP-24348 @API-CP-24348-01 @mital @API-PP-Regression @API-PP
  Scenario: Global Provider Search Results - Distance Default
    When I will get the Authentication token for "BLCRM" in "CRM"
    Then I initiate Provider Search API
    And I create a request payload with data for provider search field values and post
      | providerSearch.addressLine1 | 426 Exchange |
      | providerSearch.city | Bethlehem |
    Then I verify the response status code "200" with creation status "success"
    Then Verify provider search results sorted from nearest to farthest
    And I create a request payload with data for provider search field values and post
      | providerSearch.addressLine1 | 426 Exchange |
      | providerSearch.zipCode | 30620 |
    Then I verify the response status code "200" with creation status "success"
    Then Verify provider search results sorted from nearest to farthest
    And I create a request payload with data for provider search field values and post
      | providerSearch.city | Bethlehem |
      | providerSearch.zipCode | 30620 |
    Then I verify the response status code "200" with creation status "success"
    Then Verify provider search results sorted from nearest to farthest


  @API-CP-24348 @API-CP-24348-02 @API-CP-24348-03 @mital @API-PP-Regression @API-PP
  Scenario: Global Provider Search Results - Display (only) Fields
  Global Provider Search Results - Display (only) Fields - (Expanded View)
    When I will get the Authentication token for "BLCRM" in "CRM"
    Then I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.firstName | MARIANNA |
      | providerSearch.lastName | NGUYEN |
    Then I verify the response status code "200" with creation status "success"
    Then Verify provider search results displaying values
      | firstName                                               | MARIANNA          |
      | lastName                                                | NGUYEN            |
      | planName                                                | PEACH STATE       |
      | programTypeCd                                           | Medicaid          |
      | providerAddress[0].addressLine1                         | 770 Walnut Street |
      | providerAddress[0].city                                 | Macon             |
      | providerAddress[0].normalizedPhysicalAddress.countyName | Bibb                   |
      | providerAddress[0].state                                | GA                |
      | providerAddress[0].zipCode                              | 312012635         |
      | providerAddress[0].phoneNumbers[0].phoneNumber          | 4787874266        |
      | acceptNewPatientsInd                                    | true              |
      | providerLanguages[0].languageTypeCd                     | EN                |
      | pcpFlag                                                 | Y                 |
      | groupName                                               |[blank]|
      | acceptObInd                                             | false             |
      | genderCd                                                | F                 |
      | ageLowLimit                                             | 18.0              |
      | ageHighLimit                                            | 99.0              |
      | npi                                                     | 1023368040        |



  @aswath-CM @API-PP @API-PP-Regression @sobir
  Scenario: Use normalized physical address for provider update

    Given I initiated get all cases details API

