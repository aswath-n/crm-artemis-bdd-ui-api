@CP-3219
@umid
Feature: View Outbound Correspondence Summary Information

  @CP-3219-1  @ui-ecms1
  Scenario: Verify caret shows and hides details of correspondences
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    And I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I click on the details carat of the Correspondence
    Then I should see the details of the Correspondence

  @CP-3219-2 @ui-ecms1
  Scenario: Verify that Correspondence ID is showing
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Bruce" and Last Name as "Wayne"
    And I link the contact to an existing Case or Consumer Profile
    When I navigate to the case and contact details tab
    And I click on the details carat of the Correspondence
    Then I should see the Correspondence ID of the Correspondence

  @CP-3219-3 @ui-ecms1
  Scenario: Verify that Correspondence Type is showing
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Bruce" and Last Name as "Wayne"
    And I link the contact to an existing Case or Consumer Profile
    When I navigate to the case and contact details tab
    When I navigate to the case and contact details tab
    And I click on the details carat of the Correspondence
    Then I should see the Correspondence Type of the Correspondence

  @CP-3219-4 @ui-ecms1
  Scenario: Verify that the Language that is showing is the Language of the first notification
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Bruce" and Last Name as "Wayne"
    And I link the contact to an existing Case or Consumer Profile
    When I navigate to the case and contact details tab
    And I click on the details carat of the Correspondence
    Then I should see the Correspondence Language is the same of the first notification

  @CP-3219-5 @ui-ecms1
  Scenario: Verify that when there is more than one recipient, an ellipsis symbol will display, and when hovered over, will display the recipients in a tool tip
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an Outbound Correspondence with "3" Consumer Ids in the Regarding Section
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I click on the details carat of the Correspondence from recipients
    And I hover over an elipsis at the recipients column
    Then I should see the recipients of the correspondence

  @CP-3219-6 @ui-ecms1
  Scenario: Verify that when there is more than one channel for the Correspondence, an ellipsis will display, and when hovered over, will dipslay the recipients in a tool tip
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I hover over an elipsis at the recipients channel
    Then I should see "Mail,Email,Text" channels of the correspondence

  @CP-3219-7 @ui-ecms1
  Scenario: Verify that Status is dipslayed
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail"
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I click on the details carat of the Correspondence
    Then I should see the status of the Correspondence

  @CP-3219-8 @ui-ecms1
  Scenario: Verify that Status Date is displayed in MM/DD/YYYY format
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail"
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    Then I should see the Status Date is displayed in "MM/dd/YYYY" format

  @CP-3219-9 @ui-ecms1
  Scenario: Verify that the view icon to view the correspondence is present
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I click on the details carat of the Correspondence
    Then I should see the view icon to view the correspondence is present

  @CP-3219-10 @ui-ecms1
  Scenario: Verify that sort order of correspondence is most recent first
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Bruce" and Last Name as "Wayne"
    And I link the contact to an existing Case or Consumer Profile
    When I navigate to the case and contact details tab
    And I click on the details carat of the Correspondence
    Then I should see the sort order of correspondence is most recent first
