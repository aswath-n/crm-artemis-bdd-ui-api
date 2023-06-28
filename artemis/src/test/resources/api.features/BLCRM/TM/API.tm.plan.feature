@apiPlanController
Feature: API-Tenant Manager: Plan Management Controller


  @planProviderAPI @API-TM @API-PP-Regression @aswath @plan-manag-ms-PP @API-PP-SmokeTest
  Scenario: Getting JWT token
    Given I get the jwt token

  @planProviderAPI @test475 @API-CP-705 @API-CP-705-01 @API-CP-2453 @API-CP-2453-01 @API-CP-544 @API-TM @API-PP-Regression @aswath @plan-manag-ms-PP @API-PP-SmokeTest
  Scenario Outline: Upload service region file
      #Given I get the jwt token
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Plan Management for service region API
    When I upload the service regions file "<FileName>" with fileStatus "<FileStatus>"
    Then I verify the file upload message "<uploadMessage>"
    Examples:
      | FileName                       | FileStatus | uploadMessage                               | projectName |
      | Region_success.xlsx            | success    | Upload Successful - Please Upload Plan File |[blank]|
      | Region_success_SubProgram.xlsx | success    | Upload Successful - Please Upload Plan File |[blank]|

  @planProviderAPI @API-CP-706 @API-CP-706-01 @API-CP-796 @API-CP-3866 @API-CP-796-01 @API-CP-1605 @API-CP-545 @API-CP-545-01 @API-CP-3029 @API-TM @API-PP-Regression @plan-manag-ms-PP @aswath @API-PP-SmokeTest
  Scenario Outline: Upload Plan config file
    When I will get the Authentication token for "BLCRM" in "Tenant Manager"
    Given I upload the service region file prior to plan file
    Given I initiated Plan Management for Plan API
    When I upload the plan regions file "<FileName>" with fileStatus "<FileStatus>"
    Then I verify the plan file upload message "<uploadMessage>"
    Examples:
      | FileName                          | FileStatus | uploadMessage     | projectName |
      | Plan TestFile Success BLCRM BLCRM | success    | Upload Successful |[blank]|
      | File Sheet Name                   | fail       | Sheet Name        |[blank]|
      | File Sheet order                  | fail       | Sheet Order       |[blank]|
#      | File Failure EL Type  | fail       | Eligiblity limit Missing |[blank]|

  @planProviderAPI @API-CP-805-01 @API-CP-805 @API-CP-545 @API-CP-545-02 @API-CP-3029 @API-TM @API-PP-Regression @planProvider @plan-manag-ms-PP @Sujoy
  Scenario Outline: Get Counties using API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated get counties via API
    When I run get Service Delivery Areas
    Then I can verify get Counties API response is not empty
    Examples:
      | projectName |
      |[blank]|

  @planProviderAPI @API-CP-805-02 @API-CP-805 @API-CP-545 @API-CP-545-03 @API-CP-3029 @API-TM @API-PP-Regression @planProvider @plan-manag-ms-PP @Sujoy @aswath @qe-Devops
  Scenario Outline: Get Plan Names using API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Plan Management for service region API
    Given I get the jwt token
    Given I upload the success service region and plan file
    Given I initiated get plan name via API
    When I run get Plan Names
    Then I can verify get Plan Name API response is not empty
    Examples:
      | projectName |
      |[blank]|

  @planProviderAPI @API-CP-805-03 @API-CP-805 @API-CP-545 @API-CP-545-04 @API-CP-3029 @API-TM @API-PP-Regression @planProvider @plan-manag-ms-PP @Sujoy
  Scenario Outline: Get Program Type using API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated get program type via API
    When I run get Program Type
    Then I can verify get Program Type API response is not empty
    Examples:
      | projectName |
      |[blank]|

  @planProviderAPI @API-CP-805-04 @API-CP-805 @API-CP-545 @API-CP-545-05 @API-CP-3029 @API-TM @API-PP-Regression @planProvider @plan-manag-ms-PP @Sujoy
  Scenario: Post Sub Program Type using API
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

  @planProviderAPI @API-CP-805-05 @API-CP-805 @API-CP-545 @API-CP-545-06 @API-CP-3029 @API-TM @API-PP-Regression @planProvider @plan-manag-ms-PP @Sujoy
  Scenario Outline: Get Plan Service Delivery Area using API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated get Service Delivery Areas via API
    When I run get Service Delivery Areas
    Then I can verify get Service Delivery Areas API response is not empty
    Examples:
      | projectName |
      |[blank]|

  @planProviderAPI @API-CP-805-06 @API-CP-805 @API-CP-545 @API-CP-545-07 @API-CP-3029 @API-TM @API-PP-Regression @planProvider @plan-manag-ms-PP @Sujoy
  Scenario Outline: Get County by County Name using API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated get county via API with county name "Atlanta"
    When I run get county name
    Then I can verify get Counties API response is not empty
    Examples:
      | projectName |
      |[blank]|

  @planProviderAPI @API-CP-805-07 @API-CP-805 @API-TM @API-PP-Regression @planProvider @plan-manag-ms-PP @Sujoy
  Scenario Outline: Search Service Regions using API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated search Service Regions via API
    When I search service region with region name '"<Region Name>"', County Name '"<County Name>"' and Zip Code '"<Zip Code>"' via API
    When I run post Service Region Search
    Then I can verify get Service Delivery Areas API response is not empty

    Examples:
      | Region Name | County Name   | Zip Code | projectName |
      | Atlanta     |[blank]|          |[blank]|
      | Atlanta     | Barrow - 007 |[blank]|             |
      | Atlanta     |[blank]| 30680   |[blank]|
      |[blank]| Barrow - 007 | 30680    |[blank]|
      |[blank]| Barrow - 007 |[blank]|             |
      |[blank]|               | 30680    |[blank]|
      |[blank]| Barrow - 007 | 30680    |[blank]|
      | Atlanta     | Barrow - 007 | 30680    |[blank]|

### ****** There is test data and there is no end user process to create the test data for Statewide selection for Region ***********
  @planProviderAPI @API-CP-805-08 @API-CP-805 @API-CP-545 @API-CP-545-08 @API-CP-3029 @API-TM @API-PP-Regression @planProvider @plan-manag-ms-PP @Sujoy
  Scenario Outline: Search Statewide Regions using API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated get statewide region search via API
    When I run get Statewide Service Region Search via API
    Then I can verify get Service Delivery Areas API response is empty
    Examples:
      | projectName |
      |[blank]|

  @planProviderAPI @API-CP-803-01 @API-CP-803 @API-CP-544 @API-TM @API-PP-Regression @planProvider @plan-manag-ms-PP @Sujoy
  Scenario Outline: Search Service Plans using API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
#    Given I upload the Plan file
    Given I initiated Plan Management for service region API
    Given I get the jwt token
    Given I upload the success service region and plan file
    Given I initiated plan search via API
    When I search plan via API with '"<stateWide>"','"<planName>"','"<planCode>"','"<serviceRegion>"','"<programType>"','"<subProgramType>"','"<countyCode>"' and '"<zipCode>"'
    When I run post Plan Search
    Then I can verify get Plan Name API response is not empty
    Examples:
      | stateWide | planName                  | planCode | serviceRegion | programType | subProgramType | countyCode | zipCode | projectName |
      | false     | AMERIGROUP COMMUNITY CARE |[blank]|               |[blank]|                |[blank]|         |[blank]|


  @planProviderAPI @API-CP-803-01 @API-CP-803-01-0.2 @API-CP-803 @API-CP-544 @API-TM @API-PP-Regression @planProvider @plan-manag-ms-PP @Sujoy
  Scenario Outline: Search Service Plans using API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Plan Management for service region API
#    Given I get the jwt token
    Given I upload the success service region and plan file
    Given I initiated plan search via API
    When I search plan via API with '"<stateWide>"','"<planName>"','"<planCode>"','"<serviceRegion>"','"<programType>"','"<subProgramType>"','"<countyCode>"' and '"<zipCode>"'
    When I run post Plan Search
    Then I can verify get Plan Name API response is not empty
    Examples:
      | stateWide | planName                  | planCode | serviceRegion | programType | subProgramType | countyCode | zipCode | projectName |
#      | false     | AMERIGROUP COMMUNITY CARE |[blank]|               |[blank]|                |[blank]|         |[blank]|
      | false     |[blank]| 84       |[blank]|             |[blank]|            |[blank]|             |
      | false     |[blank]|          | Atlanta       |[blank]|                |[blank]|         |[blank]|
      | false     |[blank]|          |[blank]| Medicaid    |[blank]|            |[blank]|             |
      | false     |[blank]|          |[blank]|             | MedicaidGF     |[blank]|         |[blank]|
      | false     |[blank]|          |[blank]|             |[blank]| 007       |[blank]|             |
      | false     |[blank]|          |[blank]|             |[blank]|            | 30005   |[blank]|

### ****** There is test data and there is no end user process to create the test data for Statewide selection for plan ***********
  @planProviderAPI @API-CP-803-02 @API-CP-803 @API-CP-544 @API-TM @API-PP-Regression @planProvider @plan-manag-ms-PP @Sujoy
  Scenario Outline: Search State-wide Service Plans using API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
#    Given I upload the Plan file
    Given I initiated Plan Management for service region API
#    Given I get the jwt token
    Given I upload the success service region and plan file
    Given I initiated plan search via API
    When I search plan via API with '"<stateWide>"','"<planName>"','"<planCode>"','"<serviceRegion>"','"<programType>"','"<subProgramType>"','"<countyCode>"' and '"<zipCode>"'
    Then I run post Plan Search
    Then I can verify get Plan Name API response is empty

    Examples:
      | stateWide | planName | planCode | serviceRegion | programType | subProgramType | countyCode | zipCode | projectName |
      | True      |[blank]|          |[blank]|             |[blank]|            |[blank]|             |


  @planProviderAPI @API-CP-941-01 @API-CP-941 @API-TM @API-PP-Regression @planProvider @plan-manag-ms-PP @Sujoy
  Scenario Outline: Get Service Region Information by Region Name using API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Plan Management for service region API
    Given I get the jwt token
    Given I upload the success service region and plan file
    Given I initiated Service Region Info API with region name "Atlanta"
    When I run get service region info by region name
    Then I can verify get service region information API response is not empty
    Examples:
      | projectName |
      |[blank]|

  @planProviderAPI @API-CP-941-02 @API-CP-941 @API-CP-1905 @API-CP-1905-01 @API-TM @API-PP-Regression @planProvider @plan-manag-ms-PP @aswath @Sujoy
  Scenario Outline: Get Geographical Information Search under Regions using API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Plan Management for service region API
    Given I get the jwt token
    Given I upload the success service region and plan file
    Given I initiated geographical info via API
    When I can provide geographical information with county "<countyName>", ZipCode "<zipCode>" and city "<city>"
    When I run post geographical info
    Then I can verify geographical info API response is not empty
    Examples:
      | countyName | zipCode | city      | projectName |
      | Barrow     |[blank]|           |[blank]|


  @planProviderAPI @API-CP-941-02 @API-CP-941-02-2 @API-CP-941 @API-CP-1905 @API-CP-1905-01 @API-TM @API-PP-Regression @planProvider @plan-manag-ms-PP @aswath @Sujoy
  Scenario Outline: Get Geographical Information Search under Regions using API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Plan Management for service region API
#    Given I get the jwt token
    Given I upload the success service region and plan file
    Given I initiated geographical info via API
    When I can provide geographical information with county "<countyName>", ZipCode "<zipCode>" and city "<city>"
    When I run post geographical info
    Then I can verify geographical info API response is not empty
    Examples:
      | countyName | zipCode | city      | projectName |
#      | Barrow     |[blank]|           |[blank]|
      |[blank]| 30680   |[blank]|             |
      |[blank]|         | Winder |[blank]|
      | Barrow     | 30680   |[blank]|             |
      | Barrow     |[blank]| Winder |[blank]|
      |[blank]| 30680   | Winder |[blank]|
      | Barrow     | 30680   | Winder |[blank]|


  @planProviderAPI @API-CP-941-03 @API-CP-941 @API-TM @API-PP-Regression @planProvider @plan-manag-ms-PP @Sujoy
  Scenario Outline: Get cities based on service delivery area using API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated cities using API based on service delivery area "Atlanta"
    When I run get cities by service delivery area via API
    Then I can verify get Cities API response is not empty
    Examples:
      | projectName |
      |[blank]|

  @planProviderAPI @API-CP-941-04 @API-CP-941 @API-TM @API-PP-Regression @planProvider @plan-manag-ms-PP @Sujoy
  Scenario Outline: Get Service Region geographical info for given service delivery area using API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated geographical info using API based on service delivery area "Atlanta"
    When I run get geographical info by service delivery area via API
    Then I can verify get geographical info API response is not empty
    Examples:
      | projectName |
      |[blank]|

  @planProviderAPI @API-CP-941-05 @API-CP-941 @API-TM @API-PP-Regression @planProvider @plan-manag-ms-PP @Sujoy
  Scenario Outline: Get program type based on service region using API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated program type using API based on service delivery area "Atlanta"
    When I run get program type by service delivery area via API
    Then I can verify get program type API response by Region Id is not empty
    Examples:
      | projectName |
      |[blank]|

  @planProviderAPI @API-CP-942-01 @API-CP-942 @API-TM @API-PP-Regression @planProvider @plan-manag-ms-PP @Sujoy
  Scenario Outline: Get Plan Zipcodes Search using API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated plan zipcodes search via API
    When I run get plan zipcodes search via API
    Then I can verify get plan zipcodes search API response is not empty
    Examples:
      | projectName |
      |[blank]|

  @planProviderAPI @API-CP-942-02 @API-CP-942 @API-TM @API-PP-Regression @planProvider @plan-manag-ms-PP @Sujoy
  Scenario Outline: Get plan Zipcodes based on Cities using API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated plan Zipcodes using API based on Cities "Adrian"
    When I run get Plan Cities by city name via API
    Then I can verify  get Plan Cities by city name is not empty
    Examples:
      | projectName |
      |[blank]|

  @planProviderAPI @API-CP-942-03 @API-CP-942 @API-TM @API-PP-Regression @planProvider @plan-manag-ms-PP @Sujoy
  Scenario Outline: Search Service Plans using API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated a plan detail via API by plan name '<planName>'
    When I run get plan detail via API
    Then I can verify get plan detail API response is not empty

    Examples:
      | planName                  | projectName |
      | AMERIGROUP COMMUNITY CARE |[blank]|


  @planProviderAPI @API-CP-740-01 @API-CP-740 @API-TM @API-PP-Regression @planProvider @plan-manag-ms-PP @Sujoy
  Scenario Outline: Update Plan information under plan detail using API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated plan update via API for plan name '<planName>'
    When I update a plan with parameter '<Parameter>' with value '<Update_Value>'
    When I run post plan update
    Then I can verify plan update message via API with expected message '<Update_Message>'
    Then I can verify plan update via API updated the parameter '<Parameter>' with value '<Update_Value>'

    Examples:
      | planName                  | Parameter         | Update_Value | Update_Message              | projectName |
      | AMERIGROUP COMMUNITY CARE | autoAssignmentInd |[blank]| Fields successfully updated |[blank]|


  @planProviderAPI @API-CP-740-02 @API-CP-740 @API-TM @API-PP-Regression @planProvider @plan-manag-ms-PP @Sujoy
  Scenario Outline: Update Plan Contract information under plan detail using API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated plan update via API for plan name '<planName>'
    When I update a plan with parameter '<Parameter>' with value '<Update_Value>'
    When I run post plan update
    Then I can verify plan update message via API with expected message '<Update_Message>'
    Then I can verify plan update via API updated the parameter '<Parameter>' with value '<Update_Value>'
    Examples:
      | planName                  | Parameter | Update_Value | Update_Message              | projectName |
      | AMERIGROUP COMMUNITY CARE | endDate   |[blank]| Fields successfully updated |[blank]|

  @planProviderAPI @API-CP-740-03 @API-CP-740 @API-TM @API-PP-Regression @planProvider @plan-manag-ms-PP @Sujoy
  Scenario Outline: Update Plan Enrollment information under plan detail using API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated plan update via API for plan name '<planName>'
    When I update a plan with parameter '<Parameter>' with value '<Update_Value>'
    When I run post plan update
    Then I can verify plan update message via API with expected message '<Update_Message>'
    Then I can verify plan update via API updated the parameter '<Parameter>' with value '<Update_Value>'
    Examples:
      | planName                  | Parameter           | Update_Value | Update_Message              | projectName |
      | AMERIGROUP COMMUNITY CARE | enrollmentStartDate |[blank]| Fields successfully updated |[blank]|
      | AMERIGROUP COMMUNITY CARE | enrollmentEndDate   |[blank]| Fields successfully updated |[blank]|

  @planProviderAPI @API-CP-22170 @API-TM @API-PP-Regression @planProvider @plan-manag-ms-PP @sobir
  Scenario Outline: Expand Plan Search by Plan Code to include additional Plan info
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated plan search via API
    When I search plan via API with '"<stateWide>"','"<planName>"','"<planCode>"','"<serviceRegion>"','"<programType>"','"<subProgramType>"','"<countyCode>"' and '"<zipCode>"'
    When I run post Plan Search
    Then I can verify get Plan Name API response is not empty
    Examples:
      | stateWide | planName | planCode | serviceRegion | programType | subProgramType | countyCode | zipCode | projectName |
      | false     |[blank]| 84       |[blank]|             |[blank]|            |[blank]|             |
