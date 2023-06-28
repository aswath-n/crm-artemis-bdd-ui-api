Feature: Outbound Correspondence Links part1

  @CP-1249 @CP-1249-01 @ui-ecms1 @James
  Scenario Outline: Verify Task is linked to Ob Correspondence
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Then If any In Progress task present then update that to Cancelled
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 13241             |
    Then I send the request for the External task endpoint
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    And I click on initiate button in task search page
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I create an Outbound Correspondence
    And I have selected "<correspondenceType>" as a type
    Then I verify the Required fields values are displayed on create OB correspondence request page
      | caseID      | <caseIDIsDisplayed>     |
      | consumerIDs | <consumerIDIsDisplayed> |
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    When I click to save the Outbound Correspondence Request
    Then I validate request which was send from UI contains following values
      | caseID             | <caseIDIsDisplayed>     |
      | consumerID         | <consumerIDIsDisplayed> |
      | correspondenceType | <correspondenceType>    |
    And I retrieve the Outbound Correspondence which was created
    Then I verify the "previouslyCreated" Outbound Correspondence is linked to the "initiated" task
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I update the task status in task slider as "Complete"
    And I click on save in Task Slider
    Examples:
      | correspondenceType                         | caseIDIsDisplayed | consumerIDIsDisplayed | address1        | address2 | CITY   | State    | zipcode |
      | Case Consumer - CCONLY                     | true              | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | Case Consumers - CCSONLY                   | true              | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | Case Consumer Send Immediately - CCSENDIMM | true              | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | Case - CAONLY                              | true              | false                 | 100 Main street |empty| Canaan | New York | 12029   |
      | Consumer - CONONLY                         | false             | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | No Keys - NOKEYS                           | false             | false                 | 100 Main street |empty| Canaan | New York | 12029   |

  @CP-1249 @CP-1249-02 @API-ECMS @James
  Scenario: Verify Outbound Correspondence in context of a case is linked to a task from api
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have a Inbound Document that with the Inbound Document Type of "maersk Eligibility"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE |
      | CHANNEL      | Mail                   |
      | documentType | maersk Eligibility    |
    When I send the request to create the Inbound Correspondence Save Event
    And I create an Outbound Correspondence with the "previouslyCreated" task Id in the request
    Then I verify the "previouslyCreated" Outbound Correspondence is linked to the "initiated" task


######################## CP-28854 ########################################################

  @CP-28854  @CP-28854-01 @CP-31930 @CP-31930-01 @ui-ecms1 @Keerthi
  Scenario: Verify Navigation from OB to Linked Task and Linked Task to OB for BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1                 |
      | requiredDataElements | CaseID,ConsumerID |
      | taskTypeID           | 13359             |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType | RECEIVED |
      | Channel     | Mail     |
      | CaseID      | random   |
      | ConsumerID  | random   |
    When I send the request to create the Inbound Correspondence Save Event
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId     | Random                  |
      | ConsumerId | Random                  |
      | type       | CCONLY                  |
      | taskId     | previouslycreatedtaskId |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate navigation from OB links to "previouslycreatedtaskId" page
    And I validate navigation from OB to Task details page for "previouslycreatedtaskId"
    And I click on the back arrow button
    And I validate navigation back to "previouslycreatedtaskId" in OB details page
    And I click on the back arrow button
    And I should be navigated to the Outbound Correspondence Global Search Page

  @CP-28854 @CP-28854-02 @CP-31930 @CP-31930-02 @ui-ecms1 @Keerthi
  Scenario: Verify Navigation from OB to Linked Service Task and Linked Service Task to OB for BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1                 |
      | requiredDataElements | CaseID,ConsumerID |
      | taskTypeID           | 13241             |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType | RECEIVED |
      | Channel     | Mail     |
      | CaseID      | random   |
      | ConsumerID  | random   |
    When I send the request to create the Inbound Correspondence Save Event
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | ConsumerId | ConsumerOnly            |
      | type       | CONONLY                 |
      | taskId     | previouslycreatedtaskId |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate navigation from OB links to "previouslycreatedtaskId" page
    And I validate navigation from OB to Task details page for "previouslyCreatedServicetaskId"
    And I click on the back arrow button
    And I validate navigation back to "previouslycreatedtaskId" in OB details page
    And I click on the back arrow button
    And I should be navigated to the Outbound Correspondence Global Search Page

  ####################  CP-30773 and CP-31931 ######################

  @CP-30773 @CP-31931 @CP-30773-1.1 @CP-31931-1.1 @ui-ecms1 @Keerthi
  Scenario Outline: Validating navigation from OB to Linked Consumer on case and back to OB for BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | <correspondenceType> |
      | language                        | English              |
      | caseId                          | previouslyCreated    |
      | regardingConsumerId             | previouslyCreated    |
      | requesterId                     | 2425                 |
      | requesterType                   | ConnectionPoint      |
      | channelType                     | Mail                 |
      | address                         | random               |
    When I send the request for an Outbound Correspondence to the service
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate navigation from OB links to "previouslyCreatedConsumerId" page
    And I validate navigation to "previouslyCreatedConsumerId" Consumer Profile Demographic Info page
    And I click on the back arrow button
    And I validate navigation back to "previouslyCreatedConsumerId" in OB details page
    And I click on the back arrow button
    And I should be navigated to the Outbound Correspondence Global Search Page
    Examples:
      | correspondenceType |
      | CCONLY             |


  @CP-30773 @CP-31931 @CP-30773-1.2 @CP-31931-1.2 @ui-ecms1 @Keerthi
  Scenario: Validating navigation from OB to Linked multiple Consumers on multiple case and back to casecontacts details for BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an Outbound Correspondence with "3" Consumer Ids in the Regarding Section
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on Case Consumer Search page
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I click on the first case and consumer search result
    When I navigate to the case and contact details tab
    And I select Outbound Correspondence by "Any"
    And I validate navigation from OB links to "multipleconsumers[0]" page
    And I validate navigation to "multipleconsumers[0]" Consumer Profile Demographic Info page
    And I click on the back arrow button
    And I validate navigation from OB links to "multipleconsumers[1]" page
    And I validate navigation to "multipleconsumers[1]" Consumer Profile Demographic Info page
    And I click on the back arrow button
    And I validate navigation from OB links to "multipleconsumers[2]" page
    And I validate navigation to "multipleconsumers[2]" Consumer Profile Demographic Info page
    And I click on the back arrow button
    Then I navigate to the case and contact details


  @CP-30773 @CP-31931 @CP-30773-1.3 @CP-31931-1.3 @ui-ecms1 @Keerthi
  Scenario: Validating navigation from OB to standalone Consumer and back to OB for BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer not on a case that wants to send an Outbound Correspondence
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I create an Outbound Correspondence
    And I have selected "Consumer - CONONLY" as a type
    When I click to save the Outbound Correspondence Request
    When I navigate to the case and contact details tab
    And I select Outbound Correspondence by "Any"
    And I validate navigation from OB links to "previouslyCreatedStandaloneConsumerId" page
    And I validate navigation to "previouslyCreatedStandaloneConsumerId" Consumer Profile Demographic Info page
    And I click on the back arrow button
    And I validate navigation back to "previouslyCreatedStandaloneConsumerId" in OB details page
    And I click on the back arrow button
    Then I navigate to the case and contact details


  @CP-30773 @CP-31931 @CP-30773-1.4 @CP-31931-1.4 @ui-ecms1 @Keerthi
  Scenario Outline: Validating navigation from OB to Linked Consumer that is Linked to the initiated Task and back to OB for BLCRM
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Then If any In Progress task present then update that to Cancelled
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 13359             |
    Then I send the request for the External task endpoint
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    And I click on initiate button in task search page
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I create an Outbound Correspondence
    And I have selected "<correspondenceType>" as a type
    Then I verify the Required fields values are displayed on create OB correspondence request page
      | caseID      | <caseIDIsDisplayed>     |
      | consumerIDs | <consumerIDIsDisplayed> |
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    When I click to save the Outbound Correspondence Request
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I update the task status in task slider as "Complete"
    And I click on save in Task Slider
    And I click on Case Consumer Search page
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I click on the first case and consumer search result
    And I navigate to the case and contact details
    And I select Outbound Correspondence by "Any"
    And I validate navigation from OB links to "previouslyCreatedConsumerId" page
    And I validate navigation to "previouslyCreatedConsumerId" Consumer Profile Demographic Info page
    And I click on the back arrow button
    And I validate navigation back to "previouslyCreatedConsumerId" in OB details page
    And I click on the back arrow button
    Then I navigate to the case and contact details

    Examples:
      | correspondenceType                         | caseIDIsDisplayed | consumerIDIsDisplayed | address1        | address2 | CITY   | State    | zipcode |
      | Case Consumer Send Immediately - CCSENDIMM | true              | true                  | 100 Main street |empty| Canaan | New York | 12029   |


######################## CP-37551 ########################################################

  @CP-37551  @CP-37551-01 @ui-ecms1 @Keerthi
  Scenario: Verify warning messages if OB Correspondence Endpoint excludes External Links RefType or RefId
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I validate warning message for missing "externalRefType" for external OB links
      | externallink | empty,1234 |
    And I validate warning message for missing "externalRefId" for external OB links
      | externallink | SERVICE_REQUEST,empty |
    And I validate warning message for missing "externalRefId_and_externalRefType" for external OB links
      | externallink | empty,empty |


  @CP-37551  @CP-37551-02 @ui-ecms1 @Keerthi
  Scenario: Verify OB Correspondence Endpoint to Allow Requester to Include External Links to Create
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "General Two SR" for project ""
    And I will provide required information to create external sr with "8834" "" "" "" ""
      | Disposition |
    When I initiated external create sr api
    And I run the create external sr API and check the status code is "200"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId        | Random                        |
      | ConsumerId    | Random                        |
      | type          | CCONLY                        |
      | externallink1 | SERVICE_REQUEST,CRMTaskId     |
      | externallink2 | TASK,8012                     |
      | externallink3 | INBOUND_CORRESPONDENCE,221395 |
      | externallink4 | OUTBOUND_CORRESPONDENCE,8928  |
      | externallink5 | APPLICATION,22733             |
      | externallink6 | MISSING_INFO,221392           |
      | externallink7 | CASE,656                      |
      | externallink8 | CONSUMER,283688                |
      | externallink9 | CONTACT_RECORD,16325          |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate navigation back to "8012" in OB details page
    And I validate navigation back to "221395" in OB details page
    And I validate navigation back to "8928" in OB details page
    And I validate navigation back to "22733" in OB details page
    And I validate navigation back to "221392" in OB details page
    And I validate navigation back to "656" in OB details page
    And I validate navigation back to "283688" in OB details page
    And I validate navigation back to "16325" in OB details page
    And I validate navigation back to "previouslyCreatedConsumerId" in OB details page
    And I validate navigation back to "previouslyCreatedCaseId" in OB details page
    Then I should see the Link to the service request has the proper values
      | serviceRequestType | General Two SR |

  @CP-37551  @CP-37551-03 @API-ECMS @Keerthi
  Scenario: Verify LINK_EVENT for OB External Links
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                 |
      | ConsumerId   | Random                 |
      | type         | CCONLY                 |
      | externallink | SERVICE_REQUEST,random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I validate "LINK_EVENT" for OB external links with internal_externalId value as "random"

    ######################## CP-35144 ########################################################

  @CP-35144 @CP-35144-01 @ui-ecms1 @burak
  Scenario: Verify Viewing Display Column Details of SR linked to OB
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "General Two SR" for project ""
    And I will provide required information to create external sr with "8834" "" "" "" ""
      | Disposition |
    When I initiated external create sr api
    And I run the create external sr API and check the status code is "200"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                    |
      | ConsumerId   | Random                    |
      | type         | CCONLY                    |
      | externallink | SERVICE_REQUEST,CRMTaskId |
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I should see the Link to the service request has the proper values
      | serviceRequestType | General Two SR |

  @CP-35144 @CP-35144-02 @ui-ecms1 @burak
  Scenario: Verify Viewing Display Column Details of SR linked to OB after Closing the SR
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "General Service Request" for project ""
    And I will provide required information to create external sr with "8834" "" "" "" ""
      | Disposition |
    When I initiated external create sr api
    And I run the create external sr API and check the status code is "200"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                    |
      | ConsumerId   | Random                    |
      | type         | CCONLY                    |
      | externallink | SERVICE_REQUEST,CRMTaskId |
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I click on the link to the Service Request that is linked to the Inbound Document
    And I click on edit service request button
    And I will update the following information in edit task page
      | status        | Closed                  |
      | reasonForEdit | Entered Additional Info |
      | disposition   | General SR Closed       |
    And I click on save button on task edit page
    And I will click on back arrow on view sr page
    Then I should see the Link to the service request has the proper values
      | serviceRequestType | General Service Request |

  @CP-35144 @CP-35144-03 @ui-ecms1 @burak
  Scenario: Verify Viewing Display Column Details of SR which has special characters and max length
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "ServiceRequestWWW!./';WWWWWWWqw123124124124./;1-`=" for project ""
    And I will provide required information to create external sr with "8834" "" "" "" ""
      | Disposition |
    When I initiated external create sr api
    And I run the create external sr API and check the status code is "200"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                    |
      | ConsumerId   | Random                    |
      | type         | CCONLY                    |
      | externallink | SERVICE_REQUEST,CRMTaskId |
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I should see the Link to the service request has the proper values
      | serviceRequestType |ServiceRequestWWW!./';WWWWWWWqw123124124124./;1-`=|