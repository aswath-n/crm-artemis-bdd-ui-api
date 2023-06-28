Feature: View Outbound Correspondence Notes


  @CP-3153-01 @CP-3153 @ui-ecms1 @albert
  Scenario: Verify that the following elements are visible; save button, cancel button.
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail"
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I select Outbound Correspondence by "Any"
    Then I should see the follow elements are visible; save button, cancel button.

  @CP-3153-02 @CP-3153 @ui-ecms1 @albert
  Scenario: Verify that a list of existing notes are present
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail"
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I select Outbound Correspondence by "Any"
    And I add and save new note
    Then I should see the note and any existing notes are present

  @CP-3153-03 @CP-3153 @ui-ecms1 @albert
  Scenario: Verify that existing notes includes the following fields; User Name, date/time created, text
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail"
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I select Outbound Correspondence by "Any"
    And I add and save new note
    Then I should see the note displayed and includes the following fields; User Name, timestamp, text

  @CP-3153-04 @CP-3153 @ui-ecms1 @albert
  Scenario: Verify that existing notes are existing notes are sorted by date/time descending
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail"
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I select Outbound Correspondence by "Any"
    And I add "3" new notes to the Outbound Correspondence
    Then I should see existing notes are in descending order

  @CP-3153-05 @CP-3153 @ui-ecms1 @albert
  Scenario: Verify that a save will capture created on and created by
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail"
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I select Outbound Correspondence by "Any"
    And I add and save new note
    Then I should see the note has created on and created by

  @CP-3153-06 @CP-3153 @ui-ecms1 @albert
  Scenario: Verify that the note will store the assigned values in a Note record associated with the Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail"
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I select Outbound Correspondence by "Any"
    And I add and save new note
    Then I should see the note will store the assigned values in a Note record associated with the Correspondence

  @CP-3153-07 @CP-3153 @ui-ecms1 @albert
  Scenario: Verify that the save will trigger a save successful message
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail"
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I select Outbound Correspondence by "Any"
    Then I should see the successful message after saving a note

  @CP-3153-08 @CP-3153 @ui-ecms1 @albert
  Scenario: Verify that when save is successful, note will appear in note list and clear the note text field Given I logged into CRM and click on initiate contact
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail"
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I select Outbound Correspondence by "Any"
    And I add and save new note
    Then I should see note will appear in note list and clear the note text field

  @CP-3153-09 @CP-3153 @ui-ecms1 @albert
  Scenario: Verify that the system will warn before discarding data when navigating away
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail"
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I select Outbound Correspondence by "Any"
    And I type a new note in the note text box
    When attempt to navigate away from the page
    Then I should see a warning message

  @CP-3153-10 @CP-3153 @ui-ecms1 @albert
  Scenario: Verify that cancel button will return system to where navigated from
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail"
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I select Outbound Correspondence by "Any"
    And I type a new note in the note text box
    When attempt click on cancel
    Then I should remain on the current page and the text box should be cleared
