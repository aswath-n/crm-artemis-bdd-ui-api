Feature: NJ Store inbound correspondence to file and get its path

  @CP-10906 @CP-10906-1 @ui-ecms-nj @kamil
  Scenario: Verify that there is a "Get Image Location"
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    Given I create a Task of type Inbound Task that is linked to an Inbound Correspondence for "NJ"
    And I logged into CRM and select a project "NJ-SBE"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for Inbound Correspondence with the one created
    And I click on the Inbound Correspondence Id result
    Then I should see that there is a "GET IMAGE LOCATION" button


  @CP-10906 @CP-10906-2 @ui-ecms-nj @kamil
  Scenario: Verify that "Document Path Copied to Clipboard" when "Get Image Location"button is clicked
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    Given I create a Task of type Inbound Task that is linked to an Inbound Correspondence for "NJ"
    And I logged into CRM and select a project "NJ-SBE"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for Inbound Correspondence with the one created
    And I click on the Inbound Correspondence Id result
    And I should see that there is a "GET IMAGE LOCATION" button
    When I click on he Get Image Location button
    Then I should see "Document Path Copied to Clipboard" pop up message


  @CP-10906 @CP-10906-3 @ui-ecms-nj @kamil
  Scenario: Verify that full path is copied to the user's clipboard when "Get Image Location" button is clicked
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    Given I create a Task of type Inbound Task that is linked to an Inbound Correspondence for "NJ"
    And I logged into CRM and select a project "NJ-SBE"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for Inbound Correspondence with the one created
    And I click on the Inbound Correspondence Id result
    And I should see that there is a "GET IMAGE LOCATION" button
    When I click on he Get Image Location button
    And I saved logged in user ID
    Then I should see the full path to the image is copied into the user's clipboard
