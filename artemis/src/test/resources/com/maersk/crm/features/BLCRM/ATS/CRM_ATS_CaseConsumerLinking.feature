Feature: ATS Member Matching Page: Case And Consumer Linking Functionality
#they need to refactored maybe commented due to UI changes

  @CP-29768 @CP-29768-01 @crm-regression @ui-ats @burak #they need to refactored maybe commented due to UI changes
  Scenario: Create New Case, Create new Consumers When there is no potential matching
    ##Verify Default Selection of All consumers under the new case when there is no potential matches
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureDate | applicationSignatureExistsInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true           | false        | CURRENT YYYYMMDD         | true                          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
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
    And I verify summary section of "APPLICATION" for "CREATE_NEW" action
    And I verify summary section of "PRIMARY INDIVIDUAL" for "CREATE_NEW" action
    And I verify summary section of "APPLICATION MEMBER" for "CREATE_NEW" action
    And I verify "NEW CASE" displays 3 times in the summary section

  @CP-29768 @CP-29768-02 @CP-32329 @CP-32329-02 @CP-32343 @CP-32343-05 @CP-31251 @CP-31251-03 @CP-31274 @CP-31274-03 @crm-regression @ui-ats @burak
  Scenario: Create New Case, Create new Consumers When the user is not selected any consumers from potential matches
    ##Verify Link button is disabled when the user selects none of the existing case from the potential matches
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
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click submit button twice
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    And I verify "LINK" button is disabled on member matching page
    And I verify "ADD" checkbox is not selected for "PRIMARY INDIVIDUAL" on member matching page
    And I verify "ADD" checkbox is not selected for "APPLICATION MEMBER" on member matching page
    And I select "ADD" checkbox for "PRIMARY INDIVIDUAL" on member matching page
    And I select "ADD" checkbox for "APPLICATION MEMBER" on member matching page
    When I click on the "NEW CASE" button on Member Matching
    And I verify summary section of "MATCHED CASE" with following values
      | CASE | No Selection Made |
    And I verify summary section of "APPLICATION" for "CREATE_NEW" action
    And I verify summary section of "PRIMARY INDIVIDUAL" for "CREATE_NEW" action
    And I verify summary section of "APPLICATION MEMBER" for "CREATE_NEW" action
    And I verify "NEW CASE" displays 3 times in the summary section

  @CP-29768 @CP-29768-03 @CP-32343 @CP-32343-04 @CP-31251 @CP-31251-05 @CP-31274 @CP-31274-04 @crm-regression @ui-ats @burak
  Scenario: Verify new case option is disabled when user selects an existing case from the potential matches
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
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click submit button twice
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    And I select the case Id from the potential matches
    And I verify "NEW CASE" button is disabled on member matching page

  @CP-29768 @CP-29768-04 @CP-32329 @CP-32329-03 @CP-32343 @CP-32343-06 @CP-31251 @CP-31251-06 @CP-31274 @CP-31274-05 @crm-regression @ui-ats @burak
  Scenario: Verify New Case and Link buttons are disabled when user is not selected one applicant from each row
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
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click submit button twice
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    And I select "ADD" checkbox for "APPLICATION MEMBER" on member matching page
    And I verify "NEW CASE" button is disabled on member matching page
    And I verify "LINK" button is disabled on member matching page

  @CP-29768 @CP-29768-05 @CRM-Regression @crm-regression @ui-ats @burak
  Scenario: Verify Applicant Status as Determining, eligibility Status is Pending and navigating to case screen after clicking the case
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureDate | applicationSignatureExistsInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true           | true         | CURRENT YYYYMMDD         | true                          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
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
    When I click on the "NEW CASE" button on Member Matching
    And I click on the "SAVE_ACTION" button on Member Matching
    Then I verify I am on the Application Tracking Page
    And I see application Status as "DETERMINING" in the application information
    Then I verify Applicant status "Determining" for PI and AM on application tracking page
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for PI on Members Info Panel
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for AM on Members Info Panel
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Case"
      | Case       | -- -- |
      | CaseSource | UI    |
    And I click id of "Case" in ATS Links section
      | Type | -- -- |
    And I verify I navigated to "CASE/CONSUMER DETAILS" after clicking linked Entity

  @CP-32329 @CP-32329-01 @CP-32343 @CP-32343-01 @crm-regression @ui-ats @burak
  Scenario: Verify Link Option is enabled when user selects an existing case , Verify Summary section after clicking the link button
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
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click submit button twice
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    And I select the case Id from the potential matches
    And I select "ADD" checkbox for "APPLICATION MEMBER" on member matching page
    When I click on the "LINK" button on Member Matching
    And I verify summary section of "MATCHED MEMBERS" with following values
      | PRIMARY INDIVIDUAL | NAME              |
      | APPLICATION MEMBER | NO SELECTION MADE |
    And I verify summary section of "APPLICATION" for "LINK" action
    And I verify summary section of "APPLICATION MEMBER" for "CREATE_NEW" action

  @CP-32329 @CP-32329-04 @crm-regression @ui-ats @burak
  Scenario: Verify Applicant Status as Determining, eligibility Status is Pending and navigating to case screen after clicking the link button
    #Verify Back arrow button navigates back to Application Tracking Page
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
    Then I click submit button twice
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    And I select the case Id from the potential matches
    And I select "ADD" checkbox for "APPLICATION MEMBER" on member matching page
    When I click on the "LINK" button on Member Matching
    And I click on the "SAVE_ACTION" button on Member Matching
    Then I verify I am on the Application Tracking Page
    And I see application Status as "DETERMINING" in the application information
    Then I verify Applicant status "Determining" for PI and AM on application tracking page
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for PI on Members Info Panel
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for AM on Members Info Panel
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Case,Task"
      | Case       | -- --           |
      | CaseSource | UI              |
      | Task       | Member Matching |
      | Status     | Complete        |
      | Source     | UI              |
    And I click id of "Case" in ATS Links section
      | Type | -- -- |
    And I verify I navigated to "CASE/CONSUMER DETAILS" after clicking linked Entity
    And I click on the back arrow button
    And I verify I am on the Application Tracking Page

  @CP-32343 @CP-32343-03 @crm-regression @ui-ats @burak
  Scenario: Verify Applicant Status as Determining, eligibility Status is Pending after linking application to the existing case
    #Verify user is navigated to application tracking tab after clicking the link button
    #Verify user is navigated to the case details page after clicking the linked case ID
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
      | consumerFirstName  | consumerLastName   | dateOfBirth      | ssn              |
      | DUPLICATE FIRST AM | DUPLICATE LAST  AM | DUPLICATE DOB AM | DUPLICATE SSN AM |
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
    Then I click submit button twice
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    And I select the case Id from the potential matches
    And I verify "ADD" checkbox is selected for "CONSUMER 0" on member matching page
    And I verify "ADD" checkbox is selected for "CONSUMER 1" on member matching page
    When I click on the "LINK" button on Member Matching
    And I verify summary section of "MATCHED MEMBERS" with following values
      | PRIMARY INDIVIDUAL | NAME |
      | APPLICATION MEMBER | NAME |
    And I verify summary section of "APPLICATION" for "LINK" action
    And I click on the "SAVE_ACTION" button on Member Matching
    Then I verify I am on the Application Tracking Page
    And I see application Status as "DETERMINING" in the application information
    Then I verify Applicant status "Determining" for PI and AM on application tracking page
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for PI on Members Info Panel
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for AM on Members Info Panel
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Case,Task"
      | Case       | -- --           |
      | CaseSource | UI              |
      | Task       | Member Matching |
      | Status     | Complete        |
      | Source     | UI              |
    And I click id of "Case" in ATS Links section
      | Type | -- -- |
    And I verify I navigated to "CASE/CONSUMER DETAILS" after clicking linked Entity
    And I click on the back arrow button
    And I verify I am on the Application Tracking Page

  @CP-31251 @CP-31251-02 @CP-29766 @CP-29766-01 @CP-29766-02 @CP-33955 @CP-33955-05 @crm-regression @ui-ats @ozgen
  Scenario: Matching inbound application with case and consumer not on a case and selecting LINK Button - Verification of Summary Section and Case Linking
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
    Then I click submit button twice
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    And I see I navigated to Member Matching page
    And I verify that "case id" is displayed on Potential Match Data
    Then I verify that "consumer id" is displayed on Potential Match Data
    Then I verify that "demographic data for case" is displayed on Potential Match Data
    Then I verify that "demographic data for consumer" is displayed on Potential Match Data
    And I select the case Id from the potential matches
    And I verify "ADD" checkbox is selected for "CONSUMER 0" on member matching page
    And I verify "ADD" checkbox is not selected for "CONSUMER 1" on member matching page
    And I select "ADD" checkbox for "APPLICATION MEMBER 1" on member matching page
    And I select "ADD" checkbox for "CONS NOT ON A CASE" on member matching page
    When I click on the "LINK" button on Member Matching
    And I verify summary section of "MATCHED MEMBERS" with following values
      | PRIMARY INDIVIDUAL   | NAME              |
      | APPLICATION MEMBER   | NO SELECTION MADE |
      | APPLICATION MEMBER 2 | NAME              |
    And I verify summary section of "APPLICATION" for "LINK" action
    And I verify summary section of "APPLICATION MEMBER 1" for "CREATE_NEW_AND_LINK" action
    And I verify summary section of "CONS NOT ON A CASE" for "LINK CONSUMER" action
    And I click on the "SAVE_ACTION" button on Member Matching
    Then I verify I am on the Application Tracking Page
    And I see application Status as "DETERMINING" in the application information
    Then I verify Applicant status "Determining" for PI and AM on application tracking page
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for PI on Members Info Panel
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for AM on Members Info Panel
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Case,Task"
      | Case       | -- --           |
      | CaseSource | UI              |
      | Task       | Member Matching |
      | Status     | Complete        |
      | Source     | UI              |
    And I click id of "Case" in ATS Links section
      | Type | -- -- |
    And I verify I navigated to "CASE/CONSUMER DETAILS" after clicking linked Entity
    And I click on the back arrow button
    And I verify I am on the Application Tracking Page

  @CP-31274 @CP-31274-02 @crm-regression @ui-ats @ozgen
  Scenario: Matching inbound application with each case and consumer not on a case and selecting NEW CASE Button - Verification of Summary Section and Creating New Case
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
    And I see I navigated to Member Matching page
    And I select "ADD" checkbox for "APPLICATION MEMBER 1" on member matching page
    And I select "ADD" checkbox for "CONS NOT ON A CASE" on member matching page
    And I select "ADD" checkbox for "ONLY CASE MEMBER" on member matching page
    When I click on the "NEW CASE" button on Member Matching
    And I verify summary section of "MATCHED MEMBERS" with following values
      | PRIMARY INDIVIDUAL   | NAME              |
      | APPLICATION MEMBER   | NO SELECTION MADE |
      | APPLICATION MEMBER 2 | NAME              |
    And I verify summary section of "APPLICATION" for "LINK" action
    And I verify summary section of "APPLICATION MEMBER 1" for "CREATE_NEW_AND_LINK_TO_NEW" action
    And I verify summary section of "CONS NOT ON A CASE" for "LINK CONSUMER to NEW CASE 1" action
    And I verify summary section of "ONLY CASE MEMBER" for "LINK CONSUMER to NEW CASE 2" action
    And I click on the "SAVE_ACTION" button on Member Matching
    Then I verify I am on the Application Tracking Page
    And I see application Status as "DETERMINING" in the application information
    Then I verify Applicant status "Determining" for PI and AM on application tracking page
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for PI on Members Info Panel
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for AM on Members Info Panel
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Case,Task"
      | Case       | -- --           |
      | CaseSource | UI              |
      | Task       | Member Matching |
      | Status     | Complete        |
      | Source     | UI              |
    And I click id of "Case" in ATS Links section
      | Type | -- -- |
    And I verify I navigated to "CASE/CONSUMER DETAILS" after clicking linked Entity
    And I click on the back arrow button
    And I verify I am on the Application Tracking Page

  @CP-33475 @CP-33475-01 @crm-regression @ui-ats @vinuta
  Scenario: Display Existing Applications and status for the case consumer in the potential matching screen
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Spanish        |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Russian        |
    And I initiate save applications api consumer 1 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
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
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Spanish        |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Russian        |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click submit button twice
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    And I see I navigated to Member Matching page
    Then I verify that "existing application" is displayed on Potential Match Data

  @CP-33955 @CP-33955-03 @crm-regression @ui-ats @ozgen 
  Scenario: Each incoming consumer has one potentially matching data, app status goes to Determining
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | Male       | English         | Spanish        |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName  | consumerLastName  | dateOfBirth      | ssn        |
      | DUPLICATE FIRST AM | DUPLICATE LAST AM | DUPLICATE DOB AM | RANDOM SSN |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I clear application save submit "REQUEST" list
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Spanish        |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName  | consumerLastName  | dateOfBirth      | ssn        |
      | DUPLICATE FIRST AM | DUPLICATE LAST AM | DUPLICATE DOB AM | RANDOM SSN |
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
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
    And I see application Status as "DETERMINING" in the application information

  @CP-33955 @CP-33955-04 @crm-regression @ui-ats @ozgen
  Scenario: Each incoming consumer has one potentially matching data AND one doesnt have any matching data, app status goes to Determining
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | Male       | English         | Spanish        |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName  | consumerLastName  | dateOfBirth      | ssn        |
      | DUPLICATE FIRST AM | DUPLICATE LAST AM | DUPLICATE DOB AM | RANDOM SSN |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I clear application save submit "REQUEST" list
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Spanish        |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName  | consumerLastName  | dateOfBirth      | ssn        |
      | DUPLICATE FIRST AM | DUPLICATE LAST AM | DUPLICATE DOB AM | RANDOM SSN |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 2
      | consumerFirstName  | consumerLastName | dateOfBirth   | ssn        |
      | RANDOM FIRST       | RANDOM LAST      | RANDOM DOB    | RANDOM SSN |
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
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
    And I see application Status as "DETERMINING" in the application information

  @CP-33955 @CP-33955-06 @crm-regression @ui-ats @ozgen #will fail due to CP-38485
  Scenario: Potentially matching existing consumers are more than one case, app status goes to Targets Unidentified
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | genderCode |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | Male       |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName  | consumerLastName  | dateOfBirth      | genderCode |
      | DUPLICATE FIRST AM | DUPLICATE LAST AM | DUPLICATE DOB AM | Female     |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | workPhoneNumber | RANDOM PHONE |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create application api for ats
    And I clear application save submit "REQUEST" list
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | genderCode |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | Neutral    |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | Female     |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | workPhoneNumber | RANDOM PHONE |
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
    And I clear application save submit "REQUEST" list
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         | false        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | genderCode |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | Female     |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I initiate save applications api consumer 0 with program
      | Pregnancy Assistance |
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 1
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
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
    And I see application Status as "Targets Unidentified" in the application information

  @CP-33955 @CP-33955-07 @crm-regression @ui-ats @ozgen  #will fail due to CP-38485
  Scenario: Other consumer on target Case who was not a potential match to any incoming consumer, app status goes to Targets Unidentified
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | genderCode |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | Male       |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName  | consumerLastName  | dateOfBirth      | genderCode |
      | DUPLICATE FIRST AM | DUPLICATE LAST AM | DUPLICATE DOB AM | Female     |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | workPhoneNumber | RANDOM PHONE |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create application api for ats
    And I clear application save submit "REQUEST" list
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         | false        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | genderCode |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | Female     |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I initiate save applications api consumer 0 with program
      | Pregnancy Assistance |
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 1
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click submit button twice
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    And I see application Status as "Targets Unidentified" in the application information

