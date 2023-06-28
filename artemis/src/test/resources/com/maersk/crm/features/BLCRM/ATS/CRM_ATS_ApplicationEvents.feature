Feature: ATS Application related Events

  @CP-15702  @CP-15702-01 @ozgen @ats-events @ats-smoke
  Scenario Outline:Validation of Application Save Event
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    Then I verify Application created Success Message and Store Application ID value created
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_SAVE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName              | module      | eventType   | projectName |
      | APPLICATION_SAVE_EVENT | APPLICATION | application |[blank]|

  @CP-15702  @CP-15702-02 @ozgen @ats-events
  Scenario Outline:Validation of Application Status Save Event
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    Then I get application id from the Create Application Page
    Then I verify Application created Success Message and Store Application ID value created
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_STATUS_SAVE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                     | module      | eventType   | projectName |
      | APPLICATION_STATUS_SAVE_EVENT | APPLICATION | application |[blank]|

  @CP-14369 @CP-14369-07 @CP-18263 @CP-18263-09 @CP-17716 @CP-17716-01 @CP-22629 @CP-22629-01 @ats-events-blats2 @sang
  Scenario Outline: Medical Assistance Determining Application Status Update Event for clicking on the new button in Member matching page
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "Pregnancy Assistance" as program type
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_STATUS_UPDATE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                       | module      | eventType     | projectName |
      | APPLICATION_STATUS_UPDATE_EVENT | APPLICATION | STATUS UPDATE |[blank]|

  @CP-14369 @CP-14369-07 @CP-18263 @CP-18263-10 @CP-17716 @CP-17716-02 @CP-22629 @CP-22629-02 @ats-events-blats2 @sang
  Scenario Outline:Long Term Care Determining Application Status Update Event for clicking on the new button in Member matching page
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | DOB    | random past date |
      | GENDER | Female           |
      | SSN    | Numeric 9        |
    And I choose "HCBS" as program type
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_STATUS_UPDATE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                       | module      | eventType     | projectName |
      | APPLICATION_STATUS_UPDATE_EVENT | APPLICATION | STATUS UPDATE |[blank]|

  @CP-17716 @CP-17716-03 @ats-events-blats2 @sang
  Scenario Outline: Medical Assistance Application Status Update Event for clicking on Submit Button
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I click on Submit button only in Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_STATUS_UPDATE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                       | module      | eventType               | projectName |
      | APPLICATION_STATUS_UPDATE_EVENT | APPLICATION | STATUS UPDATE SUBMITTED |[blank]|

  @CP-17716 @CP-17716-04 @ats-events-blats2 @sang
  Scenario Outline: Long Term Care Application Status Update Event for clicking on Submit Button
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I click on Submit button only in Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_STATUS_UPDATE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                       | module      | eventType               | projectName |
      | APPLICATION_STATUS_UPDATE_EVENT | APPLICATION | STATUS UPDATE SUBMITTED |[blank]|

  @CP-17716 @CP-17716-05 @CP-22629 @CP-22629-03 @ats-events-blats2 @sang
  Scenario Outline: Medical Assistance Application Status Update Event for clicking on Duplicate Button
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I POST ATS save application api
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I POST ATS save application api
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
  #  And I click on Submit button only in Create Application Page
    And I click on Submit button and continue button for application without Programs
    And I see I navigated to Member Matching page
    And I click on the "0" select box for matching application in Member Matching page
    And I click on the Duplicate button on Member Matching page & Continue
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_STATUS_UPDATE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                       | module      | eventType     | projectName |
      | APPLICATION_STATUS_UPDATE_EVENT | APPLICATION | STATUS UPDATE |[blank]|

  @CP-17716 @CP-17716-06 @CP-22629 @CP-22629-04 @ats-events @sang
  Scenario Outline: Create Application Record to withdraw status update event for Already Receiving Services reason
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I navigate to Application Tracking
    And I see application Status as "Entering Data" in the application information
    Then I verify I see Withdraw button displayed in Application Information panel
    When  I click withdraw dropdown to withdraw the application from the Application Tracking tab
    Then I see APPLICATION WITHDRAW REASON in Application Information panel
    Then I  select "Already Receiving Services" from select reason dropdown
    When I click save button in the select reason
    Then I verify I see warning message "Are you sure you want to Withdraw this Application?"
    When I click Continue button inside the warning message
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_STATUS_UPDATE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                       | module      | eventType     | projectName |
      | APPLICATION_STATUS_UPDATE_EVENT | APPLICATION | STATUS UPDATE |[blank]|

  @CP-17716 @CP-17716-07 @CP-22629 @CP-22629-05 @ats-events @sang
  Scenario Outline: Create Application Record to withdraw status update event for Not Interested in Services reason
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I navigate to Application Tracking
    And I see application Status as "Entering Data" in the application information
    Then I verify I see Withdraw button displayed in Application Information panel
    When  I click withdraw dropdown to withdraw the application from the Application Tracking tab
    Then I see APPLICATION WITHDRAW REASON in Application Information panel
    Then I  select "Not Interested in Services" from select reason dropdown
    When I click save button in the select reason
    Then I verify I see warning message "Are you sure you want to Withdraw this Application?"
    When I click Continue button inside the warning message
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_STATUS_UPDATE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                       | module      | eventType     | projectName |
      | APPLICATION_STATUS_UPDATE_EVENT | APPLICATION | STATUS UPDATE |[blank]|

  @CP-20736 @CP-20736-02 @ats-events-blats2 @vinuta
  Scenario Outline: Request Application Status Update Event when an Application Status updated to ‘DUPLICATE’
    Given I logged into CRM
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    Then  I click on Submit button and continue button for application without Programs
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    Then I verify I am on the Application Tracking Page
    And I navigate to Create "LONG TERM CARE" application page
    And I create duplicate application to land on member matching page
    And I click on Save button on Create Application Page
    And I get application id from the Create Application Page
    Then I click on Submit button and continue button for application without Programs
    And I see I navigated to Member Matching page
    And I click on the "0" select box for matching application in Member Matching page
    When I click on the Duplicate button on Member Matching page & Continue
    And I saved logged in user ID
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_STATUS_UPDATE_EVENT" details in the event payload
    And I verify status of the application is "Duplicate" in the events payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                       | module      | eventType        | projectName |
      | APPLICATION_STATUS_UPDATE_EVENT | APPLICATION | STATUS DUPLICATE |[blank]|

  @CP-16829  @CP-16829-01 @CP-16673 @CP-16673-03 @vinuta @sang @ats-events
  Scenario Outline:Validation of Application Update Event
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    Then I verify Application created Success Message and Store Application ID value created
    Then Wait for 3 seconds
    When I click on the Edit button for the Primary Individual Details
    And I select Primary Individual Application information with the following
      | CYCLE             | Renewal   |
      | RECEIVED DATE     | current   |
      | PRIORITY          | High      |
      | CHANNEL           | Email     |
      | SIGNATURE         | Yes       |
      | SIGNATURE DATE    | yyyymmdd  |
      | RECEIVED LANGUAGE | Russian   |
    And I click on Save button on Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_UPDATE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                | module      | eventType          | projectName |
      | APPLICATION_UPDATE_EVENT | APPLICATION | application update |[blank]|

  @CP-16829  @CP-16829-02 @vinuta @ats-events
  Scenario Outline:Validation of Application Update Event not generated when clicked on cancel
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    Then I verify Application created Success Message and Store Application ID value created
    When I click on the Edit button for the Primary Individual Details
    And I click on Cancel button for Primary Individual Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "<eventName>" is not generated for "application"
    Examples:
      | eventName                | module      | eventType   | projectName |
      | APPLICATION_UPDATE_EVENT | APPLICATION | application |[blank]|

  @CP-22631 @CP-22631-01 @ats-events-blats2 @burak
  Scenario Outline:Validation of Application Status Save Event fields for Entering Data Status UI
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I navigate to Application Tracking
    And I see application Status as "Entering Data" in the application information
    Then I get application id from the Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_STATUS_SAVE_EVENT" statusId is "Entering Data" and statusReason is "null" in the event payload
    Examples:
      | eventName                     | module      | eventType   | projectName |
      | APPLICATION_STATUS_SAVE_EVENT | APPLICATION | application |[blank]|

  @CP-22631 @CP-22631-02 @ats-events @burak
  Scenario: Validation of Application Status Save Event fields for Entering Data Status API
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName                     |
      | APPLICATION_STATUS_SAVE_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 20   | 0    | eventId,desc |
    Then I received "20" events from search event API
    Then I verify "APPLICATION_STATUS_SAVE_EVENT" statusId is "Entering Data" and statusReason is "null" in the event payload

  @CP-22631 @CP-22631-03 @ats-events @burak #refactored due to new functionality CP-28625
  Scenario: Validation of Application Status Save Event fields for Submitted  Status
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName                     |
      | APPLICATION_STATUS_SAVE_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 20   | 0    | eventId,desc |
    Then I received "20" events from search event API
    Then I verify "APPLICATION_STATUS_SAVE_EVENT" statusId is "Entering Data" and statusReason is "null" in the event payload

  @CP-22631 @CP-22631-04  @ats-events @burak #refactored due to new functionality CP-28625
  Scenario: Validation of Application Status Save Event fields for Insufficient  Status
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      |[blank]|                  |[blank]|
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName                     |
      | APPLICATION_STATUS_SAVE_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 20   | 0    | eventId,desc |
    Then I received "20" events from search event API
    Then I verify "APPLICATION_STATUS_SAVE_EVENT" statusId is "Entering Data" and statusReason is "null" in the event payload

  @CP-22636 @CP-22636-01 @ats-events-blats2 @burak
  Scenario Outline: Validation of link events when Link Application marked as Duplicate to the associated Application
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I POST ATS save application api
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    And I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    And I click on the "0" select box for matching application in Member Matching page
    And I click on the Duplicate button on Member Matching page & Continue
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "2" "LINK_EVENT" generated and verify details
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName  | module          | eventType | projectName |
      | LINK_EVENT | LINK_MANAGEMENT |[blank]|             |

  @CP-23900  @CP-23900-01 @burak @ats-events
  Scenario Outline:Validation of External Application ID field for Application Save Event
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I select Primary Individual Application information with the following
      | EXTERNAL APPLICATION ID | Alpha-Numeric 36 |
    Then I click on Save button on Create Application Page
    Then I verify Application created Success Message and Store Application ID value created
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_SAVE_EVENT" details in the event payload
    Examples:
      | eventName              | module      | eventType   | projectName |
      | APPLICATION_SAVE_EVENT | APPLICATION | application |[blank]|

  @CP-23900  @CP-23900-02 @burak @ats-events
  Scenario Outline:Validation of External Application ID field for Application Update Event
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I select Primary Individual Application information with the following
      | CYCLE             | Renewal  |
      | RECEIVED DATE     | current |
      | PRIORITY          | High     |
      | CHANNEL           | Email    |
      | SIGNATURE         | Yes      |
      | SIGNATURE DATE    | yyyymmdd |
      | RECEIVED LANGUAGE | Russian  |
    And I click on Save button on Create Application Page
    Then I verify Application created Success Message and Store Application ID value created
    And I navigate to Application Tracking
    Then I click application id under Application Tracking tab in the Application Information panel
    When I click on the Edit button for the Primary Individual Details
    And I select Primary Individual Application information with the following
      | EXTERNAL APPLICATION ID | Alpha-Numeric 36 |
    And I click on Save button on Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_UPDATE_EVENT" details in the event payload
    Examples:
      | eventName                | module      | eventType | projectName |
      | APPLICATION_UPDATE_EVENT | APPLICATION | PI UPDATE |[blank]|

