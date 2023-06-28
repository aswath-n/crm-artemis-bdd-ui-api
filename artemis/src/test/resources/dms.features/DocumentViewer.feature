Feature: Close Document Viewer When User Exits Associated Context

  @CP-10562 @CP-10562-1a @ui-ecms1 @James
  Scenario: If user is performing a task and opens an outbound correspondence in viewer and user saves the task, then the image viewer closes
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I navigate to "My Task" page
    And I will search for the task with status "In-Progress"
    And I click on the priority in dashboard
    And I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    And there is a Notification Object Uploaded onto Onbase for each notification
    And I click on a view icon of "download icon" Inbound Search Results
    And I store the current count of windows open
    And I click on the priority in dashboard
    And I update the task status in task slider as "Complete"
    And I update the dispostion field in task slider as "User closed"
    When I click on save in Task Slider
    Then I should see the document viewer has automatically closed

  @CP-10562 @CP-10562-1b @ui-ecms1 @James
  Scenario: If user is performing a task and opens an inbound correspondence in viewer and user saves the task, then the image viewer closes
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | CaseID       | PreviouslyCreated       |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | INBOUND CORRESPONDENCE   |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
      | documentType   | maersk Case + Consumer  |
      | CaseID         | PreviouslyCreated        |
    And I send the request to create the Inbound Correspondence Save Event
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I navigate to "My Task" page
    And I will search for the task with status "In-Progress"
    And I click on the priority in dashboard
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    And I click on a view icon of "download icon" Inbound Search Results
    And I store the current count of windows open
    And I update the task status in task slider as "Complete"
    And I update the dispostion field in task slider as "User closed"
    When I click on save in Task Slider
    Then I should see the document viewer has automatically closed

  @CP-10562 @CP-10562-2 @ui-ecms1 @James
  Scenario: If user is in active contact and opens an outbound correspondence in viewer and user saves the task, then the image viewer closes
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail"
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    And there is a Notification Object Uploaded onto Onbase for each notification
    And I click on a view icon of "download icon" Inbound Search Results
    And I store the current count of windows open
    When I click End Contact and click "Cancel" after few seconds
    Then I should see the document viewer has automatically closed

  @CP-10562 @CP-10562-3 @ui-ecms1 @James
  Scenario: If user is in active contact and opens an inbound correspondence in viewer and user saves the task, then the image viewer closes
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | CaseID       | PreviouslyCreated       |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | INBOUND CORRESPONDENCE   |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
      | documentType   | maersk Case + Consumer  |
      | CaseID         | PreviouslyCreated        |
    And I send the request to create the Inbound Correspondence Save Event
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    And I store the handle of the main window
    And I click on a view icon of "download icon" Inbound Search Results
    And I store the current count of windows open
    And I switch back to the "main" window
    And I click on Active Contact widget
    When I click End Contact and click "Cancel" after few seconds
    When I click End Contact
    And I choose a contact reason from reason dropdown list as "Enrollment"
    And  I choose "Plan Change" option for Contact Action field
    And I click save button for reason and action
    And I enter Call Summary as "First call summary"
    Then I click on save Call Summary
    And I click save button Active contact
    Then I should see the document viewer has automatically closed

  @CP-10562 @CP-10562-4 @ui-ecms2 @James
  Scenario: If user is in case profile and opens an outbound correspondence in viewer and user saves the task, then the image viewer closes
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail"
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    When I navigate to the case and contact details tab
    And there is a Notification Object Uploaded onto Onbase for each notification
    And I click on a view icon of "download icon" Inbound Search Results
    And I store the current count of windows open
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    Then I should see the document viewer has automatically closed

  @CP-10562 @CP-10562-5 @ui-ecms2 @James
  Scenario: If user is in consumer profile and opens an inbound correspondence in viewer and user saves the task, then the image viewer closes
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | CaseID       | PreviouslyCreated       |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType    | INBOUND CORRESPONDENCE   |
      | CHANNEL        | Mail                     |
      | documentHandle | InboundDocumentIdDigital |
      | documentType   | maersk Case + Consumer  |
      | CaseID         | PreviouslyCreated        |
    And I send the request to create the Inbound Correspondence Save Event
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    When I navigate to the case and contact details tab
    And there is a Notification Object Uploaded onto Onbase for each notification
    And I click on a view icon of "download icon" Inbound Search Results
    And I store the current count of windows open
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    Then I should see the document viewer has automatically closed

  @CP-10562 @CP-10562-6 @ui-ecms2 @James
  Scenario: If user is in case profile logs out, then the image viewer closes
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail"
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    When I navigate to the case and contact details tab
    And there is a Notification Object Uploaded onto Onbase for each notification
    And I store the handle of the main window
    And I click on a view icon of "download icon" Inbound Search Results
    And I switch back to the "mainWindow" window
    And I store the current count of windows open
    When I initiate logout button
    #And I see Warning Message pop-up
    #When I click on continue button on warning message
    Then I verify navigation warning message displayed on OC request
    When I click on "Continue" button on warning message OC request
    Then I should see the document viewer has automatically closed