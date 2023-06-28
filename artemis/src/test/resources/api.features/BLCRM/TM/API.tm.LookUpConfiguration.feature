@API-TM-LOOKUP-CON
Feature: API-Tenant Manager:Look Up Configuration Controller

  #muting due creating random test in lookup

  #@API-CP-1359 @API-CP-1359-01 @API-TM-Regression @API-TM-SmokeTest @kamil
  Scenario Outline: Select DataBase for LOOKUP CONFIGURATION
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I initiated Select DataBase for LOOKUP CONFIGURATION
    Given Get Select DataBase for LOOKUP CONFIGURATION
      | dataBase | mars-consumer-blcrm |
      | bservice | consumer |
    And Verify response status code is 200
    Then Verify response for Select DataBase
    Examples:
      | projectName |
      |[blank]|


  @API-CP-1359 @API-CP-1359-02 @kamil
  Scenario Outline: Select ENUM Data Table for LOOKUP CONFIGURATION
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I initiated Select Data Table for LOOKUP CONFIGURATION
    Given Get Select Select ENUM Data Table for LOOKUP CONFIGURATION
      | tableName | ENUM_RACE_CODE |
    And Verify response status code is 200
    Then Verify Enum Data Table response
    Examples:
      | projectName |
      |[blank]|


  #@API-CP-1359 @API-CP-1359-03 @API-TM-Regression @API-TM-SmokeTest @kamil
  Scenario Outline: Add Lookup for LOOKUP CONFIGURATION
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I initiated ADD LOOKUP for LOOKUP CONFIGURATION
    Then I add new Lookup payload "addLookup" for LOOKUP CONFIGURATION
      | value       | Representative |
      | tableName   | ENUM_RACE_CODE |
      | description | customer       |
      | reportLabel | Repo           |
      | scope       | kt             |
    And Verify response status code is 200
    Examples:
      | projectName |
      |[blank]|


  #@API-CP-1359 @API-CP-1359-04 @API-TM-Regression @API-TM-SmokeTest @kamil
  Scenario Outline: Add Lookup for LOOKUP CONFIGURATION and verify Lookup in Data table
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I initiated ADD LOOKUP for LOOKUP CONFIGURATION
    Then I add new Lookup payload "addLookup" for LOOKUP CONFIGURATION
      | value       | Representative |
      | tableName   | ENUM_RACE_CODE |
      | description | customer       |
      | reportLabel | Repo           |
      | scope       | kt             |
    And Verify response status code is 200
    And I initiated Select Data Table for LOOKUP CONFIGURATION
    Given Get Select Select ENUM Data Table for LOOKUP CONFIGURATION
      | tableName | ENUM_RACE_CODE |
    And Verify response status code is 200
    Then Verify Data table value is "Representative" are added
    Examples:
      | projectName |
      |[blank]|


  #@API-CP-1359 @API-CP-1359-05 @API-TM-Regression @API-TM-SmokeTest @kamil
  Scenario Outline: Add Lookup for LOOKUP CONFIGURATION with null value
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I initiated ADD LOOKUP for LOOKUP CONFIGURATION
    Then I add new Lookup payload "addLookup" for LOOKUP CONFIGURATION
      | value     | null::         |
      | tableName | ENUM_RACE_CODE |
    And Verify response status code is 200
    And I initiated Select Data Table for LOOKUP CONFIGURATION
    Given Get Select Select ENUM Data Table for LOOKUP CONFIGURATION
      | tableName | ENUM_RACE_CODE |
    And Verify response status code is 200
    Then Verify Null value not added to the Table Name
    Examples:
      | projectName |
      |[blank]|

  @API-CP-5965 @API-CP-5965-01 @aswath @lookup-api-AS @API-AUX
  Scenario Outline: Select ENUM Date for LOOKUP CONFIGURATION
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I initiated Select Data Table for LOOKUP CONFIGURATION
    Given Get Select Select ENUM Data Table for LOOKUP CONFIGURATION
      | tableName | ENUM_CITY |
    And Verify response status code is 200
    Then Verify Enum Date response
    Examples:
      | projectName |
      |[blank]|

  @api-smoke-devops @aswath @lookup-api-AS @API-AUX
  Scenario Outline: Auxlliary SRE apigee health check verification
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I initiated Select Data Table for LOOKUP CONFIGURATION
    Given Get Select Select ENUM Data Table for LOOKUP CONFIGURATION
      | tableName | ENUM_CITY |
      | bservice  | plan      |
    And Verify response status code is 200
    Examples:
      | projectName |
      |[blank]|