Feature: Update UI Error Message for Saving a Stopped Phone Number

  @CP-48224 @CP-48224-01 @ui-cc-dc @muhabbat
  Scenario: DC-EB: Validate Error Message text on the UI when trying to Save a stopped phone number
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
#    And I minimize Genesys popup if populates
    And I search for consumer by "StateID" with value "LL5323757391"
    And I navigate to consumer profile page by clicking on stateId
    When I click on add "Language" button on consumer profile page
    Then I input all required fields with stopped destination for "Text" on Correspondence Preference Page
    And I click on save button
    And I see stopped destination Error message "Text Phone Number has been STOPPED via text message and cannot be used" is displayed