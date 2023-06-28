@umid

Feature: Save the inbound document to secure server and return a link

  @CP-10907-01 @ui-ecms-nj @API-CP-10907
  Scenario: Verify NJ-SBE network file location
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE General"
    And I logged into CRM and select a project "NJ-SBE"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I minimize Genesys popup if populates
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I should see that there is a "GET IMAGE LOCATION" button
    When I click on he Get Image Location button
    When I search for that filepath network location with value as "fromRequest" to get credentials
    Then I will verify the document matches "nonprod\QE\ECMSFiles" and is successful

  @CP-10907-02 @ui-ecms-nj @API-CP-10907
  Scenario: Verify that service returns error for an id nonprod\QE\ECMSFiles
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE General"
    And I logged into CRM and select a project "NJ-SBE"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I minimize Genesys popup if populates
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I should see that there is a "GET IMAGE LOCATION" button
    When I click on he Get Image Location button
    When I search for that filepath network location with value as "984984" to get credentials
    Then I will verify the document matches "nonprod\QE\ECMSFiles" and is unsuccessful


  @CP-17841 @ui-ecms-coverva @Prithika
  Scenario: VA: Include user folder or name in saved filename and delete on viewing item
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV Application"
    Given I logged into CRM with "Service Account 1" and select a project "CoverVA"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I minimize Genesys popup if populates
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I should see that there is a "GET IMAGE LOCATION" button
    When I click on he Get Image Location button
    Then I should see "Document Path Copied to Clipboard" pop up message
    Then I should see the path of the image is copied into the user's clipboard
    When  I search for that filepath network location with value as "fromRequest" to get credentials
    Then I will verify the document matches "nonprod\QE\ECMSFiles" and is Successful
    And I navigate back to the Inbound Correspondence Search results
    And I have a Inbound Document that with the Inbound Document Type of "VACV Appeal"
    And I click on the continue button on warning pop up
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    Then I verify clipboard has been cleared out