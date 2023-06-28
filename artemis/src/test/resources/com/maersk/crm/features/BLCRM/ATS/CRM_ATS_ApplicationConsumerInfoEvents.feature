Feature: ATS Application Consumer Info Related Events

  @CP-22374  @CP-22374-01  @ats-events @burak
  Scenario Outline:Validation of Application Consumer Status Save Event with PI Member
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    Then I verify Application created Success Message and Store Application ID value created
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_CONSUMER_STATUS_SAVE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                              | module      | eventType     | projectName |
      | APPLICATION_CONSUMER_STATUS_SAVE_EVENT | APPLICATION | STATUS UPDATE |[blank]|

  @CP-22374  @CP-22374-02 @burak @ats-events
  Scenario Outline:Validation of Application Consumer Status Save Event for App Member
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    And I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    Then I navigate to application tab page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    Then I get application id from the Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_CONSUMER_STATUS_SAVE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                              | module      | eventType     | projectName |
      | APPLICATION_CONSUMER_STATUS_SAVE_EVENT | APPLICATION | STATUS UPDATE |[blank]|

  @CP-22374  @CP-22374-03  @ats-events @burak
  Scenario Outline:Validation of Not Generating Application Consumer Status Save Event for Auth Rep  (negative)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    And I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    Then I navigate to application tab page
    When I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME                   | RANDOM 30   |
      | LAST NAME                    | RANDOM 30   |
      | AUTHORIZATION SIGNATURE DATE | RANDOM PAST |
    Then I click on Save button on Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_CONSUMER_STATUS_SAVE_EVENT" is not generated for "Auth. Rep."
    Examples:
      | eventName                              | module      | eventType     | projectName |
      | APPLICATION_CONSUMER_STATUS_SAVE_EVENT | APPLICATION | STATUS UPDATE |[blank]|

  @CP-22373 @CP-22373-04 @burak @ats-events-blats2
  Scenario Outline: Validation of Application Consumer Status Update Event for Duplicate Status UI
    Given I will get the Authentication token for "BLATS2" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I logged into CRM with "Service Tester 1" and select a project "BLATS2"
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
    Then I verify the "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT" generated for the "Primary Individual"
    Then I verify the "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT" generated for the "Application Member"
    And I verify status of the application is "Duplicate" in the events payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                                | module      | eventType | projectName |
      | APPLICATION_CONSUMER_STATUS_UPDATE_EVENT | APPLICATION |[blank]|             |

  @CP-22373 @CP-22373-05 @ats-events @burak
  Scenario Outline: Validation of Application Consumer Status Update Event for Withdrawn Status UI
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    When  I click withdraw dropdown to withdraw the application from the Application Tracking tab
    Then I see APPLICATION WITHDRAW REASON in Application Information panel
    Then I  select "Already Receiving Services" from select reason dropdown
    When I click save button in the select reason
    Then I verify I see warning message "Are you sure you want to Withdraw this Application?"
    When I click Continue button inside the warning message
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify the "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT" generated for the "Primary Individual"
    Then I verify the "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT" generated for the "Application Member"
    And I verify status of the application is "Withdrawn" in the events payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                                | module      | eventType | projectName |
      | APPLICATION_CONSUMER_STATUS_UPDATE_EVENT | APPLICATION |[blank]|             |

  @CP-22373 @CP-22373-06 @ats-events-blats2 @burak
  Scenario Outline: Validation of Application Consumer Status Update Event for Determining Status UI
    Given I will get the Authentication token for "BLATS2" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I logged into CRM with "Service Tester 1" and select a project "BLATS2"
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify the "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT" generated for the "Primary Individual"
    Then I verify the "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT" generated for the "Application Member"
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                                | module      | eventType | projectName |
      | APPLICATION_CONSUMER_STATUS_UPDATE_EVENT | APPLICATION |[blank]|             |

  @CP-22373 @CP-22373-07 @ats-events-blats2 @burak
  Scenario Outline: Validation of Not Generating Application Consumer Status Update Event for Consumer Without Programs Determining Status UI (Negative)
    Given I will get the Authentication token for "BLATS2" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I logged into CRM with "Service Tester 1" and select a project "BLATS2"
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click on Submit button only in Create Application Page
    Then I click Continue button inside the warning message
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify the "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT" not generated for the "Primary Individual"
    Examples:
      | eventName                                | module      | eventType | projectName |
      | APPLICATION_CONSUMER_STATUS_UPDATE_EVENT | APPLICATION |[blank]|             |

  @CP-16146 @CP-16146-01 @ats-events @ozgen
  Scenario Outline: Application Consumer Save Event for Primary Individual
    Given I logged into CRM
    And I saved logged in user ID
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I choose "CHIP" as program type
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME           | Alphabetic 30      |
      | MIDDLE INITIAL       | Alphabetic 1       |
      | LAST NAME            | Alphabetic 30      |
      | SUFFIX               | Alphabetic 3       |
      | DOB                  | random past date   |
      | GENDER               | Female             |
      | ARE YOU PREGNANT     | Yes                |
      | NO. BABIES EXPECTED  | 8                  |
      | EXPECTED DUE DATE    | random future date |
      | SSN                  | Numeric 9          |
      | SPOKEN LANGUAGE      | random dropdown    |
      | WRITTEN LANGUAGE     | random dropdown    |
      | EXTERNAL CONSUMER ID | Alpha-Numeric 15   |
      | EXTERNAL ID TYPE     | random dropdown    |
    And I choose Communication Opt In Information by the following list
      | Mail |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I click on Save button on Create Application Page
    Then I verify Application created Success Message and Store Application ID value created
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_CONSUMER_SAVE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                       | module      | eventType   | projectName |
      | APPLICATION_CONSUMER_SAVE_EVENT | APPLICATION | application |[blank]|

  @CP-16146 @CP-16146-02 @ats-events @ozgen
  Scenario Outline: Application Consumer Save Event for Application Member
    Given I logged into CRM
    And I saved logged in user ID
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    Then I verify Application created Success Message and Store Application ID value created
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME           | RANDOM 30        |
      | MI                   | RANDOM 1         |
      | LAST NAME            | RANDOM 30        |
      | SUFFIX               | RANDOM 3         |
      | DOB                  | random past date |
      | GENDER               | Female           |
      | ARE YOU PREGNANT     | YES              |
      | NO. OF BABIES        | 2                |
      | EXPECTED DUE DATE    | RANDOM           |
      | SSN                  | Numeric 9        |
      | EXTERNAL CONSUMER ID | RANDOM           |
      | EXTERNAL ID TYPE     | random dropdown  |
    And Select the "Medicaid" Program(s) for application member
    And click on save button for add application member
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_CONSUMER_SAVE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                       | module      | eventType        | projectName |
      | APPLICATION_CONSUMER_SAVE_EVENT | APPLICATION | consumer save am |[blank]|

  @CP-16146 @CP-16146-03 @ats-events @ozgen
  Scenario Outline: Application Consumer Save Event for Authorized Representative
    Given I logged into CRM
    And I saved logged in user ID
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    Then I verify Application created Success Message and Store Application ID value created
    When I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | AUTH TYPE                    | RANDOM        |
      | FIRST NAME                   | RANDOM 30     |
      | MI                           | RANDOM 1      |
      | LAST NAME                    | RANDOM 30     |
      | ORGANIZATION NAME            | RANDOM 5      |
      | ID NUMBER                    | Numeric 5     |
      | AUTHORIZATION SIGNATURE DATE | RANDOM PAST   |
      | END DATE                     | RANDOM FUTURE |
      | ADDRESS LINE 1               | ALPHA NUMERIC |
      | ADDRESS LINE 2               | ALPHA NUMERIC |
      | CITY                         | RANDOM        |
      | STATE                        | RANDOM        |
      | ZIP CODE                     | RANDOM        |
      | ACCESS TYPE                  | RANDOM        |
      | CORRESPONDENCE               | RANDOM        |
      | AUTHORIZED                   | Yes           |
      | AUTH STATUS                  | SET BY DATES  |
    Then I click on Save button on Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_CONSUMER_SAVE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                       | module      | eventType        | projectName |
      | APPLICATION_CONSUMER_SAVE_EVENT | APPLICATION | consumer save ar |[blank]|

  @CP-25950 @CP-25950-07 @ats-events @burak
  Scenario: Verify Application Consumer Status Update event generated for Not Applying to Programs
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode | notApplyingIndicator |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       | true                 |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode | notApplyingIndicator |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       | true                 |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Determining" in the application information
    Then I navigate to application tab page
    And I click on the Edit button for the Primary Individual Details
    And I deselect "Not Applying" program type
    Then I choose "Medicaid" as program type
    And  I click on Save button on Create Application Page
    And I click on application member name hyperlink to go to Application details page
    And I click on the edit button for Add application Member
    And I deselect "Not Applying" program type for Application Member
    And Select the "CHIP" Program(s) for application member
    And click on save button for add application member
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName                                |
      | APPLICATION_CONSUMER_STATUS_UPDATE_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 20   | 0    | eventId,desc |
    Then I received "20" events from search event API
    Then I verify The "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT" details for the "Primary Individual"
    Then I verify The "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT" details for the "Application Member"
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT" and subscriberId for ATS

  @CP-25950 @CP-25950-08 @ats-events @burak
  Scenario: Verify Application Consumer Status Update event generated for Programs to Not Applying
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | submittedInd |
      | Long Term Care  | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I initiate save applications api consumer 1 with program
      | HCBS |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Determining" in the application information
    Then I navigate to application tab page
    And I click on the Edit button for the Primary Individual Details
    And I deselect "HCBS" program type
    Then I choose "Not Applying" as program type
    And  I click on Save button on Create Application Page
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName                                |
      | APPLICATION_CONSUMER_STATUS_UPDATE_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 20   | 0    | eventId,desc |
    Then I received "20" events from search event API
    Then I verify The "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT" details for the "Primary Individual"
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT" and subscriberId for ATS

  @CP-16814 @CP-16814-01 @ats-events @ozgen
  Scenario Outline: Application Consumer Update Event for Primary Individual
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I navigate to application tab page
    When I click on the Edit button for the Primary Individual Details
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME           | Random 5           |
      | MIDDLE INITIAL       | Alphabetic 1       |
      | LAST NAME            | Random 5           |
      | SUFFIX               | Alphabetic 3       |
      | SSN                  | Numeric 9          |
      | DOB                  | random past date   |
      | GENDER               | Female             |
      | ARE YOU PREGNANT     | Yes                |
      | NO. BABIES EXPECTED  | 1                  |
      | EXPECTED DUE DATE    | random future date |
      | SPOKEN LANGUAGE      | random dropdown    |
      | WRITTEN LANGUAGE     | random dropdown    |
      | EXTERNAL CONSUMER ID | Alpha-Numeric 15   |
      | EXTERNAL ID TYPE     | random dropdown    |
    And I choose Communication Opt In Information by the following list
      | Mail |
    And I choose "CHIP" as program type
    And I get application id from the Create Application Page
    And I click on Save button on Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_CONSUMER_UPDATE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                         | module      | eventType   | projectName |
      | APPLICATION_CONSUMER_UPDATE_EVENT | APPLICATION | CONS UPDATE |[blank]|

  @CP-16814 @CP-16814-02 @ats-events @ozgen
  Scenario Outline: Application Consumer Update Event for Application Member
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | Phone     |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I navigate to application tab page
    And I get application id from the Create Application Page
    And I click on application member name hyperlink to go to Application details page
    And I verify edit button is displayed and clickable in Add Application member
    And I click on the edit button for Add application Member
    And I fill in the following application member values
      | FIRST NAME           | Random 5         |
      | MI                   | RANDOM 1         |
      | LAST NAME            | Random 5         |
      | SUFFIX               | RANDOM 3         |
      | DOB                  | random past date |
      | GENDER               | Female           |
      | ARE YOU PREGNANT     | YES              |
      | NO. OF BABIES        | 3                |
      | EXPECTED DUE DATE    | RANDOM           |
      | SSN                  | Edit Numeric 9   |
      | EXTERNAL CONSUMER ID | RANDOM           |
      | EXTERNAL ID TYPE     | random dropdown  |
    And Select the "Medicaid" Program(s) for application member
    And click on save button for add application member
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_CONSUMER_UPDATE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                         | module      | eventType   | projectName |
      | APPLICATION_CONSUMER_UPDATE_EVENT | APPLICATION | CONS UPDATE | BLCRM       |

  @CP-16814 @CP-16814-03 @ats-events @ozgen
  Scenario Outline: Application Consumer Update Event for Authorized Representative
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | Phone     |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 1
      | consumerFirstName | consumerLastName | consumerMiddleName | accessType  | consumerType | correspondence | authorizedRepOrgName | authorizedRepSignature | authorizedRepSignatureDate       |
      | Authfirstname     | Authlastname     | M                  | Full Access | Broker       | Do Not Receive | VA Foundation        | true                   | 2021-06-03T19:54:57.274000+00:00 |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I navigate to application tab page
    And I get application id from the Create Application Page
    And I click on Added Authorized Representative
    Then I click on Edit button for Authorized Representative
    Then I fill in the following Authorized Representative values
      | AUTH TYPE                    | RANDOM        |
      | FIRST NAME                   | RANDOM 30     |
      | MI                           | RANDOM 1      |
      | LAST NAME                    | RANDOM 30     |
      | ORGANIZATION NAME            | RANDOM 5      |
      | ID NUMBER                    | Numeric 5     |
      | AUTHORIZATION SIGNATURE DATE | RANDOM PAST   |
      | START DATE                   | RANDOM PAST   |
      | END DATE                     | RANDOM FUTURE |
      | ACCESS TYPE                  | RANDOM        |
      | CORRESPONDENCE               | RANDOM        |
      | AUTHORIZED                   | Yes           |
      | AUTH STATUS                  | SET BY DATES  |
    Then I click on Save button on Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_CONSUMER_UPDATE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                         | module      | eventType   | projectName |
      | APPLICATION_CONSUMER_UPDATE_EVENT | APPLICATION | CONS UPDATE | BLCRM       |