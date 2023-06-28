Feature: View Outbound Correspondence Image Part 2

  @CP-2982 @CP-2982-07 @ui-ecms2
  Scenario: Verify that I have an option to zoom in and out of the image
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I click on the view icon for the correspondence "previouslyCreated"
    Then I should be able to zoom in and out of document

  @CP-2982 @CP-2982-08 @ui-ecms2
  Scenario: Verify that I have option to side to side or up and down to every part of the page
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I click on the view icon for the correspondence "previouslyCreated"
    Then I should be able to scroll and view all of document

  @CP-2982 @CP-2982-09 @ui-ecms2
  Scenario: Verify that I can see how many pages and can choose which page to display
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I click on the view icon for the correspondence "previouslyCreated"
    Then I should be able to see how many pages and can choose which page to display

  @CP-2982 @CP-2982-10 @ui-ecms2
  Scenario: Verify that the image is displayed in a separate window from the main CRM application so that I can see the CRM application and the image at the same time
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I click on the details carat of the Correspondence
    And I click on the view icon for the correspondence "previouslyCreated"
    Then I should see the opened document is a new window

  @CP-2982 @CP-2982-11@ui-ecms2
  Scenario: Verify that when a document is retrieved, the notification id is being sent
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I click on the view icon for the correspondence "previouslyCreated"
    Then I should see the notification id in dev tools during retrieval