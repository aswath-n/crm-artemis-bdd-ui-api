@CP-28777
Feature: Update IB Corr Link Edit History to Show in Project Standard Format


  @CP-28777 @CP-28777-01  @ui-ecms2 @Keerthi
  Scenario: Update project to Eastern Standard Format
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
   # When I search for a project by "program" value "Baseline Program"
    When I expend a Project to view the details
    Then I update tenant manger "time_zone" field with value "Eastern"

  @CP-28777 @CP-28777-02  @ui-ecms2 @Keerthi
  Scenario: Update IB Corr Edit History to Show in Eastern Project Standard Format
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | fileType     | pdf                     |
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    Then I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID |  InboundDocument  |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "type" field with value "maersk Case" via "null"
    And I click on Edit Inbound Correspondence details Page Save button
    And I capture date and time in required format
    And I click on the EDIT HISTORY tab of the Inbound Correspondence Details Page
    And I validate the Edited On time is as per project timezone

  @CP-28777 @CP-28777-03 @ui-ecms2 @Keerthi
  Scenario: Update project to Central Standard Format
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    #When I search for a project by "program" value "Baseline Program"
    When I expend a Project to view the details
    Then I update tenant manger "time_zone" field with value "Central"

  @CP-28777 @CP-28777-04 @ui-ecms2 @Keerthi
  Scenario: Validate updated IB Corr Edit History to Show in Central Project Standard Format
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID |  InboundDocument  |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the EDIT HISTORY tab of the Inbound Correspondence Details Page
    And I validate the Edited On time is as per project timezone

  @CP-28777 @CP-28777-05  @ui-ecms2 @Keerthi
  Scenario: Update project to Pacific Standard Format
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    #When I search for a project by "program" value "Baseline Program"
    When I expend a Project to view the details
    Then I update tenant manger "time_zone" field with value "Pacific"

  @CP-28777 @CP-28777-06  @ui-ecms2 @Keerthi
  Scenario: Validate updated IB Corr Edit History to Show in Pacific Project Standard Format
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID |  InboundDocument  |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the EDIT HISTORY tab of the Inbound Correspondence Details Page
    And I validate the Edited On time is as per project timezone

  @CP-28777 @CP-28777-07 @ui-ecms2 @Keerthi
  Scenario: Update project back to Eastern Standard Format
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
   # When I search for a project by "program" value "Baseline Program"
    When I expend a Project to view the details
    Then I update tenant manger "time_zone" field with value "Eastern"

  @CP-28777 @CP-28777-08  @ui-ecms2 @Keerthi
  Scenario: Validate updated IB Corr Edit History to Show in Eastern Project Standard Format
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID |  InboundDocument  |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the EDIT HISTORY tab of the Inbound Correspondence Details Page
    And I validate the Edited On time is as per project timezone