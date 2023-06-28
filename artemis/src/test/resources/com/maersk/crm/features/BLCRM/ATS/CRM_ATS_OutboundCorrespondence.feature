Feature: ATS Outbound Correspondence Feature

  @CP-23290 @CP-23290-1 @CP-28818 @CP-28818-01 @crm-regression @ui-ats @burak
  Scenario Outline: Verify Auto Populated Application ID and Send to fields from App Tracking Page for Create OB
    #Verify 2 Link Events generated after OB Correspondence Save action
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | fax       |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Email |
      | Mail |
      | Phone |
      | Fax |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType | phoneNumber | primaryContactTypeInd |
      | faxNumber | 4322344323  | false                 |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1   | addressCity | addressState | addressZip | addressStreet2 |
      | Mailing     | 123 Auto Created | Houston     | Texas        | 77055      | Suite 2        |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn       | genderCode | externalConsumerId | externalConsumerIdType | pregnancyInd | expectedBabies | expectedDueDate |
      | Sally             | Maxer            | Dr             | A                  | 1998-04-03  | 987654321 | Female     | 23456              | Internal               | true         | 2              | 2021-12-08      |
    And I initiate save applications api consumer 1 with program
      | CHIP |
      | Pregnancy Assistance |
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName | consumerMiddleName | accessType  | consumerType | correspondence | authorizedRepOrgName | authorizedRepSignature | authorizedRepSignatureDate       |
      | Authfirstname     | Authlastname     | M                  | Full Access | Broker       | Receive in Addition To     | VA Foundation        | true                   | 2021-06-03T19:54:57.274000+00:00 |
    And I initiate save applications api consumer 2 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Mailing     | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I click create Correspondence on Hamburger button
    When I choose "Application ID Required - AppId" as a correspondence type
    And I verify the Application ID under Regarding Section
    Then I verify "Primary Individual" Checkbox is selected under Send To Section
    Then I verify "DEFAULT SEND TO" Checkbox is selected under Send To Section
    Then I verify "EMAIL" Checkbox is selected and disabled under Send To Section
    Then I verify "MAIL" Checkbox is selected and disabled under Send To Section
    Then I verify "FAX" Checkbox is selected and disabled under Send To Section
    And I verify following destinations listed for Primary Individual
      | Email | Primary-automation@created.com                                                       |
      | Mail  | Application Primary Mailing - 123 Auto Created Suite 2 Houston Texas 77055 |
      | Fax   | 432-234-4323                                                                   |
    And I click Save button for create correspondence page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "2" "LINK_EVENT for Outbound Correspondence" generated and verify details
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName  | module          | eventType                    | projectName |
      | LINK_EVENT | LINK_MANAGEMENT | Outbound Correspondence Save | BLCRM       |

  @CP-23290 @CP-23290-2 @CP-28818 @CP-28818-02 @crm-regression @ui-ats @burak
  Scenario: Verify Auto Populated Application ID and Send to fields after initiating task for Create OB
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | fax       |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Email |
      | Mail |
      | Phone |
      | Fax |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType | phoneNumber | primaryContactTypeInd |
      | faxNumber | 4322344323  | false                 |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1   | addressCity | addressState | addressZip | addressStreet2 |
      | Mailing     | 123 Auto Created | Houston     | Texas        | 77055      | Suite 2        |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn       | genderCode | externalConsumerId | externalConsumerIdType | pregnancyInd | expectedBabies | expectedDueDate |
      | Sally             | Maxer            | Dr             | A                  | 1998-04-03  | 987654321 | Female     | 23456              | Internal               | true         | 2              | 2021-12-08      |
    And I initiate save applications api consumer 1 with program
      | CHIP |
      | Pregnancy Assistance |
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName | consumerMiddleName | accessType  | consumerType | correspondence | authorizedRepOrgName | authorizedRepSignature | authorizedRepSignatureDate       |
      | Authfirstname     | Authlastname     | M                  | Full Access | Broker       | Receive in Addition To     | VA Foundation        | true                   | 2021-06-03T19:54:57.274000+00:00 |
    And I initiate save applications api consumer 2 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Mailing     | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and run Get Application Links Call
    And I will get "Task" ID for "Member Matching" type from the application links response
    And I logged into CRM
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Task Search" page
    And I searched ats task using taskId from "API"
    And I click on search button on task search page
    Then I click on initiate button in task search page
    And I click due in on priority dashboard to close task details
    And I click create Correspondence on Hamburger button
    When I choose "Application ID Required - AppId" as a correspondence type
    And I verify the Application ID under Regarding Section
    Then I verify "Primary Individual" Checkbox is selected under Send To Section
    Then I verify "DEFAULT SEND TO" Checkbox is selected under Send To Section
    Then I verify "EMail" Checkbox is selected and disabled under Send To Section
    Then I verify "Mail" Checkbox is selected and disabled under Send To Section
    Then I verify "Fax" Checkbox is selected and disabled under Send To Section
    And I verify following destinations listed for Primary Individual
      | Email | Primary-automation@created.com                                                       |
      | Mail  | Application Primary Mailing - 123 Auto Created Suite 2 Houston Texas 77055 |
      | Fax   | 432-234-4323                                                                   |
    And I click Save button for create correspondence page

  @CP-28818 @CP-28818-03 @crm-regression @ui-ats @ozgen
  Scenario: Verification of ApplicationId field based on Outbound Correspondence Type
    And I logged into CRM
    And I click create Correspondence on Hamburger button
    When I choose "Application ID Required - AppId" as a correspondence type
    Then I verify "Application id field" is appeared
    And I click Save button for create correspondence page
    And I verify "Application id required" message is getting displayed
    Then I provide "random" for applicationId field
    Then I verify "Validate button" is appeared
    And I click on Validate button for application id check
    And I verify "Application id invalid" message is getting displayed
    When I choose "Body Data Specified - 000bdydata" as a correspondence type
    Then I verify "Code field" is appeared
    When I choose "Application ID Required - AppId" as a correspondence type
    Then I verify "Application id field" is appeared

  @CP-28818 @CP-28818-04 @crm-regression @ui-ats @ozgen
  Scenario: Displaying Application id field as Empty and Required when navigating to page from Other Places
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | fax       |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Email |
      | Mail |
      | Fax |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType | phoneNumber | primaryContactTypeInd |
      | faxNumber | 4322344323  | false                 |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1   | addressCity | addressState | addressZip | addressStreet2 |
      | Mailing     | 123 Auto Created | Houston     | Texas        | 77055      | Suite 2        |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I click create Correspondence on Hamburger button
    When I choose "Application ID Required - AppId" as a correspondence type
    And I verify the Application ID under Regarding Section
    And I click Save button for create correspondence page
    And I navigate to Manual Consumer search page
    And I enter Manual Search criteria fields consumer First Name as "Ethan" and Last Name as "Hunt"
    And I click create Correspondence on Hamburger button
    When I choose "Application ID Required - AppId" as a correspondence type
    Then I verify "Application id field" is appeared
    And I navigate to Contact Record search
    And I click on continue button on warning message
    And I click create Correspondence on Hamburger button
    When I choose "Application ID Required - AppId" as a correspondence type
    Then I verify "Application id field" is appeared

  @CP-28818 @CP-28818-05 @crm-regression @ui-ats @ozgen
  Scenario: Verification of Validate and Edit buttons when we provide applicationId, and saving new Outbound Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | fax       |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Email |
      | Mail |
      | Fax |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType | phoneNumber | primaryContactTypeInd |
      | faxNumber | 4322344323  | false                 |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1   | addressCity | addressState | addressZip |
      | Mailing     | 123 Auto Created | Houston     | Texas        | 77055      |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName |  dateOfBirth | ssn       | genderCode |
      | RANDOM FIRST      | RANDOM FIRST     | 1998-04-03   | 987654321 | Female     |
    And I initiate save applications api consumer 1 with program
      | Pregnancy Assistance |
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName |  accessType  | consumerType | correspondence | authorizedRepOrgName |
      | RANDOM FIRST      | RANDOM LAST      |  Full Access | Broker       | Receive in Addition To     | VA Foundation        |
    And I initiate save applications api consumer 2 with applicationConsumerAddress
      | addressType | addressStreet1 | addressCity | addressState | addressZip | addressCounty |
      | Mailing     | 9 Metro Ave    | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I logged into CRM
    And I saved logged in user ID
    And I click create Correspondence on Hamburger button
    When I choose "Application ID Required - AppId" as a correspondence type
    And I provide recently created application id to applicationId field on outbound correspondence create page
    And I click on Validate button for application id check
    Then I verify "Application id Edit" is appeared
    Then I verify "Primary Individual" recipients data with the following channel data
      | Mail | Email | Fax |
    Then I verify "Application Member" recipients data with the following channel data
      | Mail | Email | Fax |
    Then I verify "Authorized Rep" recipients data with the following channel data
      | Mail | Email | Fax |
    And I choose "Primary Individual" recipient and other destinations
       | Email | Fax |
    And I choose "Authorized Rep" recipient and other destinations
      | Mail |
    And I click Save button for create correspondence page
    And I verify "OB Success" message is getting displayed

