Feature: Inbound Correspondence Related Features

  @CP-13948  @CP-13948-01 @crm-regression @ui-ats @sang
  Scenario: Verify No Application view image icon is dispalyed for Application created through UI
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    Then I verify that there is no Inbound Correspondence application pdf view icon
    When I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    Then I verify that there is no Inbound Correspondence application pdf view icon

  @CP-13948  @CP-13948-02 @crm-regression @ui-ats @sang
  Scenario: Verify View application image icon is displayed for application created through inbound correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
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
      | Channel | ProcessType            | Application Type   | Application Signature | Application Signature Date | SetId          |
      | Fax     | INBOUND CORRESPONDENCE | MEDICAL ASSISTANCE | YES                   | today: yyyy-MM-dd          | documentId one |
    Then I submit ATS sendevent request
    Given I initiated mars search events api
    And I can provide event information to search event as
      | eventName  |
      | LINK_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 100  | 0    | eventId,desc |
    Then I received "100" events from search event API
    And there is event in search event results for "ATS" with values
      | externalId | internalRefType | externalRefType        |
      | documentId | APPLICATION     | INBOUND_CORRESPONDENCE |
    And I store inbound correspondence Application Id found through link event with id "base"
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter inbound correspondence created application id in the application search page
    When I click on search button in search application page
    And I click on the inbound correspondence search Id found in the result
    And I navigate to application tab page
    Then I verify that there is an Inbound Correspondence application pdf view icon
    And I click on "first" Inbound Correspondence application pdf view icon
    Then I check to see Inbound Document Id "one" received is viewable in pdf

  @CP-24221 @CP-24221-01 @crm-regression @ui-ats @ozgen
  Scenario: Adding Application ID and Consumer ID to View Outbound Correspondence Screen
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | Mail      |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | writtenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | Russian         |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1   | addressCity | addressState | addressZip | addressStreet2 |
      | Mailing     | 123 Auto Created | Houston     | Texas        | 77055      | Suite 2        |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType | phoneNumber  | primaryContactTypeInd |
      | faxNumber | RANDOM PHONE | false                 |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn       | genderCode | externalConsumerId | externalConsumerIdType | pregnancyInd | expectedBabies | expectedDueDate |
      | RANDOM FIRST      | RANDOM LAST      | Dr             | A                  | 1998-04-03  | 987654321 | Female     | 23456              | Internal               | true         | 2              | 2021-12-08      |
    And I initiate save applications api consumer 1 with program
      | CHIP |
      | Pregnancy Assistance |
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName | consumerMiddleName | accessType  | consumerType | correspondence     | authorizedRepOrgName | authorizedRepSignature | authorizedRepSignatureDate       |
      | RANDOM FIRST      | RANDOM LAST      | M                  | Full Access | Broker       | Do Not Receive     | VA Foundation        | true                   | 2021-06-03T19:54:57.274000+00:00 |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create outbound correspondence api for ATS
    Then I provide application details for POST Create Outbound Correspondence api
    And I provide application consumer details as Recipients for Create Outbound Correspondence api
    And I run POST Outbound Correspondence Call API
    Then I verify new Outbound Correspondence is created and stored id
    And I initiate and run Get Application Links Call
    And I will get "Inbound Correspondence" ID for "" type from the application links response
    And I verify newly created Outbound Correspondence is linked to application
    And I logged into CRM
    And I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I search api created outbound correspondence from ATS
    And I click on the correspondence id of the search result
    And I verify Application Id field and value on Outbound Correspondence Page
    And I verify each consumer Ids and their role values on Outbound Correspondence Page

  @CP-19846 @CP-19846-01 @crm-regression @ui-ats @burak
  Scenario: Verifying Search Panel embedded in the View Inbound Correspondence Details screen
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate request of documentId from Put ECMS documentencoded services with following values
      | documentType | maersk Application    |
      | Language     | SPANISH                |
      | Channel      | MAIL                   |
      | Status       | RECEIVED               |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | setId        | random                 |
      | StatusSetOn  | current                |
    When I send PUT ECMS documentencoded services and store documentId "one" in a list
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I select "APPLICATION" option from Create Links(s) dropdown
    Then I verify Search Application basic page fields
    And I verify options for dropdown "STATUS" are present
      | Approved to Post | Closed | Complete | Conflicts | Determining | Duplicate | Entering Data | Expired | Incomplete | Insufficient | Not Applying | Posted | Ready to Post | Received | Review | Reviewing Targets | Submitted | Targets Confirmed | Targets Identified | Targets Unidentified | Withdrawn |
    And I verify options for dropdown "APPLICATION CYCLE" are present
      | New | Renewal |
    And I verify options for dropdown "PROGRAM" are present
      | Medicaid | CHIP | Pregnancy Assistance | HCBS |
    And I verify options for dropdown "CHANNEL" are present
      | Email | Fax | Interface | Mail | Phone | Web | Web Chat |

  @CP-34251 @CP-34251-04 @CP-12704 @CP-12704-05 @crm-regression @ui-ats @burak
  Scenario: Verify able to search with Application Code under the Inbound Correspondence Details Page
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate request of documentId from Put ECMS documentencoded services with following values
      | documentType | maersk Application    |
      | Language     | SPANISH                |
      | Channel      | MAIL                   |
      | Status       | RECEIVED               |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | setId        | random                 |
      | StatusSetOn  | current                |
    When I send PUT ECMS documentencoded services and store documentId "one" in a list
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | Mail      |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | writtenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | Russian         |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1   | addressCity | addressState | addressZip | addressStreet2 |
      | Mailing     | 123 Auto Created | Houston     | Texas        | 77055      | Suite 2        |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress     |
      | autoAPI@test.com |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType | phoneNumber  | primaryContactTypeInd |
      | faxNumber | RANDOM PHONE | false                 |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I get the application GUID from API response
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I select "APPLICATION" option from Create Links(s) dropdown
    And I verify Search Application basic page fields
    And I search for a specific application in Application Tracking search by
      | APPLICATION CODE             |APP RECEIVED DATE FROM|
      | API CREATED APPLICATION CODE |TODAY                 |
    When I click on search button in search application page
    Then I verify the search result "APPLICATION CODE" field matches with the search parameter
    And I verify the search result "APPLICATION RECEIVED DATE" field matches with the search parameter
    And I see  results in given order in the search results in Application Tracking Search:
      | APPLICATION ID | APPLICATION CODE | LAST NAME | FIRST NAME | APPLICANT DOB | STATUS | APPLICATION CYCLE | APPLICATION RECEIVED DATE | PROGRAM | CHANNEL | EXTERNAL APP ID |


  @CP-19846 @CP-19846-02 @crm-regression @ui-ats @burak
  Scenario Outline: Verify able to link application to Inbound Correspondence and Link Events
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate request of documentId from Put ECMS documentencoded services with following values
      | documentType | maersk Application    |
      | Language     | SPANISH                |
      | Channel      | MAIL                   |
      | Status       | RECEIVED               |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | setId        | random                 |
      | StatusSetOn  | current                |
    When I send PUT ECMS documentencoded services and store documentId "one" in a list
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | Mail      |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | writtenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | Russian         |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1   | addressCity | addressState | addressZip | addressStreet2 |
      | Mailing     | 123 Auto Created | Houston     | Texas        | 77055      | Suite 2        |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress     |
      | autoAPI@test.com |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType | phoneNumber  | primaryContactTypeInd |
      | faxNumber | RANDOM PHONE | false                 |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I select "APPLICATION" option from Create Links(s) dropdown
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I link application to inbound correspondence
    And I view application linked to inbound correspondence
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "<numberOfEvents>" "LINK_EVENT for Inbound Correspondence" generated and verify details
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName  | module          | eventType                   | projectName | numberOfEvents |
      | LINK_EVENT | LINK_MANAGEMENT | Inbound Correspondence Save | BLCRM       | 2              |

  @CP-19846 @CP-19846-03 @crm-regression @ui-ats @burak
  Scenario: Verify Cancel button and Application ID doesn't function on Search Panel
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate request of documentId from Put ECMS documentencoded services with following values
      | documentType | maersk Application    |
      | Language     | SPANISH                |
      | Channel      | MAIL                   |
      | Status       | RECEIVED               |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | setId        | random                 |
      | StatusSetOn  | current                |
    When I send PUT ECMS documentencoded services and store documentId "one" in a list
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | Mail      |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | writtenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | Russian         |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1   | addressCity | addressState | addressZip | addressStreet2 |
      | Mailing     | 123 Auto Created | Houston     | Texas        | 77055      | Suite 2        |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress     |
      | autoAPI@test.com |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType | phoneNumber  | primaryContactTypeInd |
      | faxNumber | RANDOM PHONE | false                 |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I select "APPLICATION" option from Create Links(s) dropdown
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I verify application ID doesn't function on search results
    When I click on Cancel button to clear out the Application Search Criteria
    And I verify Link component displays on Inbound Correspondence Details Page