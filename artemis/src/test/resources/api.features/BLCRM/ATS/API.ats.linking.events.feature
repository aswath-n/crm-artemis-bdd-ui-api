Feature: ATS Link Events

  @CP-31251 @CP-31251-01 @ats-events @ozgen
  Scenario: Creating Link events when Matching inbound application with case and consumer not on a case and selecting LINK Button
    #Matching data has one case, and one consumer not on a case, user selects one case data from potential matches and click on LINK
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn        | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | RANDOM SSN | Male       | English         | Spanish        |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I initiate link case api 0
      | ROLE       | PRIMARY INDIVIDUAL |
      | actionType | CREATE_NEW         |
    Then I initiate link case api 1
      | ROLE       | APPLICATION MEMBER |
      | actionType | CREATE_NEW         |
    And I run the link case api
    And I capture the case id from link case api
    And I capture the consumer id from link case api
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Spanish        |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 2
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I get second application member details to create consumer not on a case
    Then I logged into CRM
    When I navigate to case and consumer search page
    And I search for consumer have First Name as "Ume" and Last Name as "Kum"
    And I click on Search Button
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I provide matching application member details for consumers not on a case
    When I click on Create Consumer Button
    Then I get consumer not on a case Id from header
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
   # And I see I navigated to Member Matching page
    And I select the case Id from the potential matches
    And I verify "ADD" checkbox is selected for "CONSUMER 0" on member matching page
    And I verify "ADD" checkbox is not selected for "CONSUMER 1" on member matching page
    And I select "ADD" checkbox for "APPLICATION MEMBER 1" on member matching page
    And I select "ADD" checkbox for "CONS NOT ON A CASE" on member matching page
    When I click on the "LINK" button on Member Matching
    And I click on the "SAVE_ACTION" button on Member Matching
    When I initiated mars search events api
    And I can provide event information to search event as
      | eventName  |
      | LINK_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 100  | 0    | eventId,desc |
    Then I received "100" events from search event API
    And there is event in search event results for "ATS" with values
      | externalId | internalRefType | externalRefType |
      | caseId     | APPLICATION     | CASE            |
    And there is event in search event results for "ATS" with values
      | externalId    | internalRefType | externalRefType |
      | applicationId | CASE            | APPLICATION     |
    And there is event in search event results for "ATS" with values
      | externalId              | internalRefType | externalRefType      |
      | applicationConsumerIdPI | CONSUMER        | APPLICATION_CONSUMER |
    And there is event in search event results for "ATS" with values
      | externalId              | internalRefType | externalRefType      |
      | applicationConsumerIdAM | CONSUMER        | APPLICATION_CONSUMER |
    And there is event in search event results for "ATS" with values
      | externalId              | internalRefType | externalRefType      |
      | applicationConsumerIdAM | CONSUMER        | APPLICATION_CONSUMER |
    And there is event in search event results for "ATS" with values
      | externalId   | internalRefType      | externalRefType |
      | consumerIdPI | APPLICATION_CONSUMER | CONSUMER        |
    And there is event in search event results for "ATS" with values
      | externalId   | internalRefType      | externalRefType |
      | consumerIdAM | APPLICATION_CONSUMER | CONSUMER        |
    And there is event in search event results for "ATS" with values
      | externalId   | internalRefType      | externalRefType |
      | consumerIdAM | APPLICATION_CONSUMER | CONSUMER        |
    And I verify 8 events found for "ATS" with above values

  @CP-31274 @CP-31274-01 @ats-events @ozgen
  Scenario: Creating Link events with each case and consumer not on a case and selecting NEW CASE Button
    #Matching data has one case data, and one consumer not on a case, user doesnt select any case data from potential matches and click on NEW CASE
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn        | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | RANDOM SSN | Male       | English         | Spanish        |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I initiate link case api 0
      | ROLE       | PRIMARY INDIVIDUAL |
      | actionType | CREATE_NEW         |
    Then I initiate link case api 1
      | ROLE       | APPLICATION MEMBER |
      | actionType | CREATE_NEW         |
    And I run the link case api
    And I capture the case id from link case api
    And I capture the consumer id from link case api
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Spanish        |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 2
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I get second application member details to create consumer not on a case
    Then I logged into CRM
    When I navigate to case and consumer search page
    And I search for consumer have First Name as "Ume" and Last Name as "Kum"
    And I click on Search Button
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I provide matching application member details for consumers not on a case
    When I click on Create Consumer Button
    Then I get consumer not on a case Id from header
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click submit button twice
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    And I select "ADD" checkbox for "APPLICATION MEMBER 1" on member matching page
    And I select "ADD" checkbox for "CONS NOT ON A CASE" on member matching page
    And I select "ADD" checkbox for "ONLY CASE MEMBER" on member matching page
    When I click on the "NEW CASE" button on Member Matching
    And I click on the "SAVE_ACTION" button on Member Matching
    When I initiated mars search events api
    And I can provide event information to search event as
      | eventName  |
      | LINK_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 100  | 0    | eventId,desc |
    Then I received "100" events from search event API
    And there is event in search event results for "ATS" with values
      | externalId | internalRefType | externalRefType |
      | caseId     | APPLICATION     | CASE            |
    And there is event in search event results for "ATS" with values
      | externalId    | internalRefType | externalRefType |
      | applicationId | CASE            | APPLICATION     |
    And there is event in search event results for "ATS" with values
      | externalId              | internalRefType | externalRefType      |
      | applicationConsumerIdPI | CONSUMER        | APPLICATION_CONSUMER |
    And there is event in search event results for "ATS" with values
      | externalId              | internalRefType | externalRefType      |
      | applicationConsumerIdAM | CONSUMER        | APPLICATION_CONSUMER |
    And there is event in search event results for "ATS" with values
      | externalId              | internalRefType | externalRefType      |
      | applicationConsumerIdAM | CONSUMER        | APPLICATION_CONSUMER |
    And there is event in search event results for "ATS" with values
      | externalId   | internalRefType      | externalRefType |
      | consumerIdPI | APPLICATION_CONSUMER | CONSUMER        |
    And there is event in search event results for "ATS" with values
      | externalId   | internalRefType      | externalRefType |
      | consumerIdAM | APPLICATION_CONSUMER | CONSUMER        |
    And there is event in search event results for "ATS" with values
      | externalId   | internalRefType      | externalRefType |
      | consumerIdAM | APPLICATION_CONSUMER | CONSUMER        |
    And I verify 8 events found for "ATS" with above values

  @CP-32329 @CP-32329-05 @CP-32343 @CP-32343-02 @ats-events @burak
  Scenario: Verify Links has been established between Case<->Application and Consumers<->Application Members for Link action
    #Verify default selection of all consumers under existing case record
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn        | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | RANDOM SSN | Male       | English         | Spanish        |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I initiate link case api 0
      | ROLE       | PRIMARY INDIVIDUAL |
      | actionType | CREATE_NEW         |
    Then I initiate link case api 1
      | ROLE       | APPLICATION MEMBER |
      | actionType | CREATE_NEW         |
    And I run the link case api
    And I capture the case id from link case api
    And I capture the consumer id from link case api
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Spanish        |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    And I select the case Id from the potential matches
    And I verify "ADD" checkbox is selected for "CONSUMER 0" on member matching page
    And I verify "ADD" checkbox is not selected for "CONSUMER 1" on member matching page
    And I select "ADD" checkbox for "APPLICATION MEMBER" on member matching page
    When I click on the "LINK" button on Member Matching
    And I click on the "SAVE_ACTION" button on Member Matching
    When I initiated mars search events api
    And I can provide event information to search event as
      | eventName  |
      | LINK_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 100  | 0    | eventId,desc |
    Then I received "100" events from search event API
    And there is event in search event results for "ATS" with values
      | externalId | internalRefType | externalRefType |
      | caseId     | APPLICATION     | CASE            |
    And there is event in search event results for "ATS" with values
      | externalId    | internalRefType | externalRefType |
      | applicationId | CASE            | APPLICATION     |
    And there is event in search event results for "ATS" with values
      | externalId              | internalRefType | externalRefType      |
      | applicationConsumerIdPI | CONSUMER        | APPLICATION_CONSUMER |
    And there is event in search event results for "ATS" with values
      | externalId              | internalRefType | externalRefType      |
      | applicationConsumerIdAM | CONSUMER        | APPLICATION_CONSUMER |
    And there is event in search event results for "ATS" with values
      | externalId   | internalRefType      | externalRefType |
      | consumerIdPI | APPLICATION_CONSUMER | CONSUMER        |
    And there is event in search event results for "ATS" with values
      | externalId   | internalRefType      | externalRefType |
      | consumerIdAM | APPLICATION_CONSUMER | CONSUMER        |
    And I verify 6 events found for "ATS" with above values

  @CP-29768 @CP-29768-06 @ats-events @burak
  Scenario: Verify Links has been established between Case<->Application and Consumers<->Application Members for New_Case action
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn        | genderCode |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | RANDOM SSN | Male       |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I initiate link case api 0
      | ROLE       | PRIMARY INDIVIDUAL |
      | actionType | CREATE_NEW         |
    Then I initiate link case api 1
      | ROLE       | APPLICATION MEMBER |
      | actionType | CREATE_NEW         |
    And I run the link case api
    And I capture the case id from link case api
    And I capture the consumer id from link case api
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    And I verify "ADD" checkbox is selected for "PRIMARY INDIVIDUAL" on member matching page
    And I verify "ADD" checkbox is selected for "APPLICATION MEMBER" on member matching page
    When I click on the "NEW CASE" button on Member Matching
    And I click on the "SAVE_ACTION" button on Member Matching
    Then I verify I am on the Application Tracking Page
    And I see application Status as "DETERMINING" in the application information
    Then I verify application links component has all the business object linked : "Case"
      | Case       | -- -- |
      | CaseSource | UI    |
    And I click id of "Case" in ATS Links section
      | Type | -- -- |
    And I verify I navigated to "CASE/CONSUMER DETAILS" after clicking linked Entity
    When I initiated mars search events api
    And I can provide event information to search event as
      | eventName  |
      | LINK_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 100  | 0    | eventId,desc |
    Then I received "100" events from search event API
    And there is event in search event results for "ATS" with values
      | externalId | internalRefType | externalRefType |
      | caseIdUI   | APPLICATION     | CASE            |
    And there is event in search event results for "ATS" with values
      | externalId    | internalRefType | externalRefType |
      | applicationId | CASE            | APPLICATION     |
    And there is event in search event results for "ATS" with values
      | externalId              | internalRefType | externalRefType      |
      | applicationConsumerIdPI | CONSUMER        | APPLICATION_CONSUMER |
    And there is event in search event results for "ATS" with values
      | externalId              | internalRefType | externalRefType      |
      | applicationConsumerIdAM | CONSUMER        | APPLICATION_CONSUMER |
    And there is event in search event results for "ATS" with values
      | externalId     | internalRefType      | externalRefType |
      | consumerIdPIUI | APPLICATION_CONSUMER | CONSUMER        |
    And there is event in search event results for "ATS" with values
      | externalId     | internalRefType      | externalRefType |
      | consumerIdAMUI | APPLICATION_CONSUMER | CONSUMER        |
    And I verify 6 events found for "ATS" with above values

  @CP-32343 @CP-32343-07 @ats-events @burak
  Scenario: Verify Links has been established between Case<->Application and Consumers<->Application Members after Linking Application to Case
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Spanish        |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName  | consumerLastName  | dateOfBirth      | ssn              |
      | DUPLICATE FIRST AM | DUPLICATE LAST AM | DUPLICATE DOB AM | DUPLICATE SSN AM |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I initiate link case api 0
      | ROLE       | PRIMARY INDIVIDUAL |
      | actionType | CREATE_NEW         |
    Then I initiate link case api 1
      | ROLE       | APPLICATION MEMBER |
      | actionType | CREATE_NEW         |
    And I run the link case api
    And I capture the case id from link case api
    And I capture the consumer id from link case api
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Spanish        |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName  | consumerLastName  | dateOfBirth      | ssn              |
      | DUPLICATE FIRST AM | DUPLICATE LAST AM | DUPLICATE DOB AM | DUPLICATE SSN AM |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    And I select the case Id from the potential matches
    When I click on the "LINK" button on Member Matching
    And I click on the "SAVE_ACTION" button on Member Matching
    Then I verify I am on the Application Tracking Page
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Case,Task"
      | Case       | -- --           |
      | CaseSource | UI              |
      | Task       | Member Matching |
      | Status     | Complete        |
      | Source     | UI              |
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName  |
      | LINK_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 100  | 0    | eventId,desc |
    Then I received "100" events from search event API
    And there is event in search event results for "ATS" with values
      | externalId | internalRefType | externalRefType |
      | caseId     | APPLICATION     | CASE            |
    And there is event in search event results for "ATS" with values
      | externalId    | internalRefType | externalRefType |
      | applicationId | CASE            | APPLICATION     |
    And there is event in search event results for "ATS" with values
      | externalId              | internalRefType | externalRefType      |
      | applicationConsumerIdPI | CONSUMER        | APPLICATION_CONSUMER |
    And there is event in search event results for "ATS" with values
      | externalId              | internalRefType | externalRefType      |
      | applicationConsumerIdAM | CONSUMER        | APPLICATION_CONSUMER |
    And there is event in search event results for "ATS" with values
      | externalId   | internalRefType      | externalRefType |
      | consumerIdPI | APPLICATION_CONSUMER | CONSUMER        |
    And there is event in search event results for "ATS" with values
      | externalId   | internalRefType      | externalRefType |
      | consumerIdAM | APPLICATION_CONSUMER | CONSUMER        |
    And I verify 6 events found for "ATS" with above values

