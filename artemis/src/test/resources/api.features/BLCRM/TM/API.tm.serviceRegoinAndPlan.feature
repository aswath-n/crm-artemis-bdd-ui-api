Feature: API-Tenant Manager: Create a new provider and establish provider data elements

  @planProviderAPI @API-CP-3376 @API-CP-3376-01 @API-PP @API-PP-Regression @mohammad @plan-manag-ms-PP
  Scenario Outline: Verification of Service Region Config File Upload
    Given I get the jwt token
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I upload the service region file prior to plan file
    And I verify the response status code "200"
    And I will receive a success message "Upload Successful - Please Upload Plan File"
    Examples:
      |projectName|
      |   Regression        |

  @planProviderAPI @API-CP-3376 @API-CP-3376-02 @API-TM @API-PP-Regression @mohammad @plan-manag-ms-PP
  Scenario Outline: Verification of all existing data deleted from the database after Service Region File Uploaded
    Given I get the jwt token
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I upload the service region file prior to plan file
    And I verify the response status code "200"
    And I will receive a success message "Upload Successful - Please Upload Plan File"
    And I initiated plan search via API
    And User send Api call with payload "planSearch" for Plan search
      | planSearch.planName | AMERIGROUP COMMUNITY CARE |
    And I verify the response status code 200 and status "success"
    And I can verify get Plan Name API response is empty
    Examples:
      | projectName |
      | Regression  |
