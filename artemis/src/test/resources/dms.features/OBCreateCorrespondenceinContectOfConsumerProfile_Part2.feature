@CP-24099
Feature: Validate Recipient,default mail Channel and consumer’s mailing address in Context of Consumer not on case

  Background:
    Then I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer not on a case that wants to send an Outbound Correspondence

  @CP-24099-01  @ui-ecms1 @Keerthi
  Scenario:Validate the only one potential Recipient's Count, Id, Name and checkbox status in context of Active Contact Linked to Consumer
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I have selected an Outbound Correspondence through the UI "Consumer - CONONLY" as type
    And I Validate the only one potential Recipient in context of consumer

  @CP-24099-02  @ui-ecms1 @Keerthi
  Scenario:Validate the Default Channels and Destinations in context of Active Contact Linked to Consumer
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I have selected an Outbound Correspondence through the UI "Consumer - CONONLY" as type
    And I Validate default Channel and Destination

  @CP-24099-03  @ui-ecms1 @Keerthi
  Scenario:Validate the empty recipient object in context of Active Contact Linked to Consumer
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I have selected an Outbound Correspondence through the UI "Consumer - CONONLY" as type
    Then I validate Save functionality
    When I navigate to the case and contact details tab
    And I select Outbound Correspondence by "Any"
    And I Validate empty recipient value


  @CP-24099-04  @ui-ecms1 @Keerthi
  Scenario:Validate the only one potential Recipient's Count, Id, Name and checkbox status in context of Consumer
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click on Case Consumer Search page
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I click on the first case and consumer search result
    And I have selected an Outbound Correspondence through the UI "Consumer - CONONLY" as type
    And I Validate the only one potential Recipient in context of consumer

  @CP-24099-05  @ui-ecms1 @Keerthi
  Scenario:Validate the Default Channels and Destinations in context of of Consumer
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click on Case Consumer Search page
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I click on the first case and consumer search result
    And I have selected an Outbound Correspondence through the UI "Consumer - CONONLY" as type
    And I Validate default Channel and Destination

  @CP-24099-06  @ui-ecms1 @Keerthi
  Scenario:Validate the empty recipient object in context of Consumer
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click on Case Consumer Search page
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I click on the first case and consumer search result
    And I have selected an Outbound Correspondence through the UI "Consumer - CONONLY" as type
    Then I validate Save functionality
    When I navigate to the case and contact details tab
    And I select Outbound Correspondence by "Any"
    And I Validate empty recipient value