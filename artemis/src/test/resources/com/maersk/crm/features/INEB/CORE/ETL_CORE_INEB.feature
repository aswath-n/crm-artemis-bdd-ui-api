Feature: CRM CORE ETL

  @CP-31252 @CP-35747 @khazar @ui-core-in-eb @crm-regression @IN-EB-UI-Regression @core-etl
  Scenario: IN-EB: Dialer Response Call Campaign Contact Record Creation
    Given I have prepared callCampaignJob csv file for IN-EB
    And I upload the ETL IN-EB "callCampaignJob" file to aws bucket "max-etl-ineb-non-prod" folder "CallCampaign/Response/in/"
    When I run the "INEB_callCampaignJob" in project "ineb" for CRM Core
    Given I logged into CRM and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I search for an ETL created contact record by PhoneNumber under Advanced Search and verify valid data processed