Feature: NJ: CRM CORE ETL
  @CP-25963 @aikanysh @ui-core-nj @crm-regression @NJ-UI-Regression
  Scenario: NJ: Configure Agentless Call Campaign Contact Record Creation
    Given I have prepared "callCampaignJob" csv file by changing phone number
    And I upload the ETL NJ_SBE "callCampaignJob" file to aws bucket "max-etl-njsbe-non-prod" folder "CallCampaign/Response/in/"
    When I run the "callCampaignJob" in project "njsbe" for CRM Core
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I search for an ETL created contact record by PhoneNumber under Advanced Search
    When I click on first Contact Record ID on Contact Record
    And I verify phone number is displayed as per ETL file





