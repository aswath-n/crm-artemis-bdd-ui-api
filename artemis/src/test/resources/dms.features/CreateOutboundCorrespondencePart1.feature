Feature: Creating Outbound Correspondence from CP part 1

  @CP-25306 @CP-25306-01  @ui-ecms1 @RuslanL
  Scenario Outline: Verify user navigate to View/Edit Correspondence after User Creates Outbound Correspondence (Active contact - Consumer on case)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I create an Outbound Correspondence
    And "Regarding" sections are hidden
    And "Body Data" sections are hidden
    And I have selected "<correspondenceType>" as a type
    Then I verify the Required fields values are displayed on create OB correspondence request page
      | caseID      | <caseIDIsDisplayed>     |
      | consumerIDs | <consumerIDIsDisplayed> |
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I click to save the Outbound Correspondence Request
    Then I should be navigated to View Outbound Correspondence details
      | 100 Main street  Canaan,NY 12029 |
    Examples:
      | correspondenceType                         | caseIDIsDisplayed | consumerIDIsDisplayed | address1        | CITY   | State    | zipcode |
      | Case Consumer Send Immediately - CCSENDIMM | true              | true                  | 100 Main street | Canaan | New York | 12029   |


  @CP-25306 @CP-25306-02  @ui-ecms1 @RuslanL
  Scenario: Verify user navigate to View/Edit Correspondence after User Creates Outbound Correspondence (Active contact - Consumer with no case)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer not on a case that wants to send an Outbound Correspondence
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I create an Outbound Correspondence
    And "Regarding" sections are hidden
    And "Body Data" sections are hidden
    And I have selected "Consumer Only Send Immediately - CONSENDIMM" as a type
    Then I click to save the Outbound Correspondence Request
    Then I should be navigated to View Outbound Correspondence details
      | null |

  @CP-25306 @CP-25306-03  @ui-ecms1 @RuslanL
  Scenario Outline: Verify user navigate to View/Edit Correspondence after User Creates Outbound Correspondence (context of consumer on a case - searching for a case from case / consumer searching)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And "Regarding" sections are hidden
    And "Body Data" sections are hidden
    And I have selected "<correspondenceType>" as a type
    Then I verify the Required fields values are displayed on create OB correspondence request page
      | caseID      | <caseIDIsDisplayed>     |
      | consumerIDs | <consumerIDIsDisplayed> |
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I click to save the Outbound Correspondence Request
    Then I should be navigated to View Outbound Correspondence details
      | 100 Main street  Canaan,NY 12029 |
    Examples:
      | correspondenceType                         | caseIDIsDisplayed | consumerIDIsDisplayed | address1        | CITY   | State    | zipcode |
      | Case Consumer Send Immediately - CCSENDIMM | true              | true                  | 100 Main street | Canaan | New York | 12029   |


  @CP-25306 @CP-25306-04  @ui-ecms1 @RuslanL
  Scenario: Verify user navigate to View/Edit Correspondence after User Creates Outbound Correspondence (context of a consumer without a case - searching for a case from case / consumer searching)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer not on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And "Regarding" sections are hidden
    And "Body Data" sections are hidden
    And I have selected "Consumer Only Send Immediately - CONSENDIMM" as a type
    Then I click to save the Outbound Correspondence Request
    Then I should be navigated to View Outbound Correspondence details
      | null |


  @CP-25306 @CP-25306-06  @ui-ecms1 @RuslanL
  Scenario: Verify user navigate to View/Edit Correspondence after User Creates Outbound Correspondence (from my Task for Active contact - Consumer with no case)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Then If any In Progress task present then update that to Cancelled
    When I have a request to create External task with the following values
      | taskTypeId | 13241 |
    Then I send the request for the External task endpoint
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    And I click on initiate button in task search page
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I have a consumer not on a case that wants to send an Outbound Correspondence
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I create an Outbound Correspondence
    And "Regarding" sections are hidden
    And "Body Data" sections are hidden
    And I have selected "Consumer Only Send Immediately - CONSENDIMM" as a type
    Then I click to save the Outbound Correspondence Request
    Then I should be navigated to View Outbound Correspondence details
      | null |
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I update the task status in task slider as "Complete"
    And I click on save in Task Slider


  #as per CP-33215,its out of scope
  #@CP-25306 @CP-25306-08  @ui-ecms1 @RuslanL
  Scenario: Verify user navigate to View/Edit Correspondence after User Creates Outbound Correspondence (from my Task - context of a consumer without a case)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Then If any In Progress task present then update that to Cancelled
    When I have a request to create External task with the following values
      | taskTypeId | 13241 |
    Then I send the request for the External task endpoint
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    And I click on initiate button in task search page
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I have a consumer not on a case that wants to send an Outbound Correspondence
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And "Regarding" sections are hidden
    And "Body Data" sections are hidden
    And I have selected "Consumer Only Send Immediately - CONSENDIMM" as a type
    Then I click to save the Outbound Correspondence Request
    Then I should be navigated to View Outbound Correspondence details
      | null |
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I update the task status in task slider as "Complete"
    And I click on save in Task Slider

    ##################################################################################CP-3208###############################################################################

  @CP-3208 @CP-3208-01 @UI-ECMS @ECMS-UI-REGRESSION @burak
  Scenario: Verify Navigating to Correspondence Screen from Case&Contact Details Summary Tab
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I click on the previous created CID "previouslyCreated"
    Then I should be navigated to View Outbound Correspondence details
      | 12 address st  Canaan,NY 12029 |

  @CP-3208 @CP-3208-02  @UI-ECMS @ECMS-UI-REGRESSION @RuslanL
  Scenario: Verify View Outbound Correspondence Full Details
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate post outbound correspondence with four channels
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I search for the Outbound Correspondence with the new created CId
    And I click on the correspondence id of the search result
    And I verify View Outbound Correspondence Details page

  @CP-3208 @CP-3208-03 @UI-ECMS @ECMS-UI-REGRESSION @burak
  Scenario: Verify View Outbound Correspondence UpdatedBy and UpdatedOn Values after updating the OB with different User
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate post outbound correspondence with four channels
    And I logged into CRM
     And I saved logged in user ID
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I search for the Outbound Correspondence with the new created CId
    And I click on the correspondence id of the search result
    And I click on edit button on view Outbound Correspondence details
    And I select "On Hold" value from "Correspondence status" dropdown
    And I select "Volume control" value from "Correspondence Reason" dropdown
    And I click on Save button in outbound correspondence edit
    And I verify updated on and updated by values on Outbound Correspondence Details
    And I verify the correspondence status is "On Hold"

    ##################################################################################CP-3190###############################################################################

  @CP-3190 @CP-3190-01 @UI-ECMS @ECMS-UI-REGRESSION @burak
  Scenario: Verify View Outbound Correspondence Notification fields
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate post outbound correspondence with four channels
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I search for the Outbound Correspondence with the new created CId
    And I click on the correspondence id of the search result
    And I verify Outbound Correspondence Notification for one recipient and four Channels

  @CP-3190 @CP-3190-02 @UI-ECMS @ECMS-UI-REGRESSION @burak
  Scenario Outline: Verify Notification history for different Notification Status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have a Outbound Correspondence with Random Destination information for "Mail" channel
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I store notification status and date
    And I click on notification actions dropdown to select "<notificationStatus>"
    And I verify status history of notification status for "<notificationStatus>"
    Examples:
      | notificationStatus |
      | RETURNED           |
      | RESEND             |
      | SENT               |
      | HOLD               |
      | CANCEL             |

  @CP-3190 @CP-3190-03  @UI-ECMS @ECMS-UI-REGRESSION @RuslanL
  Scenario: Verify Correspondence Screen will Hide Notifications with Precluded Status.
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate post outbound correspondence with four channels
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I search for the Outbound Correspondence with the new created CId
    And I click on the correspondence id of the search result
    When I change the status for any created notification to Precluded
    Given After already authenticated I will log into CP "projectName"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I search for the Outbound Correspondence with the new created CId
    And I click on the correspondence id of the search result
    Then I will not see Preclude notification on IU

    ##################################################################################CP-30088###############################################################################
  @CP-30088 @CP-30088-01 @UI-ECMS @ECMS-UI-REGRESSION @James
  Scenario: Verify Reason field on Outbound Correspondence Details Page is increased to 50 character limit
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    And I update the Outbound Correspondence Notification with the following values
      | Error | This is going to be fifty characters and verified! |
    Given I logged into CRM
    And I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the Outbound Correspondence Id result
    Then I should see the notification status is "Error" along with reason popuplated with "This is going to be fifty characters and verified!"

  @API-CP-29535 @API-CP-29535-01 @API-ECMS @RuslanL
  Scenario Outline: Verify 2 character postal state or territory abbreviation, when request includes the State value
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | default         |
      | language                        | English         |
      | caseId                          | Random          |
      | regardingConsumerId             | Random          |
      | requesterId                     | 2425            |
      | requesterType                   | ConnectionPoint |
      | channelType                     | Mail            |
      | address                         | random          |
    And I update address "<stateCode>" for OB correspondence request
    When I send the request for an Outbound Correspondence to the service
    Then I should see a "<message>" in OB correspondence response with "<statusCode>"
    Examples:
      | stateCode                         | message               | statusCode |
      | null                              | ErrorStateNotProvided | 400        |
      | stateFullName                     | ErrorInvalidStateCode | 400        |
      | correctStateAbbreviationLowerCase | ErrorInvalidStateCode | 400        |
      | correctStateAbbreviationCamelCase | ErrorInvalidStateCode | 400        |
      | emptyString                       | ErrorInvalidStateCode | 400        |
      | anyTwoUpperCase                   | ErrorInvalidStateCode | 400        |
      | numeric                           | ErrorInvalidStateCode | 400        |
      | correctStateAbbreviation          | Success               | 201        |

  @API-CP-29535 @API-CP-29535-02 @api-ecms-ineb @RuslanL
  Scenario Outline: Verify 2 character postal state or territory abbreviation, when request includes the State value
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have specified the following values in the request for an Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | default         |
      | language                        | English         |
      | caseId                          | Random          |
      | regardingConsumerId             | Random          |
      | requesterId                     | 2425            |
      | requesterType                   | ConnectionPoint |
      | channelType                     | Mail            |
      | address                         | random          |
    And I update address "<stateCode>" for OB correspondence request
    When I send the request for an Outbound Correspondence to the service
    Then I should see a "<message>" in OB correspondence response with "<statusCode>"
    Examples:
      | stateCode                         | message               | statusCode |
      | null                              | ErrorStateNotProvided | 400        |
      | stateFullName                     | ErrorInvalidStateCode | 400        |
      | correctStateAbbreviationLowerCase | ErrorInvalidStateCode | 400        |
      | correctStateAbbreviationCamelCase | ErrorInvalidStateCode | 400        |
      | emptyString                       | ErrorInvalidStateCode | 400        |
      | anyTwoUpperCase                   | ErrorInvalidStateCode | 400        |
      | numeric                           | ErrorInvalidStateCode | 400        |
      | correctStateAbbreviation          | Success               | 201        |









