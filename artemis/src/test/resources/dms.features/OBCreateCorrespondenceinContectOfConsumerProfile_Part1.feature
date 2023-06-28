@CP-22247
Feature: Validate Regarding section,Link Section, Warning message and navigation to Demographic page in Context of Consumer not on case

  Background:
    Then I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer not on a case that wants to send an Outbound Correspondence

    #  link section validation removed from create OB request page CP-28601
  @CP-22247-01  @ui-ecms1 @Keerthi
  Scenario:Validate the Regarding section,Link Section, Warning message and navigation to Demographic page in context of Active Contact Linked to Consumer
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I have selected an Outbound Correspondence through the UI "Consumer - CONONLY" as type
    And I Validate Regarding section is defaulted to the Consumer ID of that Consumer Profile and is not editable
#    And I validate Links section will display and auto-populate with a row showing a link to that Consumer
#    And I validate warning message for consumer link
#    And I validate navigation to Consumer Profile Demographic Info page

  @CP-22247-02  @ui-ecms1 @Keerthi
  Scenario:Validate the Regarding section,Link Section, Warning message and navigation to Demographic page in context of consumer profile
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click on Case Consumer Search page
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I click on the first case and consumer search result
    And I have selected an Outbound Correspondence through the UI "Consumer - CONONLY" as type
    And I Validate Regarding section is defaulted to the Consumer ID of that Consumer Profile and is not editable
#    And I validate Links section will display and auto-populate with a row showing a link to that Consumer
#    And I validate warning message for consumer link
#    And I validate navigation to Consumer Profile Demographic Info page