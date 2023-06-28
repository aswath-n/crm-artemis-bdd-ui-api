@CP-2982
@umid

Feature: View Outbound Correspondence Image

  @CP-2982-01 @ui-ecms1
  Scenario: Outbound Correspondence with a Mail, Email, and Text notification - Verify that if multiple notifications for a single correspondence exist that Mail will be the image that is shown when the correspondence view image icon is clicked
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And there is a Notification Object Uploaded onto Onbase for each notification
    And I click on the view icon for the correspondence "previouslyCreated"
    Then I should see the Mail notification is viewed


  @CP-2982-02 @ui-ecms1
  Scenario: Verify that if multiple notifications for a single correspondence exist that Email will be the image that is shown when there is no Mail and the correspondence view image icon is clicked
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Email,Text"
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I navigate to the case and contact details tab
    And there is a Notification Object Uploaded onto Onbase for each notification
    And I click on the view icon for the correspondence "previouslyCreated"
    Then I should see the Email notification is viewed

  @CP-2982-03 @ui-ecms1
  Scenario: Verify that if multiple notifications for a single correspondence exist that Text will be the image that is shown when there is no Mail and no Email and the correspondence view image icon is clicked
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And there is a Notification Object Uploaded onto Onbase for each notification
    And I click on the view icon for the correspondence "previouslyCreated"
    Then I should see the Text notification is viewed

  @CP-2982-04 @ui-ecms1
  Scenario: Verify that if multiple notifications for a single correspondence exist that Mobile App will be the image that is shown when there is no Mail and no Email and the correspondence view image icon is clicked
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I navigate to the case and contact details tab
    And there is a Notification Object Uploaded onto Onbase for each notification
    And I click on the view icon for the correspondence "previouslyCreated"
    Then I should see the Mobile notification is viewed

#  @CP-2982-05 @ui-ecms1
  Scenario: Verify that if no notification object is stored for the correspondence, then no view icon is not present
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I logged into CRM
    And I click case consumer search tab
    And I search for the "CaseId" with value "RandomCaseId"
    And I search for the Outbound Correspondence "previouslyCreated"
    When I navigate to the case and contact details tab
    Then no view icons are present

#  @CP-2982-06 @ui-ecms1
  Scenario: Verify that the image is the exact image that was sent as long as it has been constructetd and stored and not canceled
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an Outbound Correspondence with a notification for "Mobile App"
    And I logged into CRM
    And I click case consumer search tab
    And I search for the "CaseId" with value "RandomCaseId"
    And I search for the Outbound Correspondence "previouslyCreated"
    When I navigate to the case and contact details tab
    And I click on the view icon for the correspondence "Mobile App"
    Then I should see the exact "Mobile App" document that was sent
