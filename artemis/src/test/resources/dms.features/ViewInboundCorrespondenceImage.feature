Feature: View Inbound Correspondence Image from Inbound Correspondence Details screen

  @CP-12433 @CP-12433-01 @ui-ecms1 @James
  Scenario: Verify I can view an Inbound Document from Inbound Search Results
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on a view icon of "first available" Inbound Search Results
    Then I should see the Inbound Document is viewable

  @CP-12433 @CP-12433-02 @ui-ecms1 @James
  Scenario: Verify I can view an Inbound Document from Inbound Details Page
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on a CID of "first available" Inbound Search Results
    And I click on the "view icon" in the Inbound Correspondence Details page
    Then I should see the Inbound Document is viewable

  @CP-2680 @CP-2680-1 @ui-ecms1 @James
    Scenario: Verify that an exact image of Inbound Correspondence as pdf
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on a CID of "first available" Inbound Search Results
    And I click on the "view icon" in the Inbound Correspondence Details page
    Then I should see the Inbound Document is viewable

  @CP-2680 @CP-2680-2 @ui-ecms1 @James
    Scenario: Verify that I am able to zoom in and out of Inbound Correspondence Image
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on a CID of "first available" Inbound Search Results
    And I click on the "view icon" in the Inbound Correspondence Details page
    Then I should be able to zoom in and out of document

  @CP-2680 @CP-2680-3 @ui-ecms1 @James
  Scenario: Verify that I am able to scroll and view all of Inbound Correspondence Image
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on a CID of "first available" Inbound Search Results
    And I click on the "view icon" in the Inbound Correspondence Details page
    Then I should be able to scroll and view all of document

  @CP-2680 @CP-2680-4 @ui-ecms1 @James
  Scenario: Verify that I am able to view how many pages and choose to display a page of Inbound Correspondence Image
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on a CID of "first available" Inbound Search Results
    And I click on the "view icon" in the Inbound Correspondence Details page
    Then I should be able to see how many pages and can choose which page to display

  @CP-2680 @CP-2680-5 @ui-ecms1 @James
  Scenario: Verify that Inbound Correspondence Image opens in separate window
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on a CID of "first available" Inbound Search Results
    And I click on the "view icon" in the Inbound Correspondence Details page
    Then I should see the opened document is a new window