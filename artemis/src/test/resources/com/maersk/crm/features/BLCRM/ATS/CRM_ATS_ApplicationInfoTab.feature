Feature: ATS Application Info Tab feature

  @CP-31710 @CP-31710-01 @CP-34251 @CP-34251-05 @CP-12704 @CP-12704-06 @crm-regression @ui-ats @Prithika
  Scenario: Verify Application Info Tab is displayed from CaseConsumer Search
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd|submittedInd|
      | Medical Assistance | CURRENT YYYYMMDD        |   false       |true        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth    | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB     | RANDOM SSN    | Male       | English         | Spanish        |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB    | RANDOM SSN    | Male       | English         | Russian        |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I create a case using Case Loader API
    And I logged into CRM
    And I click case consumer search tab
    And I search , save and select consumer associated to a Case
    And I click on the Demographic Info Tab
    And I should not see application Info Tab
    Then I initiate link case api 0
      | ROLE       | PRIMARY INDIVIDUAL |
      | caseId     | CASE               |
      | actionType | CREATE_NEW         |
    Then I initiate link case api 1
      | ROLE       | APPLICATION MEMBER |
      | actionType | CREATE_NEW         |
    And I run the link case api
    When I initiated search application api for ats
    And I search for the created application using search api by "case id" for ats
    And I get the application ids linked to the case for "Primary Individual"
    Then I click case consumer search tab
    And I search , save and select consumer associated to a Case
    And I see the Application Info Tab
    And I click on the application info tab
    And I validate column order of Application Info Tab
      | APPLICATION ID | APPLICATION CODE | LAST NAME | FIRST NAME | APPLICANT DOB | STATUS | APPLICATION CYCLE | APPLICATION RECEIVED DATE | PROGRAM | CHANNEL | EXTERNAL APP ID |
    And I validate values of application Information for recent application
    Then I click on application Id 0
    And I see Application Tracking is selected by default
    Then I click on the back arrow in header row on Application Tracking page
    And I see the Application Info Tab
    And I validate column order of Application Info Tab
      | APPLICATION ID | APPLICATION CODE | LAST NAME | FIRST NAME | APPLICANT DOB | STATUS | APPLICATION CYCLE | APPLICATION RECEIVED DATE | PROGRAM | CHANNEL | EXTERNAL APP ID |

  @CP-31710 @CP-31710-02 @crm-regression @ui-ats @prithika
  Scenario: Verify Application Info Tab is displayed from Application Tracking- From Links
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd|submittedInd|
      | Medical Assistance | CURRENT YYYYMMDD        |   false       |true        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth    | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB     | RANDOM SSN    | Male       | English         | Spanish        |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB    | RANDOM SSN    | Male       | English         | Russian        |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiate create case api and create a new case
    Then I initiate link case api 0
      | ROLE       | PRIMARY INDIVIDUAL |
      | caseId     | CASE               |
      | actionType | CREATE_NEW         |
    Then I initiate link case api 1
      | ROLE       | APPLICATION MEMBER |
      | actionType | CREATE_NEW         |
    And I run the link case api
    When I initiated search application api for ats
    And I search for the created application using search api by "case id" for ats
    And I get the application ids linked to the case for "Primary Individual"
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I click on the previously created Case Id in ATS Links section
    And I see the Application Info Tab
    And I click on the application info tab
    And I validate column order of Application Info Tab
      | APPLICATION ID | APPLICATION CODE | LAST NAME | FIRST NAME | APPLICANT DOB | STATUS | APPLICATION CYCLE | APPLICATION RECEIVED DATE | PROGRAM | CHANNEL | EXTERNAL APP ID |
    And I validate values of application Information for recent application
    And I click on application Id 0
    And I see Application Tracking is selected by default
    Then I click on the back arrow in header row on Application Tracking page
    And I see the Application Info Tab
    And I validate column order of Application Info Tab
      | APPLICATION ID | APPLICATION CODE | LAST NAME | FIRST NAME | APPLICANT DOB | STATUS | APPLICATION CYCLE | APPLICATION RECEIVED DATE | PROGRAM | CHANNEL | EXTERNAL APP ID |

  @CP-31710 @CP-31710-03 @crm-regression @ui-ats @prithika
  Scenario: Verify Application Info Tab is displayed from Initiate Contact
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd|submittedInd|
      | Medical Assistance | CURRENT YYYYMMDD        |   false       |true        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth    | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB     | RANDOM SSN    | Male       | English         | Spanish        |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB    | RANDOM SSN    | Male       | English         | Russian        |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiate create case api and create a new case
    Then I initiate link case api 0
      | ROLE       | PRIMARY INDIVIDUAL |
      | caseId     | CASE               |
      | actionType | CREATE_NEW         |
    Then I initiate link case api 1
      | ROLE       | APPLICATION MEMBER |
      | actionType | CREATE_NEW         |
    And I run the link case api
    When I initiated search application api for ats
    And I search for the created application using search api by "case id" for ats
    And I get the application ids linked to the case for "Primary Individual"
    And I logged into CRM
    When I click on initiate contact record
    When I search consumers by Case Id
    Then I link the contact to an existing Case
    When I click on the Demographic Info Tab
    And I see the Application Info Tab
    And I click on the application info tab
    And I validate column order of Application Info Tab
      | APPLICATION ID | APPLICATION CODE | LAST NAME | FIRST NAME | APPLICANT DOB | STATUS | APPLICATION CYCLE | APPLICATION RECEIVED DATE | PROGRAM | CHANNEL | EXTERNAL APP ID |
    And I validate values of application Information for recent application
    And I click on application Id 0
    And I see Application Tracking is selected by default
    Then I click on the back arrow in header row on Application Tracking page
    And I see the Application Info Tab
    And I validate column order of Application Info Tab
      | APPLICATION ID | APPLICATION CODE | LAST NAME | FIRST NAME | APPLICANT DOB | STATUS | APPLICATION CYCLE | APPLICATION RECEIVED DATE | PROGRAM | CHANNEL | EXTERNAL APP ID |

  @CP-31710 @CP-31710-04 @crm-regression @ui-ats @prithika
  Scenario: Verify Application Info Tab is displayed from Outbound correspondence
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd|submittedInd|
      | Medical Assistance | CURRENT YYYYMMDD        |   false       |true        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth    | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB     | RANDOM SSN    | Male       | English         | Spanish        |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB    | RANDOM SSN    | Male       | English         | Russian        |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    When I initiate create case api and create a new case
    Then I initiate link case api 0
      | ROLE       | PRIMARY INDIVIDUAL |
      | caseId     | CASE               |
      | actionType | CREATE_NEW         |
    Then I initiate link case api 1
      | ROLE       | APPLICATION MEMBER |
      | actionType | CREATE_NEW         |
    And I run the link case api
    When I initiated search application api for ats
    And I search for the created application using search api by "case id" for ats
    And I get the application ids linked to the case for "Primary Individual"
    And I initiated create outbound correspondence api for ATS
    Then I provide application and case details for POST Create Outbound Correspondence api
    And I run POST Outbound Correspondence Call API
    Then I verify new Outbound Correspondence is created and stored id
    And I logged into CRM
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | CASEID | previouslyCreated |
    And I click on the Outbound Correspondence Id result
    And I click on CaseId on Links section
    When I click on the Demographic Info Tab
    And I see the Application Info Tab
    And I click on the application info tab
    And I validate column order of Application Info Tab
      | APPLICATION ID | APPLICATION CODE | LAST NAME | FIRST NAME | APPLICANT DOB | STATUS | APPLICATION CYCLE | APPLICATION RECEIVED DATE | PROGRAM | CHANNEL | EXTERNAL APP ID |
    And I validate values of application Information for recent application
    And I click on application Id 0
    And I see Application Tracking is selected by default
    Then I click on the back arrow in header row on Application Tracking page
    And I see the Application Info Tab
    And I validate column order of Application Info Tab
      | APPLICATION ID | APPLICATION CODE | LAST NAME | FIRST NAME | APPLICANT DOB | STATUS | APPLICATION CYCLE | APPLICATION RECEIVED DATE | PROGRAM | CHANNEL | EXTERNAL APP ID |

  @CP-31710 @CP-31710-05 @crm-regression @ui-ats @prithika
  Scenario: Verify Application Info Tab is displayed from Inbound correspondence
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiate request of documentId from Put ECMS documentencoded services with following values
      | documentType | maersk Application    |
      | Language     | SPANISH                |
      | Channel      | MAIL                   |
      | Status       | RECEIVED               |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | setId        | random                 |
      | StatusSetOn  | current                |
    When I send PUT ECMS documentencoded services and store documentId "one" in a list
    And I have prepared default sendevent request data for ATS from file "events/ATSSendEvent.json"
    And I have prepared main properties(override if exist) for ATS sendevent endpoint as
      | documentHandle | documentDate      |
      | documentId     | today: yyyy-MM-dd |
    And I have prepared keywordFields(override if exist) for ATS sendevent endpoint as
      | Channel | ProcessType            | Application Type   | Application Signature | Application Signature Date | Mailing is Same As Residence Address | SetId          |
      | Email   | INBOUND CORRESPONDENCE | MEDICAL ASSISTANCE | YES                   | today: yyyy-MM-dd          | checkbox: NO                         | documentId one |
    Then I submit ATS sendevent request
    And I initiate and run Get Inbound Correspondence Links Call
    Then I will get applicationId and taskId from the response
    And I save the application Id from Inbound document creation for fetching application details
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I save consumer Id from application linked to Inbound Doc For Search
    When I initiate create case api and create a new case
    Then I initiate link case api for primary user
    And I run the link case api
    When I initiated search application api for ats
    And I search for the created application using search api by "case id" for ats
    And I get the application ids linked to the case for "Primary Individual"
    And I logged into CRM
    When I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I select "Case/Consumer" option from Create Links(s) dropdown
    When I enter case id to be linked to the Inbound correspondence
    And I click on Search Button on Search Consumer Page
    And I validate Linking a "Case" to the Inbound Correspondence
    And I click on refresh button
    And I click on CaseId on Links section
    When I click on the Demographic Info Tab
    And I see the Application Info Tab
    And I click on the application info tab
    And I validate column order of Application Info Tab
      | APPLICATION ID | APPLICATION CODE | LAST NAME | FIRST NAME | APPLICANT DOB | STATUS | APPLICATION CYCLE | APPLICATION RECEIVED DATE | PROGRAM | CHANNEL | EXTERNAL APP ID |
    And I validate values of application Information for recent application
    And I click on application Id 0
    And I see Application Tracking is selected by default
    Then I click on the back arrow in header row on Application Tracking page
    And I see the Application Info Tab
    And I validate column order of Application Info Tab
      | APPLICATION ID | APPLICATION CODE | LAST NAME | FIRST NAME | APPLICANT DOB | STATUS | APPLICATION CYCLE | APPLICATION RECEIVED DATE | PROGRAM | CHANNEL | EXTERNAL APP ID |


