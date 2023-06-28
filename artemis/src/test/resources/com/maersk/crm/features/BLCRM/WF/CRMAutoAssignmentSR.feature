Feature: Validation of Auto Assignment SR

  @CP-34266 @CP-34266-03 @priyal @crm-regression @ui-wf
  Scenario: Verify create AA SR from API and Verify after Update the Due Date
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I have a request to create External task with the following values
      | taskTypeId | 16044 |
      | createdBy  | 8369  |
    Then I send the request for the External task endpoint
    Then I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    And I initiated bpm process get api by service request id for process name "BLCRM_Auto_Assignment_SR"
    And I run bpm process get api by sr id
    And I will check response of service request bpm process
    And Close the soft assertions

  @CP-34267 @CP-34267-01 @CP-34266 @CP-34266-01 @CP-34268 @CP-34268-01 @CP-34269 @CP-34269-01 @CP-34269-02 @CP-34270 @CP-34271 @CP-34272 @CP-34578 @CP-34578-01 @priyal @crm-regression @ui-wf
  Scenario Outline: Verify AA SR is created from ui and Link Consumer to the AA SR
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Auto Assignment SR" service request page
    And I click on save button in create service request page
    And I Verify user is navigate back to Task and service Request Page
    And Wait for 250 seconds
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I store sr id on edit page
    Then I verify "Auto Assignment SR" link section has all the business object linked : "Task,Consumer"
    And I click on id of "AA Outbound Call" in Links section of "SR" page
    And I store task id on edit page
    Then I verify "AA Outbound Call" link section has all the business object linked : "Service Request,Consumer"
    And I click on id of "Auto Assignment SR" in Links section of "Task" page
    And I click on edit button on view task page OR view SR page
    And I will update the following information in edit task page
      | status        | Closed               |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | <disposition>        |
    And I click on save button on task edit page
    Then I verify the sr status is updated to close and disposition is set to "<disposition>"
    Then I verify "AA Outbound Call" task status is updated to "Cancelled" on the link section
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated bpm process get api by service request id for process name "BLCRM_Auto_Assignment_SR"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | disposition        |
      | Selection Made     |
      | Selection Accepted |
      | Loss of Eligibility|
      | Selection Not Made |

  @CP-34267 @CP-34267-02 @priyal @crm-regression @ui-wf
  Scenario: Verify Link Consumer to the AA SR from UI on edit page
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Auto Assignment SR" service request page
    And I click on contact link button under LINK section
    And I click on Case and Consumer button
    And I searched consumer created by api script
    And I click on Search option
    And I will expand the consumer record
    When I click on Link Record Consumer button
    And I click on save button in create service request page
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Auto Assignment SR" link section has all the business object linked : "Consumer"
    And Close the soft assertions

  @CP-37010 @CP-37010-01 @CP-39361 @CP-39361-02 @moldir @ui-wf
  Scenario: Validate BLCRM AA SR Trigger WL1 Letter
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I create a case using Case Loader API
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click case consumer search tab
    And I search and select consumer associated to a Case
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Auto Assignment SR" service request page
    And I will provide following information before creating task
      | priority | 1|
    And I click on save button in create service request page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Auto Assignment SR" link section has all the business object linked : "Consumer,Case,Outbound Correspondence"
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I initiate get service request API by ID
    And I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | outboundCorrId |
    When I click on the "first available" Outbound Correspondence
    Then I should see the Link to the service request has the proper values
      | serviceRequestType | Auto Assignment SR |
